package gt.umg.programacion.proyecto3;

public class Tabla {
	private String nombreTabla;
	private String campo;
	private String tipoDato;
	private byte[] bytesTabla;
	public String getNombreTabla() {
		return nombreTabla;
	}
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
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
	public byte[] getBytesTabla() {
		return bytesTabla;
	}
	public void setBytesTabla(byte[] bytesTabla) {
		this.bytesTabla = bytesTabla;
	}
}
