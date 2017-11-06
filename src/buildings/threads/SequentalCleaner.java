package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class SequentalCleaner implements Runnable
{
    private Floor floor;
    private RepairerCleanerSemaphore semaphore;

    public SequentalCleaner(Floor floor, RepairerCleanerSemaphore semaphore)
    {
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run()
    {
        int i = 1;
        for (Space space:floor)
        {
            try {
                semaphore.release();
                System.out.printf("Cleaning room number %d with total area %.2f square meters.\n", i++, space.getArea());
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.getMessage();
            }
        }
    }
}
