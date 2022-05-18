package com.innowise.contract.tool.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.innowise.contract.tool.domain.Subcontract} entity.
 */
public class SubcontractDTO implements Serializable {

    /**
     * ПК
     */
    @Schema(description = "ПК")
    private Long id;

    /**
     * Ссылка на контракт
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Ссылка на контракт", required = true)
    private Long contractId;

    /**
     * Ссылка на проект
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Ссылка на проект", required = true)
    private Long projectId;

    /**
     * Шифр субконтракта
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Шифр субконтракта", required = true)
    private String subcontractCipher;

    /**
     * Статус из справочника
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Статус из справочника", required = true)
    private String statusId;

    /**
     * Вид сотрудничества справочник
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Вид сотрудничества справочник", required = true)
    private String cooperationTypeId;

    /**
     * Сумма
     */
    @Schema(description = "Сумма")
    private Float sum;

    /**
     * Количество контрактных позиций в рамках субконтракта
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Количество контрактных позиций в рамках субконтракта", required = true)
    private Integer positions;

    /**
     * Справочник валют
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Справочник валют", required = true)
    private String currencyId;

    /**
     * Справочник типов сроков оплаты
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Справочник типов сроков оплаты", required = true)
    private String paymentTermTypeId;

    /**
     * Cрок оплаты
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Cрок оплаты", required = true)
    private String paymentTermId;

    /**
     * Дата начала
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Дата начала", required = true)
    private LocalDate startDate;

    /**
     * Дата окончания
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Дата окончания", required = true)
    private LocalDate finishDate;

    /**
     * Тип субконтракта справочник
     */
    @Schema(description = "Тип субконтракта справочник")
    private String typeId;

    /**
     * Тип CV Project справочник
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Тип CV Project справочник", required = true)
    private String tasktTypeId;

    /**
     * Технология справочник
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Технология справочник", required = true)
    private String technologyId;

    /**
     * Домен справочник
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Домен справочник", required = true)
    private String domenId;

    /**
     * Ссылка на документ
     */
    @Schema(description = "Ссылка на документ")
    private String link;

    private ContractDTO contractId;

    private ProjectDTO projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getSubcontractCipher() {
        return subcontractCipher;
    }

    public void setSubcontractCipher(String subcontractCipher) {
        this.subcontractCipher = subcontractCipher;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getCooperationTypeId() {
        return cooperationTypeId;
    }

    public void setCooperationTypeId(String cooperationTypeId) {
        this.cooperationTypeId = cooperationTypeId;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public Integer getPositions() {
        return positions;
    }

    public void setPositions(Integer positions) {
        this.positions = positions;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getPaymentTermTypeId() {
        return paymentTermTypeId;
    }

    public void setPaymentTermTypeId(String paymentTermTypeId) {
        this.paymentTermTypeId = paymentTermTypeId;
    }

    public String getPaymentTermId() {
        return paymentTermId;
    }

    public void setPaymentTermId(String paymentTermId) {
        this.paymentTermId = paymentTermId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTasktTypeId() {
        return tasktTypeId;
    }

    public void setTasktTypeId(String tasktTypeId) {
        this.tasktTypeId = tasktTypeId;
    }

    public String getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(String technologyId) {
        this.technologyId = technologyId;
    }

    public String getDomenId() {
        return domenId;
    }

    public void setDomenId(String domenId) {
        this.domenId = domenId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ContractDTO getContractId() {
        return contractId;
    }

    public void setContractId(ContractDTO contractId) {
        this.contractId = contractId;
    }

    public ProjectDTO getProjectId() {
        return projectId;
    }

    public void setProjectId(ProjectDTO projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubcontractDTO)) {
            return false;
        }

        SubcontractDTO subcontractDTO = (SubcontractDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subcontractDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubcontractDTO{" +
            "id=" + getId() +
            ", contractId=" + getContractId() +
            ", projectId=" + getProjectId() +
            ", subcontractCipher='" + getSubcontractCipher() + "'" +
            ", statusId='" + getStatusId() + "'" +
            ", cooperationTypeId='" + getCooperationTypeId() + "'" +
            ", sum=" + getSum() +
            ", positions=" + getPositions() +
            ", currencyId='" + getCurrencyId() + "'" +
            ", paymentTermTypeId='" + getPaymentTermTypeId() + "'" +
            ", paymentTermId='" + getPaymentTermId() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", finishDate='" + getFinishDate() + "'" +
            ", typeId='" + getTypeId() + "'" +
            ", tasktTypeId='" + getTasktTypeId() + "'" +
            ", technologyId='" + getTechnologyId() + "'" +
            ", domenId='" + getDomenId() + "'" +
            ", link='" + getLink() + "'" +
            ", contractId=" + getContractId() +
            ", projectId=" + getProjectId() +
            "}";
    }
}
