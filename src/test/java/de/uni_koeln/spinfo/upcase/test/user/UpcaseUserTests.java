package de.uni_koeln.spinfo.upcase.test.user;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextHierarchy({
//	@ContextConfiguration(classes = { PersistenceContext.class }),
//	@ContextConfiguration(classes = { WebAppContext.class }),
//	@ContextConfiguration(classes = { SecurityContext.class })
//})
//@WebAppConfiguration
public class UpcaseUserTests {
	
//	private MockMvc mockMvc;
//
//	@Autowired
//	private UpcaseUserRepository upcaseUserRepository;
//
//	@Autowired
//	private WebApplicationContext webApplicationContext;

//	@Before
//	public void setUp() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
//		
//		RegistrationForm registrationForm = new RegistrationForm("Donald", "Duck", "d.duck@duckburg.com", "Scrooge McDuck Inc.", "1234", "1324");
//		UpcaseUser upcaseUser = new UpcaseUser(registrationForm);
//		upcaseUserRepository.save(upcaseUser);
//		
//		registrationForm = new RegistrationForm("Umperio", "Bogarto", "u.bogarto@duckburg.com", "Scrooge McDuck Inc.", "asdf", "asdf");
//		upcaseUser = new UpcaseUser(registrationForm);
//		upcaseUserRepository.save(upcaseUser);
//		
//		registrationForm = new RegistrationForm("Brigitta", "MacBridge", "b.macbridge@duckburg.com", "Scrooge McDuck Inc.", "qwertz", "qwertz");
//		upcaseUser = new UpcaseUser(registrationForm);
//		upcaseUserRepository.save(upcaseUser);
//		
//		registrationForm = new RegistrationForm("Emily", "Quackfaster", "e.quackfaster@duckburg.com", "Scrooge McDuck Inc.", "quack", "quack");
//		upcaseUser = new UpcaseUser(registrationForm);
//		upcaseUserRepository.save(upcaseUser);
//	}
//	
//	 @After
//	 public void after() throws Exception {
//		 if(upcaseUserRepository.count() > 0) {
//			 upcaseUserRepository.deleteAll();
//		 }
//	 }
//
//	@Test
//	public void registrationControllerGet() throws Exception {
//		 mockMvc.perform(get("/signup"))
//		 	.andExpect(status().isOk())
//		 	.andExpect(view().name("signup"))
//		 	.andExpect(model().attribute("registrationForm", IsInstanceOf.instanceOf(RegistrationForm.class)));
//	}
//
//	@Test
//	public void registerUser() throws Exception {
//		RegistrationForm registrationForm = new RegistrationForm("Darkwing", "Duck", "darkwing.duck@duckburg.com", "Scrooge McDuck Inc.","BOOM!", "BOOM!");
//		mockMvc.perform(post("/signup").flashAttr("registrationForm", registrationForm))
//		 	.andExpect(redirectedUrl("/userCollection"));
//	}
//
//	@Test
//	public void deleteByEmail() {
//		long count = upcaseUserRepository.count();
//		Assert.assertEquals(4, count);
//		upcaseUserRepository.deleteByEmail("e.quackfaster@duckburg.com");
//		count = upcaseUserRepository.count();
//		Assert.assertEquals(3, count);
//	}
//
//	@Test
//	public void findByEmail() {
//		UpcaseUser findByEmail = upcaseUserRepository.findByEmail("d.duck@duckburg.com");
//		Assert.assertNotNull(findByEmail);
//		Assert.assertEquals("d.duck@duckburg.com", findByEmail.getEmail());
//		
//		findByEmail = upcaseUserRepository.findByEmail("b.macbridge@duckburg.com");
//		Assert.assertNotNull(findByEmail);
//		Assert.assertEquals("b.macbridge@duckburg.com", findByEmail.getEmail());
//		
//		findByEmail = upcaseUserRepository.findByEmail("b.macbridge@duckburg.com");
//		Assert.assertNotNull(findByEmail);
//		Assert.assertEquals("b.macbridge@duckburg.com", findByEmail.getEmail());
//		
//		findByEmail = upcaseUserRepository.findByEmail("u.bogarto@duckburg.com");
//		Assert.assertNotNull(findByEmail);
//		Assert.assertEquals("u.bogarto@duckburg.com", findByEmail.getEmail());
//		
//		findByEmail = upcaseUserRepository.findByEmail("e.quackfaster@duckburg.com");
//		Assert.assertNotNull(findByEmail);
//		Assert.assertEquals("e.quackfaster@duckburg.com", findByEmail.getEmail());
//	}

}
