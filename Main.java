import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String mipsCode;
        InstructionMemory instructionMemory = new InstructionMemory();
        ProgramCounter pc = new ProgramCounter(0);
        RegisterFile registers = new RegisterFile();
        System.out.println("Mips Processor (type '0' to exit)");
        
        do { 
            System.out.print("[" + pc.getPC() + "]: ");
            mipsCode = scan.nextLine();
            if(!mipsCode.equals("0")){
            AssemblerParser parser = new AssemblerParser(mipsCode, instructionMemory, pc.getPC(), registers);
            Instruction instruction = instructionMemory.getInstruction(pc);
            System.out.println(instruction.toString());
            pc.incrementPC();
            }
        } while (!mipsCode.equals("0"));
        System.out.println("Exiting and printing registers:");
        registers.displayRegisters();   
        scan.close();
    }
}
