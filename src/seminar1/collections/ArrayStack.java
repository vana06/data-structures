package seminar1.collections;
import java.util.Arrays;
import java.util.Iterator;

public class ArrayStack<Item> implements IStack<Item> {

    private static final int DEFAULT_CAPACITY = 10;

    private Item[] elementData;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.elementData = (Item[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void push(Item item) {
        if(size == elementData.length)
            grow();
        elementData[size++] = item;
    }

    @Override
    public Item pop() {
        Item result = iterator().next();
        size--;
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
        changeCapacity(elementData.length/4);
    }

    private void changeCapacity(int newCapacity) {
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<Item> {

        private int currentPosition = size;

        @Override
        public boolean hasNext() {
            return currentPosition != 0;
        }

        @Override
        public Item next() {
            if(hasNext())
                return elementData[--currentPosition];
            return null;
        }

    }

}