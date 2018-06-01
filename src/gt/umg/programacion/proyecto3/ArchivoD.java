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
						t.setPropiedad(p);
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
/*===================================================================================================================*/
/*Describe una entidad*/
	public void describeEntidad(Tabla tabla) {
		System.out.println("Indice: "+tabla.getIndice());
		System.out.println("Nombre: "+tabla.getNombreTabla());
		System.out.println("Cantidad de campos: "+tabla.getTamanio());
		System.out.println("Propiedades: ");
		int i = 1;
		for (Propiedad propiedad : tabla.getPropiedades()) {
			System.out.println("No. "+i);
			System.out.println("Nombre del Campo: "+propiedad.getNombreCampo());
			System.out.println("Tipo de dato: "+propiedad.getNombreTipoDato());
			if (propiedad.isPideLongitud()) {
				System.out.println("Longitud: "+propiedad.getLongitud());
			}
			i++;
		}
	}
/*===================================================================================================================*/
/*Crea una entidad*/
	public boolean creaEntidad() {
		boolean creado = false;
		try {
			Tabla tabla = new Tabla();
			tabla.setIndice(listaTablas.size() + 1);
			System.out.println("Ingrese el nombre de nueva entidad");
			String strNombre = "";
			int longitud = 0;
			do {
				strNombre = sc.nextLine();
				longitud = strNombre.length();
				if (longitud < 2 || longitud > 30) {
					System.out.println("Longitud NO válida [de 2 a 30]");
				}else {
					if (strNombre.contains(" ")) {
						System.out.println("Nombre no debe de tener espacios en blanco, utilizar guion bajo");
						longitud = 0;
					}
				}
			}
			while(longitud < 2 || longitud > 30);
				tabla.setNombreTabla(strNombre);
				System.out.println("Propiedades de la Entidad");
				boolean flgDetener = false;
				System.out.println("Escriba el nombre del atributo no. " + (tabla.getTamanio() + 1));
				do {
					Propiedad propiedad = new Propiedad();
					propiedad.setIndice(tabla.getIndice());
					flgDetener = false;
					do {
						strNombre = sc.nextLine();
						longitud = strNombre.length();
						if (longitud < 2 || longitud > 30) {
							System.out.println("Longitud NO válida [de 2 a 30]");
						}else {
							if (strNombre.contains(" ")) {
								System.out.println("Nombre no debe de tener espacios en blanco, utilizar guion bajo");
								flgDetener = false;
							}
						}
					}while(longitud < 2 || longitud > 30);
					propiedad.setNombreCampo(strNombre);
					System.out.println("Escoja el tipo de dato");
					System.out.println(TipoDato.INT.getValor() + " .......... " + TipoDato.INT.name());
					System.out.println(TipoDato.LONG.getValor() + " .......... " + TipoDato.LONG.name());
					System.out.println(TipoDato.STRING.getValor() + " .......... " + TipoDato.STRING.name());
					System.out.println(TipoDato.DOUBLE.getValor() + " .......... " + TipoDato.DOUBLE.name());
					System.out.println(TipoDato.FLOAT.getValor() + " .......... " + TipoDato.FLOAT.name());
					System.out.println(TipoDato.DATE.getValor() + " .......... " + TipoDato.DATE.name());
					System.out.println(TipoDato.CHAR.getValor() + " .......... " + TipoDato.CHAR.name());
					propiedad.setValorTipoDato(sc.nextInt());
					if (propiedad.isPideLongitud()) {
						System.out.println("Ingrese la longitud del campo");
						propiedad.setLongitud(sc.nextInt());
					}else {
						propiedad.setLongitud(0);
					}
					propiedad.setNombreTipoDato();
					tabla.setPropiedad(propiedad);
					System.out.println("Desea ingesar otro campo? S/N");
					flgDetener = sc.nextBoolean();
				}while(flgDetener != false);
				System.out.println("Los datos a registrar son: ");
				describeEntidad(tabla);
				System.out.println("Presione 1 para guardar 0 para cancelar");
				longitud = sc.nextInt();
				if (longitud == 1) {
					// primero guardar atributos
					// establecer la posicion inicial donde se va a guardar
					tabla.setPosicion(propiedad.length());
					propiedad.seek(propiedad.length());//longitud del archivo
					for (Propiedad p: tabla.getPropiedades()) {
						propiedad.writeInt(p.getIndice());
						propiedad.write(p.getBytesNombreCampo());
						propiedad.writeInt(p.getValorTipoDato());
						propiedad.writeInt(p.getLongitud());
						propiedad.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
					}
					// Grabar entidades
					entidad.writeInt(tabla.getIndice());
					entidad.write(tabla.getBytesNombreTabla());
					entidad.writeInt(tabla.getTamanio());
					entidad.writeInt(tabla.getBytes());
					entidad.writeLong(tabla.getPosicion());
					entidad.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
					listaTablas.add(tabla);
					creado = true;
				} else {
					System.out.println("No se guardo la entidad debido a que el usuario cancelo la tarea");
					creado = false;
				}
				System.out.println("Presione Enter para continuar");
				System.in.read();
	}catch (Exception e){
		creado = false;
		e.printStackTrace();
	}
		return creado;
}

/*===================================================================================================================*/

	public static void main(String[]args) {
		ArchivoD ad = new ArchivoD();
		ad.mIni();
		System.exit(0);
	}
	
	public void mIni() {
		
		int op=0;
		try {
			
			f = new RandomAccessFile (dataBase,"rw");
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
