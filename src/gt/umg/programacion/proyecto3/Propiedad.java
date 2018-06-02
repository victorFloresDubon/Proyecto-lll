package gt.umg.programacion.proyecto3;


public class Propiedad {

	private int indice;
	private String nombreCampo;
	private String nombreTipoDato;
	private int valorTipoDato;
	private int longitud;
	private int bytes;
	private boolean pideLongitud;
	private byte[] bytesNombreCampo;
	private TipoDato tipoDato;
	
	public int getIndice() {
		return indice;
	}
	
	public void setIndice(int indice) {
		this.indice = indice;
	}
	
	public String getNombreCampo() {
		return nombreCampo;
	}
	
	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
		bytesNombreCampo = new byte[30];
		for (int i = 0; i < nombreCampo.length(); i++) {
			bytesNombreCampo[i] = (byte)nombreCampo.charAt(i);
		}
	}
	
	public String getNombreTipoDato() {
		return nombreTipoDato;
	}
	
	public void setNombreTipoDato() {
		if (this.valorTipoDato == TipoDato.STRING.getValor()) {
			this.nombreTipoDato = TipoDato.STRING.name();
			this.bytes = this.longitud;
			tipoDato = TipoDato.STRING;
		}
		if (this.valorTipoDato == TipoDato.CHAR.getValor()) {
			this.nombreTipoDato = TipoDato.CHAR.name();
			this.bytes = 1;
			tipoDato = TipoDato.CHAR;
		}
		if (this.valorTipoDato == TipoDato.DATE.getValor()) {
			this.nombreCampo = TipoDato.DATE.name();
			this.bytes = 28;
			tipoDato = TipoDato.DATE;
		}
		if (this.valorTipoDato == TipoDato.FLOAT.getValor()) {
			this.nombreCampo = TipoDato.FLOAT.name();
			this.bytes = 4;
			tipoDato = TipoDato.FLOAT;
		}
		if (this.valorTipoDato == TipoDato.DOUBLE.getValor()) {
			
		}
	}
	
	public int getValorTipoDato() {
		return valorTipoDato;
	}
	
	public void setValorTipoDato(int valorTipoDato) {
		this.valorTipoDato = valorTipoDato;
		if (valorTipoDato == TipoDato.STRING.getValor()) {
			this.pideLongitud = true;
		}
	}
	
	public int getLongitud() {
		return longitud;
	}
	
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	
	public byte[] getBytesNombreCampo() {
		return bytesNombreCampo;
	}
	
	public void setBytesNombreCampo(byte[] bytesNombreCampo) {
		this.bytesNombreCampo = bytesNombreCampo;
		nombreCampo = new String(bytesNombreCampo);
	}
	
	public int getBytes() {
		return bytes;
	}
	
	public boolean isPideLongitud() {
		return pideLongitud;
	}
	
	public TipoDato getTipoDato() {
		return tipoDato;
	}
	
}

