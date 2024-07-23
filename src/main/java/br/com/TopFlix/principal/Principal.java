package br.com.TopFlix.principal;

import br.com.TopFlix.model.*;
import br.com.TopFlix.repository.SerieRepository;
import br.com.TopFlix.service.ConsumoApi;
import br.com.TopFlix.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private final ConsumoApi consumoApi = new ConsumoApi();
    private final Scanner leitura = new Scanner(System.in);
    private final ConverteDados conversor = new ConverteDados();
    private final List<DadosSerie> dadosSeries = new ArrayList<>();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    private SerieRepository serieRepositorio;

    private List<Serie> series = new ArrayList<>();

    private Optional<Serie> serieBuscada;


    public Principal(SerieRepository repositorio) {
        this.serieRepositorio = repositorio;
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        dadosSeries.add(dados);
        serieRepositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {

        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome");
        var nomeSerie = leitura.nextLine();

        List<DadosTemporada> listaTemporadas = new ArrayList<>();
        Optional<Serie> serieOptional = serieRepositorio.findByTituloContainingIgnoreCase(nomeSerie);


        if (serieOptional.isPresent()) {
            var serie = serieOptional.get();
            var temporadas = new ArrayList<DadosTemporada>();
            for (int i = 1; i <= serie.getTotalTemporadas(); i++) {
                var json = consumoApi.obterDados(ENDERECO + serie.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serie.setEpisodios(episodios);
            serieRepositorio.save(serie);
        } else {
            System.out.println("Série não encontrada!");
        }
//        DadosSerie dadosSerie = getDadosSerie();

//
//        List<Episodio> episodios = listaTemporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(d -> new Episodio(t.numero(), d))
//                ).collect(Collectors.toList());
//
//        episodios.
//
//                forEach(System.out::println);
//
//        System.out.
//
//                println("A partir de que ano você deseja ver os episódios? ");
//
//        var ano = leitura.nextInt();
//        leitura.
//
//                nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.
//
//                stream()
//                        .
//
//                filter(e -> e.
//
//                        getDataLancamento() != null && e.
//
//                        getDataLancamento().
//
//                        isAfter(dataBusca))
//                        .
//
//                forEach(e -> System.out.
//
//                        println(
//                                "...Temporada: " + e.getTemporada() +
//                                        " Episódio: " + e.
//
//                                        getTitulo() +
//                                        " Data lançamento: " + e.
//
//                                        getDataLancamento().
//
//                                        format(formatador)
//                        ));
//
//
//        System.out.
//
//                println("Digite um trecho do título do episódio");
//
//        var trechoTitulo = leitura.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if (episodioBuscado.
//
//                isPresent()) {
//            System.out.
//
//                    println("Episódio encontrado!");
//            System.out.
//
//                    println("Nome: " + episodioBuscado.get().
//
//                            getTitulo());
//            System.out.
//
//                    println("Temporada: " + episodioBuscado.get().
//
//                            getTemporada());
//        } else {
//            System.out.
//
//                    println("Episódio não encontrado!");
//        }
//
//        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getAvaliacao)));
//
//// Arredonda as médias para duas casas decimais
//        avaliacoesPorTemporada.
//
//                forEach((temporada, media) ->
//
//                {
//                    String mediaFormatada = String.format("%.2f", media);
//                    System.out.
//
//                            println("Temporada " + temporada + ": " + mediaFormatada);
//                });
//
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
//        System.out.
//
//                println("Média: " + est.getAverage());
//        System.out.
//
//                println("Melhor episódio: " + est.getMax());
//        System.out.
//
//                println("Pior episódio: " + est.getMin());
//        System.out.
//
//                println("Quantidade: " + est.getCount());
//

    }

    private void listarSeriesBuscadas() {
        series = serieRepositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha um série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        serieBuscada = serieRepositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("Dados da série: \n" + serieBuscada.get().toString());

        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void buscarSeriesPorAtor() {
        System.out.println("Qual o nome para a busca?");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de que valor? ");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = serieRepositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Séries em que " + nomeAtor + " trabalhou com avaliação >= a " + avaliacao + ": \n");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));


    }

    private void buscarTop5Series() {
        List<Serie> serieTop = serieRepositorio.findFirst5ByOrderByAvaliacaoDesc();
        serieTop.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Deseja buscar séries de que categoria/gênero? ");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = serieRepositorio.findByGenero(categoria);
        System.out.println("Séries da categoria " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void filtrarSeriesPorTemporadaEAvaliacao(){
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = leitura.nextDouble();
        leitura.nextLine();
        List<Serie> filtroSeries = serieRepositorio.seriesPorTemporadaEAValiacao(totalTemporadas, avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));
    }
    private void buscarEpisodioPorTrecho(){
        System.out.println("Qual o trecho de nome de episódio para busca?");
        var trechoEpisodio = leitura.nextLine();
        List<Episodio> episodiosEncontrados = serieRepositorio.episodiosPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Série: %s Temporada %s - Episódio %s - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void topEpisodiosPorSerie(){
        buscarSeriePorTitulo();
        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = serieRepositorio.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s Temporada %s - Episódio %s - %s Avaliação %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao() ));
        }
    }

    private void buscarEpisodiosDepoisDeUmaData(){
        buscarSeriePorTitulo();
        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            System.out.println("Digite o ano limite de lançamento");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();

            List<Episodio> episodiosAno = serieRepositorio.episodiosPorSerieEAno(serie, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
    }


    public void exibeMenu() {

        var opcao = -1;
        while (opcao != 0) {

            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5 - Buscar séries por ator
                    6 - Top 5 Séries
                    7 - Buscar séries por categoria
                    8 - Filtrar séries por temporada e avaliação
                    9 - Buscar episodios por trecho          
                    10 - Top 5 episódios de uma série
                    11 - Buscar episódios de uma série a partir de uma data
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    filtrarSeriesPorTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosDepoisDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

    }
}