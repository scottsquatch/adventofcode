import java.util.LinkedList;

public class MarbleGame
{
    private long[] mPlayers;
    private int mFinalMarbleScore;

    public MarbleGame(int numPlayers, int finalMarbleScore)
    {
        mPlayers = new long[numPlayers];
        mFinalMarbleScore = finalMarbleScore;
    }

    public long play()
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

        long maxScore = -1;
        for (long score : mPlayers)
        {
            if (score > maxScore)
            {
                maxScore = score;
            }
        }

        return maxScore;
    }

    public long play_optimized()
    {
        Node zero = new Node(0);
        zero.left = zero;
        zero.right = zero;

        int currentMarbleValue = 0;

        int currentPlayer = -1;

        // Print board
        //System.out.println("[-]  (" + currentMarble + ")");

        boolean finalMarblePlaced = false;
        Node currentMarble = zero;
        while (currentMarbleValue != mFinalMarbleScore)
        {
            currentMarbleValue++;
            currentPlayer = (currentPlayer + 1) % mPlayers.length;

            if (currentMarbleValue % 23 == 0)
            {
                // Go counter clockwise 7 times
                for (int i = 0; i < 7; i++)
                {
                    currentMarble = currentMarble.right;
                }

                int pointsToAdd = currentMarbleValue + currentMarble.value;

                Node nextMarble = currentMarble.left;

                currentMarble.right.left = currentMarble.left;
                currentMarble.left = currentMarble.right;

                // Now remove the value
                currentMarble.right = null;
                currentMarble.left = null;

                currentMarble = nextMarble;

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
                // Move one turn clockwise (left) and then insert value
                Node newNode = new Node(currentMarbleValue);

                currentMarble = currentMarble.left;

                newNode.left = currentMarble.left;
                newNode.right = currentMarble;

                currentMarble.left.right = newNode;
                currentMarble.left = newNode;

                currentMarble = newNode;
            }

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

        long maxScore = -1;
        for (long score : mPlayers)
        {
            if (score > maxScore)
            {
                maxScore = score;
            }
        }

        return maxScore;
    }
}

class Node
{
    public Node left;
    public Node right;
    public int value;

    public Node(int value)
    {
        left = null;
        right = null;
        this.value = value;
    }
}