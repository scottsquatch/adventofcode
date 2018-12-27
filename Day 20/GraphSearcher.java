import java.util.LinkedList;
import java.util.Collection;
import java.util.Stack;

public class GraphSearcher
{
  private BaseMap map;
  private Point current;

  public GraphSearcher(BaseMap map, Point start)
  {
    this.map = map;
    current = start;
  }

  public void search(String regex)
  {
    //System.out.println("Creating Graph Searcher with regex " + regex);
    int next = -1;
    Stack<Point> pointStack = new Stack<Point>();
    while (++next < regex.length())
    {
      char c = regex.charAt(next);
      //System.out.println("Processing character " + c);
      switch (c)
      {
        case '(':
        {
          // push point onto stack
          Point nextCurrent = new Point(current.x, current.y);
          pointStack.push(current);
          current = nextCurrent;
          break;
        }
        case ')':
        {
          // Pop point from Stack
          current = pointStack.pop();
          break;
        }
        case '|':
        {
          // Start over from top of stack
          current = pointStack.peek();
          break;
        }
        case '^':
        case '$':
        {
          // Markers, do nothing
          break;
        }
        default:
        {
          // We have direction, move along
          current = map.move(current, Direction.getDirection(c));
        }
      }
    }

    //System.out.println("Finished processing regex " + regex);
  }
}
