public enum CellType
{
	WALL('#'),
	EMPTY('.'),
	GOBLIN('G'),
	ELF('E');

	private char symbol;

	public char getSymbol()
	{
		return this.symbol;
	}

	CellType (char symbol)
	{
		this.symbol = symbol;
	}

	public static CellType getCellTypeFromSymbol(char symbol)
	{
		for (CellType type : CellType.values())
		{
			if (type.getSymbol() == symbol)
			{
				return type;
			}
		}

		return null;
	}
}
