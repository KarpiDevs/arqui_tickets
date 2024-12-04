package py.com.arquitickets.models;

import jakarta.persistence.*;

@Entity
@Table(name = "metodos_pagos")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_mto_pago")
    private Long codMetodoPago;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    public Long getCodMetodoPago() {
        return codMetodoPago;
    }

    public void setCodMetodoPago(Long codMetodoPago) {
        this.codMetodoPago = codMetodoPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
