#include <iostream>
#include <vector>
#include <string>

using namespace std;

int main() {

    vector<string> a;

    string number;

    while (cin >> number) {
        a.push_back(number);
    }

    for (int i = 0; i < a.size() - 1; i++) {
        for (int j = 0; j < a.size() - i - 1; j++) {
            if (a[j] + a[j + 1] < a[j + 1] + a[j]) {
                string temp = a[j];
                a[j] = a[j + 1];
                a[j + 1] = temp;
            }
        }
    }

    string result;

    for (const string& s: a) {
        result += s;
    }

    cout << result;

    return 0;
}