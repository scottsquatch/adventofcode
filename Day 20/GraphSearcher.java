import java.util.LinkedList;
import java.util.Collection;

public class GraphSearcher
{
  private BaseMap map;
  private Point current;
  private LinkedList<GraphSearcher> subSearchers;

  public GraphSearcher(BaseMap map, Point start)
  {
    this.map = map;
    current = start;
    subSearchers = new LinkedList<GraphSearcher>();
  }

  public void search(String regex)
  {
    //System.out.println("Creating Graph Searcher with regex " + regex);
    int next = -1;
    int parenthesisLevel = 0;
    LinkedList<String> regexesForNewSearchers = new LinkedList<String>();
    StringBuilder currentRegexBuilder = new StringBuilder();
    while (++next < regex.length())
    {
      char c = regex.charAt(next);
      switch (c)
      {
        case '(':
        {
          if (parenthesisLevel > 0)
          {
            currentRegexBuilder.append(c);
          }
          parenthesisLevel++;
          break;
        }
        case ')':
        {
          if (parenthesisLevel == 1)
          {
            // We are done
            regexesForNewSearchers.add(currentRegexBuilder.toString());

            createSubSearchers(regexesForNewSearchers);
            currentRegexBuilder = new StringBuilder();

            // Reset regex
            regexesForNewSearchers = new LinkedList<String>();
          }
          else
          {
            currentRegexBuilder.append(c);
          }
          parenthesisLevel--;
          break;
        }
        case '|':
        {
          if (parenthesisLevel == 1)
          {
            regexesForNewSearchers.add(currentRegexBuilder.toString());

            currentRegexBuilder = new StringBuilder();
          }
          else
          {
            currentRegexBuilder.append(c);
          }
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
          if (parenthesisLevel > 0)
          {
            currentRegexBuilder.append(c);
          }
          else
          {
            // We have direction, move along
            move(Direction.getDirection(c));
          }
        }
      }
    }
  }

  private void createSubSearchers(Collection<String> regexes)
  {
    for (String regex : regexes)
    {
      GraphSearcher s = new GraphSearcher(map, current);
      s.search(regex);
      subSearchers.add(s);
    }
  }

  public void move(Direction d)
  {
    if (subSearchers.isEmpty())
    {
      //System.out.print("Move " + d + " from " + current + " to ");
      current = map.move(current, d);
      //System.out.println(current);
    }
    else
    {
      for (GraphSearcher s : subSearchers)
      {
        s.move(d);
      }
    }
  }
}
