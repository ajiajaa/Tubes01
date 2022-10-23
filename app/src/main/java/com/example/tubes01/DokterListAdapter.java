package com.example.tubes01;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tubes01.databinding.ItemDokterListBinding;

import java.util.ArrayList;
import java.util.List;

public class DokterListAdapter extends BaseAdapter {
    private Activity activity;
    private List<Dokter> dokterList;
    private ItemDokterListBinding binding;

    public DokterListAdapter(Activity activity){
        this.activity = activity;
        this.dokterList = new ArrayList<Dokter>();
    }

    public void addLine(Dokter food){
        dokterList.add(food);
        notifyDataSetChanged();
    }

    public void removeLine(int i){
        dokterList.remove(i);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dokterList.size();
    }

    @Override
    public Dokter getItem(int i) {
        return dokterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = ItemDokterListBinding.inflate(inflater);
            viewHolder = new ViewHolder(i);
            viewHolder.view = binding.getRoot();
            viewHolder.view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.updateView(this.getItem(i));
        return viewHolder.view;
    }

    private class ViewHolder{
        View view;
        int i;

        public ViewHolder(int i){
            this.view = binding.getRoot();
            this.i = i;
        }

        public  void updateView(Dokter dokter){
            binding.tvDokterName.setText(dokter.getNama());
            binding.tvSpesialisasi.setText((dokter.getSpesialis()));
        }
    }
}
