import java.util.LinkedList;

public class MarbleGame
{
    private int[] mPlayers;
    private int mFinalMarbleScore;

    public MarbleGame(int numPlayers, int finalMarbleScore)
    {
        mPlayers = new int[numPlayers];
        mFinalMarbleScore = finalMarbleScore;
    }

    public int play()
    {
        LinkedList<Integer> board = new LinkedList<Integer>();

        int currentMarble = 0;
        int currentMarbleValue = 0;
        board.add(0);

        int currentPlayer = -1;


        // Print board
        //System.out.println("[-]  (" + currentMarble + ")");

        boolean finalMarblePlaced = false;
        while (currentMarbleValue != mFinalMarbleScore)
        {
            currentMarbleValue++;
            currentPlayer = (currentPlayer + 1) % mPlayers.length;

            int nextCurrentMarble;
            if (currentMarbleValue % 23 == 0)
            {
                // We have a point play!
                int pointIndex = currentMarble - 7;
                if (pointIndex < 0)
                {
                    pointIndex += board.size();
                }

                int pointsToAdd = currentMarbleValue + board.get(pointIndex);

                board.remove(pointIndex);

                // Since we have removed the item at pointIndex, the item now at pointIndex was previously clockwise to poitnIndex
                nextCurrentMarble = pointIndex;

                // Determine if we have played the final marble
                //finalMarblePlaced = pointsToAdd >= mFinalMarbleScore;

                mPlayers[currentPlayer] += pointsToAdd;

                /*System.out.println("Player " + (currentPlayer + 1) +" places marble for " + pointsToAdd + " points!");
                for (int i = 0; i < mPlayers.length; i++)
                {
                    System.out.print(i + "->" + mPlayers[i] + " Points   ");
                }
                System.out.println();*/
            }
            else
            {
                // Place marble 
                nextCurrentMarble = currentMarble + 2;
                if (nextCurrentMarble > board.size())
                {
                    nextCurrentMarble -= board.size();
                }

                currentMarble = nextCurrentMarble;
                board.add(currentMarble, currentMarbleValue);
            }

            currentMarble = nextCurrentMarble;

            // Print board
            /*
            System.out.print("[" + (currentPlayer + 1) + "]");
            for (int i = 0; i < board.size(); i++)
            {
                int marble = board.get(i);
                System.out.print("  ");
                if (i == currentMarble)
                {
                    System.out.print("(");
                }
                System.out.print(marble);
                if (i == currentMarble)
                {
                    System.out.print(")");
                }
            }
            System.out.println(); */
        }

        int maxScore = Integer.MIN_VALUE;
        for (int score : mPlayers)
        {
            if (score > maxScore)
            {
                maxScore = score;
            }
        }

        return maxScore;
    }
}