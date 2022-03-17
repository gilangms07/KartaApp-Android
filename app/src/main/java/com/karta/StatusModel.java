package com.karta;

public class StatusModel {
    String rtrw;
    String Nama;
    String Status;

    public StatusModel(String rtrw, String nama, String status) {
        this.rtrw = rtrw;
        Nama = nama;
        Status = status;
    }

    public String getRtrw() {
        return rtrw;
    }

    public String getNama() {
        return Nama;
    }

    public String getStatus() {
        return Status;
    }
}
