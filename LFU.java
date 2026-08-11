class LFUCache {
    Map<Integer,Node> m;
    Map<Integer,DoubleLinkedList> fq;
    int capacity;
    int minfreq;
    public LFUCache(int capacity) {
        m=new HashMap<>();
        fq=new HashMap<>();
        this.capacity=capacity;
        minfreq=1;
    }
    
    public int get(int key) {
        if(!m.containsKey(key))
        {
            return -1;
        }

        //get the node from m hash
        Node temp=m.get(key);

        //get frequency of that node
        int oldfrq=temp.frequency;

        //get the doublelinkedlist using frequency from frequency hashmap
        DoubleLinkedList list=fq.get(oldfrq);

        //deletion of that Node from the list 
        temp.prev.next=temp.next;
        temp.next.prev=temp.prev;
        
        //removing the list from frequency hashmap if it is empty 
        if(list.head.next == list.tail) 
        {
            fq.remove(temp.frequency);
            //updating the minfrq 
            if(oldfrq== minfreq)
            {
                minfreq++;
            }
        }
        //temp key is accessed once so frequency is increases by one
        temp.frequency++;

        //creat empty doublelinkedlist if is not in the fq hashmap
        if(!fq.containsKey(temp.frequency))
        {
            fq.put(temp.frequency,new DoubleLinkedList());
        }
        //get that linkedlist using increased frequecy
        DoubleLinkedList d=fq.get(temp.frequency);
        //add the present node in the linkedlist
        d.addfirst(temp);
        return temp.value;
    }
    
    public void put(int key, int value) {
        //key already present
        if(m.containsKey(key))
        {
            //get the Node from m hashmap
            Node temp=m.get(key);

            //update the value of the Node
            temp.value=value;

            //get the linked list from the fq hashmap using frquency of temp Node
            DoubleLinkedList list=fq.get(temp.frequency);
            
            //delete that node from present list
            temp.prev.next=temp.next;
            temp.next.prev=temp.prev;

            //removing the list from frequency hashmap if it is empty
            if (list.head.next == list.tail) 
            {
                fq.remove(temp.frequency);
                //updating the minfrq 
                if (temp.frequency== minfreq) {
                    minfreq++;
                }
            }           
            temp.frequency++;

            //creat new doublelinkedlist if is not in the fq hashmap
            if(!fq.containsKey(temp.frequency))
            {
                fq.put(temp.frequency,new DoubleLinkedList());
            }
            //get that linkedlist using increased frequecy
            DoubleLinkedList d=fq.get(temp.frequency);
            d.addfirst(temp);
        }
        //add new one
        else
        {
            //if cache full removing the LFU
            if(m.size()==capacity)
            {
                //getting the list from fq hashmap using minfreq
               DoubleLinkedList  list=fq.get(minfreq);

               //removing the last Node from that list
               Node lru=list.tail.prev;
               //list.tail.prev=list.tail.prev.prev;
                lru.prev.next=lru.next;
                lru.next.prev=lru.prev;

                //removing the list from frequency hashmap if it is empty
                if (list.head.next == list.tail) 
                {
                    fq.remove(lru.frequency);
                    if (lru.frequency== minfreq) {
                        minfreq++;
                    }
                } 
                m.remove(lru.key);
            }

            //creat new doublelinkedlist if is not in the fq hashmap
            if(!fq.containsKey(1))
            {
                fq.put(1,new DoubleLinkedList());
            }
            //creating new node
            Node newnode=new Node(key,value,1);
            
            //getting list using the frequency 1
            DoubleLinkedList temp=fq.get(1);
            temp.addfirst(newnode);
            m.put(key,newnode);
            minfreq=1;
        }
    }
}
class DoubleLinkedList
{
    Node head;
    Node tail;
    DoubleLinkedList()
    {
        head=new Node(0,0,0);
        tail=new Node(0,0,0);
        head.next=tail;
        tail.prev=head;
    }
    public void addfirst(Node temp)
    {
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
    int frequency;
    Node prev;
    Node next;
    Node(int key,int value,int frequency)
    {
        this.key=key;
        this.value=value;
        this.frequency=frequency;
        prev=null;
        next=null;
    }
}
