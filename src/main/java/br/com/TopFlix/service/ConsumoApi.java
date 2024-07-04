package br.com.TopFlix.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/*Criação do HttpClient:
HttpClient client = HttpClient.newHttpClient();
Aqui, estamos criando uma instância do HttpClient, que é responsável por fazer a requisição HTTP.

Criação do HttpRequest:
HttpRequest request = HttpRequest.newBuilder()
Aqui, estamos criando uma instância do HttpRequest, que representa a requisição HTTP que será enviada.
Usamos o método uri(URI.create(endereco)) para definir o endereço da URL que será acessada.
Finalmente, chamamos o método build() para construir o objeto HttpRequest.

Envio da requisição e obtenção da resposta:
HttpResponse<String> response = null;
Aqui, estamos declarando a variável response que irá armazenar a resposta da requisição HTTP.
Dentro de um bloco try-catch, chamamos o método client.send(request, HttpResponse.BodyHandlers.ofString()) para enviar a requisição e obter a resposta.
O método send() recebe dois parâmetros:
request: o objeto HttpRequest que representa a requisição a ser enviada.
HttpResponse.BodyHandlers.ofString(): um objeto que define como o corpo da resposta será tratado. Neste caso, estamos pedindo para que o corpo seja retornado como uma String.

Caso ocorra algum erro durante o envio da requisição ou obtenção da resposta, os blocos catch irão capturar as exceções IOException e InterruptedException e lançar uma RuntimeException.

Retorno do JSON:
String json = response.body();
Após obter a resposta, extraímos o corpo da resposta (que é um JSON) e armazenamos na variável json.
Finalmente, retornamos a String json como resultado do método obterDados().

Esse método é responsável por fazer a requisição HTTP, obter a resposta e retornar o conteúdo da resposta (que é um JSON) para ser utilizado posteriormente no código.
*/

public class ConsumoApi {

    public String obterDados(String endereco) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }
}