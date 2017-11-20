package seminar1;
import java.util.Iterator;

//Нужен для решения задач Solver
public class LinkedList<Item>  {

    private Node<Item> head;
    private int size;

    public void push(Item item) {
        if(size == 0){
            head = new Node<>(item, null);
            size++;
            return;
        }
        Node<Item> old = head;
        while (old.next != null)
            old = old.next;
        old.next = new Node<>(item, null);
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Item get(int i){
        if(size == 0)
            return null;
        Iterator current = iterator();
        Item result = (Item) current.next();
        while (i-- > 0)
            result = (Item) current.next();
        return result;
    }
    public void remove(int i){
        if(size == 0)
            return;
        if(i == 0){
            head = head.next;
        }
        Node<Item> old = head;
        Node<Item> prev = null;
        while (i-- > 0) {
            prev = old;
            old = old.next;
        }
        if(prev != null)
            prev.next = old.next;
        size--;
    }
    public void insert(Item item, int position){
        if(size == 0 || position == size) {
            push(item);
            return;
        }
        if(position >= size){
            return;
        }
        if(position == 0){
            Node<Item> newNode = new Node<>(item, head);
            head = newNode;
        }
        Node<Item> old = head;
        Node<Item> prev = null;
        while (--position >= 0) {
            prev = old;
            old = old.next;
        }
        Node<Item> newNode = new Node<>(item, old);
        if(prev != null)
            prev.next = newNode;
        size++;
    }
    String[] getArray(int begin, int end){
        String[] result = new String[end - begin - 1];

        Node<Item> current = head;
        for(int i = 1; i <= begin+1; i++){
            current = current.next;
        }

        for(int i = begin + 1; i < end; i++){
            result[i - (begin + 1)] = String.valueOf(current.item);
            current = current.next;
        }

        return result;
    }

    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {

        private Node<Item> curr = head;
        private int currentPos = 0;

        @Override
        public boolean hasNext() {
            if(currentPos != size)
                return true;
            return false;
        }

        @Override
        public Item next() {
            if(!hasNext())
                return null;
            Item result = curr.item;
            curr = curr.next;
            currentPos++;
            return result;
        }
    }

    private static class Node<Item> {
        Item item;
        Node<Item> next;

        public Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }
    }
}