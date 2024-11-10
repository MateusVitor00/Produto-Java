package controller;

import javax.persistence.*;

import model.Produto;
import model.ProdutoDAO;

import java.util.List;

public class ControleProduto {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("cadastroPU"); // Unidade de persistência definida em persistence.xml

    private ProdutoDAO produtoDAO;

    @PersistenceContext
    private EntityManager entityManager;


    public void cadastrar(String nome, String data, double preco) {
        EntityManager em = emf.createEntityManager();
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setData(data);
        produto.setPreco(preco);
        
        try {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
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

    //public Produto buscar(String nome) { // Alterado para Long, assumindo que o código é o ID
    //    return produtoDAO.buscar(nome);
    //}

    public void alterar(Long codigo, String novoNome, String novaData, double novoPreco) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Produto produto = em.find(Produto.class, codigo);
            if (produto != null) {
                produto.setNome(novoNome);
                produto.setData(novaData);
                produto.setPreco(novoPreco);
                em.merge(produto);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void excluir(String codigo) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Produto produto = em.find(Produto.class, codigo);
            if (produto != null) {
                em.remove(produto);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
