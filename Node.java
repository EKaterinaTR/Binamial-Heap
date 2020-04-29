public class Node<T extends Comparable<T>> {
    T key;
    Node <T> parent;
    Node <T> child;
    Node <T> sibling;
    int degree;
    Node (T key) {
        this.key = key;
        child = null;
        parent = null;
        sibling = null;
        degree = 0;

    }
}
