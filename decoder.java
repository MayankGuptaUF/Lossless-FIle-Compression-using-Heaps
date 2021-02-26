

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;


class BinTreeNode{
	String data;
	BinTreeNode left;
	BinTreeNode right;
	
	BinTreeNode(String data)
	{
		this.data=data;
		this.left=null;
		this.right=null;
	}
}
public class decoder {

	static BinTreeNode root=null;
	
	public static Map<String,String> getCodeTable(String codetable) throws FileNotFoundException {
		File f = new File(codetable);
		FileReader fr =new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		Map<String,String> map = new HashMap<>();
		
		String line;
		try {
			while((line=br.readLine())!=null) {
				
				if(line.equals(""))
					continue;
				else {
					String[] arr = line.split(" ");
//					Sytem.out.println()
					map.put(arr[0],arr[1]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			fr.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	private void output(BinTreeNode root, String bin) {

        ArrayList<String> result = new ArrayList<String>();


        BinTreeNode curr = root;
        for(int i=0; i<bin.length(); i++){
            

            if(bin.charAt(i) == '0'){
                curr = curr.left;
            }
            else{
                curr = curr.right;
            }
            if(curr.left==null && curr.right==null){
                result.add(curr.data);
                curr = root;
            }
        }
        File file = new File("decoded.txt");
		BufferedWriter bf = null;
        
        try{
        	bf = new BufferedWriter( new FileWriter(file) );            
            for(int i=0; i<result.size(); i++){
            	if(i==result.size()-1)
            	{
            		bf.write(result.get(i));
            	}
            		
            	else
                {
            		bf.write(result.get(i)+"\n");
            		}
            	}
            bf.flush();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

	public static String readEncode(String encoded) throws FileNotFoundException{
		File f = new File(encoded);
		FileReader fr =new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String ans = "";
		String line;
		try {
			while((line=br.readLine())!=null) {
				
				if(line.equals(""))
					continue;
				else {
					ans = ans + line;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			fr.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ans;
	}
	
	public static BinTreeNode huffmantree(String value, String code, BinTreeNode head)
	{	
		if(code.length()==0)
		{	
			head.data=value;
			
			return head;
		}
		if(head==null)
		{
			head=new BinTreeNode("InnerNode");	
		}
		
		if(code.charAt(0)=='0')
		{	
			if(head.left==null)
				{
				head.left= new BinTreeNode("InnerNode");
				}
			huffmantree(value,code.substring(1, code.length()),head.left);
		}
		if(code.charAt(0)=='1')
			{ 
			
			if(head.right==null)
			{
				head.right= new BinTreeNode("InnerNode");
			}
			
			huffmantree(value,code.substring(1, code.length()),head.right);
			}
		return head;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		

		decoder dec=new decoder();
		
		Map<String,String> codeTable = getCodeTable(args[1]);
		String str=readEncode(args[0]);	
			
		for(Map.Entry<String,String> entry:codeTable.entrySet()) {
			root=huffmantree(entry.getKey(),entry.getValue(),root);
			}
			
		dec.output(root,str);
		
		
		
	}

}
