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


