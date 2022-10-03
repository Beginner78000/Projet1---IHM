package fr.cda.projet;

import java.io.*;
import java.util.*;

import fr.cda.util.*;

// Classe de definition du site de vente
public class Site {

    private ArrayList<Produit> stock; // Les produits du stock
    private ArrayList<Commande> commandes; // Les bons de commande

    /**
     * Constructeur
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * 
     */
    public Site() throws FileNotFoundException, IOException {
        stock = new ArrayList<Produit>();
        commandes = new ArrayList<Commande>();

        initialiserStock("/home/oem/Desktop/MyProjects/Projet1/projetCDA1/data/Produits.txt");
        initialiserCommande("/home/oem/Desktop/MyProjects/Projet1/projetCDA1/data/Commandes.txt");

    }

    /**
     * @param nomFichier nom du fichier à lire
     *                   Void Chargement du fichier de stock
     */
    private void initialiserStock(String nomFichier) {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        for (String ligne : lignes) {
            System.out.println(ligne);
            String[] champs = ligne.split("[;]", 4);
            String reference = champs[0];
            String nom = champs[1];
            double prix = Double.parseDouble(champs[2]);
            int quantite = Integer.parseInt(champs[3]);
            Produit p = new Produit(reference,
                    nom,
                    prix,
                    quantite);
            stock.add(p);
        }
    }

    /**
     * @param nomFichier nom du fichier à lire
     * @throws IOException
     *                     Void Chargement du fichier des commandes
     */
    private void initialiserCommande(String nomFichier) throws IOException {
        // Le fichier d'entrée
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        // On parcourt le fichier ligne par ligne
        for (String ligne : lignes) {
            String[] champs = ligne.split("[;]", 4);
            // On crée des variable pour chacun des champs
            // On traduit l'objet String en int
            // afin de pouvoir l'utiliser dans l'ArrayList commandes
            int numero = Integer.parseInt(champs[0]);
            String date = champs[1];
            String client = champs[2];
            String ref = champs[3];

            // On vérifie si le numéro de commande existe
            Commande existCom = trouverCommande(numero);
            // Si elle n'existe pas alors on la créé
            if (existCom == null) {
                existCom = new Commande(numero, date, client);
                // Ajout du produit à la commande + ref
                commandes.add(existCom);
                existCom.addRef(ref);
            } else {
                // Sinon on rajoute uniquement la nouvelle ref
                existCom.addRef(ref);
            }
            System.out.println(numero + ". " + date + " / " + client + " / " + ref);

        }

    }

    /**
     * @return String la liste de tous les produits
     */
    public String listerTousProduits() {
        String res = "";
        for (Produit prod : stock)
            res = res + prod.toString() + "\n";

        return res;
    }

    /**
     * @return String La liste de toutes les commandes
     */
    public String listerToutesCommandes() {
        String res = "";
        for (Commande c : commandes)
            res = res + c.toString() + "\n";

        return res;
    }

    /**
     * @param numero int numero de la commande demandé
     * @return String la commande demandé
     */
    public String listerCommande(int numero) {
        String res = null;
        for (int i = 0; i < commandes.size(); i++) {
            if (commandes.get(i).getNumero() == numero) {
                res = commandes.get(i).toString();
            }
        }
        if (res == null)
            return "Commande non trouvée";

        return res;
    }

    /**
     * @param numero numero de la commande recherchée
     * @return Commande retourne la commande trouvée sinon null
     */
    public Commande trouverCommande(int numero) {
        // On parcourt la liste des commandes
        for (Commande c : commandes) {
            // Si elle existe on retourne la commande
            if (c.getNumero() == numero) {
                return c;
            }
        }
        // sinon on retourne null
        return null;
    }

    /**
     * @return String retourne les commandes non livrés
     */
    public String delivery() {
        String res = "Commandes non livrés : \n";
        String trait = "======================================= \n";
        res = res + trait;
        // On parcourt les commandes
        for (Commande c : commandes) {
            // Si la commande n'a pas été encore livré
            if (!c.isDelivered()) {
                boolean isOk = true;
                String info = "";
                // On parcourt l'ArrayList référence pour checker le stock
                for (String e : c.getReferences()) {
                    StringTokenizer strRef = new StringTokenizer(e, "=");
                    String reference = strRef.nextToken();
                    // le replaceAll permet de lire uniquement les chiffre pour éviter de lever une
                    // exception sur le dernier int "0]"
                    int nbCommande = Integer.valueOf(strRef.nextToken().replaceAll("[^0-9]", ""));
                    System.out.println(reference + " / " + nbCommande);

                    // On récupère le produit qui correspond à la référence
                    Produit p = trouverProduit(reference);
                    // On calcul le stock MaJ après déduction de la quantité commandée
                    int calQuantite = p.getQuantite() - nbCommande;

                    // Si la quantité est négative
                    if (calQuantite < 0) {
                        // la livraison n'est pas possible
                        isOk = false;
                        // On renvoit un message personnalisé
                        info = info + "Il va manquer -" + calQuantite + " au produit : " + reference + "\n";
                    }
                }
                // S'il y a suffisement de stock(commande correct)
                if (isOk) {
                    // On passe la variable isDelivered à true
                    c.setDelivered(true);
                    // Et on affiche le message
                    c.setReason("");

                    // On met à jour le nouveau stock
                    for (String e : c.getReferences()) {
                        StringTokenizer strRef = new StringTokenizer(e, "=");
                        String reference = strRef.nextToken();
                        int nbCommande = Integer.valueOf(strRef.nextToken().replaceAll("[^0-9]", ""));

                        Produit p = trouverProduit(reference);
                        // On effectue la MàJ
                        p.setQuantite(p.getQuantite() - nbCommande);
                    }

                } else {
                    // Sinon commande incorrecte
                    c.setDelivered(false);
                    c.setReason("");
                    res = res + c + "\n";
                }
            }
        }
        return res;
    }

    /**
     * @param ref reférence du produit recherché
     * @return Produit retourne le produit si trouvé sinon null
     */
    public Produit trouverProduit(String ref) {
        // On parcourt la liste des produits
        for (Produit p : stock) {
            // Si elle existe on retourne la commande
            if (p.getReference().equals(ref)) {
                return p;
            }
        }
        // sinon on retourne null
        return null;

    }

    /**
     * @return String retourne la somme totale
     *         des commandes livrés
     */
    public String sumSales() {
        double sum = 0.0;
        String res = "";
        for (Commande c : commandes) {
            res = "COMMANDE : " + c.getNumero() + "\n";
            // On vérifie si les commandes qui ont été livré
            if (c.isDelivered()) {
                for (String refCmd : c.getReferences()) {
                    StringTokenizer strRef = new StringTokenizer(refCmd, "=");
                    String reference = strRef.nextToken();
                    int nbCommande = Integer.valueOf(strRef.nextToken().replaceAll("[^0-9]", ""));

                    Produit p = trouverProduit(reference);
                    // On additionne au fur et à mesure la somme de chaque commande
                    sum = sum + p.getPrix() * (double) nbCommande;
                    res = (p.getNom() + nbCommande + p.getPrix() + "\n").toString();
                }
                res = res + "\n";
            }
        }
        // On retourne le résultat
        res = res + "====================== \n";
        res = res + "SOMME : " + sum + " euros";
        return res;
    }
}