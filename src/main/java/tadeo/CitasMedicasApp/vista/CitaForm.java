package tadeo.CitasMedicasApp.vista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tadeo.CitasMedicasApp.modelo.Cita;
import tadeo.CitasMedicasApp.controlador.CitaControlador;
import tadeo.CitasMedicasApp.modelo.InformeCitas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class CitaForm extends JFrame {
    CitaControlador citaControlador;
    private JPanel panel;
    private JTable tablaCitas;
    private JTextField idTexto;
    private JTextField pacienteTexto;
    private JTextField servicioTexto;
    private JTextField precioTexto;
    private JTextField horaTexto;
    private JButton agregarCitaButton;
    private JButton actualizarCitaButton;
    private JButton borrarCitaButton;
    private JButton informesButton;
    private DefaultTableModel tablaModeloCitas;

    @Autowired
    public CitaForm(CitaControlador citaControlador) {
        this.citaControlador = citaControlador;
        iniciarForma();
        agregarCitaButton.addActionListener(e -> agregarCita());
        tablaCitas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarCitaSeleccionada();
            }
        });

        actualizarCitaButton.addActionListener(e -> actualizarCita());
        borrarCitaButton.addActionListener(e -> eliminarCita());
        informesButton.addActionListener(e -> generarInforme());
    }

    private void iniciarForma() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900, 700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = toolkit.getScreenSize();
        int x = (tamanioPantalla.width - getWidth() / 2);
        int y = (tamanioPantalla.height - getHeight() / 2);
        setLocation(x, y);
    }

    private void agregarCita() {
        //Leer datos
        if (pacienteTexto.getText().equals("") || servicioTexto.getText().equals("") || precioTexto.getText().equals("") || horaTexto.getText().equals("")) {
            mostrarMensaje("Todos los campos son obligatorios.");
            pacienteTexto.requestFocusInWindow();
            return;
        }
        var nombrePaciente = pacienteTexto.getText();
        var servicio = servicioTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var hora = horaTexto.getText();

        var cita = new Cita(null, nombrePaciente, servicio, precio, hora);
        this.citaControlador.guardarCita(cita);
        mostrarMensaje("Se agrego la cita para el paciente: " + nombrePaciente + " correctamente.");
        limpiarFormulario();
        listarCitas();


    }

    private void cargarCitaSeleccionada() {
        //Indices de columnas inician en 0
        var renglon = tablaCitas.getSelectedRow();
        if (renglon != -1) { //regresa -1 si no se selecciona ningun registro
            String id = tablaCitas.getModel().getValueAt(renglon, 0).toString();
            idTexto.setText(id);
            String nombrePaciente = tablaCitas.getModel().getValueAt(renglon, 1).toString();
            pacienteTexto.setText(nombrePaciente);
            String tipoServicio = tablaCitas.getModel().getValueAt(renglon, 2).toString();
            servicioTexto.setText(tipoServicio);
            String precio = tablaCitas.getModel().getValueAt(renglon, 3).toString();
            precioTexto.setText(precio);
            String horaCita = tablaCitas.getModel().getValueAt(renglon, 4).toString();
            horaTexto.setText(horaCita);

        }
    }

    private void actualizarCita() {
        if (this.idTexto.getText().equals("")) {
            mostrarMensaje("Seleccione un registro.");
        } else {
            //verificar que no sea nulo
            if (pacienteTexto.getText().equals("")) {
                mostrarMensaje("Indique el nombre del paciente.");
                pacienteTexto.requestFocusInWindow();
                return;
            }
            long idPaciente = Integer.parseInt(idTexto.getText());
            var nombrePaciente = pacienteTexto.getText();
            var tipoServicio = servicioTexto.getText();
            var precio = Double.parseDouble(precioTexto.getText());
            var hora = horaTexto.getText();

            var cita = new Cita(idPaciente, nombrePaciente, tipoServicio, precio, hora);
            citaControlador.guardarCita(cita);
            mostrarMensaje("Se actualizo correctamente la cita de: " + nombrePaciente + ".");
            limpiarFormulario();
            listarCitas();
        }

    }

    private void eliminarCita() {
        var renglon = tablaCitas.getSelectedRow();
        if (renglon != -1) {
            String idCita = tablaCitas.getModel().getValueAt(renglon, 0).toString();
            var cita = new Cita();
            cita.setId(Long.parseLong(idCita));
            citaControlador.eliminarCita(cita);
            mostrarMensaje("La cita " + idCita + " fue eliminada correctamente.");
            limpiarFormulario();
            listarCitas();
        } else {
            mostrarMensaje("No se selecciono ninguna cita para eliminar.");
        }

    }

    private void generarInforme(){
        InformeCitas informe = citaControlador.generarInforme();

        if (informe != null) {
            mostrarVentanaInforme(informe);
        } else {
            mostrarMensaje("No hay citas para generar informe.");
        }
    }

    private void mostrarVentanaInforme(InformeCitas informe){
        String mensaje = "Paciente con la cita más cara: " + informe.getPacienteConCitaMasCara() + "\n" +
                "Paciente con la cita más barata: " + informe.getPacienteConCitaMasBarata() + "\n" +
                "Promedio de precios de las citas: " + informe.getPromedioPreciosCitas() + "\n" +
                "Valor total acumulado de las citas: " + informe.getValorTotalCitas();

        JOptionPane.showMessageDialog(this, mensaje, "Informe de Citas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarFormulario() {
        pacienteTexto.setText("");
        servicioTexto.setText("");
        precioTexto.setText("");
        horaTexto.setText("");
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //creacion idTexto
        idTexto = new JTextField("");
        idTexto.setVisible(false);

        this.tablaModeloCitas = new DefaultTableModel(0, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] cabeceros = {"Id", "Paciente", "Tipo de servicio", "precio", "Hora de la cita"};
        this.tablaModeloCitas.setColumnIdentifiers(cabeceros);
        this.tablaCitas = new JTable(tablaModeloCitas);

        //evitar seleccion varios registros
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarCitas();
    }

    private void listarCitas() {
        tablaModeloCitas.setRowCount(0);
        //Obtener citas
        var citas = citaControlador.listarCitas();
        citas.forEach((cita) -> {
            Object[] renglonCita = {
                    cita.getId(),
                    cita.getPaciente(),
                    cita.getTipoDeServicio(),
                    cita.getPrecio(),
                    cita.getHoraDeLaCita()
            };
            this.tablaModeloCitas.addRow(renglonCita);
        });
    }
}
