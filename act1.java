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
                m[i][j] = (byte) ((m[i][j] ^ v[i % v.length]) & 0xFF);
            }
        }
    }

    

    public static void generarDVs (int NF, int NC, int NV, int TP, int numPasadas, String nombreArchivo) {

    }

    
}



