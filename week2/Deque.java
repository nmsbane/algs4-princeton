import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    // Store Items in a doubly-linked list.
    private Node<Item> first;
    private Node<Item> last;
    private int n = 0;

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;

        private Node(Item item) {
            this.item = item;
            next = null;
            previous = null;
        }
    }

    public Deque(){
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return first == null || n <= 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        Node<Item> oldfirst = first;
        first = new Node<>(item);
        if (isEmpty()) last = first;
        else {
            first.next = oldfirst;
            oldfirst.previous = first;
        }
        n++;
    }

    public void addLast(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        Node<Item> oldlast = last;
        last = new Node<>(item);
        if (isEmpty()) first = last;
        else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        n++;

    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;
        if (first.next != null) first = first.next;
        first.previous = null;
        n--;
        if (isEmpty()) {
            last = null;
            first = null;
        }
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = last.item;
        if (last.previous != null) last = last.previous;
        last.next = null;
        n--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        return item;
    }

    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext()  { return current != null;                     }

        public void remove()      { throw new java.lang.UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}