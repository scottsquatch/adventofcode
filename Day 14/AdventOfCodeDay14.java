import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

public class AdventOfCodeDay14
{
  public static void main(String[] args)
  {
    boolean printLogs = false;
    if (args.length > 0)
    {
        printLogs = Boolean.parseBoolean(args[0]);
    }
    Scanner input = new Scanner(System.in);

    System.out.print("Please enter the number of recipes after which the eleves skill will improve: ");
    int targetNumberOfRecipes = input.nextInt();

    solveProblem1(targetNumberOfRecipes, printLogs);

    System.out.print("Please enter the ending sequence for the recipes: ");
    String targetEndSequence = input.next();
    solveProblem2(targetEndSequence, printLogs);
  }

  private static void solveProblem1(int targetNumberOfRecipes, boolean printLogs)
  {
    // Elves -> Indices of Array List
    // Recipe Board -> Array List where value is the score
    int stopCondition = targetNumberOfRecipes + 10;
    ArrayList<Integer> recipeBoard = new ArrayList<Integer>();

    // Initialize two base recipes
    recipeBoard.add(3);
    recipeBoard.add(7);

    // Initialize elves
    int elf1Recipe = 0;
    int elf2Recipe = 1;

    do
    {
      // Print out status
      if (printLogs)
      {
        for (int i = 0; i < recipeBoard.size(); i++)
        {
          int recipeScore = recipeBoard.get(i);
          if (i == elf1Recipe)
          {
            System.out.print("(" + recipeScore + ")");
          }
          else if (i == elf2Recipe)
          {
            System.out.print("[" + recipeScore + "]");
          }
          else
          {
            System.out.print(" " + recipeScore + " ");
          }
        }
        System.out.println();
      }

      // make the recipe
      int newRecipes = recipeBoard.get(elf1Recipe) + recipeBoard.get(elf2Recipe);

      // Since numbers are only 0-9, the max value here is 18.
      int firstNewRecipe = newRecipes / 10;
      int secondNewRecipe = newRecipes % 10;
      // Only add first digit if it is greater than 0
      if (firstNewRecipe > 0)
      {
        recipeBoard.add(firstNewRecipe);
      }
      recipeBoard.add(secondNewRecipe);

      // Now the elves need to select their new recipes
      elf1Recipe = (elf1Recipe + recipeBoard.get(elf1Recipe) + 1) % recipeBoard.size();
      elf2Recipe = (elf2Recipe + recipeBoard.get(elf2Recipe) + 1) % recipeBoard.size();

      // Sanity check here, if we have the same recipes, move the second elf over 1
      if (elf1Recipe == elf2Recipe)
      {
        elf2Recipe = (elf2Recipe + 1) % recipeBoard.size();
      }
    } while (recipeBoard.size() <= stopCondition);

    // Now Print out the answer
    System.out.print("10 recipes after " + targetNumberOfRecipes + " are: ");
    for (int i = targetNumberOfRecipes; i < stopCondition; i++)
    {
      System.out.print(recipeBoard.get(i));
    }
    System.out.println();
  }

  private static void solveProblem2(String targetEndSequence, boolean printLogs)
  {
    // We now are given the ending sequence;
    // Elves -> Indices of Array List
    // Recipe Board -> Array List where value is the score
    ArrayList<Integer> recipeBoard = new ArrayList<Integer>();

    // Initialize two base recipes
    recipeBoard.add(3);
    recipeBoard.add(7);

    // Initialize elves
    int elf1Recipe = 0;
    int elf2Recipe = 1;

    boolean stopLoop = false;
    int numRecipes = -1;
    int recipesToLeft = -1;
    int recipesAddedThisRound = 0;
    do
    {
      numRecipes++;
      // Print out status
      if (printLogs)
      {
        for (int i = 0; i < recipeBoard.size(); i++)
        {
          int recipeScore = recipeBoard.get(i);
          if (i == elf1Recipe)
          {
            System.out.print("(" + recipeScore + ")");
          }
          else if (i == elf2Recipe)
          {
            System.out.print("[" + recipeScore + "]");
          }
          else
          {
            System.out.print(" " + recipeScore + " ");
          }
        }
        System.out.println();
      }

      // make the recipe
      int newRecipes = recipeBoard.get(elf1Recipe) + recipeBoard.get(elf2Recipe);

      // Since numbers are only 0-9, the max value here is 18.
      Integer firstNewRecipe = newRecipes / 10;
      Integer secondNewRecipe = newRecipes % 10;
      // Only add first digit if it is greater than 0
      recipesAddedThisRound = 0;
      if (firstNewRecipe > 0)
      {
        recipeBoard.add(firstNewRecipe);
        recipesAddedThisRound++;
      }
      recipeBoard.add(secondNewRecipe);
      recipesAddedThisRound++;

      // Now the elves need to select their new recipes
      elf1Recipe = (elf1Recipe + recipeBoard.get(elf1Recipe) + 1) % recipeBoard.size();
      elf2Recipe = (elf2Recipe + recipeBoard.get(elf2Recipe) + 1) % recipeBoard.size();

      // Sanity check here, if we have the same recipes, move the second elf over 1
      if (elf1Recipe == elf2Recipe)
      {
        elf2Recipe = (elf2Recipe + 1) % recipeBoard.size();
      }

      if (recipeBoard.size() >= 10)
      {
        StringBuilder builder = null;
        boolean matchFound = false;
        int idx = recipeBoard.size() - 10;
        // We only have to check for the recipes we have added
        for (int j = 0; j < recipesAddedThisRound; j++)
        {
          if (--idx < 0)
          {
            break;
          }

          builder = new StringBuilder();
          for (int i = 0; i < 10; i++)
          {
              builder.append(recipeBoard.get(idx + i).toString());
          }

          matchFound = builder.toString().startsWith(targetEndSequence);
          if (matchFound)
          {
            recipesToLeft = idx;
            break;
          }
        }

        // System.out.println(numRecipes + ": " + targetEndSequence + " -> " + builder.toString());
        if (matchFound)
        {
          stopLoop = true;
        }
      }
    } while (!stopLoop);

    // Now Print out the answer
    System.out.print("Number of recipes " + (recipesToLeft));
  }
}
