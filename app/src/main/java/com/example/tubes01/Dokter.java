package com.example.tubes01;

import java.util.ArrayList;
import java.util.Date;

public class Dokter {
    public static ArrayList<Dokter> dokterArrayList = new ArrayList<>();
    public static String DOKTER_EDIT_EXTRA =  "dokterEdit";

    int idDokter;
    String nama;
    String spesialis;
    String noHp;
    Date deleted;

    public Dokter(int idDokter, String nama, String spesialis, String noHp){
        this.idDokter= idDokter;
        this.nama = nama;
        this.spesialis = spesialis;
        this.noHp = noHp;
        this.deleted= null;
    }
    public Dokter(int idDokter, String nama, String spesialis, String noHp, Date deleted){
        this.idDokter= idDokter;
        this.nama = nama;
        this.spesialis = spesialis;
        this.noHp = noHp;
        this.deleted= deleted;
    }

    public static Dokter getDokterForID(int passedDokterID)
    {
        for (Dokter dokter : dokterArrayList)
        {
            if(dokter.getId() == passedDokterID)
                return dokter;
        }

        return null;
    }
    public static ArrayList<Dokter> nonDeletedNotes()
    {
        ArrayList<Dokter> nonDeleted = new ArrayList<>();
        for(Dokter dokter : dokterArrayList)
        {
            if(dokter.getDeleted() == null)
                nonDeleted.add(dokter);
        }

        return nonDeleted;
    }
    public int getId() {
        return this.idDokter;
    }

    public void setIdDokter(int idDokter) {
        this.idDokter = idDokter;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSpesialis() {
        return this.spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    public String getNoHp(){
        return this.noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
    public Date getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Date deleted)
    {
        this.deleted = deleted;
    }
}
