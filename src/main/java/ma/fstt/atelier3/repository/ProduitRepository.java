package ma.fstt.atelier3.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ma.fstt.atelier3.entities.Produit;
import ma.fstt.atelier3.util.JPAUtil;

import java.util.List;

public class ProduitRepository extends GenericRepository<Produit> {

    public ProduitRepository() {
        super(Produit.class);
    }

    public List<Produit> findByCategorie(Long categorieId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                "SELECT p FROM Produit p WHERE p.categorie.id = :catId", Produit.class);
            query.setParameter("catId", categorieId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Produit> searchByMotCle(String motCle) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                "SELECT p FROM Produit p WHERE LOWER(p.designation) LIKE :mc OR LOWER(p.description) LIKE :mc", Produit.class);
            query.setParameter("mc", "%" + motCle.toLowerCase() + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
