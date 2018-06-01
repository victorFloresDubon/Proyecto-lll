package gt.umg.programacion.proyecto3;

import java.io.*;
import java.util.*;
import java.text.*;

public class ArchivoD {

	private static final String dataBase = ("/DB/dataBase.txt");//ruta base de datos pricipal
	private static final String entidades = ("/DB/entidades.txt");//ruta de archivos de las entidades
	private static final String propiedades = ("/DB/propiedades.txt");//ruta de las propiedades de las entidades
	static Scanner sc;
	private final int totalBytes = 85;
	private final static String formatoFecha = "dd/MM/yyyy";
	static DateFormat format = new SimpleDateFormat(formatoFecha);
	RandomAccessFile f = null;
	RandomAccessFile entidad = null;
	RandomAccessFile propiedad = null;
	private final int bytesTabla = 50, bytesPropiedad = 45;
	
	private List<Tabla> listaTablas = new ArrayList<>();
	
/*=======================================================================================================*/	
	private boolean validaDataBase() {
		boolean resultado = false;
		try {
			entidad = new RandomAccessFile(entidades, "rw");
			propiedad = new RandomAccessFile(propiedades, "rw");
			long longitud = entidad.length();
			if (longitud <= 0) {
				System.out.println("No existen datos");
				resultado = false; // finalizar el procedimiento
			}
			if (longitud >= bytesTabla) {
				// posicionarse al principio del archivo
				entidad.seek(0);
				Tabla t;
				while (longitud >= bytesTabla) {
					t = new Tabla();
					t.setIndice(entidad.readInt());
					byte[] bNombre = new byte[30]; // leer 30 bytes para el nombre
					entidad.read(bNombre);
					t.setBytesNombreTabla(bNombre);
					t.setTamanio(entidad.readInt());
					t.setBytes(entidad.readInt());
					t.setPosicion(entidad.readLong());
					entidad.readByte();// leer el cambio de linea
					longitud -= bytesTabla;
					// leer atributos
					long longitudAtributos = propiedad.length();
					if (longitudAtributos <= 0) {
						System.out.println("No hay registros");
						resultado = false; // finalizar el procedimiento
						break;
					}
					propiedad.seek(t.getPosicion());
					Propiedad p;
					longitudAtributos = t.getTamanio() * bytesPropiedad;
					while (longitudAtributos >= bytesPropiedad) {
						p = new Propiedad();
						p.setIndice(propiedad.readInt());
						byte[] bNombreAtributo = new byte[30]; // leer 30 bytes para el nombre
						propiedad.read(bNombreAtributo);
						p.setBytesNombreCampo(bNombreAtributo);
						p.setValorTipoDato(propiedad.readInt());
						p.setLongitud(propiedad.readInt());
						p.setNombreTipoDato();
						propiedad.readByte();// leer el cambio de linea
						t.setAtributo(p);
						longitudAtributos -= bytesPropiedad;
					}
					listaTablas.add(t);
				}
				if (listaTablas.size() > 0) {
					resultado = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
/*=======================================================================================================*/
	public static void main(String[]args) {
		ArchivoD ad = new ArchivoD();
		ad.mIni();
		System.exit(0);
	}
	
	public void mIni() {
		
		int op=0;
		try {
			
			f = new RandomAccessFile (ruta,"rw");
			System.out.println("****Bienvenido****");
			int cr;
			do {
				try {
					System.out.println("Ingresa la opcion que desees");
					System.out.println("1. Agregar");
					System.out.println("2. Listar");
					System.out.println("3. Buscar");
					System.out.println("4. Modificar");
					System.out.println("0. Salir");
					op = sc.nextInt();
					switch(op) {
					
					case 0: System.out.println("**Hasta luego**");
						break;
						
					case 1:
						break;
					case 2:
						break;
					case 3: 
						break;
					case 4:
						break;
						default: System.out.println("**opcion no valida, ingrese de nuevo** ");
						break;
						
					}
				}catch(Exception e) {
					System.out.println("Error"+e.getMessage());
				}
			}while (op !=0);
			f.close();
			
		}catch(Exception e) {
			System.out.println("Error"+e.getMessage());
		}
		
	}
	
	
			
	}
