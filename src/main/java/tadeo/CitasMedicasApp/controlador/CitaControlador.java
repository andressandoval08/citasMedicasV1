package tadeo.CitasMedicasApp.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tadeo.CitasMedicasApp.modelo.Cita;
import tadeo.CitasMedicasApp.modelo.InformeCitas;
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

    public InformeCitas generarInforme() {
        List<Cita> citas = repositorioCita.findAll();

        if (citas.isEmpty()) {
            return null; // Puedes manejar el caso de que no haya citas
        }

        Cita citaMasCara = citas.get(0);
        Cita citaMasBarata = citas.get(0);

        double totalPrecioCitas = 0;
        for (Cita cita : citas) {
            if (cita.getPrecio() > citaMasCara.getPrecio()) {
                citaMasCara = cita;
            }
            if (cita.getPrecio() < citaMasBarata.getPrecio()) {
                citaMasBarata = cita;
            }

            totalPrecioCitas += cita.getPrecio();
        }

        double promedioPrecios = totalPrecioCitas / citas.size();

        InformeCitas informe = new InformeCitas();
        informe.setPacienteConCitaMasCara(citaMasCara.getPaciente());
        informe.setPacienteConCitaMasBarata(citaMasBarata.getPaciente());
        informe.setPromedioPreciosCitas(promedioPrecios);
        informe.setValorTotalCitas(totalPrecioCitas);

        return informe;
    }


}
