#include <iostream>
#include <unordered_map>
#include <algorithm>
#include <vector>

using namespace std;


int main() {

    string s;

    unordered_map<char, unsigned long long> weight;
    unordered_map<char, unsigned long long> counter;

    unordered_map<char, unsigned long long> max_value;

    vector<pair<char,unsigned long long>> a;


    cin >> s;

    for (char ch = 'a'; ch <= 'z'; ch++) {
        int num;
        cin >> num;
        weight[ch] = num;
        counter[ch] = 0;
        max_value[ch] = 0;
    }

    for (char ch: s) {
        if (counter[ch] == 0) {
            counter[ch]++;
        }
        else {
            max_value[ch] = weight[ch] * (s.size());
            counter[ch]++;
        }
    }

    for (char ch = 'a'; ch <= 'z'; ch++) {
        a.emplace_back(ch, max_value[ch]);
    }


    sort(a.begin(), a.end(), [](const auto& first, const auto& temp) {
        return first.second > temp.second;
    });

    string fir;
    string sec;
    int cnt = 0;
    bool f = true;

    while (cnt < 26) {
        if (counter[a[cnt].first] == 0) {
            cnt++;
        } else {
            if (f) {
                if (a[cnt].second == 0 && counter[a[cnt].first] <= 1) {
                    cnt++;
                } else {
                    fir.push_back(a[cnt].first);
                    counter[a[cnt].first]--;
                    f = false;
                }
            } else {
                if (a[cnt].second == 0 && counter[a[cnt].first] <= 1) {
                    cnt++;
                } else {
                    sec.push_back(a[cnt].first);
                    counter[a[cnt].first]--;
                    f = true;
                    cnt++;
                }
            }
        }
    }


    for (char ch = 'a'; ch <= 'z'; ch++) {
        while (counter[ch] != 0) {
            fir.push_back(ch);
            counter[ch]--;
        }
    }


    std::reverse(sec.begin(), sec.end());
    string result = fir + sec;

    cout << result;
    return 0;
}