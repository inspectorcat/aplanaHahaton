package com.aplana.vin;

public class VinNumber {

    private java.lang.String wmi;
    private java.lang.String vds;
    private java.lang.String year;
    private java.lang.String checkDigit;
    private java.lang.String serialNumber;
    private static final java.lang.String MANUFACTURE ="A";


    public java.lang.String getWmi() {
        return wmi;
    }

    public void setWmi(java.lang.String wmi) {
        this.wmi = wmi;
    }

    public java.lang.String getVds() {
        return vds;
    }

    public void setVds(java.lang.String vds) {
        this.vds = vds;
    }

    public java.lang.String getYear() {
        return year;
    }

    public void setYear(java.lang.String year) {
        this.year = year;
    }

    public java.lang.String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(java.lang.String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public java.lang.String toString() {
        return wmi+vds+checkDigit+year+MANUFACTURE+serialNumber;
    }

    public java.lang.String getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(java.lang.String checkDigit) {
        this.checkDigit = checkDigit;
    }
}
