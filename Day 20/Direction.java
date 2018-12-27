public enum Direction
{
  NORTH('N'), EAST('E'), WEST('W'), SOUTH('S');

  private char symbol;

  Direction(char symbol)
  {
    this.symbol = symbol;
  }

  public char getSymbol()
  {
    return symbol;
  }

  public static Direction getDirection(char c)
  {
    for (Direction d : values())
    {
      if (d.getSymbol() == c)
      {
        return d;
      }
    }

    throw new IllegalArgumentException(c + " is not a valid direction symbol");
  }

  public static Direction getOpposite(Direction d)
  {
    switch (d)
    {
      case NORTH:
      {
        return SOUTH;
      }

      case WEST:
      {
        return EAST;
      }

      case EAST:
      {
        return WEST;
      }

      default:
      {
        return NORTH;
      }
    }
  }
}
