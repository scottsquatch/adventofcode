public class PowerGrid
{
  private FuelCell[][] cells;

  public PowerGrid(int width, int height, int gridSerialNumber)
  {
    // Initialize the cells
    cells = new FuelCell[height][width];

    for (int i = 0; i < height; i++)
    {
      for (int j = 0; j < width; j++)
      {
        // Since we are starting from 1 and ending with width/height
        // we need to "map" index to value by adding 1
        int x = j + 1;
        int y = i + 1;
        cells[i][j] = new FuelCell(x, y, gridSerialNumber);
      }
    }
  }

  /**
   * Returns the top-left coordinate of the 3x3 section of the grid which has
   * the largest total power. Form is x,y.
   */
  public String getLargestTotalPower(int gridWidth, int gridHeight)
  {
    int maxPower = Integer.MIN_VALUE;
    int xOfMaxPower = -1;
    int yOfMaxPower = -1;

    // Since the problem definition states that we only care about 3x3 squares
    for (int i = 0; i <= cells.length - gridHeight; i++)
    {
      for (int j = 0; j <= cells[i].length - gridWidth; j++)
      {
        // Get the max value for the current 3x3 grid
        int totalPower = getTotalPowerForGrid(i, j, gridWidth, gridHeight);

        if (totalPower > maxPower)
        {
          xOfMaxPower = j + 1;
          yOfMaxPower = i + 1;
          maxPower = totalPower;
        }
      }
    }

    return xOfMaxPower + "," + yOfMaxPower;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < cells.length; i++)
    {
      for (int j = 0; j < cells[i].length; j++)
      {
        int powerLevel = cells[i][j].getPowerLevel();

        if (powerLevel > 0)
        {
          // Need extra padding if we don't have negative number
          builder.append(" ");
        }

        builder.append(powerLevel + " ");
      }

      builder.append("\n");
    }

    return builder.toString();
  }

  private int getTotalPowerForGrid(int firstIndex, int secondIndex,
                                    int gridWidth, int gridHeight)
  {
    int totalPower = 0;
    for (int i = firstIndex; i < firstIndex + gridHeight; i++)
    {
      for (int j = secondIndex; j < secondIndex + gridWidth; j++)
      {
        totalPower += cells[i][j].getPowerLevel();
      }
    }

    return totalPower;
  }
}
