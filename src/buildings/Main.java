package buildings;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFloor;
import buildings.interfaces.Space;
import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;
import buildings.threads.*;

public class Main {
    public static void main(String [] argv)
    {
        DwellingFloor floors[] = new DwellingFloor[20];
        for(int j = 0; j < 20; j++)
        {
            Flat flats[] = new Flat[5+j];
            for(int i = 0; i < (5+j); i++)
            {
                flats[i] = new Flat(50+i*j, 2+i*j);
            }
            floors[j] = new DwellingFloor(flats);
        }
        System.out.println(floors[1].toString());
        Dwelling dwelling = new Dwelling(floors);
        dwelling.addSpace(3, new Flat(1000, 20));
        System.out.printf("The best space of flat is %.2f with %d rooms at home with %d floors\n", dwelling.getBestSpace().getArea(), dwelling.getBestSpace().getRooms(), floors.length);
        dwelling.delSpace(3);
        OfficeFloor officeFloor = new OfficeFloor(5);
        System.out.println(officeFloor.getCnt());
        System.out.println(officeFloor.getBestSpace().getArea());
        OfficeBuilding officeBuilding = new OfficeBuilding(4, 2, 2, 3, 1);
        officeBuilding.addSpace(2,new Office(3, 400));
        System.out.println(officeBuilding.getBestSpace().getArea());
        Space tmp[] = officeBuilding.getSortedSpaces();
        for(int i = 0; i < tmp.length; i++)
            System.out.println(i+" "+tmp[i].getArea());
        Office f = new Office(20,30);
        dwelling.addSpace(2,f);
        System.out.println();
        officeBuilding.delSpace(2);
        Space tmp1[] = officeBuilding.getSortedSpaces();
        for(int i = 0; i < tmp1.length; i++)
            System.out.println(i+" "+tmp1[i].getArea());
        System.out.println(dwelling.getSpace(2));
        System.out.println(dwelling.getSpace(1));
        System.out.println(dwelling);
        Office cc = new Office(1,15);
        System.out.println(cc.hashCode());
        System.out.println("clones 4 dwfl:");
        try
        {
            System.out.println(floors[1]);
            DwellingFloor ff = (DwellingFloor)floors[1].clone();
            floors[1].setSpace(1, new Flat(1111,11111));
            System.out.println(ff);
            System.out.println(floors[1]);
        }
        catch (CloneNotSupportedException e)
        {
            System.out.println("error");
        }
        Flat f1 = new Flat(1,1);
        OfficeFloor ff1 = new OfficeFloor(3);

        HotelFloor hf = new HotelFloor(3);
        hf.setStars(5);
        System.out.println(hf);
        Hotel h = new Hotel(hf);
        h.setSpace(1, new Flat(1111,1111));
        System.out.println(h);
        System.out.println(h.getBestSpace());
        System.out.println("of clones:");
        Office t = new Office(111,111);
        try {
            Office tt = (Office) t.clone();
            System.out.println(tt);
        }
        catch(CloneNotSupportedException e){}


        try
        {
            System.out.println(ff1);
            OfficeFloor ff2 = (OfficeFloor) ff1.clone();
            System.out.println(ff2);
            ff1.setSpace(1,new Office(111,111));
            System.out.println(ff1);
            System.out.println(ff2);
        }
        catch (CloneNotSupportedException e)
        {
            System.out.println("error");
        }

        officeBuilding.addFloor(new OfficeFloor(5));
        System.out.println(officeBuilding);

        System.out.println("ob clone:");
        try
        {
            OfficeBuilding ff2 = (OfficeBuilding) officeBuilding.clone();
            System.out.println(ff2);
            officeBuilding.setSpace(1,new Flat(11111,111));
            System.out.println(officeBuilding);
            System.out.println(ff2);
        }
        catch (CloneNotSupportedException e)
        {
            System.out.println("error");
        }

        System.out.println("Test of Factory:");
        System.out.println(Buildings.createBuilding(floors));

        for (Space of: officeFloor)
        {
            System.out.println(of);
        }

        /*DwellingFloor df = new DwellingFloor(5);
        System.out.println("threads test:");
        Repairer repairer1 = new Repairer(officeFloor);
        repairer1.setPriority(Thread.MIN_PRIORITY);
        Repairer repairer2 = new Repairer(hf);
        repairer2.setPriority(Thread.NORM_PRIORITY);
        Cleaner cleaner = new Cleaner(df);
        cleaner.setPriority(Thread.MAX_PRIORITY);
        repairer1.start();
        cleaner.start();
        cleaner.interrupt();
        repairer2.start();*/

        DwellingFloor df_test = new DwellingFloor(5);
        System.out.println("Test semaphore:");
        RepairerCleanerSemaphore repairerCleanerSemaphore = new RepairerCleanerSemaphore();
        Thread r = new Thread(new SequentalRepairer(df_test, repairerCleanerSemaphore));
        Thread c = new Thread(new SequentalCleaner(df_test, repairerCleanerSemaphore));
        c.start();
        r.start();



    }
}