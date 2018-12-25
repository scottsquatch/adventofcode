public class BaseConstructionSimulatorThread extends Thread
{
    private int startx;
    private int starty;
    private int endx;
    private int endy;
    private CharMap2D originalMap;
    private CharMap2D newMap;

    public BaseConstructionSimulatorThread(int startx, int starty, int endx, int endy, CharMap2D originalMap,
        CharMap2D newMap)
    {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        this.originalMap = originalMap;
        this.newMap = newMap;
    }

    @Override 
    public void run()
    {
        for (int y = starty; y <= endy; y++)
        {
            if (y >= 0 && y < originalMap.getMaxY())
            {
                for (int x = startx; x <= endx; x++)
                {
                    if (x >= 0 && x < originalMap.getMaxX())
                    {
                        char c = originalMap.get(x, y);

                        char cPrime = SimulationRule.getSimulationRule(c).getNextCharacter(originalMap.getAdj(x, y, 3), c);

                        setValue(x, y, cPrime);
                    }
                }
            }
        }
    }

    private synchronized void setValue(int x, int y, char c)
    {
        newMap.set(x, y, c);
    }
}