package de.uni_koeln.spinfo.upcase.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

	@Override
	public String createCollection(UploadForm uploadForm) throws CollectionAlreadyExistsException {

		final String name = SecurityContextHolder.getContext().getAuthentication().getName();
		UpcaseUser user = upcaseUserRepository.findByEmail(name);
		String collectionName = uploadForm.getCollectionName();

		if (collectionExists(uploadForm))
			throw new CollectionAlreadyExistsException(collectionName);

		List<MultipartFile> files = uploadForm.getFiles();
		Collection c = new Collection(collectionName, user);
		collectionRepository.save(c);

		File userColectionDir = createCollectionDir(uploadForm, user, files);

		try {
			List<File> convFiles = multipartsToFiles(files);
			logger.info("OCR on progress...");
			Map<String, String> exctractHOCR = ocrService.exctractHOCR(convFiles);

			logger.info("Saving hOCR files...");
			for (String imageUrl : exctractHOCR.keySet()) {
				String hOCR = exctractHOCR.get(imageUrl);
				saveToUserDir(userColectionDir, imageUrl.replaceAll("\\..{1,4}", ".html"), hOCR.getBytes());
			}

			logger.info("Parsing hOCR files...");
			List<Page> pages = hOCRParser.parse(exctractHOCR, userColectionDir);

			logger.info("Save word object within pages...");
			for (Page page : pages) {
				List<Word> words = page.getWords();
				wordRepository.save(words);
			}

			pageRepository.save(pages);

			List<String> pageIds = pages.stream().map(p -> p.getId()).collect(Collectors.toList());
			c.setPages(new HashSet<>(pageIds));

			logger.info("Update collection..." + c.getId());
			collectionRepository.update(c);
			logger.info("Collection updated :: " + c);
			convFiles.forEach(f -> f.delete());
			return c.getId();

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} catch (TesseractException e) {
			e.printStackTrace();
		}

		return null;
	}

	private File createCollectionDir(UploadForm uploadForm, UpcaseUser user, List<MultipartFile> multiParts) {
		File userColectionDir = new File("user_" + user.getId(), uploadForm.getCollectionName());
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
		Path file = Paths.get(userColectionDir.getAbsolutePath(), fileName);
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
