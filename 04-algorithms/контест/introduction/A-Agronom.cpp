#include <iostream>
#include <cmath>
using namespace std;

int main() {

    long long n;
    cin >> n;
    long long current;
    long long previous;
    cin >> previous;

    int cnt = 1;

    long long bst = 0, bend = 0,
            start = 0, end = 0;

    for (long long i = 1; i < n; i++) {
        cin >> current;
        end++;
        if (current == previous) {
            cnt++;
        }
        else {
            cnt = 1;
        }
        if (cnt == 3) {
            end--;
            if (end - start > bend - bst) {
                bend = end; bst = start;
            }
            cnt = 2;
            start =  i - 1;
            end++;
        }
        previous = current;
    }

    if (end - start > bend - bst) {
        bend = end; bst = start;
    }

    cout << bst + 1 << " " << bend + 1;

    return 0;
}
