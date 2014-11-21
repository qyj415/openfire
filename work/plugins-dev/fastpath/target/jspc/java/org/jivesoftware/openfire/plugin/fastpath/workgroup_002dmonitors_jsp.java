package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import org.xmpp.packet.JID;
import java.util.StringTokenizer;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.DbProperties;
import org.xmpp.component.ComponentManagerFactory;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import java.util.ArrayList;
import org.jivesoftware.xmpp.workgroup.AgentManager;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;

public final class workgroup_002dmonitors_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n<html>\n");

    String wgID = request.getParameter("wgID");
    String agents = request.getParameter("agents");

      out.write("\n    <head>\n        <title>Workgroup Monitors For ");
      out.print( wgID);
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-monitors\"/>\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\n\n\n          <!--<meta name=\"helpPage\" content=\"edit_queue_properties.html\"/>-->\n\n        <script language=\"JavaScript\" type=\"text/javascript\">\n        function openWin(el) {\n            var win = window.open('user-browser.jsp?formName=f&elName=agents', 'newWin', 'width=500,height=550,menubar=yes,location=no,personalbar=no,scrollbars=yes,resize=yes');\n        }\n\n        function openAgentGroupWindow(el) {\n            var agentwin = window.open('agent-group-browser.jsp?formName=f&elName=agentGroups', 'newWin', 'width=500,height=550,menubar=yes,location=no,personalbar=no,scrollbars=yes,resize=yes');\n\n        }\n        </script>\n    </head>\n");

    boolean errors = false;
    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    AgentManager aManager = workgroupManager.getAgentManager();

    Workgroup workgroup = WorkgroupManager.getInstance().getWorkgroup(new JID(wgID));
    DbProperties props = workgroup.getProperties();
    String users = props.getProperty("monitors");

    StringBuffer buf = new StringBuffer();
    List list = new ArrayList();
    if (agents != null) {
        // Save monitiors
        StringTokenizer tkn = new StringTokenizer(agents, ",");
        while (tkn.hasMoreTokens()) {
            String tok = tkn.nextToken();

            // Check if user is agent
            if (tok.indexOf('@') == -1) {
                tok += ("@" + ComponentManagerFactory.getComponentManager().getServerName());
            }
            try {
                JID address = new JID(tok.trim());
                boolean exists = aManager.hasAgent(address);
                if (exists) {
                    String bareJID = address.toBareJID();
                    if (!list.contains(bareJID)) {
                        buf.append(bareJID);
                        if (tkn.hasMoreTokens()) {
                            buf.append(",");
                        }
                        list.add(bareJID);
                    }
                }
            }
            catch (IllegalArgumentException e) {

            }
        }

        users = buf.toString();
        if (users.endsWith(",") && users.length() > 1) {
            users = users.substring(0, users.length() - 1);
        }

        // Save users
        if (ModelUtil.hasLength(users)) {
            System.out.println(users);
            props.setProperty("monitors", users);
        }
        else {
            errors = true;
        }
    }


    if (users == null) {
        users = "";
    }

      out.write("\n\n\n<body>\n\n");
 if (errors) { 
      out.write("\n <div class=\"jive-error\">\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                <tbody>\n                    <tr><td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\"\n                                                   border=\"0\"></td>\n                        <td class=\"jive-icon-label\">\n                           Unable to update Room Monitors.\n                        </td></tr>\n                </tbody>\n            </table>\n        </div><br>\n");
}
else if (agents != null) {
      out.write("\n <div class=\"jive-success\">\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                <tbody>\n                    <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\"\n                                                   border=\"0\"></td>\n                        <td class=\"jive-icon-label\">\n                          Room Monitors have been updated.\n                        </td></tr>\n                </tbody>\n            </table>\n        </div><br>\n\n");
 } 
      out.write("\n<form name=\"f\" method=\"post\" action=\"workgroup-monitors.jsp\">\n       <div class=\"jive-contentBoxHeader\">\n        Conversation Monitors\n        </div>\n<table  cellpadding=\"3\" cellspacing=\"0\" border=\"0\"  class=\"jive-contentBox\">\n\n    <tr valign=\"top\">\n        <td width=\"35%\"><b>Specify Workgroup Monitors</b><br/><span class=\"jive-description\">\n            Specify a comma-delimited list of monitors.<br/><br/> Monitors are users who are allowed to listen in on others\n            conversations without showing their presence in the conversation.\n        </span></td>\n   <td width=\"1%\"><textarea name=\"agents\" cols=\"40\" rows=\"5\" wrap=\"virtual\">");
      out.print( users);
      out.write("</textarea></td>\n\n    <td nowrap valign=\"top\" width=\"1%\" align=\"right\">\n    <a href=\"#\" onclick=\"openWin(document.f.agents);return false;\" title=\"Click to browse available agents...\">\n        <img src=\"/images/add-16x16.gif\" border=\"0\"/></a>\n    </td><td nowrap valign=\"top\" align=\"left\">\n    <a href=\"#\" onclick=\"openWin(document.f.agents);return false;\" title=\"Click to browse available agents...\">\n        Add Agents</a>\n    </td>\n </tr>\n\n    <tr><td colspan=\"4\"><input type=\"submit\" name=\"submit\" value=\"Submit\"></td></tr>\n</table>\n    <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\"/>\n    </form>\n</body>\n</html>");
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
