package com.example.tubes01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tubes01.databinding.FragmentDokterBinding;
import com.example.tubes01.databinding.FragmentPertemuanBinding;

public class FragmentDokter extends Fragment {
    public static FragmentDokter newInstance(String title){
        FragmentDokter fragment = new FragmentDokter();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = FragmentDokterBinding.inflate(inflater,container,false).getRoot();
        return view;
    }
}
