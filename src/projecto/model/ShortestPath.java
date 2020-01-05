/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import projecto.model.TADDiGraph.Digraph;
import projecto.model.TADDiGraph.Edge;
import projecto.model.TADDiGraph.Vertex;

/**
 *
 * @author Miglob
 */
public class ShortestPath {

    private Digraph sourceDigraph;
    private Vertex source;
    private Vertex destination;
    
    private int test = 0;

    public ShortestPath(Digraph sourceDigraph, Vertex source, Vertex destination) {
        this.sourceDigraph = sourceDigraph;
        this.source = source;
        this.destination = destination;
    }

    public Digraph getSourceDigraph() {
        return sourceDigraph;
    }

    public void setSourceDigraph(Digraph sourceDigraph) {
        this.sourceDigraph = sourceDigraph;
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public Stack<Vertex> getShortestPath() {

        HashMap<Stack<Vertex>, Integer> paths = new HashMap<>();
        Stack<Node> unvisitedNodes = new Stack<>();
        Node current = new Node(source, null);
        List<Node> visitedNodes = new ArrayList<>(); 
        
        do {
            if (!visitedNodes.contains(current)) {
                if (current.getVertex() != null && current.getVertex().equals(this.destination)) {
                    paths.put(tracedBack(current), current.getDistanceToSource());
                } else {

                    List<Edge> auxListEdges = (List<Edge>) sourceDigraph.outboundEdges(current.getVertex());
                    for (int i = auxListEdges.size() - 1; i >= 0; i--) {
                        Node node = new Node(sourceDigraph.opposite(current.getVertex(), auxListEdges.get(i)), current);
                        unvisitedNodes.push(node);
                    }

                    visitedNodes.add(current);
                }
                Node temp = unvisitedNodes.pop();
                current = new Node(temp.getVertex(), temp.getPredecessor());
            }
            Node temp = unvisitedNodes.pop();
            current = new Node(temp.getVertex(), temp.getPredecessor());
        } while (unvisitedNodes.size() > 0);

        Set<Stack<Vertex>> pathsSet = paths.keySet();
        Stack<Vertex> shortestPath = null;

        for (Stack<Vertex> path : pathsSet) {
            if (shortestPath == null || paths.get(path) < paths.get(shortestPath)) {
                shortestPath = path;
            }
        }

        return shortestPath;
    }

    private Stack<Vertex> tracedBack(Node node) {
        Node current = node;
        Stack<Vertex> stack = new Stack<>();
        while (current != null) {
            stack.push(current.getVertex());
            current = current.getPredecessor();
        }
        return stack;
    }

    private class Node {

        private Vertex vertex;
        private Node predecessor;
        private int distanceToSource;

        public Node(Vertex vertex, Node predecessor) {
            this.vertex = vertex;
            this.predecessor = predecessor;
            this.distanceToSource = (predecessor == null) ? 0 : predecessor.getDistanceToSource() + 1;
        }

        public Vertex getVertex() {
            return vertex;
        }

        public void setVertex(Vertex vertex) {
            this.vertex = vertex;
        }

        public Node getPredecessor() {
            return predecessor;
        }

        public void setPredecessor(Node predecessor) {
            this.predecessor = predecessor;
        }

        public int getDistanceToSource() {
            return distanceToSource;
        }

        public void setDistanceToSource(int distanceToSource) {
            this.distanceToSource = distanceToSource;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }

            if (this == obj) {
                return true;
            }

            Node other = (Node) obj;

            return this.vertex.equals(other.getVertex());

        }

        @Override
        public int hashCode() {
            return 31 * this.vertex.hashCode() + this.distanceToSource;
        }
    }
}
