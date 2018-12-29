public enum CaveCellType
{
  ROCKY(0, '.'),
  WET(1, '='),
  NARROW(2, '|');

  private int riskScore;
  private char symbol;

  CaveCellType(int riskScore, char symbol)
  {
    this.riskScore = riskScore;
    this.symbol = symbol;
  }

  public int getRiskScore()
  {
    return riskScore;
  }

  public char getSymbol()
  {
    return symbol;
  }

  public boolean validTool(Tool t)
  {
    switch (this)
    {
      case ROCKY:
      {
        return t == Tool.CLIMBING_GEAR ||
               t == Tool.TORCH;
      }
      case WET:
      {
        return t == Tool.NEITHER ||
               t == Tool.CLIMBING_GEAR;
      }
      case NARROW:
      {
        return t == Tool.TORCH ||
               t == Tool.NEITHER;
      }
    }

    throw new IllegalArgumentException(this + " case not handled");
  }

  public Tool[] validTools()
  {
    Tool[] validTools = new Tool[2];
    switch (this)
    {
      case ROCKY:
      {
        validTools[0] = Tool.CLIMBING_GEAR;
        validTools[1] = Tool.TORCH;
        break;
      }
      case WET:
      {
        validTools[0] = Tool.NEITHER;
        validTools[1] = Tool.CLIMBING_GEAR;
        break;
      }
      case NARROW:
      {
        validTools[0] = Tool.TORCH;
        validTools[1] = Tool.NEITHER;
        break;
      }
      default:
      {
        throw new IllegalArgumentException(this + " case not handled");
      }
    }

    return validTools;
  }

  public Tool getOtherValidTool(Tool t)
  {
    switch (this)
    {
      case ROCKY:
      {
        if (t == Tool.CLIMBING_GEAR)
        {
          return Tool.TORCH;
        }
        else if (t == Tool.TORCH)
        {
          return Tool.CLIMBING_GEAR;
        }
      }
      case WET:
      {
        if (t == Tool.NEITHER)
        {
          return Tool.CLIMBING_GEAR;
        }
        else if (t == Tool.CLIMBING_GEAR)
        {
          return Tool.NEITHER;
        }
      }
      case NARROW:
      {
        if (t == Tool.TORCH)
        {
          return Tool.NEITHER;
        }
        else if (t == Tool.NEITHER)
        {
          return Tool.TORCH;
        }
      }
    }

    throw new IllegalArgumentException(this + " case not handled");
  }

  public static CaveCellType getCellType(int riskScore)
  {
    for (CaveCellType type : values())
    {
      if (riskScore == type.getRiskScore())
      {
        return type;
      }
    }

    throw new IllegalArgumentException(riskScore + " does not associate to a valid cave type");
  }
}
