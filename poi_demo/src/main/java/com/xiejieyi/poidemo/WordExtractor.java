package com.xiejieyi.poidemo;

/**
 * 类描述：
 *
 * @author
 */
import java.io.FileInputStream;
import java.util.Scanner;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordExtractor
{
    public static void main(String[] args)throws Exception
    {
        XWPFDocument docx = new XWPFDocument(
                new FileInputStream("d://test.docx"));
        //using XWPFWordExtractor Class
        XWPFWordExtractor we = new XWPFWordExtractor(docx);
        System.out.println(we.getText());//保存并阅读

        // Scanner scanner = new Scanner( we.getText() );
        // Dictionary dict = new Dictionary();
        // while ( scanner.hasNextLine() ) {
        //     String line =scanner.nextLine();
        //     boolean isBlankLine = line.matches( "\\W" ) || line.length() == 0;
        //     if ( isBlankLine ) {
        //         continue;
        //     }
        //     String[] words = line.split( "\\W" );
        //     for ( String word : words ) {
        //         if ( word.length() != 0 ) {
        //             dict.insert( word );
        //         }
        //     }
        // }
        // dict.getDictionary();

    }
}
