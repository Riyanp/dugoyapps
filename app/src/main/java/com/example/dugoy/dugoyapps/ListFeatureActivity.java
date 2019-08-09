package com.example.dugoy.dugoyapps;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.dugoy.dugoyapps.dao.DatabaseHandler;
import com.example.dugoy.dugoyapps.R;
import com.example.dugoy.dugoyapps.model.Martabak;
import com.example.dugoy.dugoyapps.service.RetrofitActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListFeatureActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Intent intent;

    private DatabaseHandler dbHelper;
    private List<Martabak> listMartabak;
    private ArrayList<String> list = new ArrayList<>();

    private Button btnBack, btnRetro;

    private Gson gson = new Gson();

    Toolbar mActionBarToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_martabak);

//        listView = findViewById(R.id.listMartabak);
        btnBack = findViewById(R.id.btnBack);
        btnRetro = findViewById(R.id.btnRetro);

        btnBack.setOnClickListener(this);
        btnRetro.setOnClickListener(this);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Dugoy Apps");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E91E63")));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnBack:
                intent = new Intent(ListFeatureActivity.this, CRUDActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRetro:
                intent = new Intent(ListFeatureActivity.this, RetrofitActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        if(position > -1) {
            Object o =  gson.toJson(listMartabak.get(position));
            Intent myIntent = new Intent(ListFeatureActivity.this, CRUDActivity.class);
            myIntent.putExtra("contact", listMartabak.get(position));
            startActivity(myIntent);
        }
    }
}
