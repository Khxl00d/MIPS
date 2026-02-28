import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class MipsGUI extends JFrame {
    private JTable regTable;
    private DefaultTableModel tableModel;
    private JTable editorTable;
    private DefaultTableModel editorModel;
    private RegisterFile registers;
    private AssemblerParser parser;
    private CPU cpu;
    private InstructionMemory globalMemory = new InstructionMemory();

    public static void main(String[] args) {
        new MipsGUI();
    }

    public MipsGUI() {
        this.registers = new RegisterFile();

        String[] columnNames = {"Register", "Value"};
        this.tableModel = new DefaultTableModel(columnNames, 0);
        this.regTable = new JTable(tableModel);
        
        String[] editorColumns = {"PC", "Label", "Instruction"};
        this.editorModel = new DefaultTableModel(editorColumns, 0);
        this.editorTable = new JTable(editorModel);

        setTitle("MIPS Simulator - Team Project");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(1500);
        add(splitPane, BorderLayout.CENTER);

        JScrollPane regScroll = new JScrollPane(regTable);
        JScrollPane editorScroll = new JScrollPane(editorTable);

        regScroll.setPreferredSize(new Dimension(300, 0));

        leftPanel.add(editorScroll, BorderLayout.CENTER);
        leftPanel.add(new JLabel(" Code Editor"), BorderLayout.NORTH);
        leftPanel.add(editorScroll, BorderLayout.CENTER);

        rightPanel.add(new JLabel(" Registers"), BorderLayout.NORTH);
        rightPanel.add(regScroll, BorderLayout.CENTER);
        

        String[] regNames = {
            "$zero", "$at", "$v0", "$v1", "$a0", "$a1", "$a2", "$a3",
            "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7",
            "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7",
            "$t8", "$t9", "$k0", "$k1", "$gp", "$sp", "$fp", "$ra"
        };

        //Display Register Table
        for (int i = 0; i < regNames.length; i++) {
            tableModel.addRow(new Object[]{regNames[i],0});
        }
        
        //custom size for the editor table columns
        editorTable.getColumnModel().getColumn(0).setPreferredWidth(10); 
        editorTable.getColumnModel().getColumn(1).setPreferredWidth(40);
        editorTable.getColumnModel().getColumn(2).setPreferredWidth(550);

        //Font settings for the editor table
        Font editorFont = new Font("Monospaced", Font.PLAIN, 18);
        editorTable.setFont(editorFont);
        editorTable.setRowHeight(30);
        editorTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));


        //CodeEditor Area with 100 lines maximum
        for (int i = 0; i < 100; i++) {// change 100 to any other desired value
            editorModel.addRow(new Object[]{i * 4, "", ""}); 
        }


        //Run Button
        JToolBar toolbar = new JToolBar();
        JButton runButton = new JButton("Run");
        runButton.setFont(new Font("SanSerif", Font.BOLD, 14));

        toolbar.add(runButton);
        add(toolbar, BorderLayout.NORTH);

        //Terminal section
        JTextArea terminal = new JTextArea(8, 20);
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.GREEN);
        terminal.setFont(new Font("Monospaced", Font.PLAIN, 14));
        terminal.setEditable(false);// setting the value for this line as false, blocks the user from editting text in the terminal

        JScrollPane terminalScroll = new JScrollPane(terminal);
        JSplitPane mainVerticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, terminalScroll);

        mainVerticalSplit.setResizeWeight(0.7);
        add(mainVerticalSplit, BorderLayout.CENTER);


        //Establishing the connection between the GUI and Assembler Parser
        runButton.addActionListener(e -> {
            terminal.setText(""); 
            globalMemory = new InstructionMemory();
            String Label = "";
            String strPc;
            int pc;

            for (int i = 0; i < editorTable.getRowCount(); i++) {
                Object cellvalue = editorTable.getValueAt(i,2);
                Object cellLabel = editorTable.getValueAt(i,1);
                Object cellPc = editorTable.getValueAt(i,0);


                if (cellvalue != null && !cellvalue.toString().trim().isEmpty()) {
                    String instructionStr = cellvalue.toString().trim();
                    Label = cellLabel.toString().trim();
                    strPc = cellPc.toString().trim();
                    pc = Integer.parseInt(strPc);
                    terminal.append("Assembling Line " + i + ": " + instructionStr + "\n");
                    new AssemblerParser(instructionStr,globalMemory,Label,pc);
                }
            }

            cpu = new CPU(this.registers,this.globalMemory);

            while (cpu.PC.getPC()/4 < globalMemory.getSize()) {
               cpu.executeCPU();
            }

            updateReg(registers.getAllRegisters());
            terminal.append("Execution Successful.\n");
        });

        setVisible(true);
    }
    

    public void updateReg(int[] newValue) {
        for (int i = 0; i < newValue.length; i++) {
            tableModel.setValueAt(newValue[i],i,1);
        }
    }
}