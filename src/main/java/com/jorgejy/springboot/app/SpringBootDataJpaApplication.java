package com.jorgejy.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jorgejy.springboot.app.model.service.UploadFileService;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoderDI;

	
	@Autowired
	UploadFileService fileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileService.deleteAll();
		fileService.init();
		
		
		String password = "12345";
		
		for(int i = 0 ; i < 2; i++){
			String bCryptPassword = passwordEncoderDI.encode(password);
			System.out.println(bCryptPassword);
		}
	}

}
