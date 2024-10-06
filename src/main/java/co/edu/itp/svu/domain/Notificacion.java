package co.edu.itp.svu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Notificacion.
 */
@Document(collection = "notificacion")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("tipo")
    private String tipo;

    @NotNull
    @Field("fecha")
    private Instant fecha;

    @Field("mensaje")
    private String mensaje;

    @Field("leido")
    private Boolean leido;

    @DBRef
    @Field("destinatarios")
    @JsonIgnoreProperties(value = { "notificacions" }, allowSetters = true)
    private Set<Oficina> destinatarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Notificacion id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Notificacion tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public Notificacion fecha(Instant fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public Notificacion mensaje(String mensaje) {
        this.setMensaje(mensaje);
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeido() {
        return this.leido;
    }

    public Notificacion leido(Boolean leido) {
        this.setLeido(leido);
        return this;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public Set<Oficina> getDestinatarios() {
        return this.destinatarios;
    }

    public void setDestinatarios(Set<Oficina> oficinas) {
        this.destinatarios = oficinas;
    }

    public Notificacion destinatarios(Set<Oficina> oficinas) {
        this.setDestinatarios(oficinas);
        return this;
    }

    public Notificacion addDestinatarios(Oficina oficina) {
        this.destinatarios.add(oficina);
        return this;
    }

    public Notificacion removeDestinatarios(Oficina oficina) {
        this.destinatarios.remove(oficina);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notificacion)) {
            return false;
        }
        return getId() != null && getId().equals(((Notificacion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notificacion{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", mensaje='" + getMensaje() + "'" +
            ", leido='" + getLeido() + "'" +
            "}";
    }
}
