package edu.mit.streamjit.transmitter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class data_generator {

	public static void main(String[] args) {
		int data_length=40;
		
		
		FileOutputStream out=null;
		FileInputStream in=null;
		try {
			out = new FileOutputStream("src/edu/mit/streamjit/transmitter/data.in");
			in = new FileInputStream("src/edu/mit/streamjit/transmitter/data.in");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
				
		for (int i = 0; i < data_length; i++) {
			byte l;
			if(Math.random()>0.5)	l=1;
			else	l=0;
			
			try {
				out.write((byte)1);
//				System.out.print(l+" ");
//				int val=in.read();
//				System.out.println(val);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
