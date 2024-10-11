#include <iostream>
#include <vector>
#include <algorithm>
#include <stack>
#include <queue>

using namespace std;
typedef vector<int> EdgeVec;
typedef vector<EdgeVec> Graph;

void BFS(Graph& g, int start, vector<int>& broken) {

    queue<int> q;
    broken[start] = 1;
    q.push(start);

    while (!q.empty()) {
        int u = q.front();
        q.pop();
        for (int i = 0; i < g[u].size(); i++) {
            int v = g[u][i];
            if (broken[v] == 0) {
                broken[v] = 1;
                q.push(v);
            }
        }
    }
}


int DFS(Graph& g, int start) {
    vector<int> visited(g.size(), 0);
    stack<int> stack;

    visited[start] = 1;
    stack.push(start);

    int last_one = start;

    while (!stack.empty()) {
        int u = stack.top();
        stack.pop();

        if (visited[u] != 1) {
            last_one = u;
            visited[u] = 1;
        }

        for (int i = 0; i < g[u].size(); i++) {
            int v = g[u][i];
            if (visited[v] != 1) {
                stack.push(v);
            }
        }
    }
    return last_one;
}

EdgeVec createNew() {
    EdgeVec temp;
    return temp;
}

int main() {

    int n;

    cin >> n;

    Graph graph(n);
    Graph reversed(n);
    for (int i = 0; i < n; i++) {
        EdgeVec temp = createNew();
        EdgeVec temp2 = createNew();
        graph[i] = temp;
        reversed[i] = temp2;
    }

    int key;

    for (int i = 0; i < n; i++) {
        cin >> key;
        graph[key - 1].push_back(i);
        reversed[i].push_back(key - 1);
    }

    vector<int> broken(graph.size(), 0);

    int need_to_break = 0;

    while (find(broken.begin(), broken.end(), 0) != broken.end()) {
        need_to_break++;
        int it = find(broken.begin(), broken.end(), 0) - broken.begin();
        int check = DFS(reversed, it);

        BFS(graph, check, broken);

    }

    cout << need_to_break;

    return 0;
}