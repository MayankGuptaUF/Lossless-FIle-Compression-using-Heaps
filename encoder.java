

import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class encoder {
	
	public static Map<String, Integer> readLines(File f) throws IOException{
		FileReader fr =new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		Map<String,Integer> create = new HashMap<>();
		String line;
		while((line=br.readLine())!=null) {
			
			if(line.equals(""))
				continue;
			else if(create.containsKey(line))
				{
				int temp = create.get(line);
				create.put(line,temp+1);
				}
			else
				{
				create.put(line,1);
				}

		}
		br.close();
		fr.close();
		return create;
	}
	
	public static String createString(File f) throws IOException {
		FileReader fr =new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String ans = "";
		String line;
		while((line=br.readLine())!=null) {
			
			if(line.equals(""))
				continue;
			else
				ans = ans + "-" + line;

		}
		br.close();
		fr.close();
		return ans.substring(1);
	}
	
	public static String encode(String s,Map<String,String> codeTable) {
		String[] arr = s.split("-");
		String ret = "";
		for(int i = 0;i < arr.length;i++) {
			ret = ret + codeTable.get(arr[i]);
		}
		return ret;
	}
	
	public static void createFile(String s) {
		File file = new File("encoded.bin");
		BufferedWriter bf = null;
		try{
            
            //create new BufferedWriter for the output file
            bf = new BufferedWriter( new FileWriter(file) );
 
            //iterate map entries
            bf.write(s);
            
            bf.flush();
 
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            
            try{
                //always close the writer
                bf.close();
            }catch(Exception e){}
        }
	}
	
	public static void mapping(Map<String,String> codeTable)
	{
		File file = new File("code_table.txt");
		BufferedWriter bf = null;
        
        try{
            
            //create new BufferedWriter for the output file
            bf = new BufferedWriter( new FileWriter(file) );
 
            //iterate map entries
            for(Map.Entry<String, String> entry : codeTable.entrySet()){
                
                //Space to separate the character and code
                bf.write( entry.getKey() + " " + entry.getValue() );
                
                //new line
                bf.newLine();
            }
            
            bf.flush();
 
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            
            try{
                //always close the writer
                bf.close();
            }catch(Exception e){}
        }
    }

	/*
	 * public static void gen_bin(String path,Map<String,String> codeTable) {
	 * BufferedReader in; try { in = new BufferedReader(new FileReader(path));
	 * StringBuilder sb = new StringBuilder(); while(in.readLine()!= null) {
	 * sb.append(in.readLine()).append("\n");
	 * 
	 * } }catch (FileNotFoundException e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); }
	 * 
	 * 
	 * try { in.close(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); }
	 * 
	 * }
	 */
	public static void main(String[] args) {
		File f= new File(args[0]);
		Map<String,Integer> freq_table = new HashMap<>();
		try {
			
			freq_table=readLines(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		PairingHeap pair=new PairingHeap();
		Map<String,String> codeTable=pair.buildHuffTree(freq_table);
		
		mapping(codeTable);
		
//		gen_bin(path,codeTable);
		String t = "";
		try {
			t = createString(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String t2 = encode(t,codeTable);
		createFile(t2);
	}
}
