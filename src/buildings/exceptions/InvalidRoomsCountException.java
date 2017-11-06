package buildings.exceptions;

public class InvalidRoomsCountException extends IllegalArgumentException
{
    public InvalidRoomsCountException() { }

    public InvalidRoomsCountException(String message)
    {
        super(message);
    }
}
