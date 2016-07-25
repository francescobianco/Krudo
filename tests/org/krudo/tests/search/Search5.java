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
        Moves.init();
        
        // create a node to service the search
        Node n = new Node();
        
        //
        n.startpos();
        
        // create a serach engine based-on the node
        Search s = new Search(n);
     
        //
        try {
            s.start(10, 5000);
        } catch (Exception e) {
            dump(n);        
            dump(n.L);     
            dump(n.legals());     
            e.printStackTrace();            
        }
    }    
}
