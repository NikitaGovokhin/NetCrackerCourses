package buildings.office;

import buildings.exceptions.FloorIndexOutOfBoundsException;
import buildings.exceptions.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.iterators.FloorIterator;
import com.sun.istack.internal.Nullable;
import org.jetbrains.annotations.Contract;
import java.util.Iterator;
import java.io.Serializable;

public class OfficeBuilding implements Building, Serializable, Cloneable
{
    class ListElement implements Serializable, Cloneable
    {
        ListElement next;
        ListElement prev;
        Floor data;

        ListElement(Floor data, ListElement next, ListElement prev)
        {
            this.next = next;
            this.data = data;
            this.prev = prev;
        }

        @Override
        public Object clone() throws CloneNotSupportedException
        {
            ListElement cloned = (ListElement)super.clone();
            return cloned;

        }
    }

    private ListElement head;

    @org.jetbrains.annotations.Nullable
    @Contract(pure = true)
    private @Nullable ListElement getElement(int n)
    {
        if(head == null || n < 0)
            return null;
        ListElement tmp = head.next;
        int j = 0;
        while(tmp != head)
        {
            if(j++ == n)
                return tmp;
            tmp = tmp.next;
        }
        return null;
    }

    private void addElement(int n, ListElement listElement)
    {
        if(n < 0)
            return;
        if(n == 0 || (head == null))
        {
            if(head == null)
            {
                head = new ListElement(null, null, null);
                head.next = new ListElement(listElement.data, head, head);
                head.prev = head.next;
            }
            else
                head.next = new ListElement(listElement.data, head.next, head);
        }
        else
        {
            ListElement tmp = this.getElement(n-1);
            tmp.next = new ListElement(listElement.data, tmp.next, tmp.prev);
        }
    }

    private void delElement(int n)
    {
        if(head == null || n < 0)
            return;
        if(n == 0)
        {
            if (head.next.next != head)
            {
                ListElement tmp = head.next;
                head.next = tmp.next;
                head.next.prev = head;
            }
            else
            {
                head.next = head;
                head.prev = head;
            }
        }
        else
        {
            ListElement tmp = this.getElement(n-1);
            tmp.next = tmp.next.next;
            tmp.next.prev = tmp;
        }
    }

    public OfficeBuilding(int n, int...cnt)
    {
        if(n < 0)
            throw new FloorIndexOutOfBoundsException("Error! Number should be positive!");
        for(int i = 0; i < n; i++)
            this.addElement(i, new ListElement(new OfficeFloor(cnt[i]), null, null));
    }

    public OfficeBuilding(Floor...floors)
    {
        for(int i = 0; i < floors.length; i++)
            this.addElement(i, new ListElement(floors[i], null, null));
    }

    @Override
    public int getCntFloors()
    {
        if (head == null || head.next == head)
            return 0;
        int j = 0;
        ListElement tmp = this.getElement(j);
        if(tmp != null)
        {
            while (tmp.next != head)
            {
                tmp = tmp.next;
                j++;
            }
        }
        return ++j;
    }

    @Override
    public int getCntSpaces()
    {
        if (head == null || head.next == head)
            return 0;
        int j = 0;
        for(int i = 0; i < this.getCntFloors(); i ++)
            j += this.getElement(i).data.getCnt();
        return j;
    }

    @Override
    public float getAreaSpaces()
    {
        if(head == null || head.next == head)
            return 0;
        float res = this.getElement(0).data.getArea();
        for (int i = 1; i < this.getCntFloors(); i++)
            res += this.getElement(i).data.getArea();
        return res;
    }

    @Override
    public int getCntRooms()
    {
        if (head == null)
            return 0;
        int res = 0;
        for(int i = 0; i < this.getCntFloors(); i++)
            res+=this.getElement(i).data.getCntRooms();
        return res;
    }

    @Override
    public Floor[] getFloors()
    {
        if(head == null || this.getCntFloors() == 0)
            return null;
        Floor[] res = new Floor[this.getCntFloors()];
        for(int i = 0; i < this.getCntFloors(); i++)
            res[i] = this.getElement(i).data;
        return res;
    }

    @Override
    public Floor getFloor(int n)
    {
        if(n < 0)
            throw new FloorIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntFloors())
            throw new FloorIndexOutOfBoundsException("Error! This number was not found!");
        if(head == null || this.getCntFloors() == 0 || n < 0)
            return null;
        return this.getElement(n).data;
    }

    @Override
    public void setFloor(int n, Floor floor)
    {
        if(n < 0)
            throw new FloorIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntFloors())
            throw new FloorIndexOutOfBoundsException("Error! This number was not found!");
        if(head == null)
            return;
        this.getElement(n).data = floor;
    }

    @Override
    public Space getSpace(int n)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntSpaces())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        int tmp_n = n;
        for(int i = 0; i < this.getCntFloors(); i++)
            for(int j = 0; j < this.getFloor(i).getCnt(); j++)
                if((tmp_n--) == 0)
                    return this.getFloor(i).getSpace(j);
        return null;
    }

    @Override
    public void setSpace(int n, Space space)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntSpaces())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        int tmp_n = n;
        for(int i = 0; i < this.getCntFloors(); i++)
            for(int j = 0; j < this.getFloor(i).getCnt(); j++)
                if((tmp_n--) == 0)
                {
                    this.getFloor(i).setSpace(j, space);
                    return;
                }
    }

    @Override
    public void addSpace(int n, Space space)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntSpaces())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        int tmp_n = n;
        for(int i = 0; i < this.getCntFloors(); i++)
            for(int j = 0; j < this.getFloor(i).getCnt(); j++)
                if((tmp_n--) == 0)
                {
                    this.getFloor(i).addSpace(j, space);
                    return;
                }
    }

    @Override
    public void delSpace(int n)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCntSpaces())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        int tmp_n = n;
        for(int i = 0; i < this.getCntFloors(); i++)
            for(int j = 0; j < this.getFloor(i).getCnt(); j++)
                if((tmp_n--) == 0)
                {
                    this.getFloor(i).delSpace(j);
                    return;
                }
    }

    @Override
    public Space getBestSpace()
    {
        if(head == null || this.getCntSpaces() == 0)
            return null;
        Space max = this.getSpace(0);
        for(int i = 1; i < this.getCntSpaces(); i++)
        {
            if(max.getArea() < this.getSpace(i).getArea())
                max = this.getSpace(i);
        }
        return max;
    }

    @Override
    public Space[] getSortedSpaces()
    {
        Space tmp[] = new Office[this.getCntSpaces()];
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName() + " (" + this.getCntFloors());
        for(int i = 0; i < this.getCntFloors(); i++)
        {
            stringBuilder.append(", ");
            stringBuilder.append(this.getFloor(i).toString());
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object)
    {
        OfficeBuilding officeBuilding = (OfficeBuilding)object;
        return(getClass() == object.getClass() && this.getCntFloors() == officeBuilding.getCntFloors() && getFloors() == officeBuilding.getFloors());
    }

    @Override
    public int hashCode()
    {
        int hash = this.getCntFloors();
        for(int i = 0; i < this.getCntFloors(); i++)
            hash ^= this.getFloor(i).hashCode();

        return hash;
    }

    public void addFloor(Floor floor)
    {
        this.addElement(this.getCntFloors(), new ListElement(floor, null, null));
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        OfficeBuilding cloned = (OfficeBuilding) super.clone();
        cloned.head = null;
        Floor[] tmp_floors = this.getFloors();
        for (int i = 0; i < tmp_floors.length; i++)
            cloned.addFloor((Floor)this.getFloor(i).clone());
        return cloned;
    }

    @Override
    public Iterator<Floor> iterator()
    {
        return new FloorIterator(new OfficeBuilding(this.getFloors()));
    }
}

