package pe.com.claro.shell.plantillashell.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExternalProperties {

	@Value("${log4j.dir}")
	public String log4jDir;

	@Value("${db.eai.nombre}")
	public String dbEai;

	@Value("${db.eai.owner}")
	public String dbEaiOwner;

	@Value("${db.eai.timeout}")
	public String dbEaiTimeout;

	@Value("${db.eai.pkg.eaisu.blacklist}")
	public String pkgEaisuBlacklist;

	@Value("${db.eai.sp.eaisu.blacklist}")
	public String spEaisuBlacklist;

	@Value("${db.eai.sp.eaisu.blacklist.timeout}")
	public String spEaisuBlacklistTimeout;

	@Value("${idt1.codigo}")
	public String idt1Cod;

	@Value("${idt1.mensaje}")
	public String idt1Msj;

	@Value("${idt2.codigo}")
	public String idt2Cod;

	@Value("${idt2.mensaje}")
	public String idt2Msj;

	@Value("${ws.registro.auditoria.max.request.timeout}")
	public String wsRegistroAuditoriaMaxRequestTimeout;

	@Value("${ws.registro.auditoria.max.connection.timeout}")
	public String wsRegistroAuditoriaMaxConnectionTimeout;

	@Value("${ws.registro.auditoria.enpointAddress}")
	public String wsRegistroAuditoriaEnpointAddress;

	@Value("${ws.registro.auditoria.nombre}")
	public String wsRegistroAuditoriaNombre;

	@Value("${ws.registro.auditoria.metodo.registroAuditoria}")
	public String wsRegistroAuditoriaMetodoRegistroAuditoria;

}

