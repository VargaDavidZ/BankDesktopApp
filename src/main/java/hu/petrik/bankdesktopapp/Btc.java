package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;
import java.util.Map;

public class Btc {

    private Date date ;
    private Map<String,Float> btc;

    public Btc() {

    }

    /**
     * Constructs an instance of the Btc class with the specified date and Bitcoin-related data.
     *
     * @param date the date corresponding to the Bitcoin data
     * @param btc a map containing Bitcoin-related information, where the keys represent specific data attributes and the values are their associated float values
     */
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
