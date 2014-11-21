package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.openfire.group.GroupManager;
import org.jivesoftware.openfire.group.Group;

public final class agent_002dgroup_002dbrowser_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

    GroupManager groupManager = GroupManager.getInstance();

      out.write("\r\n\r\n");
 // Get parameters
    int              start = ParamUtils.getIntParameter(request, "start", 0);
    int              range = ParamUtils.getIntParameter(request, "range", 10);
    String           formName = ParamUtils.getParameter(request, "formName");
    String           elName = ParamUtils.getParameter(request, "elName");

    String           panel = ParamUtils.getParameter(request, "panel");
    if (panel == null) {
        panel = "frameset";
    }

      out.write("\r\n\r\n");

    if ("frameset".equals(panel)) {

      out.write("\r\n\r\n        <html>\r\n        <head>\r\n            <title>Group Browser</title>\r\n\r\n            <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\"/>\r\n            <meta name=\"decorator\" content=\"none\"/>\r\n\r\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\r\n\r\n            <script language=\"JavaScript\" type=\"text/javascript\">\r\n                var users = new Array();\r\n\r\n                function getUserListDisplay() {\r\n                    var display = \"\";\r\n                    var sep = \", \";\r\n\r\n                    for (var i = 0; i < users.length; i++) {\r\n                        if ((i + 1) == users.length) {\r\n                            sep = \"\";\r\n                        }\r\n\r\n                        display += (users[i] + sep);\r\n                    }\r\n                    return display;\r\n                }\r\n\r\n                function printUsers(theForm) {\r\n                    theForm.users.value = getUserListDisplay();\r\n                }\r\n\r\n                function addUser(theForm, username) {\r\n");
      out.write("\r\n                    users[users.length] = username;\r\n                    printUsers(theForm);\r\n                }\r\n\r\n                function closeWin() {\r\n                    window.close();\r\n                }\r\n\r\n                function done() {\r\n                    closeWin();\r\n                }\r\n            </script>\r\n        </head>\r\n\r\n        <frameset rows=\"*,105\">\r\n            <frame name=\"main\"      src=\"agent-group-browser.jsp?panel=main\" marginwidth=\"5\" marginheight=\"5\"\r\n                   scrolling=\"auto\" frameborder=\"0\">\r\n                <frame name=\"bottom\"\r\n                       src=\"agent-group-browser.jsp?panel=bottom&formName=");
      out.print( formName );
      out.write("&elName=");
      out.print( elName );
      out.write("\"\r\n                       marginwidth=\"5\"\r\n                       marginheight=\"5\"\r\n                       scrolling=\"no\"\r\n                       frameborder=\"0\">\r\n        </frameset>\r\n        </html>\r\n\r\n");

    }
    else if ("bottom".equals(panel)) {

      out.write("\r\n\r\n        <html>\r\n        <head>\r\n            <title><fmt:message key=\"title\" /> <fmt:message key=\"header.admin\" /></title>\r\n            <meta http-equiv=\"content-type\" content=\"text/html; charset=\">\r\n            <meta name=\"decorator\" content=\"none\"/>\r\n\r\n            <link rel=\"stylesheet\" href=\"style/global.css\" type=\"text/css\">\r\n        </head>\r\n\r\n        <body>\r\n        <style type=\"text/css\">\r\n            .mybutton\r\n            {\r\n             width: 100%;\r\n            }\r\n        </style>\r\n\r\n        <form name=\"f\" onsubmit=\"return false;\">\r\n            <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n                <tr>\r\n                    <td width=\"99%\">\r\n                        <textarea rows=\"4\" cols=\"40\" style=\"width:100%;\" name=\"users\" wrap=\"virtual\">\r\n                        </textarea>\r\n                    </td>\r\n\r\n                    <td width=\"1%\" nowrap>\r\n                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"75\">\r\n                            <tr>\r\n                                <td>\r\n");
      out.write("                                    <script language=\"javascript\">\r\n                                        var currentValue = parent.opener.document.");
      out.print( formName );
      out.write('.');
      out.print( elName );
      out.write(".value;\r\n\r\n                                        if (currentValue.length > 0) {\r\n                                            currentValue = \",\" + currentValue;\r\n                                        }\r\n                                    </script>\r\n\r\n                                    <input type=\"submit\"\r\n                                           name=\"\"\r\n                                           value=\"Done\"\r\n                                           class=\"mybutton\"\r\n                                           onclick=\"if(parent.getUserListDisplay()!=''){parent.opener.document.");
      out.print( formName );
      out.write('.');
      out.print( elName );
      out.write(".value=parent.getUserListDisplay()+currentValue;}parent.done();return false;\">\r\n                                </td>\r\n                            </tr>\r\n\r\n                            <tr>\r\n                                <td>\r\n                                    <input type=\"submit\" name=\"\" value=\"Cancel\" class=\"mybutton\"\r\n                                           onclick=\"parent.closeWin();return false;\">\r\n                                </td>\r\n                            </tr>\r\n                        </table>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n        </form>\r\n\r\n        </body>\r\n    </html>\r\n");

    }
    else if ("main".equals(panel)) {

      out.write("\r\n\r\n");
 // Get the user manager
        int userCount = groupManager.getGroupCount();

        // paginator vars
        int numPages = (int) Math.ceil((double) userCount / (double) range);
        int curPage = (start / range) + 1;

      out.write("\r\n\r\n        <html>\r\n        <head>\r\n            <title>Agent Browser</title>\r\n\r\n            <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\">\r\n            <meta name=\"decorator\" content=\"none\"/>\r\n\r\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\r\n        </head>\r\n\r\n        <body class=\"jive-body\">\r\n            <p>\r\n                Total Groups: ");
      out.print( groupManager.getGroupCount() );
      out.write(",\r\n\r\n");

                if (numPages > 1) {

      out.write("\r\n\r\n                            Showing ");
      out.print( (start + 1) );
      out.write('-');
      out.print( (start + range) );
      out.write(",\r\n\r\n");

                }

      out.write("\r\n\r\n                        Sorted by Group ID\r\n            </p>\r\n\r\n            <p>\r\n                Viewing page ");
      out.print( curPage );
      out.write("\r\n            </p>\r\n\r\n            <p>\r\n                Click \"Add Group\" to add a group to the list box below. When you are finished, click \"Done\".\r\n            </p>\r\n\r\n");

            if (numPages > 1) {

      out.write("\r\n\r\n                    <p>\r\n                    Pages: [\r\n\r\n");

                    for (int i = 0; i < numPages; i++) {
                        String sep = ((i + 1) < numPages) ? " " : "";
                        boolean isCurrent = (i + 1) == curPage;

      out.write("\r\n\r\n                            <a href=\"agent-group-browser.jsp?panel=main&start=");
      out.print( (i * range) );
      out.write("\"\r\n                               class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write('"');
      out.write('>');
      out.print( (i + 1) );
      out.write("</a>");
      out.print( sep );
      out.write("\r\n\r\n");

                            }

      out.write("\r\n\r\n                            ]\r\n                    </p>\r\n\r\n");

            }

      out.write("\r\n\r\n            <fieldset>\r\n                <legend>\r\n                    Possible Groups to Add\r\n                </legend>\r\n\r\n                <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"100%\">\r\n                    <th>\r\n                        &nbsp;\r\n                    </th>\r\n\r\n                    <th>\r\n                        Name/Description\r\n                    </th>\r\n\r\n                    <th align=\"center\">\r\n                        Add\r\n                    </th>\r\n\r\n");

                        if (groupManager.getGroupCount() == 0) {

      out.write("\r\n\r\n                            <tr>\r\n                                <td align=\"center\" colspan=\"3\">\r\n                                    No groups in the system.\r\n                                </td>\r\n                            </tr>\r\n\r\n");

                        }
                        else{

      out.write("\r\n\r\n");

                            // Print the list of users
                            int i = start;
                            for(Group group : groupManager.getGroups()){
                                i++;

      out.write("\r\n\r\n                                <tr class=\"jive-");
      out.print( (((i % 2) == 0) ? "even" : "odd") );
      out.write("\">\r\n                                    <td width=\"1%\">\r\n                                        ");
      out.print( i );
      out.write("\r\n                                    </td>\r\n\r\n                                    <td>\r\n                                        ");
      out.print( group.getName() );
      out.write("\r\n                                    </td>\r\n\r\n                                    <td width=\"1%\" align=\"center\">\r\n                                        <input type=\"submit\"\r\n                                               name=\"\"\r\n                                               value=\"Add Group\"\r\n                                               class=\"jive-sm-button\"\r\n                                               onclick=\"parent.addUser(parent.frames['bottom'].document.f,'");
      out.print( group.getName() );
      out.write("');\">\r\n                                    </td>\r\n                                </tr>\r\n\r\n");

                            }
                        }

      out.write("\r\n                </table>\r\n            </fieldset>\r\n\r\n            </body>\r\n        </html>\r\n");

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
