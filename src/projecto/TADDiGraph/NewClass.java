/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.TADDiGraph;

import java.util.Collection;


/**
 *
 * @author Miglob
 */
public class NewClass implements Digraph<Object, Object> {

    @Override
    public Collection<Edge<Object, Object>> incidentEdges(Vertex<Object> inbound) throws InvalidVertexException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Edge<Object, Object>> outboundEdges(Vertex<Object> outbound) throws InvalidVertexException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean areAdjacent(Vertex<Object> outbound, Vertex<Object> inbound) throws InvalidVertexException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Edge<Object, Object> insertEdge(Vertex<Object> outbound, Vertex<Object> inbound, Object edgeElement) throws InvalidVertexException, InvalidEdgeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Edge<Object, Object> insertEdge(Object outboundElement, Object inboundElement, Object edgeElement) throws InvalidVertexException, InvalidEdgeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int numVertices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int numEdges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Vertex<Object>> vertices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Edge<Object, Object>> edges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vertex<Object> opposite(Vertex<Object> v, Edge<Object, Object> e) throws InvalidVertexException, InvalidEdgeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vertex<Object> insertVertex(Object vElement) throws InvalidVertexException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object removeVertex(Vertex<Object> v) throws InvalidVertexException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object removeEdge(Edge<Object, Object> e) throws InvalidEdgeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object replace(Vertex<Object> v, Object newElement) throws InvalidVertexException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object replace(Edge<Object, Object> e, Object newElement) throws InvalidEdgeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
