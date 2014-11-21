package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.xmpp.workgroup.UnauthorizedException;
import org.jivesoftware.openfire.user.UserNotFoundException;

public final class workgroup_002derror_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    Throwable exception = org.apache.jasper.runtime.JspRuntimeLibrary.getThrowable(request);
    if (exception != null) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
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

    if (exception instanceof UnauthorizedException) {

      out.write("\r\n                  <p>\r\n          You don't have admin privileges to perform this operation.\r\n                  </p>\r\n\r\n");

}
else if (exception instanceof UserNotFoundException) {
    String username = ParamUtils.getParameter(request, "username");

      out.write("\r\n                              <p>\r\n          The requested user\r\n\r\n");

          if (username != null) {

      out.write("\r\n\r\n            (username: ");
      out.print( username );
      out.write(")\r\n\r\n");

          }

      out.write("\r\n\r\n          was not found.\r\n                              </p>\r\n\r\n");

    }

      out.write("\r\n\r\n");

    if (exception != null) {
      StringWriter sout = new StringWriter();
      PrintWriter  pout = new PrintWriter(sout);
      exception.printStackTrace(pout);

      out.write("\r\n\r\n      Spark Fastpath Exception:\r\n\r\n      <pre> ");
      out.print( sout.toString() );
      out.write(" </pre>\r\n\r\n");

    }

      out.write('\r');
      out.write('\n');
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
