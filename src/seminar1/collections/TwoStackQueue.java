package seminar1.collections;
import java.util.Iterator;

public class TwoStackQueue<Item> implements IQueue<Item> {

    private IStack<Item> stack1;
    private IStack<Item> stack2;

    public TwoStackQueue() {
        stack1 = new LinkedStack<>();
        stack2 = new LinkedStack<>();
    }

    @Override
    public void enqueue(Item item) {
        stack1.push(item);
    }

    @Override
    public Item dequeue() {
        swap(stack1, stack2);
        Item item = stack2.pop();
        swap(stack2, stack1);
        return item;
    }

    @Override
    public boolean isEmpty() {
        if(!stack1.isEmpty() && !stack2.isEmpty())
            return false;
        return true;
    }

    @Override
    public int size() {
        return stack1.size();
    }

    @Override
    public Iterator<Item> iterator() {
        /* TODO: implement it (optional) */
        return null;
    }

    private void swap(IStack<Item> stack1, IStack<Item> stack2){
        while (!stack1.isEmpty()){
            stack2.push(stack1.pop());
        }
    }
}
