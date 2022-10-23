package com.example.tubes01;

public class Dokter {
    String nama;
    String spesialis;

    public Dokter(String nama, String spesialis){
        this.nama = nama;
        this.spesialis = spesialis;
    }

    public String getNama() {
        return nama;
    }

    public String getSpesialis() {
        return spesialis;
    }
}
