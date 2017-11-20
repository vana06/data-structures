package seminar1.iterators;
import java.util.Comparator;
import java.util.Iterator;

import seminar1.iterators.heap.ArrayPriorityQueue;
import seminar1.iterators.heap.Key;

/**
 * Итератор возвращающий последовательность из N возрастающих итераторов в порядке возрастания
 * first = 1,3,4,5,7
 * second = 0,2,4,6,8
 * result = 0,1,2,3,4,4,5,6,7,8
 *
 * Time = O(n + k * log n),
 *  n — количество итераторов
 *  k — суммарное количество элементов
 */
public class MergingPeekingIncreasingIterator implements Iterator<Integer> {

    private Comparator<PeekingIncreasingIterator> comparator = (p1, p2) -> p1.peek().compareTo(p2.peek());
    PeekingIncreasingIterator[] peekingIterator;
    ArrayPriorityQueue<Key> arrayPriorityQueue;

    public MergingPeekingIncreasingIterator(IPeekingIterator... peekingIterator) {
        this.peekingIterator = new PeekingIncreasingIterator[peekingIterator.length];
        arrayPriorityQueue = new ArrayPriorityQueue<Key>(peekingIterator.length);
        for (int i = 0; i < peekingIterator.length; i++) {
            this.peekingIterator[i] = (PeekingIncreasingIterator)peekingIterator[i];
            Key key = new Key(i, (Integer)peekingIterator[i].peek());
            arrayPriorityQueue.add(key);
        }
    }

    @Override
    public boolean hasNext() {
        for(PeekingIncreasingIterator pii: peekingIterator){
            if(pii.hasNext())
                return true;
        }
        return false;
    }

    @Override
    public Integer next() {
        if(!hasNext())
            return null;
        Key min = arrayPriorityQueue.extractMin();
        if(peekingIterator[min.arrayNum].hasNext())
            arrayPriorityQueue.add(new Key(min.arrayNum, peekingIterator[min.arrayNum].next()));
        else{
            for(int i = 0; i < peekingIterator.length; i++){
                if(peekingIterator[i].hasNext()) {
                    arrayPriorityQueue.add(new Key(i, peekingIterator[i].next()));
                }
            }
        }

        return min.item;
    }
}