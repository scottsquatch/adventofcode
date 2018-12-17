public class DirectionHelper
{
  public static Direction reverseDirection(Direction direction)
  {
    if (direction == Direction.UP)
    {
      return Direction.DOWN;
    }
    else if (direction == Direction.DOWN)
    {
      return Direction.UP;
    }
    else if (direction == Direction.LEFT)
    {
      return Direction.RIGHT;
    }

    return Direction.LEFT;
  }

  public static Direction applyModifier(DirectionModifier modifier,
    Direction initialDirection)
  {
    if (modifier == DirectionModifier.LEFT)
    {
      switch (initialDirection)
      {
        case DOWN:
        {
          return Direction.RIGHT;
        }
        case LEFT:
        {
          return Direction.DOWN;
        }
        case RIGHT:
        {
          return Direction.UP;
        }
        default:
        {
          return Direction.LEFT;
        }
      }
    }
    else if (modifier == DirectionModifier.RIGHT)
    {
      switch (initialDirection)
      {
        case DOWN:
        {
          return Direction.LEFT;
        }
        case LEFT:
        {
          return Direction.UP;
        }
        case RIGHT:
        {
          return Direction.DOWN;
        }
        default:
        {
          return Direction.RIGHT;
        }
      }
    }
    else
    {
      return initialDirection;
    }
  }
}
