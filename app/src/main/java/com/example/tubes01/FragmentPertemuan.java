package com.example.tubes01;

import static com.example.tubes01.SQLiteManager.TABLE_NAME;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubes01.databinding.FragmentPertemuanBinding;

import java.util.ArrayList;

public class FragmentPertemuan extends Fragment implements View.OnClickListener {
    FragmentPertemuanBinding binding;
    PertemuanListAdapter adapter;
    ArrayList<Dokter> dokter;
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
        this.adapter = new PertemuanListAdapter(getActivity(), Pertemuan.nonDeletedPertemuan(), Dokter.nonDeletedNotes());
        binding.lvPertemuan.setAdapter(adapter);
        loadFromDBToMemory();
        SQLiteManager db= new SQLiteManager(this.getContext());
        dokter= db.readCourses();

        binding.btnAddPertemuan.setOnClickListener(this);
        this.getParentFragmentManager().setFragmentResultListener(
                "PertemuanInfo", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Pertemuan pertemuan= new Pertemuan(result.getInt("idPertemuan"),
                                result.getString("pasien"), result.getInt("idDokter"),
                                result.getString("keluhan"),result.getString("tanggal"),
                                result.getString("waktu"));


                        adapter.addLine(pertemuan);

                        System.out.println("sesudah dikirim: "+result.getInt("idDokter", 0));
                    }

                });
        return binding.getRoot();
    }


    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populatePertemuanListArray();
    }

    public void onResume()
    {
        super.onResume();
        this.adapter = new PertemuanListAdapter(getActivity(), Pertemuan.nonDeletedPertemuan(), dokter);
        binding.lvPertemuan.setAdapter(adapter);
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
