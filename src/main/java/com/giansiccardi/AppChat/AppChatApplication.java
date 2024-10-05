package com.giansiccardi.AppChat;

import com.giansiccardi.AppChat.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppChatApplication implements CommandLineRunner {

	@Autowired
	private CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(AppChatApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
   //customerService.encriptar();
	//	System.out.println("Contraseñas encriptadas al iniciar la aplicación");
	}
}
