public class MySortedLinkedList extends MyLinkedList {

	/*  
	   defines the method
	   public void add(Comparable c)
	   given a Comparable, adds it to the 
	   list in sorted order.
	*/
	public void add(Comparable c) {
		Node n = new Node(c);
		if(head == null) {
			head = n;
			size++;
		}
		else {
			Node compare = head;
			if((((Comparable) compare.item).compareTo(c))>0) {
				head = n;
				n.next = compare;
				size++;
			}
			else {
				while(compare.next!= null) {
					if((((Comparable) compare.next.item).compareTo(c))>0) {
						n.next = compare.next;
						compare.next = n;
						size++;
						break;
					}
					compare = compare.next;
				}
				compare.next = n;
				size++;
			}
		}
	}
	public int compareTo(Node n, Object o) {
		String object = (String) o;
		if(((String)n.item).compareTo(object)<0) {
			
		}
		
		
		return 0;
	}
	

	  /* 
	   override the method
	   void add(int index, Object o)
	   so that it throws an UnsupportedOperationException with the message "Do not call add(int, Object) on MySortedLinkedList".
	   Directly adding objects at an index would mess up the sorted order.
	*/
	public void add(int index, Object o) throws UnsupportedOperationException  {
		throw new UnsupportedOperationException("Do not call add(int, Object) on MySortedLinkedList");
	}

}