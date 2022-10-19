package com.example.tubes01;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tubes01.databinding.ItemListStringBinding;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    ItemListStringBinding binding;
    protected List<String> list;
    private FragmentDrawer activity;
    public ListAdapter(FragmentDrawer activity){
        this.activity= activity;
        this.list= new ArrayList<>();
    }
    public void initList(ArrayList<String> item){
        for(String items: item){
            this.list.add(items);
        }
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view== null){
            binding= ItemListStringBinding.inflate(this.activity.getLayoutInflater());
            view= binding.getRoot();
            vh= new ViewHolder(binding, this, i);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        this.binding.tvTitle.setText(this.getItem(i).toString());
        return view;
    }
    private class ViewHolder implements View.OnClickListener{
        ItemListStringBinding binding;
        ListAdapter adapter;
        int i;
        public ViewHolder(ItemListStringBinding binding, ListAdapter adapter, int i){
            this.binding= binding;
            this.adapter= adapter;
            this.i= i;
            this.binding.tvTitle.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(view== this.binding.tvTitle){
                Log.d("debug", "clicked!");
                if(this.binding.tvTitle.getText().equals("Home")){
                    activity.changePage(1);
                    activity.close();
                }else if(this.binding.tvTitle.getText().equals("Dokter")){
                    activity.changePage(2);
                    activity.close();
                }else if(this.binding.tvTitle.getText().equals("Pertemuan")){
                    activity.changePage(3);
                    activity.close();
                }else{
                    activity.closeApplication();
                }
            }
        }
    }
}
