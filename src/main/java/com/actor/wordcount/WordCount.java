package com.actor.wordcount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
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

        List<String> listSentence = Stream.of(sentense).map(k -> k.split("\\.")).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        System.out.println(listSentence);
        Map<String, List<String>> map=new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Integer> finalOutput= new ConcurrentHashMap<String,Integer>();
//        List<String> list = listSentence.stream().map(k -> k.split("\\W+")).flatMap(Arrays::stream)
//                .collect(Collectors.toList());
//        System.out.println(list);
        Set<String> uniqueKeys= new HashSet<>();
        Map<String, Integer> countMap = new ConcurrentHashMap<>();
        Map<String, Map<String, Integer>> finalMap = new ConcurrentHashMap<>();
        for(String list1: listSentence) {
            List<String> list =
                    Stream.of(list1).map(k -> k.split("\\W+")).flatMap(Arrays::stream).collect(Collectors.toList());
            uniqueKeys = Stream.of(list1).map(k -> k.split("\\W+")).flatMap(Arrays::stream).collect(Collectors.toSet());
            System.out.println(list1);
            countMap = list.stream().collect(Collectors.toMap(k -> k.toLowerCase(), k -> 1,
                    Integer::sum));

            for(String key: countMap.keySet()){
               if(finalOutput.contains(key)){
                   finalOutput.put(key,countMap.get(key)+1);
               }else{
                   finalOutput.put(key,countMap.get(key));
               }
            }
        }

//        Map<String, Integer> countMap = list.stream()
//                .collect(Collectors.toMap(k -> k.toLowerCase(), k -> 1, Integer::sum));


        System.out.println("countMap" + countMap);
        System.out.println("finalOutput" +finalOutput);
    }
}
