package fr.cda.projet;

import java.util.*;

// Classe de definition d'une commande
public class Commande {

    private int numero;
    private String date;
    private String client;
    private ArrayList<String> references;
    private boolean isDelivered;
    private String reason;

    /**
     * Constructeur (Les caracteristiques d'une commande)
     * 
     * @param numero int numero de la commande
     * @param date   String date de la commande. Au format JJ/MM/AAAA
     * @param client String nom du client
     *               ArrayList<String> references les references des produits de la
     *               commande
     *               boolean isDelivered livraison livré ou non livré
     *               String reason information sur la livraison
     */
    public Commande(int numero, String date,
            String client) {
        this.numero = numero;
        this.date = date;
        this.client = client;
        this.references = new ArrayList<>();
        isDelivered = false;
        reason = "";
    }

    /**
     * @return boolean retourne true si la commande a été livré
     */
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

    /**
     * @param ref récupère la référence d'une commande
     *            ajoute une ref à une commande déjà existante
     */
    public void addRef(String ref) {
        references.add(ref);
    }

    /**
     * Conversion en chaine
     * des caractéristiques d'une Commande
     */
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