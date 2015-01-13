package com.file.utilis;
 
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
 
 
/**
 * @date 2013-11-8 ����6:21:45
 * @version 1.0-SNAPSHOT
 */
@SuppressWarnings("resource")
public class FileUtils {
     
    /**
     * ��ȡ�ļ�MD5ֵ
     *
     * @param file
     * @return
     */
    public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0,
                    file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
     
    /**
     * ��ȡ�ļ���С
     *
     * @param file
     * @return
     */
    public static long getFileLength(File file)
            throws IOException {
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        return fis.available();
    }
     
    /**
     * ��ȡ�ļ���������
     *
     * @author WikerYong Email:<a href="#">yw_312@foxmail.com</a>
     * @version 2012-3-23 ����11:47:06
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] getBytesFromFile(File file)
            throws IOException {
        InputStream is = new FileInputStream(file);
         
        long length = file.length();
         
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
         
        byte[] bytes = new byte[(int) length];
         
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
         
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("���ܶ�ȡ�ļ�: " + file.getName());
        }
         
        is.close();
        return bytes;
    }
     
    /**
     * ��ȡ��׼�ļ���С����30KB��15.5MB
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileSize(File file)
            throws IOException {
        long size = getFileLength(file);
        DecimalFormat df = new DecimalFormat("###.##");
        float f;
        if (size < 1024 * 1024) {
            f = (float) ((float) size / (float) 1024);
            return (df.format(new Float(f).doubleValue()) + " KB");
        } else {
            f = (float) ((float) size / (float) (1024 * 1024));
            return (df.format(new Float(f).doubleValue()) + " MB");
        }
         
    }
     
    /**
     * �����ļ�
     *
     * @param f1
     *            Դ�ļ�
     * @param f2
     *            Ŀ���ļ�
     * @throws Exception
     */
    public static void copyFile(File f1, File f2)
            throws Exception {
        int length = 2097152;
        FileInputStream in = new FileInputStream(f1);
        FileOutputStream out = new FileOutputStream(f2);
        FileChannel inC = in.getChannel();
        FileChannel outC = out.getChannel();
        ByteBuffer b = null;
        while (true) {
            if (inC.position() == inC.size()) {
                inC.close();
                outC.close();
            }
            if ((inC.size() - inC.position()) < length) {
                length = (int) (inC.size() - inC.position());
            } else
                length = 2097152;
            b = ByteBuffer.allocateDirect(length);
            inC.read(b);
            b.flip();
            outC.write(b);
            outC.force(false);
        }
    }
     
    /**
     * ����ļ��Ƿ����
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean existFile(String fileName)
            throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("�ļ�δ�ҵ�:" + fileName);
        }
        return file.exists();
    }
     
    /**
     * ɾ���ļ�
     *
     * @param fileName
     */
    public static void deleteFile(String fileName)
            throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("�ļ�δ�ҵ�:" + fileName);
        }
        file.delete();
    }
     
    /**
     * ��ȡ�ļ����ַ���
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readFile(String fileName)
            throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("�ļ�δ�ҵ�:" + fileName);
        }
         
        BufferedReader in = new BufferedReader(new FileReader(file));
        StringBuffer sb = new StringBuffer();
        String str = "";
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }
        in.close();
        return sb.toString();
    }
     
    /**
     * ��ȡĿ¼���������ļ����ļ���
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static List<File> listFiles(String fileName)
            throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("�ļ�δ�ҵ�:" + fileName);
        }
        return Arrays.asList(file.listFiles());
    }
     
    /**
     * ����Ŀ¼
     *
     * @param dir
     */
    public static void mkdir(String dir) {
        String dirTemp = dir;
        File dirPath = new File(dirTemp);
        if (!dirPath.exists()) {
            dirPath.mkdir();
        }
    }
     
    /**
     * �½��ļ�
     *
     * @param fileName
     *            String ����·�����ļ��� ��:E:\phsftp\src\123.txt
     * @param content
     *            String �ļ�����
     */
    public static void createNewFile(String fileName, String content)
            throws IOException {
        String fileNameTemp = fileName;
        File filePath = new File(fileNameTemp);
        if (!filePath.exists()) {
            filePath.createNewFile();
        }
        FileWriter fw = new FileWriter(filePath);
        PrintWriter pw = new PrintWriter(fw);
        String strContent = content;
        pw.println(strContent);
        pw.flush();
        pw.close();
        fw.close();
         
    }
     
    /**
     * ɾ���ļ���
     *
     * @param folderPath
     *            �ļ���·��
     */
    public static void delFolder(String folderPath) {
        // ɾ���ļ���������������
        delAllFile(folderPath);
        String filePath = folderPath;
        java.io.File myFilePath = new java.io.File(filePath);
        // ɾ�����ļ���
        myFilePath.delete();
    }
     
    /**
     * ɾ���ļ�������������ļ�
     *
     * @param path
     *            �ļ���·��
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] childFiles = file.list();
        File temp = null;
        for (int i = 0; i < childFiles.length; i++) {
            // File.separator��ϵͳ�йص�Ĭ�����Ʒָ���
            // ��UNIXϵͳ�ϣ����ֶε�ֵΪ'/'����Microsoft Windowsϵͳ�ϣ���Ϊ '\'��
            if (path.endsWith(File.separator)) {
                temp = new File(path + childFiles[i]);
            } else {
                temp = new File(path + File.separator + childFiles[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + File.separatorChar + childFiles[i]);// ��ɾ���ļ���������ļ�
                delFolder(path + File.separatorChar + childFiles[i]);// ��ɾ�����ļ���
            }
        }
    }
     
    /**
     * ���Ƶ����ļ�����ͳ��ʽ
     *
     * @param srcFile
     *            ����·����Դ�ļ� �磺E:/phsftp/src/abc.txt
     * @param dirDest
     *            Ŀ���ļ�Ŀ¼�����ļ�Ŀ¼���������Զ����� �磺E:/phsftp/dest
     * @throws IOException
     */
    public static void copyFile(String srcFile, String dirDest)
            throws IOException {
        FileInputStream in = new FileInputStream(srcFile);
        mkdir(dirDest);
        FileOutputStream out = new FileOutputStream(dirDest + "/" + new File(srcFile).getName());
        int len;
        byte buffer[] = new byte[1024];
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();
    }
     
    /**
     * �����ļ���
     *
     * @param oldPath
     *            String Դ�ļ���·�� �磺E:/phsftp/src
     * @param newPath
     *            String Ŀ���ļ���·�� �磺E:/phsftp/dest
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath)
            throws IOException {
        // ����ļ��в����� ���½��ļ���
        mkdir(newPath);
        File file = new File(oldPath);
        String[] files = file.list();
        File temp = null;
        for (int i = 0; i < files.length; i++) {
            if (oldPath.endsWith(File.separator)) {
                temp = new File(oldPath + files[i]);
            } else {
                temp = new File(oldPath + File.separator + files[i]);
            }
             
            if (temp.isFile()) {
                FileInputStream input = new FileInputStream(temp);
                FileOutputStream output = new FileOutputStream(newPath + "/"
                        + (temp.getName()).toString());
                byte[] buffer = new byte[1024 * 2];
                int len;
                while ((len = input.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                }
                output.flush();
                output.close();
                input.close();
            }
            if (temp.isDirectory()) {// ��������ļ���
                copyFolder(oldPath + "/" + files[i], newPath + "/" + files[i]);
            }
        }
    }
     
    /**
     * �ƶ��ļ���ָ��Ŀ¼
     *
     * @param oldPath
     *            ����·�����ļ��� �磺E:/phsftp/src/ljq.txt
     * @param newPath
     *            Ŀ���ļ�Ŀ¼ �磺E:/phsftp/dest
     */
    public static void moveFile(String oldPath, String newPath)
            throws IOException {
        copyFile(oldPath, newPath);
        deleteFile(oldPath);
    }
     
    /**
     * �ƶ��ļ���ָ��Ŀ¼������ɾ���ļ���
     *
     * @param oldPath
     *            Դ�ļ�Ŀ¼ �磺E:/phsftp/src
     * @param newPath
     *            Ŀ���ļ�Ŀ¼ �磺E:/phsftp/dest
     */
    public static void moveFiles(String oldPath, String newPath)
            throws IOException {
        copyFolder(oldPath, newPath);
        delAllFile(oldPath);
    }
     
    /**
     * �ƶ��ļ���ָ��Ŀ¼����ɾ���ļ���
     *
     * @param oldPath
     *            Դ�ļ�Ŀ¼ �磺E:/phsftp/src
     * @param newPath
     *            Ŀ���ļ�Ŀ¼ �磺E:/phsftp/dest
     */
    public static void moveFolder(String oldPath, String newPath)
            throws IOException {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }
     
    /**
     * ��ѹzip�ļ�
     * ˵��:������ͨ��ZipOutputStream��ZipInputStreamʵ����zipѹ���ͽ�ѹ����.
     * ����:����java.util.zip������֧�ֺ���,��zip�ļ���������Ϊ���ĵ��ļ�ʱ,
     * �ͻ�����쳣:"Exception  in thread "main " java.lang.IllegalArgumentException 
     * at java.util.zip.ZipInputStream.getUTF8String(ZipInputStream.java:285)
     * @param srcDir
     *            ��ѹǰ��ŵ�Ŀ¼
     * @param destDir
     *            ��ѹ���ŵ�Ŀ¼
     * @throws Exception
     */
    public static void unZip(String srcDir, String destDir)
            throws IOException {
        int leng = 0;
        byte[] b = new byte[1024 * 2];
        /** ��ȡzip��ʽ���ļ� **/
        File[] zipFiles = new ExtensionFileFilter("zip").getFiles(srcDir);
        if (zipFiles != null && !"".equals(zipFiles)) {
            for (int i = 0; i < zipFiles.length; i++) {
                File file = zipFiles[i];
                /** ��ѹ�������� * */
                ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
                ZipEntry entry = null;
                while ((entry = zis.getNextEntry()) != null) {
                    File destFile = null;
                    if (destDir.endsWith(File.separator)) {
                        destFile = new File(destDir + entry.getName());
                    } else {
                        destFile = new File(destDir + File.separator + entry.getName());
                    }
                    /** �ѽ�ѹ���е��ļ�������Ŀ��Ŀ¼ * */
                    FileOutputStream fos = new FileOutputStream(destFile);
                    while ((leng = zis.read(b)) != -1) {
                        fos.write(b, 0, leng);
                    }
                    fos.close();
                }
                zis.close();
            }
        }
    }
     
    /**
     * ѹ���ļ�
     * ˵��:������ͨ��ZipOutputStream��ZipInputStreamʵ����zipѹ���ͽ�ѹ����.
     * ����:����java.util.zip������֧�ֺ���,��zip�ļ���������Ϊ���ĵ��ļ�ʱ,
     * �ͻ�����쳣:"Exception  in thread "main " java.lang.IllegalArgumentException 
     * at java.util.zip.ZipInputStream.getUTF8String(ZipInputStream.java:285)
     * @param srcDir
     *            ѹ��ǰ��ŵ�Ŀ¼
     * @param destDir
     *            ѹ�����ŵ�Ŀ¼
     * @throws Exception
     */
    public static void zip(String srcDir, String destDir)
            throws IOException {
        String tempFileName = null;
        byte[] buf = new byte[1024 * 2];
        int len;
        // ��ȡҪѹ�����ļ�
        File[] files = new File(srcDir).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    if (destDir.endsWith(File.separator)) {
                        tempFileName = destDir + file.getName() + ".zip";
                    } else {
                        tempFileName = destDir + File.separator + file.getName() + ".zip";
                    }
                    FileOutputStream fos = new FileOutputStream(tempFileName);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    ZipOutputStream zos = new ZipOutputStream(bos);// ѹ����
                     
                    ZipEntry ze = new ZipEntry(file.getName());// ѹ�����ļ���
                    zos.putNextEntry(ze);// д���µ�ZIP�ļ���Ŀ��������λ����Ŀ���ݵĿ�ʼ��
                     
                    while ((len = bis.read(buf)) != -1) {
                        zos.write(buf, 0, len);
                        zos.flush();
                    }
                    bis.close();
                    zos.close();
                     
                }
            }
        }
    }
     
    /**
     * ��ȡ����
     *
     * @param inSream
     * @param charsetName
     * @return
     * @throws Exception
     */
    public static String readData(InputStream inSream, String charsetName)
            throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inSream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inSream.close();
        return new String(data, charsetName);
    }
    
    public static boolean rename(String srcPath,String destPath){
		  //���ƶ����ļ���
		  File file = new File(srcPath);
		  //Ŀ���ļ���
		  File dir = new File(destPath);
		  //���ļ��ƶ�����һ���ļ�Ŀ¼��
		  boolean success = file.renameTo(dir);
		  return success;
		 }
   
     
    /**
     * һ��һ�ж�ȡ�ļ����ʺ��ַ���ȡ������ȡ�����ַ�ʱ���������
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static Set<String> readFileLine(String path)
            throws IOException {
        Set<String> datas = new HashSet<String>();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ((line = br.readLine()) != null) {
            datas.add(line);
        }
        br.close();
        fr.close();
        return datas;
    }
    
    
    public static String getExtensionExclude(String filepath){
  
        int idx = filepath.lastIndexOf(".");
        if (idx == -1) {
            return "";
        } else if (idx == filepath.length() - 1) {
            return "";
        } else {
            return filepath.substring(0,idx);
        }
    }
     
    public static void main(String[] args) {
        try {
            unZip("c:/test", "c:/test");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     
}
 
 
class ExtensionFileFilter
        implements FileFilter {
     
    private String extension;
     
    public ExtensionFileFilter(String extension) {
        this.extension = extension;
    }
     
    public File[] getFiles(String srcDir) throws IOException {
        return (File[]) FileUtils.listFiles(srcDir).toArray();
    }
     
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return false;
        }
         
        String name = file.getName();
        // find the last
        int idx = name.lastIndexOf(".");
        if (idx == -1) {
            return false;
        } else if (idx == name.length() - 1) {
            return false;
        } else {
            return this.extension.equals(name.substring(idx + 1));
        }
    }
    
   
}