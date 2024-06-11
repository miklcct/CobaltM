#include <cstddef>
#include <ext/functional>
#include <functional>
#include <iostream>
#include <numeric>
#include <ranges>

using __gnu_cxx::identity_element;
using std::cin;
using std::cout;
using std::endl;
using std::multiplies;
using std::plus;
using std::ranges::iota_view;
using std::reduce;

template<class T> class Hello {
  public:
    explicit constexpr Hello(T count) :
        sequence(T{1}, ++count) {}

    constexpr auto sum() {
        return genericSum(plus<T>());
    }

    constexpr auto product() {
        return genericSum(multiplies<T>());
    }

  private:
    template<class Op> constexpr auto genericSum(const Op &operation) {
        return reduce(
            sequence.begin(),
            sequence.end(),
            identity_element(operation),
            operation
        );
    }

    const iota_view<T, T> sequence;
};

int main() {
    unsigned long n;
    cin >> n;
    Hello hello{n};
    cout << hello.sum() << endl;
    cout << hello.product() << endl;
    return 0;
}