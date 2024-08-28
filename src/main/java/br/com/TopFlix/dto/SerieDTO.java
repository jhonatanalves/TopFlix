package br.com.TopFlix.dto;

import br.com.TopFlix.model.Categoria;

public record SerieDTO (Long id,
                        String titulo,
                        Integer totalTemporadas,
                        Double avaliacao,
                        Categoria genero,
                        String atores,
                        String poster,
                        String sinopse) {
}

