package com.ssh.service;

import org.springframework.stereotype.Service;

@Service
public class AspectFlag {
	
	public  void getAdminResultPage(){
		System.out.println("[url: '/t_result']");
	}
	
	public  void getAdminconsultPage(){
		System.out.println("[url: '/t_consult']");
	}
	
	public  void getSearchPage(){
		System.out.println("[url: '/search']");
	}
	
	public  void findAllResult(){
		System.out.println("[url: '/findAllResult']");
	}
	
	public  void findAllPaper(){
		System.out.println("[url: '/findAllPaper']");
	}
	
	public  void findByCondition(){
		System.out.println("[url: '/findByCondition']");
	}
	
	public  void upDateFile(){
		System.out.println("[url: '/upDateFile']");
	}
	
	public  void upLoadFile(){
		System.out.println("[url: '/upLoadFile']");
	}
	
	public  void exportExcel(){
		System.out.println("[url: '/exportExcel']");
	}
	
	public  void downExcel(){
		System.out.println("[url: '/downExcel']");
	}
	
	public  void deletePaper(){
		System.out.println("[url: '/deletePaper']");
	}
	
	public  void findAllUser(){
		System.out.println("[url: '/findAllUser']");
	}
	
	public  void searchByCondition(){
		System.out.println("[url: '/searchByCondition']");
	}
	
	public  void registry(){
		System.out.println("[url: '/registry']");
	}
	
	public  void readRegistryExcel(){
		System.out.println("[url: '/readRegistryExcel']");
	}
	
	public  void readRemarkExcel(){
		System.out.println("[url: '/readRemarkExcel']");
	}
	
	public  void deleteUser(){
		System.out.println("[url: '/deleteUser']");
	}
	
	public  void resetPassword(){
		System.out.println("[url: '/resetPassword']");
	}
	/***********************分界线*******************************/
	
	public  void getUserResultPage(){
		System.out.println("[url: '/u_result']");
	}
	
	public  void getUserconsultPage(){
		System.out.println("[url: '/u_consult']");
	}
	
	public  void findResult(){
		System.out.println("[url: '/findResult']");
	}
}
