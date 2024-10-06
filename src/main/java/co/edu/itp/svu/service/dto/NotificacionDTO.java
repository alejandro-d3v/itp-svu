package co.edu.itp.svu.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link co.edu.itp.svu.domain.Notificacion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificacionDTO implements Serializable {

    private String id;

    @NotNull
    private String tipo;

    @NotNull
    private Instant fecha;

    private String mensaje;

    private Boolean leido;

    private Set<OficinaDTO> destinatarios = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public Set<OficinaDTO> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(Set<OficinaDTO> destinatarios) {
        this.destinatarios = destinatarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificacionDTO)) {
            return false;
        }

        NotificacionDTO notificacionDTO = (NotificacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificacionDTO{" +
            "id='" + getId() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", mensaje='" + getMensaje() + "'" +
            ", leido='" + getLeido() + "'" +
            ", destinatarios=" + getDestinatarios() +
            "}";
    }
}
