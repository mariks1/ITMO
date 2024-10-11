#include <iostream>
#include <stack>
#include <string>
#include <vector>
using namespace std;

int main() {

    string input;
    cin >> input;

    vector<int> ans_array(input.length() / 2);

    stack<int> a_indices;
    stack<int> t_indices;
    stack<char> current;

    long t_ind = 0, a_ind = 0;

    bool answer = true;

    for (char i : input) {
        if (!current.empty()) {
            if (isupper(i)) {
                t_indices.push(t_ind);
                t_ind++;
                if (isupper(current.top())) {
                    current.push(i);
                } else {
                    if (i == toupper(current.top())) {
                        ans_array[t_indices.top()] = a_indices.top();
                        t_indices.pop();
                        a_indices.pop();
                        current.pop();
                    }
                    else {
                        current.push(i);
                    }
                }
            } else {
                a_indices.push(a_ind);
                a_ind++;
                if (islower(current.top())) {
                    current.push(i);
                } else {
                    if (i == tolower(current.top())) {
                        ans_array[t_indices.top()] = a_indices.top();
                        t_indices.pop();
                        a_indices.pop();
                        current.pop();
                    }
                    else {
                        current.push(i);
                    }
                }
            }
        } else {
            current.push(i);
            if (isupper(i)) {
                t_indices.push(t_ind);
                t_ind++;
            } else {
                a_indices.push(a_ind);
                a_ind++;
            }
        }
    }

    if (!current.empty()) {answer = false;}

    if (answer) {
        cout << "Possible" << "\n";
        for (long i = 0; i < input.length() / 2; i++) {
            cout << ans_array[i] + 1 << " ";
        }
    } else {
        cout << "Impossible";
    }

    return 0;
}
