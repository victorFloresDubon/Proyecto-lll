package gt.edu.umg.programacion.parcial.a;

import java.io.File;
import java.io.RandomAccessFile;

public class Cola {

	private static final String rutData = "C:\\Users\\Rolando\\Documents\\TAREAS\\PROGRAMACION I\\JAVA\\gt.edu.umg.programacion.parcial.final.a\\src\\gt\\edu\\umg\\programacion\\parcial\\a\\data.dat";
	private static final String rutHistorico = "C:\\Users\\Rolando\\Documents\\TAREAS\\PROGRAMACION I\\JAVA\\gt.edu.umg.programacion.parcial.final.a\\src\\gt\\edu\\umg\\programacion\\parcial\\a\\historico.dat";
	private Nodo raiz, cima;
	int tamano;
	public Cola() {
		raiz = null;
		cima = null;
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
		}
		System.out.println("Muestra eliminada: "+aux);
		return aux;
	}
	//Lista datos de Muestras de las colas actuales
	public void listarMuestras() {
		Nodo temp = raiz;
		System.out.println("\t\tMuestras");
		while(temp != null) {
			System.out.println("Codigo: "+temp.dato.getCodigo());
			System.out.println("Nombre: "+temp.dato.getNombre());
			System.out.println("\n");
			temp = temp.siguiente;
		}
	}
	
	public boolean cargarDatos() {
		Nodo tmp = raiz;
		try {
			RandomAccessFile data = new RandomAccessFile(rutData, "rw");
			RandomAccessFile historico = new RandomAccessFile(rutHistorico, "rw");
			File tmpData = new File(rutData);
			long longData = tmpData.length();
			long lonHistorico = historico.length();
			//si la longitud es cero elimina el archivo
			if(longData >= 0) {
				tmpData.delete();
			}
			//Muestra tmpMuestra = new Muestra();
			//Instanciamos un nuevo archivo

			//Se posiciona el cursor al inicio del archivo
			data.seek(0);
			while(true) {//escribe los datos en el DATA.DAT
				data.writeInt(tmp.dato.getCodigo());
				data.writeUTF(tmp.dato.getNombre());
				break;
			}
			//escribimos los datos en el archivo historico
			if (lonHistorico <= 0) {
				while(true) {//escribe los datos en el DATA.DAT
					historico.writeInt(tmp.dato.getCodigo());
					historico.writeUTF(tmp.dato.getNombre());
					break;
				}
			}else {
				historico.seek(lonHistorico);//nos posicionamos al final del fichero
				while(true) {//escribe los datos en el DATA.DAT
					historico.writeInt(tmp.dato.getCodigo());
					historico.writeUTF(tmp.dato.getNombre());
					break;
				}
			}
			return true;

		}catch(Exception e){
			System.out.println("Error: "+e.getMessage());
			return false;
		}
	}
	public void vaciar() {
		while (!colaVacia()) {
			raiz = raiz.siguiente;
		}
	}
}

