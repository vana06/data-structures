package seminar1.iterators;
import java.util.Comparator;
import java.util.Iterator;

import seminar1.collections.ArrayPriorityQueue;

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
    private ArrayPriorityQueue<Element> arrayPriorityQueue;

    MergingPeekingIncreasingIterator(IPeekingIterator... peekingIterator) {
        arrayPriorityQueue = new ArrayPriorityQueue<>(peekingIterator.length);
        for (int i = 0; i < peekingIterator.length; i++) {
            arrayPriorityQueue.add(new Element(peekingIterator[i]));
        }
    }

    @Override
    public boolean hasNext() {
        return arrayPriorityQueue.size() != 0;
    }

    @Override
    public Integer next() {
        Element element = arrayPriorityQueue.extractMin();
        Integer min = element.key;
        if(element.iterator.hasNext())
            arrayPriorityQueue.add(new Element(element.iterator));
        return min;
    }

    private class Element implements Comparable<Element>{

        Integer key;
        IPeekingIterator<Integer> iterator;

        Element(IPeekingIterator<Integer> iterator) {
            this.iterator = iterator;
            if(iterator.hasNext())
                this.key = iterator.next();
        }

        @Override
        public int compareTo(Element o) {
            if(key > o.key)
                return 1;
            else if(key > o.key)
                return -1;
            else
                return 0;
        }
    }

    public static void main(String[] args) {
        IPeekingIterator<Integer> first = new PeekingIncreasingIterator(0, 10, 1);
        IPeekingIterator<Integer> second = new PeekingIncreasingIterator(0, 15, 1);
        MergingPeekingIncreasingIterator mpii = new MergingPeekingIncreasingIterator(first, second);

        while (mpii.hasNext()){
            System.out.println(mpii.next());
        }
    }
}