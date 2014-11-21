package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.openfire.fastpath.settings.offline.OfflineSettingsManager;
import org.jivesoftware.openfire.fastpath.settings.offline.OfflineSettings;
import org.jivesoftware.openfire.fastpath.settings.offline.OfflineSettingsNotFound;
import org.jivesoftware.openfire.fastpath.util.WorkgroupUtils;
import org.jivesoftware.util.JiveGlobals;

public final class workgroup_002doffline_jsp extends org.apache.jasper.runtime.HttpJspBase
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
 try { 
      out.write("\r\n\r\n ");

     String wgID = request.getParameter("wgID");
     final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
     Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));

     OfflineSettingsManager offlineManager = new OfflineSettingsManager();
     String redirectValue = request.getParameter("redirectToPage");
     String statusMessage = "";

     OfflineSettings offlineSettings = null;

     boolean emailConfigured = false;

     String property = JiveGlobals.getProperty("mail.configured");
     if (ModelUtil.hasLength(property)) {
         emailConfigured = true;
     }

     boolean delete = request.getParameter("delete") != null;
     boolean save = request.getParameter("save") != null && !delete;

     if (save){
         String emailAddress = request.getParameter("email");
         String subject = request.getParameter("subject");
         String header = request.getParameter("headerField");
         offlineSettings = offlineManager.saveOfflineSettings(workgroup, redirectValue, emailAddress, subject, header);
         if (offlineSettings != null) {
             statusMessage = "Offline settings have been saved.";
         }
     }
     else if(delete){
        statusMessage = "Offline settings have been deleted.";
        offlineSettings = offlineManager.saveOfflineSettings(workgroup, redirectValue, "", "", "");
     }
     else {
         try {
             offlineSettings = offlineManager.getOfflineSettings(workgroup);
         }
         catch (OfflineSettingsNotFound offlineSettingsNotFound) {
             offlineSettings = new OfflineSettings();
         }

     }
 
      out.write("\r\n<html>\r\n    <head>\r\n        <title>");
      out.print( "Offline Settings for "+wgID);
      out.write("</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-offline\"/>\r\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\r\n        <!--<meta name=\"helpPage\" content=\"set_an_offline_policy_for_a_workgroup.html\"/>-->\r\n\r\n        <script>\r\n        function saveOfflineSettings(){\r\n             var todo = document.offline.todo;\r\n             if(todo[0].checked){\r\n                 var url = document.offline.redirectToPage.value;\r\n                 if(!Jtrim(url)){\r\n                   alert(\"Please specify the URL to forward to.\");\r\n                   document.offline.redirectToPage.focus();\r\n                   return;\r\n                 }\r\n                 document.offline.email.value = \"\";\r\n                 document.offline.subject.value = \"\";\r\n                 document.offline.headerField.value = \"\";\r\n\r\n                 document.offline.submit();\r\n             }\r\n             else if(todo[1].checked){\r\n               var email = document.offline.email.value;\r\n               var subject = document.offline.subject.value;\r\n               var message = document.offline.headerField.value;\r\n               document.offline.redirectToPage.value = '';\r\n");
      out.write("\r\n               if(!Jtrim(email) || !Jtrim(subject) || !Jtrim(message)){\r\n                 alert(\"All fields are required.\");\r\n                 return;\r\n               }\r\n                document.offline.submit();\r\n             }\r\n        }\r\n\r\n\r\n\r\n         function Jtrim(st) {\r\n             var len = st.length;\r\n             var begin = 0, end = len - 1;\r\n             while (st.charAt(begin) == \" \" && begin < len) {\r\n                 begin++;\r\n             }\r\n             while (st.charAt(end) == \" \" && end > begin) {\r\n                 end--;\r\n             }\r\n             return st.substring(begin, end + 1);\r\n         }\r\n        </script>\r\n    </head>\r\n    <body>\r\n    Specify action to take when this workgroup has no available agents to take incoming chat requests.\r\n    ");
 if(statusMessage != null && !statusMessage.equals("")) { 
      out.write("\r\n    <div class=\"success\">\r\n        ");
      out.print( statusMessage );
      out.write("\r\n    </div>\r\n    ");
 } 
      out.write("\r\n\r\n      ");
 if(!emailConfigured){ 
      out.write("\r\n            <div class=\"error\">\r\n                Email form will not be displayed until you configure your <a href=\"../../system-email.jsp\">email settings</a>.\r\n            </div>\r\n    ");
 } 
      out.write("\r\n\r\n    <div id=\"offline_message\">");
      out.print( statusMessage );
      out.write("</div>\r\n    <p/>\r\n    <form action=\"workgroup-offline.jsp\" method=\"get\" name=\"offline\">\r\n    <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\r\n    <div>\r\n        <div class=\"jive-contentBoxHeader\">\r\n        Offline Workgroup Action\r\n        </div>\r\n        <table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" class=\"jive-contentBox\">\r\n                <tr valign=\"top\">\r\n                ");
 String checked = offlineSettings.redirects() ? "checked" : ""; 
      out.write("\r\n                            <td width=\"1%\">\r\n                                <input type=\"radio\" name=\"todo\" value=\"redirectToPage\" ");
      out.print( checked );
      out.write(" />\r\n                            </td>\r\n                            <td nowrap><b>Redirect To Web Page</b>\r\n                               </td>\r\n                            <td class=\"c2\">\r\n                                <input type=\"text\" name=\"redirectToPage\" size=\"40\" value=\"");
      out.print( offlineSettings.getRedirectURL() );
      out.write("\" /><br/>\r\n                                 <span class=\"jive-description\">e.g. http://www.jivesoftware.com/contact.html</span>\r\n                            </td>\r\n                </tr>\r\n                <tr>\r\n                    <td nowrap width=\"1%\">\r\n                         <input type=\"radio\" name=\"todo\" value=\"showEmailPage\" ");
      out.print(!offlineSettings.redirects() ? "checked" :"" );
      out.write("/>\r\n                         <td><b>Display Email Form</b></td>\r\n                     </td>\r\n                     <td>&nbsp;</td>\r\n                </tr>\r\n                <!-- Email Address -->\r\n                <tr valign=\"top\">\r\n                    <td>&nbsp;</td>\r\n                    <td>Email Address:</td>\r\n                    <td>\r\n                        <input type=\"text\" size=\"40\" name=\"email\" value=\"");
      out.print( offlineSettings.getEmailAddress() );
      out.write("\" /><br/>\r\n                        <span class=\"jive-description\">Email address to send all offline messages to.</span>\r\n                    </td>\r\n                </tr>\r\n                <!-- End of Email Address -->\r\n                <!-- Subject Line -->\r\n                 <tr valign=\"top\">\r\n                    <td>&nbsp;</td>\r\n                    <td>Subject:</td>\r\n                    <td>\r\n                        <input type=\"text\" size=\"40\" name=\"subject\" value=\"");
      out.print( offlineSettings.getSubject() );
      out.write("\"/><br/>\r\n                        <span class=\"jive-description\">The subject of all offline email messages.</span>\r\n                    </td>\r\n                </tr>\r\n                <!--  End Of Subject Line -->\r\n                <tr valign=\"top\">\r\n                     <td>&nbsp;</td>\r\n                    <td>Offline Text:</td>\r\n                    <td>\r\n                        <textarea name=\"headerField\" cols=\"40\" rows=\"5\">");
      out.print( offlineSettings.getOfflineText()  );
      out.write("</textarea><br/>\r\n                        <span class=\"jive-description\">Text to display to the user in the email form.</span>\r\n                    </td>\r\n                </tr>\r\n                    <input type=\"hidden\" name=\"save\" value=\"save\">\r\n                 <tr>\r\n                </tr>\r\n            ");
      out.write("\r\n            </table>\r\n            <table><tr>\r\n                 <td colspan=\"1\"> <input type=\"button\" name=\"save\" value=\"Save Changes\" onclick=\"return saveOfflineSettings();\" /></td>\r\n                <td colspan=\"1\"> <input type=\"submit\" name=\"delete\" value=\"Delete Changes\" /></td>\r\n            </tr></table>\r\n       </div>\r\n    </form>\r\n\r\n</body>\r\n</html>\r\n\r\n");
 } catch(Exception ex){ex.printStackTrace();} 
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
