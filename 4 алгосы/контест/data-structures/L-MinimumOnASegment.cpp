#include <iostream>
#include <vector>
#include <list>
using namespace std;

int main(){
    int n, k;

    cin >> n >> k;

    vector<int> a(n);

    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }

    list<int> mini;
    vector<int> answer;

    int left_wind = 0; int i = 0;

    while (i < n) {
        while (!mini.empty() && mini.back() > a[i]) {
            mini.pop_back();
        }


        if (i - left_wind != k) {
            mini.push_back(a[i]);
            i++;
        }

        if (i - left_wind == k) {
            answer.push_back(mini.front());
            if (mini.front() == a[left_wind]) {
                mini.pop_front();
            }
            left_wind++;
        }
    }


    for (int i = 0; i < answer.size(); i++) {
        cout << answer[i] << " ";
    }
    return 0;
}