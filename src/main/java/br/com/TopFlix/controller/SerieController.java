package br.com.TopFlix.controller;

import br.com.TopFlix.dto.EpisodioDTO;
import br.com.TopFlix.dto.SerieDTO;
import br.com.TopFlix.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/*
o @RestController faz com que o SerieController escute automaticamente o endereço /series e execute a ação definida no método obterSeries(), sem você precisar implementar a lógica de como essas coisas acontecem.

O Spring cuida de tudo: ele recebe a requisição, identifica o SerieController como responsável por atender a requisição, executa o método obterSeries() e retorna a resposta em formato JSON para o navegador.

Você só precisa se preocupar em escrever a lógica do método obterSeries() para que ele retorne as informações corretas! 
* */

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService servico;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return servico.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return servico.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return servico.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id) {
        return servico.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
        return servico.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numero){
        return servico.obterTemporadasPorNumero(id, numero);
    }

    @GetMapping("/categoria/{nomeGenero}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String nomeGenero){
        return servico.obterSeriesPorCategoria(nomeGenero);
    }



}

/*
No caso do SerieController, ele recebe a requisição do navegador para a URL /series e repassa para o SerieService buscar as informações das séries no banco de dados.

Então, o SerieController é usado para:

    Receber requisições do navegador: Ele é responsável por lidar com as requisições HTTP que chegam ao seu aplicativo.
    Delegar a lógica de negócio: Ele repassa a responsabilidade de processar a requisição para o SerieService.
    Retornar a resposta: Ele envia a resposta para o navegador, com as informações das séries que foram buscadas pelo SerieService.

Em resumo, o SerieController é a interface entre o navegador e a lógica de negócio da sua aplicação, garantindo que as informações sejam processadas e retornadas corretamente.
-------------
Quando você coloca a anotação @Service em uma classe, você está dizendo ao Spring: "Ei, essa classe é importante para a minha aplicação, cuide dela para mim!".

O Spring, então, assume a responsabilidade de gerenciar essa classe, como se fosse um "pai" cuidadoso. Ele vai:

    Criar a classe: Quando a aplicação precisar usar a classe, o Spring vai criar uma instância dela.
    Destruir a classe: Quando a aplicação não precisar mais da classe, o Spring vai destruí-la para liberar a memória.
    Gerenciar as dependências: O Spring vai garantir que a classe tenha acesso a todas as outras classes que ela precisa para funcionar, como o repositório, por exemplo.

É como se você tivesse um robô que cuida da sua casa: ele limpa, organiza, faz a comida e até lava a louça! O Spring é como esse robô, cuidando de todas as classes anotadas com @Service para que você não precise se preocupar com os detalhes.

Então, ao anotar uma classe com @Service, você está delegando a responsabilidade de gerenciá-la para o Spring, liberando você para se concentrar na lógica da sua aplicação!
---------------
A anotação @Service é como um selo que você coloca em suas classes de serviço para dizer ao Spring: "Ei, essa classe é importante para a lógica da minha aplicação!".

É como se você estivesse organizando sua cozinha: você coloca um rótulo em cada pote para saber o que tem dentro, né? A @Service faz a mesma coisa, deixando claro para o Spring que aquela classe é responsável por uma parte específica da lógica da sua aplicação.

Então, sempre que você criar uma classe que:

    Contém regras de negócio: Como calcular um desconto, validar um formulário, ou processar dados.
    Interage com o repositório: Para acessar dados do banco de dados.

Anote-a com @Service! Isso vai ajudar o Spring a gerenciar a classe e a manter seu código organizado e fácil de entender.
-----------------------

*/