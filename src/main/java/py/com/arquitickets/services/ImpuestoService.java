package py.com.arquitickets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.arquitickets.models.Impuesto;
import py.com.arquitickets.repositories.ImpuestoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ImpuestoService {

    private final ImpuestoRepository impuestoRepository;

    @Autowired
    public ImpuestoService(ImpuestoRepository impuestoRepository) {
        this.impuestoRepository = impuestoRepository;
    }

    public List<Impuesto> getAllImpuestos() {
        return impuestoRepository.findAll();
    }

    public Impuesto saveImpuesto(Impuesto impuesto) {
        return impuestoRepository.save(impuesto);
    }

    public Optional<Impuesto> getImpuestoById(Long id) {
        return impuestoRepository.findById(id);
    }

}

