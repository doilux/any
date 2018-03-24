package work.doilux.any;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Any3 represents a value of three possible types.
 *
 * @param <T1> The type of the First value
 * @param <T2> The type of the Second value
 * @param <R>  The type of the Third value
 */
public class Any3<T1, T2, R> {

    private final Either<T1, T2> l;

    private final R r;

    private Any3(Either<T1, T2> l, R r) {
        this.l = l;
        this.r = r;
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
        return new Any3(Either.left(elem), null);
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
        return new Any3(Either.right(elem), null);
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
        return new Any3(null, elem);
    }

    /**
     * Returns whether this is a Any3 with first value.
     *
     * @return true, if this is First, false otherwise
     */
    public boolean isFirst() {
        return l != null && l.isLeft();
    }

    /**
     * Returns whether this is a Any3 with second value.
     *
     * @return true, if this is Second, false otherwise
     */
    public boolean isSecond() {
        return l != null && l.isRight();
    }

    /**
     * Returns whether this is a Any3 with third value.
     *
     * @return true, if this is Third, false otherwise
     */
    public boolean isThird() {
        return r != null;
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
            Function<R, R3> f3
    ) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        Objects.requireNonNull(f3, "f3 is null");
        return mapFirst(f1).mapSecond(f2).mapThird(f3);
    }

    private <U> Any3<U, T2, R> mapFirst(Function<T1, U> f) {
        Objects.requireNonNull(f, "f is null");
        return l == null ?
                new Any3<>((Either<U, T2>) l, r) :
                new Any3<>(l.mapLeft(f), r);
    }

    private <U> Any3<T1, U, R> mapSecond(Function<T2, U> f) {
        Objects.requireNonNull(f, "f is null");
        Objects.requireNonNull(f, "f is null");
        return l == null ?
                new Any3<>((Either<T1, U>) l, r) :
                new Any3<>(l.mapRight(f), r);
    }

    private <U> Any3<T1, T2, U> mapThird(Function<R, U> f) {
        Objects.requireNonNull(f, "f is null");
        return r == null ?
                new Any3<>(l, (U) r) :
                new Any3<>(l, f.apply(r));
    }


    /**
     * Applies the given actions.
     *
     * @param f1 An action which takes a first value
     * @param f2 An action which takes a second value
     * @param f3 An action which takes a third value
     * @return this instance
     */
    public Any3<T1, T2, R> peek(
            Consumer<T1> f1,
            Consumer<T2> f2,
            Consumer<R> f3

    ) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        Objects.requireNonNull(f3, "f3 is null");
        forEach(f1, f2, f3);
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
            Consumer<R> f3

    ) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        Objects.requireNonNull(f3, "f3 is null");
        forEachLeft(f1, f2);
        forEachRight(f3);
    }

    private void forEachLeft(Consumer<T1> f1, Consumer<T2> f2) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f1, "f2 is null");
        if (l != null) {
            l.forEach(f1, f2);
        }
    }

    private void forEachRight(Consumer<R> f) {
        Objects.requireNonNull(f, "f is null");
        if (r != null) {
            f.accept(r);
        }
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
            Function<R, U> f3
    ) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        Objects.requireNonNull(f3, "f3 is null");
        if (isFirst() || isSecond()) {
            return l.fold(f1, f2);
        } else if (isThird()) {
            return f3.apply(r);
        }
        throw new IllegalStateException("all values are null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Any3<?, ?, ?> any3 = (Any3<?, ?, ?>) o;

        if (l != null ? !l.equals(any3.l) : any3.l != null) return false;
        return r != null ? r.equals(any3.r) : any3.r == null;
    }

    @Override
    public int hashCode() {
        int result = l != null ? l.hashCode() : 0;
        result = 31 * result + (r != null ? r.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Any3{" +
                "l=" + l +
                ", r=" + r +
                '}';
    }

}

