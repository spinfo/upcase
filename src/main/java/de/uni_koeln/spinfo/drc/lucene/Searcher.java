package de.uni_koeln.spinfo.drc.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.uni_koeln.spinfo.drc.util.PropertyReader;

@Service
public class Searcher {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	PropertyReader propertyReader;

	private int totalHits;

	/**
	 * Search a given index (index directory specified in
	 * src/main/resources/application.properties).
	 * 
	 * @param q
	 *            the query
	 * @return a list of hits, wrapped up in objects of type
	 *         {@link SearchResult}.
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<SearchResult> search(String q) throws IOException,
			ParseException {

		DirectoryReader dirReader = openDirectory();
		IndexSearcher is = new IndexSearcher(dirReader);

		QueryParser parser = new QueryParser("contents", new StandardAnalyzer());
		Query query = parser.parse(q);
		TopDocs hits = is.search(query, propertyReader.getMaxHits());
		this.setTotalHits(hits.totalHits);
		logger.info("QUERY: " + query);

		List<SearchResult> resultList = toResult(is, hits);

		dirReader.close();
		return resultList;
	}

	private Directory getLuceneDir() throws IOException {
		String indexDir = propertyReader.getIndexDir();
		Directory dir = new SimpleFSDirectory(new File(indexDir).toPath());
		return dir;
	}

	private List<SearchResult> toResult(IndexSearcher is, TopDocs hits)
			throws IOException {
		List<SearchResult> resultList = new ArrayList<SearchResult>();
		for (int i = 0; i < hits.scoreDocs.length; i++) {
			ScoreDoc scoreDoc = hits.scoreDocs[i];
			Document doc = is.doc(scoreDoc.doc);
			SearchResult result = wrapFieldResults(doc);
			resultList.add(result);
		}
		return resultList;
	}

	/**
	 * Wrap up all field contents of a hit.
	 * 
	 * @param doc
	 * 
	 * @return a SearchResult object reflecting the given Document.
	 */
	private SearchResult wrapFieldResults(Document doc) {

		String filename = doc.get("url");
		String pageId = doc.get("pageId");
		String content = doc.get("contents");
		String language = doc.get("languages");
		String chapterId = doc.get("chapterId");
		String chapter = doc.get("chapters");
		String volumeId = doc.get("volumeId");
		String volumeTitle = doc.get("volumeTitle");

		SearchResult result = new SearchResult();
		result.setFilename(filename);
		result.setContent(content);
		result.setPageId(pageId);
		result.setLanguage(language);
		result.setChapterId(chapterId);
		result.setChapter(chapter);
		result.setVolumeId(volumeId);
		result.setVolumeTitle(volumeTitle);
		result.setURL("localhost:8080/drc/page?pageId=" + pageId);

		return result;
	}

	/**
	 * @return totalHits in current search
	 */
	public int getTotalHits() {
		return totalHits;
	}

	/**
	 * @param totalHits
	 */
	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public List<SearchResult> withQuotations(final String searchPhrase,
			boolean regex, final String volumeSelection,
			final String chapterSelection, final String langSelection,
			int numFragments, int page) throws IOException, ParseException,
			InvalidTokenOffsetsException {

		DirectoryReader dirReader = openDirectory();
		IndexSearcher is = new IndexSearcher(dirReader);
		StandardAnalyzer analyzer = new StandardAnalyzer();

		int hitsPerPage = propertyReader.getHitsPerPage();
		TopScoreDocCollector collector = TopScoreDocCollector.create(dirReader
				.maxDoc());
		int startIndex = (page - 1) * hitsPerPage;

		Query query = getQuery(searchPhrase, regex, volumeSelection,
				chapterSelection, langSelection, analyzer);

		is.search(query, collector);
		TopDocs hits = collector.topDocs(startIndex, hitsPerPage);

		this.setTotalHits(hits.totalHits);
		SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter(
				"<span class=\"quotation\">", "</span>");
		QueryScorer queryScorer = new QueryScorer(query, "contents");
		Highlighter highlighter = new Highlighter(htmlFormatter, queryScorer);
		highlighter
				.setTextFragmenter(new SimpleSpanFragmenter(queryScorer, 100));
		List<SearchResult> resultList = new ArrayList<>();
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			int id = scoreDoc.doc;
			Document doc = is.doc(id);
			String text = doc.get("contents");
			TokenStream tokenStream = analyzer.tokenStream("contents", text);
			TextFragment[] frag = highlighter.getBestTextFragments(tokenStream,
					text, false, numFragments);
			List<String> quots = new ArrayList<>();
			for (TextFragment textFragment : frag) {
				if ((textFragment != null) && (textFragment.getScore() > 0)) {
					String quotation = "[...] " + textFragment.toString()
							+ " [...]";
					quots.add(quotation);
				}
			}
			SearchResult result = wrapFieldResults(doc, quots);
			resultList.add(result);
		}

		return resultList;
	}

	private Query getQuery(final String searchPhrase, boolean regex,
			final String volumeSelection, final String chapterSelection,
			final String langSelection, StandardAnalyzer analyzer)
			throws ParseException {
		
		String contents = "";
		if (regex) {
			contents = "contents:/" + searchPhrase + "/ ";
		} else {
			contents = "contents:" + "\"" + searchPhrase + "\"" + "~10 ";
		}
		String volumeQuery = "";
		if (!volumeSelection.equals("alle")) {
			volumeQuery = "AND volumeTitle:\"" + volumeSelection + "\" ";
		}
		String chapterQuery = "";
		if (!chapterSelection.equals("alle")) {
			chapterQuery = "AND chapters:\"" + chapterSelection + "\" ";
		}
		String langQuery = "";
		if (!langSelection.equals("alle")) {
			logger.info(langSelection);
			chapterQuery = "AND languages:\"" + langSelection + "\" ";
		}
		QueryParser parser = new QueryParser("contents", analyzer);
		String q = contents + volumeQuery + chapterQuery + langQuery;

		Query query = parser.parse(q);
		logger.info("QUERY:" + query);
		return query;
	}

	private DirectoryReader openDirectory() throws IOException {
		return DirectoryReader.open(getLuceneDir());
	}

	private SearchResult wrapFieldResults(Document doc, List<String> quotations) {
		SearchResult result = new SearchResult();
		result.setFilename(doc.get("url"));
		result.setQuotations(quotations);
		result.setLanguage(doc.get("languages"));
		result.setChapter(doc.get("chapters"));
		result.setVolumeTitle(doc.get("volumeTitle"));
		result.setURL("localhost:8080/drc/page?pageName=" + doc.get("url"));
		return result;
	}

}
