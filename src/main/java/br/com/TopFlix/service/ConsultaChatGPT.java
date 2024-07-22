package br.com.TopFlix.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class ConsultaChatGPT {

    private String apiKey;

    public ConsultaChatGPT (String apiKey) {
        this.apiKey = apiKey;
    }

    public static String obterTraducao(String texto) {

        //conexão ao chatgpt via langchain4j
        OpenAiChatModel model = OpenAiChatModel.withApiKey(System.getenv("OPENAI_APIKEY"));
        return model.generate("traduza para o português o texto: " + texto);

    }
}
