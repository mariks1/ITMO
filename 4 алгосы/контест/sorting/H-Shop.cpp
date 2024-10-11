#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

int main() {


    int n, k;

    cin >> n >> k;

    vector<int> a(n);

    int number;

    for (int i = 0; i < n; i++) {
        cin >> number;
        a[i] = number;
    }

    sort(a.begin(), a.end(), greater<>());

    long long cnt = 0;
    int t = 1;

    for (int i = 0; i < n; i++) {
        if (t != k) {
            cnt += a[i];
            t++;
        }
        else {
            t = 1;
        }
    }

    cout << cnt;
    return 0;
}