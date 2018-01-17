package buildings.threads;

public class RepairerCleanerSemaphore {
    private boolean isTaken = false;

    public synchronized void take() throws InterruptedException {
        this.isTaken = true;
        this.notify();
    }

    public synchronized void release() throws InterruptedException {
        while (!this.isTaken) wait();
        this.isTaken = false;
        this.notify();
    }
}
