package com.hz.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import sun.misc.BASE64Encoder;

public class CreateImgUtil {

	// 验证码图片的宽度。
	private static int width = 60;
	// 验证码图片的高度。
	private static int height = 20;
	// 字体高度
	private static int fontHeight;

	/**
	 * 初始化验证图片属性
	 */
	public static void initxuan() {
		// 宽度
		String strWidth = "86";
		// 高度
		String strHeight = "36";
		// 将配置的信息转换成数值
		try {
			if (strWidth != null && strWidth.length() != 0) {
				width = Integer.parseInt(strWidth);
			}
			if (strHeight != null && strHeight.length() != 0) {
				height = Integer.parseInt(strHeight);
			}
		} catch (NumberFormatException e) {
		}
		fontHeight = height - 3;
	}

	public  static void createImgForRandom(String str , String filePath) {
		initxuan();
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		// 设置字体。
		g.setFont(font);
		// 画边框。
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
		g.setColor(Color.BLACK);
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		g.setColor(new Color(17, 0, 255));
		g.drawString(str, 1, 30);

		// 输出文件
		File file = new File(filePath);
		try {
			ImageIO.write(buffImg, "JPG", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 释放资源
		g.dispose();
	}
	
	public static void main(String[] args) {
		createImgForRandom("2508","E:/1234.jpg");
		//System.out.println(GetImageStr("E:/1234.jpg"));
	}
	
	
	
	public static String GetImageStr(String imgFile)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //String imgFile = "111.jpg";//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try 
        {
            in = new FileInputStream(imgFile);        
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

}