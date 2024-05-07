package br.com.alura.screemmatch2.principal;

import br.com.alura.screemmatch2.model.DadosEpisodio;
import br.com.alura.screemmatch2.model.DadosSerie;
import br.com.alura.screemmatch2.model.DadosTemporada;
import br.com.alura.screemmatch2.model.Episodio;
import br.com.alura.screemmatch2.service.ConsumoApi;
import br.com.alura.screemmatch2.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apiKey=ac6b67b9";
    public void exibeMenu(){
        System.out.println("Digite o nome da série para buscar:");
        var nomeSerie = leitura.nextLine();

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i<= dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + "&season="+ i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

//        System.out.println("\nTop 10 Episodios: ");
//        dadosEpisodios
//                .stream()
//                .filter(e->!e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .limit(10)
//                .map(e -> e.titulo().toUpperCase())
//                .forEach(System.out::println);

        System.out.println("\nLista de Episodios: ");
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(),d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

//        System.out.println("\nDigite um titulo de episodio a ser pesquisado: ");
//        var trechoTitulo = leitura.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado!");
//            System.out.println("Temporada: "+ episodioBuscado.get().getTemporada());
//        }else{
//            System.out.println("Episódio não encontrado!");
//        }
//
//        System.out.println(episodioBuscado);

//        System.out.println("A partir de que ano deseja ver os episodios?");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println("Temporada: "+ e.getTemporada() + "Episodio: "+ e.getTitulo() + "Data Lancamento: "+ e.getDataLancamento().format(formatador)
//                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Media: "+ est.getAverage());
        System.out.println("Melhor Episodio: "+ est.getMax());
        System.out.println("Pior Episodio: "+ est.getMin());
        System.out.println("Quantidade de Episodios: "+ est.getCount());

    }
}
