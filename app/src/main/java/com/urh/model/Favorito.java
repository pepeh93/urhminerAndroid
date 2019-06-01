package com.urh.model;

import android.content.Context;
import android.widget.Toast;
import com.urh.api.ApiClient;
import com.urh.view.CategoriasActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorito {
    public static void addFavorito(final Integer id, final Context context) {
        ApiClient.getClient(context).addFavorito(id, Usuario.getPersistedToken(context)).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body()) {
                    for (Categoria categoria : CategoriasActivity.categorias) {
                        for (Content content : categoria.getContent())
                            if (content.getId() == id) {
                                content.setFavorito(true);
                            }
                    }
                } else {
                    Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void deleteFavorito(final Integer id, final Context context) {
        ApiClient.getClient(context).deleteFavorito(id, Usuario.getPersistedToken(context)).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body()) {
                    for (Categoria categoria : CategoriasActivity.categorias) {
                        for (Content content : categoria.getContent())
                            if (content.getId() == id) {
                                content.setFavorito(false);
                            }
                    }
                } else {
                    Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
