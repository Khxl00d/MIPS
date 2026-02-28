import java.util.ArrayList;

public class InstructionMemory {

    private ArrayList<Instruction> insMem;

    public InstructionMemory() {
        this.insMem = new ArrayList<>();
    }

    public Instruction getInstruction(ProgramCounter PC) {
        int index = PC.getPC()/4;
        if (index >= 0 && index < insMem.size()) {
            return insMem.get(index);
        }
        else {
            return null;
        }
    }
    public void storeInstruction(Instruction inst) {
        insMem.add(inst);
    }

    public int getSize() {
        return insMem.size();
    }
}