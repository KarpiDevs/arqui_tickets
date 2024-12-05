package py.com.arquitickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.arquitickets.dto.ConsumoDTO;
import py.com.arquitickets.dto.ReservaDTO;
import py.com.arquitickets.models.Mesa;
import py.com.arquitickets.models.ReservaMesa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservaMesaRepository extends JpaRepository<ReservaMesa, Long> {

    List<ReservaMesa> findByMesaAndEstadoReserva(Mesa mesa, String estadoReserva);

    List<ReservaMesa> findByNroReservaAndEstadoReserva(Long nroReserva, String estadoReserva);

    List<ReservaMesa> findByNroMesa(Integer nroMesa);

    List<ReservaMesa> findByCodEmpleado(Integer codEmpleado);

    List<ReservaMesa> findByFechaInicio(Date fechaInicio);

    @Query("SELECT new py.com.arquitickets.dto.ConsumoDTO(a.producto.codProducto, a.producto.descProducto, SUM(a.cantidad)) " +
            "FROM ReservaConsumos a GROUP BY a.producto.codProducto, a.producto.descProducto ORDER BY SUM(a.cantidad) DESC")
    List<ConsumoDTO> ventasPorProducto();

    @Query("SELECT new py.com.arquitickets.dto.ReservaDTO(a.mesa.nroMesa, a.mesa.descMesa, a.estadoReserva) FROM ReservaMesa a ORDER BY a.mesa.nroMesa DESC")
    List<ReservaDTO> ocupacionMesas();

    @Query("SELECT COUNT(a.nroReserva), SUM(a.totalConsumo) " +
            "FROM ReservaMesa a ")
    List<Object[]> ventasDiarias();

    @Query("SELECT P.codProducto, P.descProducto, P.precioUnitario, SUM(C.cantidad) " +
            "FROM Producto P " +
            "JOIN ReservaConsumos C ON P.codProducto = C.producto.codProducto " +
            "WHERE P.categoria.codCategoria = :codCategoria " +
            "GROUP BY P.codProducto, P.descProducto")
    List<Object[]> findByCategoria(@Param("codCategoria") Long codCategoria);
}

