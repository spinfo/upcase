package de.uni_koeln.spinfo.upcase.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Box;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Word;

@Component
public class HOCRParser {

	private Pattern bboxPattern = Pattern.compile("\\d{1,4}\\s\\d{1,4}\\s\\d{1,4}\\s\\d{1,4}");

	public List<Page> parse(String html, File dir, String imageUrl) throws IOException {

		List<Page> pages = new ArrayList<>();

		int pageCount = 0;
		// for (File file : data) {

		Page page = new Page(new File(dir, imageUrl).getAbsolutePath(), pageCount, 0);
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
					words.add(new Word(token, new Box(x1, x2, y1, y2)));
				}
			}
			page.setWords(words);
		}
		pages.add(page);
		pageCount++;
		// }
		return pages;
	}

}
