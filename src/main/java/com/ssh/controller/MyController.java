package com.ssh.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ssh.entity.Answer;
import com.ssh.entity.ChatRecords;
import com.ssh.entity.Flags;
import com.ssh.entity.ListDTO;
import com.ssh.entity.Login;
import com.ssh.entity.Paper;
import com.ssh.entity.Question;
import com.ssh.entity.RecordDTO;
import com.ssh.entity.Remark;
import com.ssh.entity.User;
import com.ssh.entity.UserQueryDTO;
import com.ssh.service.AnswerService;
import com.ssh.service.AspectFlag;
import com.ssh.service.ChatRecordsService;
import com.ssh.service.PaperService;
import com.ssh.service.UserService;
import com.ssh.util.ExcelUtil;
import com.ssh.util.IconIsExists;
import com.ssh.util.InitServlet;
import com.ssh.util.MD5PasswordEncoder;
import com.ssh.util.PageBean;
import com.ssh.util.ReadTestQuestions;
import com.ssh.util.ResultMessage;
import com.ssh.util.RuleBean;
import com.ssh.util.UserSessionUtil;

@Controller
public class MyController {
	@Autowired
	UserService userService;
	@Autowired
	AnswerService answerService;
	@Autowired
	PaperService paperService;
	@Autowired
	ChatRecordsService chatRecordsService;
	@Autowired
	AspectFlag aspectFlag;
	
	/**
	 * 授权失败
	 * @return
	 */
	@RequestMapping(value="/sessionAuthenticationFailure")
	public @ResponseBody String sessionAuthenticationFailure(){
		
		return "401";
	}
	
	/**
	 * 登录请求，返回登陆界面
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/login")
	public String getLoginPage(HttpServletRequest request) throws IOException {

		return "login";
	}
	
	/**
	 * 注销请求，返回登陆界面，并清空session
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView getLogoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			Login login = user.getLogin();
			user.setLogin(login);
			userService.updateOrSaveUser(user);
			InitServlet.getSocketList().remove(user.getNumber());
		}

		if (authentication != null) {
			SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
			logoutHandler.setInvalidateHttpSession(true);
			logoutHandler.logout(request, response, authentication);
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/login");
		return modelAndView;
	}
	
//	@RequestMapping(value = "/temp")
//	public ModelAndView getTemp(HttpSession session){
//		User user = (User) session.getAttribute("user");
//		session.removeAttribute("user");
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("user",user);
//		modelAndView.setViewName("redirect:/main");
//		return modelAndView;
//	}
	
	/**
	 * 主界面请求，返回主界面
	 * 
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/main")
	public ModelAndView getMainPage(HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		String number = user.getNumber();
		int iconType = IconIsExists.iconIsExists(number);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("iconType",iconType);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("main");
		
		return modelAndView;
	}
	
	/**
	 * 问卷列表界面
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/test")
	public ModelAndView getTestPage(HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		String number = user.getNumber();
		int iconType = IconIsExists.iconIsExists(number);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("iconType",iconType);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("test");
		
		//要改吗？？？？，待定。。。
		UserSessionUtil.setUserSession(session, number, userService, paperService, chatRecordsService);
		
		return modelAndView;
	}
	
	/**
	 * 学生、教师的一些其他信息与操作的界面
	 * @return
	 * @throws Exception ;
	 */
	@RequestMapping(value = "/information")
	public ModelAndView getInformationPage(HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		String number = user.getNumber();
		int iconType = IconIsExists.iconIsExists(number);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("iconType",iconType);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("information");
		
		return modelAndView;
	}
	
	/**
	 * 学生查看结果界面
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/u_result")
	public ModelAndView getUserResultPage(HttpSession session) throws Exception {
		aspectFlag.getUserResultPage();
		
		User user = (User) session.getAttribute("user");
		String number = user.getNumber();
		int iconType = IconIsExists.iconIsExists(number);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("iconType",iconType);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("u_result");
		
		UserSessionUtil.setUserSession(session, number, userService, paperService, chatRecordsService);
		
		return modelAndView;
	}

	/**
	 * 教师查看结果界面
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/t_result")
	public ModelAndView getAdminResultPage(HttpSession session) throws Exception {
		aspectFlag.getAdminResultPage();
		
		User user = (User) session.getAttribute("user");
		String number = user.getNumber();
		int iconType = IconIsExists.iconIsExists(number);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("iconType",iconType);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("t_result");
		
		UserSessionUtil.setUserSession(session, number, userService, paperService, chatRecordsService);
		
		return modelAndView;
	}
	
	/**
	 * 学生聊天界面
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/u_consult")
	public ModelAndView getUserconsultPage(HttpSession session) throws Exception {
		aspectFlag.getUserconsultPage();
		User active_user = (User) session.getAttribute("user");
		String active_number = active_user.getNumber();
		
		List<User> users = userService.findAllUser();
		List<ChatRecords> chatRecords = new ArrayList<>();
		List<Map<String, String>> teachers = new ArrayList<>();
		HashMap<String, MyWebSocket> userMsgMap = InitServlet.getSocketList();
		for(User user : users){
			Login login = user.getLogin();
			if(login.getAuthority().equals("ROLE_ADMIN")){
				Map<String, String> map = new HashMap<>();
				String number = user.getNumber();
				String name = user.getName();
				String status = "离线";
				map.put("number", number);
				map.put("name", name);
				if(userMsgMap.containsKey(number)){
					status = "在线";
				}
				map.put("status", status);
				teachers.add(map);
				
				List<ChatRecords> lists = chatRecordsService.findByCondition(number, active_number);
				if(lists.size() == 0){
					ChatRecords c = new ChatRecords();
					c.setContent(null);
					chatRecords.add(c);
				}else{
					chatRecords.add(lists.get(lists.size()-1));
				}
			}
		}
		
		int iconType = IconIsExists.iconIsExists(active_number);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("iconType",iconType);
		modelAndView.addObject("user", active_user);
		modelAndView.addObject("teachers",teachers);
		modelAndView.addObject("chatRecords",chatRecords);
		modelAndView.setViewName("u_consult");
		
		return modelAndView;
	}
	
	/**
	 * 教师聊天界面
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="t_consult")
	public ModelAndView getAdminconsultPage(HttpSession session) throws Exception{
		aspectFlag.getAdminconsultPage();
		
		User user = (User) session.getAttribute("user");
		String number = user.getNumber();
		int iconType = IconIsExists.iconIsExists(number);
		
		List<ChatRecords> chatRecords = chatRecordsService.findByNumber(user.getNumber());
		Map<String, String> keyMaps = new HashMap<>();
		List<String> studentNames = new ArrayList<>();
		List<String> studentNumbers = new ArrayList<>();
		HashMap<String, MyWebSocket> userMsgMap = InitServlet.getSocketList();
		for(ChatRecords chatRecord : chatRecords){
			if(chatRecord.getFromId().equals(user.getNumber()) == false){
				keyMaps.put(chatRecord.getFromId(), chatRecord.getToId());
			}
			else {
				keyMaps.put(chatRecord.getToId(), chatRecord.getFromId());
			}
			
		}
		Set<String> keyset = keyMaps.keySet();
		List<RecordDTO> records = new ArrayList<>();
		for(String key : keyset){
			String fromId = key;
			String toId = keyMaps.get(key);
			List<ChatRecords> lists = chatRecordsService.findByCondition(fromId,toId);
			ChatRecords chatRecord = lists.get(lists.size()-1);
			RecordDTO recordDTO = new RecordDTO();
			String name = userService.findByNumber(key).getName();
			String fromName = userService.findByNumber(chatRecord.getFromId()).getName();
			String toName = userService.findByNumber(chatRecord.getToId()).getName();
			recordDTO.setNumber(key);
			recordDTO.setName(name);
			recordDTO.setFromName(fromName);
			recordDTO.setToName(toName);
			recordDTO.setFromId(chatRecord.getFromId());
			recordDTO.setToId(chatRecord.getToId());
			recordDTO.setTime(chatRecord.getTime());
			recordDTO.setContent(chatRecord.getContent());
			recordDTO.setStatus(chatRecord.getStatus());
			records.add(recordDTO);
		}
		
		for(int i=0 ; i < records.size()-1 ; i++){
			RecordDTO recordDTOi = records.get(i);
			
			for(int j = i+1 ; j < records.size() ; j++){
				RecordDTO recordDTOj = records.get(j);
				if(recordDTOi.getTime().compareTo(recordDTOj.getTime()) < 0){
					RecordDTO temp = recordDTOi;
					recordDTOi = recordDTOj;
					recordDTOj = temp;
					
					records.set(i, recordDTOi);
					records.set(j, recordDTOj);
				}
				
			}
		}
		for(RecordDTO record : records){
			studentNumbers.add(record.getNumber());
			if(userMsgMap.containsKey(record.getNumber())){
				studentNames.add("在线");
			}else{
				studentNames.add("离线");
			}
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("iconType",iconType);
		modelAndView.addObject("user", user);
		modelAndView.addObject("records",records);
		modelAndView.addObject("studentNames",studentNames);
		modelAndView.addObject("studentNumbers", studentNumbers);
		modelAndView.setViewName("t_consult");
		
		return modelAndView;
	}
	
	/**
	 * 查询界面
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/search")
	public ModelAndView getSearchPage(HttpSession session) throws Exception {
		aspectFlag.getSearchPage();
		
		User user = (User) session.getAttribute("user");
		String number = user.getNumber();
		int iconType = IconIsExists.iconIsExists(number);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("iconType",iconType);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("search");
		
		UserSessionUtil.setUserSession(session, number, userService, paperService, chatRecordsService);
		return modelAndView;
	}

	/**
	 * 读取问卷试题，返回填写问卷的界面，此方法被Ajax异步请求调用
	 * 
	 * @param session
	 * @param testname
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/answer", method = RequestMethod.POST)
	public ModelAndView getAnswerPage(HttpServletRequest request, HttpSession session, @RequestParam String paperNumber)
			throws Exception {
		System.out.println("getAnswerPage -- " + paperNumber);
		Paper paper = paperService.findByPaperNumber(paperNumber);
		String paperName = paper.getPaperName();
		List<Question> questions = paper.getQuestions();
		JSON json = (JSON) JSON.toJSON(questions);

		User user = (User) session.getAttribute("user");
		String number = user.getNumber();
		int iconType = IconIsExists.iconIsExists(number);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("iconType",iconType);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("answer");
		
		session.setAttribute("paperNumber",paperNumber);
		session.setAttribute("json", json);
		session.setAttribute("title",paperName);
		return modelAndView;
	}

	/**
	 * 读取问卷试题，返回填写问卷的界面，此方法被链接请求调用
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/answer")
	public ModelAndView getAnswerPage(HttpSession session) throws Exception {
		System.out.println("getAnswerPage 无参数");
		User user = (User) session.getAttribute("user");
		String number = user.getNumber();
		int iconType = IconIsExists.iconIsExists(number);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("iconType",iconType);
		modelAndView.addObject("user", user);;
		modelAndView.setViewName("answer");
		
		return modelAndView;
	}

	/**
	 * 
	 * @return 返回无权访问的提示页面
	 */
	@RequestMapping(value = "/accessDenied")
	public String getAccessDeniedPage() {
		return "403";
	}
	
	/**
	 * 会话超时
	 * @return
	 */
	@RequestMapping(value="sessionTimeout")
	public String getSessionTimeout(){
		
		return "sessiontimeout";
	}
	
	/**
	 * 读取问卷问题
	 * 
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/readTestQuestion")
	public @ResponseBody JSON getReadTestQuestion(HttpServletRequest request, HttpSession session) throws IOException {
		String paperNumber = (String) session.getAttribute("paperNumber");
		Paper paper = paperService.findByPaperNumber(paperNumber);
		List<Question> questions = paper.getQuestions();
		JSON json = (JSON) JSON.toJSON(questions);
		return json;
	}

	/**
	 * 读取问卷问题
	 * 
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/readTestQuestion", method = RequestMethod.POST)
	public @ResponseBody JSON getReadTestQuestion(@RequestParam String paperNumber, HttpServletRequest request)
			throws IOException {
		Paper paper = paperService.findByPaperNumber(paperNumber);
//		String paperName = paper.getPaperName();
		List<Question> questions = paper.getQuestions();
		JSON json = (JSON) JSON.toJSON(questions);
		return json;
	}

	/**
	 * 提交问卷答案，并将以填写的问卷flag标记为true
	 * 
	 * @param session
	 * @param answers
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveAnswer")
	public @ResponseBody ResultMessage saveAnswer(HttpSession session, @RequestParam String answers, @RequestParam int count) {
		
		try {
			// 登录用户
			User user = (User) session.getAttribute("user");
			String number = user.getNumber();

			// 将要更新的内容
			Flags flags = user.getFlags();
			String stringjson = flags.getFlags();
			Map<String, Boolean> booleans = JSON.parseObject(stringjson, Map.class);
			List<Answer> lists = user.getAnswers();
			// List<Answer> lists = new ArrayList<>();

			String paperNumber = (String) session.getAttribute("paperNumber");
			booleans.put(paperNumber, true);
			stringjson = (String) JSON.toJSONString(booleans);
			flags.setFlags(stringjson);
//			System.out.println("------saveAnswer-----");
			String[] answer = new String[count];
			answer = answers.split(" ");
			for (int i = 0; i < count; i++) {
				Answer a = new Answer();
				if (i < 9) {
					a.setId(number + paperNumber + "0" + (i + 1));
				} else {
					a.setId(number + paperNumber + (i + 1));
				}
				a.setNumber(number);
				a.setQuestionNumber(i + 1);
				a.setAnswer(answer[i]);
				a.setPaperNumber(paperNumber);
				lists.add(a);
//				System.out.println("saveAnswer [ " + (i + 1) + " : " + answer[i] + " ]");
			}

			user.setFlags(flags);
			user.setAnswers(lists);
			userService.updateOrSaveUser(user);
			session.setAttribute("user", user);
			session.setAttribute("flags", booleans);
			
			return new ResultMessage(false, "[ Save   : Success ]");
		} catch (Exception e) {
			// TODO: handle exception
			return new ResultMessage(false, "[ Save   : Error ]");
		}
	}

	/**
	 * 查看结果，返回json数据，用于绘画图形
	 * 
	 * @param session
	 * @param text
	 * @return
	 */
	@RequestMapping(value = "findResult")
	public @ResponseBody JSON findResult(HttpSession session, @RequestParam String paperNumber) {
		aspectFlag.findResult();
		System.out.println(paperNumber);
		Paper paper = paperService.findByPaperNumber(paperNumber);
		User user = (User) session.getAttribute("user");
		String number = user.getNumber();
		String remark = "暂无评语";
		// 根据学号与问卷名称查询结果
		List<Answer> lists = answerService.findResult(number, paperNumber);
		System.out.println("结果集长度:" + lists.size());
		int A = 0, B = 0, C = 0, D = 0;
		if (lists.size() > 0) {
			remark = "";
			int times = 100 / lists.size();
			for (Answer answer : lists) {
				String key = answer.getAnswer();
				switch (key) {
				case "A":
					A++;
					break;
				case "B":
					B++;
					break;
				case "C":
					C++;
					break;
				case "D":
					D++;
					break;
				default:
					break;
				}
			}
			System.out.println("[ A:" + A + " , B:" + B + " , C:" + C + " , D:" + D + "]");
			List<Remark> remarks = paper.getRemarks();
			int score = 4*A + 3*B + 2*C + D;
			for(Remark r : remarks){
				if(score >= r.getScore()){
					remark = r.getRemark();
					break;
				}
			}
			
			A = A * times;
			B = B * times;
			C = C * times;
			D = D * times;

			HashMap<String, String> map = new HashMap<>();
			map.put("A", A+"");
			map.put("B", B+"");
			map.put("C", C+"");
			map.put("D", D+"");
			map.put("remark", remark);
			
			JSON json = (JSON) JSON.toJSON(map);
			return json;
		}

		System.out.println("[ A:" + A + " , B:" + B + " , C:" + C + " , D:" + D + "]");
		Map<String, String> map = new HashMap<>();
		map.put("message", "FAILED");
		map.put("remark", remark);
		JSON json = (JSON) JSON.toJSON(map);

		return json;

	}

	/**
	 * 修改密码
	 * 
	 * @param session
	 * @param old_pwd
	 * @param fir_pwd
	 * @param sec_pwd
	 * @return
	 */
	@RequestMapping(value = "modify_pwd")
	public @ResponseBody ResultMessage modifyPwd(HttpSession session, String old_pwd, String fir_pwd, String sec_pwd) {
		try {
			User user = (User) session.getAttribute("user");
			MD5PasswordEncoder encoder = new MD5PasswordEncoder();
			String str1 = encoder.encodePassword(old_pwd, user.getNumber());
			String str2 = user.getLogin().getPassword();

			if (str1.equals(str2) == true) {
				System.out.println("修改成功");
				String password = encoder.encodePassword(fir_pwd, user.getNumber());
				user.getLogin().setPassword(password);
				userService.updateOrSaveUser(user);
				session.setAttribute("user", user);
				
				return new ResultMessage(true, "[ Modify   : Success ]");
			} else {
				System.out.println("修改失败");
				
				return new ResultMessage(false, "[ Modify   : Failure ]");
			}	
		} catch (Exception e) {
			// TODO: handle exception
			return new ResultMessage(false, "[ Modify   : Error ]");
		}
	}

	/**
	 * 修改基本信息
	 * 
	 * @param session
	 * @param name
	 * @param sex
	 * @param borth
	 * @param grade
	 * @param institute
	 * @param major
	 * @return
	 */
	@RequestMapping(value = "modify_info")
	public @ResponseBody ResultMessage modifyInfo(HttpSession session, MultipartFile icon, String name, String sex, String borth, String grade,
			String institute, String major) {
		System.out.println(name + "-" + sex + "-" + borth + "-" + grade + "-" + institute + "-" + major);
		
		try {
			User user = (User) session.getAttribute("user");
			String iconPath = session.getServletContext().getRealPath("image");
			String number = user.getNumber();
			if(icon != null){
				String iconName = icon.getOriginalFilename();
				iconName = number+iconName.substring(iconName.indexOf("."));
				System.out.println(iconName);
				File iconFile = new File(iconPath, iconName);
				icon.transferTo(iconFile);
			}
			user.setName(name);
			user.setSex(sex);
			user.setBorth(borth);
			user.setGrade(grade);
			user.setInstitute(institute);
			user.setMajor(major);
			userService.updateOrSaveUser(user);
			session.setAttribute("user", user);
			
			return new ResultMessage(true, "[ Modify   : Success ]");
		} catch (Exception e) {
			
			return new ResultMessage(false, "[ Modify   : Error ]");
		}
	}

	/**
	 * 修改联系方式
	 * 
	 * @param session
	 * @param qq
	 * @param tel
	 * @param addr
	 * @return
	 */
	@RequestMapping(value = "modify_contact")
	public @ResponseBody ResultMessage modifyContact(HttpSession session, String qq, String tel, String addr) {
		try {
			User user = (User) session.getAttribute("user");
			user.setQq(qq);
			user.setTel(tel);
			user.setAddr(addr);
			userService.updateOrSaveUser(user);
			session.setAttribute("user", user);
			
			return new ResultMessage(true, "[ Modify   : Success ]");
		} catch (Exception e) {
			// TODO: handle exception
			return new ResultMessage(false, "[ Modify   : Error ]");
		}
	}

	/**
	 * 查看所有答题情况
	 * @param paperNumber
	 * @return
	 */
	@RequestMapping(value = "findAllResult")
	public @ResponseBody JSON findAllResult(@RequestParam String paperNumber) {
		aspectFlag.findAllResult();
		List<Answer> answers = answerService.findAllResult(paperNumber);
		List<Map<String, Integer>> list = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		int max = 0;
		if(answers!=null){
			for (Answer answer : answers) {
				if (answer.getQuestionNumber() > max) {
					max = answer.getQuestionNumber();
				}
			}
			System.out.println(max);
			for (int i = 1; i <= max; i++) {
				int A = 0;
				int B = 0;
				int C = 0;
				int D = 0;
				for (Answer answer : answers) {
					if (answer.getQuestionNumber() == i) {
						switch (answer.getAnswer()) {
						case "A":
							A++;
							// System.out.println(A+"-"+i+"-A");
							break;
						case "B":
							B++;
							// System.out.println(B+"-"+i+"-B");
							break;
						case "C":
							C++;
							// System.out.println(C+"-"+i+"-C");
							break;
						case "D":
							D++;
							// System.out.println(D+"-"+i+"-D");
							break;
						default:
							break;
						}
					}
				}
				// System.out.println("A:"+A+"-"+"B:"+B+"-C:"+C+"-D:"+D);
				map.put("A", A);
				map.put("B", B);
				map.put("C", C);
				map.put("D", D);
				list.add(map);
				map = new HashMap<>();

			}
		}
		JSON json = (JSON) JSON.toJSON(list);
		String string = (String) JSON.toJSONString(list);
//		System.out.println(string);
		return json;
	}
	
	/**
	 * 查找所有问卷
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "findAllPaper")
	public @ResponseBody JSON findAllPaper(PageBean pageBean) {
		aspectFlag.findAllPaper();
		pageBean.setSidx("paperNumber");
		Page<Paper> papers = paperService.findAllPaper(pageBean.getPageable());
		JSON json = (JSON) JSON.toJSON(papers);
		return json;
	}
	
	/**
	 * 根据答题情况查询用户
	 * @param pageBean
	 * @param userQueryDTOs
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "findByCondition")
	public @ResponseBody JSON findByCondition(PageBean pageBean, @RequestBody List<UserQueryDTO> userQueryDTOs) {
		aspectFlag.findByCondition();
		JSON json = null;
		for (UserQueryDTO userQueryDTO : userQueryDTOs) {
			System.out.println(userQueryDTO.toString());
		}
		List<Page<Answer>> pages = new ArrayList<>();
		for (UserQueryDTO userQueryDTO : userQueryDTOs) {
			Page<Answer> answerPage = answerService.findAllResult(this.buildSpecification(userQueryDTO),
					pageBean.getPageable());
			pages.add(answerPage);
		}
		int size = pages.size();
		System.out.println("结果集数量：" + size);
		List<Answer> answers1 = new ArrayList<>();
		List<Answer> answers2 = new ArrayList<>();
		List<Answer> answers3 = new ArrayList<>();
		Page<Answer> page = null;
		switch (size) {
		case 1:
			System.out.println("进入1");
			page = pages.get(0);
			answers1 = page.getContent();
			json = getAnswerJSON(answers1);
			System.out.println("json = " + json);
			break;
		case 2:
			System.out.println("进入2");
			page = pages.get(0);
			answers1 = page.getContent();
			System.out.println("answers1 size - " + answers1.size());
			page = null;
			page = pages.get(1);
			answers2 = page.getContent();
			System.out.println("answers2 size - " + answers2.size());
			json = getAnswerJSON(answers1, answers2);
			System.out.println("json = " + json);
			break;
		case 3:
			System.out.println("进入3");
			page = pages.get(0);
			answers1 = page.getContent();
			System.out.println("answers1 size - " + answers1.size());
			page = pages.get(1);
			answers2 = page.getContent();
			System.out.println("answers2 size - " + answers1.size());
			page = pages.get(2);
			answers3 = page.getContent();
			System.out.println("answers3 size - " + answers1.size());
			json = getAnswerJSON(answers1, answers2, answers3);
			System.out.println("json = " + json);
			break;
		default:
			break;
		}
		return json;
	}

	public JSON getAnswerJSON(List<Answer> answers1) {
		List<User> users = new ArrayList<>();
		for (Answer answer : answers1) {
			System.out.println(answer.getNumber());
			User user = userService.findByNumber(answer.getNumber());
			users.add(user);
		}
		return (JSON) JSON.toJSON(users);
	}

	public JSON getAnswerJSON(List<Answer> answers1, List<Answer> answers2) {
		List<User> users = new ArrayList<>();
		for (Answer answer1 : answers1) {
			System.out.println(answer1.getNumber() + "--");
			for (Answer answer2 : answers2) {
				System.out.println(answer2.getNumber());
				if (answer1.getNumber().equals(answer2.getNumber())) {
					User user = userService.findByNumber(answer2.getNumber());
					users.add(user);
					break;
				}
			}

		}
		return (JSON) JSON.toJSON(users);
	}

	public JSON getAnswerJSON(List<Answer> answers1, List<Answer> answers2, List<Answer> answers3) {
		List<User> users = new ArrayList<>();
		for (Answer answer1 : answers1) {
			for (Answer answer2 : answers2) {
				if (answer1.getNumber().equals(answer2.getNumber())) {
					for (Answer answer3 : answers3) {
						if (answer2.getNumber().equals(answer3.getNumber())) {
							User user = userService.findByNumber(answer3.getNumber());
							users.add(user);
							break;
						}
					}

				}
			}

		}
		return (JSON) JSON.toJSON(users);
	}

	@SuppressWarnings("rawtypes")
	public static Specification buildSpecification(final UserQueryDTO userQueryDTO) {
		return new Specification<Answer>() {
			/**
			 * Root：查询哪个表 CriteriaQuery：查询哪些字段，排序是什么
			 * CriteriaBuilder：字段之间是什么关系，如何生成一个查询条件，每一个查询条件都是什么方式
			 * Predicate（Expression）：单独每一条查询条件的详细描述
			 */
			@Override
			public Predicate toPredicate(Root<Answer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 1.初始化查询条件列表
				List<Predicate> predicate = new ArrayList<Predicate>();

				// 循环添加查询条件
				boolean flag = userQueryDTO.getPaperNumber() != null && userQueryDTO.getQuestion() != null
						&& userQueryDTO.getAnswer() != null;
				if (flag) {
					Predicate predicate1 = cb.equal(root.get("paperNumber").as(String.class),
							userQueryDTO.getPaperNumber());
					Predicate predicate2 = cb.equal(root.get("questionNumber").as(String.class),
							userQueryDTO.getQuestion());
					Predicate predicate3 = cb.equal(root.get("answer").as(String.class), userQueryDTO.getAnswer());
					predicate.add(cb.and(predicate1, predicate2, predicate3));
				}

				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
				return query.getRestriction();

			}
		};
	}
	
	/**
	 * 更新问卷资料
	 * @param request
	 * @param updateFile
	 * @param updateExcel
	 * @return
	 */
	@RequestMapping(value = "upDateFile")
	public @ResponseBody ResultMessage upDateFile(HttpServletRequest request, MultipartFile updateFile,MultipartFile updateExcel) {
		aspectFlag.upDateFile();
		String path = request.getSession().getServletContext().getRealPath("upload");
		System.out.println("路径：" + path);
		if (updateFile != null && updateExcel != null) {
			String fileName = updateFile.getOriginalFilename();
			String excelname = updateExcel.getOriginalFilename();
			String paperName = updateFile.getOriginalFilename().substring(0, updateFile.getOriginalFilename().indexOf("."));
			String paperNumber = request.getParameter("paperNumber");
			String description = request.getParameter("description");
			System.out.println(fileName + "--" + paperNumber + "--" + description);
			File updatefile = new File(path, fileName);
			File updateexcel = new File(path,excelname);
			if (!updatefile.exists()) {
				updatefile.mkdirs();
			}
			if (!updateexcel.exists()) {
				updateexcel.mkdirs();
			}
			// 保存
			try {
				updateFile.transferTo(updatefile);
				updateExcel.transferTo(updateexcel);
				List<Remark> remarks = readRemarkExcel(paperNumber, updateexcel);
				List<Question> questions = ReadTestQuestions.read(paperNumber,updatefile);
				Paper paper = new Paper();
				paper.setPaperNumber(paperNumber);
				paper.setPaperName(paperName);
				paper.setDescription(description);
				paper.setRemarks(remarks);
				paper.setQuestions(questions);
				paperService.saveOrUpdate(paper);

				return new ResultMessage(true, "[ Update   : Success ]");
			} catch (Exception e) {
				System.out.println(e.toString());
				return new ResultMessage(false, "[ Update   : Error ]");
			}
		} else {
			return new ResultMessage(false, "[ Update   : 文件为空 ]");
		}
	}
	
	/**
	 * 上传问卷资料
	 * @param request
	 * @param paperFile
	 * @param paperImage
	 * @param paperRemark
	 * @param description
	 * @param paperNumber
	 * @return
	 */
	@RequestMapping(value = "upLoadFile")
	public @ResponseBody ResultMessage upLoadFile(HttpServletRequest request, MultipartFile paperFile, MultipartFile paperImage,
			MultipartFile paperRemark,String description, String paperNumber) {
		aspectFlag.upLoadFile();
		if (paperFile != null && paperImage != null && (paperNumber.equals("") == false)
				&& (description.equals("") == false)) {
			if (paperNumber.length() == 5) {
				Paper paper = paperService.findByPaperNumber(paperNumber);
				if (paper == null) {
					String uploadpath = request.getSession().getServletContext().getRealPath("upload");
					String imagepath = request.getSession().getServletContext().getRealPath("image");
					System.out.println("upLoadFile 文件上传路径：" + uploadpath);

					String paperName = paperFile.getOriginalFilename().substring(0,
							paperFile.getOriginalFilename().indexOf("."));
					String imageName = "image" + paperNumber + ".jpg";
					File paperfile = new File(uploadpath, paperFile.getOriginalFilename());
					File paperremark = new File(uploadpath,paperRemark.getOriginalFilename());
					File paperimage = new File(imagepath, imageName);
					if (!paperfile.exists()) {
						paperfile.mkdirs();
					}
					if (!paperimage.exists()) {
						paperimage.mkdirs();
					}
					if (!paperremark.exists()) {
						paperremark.mkdirs();
					}

					try {
						paperFile.transferTo(paperfile);
						paperImage.transferTo(paperimage);
						paperRemark.transferTo(paperremark);
						List<Remark> remarks = readRemarkExcel(paperNumber, paperremark);
						List<Question> questions = ReadTestQuestions.read(paperNumber, paperfile);
						paper = new Paper();
						paper.setPaperNumber(paperNumber);
						paper.setPaperName(paperName);
						paper.setDescription(description);
						paper.setRemarks(remarks);
						paper.setQuestions(questions);
						paperService.saveOrUpdate(paper);
						return new ResultMessage(true, "[ Upload   : Success ]");
					} catch (Exception e) {
						System.out.println(e.toString());
						return new ResultMessage(false, "[ Upload   : Error ]");
					}
				} else {
					
					return new ResultMessage(false, "[ Upload   : 问卷编号已存在 ]");
				}
			} else {
				
				return new ResultMessage(false, "[ Upload   : 问卷编号格式错误 ]");
				
			}
		} else {
			if (paperNumber.equals("") == true) {
				
				return new ResultMessage(false, "[ Upload   : 问卷编号为空 ]");
			} else if (paperImage == null) {
				
				return new ResultMessage(false, "[ Upload   : 图片文件为空 ]");
			} else if (paperFile == null) {
				
				return new ResultMessage(false, "[ Upload   : 问卷文件为空 ]");
			} else if (description.equals("") == true) {
				
				return new ResultMessage(false, "[ Upload   : 问卷描述为空 ]");
			}
			else{
				return new ResultMessage(false, "[ Upload   : Error ]");
			}
		}
	}
	
	/**
	 * 生成查询结果的文件
	 * @param listDTOs
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "exportExcel")
	public @ResponseBody JSON exportExcel(@RequestBody List<ListDTO> listDTOs, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, String> m = new HashMap<>();
		ListDTO listDTO = listDTOs.get(0);
		List<User> users = listDTO.getUsers();
		// 列名
		List<String> colNames = listDTO.getHeader();
		// 键值
		String[] keys = { "number", "name", "sex", "grade", "institute", "major", "tel" };
		// 填充数据
		List<Map<String, String>> lists = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		map.put("sheetName", "sheet1");
		lists.add(map);
		for (User user : users) {
			map = new HashMap<>();
			map.put("number", user.getNumber());
			map.put("name", user.getName());
			map.put("sex", user.getSex());
			map.put("grade", user.getGrade());
			map.put("institute", user.getInstitute());
			map.put("major", user.getMajor());
			map.put("tel", user.getTel());
			lists.add(map);
		}
		System.out.println((String) JSON.toJSONString(lists));

		// 字节输出流
		// ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = df.format(new Date()) + ".xls";
			String path = request.getSession().getServletContext().getRealPath("upload");
			OutputStream out = new FileOutputStream(path + "/" + fileName);
			
			ExcelUtil.createWorkBook(lists, keys, colNames).write(out);

			m.put("filename", fileName);
			// String pathname = path+"/"+fileName;

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

		JSON json = (JSON) JSON.toJSON(m);

		return json;
	}
	
	/**
	 * 下载查询结果的文件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "downExcel")
	public void downExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		aspectFlag.downExcel();
		String filename = (String) request.getParameter("filename");
		System.out.println(filename);
		String pathName = request.getSession().getServletContext().getRealPath("upload") + "/" + filename;
		File downFile = new File(pathName);

		response.setContentType("application/octet-stream");
		response.setContentLength((int) downFile.length());
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);

		ServletOutputStream servletOutputStream = response.getOutputStream();
		FileInputStream inputStream = new FileInputStream(downFile);
		byte[] b = new byte[1024 * 1024];
		int readBytes;
		while ((readBytes = inputStream.read(b, 0, 1024 * 1024)) != -1) {
			servletOutputStream.write(b, 0, 1024 * 1024);
		}
		servletOutputStream.close();
		inputStream.close();
		downFile.delete();
	}

	/**
	 * 删除问卷
	 * @param paperNumber
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deletePaper")
	public @ResponseBody ResultMessage deletePaper(String paperNumber, HttpServletRequest request) {
		aspectFlag.deletePaper();
		try {
			Paper paper = paperService.findByPaperNumber(paperNumber);
			String paperName = paper.getPaperName();
			String uploadpath = request.getSession().getServletContext().getRealPath("upload");
			String imagepath = request.getSession().getServletContext().getRealPath("image");
			String fileName = paperName + ".doc";
			String imageName = "image" + paperNumber + ".jpg";

			File paperFile = new File(uploadpath, fileName);
			File imageFile = new File(imagepath, imageName);

			if (!paperFile.exists()) {
				fileName = paperName + ".docx";
				paperFile = new File(uploadpath, fileName);
			}
			paperFile.delete();
			imageFile.delete();

			paperService.deleteByPaperNumber(paperNumber);
			
			return new ResultMessage(true, "[ Delete   : Success ]");
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResultMessage(true, "[ Delete   : Error ]");
		}
	}
	
	/**
	 * 查找所有用户
	 * @param request
	 * @param pageBean
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "findAllUser")
	public @ResponseBody JSON findAllUser(HttpServletRequest request, PageBean pageBean) {
		aspectFlag.findAllUser();
		Page<User> pages = userService.findAllUser(pageBean.getPageable());
		if(pages != null){
			JSON json = (JSON) JSON.toJSON(pages);
			return json;
		}
		
		return null;
	}
	
	/**
	 * 用户查询
	 * @param ruleBean
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "searchByCondition")
	public @ResponseBody JSON searchByCondition(RuleBean ruleBean) {
		aspectFlag.searchByCondition();
		List<User> lists = userService.findAllUser(this.buildSpecification(ruleBean));
		JSON json = (JSON) JSON.toJSON(lists);
		return json;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static Specification buildSpecification(final RuleBean ruleBean) {
		return new Specification<User>() {
			/**
			 * Root：查询哪个表 CriteriaQuery：查询哪些字段，排序是什么
			 * CriteriaBuilder：字段之间是什么关系，如何生成一个查询条件，每一个查询条件都是什么方式
			 * Predicate（Expression）：单独每一条查询条件的详细描述
			 */
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 1.初始化查询条件列表
				List<Predicate> predicate = new ArrayList<Predicate>();

				String field = ruleBean.getField();
				String op = ruleBean.getOp();
				String data = ruleBean.getData();
				System.out.println("[" + field + "--" + op + "--" + data + "]");
				switch (op) {
					// 等于
					case "eq":
						predicate.add(cb.equal(root.get(field).as(String.class), data));
						break;
						// 不等于
					case "ne":
						predicate.add(cb.notEqual(root.get(field).as(String.class), data));
						break;
						// 开头是
					case "bw":
						predicate.add(cb.like(root.get(field).as(String.class), data + "%"));
						break;
						// 开头不是
					case "bn":
						predicate.add(cb.notLike(root.get(field).as(String.class), data + "%"));
						break;
						// 结尾是
					case "ew":
						predicate.add(cb.like(root.get(field).as(String.class), "%" + data));
						break;
						// 结尾不是
					case "en":
						predicate.add(cb.notLike(root.get(field).as(String.class), "%" + data));
						break;
						// 包含
					case "cn":
						
						predicate.add(cb.like(root.get(field).as(String.class), "%" + data + "%"));
						break;
						// 不包含
					case "nc":
						predicate.add(cb.notLike(root.get(field).as(String.class), "%" + data + "%"));
						break;
					default:
						break;
				}
				Predicate[] p = new Predicate[predicate.size()];
				query.where(predicate.toArray(p));
				return query.getRestriction();
			}
		};
	}
	
	/**
	 * 注册用户
	 * @param request
	 * @param registryFile
	 * @return
	 */
	@RequestMapping(value = "registry")
	public @ResponseBody ResultMessage registry(HttpServletRequest request, MultipartFile registryFile){
		aspectFlag.registry();
		try {
			if (registryFile != null) {
				String fileName = registryFile.getOriginalFilename();
				String filePath = request.getSession().getServletContext().getRealPath("Temp");
				File tempFile = new File(filePath, fileName);
				if (!tempFile.exists()) {
					tempFile.mkdirs();
				}
				System.out.println("写入前" + tempFile.length());
				registryFile.transferTo(tempFile);
				System.out.println("写入后" + tempFile.length());
				List<Map<String, String>> result = readRegistryExcel(tempFile);
				for (Map<String, String> map : result) {
					String number = map.get("number");
					String name = map.get("name");
					String authority = map.get("authority");
					User user = userService.findByNumber(number);
					if (user == null) {
						Login login = new Login();
						MD5PasswordEncoder encoder = new MD5PasswordEncoder();
						String password = encoder.encodePassword(number, number);
						login.setNumber(number);
						login.setPassword(password);
						login.setAuthority(authority);

						user = new User();
						user.setNumber(number);
						user.setName(name);
						user.setLogin(login);
						userService.updateOrSaveUser(user);
					}
				}

				tempFile.delete();
				return new ResultMessage(true, "[ Registry   : Success ]");
				
			} else {
				
				return new ResultMessage(true, "[ Registry   : Failure ]");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResultMessage(true, "[ Registry   : Error ]");
		}
	}
	
	/**
	 * 读取注册文件信息
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> readRegistryExcel(File file) throws Exception {
		aspectFlag.readRegistryExcel();
		List<Map<String, String>> result = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		String filepath = file.getPath();
		InputStream inputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(file);
			if (filepath.endsWith(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (filepath.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			}

			int sheetSize = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetSize; i++) {// 遍历sheet页
				Sheet sheet = workbook.getSheetAt(i);
				// 空页
				if (sheet == null) {
					continue;
				}

				// 循环读取行
				System.out.println(sheet.getLastRowNum());
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					// System.out.print(rowNum + "--");
					for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
						Cell cell = row.getCell(cellNum);
						if (cellNum == 0) {
							map.put("number", cell.getStringCellValue());
							// System.out.print("[ "+cell.getStringCellValue() +
							// " ]");
						} else if (cellNum == 1) {
							map.put("name", cell.getStringCellValue());
							// System.out.print("[ "+cell.getStringCellValue() +
							// " ]");
						} else if (cellNum == 2) {
							map.put("authority", cell.getStringCellValue());
							// System.out.print("[ "+cell.getStringCellValue() +
							// " ]");
						}
					}
					result.add(map);
					map = new HashMap<>();
					// System.out.println();
				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			inputStream.close();
		}

		return result;
	}
	
	/**
	 * 读取评分表
	 * @param paperNumber
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public List<Remark> readRemarkExcel(String paperNumber,File file) throws Exception {
		aspectFlag.readRemarkExcel();
		List<Remark> result = new ArrayList<>();
		Remark remark = new Remark();
		String filepath = file.getPath();
		InputStream inputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(file);
			if (filepath.endsWith(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (filepath.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			}

			int sheetSize = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetSize; i++) {// 遍历sheet页
				Sheet sheet = workbook.getSheetAt(i);
				// 空页
				if (sheet == null) {
					continue;
				}

				// 循环读取行
				String[] answerNumber = {"A","B","C","D","A","B","C","D","A","B","C","D"};
				System.out.println(sheet.getLastRowNum());
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
						Cell cell = row.getCell(cellNum);
						if (cellNum == 0) {
							remark.setRemarkNumber(paperNumber+answerNumber[rowNum-1]);
							remark.setPaperNumber(paperNumber);
							remark.setScore((int)cell.getNumericCellValue());
						} else if (cellNum == 1) {
							remark.setRemark(cell.getStringCellValue());
						}
					}
					result.add(remark);
					remark = new Remark();
				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			inputStream.close();
			workbook.close();
		}

		return result;
	}
	
	/**
	 * 删除用户
	 * @param numberList
	 * @return
	 */
	@RequestMapping(value = "deleteUser")
	public @ResponseBody ResultMessage deleteUser(String numberList) {
		aspectFlag.deleteUser();
		try {
			System.out.println(numberList);
			@SuppressWarnings("unchecked")
			List<String> lists = JSON.parseObject(numberList, List.class);
			for (String number : lists) {
				User user = userService.findByNumber(number);
				userService.deleteUser(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResultMessage(false, "[ Delete   : Failure ]");
		}

		return new ResultMessage(true, "[ Delete   : Success ]");
	}
	
	/**
	 * 重置密码
	 * @param number
	 * @return
	 */
	@RequestMapping(value="resetPassword")
	public @ResponseBody ResultMessage resetPassword(String number){
		aspectFlag.resetPassword();
		try {
			User user = userService.findByNumber(number);
			Login login = user.getLogin();
			MD5PasswordEncoder encoder = new MD5PasswordEncoder();
			String password = encoder.encodePassword(number, number);
			login.setPassword(password);
			user.setLogin(login);
			userService.updateOrSaveUser(user);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResultMessage(false, "[ ResetPassword   : Failure ]");
		}
		
		return new ResultMessage(true, "[ ResetPassword   : Success ]");
		
	}
	
	/**
	 * 查找聊天记录
	 * @param id：发送方ID
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="findRecordsByCondition")
	public @ResponseBody JSON findRecordsByCondition(String toId,String fromId) throws Exception{
		System.out.println("toId = " + toId);
		System.out.println("fromId = " + fromId);
		List<ChatRecords> chatRecords = chatRecordsService.findByCondition(fromId, toId);
		if(chatRecords != null){
			for(ChatRecords c : chatRecords){
				if(c.getStatus().equals("未读")){
					c.setStatus("已读");
					chatRecordsService.save(c);
				}
			}
			chatRecords = chatRecordsService.findByCondition(fromId, toId);
			JSON json = (JSON) JSON.toJSON(chatRecords);
			return json;
		}
		return null;
	}
}
