package org.jivesoftware.openfire.plugin.userImportExport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.ImportExportPlugin;
import org.jivesoftware.openfire.XMPPServer;

public final class import_002dexport_002dselection_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>Import/Export Selection</title>\r\n        <meta name=\"pageID\" content=\"import-export-selection\"/>\r\n    </head>\r\n    <body>\r\n\r\n");
      org.jivesoftware.admin.AdminPageBean pageinfo = null;
      synchronized (request) {
        pageinfo = (org.jivesoftware.admin.AdminPageBean) _jspx_page_context.getAttribute("pageinfo", PageContext.REQUEST_SCOPE);
        if (pageinfo == null){
          pageinfo = new org.jivesoftware.admin.AdminPageBean();
          _jspx_page_context.setAttribute("pageinfo", pageinfo, PageContext.REQUEST_SCOPE);
        }
      }
      out.write('\r');
      out.write('\n');

    ImportExportPlugin plugin = (ImportExportPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("userimportexport");

      out.write("\r\n\r\n<p>\r\n\r\nThe import and export functions allow you to read data into and write user\r\ndata from your Openfire installation.\r\n\r\n<ul>\r\n    <li><a href=\"import-user-data.jsp\">Import User Data</a></li>\r\n    <li><a href=\"export-user-data.jsp\">Export User Data</a></li>    \r\n</ul>\r\n\r\n");
 if (plugin.isUserProviderReadOnly()) { 
      out.write("\r\n\r\n   Note: because you are using a read-only user data store such as LDAP or POP3 you will only be able to import user roster data, not users themselves.\r\n   Please see the <a href=\"../../plugin-admin.jsp?plugin=userimportexport&showReadme=true&decorator=none\">readme</a> for details.\r\n\r\n");
 } 
      out.write("\r\n</p>\r\n\r\n</body>\r\n</html>\r\n");
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
