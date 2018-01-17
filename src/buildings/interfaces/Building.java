package buildings.interfaces;

import java.util.Iterator;

public interface Building extends Cloneable, Iterable<Floor> {
    public int getCntFloors();

    public int getCntSpaces();

    public float getAreaSpaces();

    public int getCntRooms();

    public Floor[] getFloors();

    public Floor getFloor(int n);

    public void setFloor(int n, Floor floor);

    public Space getSpace(int n);

    public void setSpace(int n, Space space);

    public void addSpace(int n, Space space);

    public void delSpace(int n);

    public Space getBestSpace();

    public Space[] getSortedSpaces();

    public Object clone() throws CloneNotSupportedException;
}
