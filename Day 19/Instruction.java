import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Instruction
{
	private static final Pattern INSTRUCTION_REGEX = Pattern.compile("([a-z]+)\\s+([0-9]+)\\s+([0-9]+)\\s+([0-9]+)");
	private OperationType op;
	private int a;
	private int b;
	private int c;

	public Instruction(OperationType op, int a, int b, int c)
	{
		this.op = op;
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public OperationType getop()
	{
		return op;
	}

	public int getA()
	{
		return a;
	}

	public int getB()
	{
		return b;
	}

	public int getC()
	{
		return c;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append(op.getName());

		builder.append(" " + a);
		builder.append(" " + b);
		builder.append(" " + c);

		return builder.toString();
	}

	public static Instruction parseInstruction(String s)
	{
		Matcher matcher = INSTRUCTION_REGEX.matcher(s);

		if (!matcher.matches())
		{
			return null;
		}

		OperationType op;
		int a, b, c;

		op = OperationType.getOperationType(matcher.group(1));
		a = Integer.parseInt(matcher.group(2));
		b = Integer.parseInt(matcher.group(3));
		c = Integer.parseInt(matcher.group(4));

		return new Instruction(op, a, b, c);
	}
}
