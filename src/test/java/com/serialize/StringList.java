package com.serialize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class StringList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//transient修饰的域不会被序列化
	private transient int size = 0;
	private transient Entry head = null;
	
	private static class Entry {
		String data;
		Entry next;
		Entry previous;
	}
	
	public final void add(String s) {}
	
	/**自定义的序列化*/
	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();//默认的序列化
		s.writeInt(size);
		for (Entry e = head; e != null; e = e.next) {
			s.writeObject(e.data);
		}
	}
	
	/**自定义的反序列化*/
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();//默认的反序列化
		int numElements = s.readInt();
		for (int i = 0; i < numElements; i++) {
			add((String) s.readObject());
		}
	}
}
