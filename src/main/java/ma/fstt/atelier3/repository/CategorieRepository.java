package ma.fstt.atelier3.repository;

import ma.fstt.atelier3.entities.Categorie;

public class CategorieRepository extends GenericRepository<Categorie> {
    public CategorieRepository() {
        super(Categorie.class);
    }
    //toutes les méthodes CRUD sont hérité 
}
