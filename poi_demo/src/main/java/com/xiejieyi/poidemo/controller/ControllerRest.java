package com.xiejieyi.poidemo.controller;

import com.xiejieyi.poidemo.bean.ResultBean;
import com.xiejieyi.poidemo.bean.TranslateBean;
import com.xiejieyi.poidemo.common.*;
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
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 类描述：提供文件上传和EXCEL导出接口
 *
 * @author
 */
@RestController
public class ControllerRest
{
    protected Log logger = LogFactory.getLog(getClass());

    /**
     * 方法描述：文件导入接口
     * 导入文件，并计算单词的出现频率
     *
     * @author xiejieyi
     * @date 2018/5/20 0020
     */
    @RequestMapping(value = "/calcWordFrequency", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean calcWordFrequency(@RequestParam("file") MultipartFile file,
                                 HttpServletRequest request) throws Exception
    {
        //获取原始的文件名称
        String fileName = file.getOriginalFilename();

        if (fileName == null || fileName.isEmpty())
        {
            return null;
        }
        //获取上传文件的后缀
        String fileExt = fileName.substring(fileName.lastIndexOf("."));

        //使用自定义的后缀
        String newFileName = "myfile" + fileExt;
        //获取上传文件的存储路径
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");

        logger.info("filepath:=" + filePath);

        Util.delAllFile(filePath);
        Util.uploadFile(file.getBytes(), filePath, newFileName);
        logger.info("save file success=" + filePath);

        //解析文件 遍历文件夹下所有文件
        FileInputStream in = null;
        XWPFDocument docx = null;
        XWPFWordExtractor we = null;
        Scanner scanner = null;
        WordExtractor extractor = null;
        try
        {
            if (newFileName.endsWith("zip"))
            {
                Util.unZipFile(filePath + "/" + newFileName, filePath);
            }
            List<File> fileList = getFileList(filePath);
            Dictionary dict = new Dictionary();

            for (File item : fileList)
            {
                String filename = item.getName();

                if (filename.endsWith("docx"))
                {
                    in = new FileInputStream(item);
                    docx = new XWPFDocument(in);
                    //using XWPFWordExtractor Class
                    we = new XWPFWordExtractor(docx);
                    scanner = new Scanner(we.getText());

                } else
                {
                    // 创建输入流读取DOC文件
                    in = new FileInputStream(item);
                    // 创建WordExtractor
                    extractor = new WordExtractor(in);
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
                        // 只取英文小写字母，过滤掉单个字母
                        if (word.length() > 1 && word.matches("[a-zA-Z]+"))
                        {
                            dict.insert(word.toLowerCase());
                        }
                    }
                }
            }
            ResultBean result = new ResultBean(Constant.SUCCESS, dict.getDictionary());
            return result;

        } catch (IOException e)
        {
            logger.error(e);
        } finally
        {
            Util.safeReleaseResource(in);
            if (docx != null)
            {
                try
                {
                    in.close();
                } catch (Exception e)
                {
                    logger.info("close failed =", e);
                }
            }

            if (we != null)
            {
                try
                {
                    in.close();
                } catch (Exception e)
                {
                    logger.info("close failed =", e);
                }
            }

            if (scanner != null)
            {
                try
                {
                    in.close();
                } catch (Exception e)
                {
                    logger.info("close failed =", e);
                }
            }

            if (extractor != null)
            {
                try
                {
                    in.close();
                } catch (Exception e)
                {
                    logger.info("close failed =", e);
                }
            }
            // delAllFile(filePath);
        }
        //返回json
        return null;
    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public void exportExcel(@RequestBody List<Map<String, String>> origin,
                            HttpServletResponse response) throws Exception
    {
        logger.info("begin to translate word:" + origin);
        if (origin == null || origin.isEmpty())
        {
            logger.info("input is empty");
            return;
        }
        // String[] subWords = origin.split(",");
        ExcelData data = new ExcelData();
        data.setName(Constant.EXPORT_SHEET_NAME);
        List<String> titles = new ArrayList();
        titles.add(Constant.EXPORT_SHEET_ROW_WORD);
        titles.add(Constant.EXPORT_SHEET_ROW_FREQUENCY);
        titles.add(Constant.EXPORT_SHEET_ROW_PHONETIC);
        titles.add(Constant.EXPORT_SHEET_ROW_EXPLAINS);
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row;
        TranslateBean translateBean = null;

        for (Map<String, String> item : origin)
        {
            String word = item.get("word");
            String frequency = item.get("frequency");
            translateBean = YouDaoAPI.translate(word);
            row = new ArrayList();
            row.add(translateBean.getWord());
            row.add(frequency);
            row.add(translateBean.getPhonetic());
            row.add(translateBean.getExplains());
            rows.add(row);
        }
        data.setRows(rows);
        ExportExcelUtils.exportExcel(response, Constant.EXPORT_FILE_NAME, data);
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
                {
                    // 判断是文件还是文件夹
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
