package ma.fstt.atelier3.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import ma.fstt.atelier3.entities.Client;
import ma.fstt.atelier3.util.JPAUtil;

public class ClientRepository extends GenericRepository<Client> {

    public ClientRepository() {
        super(Client.class);
    }

    public Client findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Client> query = em.createQuery(
                "SELECT c FROM Client c WHERE c.email = :email", Client.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Client findByEmailAndPassword(String email, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Client> query = em.createQuery(
                "SELECT c FROM Client c WHERE c.email = :email AND c.motDePasse = :password", Client.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
