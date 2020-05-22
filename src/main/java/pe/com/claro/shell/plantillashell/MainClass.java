package pe.com.claro.shell.plantillashell;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import pe.com.claro.shell.plantillashell.service.MainService;
import pe.com.claro.shell.plantillashell.util.Constante;
import pe.com.claro.shell.plantillashell.util.ExternalProperties;

@Component
public class MainClass {

	private static Logger logger = Logger.getLogger(MainClass.class);
	private static ApplicationContext objContextoSpring;

	@Autowired
	private ExternalProperties externalProperties;

	@Autowired
	private MainService mainService;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String idTransaction = args[0];
		String messageTransaction = "[main idTx=" + idTransaction + "]";

		try {
			objContextoSpring = new ClassPathXmlApplicationContext(Constante.CONFIG_PATH);
			MainClass mainClass = objContextoSpring.getBean(MainClass.class);
			mainClass.loadLog4J(idTransaction);
			logger.info(messageTransaction + "[INICIO de metodo: main]");
			mainClass.launch(idTransaction);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(messageTransaction + "Error al Levantar Contexto: " + e + ".");

		} finally {
			try {
				logger.info(messageTransaction + "[FIN de metodo: main] Tiempo total de proceso(ms): "
						+ (System.currentTimeMillis() - startTime) + Constante.ESPACIO + Constante.MILISEGUNDOS);
				((ConfigurableApplicationContext) objContextoSpring).close();
			} catch (Exception e) {
				logger.error(messageTransaction + "Error al cerrar Contexto: " + e + ".");
			}
		}
	}

	public void loadLog4J(String idTransaction) {

		long startTime = System.currentTimeMillis();
		String messageTransaction = "[loadLog4J idTx=" + idTransaction + "]";

		PropertyConfigurator.configure(externalProperties.log4jDir);

		logger.info(messageTransaction + "[INICIO de metodo: loadLog4J]");
		logger.info(messageTransaction + "[FIN de metodo: loadLog4J] Tiempo total de proceso(ms): "
				+ (System.currentTimeMillis() - startTime) + Constante.ESPACIO + Constante.MILISEGUNDOS);

	}

	public void launch(String idTransaction) {

		long startTime = System.currentTimeMillis();
		String messageTransaction = "[launch idTx=" + idTransaction + "]";

		logger.info(messageTransaction + "[INICIO de metodo: launch]");

		try {
			mainService.run(idTransaction);
		} catch (Exception e) {
			logger.error(messageTransaction + "Error: " + e + ".");
		}

		logger.info(messageTransaction + "[FIN de metodo: launch] Tiempo total de proceso(ms): "
				+ (System.currentTimeMillis() - startTime) + Constante.ESPACIO + Constante.MILISEGUNDOS);
	}

}

