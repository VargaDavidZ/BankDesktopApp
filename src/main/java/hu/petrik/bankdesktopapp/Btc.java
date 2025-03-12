package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;
import java.util.Map;

public class Btc {

    private Date date ;
    private Map<String,Float> btc;

    public Btc() {

    }

    public Btc(Date date, Map<String, Float> btc) {
        this.date = date;
        this.btc = btc;
    }

    public Date getDate() {
        return date;
    }

    @JsonSetter("date")
    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Float> getValue() {
        return btc;
    }

    @JsonSetter("btc")
    public void setBtc(Map<String, Float> btc) {
        this.btc = btc;
    }
}
