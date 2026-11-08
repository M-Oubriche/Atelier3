package ma.fstt.atelier3.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ma.fstt.atelier3.entities.Categorie;
import ma.fstt.atelier3.entities.Produit;
import ma.fstt.atelier3.repository.CategorieRepository;
import ma.fstt.atelier3.repository.ProduitRepository;
import ma.fstt.atelier3.repository.RepositoryFactory;

import java.io.Serializable;
import java.util.List;

@Named("vitrineBean")
@SessionScoped
public class VitrineBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private ProduitRepository produitRepository;
    private CategorieRepository categorieRepository;

    private List<Produit> produits;
    private List<Categorie> categories;
    private Long selectedCategorieId;
    private String query;

    private Produit selectedProduit;
    private int quantite = 1;

    @Inject
    private PanierBean panierBean;

    @PostConstruct
    public void init() {
        this.produitRepository = RepositoryFactory.getProduitRepository();
        this.categorieRepository = RepositoryFactory.getCategorieRepository();
        this.categories = categorieRepository.findAll();
        this.produits = produitRepository.findAll();
    }

    public void filtrer() {
        if (selectedCategorieId != null && selectedCategorieId > 0) {
            this.produits = produitRepository.findByCategorie(selectedCategorieId);
        } else {
            this.produits = produitRepository.findAll();
        }
    }

    public void rechercher() {
        if (query != null && !query.trim().isEmpty()) {
            this.produits = produitRepository.searchByMotCle(query.trim());
        } else {
            this.produits = produitRepository.findAll();
        }
    }

    public void afficherTout() {
        this.query = null;
        this.selectedCategorieId = null;
        this.produits = produitRepository.findAll();
    }

    public String voirDetail(Long id) {
        this.selectedProduit = produitRepository.findById(id);
        this.quantite = 1;
        return "detail_produit?faces-redirect=true";
    }

    public String ajouterAuPanierDirect(Produit p) {
        if (panierBean != null && p != null) {
            panierBean.ajouterArticle(p, 1);
        }
        return "panier?faces-redirect=true";
    }

    public String ajouterAuPanierDetail() {
        if (panierBean != null && selectedProduit != null) {
            int qte = (quantite > 0) ? quantite : 1;
            panierBean.ajouterArticle(selectedProduit, qte);
        }
        return "panier?faces-redirect=true";
    }

    // Getters et Setters
    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public Long getSelectedCategorieId() {
        return selectedCategorieId;
    }

    public void setSelectedCategorieId(Long selectedCategorieId) {
        this.selectedCategorieId = selectedCategorieId;
        filtrer();
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Produit getSelectedProduit() {
        return selectedProduit;
    }

    public void setSelectedProduit(Produit selectedProduit) {
        this.selectedProduit = selectedProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
