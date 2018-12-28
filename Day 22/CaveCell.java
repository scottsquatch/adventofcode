public class CaveCell
{
  public final int erosionLevel;
  public final long geologicIndex;
  public final CaveCellType type;

  public CaveCell(long geologicIndex, int depth)
  {
    this.geologicIndex = geologicIndex;

    this.erosionLevel = ((int)geologicIndex + depth) % 20183;

    type = CaveCellType.getCellType(erosionLevel % 3);
  }

  @Override
  public String toString()
  {
    return type.getSymbol() + "";
  }
}
