package de.uni_koeln.spinfo.upcase.config;

import org.springframework.stereotype.Component;

@Component
public class PostDbHandler {

//	Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private MyInMemoryUserDetailsManager inMemoryUserDetailsManager;
//	@Autowired
//	private UpcaseUserRepository upcaseUserRepository;
//	@Autowired
//	private MongoTemplate template;
//
//	@PostConstruct
//	public void postContruct() {
//
//		Collection<UserDetails> users = inMemoryUserDetailsManager.getUsers();
//
//		for (UserDetails userDetails : users) {
//			if (userDetails instanceof UpcaseUser) {
//				UpcaseUser user = (UpcaseUser) userDetails;
//				UpcaseUser findByEmail = upcaseUserRepository.findByEmail(user.getEmail());
//				if (findByEmail == null) {
//					user = upcaseUserRepository.save(user);
//					logger.info(user + " ID :: " + user.getId());
//				}
//			}
//		}
//		long count = template.getCollection("ref_users").count();
//		logger.info("UpcaseUser :: " + count);
//	}

}
