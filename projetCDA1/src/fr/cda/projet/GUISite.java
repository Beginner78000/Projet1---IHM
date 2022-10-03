package fr.cda.projet;

import fr.cda.ihm.*;

// Classe de definition de l'IHM principale du compte
//
public class GUISite implements FormulaireInt {
    private Site site; // Le site

    // Constructeur
    //
    public GUISite(Site site) {
        this.site = site;

        // Creation du formulaire
        Formulaire form = new Formulaire("Site de vente", this, 1100, 730);

        // Creation des elements de l'IHM
        //
        form.addLabel("Afficher tous les produits du stock");
        form.addButton("AFF_STOCK", "Tous le stock");
        form.addLabel("");
        form.addLabel("Afficher tous les bons de commande");
        form.addButton("AFF_COMMANDES", "Toutes les commandes");
        form.addLabel("");
        form.addText("NUM_COMMANDE", "Numero de commande", true, "1");
        form.addButton("AFF_COMMANDE", "Afficher");
        form.addLabel("");
        form.addButton("LIVRER", "Livrer");
        form.addButton("MODIF_COMMANDE", "Modifier");
        form.addButton("SUM_SALES", "Total ventes");

        form.setPosition(400, 0);
        form.addZoneText("RESULTATS", "Resultats",
                true,
                "",
                600, 700);

        // Affichage du formulaire
        form.afficher();
    }

    // Methode appellee quand on clique dans un bouton
    //
    public void submit(Formulaire form, String nomSubmit) {

        // Affichage de tous les produits du stock
        //
        if (nomSubmit.equals("AFF_STOCK")) {
            String res = site.listerTousProduits();
            form.setValeurChamp("RESULTATS", res);
        }

        // Affichage de toutes les commandes
        //
        if (nomSubmit.equals("AFF_COMMANDES")) {
            String res = site.listerToutesCommandes();
            form.setValeurChamp("RESULTATS", res);
        }

        // Affichage d'une commande
        //
        if (nomSubmit.equals("AFF_COMMANDE")) {
            String numStr = form.getValeurChamp("NUM_COMMANDE");
            int num = Integer.parseInt(numStr);
            String res = site.listerCommande(num);
            form.setValeurChamp("RESULTATS", res);
        }

        // Affichage de la livraison
        if (nomSubmit.equals("LIVRER")) {
            String res = site.delivery();
            form.setValeurChamp("RESULTATS", res);
        }
        // Modifier bon de commande
        if (nomSubmit.equals("MODIF_COMMANDE")) {
            // On récupère le numéro de la commande
            String numCmd = form.getValeurChamp("NUM_COMMANDE");
            // On convertie la String en int
            int ConvertNumCmd = Integer.parseInt(numCmd);

            // On va vérifier si le numero de commande existe
            Commande cmd = site.trouverCommande(ConvertNumCmd);
            // S'il n'existe pas alors on renvoie un message d'erreur
            if (cmd == null) {
                form.setValeurChamp("RESULTAT", "Numéro de commande non trouvé");
            } else if (cmd.isDelivered()) {
                // Si le bon de commande a été livré, on ne peut pas le modifier
                form.setValeurChamp("RESULTAT", "La commande a déjà été livré, elle ne peut pas être modifié");

            } else {
            //   Sinon on affiche la nouvelle fenêtre IHM
              GUIModifierCommande ihm = new GUIModifierCommand();
              }
        }

        // Calcule somme vente
        if (nomSubmit.equals("SUM_SALES")) {
            String res = site.sumSales();
            form.setValeurChamp("RESULTATS", res);
        }
    }

}