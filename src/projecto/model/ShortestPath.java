/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Miglob
 *
 * Classe que através de um modelo nos dá o menor caminho de uma página incial
 * até uma página de destino
 *
 * Contém uma classe privada Node que cria e manipula os nós do modelo.
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

    /**
     * Método para se saber a origem do dígrafo
     *
     * @return a origem do digrafo
     */
    public Digraph getSourceDigraph() {
        return sourceDigraph;
    }

    /**
     * Método para se designar a origem do digrafo
     *
     * @param sourceDigraph a origem do digrafo
     */
    public void setSourceDigraph(Digraph sourceDigraph) {
        this.sourceDigraph = sourceDigraph;
    }

    /**
     * Método para se saber o vertice fonte
     *
     * @return um vertice que é raiz.
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * Método para se designar um vértice fonte
     *
     * @param source o vértice fonte
     */
    public void setSource(Vertex source) {
        this.source = source;
    }

    /**
     * Método para se saber o vertice de destino
     *
     * @return o vertice de destino
     */
    public Vertex getDestination() {
        return destination;
    }

    /**
     * Método para designar o vertice de destino
     *
     * @param destination o vertice de destino
     */
    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    /**
     * Método para nos dar o menor caminho numa lista ordenada dos vertices de
     * uma origem a um destino.
     *
     * @return uma lista de vertices
     */
    public List<Stack<Vertex>> shortestPaths() {
        List<Stack<Vertex>> list = new ArrayList<>();
        for (Object vertex : this.sourceDigraph.vertices()) {
            Vertex v = (Vertex) vertex;
            if (!v.equals(this.source)) {
                setDestination((Vertex) v);
                list.add(getShortestPath());
            }
        }
//        this.sourceDigraph.vertices().stream().map((vertex) -> {
//            setDestination((Vertex) vertex);
//            return vertex;
//        }).forEachOrdered((_item) -> {
//            list.add(getShortestPath());
//        });
        return list;
    }

    /**
     * Método que nos dá em formato de texto a lista ordenada do menor caminho
     * de um vertice a outro
     *
     * @return um texto formatado do menor caminho com os vertices ordenados
     */
    public String getShortestPaths() {
        List<Stack<Vertex>> list = shortestPaths();
        String str = "";

        for (Stack<Vertex> stack : list) {
            if (stack.size() > 0) {
                str += "[";
                for (int i = stack.size() - 1; i >= 0; i--) {
                    str += stack.get(i).toString();
                    if (!(i == 0)) {
                        str += " -> ";
                    }
                }
                str += "]\n";
            }
        }
        return str;
    }

    /**
     * Método que nos fornece em forma de pilha os vertices de um caminho mais
     * curto entre uma origem e um destino
     *
     * @return uma pilha nova se não existir menor caminho ou uma pilha com o
     * menor caminho
     */
    public Stack<Vertex> getShortestPath() {

        HashMap<Stack<Vertex>, Integer> paths = new HashMap<>();

        explore(paths);

        Set<Stack<Vertex>> pathsSet = paths.keySet();
        Stack<Vertex> shortestPath = null;

        for (Stack<Vertex> path : pathsSet) {
            if (shortestPath == null || paths.get(path) < paths.get(shortestPath)) {
                shortestPath = path;
            }
        }

        return (shortestPath == null) ? new Stack<>() : shortestPath;
    }

    private void explore(HashMap<Stack<Vertex>, Integer> paths) {

        Node current = new Node(source, null);
        List<Node> visitedNodes = new ArrayList<>();
        Stack<Node> unvisitedNodes = new Stack<>();

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
            }
            Node temp = unvisitedNodes.pop();
            current = new Node(temp.getVertex(), temp.getPredecessor());

        } while (unvisitedNodes.size() > 0);
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
