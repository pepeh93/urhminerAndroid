package com.urh.controller.pools;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.urh.R;
import com.urh.model.Pool;
import java.util.List;

public class PoolAdapter extends RecyclerView.Adapter<PoolAdapter.PoolsViewHolder> {
    private List<Pool> pools;
    private Context context;

    public PoolAdapter(List<Pool> pools, Context context) {
        this.pools = pools;
        this.context = context;
    }

    @Override
    public PoolsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_pool, parent, false);
        return new PoolsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoolAdapter.PoolsViewHolder poolViewHolder, int i) {
        poolViewHolder.bind(pools.get(i));
    }

    @Override
    public int getItemCount() {
        return pools.size();
    }

    class PoolsViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvStatus;
        TextView tvLastChecked;

        PoolsViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvLastChecked = itemView.findViewById(R.id.tvLastChecked);
        }

        public void bind(final Pool pool) {
            tvName.setText(pool.getName());
            tvLastChecked.setText("Última comprobación: " + pool.getLastCheckedAt());
            if(pool.getStatus() == 1){
                //El pool está activo.
                tvStatus.setText("Estado: ACTIVO");
                tvStatus.setTextColor(Color.rgb(0,165,114));
            }
            else
            {
                //El pool está caido
                tvStatus.setText("Estado: CAÍDO");
                tvStatus.setTextColor(Color.RED);
            }
        }
    }
}
