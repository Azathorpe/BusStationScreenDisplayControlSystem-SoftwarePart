package com.azathorpe.Utils;

import java.io.*;

/**
 * 文件工具类
 *
 * @version 1.0
 * 更新日志 :
 *  - 1.0 : 增加了读取文件和写入文件的功能
 * @author Azathorpe
 * @date 2026年3月31日
 * @since 1.0
 */
public class FileUtils {

    /**
     * 读取资源文件里面的内容
     * @param fileName 资源文件名称
     * @return 文件内容
     */
    public static String readResources(String fileName) throws IOException {
        //这里不上实际环境应该是勉强能跑的
        //TODO: 解决这里上实际环境可能出现的BUG
        InputStream is = new BufferedInputStream(FileUtils.class.getClassLoader().getResourceAsStream(fileName));
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = br.readLine()) != null)
                sb.append(line).append("\n");
            br.close();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        is.close();
        return sb.toString();
    }

    /**
     * 读取文件
     * @param filePath 文件路径
     * @return 文件内容
     */
    public static String readFile(String filePath){
        StringBuffer sb = new StringBuffer();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while((line = br.readLine()) != null)
                sb.append(line).append("\n");

            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 向对应文件路径写入内容
     * @param filePath 文件路径
     * @param content 内容
     */
    public static void writeFile(String filePath, String content){
        try {
            FileWriter fw = new FileWriter(filePath);
            fw.write(content);

            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
