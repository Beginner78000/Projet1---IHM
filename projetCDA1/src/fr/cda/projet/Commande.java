package fr.cda.projet;

import java.util.*;

// Classe de definition d'une commande
//
public class Commande {
    // Les caracteristiques d'une commande
    //
    private int numero; // numero de la commande
    private String date; // date de la commande. Au format JJ/MM/AAAA
    private String client; // nom du client
    private ArrayList<String> references; // les references des produits de la commande
    private boolean isDelivered;
    private String reason;

    // TODO vous devez coder le reste (constructeur, methodes ...)
    // Constructeur
    public Commande(int numero, String date,
            String client) {
        this.numero = numero;
        this.date = date;
        this.client = client;
        this.references = new ArrayList<>();
        isDelivered = false;
        reason = "";
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public ArrayList<String> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<String> references) {
        this.references = references;
    }

    // On ajoute une ref à une commande déjà existante
    public void addRef(String ref) {
        references.add(ref);
    }

    // Conversion en chaine
    public String toString() {
        String str = "Commande          : " + numero +
                "\n   Date           : " + date +
                "\n  Client          : " + client +
                "\n RefProduits      : \n       "
                + references +
                "\n--------------------------------------------";
        return str;
    }

}