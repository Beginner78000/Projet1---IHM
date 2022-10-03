// Projet 1 CDA
// 
// HART de KEATING Marie
//
package fr.cda.projet;

import java.io.*;
import java.util.*;

import fr.cda.util.*;

// Classe principale d'execution du projet
//
public class Projet {
    public static void main(String a_args[]) throws FileNotFoundException, IOException {
        Terminal.ecrireStringln("Execution du projet ");

        Site site = new Site();
        GUISite ihm = new GUISite(site);
    }
}
