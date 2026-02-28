import java.util.HashMap;

public class AssemblerParser {
    
    InstructionMemory saveIns;
    private String inMipsLine;
    private String label;
    private int pc;
    private int insType; //R - type == 1 // I - Type == 2 // J - Type == 3
    private int opcode = 0;
    private int shamt = 0;
    private int funct = 0;
    private int immediate = 0;
    private int target = 0;
    private int rs = 0;
    private int rt = 0;
    private int rd = 0;
    private String[] arrInstruction;
    private HashMap<String, Integer> registersMap = new HashMap<>();
    private HashMap<String, int[]> rTypeMap = new HashMap<>();
    private HashMap<String, Integer> iTypeMap = new HashMap<>();
    private HashMap<String, Integer> jTypeMap = new HashMap<>();

    public AssemblerParser(String inMipsLine, InstructionMemory sharedInsMem, String label, int pc) {
        this.inMipsLine = inMipsLine;
        this.saveIns = sharedInsMem;
        this.label = label;
        this.pc = pc;

        registersMap.put("$zero", 0);
        registersMap.put("$at", 1);
        registersMap.put("$v0", 2);  registersMap.put("$v1", 3);
        registersMap.put("$a0", 4);  registersMap.put("$a1", 5);  registersMap.put("$a2", 6);  registersMap.put("$a3", 7);
        registersMap.put("$t0", 8);  registersMap.put("$t1", 9);  registersMap.put("$t2", 10); registersMap.put("$t3", 11);
        registersMap.put("$t4", 12); registersMap.put("$t5", 13); registersMap.put("$t6", 14); registersMap.put("$t7", 15);
        registersMap.put("$s0", 16); registersMap.put("$s1", 17); registersMap.put("$s2", 18); registersMap.put("$s3", 19);
        registersMap.put("$s4", 20); registersMap.put("$s5", 21); registersMap.put("$s6", 22); registersMap.put("$s7", 23);
        registersMap.put("$t8", 24); registersMap.put("$t9", 25);
        registersMap.put("$k0", 26); registersMap.put("$k1", 27);
        registersMap.put("$gp", 28); registersMap.put("$sp", 29); registersMap.put("$fp", 30); registersMap.put("$ra", 31);
        
        //R-type Instructions {opcode,funct} opcode is 0 by default
        rTypeMap.put("add", new int[]{0,32});
        rTypeMap.put("and", new int[]{0, 36});
        rTypeMap.put("or",  new int[]{0, 37});
        rTypeMap.put("nor", new int[]{0, 39});
        rTypeMap.put("slt", new int[]{0, 42});
        rTypeMap.put("sll", new int[]{0, 0});
        rTypeMap.put("jr",  new int[]{0, 8});

        //I-type Instructions funct is set as -1 or any other number because it wont be stored either way
        iTypeMap.put("addi", 8);
        iTypeMap.put("andi", 12);
        iTypeMap.put("ori",  13);
        iTypeMap.put("lw",   35);
        iTypeMap.put("sw",   43);
        iTypeMap.put("beq",  4);

        //J-type Instrucion
        jTypeMap.put("j",    2);
        jTypeMap.put("jal",  3);

        arrInstruction = inMipsLine.split(" ");// if inMipsLine = addi $s0 $t0 5 then arrInstruction[0] = addi

        identifyInsType(arrInstruction);
        storeOpFunct();
        identifyRegisters(arrInstruction);
        identifyImmediate(arrInstruction);
        identifytarget(arrInstruction);
        identifyShamt(arrInstruction);
        parseLabel();
        storeInstruction();
    }

    private void parseLabel() {
        if (insType == 3) {
            if (!label.equalsIgnoreCase("")) {
                target = pc;
            }
        } 
    }

    private void storeOpFunct() {
        if (insType == 1) {
        opcode = rTypeMap.get(arrInstruction[0])[0];
        funct = rTypeMap.get(arrInstruction[0])[1];
        }

        else if (insType == 2) {
        opcode = iTypeMap.get(arrInstruction[0]);
        }

        else if (insType == 3) {
        opcode = jTypeMap.get(arrInstruction[0]);
        }

    }
    private void identifyInsType(String[] inst) {
        if (rTypeMap.containsKey(inst[0])) {

                insType = 1;
        }
        else if (iTypeMap.containsKey(inst[0])) {

                insType = 2;
        }
        else if (jTypeMap.containsKey(inst[0])) {

                insType = 3;
        }
    }

    private void identifyShamt(String[] inst) {
        if("sll".equalsIgnoreCase(inst[0])) {
            shamt = Integer.parseInt(inst[3]);
            rs = 0;
        }
    }

    private void identifyImmediate(String[] inst) {
        if (insType == 2 && !"sw".equalsIgnoreCase(inst[0]) && !"lw".equalsIgnoreCase(inst[0])) {
            immediate = Integer.parseInt(inst[3]);
        }
    }
    private void identifytarget(String[] inst) {
        if (insType == 3) {
            target = Integer.parseInt(inst[1]);
        }
    }
    private void identifyRegisters(String[] inst) {
        if(insType == 1 && !"jr".equalsIgnoreCase(inst[0])) {
            rd = registersMap.get(inst[1]);
            rs = registersMap.get(inst[2]);
            rt = registersMap.get(inst[3]);
        }
        else if(insType == 1 && "jr".equalsIgnoreCase(inst[0])) {
            rs = registersMap.get(inst[1]);
        }
        else if(insType == 2 && !"sw".equalsIgnoreCase(inst[0]) && !"lw".equalsIgnoreCase(inst[0])) {
            rt = registersMap.get(inst[1]);
            rs = registersMap.get(inst[2]);
        }
        else if(insType == 2 && ("sw".equalsIgnoreCase(inst[0]) || "lw".equalsIgnoreCase(inst[0]))) {
            rt = registersMap.get(inst[1]);
            immediate = Integer.parseInt(inst[2].substring(0,inst[2].indexOf("(")));//This will get the value from inst[2] at index 0 to the last index just before "("
            rs = registersMap.get(inst[2].substring(inst[2].indexOf("(") + 1,inst[2].indexOf(")")));//This will get the register string between the parenthesis and then get the register value from the hashmap
        }
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