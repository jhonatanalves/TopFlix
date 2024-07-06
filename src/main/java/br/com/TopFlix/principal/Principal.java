package br.com.TopFlix.principal;

import br.com.TopFlix.model.DadosEpisodio;
import br.com.TopFlix.model.DadosSerie;
import br.com.TopFlix.model.DadosTemporada;
import br.com.TopFlix.service.ConsumoApi;
import br.com.TopFlix.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private ConsumoApi consumoApi = new ConsumoApi();
    private Scanner leitura = new Scanner(System.in);
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para a busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> listaTemporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+")  + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            listaTemporadas.add(dadosTemporada);
        }
        listaTemporadas.forEach(t -> System.out.println(t));


       /*
       for(DadosTemporada dadosTemporada: listaTemporadas){
            List<DadosEpisodio> listaEpisodiosTemporada = dadosTemporada.episodios();
            for (DadosEpisodio dadosEpisodio: listaEpisodiosTemporada) {
                System.out.println(dadosEpisodio.titulo());
            }
        }
        */
        listaTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        //listaTemporadas.forEach(System.out::println);

    }
}