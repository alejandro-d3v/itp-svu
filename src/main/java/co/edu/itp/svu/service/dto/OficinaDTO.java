package co.edu.itp.svu.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.svu.domain.Oficina} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OficinaDTO implements Serializable {

    private String id;

    @NotNull
    private String nombre;

    private String descripcion;

    @NotNull
    private String nivel;

    private String oficinaSuperior;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getOficinaSuperior() {
        return oficinaSuperior;
    }

    public void setOficinaSuperior(String oficinaSuperior) {
        this.oficinaSuperior = oficinaSuperior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OficinaDTO)) {
            return false;
        }

        OficinaDTO oficinaDTO = (OficinaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, oficinaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OficinaDTO{" +
            "id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", nivel='" + getNivel() + "'" +
            ", oficinaSuperior='" + getOficinaSuperior() + "'" +
            "}";
    }
}
