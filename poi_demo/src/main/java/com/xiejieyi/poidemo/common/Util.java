package com.xiejieyi.poidemo.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 类描述：
 *
 * @author
 */
public class Util
{
    protected static Log logger = LogFactory.getLog(Util.class);

    public static void main(String[] args) throws IOException
    {
        delAllFile("C:\\Users\\Administrator\\AppData\\Local\\Temp\\tomcat-docbase.9125128789171232242.8086" +
                "\\imgupload");

    }

    /**
     * 方法描述：清除文件目录里的文件
     *
     * @author xiejieyi
     * @date 2018/5/20 0020
     */
    public static void delAllFile(String path) throws IOException
    {
        File file = new File(path);
        if (!file.exists())
        {
            return;
        }
        if (!file.isDirectory())
        {
            return;
        }
        String[] tempList = file.list();
        File temp;
        for (int i = 0; i < tempList.length; i++)
        {
            if (path.endsWith(File.separator))
            {
                temp = new File(path + tempList[i]);
            } else
            {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile())
            {
                Files.deleteIfExists(Paths.get(temp.getCanonicalPath()));
                if (temp.delete())
                {
                    logger.info("delete file =" + temp.getName() + " success");
                } else
                {
                    logger.info("delete file =" + temp.getName() + "fail");
                }
            }
        }
        // logger.info("delete path =" + path+" success");
        return;
    }

    /**
     * 方法描述：将字节流存储到文件中
     *
     * @author xiejieyi
     * @date 2018/5/20 0020
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception
    {
        File targetFile = new File(filePath);
        if (!targetFile.exists())
        {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        try
        {
            out.write(file);
            out.flush();
        } finally
        {
            out.close();
        }

    }

    public static void safeReleaseResource(InputStream in)
    {
        if (in != null)
        {
            try
            {
                in.close();
            } catch (Exception e)
            {
                logger.info("close failed =", e);
            }
        }
    }

    public static void unZipFile(String fileZip, String destDir) throws IOException
    {
        logger.info("destDir=" + destDir);
        logger.info("fileZip=" + fileZip);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip), Charset.forName("GBK"));
        ZipEntry zipEntry = zis.getNextEntry();

        try{
            while (zipEntry != null)
            {
                String fileName = zipEntry.getName();
                File newFile = new File(destDir + "/" + fileName);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0)
                {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zipEntry = zis.getNextEntry();
            }
        }
        finally
        {
            zis.closeEntry();
            zis.close();
        }


    }
}
