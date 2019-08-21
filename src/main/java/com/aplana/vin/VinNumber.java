package com.aplana.vin;

public class VinNumber {

    private String wmi;
    private String vds;
    private String year;
    private String checkDigit;
    private String serialNumber;
    private static final String MANUFACTURE ="A";


    public String getWmi() {
        return wmi;
    }

    public void setWmi(String wmi) {
        this.wmi = wmi;
    }

    public String getVds() {
        return vds;
    }

    public void setVds(String vds) {
        this.vds = vds;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return wmi+vds+checkDigit+year+MANUFACTURE+serialNumber;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(String checkDigit) {
        this.checkDigit = checkDigit;
    }
}
