package com.example.demo;

import com.example.demo.client.ClienteLiteratura;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterluraPaulApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepositorio;
	@Autowired
	private AutorRepository autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiterluraPaulApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ClienteLiteratura client = new ClienteLiteratura(libroRepositorio, autorRepositorio);
		client.menu();
	}
}
