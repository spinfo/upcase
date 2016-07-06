package de.uni_koeln.spinfo.upcase.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
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
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;
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
	private OCRService ocrService;

	@Autowired
	private HOCRParser hOCRParser;

	@Override
	public String createCollection(UploadForm uploadForm) throws CollectionAlreadyExistsException {
		final String name = SecurityContextHolder.getContext().getAuthentication().getName();
		final MultipartFile multiPart = uploadForm.getMultiPart();

		UpcaseUser user = upcaseUserRepository.findByEmail(name);

		if (collectionExists(uploadForm)) {
			throw new CollectionAlreadyExistsException();
		} else {
			File userColectionDir = new File("user_" + user.getId(), uploadForm.getCollectionName());
			userColectionDir.mkdirs();

			try {
				saveToUserDir(userColectionDir, multiPart.getOriginalFilename(), multiPart.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				File file = multipartToFile(multiPart);
				String hOCR = ocrService.exctractHOCR(file);
				List<Page> pages = hOCRParser.parse(hOCR, userColectionDir, file.getName());
				saveToUserDir(userColectionDir, multiPart.getOriginalFilename().replaceAll("\\..{1,4}", ".html"), hOCR.getBytes());
				pageRepository.save(pages);
				List<String> pageIds = pages.stream().map(p -> p.getId()).collect(Collectors.toList());
				Collection collection = new Collection(uploadForm.getCollectionName(), user,
						new HashSet<String>(pageIds));
				collectionRepository.save(collection);
				file.delete();
				// return collection.getId();

			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			} catch (TesseractException e) {
				e.printStackTrace();
			}
		}

		return "1";
	}

	private File multipartToFile(MultipartFile multiPart) throws IllegalStateException, IOException {
		File convFile = new File(multiPart.getOriginalFilename());
		multiPart.transferTo(convFile);
		return convFile;
	}

	private void saveToUserDir(File userColectionDir, String fileName, byte[] bytes) throws IOException {
		Path file = Paths.get(userColectionDir.getAbsolutePath(), fileName);
		Files.write(file, bytes);
	}

	private boolean collectionExists(UploadForm uploadForm) {
		Collection findbyTitle = collectionRepository.findbyTitle(uploadForm.getCollectionName());
		if (findbyTitle != null) {
			return true;
		}
		return false;
	}

}
