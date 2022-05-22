package ds2;

//Kehinde Abioye

import java.util.Arrays;

import algs4.StdIn;
import algs4.StdOut;

public class TestA5BST {

	public static void main(String[] args) {
		
		//a.fills an array with the words in data/tale.txt
		StdIn.fromFile("data/tale.txt");
		String[] words = StdIn.readAllStrings();
		
		//b.creates a A5BST object with key type String and 
		//value type Integer; the key will be a word and the 
		//value will be a count of that word
		A5BST<String,Integer> testTree = new A5BST<String,Integer>();
		
		//c.fills it with the words from the array, updating the 
		//value by adding one to it
		Integer unique = 0;
		for (String word: words) {
			Integer count = testTree.get(word);
			if (count == null) {
				count = 0;
				testTree.put(word, 0);
				unique++;
			}
			testTree.put(word, count+1);
		}

		StdOut.println("*** Kehinde Abioye");
		StdOut.println("Number of unique words in text: "+ unique +"\n");
		
		//d.prints the inner node and leaf count from the tree
		StdOut.println("Tree created from original ordering");
		StdOut.println("Number of leaf nodes: "+ testTree.countLeaves());
		StdOut.println("Number of inner nodes: "+ testTree.innerCount()+"\n");
		
		//e.sorts the array
		StdIn.fromFile("data/tale.txt");
		String[] wordsSorted = StdIn.readAllStrings();
		Arrays.sort(wordsSorted);
		
		//f.repeats steps (b) through (d) on this sorted array
		A5BST<String,Integer> sortedTree = new A5BST<String,Integer>();

		for (String word: wordsSorted) {
			Integer count = sortedTree.get(word);
			if (count == null) {
				count = 0;
				sortedTree.put(word, 0);
			}
			sortedTree.put(word, count+1);
	
		}
		
		StdOut.println("Tree created from sorted ordering");
		StdOut.println("Number of leaf nodes: "+sortedTree.countLeaves());
		StdOut.println("Number of inner nodes: "+sortedTree.innerCount());
	}

}
