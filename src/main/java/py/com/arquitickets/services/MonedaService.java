package py.com.arquitickets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.arquitickets.models.Empleado;
import py.com.arquitickets.models.Moneda;
import py.com.arquitickets.repositories.MonedaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MonedaService {

    private final MonedaRepository monedaRepository;

    @Autowired
    public MonedaService(MonedaRepository monedaRepository) {
        this.monedaRepository = monedaRepository;
    }

    public List<Moneda> getAllMonedas() {
        return monedaRepository.findAll();
    }

    public Moneda saveMoneda(Moneda moneda) {
        return monedaRepository.save(moneda);
    }

    public Optional<Moneda> updateCodMoneda(String codMoneda) {
        return monedaRepository.findByCodMoneda(codMoneda);
    }
}

