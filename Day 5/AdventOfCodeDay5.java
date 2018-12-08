import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

public class AdventOfCodeDay5
{
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: AdventOfcodeDay5 <inputFile>");
            System.out.println("Where <inputFile> is a path to the file of the input for the problem");
        }
        else
        {
            String inputFilePath = args[0];

            try
            {
                String polymer = readFile(inputFilePath);

                solveProblem1(polymer);
                solveProblem2(polymer);
            }
            catch (FileNotFoundException e)
            {
                System.err.println("Cannot find file " + inputFilePath + " please make sure the path is correct");
            }
            catch (IOException e)
            {
                System.err.println("IOException occurred");
                e.printStackTrace();
            }
        }
    }

    private static String readFile(String inputFilePath)
        throws IOException, FileNotFoundException
    {
        StringBuilder builder = new StringBuilder();

        BufferedReader input = new BufferedReader(
            new FileReader(inputFilePath));

        while (input.ready())
        {
            builder.append(input.readLine());
        }

        input.close();

        return builder.toString();
    }

    // Solutions for the different problems
    private static void solveProblem1(String polymer)
    {
        String reducedPolymer = new PolymerReducer().reducePolymer(polymer);

        System.out.println("Reduced polymer: " + reducedPolymer);
        System.out.println("Size: " + reducedPolymer.length());
    }

    private static void solveProblem2(String polymer)
    {
        // TODO
    }
}