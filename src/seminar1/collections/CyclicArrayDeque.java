package seminar1.collections;
import java.util.Iterator;
import java.util.ListIterator;

public class CyclicArrayDeque<Item> implements IDeque<Item> {

    private static final int DEFAULT_CAPACITY = 10;

    private Item[] elementData;
    private int size;
    private int tail, head;

    CyclicArrayDeque(){
        elementData = (Item[])new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void pushFront(Item item) {
        if(size == elementData.length)
            grow();
        if(head == elementData.length - 1)
            head = 0;
        elementData[head] = item;
        head++;
        size++;
    }

    @Override
    public void pushBack(Item item) {
        if(size == elementData.length)
            grow();
        if(tail == 0)
            tail = elementData.length - 1;
        elementData[tail] = item;
        tail--;
        size++;
    }

    @Override
    public Item popFront() {
        Item result = iterator().previous();
        if(size == 0){
            tail = head = 0;
        }
        if(elementData.length > DEFAULT_CAPACITY && size*4 <= elementData.length)
            shrink();
        return result;
    }

    @Override
    public Item popBack() {
        Item result = iterator().next();
        if(size == 0){
            tail = head = 0;
        }
        if(elementData.length > DEFAULT_CAPACITY && size*4 <= elementData.length)
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

    private void grow() {
        changeCapacity((int)(elementData.length * 1.5f));
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
    public ListIterator<Item> iterator() {
        return new CyclicArrayDequeIterator();
    }

    private class CyclicArrayDequeIterator implements ListIterator<Item> {

        @Override
        public boolean hasNext() {
            return size != 0;
        }

        public boolean hasPrevious() {
            return size != 0;
        }

        @Override
        public Item next() {
            if(hasNext()) {
                if(tail == elementData.length - 1)
                    tail = -1;
                Item item = elementData[tail+1];
                tail++;
                size--;
                return item;
            }
            return null;
        }

        public Item previous() {
            if(hasPrevious()) {
                if(head == 0)
                    head = elementData.length;
                Item item = elementData[head-1];
                head--;
                size--;
                return item;
            }
            return null;
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
}