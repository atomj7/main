package com.innowise.contract.tool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Contracts
 */
@Table("contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    /**
     * Шифр контракта
     */
    @NotNull(message = "must not be null")
    @Column("cipher")
    private String cipher;

    /**
     * Айди клиента
     */
    @NotNull(message = "must not be null")
    @Column("client_id")
    private Long clientId;

    /**
     * Справочник поставщиков
     */
    @NotNull(message = "must not be null")
    @Column("provider_id")
    private String providerId;

    /**
     * Тип контракта справочник
     */
    @Column("type_id")
    private String typeId;

    /**
     * Сумма
     */
    @NotNull(message = "must not be null")
    @Column("sum")
    private Float sum;

    /**
     * Всего позиций
     */
    @NotNull(message = "must not be null")
    @Column("position_count")
    private Integer positionCount;

    /**
     * Валюта справочник
     */
    @NotNull(message = "must not be null")
    @Column("currency_id")
    private String currencyId;

    /**
     * Срок оплаты кол-во дней
     */
    @NotNull(message = "must not be null")
    @Column("payment_term")
    private Integer paymentTerm;

    /**
     * Тип срока оплаты справочник
     */
    @NotNull(message = "must not be null")
    @Column("payment_term_type_id")
    private String paymentTermTypeId;

    /**
     * Дата начала
     */
    @NotNull(message = "must not be null")
    @Column("start_date")
    private LocalDate startDate;

    /**
     * Дата окончания
     */
    @NotNull(message = "must not be null")
    @Column("finish_date")
    private LocalDate finishDate;

    @NotNull(message = "must not be null")
    @Column("status_id")
    private String statusId;

    /**
     * Ссылка
     */
    @Column("link")
    private String link;

    @Transient
    @JsonIgnoreProperties(value = { "contractPositions", "contract", "project" }, allowSetters = true)
    private Set<Subcontract> subcontracts = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "projects", "contracts" }, allowSetters = true)
    private Client client;

    @Column("client_id")
    private Long clientId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCipher() {
        return this.cipher;
    }

    public Contract cipher(String cipher) {
        this.setCipher(cipher);
        return this;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public Contract clientId(Long clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getProviderId() {
        return this.providerId;
    }

    public Contract providerId(String providerId) {
        this.setProviderId(providerId);
        return this;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public Contract typeId(String typeId) {
        this.setTypeId(typeId);
        return this;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Float getSum() {
        return this.sum;
    }

    public Contract sum(Float sum) {
        this.setSum(sum);
        return this;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public Integer getPositionCount() {
        return this.positionCount;
    }

    public Contract positionCount(Integer positionCount) {
        this.setPositionCount(positionCount);
        return this;
    }

    public void setPositionCount(Integer positionCount) {
        this.positionCount = positionCount;
    }

    public String getCurrencyId() {
        return this.currencyId;
    }

    public Contract currencyId(String currencyId) {
        this.setCurrencyId(currencyId);
        return this;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getPaymentTerm() {
        return this.paymentTerm;
    }

    public Contract paymentTerm(Integer paymentTerm) {
        this.setPaymentTerm(paymentTerm);
        return this;
    }

    public void setPaymentTerm(Integer paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getPaymentTermTypeId() {
        return this.paymentTermTypeId;
    }

    public Contract paymentTermTypeId(String paymentTermTypeId) {
        this.setPaymentTermTypeId(paymentTermTypeId);
        return this;
    }

    public void setPaymentTermTypeId(String paymentTermTypeId) {
        this.paymentTermTypeId = paymentTermTypeId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Contract startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return this.finishDate;
    }

    public Contract finishDate(LocalDate finishDate) {
        this.setFinishDate(finishDate);
        return this;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public String getStatusId() {
        return this.statusId;
    }

    public Contract statusId(String statusId) {
        this.setStatusId(statusId);
        return this;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getLink() {
        return this.link;
    }

    public Contract link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<Subcontract> getSubcontracts() {
        return this.subcontracts;
    }

    public void setSubcontracts(Set<Subcontract> subcontracts) {
        if (this.subcontracts != null) {
            this.subcontracts.forEach(i -> i.setContract(null));
        }
        if (subcontracts != null) {
            subcontracts.forEach(i -> i.setContract(this));
        }
        this.subcontracts = subcontracts;
    }

    public Contract subcontracts(Set<Subcontract> subcontracts) {
        this.setSubcontracts(subcontracts);
        return this;
    }

    public Contract addSubcontract(Subcontract subcontract) {
        this.subcontracts.add(subcontract);
        subcontract.setContract(this);
        return this;
    }

    public Contract removeSubcontract(Subcontract subcontract) {
        this.subcontracts.remove(subcontract);
        subcontract.setContract(null);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
        this.clientId = client != null ? client.getId() : null;
    }

    public Contract client(Client client) {
        this.setClient(client);
        return this;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long client) {
        this.clientId = client;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contract)) {
            return false;
        }
        return id != null && id.equals(((Contract) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contract{" +
            "id=" + getId() +
            ", cipher='" + getCipher() + "'" +
            ", clientId=" + getClientId() +
            ", providerId='" + getProviderId() + "'" +
            ", typeId='" + getTypeId() + "'" +
            ", sum=" + getSum() +
            ", positionCount=" + getPositionCount() +
            ", currencyId='" + getCurrencyId() + "'" +
            ", paymentTerm=" + getPaymentTerm() +
            ", paymentTermTypeId='" + getPaymentTermTypeId() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", finishDate='" + getFinishDate() + "'" +
            ", statusId='" + getStatusId() + "'" +
            ", link='" + getLink() + "'" +
            "}";
    }
}
