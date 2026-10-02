import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;
import java.util.Scanner;

public class Act1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Generador de Direcciones Virtuales");
        
        System.out.print("Ingrese el número de filas (NF): ");
        int NF = scanner.nextInt();

        System.out.print("Ingrese el número de columnas (NC): ");
        int NC = scanner.nextInt();

        System.out.print("Ingrese el tamaño del vector (NV): ");
        int NV = scanner.nextInt();

        System.out.print("Ingrese el tamaño de página en bytes (TP): ");
        int TP = scanner.nextInt();

        System.out.print("Ingrese el número de pasadas: ");
        int numPasadas = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Ingrese el nombre del archivo de salida (ej: salida.txt): ");
        String nombreArchivo = scanner.nextLine();

        File resultado = generarDVs(NF, NC, NV, TP, numPasadas, nombreArchivo);

        if (resultado != null) {
            System.out.println("¡Archivo generado exitosamente en: " + resultado.getAbsolutePath());
        } else {
            System.out.println("Ocurrió un error al generar el archivo.");
        }

        scanner.close();
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
            for (int j = 0; j < columnas; j++) {
                for (int i = 0; i < filas; i++) {
                    m[i][j] = (byte) ((m[i][j] ^ v[i % v.length]) & 0xFF);
                }
            }
        }
    }

    public static File generarDVs(int NF, int NC, int NV, int TP, int numPasadas, String nombreArchivo) {
        int numCeldas = NF * NC;
        // NR: Numero total de accesos
        int NR = (numCeldas * 3 * 2) * numPasadas;
        
        // NP: Numero de paginas virtuales (Matriz + Vector) / TP
        int NP = (int) Math.ceil((double) (numCeldas + NV) / TP);

        try {
            File archivoRetorno = generarArchivo(nombreArchivo);

            try (PrintWriter writer = new PrintWriter(archivoRetorno)) {
                // Imprimir el encabezado en el archivo 
                writer.printf("TP=%d%n", TP);
                writer.printf("NF1=%d%n", NF);
                writer.printf("NC1=%d%n", NC);
                writer.printf("NV=%d%n", NV);
                writer.printf("numPasadas=%d%n", numPasadas);
                writer.printf("NR=%d%n", NR);
                writer.printf("NP=%d%n", NP);

                for (int pasada = 0; pasada < numPasadas; pasada++) {

                    // Recorrido por filas
                    for (int i = 0; i < NF; i++) {
                        for (int j = 0; j < NC; j++) {
                            int posMatriz = (i * NC) + j;
                            int posVector = (NF * NC) + (j % NV);

                            writer.printf("[mat1-%d-%d],%d,%d%n", i, j, posMatriz / TP, posMatriz % TP);
                            writer.printf("[v-0-%d],%d,%d%n", (j % NV), posVector / TP, posVector % TP);
                            writer.printf("[mat1-%d-%d],%d,%d%n", i, j, posMatriz / TP, posMatriz % TP);
                        }
                    }

                    // Recorrido por columnas
                    for (int j = 0; j < NC; j++) {
                        for (int i = 0; i < NF; i++) {
                            int posMatriz = (i * NC) + j;
                            int posVector = (NF * NC) + (i % NV);

                            writer.printf("[mat1-%d-%d],%d,%d%n", i, j, posMatriz / TP, posMatriz % TP);
                            writer.printf("[v-0-%d],%d,%d%n", (i % NV), posVector / TP, posVector % TP);
                            writer.printf("[mat1-%d-%d],%d,%d%n", i, j, posMatriz / TP, posMatriz % TP);
                        }
                    }
                }
            }
            return archivoRetorno;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static File generarArchivo(String nombreArchivo) throws IOException {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
        return archivo;
    }
}