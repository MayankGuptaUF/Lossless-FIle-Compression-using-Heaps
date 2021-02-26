
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class PairingHeap {
	class Huffman_Node
	{
		String data;
		int freq;
		Huffman_Node left;
		Huffman_Node right;
		Huffman_Node(String d,int f)
		{
			this.data=d;
			this.freq=f;
			this.left=null;
			this.right=null;
		}
		Huffman_Node(String d,int f, Huffman_Node l, Huffman_Node r)
		{
			this.data=d;
			this.freq=f;
			this.left=l;
			this.right=r;
		}
		
	}
	class HeapNode // Class heapNode maintains a the pointer for left and right sibling and also holds the node which has data.
	{
		Huffman_Node node;
		HeapNode child;
		HeapNode leftSib;
		HeapNode rightSib;
		HeapNode(Huffman_Node n)
		{
			this.node=n;
			this.child=null;
			this.leftSib=null;
			this.rightSib=null;
			
		}
		HeapNode(Huffman_Node n,HeapNode c,HeapNode l,HeapNode r)
		{
			this.node=n;
			this.child=c;
			this.leftSib=l;
			this.rightSib=r;
			
		}
		
	}
	//We create a root which is of type HeapNode and is initially null
	HeapNode root=null;
	
	Map<String,String> Codetable=new HashMap<>();
	
	private void insert_array(Map<String,Integer> freq_table)
	
	{
		for(Map.Entry<String,Integer> entry:freq_table.entrySet())
		{	// We first create a Huffman Node as in case of Bin Heap for each character
			Huffman_Node temp=new Huffman_Node(entry.getKey(),entry.getValue());
			// We then insert this into the Heap which has root==null initially
			insert(temp);
			// Insert will also create a new HeapNode
		}
		
	}
	private void huffman_insertion() // functionally same as other heaps, makes use of deleteMin which is different for a pairing heap.
	{
		Huffman_Node l=root.node;
		deleteMin();
		Huffman_Node r=root.node;
		deleteMin();
		
		Huffman_Node SumNode=new Huffman_Node("sum of "+l.freq+" and "+ r.freq+" ",l.freq+r.freq,l,r);
		
		insert(SumNode);
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
	
	private void deleteMin()
	{	// Here we have an arraylist a which behaves like a fifo queue
		HeapNode addtorootlist=root.child;
		if(addtorootlist!=null)
			{
				ArrayList<HeapNode> a= new ArrayList<>();
				while(addtorootlist!=null)
			
					{
						// When deleteMin is performed we add all children to root list
						a.add(addtorootlist);
			
						addtorootlist=addtorootlist.rightSib;
					}
			
				// Next since RemoveMin is performed we need to meld them till we have a single root.
				while(a.size()!=0)
					{
						if(a.size()>1)
							{
								HeapNode first=a.remove(0);
								HeapNode second=a.remove(0);
								HeapNode melded=meld(first,second);
								a.add(melded);
					
							}
						else
							{	
								HeapNode first=a.remove(0);
								root=first;
				
							}
				
					}
			
			}
		else
			root=null;

	}
	
	
	private void insert(Huffman_Node temp)
	{	
		HeapNode tempheap=new HeapNode(temp);
		// Root is only going to be null for the first insert and last removemin when all elements are removed.
		if(root!=null)
			root=meld(root,tempheap);	
		else
		{	
			root=tempheap;		
		}
	}
	private HeapNode firstchildtosecond(HeapNode child, HeapNode parent) {
		/*System.out.println("one"+ first.node.data +" "+ first.node.freq);
		System.out.println(second.node.data +" "+second.node.freq);
		if(first.child!=null && first.child.child!=null)
			System.out.println("zero"+first.child.node.data+first.child.child.node.data);
		*/
		child.leftSib=parent; // First's Parent is now second i.e. it is second's Leftmost child.
		child.rightSib=parent.child; // First's right sibling is second's left child
		parent.child=child;
		parent.rightSib=null;
		parent.leftSib=null;
		if(child.rightSib==null)
			return parent;
		else
			child.rightSib.leftSib=child; // Setting First as a leftSib of second's original left child.
			return parent;
	}
	
	private HeapNode meld(HeapNode first,HeapNode second)
	{
		
		if(second==null)
            return first;
		int freq1=first.node.freq;
		int freq2=second.node.freq;
		if(freq2>freq1)
			{	
			return firstchildtosecond(second,first);
			
			}
		if(freq1>freq2)
			{
			
			return firstchildtosecond(first,second);
			
			}
		
		// If both have same frequency,we just retain the first one 
		// as a parent and make the second node the child.
		// We can make the first a child to second, but then the 
		// Huffman tree generated will be different though that is also right.
		
		return firstchildtosecond(second,first);
			
			
	}
	public Map<String,String> buildHuffTree(Map<String,Integer> freq_table) // functionally same as other heaps
	{   
		insert_array(freq_table);
		
		
		while(root.child!=null)
			{
				huffman_insertion();
			}
		
		StringBuffer sb=new StringBuffer("");
		Huffman_Node root_node=root.node;
		char_encoding(root_node,root_node.left,root_node.right,sb);
		
		return Codetable;
	}
}
