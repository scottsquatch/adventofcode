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
