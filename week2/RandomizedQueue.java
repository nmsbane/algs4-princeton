/**
 * Created by nmsbane on 12/10/17.
 */

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // Store Items in a ResizingArray
    private Item[] a;
    private int size;

    public RandomizedQueue() {
        a = (Item[]) new Object[1];
        size = 0;
    }

    public boolean isEmpty() {
        return (size <= 0);
    }


    public int size() {
        return size;
    }


    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        if (size == a.length) resize(2 * a.length);
        a[size++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) copy[i] = a[i];
        a = copy;
    }

    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int index = StdRandom.uniform(size);
        Item ans = a[index];
        if (index != size - 1) a[index] = a[size - 1];
        a[size-1] = null;
        size = size - 1;
        if (size >= 1 && size == a.length / 4) resize(a.length / 2);
        return ans;
    }

    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        return a[StdRandom.uniform(size)];
    }


    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * A copy of the original resizing array with the ability to dequeue only.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {
        // Store Items in a ResizingArray
        private int subsize = size;
        private final Item[] copy;

        private RandomizedQueueIterator() {
            copy = (Item[]) new Object[subsize];
            for (int i = 0; i < subsize; i++) copy[i] = a[i];
        }

        @Override
        public boolean hasNext() { return subsize > 0; }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            int index = StdRandom.uniform(subsize);
            Item ans = copy[index];
            if (index != subsize - 1) copy[index] = copy[subsize - 1];
            copy[--subsize] = null;
            return ans;
        }
    }
}
