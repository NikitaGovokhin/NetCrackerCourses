package buildings;
import buildings.factories.DwellingFactory;
import buildings.interfaces.Building;
import buildings.interfaces.BuildingFactory;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Scanner;

public class Buildings
{
    public static BuildingFactory buildingFactory = new DwellingFactory();

    public static void outputBuilding (Building building, OutputStream out) throws IOException
    {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        try
        {
            dataOutputStream.writeInt(building.getCntFloors());
            for(int i = 0; i < building.getCntFloors(); i++)
            {
                dataOutputStream.writeInt(building.getFloor(i).getCnt());
                for(int j = 0; j < building.getFloor(i).getCnt(); j++)
                {
                    dataOutputStream.writeInt(building.getFloor(i).getSpace(j).getRooms());
                    dataOutputStream.writeFloat(building.getFloor(i).getSpace(j).getArea());
                }
            }
        }
        catch (IOException e)
        {
            e.getMessage();
        }
    }

    public static Building inputBuilding (InputStream in) throws IOException
    {
        Building building = null;
        DataInputStream dataInputStream = new DataInputStream(in);
        try
        {
            Floor[] floors = new Floor[dataInputStream.readInt()];
            for(int i = 0; i < floors.length; i++)
            {
                floors[i] = createFloor(dataInputStream.readInt());
                for(int j = 0; j < floors[i].getCnt(); j++)
                {
                    floors[i].getSpace(j).setRooms(dataInputStream.readInt());
                    floors[i].getSpace(j).setArea(dataInputStream.readFloat());
                }
            }
            building = createBuilding(floors);
        }
        catch (IOException e)
        {
            e.getMessage();
        }
        return building;
    }


    public static void writeBuilding (Building building, Writer out)
    {
        try(PrintWriter printWriter = new PrintWriter(out))
        {
            printWriter.print(building.getCntFloors());
            for(int i = 0; i < building.getCntFloors(); i++)
            {
                printWriter.print(building.getFloor(i).getCnt());
                for (int j = 0; j < building.getCntSpaces(); j++)
                {
                    printWriter.print(building.getSpace(j).getRooms() + " ");
                    printWriter.print(String.valueOf(building.getSpace(j).getArea()) + " ");
                }
            }
            printWriter.println();
        }
    }

    public static Building readBuilding (Reader in) throws IOException
    {
        Building building = null;
        try
        {
            StreamTokenizer streamTokenizer = new StreamTokenizer(in);
            while(streamTokenizer.nextToken() != streamTokenizer.TT_NUMBER) {}
            Floor[] floors = new Floor[(int) streamTokenizer.nval];
            for (int i = 0; i < floors.length; i++)
            {
                while(streamTokenizer.nextToken() != streamTokenizer.TT_NUMBER) { }
                floors[i] = createFloor((int) streamTokenizer.nval);
                for(int j = 0; j < floors[i].getCnt(); j++)
                {
                    while(streamTokenizer.nextToken() != streamTokenizer.TT_NUMBER) { }
                    floors[i].getSpace(j).setRooms((int) streamTokenizer.nval);
                    while(streamTokenizer.nextToken() != streamTokenizer.TT_NUMBER) { }
                    floors[i].getSpace(j).setArea((float)streamTokenizer.nval);
                }
            }
            building = createBuilding(floors);
        }
        catch (IOException e)
        {
            e.getMessage();
        }
        return building;
    }

    public static void serializeBuilding (Building building, OutputStream out) throws IOException
    {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        try
        {
            objectOutputStream.writeObject(building);
        }
        catch (IOException e)
        {
            e.getMessage();
        }
    }

    public static Building deserialaizeBuilding (InputStream in) throws IOException
    {
        Building building = null;
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        try
        {
            building = (Building)objectInputStream.readObject();
        }
        catch (IOException e)
        {
            e.getMessage();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return building;
    }

    public static Building readBuilding(Scanner scanner)
    {
        Floor[] floors = new Floor[scanner.nextInt()];
        for(int i = 0; i < floors.length; i++)
        {
            floors[i] = createFloor(scanner.nextInt());
            for(int j = 0; j < floors[i].getCnt(); j++)
            {
                floors[i].getSpace(j).setRooms(scanner.nextInt());
                floors[i].getSpace(j).setArea(scanner.nextFloat());
            }
        }
        return createBuilding(floors);
    }

    public static void writeBuildingFormat(Building building, Writer out)
    {
        try(Formatter formatter = new Formatter(new PrintWriter(out)))
        {
            formatter.format("Count of floors is %d ", building.getCntFloors());
            for(int i = 0; i < building.getCntFloors(); i++)
            {
                formatter.format("Count of spaces is %d ", building.getFloor(i).getCnt());
                for(int j = 0; j < building.getFloor(i).getCnt(); j++)
                {
                    formatter.format("Count of rooms is %d ", building.getFloor(i).getSpace(j).getRooms());
                    formatter.format("Area is %f ", building.getFloor(i).getSpace(j).getArea());
                }
            }
        }
    }

    public static <T extends Comparable<T>> void sort(T[] data)
    {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length - 1; j++) {
                if(data[j].compareTo(data[j+1]) > 0)
                {
                    T tmp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = tmp;
                }
            }
        }
    }

    public static <T> void sort(T[] data, Comparator<T> comparator)
    {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length - 1; j++) {
                if(comparator.compare(data[j], data[j+1]) > 0)
                {
                    T tmp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = tmp;
                }
            }
        }
    }

    public static void setBuildingFactory(BuildingFactory buildingFactory) {
        Buildings.buildingFactory = buildingFactory;
    }

    public static Space createSpace(float area)
    {
        return buildingFactory.createSpace(area);
    }

    public static Space createSpace(int roomsCount, float area) {
        return buildingFactory.createSpace(roomsCount, area);
    }

    public static Floor createFloor(int spaceCount)
    {
        return buildingFactory.createFloor(spaceCount);
    }

    public static Floor createFloor(Space...spaces)
    {
        return buildingFactory.createFloor(spaces);
    }

    public static Building createBuilding(int floorsCount, int[] spacesCount) {
        return buildingFactory.createBuilding(floorsCount, spacesCount);
    }

    public static Building createBuilding(Floor...floors)
    {
        return buildingFactory.createBuilding(floors);
    }

    public static Floor synchronizedFloor(Space...spaces) { return new SynchronizedFloor(spaces); }

    //reflection

    public static Space createSpace(float area, Class spaceClass){
        try {
            Class space = Class.forName(spaceClass.getName());
            Constructor constructor = space.getConstructor(new Class[]{float.class});
            Space result = (Space) constructor.newInstance(area);
            return result;
        }
        catch (IllegalAccessException|InstantiationException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException e){
            System.out.println("Space was not created!!!");
        }
        return null;
    }

    public static Space createSpace(int roomsCnt, float area, Class spaceClass){
        try {
            Class space = Class.forName(spaceClass.getName());
            Constructor constructor = space.getConstructor(new Class[]{float.class, int.class});
            Space result = (Space) constructor.newInstance(area, roomsCnt);
            return result;
        }
        catch (IllegalAccessException|InstantiationException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException e){
            System.out.println("Space was not created!!!");
        }
        return null;
    }

    public static Floor createFloor(int spaceCount, Class floorClass) {
        try {
            Class floor = Class.forName(floorClass.getName());
            Constructor constructor = floor.getConstructor(new Class[]{int.class});
            Floor result = (Floor) constructor.newInstance(spaceCount);
            return result;
        }
        catch (IllegalAccessException|InstantiationException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException e){
            System.out.println("Floor was not created!!!");
        }
        return null;
    }

    public static Floor createFloor(Class floorClass, Space...spaces) {
        try {
            Class floor = Class.forName(floorClass.getName());
            Constructor constructor = floor.getConstructor(new Class[]{Space[].class});
            Floor result = (Floor) constructor.newInstance((Object) spaces);
            return result;
        }
        catch (IllegalAccessException|InstantiationException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException e){
            System.out.println("Floor was not created!!!");
        }
        return null;
    }

    public static Building createBuilding(Class buildingClass, int floorsCount, int...spacesCount) {
        try {
            Class building = Class.forName(buildingClass.getName());
            Constructor constructor = building.getConstructor(new Class[]{int.class, int[].class});
            Building result = (Building) constructor.newInstance(floorsCount, spacesCount);
            return result;
        }
        catch (IllegalAccessException|InstantiationException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException e){
            System.out.println("Floor was not created!!!");
        }
        return null;
    }

    public static Building createBuilding(Class buildingClass, Floor...floors) {
        try {
            Class building = Class.forName(buildingClass.getName());
            Constructor constructor = building.getConstructor(new Class[]{Floor[].class});
            Building result = (Building) constructor.newInstance((Object) floors);
            return result;
        }
        catch (IllegalAccessException|InstantiationException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException e){
            System.out.println("Floor was not created!!!");
        }
        return null;
    }

    public static Building inputBuilding (InputStream in, Class buildingClass, Class floorClass, Class spaceClass) throws IOException {
        Building building = null;
        DataInputStream dataInputStream = new DataInputStream(in);
        try {
            Floor[] floors = new Floor[dataInputStream.readInt()];
            for(int i = 0; i < floors.length; i++) {
                floors[i] = createFloor(dataInputStream.readInt(), floorClass);
                for(int j = 0; j < floors[i].getCnt(); j++)
                    floors[i].setSpace(j, createSpace(dataInputStream.readInt(), dataInputStream.readFloat(), spaceClass));
            }
            building = createBuilding(buildingClass, floors);
        }
        catch (IOException e){
            System.out.println("Building was not created!!!");
        }
        return building;
    }

    public static Building readBuilding (Reader in, Class buildingClass, Class floorClass, Class spaceClass) throws IOException {
        Building building = null;
        try {
            StreamTokenizer streamTokenizer = new StreamTokenizer(in);
            while(streamTokenizer.nextToken() != streamTokenizer.TT_NUMBER) {}
            Floor[] floors = new Floor[(int) streamTokenizer.nval];
            for (int i = 0; i < floors.length; i++) {
                while(streamTokenizer.nextToken() != streamTokenizer.TT_NUMBER) { }
                floors[i] = createFloor((int) streamTokenizer.nval, floorClass);
                for(int j = 0; j < floors[i].getCnt(); j++) {
                    while(streamTokenizer.nextToken() != streamTokenizer.TT_NUMBER) { }
                    Integer n1 = (int) streamTokenizer.nval;
                    while(streamTokenizer.nextToken() != streamTokenizer.TT_NUMBER) { }
                    Float n2 = (float) streamTokenizer.nval;
                    floors[i].setSpace(j, createSpace(n1, n2, spaceClass));
                }
            }
            building = createBuilding(buildingClass, floors);
        }
        catch (IOException e) {
            System.out.println("Building was not created!!!");
        }
        return building;
    }

    public static Building readBuilding(Scanner scanner, Class buildingClass, Class floorClass, Class spaceClass) {
        Floor[] floors = new Floor[scanner.nextInt()];
        for(int i = 0; i < floors.length; i++) {
            floors[i] = createFloor(scanner.nextInt(), floorClass);
            for(int j = 0; j < floors[i].getCnt(); j++)
                floors[i].setSpace(j, createSpace(scanner.nextInt(), scanner.nextFloat(), spaceClass));
        }
        return createBuilding(buildingClass, floors);
    }
}
