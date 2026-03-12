import java.util.Scanner;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String mipsCode;
        InstructionMemory instructionMemory = new InstructionMemory();
        ProgramCounter pc = new ProgramCounter(0);
        RegisterFile registers = new RegisterFile();
        CPU cpu = new CPU(registers,instructionMemory, pc);
        RegisterTable regTable = new RegisterTable(registers);
        System.out.println("Mips Processor (type '0' to exit)");
        do { 
            regTable.update(registers.getAllRegisters());
            System.out.print("[" + pc.getPC() + "]: ");
            mipsCode = scan.nextLine();
            if(!mipsCode.equals("0")){
            AssemblerParser parser = new AssemblerParser(mipsCode, instructionMemory, pc.getPC(), registers, pc);
            Instruction instruction = instructionMemory.getInstruction(pc);
            cpu.executeCPU();
            if (instruction != null) {
                System.out.println(instruction.toString());
            }
            if(instruction.getOpcode() == 2 || instruction.getOpcode() == 3 && instruction.getTarget() < pc.getPC()){
                while(true){
                    Instruction instruction1 = instructionMemory.getInstruction(pc);
                    if (instruction1 != null){
                    System.out.println("\nLooping..");
                    System.out.print("[" + pc.getPC() + "]: ");
                    regTable.update(registers.getAllRegisters());
                    System.out.println(instruction1.toString());
                    cpu.executeCPU();
                    }
                    else{
                        break;
                    }
                }
            }
            }
        } while (!mipsCode.equals("0"));
        System.out.println("Exiting and printing registers:");
        registers.displayRegisters();   
        scan.close();
    }
}
class RegisterTable extends JFrame{
    RegisterFile registers;
    JTable regTable;

    public void update(int[] registers) {
        for (int i = 0; i < registers.length; i++) {
            regTable.setValueAt(registers[i],i,1);
        }
    }
    public RegisterTable(RegisterFile registers) {

        this.registers = registers;
        String[] columnName = {"Registers","Value"};
        String[] registerName = registers.getRegString();
        int[] registerValue = registers.getAllRegisters();

        Object[][] data = new Object[registerName.length][2];

        for (int i = 0; i < registerValue.length; i++) {
            data[i][0] = registerName[i];
            data[i][1] = registerValue[i];
        }

        regTable = new JTable(data,columnName);

        JScrollPane scrollPane = new JScrollPane(regTable);

        JFrame frame = new JFrame("Register Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.setSize(300, 575);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }
}
