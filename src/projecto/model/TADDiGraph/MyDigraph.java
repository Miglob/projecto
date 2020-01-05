/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model.TADDiGraph;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Vertex;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe que implementa os métodos da interface Digraph. Contem a implementação
 * das interfaces Graph e Edge traduzidas em classes privadas.
 *
 * Cria uma colecção que representa a lista de vertices e outra que representa
 * uma lista de arestas
 *
 *
 * @author Miguel lobato
 */
public class MyDigraph<V, E> implements Digraph<V, E>, Serializable {

    private Map<V, Vertex<V>> listVertices;
    private Map<E, Edge<E, V>> listEdges;

    public MyDigraph() {

        listVertices = new HashMap<>();
        listEdges = new HashMap<>();
    }

    @Override
    public Object clone() {
        //return super.clone();
        MyDigraph clone = new MyDigraph();
        Map<V, Vertex<V>> cloneListVertices = new HashMap<>();
        for (V key : listVertices.keySet()) {
            cloneListVertices.put(key, listVertices.get(key));
        }
        clone.setListVertices(cloneListVertices);
        Map<E, Edge<E, V>> cloneListEdges = new HashMap<>();
        for (E key : listEdges.keySet()) {
            cloneListEdges.put(key, listEdges.get(key));
        }
        
        clone.setListEdges(cloneListEdges);
        
        return clone;
    }

    public void setListVertices(Map<V, Vertex<V>> listVertices) {
        this.listVertices = listVertices;
    }

    public void setListEdges(Map<E, Edge<E, V>> listEdges) {
        this.listEdges = listEdges;
    }

    /**
     * Método para obter as arestas incidentes a determinado vertice.
     *
     * @param inbound vertice para obter a aresta incidente
     * @return Retorna uma colecção de arestas incidentes de um vertice. Se não
     * existirem retorna uma colecção vazia.
     * @throws InvalidVertexException erro para vertice errado na chamada dos
     * metodos
     */
    @Override
    public Collection<Edge<E, V>> incidentEdges(Vertex<V> inbound) throws InvalidVertexException {

        if (!this.listVertices.containsValue(inbound)) {
            throw new InvalidVertexException("Vertice não existe.");
        }
        ArrayList<Edge<E, V>> list = new ArrayList<>();
        for (Edge<E, V> edge : listEdges.values()) {
            if (edge.vertices()[0].equals(inbound) || edge.vertices()[1].equals(inbound)) {
                list.add(edge);

            }
        }
        return list;
    }

    /**
     * Método para obter as arestas de saída de um vertice.
     *
     * @param outbound para obter as arestas de saída de um vertice.
     *
     * @return Retorna uma colecção de arestas de saída de um vertice. Se não
     * existirem retorna uma colecção vazia
     * @throws InvalidVertexException erro para vertice errado na chamada dos
     * metodos
     */
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

    /**
     * Metodo para verificar se os vertices são adjacentes
     *
     * @param outbound para obter o vertice com arestas de saída.
     * @param inbound para obter o vertice com as arestas incidentes.
     * @return false se as arestas não são adjacentes.
     * @throws InvalidVertexException erro para vertice errado na chamada dos
     * metodos
     */
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

    /**
     * Metodo para inserir uma aresta entre dois vertices definidos.
     *
     * @param outbound para obter o vertice com arestas de saída.
     * @param inbound para obter o vertice com as arestas incidentes.
     * @param edgeElement a aresta que vai ficar ligada entre os dois vertices.
     * @return a aresta que foi inserida entre os dois vertices.
     * @throws InvalidVertexException erro para vertice errado na chamada dos
     * metodos
     * @throws InvalidEdgeException erro para aresta errada na chamada dos
     * métodos.
     */
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
        MyEdge newEdge = new MyEdge(edgeElement, inbound, outbound);
        listEdges.put(edgeElement, newEdge);
        return newEdge;
    }

    /**
     * Metodo para inserir uma aresta entre dois vertices.
     *
     * @param outboundElement um vertice para a saída da aresta.
     * @param inboundElement um vertice para a aresta que vai incidir.
     * @param edgeElement a aresta a ser colocada entre os dois vertices.
     * @return os vertices com a aresta que foi introduzida.
     * @throws InvalidVertexException erro para vertice errado na chamada dos
     * metodos
     * @throws InvalidEdgeException erro para aresta errada na chamada dos
     * métodos.
     */

    @Override
    public Edge<E, V> insertEdge(V outboundElement, V inboundElement, E edgeElement) throws InvalidVertexException, InvalidEdgeException {

        return insertEdge(listVertices.get(outboundElement), listVertices.get(inboundElement), edgeElement);

    }

    /**
     * Método para calcular o número de vertices do grafo
     *
     * @return Retorna o número de vertices do grafo
     */
    @Override
    public int numVertices() {

        return listVertices.size();

    }

    /**
     * Método para calcular o número de arestas de um grafo.
     *
     * @return Retorna o número de arestas de um grafo.
     */
    @Override
    public int numEdges() {
        return listEdges.size();
    }

    /**
     * Metodo que faz uma iteração sobre uma colecção de vertices.
     *
     * @return Retorna a iteração da colecção de vertices.
     */
    @Override
    public Collection<Vertex<V>> vertices() {

        return listVertices.values();
    }

    /**
     * Método que faz uma iteração sobre uma colecção de arestas.
     *
     * @return Retorna a iteração da colecção de arestas.
     */
    @Override
    public Collection<Edge<E, V>> edges() {

        return listEdges.values();
    }

    /**
     * Metodo que através de um vertice e de uma aresta nos dá o vertice opsto
     * ao primeiro.
     *
     * @param v vertice conhecido.
     * @param e aresta comhecida.
     * @return Retorna o vertice oposto caso ele seja válido.
     * @throws InvalidVertexException erro para vertice errado na chamada dos
     * métodos.
     * @throws InvalidEdgeException erro para aresta errada na chamada dos
     * métodos.
     */

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

    /**
     * Método para obter um dado vertice através da sua chave.
     *
     * @param vElement o vertice desejado.
     * @return o valor de determinado vertice ou null se não existir esta chave.
     * @throws InvalidVertexException erro para vertice errado na chamada dos
     * métodos.
     */

    public Vertex<V> getVertex(V vElement) throws InvalidVertexException {

        return listVertices.get(vElement);
    }

    /**
     * Método para inserir determinado vertice no grafo. Lança uma excepção caso
     * o vertice já exista.
     *
     * @param vElement o vertice a ser inserido.
     * @return o vertice que foi criado e inserido no grafo.
     * @throws InvalidVertexException erro para vertice errado na chamada dos
     * métodos.
     */

    @Override
    public Vertex<V> insertVertex(V vElement) throws InvalidVertexException {

        if (listVertices.containsKey(vElement)) {
            throw new InvalidVertexException(vElement + " já existe");
        }
        MyVertex myVertex = new MyVertex(vElement);
        listVertices.put(vElement, myVertex);
        return myVertex;
    }

    /**
     * Método para remover um vertice de um grafo.
     *
     * @param v o vertice a ser removido.
     * @return Retorna o vertice a ser removido caso seja bem sucedida a
     * operação.
     * @throws InvalidVertexException erro para vertice errado na chamada dos
     * métodos.
     */

    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {

        checkVertex(v);

        for (Edge<E, V> edge : incidentEdges(v)) {
            listEdges.remove(edge.element());
        }
        this.listVertices.remove(v.element());
        return v.element();
    }

    /**
     * Método para remover uma aresta.
     *
     * @param e a aresta a ser removida.
     * @return o elemento que está na aresta.
     * @throws InvalidEdgeException erro para aresta errada na chamada dos
     * métodos.
     */

    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {

        checkEdge(e);

        this.listEdges.remove(e.element());
        return e.element();
    }

    /**
     * Método para substituir um vertice por outro.
     *
     * @param v o vertice existente.
     * @param newElement o novo vertice para substituição.
     * @return Retorna o novo vertice que que foi colocado.
     * @throws InvalidVertexException erro para vertice errado na chamada dos
     * métodos.
     */

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

    /**
     * Método para substituir uma aresta por outra.
     *
     * @param e a aresta existente que se quer substituir.
     * @param newElement a aresta que se quer colocar.
     * @return Retorna a nova aresta colocada.
     * @throws InvalidEdgeException Método para substituir um veMétodortice por
     * outro.
     */
    @Override
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException {

        checkEdge(e);

        Vertex<V>[] vertices = e.vertices();
        MyEdge myEdge = new MyEdge(newElement, vertices[0], vertices[1]);

        listEdges.remove(e.element());

        listEdges.put(newElement, myEdge);

        return newElement;
    }

    /**
     * Metodo para imprimir a quantidade de vertices e arestas existentes no
     * modelo.
     *
     * @return Retorna e imprime para consola a quantidade de arestas e vertices
     * existentes
     */
    @Override
    public String toString() {
        String str = "Edge (" + listEdges.size() + ")\n";
        for (Edge<E, V> edge : listEdges.values()) {
            str += edge.toString() + "\n";
        }

        str += "\nVertex (" + listVertices.size() + ")\n";

        for (Vertex<V> vertex : listVertices.values()) {
            str += vertex.toString() + "\n";
        }

        return str;
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

    private class MyVertex implements Vertex<V>, Serializable {

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
            return 17 * this.elem.hashCode();
        }
    }

    private class MyEdge implements Edge<E, V>, Serializable {

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
            //System.out.printf( "%-15s %15s %n", heading1, heading2);
//            return vertices()[0].toString() + " -> " + elem.toString() + " -> " + vertices()[1].toString();

            return String.format("%-30s -> %-160s -> %-30s", vertices()[0].toString(), elem.toString(), vertices()[1].toString());
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
            int hash = 17 * this.elem.hashCode();
            hash += 17 * this.vertexA.hashCode();
            hash += 17 * this.vertexB.hashCode();
            return hash;
        }
    }
}
