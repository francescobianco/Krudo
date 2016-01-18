package org.krudo.test.base;

//
import static org.krudo.utils.Debug.*;
import static org.krudo.utils.Tools.*;

// 
import org.krudo.Node;
import org.krudo.Move;

// 
public class Move1 {

	//
	public static void main(String[] args) {
		
		Move l = Move.pop(); 
		
		long t = System.currentTimeMillis();
		
		for(int i=0; i<50000; i++) {
			
			Move m = Move.pop(); 
		}

		echo (System.currentTimeMillis()-t);

t = System.currentTimeMillis();

		for(int i=0; i<50000; i++) {
			
			Move m = Move.pop(); 
		}

		echo (System.currentTimeMillis()-t);
	}	
}
