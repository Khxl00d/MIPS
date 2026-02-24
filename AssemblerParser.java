public class AssemblerParser {
    InstructionMemory saveIns;
    private String inMipsLine;
    private String instruction;
    private String rsString;
    private int indexIns;
    private int indexRs;
    private int insType; //R - type == 1 // I - Type == 2 // J - Type == 3
    private int opcode;
    private int shamt;
    private int funct;
    private int immediate;
    private int target;
    private int rs;
    private int rt;
    private int rd;

    public AssemblerParser(String inMipsLine) {
        this.inMipsLine = inMipsLine;
        this.indexIns = -1;
        this.indexRs = indexIns+1;
        this.saveIns = new InstructionMemory();

        //instruction extractor
        for (int i = 0; i < inMipsLine.length(); i++) {
            if (inMipsLine.charAt(i) == ' ') {
                break;
            }
            else {
                indexIns++;
            }
        }
        if (indexIns >= 0) {
            instruction = inMipsLine.substring(0,indexIns);
        }

    }
    private void identifyInsType(String instruction) {
        if (
            instruction.equalsIgnoreCase("add") ||
            instruction.equalsIgnoreCase("sll") ||
            instruction.equalsIgnoreCase("and") ||
            instruction.equalsIgnoreCase("or") ||
            instruction.equalsIgnoreCase("nor") ||
            instruction.equalsIgnoreCase("jr") ||
            instruction.equalsIgnoreCase("slt")) {

                insType = 1;
        }
        else if (
            instruction.equalsIgnoreCase("addi") ||
            instruction.equalsIgnoreCase("andi") ||
            instruction.equalsIgnoreCase("ori") ||
            instruction.equalsIgnoreCase("lw") ||
            instruction.equalsIgnoreCase("sw") ||
            instruction.equalsIgnoreCase("beq")) {

                insType = 2;
        }
        else if (
            instruction.equalsIgnoreCase("j") ||
            instruction.equalsIgnoreCase("jal")) {

                insType = 3;
        }
    }
    private void identifyOpcode() {
        if (insType == 1) {
            opcode = 0;
            if (instruction.equalsIgnoreCase("add")) {
                funct = 32;
            }
            else if (instruction.equalsIgnoreCase("and")) {
                funct = 36;
            }
            else if (instruction.equalsIgnoreCase("or")) {
                funct = 37;
            }
            else if (instruction.equalsIgnoreCase("nor")) {
                funct = 39;
            }
            else if (instruction.equalsIgnoreCase("sll")) {
                funct = 0;
            }
            else if (instruction.equalsIgnoreCase("jr")) {
                funct = 8;
            }
            else if (instruction.equalsIgnoreCase("slt")) {
                funct = 42;
            }
        }
        else if (insType == 2) {
            if (instruction.equalsIgnoreCase("addi")) {
                opcode = 8;
            }
            else if (instruction.equalsIgnoreCase("andi")) {
                opcode = 12;
            }
            else if (instruction.equalsIgnoreCase("ori")) {
                opcode = 13;
            }
            else if (instruction.equalsIgnoreCase("lw")) {
                opcode = 35;
            }
            else if (instruction.equalsIgnoreCase("sw")) {
                opcode = 43;
            }
            else if (instruction.equalsIgnoreCase("beq")) {
                opcode = 4;
            }
        }
        else if (insType == 3) {
            if (instruction.equalsIgnoreCase("j")) {
                opcode = 2;
            }
            else if (instruction.equalsIgnoreCase("jal")) {
                opcode = 3;
            }
        }
    }
    private void identifyShamt() {

    }
    private void identifyFunct() {
        
    }
    private void identifyImmediate() {
        
    }
    private void identifytarget() {
        
    }
    private void identifyRs() {
        
    }
    private void identifyRt() {
        
    }
    private void identifyRd() {
        
    }
    private void storeInstruction() {
        if (insType == 1) { // stores the parsed R type instructions in Instruction memory
            Instruction intIns = new Instruction(opcode, rs, rt, rd, shamt, funct);
            saveIns.storeInstruction(intIns);
        }
        else if (insType == 2) { // stores the parsed I type instructions in Instruction memory
            Instruction intIns = new Instruction(opcode, rs, rt, immediate);
            saveIns.storeInstruction(intIns);
        }
        else if (insType == 3) { // stores the parsed J type instructions in Instruction memory
            Instruction intIns = new Instruction(opcode, target);
            saveIns.storeInstruction(intIns);
        }
    }
}