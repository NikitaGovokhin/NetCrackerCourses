package buildings.dwelling;

import buildings.exceptions.FloorIndexOutOfBoundsException;
import buildings.exceptions.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.iterators.FloorIterator;

import java.util.Iterator;
import java.io.Serializable;

public class Dwelling implements Building, Serializable, Cloneable
{
    private Floor[] floors;

    public Dwelling(int n, int...cnt)
    {
        if(n < 0)
            throw new FloorIndexOutOfBoundsException("Error! Number should be positive!");
        floors = new Floor[n];
        for(int i = 0; i < n; i++)
            floors[i] = new DwellingFloor(cnt[i]);
    }

    public Dwelling(Floor...floors)
    {
        this.floors = new Floor[floors.length];
        for(int i = 0; i < floors.length; i++)
            this.floors[i] = floors[i];
    }

    @Override
    public int getCntFloors()
    {
        return floors.length;
    }

    @Override
    public int getCntSpaces()
    {
        int cnt = floors[0].getCnt();
        for(int i = 1; i < floors.length; i++)
            cnt += floors[i].getCnt();
        return cnt;
    }

    @Override
    public float getAreaSpaces()
    {
        float area = floors[0].getArea();
        for(int i = 1; i < floors.length; i++)
            area += floors[i].getArea();
        return area;
    }

    @Override
    public int getCntRooms()
    {
        int cnt = floors[0].getCntRooms();
        for(int i = 1; i < floors.length; i++)
            cnt += floors[i].getCntRooms();
        return cnt;
    }

    @Override
    public Floor[] getFloors()
    {
        return floors;
    }

    @Override
    public Floor getFloor(int n)
    {
        if(n < 0)
            throw new FloorIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntFloors())
            throw new FloorIndexOutOfBoundsException("Error! This number was not found!");
        return floors[n];
    }

    @Override
    public void setFloor(int n, Floor floor)
    {
        if(n < 0)
            throw new FloorIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntFloors())
            throw new FloorIndexOutOfBoundsException("Error! This number was not found!");
        floors[n] = floor;
    }

    @Override
    public Space getSpace(int n)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntRooms())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        int tmp_n = n;
        for(int i = 0; i < floors.length; i++)
            for(int j = 0; j < floors[i].getCnt(); j++)
                if((tmp_n--) == 0)
                    return floors[i].getSpace(j);
        return null;
    }

    @Override
    public void setSpace(int n, Space space)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntRooms())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        int tmp_n = n;
        for(int i = 0; i < floors.length; i++)
            for(int j = 0; j < floors[i].getCnt(); j++)
                if((tmp_n--) == 0)
                {
                    floors[i].setSpace(j, space);
                    return;
                }
    }

    @Override
    public void addSpace(int n, Space space)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntRooms())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        int tmp_n = n;
        for(int i = 0; i < floors.length; i++)
            for(int j = 0; j < floors[i].getCnt(); j++)
                if(((tmp_n--) == 0) || (j == floors[i].getCnt() - 1))
                {
                    floors[i].addSpace(j, space);
                    return;
                }
    }

    @Override
    public void delSpace(int n)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntRooms())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        int tmp_n = n;
        for(int i = 0; i < floors.length; i++)
            for(int j = 0; j < floors[i].getCnt(); j++)
                if((tmp_n--) == 0)
                {
                    floors[i].delSpace(j);
                    return;
                }
    }

    @Override
    public Space getBestSpace()
    {
        Space max = floors[0].getBestSpace();
        for(int i = 1; i < floors.length; i++)
        {
            if(max.getArea() < floors[i].getBestSpace().getArea())
                max = floors[i].getBestSpace();
        }
        return max;
    }

    @Override
    public Space[] getSortedSpaces()
    {

        Space tmp[] = new Space[this.getCntRooms()];
        for(int i =0; i < tmp.length; i++)
        {
            tmp[i] = this.getSpace(i);
        }
        for(int i = 0; i < tmp.length; i++)
            for(int j = 0; j < tmp.length-1; j++)
            {
                if(tmp[j].getArea() < tmp[j+1].getArea())
                {
                    Space tmp_f = tmp[j];
                    tmp[j] = tmp[j+1];
                    tmp[j+1] = tmp_f;
                }

            }
        return tmp;
    }
    @Override
    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getClass().getSimpleName() + " (" + this.getCntFloors());
        for(int i = 0; i < this.getCntFloors(); i++)
        {
            stringBuffer.append(", ");
            stringBuffer.append(this.getFloor(i).toString());
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object object)
    {
        Dwelling dwelling = (Dwelling)object;
        return(getClass() == object.getClass() && this.getCntFloors() == dwelling.getCntFloors() && getFloors() == dwelling.getFloors());
    }

    @Override
    public int hashCode()
    {
        int hash = this.getCntFloors();
        for(int i = 0; i < this.getCntFloors(); i++)
            hash ^= this.getFloor(i).hashCode();

        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Dwelling cloned = (Dwelling)super.clone();
        cloned.floors = this.floors.clone();
        for(int i = 0; i < this.getCntFloors(); i++)
            cloned.setFloor(i, (Floor)this.getFloor(i).clone());
        for(int i = 0; i < this.getCntSpaces(); i++)
            cloned.setSpace(i, (Space)this.getSpace(i).clone());
        return cloned;
    }

    @Override
    public Iterator<Floor> iterator()
    {
        return new FloorIterator(new Dwelling(this.getFloors()));
    }
}