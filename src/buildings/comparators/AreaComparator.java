package buildings.comparators;

import buildings.interfaces.Floor;

import java.util.Comparator;

public class AreaComparator implements Comparator<Floor>
{
    @Override
    public int compare(Floor floor1, Floor floor2)
    {
        if(floor1.getArea() > floor2.getArea())
            return -1;
        if(floor1.getArea() < floor2.getArea())
            return 1;
        return 0;
    }
}
