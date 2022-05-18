package com.innowise.contract.tool.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.innowise.contract.tool.domain.Client} entity.
 */
public class ClientDTO implements Serializable {

    private Long id;

    /**
     * Название организации клиента
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Название организации клиента", required = true)
    private String name;

    /**
     * Sales Manager, который заводил клиента
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Sales Manager, который заводил клиента", required = true)
    private Long salesManagerId;

    /**
     * Страна клиента справочник
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Страна клиента справочник", required = true)
    private String countryId;

    /**
     * Статус партнера
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Статус партнера", required = true)
    private Boolean partnerStatus;

    /**
     * Контактное лицо
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Контактное лицо", required = true)
    private String contactPerson;

    /**
     * Электронная почта контактного лица
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Электронная почта контактного лица", required = true)
    private String contractEmail;

    /**
     * Индустрия клиента справочник
     */
    @Schema(description = "Индустрия клиента справочник")
    private String industryId;

    /**
     * Лицо, которое подписывает все документы(Подписант)
     */
    @Schema(description = "Лицо, которое подписывает все документы(Подписант)")
    private String signer;

    /**
     * Должность подписанта
     */
    @Schema(description = "Должность подписанта")
    private String signerPosition;

    /**
     * Юридический адрес
     */
    @Schema(description = "Юридический адрес")
    private String legalAdress;

    /**
     * Регистрационный номер организации клиента
     */
    @Schema(description = "Регистрационный номер организации клиента")
    private String registrationNumber;

    /**
     * Основание деятельности справочник
     */
    @Schema(description = "Основание деятельности справочник")
    private String baseOfActivityId;

    /**
     * VAT номер(УНП)
     */
    @Schema(description = "VAT номер(УНП)")
    private String vatNumber;

    /**
     * Банк
     */
    @Schema(description = "Банк")
    private String bankName;

    /**
     * Адрес банка
     */
    @Schema(description = "Адрес банка")
    private String bankAdress;

    /**
     * SWIFT код
     */
    @Schema(description = "SWIFT код")
    private String swiftCode;

    /**
     * IBAN
     */
    @Schema(description = "IBAN")
    private String ibanCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSalesManagerId() {
        return salesManagerId;
    }

    public void setSalesManagerId(Long salesManagerId) {
        this.salesManagerId = salesManagerId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public Boolean getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(Boolean partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContractEmail() {
        return contractEmail;
    }

    public void setContractEmail(String contractEmail) {
        this.contractEmail = contractEmail;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getSignerPosition() {
        return signerPosition;
    }

    public void setSignerPosition(String signerPosition) {
        this.signerPosition = signerPosition;
    }

    public String getLegalAdress() {
        return legalAdress;
    }

    public void setLegalAdress(String legalAdress) {
        this.legalAdress = legalAdress;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBaseOfActivityId() {
        return baseOfActivityId;
    }

    public void setBaseOfActivityId(String baseOfActivityId) {
        this.baseOfActivityId = baseOfActivityId;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAdress() {
        return bankAdress;
    }

    public void setBankAdress(String bankAdress) {
        this.bankAdress = bankAdress;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getIbanCode() {
        return ibanCode;
    }

    public void setIbanCode(String ibanCode) {
        this.ibanCode = ibanCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
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
