package com.example.tubes01;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.tubes01.databinding.ItemListPertemuanBinding;

import java.util.ArrayList;
import java.util.List;

public class PertemuanListAdapter extends BaseAdapter {
    private Activity activity;
    public List<Pertemuan> pertemuanList;
    public List<Dokter> dokter;
    private ItemListPertemuanBinding binding;

    public PertemuanListAdapter(Activity activity){
        this.activity = activity;
        this.pertemuanList = new ArrayList<Pertemuan>();
    }
    public PertemuanListAdapter(Activity activity, List<Pertemuan> pertemuan, List<Dokter> dokter){
        super();
        this.activity = activity;
        this.dokter= dokter;
        this.pertemuanList = pertemuan;
    }

    public void addLine(Pertemuan pertemuan){
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

    private class ViewHolder implements View.OnClickListener{
        Dokter selectedDokter;
        public  void updateView(Pertemuan pertemuan){
            selectedDokter= dokter.get(pertemuan.getIdDokter());
            System.out.println("nama pasien"+pertemuan.getPasien());
            binding.tvPasienOut.setText(pertemuan.getPasien());
            binding.tvDokterOut.setText(selectedDokter.getNama());
            binding.tvKendalaOut.setText(pertemuan.getKeluhan());
            binding.tvWaktuOut.setText(pertemuan.getTanggal()+" ("+pertemuan.getWaktu()+")");
            binding.ivTelepon.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(view== binding.ivTelepon){
                Context context = view.getContext();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + selectedDokter.getNoHp()));
                context.startActivity(intent);
            }
        }
    }
}
