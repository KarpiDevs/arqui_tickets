package py.com.arquitickets.models;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_producto")
    private Long codProducto;

    @Column(name = "desc_producto", nullable = false)
    private String descProducto;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "cod_categoria", referencedColumnName = "cod_categoria")
    private Categoria categoria;

    @Column(name = "stock_actual", nullable = true)
    private Double stockActual;

    @Column(name = "stock_minimo", nullable = true)
    private Double stockMinimo;

    @Column(name = "cod_impuesto", nullable = false)
    private Integer codImpuesto;

    public Long getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Long codProducto) {
        this.codProducto = codProducto;
    }

    public String getDescProducto() {
        return descProducto;
    }

    public void setDescProducto(String descProducto) {
        this.descProducto = descProducto;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Double getStockActual() { return stockActual; }

    public void setStockActual(Double stockActual) {this.stockActual = stockActual; }

    public Double getStockMinimo() { return stockMinimo; }

    public void setStockMinimo(Double stockMinimo) {this.stockMinimo = stockMinimo; }

    public Integer getCodImpuesto() {
        return codImpuesto;
    }

    public void setCodImpuesto(Integer codImpuesto) {
        this.codImpuesto = codImpuesto;
    }
}
