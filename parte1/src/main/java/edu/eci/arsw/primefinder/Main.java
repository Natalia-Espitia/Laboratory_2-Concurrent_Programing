package edu.eci.arsw.primefinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        int max = 30000000;
        int step = max / 3;

        PrimeFinderThread pft1 = new PrimeFinderThread(0, step);
        PrimeFinderThread pft2 = new PrimeFinderThread(step + 1, 2 * step);
        PrimeFinderThread pft3 = new PrimeFinderThread(2 * step + 1, max);

        pft1.start();
        pft2.start();
        pft3.start();

        try {
            Thread.sleep(5000);

            pft1.pauseThread();
            pft2.pauseThread();
            pft3.pauseThread();

            int totalPrimes = pft1.getPrimes().size() +
                              pft2.getPrimes().size() +
                              pft3.getPrimes().size();

            System.out.println("\n--- Ejecución detenida después de 5 segundos ---");
            System.out.println("Primos encontrados hasta ahora: " + totalPrimes);
            System.out.println("Presione ENTER para continuar...");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();

            pft1.resumeThread();
            pft2.resumeThread();
            pft3.resumeThread();

            pft1.join();
            pft2.join();
            pft3.join();

            totalPrimes = pft1.getPrimes().size() +
                          pft2.getPrimes().size() +
                          pft3.getPrimes().size();
            System.out.println("\n--- Ejecución finalizada ---");
            System.out.println("Total de primos encontrados: " + totalPrimes);

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}