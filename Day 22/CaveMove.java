public class CaveMove
{
  public final int minutes;
  public final Point nextPoint;
  public final double hueristicValue;
  public final Tool tool;

  public CaveMove(int minutes, Point nextPoint, double hueristicValue, Tool tool)
  {
    this.minutes = minutes;
    this.nextPoint = nextPoint;
    this.hueristicValue = hueristicValue;
    this.tool = tool;
  }

  @Override
  public String toString()
  {
    return "Minutes: " + minutes + ", NextPoint: " + nextPoint + ", hueristicValue: " + hueristicValue + ", tool: " + tool;
  }
}
