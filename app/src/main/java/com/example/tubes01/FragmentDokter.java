package com.example.tubes01;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubes01.databinding.FragmentDokterBinding;

public class FragmentDokter extends Fragment implements View.OnClickListener{
    FragmentDokterBinding binding;
    DokterListAdapter adapter;
    private AddDokterDialogFragment dialogFragment;

    public static FragmentDokter newInstance(String title){
        FragmentDokter fragment = new FragmentDokter();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = FragmentDokterBinding.inflate(inflater,container,false);
        this.adapter = new DokterListAdapter(getActivity());
        binding.lvDokter.setAdapter(adapter);
        binding.btnAddDokter.setOnClickListener(this);

        this.getParentFragmentManager().setFragmentResultListener(
                "DokterInfo", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Dokter dokter = new Dokter(result.getString("dokter"),result.getString("spesialisasi"),result.getString("noHp"));
                        adapter.addLine(dokter);
                    }
                });

        return binding.getRoot();
    }
    @Override
    public void onPause(){
        super.onPause();

    }
    @Override
    public void onResume(){
        super.onResume();

    }
    @Override
    public void onClick(View view) {
        if(view==binding.btnAddDokter){
            showDialog();
        }
    }

    public void showDialog(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AddDokterDialogFragment addDokFrag = AddDokterDialogFragment.newInstance();
        addDokFrag.show(fm, "fragment_add_dokter");
    }
}
