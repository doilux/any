package work.doilux.any;


import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

class Either<L, R> {

    private final L l;

    private final R r;

    private Either(L l, R r) {
        this.l = l;
        this.r = r;
    }

    static <L> Either left(L l) {
        return new Either(l, null);
    }

    static <R> Either right(R r) {
        return new Either(null, r);
    }

    boolean isLeft() {
        return l != null;
    }

    boolean isRight() {
        return r != null;
    }

    <T> Either<T, R> mapLeft(Function<L, T> f) {
        Objects.requireNonNull(f, "f is null");
        return l == null ?
                new Either<>((T) l, r) :
                new Either<>(f.apply(l), r);
    }

    <T> Either<L, T> mapRight(Function<R, T> f) {
        Objects.requireNonNull(f, "f is null");
        return r == null ?
                new Either<>(l, (T) r) :
                new Either<>(l, f.apply(r));
    }

    void forEach(
            Consumer<L> f1,
            Consumer<R> f2

    ) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        if (l != null) {
            f1.accept(l);
        } else {
            f2.accept(r);
        }
    }

    <U> U fold(
            Function<L, U> f1,
            Function<R, U> f2
    ) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        if (isLeft()) {
            return f1.apply(l);
        } else if (isRight()) {
            return f2.apply(r);
        }
        throw new IllegalStateException("all values are null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Either<?, ?> either = (Either<?, ?>) o;

        if (l != null ? !l.equals(either.l) : either.l != null) return false;
        return r != null ? r.equals(either.r) : either.r == null;
    }

    @Override
    public int hashCode() {
        int result = l != null ? l.hashCode() : 0;
        result = 31 * result + (r != null ? r.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Either{" +
                "l=" + l +
                ", r=" + r +
                '}';
    }
}
