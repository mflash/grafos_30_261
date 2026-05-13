import sys
from queue import Queue
from graph import Graph

class BFS:

    def __init__(self, g, s):
        self.g = g
        self.start = s

        self.marked = set()
        self.edgeTo = {}
        self.distTo = {}

        self.bfs(s)

    def bfs(self, v):
        fila = Queue()
        fila.put(v)
        self.marked.add(v)
        self.distTo[v] = 0
        while fila.qsize() > 0:
            v = fila.get()
            self.marked.add(v)
            for w in g.getAdj(v):
                if w not in self.marked:
                    self.edgeTo[w] = v
                    self.marked.add(w)
                    self.distTo[w] = self.distTo[v] + 1
                    fila.put(w)

    def hasPathTo(self, v):
        return v in self.marked
    
    def getDist(self, v):
        return self.distTo[v]
    
    def pathTo(self, v):
        path = []
        if not self.hasPathTo(v):
            return path
        while v != self.start:
            path.insert(0,v)
            v = self.edgeTo[v]
        path.insert(0,self.start)
        return path

if __name__ == "__main__":

    g = Graph("exemplos/tinyG.txt")
    bfs = BFS(g, "0")

    for v in g.getVerts():
        print(f"{v}: ", end="")
        if bfs.hasPathTo(v):
            for w in bfs.pathTo(v):
                print(f"{w} ", end="")
            print(f"({bfs.getDist(v)})", end="")
        print()
    print()
