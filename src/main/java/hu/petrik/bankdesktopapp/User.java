package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Date;


public class User {
    @JsonProperty("id")
    public String id;
    @JsonProperty("fristname") // correct the spelling
    public String Firstname;
    @JsonProperty("lastname")
    public String Lastname;
    @JsonProperty("role")
    public String Role;
    @JsonProperty("email")
    public String email;
    @JsonProperty("password")
    public String password;
    @JsonProperty("Expense")
    public String[] Expense;
    @JsonProperty("expenseid")
    public String[] expenseId;
    @JsonProperty("Income")
    public String[] Income;
    @JsonProperty("Incomeid")
    public String[] incomeId;
    @JsonProperty("createdat")
    public Date createdAt;
    @JsonProperty("updatedat")
    public Date updatedAt;
    @JsonProperty("Card")
    public String[] Accounts;
    @JsonProperty("cardid")
    public  String[] BankaccountsId;


    public User() {

    }

    public User(String id, String firstname, String lastname, String role, String email, String password, String[] expense, String[] expenseId, String[] income, String[] incomeId, Date createdAt, Date updatedAt, String[] accounts, String[] bankaccountsId) {
        this.id = id;
        Firstname = firstname;
        Lastname = lastname;
        Role = role;
        this.email = email;
        this.password = password;
        Expense = expense;
        this.expenseId = expenseId;
        Income = income;
        this.incomeId = incomeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        Accounts = accounts;
        BankaccountsId = bankaccountsId;
    }


    public void setId(String id) {
        this.id = id;
    }


    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public void setRole(String role) {
        Role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExpense(String[] expense) {
        Expense = expense;
    }

    public void setExpenseId(String[] expenseId) {
        this.expenseId = expenseId;
    }

    public void setIncome(String[] income) {
        Income = income;
    }

    public void setIncomeId(String[] incomeId) {
        this.incomeId = incomeId;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAccounts(String[] accounts) {
        Accounts = accounts;
    }

    public void setBankaccountsId(String[] bankaccountsId) {
        BankaccountsId = bankaccountsId;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getRole() {
        return Role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String[] getExpense() {
        return Expense;
    }

    public String[] getExpenseId() {
        return expenseId;
    }

    public String[] getIncome() {
        return Income;
    }

    public String[] getIncomeId() {
        return incomeId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String[] getAccounts() {
        return Accounts;
    }

    public String[] getBankaccountsId() {
        return BankaccountsId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", Firstname='" + Firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Role='" + Role + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", Expense=" + Arrays.toString(Expense) +
                ", expenseId=" + Arrays.toString(expenseId) +
                ", Income=" + Arrays.toString(Income) +
                ", incomeId=" + Arrays.toString(incomeId) +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", Accounts=" + Arrays.toString(Accounts) +
                ", BankaccountsId=" + Arrays.toString(BankaccountsId) +
                '}';
    }
}
