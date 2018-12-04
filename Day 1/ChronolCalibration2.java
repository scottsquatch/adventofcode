import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

public class ChronolCalibration2
{

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: ChronolCalibration <inputfile>");
            System.out.println("Applies the fequency modifications from the given file");
        }
        else
        {
            ArrayList<Integer> frequencies = GetFrequenciesFromFile(args[0]);
            int totalFrequency = 0;
            HashSet<Integer> resultingFrequencies = new HashSet<Integer>();
            int index = 0;

            while (!resultingFrequencies.contains(totalFrequency))
            {
                resultingFrequencies.add(totalFrequency);
                totalFrequency += frequencies.get(index++);

                if (index == frequencies.size())
                {
                    index = 0;
                }
            }

            System.out.println("The first frequency to be duplicated is " + totalFrequency);
        }
    }

    private static ArrayList<Integer> GetFrequenciesFromFile(String filePath)
    {
        ArrayList<Integer> frequencies = new ArrayList<Integer>();

        try
        {
            BufferedReader inputReader = new BufferedReader(
                new FileReader(filePath)
            );

            while (inputReader.ready()){
                int frequencyModifier = Integer.parseInt(inputReader.readLine());

                frequencies.add(frequencyModifier);
            }

            inputReader.close();
        } catch (FileNotFoundException e)
        {
            System.err.println("Could not find file " + filePath);
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
        }

        return frequencies;
    }
}