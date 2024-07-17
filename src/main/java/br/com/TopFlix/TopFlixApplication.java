package br.com.TopFlix;

import br.com.TopFlix.principal.Principal;
import br.com.TopFlix.repository.SerieRepository;
import br.com.TopFlix.service.ConsultaGemini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel;
import dev.langchain4j.model.vertexai.VertexAiGeminiStreamingChatModel;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.vertexai.VertexAiEmbeddingModel;


import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

/*


    ScreenmatchApplication: Essa classe é anotada com @SpringBootApplication, o que significa que ela é o ponto de entrada da sua aplicação Spring Boot. O Spring gerencia essa classe e todas as suas dependências. Ao injetar o SerieRepository aqui, o Spring se encarrega de criar uma instância do repositório e disponibilizála para a aplicação.

    Principal: Essa classe é uma classe comum que você criou para representar o menu da sua aplicação. Ela não é gerenciada pelo Spring, ou seja, o Spring não sabe como criar uma instância dela. Se você injetar o SerieRepository em Principal, o Spring não conseguirá fornecer uma instância do repositório, pois não está gerenciando a classe Principal.

Resumindo:

    O SerieRepository deve ser @Autowired na classe principal da sua aplicação Spring Boot (ScreenmatchApplication), pois é lá que o Spring gerencia as dependências.
    A classe Principal recebe o SerieRepository como argumento no seu construtor, garantindo que ele esteja disponível para uso dentro da classe.

Essa abordagem garante que o Spring gerencie a criação e a injeção do SerieRepository de forma correta, sem a necessidade de você se preocupar com a instânciação manual.
* */

@SpringBootApplication
public class TopFlixApplication implements CommandLineRunner {

    private static final String PROJECT_ID = "YOUR-PROJECT-ID";
    private static final String MODEL_NAME = "textembedding-gecko@latest";

    @Autowired
    private SerieRepository repositorio;

    @Value("${openai.api.key}")
    private String apiKey;

    public static void main(String[] args) {
        SpringApplication.run(TopFlixApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Principal principal = new Principal(repositorio,apiKey);
        principal.exibeMenu();


        //  ConsultaGemini g = new ConsultaGemini();
        //  ConsultaGemini.obterTraducao("teacher");

//        String PROJECT = "langchain4j";
//        String LOCATION = "us-central1";
//         String MODEL_NAME = "gemini-pro";
//
//        ChatLanguageModel model = VertexAiGeminiChatModel.builder()
//                .project(PROJECT)
//                .location(LOCATION)
//                .modelName(MODEL_NAME)
//                .build();
//
//        String response = model.generate("Tell me a joke");
//
//        System.out.println(response);

//        ChatLanguageModel model = VertexAiGeminiChatModel.builder()
//                .project("langchain4j")
//                .location("us-central1")
//                .modelName("gemini-1.5-flash-001")
//                .build();
//
//        System.out.println(model.generate("Why is the sky blue?"));

    }

}
