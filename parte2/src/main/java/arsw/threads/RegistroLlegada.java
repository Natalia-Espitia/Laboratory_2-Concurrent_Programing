package arsw.threads;

public class RegistroLlegada {

    private int ultimaPosicionAlcanzada = 1;
    private String ganador = null;

	/**
	 * Registra la llegada de un galgo a la meta.
	 * @param nombreGalgo
	 * @return
	 * La posición asignada al galgo.
	 * @author
	 * Natalia Espitia
	 * Jesus Jauregui
	 */
    public synchronized int registrarLlegada(String nombreGalgo) {
        int posicion = ultimaPosicionAlcanzada;
        ultimaPosicionAlcanzada++;
        if (posicion == 1 && ganador == null) {
            ganador = nombreGalgo;
        }
        return posicion;
    }

    public synchronized int getUltimaPosicionAlcanzada() {
        return ultimaPosicionAlcanzada;
    }

    public synchronized String getGanador() {
        return ganador;
    }
}