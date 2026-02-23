public class SignExtender {

    public int extendTo32(int imm) {
        if ((imm & 0x8000) != 0) { // check sign bit
            return imm | 0xFFFF0000; // fill upper bits with 1s
        } else {
            return imm & 0x0000FFFF; // keep positive value
        }
    }
}
