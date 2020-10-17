package entities;
import java.util.Arrays;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{

    public T value;

    public Color color;

    public Node<T> left = null;
    public Node<T> right = null;
    public Node<T> parent = null;

    public Node(T value, Color color) {
        this.value = value;
        this.color = color;
    }

    public Node(T value) {
        this.value = value;
        this.color = null;
    }

    public Node(Color color) {
        this.color = color;
        this.value = null;
    }

    public Node() {
        this.color = null;
        this.value = null;
    }

    @Override
    public int compareTo(Node<T> o) {
        return value.compareTo(o.value);
    }

}
