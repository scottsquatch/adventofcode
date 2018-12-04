import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ChronolCalibration
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
            String filePath = args[0];


            BufferedReader inputReader = new BufferedReader(
                new FileReader(filePath)
            );
            try
            {

                int totalFrequency = 0;

                while (inputReader.ready()){
                    int frequencyModifier = Integer.parseInt(inputReader.readLine());

                    totalFrequency += frequencyModifier;
                }

                System.out.println("Total Frequency is " + totalFrequency);
            } catch (FileNotFoundException e)
            {
                System.err.println("Could not find file " + filePath);
            }
            catch (IOException e)
            {
                System.err.println(e.toString());
            }
            finally
            {
                inputReader.close();
            }
        }
    }
}