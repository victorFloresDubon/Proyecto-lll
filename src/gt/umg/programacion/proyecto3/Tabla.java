package gt.umg.programacion.proyecto3;

import java.util.*;
public class Tabla {

	private int indice;
	private String nombreTabla;
	private int tamanio;
	private long posicion;
	private String campo;
	private String tipoDato;
	private byte[] bytesNombreTabla;
	private byte[] bytesTamanioTabla;
	private int bytes = 1;
	private List<Propiedad> propiedades;

	public int getIndice() {
		return indice;
	}
	public List<Propiedad> getPropiedades() {
		return propiedades;
	}
	public void setPropiedades(List<Propiedad> propiedades) {
		this.propiedades = propiedades;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	public String getNombreTabla() {
		return nombreTabla;
	}
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
		bytesNombreTabla = new byte[30];
		for (int i = 0; i < nombreTabla.length(); i++) {
			bytesNombreTabla[i] = (byte)nombreTabla.charAt(i);
		}
	}
	public int getTamanio() {
		return tamanio;
	}
	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}
	public long getPosicion() {
		return posicion;
	}
	public void setPosicion(long posicion) {
		this.posicion = posicion;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
	public byte[] getBytesNombreTabla() {
		return bytesNombreTabla;
	}
	public void setBytesNombreTabla(byte[] bytesNombreTabla) {
		this.bytesNombreTabla = bytesNombreTabla;
		nombreTabla = new String(bytesNombreTabla);
	}
	public byte[] getBytesTamanioTabla() {
		return bytesTamanioTabla;
	}
	public void setBytesTamanioTabla(byte[] bytesTamanioTabla) {
		this.bytesTamanioTabla = bytesTamanioTabla;
	}
	public int getBytes() {
		bytes = 1;
		for (Propiedad atributo : propiedades) {
			bytes = bytes + atributo.getBytes();
		}
		return bytes;
	}
	public void setBytes(int bytes) {
		this.bytes = bytes;
	}
	public void setPropiedad(Propiedad propiedad) {
		if (this.propiedades == null) {
			this.propiedades = new ArrayList<>();
		}
		this.propiedades.add(propiedad);
		this.tamanio = this.propiedades.size();
	}
	public void removePropiedad(Propiedad propiedad) {
		if (this.propiedades != null) {
			if (this.propiedades.size() > 0) {
				this.propiedades.remove(propiedad);
				this.tamanio = this.propiedades.size();
			}
		}
	}
}
