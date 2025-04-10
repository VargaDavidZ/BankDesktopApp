package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;
import java.util.Map;

public class Eur {
    private Date date ;
    private Map<String,Float> eur;

    public Eur(){

    }

    /**
     * Constructs an instance of the Eur class with the specified date and Euro-related data.
     *
     * @param date the date corresponding to the Euro data
     * @param eur a map containing Euro-related information, where the keys represent specific data attributes and the values are their associated float values
     */
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
