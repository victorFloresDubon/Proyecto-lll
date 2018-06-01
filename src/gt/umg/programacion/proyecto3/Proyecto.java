package gt.umg.programacion.proyecto3;

import java.util.Scanner;
import java.io.*;

public class Proyecto {

	static final String RUTA="/proyecto/definicion.txt";
	static Scanner sc = new Scanner(System.in);
	RandomAccessFile definicion = null;
	
	//verificar si archivo de definicion existe
	public boolean verificaArchivo() {
		File f = new File(RUTA);
		if (f.exists()) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean verificaTabla(String tabla) {
		try {
			RandomAccessFile raf = new RandomAccessFile(RUTA,"r");
			try {
				long longitud = raf.length();
				if (longitud <= 0) {
					System.out.println("No existen registros");
				    return false;
			    }
			}catch(IOException e) {
				System.out.println("Error: "+e.getMessage());
			}
			boolean encontrado = false;
			//nos posicionamos al inicio del archivo
			raf.seek(0);
			//
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public boolean crearTabla() {
		Tabla t = new Tabla();
		boolean encontrado = false;
		System.out.print("Nombre de la tabla: ");
		t.setNombreTabla(sc.nextLine());
		try {
			definicion = new RandomAccessFile(RUTA,"r");
			do {
				System.out.print("Nombre de la tabla: ");
			    t.setNombreTabla(sc.nextLine());
			    if ()
			}while(encontrado == false);

			
		}catch(IOException e) {
			System.out.println("Error: "+e.getMessage());
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
}
