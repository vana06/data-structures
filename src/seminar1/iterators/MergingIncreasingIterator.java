package seminar1.iterators;
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
    private Integer a,b;

    public MergingIncreasingIterator(IncreasingIterator first, IncreasingIterator second) {
        this.first = first;
        this.second = second;
        a = first.next();
        b = second.next();
    }

    @Override
    public boolean hasNext() {
        if(first.hasNext() || second.hasNext())
            return true;
        return false;
    }

    @Override
    public Integer next() {
        if(!hasNext())
            return null;
        Integer result;
        if(!first.hasNext() && second.hasNext())
            result = b = second.next();
        if(!second.hasNext() && first.hasNext())
            result = a = second.next();
        if(first.hasNext() && second.hasNext() && a < b){
            result = a;
            if(first.hasNext())
                a = first.next();
        } else {
            result = b;
            if(second.hasNext())
                b = second.next();
        }
        return result;
    }

}