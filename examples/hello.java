import java.util.Scanner;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;

abstract static class Hello<T> {
    public Hello(final T count) {
        sequenceSupplier = () -> getSequence(count);
    }

    public T sum() {
        return genericSum(getZero(), getPlus());
    }

    public T product() {
        return genericSum(getOne(), getMultiplies());
    }

    abstract protected T getZero();
    abstract protected T getOne();
    abstract protected Stream<T> getSequence(T count);
    abstract protected BinaryOperator<T> getPlus();
    abstract protected BinaryOperator<T> getMultiplies();

    private T genericSum(T identity, BinaryOperator<T> operation) {
        return sequenceSupplier.get().reduce(identity, operation);
    }

    private final Supplier<Stream<T>> sequenceSupplier;
}

void main() {
    final long n = new Scanner(System.in).nextLong();
    final Hello<Long> hello = new Hello<>(n) {
        @Override
        protected Long getZero() {
            return 0L;
        }

        @Override
        protected Long getOne() {
            return 1L;
        }

        @Override
        protected Stream<Long> getSequence(Long count) {
            return LongStream.rangeClosed(1, count).boxed();
        }

        @Override
        protected BinaryOperator<Long> getPlus() {
            return Long::sum;
        }

        @Override
        protected BinaryOperator<Long> getMultiplies() {
            return (a, b) -> a * b;
        }
    };
    System.out.println(hello.sum());
    System.out.println(hello.product());
}