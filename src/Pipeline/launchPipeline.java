package Pipeline;

public class launchPipeline extends Read_WB{

	/**
	 * This class launches the pipeline program
	 * @param args
	 * 				READ THIS:
	 * Note: In my output I've left in what the junk values should
	 * be (e.g. - SEOffset), however I have included indicators 
	 * for some of them (either a -1 or 0). See comments throughout
	 * my program for more information. 
	 * 
	 */
	
	private int ccNum = 0;
	public static void main(String []args){
		
	launchPipeline data = new launchPipeline();
	//Initializes the instuc in the ID Stage
	//to a "NOP" before starting clock cycle
	data.Read_ID(0x00000000);
	data.runPipeline();
	
	}
	
	/**
	 * @method - runPipeline
	 * @details - Starts the program
	 * Note: ccNum is used as an index to fetch each 
	 * instrucion in instrucCache array
	 */
	public void runPipeline(){
		while(ccNum <=11){
			setInstrucIndex(ccNum);
			IF_stage();
			ID_Stage();
			EX_Stage();
			MEM_Stage();
			WB_Stage();
			Print_Out_Everything();
			Copy_write_to_read();
			
			ccNum++;
		}
		
	}
	
	
	
	/**
	 * @details - Prints out the data currently in the "READ"
	 * and "WRITE" versions of each  pipeline registers, as well as
	 * the register values.
	 */
	public void Print_Out_Everything(){
		int cycle_num = 1+ccNum;
		System.out.println();
		System.out.println("*****************************************************");		
		System.out.println("                      Clock Cycle " + cycle_num );
		System.out.println("*****************************************************");  	
		//IF_ID Stage output
		printInstruct();
		//ID_EX stage output
		printID_Read();
		printID_Write();
		//EX_MEM Stage output
		printEX_Read();
		printEX_Write();
		//MEM_WB Stage output
		printMEM_Read();
		printMEM_Write();
		//WB Stage output
		printWB_Read();
		print_Write_to_Reg();
		//Prints out the register values 
		printRegs();
	}
	
	/**
	 * @details - Copies the write pipeline registers
	 * to the read side
	 * Note: 
	 * (1) The "Copy" methods, from each stage
	 * have the variables that will be read in 
	 * the "write" methods.
	 * (2) Method "getSavedInstruc" fetches the next
	 * instruction from instrucCache array in IF_ID class.
	 */
	
	public void Copy_write_to_read(){
		Read_ID(getSavedInstruc());
		Copy_EXWrite();
		Copy_MEMWrite();
		Copy_WBWrite();
				
	}
}
