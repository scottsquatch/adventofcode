import java.util.ArrayList;

public abstract class SimulationRule
{
    public abstract char getNextCharacter(ArrayList<Character> cells, char currentCell);

    public static SimulationRule getSimulationRule(char currentCell)
    {
        LumberGridType type = LumberGridType.getLumberType(currentCell);
        switch (type)
        {
            case OPEN:
            {
                return new OpenSimulationRule();
            }
            case TREES:
            {
                return new TreeSimulationRule();
            }
            case LUMBERYARD:
            {
                return new LumberYardSimulationRule();
            }
            default:
            {
                throw new IllegalArgumentException(type.getSymbol() + " does not have a simulation rule");
            }
        }
    }
}

class OpenSimulationRule extends SimulationRule
{
    @Override
    public char getNextCharacter(ArrayList<Character> cells, char currentCell)
    {
        int numTrees = 0;
        for (Character cell : cells)
        {
            if (cell == LumberGridType.TREES.getSymbol())
            {
                numTrees++;
            }
        }

        return (numTrees >= 3) ? LumberGridType.TREES.getSymbol() : currentCell;
    }
}

class TreeSimulationRule extends SimulationRule
{
    @Override
    public char getNextCharacter(ArrayList<Character> cells, char currentCell)
    {
        int numLumberyards = 0;
        for (Character cell : cells)
        {
            if (cell == LumberGridType.LUMBERYARD.getSymbol())
            {
                numLumberyards++;
            }
        }

        return (numLumberyards >= 3) ? LumberGridType.LUMBERYARD.getSymbol() : currentCell;
    }
}

class LumberYardSimulationRule extends SimulationRule
{
    @Override
    public char getNextCharacter(ArrayList<Character> cells, char currentCell)
    {
        int numLumberyards = 0;
        int numTrees = 0;
        for (Character cell : cells)
        {
            if (cell == LumberGridType.LUMBERYARD.getSymbol())
            {
                numLumberyards++;
            }
            else if (cell == LumberGridType.TREES.getSymbol())
            {
                numTrees++;
            }
        }

        return (numLumberyards > 0 && numTrees > 0) ? currentCell : LumberGridType.OPEN.getSymbol();
    }
}