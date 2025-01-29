package hu.petrik.bankdesktopapp;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;


import java.util.Arrays;
import java.util.Date;


public class BankAccount {
    private String id;
    private User[] Users;
    private String[] userId;
    private Expense[] Expenses;
    private Income[] Income;
    private float total;
    private Date createdAt;
    private Date updatedAt;
    private String currency;



    public BankAccount() {


    }



    public BankAccount(String id, User[] users, String[] userId, Expense[] expenses, Income[] incomes, float total, Date createdAt, Date updatedAt, String currency) throws JsonProcessingException {


        this.id = id;
        Users = users;
        this.userId = userId;
        Expenses = expenses;
        Income =  incomes;
        this.total = total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.currency = currency;
    }


    public String getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(String id) {
        this.id = id;
    }

    public User[] getUsers() {
        return Users;
    }
    @JsonSetter("Users")
    public void setUsers(User[] users) {
        Users = users;
    }

    public String[] getUserId() {
        return userId;
    }
    @JsonSetter("userId")
    public void setUserId(String[] userId) {
        this.userId = userId;
    }

    public Expense[] getExpenses() {
        return Expenses;
    }
    @JsonSetter("Expenses")
    public void setExpenses(Expense[] expenses) {
        Expenses = expenses;
    }

    public Income[] getIncome() {
        return Income;
    }

    @JsonSetter("Incomes")
    public void setIncome(Income[] income) {
        Income = income;
    }

    public float getTotal() {
        return total;
    }
    @JsonSetter("total")
    public void setTotal(float total) {
        this.total = total;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    @JsonSetter("createdAt")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @JsonSetter("updatedAt")
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCurrency() {
        return currency;
    }
    @JsonSetter("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", Users=" + Arrays.toString(Users) +
                ", userId=" + Arrays.toString(userId) +
                ", Expenses=" + Arrays.toString(Expenses) +
                ", Incomes=" + Income +
                ", total=" + total +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", currency='" + currency + '\'' +
                '}';
    }
}
