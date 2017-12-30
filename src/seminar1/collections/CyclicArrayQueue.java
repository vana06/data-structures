package seminar1.collections;
import java.util.Arrays;
import java.util.Iterator;

public class CyclicArrayQueue<Item> implements IQueue<Item> {

    private static final int DEFAULT_CAPACITY = 10;

    private Item[] elementData;
    private int size;
    private int tail, head;

    public CyclicArrayQueue(){
        elementData = (Item[]) new Object[DEFAULT_CAPACITY];
        tail = 0;
        head = 0;
    }

    @Override
    public void enqueue(Item item) {
        if(size == elementData.length)
            grow();
        elementData[head%elementData.length] = item;
        head++;
        size++;
    }

    @Override
    public Item dequeue() {
        Item result = elementData[tail%elementData.length];
        if(result != null) {
            size--;
            tail++;
        }
        if(elementData.length > DEFAULT_CAPACITY && size*4 <= elementData.length)
            shrink();
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

    private void grow() {
        changeCapacity(elementData.length * 2);
    }

    private void shrink() {
        changeCapacity(elementData.length/2);
    }

    private void changeCapacity(int newCapacity) {
        Item[] temp = (Item[]) new Object[newCapacity];
        Iterator<Item> iterator = iterator();
        int i = 0;
        while (iterator.hasNext()){
            temp[i] = iterator.next();
            i++;
        }
        elementData = temp;
        tail = 0;
        head = size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new CyclicArrayQueueIterator();
    }

    private class CyclicArrayQueueIterator implements Iterator<Item> {

        int currentPosition = tail;

        @Override
        public boolean hasNext() {
            return currentPosition != head;
        }

        @Override
        public Item next() {
            Item item = elementData[currentPosition%elementData.length];
            currentPosition++;
            return item;
        }

    }

    public static void main(String[] args) {
        CyclicArrayQueue<Integer> queue = new CyclicArrayQueue<>();
        for (int i = 0; i < 1000; i++) queue.enqueue(i);
        for (int i = 0; i < 10; i++) System.out.print(queue.dequeue() + " ");
    }
}