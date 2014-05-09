package bram.lingo.words;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Alphabet implements Iterable<Letter>{

	private static final Alphabet c_singleton = new Alphabet();
	
	public static Alphabet getInstance() {
		return c_singleton;
	}
	
	private final List<Letter> c_alphabet;
	
	private Alphabet() {
		c_alphabet = new ArrayList<Letter>();
		c_alphabet.add(Letter.a);
		c_alphabet.add(Letter.b);
		c_alphabet.add(Letter.c);
		c_alphabet.add(Letter.d);
		c_alphabet.add(Letter.e);
		c_alphabet.add(Letter.f);
		c_alphabet.add(Letter.g);
		c_alphabet.add(Letter.h);
		c_alphabet.add(Letter.i);
		c_alphabet.add(Letter.j);
		c_alphabet.add(Letter.k);
		c_alphabet.add(Letter.l);
		c_alphabet.add(Letter.m);
		c_alphabet.add(Letter.n);
		c_alphabet.add(Letter.o);
		c_alphabet.add(Letter.p);
		c_alphabet.add(Letter.q);
		c_alphabet.add(Letter.r);
		c_alphabet.add(Letter.s);
		c_alphabet.add(Letter.t);
		c_alphabet.add(Letter.u);
		c_alphabet.add(Letter.v);
		c_alphabet.add(Letter.w);
		c_alphabet.add(Letter.x);
		c_alphabet.add(Letter.y);
		c_alphabet.add(Letter.z);
		c_alphabet.add(Letter.ij);		
	}

	@Override
	public Iterator<Letter> iterator() {
		return c_alphabet.iterator();
	}


}
