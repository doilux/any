package work.doilux.any;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Any3 represents a value of three possible types.
 *
 * @param <T1> The type of the First value
 * @param <T2> The type of the Second value
 * @param <T3> The type of the Third value
 */
public class Any3<T1, T2, T3> {

    private final T1 t1;

    private final T2 t2;

    private final T3 t3;

    private Any3(T1 t1, T2 t2, T3 t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    /**
     * Constructs a Any3 with first value.
     *
     * @param elem The Value
     * @param <T1> Type of first Value
     * @return A new Any3 instance with first value
     */
    public static <T1> Any3 first(T1 elem) {
        Objects.requireNonNull(elem, "elem is null");
        return new Any3(elem, null, null);
    }

    /**
     * Constructs a Any3 with second value.
     *
     * @param elem The Value
     * @param <T2> Type of second Value
     * @return A new Any3 instance with second value
     */
    public static <T2> Any3 second(T2 elem) {
        Objects.requireNonNull(elem, "elem is null");
        return new Any3(null, elem, null);
    }

    /**
     * Constructs a Any3 with third value.
     *
     * @param elem The Value
     * @param <T3> Type of third Value
     * @return A new Any3 instance with third value
     */
    public static <T3> Any3 third(T3 elem) {
        Objects.requireNonNull(elem, "elem is null");
        return new Any3(null, null, elem);
    }

    /**
     * Returns whether this is a Any3 with first value.
     *
     * @return true, if this is First, false otherwise
     */
    public boolean isFirst() {
        return t1 != null;
    }

    /**
     * Returns whether this is a Any3 with second value.
     *
     * @return true, if this is Second, false otherwise
     */
    public boolean isSecond() {
        return t2 != null;
    }

    /**
     * Returns whether this is a Any3 with third value.
     *
     * @return true, if this is Third, false otherwise
     */
    public boolean isThird() {
        return t3 != null;
    }

    /**
     * Maps any value of this disjunction.
     *
     * @param f1   maps the first value if this has first
     * @param f2   maps the second value if this has first
     * @param f3   maps the third value if this has first
     * @param <R1> The new first type of the resulting
     * @param <R2> The new second type of the resulting
     * @param <R3> The new third type of the resulting
     * @return A new Any3 instance
     */
    public <R1, R2, R3> Any3<R1, R2, R3> bimap(
            Function<T1, R1> f1,
            Function<T2, R2> f2,
            Function<T3, R3> f3
    ) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        Objects.requireNonNull(f3, "f3 is null");
        return mapFirst(f1).mapSecond(f2).mapThird(f3);
    }

    private <R> Any3<R, T2, T3> mapFirst(Function<T1, R> f) {
        Objects.requireNonNull(f, "f is null");
        return t1 == null ?
                new Any3<R, T2, T3>((R) t1, t2, t3) :
                new Any3<R, T2, T3>(f.apply(t1), t2, t3);
    }

    private <R> Any3<T1, R, T3> mapSecond(Function<T2, R> f) {
        Objects.requireNonNull(f, "f is null");
        return t2 == null ?
                new Any3<T1, R, T3>(t1, (R) t2, t3) :
                new Any3<T1, R, T3>(t1, f.apply(t2), t3);
    }

    private <R> Any3<T1, T2, R> mapThird(Function<T3, R> f) {
        Objects.requireNonNull(f, "f is null");
        return t3 == null ?
                new Any3<T1, T2, R>(t1, t2, (R) t3) :
                new Any3<T1, T2, R>(t1, t2, f.apply(t3));
    }


    /**
     * Applies the given actions.
     *
     * @param f1 An action which takes a first value
     * @param f2 An action which takes a second value
     * @param f3 An action which takes a third value
     * @return this instance
     */
    public Any3<T1, T2, T3> peek(
            Consumer<T1> f1,
            Consumer<T2> f2,
            Consumer<T3> f3

    ) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        Objects.requireNonNull(f3, "f3 is null");
        if (isFirst()) {
            forEachFirst(f1);
        } else if (isSecond()) {
            forEachSecond(f2);
        } else if (isThird()) {
            forEachThird(f3);
        }

        return this;
    }

    /**
     * Applies the given actions.
     *
     * @param f1 An action which takes a first value
     * @param f2 An action which takes a second value
     * @param f3 An action which takes a third value
     */
    public void forEach(
            Consumer<T1> f1,
            Consumer<T2> f2,
            Consumer<T3> f3

    ) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        Objects.requireNonNull(f3, "f3 is null");
        peek(f1, f2, f3);
    }

    private void forEachFirst(Consumer<T1> f) {
        Objects.requireNonNull(f, "f is null");
        f.accept(t1);
    }

    private void forEachSecond(Consumer<T2> f) {
        Objects.requireNonNull(f, "f is null");
        f.accept(t2);
    }

    private void forEachThird(Consumer<T3> f) {
        Objects.requireNonNull(f, "f is null");
        f.accept(t3);
    }


    /**
     * Folds any value of this disjunction.
     *
     * @param f1  maps the first value
     * @param f2  maps the second value
     * @param f3  maps the third value
     * @param <U> type of the folded value
     * @returnA value of type U
     */
    public <U> U fold(
            Function<T1, U> f1,
            Function<T2, U> f2,
            Function<T3, U> f3
    ) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        Objects.requireNonNull(f3, "f3 is null");
        if (isFirst()) {
            return f1.apply(t1);
        } else if (isSecond()) {
            return f2.apply(t2);
        } else if (isThird()) {
            return f3.apply(t3);
        }
        throw new IllegalStateException("all values are null");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Any3<?, ?, ?> any3 = (Any3<?, ?, ?>) o;

        if (t1 != null ? !t1.equals(any3.t1) : any3.t1 != null) return false;
        if (t2 != null ? !t2.equals(any3.t2) : any3.t2 != null) return false;
        return t3 != null ? t3.equals(any3.t3) : any3.t3 == null;
    }

    @Override
    public int hashCode() {
        int result = t1 != null ? t1.hashCode() : 0;
        result = 31 * result + (t2 != null ? t2.hashCode() : 0);
        result = 31 * result + (t3 != null ? t3.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Any3{" +
                "t1=" + t1 +
                ", t2=" + t2 +
                ", t3=" + t3 +
                '}';
    }
}

