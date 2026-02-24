import java.util.HashMap;

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
    private HashMap<String, Integer> registersMap = new HashMap<>();


    public AssemblerParser(String inMipsLine) {
        this.inMipsLine = inMipsLine;
        this.indexIns = -1;
        this.indexRs = indexIns+1;
        this.saveIns = new InstructionMemory();
        
        registersMap.put("$zero", 0);
        registersMap.put("$at", 1);
        registersMap.put("$v0", 2); registersMap.put("$v1", 3);
        registersMap.put("$a0", 4); registersMap.put("$a1", 5); registersMap.put("$a2", 6); registersMap.put("$a3", 7);
        registersMap.put("$t0", 8); registersMap.put("$t1", 9); registersMap.put("$t2", 10); registersMap.put("$t3", 11);
        registersMap.put("$t4", 12); registersMap.put("$t5", 13); registersMap.put("$t6", 14); registersMap.put("$t7", 15);
        registersMap.put("$s0", 16); registersMap.put("$s1", 17); registersMap.put("$s2", 18); registersMap.put("$s3", 19);
        registersMap.put("$s4", 20); registersMap.put("$s5", 21); registersMap.put("$s6", 22); registersMap.put("$s7", 23);
        registersMap.put("$t8", 24); registersMap.put("$t9", 25);
        registersMap.put("$k0", 26); registersMap.put("$k1", 27);
        registersMap.put("$gp", 28); registersMap.put("$sp", 29); registersMap.put("$fp", 30); registersMap.put("$ra", 31);
        
    
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