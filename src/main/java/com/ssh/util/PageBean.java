package com.ssh.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 分页查询工具类
 * @author Jetvin
 *
 */
public class PageBean {

		private int page;
		private int rows;
		private String sidx;
		private String sord;
		
		public PageBean() {
			this.page = 1;
			this.rows = 30;
			this.sidx = "number";
			this.sord = "desc";
		}
		
		
		public PageBean(String number){
			this.page = 1;
			this.rows = 30;
			this.sidx = number;
			this.sord = "desc";
		}
		
		public void setPage(int page) {
			this.page = page;
		}
		public void setRows(int rows) {
			this.rows = rows;
		}
		public void setSidx(String sidx) {
			this.sidx = sidx;
		}
		public void setSord(String sord) {
			this.sord = sord;
		}
		
		public Pageable getPageable()
		{	
			Sort sort = new Sort(Direction.ASC, this.sidx);//默认
			if(this.sord.equals("asc")){
				sort = new Sort(Direction.ASC, this.sidx);
			}else{
				sort = new Sort(Direction.DESC, this.sidx);
			}

			return new PageRequest(this.page-1, this.rows, sort);
		}
}
