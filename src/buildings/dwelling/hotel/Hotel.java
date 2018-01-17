package buildings.dwelling.hotel;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.dwelling.Dwelling;
import buildings.dwelling.Flat;

public class Hotel extends Dwelling {
    public Hotel(int n, int... cnt) {
        super(n, cnt);
    }

    public Hotel(Floor... floors) {
        super(floors);
    }

    public int getStars() {
        int res = 0;
        Floor[] floors = this.getFloors();
        for (int i = 0; i < this.getCntFloors(); i++)
            if (this.getFloor(i) instanceof HotelFloor)
                if (((HotelFloor) this.getFloor(i)).getStars() > res)
                    res = ((HotelFloor) this.getFloor(i)).getStars();
        return res;
    }

    @Override
    public Space getBestSpace() {
        Space res = null;
        float[] c = {(float) 0.25, (float) 0.5, (float) 1, (float) 1.25, (float) 1.5};
        for (int i = 0; i < this.getCntFloors(); i++) {
            if (this.getFloor(i) instanceof HotelFloor) {
                for (int j = 0; j < this.getFloor(i).getCnt(); j++) {
                    if (this.getFloor(i).getSpace(j) instanceof Flat) {
                        if (res == null)
                            res = this.getFloor(i).getSpace(j);
                        else {
                            if (c[((HotelFloor) (this.getFloor(i))).getStars() - 1] * res.getArea() < c[((HotelFloor) this.getFloor(i)).getStars() - 1] * this.getFloor(i).getSpace(j).getArea())
                                res = this.getFloor(i).getSpace(j);
                        }
                    }
                }
            }

        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName() + " (" + this.getStars() + ", " + this.getCntFloors());
        for (int i = 0; i < this.getCntFloors(); i++) {
            stringBuilder.append(", ");
            stringBuilder.append(this.getFloor(i).toString());
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Hotel) {
            Hotel hotel = (Hotel) object;
            return (getClass() == object.getClass() && this.getCntFloors() == hotel.getCntFloors() && getFloors() == hotel.getFloors() && this.getStars() == hotel.getStars());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = this.getCntFloors();
        for (int i = 0; i < this.getCntFloors(); i++) {
            hash ^= this.getFloor(i).hashCode();
            hash ^= this.getStars();
        }
        return hash;
    }
}
