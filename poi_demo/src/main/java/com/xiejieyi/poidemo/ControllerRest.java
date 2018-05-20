package com.xiejieyi.poidemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
        String fileName = file.getOriginalFilename();
        if(fileName == null || fileName.isEmpty()){
            return new ArrayList();
        }
        String fileExt = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = "myfile." + fileExt;
        // file.get
        logger.info("filename=" +  file.getName());
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        logger.info("filepath:=" + filePath);
        try
        {
            delAllFile(filePath);
            uploadFile(file.getBytes(), filePath, newFileName);
        } catch (Exception e)
        {
            logger.error("exception " + e);
        }


        //解析文件 遍历文件夹下所有文件
        try
        {
            if(newFileName.endsWith("zip")){
                unZipFile(filePath+"/"+newFileName,filePath);
            }

            List<File> fileList = getFileList(filePath);
            Dictionary dict = new Dictionary();
            for (File item : fileList)
            {
                String filename=item.getName();
                Scanner scanner = null;
                if(filename.endsWith("docx")){
                    XWPFDocument docx = new XWPFDocument(
                            new FileInputStream(item));
                    //using XWPFWordExtractor Class
                    XWPFWordExtractor we = new XWPFWordExtractor(docx);
                    scanner = new Scanner(we.getText());
                }else{
                    // 创建输入流读取DOC文件
                    FileInputStream in = new FileInputStream(item);

                    // 创建WordExtractor
                    WordExtractor extractor = new WordExtractor(in);

                    // 对DOC文件进行提取
                    scanner = new Scanner(extractor.getText());
                }

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
                            dict.insert(word.toLowerCase());
                        }
                    }
                }
            }

            return dict.getDictionary();


        } catch (IOException e)
        {
            logger.error(e);
        }finally
        {
            // delAllFile(filePath);
        }
        //返回json
        return new ArrayList();
    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response) throws Exception
    {
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList();
        titles.add("a1");
        titles.add("a2");
        titles.add("a3");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();
        row.add("11111111111");
        row.add("22222222222");
        row.add("3333333333");
        rows.add(row);

        data.setRows(rows);

        ExportExcelUtils.exportExcel(response,"hello.xlsx",data);
    }

    private void unZipFile(String fileZip,String destDir) throws IOException{

        logger.info("destDir="+destDir);
        logger.info("fileZip="+ fileZip);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip), Charset.forName("GBK"));
        ZipEntry zipEntry = zis.getNextEntry();
        while(zipEntry != null){
            String fileName = zipEntry.getName();
            File newFile = new File(destDir +"/" + fileName);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
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

    private void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return ;
        }
        if (!file.isDirectory()) {
            return ;
        }
        String[] tempList = file.list();
        File temp;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
        }
        logger.info("delete path =" + path+" success");
        return ;
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
                } else if (fileName.endsWith("docx") || fileName.endsWith("doc"))
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
