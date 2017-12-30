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
        tail = -1;
        head = 0;
    }

    @Override
    public void pushFront(Item item) {
        if(size == elementData.length)
            grow();
        if(head == elementData.length)
            head = 0;
        elementData[head++] = item;
        size++;
    }

    @Override
    public void pushBack(Item item) {
        if(size == elementData.length)
            grow();
        if(tail == -1)
            tail = elementData.length - 1;
        elementData[tail--] = item;
        size++;
    }

    @Override
    public Item popFront() {
        if(head == 0)
            head = elementData.length;
        Item result = elementData[head--];
        size--;

        if(size == 0){
            tail = -1;
            head = 0;
        }
        if(elementData.length > DEFAULT_CAPACITY && size*4 <= elementData.length)
            shrink();
        return result;
    }

    @Override
    public Item popBack() {
        if(tail == elementData.length - 1)
            tail = -1;
        Item result = elementData[++tail];
        size--;

        if(size == 0){
            tail = -1;
            head = 0;
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
    public IListIterator<Item> iterator() {
        return new CyclicArrayDequeIterator();
    }

    private class CyclicArrayDequeIterator implements IListIterator<Item> {

        private int currentBack = tail, currentFront = head;

        @Override
        public boolean hasNext() {
            return (currentBack+1)%elementData.length != currentFront;
        }

        @Override
        public boolean hasPrevious() {
            return currentFront != (currentBack+1)%elementData.length;
        }

        @Override
        public Item next() {
            if(currentBack == elementData.length)
                currentBack = 0;
            return elementData[++currentBack];
        }

        @Override
        public Item previous() {
            if(currentFront == -1)
                currentFront = elementData.length - 1;
            return elementData[--currentFront];
        }

    }

    public static void main(String[] args) {
        CyclicArrayDeque<Integer> array = new CyclicArrayDeque<>();
        array.pushFront(1);
        array.pushFront(2);
        array.pushFront(3);
        array.pushFront(4);

        System.out.println(array.popBack());
        System.out.println(array.popBack());
        System.out.println(array.popBack());
        System.out.println(array.popBack());


        CyclicArrayDeque<Integer> deque = new CyclicArrayDeque<>();
        deque.pushBack(3);
        deque.pushBack(2);
        deque.pushBack(1);
        IListIterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("size = " + deque.size());

        deque = new CyclicArrayDeque<>();
        deque.pushFront(3);
        deque.pushFront(2);
        deque.pushFront(1);
        iterator = deque.iterator();
        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }
        System.out.println("size = " + deque.size());
    }
}