package com.example.tubes01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubes01.databinding.FragmentHomeBinding;
import com.example.tubes01.databinding.FragmentPertemuanBinding;

public class FragmentPertemuan extends Fragment {
    FragmentPertemuanBinding binding;

    public static FragmentPertemuan newInstance(String title){
        FragmentPertemuan fragment = new FragmentPertemuan();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = FragmentPertemuanBinding.inflate(inflater,container,false);



        return binding.getRoot();
    }
}
