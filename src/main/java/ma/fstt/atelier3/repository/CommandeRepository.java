package ma.fstt.atelier3.repository;

import ma.fstt.atelier3.entities.Commande;

public class CommandeRepository extends GenericRepository<Commande> {
    public CommandeRepository() {
        super(Commande.class);
    }
}
