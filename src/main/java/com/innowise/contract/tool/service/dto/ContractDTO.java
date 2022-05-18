package com.innowise.contract.tool.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.innowise.contract.tool.domain.Contract} entity.
 */
@Schema(description = "Contracts")
public class ContractDTO implements Serializable {

    private Long id;

    /**
     * Шифр контракта
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Шифр контракта", required = true)
    private String cipher;

    /**
     * Айди клиента
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Айди клиента", required = true)
    private Long clientId;

    /**
     * Справочник поставщиков
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Справочник поставщиков", required = true)
    private String providerId;

    /**
     * Тип контракта справочник
     */
    @Schema(description = "Тип контракта справочник")
    private String typeId;

    /**
     * Сумма
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Сумма", required = true)
    private Float sum;

    /**
     * Всего позиций
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Всего позиций", required = true)
    private Integer positionCount;

    /**
     * Валюта справочник
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Валюта справочник", required = true)
    private String currencyId;

    /**
     * Срок оплаты кол-во дней
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Срок оплаты кол-во дней", required = true)
    private Integer paymentTerm;

    /**
     * Тип срока оплаты справочник
     */
    @NotNull(message = "must not be null")
    @Schema(description = "Тип срока оплаты справочник", required = true)
    private String paymentTermTypeId;

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

    @NotNull(message = "must not be null")
    private String statusId;

    /**
     * Ссылка
     */
    @Schema(description = "Ссылка")
    private String link;

    private ClientDTO clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public Integer getPositionCount() {
        return positionCount;
    }

    public void setPositionCount(Integer positionCount) {
        this.positionCount = positionCount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(Integer paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getPaymentTermTypeId() {
        return paymentTermTypeId;
    }

    public void setPaymentTermTypeId(String paymentTermTypeId) {
        this.paymentTermTypeId = paymentTermTypeId;
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

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ClientDTO getClientId() {
        return clientId;
    }

    public void setClientId(ClientDTO clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractDTO)) {
            return false;
        }

        ContractDTO contractDTO = (ContractDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contractDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContractDTO{" +
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
            ", clientId=" + getClientId() +
            "}";
    }
}
