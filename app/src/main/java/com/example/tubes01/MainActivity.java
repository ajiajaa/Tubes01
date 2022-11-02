package com.example.tubes01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.tubes01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    FragmentHome fragHome;
    FragmentPertemuan fragPertemuan;
    FragmentDokter fragDokter;
    FragmentDrawer drawer;
    DrawerLayout dl;
    FragmentManager fm;
    Toolbar toolbar;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        fragHome = FragmentHome.newInstance("fragment home");
        fragPertemuan = FragmentPertemuan.newInstance("fragment pertemuan");
        fragDokter = FragmentDokter.newInstance("fragment dokter");

        this.toolbar = binding.toolbar;
        this.dl = binding.drawerLayout;
        this.setSupportActionBar(toolbar);

        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this,dl,toolbar,R.string.openDrawer,R.string.closeDrawer);
        this.dl.addDrawerListener(abdt);
        abdt.syncState();

        this.fm = this.getSupportFragmentManager();
        changePage(1);

        this.getSupportFragmentManager().setFragmentResultListener(
                "changePage", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        int page = result.getInt("page");
                        changePage(page);
                    }
                });
    }
    public void changePage(int page){
        FragmentTransaction ft = this.fm.beginTransaction();
        if(page==1){
            System.out.println("Change p1");
            if(this.fragHome.isAdded()){
                ft.show(this.fragHome);
            }else{
                ft.add(R.id.fragment_container,this.fragHome);
            }
            if(this.fragDokter.isAdded() || this.fragPertemuan.isAdded()){
                ft.hide(this.fragDokter);
            }
            if(this.fragPertemuan.isAdded()){
                ft.hide(this.fragPertemuan);
            }
        }else if(page==2){
            System.out.println("Change p2");
            if(this.fragDokter.isAdded()){
                ft.show(this.fragDokter);
            }else{
                ft.add(R.id.fragment_container,this.fragDokter);
            }
            if(this.fragPertemuan.isAdded()){
                ft.hide(this.fragPertemuan);
            }
            if(this.fragHome.isAdded()){
                ft.hide(this.fragHome);
            }
        }else if(page==3){
            System.out.println("Change p3");
            if(this.fragPertemuan.isAdded()){
                ft.show(this.fragPertemuan);

            }else{
                ft.add(R.id.fragment_container,this.fragPertemuan);
            }
            if(this.fragDokter.isAdded()){
                ft.hide(this.fragDokter);
            }
            if(this.fragHome.isAdded()){
                ft.hide(this.fragHome);
            }
        }
        ft.commit();
    }
    public void closeApplication(){
        this.moveTaskToBack(true);
        this.finish();
    }
    public void close(){
        this.binding.drawerLayout.closeDrawers();
    }
}