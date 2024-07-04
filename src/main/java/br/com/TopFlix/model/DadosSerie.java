package br.com.TopFlix.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
* as anotações colocadas no record DadosSerie são como instruções para o Jackson,
* dizendo como ele deve interpretar os campos do JSON e associá-los aos atributos do record (data binding).
* */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao) {
}
