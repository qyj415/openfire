package org.jivesoftware.openfire.plugin.emailListener;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.emailListener.EmailListener;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

public final class email_002dlistener_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

    // get parameters
    String host = ParamUtils.getParameter(request,"host");
    int port = ParamUtils.getIntParameter(request,"port",0);
    String username = ParamUtils.getParameter(request,"server_username");
    String password = ParamUtils.getParameter(request,"server_password");
    boolean ssl = ParamUtils.getBooleanParameter(request,"ssl");
    String folder = ParamUtils.getParameter(request,"folder");
    int frequency = ParamUtils.getIntParameter(request,"frequency",0);
    String users = ParamUtils.getParameter(request,"users");
    boolean save = request.getParameter("save") != null;
    boolean test = request.getParameter("test") != null;
    boolean success = ParamUtils.getBooleanParameter(request,"success");
    boolean testSuccess = false;

    EmailListener emailListener = EmailListener.getInstance();
    Map<String, String> errors = new HashMap<String, String>();
    if (test || save) {
        if (host == null || host.trim().length() == 0) {
            errors.put("host","");
        }
        if (username == null || username.trim().length() == 0) {
            errors.put("username","");
        }
        if (password == null || password.trim().length() == 0) {
            errors.put("password","");
        }
        if (folder == null || folder.trim().length() == 0) {
            errors.put("folder","");
        }
        if (frequency <= 0) {
            errors.put("frequency","");
        }
        if (port <= 0) {
            errors.put("port","");
        }
        if (users == null || users.trim().length() == 0) {
            errors.put("users","");
        }

        // Get hash value of existing password
        String existingHashPassword = "";
        if (emailListener.getPassword() != null) {
            existingHashPassword = StringUtils.hash(emailListener.getPassword());
        }

        // Check if the new password was changed. If it wasn't changed, then it is the original hashed password
        // NOTE: if the new PLAIN password equals the previous HASHED password this fails, but is unlikely.
        if (!existingHashPassword.equals(password)) {
            // Hash the new password since it was changed
            String newHashPassword = "";
            if (password != null) {
                newHashPassword = StringUtils.hash(password);
            }
            // Change password if hash values are different
            if (!existingHashPassword.equals(newHashPassword)) {
                //password = password;
            }
        }
        else {
            password = emailListener.getPassword();
        }

    }

    // Handle a test request
    if (test && errors.isEmpty()) {
        testSuccess = EmailListener.testConnection(host, port, ssl, username, password, folder);
    }
    else {
        // Save the email settings if requested
        if (save) {
            if (errors.isEmpty()) {
                emailListener.setHost(host);
                emailListener.setPort(port);
                emailListener.setSSLEnabled(ssl);
                emailListener.setUser(username);
                emailListener.setPassword(password);
                emailListener.setFolder(folder);
                emailListener.setFrequency(frequency);
                emailListener.setUsers(StringUtils.stringToCollection(users));

                // Restart the email listener service
                emailListener.stop();
                emailListener.start();

                response.sendRedirect("email-listener.jsp?success=true");
            }
        }

        host = emailListener.getHost();
        port = emailListener.getPort();
        ssl = emailListener.isSSLEnabled();
        username = emailListener.getUser();
        password = emailListener.getPassword();
        folder = emailListener.getFolder();
        frequency = emailListener.getFrequency();
        users = StringUtils.collectionToString(emailListener.getUsers());
    }

      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>Email Listener</title>\r\n        <meta name=\"pageID\" content=\"email-listener\"/>\r\n    </head>\r\n    <body>\r\n\r\n<p>\r\nConfigure the email listener service with the following form. The email listener service\r\nconnects to an email server using IMAP and listens for new messages. Specified users are then alerted by\r\nIM when new messages were detected. Messages are not deleted from the mail server.    \r\n</p>\r\n\r\n");
  if (success) { 
      out.write("\r\n\r\n    <div class=\"jive-success\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img src=\"/images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        \t<td class=\"jive-icon-label\">Settings updated successfully.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n\r\n");
  } 
      out.write("\r\n\r\n");
  if (test && testSuccess && errors.isEmpty()) { 
      out.write("\r\n\r\n    <div class=\"jive-success\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img src=\"/images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        \t<td class=\"jive-icon-label\">Test was successful.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n\r\n");
  } else if (test && !testSuccess && errors.isEmpty()) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n            <td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n            <td class=\"jive-icon-label\">Test failed.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n\r\n");
  } 
      out.write("\r\n\r\n");
  if (errors.containsKey("host")) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        \t<td class=\"jive-icon-label\">Please specify the SMTP server to use.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n\r\n");
	} else if (errors.containsKey("port")) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        \t<td class=\"jive-icon-label\">Please specify the port to use to connect to the SMTP server.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n\r\n");
	} else if (errors.containsKey("username")) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        \t<td class=\"jive-icon-label\">Please specify the user to use to connect to the SMTP server.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n\r\n");
	} else if (errors.containsKey("password")) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        \t<td class=\"jive-icon-label\">Please specify the password to use to connect to the SMTP server.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n\r\n");
	} else if (errors.containsKey("folder")) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        \t<td class=\"jive-icon-label\">Please specify the folder to use in the SMTP server.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n\r\n");
	} else if (errors.containsKey("frequency")) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        \t<td class=\"jive-icon-label\">Please specify the frequency to check for new messages.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n\r\n");
	} else if (errors.containsKey("users")) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        \t<td class=\"jive-icon-label\">Please specify one or more users that will get the notifications.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n\r\n");
	} 
      out.write("\r\n\r\n<p>\r\n\r\n<!-- BEGIN SMTP settings -->\r\n<form action=\"email-listener.jsp\" name=\"f\" method=\"post\">\r\n\r\n\t<div class=\"jive-contentBoxHeader\">\r\n\t\tEmail listener settings\r\n\t</div>\r\n\t<div class=\"jive-contentBox\">\r\n\t\t<table width=\"80%\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n\t\t<tr>\r\n\t\t\t<td width=\"30%\" nowrap>\r\n\t\t\t\tMail Host:\r\n\t\t\t</td>\r\n\t\t\t<td nowrap>\r\n\t\t\t\t<input type=\"text\" name=\"host\" value=\"");
      out.print( (host != null)?host:"" );
      out.write("\" size=\"40\" maxlength=\"150\">\r\n\t\t\t</td>\r\n\t\t</tr>\r\n        <tr>\r\n            <td nowrap>\r\n                Mail Port:\r\n            </td>\r\n            <td nowrap>\r\n                <input type=\"text\" name=\"port\" value=\"");
      out.print( (port > 0) ? String.valueOf(port) : "" );
      out.write("\" size=\"10\" maxlength=\"15\">\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td nowrap>\r\n                Use SSL (Optional):\r\n            </td>\r\n            <td nowrap>\r\n                <input type=\"checkbox\" name=\"ssl\"");
      out.print( (ssl) ? " checked" : "" );
      out.write(">\r\n            </td>\r\n        </tr>\r\n\t\t<tr>\r\n\t\t\t<td nowrap>\r\n\t\t\t\tServer Username:\r\n\t\t\t</td>\r\n\t\t\t<td nowrap>\r\n\t\t\t\t<input type=\"text\" name=\"server_username\" value=\"");
      out.print( (username != null) ? username : "" );
      out.write("\" size=\"40\" maxlength=\"150\">\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr>                               \r\n\t\t\t<td nowrap>\r\n\t\t\t\tServer Password:\r\n\t\t\t</td>\r\n\t\t\t<td nowrap>\r\n\t\t\t\t<input type=\"password\" name=\"server_password\" value=\"");
      out.print( (password != null) ? StringUtils.hash(password) : "" );
      out.write("\" size=\"40\" maxlength=\"150\">\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<td nowrap>\r\n\t\t\t\tFolder:\r\n\t\t\t</td>\r\n\t\t\t<td nowrap>\r\n                <input type=\"text\" name=\"folder\" value=\"");
      out.print( (folder != null) ? folder : "Inbox" );
      out.write("\" size=\"40\" maxlength=\"150\">\r\n\t\t\t</td>\r\n\t\t</tr>\r\n        <tr>\r\n            <td nowrap>\r\n                Check Frequency (millis):\r\n            </td>\r\n            <td nowrap>\r\n                <input type=\"text\" name=\"frequency\" value=\"");
      out.print( (frequency > 0) ? String.valueOf(frequency) : "" );
      out.write("\" size=\"10\" maxlength=\"15\">\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td nowrap>\r\n                JID of users to notify:<br>\r\n                <i>(comma delimited)</i>\r\n            </td>\r\n            <td nowrap>\r\n                <textarea name=\"users\" cols=\"40\" rows=\"3\" wrap=\"virtual\">");
      out.print( users);
      out.write("</textarea>\r\n            </td>\r\n        </tr>\r\n\t\t</table>\r\n\t</div>\r\n\r\n<input type=\"submit\" name=\"save\" value=\"Save\">\r\n<input type=\"submit\" name=\"test\" value=\"Test Settings\">\r\n</form>\r\n<!-- END SMTP settings -->\r\n\r\n</body>\r\n</html>");
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
