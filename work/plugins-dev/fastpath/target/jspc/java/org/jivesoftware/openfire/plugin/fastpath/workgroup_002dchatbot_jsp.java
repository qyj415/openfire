package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.*;
import java.util.*;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupAdminManager;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSettings;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSettingsManager;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSetting;

public final class workgroup_002dchatbot_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n");

    // Get parameters
    String wgID = ParamUtils.getParameter(request, "wgID");
    WorkgroupAdminManager workgroupAdminManager = new WorkgroupAdminManager();
    workgroupAdminManager.init(pageContext);
    boolean updated = ParamUtils.getBooleanParameter(request, "updated", false);
    boolean enabled = ParamUtils.getBooleanParameter(request, "enabled", false);
    long timeout = ParamUtils.getIntParameter(request, "timeout", 30);

    JID workgroupJID = new JID(wgID);
    String restore = request.getParameter("restore");

    final ChatSettingsManager chatSettingsManager = ChatSettingsManager.getInstance();
    Workgroup workgroup = WorkgroupManager.getInstance().getWorkgroup(workgroupJID);
    ChatSettings chatSettings = chatSettingsManager.getChatSettings(workgroup);

    String saveText = request.getParameter("saveText");
    if (ModelUtil.hasLength(saveText)) {
        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String key = (String)en.nextElement();
            String value = request.getParameter(key);
            ChatSetting setting = chatSettings.getChatSetting(key);
            if (setting != null && ModelUtil.hasLength(value)) {
                setting.setValue(value);
                chatSettingsManager.updateChatSetting(setting);
            }
        }
    }

    String keys = request.getParameter("_key");
    if (ModelUtil.hasLength(keys)) {
        ChatSetting setting = chatSettings.getChatSetting(keys);
        String defaultValue = setting.getDefaultValue();
        setting.setValue(defaultValue);
        chatSettingsManager.updateChatSetting(setting);
    }

    String enabledText = request.getParameter("enabled");
    String timeoutText = request.getParameter("timeout");
    if (ModelUtil.hasLength(enabledText)) {
        workgroup.chatbotEnabled(enabled);
    }
    else if (ModelUtil.hasLength(timeoutText)) {
        if (workgroup.isChatbotEnabled()) {
            workgroup.getChatBot().setIdleTimeout(timeout * 60 * 1000);
        }
    }
    else {
        enabled = workgroup.isChatbotEnabled();
        if (enabled) {
            timeout = workgroup.getChatBot().getIdleTimeout() / (60 * 1000);
        }
    }

      out.write("\r\n<html>\r\n    <head>\r\n        <title>");
      out.print( "Chatbot Configuration for "+wgID);
      out.write("</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-chatbot\"/>\r\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\r\n        <!--<meta name=\"helpPage\" content=\"configure_chatbot_settings.html\"/>-->\r\n\r\n        <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\r\n        <script>\r\n        function restoreKey(name){\r\n            document.text3._key.value = name;\r\n            document.text3.submit();\r\n        }\r\n        </script>\r\n        <script language=\"javascript\">\r\n            function changeImage(image, img) {\r\n                img.src = image;\r\n            }\r\n        </script>\r\n    </head>\r\n    <body>\r\n\r\n      ");

          if(updated){
      
      out.write("\r\n       <div class=\"jive-success\">\r\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n                <tbody>\r\n                    <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\"\r\n                    border=\"0\"></td>\r\n                        <td class=\"jive-icon-label\">\r\n                           Web Chat images have been updated successfully\r\n                        </td></tr>\r\n                </tbody>\r\n            </table>\r\n        </div><br/>\r\n      ");
 } 
      out.write("\r\n\r\n      <!-- Create HTML Code Snippet Table -->\r\n    <p>Use the form below to configure the messages that the chatbot will send to users using standard XMPP clients.</p>\r\n\r\n        <form name=\"text\" action=\"workgroup-chatbot.jsp\" method=\"post\">\r\n        <fieldset>\r\n            <legend>Chatbot activation</legend>\r\n            <div>\r\n            <p>\r\n            Enable or disable the chatbot for this workgroup.\r\n            </p>\r\n            <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n            <tbody>\r\n                <tr>\r\n                    <td width=\"1%\">\r\n                        <input type=\"radio\" name=\"enabled\" value=\"true\" id=\"rb01\" ");
      out.print( ((enabled) ? "checked" : "") );
      out.write(">\r\n                    </td>\r\n                    <td width=\"99%\">\r\n                        <label for=\"rb01\"><b>Enabled</b></label> - Users will be attended by the chatbot.\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td width=\"1%\">\r\n                        <input type=\"radio\" name=\"enabled\" value=\"false\" id=\"rb02\" ");
      out.print( ((!enabled) ? "checked" : "") );
      out.write(">\r\n                    </td>\r\n                    <td width=\"99%\">\r\n                        <label for=\"rb02\"><b>Disabled</b></label> - Messages sent to the workgroup will be ignored.\r\n                    </td>\r\n                </tr>\r\n            </tbody>\r\n            </table>\r\n            </div>\r\n        </fieldset>\r\n          <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\r\n          <br>\r\n          <input type=\"submit\" value=\"Save Settings\" />\r\n        </form>\r\n        <br>\r\n\r\n        <form name=\"text2\" action=\"workgroup-chatbot.jsp\" method=\"post\">\r\n        <fieldset>\r\n            <legend>Idle Session Settings</legend>\r\n            <div>\r\n            <p>\r\n            Sessions that haven't been used for a while will be removed.\r\n            </p>\r\n            <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n            <tbody>\r\n                <tr>\r\n                    <td width=\"30%\" nowrap>\r\n                        Remove sessions after they have been idle for\r\n                    </td>\r\n                    <td width=\"70%\">\r\n                        <input type=\"text\" name=\"timeout\" size=\"15\" maxlength=\"50\" value=\"");
      out.print( timeout );
      out.write("\"> minutes\r\n                    </td>\r\n                </tr>\r\n            </tbody>\r\n            </table>\r\n            </div>\r\n        </fieldset>\r\n          <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\r\n          <br>\r\n          <input type=\"submit\" value=\"Save Settings\" />\r\n        </form>\r\n        <br>\r\n\r\n        <!-- Text Settings -->\r\n        <form name=\"text3\" action=\"workgroup-chatbot.jsp\" method=\"post\">\r\n        <fieldset>\r\n            <legend>Chatbot Text Settings</legend>\r\n            <div>\r\n            <table  class=\"jive-table\"  width=\"100%\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n            <tr>\r\n            <th>Event</th><th>Current Message</th><th colspan=\"2\">Default Message</th>\r\n            </tr>\r\n            ");

                Iterator iter = chatSettings.getChatSettingsByType(ChatSettings.SettingType.bot_settings).iterator();
                while(iter.hasNext()){
                    ChatSetting setting = (ChatSetting)iter.next();
            
      out.write("\r\n            <tr valign=\"top\">\r\n                 <td width=\"25%\">");
      out.print( setting.getLabel() );
      out.write("</td>\r\n                 <td><textarea cols=\"25\" rows=\"5\" name=\"");
      out.print( setting.getKey() );
      out.write('"');
      out.write('>');
      out.print( setting.getValue() );
      out.write("</textarea></td>\r\n                 <td>");
      out.print( setting.getDefaultValue() );
      out.write("</td>\r\n                 <td><input type=\"submit\" name=\"restore\" value=\"Restore Defaults\" onClick=\"restoreKey('");
      out.print(setting.getKey());
      out.write("');\"></td>\r\n                 <input type=\"hidden\" name=\"key\" value=\"");
      out.print( setting.getKey() );
      out.write("\" />\r\n            </tr>\r\n            ");
 } 
      out.write("\r\n            </table>\r\n            </div>\r\n        </fieldset>\r\n            <br>\r\n          <input type=\"hidden\" name=\"_key\" />\r\n          <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\r\n          <input type=\"submit\" name=\"saveText\" value=\"Update Text Settings\" />\r\n        </form>\r\n\r\n    </body>\r\n</html>\r\n");

    session.setAttribute("workgroup", wgID);

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
