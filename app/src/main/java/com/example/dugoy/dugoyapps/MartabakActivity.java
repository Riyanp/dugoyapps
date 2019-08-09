package com.example.dugoy.dugoyapps;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dugoy.dugoyapps.dao.DatabaseHandler;
import com.example.dugoy.dugoyapps.R;
import com.example.dugoy.dugoyapps.adapter.MartabakAdapter;
import com.example.dugoy.dugoyapps.model.Martabak;

import java.util.ArrayList;
import java.util.List;

public class MartabakActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private List<Martabak> listMartabak = new ArrayList<Martabak>();
    private RecyclerView recyclerView;
    private MartabakAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_martabak);

        db = new DatabaseHandler(this);

        recyclerView = findViewById(R.id.rv_martabak);

        listMartabak.addAll(db.getAllMartabak());
        adapter = new MartabakAdapter(this, listMartabak);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        boolean IS_NEW = extras.getBoolean("isNew");

        if (IS_NEW) {
            Snackbar snackbar = Snackbar.make(recyclerView, "Pesanan Berhasil dibuat", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.parseColor("#E91E63"));
            snackbar.show();
        }

        Toolbar toolbar = findViewById(R.id.toolbar_martabak_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Keranjang");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E91E63")));
    }
}
