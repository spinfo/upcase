package de.uni_koeln.spinfo.upcase.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Word;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;

@Service
public class Indexer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private CollectionRepository collectionRepository;

	private IndexWriter writer;

	/**
	 * Init an indexWriter for the directory specified in properties file.
	 * 
	 * @throws IOException
	 */
	
	@PostConstruct
	public void postContruct() throws IOException {
		init();
		this.writer.deleteAll();
		this.writer.close();
	}
	public void init() throws IOException {
		File indexDir = new File("index");
		indexDir.mkdirs();
		Directory dir = new SimpleFSDirectory(indexDir.toPath());
		IndexWriterConfig writerConfig = new IndexWriterConfig(new StandardAnalyzer());
		this.writer = new IndexWriter(dir, writerConfig);
	}

	public void index(Collection collection) throws IOException {
		// TODO ROUTINE FOR LUCENE INDEX UPDATE

		init();

		long start = System.currentTimeMillis();
		logger.info("Indexing collection ...");

		Set<String> pages = collection.getPages();
		for (String pageId : pages) {
			Page page = pageRepository.findByPageId(pageId);
			indexPage(page, collection);
		}
		this.writer.close();
		logger.info("Indexing took " + (System.currentTimeMillis() - start) + " ms.");

	}

	public void index(List<Collection> collections) throws IOException {
		init();
		long start = System.currentTimeMillis();
		logger.info("Reindexing collections ...");
		for (Collection collection : collections) {
			Set<String> pages = collection.getPages();
			for (String pageId : pages) {
				Page page = pageRepository.findByPageId(pageId);
				indexPage(page, collection);
			}
		}
		this.writer.close();
		logger.info("Indexing took " + (System.currentTimeMillis() - start) + " ms.");
	}

	private void indexPage(Page page, Collection collection) {
		Document doc = pageToLuceneDoc(page, collection);
		if (doc != null) {
			logger.info("Adding doc: " + page.toString());
			try {
				if (writer == null)
					logger.info("writer is null");

				this.writer.addDocument(doc);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Document pageToLuceneDoc(Page page, Collection collection) {

		String ownerId = collection.getOwner();
		String collectionId = collection.getId();
		String collectionTitle = collection.getTitle();
		String collectionDescription = collection.getDescription();

		Document doc = new Document();
		doc.add(new StringField("imageUrl", page.getImageUrl(), Store.YES));
		doc.add(new StringField("ownerId", ownerId, Store.YES));
		doc.add(new StringField("collectionId", collectionId, Store.YES));
		doc.add(new StringField("pageId", page.getId(), Store.YES));

		doc.add(new TextField("contents", pageContent(page), Store.YES));
		doc.add(new TextField("collectionTitle", collectionTitle, Store.YES));
		doc.add(new TextField("collectionDescription", collectionDescription, Store.YES));
		return doc;
	}

	private String pageContent(Page page) {
		long start = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		List<Word> words = page.getWords();
		for (Word word : words) {
			sb.append(word.getToken() + " ");
		}
		logger.info("Page parse took " + (System.currentTimeMillis() - start) + " ms.");
		return sb.toString().trim();
	}

	/**
	 * Index has to be closed before searching (or other operations on index).
	 */
	public void close() throws IOException {
		this.writer.close();
	}

	/**
	 * @return the number of indexed documents.
	 */
	public int getNumDocs() {
		return writer.numDocs();
	}

	/**
	 * Delete existing index.
	 */
	public void deleteIndex() {
		try {
			init();
			this.writer.deleteAll();
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete existing index.
	 */
	public void reIndex() {
		try {
			init();
			List<Collection> collections = collectionRepository.findAll();
			index(collections);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return true, if the writer is open.
	 */
	public boolean isAvailable() {
		return writer.isOpen();
	}
	
	public void update(Page page, Collection collection) throws IOException {
		if(!isAvailable()) {
			init();
		}
		Document doc = pageToLuceneDoc(page, collection);
		this.writer.updateDocument(new Term("pageId", page.getId()), doc);
		this.writer.commit();
		this.writer.close();
	}

}
