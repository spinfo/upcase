package de.uni_koeln.spinfo.upcase.config;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;
import de.uni_koeln.spinfo.upcase.security.MyInMemoryUserDetailsManager;

@Component
public class PostDbHandler {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MyInMemoryUserDetailsManager inMemoryUserDetailsManager;

	@Autowired
	private UpcaseUserRepository upcaseUserRepository;

	@Autowired
	private MongoTemplate template;

	@PostConstruct
	public void postContruct() {

		Collection<UserDetails> users = inMemoryUserDetailsManager.getUsers();

		for (UserDetails userDetails : users) {
			if (userDetails instanceof UpcaseUser) {
				UpcaseUser user = (UpcaseUser) userDetails;
				UpcaseUser findByEmail = upcaseUserRepository.findByEmail(user.getEmail());
				if (findByEmail == null) {
					user = upcaseUserRepository.save(user);
					logger.info(user + " ID :: " + user.getId());
				}
			}
		}
		long count = template.getCollection("ref_users").count();
		logger.info("UpcaseUser :: " + count);
	}

}
