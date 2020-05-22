package pe.com.claro.shell.plantillashell.proxy;

import pe.com.claro.auditoria.eai.service.registroauditoriaws.AuditRequest;
import pe.com.claro.auditoria.eai.service.registroauditoriaws.ListaRequest;
import pe.com.claro.auditoria.eai.service.registroauditoriaws.RegistroRequest;
import pe.com.claro.shell.plantillashell.bean.RegistroAuditoriaOutputBean;
import pe.com.claro.shell.plantillashell.exception.WSException;

public interface RegistroAuditoriaServiceWSClient {

	public RegistroAuditoriaOutputBean registroAuditoria(String mensajeTransaccion, AuditRequest auditRequest,
			RegistroRequest registroRequest, ListaRequest listaRequest) throws WSException;

}

