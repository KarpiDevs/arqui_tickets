package py.com.arquitickets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.arquitickets.models.Propina;
import py.com.arquitickets.repositories.PropinaRepository;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;


@Service
public class PropinaService {

    private final PropinaRepository propinaRepository;

    @Autowired
    public PropinaService(PropinaRepository propinaRepository) {
        this.propinaRepository = propinaRepository;
    }

    // Obtener todas las propinas
    public List<Propina> getAllPropinas() {
        return propinaRepository.findAll();
    }

    // Obtener una propina por ID
    public Optional<Propina> getPropinaById(Long id) {
        return propinaRepository.findById(id);
    }

    // Guardar una nueva propina o actualizar una existente
    public Propina savePropina(Propina propina) {
        return propinaRepository.save(propina);
    }

    // Obtener propinas por fecha
    public List<Propina> getPropinasByFecha(LocalDate fecha) {
        return propinaRepository.findByFecha(fecha);
    }
}