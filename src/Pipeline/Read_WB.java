package Pipeline;


/**
 * This class Reads the written values from 
 * MEM_WB and writes new values to the registers. 
 * 
 * @author jthen_000
 *
 */
public class Read_WB extends MEM_WB{

	
	//These variables store the read values from the Write
	//version of MEM_WB (see Write_to_MEMWB)
	private int writeRegVal  = 0;
	private int MemtoReg = 0;
	private int RegWrite = 0;
	private int ALUresult = 0x00;
	private int LBDataVal = 0x00;
	
	//This variable store the write values to be
	//written to the registers (see Write_to_Regs)
	private int writeData = 0x00;
	
	
	/**
	 * @method - WB_Stage
	 *@details - handles the functionality of the ID Stage
	 *
	 */
	public void WB_Stage(){
		Write_to_Regs();
	}
	
	
	
	/**
 * @detail - Reads the WB control bits,
 * LWDataVal, ALUresult, and writeRegnumber
 */
	public void Copy_WBWrite(){

		RegWrite = Integer.parseInt(getWBBits().substring(0, 1));
		MemtoReg = Integer.parseInt(getWBBits().substring(1, getWBBits().length()));
		ALUresult = getALUresult();
		writeRegVal = getWriteRegVal();
		LBDataVal = getLBDataVal();
		
	}

	
	/**
	 * @details - Writes data to the Registers
	 */
	public void Write_to_Regs(){
		WB_Mux();
	}
	
	/**
	 * @details - Sets a new register value
	 * at the dest register.
	 * (1) If MemtoReg is 1 and RegWrite is 1, writeData is for a Lb
	 * (2) If MemtoReg is 0 and RegWrite is 1, writeData is for an R type,
	 * 
	 */

	public void WB_Mux(){
		if(MemtoReg == 1&& RegWrite ==1){
			Regs[writeRegVal]= LBDataVal;
			writeData = Regs[writeRegVal];
		}else if(MemtoReg == 0 && RegWrite ==1){
			Regs[writeRegVal] = ALUresult;
			writeData = Regs[writeRegVal];
		}
	}

/**
 * @details - prints the data to be read from the WB stage
 */
public void printWB_Read(){
	System.out.println("****MEM/WB Read: ****");
	System.out.println();
	System.out.println("WB Control Bits:" +
			RegWrite + "" + MemtoReg);
	System.out.println();
	System.out.println("ALUresult: 0x" + Integer.toHexString(ALUresult));
	System.out.println();
	System.out.println("LBDataValue: 0x" + Integer.toHexString(LBDataVal));
	System.out.println();
	System.out.println("WriteRegNum: " + writeRegVal);


}
/**
 * Prints out the written value to the register
 * Note:  I print out whatever writeData has been set to.
 * This  includes the "junk" values for sb and Nops.
 */
public void print_Write_to_Reg(){
	System.out.println("$" + writeRegVal + " has a  value of 0x"
			+ Integer.toHexString(writeData));
	System.out.println();
}

}
