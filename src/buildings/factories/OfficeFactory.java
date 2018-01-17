package buildings.factories;

import buildings.interfaces.Building;
import buildings.interfaces.BuildingFactory;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;

public class OfficeFactory implements BuildingFactory {
    @Override
    public Space createSpace(float area) {
        return new Office(area);
    }

    @Override
    public Space createSpace(int roomsCount, float area) {
        return new Office(area, roomsCount);
    }

    @Override
    public Floor createFloor(int spaceCount) {
        return new OfficeFloor(spaceCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new OfficeFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int[] spacesCount) {
        return new OfficeBuilding(floorsCount, spacesCount);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new OfficeBuilding(floors);
    }
}
