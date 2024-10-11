#include <iostream>
#include <vector>
#include <list>
#include <set>
#include <unordered_set>
#include <queue>

struct interval {
    struct interval* next;
    struct interval* previous;
    long long starts_at;
    bool free;
    unsigned long long size;
};

struct interval* bad_request() {
    return new interval{nullptr, nullptr, -1, true, 0};
}

struct interval* new_inter(struct interval* next, struct interval* previous, long long starts_at, bool free, unsigned long long size) {
    return new interval{next, previous, starts_at, free, size};
}

using namespace std;
int main() {

    unsigned long long n;
    int m;

    cin >> n >> m;

    long long request;

    set<pair<long long, struct interval*>, greater<>> space;
    vector<struct interval*> all_req(m);

    space.emplace(n, new_inter(nullptr, nullptr, 0, true, n));

    for (int i = 0; i < m; i++) {
        cin >> request;
        if (request > 0) {
            if (space.empty()) {
                cout << -1 << "\n";
                all_req[i] = bad_request();
                continue;
            }
            if (space.begin()->first < request) {
                cout << -1 << "\n";
                all_req[i] = bad_request();
                continue;
            } else {
                cout << space.begin()->second->starts_at + 1 << "\n";
                if (space.begin()->first - request > 0) {
                    interval* temporary = new_inter(space.begin()->second->next, space.begin()->second, space.begin()->second->starts_at + request, true, space.begin()->second->size - request);
                    space.begin()->second->free = false;
                    space.begin()->second->size = request;
                    space.begin()->second->next = temporary;
                    all_req[i] = space.begin()->second;
                    space.erase(space.begin());
                    space.insert({temporary->size, temporary});
                }
                else {
                    space.begin()->second->free = false;
                    space.begin()->second->size = request;
                    all_req[i] = space.begin()->second;
                    space.erase(space.begin());
                }
            }
        }
        else {
            all_req[i] = bad_request();
            int index = abs(request) - 1;
            if (all_req[index]->starts_at == -1) {
                continue;
            }
            else {
                all_req[index]->free = true;
                if (all_req[index]->previous != nullptr) {
                    if (all_req[index]->next != nullptr) {
                        if (all_req[index]->previous->free && all_req[index]->next->free) {
                            space.erase({all_req[index]->previous->size, all_req[index]->previous});
                            space.erase({all_req[index]->next->size, all_req[index]->next});
                            all_req[index]->size = all_req[index]->previous->size + all_req[index]->size + all_req[index]->next->size;
                            all_req[index]->starts_at = all_req[index]->previous->starts_at;
                            all_req[index]->next = all_req[index]->next->next;
                            all_req[index]->previous = all_req[index]->previous->previous;
                            if (all_req[index]->next != nullptr) {
                                all_req[index]->next->previous = all_req[index];
                            }
                            if (all_req[index]->previous != nullptr) {
                                all_req[index]->previous->next = all_req[index];
                            }
                            space.insert({all_req[index]->size, all_req[index]});
                        } else if (all_req[index]->previous->free) {
                            space.erase({all_req[index]->previous->size, all_req[index]->previous});
                            all_req[index]->starts_at = all_req[index]->previous->starts_at;

                            all_req[index]->size = all_req[index]->size + all_req[index]->previous->size;
                            all_req[index]->previous = all_req[index]->previous->previous;
                            if (all_req[index]->previous != nullptr) {
                                all_req[index]->previous->next = all_req[index];
                            }
                            space.insert({all_req[index]->size, all_req[index]});
                        }
                        else if (all_req[index]->next->free) {
                            space.erase({all_req[index]->next->size, all_req[index]->next});
                            all_req[index]->size = all_req[index]->size + all_req[index]->next->size;
                            all_req[index]->next = all_req[index]->next->next;
                            if (all_req[index]->next != nullptr) {
                                all_req[index]->next->previous = all_req[index];
                            }
                            space.insert({all_req[index]->size, all_req[index]});
                        }
                        else {
                            space.insert({all_req[index]->size, all_req[index]});
                        }
                    }
                    else {
                        if (all_req[index]->previous->free) {
                            space.erase({all_req[index]->previous->size, all_req[index]->previous});
                            all_req[index]->starts_at = all_req[index]->previous->starts_at;
                            all_req[index]->size = all_req[index]->previous->size + all_req[index]->size;
                            all_req[index]->previous = all_req[index]->previous->previous;
                            if (all_req[index]->previous != nullptr) {
                                all_req[index]->previous->next = all_req[index];
                            }
                            space.insert({all_req[index]->size, all_req[index]});
                        } else {
                            space.insert({all_req[index]->size, all_req[index]});
                        }
                    }
                } else {
                    if (all_req[index]->next != nullptr) {
                        if (all_req[index]->next->free) {
                            space.erase({all_req[index]->next->size, all_req[index]->next});
                            all_req[index]->size += all_req[index]->next->size;
                            all_req[index]->next = all_req[index]->next->next;
                            if (all_req[index]->next != nullptr) {
                                all_req[index]->next->previous = all_req[index];
                            }
                            space.insert({all_req[index]->size, all_req[index]});
                        }
                        else {
                            space.insert({all_req[index]->size, all_req[index]});
                        }
                    } else {
                        space.insert({all_req[index]->size, all_req[index]});
                    }
                }

            }
        }
    }

    return 0;
}
