package buildings;

import buildings.dwelling.Flat;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.iterators.SpaceIterator;
import java.util.Iterator;

public class SynchronizedFloor implements Floor
{
    private Space[] spaces;

    public SynchronizedFloor(Space...spaces)
    {
        this.spaces = spaces;
    }

    @Override
    public synchronized int getCnt()
    {
        return spaces.length;
    }

    @Override
    public synchronized float getArea()
    {
        float res = 0;
        for (Space space:spaces) {
            res+=space.getArea();
        }
        return res;
    }

    @Override
    public synchronized int getCntRooms(){
        int res = 0;
        for (Space space:spaces) {
            res+=space.getRooms();
        }
        return res;
    }

    @Override
    public synchronized Space[] getSpaces()
    {
        return spaces;
    }

    @Override
    public synchronized Space getSpace(int n)
    {
        return spaces[n];
    }

    @Override
    public synchronized void setSpace(int n)
    {
        spaces[n] = new Flat();
    }

    @Override
    public synchronized void setSpace(int n, Space space)
    {
        spaces[n] = space;
    }

    @Override
    public synchronized void addSpace(int n, Space space)
    {
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
    public synchronized void delSpace(int n) {
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
    public synchronized Space getBestSpace()
    {
        Space res = null;
        for (Space space: spaces) {
            if(res == null)
                res = space;
            if(res.getArea() < space.getArea())
                res = space;
        }
        return res;
    }

    @Override
    public synchronized Object clone() throws CloneNotSupportedException
    {
        Floor cloned = (Floor) super.clone();
        for (int i = 0; i < spaces.length; i++) {
            cloned.setSpace(i, (Space)spaces[i].clone());
        }
        return cloned;
    }

    @Override
    public synchronized int compareTo(Floor floor)
    {
        if(this.getCnt() > floor.getCnt())
            return 1;
        if(this.getCnt() == floor.getCnt())
            return 0;
        return -1;
    }

    @Override
    public synchronized Iterator<Space> iterator()
    {
        return new SpaceIterator(new SynchronizedFloor(this.getSpaces()));
    }
}
