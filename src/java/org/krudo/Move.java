/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// required static class
import static org.krudo.Config.*;

// a stack of moves user for legal
public final class Move 
{            
    // constants
    private final static int
    SIZE = 110; 
    
    // counters 
    public int 
    count = 0; // pseudo counter index
    
    // fields
    public final int[] 
    s = new int[SIZE], // start square of a move
    v = new int[SIZE], // versus square of a move
    k = new int[SIZE], // kind of a move
    w = new int[SIZE]; // weight/save-value of a move
     
    // incheck status
    public boolean check;
    
    // empty constructor
    public Move() {}
                        
    // add pseudo-move into stack 
    // used in Node.pseudo()
    public final void add(
        final int s0, 
        final int v0,
        final int k0
    ) {        
        //
        s[count] = s0;
        v[count] = v0;
        k[count] = k0;    
        count++;
    }
    
    // copy move by index i0 from to i1
    public final void copy(
        final int i0, // from index to copy
        final int i1  // destination index to copy
    ) {        
        // exit if no-sense copy
        if (i0 == i1) { return; }
        
        // performe value copy
        s[i1] = s[i0];
        v[i1] = v[i0];
        k[i1] = k[i0];                                
    }

    //
    public final Move sort() 
    {    
        // 
        if (!MOVE_SORT) { return this; }
                
        // swap count
        int z;            

        // 
        do 
        {                                 
            // set swap count to zero
            z = 0;
            
            //
            for (int j = 1; j < count; j++) if (w[j-1] < w[j]) 
            {
                // swap move by index
                swap(j - 1, j);

                // increase swap count
                z++;    
            }                          
        }            
         
        // repeat if not have need to swap
        while (z != 0);
                        
        //
        return this;
    }
    
    // swap by index two moves into stack
    private void swap(
        final int i0, 
        final int i1
    ) {            
        // temp value for swat
        int t;

        //
        t = s[i0]; 
        s[i0] = s[i1]; 
        s[i1] = t;
        
        //
        t = v[i0]; 
        v[i0] = v[i1]; 
        v[i1] = t;
        
        //
        t = k[i0]; 
        k[i0] = k[i1]; 
        k[i1] = t;
        
        //
        t = w[i0]; 
        w[i0] = w[i1]; 
        w[i1] = t;
    }
    
    // duplicate a move-stack instance
    public final Move twin()
    {
        //
        if (!CACHE_LEGALS || !MOVE_TWIN) { return this; }
        
        //
        Move m = Moves.pick();
        
        //
        System.arraycopy(s, 0, m.s, 0, count);
        System.arraycopy(v, 0, m.v, 0, count);
        System.arraycopy(k, 0, m.k, 0, count);
        System.arraycopy(w, 0, m.w, 0, count);
        
        //
        m.check = check;
        
        //
        m.count = count;
        
        //
        return m;
    }
}
