package py.com.arquitickets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.arquitickets.models.Categoria;
import py.com.arquitickets.models.MetodoPago;
import py.com.arquitickets.repositories.CategoriaRepository;
import py.com.arquitickets.repositories.MetodoPagoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    @Autowired
    public MetodoPagoService(MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public Optional<MetodoPago> getMetodoPagoById(Long id) {
        return metodoPagoRepository.findById(id);
    }
}

