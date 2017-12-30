package seminar1.collections;
import java.util.Iterator;

public class LinkedQueue<Item> implements IQueue<Item> {

    // -> [tail -> .. -> .. -> head] ->
    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    @Override
    public void enqueue(Item item) {
        if(size == 0){
            tail = head = new Node<Item>(item, null);
            size++;
            return;
        }
        Node<Item> newNode = new Node<>(item, null);
        head.next = newNode;
        head = newNode;
        size++;
    }

    @Override
    public Item dequeue() {
        if(size == 0) {
            return null;
        }
        Item result = tail.item;
        tail = tail.next;
        size--;
        return result;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedQueueIterator();
    }

    private class LinkedQueueIterator implements Iterator<Item> {

        Node<Item> curr = tail;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public Item next() {
            Item item = curr.item;
            curr = curr.next;
            return item;
        }

    }

    private static class Node<Item> {
        Item item;
        Node<Item> next;

        public Node(Item item) {
            this.item = item;
        }

        public Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        for (int i = 0; i < 1000; i++) queue.enqueue(i);
        for (int i = 0; i < 10; i++) System.out.print(queue.dequeue() + " ");
    }
}