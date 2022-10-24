package com.example.tubes01;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

import com.example.tubes01.databinding.DialogFragmentAddDokterBinding;
import com.example.tubes01.databinding.FragmentDokterBinding;

public class AddDokterDialogFragment extends DialogFragment implements View.OnClickListener{
    DialogFragmentAddDokterBinding binding;
    ListView lvDokter;

    public static AddDokterDialogFragment newInstance(){
        AddDokterDialogFragment fragment = new AddDokterDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = DialogFragmentAddDokterBinding.inflate(inflater,container,false);
        this.binding.btnAddDokter.setOnClickListener(this);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if(view==this.binding.btnAddDokter){
            Bundle result = new Bundle();
            result.putString("dokter",this.binding.etDokterName.getText().toString());
            result.putString("spesialisasi",this.binding.etSpesialisasi.getText().toString());
            this.getParentFragmentManager().setFragmentResult("DokterInfo",result);
            this.binding.etDokterName.setText("");
            this.binding.etSpesialisasi.setText("");
            this.dismiss();
        }
    }
}
