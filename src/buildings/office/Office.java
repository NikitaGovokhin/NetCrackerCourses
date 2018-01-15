package buildings.office;

import buildings.exceptions.InvalidRoomsCountException;
import buildings.exceptions.InvalidSpaceAreaException;
import buildings.interfaces.Space;

import java.io.Serializable;

public class Office implements Space, Serializable, Cloneable
{
    private int rooms = 1;
    private float area = 250;

    public Office()
    {
        rooms = 1;
        area = 250;
    }

    public Office(float area)
    {
        this.area = area;
    }

    public Office(float area, int rooms)
    {
        this.rooms = rooms;
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
    public float getArea()
    {
        return area;
    }

    @Override
    public void setArea(float area)
    {
        if (area < 0)
            throw new InvalidSpaceAreaException("Error! Area should be positive!");
        this.area = area;
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
        Office office = (Office)object;
        return(getClass() == object.getClass() && this.getArea() == office.getArea() && this.getRooms() == office.getRooms());
    }

    @Override
    public int hashCode()
    {
        return (int) new Byte((byte)this.getRooms()) ^ new Byte((byte)this.getArea());
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Office cloned = (Office)super.clone();
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
