package py.com.arquitickets.models;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_mesa")
    private Long nroMesa;

    @Column(name = "desc_mesa", nullable = false)
    private String descMesa;

    @Column(name = "cantidad_sillas", nullable = false)
    private Long cantSillas;

    @ManyToOne
    @JoinColumn(name = "cod_empleado", referencedColumnName = "cod_empleado")
    private Empleado empleado;

    public Long getNroMesa() {
        return nroMesa;
    }

    public void setNroMesa(Long nroMesa) {
        this.nroMesa = nroMesa;
    }

    public String getDescMesa() {
        return descMesa;
    }

    public void setDescMesa(String descMesa) {
        this.descMesa = descMesa;
    }

    public Long getCantSillas() { return cantSillas; }

    public void setCantSillas(Long cantSillas) { this.cantSillas = cantSillas; }

    public Empleado getEmpleado() { return empleado; }

    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
}
