package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Expense {
    @JsonProperty("id")
    public String id;
    @JsonProperty("total")
    public Float total;
    @JsonProperty("category")
    public String category;
    @JsonProperty("vendor")
    public String vendor;
    @JsonProperty("description")
    public String description;
    @JsonProperty("User")
    public User User;
    @JsonProperty("userId")
    public String userId;
    @JsonProperty("createdAt")
    public Date createdAt;
    @JsonProperty("updatedAt")
    public Date updatedAt;
    @JsonProperty("Account")
    public BankAccount bankAccount;
    @JsonProperty("accountId")
    public String accountId;
    @JsonProperty("repeatAmount")
    public int repeatAmount;
    @JsonProperty("repeatMetric")
    public String repeatMetric;
    @JsonProperty("repeatStart")
    public Date repeatStart;
    @JsonProperty("repeatEnd")
    public Date repeatEnd;

    public Expense() {

    }

    public Expense(String id, Float total, String category, String vendor, String description, hu.petrik.bankdesktopapp.User user, String userId, Date createdAt, Date updatedAt, BankAccount bankAccount, String accountId, int repeatAmount, String repeatMetric, Date repeatStart, Date repeatEnd) {
        this.id = id;
        this.total = total;
        this.category = category;
        this.vendor = vendor;
        this.description = description;
        User = user;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.bankAccount = bankAccount;
        this.accountId = accountId;
        this.repeatAmount = repeatAmount;
        this.repeatMetric = repeatMetric;
        this.repeatStart = repeatStart;
        this.repeatEnd = repeatEnd;
    }


}
