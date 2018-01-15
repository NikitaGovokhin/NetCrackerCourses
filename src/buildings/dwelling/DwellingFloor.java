package buildings.dwelling;

import buildings.exceptions.InvalidRoomsCountException;
import buildings.exceptions.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.iterators.SpaceIterator;

import java.io.Serializable;
import java.util.Iterator;

public class DwellingFloor implements Floor, Serializable, Cloneable
{
    private Space spaces[];

    public DwellingFloor(int cnt)
    {
        if(cnt < 0)
            throw new InvalidRoomsCountException("Error! Count should be positive!");
        spaces = new Space[cnt];
        for(int i = 0; i < cnt; i++)
        {
            spaces[i] = new Flat();
        }
    }

    public DwellingFloor(Space...spaces)
    {
        this.spaces = new Space[spaces.length];
        for(int i = 0; i < spaces.length; i++)
            this.spaces[i] = spaces[i];
    }

    @Override
    public int getCnt()
    {
        return spaces.length;
    }

    @Override
    public float getArea()
    {
        float area = spaces[0].getArea();
        for(int i = 1; i < spaces.length; i++)
            area += spaces[i].getArea();
        return area;
    }

    @Override
    public int getCntRooms()
    {
        int cnt = spaces[0].getRooms();
        for(int i = 1; i < spaces.length; i++)
            cnt += spaces[i].getRooms();
        return cnt;
    }

    @Override
    public Space[] getSpaces()
    {
        return spaces;
    }

    @Override
    public Space getSpace(int n)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n <= spaces.length)
            return spaces[n];
        else
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
    }

    @Override
    public void setSpace(int n)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n<= spaces.length)
            spaces[n] = new Flat();
        else
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
    }

    @Override
    public void setSpace(int n, Space space)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n<= spaces.length)
            spaces[n] = space;
        else
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
    }

    @Override
    public void addSpace(int n, Space space)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > spaces.length)
        {
            Space tmp[]= new Space[n];
            for(int i = 0; i < spaces.length; i++)
            {
                tmp[i] = new Flat();
            }
            for(int i = 0; i < spaces.length; i++)
            {
                tmp[i] = spaces[i];
            }
            tmp[n-1] = space;
            spaces = tmp;
        }
        else
        {
            Space tmp[]= new Space[spaces.length + 1];
            if(n == 0) {
                tmp[n] = space;
                for (int i = 0; i < spaces.length; i++) {
                    tmp[i+1] = spaces[i];
                }
            }
            else {
                for (int i = 0; i < n; i++) {
                    tmp[i] = spaces[i];
                }
                tmp[n] = space;
                for (int i = n + 1; i < tmp.length; i++) {
                    tmp[i] = spaces[i-1];
                }
            }
            spaces = tmp;
        }
    }

    @Override
    public void delSpace(int n)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        Space tmp[] = new Space[spaces.length - 1];
        if(n == 0)
        {
            for(int i = 0; i < spaces.length-1; i++)
            {
                tmp[i] = spaces[i+1];
            }
        }
        else
        {
            for(int i = 0; i < n; i++) {
                tmp[i] = spaces[i];
            }
            for(int i = n; i < tmp.length; i++) {
                tmp[i] = spaces[i+1];
            }
        }
        spaces = tmp;
    }

    @Override
    public Space getBestSpace()
    {
        Space max = spaces[0];
        for(int i = 1; i < spaces.length; i++)
        {
            if(max.getArea() < spaces[i].getArea())
                max = spaces[i];
        }
        return max;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName() + " (" + this.getCnt());
        for(int i = 0; i < this.getCnt(); i++)
        {
            stringBuilder.append(", ");
            stringBuilder.append(this.getSpace(i).toString());
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object)
    {
        DwellingFloor dwellingFloor = (DwellingFloor)object;
        return(getClass() == object.getClass() && this.getCnt() == dwellingFloor.getCnt() && getSpaces() == dwellingFloor.getSpaces());
    }

    @Override
    public int hashCode()
    {
        int hash = this.getCnt();
        for(int i = 0; i < this.getCnt(); i++)
        {
            hash ^= this.getSpace(i).hashCode();

        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        DwellingFloor cloned = (DwellingFloor)super.clone();
        cloned.spaces = this.spaces.clone();
        for(int i = 0; i < this.getCnt(); i++)
            cloned.setSpace(i, (Space)this.getSpace(i).clone());
        return cloned;
    }

    @Override
    public Iterator<Space> iterator()
    {
        return new SpaceIterator(new DwellingFloor(this.getSpaces()));
    }

    @Override
    public int compareTo(Floor floor)
    {
        if(this.getCnt() > floor.getCnt())
            return 1;
        if(this.getCnt() == floor.getCnt())
            return 0;
        return -1;
    }
}
