public class Cart
{
  private DirectionModifier intersectionSelectionMethod;
  private Direction direction;

  public Cart(Character cartChar)
  {

    intersectionSelectionMethod = DirectionModifier.LEFT;
    this.direction = getDirectionFromChar(cartChar);
  }

  public void setDirection(Direction newDirection)
  {
    direction = newDirection;
  }

  private Direction getDirectionFromChar(Character cartChar)
  {
    switch (cartChar)
    {
      case '^':
      {
        return Direction.UP;
      }
      case '>':
      {
        return Direction.RIGHT;
      }
      case '<':
      {
        return Direction.LEFT;
      }
      case 'v':
      {
        return Direction.DOWN;
      }
    }
    return Direction.UP;
  }

  private Character getCharFromDirection(Direction direction)
  {
    switch (direction)
    {
      case UP:
      {
        return '^';
      }
      case DOWN:
      {
        return 'v';
      }
      case LEFT:
      {
        return '<';
      }
      case RIGHT:
      {
        return '>';
      }
    }

    return ' ';
  }

  public Direction getDirectionToGoAtIntersection()
  {
    Direction directionToGo = DirectionHelper.applyModifier(
      intersectionSelectionMethod,
      direction);

    switch (intersectionSelectionMethod)
    {
      case LEFT:
      {
        intersectionSelectionMethod = DirectionModifier.STRAIGHT;
        break;
      }
      case STRAIGHT:
      {
        intersectionSelectionMethod = DirectionModifier.RIGHT;
        break;
      }
      default:
      {
        intersectionSelectionMethod = DirectionModifier.LEFT;
      }
    }

    return directionToGo;
  }

  @Override
  public String toString()
  {
    return getCharFromDirection(this.direction).toString();
  }
}
