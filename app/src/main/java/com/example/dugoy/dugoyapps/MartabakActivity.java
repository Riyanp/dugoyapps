package com.example.dugoy.dugoyapps;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

import com.example.dugoy.dugoyapps.dao.DatabaseHandler;
import com.example.dugoy.dugoyapps.R;
import com.example.dugoy.dugoyapps.adapter.MartabakAdapter;
import com.example.dugoy.dugoyapps.model.Martabak;

import java.util.ArrayList;
import java.util.List;

public class MartabakActivity extends AppCompatActivity {

    private static final int VERTICAL = 1;
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

        Toolbar toolbar = findViewById(R.id.toolbar_martabak_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Keranjang");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E91E63")));
    }
}
