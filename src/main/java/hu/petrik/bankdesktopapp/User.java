package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Date;


public class User {
    @JsonProperty("id")
    public String id;
    @JsonProperty("firstName") // correct the spelling
    public String Firstname;
    @JsonProperty("lastName")
    public String Lastname;
    @JsonProperty("role")
    public String Role;
    @JsonProperty("email")
    public String email;
    @JsonProperty("password")
    public String password;
    @JsonProperty("Expenses")
    public String[] Expense;
    @JsonProperty("expenseId")
    public String[] expenseId;
    @JsonProperty("Incomes")
    public String[] Income;
    @JsonProperty("incomeId")
    public String[] incomeId;
    @JsonProperty("createdAt")
    public Date createdAt;
    @JsonProperty("updatedAt")
    public Date updatedAt;
    @JsonProperty("Account")
    public BankAccount[] Accounts;
    @JsonProperty("accountId")
    public  String[] BankaccountsId;


    public User() {

    }

    public User(String id, String firstname, String lastname, String role, String email, String password, String[] expense, String[] expenseId, String[] income, String[] incomeId, Date createdAt, Date updatedAt, BankAccount[] accounts, String[] bankaccountsId) {
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
