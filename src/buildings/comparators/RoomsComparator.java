package buildings.comparators;

import buildings.interfaces.Building;
import buildings.interfaces.Space;

import java.util.Comparator;

public class RoomsComparator implements Comparator<Space> {

    @Override
    public int compare(Space space1, Space space2)
    {
        if(space1.getRooms() > space2.getRooms())
            return -1;
        if(space1.getRooms() < space2.getRooms())
            return 1;
        return 0;
    }
}
