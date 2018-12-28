import java.util.Hashtable;
import java.util.Scanner;

public class WristDeviceEmulator
{
    private Instruction[] instructionStack;
    private int ipRegister;
    private int ip;
    private Scanner in;

    public WristDeviceEmulator(String[] lines)
    {
        instructionStack = new Instruction[lines.length - 1];
        ip = 0;

        // First line should always set the IP register
        ipRegister = Integer.parseInt(lines[0].replace("#ip ", ""));

        for (int i = 1; i < lines.length; i++)
        {
            instructionStack[i - 1] = Instruction.parseInstruction(lines[i]);
        }

        in = null;
    }

    public void attachDebugger()
    {
      in = new Scanner(System.in);
    }

    public RegisterState run()
    {
        return run(new RegisterState(0, 0, 0, 0, 0, 0));
    }

    public RegisterState run(RegisterState initialState)
    {
	    RegisterState state = (RegisterState)initialState.clone();

	    while (ip >= 0 && ip < instructionStack.length)
	    {
		    state = step(state);
	    }

	    return state;
    }

    public RegisterState step(RegisterState currentState)
    {
        Instruction instr = instructionStack[ip];
        RegisterState next = (RegisterState)currentState.clone();

        // Update ip Register to ip value
        next.setRegisterValue(ipRegister, ip);

        StringBuilder builder = new StringBuilder();
        builder.append("ip=" + ip + " " + next + " " + instr);
        next = OperationType.applyOperation(instr.getop(), next, instr);

	      builder.append(" " + next);

        ip = next.getRegisterValue(ipRegister);

        ip++;

        if (in != null)
        {
          System.out.println(builder.toString());
          String sInput = in.nextLine();
          String[] split = sInput.split(" ");
          if (split != null & split.length > 1)
          {
            int register = Integer.parseInt(split[0]);
            int value = Integer.parseInt(split[1]);

            next.setRegisterValue(register, value);
          }
        }
        return next;
    }
}
