package tadeo.CitasMedicasApp.controlador;

import tadeo.CitasMedicasApp.modelo.Cita;

import java.util.List;

public interface ICitaControlador {
    public List <Cita> listarCitas ();

    public Cita buscarCitaPorId(Long Id);

    public void guardarCita (Cita cita);

    public void eliminarCita (Cita cita);

}
