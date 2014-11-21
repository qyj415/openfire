package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.HashMap;
import java.util.Map;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.xmpp.component.ComponentManagerFactory;
import org.jivesoftware.openfire.fastpath.util.WorkgroupUtils;

public final class workgroup_002dcreate_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n<!-- Define Administration Bean -->\r\n");

    final XMPPServer xmppServer = XMPPServer.getInstance();
    Map errors = new HashMap();
    // Get a workgroup manager
    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    // If the workgroup manager is null, service is down so redirect:
    if (workgroupManager == null) {
        response.sendRedirect("error-serverdown.jsp");
        return;
    }

      out.write("\r\n\r\n");
 // Get parameters //
    boolean create = request.getParameter("create") != null;
    boolean cancel = request.getParameter("cancel") != null;
    String wgName = ParamUtils.getParameter(request, "wgName");
    String description = ParamUtils.getParameter(request, "description");
    String queueName = ParamUtils.getParameter(request, "queueName");
    String agents = ParamUtils.getParameter(request, "agents");
    int maxChats = ParamUtils.getIntParameter(request, "maxChats", 0);
    int minChats = ParamUtils.getIntParameter(request, "minChats", 0);

    // Handle a cancel
    if (cancel) {
        response.sendRedirect("workgroup-summary.jsp");
        return;
    }

    if (create) {
        errors = WorkgroupUtils.createWorkgroup(wgName, description, agents);
        if (errors.size() == 0) {
            Workgroup workgroup = workgroupManager.getWorkgroup(wgName);
            response.sendRedirect(
                    "workgroup-create-success.jsp?wgID=" + workgroup.getJID().toString());
            return;
        }
    }

      out.write("\r\n<html>\r\n    <head>\r\n        <title>Create Workgroup</title>\r\n        <meta name=\"pageID\" content=\"workgroup-create\"/>\r\n        <!--<meta name=\"helpPage\" content=\"create_a_workgroup.html\"/>-->\r\n        <script>\r\n            function openWin(el) {\r\n                var win = window.open(\r\n                              'user-browser.jsp?formName=f&elName=agents', 'newWin',\r\n                              'width=500,height=550,menubar=yes,location=no,personalbar=no,scrollbars=yes,resize=yes');\r\n            }\r\n        </script>\r\n    </head>\r\n    <body>\r\n    <style type=\"text/css\">\r\n        @import \"style/style.css\";\r\n    </style>\r\n        <p>\r\n        Use the form below to create a new workgroup in the system.</p>\r\n");

    if (!errors.isEmpty()) {

      out.write("\r\n        <div class=\"jive-error\">\r\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td class=\"jive-icon\">\r\n                            <img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/>\r\n                        </td>\r\n                        <td class=\"jive-icon-label\">\r\n");

                            if (errors.get("general") != null) {

      out.write("\r\n                                    Unable to create the workgroup.\r\n");

                            }
                            else if (errors.get("exists") != null) {

      out.write("\r\n                               The workgroup name is already in use. Please try another.\r\n");

                            }
                            else if (errors.get("wgName") != null) {

      out.write("\r\n                                Supply a valid name for the workgroup.\r\n");

                            }
                            else if (errors.get("maxChats") != null) {

      out.write("\r\n                                Invalid maximum number of chat sessions.\r\n");

                            }
                            else if (errors.get("minChats") != null) {

      out.write("\r\n                                Invalid minimum number of chat sessions.\r\n");

                            }
                            else if (errors.get("minChatsGreaterThanMax") != null) {

      out.write("\r\n                                Minimum chat sessions can not be greater than maximum.\r\n");

                            }

      out.write("\r\n                        </td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n        </div>\r\n        <br>\r\n");

    }

      out.write("\r\n    <form name=\"f\" action=\"workgroup-create.jsp\" method=\"post\">\r\n        <div>\r\n             <div class=\"jive-contentBoxHeader\">\r\n        Create New Workgroup\r\n        </div>\r\n            <table cellpadding=\"3\" cellspacing=\"3\" border=\"0\"  class=\"jive-contentBox\">\r\n\r\n                <tr valign=\"top\">\r\n                    <td width=\"30%\">\r\n                       Workgroup Name: *\r\n                        <br/>\r\n                    </td>\r\n                    <td colspan=\"2\" style=\"border-right:1px #ccc solid;\">\r\n                        <input type=\"text\" name=\"wgName\" size=\"40\" maxlength=\"45\"\r\n                               value=\"");
      out.print( ((wgName != null) ? wgName : "") );
      out.write("\"/>\r\n                        @workgroup.");
      out.print( xmppServer.getServerInfo().getXMPPDomain() );
      out.write("<br/><span class=\"jive-description\">e.g. sales, marketing, bizdev, support.</span>\r\n                    </td>\r\n                </tr>\r\n                <tr valign=\"top\">\r\n                    <td>\r\n                        Members:\r\n                    </td>\r\n                    <td width=\"1%\">\r\n                        <textarea name=\"agents\" cols=\"30\" rows=\"3\">");
      out.print( ((description != null) ? description : "") );
      out.write("</textarea><br/>\r\n                        <span class=\"jive-description\">Comma delimited list of initial members of the workgroup.</span>\r\n                    </td>\r\n                    ");
 if (!ComponentManagerFactory.getComponentManager().isExternalMode()) { 
      out.write("\r\n                    <td nowrap valign=\"top\" style=\"border-right:1px #ccc solid;\">\r\n                        <table>\r\n                            <tr>\r\n                                <td> <a href=\"#\" onclick=\"openWin(document.f.agents);return false;\"\r\n                           title=\"Click to browse available agents...\"> <img src=\"images/user.gif\" border=\"0\" alt=\"\"/></a></td>\r\n                                <td><a href=\"#\" onclick=\"openWin(document.f.agents);return false;\"\r\n                           title=\"Click to browse available agents...\">Browse...</a></td>\r\n                            </tr>\r\n                        </table>\r\n                    </td>\r\n                    ");
 } 
      out.write("\r\n                </tr>\r\n                <tr  valign=\"top\">\r\n                    <td>\r\n                       Description:\r\n\r\n                    </td>\r\n                    <td colspan=\"2\" width=\"1%\" style=\"border-right:1px #ccc solid;\">\r\n                        <textarea name=\"description\" cols=\"30\"\r\n                                  rows=\"3\">");
      out.print( ((description != null) ? description : "") );
      out.write("</textarea> <br/>\r\n                        <span class=\"jive-description\">General description of the workgroup.</span>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n        </div>\r\n\r\n   <span class=\"jive-description\">\r\n    * Required fields\r\n    </span><br/><br/>\r\n        <input type=\"submit\" name=\"create\" value=\"Create Workgroup\"/>\r\n        <input type=\"submit\" name=\"cancel\" value=\"Cancel\"/>\r\n        <input type=\"hidden\" name=\"queueName\" size=\"40\" maxlength=\"75\"\r\n               value=\"");
      out.print( ((queueName != null) ? queueName : "") );
      out.write("\"/>\r\n        <input type=\"hidden\" name=\"maxChats\" size=\"5\" maxlength=\"10\"\r\n               value=\"");
      out.print( ((maxChats > 0) ? "" + maxChats : "") );
      out.write("\"/>\r\n        <input type=\"hidden\" name=\"minChats\" size=\"5\" maxlength=\"10\"\r\n               value=\"");
      out.print( ((minChats > 0) ? "" + minChats : "") );
      out.write("\"/>\r\n    </form>\r\n\r\n\r\n    <script language=\"JavaScript\" type=\"text/javascript\">\r\n        document.f.wgName.focus();\r\n    </script>\r\n</body>\r\n</html>\r\n");
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
