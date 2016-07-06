package de.uni_koeln.spinfo.upcase.lucene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Indexer {

	Logger logger = LoggerFactory.getLogger(getClass());

//	@Autowired
//	private DataBase db;
//
//	@Autowired
//	PropertyReader propertyReader;
//
//	private IndexWriter writer;
//
//	/**
//	 * Creates a new index if there is none on startup (PostConstruct = called
//	 * after all services are initialized).
//	 * 
//	 * @throws IOException
//	 */
//	@PostConstruct
//	public void initialIndex() throws IOException {
//		init(null);
//		int numdocs = (this.writer.numDocs() == 0 ? index() : this.writer.numDocs());
//		logger.info("Index size: " + numdocs);
//		this.writer.close();
//	}
//
//	/**
//	 * Init an indexWriter for the directory specified in properties file.
//	 * 
//	 * @throws IOException
//	 */
//	public void init(final String path) throws IOException {
//		String indexDir = path == null ? propertyReader.getIndexDir() : path;
//		Directory dir = new SimpleFSDirectory(new File(indexDir).toPath());
//		IndexWriterConfig writerConfig = new IndexWriterConfig(new StandardAnalyzer());
//		this.writer = new IndexWriter(dir, writerConfig);
//	}
//
//	/**
//	 * Build index over db, optionally restricted to a predefined size (for test
//	 * purposes).
//	 * 
//	 * @param size
//	 * @return Total number of indexed documents.
//	 */
//	public int index() {
//
//		long start = System.currentTimeMillis();
//		logger.info("Indexing collection...");
//		logger.info("MAXSIZE: " + propertyReader.getMaxIndexSize());
//		logger.info("Docs to index: " + db.getMongoTemplate().count(new Query(), Page.class));
//
//		Iterable<Volume> volumes = db.getVolumeRepository().findAll();
//		int pageCount = 0;
//		boolean breakInnerLoop = false;
//		for (Volume volume : volumes) {
//			List<Page> pages = db.getPageRepository().findByVolumeId(
//					volume.getId());
//			for (Page p : pages) {
//				pageCount++;
//				if (pageCount <= propertyReader.getMaxIndexSize()) {
//					logger.info(pageCount + ": " + p.toString());
//					indexPage(p);
//				} else {
//					breakInnerLoop = true;
//					break;
//				}
//			}
//			if (breakInnerLoop)
//				break;
//		}
//		logger.info("Indexing took " + (System.currentTimeMillis() - start)
//				+ " ms.");
//		return this.writer.numDocs();
//	}
//
//	/**
//	 * Convert page and add to index.
//	 * 
//	 * @param page
//	 * @param langTitle
//	 */
//	private void indexPage(Page page) {
//		Document doc = pageToLuceneDoc(page);
//		if (doc != null) {
//			logger.info("Adding doc: " + page.toString());
//			try {
//				this.writer.addDocument(doc);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * Convert page into Lucene Document.
//	 * 
//	 * @param page
//	 * @param langTitle
//	 * @return Lucene Document to be indexed.
//	 */
//	private Document pageToLuceneDoc(Page page) {
//		Document doc = new Document();
//		doc.add(new StringField("url", page.getUrl(), Store.YES));
//		doc.add(new StringField("pageId", page.getId(), Store.YES));
//		doc.add(new TextField("contents", pageContent(page), Store.YES));
//		doc.add(new TextField("languages", languages(page), Store.YES));
//		doc.add(new TextField("chapterId", chapterIds(page), Store.YES));
//		doc.add(new TextField("chapters", chapters(page), Store.YES));
//		doc.add(new StringField("volumeId", page.getVolumeId(), Store.YES));
//		String volume = db.getVolumeRepository().findOne(page.getVolumeId()).getTitle();
//		doc.add(new TextField("volumeTitle", volume, Store.YES));
//		String ppn = page.getPrintedPageNuber();
//		if (ppn != null)
//			doc.add(new StringField("pageNumber", ppn, Store.YES));
//		return doc;
//	}
//
//	/**
//	 * Retrieve chapter Id(s) a page belongs to. May contain two ids, for in
//	 * some cases chapters start in the middle of a page.
//	 * 
//	 * @param page
//	 * @return (blank separated) chapter id(s)
//	 */
//	private String chapterIds(Page page) {
//		StringBuilder sb = new StringBuilder();
//		List<String> chapterIds = page.getChapterIds();
//		for (String id : chapterIds) {
//			sb.append(id + " ");
//		}
//		return sb.toString().trim();
//	}
//
//	/**
//	 * Retrieve chapter name(s) a page belongs to. May contain two chapter
//	 * titles, for in some cases chapters start in the middle of a page.
//	 * 
//	 * @param page
//	 * @return (blank separated) chapter title(s)
//	 */
//	private String chapters(Page page) {
//		StringBuilder sb = new StringBuilder();
//		List<String> chapterIds = page.getChapterIds();
//		for (String id : chapterIds) {
//			sb.append(db.getChapterRepository().findOne(id).getTitle() + " ");
//		}
//		return sb.toString().trim();
//	}
//
//	/**
//	 * Retrieve page language(s) from LanguageRepository. May contain multiple
//	 * language tags.
//	 * 
//	 * @param page
//	 * @return (blank separated) language tag(s)
//	 */
//	private String languages(Page page) {
//		StringBuilder sb = new StringBuilder();
//		List<String> languageIds = page.getLanguageIds();
//		for (String id : languageIds) {
//			sb.append(db.getLanguageRepository().findOne(id).getTitle() + " ");
//		}
//		return sb.toString().trim();
//	}
//
//	/**
//	 * Retrieve page contents from WordRepository.
//	 * 
//	 * @param page
//	 * @return page contents
//	 */
//	private String pageContent(Page page) {
//		long start = System.currentTimeMillis();
//		StringBuilder sb = new StringBuilder();
//		List<Word> words = db.getWordRepository().findByRange(page.getStart(),
//				page.getEnd());
//		for (Word word : words) {
//			sb.append(word.getCurrentVersion().getValue() + " ");
//		}
//		logger.info("... parse took " + (System.currentTimeMillis() - start)
//				+ " ms.");
//		return sb.toString().trim();
//	}
//
//	/**
//	 * Index has to be closed before searching (or other operations on index).
//	 */
//	public void close() throws IOException {
//		this.writer.close();
//	}
//
//	/**
//	 * @return the number of indexed documents.
//	 */
//	public int getNumDocs() {
//		return writer.numDocs();
//	}
//
//	/**
//	 * Delete existing index.
//	 */
//	public void deleteIndex() {
//		try {
//			this.writer.deleteAll();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @return true, if the writer is open.
//	 */
//	public boolean isAvailable() {
//		return writer.isOpen();
//	}

}
