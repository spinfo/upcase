package de.uni_koeln.spinfo.upcase.test.data;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import de.uni_koeln.spinfo.upcase.model.form.RegistrationForm;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Box;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Word;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepositoryImpl;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.WordRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@EnableTransactionManagement
public class ImportTests {

	private static final MongodStarter starter = MongodStarter.getDefaultInstance();
	private static MongodExecutable _mongodExe;
	private static MongodProcess _mongod;
	// private static MongoClient _mongo;

	private Collection collection;
	private static List<Page> pages;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CollectionRepository collectionRepository;

	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private WordRepository wordRepository;
	
	@Autowired
	private UpcaseUserRepository userRepository;

	@Configuration
	static class RepositoryTestConfiguration {

		@Bean
		public MongoClient mongo() throws Exception {
			return new MongoClient("localhost");
		}

		@Bean
		public MongoRepositoryFactory repositoryFactory() throws Exception {
			return new MongoRepositoryFactory(mongoTemplate());
		}

		@Bean
		public MongoDbFactory mongoDbFactory() throws Exception {
			return new SimpleMongoDbFactory(mongo(), "upcase_test_db");
		}

		@Bean
		public MongoTemplate mongoTemplate() throws Exception {
			return new MongoTemplate(mongoDbFactory());
		}

		@Bean
		public CollectionRepository collectionRepository() throws Exception {
			return new CollectionRepositoryImpl(mongoTemplate());
		}

		@Bean
		public PageRepository pageRepository() throws Exception {
			return repositoryFactory().getRepository(PageRepository.class);
		}

		@Bean
		public WordRepository wordRepository() throws Exception {
			return repositoryFactory().getRepository(WordRepository.class);
		}
		
		@Bean
		public UpcaseUserRepository userRepository() throws Exception {
			return repositoryFactory().getRepository(UpcaseUserRepository.class);
		}
		
		@Bean
		public MappingMongoConverter mongoConverter() throws Exception {
			MongoMappingContext mappingContext = new MongoMappingContext();
			DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
			MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
			mongoConverter.setCustomConversions(customConversions());
			return mongoConverter;
		}

		@Bean
		public CustomConversions customConversions() {
			List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
			converters.add(new DBObjectToStringConverter());
			converters.add(new StringToDBObjectConverter());
			return new CustomConversions(converters);
		}

		class DBObjectToStringConverter implements Converter<DBObject, String> {
			@Override
			public String convert(DBObject source) {
				return source == null ? null : source.toString();
			}
		}

		class StringToDBObjectConverter implements Converter<String, DBObject> {

			@Override
			public DBObject convert(String source) {

				try {
					@SuppressWarnings("unchecked")
					HashMap<String, Object> map = new ObjectMapper().readValue(source, HashMap.class);
					return new BasicDBObject(map);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		}
	}

	@BeforeClass
	public static void init() throws IOException {
		createCollection();
		setUpDatabase();
	}

	@AfterClass
	public static void destroy() {
		_mongod.stop();
		_mongodExe.stop();
	}

	private static void setUpDatabase() throws UnknownHostException, IOException {
		_mongodExe = starter.prepare(new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(27017, Network.localhostIsIPv6())).build());
		_mongod = _mongodExe.start();
		// _mongo = new MongoClient("localhost", 27017);
	}

	private static void createCollection() throws IOException {
		Pattern bboxPattern = Pattern.compile("\\d{1,4}\\s\\d{1,4}\\s\\d{1,4}\\s\\d{1,4}");


		pages = new ArrayList<>();

		File[] data = new File("OCR").listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".html");
			}

		});

		int pageCount = 0;
		for (File file : data) {

			String imgUrl = file.getName().replace(".html", ".png");
			Page page = new Page("13245", imgUrl, pageCount, 0);
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
				
				// Words  in Line
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
		}

	}

	@Test
	public void connection() {
		String dbName = mongoTemplate.getDb().getName();
		Assert.assertNotNull(dbName);
		Assert.assertEquals("upcase_test_db", dbName);
	}

	@Test
	public void saveCollectionService() {
		
		UpcaseUser upcaseUser = new UpcaseUser(new RegistrationForm("Mihail", "Atanassov",
				"matanass@uni-koeln.de", "Department of Computational Linguistics", "secret123", "secret123"));
		
		userRepository.save(upcaseUser);
		Assert.assertNotNull(upcaseUser.getId());
		System.out.println(upcaseUser + " ID :: " + upcaseUser.getId());
		// Create collection
		collection = new Collection("My Test Collection");
		collection.setContributable(false);
		collection.setOwner(upcaseUser.getId());
		long count = collectionRepository.count();
		Assert.assertNotNull(collection);
		Assert.assertEquals(0, count);
		// Save pages
		pageRepository.save(pages);
		List<String> pageIds = new ArrayList<>();
		for (Page page : pages) {
			pageIds.add(page.getId());
			// Save words
			wordRepository.save(page.getWords());
			System.out.println(page);
		}
		collection.setPages(new HashSet<String>(pageIds));
		// Save collection
		collectionRepository.save(collection);
		count = collectionRepository.count();
		Assert.assertEquals(1, count);
		// Return collection
		System.out.println(collection);
	}

}
