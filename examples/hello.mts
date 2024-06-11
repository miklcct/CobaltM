import * as readline from 'readline/promises';

abstract class Hello<T> {
    constructor(count: T) {
        this._getSequence = () => this.getSequence(count);
    }

    sum(): T {
        return this.genericSum(this.zero, this.plus);
    }

    product(): T {
        return this.genericSum(this.one, this.multiplies);
    }

    protected abstract get zero(): T;
    protected abstract get one(): T;
    protected abstract getSequence(count: T): Iterable<T>;
    protected abstract plus(a: T, b: T): T;
    protected abstract multiplies(a: T, b: T): T;

    private genericSum(identity: T, operation: (a: T, b: T) => T) {
        let accumulator = identity;
        for (const item of this.sequence) {
            accumulator = operation(accumulator, item);
        }
        return accumulator;
    }

    private get sequence(): Iterable<T> {
        return this._getSequence();
    }

    private readonly _getSequence: () => Iterable<T>;
}

const rl = readline.createInterface({
    input : process.stdin,
    output : process.stdout,
});
const n = BigInt((await rl[Symbol.asyncIterator]().next()).value);
const hello = new class extends Hello<bigint> {
    protected get zero() : bigint {
        return 0n;
    }

    protected get one() : bigint {
        return 1n;
    }

    protected *getSequence(count : bigint) : Iterable<bigint> {
        let i = 1n;
        while (i <= count) {
            yield i;
            ++i;
        }
    }

    protected plus(a : bigint, b : bigint) : bigint {
        return a + b;
    }

    protected multiplies(a : bigint, b : bigint) : bigint {
        return a * b;
    }
}(n);
rl.write(`${hello.sum().toString()}\n`);
rl.write(`${hello.product().toString()}`);
rl.close();