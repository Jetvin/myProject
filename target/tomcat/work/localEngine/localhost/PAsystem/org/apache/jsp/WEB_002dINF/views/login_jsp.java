package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("\t<title>用户登录</title>\r\n");
      out.write("\t\r\n");
      out.write("\t<link rel=\"stylesheet\" href=\"css/bootstrap.css\" />\r\n");
      out.write("\t<link rel=\"stylesheet\" href=\"css/login.css\" />\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"js/bootstrap.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div class=\"mymain\">\r\n");
      out.write("\t\t<c:url var=\"loginUrl\" value=\"/login\" />\r\n");
      out.write("\t\t<form action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loginUrl}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" method=\"post\" class=\"login_form\">\r\n");
      out.write("\t\t\t<div class=\"col-lg-12 text-center text-info\">\r\n");
      out.write("\t\t\t\t<h2>大学生心理健康测评系统</h2>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("            <div class=\"col-lg-12\"></div>\r\n");
      out.write("\t\t\t<div class=\"col-lg-12\">\r\n");
      out.write("\t\t\t\t<div class=\"input-group\">\r\n");
      out.write("\t\t\t\t\t<span class=\"input-group-addon\">用户</span> <input type=\"text\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"form-control\" name=\"username\" placeholder=\"请输入账户名\" required\r\n");
      out.write("\t\t\t\t\t\tautofocus />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col-lg-12\"></div>\r\n");
      out.write("\t\t\t<div class=\"col-lg-12\">\r\n");
      out.write("\t\t\t\t<div class=\"input-group\">\r\n");
      out.write("\t\t\t\t\t<span class=\"input-group-addon\">密码</span> <input type=\"password\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"form-control\" name=\"password\" placeholder=\"请输入密码\" required\r\n");
      out.write("\t\t\t\t\t\tautofocus />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col-lg-12\"></div>\r\n");
      out.write("            \r\n");
      out.write("\t\t\t<input type=\"hidden\" name=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_csrf.parameterName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" \tvalue=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_csrf.token}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" />\r\n");
      out.write("\t\t\t<div class=\"col-lg-12\">\r\n");
      out.write("\t\t\t\t<center><c:out value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${message}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"></c:out></center>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col-lg-12\">\r\n");
      out.write("\t\t\t\t<button type=\"submit\" class=\"btn btn-success col-lg-12\" style=\"width: 200px;margin-left: 130px;\">登录</button>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
