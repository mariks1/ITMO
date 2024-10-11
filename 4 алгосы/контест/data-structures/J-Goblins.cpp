#include <iostream>
#include <vector>
#include <list>
#include <set>
#include <map>
using namespace std;


int main() {

    int n;
    cin >> n;

    list<int> first_half;
    list<int> second_half;

    char type;
    int number_of_goblin;

    for (int i = 0; i < n; i++) {
        cin >> type;
        switch (type) {
            case '+':
                cin >> number_of_goblin;
                second_half.push_back(number_of_goblin);
                if (second_half.size() > first_half.size()) {
                    first_half.push_back(second_half.front());
                    second_half.pop_front();
                }
                break;
            case '*':
                cin >> number_of_goblin;
                if (second_half.size() == first_half.size()) {
                    first_half.push_back(number_of_goblin);
                } else {
                    second_half.push_front(number_of_goblin);
                }
                break;
            case '-':
                number_of_goblin = first_half.front();
                first_half.pop_front();
                if (second_half.size() > first_half.size()) {
                    first_half.push_back(second_half.front());
                    second_half.pop_front();
                }
                cout << number_of_goblin << "\n";
                break;
        }
    }



    return 0;
}
