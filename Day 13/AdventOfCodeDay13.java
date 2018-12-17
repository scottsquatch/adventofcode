import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class AdventOfCodeDay13
{
  public static void main(String args[])
  {
    if (args.length != 1)
    {
      System.out.println("Usage: AdventOfCodeDay13 <input file>");
    }
    else
    {
      String inputFilePath = args[0];

      try
      {
        BufferedReader input = new BufferedReader(new FileReader(inputFilePath));

        SimpleTrack track = new SimpleTrack(input);

        // String selection = "";
        // Scanner userInput = new Scanner(System.in);
        //
        System.out.println(track);
        track.printCarts();
        //
        // do
        // {
        //   track.tick();
        //
        //   System.out.println(track);
        //   track.printCarts();
        //
        //   System.out.print("Press q to quit or anything else to continue");
        //   selection = userInput.nextLine();
        // } while (!selection.equals("q"));

        // BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"));

        do
        {
          track.tick();
          // track.tick_slow();

          System.out.println(track);
          track.printCarts();
          // output.write(track + "\n\n");

        } while (!track.hasCollision());

        System.out.println("First collision point " + track.getFirstCollisionPoint());

        input.close();
        // output.close();
      }
      catch (FileNotFoundException e)
      {
        System.err.println("Cannot find file " + inputFilePath);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
}
