package Pipeline;
/**
 * Read Register Values are in Read_EX
 * Write Values are: ALUresult, WriteRegVal
 * , SWVal, WBBits, MBits 
 *
 * @author jthen_000
 *
 */
public class EX_MEM extends ID_EX{
//These variables are used for the READ portion of ID_EX
	private int SEOffset = 0;
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
	private int readRegVal2 = 0x00;
	//These variables are used to write to the MEM
	private int writeRegVal = 0;
	private int ALUresult = 0x00;
	private int SBVal = 0x00;
	private String  WBBits = "00";
	private String MBits = "000";
	private int writeReg1 = 0;
	private int writeReg2 =0;

	

	/**
	 * @method - EX_Stage
	 *@details - handles the functionality of the EX Stage
	 *
	 */
	public void EX_Stage(){
		Write_to_EXMEM();
	}

	/**
	 * @method - Copy_EXWrite
	 * @detail - Reads in the nine control bits, 
	 * register values, offset and write register values from
	 * IDEX class.
	 * 
	 */
	public void Copy_EXWrite(){
		RegDst = getRegDst();
		ALUOp0 = getALUOp0();
		ALUOp1 = getALUOp1();
		ALUSrc = getALUSrc();


		RegWrite = getRegWrite();
		MemtoReg = getMemtoReg();
		Branch = getBranch();
		MemRead = getMemRead();
		MemWrite = getMemWrite();

		regVal1 = getRegVal1();
		regVal2 = getRegVal2();
		readRegVal2 = regVal2; //saves regVal2 to be printed later on
		SEOffset = getOffset();

		writeReg1 = getWriteReg1();
		writeReg2 = getWriteReg2();
	}


	/**
	 * @details - Writes the WB and M control bits,
	 *    ALU result, SWValue, and Write Register values
	 *    to the EX/MEM write register
	 * 
	 */
	public void Write_to_EXMEM(){
		WBBits = Integer.toString(RegWrite)
				+ Integer.toString(MemtoReg);
		MBits = Integer.toString(Branch)+
				Integer.toString(MemRead) +
				Integer.toString(MemWrite);
		SBVal = regVal2;
		EX_Mux();
		
		ALUresult();

	}

//***Getters to READ from the EX MEM in the MEM stage
	public int getALUresult(){
		return ALUresult;
	}

	public String getMBits(){
		return MBits;
	}

	public String getWBBits() {
		return WBBits;
	}


	public int getWriteRegVal(){

		return writeRegVal;

	}

	public int getSBVal(){
		return SBVal;
	}


	/**
	 * @method - Mux
	 * @details - Writes the writeRegVal, to EXMEM
	 * and sets the second register value to be read 
	 * into the ALU control.
	 * Note:
	 * (1) If ALUSrc is 1 and RegDst is -1,
	 * write the "junk" value to EXMEM, and sets regVal2 to SEOffset.
	 * (2) Else if ALUSrc is 1 and RegDst is 0, 
	 * sets the regVal2( SEoffset) and writes writeReg2 in ExMEM.
	 * (3) Else, (if ALUSrc is 0 and RegDst is 1)
	 * read in regVal2 and write writeReg1 in ExMEM.
	 */
	public void EX_Mux(){
		if(ALUSrc == 1 && RegDst == -1){
			regVal2 = SEOffset;
			//if value is an "-1" it doesn't pass through to Mux
			writeRegVal = -1;		
		}else if (ALUSrc ==1 && RegDst == 0){
			regVal2 = SEOffset;
			writeRegVal = writeReg1;

		}else if (ALUSrc == 0 && RegDst == 1){
			writeRegVal = writeReg2;
		}else{//for NOPs
			writeRegVal = 0;
		}
	}


	/**
	 * @method - ALUresult
	 * @detail - calculates the ALU result 
	 * 
	 * 
	 */
	public void ALUresult(){
		String ALUOp = Integer.toString(ALUOp1) +
				Integer.toString(ALUOp0);
		if(ALUOp.matches("00*")){//lb sb
			ALUresult = regVal1 + regVal2; 
		}else if(ALUOp.matches("10*")){//R
			if(SEOffset == 0x20){//check for add
				ALUresult = regVal1 + regVal2; 
			}else{//perform subtraction
					ALUresult = regVal1 - regVal2;
			}
		

		}
	}

/**
 *@details- Prints out the EX Read
 * Note: An SEOffset of a x20, x22, or x0 is a "junk" value.
 */
 
	public void printEX_Read(){
		System.out.println("****ID/EX Read: ****");
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
		System.out.println("ReadReg2 Val: 0x" + Integer.toHexString(readRegVal2));
		System.out.println();
		System.out.println("SEOffset: 0x" + Integer.toHexString(SEOffset));
		System.out.println();
		System.out.println("WriteregVal1: " + writeReg1 +"(Instruc 20-16)");
		System.out.println("WriteregVal2: " + writeReg2 +"(Instruc 11-15)");



	}
/**
 * @details - Prints the contents written to the WRITE side
 * Note: writeRegNum at -1 is a "junk" value.
 */
	public void printEX_Write(){


		System.out.println("****EX/MEM Write: ****");
		System.out.println();
		System.out.println("M Control Bits: " + 
				MBits);
		System.out.println("WB Control Bits:" +
				WBBits);
		System.out.println();
		System.out.println("ALU Result: 0x" + Integer.toHexString(ALUresult));
		System.out.println("SBValue: 0x" + Integer.toHexString(SBVal));
		System.out.println();
		System.out.println("WriteregVal: " + writeRegVal);
	}




}
