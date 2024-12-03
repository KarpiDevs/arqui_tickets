package py.com.arquitickets.models;

import jakarta.persistence.*;

@Entity
@Table(name = "monedas")
public class Moneda {

    @Id
    @Column(name = "cod_moneda", nullable = false)
    private String codMoneda;

    @Column(name = "desc_moneda", nullable = false)
    private String descMoneda;

    @Column(name = "simbolo", nullable = false)
    private String simbolo;

    @Column(name = "activo", nullable = false)
    private String activo;

    public String getCodMoneda() {
        return codMoneda;
    }

    public void setCodMoneda(String codMoneda) {
        this.codMoneda = codMoneda;
    }

    public String getDescMoneda() {
        return descMoneda;
    }

    public void setDescMoneda(String descMoneda) {
        this.descMoneda = descMoneda;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
