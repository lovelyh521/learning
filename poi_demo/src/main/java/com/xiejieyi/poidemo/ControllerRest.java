package com.xiejieyi.poidemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 类描述：提供接口
 *
 * @author
 */
@RestController
public class ControllerRest
{
    protected Log logger = LogFactory.getLog(getClass());

    @RequestMapping(value = "/calcWordFrequency", method = RequestMethod.POST)
    public @ResponseBody
    List calcWordFrequency(@RequestParam("file") MultipartFile file,
                             HttpServletRequest request)
    {
        logger.info(file.getName());
        String fileName = file.getOriginalFilename();
        logger.info("filename=" + fileName);
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        try
        {
            logger.info("filepath:=" + filePath);
            uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e)
        {
            logger.error("exception " + e);
        }

        //解析文件 遍历文件夹下所有文件
        try
        {
            List<File> fileList = getFileList(filePath);
            // Dictionary dict = new Dictionary();
            for (File item : fileList)
            {
                XWPFDocument docx = new XWPFDocument(
                        new FileInputStream(item));
                //using XWPFWordExtractor Class
                XWPFWordExtractor we = new XWPFWordExtractor(docx);
                Scanner scanner = new Scanner(we.getText());

                while (scanner.hasNextLine())
                {
                    String line = scanner.nextLine();
                    boolean isBlankLine = line.matches("\\W") || line.length() == 0;
                    if (isBlankLine)
                    {
                        continue;
                    }
                    String[] words = line.split("\\W");
                    for (String word : words)
                    {
                        if (word.length() != 0 && word.matches("[a-zA-Z]+"))
                        {
                            Dictionary.insert(word.toLowerCase());
                        }
                    }
                }
            }
            return Dictionary.getDictionary();


        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //删除文件

        //返回json
        return new ArrayList();
    }

    private void uploadFile(byte[] file, String filePath, String fileName) throws Exception
    {
        File targetFile = new File(filePath);
        if (!targetFile.exists())
        {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    private List<File> getFileList(String strPath)
    {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        List<File> fileList = new ArrayList<>();
        if (files != null)
        {
            for (int i = 0; i < files.length; i++)
            {
                String fileName = files[i].getName();
                if (files[i].isDirectory())
                { // 判断是文件还是文件夹
                    //文件夹忽略
                    continue;
                } else if (fileName.endsWith("docx"))
                { // 判断文件名是否以.avi结尾
                    String strFileName = files[i].getAbsolutePath();
                    logger.info("---" + strFileName);
                    fileList.add(files[i]);
                } else
                {
                    continue;
                }
            }

        }
        return fileList;
    }

}
