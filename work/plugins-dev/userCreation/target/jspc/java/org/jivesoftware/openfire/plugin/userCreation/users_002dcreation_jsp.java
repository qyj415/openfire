package org.jivesoftware.openfire.plugin.userCreation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.TaskEngine;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.UserCreationPlugin;
import java.util.HashMap;
import java.util.Map;

public final class users_002dcreation_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n<html>\r\n    <head>\r\n        <title>Quick Users Creation</title>\r\n        <meta name=\"pageID\" content=\"users-creation\"/>\r\n    </head>\r\n    <body>\r\n\r\n");

    String prefix = ParamUtils.getParameter(request, "prefix");
    String from = ParamUtils.getParameter(request, "from");
    String total = ParamUtils.getParameter(request, "total");
    String usersPerRoster = ParamUtils.getParameter(request, "usersPerRoster");

    Map<String, String> errors = new HashMap<String, String>();

    boolean running = false;

    if (prefix != null) {
        final String userPrefix = prefix;
        final int intFrom = Integer.parseInt(from);
        final int maxUsers = Integer.parseInt(total);
        final int usersRoster = Integer.parseInt(usersPerRoster) + 1;
        if (maxUsers % usersRoster != 0 || maxUsers <= usersRoster) {
            errors.put("arguments", "");
        }

        if (errors.isEmpty()) {
            final UserCreationPlugin plugin =
                    (UserCreationPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("usercreation");
            TaskEngine.getInstance().submit(new Runnable() {
                public void run() {
                    plugin.createUsers(userPrefix, intFrom, maxUsers);
                    plugin.populateRosters(userPrefix, intFrom, maxUsers, usersRoster);
                    plugin.createVCards(userPrefix, intFrom, maxUsers);
                }
            });
            running = true;
        }
    }

      out.write("\r\n\r\n");
  if (!errors.isEmpty()) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n            <td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"/></td>\r\n            <td class=\"jive-icon-label\">\r\n\r\n            ");
 if (errors.get("arguments") != null) { 
      out.write("\r\n                Number of users per roster should be greater than total number of users. Number of users per roster <b>plus one</b> should also be a multiple of total number of users. \r\n            ");
 } 
      out.write("\r\n            </td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n    <br>\r\n\r\n");
  } else if (running) { 
      out.write("\r\n\r\n    <div class=\"jive-success\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr><td class=\"jive-icon\"><img src=\"/images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\r\n        <td class=\"jive-icon-label\">\r\n        Users being created in background and getting their rosters populated. Check the stdout for more information.\r\n        </td></tr>\r\n    </tbody>\r\n    </table>\r\n    </div><br>\r\n\r\n");
  } 
      out.write("\r\n\r\n<form name=\"f\" action=\"users-creation.jsp\">\r\n    <fieldset>\r\n        <legend>Creation Form</legend>\r\n        <div>\r\n        <table cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"600\">\r\n        <tr class=\"c1\">\r\n            <td width=\"1%\" colspan=\"2\" nowrap>\r\n                User prefix:\r\n                &nbsp;<input type=\"text\" name=\"prefix\" value=\"");
      out.print((prefix != null ? prefix : "") );
      out.write("\" size=\"30\" maxlength=\"75\"/>\r\n\t        </td>\r\n        </tr>\r\n        <tr class=\"c1\">\r\n            <td width=\"1%\" colspan=\"2\" nowrap>\r\n                From index:\r\n                &nbsp;<input type=\"text\" name=\"from\" value=\"");
      out.print((from != null ? from : "0") );
      out.write("\" size=\"5\" maxlength=\"15\"/>\r\n\t        </td>\r\n        </tr>\r\n        <tr class=\"c1\">\r\n            <td width=\"1%\" colspan=\"2\" nowrap>\r\n                Total users:\r\n                &nbsp;<input type=\"text\" name=\"total\" value=\"");
      out.print((total != null ? total : "1000") );
      out.write("\" size=\"5\" maxlength=\"15\"/>\r\n\t        </td>\r\n        </tr>\r\n        <tr class=\"c1\">\r\n            <td width=\"1%\" colspan=\"2\" nowrap>\r\n                Contacts in roster:\r\n                &nbsp;<input type=\"text\" name=\"usersPerRoster\" value=\"");
      out.print((usersPerRoster != null ? usersPerRoster : "30") );
      out.write("\" size=\"5\" maxlength=\"15\"/>\r\n\t        </td>\r\n        </tr>\r\n            <tr class=\"c1\">\r\n                <td width=\"1%\" colspan=\"2\" nowrap>\r\n                    <input type=\"submit\" name=\"Create\"/>\r\n                </td>\r\n            </tr>\r\n        </table>\r\n        </div>\r\n    </fieldset>\r\n</form>\r\n\r\n</body>\r\n</html>");
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
