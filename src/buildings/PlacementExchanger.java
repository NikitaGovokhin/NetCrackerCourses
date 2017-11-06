package buildings;

import buildings.exceptions.InexchangeableFloorsException;
import buildings.exceptions.InexchangeableSpacesException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class PlacementExchanger
{

    public static boolean spaceExchangeTest(Space space1, Space space2)
    {
            return (space1.getArea() == space2.getArea() && space1.getRooms() == space2.getRooms());
    }

    public static boolean floorExchangeTest(Floor floor1, Floor floor2)
    {
        return (floor1.getArea() == floor2.getArea() && floor1.getCntRooms() == floor2.getCntRooms());
    }

    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2) throws InexchangeableSpacesException
    {
        if(!spaceExchangeTest(floor1.getSpace(index1), floor2.getSpace(index2)))
            throw new InexchangeableSpacesException("Error! These spaces can not be exchanged!");
        Space tmp = floor1.getSpace(index1);
        floor1.setSpace(index1, floor2.getSpace(index2));
        floor2.setSpace(index2, tmp);
    }

    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2) throws InexchangeableFloorsException
    {
        if(!floorExchangeTest(building1.getFloor(index1), building1.getFloor(index2)))
            throw new InexchangeableFloorsException("Error! These floors can not be exchanged!");
        Floor tmp = building1.getFloor(index1);
        building1.setFloor(index1, building2.getFloor(index2));
        building2.setFloor(index2, tmp);
    }
}
