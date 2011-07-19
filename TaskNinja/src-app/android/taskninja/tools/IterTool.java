package android.taskninja.tools;

import java.util.Iterator;
import java.util.Set;

public class IterTool <T>{

	public T get(Iterator<T> iter, int position){
		int i = 0;
		T result = null;
		
		while (iter.hasNext()){
			
			T current = iter.next();
			
			if (i == position){
				result = current;
				break;
			}
			
			i++;
		}
		
		return result;
	}
	
}
