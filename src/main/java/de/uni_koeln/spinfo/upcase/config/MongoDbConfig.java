package de.uni_koeln.spinfo.upcase.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories(basePackages = "de.uni_koeln.spinfo.upcase.mongodb.repository")
@PropertySource("classpath:database.properties")
public class MongoDbConfig extends AbstractMongoConfiguration {

	@Value("${mongodb.port}")
	private int port;

	@Value("${mongodb.host}")
	private String host;

	@Value("${mongodb.dbname}")
	private String dbName;

	@Value("${mongodb.user}")
	private String userName;

	@Value("${mongodb.password}")
	private String password;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	@Override
	public MongoClient mongo() throws Exception {
		if(userName.isEmpty() || password.isEmpty())
			return new MongoClient(host);
		else {
			ServerAddress address = new ServerAddress(host, port);
			MongoCredential credential = MongoCredential.createMongoCRCredential(userName, getDatabaseName(), password.toCharArray());
			List<MongoCredential> creds = new ArrayList<>();
			creds.add(credential);
			return new MongoClient(address, creds);
		}
	}

	@Bean
	public MongoRepositoryFactory repositoryFactory() throws Exception {
		return new MongoRepositoryFactory(mongoTemplate());
	}

	@Bean
	@Override
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(mongo(), getDatabaseName());
	}

	@Bean
	@Override
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
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
	@Override
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

	@Override
	protected String getDatabaseName() {
		return dbName;
	}

}
