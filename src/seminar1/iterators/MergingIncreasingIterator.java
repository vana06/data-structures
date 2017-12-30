package seminar1.iterators;
import seminar1.collections.ArrayPriorityQueue;

import java.util.Iterator;

/**
 * Итератор возвращающий последовательность из двух возрастающих итераторов в порядке возрастания
 * first = 1,3,4,5,7
 * second = 0,2,4,6,8
 * result = 0,1,2,3,4,4,5,6,7,8
 *
 * Time = O(k),
 *  k — суммарное количество элементов
 */
public class MergingIncreasingIterator implements Iterator<Integer> {

    private IncreasingIterator first;
    private IncreasingIterator second;
    private ArrayPriorityQueue<Integer> queue;

    public MergingIncreasingIterator(IncreasingIterator first, IncreasingIterator second) {
        this.first = first;
        this.second = second;
        queue = new ArrayPriorityQueue<>();
        if(first.hasNext()) {
            queue.add(first.next());
        }
        if(second.hasNext()) {
            queue.add(second.next());
        }
    }

    @Override
    public boolean hasNext() {
        return queue.size() != 0;
    }

    @Override
    public Integer next() {
        Integer result = queue.extractMin();
        if(first.hasNext()) {
            queue.add(first.next());
        }
        if(second.hasNext()) {
            queue.add(second.next());
        }

        return result;
    }

    public static void main(String[] args) {
        IncreasingIterator first = new IncreasingIterator(0, 10, 2);
        IncreasingIterator second = new IncreasingIterator(0, 15, 2);
        MergingIncreasingIterator mii = new MergingIncreasingIterator(first, second);

        while (mii.hasNext()){
            System.out.println(mii.next());
        }
    }

}