package fr.cda.projet;

import fr.cda.ihm.*;

// Classe de definition de l'IHM principale du compte
//
public class IHMSite implements FormulaireInt {
    private Site site; // Le site
    private Commande commandes; // Les bon de commandes
    private Formulaire form;

    // Constructeur
    public IHMSite(Site site, Commande commandes) {
        this.site = site;
        this.commandes = commandes;

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

        // Affichage des livraisons
        if (nomSubmit.equals("LIVRER")) {
            String res = site.delivery();
            form.setValeurChamp("RESULTATS", res);
        }

        // Modifier bon de commande
        if (nomSubmit.equals("MODIF_COMMANDE")) {
            // On r??cup??re le num??ro de la commande
            String numCmd = form.getValeurChamp("NUM_COMMANDE");
            // On convertie la String en int
            int ConvertNumCmd = Integer.parseInt(numCmd);

            // On va v??rifier si le numero de commande existe
            Commande cmd = site.trouverCommande(ConvertNumCmd);
            // S'il n'existe pas alors on renvoie un message d'erreur
            if (cmd == null) {
                form.setValeurChamp("RESULTAT", "Num??ro de commande non trouv??");
            } else if (cmd.isDelivered()) {
                // Si le bon de commande a ??t?? livr??, on ne peut pas le modifier
                form.setValeurChamp("RESULTAT", "La commande a d??j?? ??t?? livr??, elle ne peut pas ??tre modifi??");

            } else {
                // Sinon on affiche la nouvelle fen??tre IHM
                // GUIModifierCommande ihm = new GUIModifierCommande(this, this.commandes, this.site);
            }
        }

        // Calcule somme vente
        if (nomSubmit.equals("SUM_SALES")) {
            String res = site.sumSales();
            form.setValeurChamp("RESULTATS", res);
        }
    }

}