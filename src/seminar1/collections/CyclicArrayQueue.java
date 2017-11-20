package seminar1.collections;
import java.util.Arrays;
import java.util.Iterator;

public class CyclicArrayQueue<Item> implements IQueue<Item> {

    private static final int DEFAULT_CAPACITY = 10;

    private Item[] elementData;
    private int size;
    private int tail, head;

    CyclicArrayQueue(){
        elementData = (Item[]) new Object[DEFAULT_CAPACITY];
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
        Item result = iterator().next();
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
        if(size == 0)
            return true;
        return false;
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
        head = size-1;
    }

    @Override
    public Iterator<Item> iterator() {
        return new CyclicArrayQueueIterator();
    }

    private class CyclicArrayQueueIterator implements Iterator<Item> {

        int currentPosition = tail;
        int currentSize = size;

        @Override
        public boolean hasNext() {
            return currentSize != 0;
        }

        @Override
        public Item next() {
            if(hasNext()) {
                Item item = elementData[currentPosition%elementData.length];
                currentPosition++;
                currentSize--;
                return item;
            }
            return null;
        }

    }
}