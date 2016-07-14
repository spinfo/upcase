package de.uni_koeln.spinfo.upcase.service;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class OCRService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	private Tesseract tesseract;
	
	private FileFilter filer;

	public String exctractHOCR(File file) throws TesseractException {
		init();
		String hOCRs = "";
		if (filer.accept(file)) {
			hOCRs = tesseract.doOCR(file);
		}
		return hOCRs;
	}

	public List<List<String>> exctractHOCR(List<File> files) throws TesseractException {
		init();
//		Map<String, String> fileToHtml = new HashMap<>();
		List<List<String>> hOCRs = new ArrayList<>();
		
		for (File file : files) {
			if (filer.accept(file)) {
				List<String> fileUrlHOCR = new ArrayList<>();
				
				String ocr = tesseract.doOCR(file);
				fileUrlHOCR.add(file.getName());
				fileUrlHOCR.add(ocr);
				hOCRs.add(fileUrlHOCR);
//				fileToHtml.put(file.getName(), ocr);
				
				logger.info("Extract text from " + file.getName());
//				hOCRs.add(tesseract.doOCR(file));
			}
		}
		return hOCRs;
	}

	private void init() {
		tesseract = new Tesseract();
//		tesseract.setLanguage("ita");
//		tesseract.setDatapath("/usr/share/tesseract-ocr/tessdata");
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
