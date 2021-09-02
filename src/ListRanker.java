import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedWriter;

public class ListRanker {
	
	public static boolean timetosave = false;
	
	public static void main(String[] args) throws IOException {

		File listFile = new File("list.txt");
		Random rand = new Random();
		Scanner in = new Scanner(listFile);
		ArrayList<String> list = new ArrayList<String>();
		while (in.hasNextLine()) {
			list.add(in.nextLine().split("\n")[0]);
		}
		in.close();
		
		in = new Scanner(System.in);
		
		
		File check = new File("sortSave.txt");
		int resumeI = -1;
		int resumeJ = -1;
		boolean resumeDoneCheck = true;
		String resumeKey = "";
		String[] sortList = new String[list.size()];
		if (!check.createNewFile()) {
			Scanner load = new Scanner(check);
			
			for (int i = 0; i < sortList.length; i++) {
				sortList[i] = load.nextLine().split("\n")[0];
			}
			resumeI = load.nextInt();
			load.nextLine();
			resumeJ = load.nextInt();
			load.nextLine();
			resumeDoneCheck = load.nextBoolean();
			if (!resumeDoneCheck) {
				load.nextLine();
				resumeKey = load.nextLine().split("\n")[0];
			}
			
			load.close();
			
		} else {
			
			sortList = list.toArray(new String[0]);
		}
		
		//sort
		boolean doneCheck = true;
		int sortI;
		String key = "";
		int sortJ = 0;
		for (sortI = 1; sortI < sortList.length; ++sortI) {
			if (resumeI == -1 || sortI == resumeI) {
				if (sortI == resumeI) {
					resumeI = -1;
					sortJ = resumeJ;
					key = resumeKey;
				} else {
					sortJ = sortI - 1;
					key = sortList[sortI];
				}
	 
	            doneCheck = true;
	            while (sortJ >= 0 && promptComparison(key, sortList[sortJ], in)) {
	                sortList[sortJ + 1] = sortList[sortJ];
	                sortJ--;
	                
	                if(timetosave) {
	            		doneCheck = false;
		            	break;
		            }
	            }
	            
	            if (doneCheck) {
	            	sortList[sortJ + 1] = key;
	            }
	            
	            if(timetosave) {
	            	if (doneCheck) {
	            		sortJ = sortI - 1;
	            	}
	            	break;
	            }
			}
        }
		
		if (sortI == sortList.length) {
			System.out.println("List complete!");
			
			System.out.println("How many ranks do you want?");
			System.out.println("Type 1 to say 5 ranks, 2 to say 6 ranks, 3 to say 8 ranks, and 4 to say 10 ranks");
			double[] ranks;
			Scanner rankProb;
			int[] numItemsPerTier;
			int total;
			switch(in.next().charAt(0)) {
				case '1':
					ranks = new double[5];
					rankProb = new Scanner(new File("5tier.txt"));
					for (int i = 0; i < ranks.length; i++) {
						ranks[i] = Double.parseDouble(rankProb.nextLine());
					}
					numItemsPerTier = new int[5];
					total = 0;
					for (int i = 0; i < numItemsPerTier.length; i++) {
						int insert = (int) Math.floor(ranks[i] * (double) list.size());
						total += insert;
						numItemsPerTier[i] = insert;
					}
					for (int i = 0; i < list.size() - total; i++) {
						numItemsPerTier[rand.nextInt(numItemsPerTier.length)]++;
					}
					saveTiersToFile(sortList, numItemsPerTier);
					break;
				case '2':
					ranks = new double[6];
					rankProb = new Scanner(new File("6tier.txt"));
					for (int i = 0; i < ranks.length; i++) {
						ranks[i] = Double.parseDouble(rankProb.nextLine());
					}
					numItemsPerTier = new int[6];
					total = 0;
					for (int i = 0; i < numItemsPerTier.length; i++) {
						int insert = (int) Math.floor(ranks[i] * (double) list.size());
						total += insert;
						numItemsPerTier[i] = insert;
					}
					for (int i = 0; i < list.size() - total; i++) {
						numItemsPerTier[rand.nextInt(numItemsPerTier.length)]++;
					}
					saveTiersToFile(sortList, numItemsPerTier);
					break;
				case '3':
					ranks = new double[8];
					rankProb = new Scanner(new File("8tier.txt"));
					for (int i = 0; i < ranks.length; i++) {
						ranks[i] = Double.parseDouble(rankProb.nextLine());
					}
					numItemsPerTier = new int[8];
					total = 0;
					for (int i = 0; i < numItemsPerTier.length; i++) {
						int insert = (int) Math.floor(ranks[i] * (double) list.size());
						total += insert;
						numItemsPerTier[i] = insert;
					}
					for (int i = 0; i < list.size() - total; i++) {
						numItemsPerTier[rand.nextInt(numItemsPerTier.length)]++;
					}
					saveTiersToFile(sortList, numItemsPerTier);
					break;
				case '4':
					ranks = new double[10];
					rankProb = new Scanner(new File("10tier.txt"));
					for (int i = 0; i < ranks.length; i++) {
						ranks[i] = Double.parseDouble(rankProb.nextLine());
					}
					numItemsPerTier = new int[10];
					total = 0;
					for (int i = 0; i < numItemsPerTier.length; i++) {
						int insert = (int) Math.floor(ranks[i] * (double) list.size());
						total += insert;
						numItemsPerTier[i] = insert;
					}
					for (int i = 0; i < list.size() - total; i++) {
						numItemsPerTier[rand.nextInt(numItemsPerTier.length)]++;
					}
					saveTiersToFile(sortList, numItemsPerTier);
					break;
			}
		} else {
			saveList(sortList, sortI, sortJ, doneCheck, key);
		}
		
		in.close();
		System.out.println("Okay bet, you're done");
	}
	
	private static void saveList(String[] sortList, int sortI, int sortJ, boolean doneCheck, String key) throws IOException {
		PrintWriter save = new PrintWriter(new BufferedWriter(new FileWriter("sortSave.txt")));
		for (int i = 0; i < sortList.length; i++) {
			save.println(sortList[i]);
		}
		save.println(sortI);
		save.println(sortJ);
		save.println(doneCheck);
		save.println(key);
		
		save.close();
	}

	public static boolean promptComparison(String a, String b, Scanner in) {
		System.out.println(a + " is better than " + b);
		System.out.println("Say \"y\" if true, \"n\" if false, and \"yq\" or \"nq\" to save and quit");
		
		String choice = in.next(); 
		in.nextLine();
		
		if (choice.equals("y")) {
			return true;
		} else if (choice.equals("n")) {
			return false;
		} else if (choice.equals("yq")) {
			timetosave = true;
			return true;
		} else if (choice.equals("nq")) {
			timetosave = true;
			return false;
		} 
		
		return false;
	}

	
	public static void saveTiersToFile(String[] sortList, int[] numItemsPerTier) throws IOException {
		File check = new File("tieredList.txt");
		check.createNewFile();
		
		PrintWriter save = new PrintWriter(new BufferedWriter(new FileWriter("tieredList.txt")));
		int index = 0;
		for (int i = 0; i < numItemsPerTier.length; i++) {
			save.write("Tier " + i);
			save.println();
			save.write("-----------------------------------------------------------------------");
			save.println();
			for (int j = 0; j < numItemsPerTier[i]; j++) {
				save.write("" + sortList[index]);
				save.println();
				index++;
			}
			save.println();
		}
		
		save.close();
	}
}