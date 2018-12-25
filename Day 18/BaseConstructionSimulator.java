public class BaseConstructionSimulator
{
    CharMap2D map;
    public BaseConstructionSimulator(String[] lines)
    {
        map = new CharMap2D(lines);
    }

    public void passMinute()
    {
        CharMap2D nextMap = new CharMap2D(map);
        // Create a clone of the map, as we don't want changes to affect the result unitl the next minute
        for (int y = 0; y < map.getMaxY(); y++)
        {
            for (int x = 0; x < map.getMaxX(); x++)
            {
                char c = map.get(x, y);

                char cPrime = SimulationRule.getSimulationRule(c).getNextCharacter(map.getAdj(x, y, 3), c);

                nextMap.set(x, y, cPrime);
            }
        }

        map = nextMap;
    }

    public int getNum(LumberGridType type)
    {
        return map.getNum(type.getSymbol());
    }

    @Override 
    public String toString()
    {
        return map.toString();
    }
}