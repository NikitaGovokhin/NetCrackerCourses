package buildings.interfaces;

public interface Space extends Cloneable, Comparable<Space>
{
    public int getRooms();
    public void setRooms(int rooms);
    public float getArea();
    public void setArea(float area);
    public Object clone() throws CloneNotSupportedException;
}
