package com.ssh.util;

import java.io.File;

/**
 * 判断头像的文件类型
 * @author Jetvin
 *
 */
public class IconIsExists {
	
	/**
	 * 
	 * @param number
	 * @return 0:默认头像 ；1：头像格式为jpg；2：头像格式为png
	 */
	public static int iconIsExists(String number){
		String iconPath = SessionContent.getSession().getServletContext().getRealPath("image");
		String filename = number+".jpg";
		File file = new File(iconPath,filename);
		if(file.exists()){
			return 1;
		}
		else{
			filename = number+".png";
			file = new File(iconPath,filename);
			if(file.exists()){
				return 2;
			}
		}
		
		return 0;
	}
}
