package hu.petrik.bankdesktopapp;
import com.fasterxml.jackson.annotation.JsonSetter;


import java.util.Date;


public class Expense extends Transaction {
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
    private String repeatableTransactionId;

    public Expense() {

    }


    public Expense(String id, Float total, String category, String vendor, String description, hu.petrik.bankdesktopapp.User user, String userId, Date createdAt, Date updatedAt, BankAccount bankAccount, String accountId, String repeatableTransactionId) {
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
        this.repeatableTransactionId = repeatableTransactionId;
    }

    @JsonSetter("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonSetter("total")
    public void setTotal(Float total) {
        this.total = total;
    }

    @JsonSetter("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonSetter("vendor")
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @JsonSetter("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonSetter("User")
    public void setUser(User user) {
        User = user;
    }

    @JsonSetter("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonSetter("createdAt")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonSetter("updatedAt")
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonSetter("bankAccount")
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @JsonSetter("accountId")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRepeatableTransactionId() {
        return repeatableTransactionId;
    }
    @JsonSetter("repeatableTransactionId")
    public void setRepeatableTransactionId(String repeatableTransactionId) {
        this.repeatableTransactionId = repeatableTransactionId;
    }

    public String getId() {
        return id;
    }

    public Float getTotal() {
        return total;
    }

    public String getCategory() {
        return category;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return User;
    }

    public String getUserId() {
        return userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public String getAccountId() {
        return accountId;
    }


    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", total=" + total +
                ", category='" + category + '\'' +
                ", vendor='" + vendor + '\'' +
                ", description='" + description + '\'' +
                ", User=" + User +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", bankAccount=" + bankAccount +
                ", accountId='" + accountId + '\'' +
                ", repeatableTransactionId='" + repeatableTransactionId + '\'' +
                "} " + super.toString();
    }
}
