import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.HashSet;

// Use 2D array of characters for track
public class SimpleTrack
{
  // Underlying Track does not include carts, that way we can know what character to use after cart moves
  private ArrayList<ArrayList<Character>> underlyingTrack;
  private ArrayList<ArrayList<Character>> currentTrackState;
  private TreeMap<Point, Cart> cartMap;
  private Point firstCollisionPoint;

  public SimpleTrack(BufferedReader input)
    throws IOException
  {
    // Create two dimensional array
    underlyingTrack = new ArrayList<ArrayList<Character>>();
    currentTrackState = new ArrayList<ArrayList<Character>>();
    cartMap = new TreeMap<Point, Cart>();
    firstCollisionPoint = null;
    while (input.ready())
    {
      String line = input.readLine();
      if (line != null)
      {
        ArrayList<Character> stateRow = new ArrayList<Character>();
        ArrayList<Character> underlyingTrackRow = new ArrayList<Character>();
        for (int i = 0; i < line.length(); i++)
        {
            char currentChar = line.charAt(i);
            stateRow.add(currentChar);

            // for track we need to replace carts. Looking at the input
            // They are always given in the - and | case
            boolean isCart = false;
            if (currentChar == '^' || currentChar == 'v')
            {
              underlyingTrackRow.add('|');
              isCart = true;
            }
            else if (currentChar == '<' || currentChar == '>')
            {
              underlyingTrackRow.add('-');
              isCart = true;
            }
            else
            {
              underlyingTrackRow.add(currentChar);
            }

            if (isCart)
            {
              Point p = new Point();
              p.x = underlyingTrackRow.size() - 1;
              p.y = currentTrackState.size();

              cartMap.put(p, new Cart(currentChar));
            }
        }

        currentTrackState.add(stateRow);
        underlyingTrack.add(underlyingTrackRow);
      }
      else
      {
        break;
      }
    }
  }

  public void printUnderlyingTrack()
  {
    for (int y = 0; y < underlyingTrack.size(); y++)
    {
      ArrayList<Character> row = underlyingTrack.get(y);
      for (int x = 0; x < row.size(); x++)
      {
        System.out.print(row.get(x));
      }

      System.out.println();
    }
  }

  public void printCarts()
  {
    for (Point p : cartMap.keySet())
    {
      System.out.println(p + ": " + cartMap.get(p));
    }
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();

    for (int y = 0; y < currentTrackState.size(); y++)
    {
      ArrayList<Character> row = currentTrackState.get(y);
      for (int x = 0; x < row.size(); x++)
      {
        builder.append(row.get(x));
      }

      builder.append("\n");
    }

    return builder.toString();
  }

  public void tick()
  {
    TreeMap<Point, Cart> nextCartMap = new TreeMap<Point, Cart>();
    // System.out.println();
    // System.out.println();
    HashSet<Point> crashPoints = new HashSet<Point>();
    while (!cartMap.isEmpty())
    {
      Map.Entry<Point, Cart> cartMapEntry = cartMap.pollFirstEntry();
      // System.out.println("Working on cart " + cartMapEntry.getValue() +
      //   " at position " + cartMapEntry.getKey());
      Point currentPoint = cartMapEntry.getKey();
      int x = currentPoint.x;
      int y = currentPoint.y;
      Point newPoint = new Point();
      newPoint.x = x;
      newPoint.y = y;
      ArrayList<Character> row = currentTrackState.get(y);
      Cart currentCart = cartMapEntry.getValue();
      char currentChar = currentCart.toString().charAt(0);

      Point diff = new Point();
      switch (currentChar)
      {
        case '>':
        {
          diff.x = 1;
          break;
        }
        case '<':
        {
          diff.x = -1;
          break;
        }
        case '^':
        {
          diff.y = -1;
          break;
        }
        case 'v':
        {
          diff.y = 1;
          break;
        }
      }

      newPoint.x = x + diff.x;
      newPoint.y = y + diff.y;

      switch(currentChar)
      {
        case '<':
        case '>':
        {
          char nextChar = currentTrackState.get(newPoint.y).get(newPoint.x);
          if (nextChar == '-')
          {
          }
          else if (currentChar == '>' && nextChar == '\\')
          {
            // Update direction
              printSquareAroundPoint(x, y);
            currentCart.setDirection(Direction.DOWN);
          }
          else if (currentChar == '>' && nextChar == '/')
          {
            // Update direction
              printSquareAroundPoint(x, y);
            currentCart.setDirection(Direction.UP);
          }
          else if (currentChar == '<' && nextChar == '/')
          {
            printSquareAroundPoint(x, y);
            currentCart.setDirection(Direction.DOWN);
          }
          else if (currentChar == '<' && nextChar == '\\')
          {
            printSquareAroundPoint(x, y);
            currentCart.setDirection(Direction.UP);
          }
          else if (nextChar == '+')
          {
            currentCart.setDirection(currentCart.getDirectionToGoAtIntersection());
          }
          else if (nextChar == '>' || nextChar == '<' || nextChar == '^' ||
            nextChar == 'v' || nextChar == 'X')
          {
            // collision
            if (firstCollisionPoint == null)
            {
              firstCollisionPoint = newPoint;
            }

            crashPoints.add(newPoint);
            // Remove collising cart
            cartMap.remove(newPoint);
            nextCartMap.remove(newPoint);
            currentTrackState.get(y).set(x, underlyingTrack.get(y).get(x));
            currentTrackState.get(newPoint.y).set(newPoint.x, 'X');
            // Continue so that we don't add to next cartMap
            continue;
          }
          else
          {
            newPoint.x = x;
            newPoint.y = y;
          }

          break;
        }

        case '^':
        case 'v':
        {
          char nextChar = currentTrackState.get(newPoint.y).get(newPoint.x);
          if (nextChar == '|')
          {

          }
          else if (currentChar == '^' && nextChar == '/')
          {
            printSquareAroundPoint(x, y);
            currentCart.setDirection(Direction.RIGHT);
          }
          else if (currentChar == '^' && nextChar == '\\')
          {
            printSquareAroundPoint(x, y);
            currentCart.setDirection(Direction.LEFT);
          }
          else if (currentChar == 'v' && nextChar == '/')
          {
            printSquareAroundPoint(x, y);
            currentCart.setDirection(Direction.LEFT);
          }
          else if (currentChar == 'v' && nextChar == '\\')
          {
            printSquareAroundPoint(x, y);
            currentCart.setDirection(Direction.RIGHT);
          }
          else if (nextChar == '+')
          {
            currentCart.setDirection(currentCart.getDirectionToGoAtIntersection());
          }
          else if (nextChar == '>' || nextChar == '<' || nextChar == '^' ||
            nextChar == 'v' || nextChar == 'X')
          {
            // collision
            if (firstCollisionPoint == null)
            {
              firstCollisionPoint = newPoint;
            }

            crashPoints.add(newPoint);
            // Remove collising cart
            cartMap.remove(newPoint);
            nextCartMap.remove(newPoint);
            currentTrackState.get(y).set(x, underlyingTrack.get(y).get(x));
            currentTrackState.get(newPoint.y).set(newPoint.x, 'X');
            // Continue so that we don't add to next cartMap
            continue;
          }
          else
          {
            newPoint.x = x;
            newPoint.y = y;
          }

          break;
        }
      }

      currentTrackState.get(y).set(x, underlyingTrack.get(y).get(x));
      currentTrackState.get(newPoint.y).set(newPoint.x, currentCart.toString().charAt(0));

      nextCartMap.put(newPoint, currentCart);
    }

    cartMap = nextCartMap;

    // Now we need to clean up the crashes
    for (Point p : crashPoints)
    {
      // remove from map
      cartMap.remove(p);

      // Remove Xs
      currentTrackState.get(p.y).set(p.x, underlyingTrack.get(p.y).get(p.x));
    }
  }

  public void tick_slow()
  {
    HashSet<Point> cartsProcessed = new HashSet<Point>();
    for (int y = 0; y < currentTrackState.size(); y++)
    {
      ArrayList<Character> row = currentTrackState.get(y);
      for (int x = 0; x < row.size(); x++)
      {
        Character currentChar = row.get(x);

        Point currentPoint = new Point();
        currentPoint.x = x;
        currentPoint.y = y;
        if (!cartsProcessed.contains(currentPoint) &&
          (currentChar == '^' || currentChar == '>' || currentChar == '<' | currentChar == 'v'))
        {
          Point newPoint = new Point();
          newPoint.x = x;
          newPoint.y = y;

          Cart currentCart = cartMap.remove(currentPoint);

          Point diff = new Point();
          switch (currentChar)
          {
            case '>':
            {
              diff.x = 1;
              break;
            }
            case '<':
            {
              diff.x = -1;
              break;
            }
            case '^':
            {
              diff.y = -1;
              break;
            }
            case 'v':
            {
              diff.y = 1;
              break;
            }
          }

          newPoint.x = x + diff.x;
          newPoint.y = y + diff.y;

          switch(currentChar)
          {
            case '<':
            case '>':
            {
              char nextChar = currentTrackState.get(newPoint.y).get(newPoint.x);
              if (nextChar == '-')
              {
              }
              else if (currentChar == '>' && nextChar == '\\')
              {
                currentCart.setDirection(Direction.DOWN);
              }
              else if (currentChar == '>' && nextChar == '/')
              {
                currentCart.setDirection(Direction.UP);
              }
              else if (currentChar == '<' && nextChar == '/')
              {
                currentCart.setDirection(Direction.DOWN);
              }
              else if (currentChar == '<' && nextChar == '\\')
              {
                currentCart.setDirection(Direction.UP);
              }
              else if (nextChar == '+')
              {
                currentCart.setDirection(currentCart.getDirectionToGoAtIntersection());
              }
              else if (nextChar == '>' || nextChar == '<' || nextChar == '^' ||
                nextChar == 'v' || nextChar == 'X')
              {
                // collision
                if (firstCollisionPoint == null)
                {
                  firstCollisionPoint = newPoint;
                }

                // Remove collising cart
                cartMap.remove(newPoint);
                currentTrackState.get(y).set(x, underlyingTrack.get(y).get(x));
                currentTrackState.get(newPoint.y).set(newPoint.x, 'X');
                // Continue so that we don't add to next cartMap
                continue;
              }
              else
              {
                newPoint.x = x;
                newPoint.y = y;
              }

              break;
            }

            case '^':
            case 'v':
            {
              char nextChar = currentTrackState.get(newPoint.y).get(newPoint.x);
              if (nextChar == '|')
              {

              }
              else if (currentChar == '^' && nextChar == '/')
              {
                currentCart.setDirection(Direction.RIGHT);
              }
              else if (currentChar == '^' && nextChar == '\\')
              {
                currentCart.setDirection(Direction.LEFT);
              }
              else if (currentChar == 'v' && nextChar == '/')
              {
                currentCart.setDirection(Direction.LEFT);
              }
              else if (currentChar == 'v' && nextChar == '\\')
              {
                currentCart.setDirection(Direction.RIGHT);
              }
              else if (nextChar == '+')
              {
                currentCart.setDirection(currentCart.getDirectionToGoAtIntersection());
              }
              else if (nextChar == '>' || nextChar == '<' || nextChar == '^' ||
                nextChar == 'v' || nextChar == 'X')
              {
                // collision
                if (firstCollisionPoint == null)
                {
                  firstCollisionPoint = newPoint;
                }

                // Remove collising cart
                cartMap.remove(newPoint);
                currentTrackState.get(y).set(x, underlyingTrack.get(y).get(x));
                currentTrackState.get(newPoint.y).set(newPoint.x, 'X');
                // Continue so that we don't add to next cartMap
                continue;
              }
              else
              {
                newPoint.x = x;
                newPoint.y = y;
              }

              break;
            }
          }

          currentTrackState.get(y).set(x, underlyingTrack.get(y).get(x));
          currentTrackState.get(newPoint.y).set(newPoint.x, currentCart.toString().charAt(0));

          cartMap.put(newPoint, currentCart);
          cartsProcessed.add(newPoint);
        }
      }
    }
  }

  public boolean hasCollision()
  {
    return firstCollisionPoint != null;
  }

  public Point getFirstCollisionPoint()
  {
    return firstCollisionPoint;
  }

  private void printSquareAroundPoint(int x, int y)
  {
    // System.out.println(currentTrackState.get(y-1).get(x-1).toString() + currentTrackState.get(y-1).get(x).toString() + currentTrackState.get(y-1).get(x+1).toString());
    // System.out.println(currentTrackState.get(y).get(x-1).toString() + currentTrackState.get(y).get(x).toString() + currentTrackState.get(y).get(x+1).toString());
    // System.out.println(currentTrackState.get(y+1).get(x-1).toString() + currentTrackState.get(y+1).get(x).toString() +currentTrackState.get(y+1).get(x+1).toString());
    // System.out.println();
    // System.out.println();
  }

  public int getNumberOfCartsLeft()
  {
    return cartMap.size();
  }

  public String getCartLocationsString()
  {
    StringBuilder builder = new StringBuilder();

    for (Point p : cartMap.keySet())
    {
      builder.append(p + " ");
    }

    return builder.toString();
  }
}
