package com.innowise.contract.tool.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.innowise.contract.tool.domain.ContractPosition} entity.
 */
@Schema(description = "Contract position")
public class ContractPositionDTO implements Serializable {

    /**
     * PK
     */
    @Schema(description = "PK")
    private Long id;

    /**
     * Справочник контрактных позиций
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Справочник контрактных позиций", required = true)
    private String contractPositionId;

    /**
     * Оплачиваемый сотрудник
     */
    @Schema(description = "Оплачиваемый сотрудник")
    private Long employeeId;

    /**
     * Ссылка на субконтракт
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Ссылка на субконтракт", required = true)
    private Long subcontractId;

    /**
     * Контрактные позиции
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Контрактные позиции", required = true)
    private Integer numberContractPosition;

    /**
     * Тип Ограничения справочник
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Тип Ограничения справочник", required = true)
    private String restrictionTypeId;

    /**
     * Ограничение
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Ограничение", required = true)
    private Integer restriction;

    /**
     * Валюта справочник
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Валюта справочник", required = true)
    private String currencyId;

    /**
     * Рейт час
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Рейт час", required = true)
    private Float rateAnHour;

    /**
     * Статус словарей
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Статус словарей", required = true)
    private String statusId;

    private SubcontractDTO subcontract;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractPositionId() {
        return contractPositionId;
    }

    public void setContractPositionId(String contractPositionId) {
        this.contractPositionId = contractPositionId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getSubcontractId() {
        return subcontractId;
    }

    public void setSubcontractId(Long subcontractId) {
        this.subcontractId = subcontractId;
    }

    public Integer getNumberContractPosition() {
        return numberContractPosition;
    }

    public void setNumberContractPosition(Integer numberContractPosition) {
        this.numberContractPosition = numberContractPosition;
    }

    public String getRestrictionTypeId() {
        return restrictionTypeId;
    }

    public void setRestrictionTypeId(String restrictionTypeId) {
        this.restrictionTypeId = restrictionTypeId;
    }

    public Integer getRestriction() {
        return restriction;
    }

    public void setRestriction(Integer restriction) {
        this.restriction = restriction;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Float getRateAnHour() {
        return rateAnHour;
    }

    public void setRateAnHour(Float rateAnHour) {
        this.rateAnHour = rateAnHour;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public SubcontractDTO getSubcontract() {
        return subcontract;
    }

    public void setSubcontract(SubcontractDTO subcontract) {
        this.subcontract = subcontract;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractPositionDTO)) {
            return false;
        }

        ContractPositionDTO contractPositionDTO = (ContractPositionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contractPositionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContractPositionDTO{" +
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
            ", subcontract=" + getSubcontract() +
            "}";
    }
}
