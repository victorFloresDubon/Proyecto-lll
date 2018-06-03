package gt.edu.umg.programacion.parcial.a;

import java.util.Scanner;
import java.io.*;

public class Main {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Main iniciar = new Main();
		iniciar.menuMuestras();
	}

	private void menuMuestras() {
		try {
			int opc;
			Cola c = new Cola();
			Muestra m = new Muestra();
			do {
				System.out.println("\t\tSistema de Control de Muestras");
				System.out.println("\t1. Agregar Muestra");
				System.out.println("\t2. Quitar Muestra en cola");
				System.out.println("\t3. Listar muestras en cola");
				System.out.println("\t4. Cargar Datos de Muestras");
				System.out.println("\t5. Historico");
				System.out.println("\t0. Salir");
				opc = sc.nextInt();
				switch (opc) {
				case 0:
					System.out.println("Gracias por usar nuestro sistema, vuelva pronto");
					break;
				case 1:
					int codigo;
					String nombre;
					System.out.println("Codigo: ");
					codigo = sc.nextInt();
					m.setCodigo(codigo);
					System.out.println("Nombre: ");
					nombre = sc.nextLine();
					nombre = sc.nextLine();
					m.setNombre(nombre);
					c.agregarMuestra(m);
					System.out.println("Muestra en cola");
					break;
				case 2:
					c.quitarMuestra();
					break;
				case 3:
					c.listarMuestras();
					break;
				case 4:
					System.out.println("Cargando datos....");
					if(c.cargarDatos()) {
						System.out.println("Datos en cola cargados con exito");
						System.out.println("Vaciando cola....");
						c.vaciar();
						System.out.println("Cola vacia, datos historicos actualizados!!");
					}else {
						System.out.println("No se cargaron los datos");
					}
					break;
				case 5:
					break;

				default:
					System.out.println("Opcion no existe");
					break;
				}
			}while(opc != 0);
		}catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
}
