package tadeo.CitasMedicasApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import tadeo.CitasMedicasApp.vista.CitaForm;

import java.awt.*;

@SpringBootApplication
public class CitasMedicasAppApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext contextoSpring =
				new SpringApplicationBuilder(CitasMedicasAppApplication.class)
						.headless(false)
						.web(WebApplicationType.NONE)
						.run(args);

		//Ejecucion de codigo para cargar el formulario
		EventQueue.invokeLater(()->{
			//obtener objeto form
			CitaForm citaForm = contextoSpring.getBean(CitaForm.class);
			citaForm.setVisible(true);
		});
	}

}
