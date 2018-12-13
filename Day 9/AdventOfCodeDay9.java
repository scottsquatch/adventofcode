import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class AdventOfCodeDay9
{
    private static final Pattern GAME_CONFIG_PATTERN = Pattern.compile("([0-9]+) players; last marble is worth ([0-9]+) points");

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("usage: AdventOfCodeDay9 <input.txt>");
            System.out.println("<input.txt> is a file which contains game configurations");
        }
        else
        {
            String inputFilePath = args[0];
            try
            {
                BufferedReader input = new BufferedReader(new FileReader(inputFilePath));
                ArrayList<String> lines = new ArrayList<String>();
                while (input.ready())
                {
                    lines.add(input.readLine());
                }

                solveProblem1(lines);
                solveProblem2(lines);

                input.close();
            }
            catch (FileNotFoundException e)
            {
                System.err.println("File " + inputFilePath + " cannot be found, please verify path is correct.");
            }
            catch (IOException e)
            {
                System.err.println("IOException occurred: " + e.toString());
                e.printStackTrace();
            }
        }
    }

    private static void solveProblem1(ArrayList<String> configs)
    {
        for (String config : configs)
        {
            Matcher configMatcher = GAME_CONFIG_PATTERN.matcher(config);

            if (configMatcher.matches())
            {
                MarbleGame game = new MarbleGame(Integer.parseInt(configMatcher.group(1)), Integer.parseInt(configMatcher.group(2)));

                System.out.println(config + "; high score is " + game.play());
            }
        }
    }

    private static void solveProblem2(ArrayList<String> configs)
    {
        // TODO
    }
}