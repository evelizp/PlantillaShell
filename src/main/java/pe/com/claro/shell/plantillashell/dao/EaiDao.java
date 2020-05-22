package pe.com.claro.shell.plantillashell.dao;

import pe.com.claro.shell.plantillashell.exception.DBException;

public interface EaiDao {
	void liberarBlackList(String mensajeTransaccion) throws DBException;
}
