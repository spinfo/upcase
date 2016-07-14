package de.uni_koeln.spinfo.upcase.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Box;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Word;

@Component
public class HOCRParser {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	private Pattern bboxPattern = Pattern.compile("\\d{1,4}\\s\\d{1,4}\\s\\d{1,4}\\s\\d{1,4}");

	public List<Page> parse(final String collectionId, List<List<String>> exctractHOCR, File userColectionDir) throws IOException {

		List<Page> pages = new ArrayList<>();
		int pageCount = 0;
//		Set<String> fileNames = exctractHOCR.keySet();
//		for (String imageUrl : fileNames) {
		for (List<String> wrapper : exctractHOCR) {
			
			String imageUrl = wrapper.get(0);
			String html = wrapper.get(1);

			logger.info("parsing ... " + imageUrl);
			
			File fullFile = new File(userColectionDir, imageUrl);
			File firstParent = fullFile.getParentFile();
			logger.info("firstParent :: " + firstParent.getName());
			File secondParent = firstParent.getParentFile();
			logger.info("secondParent :: " + secondParent.getName());
			
			String realPath = secondParent.getName() + "/" + firstParent.getName() + "/" + imageUrl;
			logger.info("realPath :: " + realPath);
			
			Page page = new Page(collectionId, realPath, pageCount, 0);
			List<Word> words = new ArrayList<>();

			// hOCR document
			Document hOCR = Jsoup.parse(html, "UTF-8");
			// Page
			Elements ocrPage = hOCR.select(".ocr_page");
			// Paragraphs in Page
			Elements ocrPars = ocrPage.select(".ocr_par");

			for (Element parElement : ocrPars) {
				// Lines in Paragraph
				// Elements ocrLine = ocrPars.select(".ocr_line");

				// Words in Line
				Elements ocrxWords = parElement.select(".ocrx_word");
				for (Element wordElement : ocrxWords) {

					String title = wordElement.attr("title");
					Matcher matcher = bboxPattern.matcher(title);
					matcher.find();

					String[] bBox = matcher.group(0).trim().split(" ");
					String token = wordElement.text();

					if (!token.isEmpty()) {
						
						int x1 = Integer.parseInt(bBox[0]);
						int x2 = Integer.parseInt(bBox[2]);
						int y1 = Integer.parseInt(bBox[1]);
						int y2 = Integer.parseInt(bBox[3]);
						
						Word word = new Word(token, new Box(x1, x2, y1, y2));
						words.add(word);
					}
				}
				page.setWords(words);
			}
			pages.add(page);
			pageCount++;
		}
		
		for (Page page : pages) {
			logger.info(page.toString());
		}
		return pages;
	}

}
