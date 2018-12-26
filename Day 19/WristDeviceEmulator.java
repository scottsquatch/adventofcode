public class WristDeviceEmulator
{
    private Instruction[] instructionStack;
    private int ipRegister;
    private int ip;

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
    }

    public RegisterState run()
    {
        RegisterState state = new RegisterState(0, 0, 0, 0, 0, 0, ipRegister);

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

        System.out.print("ip=" + ip + " " + next + " " + instr);

        next = OperationType.applyOperation(instr.getop(), next, instr);

        System.out.println(" " + next);

        ip = next.getRegisterValue(ipRegister);

        ip++;

        return next;
    }
}