package lab.anubis.deputedrc.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lab.anubis.deputedrc.R;
import lab.anubis.deputedrc.databinding.ItemRealisationBinding;
import lab.anubis.deputedrc.model.Realisation;

public class RealisationAdapter extends RecyclerView.Adapter<RealisationAdapter.RealisationViewHolder> {

    public RealisationAdapter(OnRealisationClickListener listener) {
        this.listener = listener;
    }

    public interface OnRealisationClickListener{
        void onRealisationClick(Realisation realisation);
    }

    private final List<Realisation> listeAffichee = new ArrayList<>();
    private final OnRealisationClickListener listener;

    public void soumettreListe(List<Realisation> nouvelleListe){
        listeAffichee.clear();
        listeAffichee.addAll(nouvelleListe);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RealisationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRealisationBinding binding = ItemRealisationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
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

    static class RealisationViewHolder extends RecyclerView.ViewHolder{
        private final ItemRealisationBinding binding;

        public RealisationViewHolder(ItemRealisationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Realisation realisation, OnRealisationClickListener listener){
            binding.tvTitreRealisation.setText(realisation.getTitre());
            binding.tvSecteur.setText(realisation.getSecteur());
            binding.tvLieuAnnee.setText(realisation.getAnnee() + " · " + realisation.getLieu());

            appliquerCouleurSecteur(realisation.getSecteur());

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onRealisationClick(realisation);
            });
        }

        private void appliquerCouleurSecteur(String secteur) {
            int couleurFond;
            int couleurTexte;

            switch (secteur) {
                case "Santé":
                    couleurFond = ContextCompat.getColor(binding.getRoot().getContext(), R.color.warning_bg);
                    couleurTexte = ContextCompat.getColor(binding.getRoot().getContext(), R.color.warning);
                    break;
                case "Eau":
                case "Infrastructures":
                    couleurFond = ContextCompat.getColor(binding.getRoot().getContext(), R.color.info_bg);
                    couleurTexte = ContextCompat.getColor(binding.getRoot().getContext(), R.color.info);
                    break;
                default: // Éducation et autres
                    couleurFond = ContextCompat.getColor(binding.getRoot().getContext(), R.color.success_bg);
                    couleurTexte = ContextCompat.getColor(binding.getRoot().getContext(), R.color.success);
                    break;
            }

            GradientDrawable fond = (GradientDrawable) binding.tvSecteur.getBackground().mutate();
            fond.setColor(couleurFond);
            binding.tvSecteur.setTextColor(couleurTexte);
        }

    }
}
