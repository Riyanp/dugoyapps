package com.example.dugoy.dugoyapps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dugoy.dugoyapps.R;
import com.example.dugoy.dugoyapps.model.Martabak;

import java.util.List;

public class MartabakAdapter extends RecyclerView.Adapter<MartabakAdapter.MyViewHolder> {

    private Context context;
    private List<Martabak> martabakList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvId, harga;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvId = view.findViewById(R.id.tv_id);
            harga = view.findViewById(R.id.harga);
        }
    }


    public MartabakAdapter(Context context, List<Martabak> notesList) {
        this.context = context;
        this.martabakList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_martabak, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Martabak martabak = martabakList.get(position);

        holder.tvId.setText(martabak.getName());
        holder.tvName.setText(martabak.getToppings());
        holder.harga.setText(martabak.getSubtotal().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, martabak.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return martabakList.size();
    }
}
