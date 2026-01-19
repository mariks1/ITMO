#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;
typedef vector<int> EdgeVec;
typedef vector<EdgeVec> Graph;

bool BFS(const Graph & g) {
    vector<int> visited(g.size());
    queue<int> q;
    auto it = std::find(visited.begin(), visited.end(), 0);
    while (it != visited.end()) {
        visited[it - visited.begin()] = 1;
        int cnt = 1;
        q.push(it - visited.begin());
        while (!q.empty()) {
            int u = q.front();
            cnt = (visited[u] == 1) ? 2 : 1;
            q.pop();
            for (int i = 0; i < g[u].size(); i++) {
                int v = g[u][i];
                if (visited[v] == 0) {
                    visited[v] = cnt;
                    q.push(v);
                } else {
                    if (visited[v] != cnt) {
                        return false;
                    } else {
                        continue;
                    }
                }
            }
        }
        it = find(visited.begin(), visited.end(), 0);
    }
    return true;
}

EdgeVec createNew() {
    EdgeVec temp;
    return temp;
}

int main() {

    int n; int m;

    cin >> n;
    cin >> m;

    Graph graph(n);
    for (int i = 0; i < n; i++) {
        EdgeVec temp = createNew();
        graph[i] = temp;
    }

    int first; int second;

    for (int i = 0; i < m; i++) {
        cin >> first >> second;
        graph[first - 1].push_back(second - 1);
        graph[second - 1].push_back(first - 1);
    }

    bool test = BFS(graph);

    if (test) {
        cout << "YES";
    } else {
        cout << "NO";
    }
    return 0;
}