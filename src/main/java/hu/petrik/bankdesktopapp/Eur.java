package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;
import java.util.Map;

public class Eur {
    private Date date ;
    private Map<String,Float> eur;

    public Eur(){

    }


    public Eur(Date date, Map<String, Float> eur) {
        this.date = date;
        this.eur = eur;
    }

    public Date getDate() {
        return date;
    }

    @JsonSetter("date")
    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Float> getValue() {
        return eur;
    }

    @JsonSetter("eur")
    public void setEur(Map<String, Float> eur) {
        this.eur = eur;
    }
}
