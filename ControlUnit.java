public class ControlUnit{

    int[] controlSignals(int opCode, int func){
        int[] output = new int[9];

        switch(opCode){
            //R-type instructions (add, and, sll, nor, slt)
            case 0:
                // to distinguish between (jr) and the rest of R-type instructions
                if(func==8){
                output[1] = 1; output[2] = 0; 
                output[3] = 0; output[6] = 0; output[8] = 0; 

                }else{
                output[0] = 1; output[1] = 0; output[2] = 0; 
                output[3] = 0; output[4] = 0; output[5] = 2; 
                output[6] = 0; output[7] = 0; output[8] = 1; 

                }
            break;
            
            //I-type Arethmetic/Logic instructions (addi, andi, ori)
            case 8: 
            case 12: 
            case 13:
                output[0] = 0; output[1] = 0; output[2] = 0; 
                output[3] = 0; output[4] = 0; output[5] = 0; 
                output[6] = 0; output[7] = 1; output[8] = 1;  
            break;

            //Load instructions (lw,sw)
            case 32:
            case 35: 
                output[0] = 0; output[1] = 0; output[2] = 0; 
                output[3] = 1; output[4] = 1; output[5] = 0; 
                output[6] = 0; output[7] = 1; output[8] = 1; 
            break;

            //Store (sw, sb)
            case 40: 
            case 43: 
                output[1] = 0; output[2] = 0; 
                output[3] = 0; output[5] = 0; 
                output[6] = 1; output[7] = 1; output[8] = 0; 

            break;

            //(beq)
            case 4: 
                output[1] = 0; output[2] = 1; 
                output[3] = 0; output[5] = 1; 
                output[6] = 0; output[7] = 0; output[8] = 0; 

            break;

            //(j)
            case 2: 
                output[1] = 1; output[2] = 0; 
                output[3] = 0; output[6] = 0; output[8] = 0; 

            break;

            //(jal)
            case 3:
                output[1] = 1; output[2] = 0; 
                output[3] = 0; output[6] = 0; output[8] = 1; 
            break;
            
            

        }

        return output;

 }

}
