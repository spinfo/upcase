package de.uni_koeln.spinfo.upcase.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class PropertyReader {

	private Logger logger = LoggerFactory.getLogger(getClass());

//	private Properties properties;
//	private ResourceLoader resourceLoader;

	@Value("${lucene.index}")
	private String index;
	
	@Value("${lucene.index.maxSize}")
	private int maxSize;
	
	@Value("${lucene.search.maxHits}")
	private int maxHits;
	
	@Value("${lucene.search.pageHits}")
	private int pagehits;
	
//	private static final String INDEX = "lucene.index";
//	private static final String MAXSIZE = "lucene.index.maxSize";
//	private static final String MAXHITS = "lucene.search.maxHits";
//	private static final String PAGEHITS = "lucene.search.pageHits";

//	@Inject
//	public PropertyReader(ResourceLoader resourceLoader) throws IOException {
//		this.resourceLoader = resourceLoader;
//		this.properties = new Properties();
//		init();
//	}

//	public void init() throws IOException {
//		loadProperties(resourceLoader.getResource("file:application.properties").getFile());
//	}

//	private void loadProperties(File file) throws IOException {
//		logger.info("Loading properties from file : " + file.getAbsolutePath());
//		FileInputStream fileInputStream = new FileInputStream(file);
//		this.properties.load(fileInputStream);
//		fileInputStream.close();
//	}

	public String getIndexDir() {
//		return properties.getProperty(INDEX);
		return index;
	}

	public int getMaxIndexSize() {
//		return Integer.parseInt(properties.getProperty(MAXSIZE));
		return maxSize;
	}

	public int getMaxHits() {
		return maxHits;
//		return Integer.parseInt(properties.getProperty(MAXHITS));
	}
	
	public int getHitsPerPage() {
		return pagehits;
//		return Integer.parseInt(properties.getProperty(PAGEHITS));
	}


}
