package com.urh.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.urh.R;
import com.urh.api.ApiClient;
import com.urh.controller.categorias.CategoriaAdapter;
import com.urh.model.Categoria;
import com.urh.model.Usuario;

import android.location.LocationManager;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISOS_UBICACION = 1;

    NavigationView navigationView;
    View navHeaderView;
    LocationManager locationManager;
    TextView tvCiudad;
    private RecyclerView rv;
    private ProgressBar progressBar;
    public static List<Categoria> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView collapsingToolbarImageView = findViewById(R.id.collapsing_toolbar_image_view);
        collapsingToolbarImageView.setImageResource(R.drawable.main);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rv = findViewById(R.id.rvCategorias);
        progressBar = findViewById(R.id.progressBar);

        inicializarNavHeader();
        getCategorias();
        getUbicacion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarImagenPerfil();
    }

    private void inicializarNavHeader() {
        navHeaderView = navigationView.getHeaderView(0);
        TextView tvNameEmail = navHeaderView.findViewById(R.id.tvNameEmail);

        tvCiudad = navHeaderView.findViewById(R.id.tvCiudad);
        SharedPreferences sp = CategoriasActivity.this.getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE);
        tvNameEmail.setText(sp.getString("name", "") + " - " + sp.getString("email", ""));
        actualizarImagenPerfil();

        navHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoriasActivity.this,
                        PerfilActivity.class);
                CategoriasActivity.this.startActivity(intent);
            }
        });
    }

    private void actualizarImagenPerfil() {
        if (PerfilActivity.tieneFotoPerfil(this)) {
            Bitmap bitmap = PerfilActivity.getFotoPerfil(this);
            ImageView ivPerfil = navHeaderView.findViewById(R.id.ivPerfil);
            ivPerfil.setImageBitmap(bitmap);
        }
    }

    private void getCategorias() {
        ApiClient.getClient(this).getCategorias(Usuario.getPersistedToken(this)).enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                categorias = response.body();
                llenarListaCategorias(categorias);
                rv.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable throwable) {
                Toast.makeText(CategoriasActivity.this, "Error al obtener información del servidor. Asegurese que hay una conexión a Internet",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void llenarListaCategorias(List<Categoria> categorias) {
        rv = findViewById(R.id.rvCategorias);
        CategoriaAdapter adapter = new CategoriaAdapter(categorias, CategoriasActivity.this);
        rv.setLayoutManager(new LinearLayoutManager(CategoriasActivity.this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_hashratepools:
                Intent intentHashratePools = new Intent(this, HashratePoolsActivity.class);
                startActivity(intentHashratePools);
                break;
            case R.id.nav_favoritos:
                Intent intentFavoritos = new Intent(this, FavoritosActivity.class);
                startActivity(intentFavoritos);
                break;
            case R.id.nav_cerrar_sesion:
                cerrarSesion();
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cerrarSesion() {
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.app_name),
                        Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void getUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISOS_UBICACION);
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, R.string.activaUbicacion, Toast.LENGTH_LONG).show();
        } else {
            Location location = null;
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null)
                getCiudad(location.getLatitude(), location.getLongitude());
        }
    }

    private void getCiudad(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> ubicaciones;

        try {
            ubicaciones = geocoder.getFromLocation(lat, lng, 1);
            if (ubicaciones.size() > 0)
                tvCiudad.setText(ubicaciones.get(0).getLocality());
            else
                tvCiudad.setText(R.string.ciudadNoIdentificada);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISOS_UBICACION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUbicacion();
            }
        }
    }
}
