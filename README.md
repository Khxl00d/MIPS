# Java MIPS Processor
This Program is a MIPS Processor that is created using java 21.
This project emplements all the important components in a normal MIPS Processing unit, such as, Adder, ALU Control, ALU, Control Unit, Data Memory, Instruction, Instruction Memory, CPU, Multiplexer, Programe Counter, Register File, and a Assebler Parser.
This program also provides a GUI (Graphical User Interface) that provieds the user with a constant update on the registers.

 # Requerments
 * Java development Kit (JDK)
 * Any java-compatable complier (Eclipse, IntelliJ, VS Code).

# Executing Program
 * The Main Mips runner is found in the Main.java file, just by running the file you can code your mips instructions in the terminal, and if the user wishes to exit they can simply enter 0.
 * The Processor runs just like how a real one would; so the user would see which index they are at by 4 increments, where they can jump using the PC index, Such as; j 200. The user's PC will update to 200.

# Project Structure/File Explaination
 *Main.java                 -Program Starter, runs the program takes input sends output.
 *CPU.java                  -Core innstructions logic, Units the Program in one place.
 *ControlUnit.java          -Gives out the wire signales to each component in the processor.
 *ALU.java                  -Arithmatic unit, Handels any mathmatical logic used in the CPU.
 *ALUControl.java           -Sends/Translates the ALU Operation coming from the CPU back to the ALU.
 *RegisterFile.java         -Saves the Registers in a HashMap to be used later in muliple operations.
 *InstructionMemory.java    -Stores the instructions from the Assembler, and retrves them using the PC.
 *DataMemory.java           -Read/Writes the Memory for lw and sw instructions.
 *ProgramCounter.java       -Updates the user Program counter for every instruction that is added.
 *Adder.java                -Addes 4 to the PC, Used for Jal instruction.
 *Multiplexer.java          -Multiplexer chooses one of several inputs to output based on the output from the control unit.
 *Instruction.java          -Outputs the Data Model based on the used instruction (R-Type, I-type, J-Type).
 *AssemblerParser.java      -Takes the users input as text and Translates it to MIPS intructions.


# Authers
 *Hassan Mousa
 *Khalid Albadda
 *Leen Albadda
 *Sana Mathbut
 *Dilanka Dharmapala
