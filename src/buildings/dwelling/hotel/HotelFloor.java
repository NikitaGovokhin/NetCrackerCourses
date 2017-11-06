package buildings.dwelling.hotel;

import buildings.interfaces.Space;
import buildings.dwelling.DwellingFloor;

public class HotelFloor extends DwellingFloor
{
    private int stars;
    private static final int DEFAULT_STARS = 1;

    public HotelFloor(int cnt)
    {
        super(cnt);
        this.stars = DEFAULT_STARS;
    }

    public HotelFloor(Space...spaces)
    {
        super(spaces);
        this.stars = DEFAULT_STARS;
    }

    public int getStars()
    {
        return stars;
    }

    public void setStars(int stars)
    {
        this.stars = stars;
    }

    @Override
    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getClass().getSimpleName() + " (" + this.getStars() + ", " + this.getCnt());
        for(int i = 0; i < this.getCnt(); i++)
        {
            stringBuffer.append(", ");
            stringBuffer.append(this.getSpace(i).toString());
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object object)
    {
        if(object instanceof HotelFloor) {
            HotelFloor hotelFloor = (HotelFloor) object;
            return (getClass() == object.getClass() && this.getCnt() == hotelFloor.getCnt() && getSpaces() == hotelFloor.getSpaces() && this.getStars() == hotelFloor.getStars());
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = this.getCnt();
        for(int i = 0; i < this.getCnt(); i++)
        {
            hash ^= this.getSpace(i).hashCode();
            hash ^= this.getStars();
        }
        return hash;
    }
}
