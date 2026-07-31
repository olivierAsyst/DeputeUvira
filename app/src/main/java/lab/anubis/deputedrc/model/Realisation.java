package lab.anubis.deputedrc.model;

import java.io.Serializable;

public class Realisation implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final String titre;
    private final String secteur; // Éducation, Santé, Eau, Infrastructures...
    private final String lieu;
    private final int annee;
    private final String description;
    private final String beneficiaires;
    private final String financement;
    private final String statut;

    public Realisation(String id, String titre, String secteur, String lieu, int annee, String description, String beneficiaires, String financement, String statut) {
        this.id = id;
        this.titre = titre;
        this.secteur = secteur;
        this.lieu = lieu;
        this.annee = annee;
        this.description = description;
        this.beneficiaires = beneficiaires;
        this.financement = financement;
        this.statut = statut;
    }

    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getSecteur() {
        return secteur;
    }

    public String getLieu() {
        return lieu;
    }

    public int getAnnee() {
        return annee;
    }

    public String getDescription() {
        return description;
    }

    public String getBeneficiaires() {
        return beneficiaires;
    }

    public String getFinancement() {
        return financement;
    }

    public String getStatut() {
        return statut;
    }
    public boolean estComplete() { return "complete".equals(statut); }
}
