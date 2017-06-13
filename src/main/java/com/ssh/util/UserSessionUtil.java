package com.ssh.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.ssh.entity.ChatRecords;
import com.ssh.entity.Flags;
import com.ssh.entity.Paper;
import com.ssh.entity.User;
import com.ssh.service.ChatRecordsService;
import com.ssh.service.PaperService;
import com.ssh.service.UserService;

/**
 * 更新Session中的user 、 chatRecords 、chatRecordsService 、paper
 * @author Jetvin
 *
 */
public class UserSessionUtil {

	public static void setUserSession(HttpSession session,String number, UserService userService,PaperService paperService,ChatRecordsService chatRecordsService){
				//登录用户信息
				User user = userService.findByNumber(number);
				List<Paper> papers = paperService.findAllPaper();
				Map<String, Boolean> maps = new HashMap<>();
				//问卷填写情况
				Flags flags = user.getFlags();
				if(flags == null){
					flags = new Flags();
					flags.setNumber(user.getNumber());
					
					//List<Boolean> list = new ArrayList<>();
					for(int i = 0 ; i < papers.size() ; i++){
						String key = papers.get(i).getPaperNumber();
						maps.put(key, false);
					}
					String json = (String) JSON.toJSONString(maps);
					flags.setFlags(json);
					session.setAttribute("flags", maps);
				}else{
					String json = flags.getFlags();
					//List<Boolean> list = JSON.parseObject(json, List.class);
					maps = JSON.parseObject(json, Map.class);
					if(maps.size() < papers.size()){
						for(int i = maps.size() ; i < papers.size(); i++){
							String key = papers.get(i).getPaperNumber();
							maps.put(key, false);
						}
						flags.setFlags((String) JSON.toJSONString(maps));
						session.setAttribute("flags", maps);
					}else if(maps.size() > papers.size() ){
						Map<String, Boolean> tempMaps = new HashMap<>();
						for(int i = 0 ; i < papers.size() ; i++){
							String key = papers.get(i).getPaperNumber();
							if(maps.containsKey(key) == true){
								tempMaps.put(key, maps.get(key));
							}
						}
						flags.setFlags((String) JSON.toJSONString(tempMaps));
						session.setAttribute("flags", tempMaps);
					}
					else {
						session.setAttribute("flags", maps);
					}
				}
				user.setFlags(flags);
				session.setAttribute("user", user);
				
				List<ChatRecords> chatRecords = chatRecordsService.findByNumber(number);
				System.out.println("AuthenticationSuccessHandler 聊天记录数：" +chatRecords.size());
				session.setAttribute("chatRecords", chatRecords);
				session.setAttribute("chatRecordsService",chatRecordsService);
				session.setAttribute("papers", papers);
	}
}
