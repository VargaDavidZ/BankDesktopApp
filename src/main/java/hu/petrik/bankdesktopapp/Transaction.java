package hu.petrik.bankdesktopapp;

import java.util.Date;

public class Transaction {
    private String id;
    private Float total;
    private String category;
    private String vendor;
    private String description;
    private User User;
    private String userId;
    private Date createdAt;
    private Date updatedAt;
    private BankAccount bankAccount;
    private String accountId;
    private int repeatAmount;
    private String repeatMetric;
    private Date repeatStart;
    private Date repeatEnd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getRepeatAmount() {
        return repeatAmount;
    }

    public void setRepeatAmount(int repeatAmount) {
        this.repeatAmount = repeatAmount;
    }

    public String getRepeatMetric() {
        return repeatMetric;
    }

    public void setRepeatMetric(String repeatMetric) {
        this.repeatMetric = repeatMetric;
    }

    public Date getRepeatStart() {
        return repeatStart;
    }

    public void setRepeatStart(Date repeatStart) {
        this.repeatStart = repeatStart;
    }

    public Date getRepeatEnd() {
        return repeatEnd;
    }

    public void setRepeatEnd(Date repeatEnd) {
        this.repeatEnd = repeatEnd;
    }
}
