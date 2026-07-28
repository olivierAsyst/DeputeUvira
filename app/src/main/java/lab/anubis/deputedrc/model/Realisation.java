package lab.anubis.deputedrc.model;

public class Realisation {
    private final String id;
    private final String titre;
    private final String secteur; // Éducation, Santé, Eau, Infrastructures...
    private final String lieu;
    private final int annee;
    private final String description;
    private final String beneficiaires;
    private final String financement;

    public Realisation(String id, String titre, String secteur, String lieu, int annee, String description, String beneficiaires, String financement) {
        this.id = id;
        this.titre = titre;
        this.secteur = secteur;
        this.lieu = lieu;
        this.annee = annee;
        this.description = description;
        this.beneficiaires = beneficiaires;
        this.financement = financement;
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
}
