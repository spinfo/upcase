package de.uni_koeln.spinfo.upcase.service;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class OCRService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	private Tesseract tesseract;
	
	private FileFilter filer;

	@PreAuthorize("hasRole('USER')")
	public String exctractHOCR(File file) throws TesseractException {
		init();
		String hOCRs = "";
		if (filer.accept(file)) {
			hOCRs = tesseract.doOCR(file);
		}
		return hOCRs;
	}

	@PreAuthorize("hasRole('USER')")
	public Map<String, String> exctractHOCR(List<File> files) throws TesseractException {
		init();
		Map<String, String> fileToHtml = new HashMap<>();
//		List<String> hOCRs = new ArrayList<>();
		for (File file : files) {
			if (filer.accept(file)) {
				String ocr = tesseract.doOCR(file);
				fileToHtml.put(file.getName(), ocr);
				logger.info("Extract text from " + file.getName());
//				hOCRs.add(tesseract.doOCR(file));
			}
		}
		return fileToHtml;
	}

	private void init() {
		tesseract = new Tesseract();
//		tesseract.setLanguage("ita");
		tesseract.setDatapath("/usr/local/share/tessdata");
		tesseract.setHocr(true);
		filer = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				boolean accept = false;
				if (pathname.getName().endsWith(".png"))
					accept = true;
				if (pathname.getName().endsWith(".jpeg"))
					accept = true;
				if (pathname.getName().endsWith(".jpg"))
					accept = true;
				if (pathname.getName().endsWith(".tiff"))
					accept = true;
				return accept;
			}
		};
	}

}
