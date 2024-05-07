package br.com.alura.screemmatch2;

import br.com.alura.screemmatch2.model.DadosSerie;
import br.com.alura.screemmatch2.service.ConsumoApi;
import br.com.alura.screemmatch2.service.ConverteDados;
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
		var consumoApi = new ConsumoApi();
		String apiKey = "ac6b67b9";
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apiKey="+apiKey);
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

		System.out.println(dados);

//		json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
//		System.out.println(json);

	}
}
