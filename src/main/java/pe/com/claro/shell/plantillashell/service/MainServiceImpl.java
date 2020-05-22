package pe.com.claro.shell.plantillashell.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.claro.auditoria.eai.service.registroauditoriaws.AuditRequest;
import pe.com.claro.auditoria.eai.service.registroauditoriaws.RegistroRequest;
import pe.com.claro.shell.plantillashell.dao.EaiDao;
import pe.com.claro.shell.plantillashell.exception.DBException;
import pe.com.claro.shell.plantillashell.exception.WSException;
import pe.com.claro.shell.plantillashell.proxy.RegistroAuditoriaServiceWSClient;
import pe.com.claro.shell.plantillashell.util.Constante;

@Service
public class MainServiceImpl implements MainService {

	private final Logger logger = Logger.getLogger(MainServiceImpl.class);

	@Autowired
	private EaiDao eaiDao;

	@Autowired
	private RegistroAuditoriaServiceWSClient registroAuditoriaServiceWSClient;

	public void run(String idTransaction) {
		long startTime = System.currentTimeMillis();
		String messageTransaction = "[run idTx=" + idTransaction + "]";
		logger.info(messageTransaction + "[INICIO de metodo: run]");

		try {
			eaiDao.liberarBlackList(messageTransaction);
		} catch (DBException e) {
			logger.error(messageTransaction + "Error al consultar base de datos");
			logger.error(messageTransaction + "Codigo: " + e.getCodError());
			logger.error(messageTransaction + "Mensaje: " + e.getMsjError());
		}

		try {
			AuditRequest auditRequest = new AuditRequest();
			RegistroRequest registroRequest = new RegistroRequest();
			auditRequest.setIdTransaccion(idTransaction);
			auditRequest.setIpAplicacion("10.252.1.100");
			auditRequest.setAplicacion(Constante.COE_SOA);
			auditRequest.setUsrAplicacion(Constante.COE_SOA);
			registroRequest.setTransaccion(idTransaction);
			registroRequest.setIpCliente("10.252.1.100");
			registroRequest.setNombreCliente(Constante.COE_SOA);
			registroRequest.setCuentaUsuario("PRUEBA");
			registroRequest.setTelefono("3110011");
			registroRequest.setTexto("Pruebas WS");
			registroAuditoriaServiceWSClient.registroAuditoria(messageTransaction, auditRequest, registroRequest, null);
		} catch (WSException e) {
			logger.error(messageTransaction + "Error al consumir el WS");
			logger.error(messageTransaction + "Codigo: " + e.getCodError());
			logger.error(messageTransaction + "Mensaje: " + e.getMsjError());
		}

		logger.info(messageTransaction + "[FIN de metodo: run] Tiempo total de proceso(ms): "
				+ (System.currentTimeMillis() - startTime) + " milisegundos.");
	}

}

