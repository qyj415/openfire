package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.util.*;
import java.util.*;
import java.net.URLEncoder;
import java.net.URLDecoder;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.openfire.fastpath.macros.WorkgroupMacros;
import org.jivesoftware.openfire.fastpath.macros.MacroGroup;
import org.jivesoftware.openfire.fastpath.macros.Macro;

public final class workgroup_002dview_002dresponses_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
 // Get parameters
    String wgID = ParamUtils.getParameter(request, "wgID");
    boolean add = request.getParameter("add") != null;
    boolean delete = request.getParameter("delete") != null;

    Map errors = new HashMap();

    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));
    WorkgroupMacros workgroupMacros = WorkgroupMacros.getInstance();

    String groupTitle = URLDecoder
            .decode(ParamUtils.getParameter(request, "macroGroupTitle"), "UTF-8");


    MacroGroup group = workgroupMacros.getMacroGroup(workgroup, groupTitle);
    if (delete) {
        int entry = ParamUtils.getIntParameter(request, "entry", -1);
        if (entry != -1) {
            Macro macro = group.getMacro(entry);
            if (macro != null) {
                group.removeMacro(macro);
            }
        }
    }



      out.write("\r\n\r\n\r\n\r\n<html>\r\n    <head>\r\n        <title>Canned Response List</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-macros\"/>\r\n        <meta name=\"extraParams\" content=\"wgID=");
      out.print( wgID );
      out.write("\"/>\r\n    </head>\r\n    <body>\r\n\r\n<p>\r\nViewing all responses in <b>\"");
      out.print( groupTitle );
      out.write("\"</b>\r\n</p>\r\n\r\n<form action=\"workgroup-add-response.jsp\" method=\"post\">\r\n<input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\" />\r\n<input type=\"hidden\" name=\"macroGroupTitle\" value=\"");
      out.print( URLEncoder.encode(groupTitle, "UTF-8") );
      out.write("\" />\r\n<input type=\"hidden\" name=\"add\" value=\"true\" />\r\n<table class=\"jive-table\" cellspacing=\"0\" cellpadding=\"0\"  width=\"100%\">\r\n<th colspan=\"1\">Title</th><th>Type</th><th>Options</th>\r\n");

    int count = 0;
    for(Macro macro : group.getMacros()){
      out.write("\r\n        <tr>\r\n        <td>");
      out.print( macro.getTitle());
      out.write("</td>\r\n        <td>");
      out.print( "Text");
      out.write("</td>\r\n        <td align=\"center\"><a href=\"workgroup-add-response.jsp?edit=true&wgID=");
      out.print(wgID);
      out.write("&macroGroupTitle=");
      out.print( URLEncoder.encode(groupTitle, "UTF-8"));
      out.write("&entry=");
      out.print(count);
      out.write("\"><img src=\"images/edit-16x16.gif\" border=\"0\"></a>&nbsp;<a href=\"workgroup-view-responses.jsp?delete=true&wgID=");
      out.print(wgID);
      out.write("&macroGroupTitle=");
      out.print( URLEncoder.encode(groupTitle, "UTF-8"));
      out.write("&entry=");
      out.print(count);
      out.write("\"><img src=\"images/delete-16x16.gif\" border=\"0\"></a></td>\r\n        </tr>\r\n\r\n");

        count++;
    }

      out.write("\r\n</table>\r\n</form>\r\n</body>\r\n</html>\r\n\r\n");
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
