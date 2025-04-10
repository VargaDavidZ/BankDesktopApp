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
    private String ownerName;
    private String ownerId;



    public BankAccount() {


    }

    /**
     * Constructor for the BankAccount class.
     * Initializes a new instance of a bank account with the specified details.
     *
     * @param id The unique identifier of the bank account.
     * @param users An array of User objects associated with the bank account.
     * @param userId An array of user IDs linked to the bank account.
     * @param expenses An array of Expense objects associated with the bank account.
     * @param income An array of Income objects associated with the bank account.
     * @param total The total balance in the bank account.
     * @param createdAt The date when the bank account was created.
     * @param updatedAt The date when the bank account was last updated.
     * @param currency The currency used in the bank account.
     * @param ownerName The name of the owner of the bank account.
     */
    public BankAccount(String id, User[] users, String[] userId, Expense[] expenses, hu.petrik.bankdesktopapp.Income[] income, float total, Date createdAt, Date updatedAt, String currency, String ownerName) {
        this.id = id;
        Users = users;
        this.userId = userId;
        Expenses = expenses;
        Income = income;
        this.total = total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.currency = currency;
        this.ownerName = ownerName;
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



    public String getOwnerName() {
        return ownerName;
    }

    @JsonSetter("ownerName")
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public String getOwnerId() {
        return ownerId;
    }
    @JsonSetter("ownerId")
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }


    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", Users=" + Arrays.toString(Users) +
                ", userId=" + Arrays.toString(userId) +
                ", Expenses=" + Arrays.toString(Expenses) +
                ", Income=" + Arrays.toString(Income) +
                ", total=" + total +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", currency='" + currency + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}
