/**
 * Krudo 0.16a 
 * by Francesco Bianco <bianco@javanile.org>
 */

// utility to parse fen string
package org.krudo;

// required static classes and methods
import static org.krudo.Tool.*;
import static org.krudo.Parse.*;
import static org.krudo.Describe.*;
import static org.krudo.Constants.*;

// fen class utility
public final class Fen 
{		
	// parse fen and apply status into node passed
	public static final void parse(
		final Node node,  // node status position rappresentation apply to
		final String fen  // fen string with position to parse
	) {
		// base status fields
		node.t = w;      // color-side to play
		node.c = 0b1111; // castling status all castling disabled (negative logic)
		node.e = xx;
		node.cw = 0;
		node.cb = 0;
        node.ote = 256;  // no piece on board ending game status is 256
		node.wks = xx;
		node.bks = xx;
		node.hm = 0;
		node.n = 1;
        
        //
        node.L.i = 0;
        
        // clear board matirial 
        for (int p = 0; p < 12; p++) { node.M[p] = 0; }
		        
        // clear board position
        for (int s = 0; s < 64; s++) { node.B[s] = O; }
					
		//
		String[] portion = fen.split("\\s");
		                
		//
		for (int section = 0; section < portion.length; section++) 
        {	
			//
			switch (section) 
            {					
				// position
				case 0: 		
					int s = a8;		
					for (int i=0; i<portion[section].length(); i++) 
                    {					
						switch(portion[section].charAt(i)) 
                        {
							case 'p': node.B[s] = bp; node.M[bp&lo]++; node.cb++; break;
							case 'n': node.B[s] = bn; node.M[bn&lo]++; node.cb++; node.ote -= Eval.OTE[bn & lo]; break;
							case 'b': node.B[s] = bb; node.M[bb&lo]++; node.cb++; node.ote -= Eval.OTE[bb & lo]; break;
							case 'r': node.B[s] = br; node.M[br&lo]++; node.cb++; node.ote -= Eval.OTE[br & lo]; break;
							case 'q': node.B[s] = bq; node.M[bq&lo]++; node.cb++; node.ote -= Eval.OTE[bq & lo]; break;
							case 'k': node.B[s] = bk; node.M[bk&lo]++; node.bks = s; node.cb++; break;
							case 'P': node.B[s] = wp; node.M[wp&lo]++; node.cw++; break;
							case 'N': node.B[s] = wn; node.M[wn&lo]++; node.cw++; node.ote -= Eval.OTE[wn & lo]; break;
							case 'B': node.B[s] = wb; node.M[wb&lo]++; node.cw++; node.ote -= Eval.OTE[wb & lo]; break;
							case 'R': node.B[s] = wr; node.M[wr&lo]++; node.cw++; node.ote -= Eval.OTE[wr & lo]; break;
							case 'Q': node.B[s] = wq; node.M[wq&lo]++; node.cw++; node.ote -= Eval.OTE[wq & lo]; break;
							case 'K': node.B[s] = wk; node.M[wk&lo]++; node.wks = s; node.cw++; break;
							case '/': s = s - 17; break;						
							case '1': s = s + 0; break;
							case '2': s = s + 1; break;
							case '3': s = s + 2; break;
							case '4': s = s + 3; break;
							case '5': s = s + 4; break;
							case '6': s = s + 5; break;
							case '7': s = s + 6; break;
							case '8': s = s + 7; break;								
						}
						s++;
					}
					break;
				
				// turn
				case 1:	
					switch(portion[section].charAt(0)) 
                    {
						case 'b': node.t = b; break;	
						case 'w': node.t = w; break;
					}
					break;
				
				// castling	
				case 2:
					for (int i=0; i<portion[section].length(); i++) 
                    {					
						switch (portion[section].charAt(i)) 
                        {
							case 'K': node.c ^= K___; break;	
							case 'Q': node.c ^= _Q__; break;
							case 'k': node.c ^= __k_; break;	
							case 'q': node.c ^= ___q; break;
						}
					}
					break;					

				// en-passant	
				case 3:
					if (portion[section].charAt(0) != '-') 
                    {
                        //                        
                        int e = parse_square(""+portion[section].charAt(0)+portion[section].charAt(1));
                                                                       
                        // set node enpassant square if is appliable
                        if (node.t == w) 
                        {
                            node.black_domove_enpassant(e);
                        } 
                        
                        //
                        else 
                        {
                            node.white_domove_enpassant(e);
                        } 
					}
					break;					
					
				// half-moves after pawn move or capture	
				case 4:
					node.hm = Integer.parseInt(portion[section]);
					break;					
					
				// from the beginning	
				case 5:
					node.n = Integer.parseInt(portion[section]);
					break;								
			}			
		}	
	}

	//
    public static final String node(Node node)
    {
        String fen = "";

        int o = 0;
        for (int r = 7; r >= 0; r--) {
            for (int c = 0; c < 8; c++) {
                int p = node.B[r * 8 + c];
                if (p == O) {
                    o++;
                } else {
                    if (o > 0) { fen += String.valueOf(o); o = 0; }
                    switch (p) {
                        case wp: fen += "P"; break;
                        case wn: fen += "N"; break;
                        case wb: fen += "B"; break;
                        case wr: fen += "R"; break;
                        case wq: fen += "Q"; break;
                        case wk: fen += "K"; break;
                        case bp: fen += "p"; break;
                        case bn: fen += "n"; break;
                        case bb: fen += "b"; break;
                        case br: fen += "r"; break;
                        case bq: fen += "q"; break;
                        case bk: fen += "k"; break;
                    }
                }
            }
            if (o > 0) { fen += String.valueOf(o); o = 0; }
            fen += r != 0 ? "/" : " ";
        }

        fen += node.t == w ? "w " : "b ";

        return fen;
    }
}
