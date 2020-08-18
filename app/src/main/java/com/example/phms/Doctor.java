package com.example.phms;

public class Doctor {

    private String name, phone, checkupInfo;

    public Doctor() {}

    public Doctor(String name, String phone, String password, String checkInfo) {
        this.name = name;
        this.phone = phone;
        this.checkupInfo = checkInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCheckInfo() {
        return checkupInfo;
    }

    public void setCheckInfo(String CheckupInfo) {
        this.checkupInfo = CheckupInfo;
    }
}
