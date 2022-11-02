package com.example.tubes01;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubes01.databinding.DialogFragmentAddPertemuanBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddPertemuanDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    DialogFragmentAddPertemuanBinding binding;
    List<String> dokter;
    ArrayList<Dokter> dokter1;
    Spinner spinner;
    int hour = 0;
    int minute = 0;
    DokterListAdapter adapter;
    static Pertemuan selectedPertemuan;
    static boolean isSelected;
    public static AddPertemuanDialogFragment newInstance(){

        AddPertemuanDialogFragment fragment = new AddPertemuanDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = DialogFragmentAddPertemuanBinding.inflate(inflater,container,false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        this.dokter = new ArrayList<String>();
        binding.ivCalender.setOnClickListener(this);
        binding.ivWaktu.setOnClickListener(this);

        this.getParentFragmentManager().setFragmentResultListener(
                "DokterNames", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        dokter.add(result.getString("dokter"));
                    }
                });
        SQLiteManager db= new SQLiteManager(this.getContext());
        dokter1= db.readCourses();
        this.spinner = (Spinner) this.binding.spinner;
        this.adapter = new DokterListAdapter(this.getActivity(),
                R.layout.item_dokter_list, dokter1);
        spinner.setAdapter(adapter);
        this.binding.btnAddPertemuan.setOnClickListener(this);
        isSelected= getArguments().getBoolean("isSelected");
        System.out.println("boolean sebelum di cek"+isSelected);

        checkForEditDokter();
        return binding.getRoot();
    }
    private void checkForEditDokter()
    {
        Pertemuan selectedPertemuan;
//        Intent previousIntent = getIntent();
//        int passedNoteID = previousIntent.getIntExtra(Dokter.DOKTER_EDIT_EXTRA, -1);
        int value= getArguments().getInt(Pertemuan.PERTEMUAN_EDIT_EXTRA);
        isSelected= getArguments().getBoolean("isSelected");
        selectedPertemuan=Pertemuan.getPertemuanForID(value);
        System.out.println("selected pertemuan diterima"+selectedPertemuan);
        System.out.println("boolean pas di cek"+isSelected);

        if (selectedPertemuan != null && isSelected)
        {
            this.binding.tvNama.setText(selectedPertemuan.getPasien());
            this.binding.tvDokter.setText(Dokter.getDokterForID(selectedPertemuan.getIdDokter()).getNama());
            this.binding.etKeluhan.setText(selectedPertemuan.getKeluhan());
            this.spinner.setSelection(selectedPertemuan.getIdDokter());
            this.binding.tvTanggalOut.setText(selectedPertemuan.getTanggal());
            this.binding.tvWaktuOut.setText((selectedPertemuan.getWaktu()));
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
//            this.binding.btnAddDelete.setVisibility(View.INVISIBLE);

        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        this.adapter = new DokterListAdapter(this.getActivity(),
                R.layout.item_dokter_list, dokter1);
        spinner.setAdapter(adapter);
    }
    private void showDatePicker(){
        DatePickerDialog dpd = new DatePickerDialog(
                this.getContext(),
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

        TimePickerDialog timePicker = new TimePickerDialog(this.getContext(), AlertDialog.THEME_HOLO_DARK, timeListener, hour,minute,true);

        timePicker.setTitle("Pilih Waktu Temu");
        timePicker.show();
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
            this.getParentFragmentManager().setFragmentResult("PertemuanInfo",result);

            this.dismiss();
        }
    }
    public void savePertemuan(){
        System.out.println("selected pertemuan saat editsave "+selectedPertemuan);
        if(selectedPertemuan == null)
        {
            Dokter idDokter= (Dokter) spinner.getSelectedItem();
            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this.getActivity());
            String nama = String.valueOf(this.binding.etNama.getText());
            int dokter = Integer.valueOf(idDokter.getId());
            String keluhan = String.valueOf(this.binding.etKeluhan.getText());
            String tanggal = String.valueOf(this.binding.tvTanggalOut.getText());
            String waktu = String.valueOf(this.binding.tvWaktuOut.getText());

            int id = Pertemuan.pertemuanArrayList.size();
            Pertemuan newPertemuan = new Pertemuan(id, nama, dokter, keluhan, tanggal, waktu);
            Pertemuan.pertemuanArrayList.add(newPertemuan);
            sqLiteManager.addPertemuanToDatabase(newPertemuan);
        }
        else
        {
            String tanggal= String.valueOf(this.binding.tvTanggalOut.getText());
            String waktu= String.valueOf(this.binding.tvWaktuOut.getText());
            selectedPertemuan.setTanggal(tanggal);
            selectedPertemuan.setWaktu(waktu);
        }
        getActivity().getFragmentManager().popBackStack();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        binding.tvTanggalOut.setText(dayOfMonth+"/"+(month+1)+"/"+year);
    }
}
