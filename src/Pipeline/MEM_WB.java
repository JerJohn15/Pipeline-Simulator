package Pipeline;

	

public class MEM_WB extends EX_MEM{
/**
 *This class handles the READ and WRITE portions of
 *the MEM WB register
 */
	
	//These variables store the read values from EX_MEM
	private int Branch = 0;
	private int MemRead = 0;
	private int MemWrite = 0;
	private int address = 0;//address to be used in case of lb
	
	private int read_ALUresult =0;
	private int SBVal = 0;
	private int read_writeReg = 0;
	private String read_WBBits = "00";
	
	//These variables store the write values to the MEM_WB
	private String WBBits = "00";
	private int writeRegVal = 0;
	private int ALUresult = 0x00;
	private int LBDataVal = 0x00;
	private int writeData = 0x00;
	
	
	/**
	 * @method - MEM_Stage
	 *@details - handles the functionality of the MEM Stage
	 *
	 */
	public void MEM_Stage(){
		Write_to_MEMWB();
	}
	
	
	
	/**
	 * @details - Reads the ALUresult,
	 * 	writeRegNum, WB and M control bits, 
	 * and SWValue, and address 
	 * 
	 */
	public void Copy_MEMWrite(){
		read_WBBits = super.getWBBits();
		Branch = Integer.parseInt(getMBits().substring(0, 1));
		MemRead =  Integer.parseInt(getMBits().substring(1, 2));
		MemWrite = Integer.parseInt(getMBits().substring(2));
		read_ALUresult = super.getALUresult();
		address = read_ALUresult;//address to be used to find LBDataVal
		SBVal = getSBVal();
		read_writeReg = super.getWriteRegVal();
		
		
	}
	/**
	 * @method 
	 * @detail - Writes the WB control bits, 
	 * ALUresult, LBData Value, writeReg number, 
	 * and writeData to the MEMWB write side.
	 */
	public void Write_to_MEMWB(){
		setWBBits();
		setALUresult();
		setLBDataVal();
		setWriteReg();
		setWriteData();
		
		
	}
	
	/**
	 * @details - Reads the value
	 * from memory address if MemRead is true (for lb)
	 * Note: LBData is set to 0, if value is junk or a Nop
	 */
	
	public void setLBDataVal(){
		if(MemRead == 1){
			LBDataVal = Main_Mem[address];
		}else{//set to  zero for output
			LBDataVal = 0x0;
		}
	}
	
	/**
	 * @method - setWriteData
	 * @details- Writes the Sb  value
	 * to the memory address (ALUresult value), if
	 * a sb. 
	 * Note: writeData is set to 0 in the case of a 
	 * Nop or junk value
	 */
	public void setWriteData(){
		if(MemWrite == 1){
			Main_Mem[ALUresult] = SBVal;
			writeData = Main_Mem[ALUresult];
		}else{//set to zero for output
			writeData = 0x0;
		}
	}
	
	
	/**
	 * Writes the writeRegVal, WB control bits, and
	 * ALU result to the WRITE stage of MEMWB
	 */
	public void setWriteReg(){
		writeRegVal = read_writeReg;
	}
	public void setWBBits(){
		WBBits = read_WBBits;
	}
	
	public void setALUresult(){
		ALUresult = read_ALUresult;
	}

//Getters to be used in the READ WB stage
	@Override
	public String getWBBits(){
		return WBBits;
	}



	public int getLBDataVal() {
		return LBDataVal;
	}



	@Override
	public int getWriteRegVal() {
		return writeRegVal;
	}



	@Override
	public int getALUresult() {
		return ALUresult;
	}
	
	//Prints out the MEM read contents
	/**
	 * @detail - Prints the read for exmem
	 * Note: writeRegNum at -1 is "junk"
	 */
	public void printMEM_Read(){
		System.out.println("****EX/MEM Read: ****");
		System.out.println();
		System.out.println("M Control Bits: " + 
				Branch + "" + MemRead + "" + MemWrite);
		System.out.println("WB Control Bits:" +WBBits);
		System.out.println();
		System.out.println("ALUresult: 0x" + Integer.toHexString(read_ALUresult));
		System.out.println();
		System.out.println("SBValue: 0x" + Integer.toHexString(SBVal));
		System.out.println();
		System.out.println("WriteRegNum: " + writeRegVal);


		
	}
	/**
	 * @method -
	 * @detail - Prints the data written 
	 * to the WRITE version of MEM_WB
	 *Note: writeRegNum at -1 is a "junk" value.
	 *  
	 */
	public void printMEM_Write(){
		
		System.out.println("****MEM/WB Write: ****");
		System.out.println();
		System.out.println("WB Control Bits:" +WBBits);
		System.out.println();
		System.out.println("ALUresult: 0x" + Integer.toHexString(ALUresult));
		System.out.println();
		System.out.println("LBDataValue: 0x" + Integer.toHexString(LBDataVal));
		System.out.println();
		System.out.println("WriteRegNum: " + writeRegVal);
		System.out.println("WriteData : 0x" + Integer.toHexString(writeData));


		
		
	}
	
}
	

