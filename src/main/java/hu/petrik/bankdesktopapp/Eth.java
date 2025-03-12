package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;
import java.util.Map;

public class Eth {
    private Date date ;
    private Map<String,Float> eth;

    public Eth() {

    }

    public Eth(Date date, Map<String, Float> eth) {
        this.date = date;
        this.eth = eth;
    }

    public Date getDate() {
        return date;
    }

    @JsonSetter("date")
    public void setDate(Date date) {
        this.date = date;
    }


    public Map<String, Float> getValue() {
        return eth;
    }
    @JsonSetter("eth")
    public void setEth(Map<String, Float> eth) {
        this.eth = eth;
    }
}
