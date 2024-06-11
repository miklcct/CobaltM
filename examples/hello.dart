import 'dart:io';

abstract class Hello<T> {
  Hello(T count) {
    _sequence = getSequence(count);
  }

  T sum() => _genericSum(zero, plus);
  T product() => _genericSum(one, multiplies);

  T get zero;
  T get one;
  Iterable<T> getSequence(T count);
  T plus(T a, T b);
  T multiplies(T a, T b);

  late final Iterable<T> _sequence;
  T _genericSum(T identity, T Function(T, T) operation) =>
      _sequence.fold(identity, operation);
}

class _HelloBigInt extends Hello<BigInt> {
  _HelloBigInt(super.count);

  @override
  BigInt get zero => BigInt.zero;

  @override
  BigInt get one => BigInt.one;

  @override
  Iterable<BigInt> getSequence(BigInt count) sync* {
    var i = BigInt.one;
    while (i <= count) {
      yield i;
      i += BigInt.one;
    }
  }

  @override
  BigInt multiplies(BigInt a, BigInt b) => a * b;

  @override
  BigInt plus(BigInt a, BigInt b) => a + b;
}

void main() {
  final n = BigInt.parse(stdin.readLineSync()!);
  final hello = _HelloBigInt(n);
  print(hello.sum());
  print(hello.product());
}