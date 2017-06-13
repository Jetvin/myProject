package com.ssh.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.alibaba.fastjson.JSON;
import com.ssh.entity.Question;
import com.ssh.entity.Test;

/**
 * 读取问卷问题，返回一个List集合
 * @author Jetvin
 *
 */
public class ReadTestQuestions {
	
	@SuppressWarnings("resource")
	public static List<Question> read(String paperNumber,File file) throws IOException{
		List<Question> questions = new ArrayList<>();
		String fileName = file.getName();
		int count = 1;
		int index = 1;
		FileInputStream in = null;
		WordExtractor extractor = null;
		XWPFDocument xwpf = null;
		try {
			if(fileName.endsWith(".doc")){
				in = new FileInputStream(file);
				extractor = new WordExtractor(in);
				String paraTexts[] = extractor.getParagraphText();
				Question question = new Question();
				for (int i = 0; i < paraTexts.length; i++) {
					String s = paraTexts[i].substring(0, paraTexts[i].length()-2);
					switch (count%5) {
					case 1:
						if(index < 10){
							question.setId(paperNumber + "0" + index);
							question.setQuestionNumber("0" + index);
						}else{
							question.setId(paperNumber + index);
							question.setQuestionNumber("" + index);
						}
						question.setPaperNumber(paperNumber);
						question.setQuestion(s);
						count++;
						index++;
						break;
					case 2:
//						System.out.println(s);
						question.setAnswerA(s);
						count++;
						break;
					case 3:
//						System.out.println(s);
						question.setAnswerB(s);
						count++;
						break;
					case 4:
//						System.out.println(s);
						question.setAnswerC(s);
						count++;
						break;
					case 0:
//						System.out.println(s);
						question.setAnswerD(s);
//						System.out.println(question.toString());
						questions.add(question);
						question = new Question();
						count++;
						break;
					default:
						break;
					}
				}		
			}
			else if(fileName.endsWith(".docx")){
				in  = new FileInputStream(file);
				xwpf = new XWPFDocument(in);
				List<XWPFParagraph> listParagraphs = xwpf.getParagraphs();
				Question question = new Question();
				for(int i = 0 ; i < listParagraphs.size() ; i++){
					String s = listParagraphs.get(i).getText();
					switch (count%5) {
					case 1:
						if(index < 10){
							question.setId(paperNumber + "0" + index);
							question.setQuestionNumber("0" + index);
						}else{
							question.setId(paperNumber + index);
							question.setQuestionNumber("" + index);
						}
						question.setPaperNumber(paperNumber);
						question.setQuestion(s);
						count++;
						index++;
						break;
					case 2:
						question.setAnswerA(s);
						count++;
						break;
					case 3:
						question.setAnswerB(s);
						count++;
						break;
					case 4:
						question.setAnswerC(s);
						count++;
						break;
					case 0:
						question.setAnswerD(s);
						questions.add(question);
						question = new Question();
						count++;
						break;
					default:
						break;
					}
				}
				
			}	
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			if(in != null){
				in.close();
			}
			if(extractor != null){
				extractor.close();
			}
			if(xwpf != null){
				xwpf.close();
			}
		}
		
		return questions;
	}
	
//	@SuppressWarnings("resource")
//	public static JSON read(HttpServletRequest request,String paperName) throws IOException{
//		String path = request.getSession().getServletContext().getRealPath("upload");
//		File targetFile = new File(path, paperName+".doc");
//		String fileName = paperName+".doc";
//		if(!targetFile.exists()){  
//            targetFile = new File(path, paperName+".docx");  
//            fileName = paperName+".docx";
//        }
//		List<Test> list = new ArrayList<Test>();
//		Test test = new Test();
//		int count = 1;
//		FileInputStream in = null;
//		WordExtractor extractor = null;
//		XWPFDocument xwpf = null;
//		try {
//			if(fileName.endsWith(".doc")){
//				in = new FileInputStream(targetFile);
//				extractor = new WordExtractor(in);
//				String paraTexts[] = extractor.getParagraphText();
//				for (int i = 0; i < paraTexts.length; i++) {
//					String s = paraTexts[i].substring(0, paraTexts[i].length()-2);
//					switch (count%5) {
//					case 1:
//						test.setQuestion(s);
//						count++;
//						break;
//					case 2:
//						test.setAnswerA(s);
//						count++;
//						break;
//					case 3:
//						test.setAnswerB(s);
//						count++;
//						break;
//					case 4:
//						test.setAnswerC(s);
//						count++;
//						break;
//					case 0:
//						test.setAnswerD(s);
//						list.add(test);
//						test = new Test();
//						count++;
//						break;
//					default:
//						break;
//					}
////					System.out.println(i+":" + paraTexts[i].substring(0, paraTexts[i].length()-2));
//				}		
//			}
//			else if(fileName.endsWith(".docx")){
////				System.out.println(".docx");
//				in  = new FileInputStream(targetFile);
//				xwpf = new XWPFDocument(in);
//				List<XWPFParagraph> listParagraphs = xwpf.getParagraphs();
//				for(int i = 0 ; i < listParagraphs.size() ; i++){
//					String s = listParagraphs.get(i).getText();
//					switch (count%5) {
//					case 1:
//						test.setQuestion(s);
//						count++;
//						break;
//					case 2:
//						test.setAnswerA(s);
//						count++;
//						break;
//					case 3:
//						test.setAnswerB(s);
//						count++;
//						break;
//					case 4:
//						test.setAnswerC(s);
//						count++;
//						break;
//					case 0:
//						test.setAnswerD(s);
//						list.add(test);
//						test = new Test();
//						count++;
//						break;
//					default:
//						break;
//					}
////					System.out.println(i+":"+listParagraphs.get(i).getText());
//				}
//				
//			}	
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}finally {
//			if(in != null){
//				in.close();
//			}
//			if(extractor != null){
//				extractor.close();
//			}
//			if(xwpf != null){
//				xwpf.close();
//			}
//		}
//		
//		JSON json = (JSON) JSON.toJSON(list);
//		return json;
//	}
}
