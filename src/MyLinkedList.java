
public class MyLinkedList implements ListInterface {


	protected class Node{
		Object item;
		Node next;
		Node(Object i){
			item = i;
			next = null;
		}
	}
	protected Node head;
	protected int size;
	
	public boolean isEmpty() {
		return(head == null);
		
	}
	public void add(int index, Object value) {
	  if(index == 0) {
		if(head == null) {
			Node n = new Node(value);
			head = n;
			size++;
		  }
			else {
			Node n = new Node(value);
			n.next = head;
			head = n;
			size ++;
		   }
	  }
		else {
			Node n = new Node(value);
			Node x = head;
			int i = 0;
			if(x == null) {
				n.next = null;
			
			}
			else {
				while(i!= index && x.next!=null) {
					 x = x.next;
					 i++;				
				}
			}
			if(x == null) {
			n.next = null;
							
			}else {
			n.next = x.next;  //sets inserted node next to next value
			x.next = n;   //sets previous link to point at n (x.next is the same link as the other node)
			size ++;	
			
			}
		}
			
	} 
	
	
	
	
	
	public int size() {
		return size;
	}
	
	
	
	
	public void remove(int index) {
	Node n = head;
	if(index == 0 && n.next!= null) {
		head = n.next;
		size--;
		return;
	}
	for(int i = 0; i <index-1; i++) {
		n = n.next;
	}//n = index before one seated for removal
	if(n.next == null) {
		head = n.next;
		size--;
		return;
	}

	if(n.next.next == null) {
		n.next = null;
		size--;
		return;
	} else {
		Node rm = n.next.next;
		n.next = rm;
		size--;
		
	}

	}
	
	public void removeAll() {
		head = null;
		size = 0;
	}
	
	public Object get(int index) {
		Node n = head;
		for(int i=0; i<index;i++) {
			 n = n.next;
		}
		return n.item;
	}
	
	public int find(Object o) {
		Node n = head;
		if(n == null) {
			return -1;
		}
		int i = 0;
		while(n!=null) {
			if(n.item.equals(o)) {
				return i;
			}
			if(n.next == null) {
				break; 
			}else {
			n = n.next;
			}
			i++;
		}
		if(n.item != o) {
			return -1;
		}
		return i;
	}


	public String toString() {
		Node n = head;
		String list = "";
		if(n!=null) {
			list = (String)n.item;
			n = n.next;
			if(n!=null) {
				list = list.concat(", ");
			}
		}
		if(n == null) {
			return list;
		}
		while(n.next!=null) {
			list = list.concat((String) n.item);
			if(n.next != null) {
			list = list.concat(", ");
			n = n.next;
			}
		}
		list = list.concat((String) n.item);
	
		return list;
	}
}
