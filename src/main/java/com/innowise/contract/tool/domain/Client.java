package com.innowise.contract.tool.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Client.
 */
@Table("client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    /**
     * Название организации клиента
     */
    @NotNull(message = "must not be null")
    @Column("name")
    private String name;

    /**
     * Sales Manager, который заводил клиента
     */
    @NotNull(message = "must not be null")
    @Column("sales_manager_id")
    private Long salesManagerId;

    /**
     * Страна клиента справочник
     */
    @NotNull(message = "must not be null")
    @Column("country_id")
    private String countryId;

    /**
     * Статус партнера
     */
    @NotNull(message = "must not be null")
    @Column("partner_status")
    private Boolean partnerStatus;

    /**
     * Контактное лицо
     */
    @NotNull(message = "must not be null")
    @Column("contact_person")
    private String contactPerson;

    /**
     * Электронная почта контактного лица
     */
    @NotNull(message = "must not be null")
    @Column("contract_email")
    private String contractEmail;

    /**
     * Индустрия клиента справочник
     */
    @Column("industry_id")
    private String industryId;

    /**
     * Лицо, которое подписывает все документы(Подписант)
     */
    @Column("signer")
    private String signer;

    /**
     * Должность подписанта
     */
    @Column("signer_position")
    private String signerPosition;

    /**
     * Юридический адрес
     */
    @Column("legal_adress")
    private String legalAdress;

    /**
     * Регистрационный номер организации клиента
     */
    @Column("registration_number")
    private String registrationNumber;

    /**
     * Основание деятельности справочник
     */
    @Column("base_of_activity_id")
    private String baseOfActivityId;

    /**
     * VAT номер(УНП)
     */
    @Column("vat_number")
    private String vatNumber;

    /**
     * Банк
     */
    @Column("bank_name")
    private String bankName;

    /**
     * Адрес банка
     */
    @Column("bank_adress")
    private String bankAdress;

    /**
     * SWIFT код
     */
    @Column("swift_code")
    private String swiftCode;

    /**
     * IBAN
     */
    @Column("iban_code")
    private String ibanCode;

    @Transient
    @JsonIgnoreProperties(value = { "subcontracts", "client" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "subcontracts", "client" }, allowSetters = true)
    private Set<Contract> contracts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Client name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSalesManagerId() {
        return this.salesManagerId;
    }

    public Client salesManagerId(Long salesManagerId) {
        this.setSalesManagerId(salesManagerId);
        return this;
    }

    public void setSalesManagerId(Long salesManagerId) {
        this.salesManagerId = salesManagerId;
    }

    public String getCountryId() {
        return this.countryId;
    }

    public Client countryId(String countryId) {
        this.setCountryId(countryId);
        return this;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public Boolean getPartnerStatus() {
        return this.partnerStatus;
    }

    public Client partnerStatus(Boolean partnerStatus) {
        this.setPartnerStatus(partnerStatus);
        return this;
    }

    public void setPartnerStatus(Boolean partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }

    public Client contactPerson(String contactPerson) {
        this.setContactPerson(contactPerson);
        return this;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContractEmail() {
        return this.contractEmail;
    }

    public Client contractEmail(String contractEmail) {
        this.setContractEmail(contractEmail);
        return this;
    }

    public void setContractEmail(String contractEmail) {
        this.contractEmail = contractEmail;
    }

    public String getIndustryId() {
        return this.industryId;
    }

    public Client industryId(String industryId) {
        this.setIndustryId(industryId);
        return this;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getSigner() {
        return this.signer;
    }

    public Client signer(String signer) {
        this.setSigner(signer);
        return this;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getSignerPosition() {
        return this.signerPosition;
    }

    public Client signerPosition(String signerPosition) {
        this.setSignerPosition(signerPosition);
        return this;
    }

    public void setSignerPosition(String signerPosition) {
        this.signerPosition = signerPosition;
    }

    public String getLegalAdress() {
        return this.legalAdress;
    }

    public Client legalAdress(String legalAdress) {
        this.setLegalAdress(legalAdress);
        return this;
    }

    public void setLegalAdress(String legalAdress) {
        this.legalAdress = legalAdress;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public Client registrationNumber(String registrationNumber) {
        this.setRegistrationNumber(registrationNumber);
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBaseOfActivityId() {
        return this.baseOfActivityId;
    }

    public Client baseOfActivityId(String baseOfActivityId) {
        this.setBaseOfActivityId(baseOfActivityId);
        return this;
    }

    public void setBaseOfActivityId(String baseOfActivityId) {
        this.baseOfActivityId = baseOfActivityId;
    }

    public String getVatNumber() {
        return this.vatNumber;
    }

    public Client vatNumber(String vatNumber) {
        this.setVatNumber(vatNumber);
        return this;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getBankName() {
        return this.bankName;
    }

    public Client bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAdress() {
        return this.bankAdress;
    }

    public Client bankAdress(String bankAdress) {
        this.setBankAdress(bankAdress);
        return this;
    }

    public void setBankAdress(String bankAdress) {
        this.bankAdress = bankAdress;
    }

    public String getSwiftCode() {
        return this.swiftCode;
    }

    public Client swiftCode(String swiftCode) {
        this.setSwiftCode(swiftCode);
        return this;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getIbanCode() {
        return this.ibanCode;
    }

    public Client ibanCode(String ibanCode) {
        this.setIbanCode(ibanCode);
        return this;
    }

    public void setIbanCode(String ibanCode) {
        this.ibanCode = ibanCode;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setClient(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setClient(this));
        }
        this.projects = projects;
    }

    public Client projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public Client addProject(Project project) {
        this.projects.add(project);
        project.setClient(this);
        return this;
    }

    public Client removeProject(Project project) {
        this.projects.remove(project);
        project.setClient(null);
        return this;
    }

    public Set<Contract> getContracts() {
        return this.contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        if (this.contracts != null) {
            this.contracts.forEach(i -> i.setClient(null));
        }
        if (contracts != null) {
            contracts.forEach(i -> i.setClient(this));
        }
        this.contracts = contracts;
    }

    public Client contracts(Set<Contract> contracts) {
        this.setContracts(contracts);
        return this;
    }

    public Client addContract(Contract contract) {
        this.contracts.add(contract);
        contract.setClient(this);
        return this;
    }

    public Client removeContract(Contract contract) {
        this.contracts.remove(contract);
        contract.setClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", salesManagerId=" + getSalesManagerId() +
            ", countryId='" + getCountryId() + "'" +
            ", partnerStatus='" + getPartnerStatus() + "'" +
            ", contactPerson='" + getContactPerson() + "'" +
            ", contractEmail='" + getContractEmail() + "'" +
            ", industryId='" + getIndustryId() + "'" +
            ", signer='" + getSigner() + "'" +
            ", signerPosition='" + getSignerPosition() + "'" +
            ", legalAdress='" + getLegalAdress() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", baseOfActivityId='" + getBaseOfActivityId() + "'" +
            ", vatNumber='" + getVatNumber() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", bankAdress='" + getBankAdress() + "'" +
            ", swiftCode='" + getSwiftCode() + "'" +
            ", ibanCode='" + getIbanCode() + "'" +
            "}";
    }
}
