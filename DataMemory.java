import java.util.HashMap;

public class DataMemory {
    private HashMap<Integer, Integer> memory;

    public DataMemory() {
        this.memory = new HashMap<>();
    }
    
    int readData(int address, boolean readMem) {
        if (readMem) {
            return memory.getOrDefault(address, 0);
        }
        else {
            return 0;
        }
    }

    void writeData(int address, int value, boolean writeMem) {
        if (writeMem) {
            memory.put(address, value);
        }
    }
}