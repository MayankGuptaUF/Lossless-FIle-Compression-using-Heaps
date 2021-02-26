

import java.util.HashMap;
import java.util.Map;




public class BinHeap {
	
	class Huffman_Node{
		String data;
		int freq;
		Huffman_Node left;
		Huffman_Node right;
		
		Huffman_Node(String d,int f, Huffman_Node l, Huffman_Node r)
		{
			this.data=d;
			this.freq=f;
			this.left=l;
			this.right=r;
		}
		// Only when we sum two nodes and reinsert them will we have a left and right.
		Huffman_Node (String d, int f) {
			this.data = d;
			this.freq = f;
			this.left = null;
			this.right = null;
		}
	}
	
	Huffman_Node[] heaparray;
	int heapsize=0;
	private static int d=2;
	Map<String,String> Codetable =new HashMap<>();
	
	private void heap_array(int set_size, Map<String,Integer> freq_table)

	{
		heaparray = new Huffman_Node[set_size]; // create an array heaparray of size same as the no of keys
		int pos = 0;
		for(Map.Entry<String,Integer> entry:freq_table.entrySet()) {
			heaparray[pos] = new Huffman_Node(entry.getKey(),entry.getValue());
			pos=pos+1;
			
		}
		
		
	}
	
	private void huffman_insertion()
	{	if(heapsize<=1)
		return;
	Huffman_Node l=heaparray[0];
	deleteMin();
	Huffman_Node r=heaparray[0];
	deleteMin();
	
	Huffman_Node SumNode=new Huffman_Node("sum of "+l.freq+" and "+ r.freq+" ",l.freq+r.freq,l,r);
	insert(SumNode,SumNode.freq);
	
	}
	
	
	
	private void char_encoding(Huffman_Node head,Huffman_Node left,Huffman_Node right,StringBuffer sb)
	{	// All the data is stored at the terminal nodes so they won't have left or right child.
		if(head==null)
			return;
		else {
			if(left==null && right==null)
			{	String huffcode=sb.toString();
				Codetable.put(head.data,huffcode);
				return;
			}
			// Else we recursively traverse the heap
			else {   int del=sb.length();
				 sb.append("0");
				 char_encoding(left,left.left,left.right,sb);
				 del=sb.length()-1;
				 sb.deleteCharAt(del);
				 sb.append("1");
			     	 char_encoding(right,right.left,right.right,sb);
			     	 del=sb.length()-1;
			     	 sb.deleteCharAt(del);
			}
			
	        }
	}
	
	
	private void insert(Huffman_Node SumNode,int freqsum)
	{
		heapsize=heapsize+1;
				
		int i=heapsize-1;
		int p=(i-1)/2;
		// In Insert we move bottom up to the parent till we are able to break out.
		while(i>=1 && heaparray[p].freq > freqsum)
		{
			heaparray[i]=heaparray[p];
			i=p;
			p=(i-1)/2;
		}
		heaparray[i]=SumNode;
	}
	private int find_smallest(int p)
	{ // By making children a loop, we have generalised it so that any d-ary heap can be used.
		Map<Integer, Integer> children = new HashMap<>();
		for(int i=0;i<d;i++)
			children.put(i,(d*(p))+1+i);
		int least=p;
		for(int i=0;i<d;i++)
			if(children.get(i)<heapsize &&  heaparray[children.get(i)].freq < heaparray[least].freq )
				least=children.get(i);
		return least;
			
	}
	
	private void minHeapify(int p)
	{	//min heapify compares the children and if there is replacement minHeapify travels with the node till termination
		int smallest=find_smallest(p);
		if(p==smallest)
			{
			return;
			}
		if(p!=smallest)
			{
			Huffman_Node switchp=heaparray[smallest];
			heaparray[smallest]=heaparray[p];
			heaparray[p]=switchp;
			minHeapify(smallest);
			}
	}
	
	
	private void deleteMin()
	{
		heaparray[0]=heaparray[heapsize-1]; // Delete min element and Replace the min element with the furthest right node
		heaparray[heapsize-1]=null;// Since our heap exists from left to right, farthest right element is the last element of this heaparray.
		heapsize=heapsize-1; // Heap size is decreased by 1 as we lose an element
		minHeapify(0); // Heapify to balance heap properties.
		
	}
	
	public Map<String,String> buildHuffTree(Map<String,Integer> freq_table)
	{	// First we create the heap array
		int set_size=freq_table.size();
		heapsize=set_size;
		heap_array(set_size,freq_table);
		// Then we minheapify 
		int start =heapsize/2-1;
		for(int i=start;i>=0;i--)
		{
			minHeapify(i);
			
		}
		// Now we remove min nodes, sum them up and reinsert them into the tree and continue this process till all heap elements are at terminal nodes.
		while(heapsize>1)	
			huffman_insertion();
		
		StringBuffer sb=new StringBuffer("");
		Huffman_Node root=heaparray[0];
		char_encoding(root,root.left,root.right,sb);
			
		return Codetable;
	
	}
	
}

