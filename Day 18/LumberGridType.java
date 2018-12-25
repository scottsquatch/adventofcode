public enum LumberGridType
{
    OPEN('.'),
    LUMBERYARD('#'),
    TREES('|');

    private char symbol;

    LumberGridType(char symbol)
    {
        this.symbol = symbol;
    }

    public char getSymbol()
    {
        return symbol;
    }

    public static LumberGridType getLumberType(char symbol)
    {
        for (LumberGridType lumberGridType : LumberGridType.values())
        {
            if (lumberGridType.getSymbol() == symbol)
            {
                return lumberGridType;
            }
        }

        throw new IllegalArgumentException(symbol + " is not a valid Lumber Grid Type");
    }
}