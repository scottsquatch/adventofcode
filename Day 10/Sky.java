import java.util.ArrayList;
import java.util.Collection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Sky
{
	private ArrayList<Light> lights;

	public Sky(Collection<Light> lights)
	{
		this.lights = new ArrayList<Light>();
	
		for (Light light : lights)
		{	
			this.lights.add(light);
		}
	}


	public void passTime(int seconds)
	{
		for (int i = 0; i < lights.size(); i++)
		{
			lights.get(i).passTime(seconds);
		}
	}

	public void print()
	{
		int xmax = Integer.MIN_VALUE;
		int xmin = Integer.MAX_VALUE;
		int ymax = Integer.MIN_VALUE;
		int ymin = Integer.MAX_VALUE;
		for (Light light : lights)
		{
			int x = light.getPosition().x;
			int y = light.getPosition().y;

			if (x > xmax)
			{
				xmax = x;
			}
			else if (x < xmin)
			{
				xmin = x;
			}

			if (y > ymax)
			{
				ymax = y;
			}
			else if (y < ymin)
			{
				ymin = y;
			}
		}

		// Print to screen
		for (int i = ymin; i <= ymax; i++)
		{
			for (int j = xmin; j <= xmax; j++)
			{
				boolean lightFound = false;
				for (Light light : lights)
				{
					if (light.getPosition().x == j &&
					    light.getPosition().y == i)
					{
						lightFound = true;
						break;
					}
				}
				
				char charToPrint;
				if (lightFound)
				{
					charToPrint = '#';
				}
				else
				{
					charToPrint = ' ';
				}

				System.out.print(charToPrint);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void writeToFile()
	{
		BufferedWriter writer = null;
		try
		{
			File file = new File("output.txt");
			if (!file.exists()) 
			{
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(file));

			int xmax = Integer.MIN_VALUE;
			int xmin = Integer.MAX_VALUE;
			int ymax = Integer.MIN_VALUE;
			int ymin = Integer.MAX_VALUE;
			for (Light light : lights)
			{
				int x = light.getPosition().x;
				int y = light.getPosition().y;
	
				if (x > xmax)
				{
					xmax = x;
				}
				else if (x < xmin)
				{
					xmin = x;
				}
	
				if (y > ymax)
				{
					ymax = y;
				}
				else if (y < ymin)
				{
					ymin = y;
				}
			}
	
			// Print to screen
			for (int i = ymin; i <= ymax; i++)
			{
				for (int j = xmin; j <= xmax; j++)
				{
					boolean lightFound = false;
					for (Light light : lights)
					{
						if (light.getPosition().x == j &&
							light.getPosition().y == i)
						{
							lightFound = true;
							break;
						}
					}
					
					char charToPrint;
					if (lightFound)
					{
						charToPrint = '#';
					}
					else
					{
						charToPrint = ' ';
					}
	
					writer.write(charToPrint);
				}
				writer.newLine();
			}
			writer.newLine();

			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void getLetters(boolean writeToFile)
	{
		// Algorithm:
		// Determine the area of the bounding box for the points
		// This should shrink as we are going towards the point where letters are visible and growing as we pass it
		// So just look for the point where the bounding rectangle is increasing
		long previousArea;
		long currentArea = Long.MAX_VALUE;
		do
		{
			previousArea = currentArea;
			// Find xmax, xmin, ymax, ymin
			passTime(1);
			long xmax = Long.MIN_VALUE;
			long xmin = Long.MAX_VALUE;
			long ymax = Long.MIN_VALUE;
			long ymin = Long.MAX_VALUE;

			for (Light light : lights)
			{
				long x = light.getPosition().x;
				long y = light.getPosition().y;

				if (x > xmax)
				{
					xmax = x;
				}
				else if (x < xmin)
				{
					xmin = x;
				}

				if (y > ymax)
				{
					ymax = y;
				}
				else if (y < ymin)
				{
					ymin = y;
				}
			}

			currentArea = (ymax - ymin + 1) * (xmax - xmin + 1);
			System.out.println(currentArea);
		} while (currentArea < previousArea);	

		passTime(-1);
		
		if (writeToFile)
		{
			writeToFile();
		}
		else
		{
			print();
		}
	}
}

