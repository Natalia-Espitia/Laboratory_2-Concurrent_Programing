package arsw.threads;

public class EstadoCarrera {
    private boolean enPausa = false;

    public synchronized void pausar() {
        enPausa = true;
    }

    public synchronized void continuar() {
        enPausa = false;
    }

    public synchronized boolean isEnPausa() {
        return enPausa;
    }
}