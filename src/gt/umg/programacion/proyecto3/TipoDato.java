package gt.umg.programacion.proyecto3;

public enum TipoDato {

	
	INT(1), LONG(2), STRING(3), DOUBLE(4), FLOAT(5), DATE(6), CHAR(7);
	
	private final int valor;
    private TipoDato(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

}
