package com.example.tubes01;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubes01.databinding.FragmentHomeBinding;
import com.example.tubes01.databinding.FragmentPertemuanBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FragmentPertemuan extends Fragment implements View.OnClickListener {
    FragmentPertemuanBinding binding;
    PertemuanListAdapter adapter;

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
        this.adapter = new PertemuanListAdapter(getActivity());
        binding.lvPertemuan.setAdapter(adapter);

        binding.btnAddPertemuan.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view==binding.btnAddPertemuan){
            showDialog();
        }
    }

    public void showDialog(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AddPertemuanDialogFragment addPertFrag = AddPertemuanDialogFragment.newInstance();
        addPertFrag.show(fm, "fragment_add_pertemuan");
    }
}
