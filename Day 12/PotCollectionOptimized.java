import java.util.List;
import java.util.ArrayList;

public class PotCollectionOptimized
{
	private List<Pot> pots;
	private String initialState;

	public PotCollectionOptimized(String initialState)
	{
		this.initialState = initialState;

		initialize();
	}

	// Initialize the pots list with the given list.
	private void initialize()
	{
		this.pots = new ArrayList<Pot>();

		for (int i = 0; i < this.initialState.length(); i++)
		{
			char currentChar = this.initialState.charAt(i);
			Pot currentPot = new Pot(i);

			switch (currentChar)
			{
				case '.':
				{
					currentPot.setIsFilled(false);
				}
				break;

				case '#':
				{
					currentPot.setIsFilled(true);
				}
				break;
			}

			this.pots.add(currentPot);
		}

		padList();
	}

	// Automatically handle the padding at the end and beginning of the list
	private void padList()
	{
		// Determine how many empty pots we need to add.
		// If this value is negative then we will need to remove them
		int leftPadding = 5;
		for (int i = 0; i < this.pots.size(); i++)
		{
			if (this.pots.get(i).getIsFilled())
			{
				break;
			}
			else
			{
				leftPadding--;
			}
		}

		if (leftPadding < 0)
		{
			for (int i = 0; i < leftPadding; i++)
			{
				this.pots.remove(0);
			}
		}
		else if (leftPadding > 0)
		{
			for (int i = leftPadding; i > 0; i--)
			{
				Pot p = new Pot(getLeftmostPotNumber() - 1);
				p.setIsFilled(false);

				this.pots.add(0, p);
			}
		}

		int rightPadding = 5;
		for (int i = this.pots.size() - 1; i >= 0; i--)
		{
			if (this.pots.get(i).getIsFilled())
			{
				break;
			}
			else
			{
				rightPadding--;
			}
		}

		if (rightPadding < 0)
		{
			for (int i = 0; i < leftPadding; i++)
			{
				this.pots.remove(this.pots.size() - 1);
			}
		}
		else if (rightPadding > 0)
		{
			for (int i = rightPadding; i > 0; i--)
			{
				Pot p = new Pot(getRightmostPotNumber() + 1);
				p.setIsFilled(false);

				this.pots.add(this.pots.size(), p);
			}
		}
	}

	public int getLeftmostPotNumber()
	{
		return this.pots.get(0).getNum();
	}

	public int getRightmostPotNumber()
	{
		return this.pots.get(this.pots.size() - 1).getNum();
	}

	public void reset()
	{
		initialize();
	}

	public String getPotText(int startPot, int numPots)
	{
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < numPots; i++)
		{
			int potsIndex = i + startPot - getLeftmostPotNumber();

			Pot p = this.pots.get(potsIndex);

			if (p.getIsFilled())
			{
				builder.append("#");
			}
			else
			{
				builder.append(".");
			}
		}

		return builder.toString();
	}

	public void setPotFilled(int potNum, boolean isFilled)
	{
		int leftmostPotNumber = getLeftmostPotNumber();

		int index = potNum - leftmostPotNumber;

		this.pots.get(index).setIsFilled(isFilled);

		padList();
	}

	public Pot getPot(int potNum)
	{
		int index = potNum - getLeftmostPotNumber();

		return pots.get(index);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.pots.size(); i++)
		{
			Pot p = this.pots.get(i);
			builder.append(p.getNum() + ": ");
			if (p.getIsFilled())
			{
				builder.append('#');
			}
			else
			{
				builder.append('.');
			}
		}

		return builder.toString();
	}
}
