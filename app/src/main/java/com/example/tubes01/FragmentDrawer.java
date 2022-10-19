package com.example.tubes01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubes01.databinding.FragmentDrawerBinding;

import java.util.ArrayList;

public class FragmentDrawer extends Fragment {
    FragmentDrawerBinding binding;
    protected ListAdapter adapter;
    ArrayList<String> items= new ArrayList<String>();
    public FragmentDrawer(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding= FragmentDrawerBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        this.items.add("Home");
        this.items.add("Dokter");
        this.items.add("Pertemuan");
        this.items.add("Exit");
        this.adapter= new ListAdapter(this);
        this.adapter.initList(items);
        this.binding.lvItems.setAdapter(this.adapter);

        return view;
    }
    public void changePage(int i){
        ((MainActivity)getActivity()).changePage(i);
    }
    public void closeApplication(){
        ((MainActivity)getActivity()).closeApplication();
    }
    public void close(){
        ((MainActivity)getActivity()).close();
    }
}
