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
    public List<Dokter> dokterList;
    private ItemDokterListBinding binding;

    public DokterListAdapter(Activity activity, List<Dokter> dokter){
        this.activity = activity;
        this.dokterList = dokter;
    }

    public void addLine(Dokter food){
        dokterList.add(food);
        notifyDataSetChanged();
    }

    public void removeLine(int i){
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
            binding = ItemDokterListBinding.inflate(LayoutInflater.from(parent.getContext()));
            viewHolder = new ViewHolder();
            convertView = binding.getRoot();
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.updateView(this.getItem(i));
        return convertView;
    }

    private class ViewHolder{

        public  void updateView(Dokter dokter){
            binding.tvDokterOut.setText("Dr."+dokter.getNama());
            binding.tvSpesialisasiOut.setText((dokter.getSpesialis()));
            binding.tvNoHpOut.setText(dokter.getNoHp());
        }

    }
}
