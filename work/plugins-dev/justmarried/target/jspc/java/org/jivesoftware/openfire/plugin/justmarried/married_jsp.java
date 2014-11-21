package org.jivesoftware.openfire.plugin.justmarried;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.married.JustMarriedPlugin;

public final class married_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\r');
      out.write('\n');

	webManager.init(request, response, session, application, out);
	String oldName = request.getParameter("oldName");
	String newName = request.getParameter("newName");
	String keepCopy = request.getParameter("copy");
	String newEmail = request.getParameter("email");
	String newRealName = request.getParameter("realName");

      out.write("\r\n\r\n<html>\r\n<head>\r\n<title>Just married - name changer</title>\r\n<meta name=\"pageID\" content=\"justmarried\" />\r\n<meta name=\"helpPage\" content=\"\" />\r\n<script src=\"./js/bootstrap.min.js\" type=\"text/javascript\"></script>\r\n<link href=\"./css/bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\">\r\n\r\n</head>\r\n<body>\r\n\r\n\t<div class=\"jive-contentBoxHeader\">Just married</div>\r\n\t<div class=\"jive-contentBox\">\r\n\t\t");

			if (oldName != null && newName != null && oldName.trim().length() > 0 && newName.trim().length() > 0) {
				boolean success = JustMarriedPlugin.changeName(oldName, newName, keepCopy == null ? true : false, newEmail, newRealName);
				if (success) {
					out.write("<div class=\"success\">Sucessfully renamed user " + oldName + " to " + newName
							+ "!</div>");
				} else {
					out.write("<div class=\"error\">Something went wrong :-/. Please have a closer look to the error log!</div>");
				}
			} else {
		
      out.write("\r\n\t\t<form class=\"form-horizontal\">\r\n\t\t\t<fieldset>\r\n\t\t\t\t<legend>Change the name here</legend>\r\n\t\t\t\t<label class=\"control-label\" for=\"input01\">Current username*</label>\r\n\t\t\t\t<div\r\n\t\t\t\t\t");
out.write(oldName != null && oldName.length() == 0 ? "class=\"control-group error\""
						: "class=\"controls\"");
      out.write(">\r\n\t\t\t\t\t<input type=\"text\" name=\"oldName\" style=\"height:26px\"\r\n\t\t\t\t\t\tclass=\"input-xlarge\"\r\n\t\t\t\t\t\t");
out.write(oldName != null && oldName.length() == 0 ? "id=\"inputError\"" : "id=\"input01\"");
      out.write(">\r\n\t\t\t\t\t<p class=\"help-block\">The current username e.g user.name\r\n\t\t\t\t\t\t(without server)</p>\r\n\t\t\t\t</div>\r\n\t\t\t\t<label class=\"control-label\" for=\"input01\">New username*</label>\r\n\t\t\t\t<div\r\n\t\t\t\t\t");
out.write(newName != null && newName.length() == 0 ? "class=\"control-group error\""
						: "class=\"controls\"");
      out.write(">\r\n\t\t\t\t\t<input type=\"text\" name=\"newName\" style=\"height:26px\"\r\n\t\t\t\t\t\tclass=\"input-xlarge\"\r\n\t\t\t\t\t\t");
out.write(newName != null && newName.length() == 0 ? "id=\"inputError\"" : "id=\"input01\"");
      out.write(">\r\n\t\t\t\t\t<p class=\"help-block\">The new username e.g user.newname\r\n\t\t\t\t\t\t(without server)</p>\r\n\t\t\t\t</div>\r\n\t\t\t\t<label class=\"control-label\" for=\"input01\">New E-Mail address</label>\r\n\t\t\t\t<div class=\"controls\">\r\n\t\t\t\t\t<input type=\"text\" name=\"email\" style=\"height:26px\"\r\n\t\t\t\t\t\tclass=\"input-xlarge\" id=\"input01\">\r\n\t\t\t\t\t<p class=\"help-block\">New email address. Will copy address from old user if field is empty.</p>\r\n\t\t\t\t</div>\r\n\t\t\t\t<label class=\"control-label\" for=\"input01\">New Name</label>\r\n\t\t\t\t<div class=\"controls\">\r\n\t\t\t\t\t<input type=\"text\" name=\"realName\" style=\"height:26px\"\r\n\t\t\t\t\t\tclass=\"input-xlarge\" id=\"input01\">\r\n\t\t\t\t\t<p class=\"help-block\">Will copy name from old user if field is empty.</p>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"control-group\">\r\n\t\t\t\t\t<label class=\"checkbox\"> <input type=\"checkbox\"\r\n\t\t\t\t\t\tid=\"optionsCheckbox2\" name=\"copy\" value=\"keepCopy\"> Keep a\r\n\t\t\t\t\t\tcopy of the old username\r\n\t\t\t\t\t</label>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"control-group\">\r\n\t\t\t\t\t<button type=\"submit\" class=\"btn btn-primary\">Rename user</button>\r\n");
      out.write("\t\t\t\t</div>\r\n\t\t\t\t<p class=\"help-block\">* Mandatory item</p>\r\n\t\t\t</fieldset>\r\n\t\t</form>\r\n\r\n\t\t");

			}
		
      out.write("\r\n\r\n\t</div>\r\n</body>\r\n</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
