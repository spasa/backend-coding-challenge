package com.engagetech.engage.entity;

import com.engagetech.engage.entity.validation.EntityValidationChecks.CreateExpenseChecks;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {
    
    private Long id;
    private Integer userId;
    private Long sessionId;
    
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT")
    @NotNull(message = "expense.entity.for.date.empty", groups = CreateExpenseChecks.class)
    private Date forDate;
    
    @NotNull(message = "expense.entity.amount.empty", groups = CreateExpenseChecks.class)
    private BigDecimal amount;
    
    @JsonProperty("vat")
    private BigDecimal taxValue;
    
    @NotEmpty(message = "expense.entity.reason.empty", groups = CreateExpenseChecks.class)
    private String reason;
    
    private String currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Date getForDate() {
        return forDate;
    }

    public void setForDate(Date forDate) {
        this.forDate = forDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Expense other = (Expense) obj;
        if (this.id == null || !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Expense{" + "id=" + id + ", userId=" + userId + ", sessionId=" + sessionId + ", forDate=" + forDate + ", amount=" + amount + ", taxValue=" + taxValue + ", reason=" + reason + '}';
    }

}
