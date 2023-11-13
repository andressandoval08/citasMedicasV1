package tadeo.CitasMedicasApp.servicio;

import tadeo.CitasMedicasApp.modelo.Cita;

import java.util.List;

public interface ICitaServicio {
    public List <Cita> listarCitas ();

    public Cita buscarCitaPorId(Long Id);

    public void guardarCita (Cita cita);

    public void eliminarCita (Cita cita);

}
