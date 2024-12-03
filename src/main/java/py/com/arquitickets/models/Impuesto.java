package py.com.arquitickets.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "impuestos")
public class Impuesto {

    @Id
    @Column(name = "cod_impuesto", nullable = false)
    private Integer codImpuesto;

    @Column(name = "desc_impuesto", nullable = false)
    private String descImpuesto;

    @Column(name = "val_impuesto", nullable = false)
    private Long valImpuesto;

    @Column(name = "activo", nullable = false)
    private String activo;

    public Integer getCodImpuesto() {
        return codImpuesto;
    }

    public void setCodImpuesto(Integer codImpuesto) {
        this.codImpuesto = codImpuesto;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public Long getValImpuesto() {
        return valImpuesto;
    }

    public void setValImpuesto(Long valImpuesto) {
        this.valImpuesto = valImpuesto;
    }

    public String getDescImpuesto() {
        return descImpuesto;
    }

    public void setDescImpuesto(String descImpuesto) {
        this.descImpuesto = descImpuesto;
    }
}
