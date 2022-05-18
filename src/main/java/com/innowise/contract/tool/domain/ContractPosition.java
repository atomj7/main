package com.innowise.contract.tool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Contract position
 */
@Table("contract_position")
public class ContractPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * PK
     */
    @Id
    @Column("id")
    private Long id;

    /**
     * Справочник контрактных позиций
     */
    @NotNull(message = "must not be null")
    @Column("contract_position_id")
    private String contractPositionId;

    /**
     * Оплачиваемый сотрудник
     */
    @Column("employee_id")
    private Long employeeId;

    /**
     * Ссылка на субконтракт
     */
    @NotNull(message = "must not be null")
    @Column("subcontract_id")
    private Long subcontractId;

    /**
     * Контрактные позиции
     */
    @NotNull(message = "must not be null")
    @Column("number_contract_position")
    private Integer numberContractPosition;

    /**
     * Тип Ограничения справочник
     */
    @NotNull(message = "must not be null")
    @Column("restriction_type_id")
    private String restrictionTypeId;

    /**
     * Ограничение
     */
    @NotNull(message = "must not be null")
    @Column("restriction")
    private Integer restriction;

    /**
     * Валюта справочник
     */
    @NotNull(message = "must not be null")
    @Column("currency_id")
    private String currencyId;

    /**
     * Рейт час
     */
    @NotNull(message = "must not be null")
    @Column("rate_an_hour")
    private Float rateAnHour;

    /**
     * Статус словарей
     */
    @NotNull(message = "must not be null")
    @Column("status_id")
    private String statusId;

    @Transient
    @JsonIgnoreProperties(value = { "contractPositions", "contract", "project" }, allowSetters = true)
    private Subcontract subcontract;

    @Column("subcontract_id")
    private Long subcontractId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContractPosition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractPositionId() {
        return this.contractPositionId;
    }

    public ContractPosition contractPositionId(String contractPositionId) {
        this.setContractPositionId(contractPositionId);
        return this;
    }

    public void setContractPositionId(String contractPositionId) {
        this.contractPositionId = contractPositionId;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public ContractPosition employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getSubcontractId() {
        return this.subcontractId;
    }

    public ContractPosition subcontractId(Long subcontractId) {
        this.setSubcontractId(subcontractId);
        return this;
    }

    public void setSubcontractId(Long subcontractId) {
        this.subcontractId = subcontractId;
    }

    public Integer getNumberContractPosition() {
        return this.numberContractPosition;
    }

    public ContractPosition numberContractPosition(Integer numberContractPosition) {
        this.setNumberContractPosition(numberContractPosition);
        return this;
    }

    public void setNumberContractPosition(Integer numberContractPosition) {
        this.numberContractPosition = numberContractPosition;
    }

    public String getRestrictionTypeId() {
        return this.restrictionTypeId;
    }

    public ContractPosition restrictionTypeId(String restrictionTypeId) {
        this.setRestrictionTypeId(restrictionTypeId);
        return this;
    }

    public void setRestrictionTypeId(String restrictionTypeId) {
        this.restrictionTypeId = restrictionTypeId;
    }

    public Integer getRestriction() {
        return this.restriction;
    }

    public ContractPosition restriction(Integer restriction) {
        this.setRestriction(restriction);
        return this;
    }

    public void setRestriction(Integer restriction) {
        this.restriction = restriction;
    }

    public String getCurrencyId() {
        return this.currencyId;
    }

    public ContractPosition currencyId(String currencyId) {
        this.setCurrencyId(currencyId);
        return this;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Float getRateAnHour() {
        return this.rateAnHour;
    }

    public ContractPosition rateAnHour(Float rateAnHour) {
        this.setRateAnHour(rateAnHour);
        return this;
    }

    public void setRateAnHour(Float rateAnHour) {
        this.rateAnHour = rateAnHour;
    }

    public String getStatusId() {
        return this.statusId;
    }

    public ContractPosition statusId(String statusId) {
        this.setStatusId(statusId);
        return this;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public Subcontract getSubcontract() {
        return this.subcontract;
    }

    public void setSubcontract(Subcontract subcontract) {
        this.subcontract = subcontract;
        this.subcontractId = subcontract != null ? subcontract.getId() : null;
    }

    public ContractPosition subcontract(Subcontract subcontract) {
        this.setSubcontract(subcontract);
        return this;
    }

    public Long getSubcontractId() {
        return this.subcontractId;
    }

    public void setSubcontractId(Long subcontract) {
        this.subcontractId = subcontract;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractPosition)) {
            return false;
        }
        return id != null && id.equals(((ContractPosition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContractPosition{" +
            "id=" + getId() +
            ", contractPositionId='" + getContractPositionId() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", subcontractId=" + getSubcontractId() +
            ", numberContractPosition=" + getNumberContractPosition() +
            ", restrictionTypeId='" + getRestrictionTypeId() + "'" +
            ", restriction=" + getRestriction() +
            ", currencyId='" + getCurrencyId() + "'" +
            ", rateAnHour=" + getRateAnHour() +
            ", statusId='" + getStatusId() + "'" +
            "}";
    }
}
