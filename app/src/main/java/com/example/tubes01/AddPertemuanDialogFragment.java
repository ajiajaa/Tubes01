package com.example.tubes01;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubes01.databinding.DialogFragmentAddPertemuanBinding;
import com.example.tubes01.databinding.FragmentPertemuanBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddPertemuanDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    DialogFragmentAddPertemuanBinding binding;
    List<String> dokter;
    int hour = 0;
    int minute = 0;

    public static AddPertemuanDialogFragment newInstance(){
        AddPertemuanDialogFragment fragment = new AddPertemuanDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = DialogFragmentAddPertemuanBinding.inflate(inflater,container,false);
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

        binding.dropDokter.setAdapter(new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_dropdown_item,dokter));

        return binding.getRoot();
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
            this.dismiss();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        binding.tvTanggalOut.setText(dayOfMonth+"/"+(month+1)+"/"+year);
    }
}
