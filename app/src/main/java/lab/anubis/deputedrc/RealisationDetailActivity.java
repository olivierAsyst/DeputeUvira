package lab.anubis.deputedrc;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import lab.anubis.deputedrc.databinding.ActivityRealisationDetailBinding;
import lab.anubis.deputedrc.model.Realisation;

public class RealisationDetailActivity extends AppCompatActivity {

    public static final String EXTRA_REALISATION = "extra_realisation";
    private ActivityRealisationDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRealisationDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Realisation r = (Realisation) getIntent().getSerializableExtra(EXTRA_REALISATION);
        if (r == null) {
            finish();
            return;
        }

        afficherRealisation(r);
        binding.btnRetour.setOnClickListener(v -> finish());
    }

    private void afficherRealisation(Realisation r) {
        binding.tvTitreDetail.setText(r.getTitre());
        binding.tvLieuDetail.setText("📍 " + r.getLieu());
        binding.tvAnneeDetail.setText(String.valueOf(r.getAnnee()));
        binding.tvDescriptionDetail.setText(r.getDescription());

        binding.tvSecteurDetail.setText(r.getSecteur().toUpperCase());
        binding.tvSecteurDetail.setBackgroundColor(couleurSecteur(r.getSecteur()));

        if (r.estComplete()) {
            binding.tvStatutDetail.setText("✓ Complété");
            binding.tvStatutDetail.setTextColor(ContextCompat.getColor(this, R.color.text_white_100));
            binding.tvStatutDetail.setBackgroundColor(ContextCompat.getColor(this, R.color.status_complete));
        } else {
            binding.tvStatutDetail.setText("⟳ En cours");
            binding.tvStatutDetail.setTextColor(ContextCompat.getColor(this, android.R.color.black));
            binding.tvStatutDetail.setBackgroundColor(ContextCompat.getColor(this, R.color.accent_amber));
        }

        if (r.getBeneficiaires() != null && !r.getBeneficiaires().isEmpty()) {
            binding.tvBeneficiairesDetail.setText(r.getBeneficiaires());
        } else {
            binding.blocBeneficiaires.setVisibility(android.view.View.GONE);
        }

        if (r.getFinancement() != null && !r.getFinancement().isEmpty()) {
            binding.tvFinancementDetail.setText(r.getFinancement());
        } else {
            binding.blocFinancement.setVisibility(android.view.View.GONE);
        }

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=600&h=400&fit=crop")
                .centerCrop()
                .into(binding.imgDetail);

        binding.btnPartager.setOnClickListener(v -> {
            Intent intentPartage = new Intent(Intent.ACTION_SEND);
            intentPartage.setType("text/plain");
            intentPartage.putExtra(Intent.EXTRA_TEXT,
                    r.getTitre() + " — " + r.getLieu() + " (" + r.getAnnee() + ")\n" + r.getDescription());
            startActivity(Intent.createChooser(intentPartage, getString(R.string.action_partager_realisation)));
        });
    }

    private int couleurSecteur(String secteur) {
        switch (secteur) {
            case "education": return ContextCompat.getColor(this, R.color.sector_education);
            case "sante": return ContextCompat.getColor(this, R.color.sector_sante);
            case "infrastructure": return ContextCompat.getColor(this, R.color.sector_infrastructure);
            case "eau": return ContextCompat.getColor(this, R.color.sector_eau);
            case "securite": return ContextCompat.getColor(this, R.color.sector_securite);
            default: return ContextCompat.getColor(this, R.color.sector_education);
        }
    }
}
