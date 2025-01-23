package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Date;

public class BankAccount {
    @JsonProperty("id")
    public String id;
    @JsonProperty("Users")
    public User[] Users;
    @JsonProperty("userId")
    public String[] userId;
    @JsonProperty("Expenses")
    public String[] Expenses;
    @JsonProperty("Incomes")
    public String[] Incomes;
    @JsonProperty("total")
    public float total;
    @JsonProperty("createdAt")
    public Date createdAt;
    @JsonProperty("updatedAt")
    public Date updatedAt;
    @JsonProperty("currency")
    public String currency;

    public BankAccount() {

    }

    public BankAccount(String id, User[] users, String[] userId, String[] expenses, String[] incomes, float total, Date createdAt, Date updatedAt, String currency) {
        this.id = id;
        Users = users;
        this.userId = userId;
        Expenses = expenses;
        Incomes = incomes;
        this.total = total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.currency = currency;
    }


    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", Users=" + Arrays.toString(Users) +
                ", userId=" + Arrays.toString(userId) +
                ", Expenses=" + Arrays.toString(Expenses) +
                ", Incomes=" + Arrays.toString(Incomes) +
                ", total=" + total +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", currency='" + currency + '\'' +
                '}';
    }
}
