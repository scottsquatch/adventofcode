public enum OperationType
{
	ADDR("addr"),
	ADDI("addi"),

	MULR("mulr"),
	MULI("muli"),

	BANR("banr"),
	BANI("bani"),

	BORR("borr"),
	BORI("bori"),

	SETR("setr"),
	SETI("seti"),

	GTIR("gtir"),
	GTRI("gtri"),
	GTRR("gtrr"),

	EQIR("eqir"),
	EQRI("eqri"),
	EQRR("eqrr");

	private String name;

	OperationType(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public static RegisterState applyOperation(OperationType type, RegisterState state, Instruction instruction)
	{
		RegisterState newState = (RegisterState)state.clone();

		switch (type)
		{
			case ADDR:
			{
				newState.setRegisterValue(instruction.getC(), state.getRegisterValue(instruction.getA()) + state.getRegisterValue(instruction.getB()));
				break;
			}
			case ADDI:
			{
				
				newState.setRegisterValue(instruction.getC(), state.getRegisterValue(instruction.getA()) + instruction.getB());
				break;
			}

			case MULR:
			{
				
				newState.setRegisterValue(instruction.getC(), state.getRegisterValue(instruction.getA()) * state.getRegisterValue(instruction.getB()));
				break;
			}
			case MULI:
			{
				
				newState.setRegisterValue(instruction.getC(), state.getRegisterValue(instruction.getA()) * instruction.getB());
				break;
			}

			case BANR:
			{
				newState.setRegisterValue(instruction.getC(), state.getRegisterValue(instruction.getA()) & state.getRegisterValue(instruction.getB()));
				break;
			}
			case BANI:
			{
				
				newState.setRegisterValue(instruction.getC(), state.getRegisterValue(instruction.getA()) & instruction.getB());
				break;
			}

			case BORR:
			{
				
				newState.setRegisterValue(instruction.getC(), state.getRegisterValue(instruction.getA()) | state.getRegisterValue(instruction.getB()));
				break;
			}
			case BORI:
			{
				
				newState.setRegisterValue(instruction.getC(), state.getRegisterValue(instruction.getA()) | instruction.getB());
				break;
			}

			case SETR:
			{
				newState.setRegisterValue(instruction.getC(), state.getRegisterValue(instruction.getA()));
				break;
			}
			case SETI:
			{
				
				newState.setRegisterValue(instruction.getC(), instruction.getA());
				break;
			}
			
			case GTIR:
			{
				int newValue = (instruction.getA() > state.getRegisterValue(instruction.getB())) ? 1 : 0;
				
				newState.setRegisterValue(instruction.getC(), newValue);
				break;
			}
			case GTRI:
			{
				int newValue = (state.getRegisterValue(instruction.getA()) > instruction.getB()) ? 1 : 0;
				
				newState.setRegisterValue(instruction.getC(), newValue);
				break;
			}
			case GTRR:
			{
				int newValue = (state.getRegisterValue(instruction.getA()) > state.getRegisterValue(instruction.getB())) ? 1 : 0;
				newState.setRegisterValue(instruction.getC(), newValue);
				break;
			}


			case EQIR:
			{
				int newValue = (instruction.getA() == state.getRegisterValue(instruction.getB())) ? 1 : 0;
				
				newState.setRegisterValue(instruction.getC(), newValue);
				break;
			}
			case EQRI:
			{
				int newValue = (state.getRegisterValue(instruction.getA()) == instruction.getB()) ? 1 : 0;
				
				newState.setRegisterValue(instruction.getC(), newValue);
				break;
			}
			case EQRR:
			{
				int newValue = (state.getRegisterValue(instruction.getA()) == state.getRegisterValue(instruction.getB())) ? 1 : 0;
				newState.setRegisterValue(instruction.getC(), newValue);
				break;
			}

			default:
			{
				throw new IllegalArgumentException(type.getName() + " is not handled");
			}
		}

		return newState;
	}

	public static OperationType getOperationType(String s)
	{
		for (OperationType type : OperationType.values())
		{
			if (type.getName().equals(s))
			{
				return type;
			}
		}

		return null;
	}
}	
