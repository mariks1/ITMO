#include <iostream>
#include <string>
#include <algorithm>
#include <vector>
#include <stack>
#include <map>

using namespace std;

bool isNumber(const string& str) {
    bool t = false;
    for (char c: str) {
        if (!isdigit(c) && c != '-' && c != '+') {
            t = true;
            break;
        }
    }
    return !t;
}

stack<string> create_stack() {
    stack<string> t;
    return t;
}

int main() {

    map<string, stack<string>> m;
    stack<stack<string>> current;

    current.push(create_stack());

    string line;
    string left; string right;

    vector<string> answer;

    while (cin >> line) {
        if (line == "{") {
            current.push(create_stack());
        } else if (line == "}") {
            while (!current.top().empty()) {
                m[current.top().top()].pop();
                current.top().pop();
            }
            current.pop();
        }
        else {
            left = line.substr(0, line.find('='));
            right = line.substr(line.find('=') + 1);
            if (isNumber(right)) {
                current.top().push(left);
                m[left].push(right);
            } else {
                if (m.find(right) == m.end() || m[right].empty()) {
                    answer.emplace_back("0");
                    m[left].emplace("0");
                    current.top().push(left);
                } else {
                    m[left].push(m[right].top());
                    current.top().push(left);
                    answer.push_back(m[right].top());
                }
            }
        }
    }

    for (const auto & i : answer) {
        cout << i << endl;
    }

    return 0;
}
