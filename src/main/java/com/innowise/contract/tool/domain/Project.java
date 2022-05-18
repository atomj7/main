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
 * Projects
 */
@Table("project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    /**
     * Клиент
     */
    @NotNull(message = "must not be null")
    @Column("client_id")
    private Long clientId;

    /**
     * Название проекта
     */
    @NotNull(message = "must not be null")
    @Column("name")
    private String name;

    /**
     * Дата начала
     */
    @NotNull(message = "must not be null")
    @Column("start_date")
    private LocalDate startDate;

    /**
     * Дата окончания
     */
    @Column("finish_date")
    private LocalDate finishDate;

    /**
     * Ссылка
     */
    @Column("link")
    private String link;

    /**
     * Статус дикшинари
     */
    @NotNull(message = "must not be null")
    @Column("status_id")
    private String statusId;

    @Transient
    @JsonIgnoreProperties(value = { "ids", "contractId", "projectId" }, allowSetters = true)
    private Set<Subcontract> ids = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "ids", "ids" }, allowSetters = true)
    private Client clientId;

    @Column("client_id_id")
    private Long clientIdId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public Project clientId(Long clientId) {
        this.setClientId(clientId);
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return this.name;
    }

    public Project name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Project startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return this.finishDate;
    }

    public Project finishDate(LocalDate finishDate) {
        this.setFinishDate(finishDate);
        return this;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public String getLink() {
        return this.link;
    }

    public Project link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatusId() {
        return this.statusId;
    }

    public Project statusId(String statusId) {
        this.setStatusId(statusId);
        return this;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public Set<Subcontract> getIds() {
        return this.ids;
    }

    public void setIds(Set<Subcontract> subcontracts) {
        if (this.ids != null) {
            this.ids.forEach(i -> i.setProjectId(null));
        }
        if (subcontracts != null) {
            subcontracts.forEach(i -> i.setProjectId(this));
        }
        this.ids = subcontracts;
    }

    public Project ids(Set<Subcontract> subcontracts) {
        this.setIds(subcontracts);
        return this;
    }

    public Project addId(Subcontract subcontract) {
        this.ids.add(subcontract);
        subcontract.setProjectId(this);
        return this;
    }

    public Project removeId(Subcontract subcontract) {
        this.ids.remove(subcontract);
        subcontract.setProjectId(null);
        return this;
    }

    public Client getClientId() {
        return this.clientId;
    }

    public void setClientId(Client client) {
        this.clientId = client;
        this.clientIdId = client != null ? client.getId() : null;
    }

    public Project clientId(Client client) {
        this.setClientId(client);
        return this;
    }

    public Long getClientIdId() {
        return this.clientIdId;
    }

    public void setClientIdId(Long client) {
        this.clientIdId = client;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", clientId=" + getClientId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", finishDate='" + getFinishDate() + "'" +
            ", link='" + getLink() + "'" +
            ", statusId='" + getStatusId() + "'" +
            "}";
    }
}
