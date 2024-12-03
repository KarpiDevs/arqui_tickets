package py.com.arquitickets.models;


import jakarta.persistence.*;

@Entity
@Table(name = "propinas")
public class Propinas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_propinas", nullable = false)
    private Long codPropina;

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
