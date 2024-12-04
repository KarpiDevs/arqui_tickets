package py.com.arquitickets.models;


import jakarta.persistence.*;

@Entity
@Table(name = "propinas")
public class Propinas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_propinas", nullable = false)
    private Long codPropina;

    @ManyToOne
    @JoinColumn(name = "cod_empleado", referencedColumnName = "cod_empleado")
    private Empleado empleado;

    @Column(name = "monto", nullable = false)
    private Double monto;

    public Long getCodPropina() {
        return codPropina;
    }

    public void setCodPropina(Long codPropina) {
        this.codPropina = codPropina;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
