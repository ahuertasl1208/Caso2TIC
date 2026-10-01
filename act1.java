import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;

public class Act1 {
    public static void main(String[] args) {
        // Entry point for running this class.
    }

    public static void cifrar(byte[][] m, byte[] v, int numPasadas) {

        int filas = m.length;
        int columnas = m[0].length;
        for (int pasada = 0; pasada < numPasadas; pasada++) {
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    m[i][j] = (byte) ((m[i][j] + v[j % v.length]) & 0xFF);
                }
            }
        }

        for (int j = 0; j < columnas; j++) {
            for (int i = 0; i < filas; i++) {
                // 3 acceos a memoria
                // 2 de lectura
                // 1 de escritura
                m[i][j] = (byte) ((m[i][j] ^ v[i % v.length]) & 0xFF);
            }
        }
    }

    

public static File generarDVs (int NF, int NC, int NV, int TP, int numPasadas, String nombreArchivo) {
    int numCeldas = NF * NC;
    // NR: Numero total de accesos. 
    int NR  = (numCeldas * 3 * 2) * numPasadas;
    // NP: Numero de paginas virtuales
    // el numero de paginas virtuales se redondea para arriba
    int NP = (int) Math.ceil((double)(numCeldas + TP) / TP);

    try {
        
        File archivoRetorno = generarArchivo(nombreArchivo);
        
        try (PrintWriter writer = new PrintWriter(archivoRetorno)) {
            writer.printf("TP=%d%n", TP);
            writer.printf("NF1=%d%n", NF); 
            writer.printf("NV=%d%n", NV);
            writer.printf("numPasadas=%d%n", numPasadas);
            writer.printf("NR=%d%n", NR);
            writer.printf("NP=%d%n", NP);
        } 

        return archivoRetorno;
        
    } catch (IOException e) {
        // Imprimir el stack trace para ver donde hubo error
        e.printStackTrace();
    }

    return null;
}


    public static File generarArchivo (String nombreArchivo) throws IOException {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
        return archivo;
    }

    
}



