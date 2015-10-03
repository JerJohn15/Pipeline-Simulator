package Pipeline;

public class ID_EX extends IF_ID{

	/**
	 * This class handles the operations of the IDEX
	 * register
	 */
	
	//These variables are the ones that store the 
	//values from the WRITE version of  IF_ID.
	
	private int instruc = 0;
	//These variables store the values to be written to
	//the EX_MEM class
	protected String funct = "";
	private int src1 = 0;
	private int src2 = 0;
	private int dest = 0;
	private short offset = 0;
	private int RegDst = 0;
	private int ALUOp0 = 0;
	private int ALUOp1 = 0;
	private int ALUSrc = 0;
	private int Branch = 0;
	private int MemRead =0;
	private int MemWrite = 0;
	private int RegWrite = 0;
	private int MemtoReg = 0;
	private int regVal1 =0x00;
	private int regVal2 = 0x00;
	private int writeReg1 = 0;
	private int writeReg2 = 0;
	
	
	
	
	
	/**
	 * @method - ID_Stage
	 *@details - handles the functionality of the ID Stage
	 *
	 */
	public void ID_Stage(){
		Write_Ex();
	}
	
	/**
	 * @method - Read_ID
	 * @detail - reads the instruction
	 * from the ID stage (in IFID class)
	 */
	public void Read_ID(int aInstruc){
		instruc = aInstruc;
		
	}
	
	/**
	 * @method - Write_Ex
	 * @detail - writes the nine control 
	 * bits, the reg values, sign extend (if one exists)
	 * , and the writeRegNums to the EX register.
	 * Note: the decode method handles the calculation
	 * for getting the 32 offset and the two write  Reg 
	 * numbers. 
	 */
	public void Write_Ex(){
		decode(); 
		setControl();
		setRegValues();
		
		
		
	}
	//***Getters used to perform the READ portion of EXMEM
	//pipeline register 
	public short getOffset() {
		return offset;
	}
	
	

	public int getWriteReg2() {
		return writeReg2;
	}

	public int getWriteReg1() {
		return writeReg1;
	}

	

	
	
//Getters for the nine control bits
	public int getRegDst() {
		return RegDst;
	}

	public int getALUOp0() {
		return ALUOp0;
	}

	public int getALUOp1() {
		return ALUOp1;
	}

	public int getALUSrc() {
		return ALUSrc;
	}

	public int getBranch() {
		return Branch;
	}

	public int getMemRead() {
		return MemRead;
	}

	public int getMemWrite() {
		return MemWrite;
	}

	public int getRegWrite() {
		return RegWrite;
	}

	public int getMemtoReg() {
		return MemtoReg;
	}

	public int getRegVal1() {
		return regVal1;
	}

	public int getRegVal2() {
		return regVal2;
	}
		
	
	//****Setters used to perform the WRITE to IDEX
	/**
	 * @method - setRegValues
	 * @details - Sets the register values
	 * for an instruction.
	 * 
	 */
	public void setRegValues(){
	
		regVal1 = Regs[src1];
		regVal2= Regs[src2];
		
	}

	/**
	 * @method-  setControl
	 * @detail - Sets the nine control bits into
	 * the IDEX register. 
	 * Note:
	 * (1)If the funct is a sb,
	 * the junk value for the regDst and MemtoReg is set to
	 * -1.
	 * 
	 * 
	 */
	public void setControl(){
		if(funct.matches("add|sub*")){
			setExControl(1,0,1,0);
			setMControl(0,0,0);
			setWBControl(1,0);
		}else if(funct.matches("sb*")){
			setExControl(-1,0,0,1);
			setMControl(0,0,1);
			setWBControl(0,-1);
		}else if(funct.matches("lb*")){
			setExControl(0,0,0,1);
			setMControl(0,1,0);
			setWBControl(1,1);
		}else{//nop
			setExControl(0,0,0,0);
			setMControl(0,0,0);
			setWBControl(0,0);
		}
	}

	public void setExControl(int aRegDst, int aALUOp0, int aALUOp1,int aALUSrc){
		RegDst = aRegDst;
		ALUOp0 = aALUOp0;
		ALUOp1 = aALUOp1;
		ALUSrc = aALUSrc;
	}

	
	public void setMControl(int aBranch, int aMread, int aMwrite){
		
		Branch = aBranch;
		MemRead = aMread;
		MemWrite = aMwrite;
		
	}
	
	public void setWBControl(int aRegWrite, int aMemtoReg){
		RegWrite = aRegWrite;
		MemtoReg = aMemtoReg;
	}
	
	/**
	 * @method -setIFunct
	 * @detail - sets the instruc for lb and sb
	 *
	 */
	public void setIFunct(int opVal){
		switch(opVal){
		case 0x20:
			funct = "lb";
			break;
		case 0x28:
			funct = "sb";
			break;
		default:
			System.out.println("Error value not found.");
			break;
		}
		
	}
	
	/**
	 * @method- setFunct
	 * Sets the MIMPS function if found.
	 *
	 */
	
	public void setRFunct(){
		
		switch(offset){
		case 0x0:
			funct = "nop";
			break;
		case 0x20:
			funct = "add";
			break;
		case 0x22:
			funct = "sub";
			break;
		default:
			System.out.println("Error value not found.");
			break;
		}
	}

	/**
	 * @method - calculateField
	 * @details - Calculates each MIMPS field
	 * @return - Returns the decimal value of 
	 * a MIMPS field
	 */
	public int calculateField(int mask,int shiftNum){
		int bitwise =  instruc & mask;
		int shifted_bits = bitwise>>>shiftNum;
			return shifted_bits;
	}

	/**
	 * @method - decode
	 *@detail - Decodes an instruction.
	 *1) Calculates the I format fields if opcode is 
	 * 0. Else, calculates the R format fields.
	 * 2) Calculates the writeReg values for each instruction type.
	 * Note: dest and src2 (R format)
	 *  	 writeReg1 and src2 (I format)   
	 *  
	 * @param ainstruct
	 */
	public void decode(){
		int optCode = calculateField(0xFC000000,26);
		if(optCode==0){			
			//calculate first src
			src1 = calculateField(0x03E00000,21);
			//calculate second src 16-20
			src2 = calculateField(0x001F0000,16);
			//calculate dest 11-15
			dest = calculateField(0x0000F800,11);
			//calculate shamt
			calculateField(0x000007C0,6);
			//calculate funct 
			 offset = (short) calculateField(0x0000003F,0);
			setRFunct();
			//stores the writeReg1 value 
			writeReg2 = dest;
			writeReg1 = src2;
		}else{
			//sets function
			setIFunct(optCode);	
			//calculate first src
			src1 = calculateField(0x03E00000,21);
			//calculate second src 16-20
			src2 = calculateField(0x001F0000,16);
			//calculate offset 0-15
			offset =  (short) (instruc & 0x0000FFFF);
			//sets write values
			 writeReg2 =  calculateField(0x0000F800,11);
			 writeReg1 = src2;
		}
	}
	/**
	 * @details - Prints  the current instruction
	 * from the cacheInstruc array
	 */
	public void printID_Read(){
		System.out.println("****IF/ID Read: ****");
		System.out.println("Instruct: 0x" + Integer.toHexString(instruc));
		
	}
	

	
	/**
	 * @details - Prints the contents to be written
	 * to the ID/EX register
	 */
	public void printID_Write(){
		System.out.println("****ID/EX Write: ****");
		System.out.println();
		System.out.println("EX Control Bits: " +
				RegDst + "" + ALUOp0 + ""+ ALUOp1 + ""
				+ ALUSrc);
		System.out.println("M Control Bits: " + 
				Branch + "" + MemRead + "" + MemWrite);
		System.out.println("WB Control Bits:" +
				RegWrite + "" + MemtoReg);
		System.out.println();
		System.out.println("ReadReg1 Val: 0x" + Integer.toHexString(regVal1));
		System.out.println("ReadReg2 Val: 0x" + Integer.toHexString(regVal2));
		System.out.println();
		System.out.println("SEOffset: 0x" +Integer.toHexString(offset));
				
		System.out.println();
		System.out.println("WriteregVal1: " + writeReg1 +"(Instruc 20-16)");
		System.out.println("WriteregVal2: " + writeReg2 +"(Instruc 11-15)");
		
	}
	
	
	
	
}
