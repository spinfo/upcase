package de.uni_koeln.spinfo.drc.user;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;

import de.uni_koeln.spinfo.drc.model.RegistrationForm;
import de.uni_koeln.spinfo.drc.mongodb.data.document.UpcaseUser;
import de.uni_koeln.spinfo.drc.mongodb.repository.UpcaseUserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserTests {

	@Autowired 
	private UpcaseUserRepository upcaseUserRepository;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void registerUser() {
		RegistrationForm registrationForm = new RegistrationForm("Mihail", "Atanassov", "matanass@uni-koeln.de",  "University of Cologne", "asdf123!", "asdf123!");
		UpcaseUser upcaseUser = new UpcaseUser(registrationForm);
		upcaseUser = upcaseUserRepository.save(upcaseUser);
		Assert.assertNotNull(upcaseUser.getId());
		logger.info(upcaseUser.getId());
		logger.info(upcaseUser.getHash());
	}
	
	@Configuration
	@EnableMongoRepositories
	@ComponentScan(basePackageClasses = {UpcaseUserRepository.class})  
	@PropertySource("classpath:database.properties")
	static class PersonRepositoryTestConfiguration extends AbstractMongoConfiguration {

	    @Override
	    protected String getDatabaseName() {
	        return "upcase-test";
	    }
		
	    @Override
		public Mongo mongo() {
			return new Fongo("mongo-test").getMongo();
		}
		
	    @Override
	    protected String getMappingBasePackage() {
	        return UserTests.class.getPackage().getName();
	    }

	}
}
