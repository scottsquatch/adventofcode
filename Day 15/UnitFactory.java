public class UnitFactory
{
    private int initialAPElves;

    public UnitFactory()
    {
        initialAPElves = 3;
    }

    public void setInitialApForElves(int ap)
    {
        initialAPElves = ap;
    }

    public Unit createUnit(CellType type)
    {
        switch(type)
		{
			case GOBLIN:
			{
				return new Goblin();
			}
			case ELF:
			{
                Unit u = new Elf();
                u.ap = initialAPElves;
				return u;
			}
			default:
			{
				throw new IllegalArgumentException("Cell type " + type.getSymbol() + " is not valid");
			}
		}
    }
}