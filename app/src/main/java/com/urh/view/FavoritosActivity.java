package com.urh.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.urh.R;
import com.urh.api.ApiClient;
import com.urh.controller.favoritos.FavoritosAdapter;
import com.urh.model.Content;
import com.urh.model.Usuario;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritosActivity extends AppCompatActivity {
    private FavoritosAdapter favAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        inicializarToolbar();
        getFavoritos();
    }

    private void getFavoritos() {
        ApiClient.getClient(this).getFavoritos(Usuario.getPersistedToken(this)).enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                List<Content> favoritos = response.body();
                RecyclerView rv = findViewById(R.id.rvFavoritos);
                TextView tvSinFavoritos = findViewById(R.id.tvSinFavoritos);

                if (favoritos.size() == 0) {
                    rv.setVisibility(View.GONE);
                    tvSinFavoritos.setVisibility(View.VISIBLE);
                }
                else
                {
                    favAdapter = new FavoritosAdapter(favoritos, getApplicationContext());
                    rv.setLayoutManager(new LinearLayoutManager(FavoritosActivity.this,
                            LinearLayoutManager.VERTICAL, false));
                    rv.setAdapter(favAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                Toast.makeText(FavoritosActivity.this, "error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFavoritos();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_favoritos, menu);
        return true;
    }
}

