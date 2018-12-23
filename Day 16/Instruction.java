import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Instruction
{
	private static final Pattern INSTRUCTION_REGEX = Pattern.compile("([0-9]+)\\s+([0-9]+)\\s+([0-9]+)\\s+([0-9]+)");
	private int opCode;
	private int a;
	private int b;
	private int c;

	public Instruction(int opCode, int a, int b, int c)
	{
		this.opCode = opCode;
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public int getOpCode()
	{
		return opCode;
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

		builder.append(opCode);

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

		int op, a, b, c;

		op = Integer.parseInt(matcher.group(1));
		a = Integer.parseInt(matcher.group(2));
		b = Integer.parseInt(matcher.group(3));
		c = Integer.parseInt(matcher.group(4));

		return new Instruction(op, a, b, c);
	}

	public static void main(String[] args)
	{
		System.out.println("Testing Instruction class");
		Instruction i = Instruction.parseInstruction("2 41 51 3");

		assert(2 == i.getOpCode());
		assert(41 == i.getA());
		assert(51 == i.getB());
		assert(3 == i.getC());
		
		assert("2 41 51 3".equals(i.toString()));
		System.out.println("All test pass!");
	}
}
