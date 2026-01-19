#include <iostream>
#include <vector>
#include <queue>
#include <string>
#include <unordered_map>
#include <algorithm>
#include <stack>

using namespace std;
const int mx = (1e9);


int main() {

    int n, m, x_start, y_start, x_end, y_end;


    cin >> n >> m >> x_start >> y_start >> x_end >> y_end;

    vector<vector<int>> mp(n, vector<int>(m));
    vector<int> cost(n * m, mx);
    vector<char> path(n * m);

    char type;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            cin >> type;
            switch (type) {
                case '.':
                    mp[i][j] = 1;
                    break;
                case 'W':
                    mp[i][j] = 2;
                    break;
                case '#':
                    mp[i][j] = -1;
                    break;
            }
        }
    }

    vector<pair<int, int>> variants = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    vector<char> path_variants = {'S', 'E', 'N', 'W'};

    unordered_map<char, pair<int, int>> inverted_path {{'S', {-1, 0}}, {'E', {0, -1}}, {'N', {1, 0}}, {'W', {0, 1}}};

    cost[(x_start - 1) * m + y_start - 1] = 0;

    priority_queue<pair<int,int>, vector<pair<int, int>>, greater<>> q;

    q.emplace(0, (x_start - 1) * m + y_start - 1);

    pair<int, int> temp;

    int x_new;
    int y_new;
    while (!q.empty()) {
        temp = q.top();
        q.pop();
        if (temp.first > cost[temp.second]) {
            continue;
        }
        int x = temp.second / m, y = temp.second % m;
        for (int i = 0; i < 4; i++) {
            x_new = x + variants[i].first, y_new = y + variants[i].second;
            if ((x_new >= 0) && (x_new < n) && (y_new < m) && (y_new >= 0) && (mp[x_new][y_new] != -1)) {
                if (cost[x_new * m + y_new] > temp.first + mp[x_new][y_new]) {
                    cost[x_new * m + y_new] = temp.first + mp[x_new][y_new];
                    path[x_new * m + y_new] = path_variants[i];
                    q.emplace(cost[x_new * m + y_new], x_new * m + y_new);
                }
            }
        }
    }

    stack<char> ans;

    int temp_x;

    if (cost[(x_end - 1) * m + y_end - 1] != mx) {
        cout << cost[(x_end - 1) * m + y_end - 1] << '\n';
        x_new = x_end - 1;
        y_new = y_end - 1;
        while (true) {
            ans.push(path[x_new * m + y_new]);
            temp_x = x_new + inverted_path[path[x_new * m + y_new]].first;
            y_new = y_new + inverted_path[path[x_new * m + y_new]].second;
            x_new = temp_x;
            if ((x_new == x_start - 1) && (y_new == y_start - 1)) {
                break;
            }
        }
        while (!ans.empty()) {
            cout << ans.top();
            ans.pop();
        }
    } else {
        cout << -1;
    }


    return 0;
}
