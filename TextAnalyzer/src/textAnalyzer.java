import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.*;
import java.lang.reflect.Array;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class textAnalyzer {

	static String i; // string to save data from URL
	static Document document;
	public static String elementsText, text; // from h1, h3, h4 and p - class poem
	private static List textToProcess;
	

	public static void main(String[] args) throws Exception {

		
		try {
			// Get Document object after parsing the html from given url.
			document = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();

			String title = document.title(); // Get title
			// print(" Title: " + title); //Print title.

			Elements header = document.select("h1,h4,h3");
			Elements poem = document.select("p.poem");
			String elementsText = header.text() + " " + poem.text();
//			System.out.println(elementsText);

			String text = elementsText.replaceAll("[^a-zA-Z0-9]", " "); // "\\p{Punct}",""
			String stringToUse = text.replaceAll("\\s+", " ");
//			System.out.println(stringToUse);
					
			String[] stringArray = stringToUse.split(" ");
//			List <String> textToProcess = new ArrayList<String>();
				
//			textToProcess = Arrays.asList(string);
				
//				for(String element: textToProcess) {
//					System.out.println(element);
//				}
			
			
			Map<String, Integer> map = new HashMap<String, Integer>();
//			countWords(stringToUse, words);
//			System.out.println(words);
			
// counting words in array
			 for (String word : stringArray) {
			        Integer num = map.get(word);
			        num = (num == null) ? 1 : ++num;
			        map.put(word, num);
			        
			    }
			 
			// System.out.println(map);
			 
//

				Map<String, Integer> treeMap = new TreeMap<String, Integer>(){
				                
				                	public int compare (Integer o1, Integer o2) {
				                		return o2.compareTo(o1);
				                	}
				                };
				                treeMap.putAll(map);
				                
				              // printMap(treeMap); 
			 
				               
				               Map<String, Integer> sortedMap = sortByValue(treeMap);
				               //printMap(sortedMap);
				               
				               LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
				               sortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				                       .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
				                
				               //System.out.println("Reverse Sorted Map   : " + reverseSortedMap);
				     
				            //   printMap(reverseSortedMap);
				               
				              printTop10(reverseSortedMap);
				              
				             
				               
			               
				               
				               
		 
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	}
		//getTopTen(textToProcess);
		
//		List<String> list = new ArrayList<String>();
//		Map<String, Long> map = list.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));
//
//		List<Map.Entry<String, Long>> result = map.entrySet().stream()
//				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).collect(Collectors.toList());
//
//		System.out.println(result + "here");





	private static void  printTop10(LinkedHashMap<String, Integer> reverseSortedMap) {
	//	System.out.print(reverseSortedMap.keySet().stream().sorted().limit(10).collect(Collectors.toMap(Function.identity(), reverseSortedMap::get)));
		
		

		List array = new ArrayList(reverseSortedMap.keySet());
		
		array = array.subList(0,10);
	//	System.out.println(array + "HERE" );
		

		
		
//		int i=0;
//		for(Map.Entry entry: reverseSortedMap.entrySet()){
//			System.out.println(reverseSortedMap);
//			
//		i++;
//		if(i==10){
//		break;
//		}
//		}
		
		
		
		Set<Entry<String,Integer>> setOfEntries = reverseSortedMap.entrySet();
		// get the iterator from entry set
	    Iterator<Entry<String, Integer>> iterator 
	                            = setOfEntries.iterator();

	    // iterate over map
	    while (iterator.hasNext()) {
	      Entry<String, Integer> entry = iterator.next();
	      Integer value = entry.getValue();

	      if (value.compareTo(Integer.valueOf((int) 11)) < 0) {
	        //System.out.println("removing : " + entry);
	        // priceMap.remove(entry.getKey()); // wrong - will throw
	        // ConcurrentModficationException
	        // priceMap.remove(entry.getKey(), entry.getValue()); // wrong - will
	        // throw error
	        iterator.remove(); // always use remove() method of iterator
	        
	        
	      }
	      

	    }
	    System.out.println(setOfEntries);
	    
	    
		
//	    for (Entry<String, Integer> entry : ((Map<String,Integer>) setOfEntries).entrySet()) {
//            System.out.println("Word:" + entry.getKey()
//				+ "||"+" Frequency: " + entry.getValue());
//        }
//	    
	    
	   
	      
		
	        
	        
	        
	        
	        
	    
	  }

	
	

	private static Map<String, Integer> sortByValue(Map<String, Integer> treeMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(treeMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/
//System.out.println(sortedMap);

        return sortedMap;
    }


	public static <S, I> void printMap(Map<S, I> map) {
        for (Map.Entry<S, I> entry : map.entrySet()) {
            System.out.println("Word:" + entry.getKey()
				+ "||"+" Frequency: " + entry.getValue());
        }
	

//	private static void countWords(String stringToUse, Map <String, Integer> words) throws FileNotFoundException {
//		//Scanner wordsInString = new Scanner (new File(stringToUse));
//		while (stringToUse.hasNext()) {
//			String word = wordsInString.next();
//			Integer count = words.get(word);
//			if (count !=null)
//				count ++;
//			else 
//				count = 1;
//			words.put(word, count);
//			
//		}
//		wordsInString.close();
//	}


//	//public static List<Map.Entry<String, Integer>> getTopTen(List textToProcess) {
//
//		List<String> list = new ArrayList<String>();
//		Map<String, Long> map = list.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));
//
//		List<Map.Entry<String, Long>> result = map.entrySet().stream()
//				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).collect(Collectors.toList());
//
//		System.out.println(result);
//		return null;
//
//	}

	//void countWords(String ){}
}
}
