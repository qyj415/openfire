package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import java.util.Iterator;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.xmpp.packet.JID;

public final class user_002dbrowser_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n");
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
 webManager.init(request, response, session, application, out ); 
      out.write("\r\n\r\n");
  // Get parameters
    int start = ParamUtils.getIntParameter(request,"start",0);
    int range = ParamUtils.getIntParameter(request,"range",10);
    String formName = ParamUtils.getParameter(request,"formName");
    String elName = ParamUtils.getParameter(request,"elName");

    String panel = ParamUtils.getParameter(request,"panel");
    if (panel == null) {
        panel = "frameset";
    }

      out.write("\r\n\r\n");
  if ("frameset".equals(panel)) { 
      out.write("\r\n\r\n<html>\r\n    <head>\r\n\r\n        <title>User Browser</title>\r\n        <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\"/>\r\n        <meta name=\"decorator\" content=\"none\"/>\r\n\r\n        <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\r\n\r\n        <script language=\"JavaScript\" type=\"text/javascript\">\r\n            var users = new Array();\r\n            function getUserListDisplay() {\r\n                var display = \"\";\r\n                var sep = \", \";\r\n                for (var i=0; i<users.length; i++) {\r\n                    if ((i+1) == users.length) {\r\n                        sep = \"\";\r\n                    }\r\n                    display += (users[i] + sep);\r\n                }\r\n                return display;\r\n            }\r\n            function printUsers(theForm) {\r\n                theForm.users.value = getUserListDisplay();\r\n            }\r\n            function addUser(theForm, username) {\r\n                users[users.length] = username;\r\n                printUsers(theForm);\r\n            }\r\n");
      out.write("            function closeWin() {\r\n                window.close();\r\n            }\r\n            function done() {\r\n                closeWin();\r\n            }\r\n        </script>\r\n    </head>\r\n\r\n\r\n    <frameset rows=\"*,105\">\r\n        <frame name=\"main\" src=\"user-browser.jsp?panel=main\"\r\n                marginwidth=\"5\" marginheight=\"5\" scrolling=\"auto\" frameborder=\"0\">\r\n        <frame name=\"bottom\" src=\"user-browser.jsp?panel=bottom&formName=");
      out.print( formName );
      out.write("&elName=");
      out.print( elName );
      out.write("\"\r\n                marginwidth=\"5\" marginheight=\"5\" scrolling=\"no\" frameborder=\"0\">\r\n    </frameset>\r\n    </html>\r\n\r\n");
  } else if ("bottom".equals(panel)) { 
      out.write("\r\n\r\n    <html>\r\n    <head>\r\n        <title><fmt:message key=\"title\" /> <fmt:message key=\"header.admin\" /></title>\r\n        <meta http-equiv=\"content-type\" content=\"text/html; charset=\">\r\n        <meta name=\"decorator\" content=\"none\"/>\r\n\r\n        <link rel=\"stylesheet\" href=\"style/global.css\" type=\"text/css\">\r\n    </head>\r\n\r\n    <body>\r\n\r\n    <style type=\"text/css\">\r\n    .mybutton {\r\n        width : 100%;\r\n    }\r\n    </style>\r\n\r\n    <form name=\"f\" onsubmit=\"return false;\">\r\n\r\n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <tr>\r\n        <td width=\"99%\">\r\n\r\n            <textarea rows=\"4\" cols=\"40\" style=\"width:100%;\" name=\"users\" wrap=\"virtual\"></textarea>\r\n\r\n        </td>\r\n        <td width=\"1%\" nowrap>\r\n\r\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"75\">\r\n            <tr>\r\n                <td>\r\n                <script language=\"javascript\">\r\n                  var currentValue = parent.opener.document.");
      out.print( formName );
      out.write('.');
      out.print( elName );
      out.write(".value;\r\n                  if(currentValue.length > 0){\r\n                    currentValue = \",\"+currentValue;\r\n                  }\r\n                </script>\r\n                    <input type=\"submit\" name=\"\" value=\"Done\" class=\"mybutton\"\r\n                     onclick=\"if(parent.getUserListDisplay()!=''){parent.opener.document.");
      out.print( formName );
      out.write('.');
      out.print( elName );
      out.write(".value=parent.getUserListDisplay()+currentValue;}parent.done();return false;\">\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"submit\" name=\"\" value=\"Cancel\" class=\"mybutton\"\r\n                     onclick=\"parent.closeWin();return false;\">\r\n                </td>\r\n            </tr>\r\n            </table>\r\n\r\n        </td>\r\n    </tr>\r\n    </table>\r\n\r\n    </form>\r\n</body>\r\n</html>\r\n\r\n");
  } else if ("main".equals(panel)) { 
      out.write("\r\n\r\n    ");
  // Get the user manager
        int userCount = webManager.getUserManager().getUserCount();

        // paginator vars
        int numPages = (int)Math.ceil((double)userCount/(double)range);
        int curPage = (start/range) + 1;
    
      out.write("\r\n\r\n    <html>\r\n<head>\r\n\r\n    <title>User Browser</title>\r\n    <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\">\r\n    <meta name=\"decorator\" content=\"none\"/>\r\n\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\r\n</head>\r\n<body class=\"jive-body\">\r\n\r\n    <p>\r\n    Total Users: ");
      out.print( webManager.getUserManager().getUserCount() );
      out.write(",\r\n    ");
  if (numPages > 1) { 
      out.write("\r\n\r\n        Showing ");
      out.print( (start+1) );
      out.write('-');
      out.print( (start+range) );
      out.write(",\r\n\r\n    ");
  } 
      out.write("\r\n    Sorted by User ID.\r\n    </p>\r\n\r\n    <p>\r\n    Viewing page ");
      out.print( curPage );
      out.write("\r\n    </p>\r\n\r\n    <p>Click \"Add User\" to add a user to the list box below. When you're done\r\n    click \"Done\".\r\n    </p>\r\n\r\n    ");
  if (numPages > 1) { 
      out.write("\r\n\r\n        <p>\r\n        Pages:\r\n        [\r\n        ");
  for (int i=0; i<numPages; i++) {
                String sep = ((i+1)<numPages) ? " " : "";
                boolean isCurrent = (i+1) == curPage;
        
      out.write("\r\n            <a href=\"user-browser.jsp?panel=main&start=");
      out.print( (i*range) );
      out.write("\"\r\n             class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\"\r\n             >");
      out.print( (i+1) );
      out.write("</a>");
      out.print( sep );
      out.write("\r\n\r\n        ");
  } 
      out.write("\r\n        ]\r\n        </p>\r\n\r\n    ");
  } 
      out.write("\r\n    <fieldset>\r\n    <legend>Possible Agents to Add</legend>\r\n    <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"100%\">\r\n\r\n        <th>&nbsp;</th>\r\n        <th>Username</th>\r\n        <th>Name</th>\r\n        <th align=\"center\">Add</th>\r\n\r\n    ");
  // Print the list of users
        Iterator users = webManager.getUserManager().getUsers(start, range).iterator();
        if (!users.hasNext()) {
    
      out.write("\r\n        <tr>\r\n            <td align=\"center\" colspan=\"4\">\r\n                No users in the system.\r\n            </td>\r\n        </tr>\r\n\r\n    ");

        }
        int i = start;
        while (users.hasNext()) {
            User user = (User)users.next();
            i++;
    
      out.write("\r\n        <tr class=\"jive-");
      out.print( (((i%2)==0) ? "even" : "odd") );
      out.write("\">\r\n            <td width=\"1%\">\r\n                ");
      out.print( i );
      out.write("\r\n            </td>\r\n            <td width=\"60%\">\r\n                ");
      out.print( JID.unescapeNode(user.getUsername()) );
      out.write("\r\n            </td>\r\n            <td width=\"50%\">\r\n            ");
 String name = user.getName();
               if(!ModelUtil.hasLength(name)){
                   name = "&nbsp;";
               }
           
      out.write("\r\n                ");
      out.print( name );
      out.write("\r\n            </td>\r\n            <td width=\"1%\" align=\"center\">\r\n                <input type=\"submit\" name=\"\" value=\"Add User\" class=\"jive-sm-button\"\r\n                 onclick=\"parent.addUser(parent.frames['bottom'].document.f,'");
      out.print( JID.unescapeNode(user.getUsername()) );
      out.write("');\">\r\n            </td>\r\n        </tr>\r\n\r\n    ");

        }
    
      out.write("\r\n    </table>\r\n    </fieldset>\r\n\r\n    ");
  if (numPages > 1) { 
      out.write("\r\n\r\n        <p>\r\n        Pages:\r\n        [\r\n        ");
  for (i=0; i<numPages; i++) {
                String sep = ((i+1)<numPages) ? " " : "";
                boolean isCurrent = (i+1) == curPage;
        
      out.write("\r\n            <a href=\"user-browser.jsp?panel=main&start=");
      out.print( (i*range) );
      out.write("\"\r\n             class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\"\r\n             >");
      out.print( (i+1) );
      out.write("</a>");
      out.print( sep );
      out.write("\r\n\r\n        ");
  } 
      out.write("\r\n        ]\r\n        </p>\r\n\r\n    ");
  } 
      out.write("\r\n    </body>\r\n</html>\r\n\r\n");
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
