package com.urh.api;

import com.urh.model.Categoria;
import com.urh.model.Content;
import com.urh.model.Pool;
import com.urh.model.Usuario;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("content")
    public Call<List<Categoria>> getCategorias(@Header("Authorization") String authHeader);

    @POST("user/login")
    Call<Usuario> login(@Body Usuario loginBody);
    
    @GET("users")
    Call<Usuario> getUserInfo(@Header("Authorization") String authHeader);

    @DELETE("favorito/{id}")
    Call<Boolean> deleteFavorito(@Path("id") Integer id, @Header("Authorization") String authHeader);

    @PUT("favorito/{id}")
    Call<Boolean> addFavorito(@Path("id") Integer id, @Header("Authorization") String authHeader);

    @GET("favoritos")
    Call<List<Content>> getFavoritos(@Header("Authorization") String authHeader);

    @GET("pools")
    Call<List<Pool>> getPools(@Header("Authorization") String authHeader);
}