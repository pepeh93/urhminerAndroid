package com.urh.controller.favoritos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.urh.R;
import com.urh.api.ApiClient;
import com.urh.model.Content;
import com.urh.view.ContentDetails1Activity;
import com.urh.view.ContentDetails2Activity;
import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder> {
    private List<Content> favoritos;
    private Context context;

    public FavoritosAdapter(List<Content> favoritos, Context context) {
        this.favoritos = favoritos;
        this.context = context;
    }

    @Override
    public FavoritosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_favorito, parent, false);
        return new FavoritosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoritosViewHolder holder, int position) {
        holder.bind(favoritos.get(position));
    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    class FavoritosViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView image;

        FavoritosViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            image = itemView.findViewById(R.id.image);
        }

        public void bind(final Content favorito) {
            Glide.with(context)
                    .load(ApiClient.imagesUrl + favorito.getImage())
                    .into(image);
            tvName.setText(favorito.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (favorito.getTipoContenido() == 1) {
                        intent = new Intent(context, ContentDetails1Activity.class);
                    } else {
                        intent = new Intent(context, ContentDetails2Activity.class);
                    }
                    intent.putExtra("content", new Gson().toJson(favorito));
                    context.startActivity(intent);
                }
            });
        }
    }
}
