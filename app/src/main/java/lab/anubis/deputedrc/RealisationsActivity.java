package lab.anubis.deputedrc;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

        final int paddingTopOriginalHeader = binding.headerRealisations.getPaddingTop();
        final int paddingBottomOriginalNav = binding.bottomNavigation.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            binding.headerRealisations.setPadding(
                    binding.headerRealisations.getPaddingLeft(),
                    paddingTopOriginalHeader + systemBars.top,
                    binding.headerRealisations.getPaddingRight(),
                    binding.headerRealisations.getPaddingBottom()
            );

            binding.bottomNavigation.setPadding(
                    binding.bottomNavigation.getPaddingLeft(),
                    binding.bottomNavigation.getPaddingTop(),
                    binding.bottomNavigation.getPaddingRight(),
                    paddingBottomOriginalNav + systemBars.bottom
            );
            return insets;
        });
        configurerNavigationBasse();
    }

    private void chargerDonneesDeDemonstration() {
        toutesLesRealisations.add(new Realisation("1",
                "Réhabilitation de l'école de Kavimvira", "education", "Kavimvira, Uvira", 2025,
                "Reconstruction de 6 salles de classe et fourniture de bancs pupitres.",
                "600 élèves", "Fonds constit.", "complete"));

        toutesLesRealisations.add(new Realisation("2",
                "Forage d'eau potable — Kasenga", "eau", "Kasenga, Haut-Katanga", 2024,
                "Installation d'un forage desservant plus de 300 ménages.",
                "300 ménages", "Partenaire ONG", "complete"));

        toutesLesRealisations.add(new Realisation("3",
                "Équipement du centre de santé de Baraka", "sante", "Baraka, Fizi", 2024,
                "Don de matériel médical et de lits d'hospitalisation.",
                "1 centre de santé", "Fonds constit.", "inprogress"));
    }

    private void configurerFiltres() {
        binding.chipGroupSecteurs.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;

            reinitialiserApparenceChips();

            int idSelectionne = checkedIds.get(0);
            com.google.android.material.chip.Chip chipActif = binding.getRoot().findViewById(idSelectionne);
            chipActif.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.accent_amber)));
            chipActif.setTextColor(ContextCompat.getColor(this, R.color.on_amber));
            chipActif.setChipStrokeWidth(0f);

            if (idSelectionne == R.id.chipTous) {
                adapter.soumettreListe(toutesLesRealisations);
            } else if (idSelectionne == R.id.chipEducation) {
                adapter.soumettreListe(filtrerParSecteur("education"));
            } else if (idSelectionne == R.id.chipSante) {
                adapter.soumettreListe(filtrerParSecteur("sante"));
            } else if (idSelectionne == R.id.chipEau) {
                adapter.soumettreListe(filtrerParSecteur("eau"));
            }
        });
    }

    private void reinitialiserApparenceChips() {
        int[] tousLesChips = {R.id.chipTous, R.id.chipEducation, R.id.chipSante, R.id.chipEau};
        for (int idChip : tousLesChips) {
            com.google.android.material.chip.Chip chip = binding.getRoot().findViewById(idChip);
            chip.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.card_bg)));
            chip.setTextColor(ContextCompat.getColor(this, R.color.text_white_50));
            chip.setChipStrokeColor(android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.border_white_10)));
            chip.setChipStrokeWidth(1f);
        }
    }

    private void configurerListe() {
        adapter = new RealisationAdapter(realisation -> {
            android.content.Intent intent = new android.content.Intent(this, RealisationDetailActivity.class);
            intent.putExtra(RealisationDetailActivity.EXTRA_REALISATION, realisation);
            startActivity(intent);
        });

        binding.recyclerRealisations.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerRealisations.setAdapter(adapter);
        adapter.soumettreListe(toutesLesRealisations);
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