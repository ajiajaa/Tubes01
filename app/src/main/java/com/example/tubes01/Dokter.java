package com.example.tubes01;

public class Dokter {
    String nama;
    String spesialis;
    String noHp;

    public Dokter(String nama, String spesialis, String noHp){
        this.nama = nama;
        this.spesialis = spesialis;
        this.noHp = noHp;
    }

    public String getNama() {
        return this.nama;
    }

    public String getSpesialis() {
        return this.spesialis;
    }

    public String getNoHp(){
        return this.noHp;
    }
}
