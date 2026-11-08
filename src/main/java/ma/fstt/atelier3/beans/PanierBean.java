package ma.fstt.atelier3.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import ma.fstt.atelier3.entities.Client;
import ma.fstt.atelier3.entities.Commande;
import ma.fstt.atelier3.entities.LigneCommande;
import ma.fstt.atelier3.entities.LignePanier;
import ma.fstt.atelier3.entities.Panier;
import ma.fstt.atelier3.entities.Produit;
import ma.fstt.atelier3.repository.ClientRepository;
import ma.fstt.atelier3.repository.CommandeRepository;
import ma.fstt.atelier3.repository.RepositoryFactory;

import java.io.Serializable;
import java.util.List;

@Named("panierBean")
@SessionScoped
public class PanierBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Panier panier;
    private Long selectedClientId;
    private String confirmationMessage;

    private ClientRepository clientRepository;
    private CommandeRepository commandeRepository;

    @PostConstruct
    public void init() {
        this.panier = new Panier();
        this.clientRepository = RepositoryFactory.getClientRepository();
        this.commandeRepository = RepositoryFactory.getCommandeRepository();
    }

    public void ajouterArticle(Produit produit, int quantite) {
        if (produit != null) {
            panier.ajouterArticle(produit, quantite);
        }
    }

    public String modifierQuantite(Long produitId, int quantite) {
        if (produitId != null) {
            panier.modifierQuantite(produitId, quantite);
        }
        return "panier?faces-redirect=true";
    }

    public String supprimerArticle(Long produitId) {
        if (produitId != null) {
            panier.supprimerArticle(produitId);
        }
        return "panier?faces-redirect=true";
    }

    public String viderPanier() {
        panier.vider();
        confirmationMessage = null;
        return "panier?faces-redirect=true";
    }

    public String validerCommande() {
        if (panier.getArticles().isEmpty()) {
            confirmationMessage = "Votre panier est vide.";
            return "panier?faces-redirect=true";
        }

        if (selectedClientId == null) {
            confirmationMessage = "Veuillez sélectionner un client pour valider la commande.";
            return "panier?faces-redirect=true";
        }

        Client client = clientRepository.findById(selectedClientId);
        if (client == null) {
            confirmationMessage = "Client introuvable.";
            return "panier?faces-redirect=true";
        }

        // Création de la commande JPA
        Commande commande = new Commande(client, panier.getTotal());
        for (LignePanier lp : panier.getArticles()) {
            LigneCommande lc = new LigneCommande(commande, lp.getProduit(), lp.getQuantite(), lp.getProduit().getPrix());
            commande.addLigne(lc);
        }

        commandeRepository.create(commande);

        double totalPaye = panier.getTotal();
        panier.vider();
        confirmationMessage = "Commande validée avec succès pour " + client.getNom() + " " + client.getPrenom() + " (Montant total : " + totalPaye + " DH).";

        return "panier?faces-redirect=true";
    }

    public List<LignePanier> getArticles() {
        return panier.getArticles();
    }

    public double getTotal() {
        return panier.getTotal();
    }

    public int getNombreArticles() {
        return panier.getNombreArticles();
    }

    public List<Client> getClients() {
        if (clientRepository == null) {
            clientRepository = RepositoryFactory.getClientRepository();
        }
        return clientRepository.findAll();
    }

    // Getters et Setters
    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Long getSelectedClientId() {
        return selectedClientId;
    }

    public void setSelectedClientId(Long selectedClientId) {
        this.selectedClientId = selectedClientId;
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }
}
