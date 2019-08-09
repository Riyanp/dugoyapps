package com.example.dugoy.dugoyapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dugoy.dugoyapps.dao.DatabaseHandler;
import com.example.dugoy.dugoyapps.R;
import com.example.dugoy.dugoyapps.model.Martabak;
import com.example.dugoy.dugoyapps.service.RetrofitActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CRUDActivity extends AppCompatActivity implements View.OnClickListener {

    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private EditText kuantitas;
    private Button btnAdd, btnUpdate, btnDelete, btnList, btnAPI;
    private Intent intent;
    private Spinner spinner;
    private RadioButton biasa, special;
    private CheckBox susu, keju, coklat;

    private DatabaseHandler dbHelper;
    private Gson gson = new Gson();

    private Martabak myMartabak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHandler(CRUDActivity.this);
        kuantitas = findViewById(R.id.kuantitas);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.martabaks, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        susu = findViewById(R.id.susu);
        keju = findViewById(R.id.keju);
        coklat = findViewById(R.id.coklat);

        biasa = findViewById(R.id.biasa);
        special = findViewById(R.id.special);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnList = findViewById(R.id.btnList);
        btnAPI = findViewById(R.id.btnAPI);

        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnAPI.setOnClickListener(this);

        //myMartabak = gson.fromJson(getIntent().getStringExtra("contact"),Martabak.class);
        myMartabak = (Martabak) getIntent().getSerializableExtra("contact");

        if (myMartabak != null) {
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            int selected = 0;
            if (myMartabak.getName().equals("Martabak Black Forest")) {
                selected = 1;
            }
            spinner.setSelection(selected);

            if (myMartabak.getToppings().contains("Susu"))
                susu.setChecked(true);
            if (myMartabak.getToppings().contains("Keju"))
                keju.setChecked(true);
            if (myMartabak.getToppings().contains("Coklat"))
                coklat.setChecked(true);
            if (myMartabak.getType().contains("Special"))
                special.setChecked(true);

//            tel.setText(myMartabak.getTel());
        }

    }

    @Override
    public void onClick(View view) {

        double harga = 0;
        String martabakName = spinner.getSelectedItem().toString();
        long martabakId = spinner.getSelectedItemId();
        Integer qty = Integer.valueOf(kuantitas.getText().toString());
        if (martabakId == 1L) {
            harga = 15000;
        } else if (martabakId == 2L) {
            harga = 20000;
        } else {
            harga = 15000;
        }

        ArrayList topping = new ArrayList();
        boolean atLeastOneChecked = false;

        //Validate checkbox
        if (susu.isChecked()) {
            harga += 3000;
            topping.add("Susu");
            atLeastOneChecked = true;
        }
        if (keju.isChecked()) {
            harga += 7000;
            topping.add("Keju");
            atLeastOneChecked = true;
        }
        if (coklat.isChecked()) {
            harga += 5000;
            topping.add("Coklat");
            atLeastOneChecked = true;
        }

        //Validate radiobutton
        String type = "Biasa";
        if (special.isChecked()) {
            type = "Special";
            harga += 5000;
        }

        if (qty != 0)
            harga *= qty;

        String toppingString = topping.toString();

        switch (view.getId()) {
            case R.id.btnAdd:

                if (atLeastOneChecked && qty != 0) {
                    Martabak martabak = new Martabak();
                    martabak.setName(martabakName);
                    martabak.setTel("kosong");
                    martabak.setToppings(toppingString.replaceAll("[\\[\\]\\(\\)]", ""));
                    martabak.setType(type);
                    martabak.setSubtotal(harga);

                    dbHelper.openDB();

                    long l = dbHelper.addContact(martabak);

                    if (l > -1) {
                        intent = new Intent(CRUDActivity.this, MartabakActivity.class);
                        intent.putExtra("isNew", true);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CRUDActivity.this, "Harap periksa kembali inputan anda.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(scrollView, "Harap periksa kembali inputan anda.", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.RED);
                    snackbar.show();
                }
                break;

            case R.id.btnUpdate:
//                myMartabak.setTel(tel.getText().toString());
                myMartabak.setToppings(toppingString.replaceAll("[\\[\\]\\(\\)]", ""));
                myMartabak.setName(martabakName);
                myMartabak.setType(type);
                myMartabak.setSubtotal(harga);
                dbHelper.updateContact(myMartabak);
                Toast.makeText(CRUDActivity.this, "A contact is updated ",
                        Toast.LENGTH_SHORT).show();
                intent = new Intent(CRUDActivity.this, ListFeatureActivity.class);
                startActivity(intent);
                break;

            case R.id.btnDelete:

                AlertDialog.Builder builder = new AlertDialog.Builder(CRUDActivity.this);
                builder.setTitle("Delete process");
                builder.setMessage("Are you sure to delete : ");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dbHelper.deleteContact(myMartabak.getId());
                        Toast.makeText(CRUDActivity.this, "Pesanan dihapus",
                                Toast.LENGTH_SHORT).show();
                        intent = new Intent(CRUDActivity.this, ListFeatureActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.create().show();
                break;

            case R.id.btnList:
                intent = new Intent(CRUDActivity.this, MartabakActivity.class);
                startActivity(intent);
                break;

            case R.id.btnAPI:
                intent = new Intent(CRUDActivity.this, RetrofitActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void increase(View view) {
        EditText qty = findViewById(R.id.kuantitas);
        Integer before = Integer.valueOf(qty.getText().toString());
        before += 1;
        qty.setText(before.toString());
    }

    public void decrease(View view) {
        EditText qty = findViewById(R.id.kuantitas);
        Integer before = Integer.valueOf(qty.getText().toString());
        if (before != 0) {
            before -= 1;
            qty.setText(before.toString());
        }
    }
}
