package org.krudo.tests.base;

import static org.krudo.util.Tool.*;
import static org.krudo.util.Debug.*;

//
import org.krudo.Node;
import org.krudo.Cache;
import org.krudo.Search;

// 
public class Cache1 {

	//
	public static void main(String[] args) {
	
		Node n = new Node();
		
		Search s = new Search(n);
		
		s.eval(6);
		
		echo (n.i);
		echo ("cache size:",Cache.legals.size());
		
		
	}	
}