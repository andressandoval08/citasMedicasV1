package tadeo.CitasMedicasApp.modelo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InformeCitas {
    private String pacienteConCitaMasCara;
    private String pacienteConCitaMasBarata;
    private double promedioPreciosCitas;
    private double valorTotalCitas;

}
