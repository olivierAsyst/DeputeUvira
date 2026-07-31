package lab.anubis.deputedrc.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;

import lab.anubis.deputedrc.databinding.ItemRealisationPhareBinding;
import lab.anubis.deputedrc.model.Realisation;

public class RealisationPhareAdapter extends RecyclerView.Adapter<RealisationPhareAdapter.PhareViewHolder> {

    private final List<Realisation> liste;

    public RealisationPhareAdapter(List<Realisation> liste) {
        this.liste = liste;
    }

    @NonNull
    @Override
    public PhareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRealisationPhareBinding binding = ItemRealisationPhareBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PhareViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhareViewHolder holder, int position) {
        holder.bind(liste.get(position));
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    static class PhareViewHolder extends RecyclerView.ViewHolder {
        private final ItemRealisationPhareBinding binding;

        PhareViewHolder(ItemRealisationPhareBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Realisation r) {
            binding.tvAnneePhare.setText("Complété " + r.getAnnee());
            binding.tvTitrePhare.setText(r.getTitre());
            binding.tvLieuPhare.setText(r.getLieu());
            Glide.with(binding.getRoot().getContext())
                    .load("https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=400&h=240&fit=crop")
                    .centerCrop()
                    .into(binding.imgPhare);
        }
    }
}