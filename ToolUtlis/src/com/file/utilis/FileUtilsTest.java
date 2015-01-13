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
		  //被移动的文件夹
		  File file = new File(srcPath);
		  //目标文件夹
		  File dir = new File(destPath);
		  //将文件移动到另一个文件目录下
		  boolean success = file.renameTo(dir);
		  return success;
		 }

}
