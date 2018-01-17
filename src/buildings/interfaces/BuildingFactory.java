package buildings.interfaces;

public interface BuildingFactory {
    public Space createSpace(float area);

    public Space createSpace(int roomsCount, float area);

    public Floor createFloor(int spaceCount);

    public Floor createFloor(Space[] spaces);

    public Building createBuilding(int floorsCount, int[] spacesCount);

    public Building createBuilding(Floor[] floors);
}
