

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HuffmanPerformance {
	
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
	public static void main(String[] args) {
		File f= new File("sample_input_large.txt");
		Map<String,Integer> freq_table = new HashMap<>();
		try {
			freq_table=readLines(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Analysis(freq_table);
		
	}
	public static void Analysis(Map<String,Integer> freq_table)
	{
		Map<String,String> codeTablebin = new HashMap<>();
		long startTimebin = System.nanoTime();
		for(int i=0;i<10;i++)
			codeTablebin=build_using_BinHeap(freq_table);
		long endTimebin  = System.nanoTime();
		Map<String,String> codeTablefour = new HashMap<>();
		long startTimefour = System.nanoTime();
		for(int i=0;i<10;i++)
			codeTablefour=build_using_FourHeap(freq_table);
		long endTimefour   = System.nanoTime();
		Map<String,String> codeTablepair = new HashMap<>();
		long startTimepair = System.nanoTime();
		for(int i=0;i<10;i++)
			codeTablepair=build_using_PairingHeap(freq_table);
		long endTimepair   = System.nanoTime();
		
		long totalTimebin = endTimebin - startTimebin;
		long totalTimefour = endTimefour - startTimefour;
		long totalTimepair = endTimepair - startTimepair;
		
		System.out.println("Time using Binary Heap (microseconds):"+ totalTimebin/1000);
		System.out.println("Time using 4-way Heap (microseconds):"+ totalTimefour/1000);
		System.out.println("Time using Pairing Heap (microseconds):"+ totalTimepair/1000);
		//System.out.println(codeTable);
	}
	public static Map<String, String> build_using_BinHeap(Map<String,Integer> freq_table)
	{
		BinHeap ph=new BinHeap();
		Map<String,String> codeTable=ph.buildHuffTree(freq_table);
		return codeTable;
	}
	
	public static Map<String, String> build_using_FourHeap(Map<String,Integer> freq_table)
	{
		FourwayHeap ph=new FourwayHeap();
		Map<String,String> codeTable=ph.buildHuffTree(freq_table);
		return codeTable;
	}
	
	public static Map<String, String> build_using_PairingHeap(Map<String,Integer> freq_table)
	{
		FourwayHeap ph=new FourwayHeap();
		Map<String,String> codeTable=ph.buildHuffTree(freq_table);
		return codeTable;
	}
	
}

