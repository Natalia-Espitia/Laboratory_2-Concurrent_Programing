package arsw.threads;

public class Galgo extends Thread {
    private int paso;
    private Carril carril;
    private RegistroLlegada regl;
    private final Object pausaLock;
    private final EstadoCarrera estadoCarrera;

    public Galgo(Carril carril, String name, RegistroLlegada reg, Object pausaLock, EstadoCarrera estadoCarrera) {
        super(name);
        this.carril = carril;
        paso = 0;
        this.regl = reg;
        this.pausaLock = pausaLock;
        this.estadoCarrera = estadoCarrera;
    }

    public void corra() throws InterruptedException {
        while (paso < carril.size()) {

            synchronized (pausaLock) {
                while (estadoCarrera.isEnPausa()) {
                    pausaLock.wait();
                }
            }

            Thread.sleep(100);
            carril.setPasoOn(paso++);
            carril.displayPasos(paso);

            if (paso == carril.size()) {
                carril.finish();
                int ubicacion = regl.registrarLlegada(this.getName());
                System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
            }
        }
    }

    @Override
    public void run() {
        try {
            corra();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}