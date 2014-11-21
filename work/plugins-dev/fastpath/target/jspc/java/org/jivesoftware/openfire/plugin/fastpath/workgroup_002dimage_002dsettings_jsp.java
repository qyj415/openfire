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
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSettings;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSettingsManager;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSetting;

public final class workgroup_002dimage_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    JID workgroupJID = new JID(wgID);

    final ChatSettingsManager chatSettingsManager = ChatSettingsManager.getInstance();
    Workgroup workgroup = WorkgroupManager.getInstance().getWorkgroup(workgroupJID);
    ChatSettings chatSettings = chatSettingsManager.getChatSettings(workgroup);


      out.write("\r\n<html>\r\n    <head>\r\n        <title>");
      out.print( "WebChat Images for "+wgID);
      out.write("</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-image-settings\"/>\r\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\r\n        <!--<meta name=\"helpPage\" content=\"add_or_change_form_images.html\"/>-->\r\n\r\n        <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\r\n        <script>\r\n        function restoreKey(name){\r\n            document.text._key.value = name;\r\n            document.text.submit();\r\n        }\r\n        </script>\r\n        <script language=\"javascript\">\r\n            function changeImage(image, img) {\r\n                img.src = image;\r\n            }\r\n        </script>\r\n    </head>\r\n    <body>\r\n\r\n      ");

          if(updated){
      
      out.write("\r\n       <div class=\"jive-success\">\r\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n                <tbody>\r\n                    <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\"\r\n                    border=\"0\"></td>\r\n                        <td class=\"jive-icon-label\">\r\n                           Web UI images have been updated successfully\r\n                        </td></tr>\r\n                </tbody>\r\n            </table>\r\n        </div><br/>\r\n      ");
 } 
      out.write("\r\n\r\n\r\n\r\n        <br/><br/>\r\n\r\n  <form name=\"f\" action=\"upload.jsp\" enctype=\"multipart/form-data\" method=\"post\">\r\n        <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\r\n        <!-- Create Image Table -->\r\n        <table width=\"75%\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n        <tr>\r\n            <th colspan=\"3\">Web UI Image Configuration</th>\r\n        </tr>\r\n                    ");
      java.util.LinkedHashMap images = null;
      synchronized (_jspx_page_context) {
        images = (java.util.LinkedHashMap) _jspx_page_context.getAttribute("images", PageContext.PAGE_SCOPE);
        if (images == null){
          images = new java.util.LinkedHashMap();
          _jspx_page_context.setAttribute("images", images, PageContext.PAGE_SCOPE);
        }
      }
      out.write("\r\n                    ");

                        Iterator imagesIter = chatSettings
                                .getChatSettingsByType(ChatSettings.SettingType.image_settings)
                                .iterator();
                        while (imagesIter.hasNext()) {
                            ChatSetting setting = (ChatSetting)imagesIter.next();
                            String key = setting.getKey().toString();
                            String label = setting.getLabel();
                            String description = setting.getDescription();
                            if (description == null) {
                                continue;
                            }
                    
      out.write("\r\n\r\n      <tr valign=\"top\">\r\n        <td bgcolor=\"#FFFFFF\" width=\"40%\"><b>");
      out.print( label );
      out.write(":</b><br/><span class=\"jive-description\">");
      out.print( description );
      out.write("</span></td>\r\n        <td bgcolor=\"#FFFFFF\">\r\n            <table cellspacing=\"3\" cellpadding=\"2\" border=\"0\" width=\"100%\">\r\n              <tr>\r\n                <td width=\"1%\" nowrap>\r\n                  <input type=\"file\" name=\"");
      out.print( key );
      out.write("\" onchange=\"changeImage(document.f.");
      out.print(key);
      out.write(".value, document.f.");
      out.print( key );
      out.write("image);\" size=\"40\"/>\r\n                </td>\r\n\r\n              </tr>\r\n              <tr>\r\n\r\n              <td>\r\n                  <img name=\"");
      out.print( key );
      out.write("image\" src=\"getimage?imageName=");
      out.print( key );
      out.write("\"/>\r\n                </td>\r\n\r\n              </tr>\r\n            </table>\r\n        </td>\r\n      </tr>\r\n\r\n     ");
 } 
      out.write("\r\n                    <tr>\r\n                        <td colspan=\"2\" align=\"left\">\r\n                            <input type=\"submit\" value=\"Update Images\">\r\n                        </td>\r\n                    </tr>\r\n                </table>\r\n    </form>\r\n    </body>\r\n</html>\r\n");

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
