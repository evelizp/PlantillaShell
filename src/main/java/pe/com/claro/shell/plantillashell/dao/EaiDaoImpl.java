package pe.com.claro.shell.plantillashell.dao;

import java.sql.SQLTimeoutException;
import java.util.Map;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;
import pe.com.claro.shell.plantillashell.bean.ResponseBean;
import pe.com.claro.shell.plantillashell.exception.DBException;
import pe.com.claro.shell.plantillashell.util.Constante;
import pe.com.claro.shell.plantillashell.util.ExternalProperties;
import pe.com.claro.shell.plantillashell.util.JAXBUtilitarios;
import pe.com.claro.shell.plantillashell.util.Util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class EaiDaoImpl implements EaiDao {

	private static transient Logger logger = Logger.getLogger(EaiDaoImpl.class);

	@Autowired
	private ExternalProperties externalProperties;

	@Autowired
	@Qualifier("eaidb")
	private DataSource eaidb;

	public void liberarBlackList(String mensajeTransaccion) throws DBException {
		String mensajeMetodo = mensajeTransaccion + "[ insertarBlackList ]";

		ResponseBean responseBean = new ResponseBean();

		try {

			logger.info(mensajeMetodo + "Consultando BD " + externalProperties.dbEai);

			SimpleJdbcCall procLiberar = new SimpleJdbcCall(eaidb).withoutProcedureColumnMetaDataAccess()
					.withSchemaName(externalProperties.dbEaiOwner)
					.withProcedureName(externalProperties.pkgEaisuBlacklist + Constante.PUNTO
							+ externalProperties.spEaisuBlacklist)
					.declareParameters(new SqlOutParameter("p_codigo", OracleTypes.VARCHAR),
							new SqlOutParameter("p_mensaje", OracleTypes.VARCHAR));

			logger.info(mensajeMetodo + "Se invocara el SP: " + externalProperties.dbEaiOwner + "."
					+ externalProperties.pkgEaisuBlacklist + "." + externalProperties.spEaisuBlacklist);

			procLiberar.getJdbcTemplate().setQueryTimeout(Integer.parseInt(externalProperties.spEaisuBlacklistTimeout));

			Map<String, Object> resultMap = procLiberar.execute();

			logger.info(mensajeMetodo + "Se invoco con exito el SP: " + externalProperties.dbEaiOwner + "."
					+ externalProperties.pkgEaisuBlacklist + "." + externalProperties.spEaisuBlacklist);

			responseBean.setCodigoRespuesta(resultMap.get("p_codigo").toString());
			responseBean.setMensajeRespuesta(resultMap.get("p_mensaje").toString());

			logger.info(mensajeMetodo + "PARAMETROS [OUPUT]: ");
			logger.info(mensajeMetodo + JAXBUtilitarios.anyObjectToXmlText(responseBean));

		} catch (NestedRuntimeException e) {
			String textoError = Util.getStackTraceFromException(e);
			logger.error(mensajeTransaccion + "Se ha producido un error [NestedRuntimeException] definido como:"
					+ Constante.SALTOLINEA + textoError, e);
			String mensajeError = "";

			if (e.contains(SQLTimeoutException.class)) {
				mensajeError = externalProperties.idt2Msj;
				mensajeError += e.getMessage();
				throw new DBException(externalProperties.idt2Cod, mensajeError, e);
			} else {
				mensajeError = externalProperties.idt1Msj;
				mensajeError += "Error ejecutando [" + externalProperties.pkgEaisuBlacklist + "."
						+ externalProperties.spEaisuBlacklist + "]" + e.getMessage();
				throw new DBException(externalProperties.idt1Cod, mensajeError, e);
			}

		} catch (Exception e) {

			String textoError = Util.getStackTraceFromException(e);
			logger.error(mensajeTransaccion + "Se ha producido un error [Exception] definido como: "
					+ Constante.SALTOLINEA + textoError, e);
			String mensajeError = "";

			mensajeError = externalProperties.idt1Msj;
			mensajeError += "Error ejecutando[" + externalProperties.pkgEaisuBlacklist + "."
					+ externalProperties.spEaisuBlacklist + "]" + e.getMessage();

			throw new DBException(externalProperties.idt1Cod, mensajeError, e);

		}

	}

}

