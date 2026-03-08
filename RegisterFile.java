public class RegisterFile {
    private int[] register;
    private String[] registerStrings = {"$zero", "$at", "$v0", "$v1", "$a0", "$a1", "$a2", "$a3", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7", "$s0", "$s1", "$s2", "$s3","$s4", "$s5", "$s6", "$s7", "$t8","$t9", "$k0", "$k1", "$gp", "$sp", "$fp", "$ra"};
    public RegisterFile() {
        this.register = new int[32];
        register[0] = 0;
    }

    public int readRegister(int reg) {
            return register[reg];
    }

    public void writeRegister(int reg, int value, int regWrite) {
        if (reg != 0 && regWrite==1) {
            register[reg] = value;
        }
    }

    public int[] getAllRegisters() {
        return this.register;
    }

    public void displayRegisters() {
        System.out.println("\n |Register State| "); 
        for (int i = 0; i < register.length; i++) {
            System.out.println(registerStrings[i] + ": " + register[i]);
        }
    }
}
