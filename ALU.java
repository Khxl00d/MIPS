public class ALU {
    public int result;

    public int ALUOutput(int readData1, int readData2, int ALUCont){
        switch(ALUCont){
            case 0: result = readData1 + readData2; break;
            case 1: result = readData1 - readData2; break;
            case 2: result = readData1 & readData2; break;
            case 3: result = readData1 | readData2; break;
            case 4: result = readData1 - readData2; if(result < 0){result = 0;} else{ result = 1;} break;
            default:
                result = 0;
        }
        return result;
    }
}
