package com.furnitureStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.furnitureStore.entities.User;
import com.furnitureStore.repositories.UserRepository;

@Configuration
public class LoadData {
	  private static final Logger log = LoggerFactory.getLogger(LoadData.class);
	  
	  @Autowired
	  private  PasswordEncoder passwordEncoder;
	  
	  @Bean
	  CommandLineRunner initDatabase(UserRepository repository) {

	    return args -> {
	    	User user = repository.findByUsername("admin");
	    	if (user == null) {
	  	      log.info("Preloading " + repository.save(new User("admin", "abc123@gmail.com", passwordEncoder.encode("admin"), 5186456585L, 
	  	    		  "ROLE_USER;ROLE_ADMIN")));
	    	}
	    };
	  }
}