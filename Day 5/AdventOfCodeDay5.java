import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.io.FileReader;
import java.util.Collection;

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

        System.out.println("Size: " + reducedPolymer.length());
    }

    private static void solveProblem2(String polymer)
    {
        int minimumPolymerLength = Integer.MAX_VALUE;
        Collection<Character> unitTypes = getUnitTypes(polymer);
        for (Character unitType : unitTypes)
        {
            System.out.println("Removing " + unitType + " from polymer.");
            String reducedPolymer = new PolymerReducer(unitType).reducePolymer(polymer);

            System.out.println("Reduced polymer size " + reducedPolymer.length());
            if (reducedPolymer.length() < minimumPolymerLength)
            {
                minimumPolymerLength = reducedPolymer.length();
            }
        }

        System.out.println("The minimum possible polymer size is " + minimumPolymerLength);
    }

    private static Collection<Character> getUnitTypes(String polymerText)
    {
        HashSet<Character> unitTypes = new HashSet<Character>();

        for (int i = 0; i < polymerText.length(); i++)
        {
            Character c = polymerText.charAt(i);
            if (!unitTypes.contains(c) &&
                !unitTypes.contains(Character.toUpperCase(c)))
            {
                unitTypes.add(c);
            }
        }

        return unitTypes;
    }
}