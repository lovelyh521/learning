package com.xiejieyi.poidemo;

import java.util.*;

/**
 * 类描述：
 *
 * @author
 */
public class Dictionary
{
    private HashMap<String, Integer> dictionary;
    private int wordsCount;

    /**
     * 字典这个类的构造函数
     */
    public Dictionary()
    {
        dictionary = new HashMap<>();
        wordsCount = 0;
    }

    /**
     * 向字典里插入一个单词
     *
     * @param word
     */
    public void insert(String word)
    {
        if (dictionary.containsKey(word))
        {
            int currentCount = dictionary.get(word);
            dictionary.put(word, currentCount + 1);
        } else
        {
            dictionary.put(word, 1);
        }
        wordsCount++;
    }

    /**
     * 取得字典里所有不同的单词
     *
     * @return
     */
    public int getDifferentWordsNum()
    {
        return dictionary.size();
    }

    /**
     * 返回字典里的所有单词 * 其出现次数
     *
     * @return
     */
    public int getAllWordsNum()
    {
        return wordsCount;
    }

    /**
     * 展示字典中存放的所有单词及其出现次数
     */
    public List getDictionary()
    {
        // for (Iterator<String> it = dictionary.keySet().iterator(); it.hasNext(); )
        // {
        //     String key = it.next();
        //     System.out.print(key);
        //     System.out.print(": ");
        //     System.out.println(dictionary.get(key));
        // }


        List<Map.Entry<String, Integer>> list = new ArrayList<>(dictionary.entrySet());

        //升序排序
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        // for (Map.Entry<String, Integer> e: list) {
        //     System.out.println(e.getKey()+":"+e.getValue());
        // }
        return list;
    }
    // public static void main( String[] args ) throws Exception {
    //     //这里放置你所说的段落
    //     String passage = "public static void main( String[] args ) {";
    //     Scanner scanner = new Scanner( passage );
    //     Dictionary dict = new Dictionary();
    //     while ( scanner.hasNextLine() ) {
    //         String line =scanner.nextLine();
    //         boolean isBlankLine = line.matches( "\\W" ) || line.length() == 0;
    //         if ( isBlankLine ) {
    //             continue;
    //         }
    //         String[] words = line.split( "\\W" );
    //         for ( String word : words ) {
    //             if ( word.length() != 0 ) {
    //                 dict.insert( word );
    //             }
    //         }
    //     }
    //     dict.displayDictionary();
    // }
}