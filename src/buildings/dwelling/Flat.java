package buildings.dwelling;

import buildings.exceptions.InvalidRoomsCountException;
import buildings.exceptions.InvalidSpaceAreaException;
import buildings.interfaces.Space;

import java.io.Serializable;

public class Flat implements Space, Serializable, Cloneable
{
    private int rooms = 2;
    private float area = 50;

    public Flat()
    {
        rooms = 2;
        area = 50;
    }

    public Flat(float area)
    {
        this.area = area;
    }

    public Flat(float area, int rooms)
    {
        this.area = area;
        this.rooms = rooms;
    }

    @Override
    public float getArea()
    {
        return area;
    }

    @Override
    public void setArea(float area)
    {
        if(area < 0)
            throw new InvalidSpaceAreaException("Error! Area should be positive!");
        this.area = area;
    }

    @Override
    public int getRooms()
    {
        return rooms;
    }

    @Override
    public void setRooms(int rooms)
    {
        if(rooms < 0)
            throw new InvalidRoomsCountException("Error! Rooms should be positive!");
        this.rooms = rooms;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName() + " (" + this.getRooms() + ", " + this.getArea() + ")");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object)
    {
        Flat flat = (Flat)object;
        return(getClass() == object.getClass() && this.getArea() == flat.getArea() && this.getRooms() == flat.getRooms());
    }

    @Override
    public int hashCode()
    {
        return (int) new Byte((byte)this.getRooms()) ^ new Byte((byte)this.getArea());
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Flat cloned = (Flat)super.clone();
        return cloned;
    }

    @Override
    public int compareTo(Space space)
    {
        if(this.getArea() > space.getArea())
            return 1;
        if(this.getArea() == space.getArea())
            return 0;
        return -1;
    }

}