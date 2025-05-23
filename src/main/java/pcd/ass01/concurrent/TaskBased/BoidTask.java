package pcd.ass01.concurrent.TaskBased;

import pcd.ass01.model.Boid;
import pcd.ass01.model.BoidModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BoidTask implements Runnable {
    private final BoidModel model;
    private final List<Boid> assignedBoids;
    private final BoidsSimulator simulator;
    private final CyclicBarrier barrier; // Aggiungi barriera per sincronizzazione

    public BoidTask(BoidModel model, List<Boid> assignedBoids, BoidsSimulator simulator, CyclicBarrier barrier) {
        this.model = model;
        this.assignedBoids = new ArrayList<>(assignedBoids);
        this.simulator = simulator;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            // Ciclo persistente
            while (!Thread.currentThread().isInterrupted() && simulator.isRunning()) {
                // Gestione pausa
                while (simulator.isPaused() && simulator.isRunning() && !Thread.currentThread().isInterrupted()) {
                    Thread.sleep(50);
                }
                
                if (!simulator.isRunning() || Thread.currentThread().isInterrupted()) {
                    break;
                }
                
                // Aggiorna tutti i boid assegnati
                for (Boid boid : assignedBoids) {
                    if (!simulator.isRunning()) {
                        return;
                    }
                    
                    boid.updateState(model);
                    int index = model.getBoidIndex(boid);
                    
                    if (index >= 0) {
                        model.updateBoid(index, boid);
                    }
                }
                
                // Sincronizzazione con gli altri task e il thread principale
                barrier.await();
            }
        } catch (InterruptedException e) {
            // Thread interrotto, termina silenziosamente
            Thread.currentThread().interrupt();
        } catch (BrokenBarrierException e) {
            // La barriera è stata resettata, probabilmente durante un reset
            if (simulator.isRunning()) {
                System.err.println("BrokenBarrierException in BoidTask: " + e.getMessage());
            }
        }
    }
}
