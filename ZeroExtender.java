public class ZeroExtender {

    public String getZeroExtender(int imm){

        String binary = Integer.toBinaryString(imm);

        while(binary.length() < 16){
            binary = "0" + binary;
        }

        String result = "0000000000000000" + binary;

        return result;
    }
}
