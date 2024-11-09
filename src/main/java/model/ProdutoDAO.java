package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("cadastroPU");
    private EntityManager entityManager;

    public ProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void salvar(Produto produto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    public Produto buscar(String nome) {
        String jpql = "SELECT produto FROM Produto produto WHERE produto.nome = :nome";
        TypedQuery<Produto> query = entityManager.createQuery(jpql, Produto.class);
        query.setParameter("nome", nome);

        return query.getResultStream().findFirst().orElse(null);
    }


    public List<Produto> buscarTodos() {
        // JPQL (consulta orientada a objetos) para buscar todos os produtos
        String jpql = "SELECT produto FROM Produto produto";
        TypedQuery<Produto> query = entityManager.createQuery(jpql,Produto.class);
        return query.getResultList();
    }

    public void atualizar(Produto produto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(produto);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void deletar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Produto produto = em.find(Produto.class, id);
            if (produto != null) {
                em.remove(produto);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
