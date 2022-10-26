package com.example.tubes01;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.tubes01.databinding.DialogFragmentAddDokterBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AddDokterDialogFragment extends DialogFragment implements View.OnClickListener{
    DialogFragmentAddDokterBinding binding;

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
            if("".equals(this.binding.etDokterName.getText().toString()) || "".equals(this.binding.etSpesialisasi.getText().toString()) || "".equals(this.binding.etNoHp.getText().toString())){
                Toast.makeText(getActivity(), "Silahkan Masukan Seluruh Data Dokter Yang Diperlukan !",
                        Toast.LENGTH_LONG).show();
            }else{
                Bundle result = new Bundle();
                result.putString("dokter",this.binding.etDokterName.getText().toString());
                result.putString("spesialisasi",this.binding.etSpesialisasi.getText().toString());
                result.putString("noHp",this.binding.etNoHp.getText().toString());
                this.getParentFragmentManager().setFragmentResult("DokterInfo",result);

                File file;
                FileOutputStream fos= null;
                try {
                    file= new File("/data/data/com.example.tubes01/dataDokter.txt");
                    fos= new FileOutputStream(file, true);
                    FileWriter writer= new FileWriter(file, true);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    writer.append(this.binding.etDokterName.getText()+" ");
                    writer.append(this.binding.etSpesialisasi.getText()+"\n");
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(fos!= null){
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                this.binding.etDokterName.setText("");
                this.binding.etSpesialisasi.setText("");
                this.binding.etNoHp.setText("");
                this.dismiss();
            }
        }
    }
}