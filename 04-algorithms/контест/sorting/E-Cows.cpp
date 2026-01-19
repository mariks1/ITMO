#include <iostream>
#include "vector"

using namespace std;



int main() {

    int n, k;

    cin >> n >> k;

    vector<long> a;
    long t;
    for (int i = 0; i < n; i++) {
        cin >> t;
        a.push_back(t);
    }

    long low = 0; long high = a.back() - a.front();

    long answer;

    while (low <= high) {
        long mid = low + (high - low) / 2;
        int cnt = 1;
        long point = a[0];
        for (long temp: a) {
            if (temp - point >= mid) {
                cnt+=1;
                point = temp;
            }
        }
        if (cnt >= k) {
            answer = mid;
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }

    cout << answer;

    return 0;
}
