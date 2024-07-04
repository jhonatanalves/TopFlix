package br.com.TopFlix;

import br.com.TopFlix.model.DadosSerie;
import br.com.TopFlix.service.ConsumoApi;
import br.com.TopFlix.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TopFlixApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TopFlixApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        var consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
        ConverteDados conversor = new ConverteDados();
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);
    }
}
