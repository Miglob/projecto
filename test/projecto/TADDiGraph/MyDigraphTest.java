/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.TADDiGraph;

import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Vertex;
import projecto.model.TADDiGraph.MyDigraph;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miglob
 */
public class MyDigraphTest {

    public MyDigraphTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of incidentEdges method, of class MyDigraph.
     */
    @Test
    public void testIncidentEdges() {
        System.out.println("incidentEdges");
        MyDigraph instance = new MyDigraph();

        Object v1Element = 44;
        Object v2Element = 55;
        Object v3Element = 66;

        Vertex v1 = instance.insertVertex(v1Element);
        Vertex v2 = instance.insertVertex(v2Element);
        Vertex v3 = instance.insertVertex(v3Element);

        int expectedResult = 0;
        int resultV1 = instance.incidentEdges(v1).size();
        int resultV2 = instance.incidentEdges(v2).size();
        int resultV3 = instance.incidentEdges(v3).size();

        assertEquals(expectedResult, resultV1);
        assertEquals(expectedResult, resultV2);
        assertEquals(expectedResult, resultV3);

        Object e1Element = "A";
        Object e2Element = "B";

        Edge e1 = instance.insertEdge(v1, v2, e1Element);
        Edge e2 = instance.insertEdge(v1, v3, e2Element);

        expectedResult = 2;

        resultV1 = instance.incidentEdges(v1).size();

        assertEquals(expectedResult, resultV1);

        expectedResult = 1;

        resultV2 = instance.incidentEdges(v2).size();
        resultV3 = instance.incidentEdges(v3).size();

        assertEquals(expectedResult, resultV2);
        assertEquals(expectedResult, resultV3);
    }

    /**
     * Test of outboundEdges method, of class MyDigraph.
     */
    @Test
    public void testOutboundEdges() {
        System.out.println("outboundEdges");
        MyDigraph instance = new MyDigraph();

        Object v1Element = 1;
        Object v2Element = 2;
        Object v3Element = 3;

        Vertex v1 = instance.insertVertex(v1Element);
        Vertex v2 = instance.insertVertex(v2Element);
        Vertex v3 = instance.insertVertex(v3Element);

        Object edgeElement = "A";
        Edge e1 = instance.insertEdge(v1, v2, edgeElement);
        Edge e2 = instance.insertEdge(v1, v3, edgeElement);
        Edge e3 = instance.insertEdge(v2, v3, edgeElement);

        Collection e1OutboundEdges = instance.outboundEdges(v2);
        Collection e2OutboundEdges = instance.outboundEdges(v3);
        Collection e3OutboundEdges = instance.outboundEdges(v3);

        boolean e1Result = e1OutboundEdges.contains(e1);
        boolean e2Result = e2OutboundEdges.contains(e2);
        boolean e3Result = e3OutboundEdges.contains(e3);

        Object expectedResult = true;
        assertEquals(expectedResult, e1Result);
        assertEquals(expectedResult, e2Result);
        assertEquals(expectedResult, e3Result);
    }

    /**
     * Test of areAdjacent method, of class MyDigraph.
     */
    @Test
    public void testAreAdjacent() {
        System.out.println("areAdjacent");
        MyDigraph instance = new MyDigraph();

        Object v1Element = 1;
        Object v2Element = 2;

        Vertex v1 = instance.insertVertex(v1Element);
        Vertex v2 = instance.insertVertex(v2Element);

        Object edgeElement = "A";
        Edge e = instance.insertEdge(v1, v2, edgeElement);

        Object result = instance.areAdjacent(v1, v2);
        Object expectedResult = true;
        assertEquals(expectedResult, result);
    }

    /**
     * Test of insertEdge method, of class MyDigraph.
     */
    @Test
    public void testInsertEdge_3args_1() {
        System.out.println("insertEdge");
        MyDigraph instance = new MyDigraph();

        Object v1Element = 1;
        Object v2Element = 2;

        Vertex v1 = instance.insertVertex(v1Element);
        Vertex v2 = instance.insertVertex(v2Element);

        Object edgeElement = "A";
        Edge e = instance.insertEdge(v1, v2, edgeElement);

        Object result = e.element();
        Object expectedResult = "A";
        assertEquals(expectedResult, result);
    }

    /**
     * Test of insertEdge method, of class MyDigraph.
     */
    @Test
    public void testInsertEdge_3args_2() {
        System.out.println("insertEdge");
        MyDigraph instance = new MyDigraph();

        Object v1Element = 1;
        Object v2Element = 2;

        Vertex v1 = instance.insertVertex(v1Element);
        Vertex v2 = instance.insertVertex(v2Element);

        Object edgeElement = "A";
        Edge e = instance.insertEdge(v1Element, v2Element, edgeElement);

        Object result = e.element();
        Object expectedResult = "A";
        assertEquals(expectedResult, result);
    }

    /**
     * Test of numVertices method, of class MyDigraph.
     */
    @Test
    public void testNumVertices() {
        System.out.println("numVertices");
        MyDigraph instance = new MyDigraph();

        int expResult = 0;
        int result = instance.numVertices();
        assertEquals(expResult, result);

        Object v1Element = 1;
        Vertex v1 = instance.insertVertex(v1Element);

        expResult = 1;
        result = instance.numVertices();
        assertEquals(expResult, result);
    }

    /**
     * Test of numEdges method, of class MyDigraph.
     */
    @Test
    public void testNumEdges() {
        System.out.println("numEdges");
        MyDigraph instance = new MyDigraph();

        int expResult = 0;
        int result = instance.numEdges();
        assertEquals(expResult, result);

        Object v1Element = 1;
        Object v2Element = 2;

        Vertex v1 = instance.insertVertex(v1Element);
        Vertex v2 = instance.insertVertex(v2Element);

        Object edgeElement = "A";
        Edge e = instance.insertEdge(v1Element, v2Element, edgeElement);

        expResult = 1;
        result = instance.numEdges();
        assertEquals(expResult, result);
    }

    /**
     * Test of vertices method, of class MyDigraph.
     */
    @Test
    public void testVertices() {
        System.out.println("vertices");
        MyDigraph instance = new MyDigraph();

        Object v1Element = 1;
        Vertex v1 = instance.insertVertex(v1Element);

        Collection colection = instance.vertices();
        boolean result = colection.contains(v1);
        boolean expectedResult = true;
        assertEquals(expectedResult, result);

        instance.removeVertex(v1);
        result = colection.contains(v1);
        expectedResult = false;
        assertEquals(expectedResult, result);
    }

    /**
     * Test of edges method, of class MyDigraph.
     */
    @Test
    public void testEdges() {
        System.out.println("edges");
        MyDigraph instance = new MyDigraph();

        Object v1Element = 1;
        Object v2Element = 2;

        Vertex v1 = instance.insertVertex(v1Element);
        Vertex v2 = instance.insertVertex(v2Element);

        Object edgeElement = "A";
        Edge e = instance.insertEdge(v1Element, v2Element, edgeElement);

        Collection colection = instance.edges();
        boolean result = colection.contains(e);
        boolean expectedResult = true;
        assertEquals(expectedResult, result);

        instance.removeEdge(e);
        result = colection.contains(e);
        expectedResult = false;
        assertEquals(expectedResult, result);
    }

    /**
     * Test of opposite method, of class MyDigraph.
     */
    @Test
    public void testOpposite() {
        System.out.println("opposite");
        MyDigraph instance = new MyDigraph();

        Object v1Element = 1;
        Object v2Element = 2;

        Vertex v1 = instance.insertVertex(v1Element);
        Vertex v2 = instance.insertVertex(v2Element);

        Object edgeElement = "A";
        Edge e = instance.insertEdge(v1Element, v2Element, edgeElement);

        Vertex result = instance.opposite(v1, e);
        Vertex expectedResult = v2;
        assertEquals(expectedResult, result);

        result = instance.opposite(v2, e);
        expectedResult = v1;
        assertEquals(expectedResult, result);
    }

    /**
     * Test of insertVertex method, of class MyDigraph.
     */
    @Test
    public void testInsertVertex() {
        System.out.println("insertVertex");
        MyDigraph instance = new MyDigraph();

        int expectedResult = 0;
        int result = instance.numVertices();

        assertEquals(expectedResult, result);

        Object v1Element = 1;
        Vertex v1 = instance.insertVertex(v1Element);

        expectedResult = 1;
        result = instance.numVertices();

        assertEquals(expectedResult, result);
    }

    /**
     * Test of removeVertex method, of class MyDigraph.
     */
    @Test
    public void testRemoveVertex() {
        System.out.println("removeVertex");
        MyDigraph instance = new MyDigraph();

        int expectedResult = 0;
        int result = instance.numVertices();

        assertEquals(expectedResult, result);

        Object v1Element = 1;
        Vertex v1 = instance.insertVertex(v1Element);

        expectedResult = 1;
        result = instance.numVertices();

        assertEquals(expectedResult, result);

        instance.removeVertex(v1);

        expectedResult = 0;
        result = instance.numVertices();

        assertEquals(expectedResult, result);
    }

    /**
     * Test of removeEdge method, of class MyDigraph.
     */
    @Test
    public void testRemoveEdge() {
        System.out.println("removeEdge");
        MyDigraph instance = new MyDigraph();

        int expectedResult = 0;
        int result = instance.numEdges();

        assertEquals(expectedResult, result);

        Object v1Element = 1;
        Object v2Element = 2;

        Vertex v1 = instance.insertVertex(v1Element);
        Vertex v2 = instance.insertVertex(v2Element);

        Object edgeElement = "A";
        Edge e = instance.insertEdge(v1Element, v2Element, edgeElement);

        expectedResult = 1;
        result = instance.numEdges();

        assertEquals(expectedResult, result);

        instance.removeEdge(e);

        expectedResult = 0;
        result = instance.numEdges();

        assertEquals(expectedResult, result);
    }

    /**
     * Test of replace method, of class MyDigraph.
     */
    @Test
    public void testReplace_Vertex_GenericType() {
        System.out.println("replace");
        MyDigraph instance = new MyDigraph();

        Object v1Element = 1;
        Vertex v1 = instance.insertVertex(v1Element);

        Object result = v1.element();
        int expectedResult = 1;
        assertEquals(expectedResult, result);

        result = instance.replace(v1, 2);
        expectedResult = 2;
        assertEquals(expectedResult, result);
    }

    /**
     * Test of replace method, of class MyDigraph.
     */
    @Test
    public void testReplace_Edge_GenericType() {
        System.out.println("replace");
        MyDigraph instance = new MyDigraph();

        Object v1Element = 1;
        Object v2Element = 2;

        Vertex v1 = instance.insertVertex(v1Element);
        Vertex v2 = instance.insertVertex(v2Element);

        Object edgeElement = "A";
        Edge e = instance.insertEdge(v1, v2, edgeElement);

        Object result = e.element();
        String expectedResult = "A";
        assertEquals(expectedResult, result);

        result = instance.replace(e, "B");
        expectedResult = "B";
        assertEquals(expectedResult, result);
    }
}
