package com.file.utilis;

import java.io.File;
import java.io.IOException;

public class FileUtilsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String f1 = "D:/DevSoftware/testfolder/crossA2.html";
		String f2 = "D:/DevSoftware/testfolder_bk/crossA6.html";
		//System.out.println(rename(f1,f2));
		System.out.println(FileUtils.getExtensionExclude(f1));		
//		try {
//			FileUtils.copyFile(f1, f2);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
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

}
