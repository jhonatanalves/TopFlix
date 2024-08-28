package br.com.TopFlix.repository;

import br.com.TopFlix.model.Categoria;
import br.com.TopFlix.model.Episodio;
import br.com.TopFlix.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/*
* o JpaRepository nos fornece uma base pronta para realizar operações CRUD (Create, Read, Update, Delete) no banco de dados.

Imagine que você precisa criar métodos para salvar, buscar, atualizar e deletar dados em uma tabela. Você teria que escrever código repetitivo para cada uma dessas operações.

O JpaRepository já possui esses métodos implementados, economizando tempo e esforço.

Por exemplo:

    save(T entity): Salva uma entidade no banco de dados.
    findAll(): Busca todas as entidades da tabela.
    findById(ID id): Busca uma entidade pelo seu ID.
    delete(T entity): Deleta uma entidade do banco de dados.

Ao estender o JpaRepository, você herda esses métodos e pode usálos diretamente em seu repositório.
*
* Além dos métodos básicos, o JpaRepository também oferece métodos para realizar consultas mais complexas, como:

    findByNome(String nome): Busca uma entidade pelo seu nome.
    findAllByGenero(String genero): Busca todas as entidades de um determinado gênero.

*
* */

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avaliacao);

    List<Serie> findFirst5ByOrderByAvaliacaoDesc();

    @Query("SELECT s FROM Serie s WHERE s.id = :id")
    Optional<Serie>  serieId (Long id);

    @Query("SELECT s FROM Serie s ORDER BY s.avaliacao DESC LIMIT 5")
    List<Serie> seriesTop5 ();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double avaliacao);

    @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAValiacao(int totalTemporadas, double avaliacao);

    //select * from series s JOIN episodios e ON s.id = e.serie_id ORDER BY e.data_Lancamento DESC LIMIT 5
    @Query("SELECT s FROM Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> seriesComLancamentosMaisRecentes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numero")
    List<Episodio> obterEpisodiosPorTemporada(Long id, Long numero);

    List<Serie> findTop5ByOrderByEpisodiosDataLancamentoDesc();

    /*
    SELECT e.*
    FROM Serie s
    JOIN Episodio e ON s.id = e.serie_id
    WHERE e.titulo LIKE '%:trechoEpisodio%';
    */
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);

}
