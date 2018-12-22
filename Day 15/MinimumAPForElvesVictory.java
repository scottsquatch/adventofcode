import java.util.Collection;

public class MinimumAPForElvesVictory
{
    private Collection<String> initialMapLines;
    private GameEngine finalGameRun;

    public MinimumAPForElvesVictory(Collection<String> initialMapLines)
    {
        this.initialMapLines = initialMapLines;
    }

    public int run(boolean print)
    {
        int elvesInitialAP = 2;
        GameMap map = new GameMap(initialMapLines);
        int initialEleves = map.getNum('E');

        do
        {
            elvesInitialAP++;

            GameEngine engine = new GameEngine(new GameMap(initialMapLines, elvesInitialAP));
            engine.playGame(print);

            finalGameRun = engine;
        } while (finalGameRun.getNumUnits('E') != initialEleves);

        return elvesInitialAP;
    }

    public GameEngine getFinalGameRun()
    {
        return this.finalGameRun;
    }
}