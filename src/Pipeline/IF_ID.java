package Pipeline;

public class IF_ID {
/**
 * This class fetches an instruction 
 * from the array and writes it to the IFID register
 * (see Write_ID)
 * 
 */
	protected int []Main_Mem= new int[1024];
	protected int []Regs = new int[32];
	protected int []instructCache = new int [12];
	protected int NUM_OF_CYCLES = 0;
	private int savedInstruc = 0x00000000;;

//intializes the values in main mem and registers
	public IF_ID(){
		
		int val =0x0;
		for(int i = 0;i<Main_Mem.length;i++){		
			switch(i){
			case 0x100:
			case 0x200:
			case 0x300:
			case 0x400:
			case 0x500:
			case 0x600:
			case 0x700:
				val = 0x0;
				break;
			default:
				break;
			}

			Main_Mem[i] = val;
			val++;

		}
		val = 0x100;
		for(int i = 0;i<Regs.length;i++){
			if(i==0){
				Regs[i]=0x0;
			}else{
				Regs[i] = val;
			}
				 val++;

		}

	}
	
	/**
	 * @details - Sets the current index
	 * to be later used in the instrucCache array
	 * @param num
	 */
	public void setInstrucIndex(int num){
		NUM_OF_CYCLES = num;

	}
	
	/**
	 * @details - Sets the instruc array
	 * and writes the current instruc to IFID 
	 */
	public void IF_stage(){
		
		setInstruct();
		write_ID();
	}
	
	
	/**
	 * @details - Writes to the IFID
	 * 
	 */
	public void write_ID(){
			savedInstruc = instructCache[NUM_OF_CYCLES];

	}
	

	
/**
 * @method - 
 * @detail - Sets the instructions
 */
	public void setInstruct(){
		instructCache[0] = 0xa1020000;
		instructCache[1] = 0x810AFFFC;
		instructCache[2] = 0x00831820;
		instructCache[3] = 0x01263820;
		instructCache[4] = 0x01224820;
		instructCache[5] = 0x81180000;
		instructCache[6] = 0x81510010;
		instructCache[7] = 0x00624022;
		instructCache[8] = 0x00000000;
		instructCache[9] = 0x00000000;
		instructCache[10] = 0x00000000;
		instructCache[11] = 0x00000000;

	}

//The getters and setters used to READ from the IFID
	//and WRITE to the IDIF register
	public int getSavedInstruc() {
		return savedInstruc;
	}

	public void setSavedInstruc(int savedInstruc) {
		this.savedInstruc = savedInstruc;
	}
	
/**
 * @method - printRegs
 * @details - Prints the register values 
 * 
 */
	public void printRegs(){
		System.out.println("Register Values (Before copying write to read) ");
		for(int index=0; index<Regs.length;index++){
			System.out.println("Reg $" + index +
					" = 0x" +Integer.toHexString(Regs[index]));
		}
	}
	
	/**
	 * @method -
	 * @details - Prints the current instruction
	 * from the cache instruction 
	 */
	
	public void printInstruct(){
		System.out.println();
		System.out.println("****IF/ID Write: ****");
		System.out.println("Instruction: 0x" 
	+ Integer.toHexString(savedInstruc));
	}
	
}
