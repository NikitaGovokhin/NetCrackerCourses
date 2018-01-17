package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class SequentalRepairer implements Runnable {
    private Floor floor;
    private RepairerCleanerSemaphore semaphore;

    public SequentalRepairer(Floor floor, RepairerCleanerSemaphore semaphore) {
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        int i = 1;
        for (Space space : floor) {
            try {
                System.out.printf("Repairing space number %d with total area %.2f square meters.\n", i++, space.getArea());
                semaphore.take();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }
    }
}
