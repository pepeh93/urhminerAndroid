package com.urh.controller.categorias;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.urh.R;
import com.urh.controller.contents.ListaContentsAdapter;
import com.urh.model.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> categorias;
    private Context context;

    public CategoriaAdapter(List<Categoria> categorias, Context context)
    {
        this.categorias = categorias;
        this.context = context;
    }

    @Override
    public CategoriaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriaViewHolder holder, int position) {
        holder.tvName.setText(categorias.get(position).getName());
        ListaContentsAdapter adapter = new ListaContentsAdapter(categorias.get(position).getContent(), context);
        holder.rvContent.setHasFixedSize(true);
        holder.rvContent.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvContent.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

     class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        RecyclerView rvContent;

        CategoriaViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            rvContent = itemView.findViewById(R.id.rvContent);
        }
}}