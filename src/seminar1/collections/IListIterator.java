package seminar1.collections;

import java.util.Iterator;

public interface IListIterator<Item> extends Iterator<Item>{

    boolean hasPrevious();

    Item previous();
}
