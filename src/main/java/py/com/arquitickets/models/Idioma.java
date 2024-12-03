package py.com.arquitickets.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "idiomas")
public class Idioma {

    @Id
    @Column(name = "cod_idioma", nullable = false)
    private String codIdioma;

    @Column(name = "desc_idioma", nullable = false)
    private String descIdioma;

    @Column(name = "activo", nullable = false)
    private String activo;

    public String getCodIdioma() {
        return codIdioma;
    }

    public void setCodIdioma(String codIdioma) {
        this.codIdioma = codIdioma;
    }

    public String getDescIdioma() {
        return descIdioma;
    }

    public void setDescIdioma(String descIdioma) {
        this.descIdioma = descIdioma;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
