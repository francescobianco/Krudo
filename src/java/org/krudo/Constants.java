/**
 * Krudo 0.16a - a chess engine for cooks
 * by Francesco Bianco <bianco@javanile.org>
 */

//
package org.krudo;

// all constants
import static org.krudo.Describe.desc;
import static org.krudo.Tool.print;
import static org.krudo.Tool.rpad;

//
import java.util.function.Consumer;

//
public final class Constants
{    
    // start position in fen
    public final static String
    STARTPOS = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    
    // piece redundant piece code 
    public final static int 
    O = 0b0__000000__000000000000, // empty no piece        
    P = 0b0__000000__100000100000, // pawn             
    N = 0b0__000000__100010100010, // knight 
    B = 0b0__000000__100100100100, // bishop 
    R = 0b0__000000__100110100110, // rook 
    Q = 0b0__000000__101000101000, // queen 
    K = 0b0__000000__101010101010; // king 
        
    // colors and side to move turn 
    public final static int             
    w = 0b0__000000__000001000001, // white side move "turn"
    b = 0b0__000000__010000010000, // black side move "turn"
    T = 0b0__000000__010001010001; // side move switch "turn swap"
    
    // white piece
    public final static int 
    wp = w | P, wn = w | N, wb = w | B,
    wr = w | R, wq = w | Q, wk = w | K;
    
    // black piece
    public final static int 
    bp = b | P, bn = b | N, bb = b | B,
    br = b | R, bq = b | Q, bk = b | K;
    
    // piece index mapper HI map and LOW map
    public final static int
    lo = 0b0__000000__000000001111, // piece index mask        
    hi = 0b0__000000__001111000000, // piece index mask    
    pi = 0b0__000000__111111111111; // extract piece from "k"

    // special constant for "k"
    public final static int
    MOVE = 0b0__000001__000000000000, // normal move    
    KSCA = 0b0__000010__000000000000, // king side castling move
    QSCA = 0b0__000100__000000000000, // queen side castling move
    PDMO = 0b0__001000__000000000000, // pawn double move
    ECAP = 0b0__010000__000000000000, // en-passant capture move        
    PROM = 0b0__100000__000000000000; // promotion move    
    
    // kind of moves
    public final static int 
    KMOV = MOVE | K,
    RKMO = KSCA | R,
    RQMO = QSCA | R,
    WQPM = PROM | wq, 
    WRPM = PROM | wr,
    WBPM = PROM | wb,            
    WNPM = PROM | wn,
    BQPM = PROM | bq, 
    BRPM = PROM | br,
    BBPM = PROM | bb,            
    BNPM = PROM | bn;
        
    // castling flags "c values"
    public final static int 
    KQ__ = 0b0__0011, 
    __kq = 0b0__1100, 
    K___ = 0b0__0001, 
    _Q__ = 0b0__0010, 
    __k_ = 0b0__0100, 
    ___q = 0b0__1000; 
                
    // squares
    public final static int 
    a8 = 56, b8 = 57, c8 = 58, d8 = 59, e8 = 60, f8 = 61, g8 = 62, h8 = 63,
    a7 = 48, b7 = 49, c7 = 50, d7 = 51, e7 = 52, f7 = 53, g7 = 54, h7 = 55,
    a6 = 40, b6 = 41, c6 = 42, d6 = 43, e6 = 44, f6 = 45, g6 = 46, h6 = 47, 
    a5 = 32, b5 = 33, c5 = 34, d5 = 35, e5 = 36, f5 = 37, g5 = 38, h5 = 39, 
    a4 = 24, b4 = 25, c4 = 26, d4 = 27, e4 = 28, f4 = 29, g4 = 30, h4 = 31,     
    a3 = 16, b3 = 17, c3 = 18, d3 = 19, e3 = 20, f3 = 21, g3 = 22, h3 = 23, 
    a2 = 8,  b2 = 9,  c2 = 10, d2 = 11, e2 = 12, f2 = 13, g2 = 14, h2 = 15, 
    a1 = 0,  b1 = 1,  c1 = 2,  d1 = 3,  e1 = 4,  f1 = 5,  g1 = 6,  h1 = 7;

    // out-of-board symbols
    public final static int xx = 64;

    // cardinals for span smapims 
    public final static int 
    NW = 7, NN = 1, NE = 6,
    WW = 3,         EE = 2,
    SW = 4, SS = 0, SE = 5;
                
    // smapims for span
    public final static int[][] SPAN = { 
        /*a1:*/{xx, a2, b1, xx, xx, xx, b2, xx}, 
        /*b1:*/{xx, b2, c1, a1, xx, xx, c2, a2}, 
        /*c1:*/{xx, c2, d1, b1, xx, xx, d2, b2}, 
        /*d1:*/{xx, d2, e1, c1, xx, xx, e2, c2}, 
        /*e1:*/{xx, e2, f1, d1, xx, xx, f2, d2}, 
        /*f1:*/{xx, f2, g1, e1, xx, xx, g2, e2}, 
        /*g1:*/{xx, g2, h1, f1, xx, xx, h2, f2}, 
        /*h1:*/{xx, h2, xx, g1, xx, xx, xx, g2}, 
        /*a2:*/{a1, a3, b2, xx, xx, b1, b3, xx}, 
        /*b2:*/{b1, b3, c2, a2, a1, c1, c3, a3}, 
        /*c2:*/{c1, c3, d2, b2, b1, d1, d3, b3}, 
        /*d2:*/{d1, d3, e2, c2, c1, e1, e3, c3}, 
        /*e2:*/{e1, e3, f2, d2, d1, f1, f3, d3}, 
        /*f2:*/{f1, f3, g2, e2, e1, g1, g3, e3}, 
        /*g2:*/{g1, g3, h2, f2, f1, h1, h3, f3}, 
        /*h2:*/{h1, h3, xx, g2, g1, xx, xx, g3}, 
        /*a3:*/{a2, a4, b3, xx, xx, b2, b4, xx}, 
        /*b3:*/{b2, b4, c3, a3, a2, c2, c4, a4}, 
        /*c3:*/{c2, c4, d3, b3, b2, d2, d4, b4}, 
        /*d3:*/{d2, d4, e3, c3, c2, e2, e4, c4}, 
        /*e3:*/{e2, e4, f3, d3, d2, f2, f4, d4}, 
        /*f3:*/{f2, f4, g3, e3, e2, g2, g4, e4}, 
        /*g3:*/{g2, g4, h3, f3, f2, h2, h4, f4}, 
        /*h3:*/{h2, h4, xx, g3, g2, xx, xx, g4}, 
        /*a4:*/{a3, a5, b4, xx, xx, b3, b5, xx}, 
        /*b4:*/{b3, b5, c4, a4, a3, c3, c5, a5}, 
        /*c4:*/{c3, c5, d4, b4, b3, d3, d5, b5}, 
        /*d4:*/{d3, d5, e4, c4, c3, e3, e5, c5}, 
        /*e4:*/{e3, e5, f4, d4, d3, f3, f5, d5}, 
        /*f4:*/{f3, f5, g4, e4, e3, g3, g5, e5}, 
        /*g4:*/{g3, g5, h4, f4, f3, h3, h5, f5}, 
        /*h4:*/{h3, h5, xx, g4, g3, xx, xx, g5}, 
        /*a5:*/{a4, a6, b5, xx, xx, b4, b6, xx}, 
        /*b5:*/{b4, b6, c5, a5, a4, c4, c6, a6}, 
        /*c5:*/{c4, c6, d5, b5, b4, d4, d6, b6}, 
        /*d5:*/{d4, d6, e5, c5, c4, e4, e6, c6}, 
        /*e5:*/{e4, e6, f5, d5, d4, f4, f6, d6}, 
        /*f5:*/{f4, f6, g5, e5, e4, g4, g6, e6}, 
        /*g5:*/{g4, g6, h5, f5, f4, h4, h6, f6}, 
        /*h5:*/{h4, h6, xx, g5, g4, xx, xx, g6}, 
        /*a6:*/{a5, a7, b6, xx, xx, b5, b7, xx}, 
        /*b6:*/{b5, b7, c6, a6, a5, c5, c7, a7}, 
        /*c6:*/{c5, c7, d6, b6, b5, d5, d7, b7}, 
        /*d6:*/{d5, d7, e6, c6, c5, e5, e7, c7}, 
        /*e6:*/{e5, e7, f6, d6, d5, f5, f7, d7}, 
        /*f6:*/{f5, f7, g6, e6, e5, g5, g7, e7}, 
        /*g6:*/{g5, g7, h6, f6, f5, h5, h7, f7}, 
        /*h6:*/{h5, h7, xx, g6, g5, xx, xx, g7}, 
        /*a7:*/{a6, a8, b7, xx, xx, b6, b8, xx}, 
        /*b7:*/{b6, b8, c7, a7, a6, c6, c8, a8}, 
        /*c7:*/{c6, c8, d7, b7, b6, d6, d8, b8}, 
        /*d7:*/{d6, d8, e7, c7, c6, e6, e8, c8}, 
        /*e7:*/{e6, e8, f7, d7, d6, f6, f8, d8}, 
        /*f7:*/{f6, f8, g7, e7, e6, g6, g8, e8}, 
        /*g7:*/{g6, g8, h7, f7, f6, h6, h8, f8}, 
        /*h7:*/{h6, h8, xx, g7, g6, xx, xx, g8}, 
        /*a8:*/{a7, xx, b8, xx, xx, b7, xx, xx}, 
        /*b8:*/{b7, xx, c8, a8, a7, c7, xx, xx}, 
        /*c8:*/{c7, xx, d8, b8, b7, d7, xx, xx}, 
        /*d8:*/{d7, xx, e8, c8, c7, e7, xx, xx}, 
        /*e8:*/{e7, xx, f8, d8, d7, f7, xx, xx}, 
        /*f8:*/{f7, xx, g8, e8, e7, g7, xx, xx}, 
        /*g8:*/{g7, xx, h8, f8, f7, h7, xx, xx}, 
        /*h8:*/{h7, xx, xx, g8, g7, xx, xx, xx}
    };

    // smapims for hope
    public final static int[][] HOPE = { 
        /*a1:*/{b3, c2, xx, xx, xx, xx, xx, xx}, 
        /*b1:*/{a3, c3, d2, xx, xx, xx, xx, xx}, 
        /*c1:*/{a2, b3, d3, e2, xx, xx, xx, xx}, 
        /*d1:*/{b2, c3, e3, f2, xx, xx, xx, xx}, 
        /*e1:*/{c2, d3, f3, g2, xx, xx, xx, xx}, 
        /*f1:*/{d2, e3, g3, h2, xx, xx, xx, xx}, 
        /*g1:*/{e2, f3, h3, xx, xx, xx, xx, xx}, 
        /*h1:*/{f2, g3, xx, xx, xx, xx, xx, xx}, 
        /*a2:*/{b4, c1, c3, xx, xx, xx, xx, xx}, 
        /*b2:*/{a4, c4, d1, d3, xx, xx, xx, xx}, 
        /*c2:*/{a1, a3, b4, d4, e1, e3, xx, xx}, 
        /*d2:*/{b1, b3, c4, e4, f1, f3, xx, xx}, 
        /*e2:*/{c1, c3, d4, f4, g1, g3, xx, xx}, 
        /*f2:*/{d1, d3, e4, g4, h1, h3, xx, xx}, 
        /*g2:*/{e1, e3, f4, h4, xx, xx, xx, xx}, 
        /*h2:*/{f1, f3, g4, xx, xx, xx, xx, xx}, 
        /*a3:*/{b1, b5, c2, c4, xx, xx, xx, xx}, 
        /*b3:*/{a1, a5, c1, c5, d2, d4, xx, xx}, 
        /*c3:*/{a2, a4, b1, b5, d1, d5, e2, e4}, 
        /*d3:*/{b2, b4, c1, c5, e1, e5, f2, f4}, 
        /*e3:*/{c2, c4, d1, d5, f1, f5, g2, g4}, 
        /*f3:*/{d2, d4, e1, e5, g1, g5, h2, h4}, 
        /*g3:*/{e2, e4, f1, f5, h1, h5, xx, xx}, 
        /*h3:*/{f2, f4, g1, g5, xx, xx, xx, xx}, 
        /*a4:*/{b2, b6, c3, c5, xx, xx, xx, xx}, 
        /*b4:*/{a2, a6, c2, c6, d3, d5, xx, xx}, 
        /*c4:*/{a3, a5, b2, b6, d2, d6, e3, e5}, 
        /*d4:*/{b3, b5, c2, c6, e2, e6, f3, f5}, 
        /*e4:*/{c3, c5, d2, d6, f2, f6, g3, g5}, 
        /*f4:*/{d3, d5, e2, e6, g2, g6, h3, h5}, 
        /*g4:*/{e3, e5, f2, f6, h2, h6, xx, xx}, 
        /*h4:*/{f3, f5, g2, g6, xx, xx, xx, xx}, 
        /*a5:*/{b3, b7, c4, c6, xx, xx, xx, xx}, 
        /*b5:*/{a3, a7, c3, c7, d4, d6, xx, xx}, 
        /*c5:*/{a4, a6, b3, b7, d3, d7, e4, e6}, 
        /*d5:*/{b4, b6, c3, c7, e3, e7, f4, f6}, 
        /*e5:*/{c4, c6, d3, d7, f3, f7, g4, g6}, 
        /*f5:*/{d4, d6, e3, e7, g3, g7, h4, h6}, 
        /*g5:*/{e4, e6, f3, f7, h3, h7, xx, xx}, 
        /*h5:*/{f4, f6, g3, g7, xx, xx, xx, xx}, 
        /*a6:*/{b4, b8, c5, c7, xx, xx, xx, xx}, 
        /*b6:*/{a4, a8, c4, c8, d5, d7, xx, xx}, 
        /*c6:*/{a5, a7, b4, b8, d4, d8, e5, e7}, 
        /*d6:*/{b5, b7, c4, c8, e4, e8, f5, f7}, 
        /*e6:*/{c5, c7, d4, d8, f4, f8, g5, g7}, 
        /*f6:*/{d5, d7, e4, e8, g4, g8, h5, h7}, 
        /*g6:*/{e5, e7, f4, f8, h4, h8, xx, xx}, 
        /*h6:*/{f5, f7, g4, g8, xx, xx, xx, xx}, 
        /*a7:*/{b5, c6, c8, xx, xx, xx, xx, xx}, 
        /*b7:*/{a5, c5, d6, d8, xx, xx, xx, xx}, 
        /*c7:*/{a6, a8, b5, d5, e6, e8, xx, xx}, 
        /*d7:*/{b6, b8, c5, e5, f6, f8, xx, xx}, 
        /*e7:*/{c6, c8, d5, f5, g6, g8, xx, xx}, 
        /*f7:*/{d6, d8, e5, g5, h6, h8, xx, xx}, 
        /*g7:*/{e6, e8, f5, h5, xx, xx, xx, xx}, 
        /*h7:*/{f6, f8, g5, xx, xx, xx, xx, xx}, 
        /*a8:*/{b6, c7, xx, xx, xx, xx, xx, xx}, 
        /*b8:*/{a6, c6, d7, xx, xx, xx, xx, xx}, 
        /*c8:*/{a7, b6, d6, e7, xx, xx, xx, xx}, 
        /*d8:*/{b7, c6, e6, f7, xx, xx, xx, xx}, 
        /*e8:*/{c7, d6, f6, g7, xx, xx, xx, xx}, 
        /*f8:*/{d7, e6, g6, h7, xx, xx, xx, xx}, 
        /*g8:*/{e7, f6, h6, xx, xx, xx, xx, xx}, 
        /*h8:*/{f7, g6, xx, xx, xx, xx, xx, xx}, 
    };
      
    //
    public final static int[][] LINK = new int[][] {
        /*a1:*/{1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,},
        /*b1:*/{1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,},
        /*c1:*/{1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,},
        /*d1:*/{1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,},
        /*e1:*/{1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,},
        /*f1:*/{1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,},
        /*g1:*/{1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,},
        /*h1:*/{1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,1,},
        /*a2:*/{1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,},
        /*b2:*/{1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,},
        /*c2:*/{1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,},
        /*d2:*/{0,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,},
        /*e2:*/{0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,},
        /*f2:*/{0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,},
        /*g2:*/{0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,0,1,0,0,0,0,0,1,0,},
        /*h2:*/{0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,0,1,0,0,0,0,0,1,},
        /*a3:*/{1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,},
        /*b3:*/{1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,},
        /*c3:*/{1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,},
        /*d3:*/{0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,},
        /*e3:*/{0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,},
        /*f3:*/{0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,},
        /*g3:*/{0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,0,},
        /*h3:*/{0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,},
        /*a4:*/{1,0,0,1,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,},
        /*b4:*/{0,1,0,0,1,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,},
        /*c4:*/{0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,},
        /*d4:*/{1,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,},
        /*e4:*/{0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,1,1,0,0,0,1,0,0,0,},
        /*f4:*/{0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,},
        /*g4:*/{0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,},
        /*h4:*/{0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,},
        /*a5:*/{1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,},
        /*b5:*/{0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,},
        /*c5:*/{0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,1,0,0,1,0,0,},
        /*d5:*/{0,0,0,1,0,0,0,1,1,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,},
        /*e5:*/{1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,1,},
        /*f5:*/{0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,},
        /*g5:*/{0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,1,0,0,1,0,},
        /*h5:*/{0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,1,0,0,1,},
        /*a6:*/{1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,},
        /*b6:*/{0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,},
        /*c6:*/{0,0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,},
        /*d6:*/{0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,},
        /*e6:*/{0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,},
        /*f6:*/{1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,},
        /*g6:*/{0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,},
        /*h6:*/{0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,},
        /*a7:*/{1,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,},
        /*b7:*/{0,1,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,},
        /*c7:*/{0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,},
        /*d7:*/{0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,0,},
        /*e7:*/{0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,0,},
        /*f7:*/{0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,},
        /*g7:*/{1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,},
        /*h7:*/{0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,},
        /*a8:*/{1,0,0,0,0,0,0,1,1,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,},
        /*b8:*/{0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,},
        /*c8:*/{0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,},
        /*d8:*/{0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,},
        /*e8:*/{0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,},
        /*f8:*/{0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,},
        /*g8:*/{0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,},
        /*h8:*/{1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,},
    };
        
    // magic constants
    public final static int oo = 300000;
    public final static int checkmate = 100000;
    public final static int stalemate = 0;
        
    // time constants
    public final static long
    TIME_5_SECONDS  = 5 * 1000,
    TIME_30_SECONDS = 30 * 1000,
    TIME_5_MINUTES  = 5 * 60 * 1000;
    
    // string constants
    public final static char NEWLINE = '\n';
}
