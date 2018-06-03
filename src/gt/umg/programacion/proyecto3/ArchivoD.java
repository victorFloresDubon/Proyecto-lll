<<<<<<< HEAD
package gt.edu.umg;

import java.io.*;
import java.util.Scanner;

public class ArchivoD {

	static final String ruta = ("registro.txt");
	static Scanner sc;
	private final int totalBytes = 50;
	RandomAccessFile f = null;
	
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
=======
package gt.umg.programacion.proyecto3;

import java.io.*;
import java.util.*;
import java.text.*;

public class ArchivoD {

	private static final String dataBase = ("C:\\Users\\Rolando\\Documents\\TAREAS\\PROGRAMACION I\\JAVA\\gt.umg.programacion.proyecto3\\src\\gt\\umg\\programacion\\proyecto3\\dataBase.txt");//ruta base de datos pricipal
	private static final String entidades = ("C:\\Users\\Rolando\\Documents\\TAREAS\\PROGRAMACION I\\JAVA\\gt.umg.programacion.proyecto3\\src\\gt\\umg\\programacion\\proyecto3\\entidades.txt");//ruta de archivos de las entidades
	private static final String propiedades = ("C:\\Users\\Rolando\\Documents\\TAREAS\\PROGRAMACION I\\JAVA\\gt.umg.programacion.proyecto3\\src\\gt\\umg\\programacion\\proyecto3\\propiedades.txt");//ruta de las propiedades de las entidades
	static Scanner sc = new Scanner(System.in);
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
	/*Crea los nombres de los ficheros de cada entidad*/
	private String creaNombreFichero(String nombre) {
		return nombre.trim() + " .txt";
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
				strNombre = sc.nextLine();
				longitud = strNombre.length();
				if (longitud < 2 || longitud > 30) {
					System.out.println("Longitud NO valida [de 2 a 30]");
				}else {
					if (strNombre.contains(" ")) {
						System.out.println("Nombre no debe de tener espacios en blanco, utilizar guion bajo");
						longitud = 0;
					}
				}
			}while(longitud < 2 || longitud > 30);
			tabla.setNombreTabla(strNombre);
			System.out.println("Propiedades de la Entidad");
			int flgDetener = 0;
			System.out.println("Escriba el nombre del atributo no. " + (tabla.getTamanio() + 1));
			do {
				Propiedad propiedad = new Propiedad();
				propiedad.setIndice(tabla.getIndice());
				flgDetener = 0;
				do {
					strNombre = sc.nextLine();
					longitud = strNombre.length();
					if (longitud < 2 || longitud > 30) {
						System.out.println("Longitud NO valida [de 3 a 30]");
					}else {
						if (strNombre.contains(" ")) {
							System.out.println("Nombre no debe de tener espacios en blanco, utilizar guion bajo");
							flgDetener = 0;
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
				System.out.println("Desea ingesar otro campo? 1. Si 0. No");
				flgDetener = sc.nextInt();
			}while(flgDetener != 0);
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
	/*Modifica datos de una entidad*/
	private void alteraEntidad() {
		try {
			int indice = 0;
			while(indice == 1 || indice > listaTablas.size()) {
				for (Tabla t : listaTablas) {
					System.out.println(t.getIndice() +" || "+t.getNombreTabla());
				}
				System.out.println("Seleccione la entidad a modificar");
				indice = sc.nextInt();
			}
			Tabla t = null;
			for(Tabla tabla : listaTablas) {
				if (indice == tabla.getIndice()) {
					t = tabla;
					break;
				}
			}
			String nomFichero = creaNombreFichero(t.getNombreTabla());
			f  = new RandomAccessFile(dataBase, "rw");
			long longDatos = f.length();
			f.close();
			if (longDatos > 0) {
				System.out.println("Entidad debe estar vacía para poder alterarse");
			}else {
				boolean flgEncontrado = false;
				boolean flgModificado = false;
				entidad.seek(0);//nos vamos al principio del archivo
				long longitud = entidad.length();
				int registros = 0;
				int salir = 0;
				int i;
				Tabla tabla;
				byte[] tempBytes;
				while (longitud > totalBytes) {
					tabla = new Tabla();
					tabla.setIndice(entidad.readInt());
					tempBytes = new byte[30];
					entidad.read(tempBytes);
					tabla.setBytesNombreTabla(tempBytes);
					tabla.setTamanio(entidad.readInt());
					tabla.setBytes(entidad.readInt());
					tabla.setPosicion(entidad.readLong());
					if (tabla.getIndice() == t.getIndice()) {
						System.out.println("Presione ENTER si no desea modificar el campo");
						System.out.println("Nombre del campo a modificar");
						String entrada = "";
						int tamanio = 0;
						long posicion;
						do {
							entrada = sc.nextLine();
							tamanio = entrada.length();
							if (tamanio == 1 || tamanio > 30) {
								System.out.println("Longitud del campo debe ser entre [2 - 30]");
							}
						}while (tamanio == 1 || tamanio > 30);
						if (tamanio > 0) {
							tabla.setNombreTabla(entrada);
							posicion = registros * totalBytes;
							f.seek(posicion);
							f.skipBytes(4);//nos desplazamos despues del indice (entero = 4 bytes)
							f.write(tabla.getBytesNombreTabla());//guardamos los cambios
							flgModificado = true;
						}
						i = 1;
						for (Propiedad p : t.getPropiedades()) {
							System.out.println("Modificando Atributo");
							System.out.println(p.getNombreCampo().trim());
						}
						break;
					}
					registros++;
					//se restan los bytes del registro leido
					longitud -= totalBytes;
				}

			}
		}catch(Exception e) {
			System.out.println("Ocurrio un error almodificar entidad: "+e.getMessage());
		}
	}
	/*===================================================================================================================*/
	/*Listar entidades*/
	private void listarEntidades() {
		if (listaTablas.size() > 0) {
			int detalle = 0;
			System.out.println("Desea que se impriman los campos de las entidades? 1. Si, 0.No");
			detalle = sc.nextInt();
			if (detalle == 1) {
				for (Tabla entidad : listaTablas) {
					describeEntidad(entidad);
				}
			}else {
				for (Tabla entidad : listaTablas) {
					System.out.println("Indice: "+entidad.getIndice());
					System.out.println("Nombre de la entidad: "+entidad.getNombreTabla());
					System.out.println("Cantidad de propiedades: "+entidad.getTamanio());
				}
			}
		}else {
			System.out.println("No se encontraron entidades");
		}
	}
	/*===================================================================================================================*/
	/*Agrega registros*/
	private void agregaRegistros() {
		int indice = 0;
		while(indice < 1 || indice > listaTablas.size()) {
			for (Tabla tabla : listaTablas) {
				System.out.println(tabla.getIndice() + " Campo: " + tabla.getNombreTabla() );
			}
			System.out.println("Seleccione la entidad a trabajar");
			indice = sc.nextInt();
		}
		mIni(indice);
	}
	/*===================================================================================================================*/
	/*Menu de Definicion*/
	private void menuDef(boolean mostrarInsertaRegistros) {
		int opcion;
		do {
			System.out.println("Menu de Definicion de Bases de Datos");
			System.out.println("Escoja su opcion");
			System.out.println("\t1. Agregar entidad");
			System.out.println("\t2. Modificar entidad");
			System.out.println("\t3. Listar entidades");
			if (mostrarInsertaRegistros) {
				System.out.println("\t4. Insertar Registros");
			}
			System.out.println("\t5. Borrar Bases de Datos");
			System.out.println("\t0. Salir");
			opcion = sc.nextInt();
			switch (opcion) {
			case 0:
				System.out.println("Gracias por usar nuesto sistema, vuelva pronto!");
				break;
			case 1:
				if (creaEntidad()) {
					System.out.println("Entidad creada con exito");
					mostrarInsertaRegistros = true;
				}
				break;
			case 2:
				alteraEntidad();
				break;
			case 3:
				listarEntidades();
				break;	
			case 4:
				agregaRegistros();
				break;
			case 5:
				int conf = 0;
				System.out.println("Está seguro de borrar los datos existentes? 1. Si, para salir presione cualquier tecl");
				conf = sc.nextInt();
				if (conf == 1) {
					cerrarArchivos();
					if (borrarArchivos()) {
						listaTablas = null;
						listaTablas = new ArrayList<>();
						System.out.println("Archivos borrados");
					}
				}
				break;

			default:
				System.out.println("Opcion NO existente");
				break;
			}
		}while(opcion != 0);
	}
	/*===================================================================================================================*/

	/*Main*/
	public static void main(String[]args) {
		ArchivoD ad = new ArchivoD();
		if (ad.validaDataBase()) {
			ad.menuDef(true);
		}else {
			ad.menuDef(false);
		}
		System.exit(0);
	}
/*===================================================================================================================*/	
/*Menu principal*/
	private void mIni(int indice) {

		int op=0;
		String nombreFichero = "";

		try {
			Tabla entidad = null;
			for (Tabla t: listaTablas) {
				if (indice == t.getIndice()) {
					nombreFichero = creaNombreFichero(t.getNombreTabla());
					entidad = t;
					break;
				}
			}
			f = new RandomAccessFile (dataBase,"rw");
			System.out.println("****Bienvenido****");
			Propiedad p = entidad.getPropiedades().get(0);
>>>>>>> f6214d16db89917437a33f288a26d329dd743ee9
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
<<<<<<< HEAD
					
					case 0: System.out.println("**Hasta luego**");
						break;
						
					case 1:
=======

					case 0: System.out.println("**Hasta luego**");
					break;

					case 1:
						insertaRegistro(entidad);
>>>>>>> f6214d16db89917437a33f288a26d329dd743ee9
						break;
					case 2:
						break;
					case 3: 
						System.out.println("Ingrese el No. de carne a buscar");
						int carne;
						carne = sc.nextInt();
						buscaRegistro(carne);
						break;
					case 4:
						System.out.println("Ingrese el No. de carne a modificar");
						int codigo;
						codigo = sc.nextInt();
						modificaRegistro(codigo);
						break;
<<<<<<< HEAD
					default: System.out.println("**opcion no valida, intente de nuevo** ");
=======
<<<<<<< HEAD
						default: System.out.println("**opcion no valida, ingrese de nuevo** ");
						break;
						
=======
					default: System.out.println("**opcion no valida, ingrese de nuevo** ");
>>>>>>> 41c2ad2129eae216159fb23ca8ce60b51bae92dd
					break;

>>>>>>> f6214d16db89917437a33f288a26d329dd743ee9
					}
				}catch(Exception e) {
					System.out.println("Error"+e.getMessage());
				}
			}while (op !=0);
			f.close();
<<<<<<< HEAD
			
		}catch(Exception e) {
			System.out.println("Error"+e.getMessage());
		}
		
	}
	
	
			
	}


=======
		}catch(Exception e) {
			System.out.println("Error"+e.getMessage());
		}

	}
/*===================================================================================================================*/	
/*Agregar registros*/
	private boolean insertaRegistro(Tabla tabla) {
		boolean sucedido = false;
		try {
			f.seek(f.length());//nos movemos al final del archivo
			boolean valido;
			byte[] bytesStr;
			String tmpStr = "";
			for (Propiedad propiedad : tabla.getPropiedades()) {
				valido = false;
				System.out.println("Ingrese " + propiedad.getNombreCampo().trim());
				while(!valido) {
					try {
						switch (propiedad.getTipoDato()) {
						case INT:
							int tmpInt = sc.nextInt();
							f.writeInt(tmpInt);
							sc.nextLine();
							break;
						case LONG:
							long tmpLong = sc.nextLong();
							f.writeLong(tmpLong);
							break;
						case STRING:
							int longitud = 0;
							do {
								tmpStr = sc.nextLine();
								longitud = tmpStr.length();
								if (longitud <= 1 || longitud > propiedad.getLongitud()) {
									System.out.println("La longitud de " + propiedad.getNombreTipoDato()
											+ " no es valida [1 - " + propiedad.getLongitud() + "]");
								}
							} while (longitud <= 0 || longitud > propiedad.getLongitud());
							// arreglo de bytes de longitud segun definida
							bytesStr = new byte[propiedad.getLongitud()];
							// convertir caracter por caracter a byte y agregarlo al arreglo
							for (int i = 0; i < tmpStr.length(); i++) {
								bytesStr[i] = (byte) tmpStr.charAt(i);
							}
							f.write(bytesStr);
							break;
						case DOUBLE:
							double tmpDouble = sc.nextDouble();
							f.writeDouble(tmpDouble);
							break;
						case FLOAT:
							float tmpFloat = sc.nextFloat();
							f.writeFloat(tmpFloat);
							break;
						case DATE:
							Date date = null;
							tmpStr = "";
							while (date == null) {
								System.out.println("Formato de fecha: " + formatoFecha);
								tmpStr = sc.nextLine();
								date = strintToDate(tmpStr);
							}
							bytesStr = new byte[propiedad.getBytes()];
							for (int i = 0; i < tmpStr.length(); i++) {
								bytesStr[i] = (byte) tmpStr.charAt(i);
							}
							f.write(bytesStr);
							break;
						case CHAR:
							do {
								tmpStr = sc.nextLine();
								longitud = tmpStr.length();
								if (longitud < 1 || longitud > 1) {
									System.out.println("Solo se permite un caracter");
								}
							} while (longitud < 1 || longitud > 1);
							byte caracter = (byte) tmpStr.charAt(0);
							f.writeByte(caracter);
							break;
						}
						valido = true;
					} catch (Exception e) {
						System.out.println(
								"Error " + e.getMessage() + " al capturar tipo de dato, vuelva a ingresar el valor: ");
						sc.nextLine();
					}
				} 
			} 
			f.write("\n".getBytes()); //para que el siguiente registro se agregue abajo
			sucedido = true;
		} catch (Exception e) {
			sucedido = false;
			System.out.println("Error al agregar el registro " + e.getMessage());
		}
		return sucedido;
	}
/*===================================================================================================================*/	
/*Modificar registros*/
	private void modificaRegistro(int carne) {
		try {
			//banderas de encontrado y modificado
			boolean flgEncontrado = false, flgModificado = false;
			//vamos al principio del archivo
			f.seek(0);
			long lon = f.length();
			int registros = 0;
			Alumno a = new Alumno();
			while(lon > totalBytes) {
				a.setCarne(f.readInt());
				byte[] byteNombre = new byte[50];
				f.read(byteNombre);
				a.setBytesNombre(byteNombre);
				byte[] byteFecha = new byte[28];
				f.read(byteFecha);
				f.readByte();
				a.setBytesFechaNacimiento(byteFecha);
				if (a.getCarne() == carne) {
					System.out.println("Presione ENTER si no desea modificar el registro");
					System.out.println("Ingrese el nombre");
					String tmpStr = "";
					int len = 0;
					long pos;
					do {
						tmpStr = sc.nextLine();
						len = tmpStr.length();
						if (len > 50) {
							System.out.println("La longitud del nombre no es valida [1 - 50]");
						}
					} while (len > 50);
					if (len > 0) {
						a.setNombre(tmpStr);
						// encontrar la posicion especifica del campo a modificar
						// primero encontrar la posicion del registro
						pos = registros * totalBytes;
						f.seek(pos);
						// sumar el tamanio del campo llave
						f.skipBytes(4); // moverse despues del carne (int = 4 bytes)
						// grabar el cambio
						f.write(a.getBytesNombre());
						flgModificado = true;
					}
					System.out.println("Ingrese la fecha (" + formatoFecha + ")");
					tmpStr = sc.nextLine();
					if (tmpStr.length() > 0) {
						Date date = null;
						while (date == null) {
							date = strintToDate(tmpStr);
						}
						a.setFechaNacimiento(date);
						pos = registros * totalBytes;
						f.seek(pos);
						f.skipBytes(4 + 50); // moverse despues del carne + el nombre (int = 4 bytes, nombre = 50
													// bytes)
						f.write(a.getBytesFechaNacimiento());
						flgModificado = true;
					}
					// imprimir los campos del registro
					if (flgModificado) { // equivalente a (bndModificado == true)
						System.out.println("El registro fue modificado correctamente, los nuevos datos son:");
					}
					System.out.println("Carne: " + a.getCarne());
					System.out.println("Nombre: " + a.getNombre());
					System.out.println("Fecha de nacimiento: " + dateToString(a.getFechaNacimiento()));
					flgModificado = true;
					// si el registro se ha encontrado entonces salir del ciclo
					break;
				}
				registros++;
				// restar los bytes del registro leido
				lon -= totalBytes;
			}
			//si no se encuentra muestra mensaje
			if (!flgModificado) {
				System.out.println("No se encontro el carne indicado, por favor verifique");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

/*===================================================================================================================*/	
/*Busca registro*/
	public void buscaRegistro(int carne) {
		try {
			long lon = f.length();
			if (lon > 0) {
				System.out.println("Arvhivo vacio");
				return;
			}
			boolean flgEncontrado = false;
			f.seek(0);//nos colocamos al inicio del archivo
			Alumno a = new Alumno();
			while(lon >= totalBytes) {
				a.setCarne(f.readInt());
				byte[] byteNombre = new byte[50];
				f.read(byteNombre);
				a.setBytesNombre(byteNombre);
				byte[] byteFecha = new byte[28];
				f.read(byteFecha);
				a.setBytesFechaNacimiento(byteFecha);
				if (a.getCarne() == carne) {
					//mostramos los campos del registro
					System.out.println("No. Carne: "+a.getCarne());
					System.out.println("Nombre: "+a.getNombre());
					System.out.println("Fecha de Nacimiento: "+dateToString(a.getFechaNacimiento()));
					break;//salir del ciclo cuando encuentre el registro
				}
				lon -= totalBytes;
			}
			if (!flgEncontrado) {
				System.out.println("No se encontro el carnet indicado, verifique de nuevo");
			}
		}catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
/*===================================================================================================================*/	
/*Listar registros*/
	public void listarRegistros(Tabla tabla) {
		try {
			long lon = f.length();
			if(lon <= 0) {
				System.out.println("Archivo vac�o");
				return;
			}
			f.seek(0);
			byte[] tempArregloByte;
			String lin = "";
			for (Propiedad propiedad : tabla.getPropiedades()) {
				lin += propiedad.getNombreCampo().toString().trim() + "\t\t";
			}
			System.out.println(lin);
			while(lon >= tabla.getBytes()) {
				lin = "";
				for (Propiedad propiedad : tabla.getPropiedades()) {
					switch(propiedad.getTipoDato()) {
					case INT:
						int tempInt = f.readInt();
						lin += String.valueOf(tempInt) + "\t\t";
						break;
					case LONG:
						long tempLong = f.readLong();
						lin += String.valueOf(tempLong) + "\t\t";
						break;
					case STRING:
						tempArregloByte = new byte[propiedad.getLongitud()];
						f.read(tempArregloByte);
						String tempStr = new String(tempArregloByte);
						lin += tempStr.trim() + "\t\t";
						break;
					case DOUBLE:
						double tempDouble = f.readDouble();
						lin += String.valueOf(tempDouble) + "\t\t";
						break;
					case FLOAT:
						float tempFloat = f.readFloat();
						lin += String.valueOf(tempFloat) + "\t\t";
						break;
					case DATE:
						tempArregloByte = new byte[propiedad.getBytes()];
						f.read(tempArregloByte);
						tempStr = new String(tempArregloByte);
						lin += tempStr.trim() + "\t\t";
						break;
					case CHAR:
						char tempChar = f.readChar();
						lin += String.valueOf(tempChar) + "\t\t";
						break;
					}
				}
				f.readByte();//lee cambio de linea
				//restamos los bytes del registro leido
				lon -= tabla.getBytes();
				System.out.println(lin);
			}
		}catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
/*===================================================================================================================*/	

/*Cerrar archivos*/
private void cerrarArchivos() {
	if (f != null) {
		try {
			System.out.println("Cerrando archivos de Base de Datos....");
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	if (propiedad != null) {
		try {
			propiedad.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	if (entidad != null) {
		try {
			entidad.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	System.out.println("Base de datos cerrada!");
}
/*===================================================================================================================*/	

/*Eliminar datos de la base de datos*/
private boolean borrarArchivos() {
	boolean sucedido = false;
	try {
		File fichero;
		for (Tabla tabla : listaTablas) {
			fichero = new File(dataBase + tabla.getNombreTabla().trim() + ".dat");
			if (fichero.exists()) {
				fichero.delete();
			}
			fichero = null;
		}
		fichero = new File(propiedades);
		if (fichero.exists()) {
			fichero.delete();
		}
		fichero = null;
		fichero = new File(entidades);
		if (fichero.exists()) {
			fichero.delete();
		}
		fichero = null;
		sucedido = true;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return sucedido;
}
/*===================================================================================================================*/	
/*Funciones para convertir el formato de fecha a String */
public Date strintToDate(String strFecha) {
	Date date = null;
	try {
		date = format.parse(strFecha);
	} catch (Exception e) {
		date = null;
		System.out.println("Error: " + e.getMessage());
	}
	return date;
}

public String dateToString(Date date) {
	String strFecha;
	strFecha = format.format(date);
	return strFecha;
}
/*===================================================================================================================*/	

}
>>>>>>> f6214d16db89917437a33f288a26d329dd743ee9
