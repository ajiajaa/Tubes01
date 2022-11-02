package com.example.tubes01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.tubes01.databinding.ActivityAddDokterBinding;
import com.example.tubes01.databinding.ActivityAddPertemuanBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddPertemuan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener{
    ActivityAddPertemuanBinding binding;
    List<String> dokter;
    ArrayList<Dokter> dokter1;
    Spinner spinner;
    int hour = 0;
    int minute = 0;
    DokterListAdapter adapter;
    Pertemuan selectedPertemuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPertemuanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        this.dokter = new ArrayList<String>();
        SQLiteManager db= new SQLiteManager(this);
        checkForEditDokter();
        this.getSupportFragmentManager().setFragmentResultListener(
                "DokterNames", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        dokter.add(result.getString("dokter"));
                    }
                });
        dokter1= db.readCourses();
        this.spinner = (Spinner) this.binding.spinner;
        this.adapter = new DokterListAdapter(this,
                R.layout.item_dokter_list, dokter1);
        spinner.setAdapter(adapter);
        binding.ivCalender.setOnClickListener(this);
        binding.ivWaktu.setOnClickListener(this);
        binding.btnAddDelete.setOnClickListener(this);
        this.binding.btnAddPertemuan.setOnClickListener(this);

    }

    private void checkForEditDokter() {
        Intent previousIntent = getIntent();
        int passedNoteID = previousIntent.getIntExtra(Pertemuan.PERTEMUAN_EDIT_EXTRA, -1);
        selectedPertemuan = Pertemuan.getPertemuanForID(passedNoteID);

        if (selectedPertemuan != null)
        {
            this.binding.tvTanggalOut.setText(selectedPertemuan.getTanggal());
            this.binding.tvWaktuOut.setText(selectedPertemuan.getWaktu());
            this.binding.tvNama.setVisibility(View.INVISIBLE);
            this.binding.spinner.setVisibility(View.INVISIBLE);
            this.binding.tvDokter.setVisibility(View.INVISIBLE);
            this.binding.tvKeluhan.setVisibility(View.INVISIBLE);
            this.binding.etNama.setVisibility(View.INVISIBLE);
            this.binding.etKeluhan.setVisibility(View.INVISIBLE);
            this.binding.btnAddPertemuan.setText("UBAH JANJI PERTEMUAN");
        }
        else
        {
            this.binding.btnAddDelete.setVisibility(View.INVISIBLE);
        }
    }
    public void onResume()
    {
        super.onResume();
        this.adapter = new DokterListAdapter(this,
                R.layout.item_dokter_list, dokter1);
        spinner.setAdapter(adapter);
    }
    private void showDatePicker(){
        DatePickerDialog dpd = new DatePickerDialog(
                AddPertemuan.this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dpd.show();
    }
    private void showTimePicker(){
        TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourSelect, int minuteSelect) {
                hour = hourSelect;
                minute = minuteSelect;
                binding.tvWaktuOut.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };

        TimePickerDialog timePicker = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, timeListener, hour,minute,true);

        timePicker.setTitle("Pilih Waktu Temu");
        timePicker.show();
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        binding.tvTanggalOut.setText(dayOfMonth+"/"+(month+1)+"/"+year);
    }
    public void savePertemuan(){
        Dokter idDokter= (Dokter) spinner.getSelectedItem();
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String nama = String.valueOf(this.binding.etNama.getText());
        int dokter = Integer.valueOf(idDokter.getId());
        String keluhan = String.valueOf(this.binding.etKeluhan.getText());
        String tanggal = String.valueOf(this.binding.tvTanggalOut.getText());
        String waktu = String.valueOf(this.binding.tvWaktuOut.getText());

        if(selectedPertemuan == null)
        {
            int id = Pertemuan.pertemuanArrayList.size();
            Pertemuan newPertemuan = new Pertemuan(id, nama, dokter, keluhan, tanggal, waktu);
            Pertemuan.pertemuanArrayList.add(newPertemuan);
            sqLiteManager.addPertemuanToDatabase(newPertemuan);
        }
        else
        {
            selectedPertemuan.setTanggal(tanggal);
            selectedPertemuan.setWaktu(waktu);
            sqLiteManager.updatePertemuanInDB(selectedPertemuan);
        }

        finish();
    }
    public void deletePertemuan()
    {
        selectedPertemuan.setDeleted1(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updatePertemuanInDB(selectedPertemuan);
        finish();
    }


    @Override
    public void onClick(View view) {
        if(view==binding.ivCalender){
            showDatePicker();
        }else if(view==binding.ivWaktu){
            showTimePicker();
        }else if(view==binding.btnAddPertemuan){
            Bundle result = new Bundle();
            Dokter idDokter= (Dokter) spinner.getSelectedItem();

            result.putInt("idPertemuan", Pertemuan.pertemuanArrayList.size());
            result.putString("pasien",this.binding.etNama.getText().toString());
            result.putInt("idDokter", idDokter.getId());
            result.putString("keluhan",this.binding.etKeluhan.getText().toString());
            result.putString("tanggal",this.binding.tvTanggalOut.getText().toString());
            result.putString("waktu",this.binding.tvWaktuOut.getText().toString());
            savePertemuan();
            System.out.println("dokter sebelum dikirim: "+idDokter.getId());
            this.getSupportFragmentManager().setFragmentResult("PertemuanInfo",result);

        }else if(view== binding.btnAddDelete){
            deletePertemuan();
        }
    }
}