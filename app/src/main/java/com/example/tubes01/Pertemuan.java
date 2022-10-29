package com.example.tubes01;

import java.util.ArrayList;
import java.util.Date;

public class Pertemuan {
    public static ArrayList<Pertemuan> pertemuanArrayList = new ArrayList<>();
    public static String PERTEMUAN_EDIT_EXTRA =  "pertemuanEdit";



    int idPertemuan;
    int idDokter;
    String pasien;
    String keluhan;
    String tanggal;
    String waktu;
    Date deleted1;

    public Pertemuan(int idPertemuan, String pasien, int idDokter, String keluhan, String tanggal, String waktu, Date deleted1){
        this.idPertemuan= idPertemuan;
        this.pasien = pasien;
        this.idDokter= idDokter;
        this.keluhan = keluhan;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.deleted1= deleted1;
    }

    public Pertemuan(int idPertemuan, String pasien,int idDokter, String keluhan, String tanggal, String waktu){
        this.idPertemuan= idPertemuan;
        this.pasien = pasien;
        this.idDokter= idDokter;
        this.keluhan = keluhan;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.deleted1= null;
    }

    public Pertemuan getPertemuanForID(int passedPertemuanID)
    {
        for (Pertemuan pertemuan : pertemuanArrayList)
        {
            if(pertemuan.getId() == passedPertemuanID)
                return pertemuan;
        }

        return null;
    }
    public static ArrayList<Pertemuan> nonDeletedPertemuan()
    {
        ArrayList<Pertemuan> nonDeleted = new ArrayList<>();
        for(Pertemuan pertemuan : pertemuanArrayList)
        {
            if(pertemuan.getDeleted1() == null)
                nonDeleted.add(pertemuan);
        }

        return nonDeleted;
    }
    public int getId() {
        return this.idPertemuan;
    }

    public void setId(int idPertemuan) {
        this.idPertemuan= idPertemuan;
    }
    public int getIdDokter() {
        return this.idDokter;
    }

    public void setIdDokter(int idDokter) {
        this.idPertemuan= idDokter;
    }

    public String getPasien() {
        return pasien;
    }

    public void setPasien(String pasien) {
        this.pasien = pasien;
    }

    public String getKeluhan() {
        return this.keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu(){
        return this.waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
    public Date getDeleted1()
    {
        return deleted1;
    }

    public void setDeleted1(Date deleted1)
    {
        this.deleted1= deleted1;
    }
}
