class LRUCache {
    Map<Integer,Node> m;
    Node head;
    Node tail;
    int capacity;
    public LRUCache(int capacity) {
        this.capacity=capacity;
        m=new HashMap<>();

        head=new Node(0,0);
        tail=new Node(0,0);

        head.next=tail;
        tail.prev=head;
    }
    
    public int get(int key) {
        if(!m.containsKey(key))
        {
            return -1;
        }
        Node temp=m.get(key);

        temp.prev.next=temp.next;
        temp.next.prev=temp.prev;

        temp.prev=head;
        temp.next=head.next;
        head.next.prev=temp;
        head.next=temp;
        return temp.value;

    }
    
    public void put(int key, int value) {
        Node temp=null;
        if(!m.containsKey(key))
        {
            if(m.size()==capacity)
            {
                m.remove(tail.prev.key);
                tail.prev=tail.prev.prev;
                tail.prev.next=tail;
            }
            temp=new Node(key,value);
            m.put(key,temp);
        }
        else
        {
            temp=m.get(key);
            temp.value=value;

            temp.prev.next=temp.next;
            temp.next.prev=temp.prev;
        }
        temp.prev=head;
        temp.next=head.next;    
        head.next.prev=temp;
        head.next=temp;
    }
}
class Node
{
    int key;
    int value;
    Node prev;
    Node next;
    Node(int key,int value)
    {
        this.key=key;
        this.value=value;
    }
}
