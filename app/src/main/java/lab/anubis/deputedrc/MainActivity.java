package lab.anubis.deputedrc;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lab.anubis.deputedrc.adapter.RealisationAdapter;
import lab.anubis.deputedrc.databinding.ActivityMainBinding;
import lab.anubis.deputedrc.model.Realisation;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    // Contrôle central : période de campagne active ou non.
    // À terme, cette valeur viendra d'une config distante (Firebase Remote Config
    // ou API back-office), pas d'une constante en dur.
    private static final boolean CAMPAGNE_ATCIVE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appliquerVisibiliteBadgeCampagne();
        chargerImages();
        configurerRealisationsPhares();
        configurerNavigationBasse();

        final int paddingBottomOriginalNav = binding.bottomNavigation.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            binding.includeBadge.setTranslationY(systemBars.top);

            binding.bottomNavigation.setPadding(
                    binding.bottomNavigation.getPaddingLeft(),
                    binding.bottomNavigation.getPaddingTop(),
                    binding.bottomNavigation.getPaddingRight(),
                    paddingBottomOriginalNav + systemBars.bottom
            );
//
//            binding.bottomNavigation.setPadding(
//                    binding.bottomNavigation.getPaddingLeft(),
//                    binding.bottomNavigation.getPaddingTop(),
//                    binding.bottomNavigation.getPaddingRight(),
//                    systemBars.bottom + paddingBottomOriginalNav
//            );

            return insets;
        });
    }

    private void appliquerVisibiliteBadgeCampagne() {
        binding.includeBadge.setVisibility(
                CAMPAGNE_ATCIVE ? View.VISIBLE : View.GONE
        );
    }
    private void chargerImages() {
        Glide.with(this)
                .load("https://images.unsplash.com/photo-1489424731084-a5d8b219a5bb?w=800&h=500&fit=crop")
                .centerCrop()
                .into(binding.imgHero);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=120&h=120&fit=crop")
                .centerCrop()
                .into(binding.imgPortrait);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1529107386315-e1a2ed48a620?w=400&h=220&fit=crop")
                .centerCrop()
                .into(binding.imgActualite1);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1581056771107-24ca5f033842?w=400&h=220&fit=crop")
                .centerCrop()
                .into(binding.imgActualite2);
    }

    private void configurerRealisationsPhares() {
        List<Realisation> phares = new ArrayList<>();
        phares.add(new Realisation("1", "École Primaire de Sange", "Éducation", "Sange", 2020,
                "", "300 élèves", ""));
        phares.add(new Realisation("2", "Centre de Santé d'Uvira Nord", "Santé", "Uvira Nord", 2021,
                "", "", ""));
        phares.add(new Realisation("3", "Route Uvira–Kiliba", "Infrastructure", "Kiliba", 2022,
                "", "", ""));

        binding.recyclerPhares.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerPhares.setAdapter(new RealisationAdapter(phares));
    }



    private void configurerNavigationBasse () {
        binding.bottomNavigation.setSelectedItemId(R.id.nav_accueil);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_accueil) {
                return true;  // déjà sur l'accueil
            } else if (id == R.id.nav_realisations) {
                startActivity(new android.content.Intent(this, RealisationsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_agenda) {
                // TODO : démarrer AgendaActivity
                return true;
            } else if (id == R.id.nav_actualites) {
                // TODO : démarrer ActualitesActivity
                return true;
            } else if (id == R.id.nav_menu) {
                // TODO : démarrer MenuActivity
                return true;
            }
            return false;
        });
    }
}