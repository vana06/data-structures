package seminar1.collections;
import java.util.Iterator;

public class LinkedStack<Item> implements IStack<Item> {

    private Node<Item> head;
    private int size;

    @Override
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

    @Override
    public Item pop() {
        if(size == 0)
            return null;
        Item result = iterator().next();
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
        return new LinkedStackIterator();
    }

    private class LinkedStackIterator implements Iterator<Item> {

        private Node<Item> curr = head;
        private int currentPos = size;

        @Override
        public boolean hasNext() {
            if(currentPos != 0)
                return true;
            return false;
        }

        @Override
        public Item next() {
            if(!hasNext())
                return null;
            if(currentPos == 1)
                return curr.item;
            while(--currentPos > 0){
                curr = curr.next;
            }
            return curr.item;
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