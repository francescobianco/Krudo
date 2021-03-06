/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

//
import static org.krudo.Tool.*;
import static org.krudo.Parse.*;
import static org.krudo.Encode.*;
import static org.krudo.Decode.*;
import static org.krudo.Constants.*;

//
public class Describe
{
    // piece to string
	public static final String piece(
        final int p
    ) {		
		//
		switch (p) 
        {
			case O:  return ".";			
			case wp: return "wp";	
			case wn: return "wn";	
			case wb: return "wb";	
			case wr: return "wr";	
			case wq: return "wq";	
			case wk: return "wk";	
			case bp: return "bp";	
			case bn: return "bn";	
			case bb: return "bb";	
			case br: return "br";	
			case bq: return "bq";	
			case bk: return "bk";						
			default: return "?";	
		}	
	} 
	    
    // square to string
	public static final String square(
        final int s
    ) {		
		//
		return s>=0 && s<64 ? ""+(char)('a'+s%8)+(char)('1'+s/8) : "xx";		
	}  
        
    //
    public final static String algebric(
        final Node n, 
        final String m
    ) {
        //
        String a = "";
        
        // parse move parts and retrieve s,v,k
        int s = parse_square(m.substring(0, 2));
        int v = parse_square(m.substring(2, 4));
        int k = parse_kind_of_move(m, n.B[s], s, v, n.B[v], n.c, n.e, n.t);  
                
        // castling move
        if (k == KSCA) { a = "O-O"; }
            
        //
        else if (k == QSCA) { a = "O-O-O"; }            
        
        // figurine
        else if (n.B[s] != wp && n.B[s] != bp) { a = f2s(n.B[s]); }
                 
        //
        if (n.B[v] != O) 
        {
            //
            if (n.B[s] == wp || n.B[s] == bp)
            {
                //
                a += ""+(char)('a'+s%8);
            }
            
            //
            a += "x";
        }
        
        //
        a += s2s(v);
        
        //
        n.domove(m);
        
        //
        n.legals();
        
        //
        if (n.legals.check) 
        {
            a += "+";        
        }
        
        //
        n.unmove();
                
        //
        return a;
    }
        
	//
	public final static String desc(
		final Move m, 		
		final int i,
		final Node n
	) {	
		//
		String move = "";
		
		//
		final int p = n.B[m.s[i]];
		
		//
		final int x = n.B[m.v[i]];

		//
		if (m.k[i] != KSCA) {
			
			if (p != wp && p != bp) 
            {
				move += f2s(p);
			} 

			if (x != 0) 
            {
				move += "x";
			}

			move += s2s(m.v[i]);

			/*
			if (x != 0) {
				a += " ("+i2f(x)+")";
			}
			*/
		} 

		//
		else if (false) 
        {
			move += "O-O";
		} 

		//
		else if (false) 
        {
			move += "O-O-O";
		}

		//
		//n.domove(m,i);
		//if (n.t==w?n.black_attack(n.wks):n.white_attack(n.bks)) {
		//	a += "+";
		//}
		//n.unmove();
		
		//
		//if (DEBUG_SHOW_MOVE_WEIGHT) 
        //{
		//	move += String.format("(%+d)", m.w[i]);
		//}
		
		//
		return move;	
	}

	//
	public final static String desc(Node n) 
    {
		//
		String desc = "";
		
		//
		for (int r = 7; r >= 0; r--) 
        {			
			//
			for(int c = 0; c < 8; c++) 
            {
				desc += Decode.piece(n.B[cr2i(c, r)]) + " ";
			}
			
			//
			desc += (r==0 && n.t==w || r==7 && n.t==b ? "<  " : "   ");
			
			//
			switch (r)
            {
				//case 0: keys("e:",i2s(n.e),"c:",Integer.toBinaryString(n.c)); break;
				//case 2: keys("wks:",i2s(n.wks),"bks:",i2s(n.bks)); break;
				//case 3: keys("wrs:",i2s(n.wks),"brs:",i2s(n.bks)); break;
				//case 4: keys("ph:",n.cw,"ew:",n.wks); break;
				case 4: desc += "c: "+bin(n.c, 4)+" e: "+square(n.e); break;
				case 3: desc += "M: "+n.M[0]+" "+n.M[1]+" "+n.M[2]+" "+n.M[3]
                                 +" "+n.M[4]+" "+n.M[5]+" "+n.M[6]+" "+n.M[7]
                                 +" "+n.M[8]+" "+n.M[9]+" "+n.M[10]+" "+n.M[11]; break;
				case 2: desc += "cw: "+n.cw+"  cb: "+n.cb+"  ote: "+n.ote; break;
				case 1: desc += "phk: " + Long.toHexString(n.phk); break;					
				case 0: desc += "mhk: " + Long.toHexString(n.mhk); break;					
			}
			
			//
			desc += "\n";
		}
		
		//
		return desc;
	}
    
    //
	public final static String desc(PV pv) 
    {
        //
		String desc = "";
		
        //
        String s = "";		
		
        //
        for (int i=0; i<pv.i; i++)
        {
			desc += s + m2s(pv.s[i], pv.v[i], pv.k[i]); 
			s = " ";
		}
        
        //
		return desc;		
	}
	
	//
	public final static String desc(Line line) 
    {
        //
		String desc = "";
		
        //
        String s = "";		
		
        //
        for (int i=0; i<line.i; i++)
        {
			desc += s + //hex(line.phk[i]) + ": " +
					m2s(line.s[i], line.v[i], line.k[i]);
			s = " ";
		}
        
        //
		return desc;		
	}
	
	//
	public final static String desc(Move m) 
    {
		//
		String desc = "";
		
		//
		String sepa = "";
		
        //
        int cell = //DEBUG_SHOW_MOVE_WEIGHT ? 10 : 
                    6;
			
        //
        int colm = //DEBUG_SHOW_MOVE_WEIGHT ? 4 : 
                    6;    
        
		//
		for (int i = 0; i < m.count; i++)
        {	
			//
			String move = desc(m, i);
										
			//
			desc += sepa + lpad(move, cell);
			
			//
			sepa = i % colm == (colm - 1) ? "\n" : " ";
		}	
		
		//
		return desc;
	}

    //
	public final static String desc(final Capture c) 
    {
		//
		String desc = "";
		
		//
		String sepa = "";
		
        //
        int cell = //DEBUG_SHOW_MOVE_WEIGHT ? 10 : 
                    6;
			
        //
        int colm = //DEBUG_SHOW_MOVE_WEIGHT ? 4 : 
                    6;    
        
		//
		for (int i = 0; i < c.count; i++)
        {	
			//
			String move = desc(c, i);
										
			//
			desc += sepa + lpad(move, cell);
			
			//
			sepa = i % colm == (colm - 1) ? "\n" : " ";
		}	
		
		//
		return desc;
	}

	//
	public final static String desc(Move m, Node n) {
	
		//
		String desc = "";
		
		//
		String sepa = "";
		
		//
		for (int j = 0; j < m.count; j++) {
			
			//
			String move = desc(m, j);
										
			//
			int cell = //DEBUG_SHOW_MOVE_WEIGHT ? 10 : 
                        6;
			
			//
			desc += sepa + lpad(move, cell);
			
			//
			sepa = j % 6 == 5 ? "\n" : " ";
		}	
		
		//
		return desc;
	}
	
	//
	public final static String desc(
        final Node n, 
        final Move m
    ) {	
		//
		String desc = "";
		
		//
		for (int r = 0; r < 8; r++) {
			
			//
			for (int c = 0; c < 8; c++) {
				desc += Decode.piece(n.B[(7-r)*8+c]) + " ";
			}
			
			//
			desc += r==0 && n.t==b || r==7 && n.t==w ? "<  " : "   ";
			
			//
			for(int i = r * 4; i < r * 4 + 4; i++) {
				if (i < m.count) {
					desc += lpad(desc(m, i), 11);
				}
			}
			
			/*
			switch(r) {
				case 0: keys("e:",i2s(n.e),"c:",Integer.toBinaryString(n.c)); break;
				case 1: keys("cw:",n.cw,"cb:",n.cb); break;
				case 2: keys("wks:",i2s(n.wks),"bks:",i2s(n.bks)); break;
				case 3: keys("wrs:",i2s(n.wks),"brs:",i2s(n.bks)); break;
				case 4: keys("ph:",n.cw,"ew:",n.wks); break;
				case 5: keys("wpw:",n.wks,"bpw:",n.wks); break;
				case 6: keys("hm:",n.hm,"n:",n.n); break;
				case 7: keys("h:",Long.toHexString(hash(n))); break;					
			}
			*/
			
			desc += "\n";
		}		
					
		//
		return desc;
	}
		
	//
	public final static String desc(		
		final Move m, 
		final int i
	) {
		//
		return m2s(m.s[i], m.v[i], m.k[i], m.w[i]);
	}
    
    //
	public final static String desc(		
		final PV pv, 
		final int i
	) {
		//
		return m2s(pv.s[i], pv.v[i], pv.k[i]);
	}
    
    //
	public final static String desc(		
		final Capture c, 
		final int i
	) {
		//
		return m2s(c.s[i], c.v[i], c.k[i], c.w[i]);
	}
}
