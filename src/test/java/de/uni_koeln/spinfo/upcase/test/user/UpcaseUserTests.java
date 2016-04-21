package de.uni_koeln.spinfo.upcase.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.uni_koeln.spinfo.upcase.context.PersistenceContext;
import de.uni_koeln.spinfo.upcase.context.SecurityContext;
import de.uni_koeln.spinfo.upcase.context.WebAppContext;
import de.uni_koeln.spinfo.upcase.model.RegistrationForm;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.UpcaseUserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
	@ContextConfiguration(classes = { PersistenceContext.class }),
	@ContextConfiguration(classes = { WebAppContext.class }),
	@ContextConfiguration(classes = { SecurityContext.class })
})
@WebAppConfiguration
public class UpcaseUserTests {
	
	private MockMvc mockMvc;

	@Autowired
	private UpcaseUserRepository upcaseUserRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		
		RegistrationForm registrationForm = new RegistrationForm("Donald", "Duck", "d.duck@duckburg.com", "Scrooge McDuck Inc.", "1234", "1324");
		UpcaseUser upcaseUser = new UpcaseUser(registrationForm);
		upcaseUserRepository.save(upcaseUser);
		
		registrationForm = new RegistrationForm("Umperio", "Bogarto", "u.bogarto@duckburg.com", "Scrooge McDuck Inc.", "asdf", "asdf");
		upcaseUser = new UpcaseUser(registrationForm);
		upcaseUserRepository.save(upcaseUser);
		
		registrationForm = new RegistrationForm("Brigitta", "MacBridge", "b.macbridge@duckburg.com", "Scrooge McDuck Inc.", "qwertz", "qwertz");
		upcaseUser = new UpcaseUser(registrationForm);
		upcaseUserRepository.save(upcaseUser);
		
		registrationForm = new RegistrationForm("Emily", "Quackfaster", "e.quackfaster@duckburg.com", "Scrooge McDuck Inc.", "quack", "quack");
		upcaseUser = new UpcaseUser(registrationForm);
		upcaseUserRepository.save(upcaseUser);
	}
	
	 @After
	 public void after() throws Exception {
		 if(upcaseUserRepository.count() > 0) {
			 upcaseUserRepository.deleteAll();
		 }
	 }

	@Test
	public void registrationControllerGet() throws Exception {
		 mockMvc.perform(get("/signup"))
		 	.andExpect(status().isOk())
		 	.andExpect(view().name("signup"))
		 	.andExpect(model().attribute("registrationForm", IsInstanceOf.instanceOf(RegistrationForm.class)));
	}

	@Test
	public void registerUser() throws Exception {
		RegistrationForm registrationForm = new RegistrationForm("Darkwing", "Duck", "darkwing.duck@duckburg.com", "Scrooge McDuck Inc.","BOOM!", "BOOM!");
		mockMvc.perform(post("/signup").flashAttr("registrationForm", registrationForm))
		 	.andExpect(redirectedUrl("/userCollection"));
	}

	@Test
	public void deleteByEmail() {
		long count = upcaseUserRepository.count();
		Assert.assertEquals(4, count);
		upcaseUserRepository.deleteByEmail("e.quackfaster@duckburg.com");
		count = upcaseUserRepository.count();
		Assert.assertEquals(3, count);
	}

	@Test
	public void findByEmail() {
		UpcaseUser findByEmail = upcaseUserRepository.findByEmail("d.duck@duckburg.com");
		Assert.assertNotNull(findByEmail);
		Assert.assertEquals("d.duck@duckburg.com", findByEmail.getEmail());
		
		findByEmail = upcaseUserRepository.findByEmail("b.macbridge@duckburg.com");
		Assert.assertNotNull(findByEmail);
		Assert.assertEquals("b.macbridge@duckburg.com", findByEmail.getEmail());
		
		findByEmail = upcaseUserRepository.findByEmail("b.macbridge@duckburg.com");
		Assert.assertNotNull(findByEmail);
		Assert.assertEquals("b.macbridge@duckburg.com", findByEmail.getEmail());
		
		findByEmail = upcaseUserRepository.findByEmail("u.bogarto@duckburg.com");
		Assert.assertNotNull(findByEmail);
		Assert.assertEquals("u.bogarto@duckburg.com", findByEmail.getEmail());
		
		findByEmail = upcaseUserRepository.findByEmail("e.quackfaster@duckburg.com");
		Assert.assertNotNull(findByEmail);
		Assert.assertEquals("e.quackfaster@duckburg.com", findByEmail.getEmail());
	}

}
