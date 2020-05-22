package pe.com.claro.shell.plantillashell.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class Util {

	public static String obtenerMensajeProperties(Integer cantidad, String[] valores, String mensaje) {

		String valor = Constante.VACIO;
		Object[] object = null;
		MessageFormat form = new MessageFormat(mensaje);

		object = new Object[cantidad];

		for (int i = 0; i <= cantidad - 1; i++) {
			object[i] = valores[i];
		}

		valor = form.format(object);

		return valor;
	}

	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static double round(double val, int places) {
		long factor = (long) Math.pow(10, places);
		double valor = val * factor;
		long tmp = Math.round(valor);
		return (double) tmp / factor;
	}

	public static java.sql.Date getSQLDate() {
		return new java.sql.Date(Calendar.getInstance().getTime().getTime());
	}

	public static Date getUtilDateDeMiliseg(long milisegundos) {
		Date fecha = new Date();
		fecha.setTime(milisegundos);
		return fecha;
	}

	public static Date getUtilDateDeMiliseg(String milisegundos) {
		if (milisegundos == null || milisegundos.trim().equals(Constante.VACIO)) {
			return null;
		}
		Date fecha = new Date();
		fecha.setTime(Long.parseLong(milisegundos));
		return fecha;
	}

	public static String getDateFormato(Date fecha, String formato) {
		if (fecha == null) {
			return Constante.VACIO;
		}
		return new SimpleDateFormat(formato).format(fecha);
	}

	public static String getDateFormato(String fecha, String formato) {
		if (fecha == null || fecha.trim().equals(Constante.VACIO)) {
			return Constante.VACIO;
		}
		return new SimpleDateFormat(formato).format(fecha);
	}

	public static String getDateFormato(Calendar fecha, String formato) {
		if (fecha == null) {
			return Constante.VACIO;
		}
		return new SimpleDateFormat(formato).format(fecha.getTime());
	}

	public static String getDateFormato(long fecha, String formato) {
		return new SimpleDateFormat(formato).format(fecha);
	}

	public static Boolean validarUsuario(String user, String usuarios) {

		Boolean userValido = false;
		String[] strUsers = usuarios.split(Constante.COMA);

		if (strUsers != null && strUsers.length > 0) {
			for (String s : strUsers) {
				if (s.equalsIgnoreCase(user)) {
					return true;
				}
			}
		}

		return userValido;
	}

	public static String getStackTraceFromException(Exception exception) {
		StringWriter stringWriter = new StringWriter();
		exception.printStackTrace(new PrintWriter(stringWriter, true));
		return stringWriter.toString();
	}
}
