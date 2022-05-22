package ds2;

//Kehinde Abioye

//import java.util.Arrays;
import algs4.StdAudio;
import algs4.StdOut;
import algs4.StdIn;
import algs4.BST;

public class PlayNotes {
	public static void playNote(double duration, double frequency) {
		final int sliceCount = (int) (StdAudio.SAMPLE_RATE * duration);
		final double[] slices = new double[sliceCount+1];
		for (int i = 0; i <= sliceCount; i++) {
			slices[i] += Math.sin(2 * Math.PI * i * frequency / StdAudio.SAMPLE_RATE);
		}
		StdAudio.play(slices);
	}

	public static void main(String[] args) {

		//1.print name followed by 3 
		StdOut.println("Kehinde Abioye***");
		
		//2. reads the lines in the file data/notes_frequencies.txt line by line, splitting 
		//each line into a note name and a frequency, and placing that note/frequency into a
		//symbol table class (e.g., BST) where the note is the key and the frequency the value
		StdIn.fromFile("data/notes_frequencies.txt");//reads file
		
		BST<String, String> treeFreq = new BST<String,String>();
		while (!StdIn.isEmpty()) {
			String line = StdIn.readLine();//line by line
			String[] freqs = line.split("\\s+");//splitting each line into a note name and a frequency
			//StdOut.println(line);//print line
			//StdOut.println(Arrays.toString(words));//print array
			
			treeFreq.put(freqs[0], freqs[1]);//place nodes
		}
		
		//3.reads the lines in the file data/cnotes.txt line by line, splitting each line into a duration (double) 
		//and a note name (string), looking up the frequency of that note in the symbol table, and calling the 
		//PlayNote method (see below) with the duration and frequency;
		StdIn.fromFile("data/cnotes.txt");//read file
		
		BST<String, String> treeNote = new BST<String,String>();
		while (!StdIn.isEmpty()) {
			String line2 = StdIn.readLine();//line by line
			String[] notes = line2.split("\\s+");//splitting each line into a note name and a frequency
			//StdOut.println(line2);//print line
			//StdOut.println(Arrays.toString(notes));//print array
			treeNote.put(notes[0], notes[1]);//place notes
			String noteName_n = notes[1];//store note name
			double dur= Double.valueOf(notes[0]);//store duration
			double freqVal = Double.valueOf(treeFreq.get(noteName_n));//obtain freq of note from symbol table of freqs and notes
			//get returns value of key
			playNote(dur,freqVal);//play notes
		}
	}

}
