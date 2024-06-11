import java.util.Scanner

internal abstract class Hello<T>(count: T) {
    fun sum(): T {
        return genericSum(zero, ::plus)
    }

    fun product(): T {
        return genericSum(one, ::multiplies)
    }

    protected abstract val zero: T
    protected abstract val one: T
    protected abstract fun getSequence(count: T): Iterable<T>
    protected abstract fun plus(a: T, b: T): T
    protected abstract fun multiplies(a: T, b: T): T

    private inline fun genericSum(identity: T, operation: (T, T) -> T): T {
        return sequence.fold(identity, operation)
    }

    private val sequence: Iterable<T> = this.getSequence(count)
}

fun main() {
    val n = Scanner(System.`in`).nextLong()
    val hello = object : Hello<Long>(n) {
        override val zero = 0L
        override val one = 1L
        override fun getSequence(count: Long): Iterable<Long> {
            return (1..n).asIterable()
        }
        override fun plus(a: Long, b: Long): Long {
            return a + b
        }
        override fun multiplies(a: Long, b: Long): Long {
            return a * b
        }
    }
    println(hello.sum())
    println(hello.product())
}