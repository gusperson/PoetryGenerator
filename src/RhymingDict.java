import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RhymingDict { 	
  

	// Given a pronunciation, get the rhyme group
	// get the more *heavily emphasized vowel* and follwing syllables
	// For "tomato", this is "-ato", and not "-omato", or "-o"
	// Tomato shares a rhyming group with "potato", but not "grow"
	private static String getRhymeGroup(String line) {

		int firstSpace = line.indexOf(" "); 

		String pronunciation = line.substring(firstSpace + 1, line.length());

		int stress0 = pronunciation.indexOf("0");
		int stress1 = pronunciation.indexOf("1");
		int stress2 = pronunciation.indexOf("2");

		if (stress2 >= 0)
			return pronunciation.substring(stress2 - 2, pronunciation.length());
		if (stress1 >= 0)
			return pronunciation.substring(stress1 - 2, pronunciation.length());
		if (stress0 >= 0)
			return pronunciation.substring(stress0 - 2, pronunciation.length());
		
		// No vowels at all? ("hmmm", "mmm", "shh")
		return pronunciation;
	}

	private static String getWord(String line) {
		int firstSpace = line.indexOf(" ");

		String word = line.substring(0, firstSpace);

		return word; 
	}

	// Load the dictionary
	private static String[] loadDictionary() {
		// Load the file and read it

		String[] lines = null; // Array we'll return holding all the lines of the dictionary
		
		try {
			String path = "cmudict/cmudict-short.dict";
			// Creating an array of strings, one for each line in the file
			lines = new String(Files.readAllBytes(Paths.get(path))).split("\\r?\\n");
			
		}
		catch (IOException ex){
			ex.printStackTrace();
		}

		return lines; 
	}

	
	public static void main(String []args) {

		String[] dictionaryLines = loadDictionary();

		/* 	Test Code for MyLinkedList

		MyLinkedList testList = new MyLinkedList(); 
		testList.add(0, "hello");
		testList.add(1, "world");
		testList.add(2, "!");

		System.out.println(testList);
		System.out.println("index 2 = " + testList.get(2));
		System.out.println("world at index " + testList.find("world"));
		System.out.println("hello at index " + testList.find("hello"));
		System.out.println("! at index " + testList.find("!"));
		System.out.println("wow at index " + testList.find("wow"));
		testList.remove(2);
		System.out.println(testList);		
		testList.remove(0);
		System.out.println(testList);
		testList.remove(0);
		System.out.println(testList);
		System.out.println("hello at index " + testList.find("hello"));
		*/

		// List of rhyme groups. The items in this linked list will be RhymeGroupWords. 
		ListInterface rhymeGroups = new MyLinkedList(); 

		/* load the dictionary into  linked lists.  Inside each of this objects is another linked list which is a list of words within the same
		   rhyme group.  */
		ListInterface rhymegroup1 = new MySortedLinkedList();
		((MySortedLinkedList)rhymegroup1).add(getWord(dictionaryLines[0]));
		RhymeGroupWords first = new RhymeGroupWords(getRhymeGroup(dictionaryLines[0]), rhymegroup1);
		rhymeGroups.add(0, first);
		System.out.println(dictionaryLines.length);
		for(int i = 1; i<dictionaryLines.length; i++) {			
			String word = getWord(dictionaryLines[i]);
			String rg = getRhymeGroup(dictionaryLines[i]);
			boolean rgfound = false;
			for(int j = 0; j<rhymeGroups.size();j++) {
				if(rg.equals(((RhymeGroupWords) rhymeGroups.get(j)).getRhymeGroup())) {
					((MySortedLinkedList)((RhymeGroupWords) rhymeGroups.get(j)).getWordList()).add(word);
					rgfound = true;
					break;
				}
				
			}
			if(rgfound == false) {
				int rhymeGroupsIndex = 0;
				ListInterface forrhymegroup = new MySortedLinkedList();
				((MySortedLinkedList)forrhymegroup).add(word);
				RhymeGroupWords newrhymegroup = new RhymeGroupWords(rg,forrhymegroup);
				rhymeGroups.add(rhymeGroupsIndex,newrhymegroup);
				rhymeGroupsIndex++;
			}
			 
		}
		
		// This code prints out the rhyme groups that have been loaded above. 
		for(int i =0; i < rhymeGroups.size(); i++) {
			RhymeGroupWords rg = (RhymeGroupWords) rhymeGroups.get(i);
			System.out.print(rg.getRhymeGroup() + ": ");
			System.out.println(rg.getWordList());
		} 

		// iterate through pairs of arguments, testing to see if they are in the same rhyme group or not.
		
		int x = args.length;
		if(args.length%2 != 0) {
			x = args.length-1;
		}
		for(int i = 0; i<x;i++) {
	
			int k = -1, z = 0;
			for(int j = 0; j<rhymeGroups.size(); j++) {
				 k = ((RhymeGroupWords) rhymeGroups.get(j)).getWordList().find(args[i]);
				if(k!= -1) {
					z = j;
					break;
					}
				if(k != -1) {
					break;
				}
			}//finds index of rhyme group where args[i] is found
			if(k == -1) {
				System.out.println(args[i] +" is not in the dictionary");
				i++;
				continue;
			} else {
				if(( (RhymeGroupWords) rhymeGroups.get(z)).getWordList().find(args[i+1]) != -1) {
					System.out.println(args[i] +" and " +args[i+1]+ " rhyme");
				}else {
					for(int j = 0; j<rhymeGroups.size(); j++) {
						z = ((RhymeGroupWords) rhymeGroups.get(j)).getWordList().find(args[i+1]);
						if(z != -1) {
							System.out.println(args[i] +" and " +args[i+1]+ " don't rhyme");
							break;
						}
					}
					if(z == -1) {
						System.out.println(args[i+1] +" is not in the dictionary");
						i++;
						continue;
					}
				}
				i++;
			}
			
			
			
		}
		int size = rhymeGroups.size();
		System.out.println(size);

		rhymeGroups.removeAll();
		 size = rhymeGroups.size();
		System.out.println(size);
	}
}