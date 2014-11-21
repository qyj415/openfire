package org.jivesoftware.openfire.plugin.subscription;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.jivesoftware.openfire.user.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.SubscriptionPlugin;
import org.jivesoftware.util.*;

public final class subscription_002dplugin_002dproperties_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

   boolean save = request.getParameter("save") != null;
   boolean success = request.getParameter("success") != null;
   String type = ParamUtils.getParameter(request, "type");
   String level = ParamUtils.getParameter(request, type);
   
   String username = ParamUtils.getParameter(request, "username");
   boolean addUser = ParamUtils.getBooleanParameter(request, "addUser");
   boolean deleteUser = ParamUtils.getBooleanParameter(request, "deleteUser");   

   SubscriptionPlugin plugin = (SubscriptionPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("subscription");

   Map<String, String> errors = new HashMap<String, String>();
   
	if (addUser) {
      if (username == null) {
          errors.put("missingUser", "missingUser");
      }
      else {
         username = username.trim().toLowerCase();
      
         try  {
            XMPPServer.getInstance().getUserManager().getUser(username);
            plugin.addWhiteListUser(username);
            response.sendRedirect("subscription-plugin-properties.jsp?addSuccess=true");
            return;
         }
         catch (UserNotFoundException unfe) {
            errors.put("userNotFound", "userNotFound");
         }
      }
   }

   if (deleteUser) {
      plugin.removeWhiteListUser(username);
      response.sendRedirect("subscription-plugin-properties.jsp?deleteSuccess=true");
      return;
   }
   
   
   if (save) {      
      plugin.setSubscriptionType(type);
      
      if (level != null) {
         plugin.setSubscriptionLevel(level);
      }
            
      response.sendRedirect("subscription-plugin-properties.jsp?success=true");
      return;
   }
   
   type = plugin.getSubscriptionType();
   level = plugin.getSubscriptionLevel();

      out.write("\r\n\r\n<html>\r\n\t<head>\r\n\t  <title>Subscription Service Properties</title>\r\n\t  <meta name=\"pageID\" content=\"subscription-plugin-properties\"/>\r\n   </head>\r\n   <body>\r\n   \r\n   <script language=\"JavaScript\" type=\"text/javascript\">\r\n      function addUsername() {\r\n         document.notifyform.addUser.value = 'true';\r\n         document.notifyform.submit();\r\n      }\r\n   </script>\r\n\r\n<p>Use the form below to set the subscription service properties.</p>\r\n\r\n");
 if (success) { 
      out.write("\r\n\r\n\t<div class=\"jive-success\">\r\n\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n\t<tbody>\r\n\t   <tr>\r\n         <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n         <td class=\"jive-icon-label\">Service properties edited successfully.</td>\r\n      </tr>\r\n   </tbody>\r\n   </table>\r\n   </div>\r\n   <br>\r\n    \r\n");
 } 
      out.write("\r\n\r\n<form action=\"subscription-plugin-properties.jsp?save\" name=\"notifyform\" method=\"post\">\r\n<input type=\"hidden\" name=\"addUser\" value=\"\">\r\n\r\n<fieldset>\r\n   <legend>Subscription Service Settings</legend>\r\n   <div>\r\n   <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n   <tbody>\r\n      <tr>\r\n         <td width=\"1%\">\r\n            <input type=\"radio\" name=\"type\" value=\"");
      out.print( SubscriptionPlugin.DISABLED );
      out.write("\" id=\"rb01\"\r\n               ");
      out.print( (type.equals(SubscriptionPlugin.DISABLED) ? "checked" : "") );
      out.write(">\r\n         </td>\r\n         <td width=\"99%\">\r\n            <label for=\"rb01\"><strong>Disabled</strong></label> - No subscriptions requests will be intercepted.\r\n         </td>\r\n      </tr>      \r\n      <tr>\r\n         <td width=\"1%\">\r\n            <input type=\"radio\" name=\"type\" value=\"");
      out.print( SubscriptionPlugin.ACCEPT );
      out.write("\" id=\"rb02\"\r\n               ");
      out.print( (type.equals(SubscriptionPlugin.ACCEPT) ? "checked" : "") );
      out.write(">\r\n         </td>\r\n         <td width=\"99%\">\r\n            <label for=\"rb02\"><strong>Accept</strong></label> - Subscription requests will be intercepted and accepted.\r\n         </td>\r\n      </tr>\r\n      <tr valign=\"top\">\r\n\t      <td width=\"1%\" nowrap>&nbsp;</td>\r\n         <td width=\"99%\">\r\n\r\n            <table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n               <tr>\r\n                  <td width=\"1%\">\r\n                     <input type=\"radio\" name=\"accept\" value=\"");
      out.print( SubscriptionPlugin.LOCAL );
      out.write("\" id=\"rb03\"\r\n                        ");
      out.print( (level.equals(SubscriptionPlugin.LOCAL) ? "checked" : "") );
      out.write(">\r\n                  </td>\r\n                  <td width=\"99%\">\r\n                     <label for=\"rb03\"><strong>Local</strong></label> - Only subscription requests sent by users <u>who have</u> an account on <i>");
      out.print(XMPPServer.getInstance().getServerInfo().getXMPPDomain() );
      out.write("</i> will be intercepted and accepted.\r\n                  </td>\r\n               </tr>\r\n               <tr>\r\n                  <td width=\"1%\">\r\n                     <input type=\"radio\" name=\"accept\" value=\"");
      out.print( SubscriptionPlugin.ALL );
      out.write("\" id=\"rb04\"\r\n                        ");
      out.print( (level.equals(SubscriptionPlugin.ALL) ? "checked" : "") );
      out.write(">\r\n                  </td>\r\n                  <td width=\"99%\">\r\n                     <label for=\"rb04\"><strong>All</strong></label> - All subscription requests will be intercepted and automatically accepted.\r\n                  </td>\r\n               </tr>\r\n             </table>\r\n         </td>\r\n      </tr>\r\n      <tr>\r\n         <td width=\"1%\">\r\n            <input type=\"radio\" name=\"type\" value=\"");
      out.print( SubscriptionPlugin.REJECT );
      out.write("\" id=\"rb05\"\r\n               ");
      out.print( (type.equals(SubscriptionPlugin.REJECT) ? "checked" : "") );
      out.write(">\r\n         </td>\r\n         <td width=\"99%\">\r\n            <label for=\"rb05\"><strong>Reject</strong></label> - Subscription requests will be intercepted and rejected.\r\n         </td>\r\n      </tr>\r\n      <tr valign=\"top\">\r\n         <td width=\"1%\" nowrap>&nbsp;</td>\r\n         <td width=\"99%\">\r\n            <table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n               <tr>\r\n                  <td width=\"1%\">\r\n                     <input type=\"radio\" name=\"reject\" value=\"");
      out.print( SubscriptionPlugin.LOCAL );
      out.write("\" id=\"rb06\"\r\n                        ");
      out.print( (level.equals(SubscriptionPlugin.LOCAL) ? "checked" : "") );
      out.write(">\r\n                  </td>\r\n                  <td width=\"99%\">\r\n                     <label for=\"rb06\"><strong>Local</strong></label> - Only subscription requests sent by users <u>who do not have</u> an account on <i>");
      out.print(XMPPServer.getInstance().getServerInfo().getXMPPDomain() );
      out.write("</i> will be intercepted and rejected.\r\n                  </td>\r\n               </tr>\r\n               <tr>\r\n                  <td width=\"1%\">\r\n                     <input type=\"radio\" name=\"reject\" value=\"");
      out.print( SubscriptionPlugin.ALL );
      out.write("\" id=\"rb07\"\r\n                        ");
      out.print( (level.equals(SubscriptionPlugin.ALL) ? "checked" : "") );
      out.write(">\r\n                  </td>\r\n                  <td width=\"99%\">\r\n                     <label for=\"rb07\"><strong>All</strong></label> - All subscription requests will be intercepted and rejected.\r\n                  </td>\r\n                </tr>\r\n             </table>\r\n         </td>\r\n      </tr>\r\n   </tbody>\r\n   </table>\r\n   </div>\r\n   \r\n\t<br>\r\n   <input type=\"submit\" value=\"Save Settings\">\r\n</fieldset>\r\n\r\n<br><br>\r\n\r\n<fieldset>\r\n   <legend>White List</legend>\r\n   <div>\r\n   \r\n   <p>Any user specified in the list below will continue to have full control over manually accepting and rejecting subscription requests.</p>\r\n   \r\n   ");
 if (ParamUtils.getBooleanParameter(request, "deleteSuccess")) { 
      out.write("\r\n   \r\n   <div class=\"jive-success\">\r\n   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n   <tbody>\r\n      <tr>\r\n         <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n         <td class=\"jive-icon-label\">User successfully removed.</td>\r\n      </tr>\r\n   </tbody>\r\n   </table>\r\n   </div>\r\n   \r\n   ");
 } else if (ParamUtils.getBooleanParameter(request, "addSuccess")) { 
      out.write("\r\n   \r\n   <div class=\"jive-success\">\r\n   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n   <tbody>\r\n      <tr>\r\n         <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n         <td class=\"jive-icon-label\">User successfully added.</td>\r\n      </tr>\r\n   </tbody>\r\n   </table>\r\n   </div>\r\n   \r\n   ");
 } else if (errors.containsKey("missingUser")) { 
      out.write("\r\n   \r\n   <div class=\"jive-error\">\r\n   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n   <tbody>\r\n      <tr>\r\n         <td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n         <td class=\"jive-icon-label\">Missing user.</td>\r\n      </tr>\r\n   </tbody>\r\n   </table>\r\n   </div>\r\n   \r\n   ");
 } else if (errors.containsKey("userNotFound")) { 
      out.write("\r\n   \r\n   <div class=\"jive-error\">\r\n   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n   <tbody>\r\n      <tr>\r\n         <td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n         <td class=\"jive-icon-label\">User not found.</td>\r\n      </tr>\r\n   </tbody>\r\n   </table>\r\n   </div>\r\n   \r\n   ");
 } 
      out.write("\r\n   \r\n   <div>\r\n   <label for=\"usertf\">Add user:</label>\r\n   <input type=\"text\" name=\"username\" size=\"30\" maxlength=\"100\" value=\"");
      out.print( (username != null ? username : "") );
      out.write("\" id=\"usertf\"/>\r\n   <input type=\"submit\" value=\"Add\" onclick=\"return addUsername();\"/>\r\n   \r\n   <br><br>\r\n   \r\n   <div class=\"jive-table\" style=\"width:400px;\">\r\n   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n   <thead>\r\n      <tr>\r\n         <th width=\"99%\">User</th>\r\n         <th width=\"1%\" nowrap>Remove</th>\r\n      </tr>\r\n   </thead>\r\n   <tbody>\r\n   ");
 if (plugin.getWhiteListUsers().size() == 0) { 
      out.write("\r\n   \r\n   <tr>\r\n      <td width=\"100%\" colspan=\"2\" align=\"center\" nowrap>No users specified, use the form above to add one.</td>\r\n   </tr>\r\n   \r\n   ");
 } 
      out.write("\r\n   \r\n   ");
 for (String user : plugin.getWhiteListUsers()) { 
      out.write("\r\n   \r\n   <tr>\r\n      <td width=\"99%\">");
      out.print(user );
      out.write("</td>\r\n      <td width=\"1%\" align=\"center\"><a\r\n                     href=\"subscription-plugin-properties.jsp?deleteUser=true&username=");
      out.print(user );
      out.write("\"\r\n                     title=\"Delete User?\"\r\n                     onclick=\"return confirm('Are you sure you want to delete this user?');\"><img\r\n                     src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\"\r\n                     border=\"0\" alt=\"\"></a>\r\n      </td>\r\n   </tr>\r\n   \r\n   ");
 } 
      out.write("\r\n   </tbody>\r\n   </table>\r\n   </div>\r\n   </div>\r\n</fieldset>\r\n\r\n</form>\r\n\r\n</body>\r\n</html>");
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
