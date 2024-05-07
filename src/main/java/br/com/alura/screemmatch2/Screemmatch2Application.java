package br.com.alura.screemmatch2;

import br.com.alura.screemmatch2.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Screemmatch2Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Screemmatch2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
