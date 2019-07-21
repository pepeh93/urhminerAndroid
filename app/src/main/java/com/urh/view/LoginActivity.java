package com.urh.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.urh.R;
import com.urh.api.ApiClient;
import com.urh.model.Usuario;
import com.urh.view.onboarding.OnboardingActivity;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btnIngresar;
    private ProgressBar progressBar;
    private EditText etUsuario;
    private EditText etContraseña;
    private TextView btnNoPuedoAcceder;
    private TextView btnCrearCuenta;
    private Boolean viewInitialized = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Usuario usuario = Usuario.getPersistedUser(LoginActivity.this);
        if (usuario != null && !usuario.getEmail().isEmpty() && !usuario.getPassword().isEmpty()) {
            login(usuario.getEmail(), usuario.getPassword());
        } else {
            inicializarVista();
        }
    }

    private void inicializarVista() {
        setContentView(R.layout.activity_login);
        etUsuario = findViewById(R.id.etUsuario);
        etContraseña = findViewById(R.id.etContraseña);
        btnIngresar = findViewById(R.id.btnIngresar);
        progressBar = findViewById(R.id.progressBar);
        inicializarBotonNoPuedoAcceder();
        inicializarBotonCrearCuenta();
        viewInitialized = true;

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = etUsuario.getText().toString();
                String contraseña = etContraseña.getText().toString();

                if (!contraseña.isEmpty() && !usuario.isEmpty() && validEmail(usuario)) {
                    login(usuario, contraseña);
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.faltaUserYPass), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void login(String usuario, final String contraseña) {
        showProgressBar();
        Call<Usuario> loginCall = ApiClient.getClient(LoginActivity.this).login(new Usuario(usuario, contraseña));
        loginCall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    String error = getString(R.string.userOPassIncorrecto);
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                    hideProgressBar();

                    if(!viewInitialized)
                    inicializarVista();
                } else {
                    Usuario usuarioLogueado;
                    usuarioLogueado = response.body();
                    usuarioLogueado.setPassword(contraseña);
                    Usuario.persistirUsuario(LoginActivity.this, usuarioLogueado);

                    Intent intent;
                    if (viewInitialized)
                        intent = new Intent(LoginActivity.this, OnboardingActivity.class);
                    else
                        intent = new Intent(LoginActivity.this, CategoriasActivity.class);

                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgressBar();
            }
        });
    }

    private void inicializarBotonCrearCuenta() {
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://urhminer.ml/register"));
                startActivity(browserIntent);
            }
        });
    }

    private void showProgressBar() {
        if (viewInitialized) {
            btnIngresar.setVisibility(View.GONE);
            btnCrearCuenta.setVisibility(View.GONE);
            btnNoPuedoAcceder.setVisibility(View.GONE);
            etContraseña.setEnabled(false);
            etUsuario.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (viewInitialized) {
            etContraseña.setEnabled(true);
            etUsuario.setEnabled(true);
            btnIngresar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            btnCrearCuenta.setVisibility(View.VISIBLE);
            btnNoPuedoAcceder.setVisibility(View.VISIBLE);
        }
    }

    private void inicializarBotonNoPuedoAcceder() {
        btnNoPuedoAcceder = findViewById(R.id.btnNoPuedoAcceder);
        btnNoPuedoAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://urhminer.ml/password/reset"));
                startActivity(browserIntent);
            }
        });
    }
}
