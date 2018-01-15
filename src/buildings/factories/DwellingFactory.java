package buildings.factories;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Building;
import buildings.interfaces.BuildingFactory;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class DwellingFactory implements BuildingFactory
{
    @Override
    public Space createSpace(float area)
    {
        return new Flat(area);
    }

    @Override
    public Space createSpace(int roomsCount, float area)
    {
        return new Flat(area, roomsCount);
    }

    @Override
    public Floor createFloor(int spaceCount)
    {
        return new DwellingFloor(spaceCount);
    }

    @Override
    public Floor createFloor(Space[] spaces)
    {
        return new DwellingFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int[] spacesCount) {
        return new Dwelling(floorsCount, spacesCount);
    }

    @Override
    public Building createBuilding(Floor[] floors)
    {
        return new Dwelling(floors);
    }
}
