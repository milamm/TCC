package image;

import java.util.Vector;

public class Matrix {
	private int w;
	private int h;
	private Vector<Vector<Object>> data;
	Object init_value;
	
	public Matrix(int w, int h, Object init_val) {
		this.w = w;
		this.h = h;
		init_value = init_val;
		data = new Vector<Vector<Object>>(h,10);
		
		for(int i=0; i<h; i++) {
			Vector<Object> row = new Vector<Object>(w,10);
			for(int j=0; j<w; j++) {
				row.add(init_value);
			}
				
			data.add(row);	
		}
	}
	
	public Object get(int x, int y) {
		return data.get(y).get(x);
	}
	
	public void set(int x, int y, Object o) {
		try {
			
			Vector<Object> curRow = data.get(y);
			curRow.set(x, o);
			
		} catch (IndexOutOfBoundsException e) {
			
			for(int i=data.size(); i<=y; i++) {
				Vector<Object> row = new Vector<Object>(w,10);
				for(int j=0; j<w; j++) {
					row.add(init_value);
				}
					
				data.add(row);
			}
			
			if(data.get(0).size() <= x ) {
				for (int i = 0; i < data.size(); i++) {
					for(int j=data.get(i).size(); j<=x; j++) {
						data.get(i).add(j, init_value);
					}
				}
			}
		
			Vector<Object> curRow = data.get(y);
			curRow.set(x, o);
			this.w = curRow.size();
			this.h = data.size();
			
		}
		/*Vector<Object> curRow = null;
		
		if(y >= data.capacity()) 
			for(int i=h; i<=y; i++) {
				Vector<Object> row = new Vector<Object>(w,10);
				for(int j=0; j<w; j++) {
					row.add(new Object());
				}
					
				data.add(row);	
			}
		
		curRow = data.get(y);
		
		if(curRow.capacity()-1 < x)
			for (int i = 0; i < data.capacity(); i++) {
				for(int j=curRow.capacity(); j<=x; j++) {
					data.get(j).add(j, new Object());
				}
			}
		
		curRow.set(x, o);
		this.w = curRow.capacity();
		this.h = data.capacity();*/
	}
	
	public int getHeight() {
		return h;
	}
	
	public int getWidth() {
		return w;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		for(int i=0; i<data.size(); i++) {
			for(int j=0; j<data.get(i).size(); j++)
				if(data.get(i).get(j).equals(false))
					str += " " + data.get(i).get(j).toString();
				else if(data.get(i).get(j).equals(true))
					str += " " + data.get(i).get(j).toString() + " ";
				else 
					str += " " + data.get(i).get(j).toString();
			str += "\n";
		}
		
		return str;
	}
	
}