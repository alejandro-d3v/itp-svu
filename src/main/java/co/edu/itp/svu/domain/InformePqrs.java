package co.edu.itp.svu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A InformePqrs.
 */
@Document(collection = "informe_pqrs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InformePqrs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("fecha_inicio")
    private Instant fechaInicio;

    @NotNull
    @Field("fecha_fin")
    private Instant fechaFin;

    @NotNull
    @Field("total_pqrs")
    private Integer totalPqrs;

    @NotNull
    @Field("total_resueltas")
    private Integer totalResueltas;

    @NotNull
    @Field("total_pendientes")
    private Integer totalPendientes;

    @DBRef
    @Field("oficina")
    @JsonIgnoreProperties(value = { "notificacions" }, allowSetters = true)
    private Oficina oficina;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public InformePqrs id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getFechaInicio() {
        return this.fechaInicio;
    }

    public InformePqrs fechaInicio(Instant fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return this.fechaFin;
    }

    public InformePqrs fechaFin(Instant fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getTotalPqrs() {
        return this.totalPqrs;
    }

    public InformePqrs totalPqrs(Integer totalPqrs) {
        this.setTotalPqrs(totalPqrs);
        return this;
    }

    public void setTotalPqrs(Integer totalPqrs) {
        this.totalPqrs = totalPqrs;
    }

    public Integer getTotalResueltas() {
        return this.totalResueltas;
    }

    public InformePqrs totalResueltas(Integer totalResueltas) {
        this.setTotalResueltas(totalResueltas);
        return this;
    }

    public void setTotalResueltas(Integer totalResueltas) {
        this.totalResueltas = totalResueltas;
    }

    public Integer getTotalPendientes() {
        return this.totalPendientes;
    }

    public InformePqrs totalPendientes(Integer totalPendientes) {
        this.setTotalPendientes(totalPendientes);
        return this;
    }

    public void setTotalPendientes(Integer totalPendientes) {
        this.totalPendientes = totalPendientes;
    }

    public Oficina getOficina() {
        return this.oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public InformePqrs oficina(Oficina oficina) {
        this.setOficina(oficina);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InformePqrs)) {
            return false;
        }
        return getId() != null && getId().equals(((InformePqrs) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformePqrs{" +
            "id=" + getId() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", totalPqrs=" + getTotalPqrs() +
            ", totalResueltas=" + getTotalResueltas() +
            ", totalPendientes=" + getTotalPendientes() +
            "}";
    }
}
