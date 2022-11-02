package com.example.tubes01;

import static com.example.tubes01.SQLiteManager.TABLE_NAME;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
        SQLiteManager db= new SQLiteManager(this.getContext());
        dokter= db.readCourses();
        loadFromDBToMemory();
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
        setOnClickListener();
        return binding.getRoot();
    }


    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populatePertemuanListArray();
    }
    @Override
    public void onResume()
    {
        super.onResume();
//        adapter.notifyDataSetChanged();
        getParentFragmentManager().beginTransaction().attach(this).commit ();
        this.adapter = new PertemuanListAdapter(getActivity(), Pertemuan.nonDeletedPertemuan(), Dokter.nonDeletedNotes());
        binding.lvPertemuan.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        if(view==binding.btnAddPertemuan){
//            Bundle result= new Bundle();
//            result.putInt(Pertemuan.PERTEMUAN_EDIT_EXTRA, -1);
//            result.putBoolean("isSelected",false);
//            getParentFragmentManager().setFragmentResult("idPertemuan", result);
//            showDialog();
            Intent newPertemuanIntent = new Intent(getActivity(), AddPertemuan.class);
            startActivity(newPertemuanIntent);
        }
    }

    public void showDialog(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AddPertemuanDialogFragment addPertFrag = AddPertemuanDialogFragment.newInstance();
        addPertFrag.show(fm, "fragment_add_pertemuan");
    }
    private void setOnClickListener() {
        this.binding.lvPertemuan.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
//                Bundle result= new Bundle();
//                Pertemuan selectedPertemuan = (Pertemuan) binding.lvPertemuan.getItemAtPosition(position);
//                result.putInt(Pertemuan.PERTEMUAN_EDIT_EXTRA, selectedPertemuan.getId());
//                result.putBoolean("isSelected", true);
//                getParentFragmentManager().setFragmentResult("idPertemuan", result);
//                showDialog();
//                result.putInt(Dokter.DOKTER_EDIT_EXTRA, -1);

//                getParentFragmentManager().setFragmentResult("idDokter", result);
                Pertemuan selectedPertemuan = (Pertemuan) binding.lvPertemuan.getItemAtPosition(position);
                Intent editPertemuanIntent = new Intent(getActivity(), AddPertemuan.class);
                editPertemuanIntent.putExtra(Pertemuan.PERTEMUAN_EDIT_EXTRA, selectedPertemuan.getId());
                startActivity(editPertemuanIntent);

            }
        });
    }

}
