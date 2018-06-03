package gt.edu.umg.programacion.parcial.a;

public class Cola {

	private Nodo raiz, cima;
	int tamano;
	public Cola() {
		raiz = null;
		cima = null;
		tamano = 0;
	}
	public boolean colaVacia(){
		if (raiz == null) {
			return true;
		}else {
			return false;
		}
	}
	//Agregar muestra
	public void agregarMuestra(Muestra dato) {
		Nodo nuevo = new Nodo(dato);
		nuevo.siguiente = null;
		if(colaVacia()) {
			raiz = nuevo;
			cima = nuevo;
		}else {
			cima.siguiente = nuevo;
			cima = nuevo;
		}
		tamano++;
	}
	//Quitar datos de Muestra
	public Muestra quitarMuestra() {
		if (colaVacia()) {
			System.out.println("No hay datos de la muestra");
			return null;
		}
		Muestra aux = raiz.dato;
		if (raiz == cima) {
			raiz = null;
			cima = null;
		}else {
			raiz = raiz.siguiente;
			tamano--;
		}
		System.out.println("Muestra eliminada: "+aux);
		return aux;
	}
	//Lista datos de Muestras de las colas actuales
	public void listarMuestras() {
		Nodo temp = raiz;
		while(temp != null) {
			System.out.println("\t\tMuestras");
			System.out.println("Codigo: "+temp.dato.getCodigo());
			System.out.println("Nombre: "+temp.dato.getNombre());
			System.out.println("\n");
			System.out.println("\n");
			temp = temp.siguiente;
		}
	}
	
}
