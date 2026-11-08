package ma.fstt.atelier3.repository;

public class RepositoryFactory {
    private static ProduitRepository produitRepository;
    private static CategorieRepository categorieRepository;
    private static ClientRepository clientRepository;
    private static CommandeRepository commandeRepository;
    private static PanierRepository panierRepository;

    public static synchronized ProduitRepository getProduitRepository() {
        if (produitRepository == null) {
            produitRepository = new ProduitRepository();
        }
        return produitRepository;
    }

    public static synchronized CategorieRepository getCategorieRepository() {
        if (categorieRepository == null) {
            categorieRepository = new CategorieRepository();
        }
        return categorieRepository;
    }

    public static synchronized ClientRepository getClientRepository() {
        if (clientRepository == null) {
            clientRepository = new ClientRepository();
        }
        return clientRepository;
    }

    public static synchronized CommandeRepository getCommandeRepository() {
        if (commandeRepository == null) {
            commandeRepository = new CommandeRepository();
        }
        return commandeRepository;
    }

    public static synchronized PanierRepository getPanierRepository() {
        if (panierRepository == null) {
            panierRepository = new PanierRepository();
        }
        return panierRepository;
    }
}
