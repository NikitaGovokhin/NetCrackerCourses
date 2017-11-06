package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class Repairer extends Thread
{
    private Floor floor;

    public Repairer(Floor floor)
    {
        this.floor = floor;
    }

    @Override
    public void run()
    {
        int i = 1;
        for (Space space:floor)
        {
            System.out.printf("Repairing space number %d with total area %.2f square meters.\n", i++, space.getArea());
        }
    }
}
