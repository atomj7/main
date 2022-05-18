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
 * A Subcontract.
 */
@Table("subcontract")
public class Subcontract implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ПК
     */
    @Id
    @Column("id")
    private Long id;

    /**
     * Ссылка на контракт
     */
    @NotNull(message = "must not be null")
    @Column("contract_id")
    private Long contractId;

    /**
     * Ссылка на проект
     */
    @NotNull(message = "must not be null")
    @Column("project_id")
    private Long projectId;

    /**
     * Шифр субконтракта
     */
    @NotNull(message = "must not be null")
    @Column("subcontract_cipher")
    private String subcontractCipher;

    /**
     * Статус из справочника
     */
    @NotNull(message = "must not be null")
    @Column("status_id")
    private String statusId;

    /**
     * Вид сотрудничества справочник
     */
    @NotNull(message = "must not be null")
    @Column("cooperation_type_id")
    private String cooperationTypeId;

    /**
     * Сумма
     */
    @Column("sum")
    private Float sum;

    /**
     * Количество контрактных позиций в рамках субконтракта
     */
    @NotNull(message = "must not be null")
    @Column("positions")
    private Integer positions;

    /**
     * Справочник валют
     */
    @NotNull(message = "must not be null")
    @Column("currency_id")
    private String currencyId;

    /**
     * Справочник типов сроков оплаты
     */
    @NotNull(message = "must not be null")
    @Column("payment_term_type_id")
    private String paymentTermTypeId;

    /**
     * Cрок оплаты
     */
    @NotNull(message = "must not be null")
    @Column("payment_term_id")
    private String paymentTermId;

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

    /**
     * Тип субконтракта справочник
     */
    @Column("type_id")
    private String typeId;

    /**
     * Тип CV Project справочник
     */
    @NotNull(message = "must not be null")
    @Column("taskt_type_id")
    private String tasktTypeId;

    /**
     * Технология справочник
     */
    @NotNull(message = "must not be null")
    @Column("technology_id")
    private String technologyId;

    /**
     * Домен справочник
     */
    @NotNull(message = "must not be null")
    @Column("domen_id")
    private String domenId;

    /**
     * Ссылка на документ
     */
    @Column("link")
    private String link;

    @Transient
    @JsonIgnoreProperties(value = { "subcontract" }, allowSetters = true)
    private Set<ContractPosition> contractPositions = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "subcontracts", "client" }, allowSetters = true)
    private Contract contract;

    @Transient
    @JsonIgnoreProperties(value = { "subcontracts", "client" }, allowSetters = true)
    private Project project;

    @Column("contract_id")
    private Long contractId;

    @Column("project_id")
    private Long projectId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Subcontract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContractId() {
        return this.contractId;
    }

    public Subcontract contractId(Long contractId) {
        this.setContractId(contractId);
        return this;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public Subcontract projectId(Long projectId) {
        this.setProjectId(projectId);
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getSubcontractCipher() {
        return this.subcontractCipher;
    }

    public Subcontract subcontractCipher(String subcontractCipher) {
        this.setSubcontractCipher(subcontractCipher);
        return this;
    }

    public void setSubcontractCipher(String subcontractCipher) {
        this.subcontractCipher = subcontractCipher;
    }

    public String getStatusId() {
        return this.statusId;
    }

    public Subcontract statusId(String statusId) {
        this.setStatusId(statusId);
        return this;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getCooperationTypeId() {
        return this.cooperationTypeId;
    }

    public Subcontract cooperationTypeId(String cooperationTypeId) {
        this.setCooperationTypeId(cooperationTypeId);
        return this;
    }

    public void setCooperationTypeId(String cooperationTypeId) {
        this.cooperationTypeId = cooperationTypeId;
    }

    public Float getSum() {
        return this.sum;
    }

    public Subcontract sum(Float sum) {
        this.setSum(sum);
        return this;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public Integer getPositions() {
        return this.positions;
    }

    public Subcontract positions(Integer positions) {
        this.setPositions(positions);
        return this;
    }

    public void setPositions(Integer positions) {
        this.positions = positions;
    }

    public String getCurrencyId() {
        return this.currencyId;
    }

    public Subcontract currencyId(String currencyId) {
        this.setCurrencyId(currencyId);
        return this;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getPaymentTermTypeId() {
        return this.paymentTermTypeId;
    }

    public Subcontract paymentTermTypeId(String paymentTermTypeId) {
        this.setPaymentTermTypeId(paymentTermTypeId);
        return this;
    }

    public void setPaymentTermTypeId(String paymentTermTypeId) {
        this.paymentTermTypeId = paymentTermTypeId;
    }

    public String getPaymentTermId() {
        return this.paymentTermId;
    }

    public Subcontract paymentTermId(String paymentTermId) {
        this.setPaymentTermId(paymentTermId);
        return this;
    }

    public void setPaymentTermId(String paymentTermId) {
        this.paymentTermId = paymentTermId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Subcontract startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return this.finishDate;
    }

    public Subcontract finishDate(LocalDate finishDate) {
        this.setFinishDate(finishDate);
        return this;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public Subcontract typeId(String typeId) {
        this.setTypeId(typeId);
        return this;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTasktTypeId() {
        return this.tasktTypeId;
    }

    public Subcontract tasktTypeId(String tasktTypeId) {
        this.setTasktTypeId(tasktTypeId);
        return this;
    }

    public void setTasktTypeId(String tasktTypeId) {
        this.tasktTypeId = tasktTypeId;
    }

    public String getTechnologyId() {
        return this.technologyId;
    }

    public Subcontract technologyId(String technologyId) {
        this.setTechnologyId(technologyId);
        return this;
    }

    public void setTechnologyId(String technologyId) {
        this.technologyId = technologyId;
    }

    public String getDomenId() {
        return this.domenId;
    }

    public Subcontract domenId(String domenId) {
        this.setDomenId(domenId);
        return this;
    }

    public void setDomenId(String domenId) {
        this.domenId = domenId;
    }

    public String getLink() {
        return this.link;
    }

    public Subcontract link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<ContractPosition> getContractPositions() {
        return this.contractPositions;
    }

    public void setContractPositions(Set<ContractPosition> contractPositions) {
        if (this.contractPositions != null) {
            this.contractPositions.forEach(i -> i.setSubcontract(null));
        }
        if (contractPositions != null) {
            contractPositions.forEach(i -> i.setSubcontract(this));
        }
        this.contractPositions = contractPositions;
    }

    public Subcontract contractPositions(Set<ContractPosition> contractPositions) {
        this.setContractPositions(contractPositions);
        return this;
    }

    public Subcontract addContractPosition(ContractPosition contractPosition) {
        this.contractPositions.add(contractPosition);
        contractPosition.setSubcontract(this);
        return this;
    }

    public Subcontract removeContractPosition(ContractPosition contractPosition) {
        this.contractPositions.remove(contractPosition);
        contractPosition.setSubcontract(null);
        return this;
    }

    public Contract getContract() {
        return this.contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
        this.contractId = contract != null ? contract.getId() : null;
    }

    public Subcontract contract(Contract contract) {
        this.setContract(contract);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
        this.projectId = project != null ? project.getId() : null;
    }

    public Subcontract project(Project project) {
        this.setProject(project);
        return this;
    }

    public Long getContractId() {
        return this.contractId;
    }

    public void setContractId(Long contract) {
        this.contractId = contract;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long project) {
        this.projectId = project;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subcontract)) {
            return false;
        }
        return id != null && id.equals(((Subcontract) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subcontract{" +
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
            "}";
    }
}
