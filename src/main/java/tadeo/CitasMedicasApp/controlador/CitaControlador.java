package tadeo.CitasMedicasApp.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tadeo.CitasMedicasApp.modelo.Cita;
import tadeo.CitasMedicasApp.modelo.RepositorioCita;

import java.util.List;

@Service
public class CitaControlador implements ICitaControlador {
    @Autowired
    private RepositorioCita repositorioCita;

    @Override
    public List<Cita> listarCitas() {
        return repositorioCita.findAll();
    }

    @Override
    public Cita buscarCitaPorId(Long Id) {
       Cita cita = repositorioCita.findById(Id).orElse(null);
       return cita;
    }

    @Override
    public void guardarCita(Cita cita) {
        repositorioCita.save(cita);
    }

    @Override
    public void eliminarCita(Cita cita) {
        repositorioCita.delete(cita);
    }
}
