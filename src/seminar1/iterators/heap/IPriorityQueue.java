package seminar1.iterators.heap;


public interface IPriorityQueue<Key extends Comparable<Key>> extends Iterable<Key> {

    void add(Key key);

    Key peek();

    Key extractMin();

    boolean isEmpty();

    int size();
}