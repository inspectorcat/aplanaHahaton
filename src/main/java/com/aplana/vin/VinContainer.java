package com.aplana.vin;

public class VinContainer {

    private String vinNumber;


    public VinContainer(VinNumber vinNumber) {
        this.vinNumber = vinNumber.toString();

    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(VinNumber vinNumber) {
        this.vinNumber = vinNumber.toString();
    }
}
