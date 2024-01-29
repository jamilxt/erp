package com.brainstation23.erp;

import com.brainstation23.erp.persistence.entity.Role;
import com.brainstation23.erp.persistence.entity.UserEntity;
import com.brainstation23.erp.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class ErpApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	@Autowired
	public ErpApplication(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
    public static void main(String[] args) {
		SpringApplication.run(ErpApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var adminUser = userRepository.findByRole(Role.Admin);
		if(adminUser == null)
		{
			var user = new UserEntity();
			user.setRole(Role.Admin)
					.setId(UUID.randomUUID())
					.setFirstName("admin")
					.setLastName("admin")
					.setUsername("admin")
					.setPassword(new BCryptPasswordEncoder().encode("admin"))
					.setEmail("admin@admin.com");
			userRepository.save(user);
		}
	}
}
