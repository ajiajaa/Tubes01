package com.example.tubes01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.Date;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubes01.databinding.FragmentDokterBinding;

public class FragmentDokter extends Fragment implements View.OnClickListener{
    FragmentDokterBinding binding;
    DokterListAdapter adapter;

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
        this.adapter = new DokterListAdapter(getActivity(), Dokter.nonDeletedNotes());
        binding.lvDokter.setAdapter(adapter);
        loadFromDBToMemory();
        binding.btnAddDokter.setOnClickListener(this);
        setOnClickListener();

        this.getParentFragmentManager().setFragmentResultListener(
                "DokterInfo", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Dokter dokter = new Dokter(result.getInt("idDokter"),result.getString("dokter"),result.getString("spesialisasi"),result.getString("noHp"));
                        adapter.addLine(dokter);
//                        sendDokterNames(result.getString("dokter"));
                    }

                });

        return binding.getRoot();
    }

    private void setOnClickListener() {
        this.binding.lvDokter.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
//                Bundle result= new Bundle();
//                result.putInt(Dokter.DOKTER_EDIT_EXTRA, selectedDokter.getId());
//                getParentFragmentManager().setFragmentResult("idDokter", result);
//                showDialog();
//                result.putInt(Dokter.DOKTER_EDIT_EXTRA, -1);
//                getParentFragmentManager().setFragmentResult("idDokter", result);
                Dokter selectedDokter = (Dokter) binding.lvDokter.getItemAtPosition(position);
                Intent editDokterIntent = new Intent(getActivity(), AddDokter.class);
                editDokterIntent.putExtra(Dokter.DOKTER_EDIT_EXTRA, selectedDokter.getId());
                startActivity(editDokterIntent);

            }
        });
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateDokterListArray();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.adapter = new DokterListAdapter(getActivity(), Dokter.nonDeletedNotes());
        binding.lvDokter.setAdapter(adapter);
    }
    @Override
    public void onClick(View view) {
        if(view==binding.btnAddDokter){
            Intent newDokterIntent = new Intent(getActivity(), AddDokter.class);
            startActivity(newDokterIntent);
//            showDialog();
        }
    }

//    public void sendDokterNames(String dokter){
//        Bundle result = new Bundle();
//        result.putString("dokter",dokter);
//        this.getParentFragmentManager().setFragmentResult("DokterNames",result);
//    }

    public void showDialog(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        AddDokterDialogFragment addDokFrag = AddDokterDialogFragment.newInstance();
        addDokFrag.show(fm, "fragment_add_dokter");
    }
}
