package fr.cda.projet;

import fr.cda.ihm.FormulaireInt;

import java.util.ArrayList;
import java.util.StringTokenizer;

import fr.cda.ihm.Formulaire;

public class GUIModifierCommande implements FormulaireInt{
    private Formulaire formPP;
    private Commande commandes;
    private Site site;
    
    public GUIModifierCommande(Formulaire formpp, Commande c, Site s){
        this.formPP = formpp;
        this.commandes = c;
        this.site = s;

        // Création du formulaire
        Formulaire form1 = new Formulaire("Site de vente", this, 450, 450);

        // Mise en place des zones de saisies permettant de modifier les quantités
        // des références de produit d'un bon de commande
        ArrayList<String> refProduitCmd = commandes.getReferences();

        // On parcourt l'ensemble des produits 
        // se trouvant dans le bon de commande
        for(int i = 0; i < refProduitCmd.size(); i++){
            StringTokenizer strRef = new StringTokenizer(refProduitCmd.get(i), "=");
            String reference = strRef.nextToken();
            form1.addText("QUANTITE", reference, true, strRef.nextToken());
        }

        // On ajoute le bouton VALIDER 
        // pour confirmer les modifications et fermer la fenêtre
        form1.addButton("VALIDER", "Valider");

        form1.afficher();
    }

    @Override
    public void submit(Formulaire form, String nomSubmit){
        // Validation des modifications
        if(nomSubmit.equals("VALIDER")){
            // On affecte les nouvelles données de l'IHM 
            // avec celle de la commande
            ArrayList<String> newRefProduitCmd = new ArrayList<>();
            ArrayList<String> refProduitCmd = commandes.getReferences();

            // On parcourt l'ensemble des reférences 
            // afin de mettre à jour les données une à une
            for(int i=0; i < refProduitCmd.size(); i++){
                StringTokenizer strRef = new StringTokenizer(refProduitCmd.get(i), "=");
                String reference = strRef.nextToken();
                // On récupère la nouvelle quantité
                String quantite = form.getValeurChamp("QUANTITE");
                // On reformate les nouvelles infos 
                // afin quelles correspondent au format de la commande
                String temp = reference+ "=" +quantite;
                newRefProduitCmd.add(temp);
            }
            // On met le bon de commande à jour
            commandes.setReferences(newRefProduitCmd);

            // On affiche la modification dans la zone des résultats
            this.formPP.setValeurChamp("RESULTATS", commandes.toString());

            // On ferme la fenêtre automatiquement 
            form.fermer();

        }
    }
    
}
