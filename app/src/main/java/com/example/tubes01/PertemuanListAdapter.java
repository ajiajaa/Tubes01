package com.example.tubes01;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tubes01.databinding.ItemDokterListBinding;
import com.example.tubes01.databinding.ItemListPertemuanBinding;

import java.util.ArrayList;
import java.util.List;

public class PertemuanListAdapter extends BaseAdapter {
    private Activity activity;
    public List<Dokter> dokterList;
    private ItemListPertemuanBinding binding;

    public PertemuanListAdapter(Activity activity){
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

        public  void updateView(Dokter dokter){
        }

    }
}
