package ma.fstt.atelier3.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import ma.fstt.atelier3.entities.Client;
import ma.fstt.atelier3.repository.ClientRepository;
import ma.fstt.atelier3.repository.RepositoryFactory;

import java.io.Serializable;
import java.util.List;

@Named("clientBean")
@SessionScoped
public class ClientBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Client client;
    private Client clientToEdit;
    private List<Client> clients;
    private String message;

    private ClientRepository clientRepository;

    @PostConstruct
    public void init() {
        this.client = new Client();
        this.clientToEdit = null;
        this.clientRepository = RepositoryFactory.getClientRepository();
        chargerClients();
    }

    public void chargerClients() {
        this.clients = clientRepository.findAll();
    }

    public String enregistrer() {
        try {
            if (clientToEdit != null && client.getId() != null) {
                clientRepository.update(client);
                message = "Client " + client.getNom() + " " + client.getPrenom() + " modifié avec succès !";
            } else {
                clientRepository.create(client);
                message = "Nouveau client " + client.getNom() + " " + client.getPrenom() + " ajouté avec succès !";
            }
            client = new Client();
            clientToEdit = null;
            chargerClients();
        } catch (Exception e) {
            message = "Erreur lors de l'enregistrement du client : " + e.getMessage();
        }
        return "clients?faces-redirect=true";
    }

    public void editer(Client c) {
        if (c != null) {
            this.clientToEdit = c;
            this.client = new Client();
            this.client.setId(c.getId());
            this.client.setNom(c.getNom());
            this.client.setPrenom(c.getPrenom());
            this.client.setEmail(c.getEmail());
            this.client.setMotDePasse(c.getMotDePasse());
            this.client.setTelephone(c.getTelephone());
            this.client.setAdresse(c.getAdresse());
        }
    }

    public void annuler() {
        this.clientToEdit = null;
        this.client = new Client();
        this.message = null;
    }

    public String supprimer(Long id) {
        try {
            if (id != null) {
                clientRepository.delete(id);
                message = "Client supprimé avec succès !";
                if (clientToEdit != null && clientToEdit.getId().equals(id)) {
                    annuler();
                }
                chargerClients();
            }
        } catch (Exception e) {
            message = "Erreur lors de la suppression du client : " + e.getMessage();
        }
        return "clients?faces-redirect=true";
    }

    // Getters et Setters
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClientToEdit() {
        return clientToEdit;
    }

    public void setClientToEdit(Client clientToEdit) {
        this.clientToEdit = clientToEdit;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
