package nl.han.ica.datastructures;


import java.util.EmptyStackException;
import java.util.LinkedList;

public class HANStack<T> implements IHANStack<T> {

    LinkedList<T> list = new LinkedList<>();

    @Override
    public void push(T value) {
        list.addFirst(value);
    }

    @Override
    public T pop() {
        if (list.size() == 0) {
            throw new EmptyStackException();
        }
        return list.removeFirst();

    }

    @Override
    public T peek() {
        if (list.size() == 0) {
            throw new EmptyStackException();
        }
        return list.getFirst();

    }
}