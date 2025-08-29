package arsw.threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainCanodromo {

    private static Galgo[] galgos;
    private static Canodromo can;
    private static RegistroLlegada reg = new RegistroLlegada();

    private static final Object pausaLock = new Object();
    private static final EstadoCarrera estadoCarrera = new EstadoCarrera();

    public static void main(String[] args) {
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        can.setStartAction(
            new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    ((JButton) e.getSource()).setEnabled(false);
                    new Thread() {
                        public void run() {
                            for (int i = 0; i < can.getNumCarriles(); i++) {
                                galgos[i] = new Galgo(can.getCarril(i), "" + i, reg, pausaLock, estadoCarrera);
                                galgos[i].start();
                            }

                            for (int i = 0; i < can.getNumCarriles(); i++) {
                                try {
                                    galgos[i].join();
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }

                            can.winnerDialog(reg.getGanador(), reg.getUltimaPosicionAlcanzada() - 1);
                            System.out.println("El ganador fue:" + reg.getGanador());
                        }
                    }.start();
                }
            });

        can.setStopAction(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    synchronized (pausaLock) {
                        estadoCarrera.pausar();
                        System.out.println("Carrera pausada");
                    }
                }
            });

        can.setContinueAction(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    synchronized (pausaLock) {
                        estadoCarrera.continuar();
                        pausaLock.notifyAll();
                        System.out.println("Carrera reanudada");
                    }
                }
            });
    }
}