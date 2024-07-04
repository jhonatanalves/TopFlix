package br.com.TopFlix.service;

public interface IConverteDados {

    <T> T  obterDados(String json, Class<T> classe);
}
