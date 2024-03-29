package Taller.Aleatorios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 *
 * @author Jhon
 */
public class EjemploArchivoAleatorio {

    private int clave;
    private String nombre;
    private int edad;
    private long tamreg;
    private long canreg;
    private File fl;
    RandomAccessFile archivo;

    /**
     * Constructor de la clase
     */
    public EjemploArchivoAleatorio() {
        this.clave = 0;
        this.nombre = "";
        this.edad = 0;
        this.tamreg = 58;
        this.canreg = 0;
        try {
            fl = new File("C://prueba.dat");
            archivo = new RandomAccessFile(fl, "rw");
        } catch (FileNotFoundException fnfe) {/* Archivo no encontrado */
        }
    }

    /**
     * Metodo par aleer todo el contenido de un archivo aletorio
     */
    public void leerTodo() {
        System.out.println("\nMostrando todos los Registros");
        try {
            archivo.seek(0);
            nombre = "";
            canreg = archivo.length() / tamreg;
            for (int r = 0; r < canreg; r++) {
                clave = archivo.readInt();
                for (int i = 0; i < 25; ++i) {
                    nombre += archivo.readChar();
                }
                edad = archivo.readInt();
                System.out.println(" Registro No: " + clave + " Nombre: "
                        + nombre + " Edad: " + edad);
                nombre = "";
            }
        } catch (FileNotFoundException fnfe) {/* Archivo no encontrado */
        } catch (IOException ioe) {
            /* Error al escribir */
        }
    }

    /**
     * Metodo para leer un registro determinado
     */
    public void leerUnRegistro() {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nBuscando un Registro:");
        System.out.println("Ingrese el numero de registro a buscar: ");
        try {
            archivo.seek(0);
            clave = Integer.parseInt(teclado.readLine());
            archivo.seek((clave - 1) * tamreg); // argumento es tipo long
            clave = archivo.readInt();
            for (int i = 0; i < 25; ++i) {
                nombre += archivo.readChar();
            }
            edad = archivo.readInt();
            System.out.println("Registro No. " + clave + " Nombre: " + nombre
                    + " Edad: " + edad + "\n\n");
            nombre = "";
        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
        }
    }

    /**
     * Metodo para escribir un registro en un archivo aleatorio
     */
    public void escribir() {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(
                System.in));
        try {
            System.out.println("\nEscribiendo Registros:");
            String r = "S";
            while (r.equalsIgnoreCase("S")) {
                clave = buscarUltimo();
                if (clave <= 0) {
                    System.out.println("\nNo hay registros. Se escribira el primero.");
                }
                clave++;
                System.out.println("Ingrese nombre: ");
                nombre = teclado.readLine();
                if (nombre.length() < 25) {
                    for (int i = nombre.length(); i < 25; i++) {
                        nombre = nombre + " ";
                    }
                } else {
                    nombre = nombre.substring(0, 25);
                }
                System.out.println("Ingrese edad: ");
                edad = Integer.parseInt(teclado.readLine());
                if (archivo.length() != 0) {
                    archivo.seek(archivo.length());
                }
                archivo.writeInt(clave);
                archivo.writeChars(nombre);
                archivo.writeInt(edad);
                System.out.println("Ingresar otra linea (S or N)?: \t");
                r = teclado.readLine();
            }
        } catch (FileNotFoundException fnfe) {/* Archivo no encontrado */
        } catch (IOException ioe) {
            /* Error al escribir */
        }
    }

    /**
     * *
     * Metodo para cerrar la conexion al archivo y el flujo o stream
     */
    public void cerrar() {
        try {
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int buscarUltimo() {
        clave = 0;
        try {
            archivo.seek(archivo.length() - tamreg);
            clave = archivo.readInt();
        } catch (FileNotFoundException fnfe) {/* Archivo no encontrado */
        } catch (IOException ioe) {
            /* Error al escribir */
        }
        return clave;
    }

    public static void main(String[] args) {
        ArchivoDirecto arch = new ArchivoDirecto();
        arch.escribir();
        arch.leerTodo();
        arch.leerUnRegistro();
        arch.actualizandoRegistros();
        arch.leerTodo();
        arch.mdRegistro();
        arch.leerTodo();
        arch.cerrar();
    }
}
