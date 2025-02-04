package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;
import java.util.Map;

public class Usd {
    private Date date ;
    private Map<String,Float> usd;

    public Usd() {

    }

    public Usd(Date date, Map<String, Float> usd) {
        this.date = date;
        this.usd = usd;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonSetter("usd")
    public Map<String, Float> getValue() {
        return usd;
    }

    public void setUsd(Map<String, Float> usd) {
        this.usd = usd;
    }
}
