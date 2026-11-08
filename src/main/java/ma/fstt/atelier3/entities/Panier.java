package ma.fstt.atelier3.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paniers")
public class Panier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LignePanier> lignes = new ArrayList<>();

    public Panier() {}

    public Panier(Client client) {
        this.client = client;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public List<LignePanier> getLignes() { return lignes; }
    public void setLignes(List<LignePanier> lignes) { this.lignes = lignes; }

    public List<LignePanier> getArticles() {
        return lignes;
    }

    public void ajouterArticle(Produit produit, int quantite) {
        if (produit == null || produit.getId() == null) return;
        for (LignePanier lp : lignes) {
            if (lp.getProduit().getId().equals(produit.getId())) {
                lp.setQuantite(lp.getQuantite() + quantite);
                return;
            }
        }
        LignePanier nouvelleLigne = new LignePanier(this, produit, quantite);
        lignes.add(nouvelleLigne);
    }

    public void modifierQuantite(Long produitId, int quantite) {
        if (quantite <= 0) {
            supprimerArticle(produitId);
            return;
        }
        for (LignePanier lp : lignes) {
            if (lp.getProduit().getId().equals(produitId)) {
                lp.setQuantite(quantite);
                return;
            }
        }
    }

    public void supprimerArticle(Long produitId) {
        lignes.removeIf(lp -> lp.getProduit().getId().equals(produitId));
    }

    public void vider() {
        lignes.clear();
    }

    public double getTotal() {
        return lignes.stream()
                .mapToDouble(LignePanier::getSousTotal)
                .sum();
    }

    public int getNombreArticles() {
        return lignes.stream()
                .mapToInt(LignePanier::getQuantite)
                .sum();
    }
}
