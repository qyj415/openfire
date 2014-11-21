package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupAdminManager;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSettingsManager;

public final class workgroup_002dsound_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\n');
      out.write('\n');
      out.write('\n');

    // Get parameters
    String wgID = ParamUtils.getParameter(request, "wgID");
    WorkgroupAdminManager workgroupAdminManager = new WorkgroupAdminManager();
    workgroupAdminManager.init(pageContext);

    boolean updated = ParamUtils.getBooleanParameter(request, "updated", false);

    JID workgroupJID = new JID(wgID);

    final ChatSettingsManager chatSettingsManager = ChatSettingsManager.getInstance();
    Workgroup workgroup = WorkgroupManager.getInstance().getWorkgroup(workgroupJID);
    String incomingMessage = workgroup.getProperties().getProperty("incomingSound");
    String outgoingMessage = workgroup.getProperties().getProperty("outgoingSound");


      out.write("\n<html>\n    <head>\n        <title>");
      out.print( "Sound Settings For " + wgID);
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-sound-settings\"/>\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\n        <!--<meta name=\"helpPage\" content=\"index.html\"/>-->\n\n        <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\n    </head>\n    <body>\n\n      ");

          if (updated) {
      
      out.write("\n       <div class=\"jive-success\">\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                <tbody>\n                    <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\"\n                                                   border=\"0\"></td>\n                        <td class=\"jive-icon-label\">\n                           Sounds have been updated successfully\n                        </td></tr>\n                </tbody>\n            </table>\n        </div><br/>\n      ");
 } 
      out.write("\n\n        <br/><br/>\n\n  <form name=\"f\" action=\"uploadSounds.jsp\" enctype=\"multipart/form-data\" method=\"post\">\n        <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\"/>\n        <!-- Create Image Table -->\n        <table width=\"600\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n        <tr>\n            <th colspan=\"3\">Sound Configuration</th>\n        </tr>\n\n        <tr>\n          <td>Incoming Message Sound:</td>\n             <td width=\"1%\" nowrap>\n                  <input type=\"file\" name=\"incomingSound\" size=\"40\"/>\n                </td>\n              ");
 if(ModelUtil.hasLength(incomingMessage)){ 
      out.write("\n              <td><a href=\"getsound?workgroup=");
      out.print( wgID );
      out.write("&action=incoming\">Play Sound</a></td>\n              ");
 } 
      out.write("\n        </tr>\n        <tr>\n            <td>\n              Message Sent Sound:\n            </td>\n                <td width=\"1%\" nowrap>\n                    <input type=\"file\" name=\"outgoingSound\" size=\"40\"/>\n                </td>\n            ");
 if(ModelUtil.hasLength(outgoingMessage)){ 
      out.write("\n              <td><a href=\"getsound?workgroup=");
      out.print( wgID );
      out.write("&action=outgoing\">Play Sound</a></td>\n              ");
 } 
      out.write("\n         </tr>\n                    <tr>\n                        <td colspan=\"2\" align=\"left\">\n                            <input type=\"submit\" value=\"Update Sounds\">\n                        </td><td>&nbsp;</td>\n                    </tr>\n                </table>\n    </form>\n    </body>\n</html>\n");

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
