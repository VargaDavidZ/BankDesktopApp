package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Arrays;
import java.util.Date;

@JsonIgnoreProperties({ "password" })
public class User {
    private String id;
    private String Firstname;
    private String Lastname;
    private String Role;
    private String email;
    //private String password;
    private String[] Expense;
    private String[] expenseId;
    private String[] Income;
    private String[] incomeId;
    private Date createdAt;
    private Date updatedAt;
    private BankAccount[] Accounts;
    private  String[] BankaccountsId;
    private String authToken;


    public User() {

    }

    public User(String id, String firstname, String lastname, String role, String email, String[] expense, String[] expenseId, String[] income, String[] incomeId, Date createdAt, Date updatedAt, BankAccount[] accounts, String[] bankaccountsId, String authToken) {
        this.id = id;
        Firstname = firstname;
        Lastname = lastname;
        Role = role;
        this.email = email;
        //this.password = password;
        Expense = expense;
        this.expenseId = expenseId;
        Income = income;
        this.incomeId = incomeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        Accounts = accounts;
        BankaccountsId = bankaccountsId;
        this.authToken = authToken;
    }

    public String getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return Firstname;
    }

    @JsonSetter("firstName")
    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    @JsonSetter("lastName")
    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getRole() {
        return Role;
    }

    @JsonSetter("role")
    public void setRole(String role) {
        Role = role;
    }

    public String getEmail() {
        return email;
    }

    @JsonSetter("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /*
    public String getPassword() {
        return password;
    }



    @JsonSetter("password")
    public void setPassword(String password) {
        this.password = password;
    }

     */





    public String[] getExpense() {
        return Expense;
    }

    @JsonSetter("Expense")
    public void setExpense(String[] expense) {
        Expense = expense;
    }

    public String[] getExpenseId() {
        return expenseId;
    }

    @JsonSetter("expenseId")
    public void setExpenseId(String[] expenseId) {
        this.expenseId = expenseId;
    }

    public String[] getIncome() {
        return Income;
    }

    @JsonSetter("Income")
    public void setIncome(String[] income) {
        Income = income;
    }

    public String[] getIncomeId() {
        return incomeId;
    }

    @JsonSetter("incomeId")
    public void setIncomeId(String[] incomeId) {
        this.incomeId = incomeId;
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

    public BankAccount[] getAccounts() {
        return Accounts;
    }

    @JsonSetter("Accounts")
    public void setAccounts(BankAccount[] accounts) {
        Accounts = accounts;
    }

    public String[] getBankaccountsId() {
        return BankaccountsId;
    }

    @JsonSetter("accountId")
    public void setBankaccountsId(String[] bankaccountsId) {
        BankaccountsId = bankaccountsId;
    }

    public String getAuthToken() {
        return authToken;
    }

    @JsonSetter("access_token")
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", Firstname='" + Firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", Role='" + Role + '\'' +
                ", email='" + email + '\'' +
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
