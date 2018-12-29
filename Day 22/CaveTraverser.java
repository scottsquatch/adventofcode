import java.util.HashSet;

public class CaveTraverser
{
  private RescueUnit unit;
  private Cave cave;
  private int minutesPassed;
  private Point currentPosition;
  private HashSet<Point> visited;

  public CaveTraverser(RescueUnit unit, Cave cave)
  {
    this.unit = unit;
    this.cave = cave;
    minutesPassed = 0;
    currentPosition = Cave.CAVE_ENTRANCE_POINT;
    visited = new HashSet<Point>();
  }

  // Returns true if the target has been saved
  public void makeNextMove()
  {
    if (targetRescued())
    {
      return;
    }
    else if (currentPosition.equals(cave.getTargetPoint()))
    {
      unit.equippedTool = Tool.TORCH;
      minutesPassed += 7;
    }
    else
    {
      Point[] adj = cave.adj(currentPosition);

      CaveMove move = null;
      for (Point p : adj)
      {
        if (!visited.contains(p))
        {
          CaveMove potentialMove = getPotentialMove(currentPosition, p);
          System.out.println("Potential Move: " + potentialMove);
          if (potentialMove.hueristicValue != Double.POSITIVE_INFINITY
              && (move == null || potentialMove.hueristicValue < move.hueristicValue))
          {
            move = potentialMove;
          }
        }
      }

      System.out.println(move);
      if (!visited.contains(move.nextPoint))
      {
        visited.add(move.nextPoint);
      }

      if (move.minutes != 7)
      {
        currentPosition = move.nextPoint;
      }

      minutesPassed += move.minutes;
      unit.equippedTool = move.tool;
    }
  }

  public boolean targetRescued()
  {
    return currentPosition.equals(cave.getTargetPoint()) && unit.equippedTool == Tool.TORCH;
  }

  private CaveMove getPotentialMove(Point start, Point end)
  {
    // first determine if it is possible
    double distance = Math.sqrt(Math.pow(cave.getTargetPoint().x - end.x, 2) + Math.pow(cave.getTargetPoint().y - end.y, 2));
    CaveCell startCell = cave.get(start);
    CaveCell endCell = cave.get(end);
    if (!endCell.type.validTool(unit.equippedTool))
    {
      // Now see if there is another tool which is valid for both cells
      for (Tool t : Tool.values())
      {
        if (t != unit.equippedTool &&
            startCell.type.validTool(t) &&
            endCell.type.validTool(t))
        {
          return new CaveMove(7, start, distance + 7, t);
        }
      }

      // No match found, move is impossible
      return new CaveMove(1, start, Double.POSITIVE_INFINITY, unit.equippedTool);
    }

    return new CaveMove(1, end, distance, unit.equippedTool);
  }

  public int getMinutesPassed()
  {
    return minutesPassed;
  }
}
