import java.util.Random;
import java.util.Scanner;

public class HundirLaFlota {
    private char[][] tablero;
    private int intentos;

    public HundirLaFlota(int tamaño, int intentos) {
        this.tablero = new char[tamaño][tamaño];
        this.intentos = intentos;
        // Inicializar el tablero con '-'
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                tablero[i][j] = '-';
            }
        }
    }

    public void colocarBarcos(int lanchas, int buques, int acorazados, int portaaviones) {
        colocarBarco('L', 1, lanchas); // Lancha
        colocarBarco('B', 3, buques); // Buque
        colocarBarco('Z', 4, acorazados); // Acorazado
        colocarBarco('P', 5, portaaviones); // Portaaviones
    }

    private void colocarBarco(char tipo, int tamaño, int cantidad) {
        Random rand = new Random();
        for (int i = 0; i < cantidad; i++) {
            int fila, columna;
            do {
                fila = rand.nextInt(tablero.length);
                columna = rand.nextInt(tablero[0].length);
            } while (!puedeColocarBarco(fila, columna, tamaño));
            for (int j = 0; j < tamaño; j++) {
                tablero[fila][columna + j] = tipo;
            }
        }
    }

    private boolean puedeColocarBarco(int fila, int columna, int tamaño) {
        if (columna + tamaño > tablero[0].length) {
            return false; // El barco no cabe en el tablero
        }
        for (int i = 0; i < tamaño; i++) {
            if (tablero[fila][columna + i] != '-') {
                return false; // El barco se solapa con otro barco
            }
        }
        return true;
    }

    public void mostrarTablero() {
        System.out.print("  ");
        for (int i = 0; i < tablero[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < tablero.length; i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j] == '-') {
                    System.out.print("- ");
                } else if (tablero[i][j] == 'X') {
                    System.out.print("X ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public void jugar() {
        Scanner scanner = new Scanner(System.in);
    while (intentos > 0) {
        mostrarTablero();
        System.out.println("Introduce las coordenadas de tu disparo (formato: columna,fila): ");
        String coordenadas = scanner.nextLine();
        int columna = Integer.parseInt(coordenadas.split(",")[0]);
        int fila = coordenadas.split(",")[1].toUpperCase().charAt(0) - 'A';
        if (tablero[fila][columna] != '-') {
            System.out.println("¡Tocado!");
            tablero[fila][columna] = 'X';
        } else {
            System.out.println("¡Agua!");
        }
        intentos--;
    }
    scanner.close();
    }

    public static void main(String[] args) {
        System.out.println("¡Bienvenido a Hundir La Flota!");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nivel de dificultad (Fácil, Medio, Difícil): ");
        String nivelDificultad = scanner.nextLine();

        int tamaño = 10;
        int intentos = 50;
        int lanchas = 5;
        int buques = 3;
        int acorazados = 1;
        int portaaviones = 1;

        if (nivelDificultad.equalsIgnoreCase("Medio")) {
            intentos = 30;
            lanchas = 2;
            buques = 1;
            acorazados = 1;
            portaaviones = 1;
        } else if (nivelDificultad.equalsIgnoreCase("Difícil")) {
            intentos = 10;
            lanchas = 1;
            buques = 1;
            acorazados = 0;
            portaaviones = 0;
        } else if (nivelDificultad.equalsIgnoreCase("Personalizado")) {
            System.out.println("Ingrese el número de intentos: ");
            intentos = scanner.nextInt();
            System.out.println("Ingrese el número de lanchas: ");
            lanchas = scanner.nextInt();
            System.out.println("Ingrese el número de buques: ");
            buques = scanner.nextInt();
            System.out.println("Ingrese el número de acorazados: ");
            acorazados = scanner.nextInt();
            System.out.println("Ingrese el número de portaaviones: ");
            portaaviones = scanner.nextInt();
        }

        HundirLaFlota juego = new HundirLaFlota(tamaño, intentos);
        juego.colocarBarcos(lanchas, buques, acorazados, portaaviones);
        juego.jugar();
        scanner.close();
    }
}
