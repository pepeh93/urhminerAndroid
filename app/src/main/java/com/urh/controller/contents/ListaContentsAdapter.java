package com.urh.controller.contents;

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

import java.util.ArrayList;

public class ListaContentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Content> contents;
    private Context context;

    public ListaContentsAdapter(ArrayList<Content> contents, Context context) {
        this.contents = contents;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.content_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((ContentViewHolder) viewHolder).bind(contents.get(position));
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    private class ContentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final ImageView imagen;

        public ContentViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imagen = itemView.findViewById(R.id.imagen);
        }

        public void bind(final Content content) {
            tvName.setText(content.getName());

            Glide.with(context)
                    .load(ApiClient.imagesUrl + content.getImage())
                    .into(imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (content.getTipoContenido() == 1) {
                        intent = new Intent(context, ContentDetails1Activity.class);
                    } else {
                        intent = new Intent(context, ContentDetails2Activity.class);
                    }
                    intent.putExtra("content", new Gson().toJson(content));
                    context.startActivity(intent);
                }
            });
        }
    }
}