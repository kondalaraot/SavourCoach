package com.savourcoach;

/**
 * Created by Suchi on 2/8/2017.
 */
public class MindfulMoment {

    private String prodID;
    private String prodDescr;
    private String prodPrice;
    private boolean isPurchased;

    public MindfulMoment() {
    }

    public MindfulMoment(String prodID, String prodDescr, String prodPrice,boolean isPurchased) {
        this.prodID = prodID;
        this.prodDescr = prodDescr;
        this.prodPrice = prodPrice;
        this.isPurchased = isPurchased;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public String getProdDescr() {
        return prodDescr;
    }

    public void setProdDescr(String prodDescr) {
        this.prodDescr = prodDescr;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
