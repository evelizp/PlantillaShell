package pe.com.claro.shell.plantillashell.service;

import pe.com.claro.shell.plantillashell.exception.DBException;

public interface MainService {

	public void run(String idTransaccion) throws DBException;

}

