/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo.tests.legals;

//
import org.krudo.*;

//
import static org.krudo.util.Debug.*;
    
//
public class Legals3 
{
    //
    public static void main(String[] args) 
    {    
        //
        Moves.init();

        //
        Node n = new Node();

        //
        n.startpos();
                        
        //
        dump(n.legals());

        //
        dump(n.legals());
        
        //
        Legals.dump();
    }
}