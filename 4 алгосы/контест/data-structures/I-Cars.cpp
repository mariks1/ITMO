#include <iostream>
#include <list>
#include "unordered_set"
#include <queue>
#include <unordered_map>
#include <vector>

using namespace std;

int main() {


    int n, k, p;

    cin >> n >> k >> p;

    unordered_map<int, list<int>> same_cars;

    vector<int> cars(p);

    priority_queue<pair<int, int>> distance;

    unordered_set<int> current;

    int number;

    for (int i = 0; i < p; i++) {
        cin >> number;
        cars[i] = number;
        if (same_cars.find(number) == same_cars.end()) {
            list<int> temp;
            temp.push_back(i);
            same_cars[number] = temp;
        } else {
            same_cars[number].push_back(i);
        }
    }

    int answer = 0;
    int front_index;

    pair<int, int> furthest_car;

    for (int i = 0; i < p; i++) {
        if (current.size() < k) {
            if (current.find(cars[i]) != current.end()) {
                same_cars[cars[i]].pop_front();
                if (!same_cars[cars[i]].empty()) {
                    front_index = same_cars[cars[i]].front();
                } else {
                    front_index = p + 1;
                }
                distance.emplace(front_index, cars[i]);
            } else {
                current.insert(cars[i]);
                same_cars[cars[i]].pop_front();
                if (!same_cars[cars[i]].empty()) {
                    front_index = same_cars[cars[i]].front();
                } else {
                    front_index = p + 1;
                }
                answer++;
                distance.emplace(front_index, cars[i]);
            }
        }
        else {
            if (current.find(cars[i]) != current.end()) {
                same_cars[cars[i]].pop_front();
                if (!same_cars[cars[i]].empty()) {
                    front_index = same_cars[cars[i]].front();
                } else {
                    front_index = p + 1;
                }
                distance.emplace(front_index, cars[i]);
            }
            else {
                furthest_car = distance.top();
                distance.pop();
                current.erase(furthest_car.second);
                current.insert(cars[i]);
                same_cars[cars[i]].pop_front();
                if (!same_cars[cars[i]].empty()) {
                    front_index = same_cars[cars[i]].front();
                } else {
                    front_index = p + 1;
                }
                answer++;
                distance.emplace(front_index, cars[i]);
            }
        }
    }

    cout << answer;

    return 0;
}