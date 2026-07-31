package lab.anubis.deputedrc.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lab.anubis.deputedrc.R;
import lab.anubis.deputedrc.databinding.ItemRealisationBinding;
import lab.anubis.deputedrc.model.Realisation;

public class RealisationAdapter extends RecyclerView.Adapter<RealisationAdapter.RealisationViewHolder> {

    public interface OnRealisationClickListener {
        void onRealisationClick(Realisation realisation);
    }

    private final List<Realisation> listeAffichee = new ArrayList<>();
    private final OnRealisationClickListener listener;

    public RealisationAdapter(OnRealisationClickListener listener) {
        this.listener = listener;
    }

    public void soumettreListe(List<Realisation> nouvelleListe) {
        listeAffichee.clear();
        listeAffichee.addAll(nouvelleListe);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RealisationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRealisationBinding binding = ItemRealisationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new RealisationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RealisationViewHolder holder, int position) {
        holder.bind(listeAffichee.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listeAffichee.size();
    }

    static class RealisationViewHolder extends RecyclerView.ViewHolder {

        private final ItemRealisationBinding binding;

        RealisationViewHolder(ItemRealisationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Realisation r, OnRealisationClickListener listener) {
            android.content.Context ctx = binding.getRoot().getContext();

            binding.tvTitreRealisation.setText(r.getTitre());
            binding.tvDescriptionRealisation.setText(r.getDescription());
            binding.tvLieu.setText("📍 " + r.getLieu());
            binding.tvAnnee.setText(String.valueOf(r.getAnnee()));

            binding.tvSecteur.setText(libelleSecteur(r.getSecteur()));
            binding.tvSecteur.setBackgroundColor(couleurSecteur(ctx, r.getSecteur()));

            if (r.estComplete()) {
                binding.tvStatut.setText("✓ Complété");
                binding.tvStatut.setTextColor(ContextCompat.getColor(ctx, R.color.text_white_100));
                binding.tvStatut.setBackgroundColor(ContextCompat.getColor(ctx, R.color.status_complete));
            } else {
                binding.tvStatut.setText("⟳ En cours");
                binding.tvStatut.setTextColor(ContextCompat.getColor(ctx, android.R.color.black));
                binding.tvStatut.setBackgroundColor(ContextCompat.getColor(ctx, R.color.accent_amber));
            }

            Glide.with(ctx).load(imageDemo(r.getSecteur())).centerCrop().into(binding.imgRealisation);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onRealisationClick(r);
            });
        }

        private void appliquerCouleurSecteur(String secteur) {
            int couleurFond;
            int couleurTexte;
            android.content.Context ctx = binding.getRoot().getContext();

            switch (secteur) {
                case "Santé":
                    couleurFond = ContextCompat.getColor(ctx, R.color.purple_bg_20);
                    couleurTexte = ContextCompat.getColor(ctx, R.color.purple_declaration);
                    break;
                case "Eau":
                case "Infrastructures":
                case "Infrastructure":
                    couleurFond = ContextCompat.getColor(ctx, R.color.blue_bg_20);
                    couleurTexte = ContextCompat.getColor(ctx, R.color.info_blue);
                    break;
                default: // Éducation et autres
                    couleurFond = ContextCompat.getColor(ctx, R.color.green_bg_20);
                    couleurTexte = ContextCompat.getColor(ctx, R.color.success_green);
                    break;
            }

            binding.tvSecteur.setBackgroundColor(couleurFond);
            binding.tvSecteur.setTextColor(couleurTexte);
        }

        private String libelleSecteur(String secteur) {
            switch (secteur) {
                case "education": return "ÉDUCATION";
                case "sante": return "SANTÉ";
                case "infrastructure": return "INFRASTRUCTURE";
                case "eau": return "EAU";
                case "securite": return "SÉCURITÉ";
                default: return secteur.toUpperCase();
            }
        }

        private int couleurSecteur(android.content.Context ctx, String secteur) {
            switch (secteur) {
                case "education": return ContextCompat.getColor(ctx, R.color.sector_education);
                case "sante": return ContextCompat.getColor(ctx, R.color.sector_sante);
                case "infrastructure": return ContextCompat.getColor(ctx, R.color.sector_infrastructure);
                case "eau": return ContextCompat.getColor(ctx, R.color.sector_eau);
                case "securite": return ContextCompat.getColor(ctx, R.color.sector_securite);
                default: return ContextCompat.getColor(ctx, R.color.sector_education);
            }
        }

        private String imageDemo(String secteur) {
            // Démo — à remplacer par un champ imageUrl réel sur Realisation en production
            return "https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=400&h=240&fit=crop";
        }
    }
}