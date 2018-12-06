import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FabricOverlapCalculator
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("Usage: FabricOverlapCalulator <input>");
			System.out.println("<input> is file with fabric claims");
		}
		else
		{
			String inputFile = args[0];
			
			try
			{
				BufferedReader input = new BufferedReader(new FileReader(inputFile));


				Fabric santasFabric = new Fabric(2000, 20000);
				while (input.ready())
				{
					Claim claim = new Claim(input.readLine());
					santasFabric.makeClaim(claim);
				}

				System.out.println("The square inches of fabric which has overlapping claims is " + santasFabric.getOverlap());

			}
			catch (FileNotFoundException e)
			{
				System.err.println("Could not find file " + inputFile + " please make sure the path is correct");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
