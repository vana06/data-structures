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
        Item result = head.item;
        head = head.previous;
        return result;
    }

    @Override
    public Item popBack() {
        Item result = tail.item;
        tail = tail.next;
        return result;
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
    public IListIterator<Item> iterator() {
        return new LinkedDequeIterator();
    }

    private class LinkedDequeIterator implements IListIterator<Item> {

        Node<Item> currHead = head, currTail = tail;

        @Override
        public boolean hasNext() {
            return (currTail != null);
        }

        @Override
        public boolean hasPrevious() {
            return (currHead != null);
        }

        @Override
        public Item next() {
            Item result = currTail.item;
            currTail = currTail.next;
            return result;
        }

        @Override
        public Item previous() {
            Item result = currHead.item;
            currHead = currHead.previous;
            return result;
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

    public static void main(String[] args) {
        LinkedDeque<Integer> deque = new LinkedDeque<>();
        deque.pushBack(1);
        deque.pushBack(2);
        deque.pushBack(3);
        IListIterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}