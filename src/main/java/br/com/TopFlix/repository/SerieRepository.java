package br.com.TopFlix.repository;

import br.com.TopFlix.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

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
}