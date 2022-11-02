package com.example.tubes01;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tubes01.databinding.DialogFragmentAddDokterBinding;

import java.util.Date;

public class AddDokterDialogFragment extends DialogFragment implements View.OnClickListener{
    DialogFragmentAddDokterBinding binding;
    private Dokter selectedDokter;
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
//        this.binding.btnAddDelete.setOnClickListener(this);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        checkForEditDokter();
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view==this.binding.btnAddDokter){
            if("".equals(this.binding.etDokterName.getText().toString()) || "".equals(this.binding.etSpesialisasi.getText().toString()) || "".equals(this.binding.etNoHp.getText().toString())){
                Toast.makeText(getActivity(), "Silahkan Masukan Seluruh Data Dokter Yang Diperlukan !",
                        Toast.LENGTH_LONG).show();
            }else{

//
//                File file;
//                FileOutputStream fos= null;
//                try {
//                    file= new File("/data/data/com.example.tubes01/dataDokter.txt");
//                    fos= new FileOutputStream(file, true);
//                    FileWriter writer= new FileWriter(file, true);
//                    if(!file.exists()){
//                        file.createNewFile();
//                    }
//                    writer.append(this.binding.etDokterName.getText()+" ");
//                    writer.append(this.binding.etSpesialisasi.getText()+"\n");
//                    writer.flush();
//                    writer.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        if(fos!= null){
//                            fos.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
                saveDokter();
                this.dismiss();
            }
        }
    }
    private void checkForEditDokter()
    {
//        Intent previousIntent = getIntent();
//        int passedNoteID = previousIntent.getIntExtra(Dokter.DOKTER_EDIT_EXTRA, -1);
        int value= getArguments().getInt(Dokter.DOKTER_EDIT_EXTRA);
        selectedDokter=Dokter.getDokterForID(value);
        System.out.println(value);

        if (selectedDokter != null)
        {
            this.binding.etDokterName.setText(selectedDokter.getNama());
            this.binding.etSpesialisasi.setText(selectedDokter.getSpesialis());
            this.binding.etNoHp.setText(selectedDokter.getNoHp());
        }
    }


    public void saveDokter(){
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this.getActivity());
        String nama = String.valueOf(this.binding.etDokterName.getText());
        String spesialisasi = String.valueOf(this.binding.etSpesialisasi.getText());
        String noHP = String.valueOf(this.binding.etNoHp.getText());

        if(selectedDokter == null)
        {
            int id = Dokter.dokterArrayList.size();
            Dokter newDokter = new Dokter(id, nama, spesialisasi,noHP);
            Dokter.dokterArrayList.add(newDokter);
            sqLiteManager.addDokterToDatabase(newDokter);

            Bundle result= new Bundle();
            result.putString("dokter",this.binding.etDokterName.getText().toString());
            result.putString("spesialisasi",this.binding.etSpesialisasi.getText().toString());
            result.putString("noHp",this.binding.etNoHp.getText().toString());
            this.getParentFragmentManager().setFragmentResult("DokterInfo",result);
        }
        else
        {
            selectedDokter.setNama(nama);
            selectedDokter.setSpesialis(spesialisasi);
            selectedDokter.setNoHp(noHP);
            sqLiteManager.updateDokterInDB(selectedDokter);
        }
        getActivity().getFragmentManager().popBackStack();
    }

    public void deleteDokter()
    {
        selectedDokter.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this.getActivity());
        sqLiteManager.updateDokterInDB(selectedDokter);
        getActivity().getFragmentManager().popBackStack();
    }
}