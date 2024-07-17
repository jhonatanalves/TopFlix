package br.com.TopFlix.service;


import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel;


public class ConsultaGemini {

    static String project = "516083918799";
    static String location = "brazil";
    static String modelName = "gemini-1.5-flash-001";

    public static void obterTraducao(String texto) {
        VertexAiGeminiChatModel model = VertexAiGeminiChatModel.builder()
                .project(project)
                .location(location)
                .modelName(modelName)
                .build();

        String response = model.generate("Tell me a joke");
        System.out.println("Gemini responde: " + response);
    }
}
