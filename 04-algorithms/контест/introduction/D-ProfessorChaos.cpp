#include <iostream>
#include <cmath>
using namespace std;

int main() {

    int a; int b; int c; int d; long long k;
    cin >> a >> b >> c >> d >> k;

    k = k > 1000 ? 1000: k;

    double res = a * b - c;

    if (res <= 0) {
        cout << "0";
        return 0;
    } else if (res > d) {
        res = d;
    }

    for (int i = 1; i < k; i++) {
        res = res * b - c;
        if (res <= 0) {
            cout << "0";
            return 0;
        }
        else if (res > d) {
            res = d;
        }
    }

    if (res <= 0) {
        cout << "0";
    } else if (res > d) {
        cout << d;
    } else {
        cout << res;
    }

    return 0;
}
