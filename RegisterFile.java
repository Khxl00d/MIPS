public class RegisterFile {
    private int[] register;

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
}