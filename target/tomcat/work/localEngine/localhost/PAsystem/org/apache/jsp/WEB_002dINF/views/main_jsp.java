package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/WEB-INF/views/nav.jsp");
  }

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
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("    <title>大学生心理健康测评系统</title>\r\n");
      out.write("    \r\n");
      out.write("    <!-- 引入样式表 -->\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/nav.css\">\r\n");
      out.write("    <style type=\"text/css\">\r\n");
      out.write("        .img_div img{\r\n");
      out.write("            width: 1366px;\r\n");
      out.write("            height: 515px;\r\n");
      out.write("        }\r\n");
      out.write("    </style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("    <!-- 导航栏 -->\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("<div class=\"header\">\r\n");
      out.write("\t<nav id=\"nav\">\r\n");
      out.write("\t\t<div class=\"nav_hd\">\r\n");
      out.write("\t\t\t<h2>大学生心理健康测评系统</h2>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"nav_bd\">\r\n");
      out.write("\t\t\t<ul>\r\n");
      out.write("\t\t\t\t<li><a href=\"main\">首页</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"test\">心理测评</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"result\">测评结果</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"u_consult\">在线咨询</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"information\">个人设置</a></li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"nav_fd\">\r\n");
      out.write("\t\t\t<h3>在线用户：</h3>\r\n");
      out.write("\t\t\t<h3>陶嘉伟</h3>\r\n");
      out.write("\t\t\t<a href=\"#\">注销</a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</nav>\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    <div class=\"img_div\">\r\n");
      out.write("        <img src=\"image/image02.jpg\">\r\n");
      out.write("    </div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
