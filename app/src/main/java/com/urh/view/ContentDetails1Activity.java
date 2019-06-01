package com.urh.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.urh.R;
import com.urh.api.ApiClient;
import com.urh.model.Content;
import com.urh.model.Favorito;


public class ContentDetails1Activity extends AppCompatActivity {
    private Content content;
    private Boolean esFavorito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_details1);
        inicializarToolbar();

        Bundle args = getIntent().getExtras();
        content = new Gson().fromJson(args.getString("content"), Content.class);
        esFavorito = content.getFavorito();

        TextView tvName = findViewById(R.id.tvName);
        TextView tvAlgorithm = findViewById(R.id.tvAlgorithm);
        TextView tvCountry = findViewById(R.id.tvCountry);
        TextView tvYear = findViewById(R.id.tvYear);
        TextView tvMaximumValue = findViewById(R.id.tvMaximumValue);
        TextView tvUnit = findViewById(R.id.tvUnit);
        TextView tvBlockAmount = findViewById(R.id.tvBlockAmount);
        TextView tvDescription = findViewById(R.id.tvDescription);
        ImageView imagen = findViewById(R.id.imagen);
        Button btnVerVideo = findViewById(R.id.btnVerVideo);

        tvName.setText(content.getName());
        tvAlgorithm.setText(content.getAlgorithm());
        tvCountry.setText(content.getCountry());
        tvYear.setText(String.valueOf(content.getYear()));
        tvMaximumValue.setText(String.valueOf(content.getMaximum_value()));
        tvBlockAmount.setText(String.valueOf(content.getBlock_amount()));
        tvDescription.setText(content.getDescription());
        tvUnit.setText(content.getUnit());

        if(content.getExtraButtonLink() != null && !content.getExtraButtonLink().isEmpty()){
            Button btnExtra = findViewById(R.id.btnExtra);
            btnExtra.setVisibility(View.VISIBLE);
            btnExtra.setText(content.getExtraButtonName());
            btnExtra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(content.getExtraButtonLink()));
                    startActivity(browserIntent);
                }
            });
        }
        Glide.with(ContentDetails1Activity.this)
                .load(ApiClient.imagesUrl + content.getImage())
                .into(imagen);

        View.OnClickListener loadVideoViewer = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentDetails1Activity.this, VideoViewerActivity.class);
                intent.putExtra("videoId", content.getVideo_id());
                ContentDetails1Activity.this.startActivity(intent);
            }
        };

        imagen.setOnClickListener(loadVideoViewer);
        btnVerVideo.setOnClickListener(loadVideoViewer);
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
        getMenuInflater().inflate(R.menu.menu_info_content, menu);
        MenuItem mnuFavorito;
        if (esFavorito) {
            mnuFavorito = menu.findItem(R.id.favorito).setIcon(R.drawable.ic_star_full);
        } else {
            mnuFavorito = menu.findItem(R.id.favorito).setIcon(R.drawable.ic_star_empty);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem mnuFavorito;

        switch (item.getItemId()) {
            case R.id.favorito:
                if (esFavorito) {
                    Favorito.deleteFavorito(content.getId(), this);
                    item.setIcon(R.drawable.ic_star_empty);
                    Toast.makeText(this, getString(R.string.quitadoDeFavoritos, content.getName()), Toast.LENGTH_SHORT).show();
                } else {
                    Favorito.addFavorito(content.getId(), this);
                    item.setIcon(R.drawable.ic_star_full);
                    Toast.makeText(this, getString(R.string.agregadoAFavoritos, content.getName()), Toast.LENGTH_SHORT).show();
                }
                esFavorito = !esFavorito;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

