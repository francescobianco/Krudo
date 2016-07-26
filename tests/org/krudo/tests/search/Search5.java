/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.search;

//
import org.krudo.*;

//
import static org.krudo.Tool.*;
import static org.krudo.Debug.*;

// 
public class Search5 
{
    //
    public static void main(String[] args) 
    {
        //
        PVs.init();
        
        //
        Moves.init();
        
        // create a node to service the search
        Node n = new Node();
        
        //
        n.startpos("8/8/4k3/8/8/8/8/R3K3 w Q");
        
        n.domove("e1d1 e6e5 d1d2 e5e4".split("\\s"));
        
        n.legals();
        dump(n);
        dump(n.m);
        exit();
        
        // create a serach engine based-on the node
        Search s = new Search(n);
     
        //
        try {
            s.start(10, 50000);
        } catch (Exception e) {
            dump(n);        
            dump(n.L);    
            n.legals(); 
            dump(n.m);     
            e.printStackTrace();            
        }
    }    
}
