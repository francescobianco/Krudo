
  /*\
 / + \ Krudo 0.20a - the blasphemy chess engine.
 \IHS/ by Francesco Bianco <bianco@javanile.org>
  \*/

// root package
package org.krudo;

// required static class
import java.io.IOException;

import static org.krudo.Tool.*;
import static org.krudo.Describe.*;

// main class entry point for java application
public final class Krudo 
{      
    // engine store chess game status is chess brain interface         
    public final static Engine ENGINE = new Engine();
    
    // console interface with "standard i/o" management
    public final static Console CONSOLE = new Console(); 
                        
    // entry-point for console application
    public static void main(String[] args) 
    {        
        // prepare and start input loop of engine 
        try 
        {    
            // initialize console with log file
            CONSOLE.start(path("krudo.txt"));

            // credits message
            CONSOLE.print("Krudo 0.20a by Francesco Bianco <bianco@javanile.org>");
                  
            // init warmup memory and cache
            init();
            
            // standard input loop
            loop();                    
        } 
        
        // exception catched print in console
        catch (Throwable e) 
        {            
            //
            CONSOLE.error(e);    
            
            //
            CONSOLE.print(desc(ENGINE.search.node));
            
            //
            ENGINE.search.node.legals();
            
            //
            CONSOLE.print(desc(ENGINE.search.node.legals));            
        } 
                
        // exit and close console
        finally 
        {            
            // close and flush all
            CONSOLE.close();            
        }    
    }
    
    //
    public static void init() 
    {
        //
        TT.init();
        
        // PV memory
        PVs.init();
        
        // prepare save constatnts
        Eval.init();

        // prepare move-stacks
        Moves.init();

        // prepare legals cache
        Legals.init();

        // prepare captures
        Captures.init();
    }
    
    //
    private static void loop() throws IOException
    {                
        // do input wait loop
        for (;;) 
        {    
            // parse and read input from stdin
            UCI i = UCI.parse(CONSOLE.input());

            // switch based on parsed command
            switch (i.cmd)
            {
                // start uci session
                case UCI.UCI:    

                    // initialize thinker
                    ENGINE.init();

                    // set search callback function in onDone-event search
                    ENGINE.search.send_info = UCI.SEND_INFO;
                    ENGINE.search.send_move = UCI.SEND_MOVE;
                   
                    // uci first message
                    CONSOLE.print(UCI.ID_NAME, "Krudo 0.16a");
                    CONSOLE.print(UCI.ID_AUTHOR, "Francesco Bianco");

                    // uci is ready to receive command
                    CONSOLE.print(UCI.UCIOK);

                    // break switch
                    break;

                // isready request
                case UCI.ISREADY:

                    // print out im ready
                    if (ENGINE.isReady())
                    {
                        CONSOLE.print(UCI.READYOK);
                    }

                    // break switch
                    break;

                // set thinker to start position
                case UCI.UCINEWGAME:

                    // set to start position
                    ENGINE.startpos();

                    // break switch
                    break;

                // set thinker to start position
                case UCI.POSITION_FEN:

                    // set to start position
                    ENGINE.startpos(i.arg[0]);
                    
                    //
                    if (i.arg.length > 1)
                    {
                        //
                        ENGINE.domove(i.arg, 1);
                    }

                    // break switch
                    break;

                // set thinker to start position
                case UCI.POSITION_STARTPOS:

                    // set to start position
                    ENGINE.startpos();

                    // break switch
                    break;

                // set thinker to start position e do move sequences    
                case UCI.POSITION_STARTPOS_MOVES:

                    //
                    ENGINE.startpos();

                    //
                    ENGINE.domove(i.arg);

                    //
                    CONSOLE.log(desc(ENGINE.search.node));
                    
                    // break switch
                    break;

                // start thinking based on args passed    
                case UCI.GO:                        

                    // call go with black and white time attentions
                    if (has(i.arg[UCI.WTIME]))
                    {
                        ENGINE.wtime = toLong(i.arg[UCI.WTIME]); 
                    }
                     
                    //
                    if (has(i.arg[UCI.BTIME])) 
                    {
                        ENGINE.btime = toLong(i.arg[UCI.BTIME]);
                    } 

                    //
                    ENGINE.go();

                    // break switch                        
                    break;

                // quit from main loop    
                case UCI.QUIT: return;
            }
        } 
    }
}
