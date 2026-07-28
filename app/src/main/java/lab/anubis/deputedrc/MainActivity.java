package lab.anubis.deputedrc;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import lab.anubis.deputedrc.databinding.ActivityMainBinding;

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
        configurerNavigationBasse();

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void appliquerVisibiliteBadgeCampagne() {
        binding.includeBadge.badgeNumeroVote.setVisibility(
                CAMPAGNE_ATCIVE ? View.VISIBLE : View.GONE
        );
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