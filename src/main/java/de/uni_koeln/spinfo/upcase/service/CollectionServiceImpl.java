package de.uni_koeln.spinfo.upcase.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import de.uni_koeln.spinfo.upcase.CollectionAlreadyExistsException;
import de.uni_koeln.spinfo.upcase.lucene.Indexer;
import de.uni_koeln.spinfo.upcase.model.form.UploadForm;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Word;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.WordRepository;
import de.uni_koeln.spinfo.upcase.util.HOCRParser;
import net.sourceforge.tess4j.TesseractException;

@Component
public class CollectionServiceImpl implements CollectionService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UpcaseUserRepository upcaseUserRepository;

	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private CollectionRepository collectionRepository;

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private OCRService ocrService;

	@Autowired
	private HOCRParser hOCRParser;

	@Autowired
	private Indexer indexer;

	@Override
	public String createCollection(UploadForm uploadForm, String path) throws CollectionAlreadyExistsException {

		final String name = SecurityContextHolder.getContext().getAuthentication().getName();
		UpcaseUser user = upcaseUserRepository.findByEmail(name);
		String collectionName = uploadForm.getCollectionName();

		if (collectionExists(uploadForm))
			throw new CollectionAlreadyExistsException(collectionName);

		List<MultipartFile> files = uploadForm.getFiles();

		File userColectionDir = createCollectionDir(uploadForm, user, files, path);

		Collection collection = new Collection(collectionName, user, uploadForm.getDescription(),
				userColectionDir.getAbsolutePath());
		collectionRepository.save(collection);

		try {
			List<File> convFiles = multipartsToFiles(files);
			logger.info("OCR on progress...");

			List<List<String>> exctractHOCR = ocrService.exctractHOCR(convFiles);

			convFiles.forEach(f -> f.delete());

			logger.info("Saving hOCR files...");
			for (List<String> wrapper : exctractHOCR) {
				String imageUrl = wrapper.get(0);
				String hOCR = wrapper.get(1);
				saveToUserDir(userColectionDir, imageUrl.replaceAll("\\..{1,4}", ".html"), hOCR.getBytes());
			}

			logger.info("Parsing hOCR files...");
			List<Page> pages = hOCRParser.parse(collection.getId(), exctractHOCR, userColectionDir);

			logger.info("Save word object within pages...");
			for (Page page : pages) {
				List<Word> words = page.getWords();
				wordRepository.save(words);
			}
			pageRepository.save(pages);
			List<String> pageIds = pages.stream().map(p -> p.getId()).collect(Collectors.toList());
			collection.setPages(new HashSet<>(pageIds));

			logger.info("Update collection..." + collection.getId());
			collectionRepository.update(collection);

			logger.info("Collection updated :: " + collection);

			indexer.index(collection);

			return collection.getId();

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} catch (TesseractException e) {
			e.printStackTrace();
		}

		return null;
	}

	private File createCollectionDir(UploadForm uploadForm, UpcaseUser user, List<MultipartFile> multiParts, String path) {
		logger.info(path + "/user_" + user.getId(), uploadForm.getCollectionName());
		File userColectionDir = new File(path + "/user_" + user.getId(), uploadForm.getCollectionName());
		userColectionDir.mkdirs();
		try {
			logger.info("Create userColDir and saving files...");
			for (MultipartFile multipartFile : multiParts) {
				saveToUserDir(userColectionDir, multipartFile.getOriginalFilename(), multipartFile.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userColectionDir;
	}

	private List<File> multipartsToFiles(List<MultipartFile> multiParts) throws IllegalStateException, IOException {
		List<File> toReturn = new ArrayList<>();
		for (MultipartFile multipartFile : multiParts) {
			File convFile = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(convFile);
			toReturn.add(convFile);
		}
		return toReturn;
	}

	private void saveToUserDir(File userColectionDir, String fileName, byte[] bytes) throws IOException {
		Path file = Paths.get(userColectionDir.getPath(), fileName);
		Files.write(file, bytes);
		logger.info("Wrote file " + file.getFileName());
	}

	private boolean collectionExists(UploadForm uploadForm) {
		Collection findbyTitle = collectionRepository.findbyTitle(uploadForm.getCollectionName());
		if (findbyTitle != null) {
			return true;
		}
		return false;
	}

}
