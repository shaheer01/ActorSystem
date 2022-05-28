package com.actor.wordcount;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCount {
    public static void main (String[] args) {

//        System.out.println("Simple Java Word Count Program");
//
//        String str1 = ("Today is Holdiay Day is");

//        StringTokenizer tokens = new StringTokenizer(str1);

//        while (tokens.hasMoreTokens()){
//
//            tokens.nextToken();
//        }

//        String str [] = str1.split(" ");
//        System.out.println("str: "+ str);
//        Set<String> str2 = Arrays.stream(str).collect(Collectors.toSet());
//        System.out.println("str: "+ str2);
//        HashMap<String, Integer> count = new HashMap();
//        for (String s : str2){
//            count.put(s, numberOfOccurrences(s, str));
//        }
//        System.out.println(count);
//    }
//
//    private static int numberOfOccurrences(String word, String[] sentence) {
//        int count = 0;
//        for (int i = 0; i < sentence.length; i++)
//        {
//            // if match found increase count
//            if (word.equals(sentence[i]))
//                count++;
//        }
//
//        return count;
//    }

        String sentense = "Java is a language.Java is easy and i like Java.This is new line";

        List<String> list = Stream.of(sentense).map(k -> k.split("\\.")).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        System.out.println(list);

        Map<String, Integer> countMap = list.stream()
                .collect(Collectors.toMap(k -> k.toLowerCase(), k -> 1, Integer::sum));

        System.out.println(countMap);
    }
}
