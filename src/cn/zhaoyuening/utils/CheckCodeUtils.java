package cn.zhaoyuening.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class CheckCodeUtils {
	//验证码提取字符的字符库 包含大小写字母与数字
	private static ArrayList<Character> chars ;
	private static Random random = new Random();
	
	static{
		chars = new ArrayList<Character>();
		//添加大小写字母与数字到 字符库中
		for(char c='a';c<='z';c++){
			chars.add(c);
		}
		for(char c='0';c<='9';c++){
			chars.add(c);
		}
		for(char c='A';c<='Z';c++){
			chars.add(c);
		}
	}
	//随机的从字符库中抽取出字符 作为验证码
	private static  char getChar(){
		int index = random.nextInt(chars.size()-1);
		return chars.get(index);
	}
	
	public static String showCheckCode(int count,int width,int height,int fontSize,OutputStream out) throws IOException{
		BufferedImage bi = new BufferedImage(width	,height, BufferedImage.TYPE_INT_RGB);
		//获取画笔
		Graphics2D graphics = (Graphics2D)bi.getGraphics();
		//画背景
		graphics.setColor(Color.BLUE);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.WHITE);
		graphics.fillRect(3, 3, width-6, height-6);
		
		//获取4字符随机验证码 
		//显示验证码到图片
		graphics.setColor(Color.RED);
		char[] words = new char[count];
		int x=width/6;
		int y=x;
		int di = width/6;
		//设置字体
		int size =fontSize;
		graphics.setFont(new Font("font",Font.BOLD,size));
		for(int i=0;i<words.length;i++){
			int jiaodu = random.nextInt(30);
			jiaodu -= 30;
			double theta =  (double)jiaodu/(double)180*Math.PI;
			graphics.rotate(theta,x,y);
			words[i]=getChar();
			graphics.drawString(words[i]+"", x, y);
			graphics.rotate(-theta,x,y);
			x+=di;
		}
		//画干扰线
		for(int i=0;i<8;i++){
			graphics.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
		}
		//输出图像到浏览器
		ImageIO.write(bi, "jpg", out);
		StringBuffer sb = new StringBuffer();
		for (char c : words) {
			sb.append(c);
		}
		return sb.toString();
	}
		
}
