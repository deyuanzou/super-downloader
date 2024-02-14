package src.com.yuan;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {

    private Integer threadNum;


    /**
     * 读取配置文件，完成初始化
     *
     * @param propDir 配置文件存放的目录
     */
    public void start(String propDir) {
        //读取配置文件
        File propFile = new File(propDir + "/config.properties");
        Properties properties = new Properties();
        Reader reader = null;

        try {
            reader = new FileReader(propFile);
            properties.load(reader);
            String threadNum = properties.getProperty("thread-num");
            String targetDir = properties.getProperty("target-dir");
            this.threadNum = Integer.parseInt(threadNum);
            this.superDownload(propDir + "/download.txt", targetDir);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    /**
     * 多线程下载文件保存到本地
     *
     * @param downloadTxt 下载文件网址储存目录
     * @param targetDir   下载文件保存的目录，要确保已存在
     */
    public void superDownload(String downloadTxt, String targetDir) {
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("[INFO]发现下载目录[" + dir.getPath() + "]不存在，已自动创建");
        }

        List<String> resource = new ArrayList<>();

        BufferedReader bfreader = null;
        ExecutorService threadPool = null;
        try {
            bfreader = new BufferedReader(new FileReader(downloadTxt));
            String line = null;
            while ((line = bfreader.readLine()) != null) {
                resource.add(line);
                System.out.println(line);
            }
            threadPool = Executors.newFixedThreadPool(this.threadNum);
            Downloader that = this;//将当前对象传递给匿名类
            for (String item:resource) {
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        that.download(item,targetDir);
                    }
                });
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bfreader != null) {
                try {
                    bfreader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (threadPool != null) {
                threadPool.shutdown();
            }
        }


    }


    /**
     * 单线程下载文件保存到本地
     *
     * @param source    源文件的网址
     * @param targetDir 下载文件保存的目录，要确保已存在
     */
    public void download(String source, String targetDir) {
        InputStream inputStream = null;//输入流
        OutputStream outputStream = null;//输出流

        try {

            //从网址中截取文件名 即最后一个`/`后的内容
            String fileName = source.substring(source.lastIndexOf("/") + 1);
            //获取当前时间戳
            long timestamp = System.currentTimeMillis();
            //将时间戳转换为字符串
            String timestampStr = String.valueOf(timestamp);

            //加上时间戳得到唯一的文件名
            fileName = timestampStr + "-" + fileName;

            //创建目标文件对象
            File targetFile = new File(targetDir + "/" + fileName);
            if (!targetFile.exists()) {
                targetFile.createNewFile();//如果文件不存在则新建文件
            }

            //获取网络资源输入流对象
            URL url = new URL(source);
            URLConnection connection = url.openConnection();
            inputStream = connection.getInputStream();

            //获取文件输出流对象
            outputStream = new FileOutputStream(targetFile);

            byte[] readArr = new byte[1024];//每次最多读取1KB
            int len = 0;//保存每次读取的字节长度

            //循环读取
            while ((len = inputStream.read(readArr)) != -1) {
                outputStream.write(readArr, 0, len);
            }

            System.out.println("[INFO]图片下载完成:" + source + "\n\t ->" + targetFile.getPath() + "(" + Math.floor(targetFile.length() / 1024) + "kb)");


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            try {

                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }


}
