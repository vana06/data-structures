package seminar1.iterators.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class ArrayPriorityQueue<Key extends Comparable<Key>> implements IPriorityQueue<Key> {

    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    private Key[] elementData;
    private Comparator<Key> comparator;
    private int size = 0;

    public ArrayPriorityQueue() {
        /* TODO: implement it — O(n) ???*/
        elementData = (Key[]) new Comparable[DEFAULT_INITIAL_CAPACITY];
    }

    public ArrayPriorityQueue(int initialSize) {
        /* TODO: implement it — O(n) ???*/
        elementData = (Key[]) new Comparable[initialSize];
    }

    public ArrayPriorityQueue(Comparator<Key> comparator) {
        /* TODO: implement it — O(n) ???*/
        elementData = (Key[]) new Comparable[DEFAULT_INITIAL_CAPACITY];
        this.comparator = comparator;
    }

    @Override
    public void add(Key key) {
        if (size == elementData.length)
            grow();
        if (size == 0)
            elementData[0] = key;
        else {
            elementData[size] = key;
            siftUp(size);
        }
        size++;
    }

    @Override
    public Key peek() {
        return (size == 0) ? null : elementData[0];
    }

    @Override
    public Key extractMin() {
        Key result = iterator().next();
        if(elementData.length > DEFAULT_INITIAL_CAPACITY && size*4 <= elementData.length)
            shrink();
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

    private void siftUp(int index) {
        if (index == 0) {
            return;
        }

        int parent;
        if (index % 2 == 0) {
            parent = (index - 2) / 2;
        } else {
            parent = (index - 1) / 2;
        }

        if (greater(parent,index)) {
            Key temp = elementData[index];
            elementData[index] = elementData[parent];
            elementData[parent] = temp;
            siftUp(parent);
        }
    }

    private void siftDown(int index) {
        if(2 * index + 1 > size)
            return;

        int left = 2 * index + 1;
        int right = left + 1;

        if(right < size && !greater(right, left)) {
            left = right;
        }

        if(greater(index, left)){
            Key temp = elementData[index];
            elementData[index] = elementData[left];
            elementData[left] = temp;
            siftDown(left);
        }
    }

    private void grow() {
        /* Если массив заполнился,
         * то увеличить его размер в полтора раз
         */
        elementData = Arrays.copyOf(elementData, size*2);
    }

    private void shrink() {
        /* Если количество элементов в четыре раза меньше,
         * то уменьшить его размер в два раза
         */
        elementData = Arrays.copyOf(elementData, size/2);
    }

    private boolean greater(int i, int j) {
        return comparator == null
                ? elementData[i].compareTo(elementData[j]) > 0
                : comparator.compare(elementData[i], elementData[j]) > 0;
    }

    @Override
    public Iterator<Key> iterator() {
        return new ArrayPriorityQueueIterator();
    }

    private class ArrayPriorityQueueIterator implements Iterator<Key>{

        @Override
        public boolean hasNext() {
            return size != 0;
        }

        @Override
        public Key next() {
            Key result = elementData[0];
            size--;
            elementData[0] = elementData[size];
            siftDown(0);
            return result;
        }
    }
}