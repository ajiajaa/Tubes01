package com.example.tubes01;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tubes01.databinding.ItemListPertemuanBinding;

import java.util.ArrayList;
import java.util.List;

public class PertemuanListAdapter extends BaseAdapter {
    private Activity activity;
    public List<Pertemuan> pertemuanList;
    private ItemListPertemuanBinding binding;

    public PertemuanListAdapter(Activity activity){
        this.activity = activity;
        this.pertemuanList = new ArrayList<Pertemuan>();
    }

    public void addLine(String nama, String dokter, String keluhan, String tanggal, String waktu){
        Pertemuan pertemuan  = new Pertemuan(nama,dokter,keluhan,tanggal,waktu);
        pertemuanList.add(pertemuan);
        notifyDataSetChanged();
    }

    public void removeLine(int i){
        pertemuanList.remove(i);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pertemuanList.size();
    }

    @Override
    public Pertemuan getItem(int i) {
        return pertemuanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        PertemuanListAdapter.ViewHolder viewHolder;

        if(convertView==null){
            binding = ItemListPertemuanBinding.inflate(LayoutInflater.from(parent.getContext()));
            viewHolder = new PertemuanListAdapter.ViewHolder();
            convertView = binding.getRoot();
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (PertemuanListAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.updateView(this.getItem(i));
        return convertView;
    }

    private class ViewHolder{

        public  void updateView(Pertemuan pertemuan){
            binding.tvPasienOut.setText(pertemuan.getPasien());
            binding.tvKendalaOut.setText(pertemuan.getKeluhan());
            binding.tvWaktuOut.setText(pertemuan.getTanggal()+" ("+pertemuan.getWaktu()+")");
        }

    }
}
