package pe.com.claro.shell.plantillashell.proxy;

import javax.xml.ws.Holder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;
import pe.com.claro.auditoria.eai.service.registroauditoriaws.AuditRequest;
import pe.com.claro.auditoria.eai.service.registroauditoriaws.AuditResponse;
import pe.com.claro.auditoria.eai.service.registroauditoriaws.ListaRequest;
import pe.com.claro.auditoria.eai.service.registroauditoriaws.ListaResponse;
import pe.com.claro.auditoria.eai.service.registroauditoriaws.RegistroAuditoriaPortType;
import pe.com.claro.auditoria.eai.service.registroauditoriaws.RegistroRequest;
import pe.com.claro.shell.plantillashell.bean.RegistroAuditoriaOutputBean;
import pe.com.claro.shell.plantillashell.exception.WSException;
import pe.com.claro.shell.plantillashell.util.Constante;
import pe.com.claro.shell.plantillashell.util.ExternalProperties;
import pe.com.claro.shell.plantillashell.util.JAXBUtilitarios;

@Service
public class RegistroAuditoriaServiceWSClientImpl implements RegistroAuditoriaServiceWSClient {

	private static final Logger logger = Logger.getLogger(RegistroAuditoriaServiceWSClientImpl.class);

	@Autowired
	private ExternalProperties properties;

	@Autowired
	@Qualifier(value = "RegistroAuditoriaService")
	private RegistroAuditoriaPortType registroAuditoriaPortType;

	@Override
	public RegistroAuditoriaOutputBean registroAuditoria(String mensajeTransaccion, AuditRequest auditRequest,
			RegistroRequest registroRequest, ListaRequest listaRequest) throws WSException {
		logger.info(mensajeTransaccion + "==Inicio del metodo registroAuditoria");
		long tiempoInicio = System.currentTimeMillis();
		RegistroAuditoriaOutputBean output = new RegistroAuditoriaOutputBean();
		final Holder<AuditResponse> auditResponse = new Holder<>();
		final Holder<ListaResponse> listaResponse = new Holder<>();

		try {

			logger.info(mensajeTransaccion + "WSDL de servicio: " + properties.wsRegistroAuditoriaEnpointAddress);
			logger.info(mensajeTransaccion + "TimeOut de servicio request: "
					+ properties.wsRegistroAuditoriaMaxRequestTimeout);
			logger.info(mensajeTransaccion + "TimeOut de servicio connection: "
					+ properties.wsRegistroAuditoriaMaxConnectionTimeout);
			logger.info(mensajeTransaccion + "Metodo a ejecutar: registroAuditoria");
			logger.info(mensajeTransaccion + "Parametros entrada: auditRequest "
					+ JAXBUtilitarios.anyObjectToXmlText(auditRequest));
			logger.info(mensajeTransaccion + "Parametros entrada: registroRequest "
					+ JAXBUtilitarios.anyObjectToXmlText(registroRequest));

			registroAuditoriaPortType.registroAuditoria(auditRequest, registroRequest, listaRequest, auditResponse,
					listaResponse);

			output.setIdTransaccion(auditResponse.value.getIdTransaccion());
			output.setCodigoRespuesta(auditResponse.value.getCodigoRespuesta());
			output.setMensajeRespuesta(auditResponse.value.getMensajeRespuesta());

			logger.info(mensajeTransaccion + "Parametros salida: output " + JAXBUtilitarios.anyObjectToXmlText(output));

		} catch (NestedRuntimeException e) {
			logger.error(mensajeTransaccion + " Error invocando registroAuditoria : ", e);
			throw new WSException(properties.idt2Cod,
					properties.idt2Msj.replace(Constante.BD_WS, properties.wsRegistroAuditoriaNombre)
							.replace(Constante.RECURSO, properties.wsRegistroAuditoriaMetodoRegistroAuditoria),
					e);
		} catch (Exception e) {
			logger.error(mensajeTransaccion + " Error invocando registroAuditoria : ", e);
			throw new WSException(properties.idt1Cod,
					properties.idt1Msj.replace(Constante.BD_WS, properties.wsRegistroAuditoriaNombre)
							.replace(Constante.RECURSO, properties.wsRegistroAuditoriaMetodoRegistroAuditoria),
					e);
		} finally {
			logger.info(mensajeTransaccion + "==Fin del metodo registroAuditoria");
			logger.info(mensajeTransaccion + "Tiempo TOTAL Proceso: [" + (System.currentTimeMillis() - tiempoInicio)
					+ " milisegundos ]");
		}

		return output;

	}

}
