package com.example.tubes01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tubes01.databinding.FragmentDrawerBinding;
import com.example.tubes01.databinding.FragmentHomeBinding;

public class FragmentHome extends Fragment implements View.OnClickListener{
    FragmentHomeBinding binding;

    public static FragmentHome newInstance(String title){
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = FragmentHomeBinding.inflate(inflater,container,false);

        binding.btnBuatPertemuan.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view==binding.btnBuatPertemuan){
            Bundle result = new Bundle();
            result.putInt("page",3);
            this.getParentFragmentManager().setFragmentResult("changePage",result);
        }
    }
}
