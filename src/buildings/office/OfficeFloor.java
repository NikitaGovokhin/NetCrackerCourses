package buildings.office;

import buildings.exceptions.FloorIndexOutOfBoundsException;
import buildings.exceptions.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.iterators.SpaceIterator;
import com.sun.istack.internal.Nullable;
import org.jetbrains.annotations.Contract;
import java.util.Iterator;

import java.io.Serializable;

public class OfficeFloor implements Floor, Serializable, Cloneable
{
    class ListElement implements Serializable, Cloneable
    {
        ListElement next;
        Space data;

        ListElement(Space data, ListElement next)
        {
            this.next = next;
            this.data = data;
        }

        @Override
        public Object clone() throws CloneNotSupportedException
        {
            ListElement cloned = (ListElement) super.clone();
            return cloned;
        }
    }
    private ListElement head;

    @org.jetbrains.annotations.Nullable
    @Contract(pure = true)
    private @Nullable ListElement getElement(int n)
    {
        if(head == null)
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
        if(n == 0 || (head == null))
        {
            if(head == null)
            {
                head = new ListElement(null, null);
                head.next = new ListElement(listElement.data, head);
            }
            else
                head.next = new ListElement(listElement.data, head.next);
        }
        else
        {
            ListElement tmp = this.getElement(n-1);
            tmp.next = new ListElement(listElement.data, tmp.next);
        }
    }

    private void delElement(int n)
    {
        if(head == null)
            return;
        if(n == 0)
        {
            if (head.next.next != head)
            {
                ListElement tmp = head.next;
                head.next = tmp.next;
            }
            else
                head.next = head;
        }
        else
        {
            ListElement tmp = this.getElement(n-1);
            tmp.next = tmp.next.next;
        }
    }

    public OfficeFloor(int cnt)
    {
        if(cnt < 0)
            throw new FloorIndexOutOfBoundsException("Error! Count should be positive!");
        for(int i = 0; i < cnt; i++)
            this.addElement(i, new ListElement(new Office(), null));
    }

    public OfficeFloor(Space...spaces)
    {
        for(int i = 0; i < spaces.length; i++)
            this.addElement(i, new ListElement(spaces[i], null));
    }

    @Override
    public int getCnt()
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
    public float getArea()
    {
        if (head == null || head.next == head)
            return 0;
        float res = this.getElement(0).data.getArea();
        for (int i = 1; i < this.getCnt(); i++)
            res += this.getElement(i).data.getArea();
        return res;
    }

    @Override
    public int getCntRooms()
    {
        if (head == null || head.next == head)
            return 0;
        int res = 0;
        for(int i = 0; i < this.getCnt(); i++)
            res+=this.getElement(i).data.getRooms();
        return res;
    }

    @Override
    public Space[] getSpaces()
    {
        if(head == null || this.getCnt() == 0)
            return null;
        Space res[] = new Space[this.getCnt()];
        for(int i = 0; i < this.getCnt(); i++)
            res[i] = this.getElement(i).data;
        return res;
    }

    @Override
    public Space getSpace(int n)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCnt())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        if(head == null || this.getCnt() == 0)
            return null;
        return this.getElement(n).data;
    }

    @Override
    public void setSpace(int n)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCnt())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        if(head == null || head.next.next == null)
            return;
        this.getElement(n).data = new Office();
    }

    @Override
    public void setSpace(int n, Space space)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCnt())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        if(head == null)
            return;
        this.getElement(n).data = space;
    }

    @Override
    public void addSpace(int n, Space space)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCnt())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        this.addElement(n, new ListElement(space, null));
    }

    public void addSpace(Space space)
    {
        this.addElement(this.getCnt(), new ListElement(space, null));
    }

    @Override
    public void delSpace(int n)
    {
        if(n < 0)
            throw new SpaceIndexOutOfBoundsException("Error! Number should be positive!");
        if(n > this.getCnt())
            throw new SpaceIndexOutOfBoundsException("Error! This number was not found!");
        this.delElement(n);
    }

    @Override
    public Space getBestSpace()
    {
        if(head == null || this.getCnt() == 0)
            return null;
        Space max = this.getSpace(0);
        for(int i = 1; i < this.getCnt(); i++)
        {
            if(max.getArea() < this.getElement(i).data.getArea())
                max = this.getElement(i).data;
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
        OfficeFloor officeFloor = (OfficeFloor)object;
        return(getClass() == object.getClass() && this.getCnt() == officeFloor.getCnt() && getSpaces() == officeFloor.getSpaces());
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
        OfficeFloor cloned = (OfficeFloor)super.clone();
        cloned.head = null;
        Space[] tmp_spaces = this.getSpaces();
        for (int i = 0; i < tmp_spaces.length; i++)
            cloned.addSpace((Space)tmp_spaces[i].clone());
        return cloned;
    }

    @Override
    public Iterator<Space> iterator()
    {
        return new SpaceIterator(new OfficeFloor(this.getSpaces()));
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

