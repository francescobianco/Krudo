package org.krudo.tests.base;

//
import static org.krudo.util.Debug.*;

//
import org.krudo.util.Book;
import org.krudo.Move;
import org.krudo.Node;

// 
public class Book1 {

	//
	public static void main(String[] args) {
				
		// 
		Node p = new Node();
		
		//
		Move m = Book.list(p);

		//
		dump(p);
		
		// print out position
		dump(m);
	}	
}