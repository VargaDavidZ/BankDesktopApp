package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;

public class RepeatableTransaction {
    private String id;
    private Float total;
    private String category;
    private String vendor;
    private String description;
    private User User;
    private String userId;
    private Date createdAt;
    private Date lastChange;
    private BankAccount bankAccount;
    private String accountId;
    private int repeatAmount;
    private String repeatMetric;
    private Date repeatStart;
    private Date repeatEnd;
    private String name;

    public RepeatableTransaction() {

    }

    /**
     * Constructs a new instance of the RepeatableTransaction class with the specified parameters.
     *
     * @param id the unique identifier of the transaction
     * @param total the total amount of the transaction
     * @param category the category assigned to the transaction
     * @param vendor the vendor associated with the transaction
     * @param description a description of the transaction
     * @param user the user associated with the transaction
     * @param userId the unique identifier of the user related to the transaction
     * @param createdAt the creation date of the transaction
     * @param lastChange the last modification date of the transaction
     * @param bankAccount the bank account related to the transaction
     * @param accountId the unique identifier of the related bank account
     * @param repeatAmount the number of times the transaction should repeat
     * @param repeatMetric the time metric for the repetition (e.g., days, weeks, months)
     * @param repeatStart the starting date for the repetition
     * @param repeatEnd the ending date for the repetition
     * @param name the name of the transaction
     */
    public RepeatableTransaction(String id , Float total, String category, String vendor, String description, hu.petrik.bankdesktopapp.User user, String userId, Date createdAt, Date lastChange, BankAccount bankAccount, String accountId, int repeatAmount, String repeatMetric, Date repeatStart, Date repeatEnd, String name) {
        this.id = id;
        this.total = total;
        this.category = category;
        this.vendor = vendor;
        this.description = description;
        User = user;
        this.userId = userId;
        this.createdAt = createdAt;
        this.lastChange = lastChange;
        this.bankAccount = bankAccount;
        this.accountId = accountId;
        this.repeatAmount = repeatAmount;
        this.repeatMetric = repeatMetric;
        this.repeatStart = repeatStart;
        this.repeatEnd = repeatEnd;
        this.name = name;
    }


    public String getId() {
        return id;
    }


    @JsonSetter("id")
    public void setId(String id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    @JsonSetter("total")
    public void setTotal(Float total) {
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    @JsonSetter("category")
    public void setCategory(String category) {
        this.category = category;
    }

    public String getVendor() {
        return vendor;
    }

    @JsonSetter("vendor")
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }


    public String getDescription() {
        return description;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonSetter("description")
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

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
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

    public String getName() {
        return name;
    }
}
