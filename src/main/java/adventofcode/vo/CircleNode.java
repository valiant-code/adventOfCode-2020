package adventofcode.vo;

public class CircleNode<T> {
    public CircleNode<T> next = this;
    public CircleNode<T> previous = this;

    public final T value;

    public CircleNode(T value) {
        this.value = value;
    }

    public CircleNode<T> remove() {
        previous.next = next;
        next.previous = previous;

        return this;
    }

    public CircleNode<T> jumpAhead(int count) {
        CircleNode<T> rv = this;
        for (int i = 0; i < count; i++) {
            rv = rv.next;
        }
        return rv;
    }

    public CircleNode<T> jumpBack(int count) {
        CircleNode<T> rv = this;
        for (int i = 0; i < count; i++) {
            rv = rv.previous;
        }
        return rv;
    }

    public CircleNode<T> insertAfter(CircleNode<T> node) {
        next.previous = node;
        node.previous = this;
        node.next = next;
        this.next = node;

        return node;
    }

    public CircleNode<T> insertBefore(CircleNode<T> node) {
        previous.next = node;
        node.previous = previous;
        node.next = this;
        this.previous = node;

        return node;
    }

    @Override
    public String toString() {
        return "CircleNode{" +
                "value=" + value +
                ", previous=" + previous.value +
                ", next=" + next.value +
                '}';
    }

    //    @Override
//    public String toString() {
//        return next.toString(new StringBuilder(), this);
//    }
//
//    public String toString(StringBuilder prefix, CircleNode<T> stop) {
//        if (Objects.equals(this, stop)) {
//            return prefix.toString();
//        } else {
//            prefix.append(',').append(value);
//            return next.toString(prefix, stop);
//        }
//    }
}
