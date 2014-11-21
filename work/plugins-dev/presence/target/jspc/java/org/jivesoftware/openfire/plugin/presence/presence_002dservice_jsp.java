package org.jivesoftware.openfire.plugin.presence;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.util.*;
import org.jivesoftware.openfire.plugin.PresencePlugin;

public final class presence_002dservice_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			"error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n");

    PresencePlugin plugin = (PresencePlugin)XMPPServer.getInstance().getPluginManager().getPlugin("presence");

    // Get parameters
    boolean save = request.getParameter("save") != null;
    boolean success = request.getParameter("success") != null;
	boolean presencePublic = ParamUtils.getBooleanParameter(request, "presencePublic");
    String unavailableStatus = ParamUtils.getParameter(request, "presenceUnavailableStatus", false);
    if (unavailableStatus == null) {
        unavailableStatus = plugin.getUnavailableStatus();
    }


    // Handle a save
    if (save) {
        plugin.setPresencePublic(presencePublic);
        plugin.setUnavailableStatus(unavailableStatus);
        response.sendRedirect("presence-service.jsp?success=true");
        return;
    }

    presencePublic = plugin.isPresencePublic();

      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>Presence Service</title>\r\n        <meta name=\"pageID\" content=\"presence-service\"/>\r\n    </head>\r\n    <body>\r\n\r\n<div class=\"information\">\r\n    ");

        String serverName = XMPPServer.getInstance().getServerInfo().getXMPPDomain();
        int port = JiveGlobals.getXMLProperty("adminConsole.port", -1);
        int securePort = JiveGlobals.getXMLProperty("adminConsole.securePort", -1);
        boolean secureOnly = (port == -1);

    
      out.write("\r\n\r\n    Presence Service URL:<br>\r\n    <tt>");
      out.print( secureOnly?"https":"http");
      out.write(':');
      out.write('/');
      out.write('/');
      out.print(serverName);
      out.write(':');
      out.print( secureOnly?securePort:port);
      out.write("/plugins/presence/status</tt>\r\n    <br><br>\r\n    Example:<br>\r\n    <tt>");
      out.print( secureOnly?"https":"http");
      out.write(':');
      out.write('/');
      out.write('/');
      out.print(serverName);
      out.write(':');
      out.print( secureOnly?securePort:port);
      out.write("/plugins/presence/status?jid=admin@");
      out.print(serverName);
      out.write("</tt>  \r\n\r\n\r\n</div>\r\n\r\n<p>\r\nUse the form below to configure user presence visibility. By default, user\r\npresence should only be visible to those users that are authorized.<br>\r\n</p>\r\n\r\n");
  if (success) { 
      out.write("\r\n\r\n    <div class=\"jive-success\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\r\n        <td class=\"jive-icon-label\">\r\n            Presence service properties edited successfully.\r\n        </td></tr>\r\n    </tbody>\r\n    </table>\r\n    </div><br>\r\n");
 } 
      out.write("\r\n\r\n<form action=\"presence-service.jsp?save\" method=\"post\">\r\n\r\n<fieldset>\r\n    <legend>Presence visibility</legend>\r\n    <div>\r\n    <p>\r\n    For security reasons, users control which users are authorized to see their presence. However,\r\n    it is posible to configure the service so that anyone has access to all presence information.\r\n    Use this option with caution.\r\n    </p>\r\n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <tbody>\r\n        <tr>\r\n            <td width=\"1%\">\r\n            <input type=\"radio\" name=\"presencePublic\" value=\"true\" id=\"rb01\"\r\n             ");
      out.print( ((presencePublic) ? "checked" : "") );
      out.write(">\r\n            </td>\r\n            <td width=\"99%\">\r\n                <label for=\"rb01\"><b>Anyone</b> - Anyone may get presence information.</label>\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td width=\"1%\">\r\n            <input type=\"radio\" name=\"presencePublic\" value=\"false\" id=\"rb02\"\r\n             ");
      out.print( ((!presencePublic) ? "checked" : "") );
      out.write(">\r\n            </td>\r\n            <td width=\"99%\">\r\n                <label for=\"rb02\"><b>Subscribed</b> - Presence information is only visibile to authorized users.</label>\r\n            </td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n</fieldset>\r\n\r\n<br>\r\n    \r\n<fieldset>\r\n    <legend>Plain Text 'Unavailable' Status Message</legend>\r\n    <div>\r\n    <p>\r\n    In &quot;text&quot; mode the status message for unavailable users is &quot;Unavailable&quot;\r\n    by default. It is possible to change the unavailable status message by setting this property.\r\n    </p>\r\n    <p>\r\n        <input type=\"text\" name=\"presenceUnavailableStatus\" value=\"");
      out.print( unavailableStatus );
      out.write("\">\r\n    </p>\r\n    </div>\r\n</fieldset>\r\n\r\n<br><br>\r\n\r\n<input type=\"submit\" value=\"Save Properties\">\r\n</form>\r\n\r\n</body>\r\n</html>");
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
