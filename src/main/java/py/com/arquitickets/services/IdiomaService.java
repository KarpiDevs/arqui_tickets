package py.com.arquitickets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.arquitickets.models.Idioma;
import py.com.arquitickets.models.Moneda;
import py.com.arquitickets.repositories.IdiomaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IdiomaService {

    private final IdiomaRepository idiomaRepository;

    @Autowired
    public IdiomaService(IdiomaRepository idiomaRepository) {
        this.idiomaRepository = idiomaRepository;
    }

    public List<Idioma> getAllIdiomas() {
        return idiomaRepository.findAll();
    }

    public Idioma saveIdioma(Idioma idioma) {
        return idiomaRepository.save(idioma);
    }

    public Optional<Idioma> updateCodIdioma(String codIdioma) {
        return idiomaRepository.findByCodIdioma(codIdioma);
    }

}

