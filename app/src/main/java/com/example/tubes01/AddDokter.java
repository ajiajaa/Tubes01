package com.example.tubes01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.tubes01.databinding.ActivityAddDokterBinding;

import java.util.Date;

public class AddDokter extends AppCompatActivity implements View.OnClickListener{
    private Dokter selectedDokter;
    ActivityAddDokterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDokterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        checkForEditDokter();

        this.binding.btnAddDokter.setOnClickListener(this);
        this.binding.btnAddDelete.setOnClickListener(this);
        this.binding.btnTelepon.setOnClickListener(this);
    }

    private void checkForEditDokter()
    {
        Intent previousIntent = getIntent();
        int passedNoteID = previousIntent.getIntExtra(Dokter.DOKTER_EDIT_EXTRA, -1);
        selectedDokter = Dokter.getDokterForID(passedNoteID);

        if (selectedDokter != null)
        {
            this.binding.etDokterName.setText(selectedDokter.getNama());
            this.binding.etSpesialisasi.setText(selectedDokter.getSpesialis());
            this.binding.etNoHp.setText(selectedDokter.getNoHp());
        }
        else
        {
            this.binding.btnAddDelete.setVisibility(View.INVISIBLE);
            this.binding.btnTelepon.setVisibility(View.INVISIBLE);
        }
    }

    public void saveDokter(){
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String nama = String.valueOf(this.binding.etDokterName.getText());
        String spesialisasi = String.valueOf(this.binding.etSpesialisasi.getText());
        String noHP = String.valueOf(this.binding.etNoHp.getText());

        if(selectedDokter == null)
        {
            int id = Dokter.dokterArrayList.size();
            Dokter newDokter = new Dokter(id, nama, spesialisasi,noHP);
            Dokter.dokterArrayList.add(newDokter);
            sqLiteManager.addDokterToDatabase(newDokter);
        }
        else
        {
            selectedDokter.setNama(nama);
            selectedDokter.setSpesialis(spesialisasi);
            selectedDokter.setNoHp(noHP);
            sqLiteManager.updateDokterInDB(selectedDokter);
        }

        finish();
    }

    public void deleteDokter()
    {
        selectedDokter.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateDokterInDB(selectedDokter);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view== this.binding.btnAddDokter){
            saveDokter();
        }
        if(view== this.binding.btnAddDelete){
            deleteDokter();
        }
        if(view== this.binding.btnTelepon){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + selectedDokter.getNoHp()));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}