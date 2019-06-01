package com.urh.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.urh.R;
import com.urh.api.ApiClient;
import com.urh.controller.pools.PoolAdapter;
import com.urh.model.Pool;
import com.urh.model.Usuario;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HashratePoolsActivity extends AppCompatActivity {
    public static List<Pool> pools;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashrate_pools);
        rv = findViewById(R.id.rvPools);
        inicializarToolbar();
        obtenerPools();
    }

    private void obtenerPools() {
        ApiClient.getClient(getApplicationContext()).getPools(
                Usuario.getPersistedToken(getApplicationContext())).enqueue(new Callback<List<Pool>>() {
            @Override
            public void onResponse(Call<List<Pool>> call, Response<List<Pool>> response) {
                pools = response.body();

                if (pools.size() == 0) {
                    rv.setVisibility(View.GONE);
                    TextView tvSinPools = findViewById(R.id.tvSinPools);
                    tvSinPools.setVisibility(View.VISIBLE);
                } else {
                    llenarListaPools(pools);
                }
            }

            @Override
            public void onFailure(Call<List<Pool>> call, Throwable throwable) {
                Toast.makeText(HashratePoolsActivity.this, "Error al obtener información del servidor. Asegurese que hay una conexión a Internet",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void llenarListaPools(List<Pool> pools) {

        PoolAdapter adapter = new PoolAdapter(pools, HashratePoolsActivity.this);
        rv.setLayoutManager(new LinearLayoutManager(HashratePoolsActivity.this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
    }

    private void inicializarToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
