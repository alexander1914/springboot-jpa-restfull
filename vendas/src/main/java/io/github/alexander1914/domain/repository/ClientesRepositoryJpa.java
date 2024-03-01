package io.github.alexander1914.domain.repository;

import io.github.alexander1914.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//TODO: JpaRepository é uma interface que tem o objeto EntityManager para fazer as transações na base de dados.
// OBS: É necessário passar a Entidade a tabela e tipo do ID.
public interface ClientesRepositoryJpa extends JpaRepository<Cliente, Integer> {
    //TODO: findByNomeLike: é um query método do JPA.
    // OBS: findBy é uma pesquisa na base dados.
    List<Cliente> findByNomeLike(String nome);
    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);
    boolean existsByNome(String nome);

    @Query("select c from Cliente c left join fetch c.pedidos where c.id =:id ")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

    //TODO: @Query: é uma anotation do spring para realizar queries especificas JPQL na base de dados.
    // @Param: é uma anotation para mapear o paramêtro.
    // OBS: Para mapear o paramêtro ex :nome @Param("nome")
    @Query(value = "select c from Cliente c where c.nome like :nome ")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    //TODO: Usando uma query native nesse exemplo
    @Query(value = "select * from Cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPorNomeQueryNative(@Param("nome") String nome);

    //TODO: @Modifying é uma anotation para definir esse query metodo.
    @Query("delete from Cliente c where c.nome =:nome ")
    @Modifying
    void deleteByNome(String nome);
}
