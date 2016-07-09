package de.uni_koeln.spinfo.upcase.test.data;

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
import org.junit.Before;
import org.junit.Test;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Box;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Word;

public class ExportData {

	private Page page;

	@Before
	public void prepare() throws IOException {
		Pattern bboxPattern = Pattern.compile("\\d{1,4}\\s\\d{1,4}\\s\\d{1,4}\\s\\d{1,4}");
		File file = new File("PPN345572629_0009-0010.html");
		page = new Page(file.getAbsolutePath(), 0, 0);
		List<Word> words = new ArrayList<>();

		// hOCR document
		Document hOCR = Jsoup.parse(file, "UTF-8");
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

	}

	@Test
	public void exportToPDF() {
		
		
		
		List<Word> words = page.getWords();
		for (Word word : words) {
			
			String token = word.getToken();
			
			Box box = word.getBox();
			int x1 = box.getX1();
			int x2 = box.getX2();
			int y1 = box.getY1();
			int y2 = box.getY2();
			
			System.out.println(token + " -> " + box);
			
			// iTEXT
		}
		
	}

}
