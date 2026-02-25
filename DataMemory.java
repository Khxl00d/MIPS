import java.util.HashMap;

public class DataMemory {
    private HashMap<Integer, Integer> memory;

    public DataMemory() {
        this.memory = new HashMap<>();
    }
    
    int readData(int address, int readMem) {
        if (readMem == 1) {
            return memory.getOrDefault(address, 0);
        }
        else {
            return 0;
        }
    }

    void writeData(int address, int value, int writeMem) {
        if (writeMem == 1) {
            memory.put(address, value);
        }
    }
}