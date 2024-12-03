package py.com.arquitickets.models;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "propinas")
public class Propina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_propina")
    private Long codPropina;

    @Column(name = "fecha", nullable = false)
    private LocalDate  fecha;

    @Column(name = "cod_empleado", nullable = false)
    private String codEmpleado;

    @Column(name = "monto", nullable = false)
    private Double monto;

    public Long getCodPropina() {
        return codPropina;
    }

    public void setCodPropina(Long codPropina) {
        this.codPropina = codPropina;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate  fecha) {
        this.fecha = fecha;
    }

    public String getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(String codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
