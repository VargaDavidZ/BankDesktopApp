package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.util.Date;

public class RepeatableExpense {

    private String id;
    private String total;
    private String category;
    private String description;
    private String userId;
    private String accountId;
    private String repeatAmount;
    private String repeatMetric;
    private Date repeatStart;
    private Date repeatEnd;
    private String name;
    private Date lastChange;
    private Date createdAt;

    public RepeatableExpense() {

    }

    /**
     * Constructs an instance of the RepeatableExpense class with specified details for a repeatable expense entry.
     *
     * @param id the unique identifier for the expense
     * @param total the total amount of the expense
     * @param category the category to which the expense belongs
     * @param description a textual description of the expense
     * @param userId the identifier of the user associated with the expense
     * @param accountId the identifier of the account associated with the expense
     * @param repeatAmount the amount indicating the repeat interval of the expense
     * @param repeatMetric the metric of the repeat interval (e.g., days, weeks, months)
     * @param repeatStart the start date for the repeatable expense
     * @param repeatEnd the end date for the repeatable expense
     * @param name the name or title of the expense
     * @param lastChange the date of the last modification to the expense
     * @param createdAt the date the expense was created
     */
    public RepeatableExpense(String id, String total, String category, String description, String userId, String accountId, String repeatAmount, String repeatMetric, Date repeatStart, Date repeatEnd, String name, Date lastChange, Date createdAt) {
        this.id = id;
        this.total = total;
        this.category = category;
        this.description = description;
        this.userId = userId;
        this.accountId = accountId;
        this.repeatAmount = repeatAmount;
        this.repeatMetric = repeatMetric;
        this.repeatStart = repeatStart;
        this.repeatEnd = repeatEnd;
        this.name = name;
        this.lastChange = lastChange;
        this.createdAt = createdAt;
    }

    @JsonSetter("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonSetter("lastChange")
    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    @JsonSetter("createdAt")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonSetter("total")
    public void setTotal(String total) {
        this.total = total;
    }

    @JsonSetter("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonSetter("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonSetter("userID")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonSetter("accountId")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @JsonSetter("repeatAmount")
    public void setRepeatAmount(String repeatAmount) {
        this.repeatAmount = repeatAmount;
    }

    @JsonSetter("repeatMetric")
    public void setRepeatMetric(String repeatMetric) {
        this.repeatMetric = repeatMetric;
    }

    @JsonSetter("repeatStart")
    public void setRepeatStart(Date repeatStart) {
        this.repeatStart = repeatStart;
    }

    @JsonSetter("repeatEnd")
    public void setRepeatEnd(Date repeatEnd) {
        this.repeatEnd = repeatEnd;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getRepeatAmount() {
        return repeatAmount;
    }

    public String getRepeatMetric() {
        return repeatMetric;
    }

    public Date getRepeatStart() {
        return repeatStart;
    }

    public Date getRepeatEnd() {
        return repeatEnd;
    }

    public String getName() {
        return name;
    }
}
