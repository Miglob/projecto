/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.TADDiGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Miglob
 */
public class MyDigraph<V, E> implements Digraph<V, E> {

    private Map<V, Vertex<V>> listVertices;
    private Map<E, Edge<E, V>> listEdges;

    public MyDigraph() {

        listVertices = new HashMap<>();
        listEdges = new HashMap<>();
    }

    @Override
    public Collection<Edge<E, V>> incidentEdges(Vertex<V> inbound) throws InvalidVertexException {

        if (!this.listVertices.containsValue(inbound)) {
            throw new InvalidVertexException("Vertice não existe.");
        }
        HashSet<Edge<E, V>> set = new HashSet<>();
        for (Edge<E, V> edge : listEdges.values()) {
            if (edge.vertices()[0].equals(inbound) || edge.vertices()[1].equals(inbound)) {
                set.add(edge);

            }
        }
        return set;
    }

    @Override
    public Collection<Edge<E, V>> outboundEdges(Vertex<V> outbound) throws InvalidVertexException {

        checkVertex(outbound);

        List<Edge<E, V>> outboundEdges = new ArrayList<>();

        for (Edge<E, V> edge : listEdges.values()) {
            Vertex<V>[] vertices = edge.vertices();
            if (vertices[0] == outbound || vertices[1] == outbound) {
                outboundEdges.add(edge);
            }
        }
        return outboundEdges;
    }

    @Override
    public boolean areAdjacent(Vertex<V> outbound, Vertex<V> inbound) throws InvalidVertexException {

        MyVertex myOutbound = checkVertex(outbound);
        MyVertex myInbound = checkVertex(inbound);

        for (Edge<E, V> edge : listEdges.values()) {
            Vertex<V>[] vertices = edge.vertices();
            if ((vertices[0] == outbound && vertices[1] == inbound) || (vertices[1] == outbound && vertices[0] == inbound)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public Edge<E, V> insertEdge(Vertex<V> outbound, Vertex<V> inbound, E edgeElement) throws InvalidVertexException, InvalidEdgeException {

        MyVertex myOutbound = checkVertex(outbound);
        MyVertex myInbound = checkVertex(inbound);

        if (listEdges.containsKey(edgeElement)) {
            Edge<E, V> existingEdge = listEdges.get(edgeElement);
            if (existingEdge.vertices()[0].equals(outbound) && existingEdge.vertices()[1].equals(inbound)) {
                throw new InvalidVertexException("O elemento já está presente na aresta existente");
            }
        }
        MyEdge newEdge = new MyEdge(edgeElement, outbound, inbound);
        listEdges.put(edgeElement, newEdge);
        return newEdge;
    }

    @Override
    public Edge<E, V> insertEdge(V outboundElement, V inboundElement, E edgeElement) throws InvalidVertexException, InvalidEdgeException {

        return insertEdge(listVertices.get(outboundElement), listVertices.get(inboundElement), edgeElement);

    }

    @Override
    public int numVertices() {

        return listVertices.size();

    }

    @Override
    public int numEdges() {
        return listEdges.size();
    }

    @Override
    public Collection<Vertex<V>> vertices() {

        return listVertices.values();
    }

    @Override
    public Collection<Edge<E, V>> edges() {

        return listEdges.values();
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException {

        Vertex<V>[] vertices = checkEdge(e).vertices();
        if (vertices[0] == v) {
            return vertices[1];
        }
        if (vertices[1] == v) {
            return vertices[0];
        }
        throw new InvalidVertexException("vertice inválido");
    }

    @Override
    public Vertex<V> insertVertex(V vElement) throws InvalidVertexException {

        if (listVertices.containsKey(vElement)) {
            throw new InvalidVertexException(vElement + " já existe");
        }
        MyVertex myVertex = new MyVertex(vElement);
        listVertices.put(vElement, myVertex);
        return myVertex;
    }

    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {

        checkVertex(v);

        for (Edge<E, V> edge : incidentEdges(v)) {
            listEdges.remove(edge.element());
        }
        this.listVertices.remove(v.element());
        return v.element();
    }

    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {

        checkEdge(e);

        this.listEdges.remove(e.element());
        return e.element();
    }

    @Override
    public V replace(Vertex<V> v, V newElement) throws InvalidVertexException {

        checkVertex(v);

        MyVertex myVertex = new MyVertex(newElement);

        for (Edge<E, V> edge : incidentEdges(v)) {
            Vertex<V>[] vertices = edge.vertices();
            if (vertices[0].equals(v)) {
                MyEdge myEdge = new MyEdge(edge.element(), myVertex, vertices[1]);
                removeEdge(edge);
                insertEdge(myVertex, vertices[1], myEdge.element());
            }
            if (vertices[1].equals(v)) {
                MyEdge myEdge = new MyEdge(edge.element(), vertices[0], myVertex);
                removeEdge(edge);
                insertEdge(vertices[0], myVertex, myEdge.element());
            }
        }

        return newElement;

    }

    @Override
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException {

        checkEdge(e);

        Vertex<V>[] vertices = e.vertices();
        MyEdge myEdge = new MyEdge(newElement, vertices[0], vertices[1]);

        listEdges.remove(e.element());

        listEdges.put(newElement, myEdge);

        return newElement;
    }

    private MyVertex checkVertex(Vertex<V> p) throws InvalidVertexException {

        if (p == null) {
            throw new InvalidVertexException("vertice errado");
        }
        if (!this.listVertices.containsValue(p)) {
            throw new InvalidVertexException("vertice não existe");
        }
        try {
            return (MyVertex) p;
        } catch (ClassCastException e) {
            throw new InvalidVertexException("vertice errado");
        }
    }

    private MyEdge checkEdge(Edge<E, V> ed) throws InvalidEdgeException {

        if (ed == null) {
            throw new InvalidEdgeException("aresta errada");
        }
        if (!this.listEdges.containsValue(ed)) {
            throw new InvalidEdgeException("aresta inválida");
        }
        try {
            return (MyEdge) ed;
        } catch (ClassCastException e) {
            throw new InvalidEdgeException("aresta errada");
        }
    }

    private class MyVertex implements Vertex<V> {

        private V elem;

        public MyVertex(V elem) {
            this.elem = elem;
        }

        @Override
        public V element() throws InvalidVertexException {

            if (elem == null) {
                throw new InvalidVertexException("Vertice nulo");
            }
            return elem;
        }

        @Override
        public String toString() {
            return elem.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }

            MyVertex vertex = (MyVertex) obj;

            return (vertex.element().equals(this.element()));
        }

        @Override
        public int hashCode() {
            return 17 * this.hashCode();
        }
    }

    private class MyEdge implements Edge<E, V> {

        private E elem;
        private Vertex<V> vertexA, vertexB;

        public MyEdge(E elem, Vertex<V> vertexA, Vertex<V> vertexB) {

            this.elem = elem;
            this.vertexA = vertexA;
            this.vertexB = vertexB;
        }

        @Override
        public E element() throws InvalidEdgeException {

            if (elem == null) {
                throw new InvalidEdgeException("Edge null");
            }
            return elem;

        }

        @Override
        public Vertex<V>[] vertices() {

            Vertex[] vertices = new Vertex[2];
            vertices[0] = vertexA;
            vertices[1] = vertexB;
            return vertices;
        }

        @Override
        public String toString() {
            return elem.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }

            MyEdge edge = (MyEdge) obj;

            return (edge.element() == this.element());
        }

        @Override
        public int hashCode() {
            return 17 * this.hashCode();
        }
    }
}
