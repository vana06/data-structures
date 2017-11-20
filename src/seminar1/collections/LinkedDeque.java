package seminar1.collections;
import java.util.Iterator;
import java.util.ListIterator;

public class LinkedDeque<Item> implements IDeque<Item> {

    private Node<Item> head, tail;
    private int size = 0;

    @Override
    public void pushFront(Item item) {
        if(size == 0){
            head = tail = new Node<>(item);
            size++;
            return;
        }
        Node<Item> newNode = new Node<>(item, null, head);
        head.next = newNode;
        head = newNode;
        size++;
    }

    @Override
    public void pushBack(Item item) {
        if(size == 0){
            head = tail = new Node<>(item);
            size++;
            return;
        }
        Node<Item> newNode = new Node<>(item, tail, null);
        tail.previous = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public Item popFront() {
        ListIterator<Item> iterator = iterator();
        return iterator.previous();
    }

    @Override
    public Item popBack() {
        ListIterator<Item> iterator = iterator();
        return iterator.next();
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ListIterator<Item> iterator() {
        return new LinkedDequeIterator();
    }

    private class LinkedDequeIterator implements ListIterator<Item> {

        Node<Item> currHead = head, currTail = tail;

        @Override
        public boolean hasNext() {
            return (currTail != null);
        }

        public boolean hasPrevious() {
            return (currHead != null);
        }

        @Override
        public Item next() {
            if(!hasPrevious())
                return null;
            Item result = tail.item;
            tail = tail.next;
            return result;
        }

        public Item previous() {
            if(!hasNext())
                return null;
            Item result = head.item;
            head = head.previous;
            return result;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(Item item) {

        }

        @Override
        public void add(Item item) {

        }
    }

    private static class Node<Item>{
        Item item;
        Node<Item> next, previous;

        public Node(Item item) {
            this.item = item;
        }

        public Node(Item item, Node<Item> next, Node<Item> previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }
}