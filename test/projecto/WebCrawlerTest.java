/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projecto.TADDiGraph.MyDigraph;

/**
 *
 * @author Miglob
 */
public class WebCrawlerTest {
    
    public WebCrawlerTest() {
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
     * Test of getDigraph method, of class WebCrawler.
     */
    @Test
    public void testGetDigraph() {
        System.out.println("getDigraph");
        WebCrawler instance = new WebCrawler();
        
        MyDigraph graph = instance.getDigraph();
        boolean result = graph instanceof MyDigraph;        
        boolean expResult = true;
        
        assertEquals(expResult, result);
    }

    /**
     * Test of automaticModelCreation method, of class WebCrawler.
     */
    @Test
    public void testAutomaticModelCreation() throws Exception {
        System.out.println("automaticModelCreation");
        
        WebCrawler instance = new WebCrawler();
        
        instance.automaticModelCreation("https://www.google.com", 10);
        
        int vertexResult = instance.getDigraph().numVertices();
        int expResult = 10;
        
        assertEquals(vertexResult, expResult);
        
        int edgeResult = instance.getDigraph().numEdges();
        expResult = 9;
        
        assertEquals(edgeResult, expResult);
    }
    
}
