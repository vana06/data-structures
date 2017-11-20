package seminar1.iterators.heap;

/**
 * Created by cilci_000 on 16.10.2017.
 */
public class Key implements Comparable<Key> {
    public int arrayNum, item;

    public Key(int arrayNum, int item) {
        this.arrayNum = arrayNum;
        this.item = item;
    }

    @Override
    public int compareTo(Key o) {
        if(item > o.item)
            return 1;
        else if(item > o.item)
            return -1;
        else
            return 0;
    }
}
