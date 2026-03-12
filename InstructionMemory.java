import java.util.ArrayList;

public class InstructionMemory {

    private ArrayList<Instruction> insMem;
    int index;

    public InstructionMemory() {
        this.insMem = new ArrayList<>();
    }

    public Instruction getInstruction(ProgramCounter PC) {
        this.index = PC.getPC()/4;
        if (index >= 0 && index < insMem.size()) {
            return insMem.get(index);
        }
        else {
            return null;
        }
    }
    public void storeInstruction(Instruction inst, ProgramCounter PC) {
        this.index = PC.getPC()/4;
        while (insMem.size() <= index) {
            insMem.add(null);
        }
        insMem.set(index, inst);
    }

    public int getSize() {
        return insMem.size();
    }
}
