package br.com.TopFlix.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;


public class ConsultaChatGPT {

    private String apiKey;

    public ConsultaChatGPT (String apiKey) {
        this.apiKey = apiKey;
    }

    public String obterTraducao(String texto) {


//        OpenAiService service = new OpenAiService("xyz");
//
//        CompletionRequest requisicao = CompletionRequest.builder()
//                .model("davinci-002")
//                .prompt("traduza para o português o texto: " + texto)
//                .maxTokens(1000)
//                .temperature(0.7)
//                .build();
//
//
//        var resposta = service.createCompletion(requisicao);
//        return resposta.getChoices().get(0).getText();

        //coneção ao chatgpt via langchain4j

        //String apiKey = "demo";
        OpenAiChatModel model = OpenAiChatModel.withApiKey(apiKey);
        return model.generate("traduza para o português o texto: " + texto);

    }
}
