package lab.anubis.deputedrc;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import lab.anubis.deputedrc.adapter.RealisationAdapter;
import lab.anubis.deputedrc.databinding.ActivityRealisationsBinding;
import lab.anubis.deputedrc.model.Realisation;

public class RealisationsActivity extends AppCompatActivity {

    private ActivityRealisationsBinding binding;
    private RealisationAdapter adapter;
    private final List<Realisation> toutesLesRealisations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRealisationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chargerDonneesDeDemonstration();
        configurerListe();
        configurerFiltres();

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        configurerNavigationBasse();
    }

    private void chargerDonneesDeDemonstration() {
        toutesLesRealisations.add(new Realisation("1",
                "Réhabilitation de l'école de Kavimvira", "Éducation", "Kavimvira, Uvira", 2025,
                "Reconstruction de 6 salles de classe et fourniture de bancs pupitres.",
                "600 élèves", "Fonds constit."));

        toutesLesRealisations.add(new Realisation("2",
                "Forage d'eau potable — Kasenga", "Eau", "Kasenga, Haut-Katanga", 2024,
                "Installation d'un forage desservant plus de 300 ménages.",
                "300 ménages", "Partenaire ONG"));

        toutesLesRealisations.add(new Realisation("3",
                "Équipement du centre de santé de Baraka", "Santé", "Baraka, Fizi", 2024,
                "Don de matériel médical et de lits d'hospitalisation.",
                "1 centre de santé", "Fonds constit."));
    }

    private void configurerListe() {
        adapter = new RealisationAdapter(realisation -> {
            // TODO : ouvrir DetailRealisationActivity avec l'id de la réalisation
        });

        binding.recyclerRealisations.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerRealisations.setAdapter(adapter);
        adapter.soumettreListe(toutesLesRealisations);
    }

    private void configurerFiltres() {
        binding.chipGroupSecteurs.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;

            int idSelectionne = checkedIds.get(0);

            if (idSelectionne == R.id.chipTous) {
                adapter.soumettreListe(toutesLesRealisations);
            } else if (idSelectionne == R.id.chipEducation) {
                adapter.soumettreListe(filtrerParSecteur("Éducation"));
            } else if (idSelectionne == R.id.chipSante) {
                adapter.soumettreListe(filtrerParSecteur("Santé"));
            } else if (idSelectionne == R.id.chipEau) {
                adapter.soumettreListe(filtrerParSecteur("Eau"));
            }
        });
    }

    private List<Realisation> filtrerParSecteur(String secteur) {
        List<Realisation> resultat = new ArrayList<>();
        for (Realisation r : toutesLesRealisations) {
            if (r.getSecteur().equals(secteur)) resultat.add(r);
        }
        return resultat;
    }

    private void configurerNavigationBasse() {
        binding.bottomNavigation.setSelectedItemId(R.id.nav_realisations);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_accueil) {
                startActivity(new android.content.Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_realisations) {
                return true; // déjà ici
            } else if (id == R.id.nav_agenda) {
                // TODO : AgendaActivity
                return true;
            } else if (id == R.id.nav_actualites) {
                // TODO : ActualitesActivity
                return true;
            } else if (id == R.id.nav_menu) {
                // TODO : MenuActivity
                return true;
            }
            return false;
        });
    }
}