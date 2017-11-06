package buildings.exceptions;

public class InvalidSpaceAreaException extends IllegalArgumentException
{
    public InvalidSpaceAreaException() { }

    public InvalidSpaceAreaException(String message)
    {
        super(message);
    }
}
