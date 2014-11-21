package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;

public final class workgroup_002ddelete_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n<!-- Define Administration Bean -->\r\n");
   // Get parameters //
    String wgID = ParamUtils.getParameter(request,"wgID");
    boolean cancel = request.getParameter("cancel") != null;
    boolean delete = request.getParameter("delete") != null;

    final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    JID workgroupJID = new JID(wgID);

    // Load the workgroup object
    Workgroup workgroup = workgroupManager.getWorkgroup(workgroupJID);

    // handle a delete request
    if (cancel) {
        response.sendRedirect("workgroup-properties.jsp?wgID=" + wgID);
    }

    // handle a delete request
    if (delete) {
        workgroupManager.deleteWorkgroup(workgroup);
        response.sendRedirect("workgroup-summary.jsp?wgID="+wgID+"&deleted=true");
        return;
    }

      out.write("\r\n<html>\r\n    <head>\r\n        <title>");
      out.print( "Delete Workgroup: "+wgID);
      out.write("</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-properties\"/>\r\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\r\n        <!--<meta name=\"helpPage\" content=\"delete_a_workgroup.html\"/>-->\r\n    </head>\r\n    <body>\r\n<p>Are you sure you want to delete this workgroup?</p>\r\n<form action=\"workgroup-delete.jsp\">\r\n  <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\"/>\r\n  <input type=\"submit\" name=\"delete\" value=\"Yes\"/>\r\n  <input type=\"submit\" name=\"cancel\" value=\"No\"/>\r\n</form>\r\n</body>\r\n</html>\r\n");
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
