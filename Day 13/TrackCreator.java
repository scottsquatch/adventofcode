import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class TrackCreator
{
  public TrackCreator()
  {

  }

  public Track createTrack(String trackText)
    throws IOException
  {
    Track newTrack = new Track();

    // Create two dimensional array
    ArrayList<ArrayList<Character>> trackRepresentation = new ArrayList<ArrayList<Character>>();
    BufferedReader input = new BufferedReader(new StringReader(trackText));
    while (input.ready())
    {
      String line = input.readLine();
      if (line != null)
      {
        ArrayList<Character> row = new ArrayList<Character>();
        for (int i = 0; i < line.length(); i++)
        {
            row.add(line.charAt(i));
        }

        trackRepresentation.add(row);
      }
      else
      {
        break;
      }
    }

    // Now that we have our 2D list, time to create the graph
    for (int y = 0; y < trackRepresentation.size(); y++)
    {
      ArrayList<Character> row = trackRepresentation.get(y);
      for (int x = 0; x < row.size(); x++)
      {
        Character currentChar = row.get(x);
        if (currentChar != ' ')
        {
          Point p = new Point();
          p.x = x;
          p.y = y;
          Vertex newVertex = newTrack.getVertex(p);
          // First check if we have not already added this Vertex
          if (newVertex == null)
          {
            newVertex = new Vertex();
            newTrack.addVertex(p, newVertex);
          }

          Collection<Direction> directionsToAdd = getDirectionsToAdd(currentChar);

          for (Direction direction : directionsToAdd)
          {
            Point newP = new Point();
            newP.x = x;
            newP.y = y;

            // Modify point for direction
            switch (direction)
            {
              case DOWN:
              {
                newP.y = newP.y + 1;
                break;
              }

              case RIGHT:
              {
                newP.x = newP.x + 1;
                break;
              }
            }

            Vertex nextVertex = newTrack.getVertex(newP);
            if (nextVertex == null)
            {
              nextVertex = new Vertex();
              newTrack.addVertex(newP, nextVertex);
            }

            newTrack.addEdge(newVertex, nextVertex, direction);
          }
        }
      }
    }

    return newTrack;
  }

  private Collection<Direction> getDirectionsToAdd(char section)
  {
    // Note that since we are processing TOP-DOWN, LEFT TO RIGHT
    // we only need to add down and left
    ArrayList<Direction> directionsToAdd = new ArrayList<Direction>();
    switch (section)
    {
      case '|':
      {
        directionsToAdd.add(Direction.DOWN);
        break;
      }
      case '-':
      {
        directionsToAdd.add(Direction.RIGHT);
        break;
      }
      case '\\':
      {
        directionsToAdd.add(Direction.DOWN);
        break;
      }
      case '/':
      {
        directionsToAdd.add(Direction.DOWN);
        directionsToAdd.add(Direction.RIGHT);
        break;
      }
      case '+':
      {
        directionsToAdd.add(Direction.DOWN);
        directionsToAdd.add(Direction.RIGHT);
        break;
      }

      // TODO: Add support for Carts
    }

    return directionsToAdd;
  }
  public static void main(String[] args)
  {
    System.out.println("Start testing TrackCreator class");
    String track = "  /-\\\n" +
                   "/-+\\|\n" +
                   "| |+/\n" +
                   "\\--/ \n";

    try
    {
      Track testTrack = new TrackCreator().createTrack(track);

      Point p = new Point();

      // y == 0
      p.x = 0;
      p.y = 0;
      assert(null == testTrack.getVertex(p));

      p.x = 1;
      assert(null == testTrack.getVertex(p));

      p.x = 2;
      Vertex v = testTrack.getVertex(p);
      assert(null == v.getNeighbor(Direction.UP));
      assert(null == v.getNeighbor(Direction.LEFT));
      assert(null != v.getNeighbor(Direction.RIGHT));
      assert(null != v.getNeighbor(Direction.DOWN));

      p.x = 3;
      v = testTrack.getVertex(p);
      assert(null == v.getNeighbor(Direction.UP));
      assert(null != v.getNeighbor(Direction.LEFT));
      assert(null != v.getNeighbor(Direction.RIGHT));
      assert(null == v.getNeighbor(Direction.DOWN));

      p.x = 4;
      v = testTrack.getVertex(p);
      assert(null == v.getNeighbor(Direction.UP));
      assert(null != v.getNeighbor(Direction.LEFT));
      assert(null == v.getNeighbor(Direction.RIGHT));
      assert(null != v.getNeighbor(Direction.DOWN));

      // y == 1
      p.y = 1;
      p.x = 0;
      v = testTrack.getVertex(p);
      assert(null == v.getNeighbor(Direction.UP));
      assert(null == v.getNeighbor(Direction.LEFT));
      assert(null != v.getNeighbor(Direction.RIGHT));
      assert(null != v.getNeighbor(Direction.DOWN));

      p.x = 1;
      v = testTrack.getVertex(p);
      assert(null == v.getNeighbor(Direction.UP));
      assert(null != v.getNeighbor(Direction.LEFT));
      assert(null != v.getNeighbor(Direction.RIGHT));
      assert(null == v.getNeighbor(Direction.DOWN));

      p.x = 2;
      v = testTrack.getVertex(p);
      assert(null != v.getNeighbor(Direction.UP));
      assert(null != v.getNeighbor(Direction.LEFT));
      assert(null != v.getNeighbor(Direction.RIGHT));
      assert(null != v.getNeighbor(Direction.DOWN));

      p.x = 3;
      v = testTrack.getVertex(p);
      assert(null == v.getNeighbor(Direction.UP));
      assert(null != v.getNeighbor(Direction.LEFT));
      assert(null == v.getNeighbor(Direction.RIGHT));
      assert(null != v.getNeighbor(Direction.DOWN));

      p.x = 4;
      v = testTrack.getVertex(p);
      assert(null != v.getNeighbor(Direction.UP));
      assert(null == v.getNeighbor(Direction.LEFT));
      assert(null == v.getNeighbor(Direction.RIGHT));
      assert(null != v.getNeighbor(Direction.DOWN));

      // y == 2
      p.y = 2;
      p.x = 0;
      v = testTrack.getVertex(p);
      assert(null != v.getNeighbor(Direction.UP));
      assert(null == v.getNeighbor(Direction.LEFT));
      assert(null == v.getNeighbor(Direction.RIGHT));
      assert(null != v.getNeighbor(Direction.DOWN));

      p.x = 1;
      v = testTrack.getVertex(p);
      assert(null == v);

      p.x = 2;
      v = testTrack.getVertex(p);
      assert(null != v.getNeighbor(Direction.UP));
      assert(null == v.getNeighbor(Direction.LEFT));
      assert(null == v.getNeighbor(Direction.RIGHT));
      assert(null != v.getNeighbor(Direction.DOWN));

      p.x = 3;
      v = testTrack.getVertex(p);
      assert(null != v.getNeighbor(Direction.UP));
      assert(null == v.getNeighbor(Direction.LEFT));
      assert(null != v.getNeighbor(Direction.RIGHT));
      assert(null != v.getNeighbor(Direction.DOWN));

      p.x = 4;
      v = testTrack.getVertex(p);
      assert(null != v.getNeighbor(Direction.UP));
      assert(null != v.getNeighbor(Direction.LEFT));
      assert(null == v.getNeighbor(Direction.RIGHT));
      assert(null == v.getNeighbor(Direction.DOWN));

      // y == 3
      p.y = 3;
      p.x = 0;
      v = testTrack.getVertex(p);
      assert(null != v.getNeighbor(Direction.UP));
      assert(null == v.getNeighbor(Direction.LEFT));
      assert(null != v.getNeighbor(Direction.RIGHT));
      assert(null == v.getNeighbor(Direction.DOWN));

      p.x = 1;
      v = testTrack.getVertex(p);
      assert(null == v.getNeighbor(Direction.UP));
      assert(null != v.getNeighbor(Direction.LEFT));
      assert(null != v.getNeighbor(Direction.RIGHT));
      assert(null == v.getNeighbor(Direction.DOWN));

      p.x = 2;
      v = testTrack.getVertex(p);
      assert(null == v.getNeighbor(Direction.UP));
      assert(null != v.getNeighbor(Direction.LEFT));
      assert(null != v.getNeighbor(Direction.RIGHT));
      assert(null == v.getNeighbor(Direction.DOWN));

      p.x = 3;
      v = testTrack.getVertex(p);
      assert(null != v.getNeighbor(Direction.UP));
      assert(null != v.getNeighbor(Direction.LEFT));
      assert(null == v.getNeighbor(Direction.RIGHT));
      assert(null == v.getNeighbor(Direction.DOWN));

      p.x = 4;
      v = testTrack.getVertex(p);
      assert(null == v);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    System.out.println("All tests passed!");
  }
}
