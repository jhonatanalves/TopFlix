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

        //conexão ao chatgpt via langchain4j
        OpenAiChatModel model = OpenAiChatModel.withApiKey(apiKey);
        return model.generate("traduza para o português o texto: " + texto);

    }
}
