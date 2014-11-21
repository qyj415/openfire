package org.jivesoftware.openfire.plugin.gojara;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.gojara.sessions.GatewaySession;
import org.jivesoftware.openfire.plugin.gojara.sessions.TransportSessionManager;
import org.jivesoftware.openfire.plugin.gojara.sessions.GojaraAdminManager;
import org.jivesoftware.openfire.plugin.gojara.database.SessionEntry;
import org.jivesoftware.openfire.plugin.gojara.utils.JspHelper;
import org.jivesoftware.openfire.XMPPServer;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;

public final class gojara_002dsessionDetails_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\t\r\n\r\n\r\n\r\n\r\n\r\n");

	TransportSessionManager transportManager = TransportSessionManager.getInstance();
	GojaraAdminManager gojaraAdminManager = GojaraAdminManager.getInstance();
	String username = request.getParameter("username");

      out.write("\r\n<html>\r\n<head>\r\n<title>GatewaySession Details for: &emsp; ");
      out.print(username);
      out.write("</title>\r\n<meta name=\"pageID\" content=\"gojaraSessions\" />\r\n<script language='JavaScript'>\r\n\tchecked = false;\r\n\tfunction checkedAll () {\r\n\t\tif (checked == false){checked = true}else{checked = false}\r\n\t\tfor (var i = 0; i < document.getElementById('gojara-sessDetailsUnregister').elements.length; i++) {\r\n\t\t\tdocument.getElementById('gojara-sessDetailsUnregister').elements[i].checked = checked;\r\n\t\t}\r\n\t}\r\n</script>\r\n</head>\r\n<body>\r\n\t\r\n\t");
 if (!gojaraAdminManager.areGatewaysConfigured()) {
      out.write("\r\n\t\t<h2 align=\"center\"><a href=\"gojara-gatewayStatistics.jsp\">Warning: Not all Gateways are configured for admin usage. This means unregistrations will not be properly executed.<br/>\r\n\t\t Please configure admin_jid = gojaraadmin@");
      out.print( XMPPServer.getInstance().getServerInfo().getXMPPDomain() );
      out.write("  in Spectrum2 transport configuration.</a></h2>\r\n\t ");
 } 
      out.write("\r\n\t\r\n\t");

		if (request.getParameter(username) != null) {
			String[] unregister = request.getParameterValues(username);
	
      out.write("\r\n\t\t<br>\r\n\t\t<br>\r\n\t\t<div align=\"center\">\r\n\t\t<ul style=\"list-style: none;padding:0;margin:0;\">\r\n\t\t");

			for (String key : unregister) {
		
      out.write("\r\n\t\r\n\t\t\t<li>");
      out.print(gojaraAdminManager.unregisterUserFrom(key, username));
      out.write("</li>\r\n\t\t");

			}
		
      out.write("\r\n\t\t</ul></div>\r\n\t\t<br>\r\n\t\t<br>\r\n\t\r\n\t\t");

		}
		ArrayList<GatewaySession> userconnections = transportManager.getConnectionsFor(username);
		if (userconnections.isEmpty()) {
	
      out.write("\r\n\t<h2 align=\"center\">User has no active sessions</h2>\r\n\t");

		} else {
	
      out.write("\r\n\t\t<h1 align=\"center\">Active Sessions:</h1>\r\n\t<br>\r\n\t<div class=\"jive-table\">\r\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n\t\t\t<thead>\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<th nowrap>Resource</th>\r\n\t\t\t\t\t<th nowrap>Login Time</th>\r\n\t\t\t\t</tr>\r\n\t\t\t</thead>\r\n\t\t\t<tbody>\r\n\t\t\t\t");

					for (GatewaySession gws : userconnections) {
				
      out.write("\r\n\t\t\t\t<tr class=\"jive-odd\">\r\n\t\t\t\t\t<td>");
      out.print(gws.getTransport());
      out.write("</td>\r\n\t\t\t\t\t<td title=\"");
      out.print(JspHelper.dateDifferenceHelper(gws.getLastActivity()) );
      out.write('"');
      out.write('>');
      out.print(gws.getLastActivity());
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t");

					}
				
      out.write("\r\n\t\t\t</tbody>\r\n\t\t</table>\r\n\t</div>\r\n\r\n\t");

		}
	
      out.write("\r\n\r\n\t<br>\r\n\t<hr>\r\n\t\t<h1 align=\"center\">Associated Registrations:</h1>\r\n\t<br>\r\n\t<form name=\"unregister-form\" id=\"gojara-sessDetailsUnregister\"\r\n\t\tmethod=\"POST\">\r\n\t\t<div class=\"jive-table\">\r\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n\t\t\t\t<thead>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<th nowrap>User Name:</th>\r\n\t\t\t\t\t\t<th nowrap>Resource:</th>\r\n\t\t\t\t\t\t<th nowrap>Active?</th>\r\n\t\t\t\t\t\t<th nowrap>Admin Configured?</th>\r\n\t\t\t\t\t\t<th nowrap>Last login was at:</th>\r\n\t\t\t\t\t\t<th nowrap>Unregister?</th>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</thead>\r\n\t\t\t\t<tbody>\r\n\t\t\t\t\t");

						ArrayList<SessionEntry> registrations = transportManager.getRegistrationsFor(username);
					
      out.write("\r\n\t\t\t\t\t");

						for (SessionEntry registration : registrations) {
					
      out.write("\r\n\t\t\t\t\t<tr class=\"jive-odd\">\r\n\t\t\t\t\t\t<td><a\r\n\t\t\t\t\t\t\thref=\"/user-properties.jsp?username=");
      out.print(registration.getUsername());
      out.write('"');
      out.write('>');
      out.print(registration.getUsername());
      out.write("</a></td>\r\n\t\t\t\t\t\t<td>");
      out.print(registration.getTransport());
      out.write("</td>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t");

								if (transportManager.isTransportActive(registration.getTransport())) {
							
      out.write(" <img alt=\"Yes\" src=\"/images/success-16x16.gif\"> ");

							 	} else {
							 
      out.write(" <img alt=\"No\" src=\"/images/error-16x16.gif\" title=\"Sending unregister to inactive transport will result in NOT UNREGISTERING the registration.\"> ");

							 	}
							 
      out.write("\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t");
 if (gojaraAdminManager.isGatewayConfigured(registration.getTransport())) { 
      out.write("\r\n\t\t\t\t\t\t<img alt=\"Yes\" src=\"/images/success-16x16.gif\"> \r\n\t\t\t\t\t\t");
 	} else { 
      out.write("\r\n\t\t\t\t\t\t <img alt=\"No\" src=\"/images/error-16x16.gif\" title=\"Sending unregister to unconfigured transport will result in NOT UNREGISTERING the registration.\">\r\n\t\t\t\t\t\t  ");
 }
      out.write("\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td\r\n\t\t\t\t\t\t\ttitle=\"");
      out.print(JspHelper.dateDifferenceHelper(registration.getLast_activityAsDate()));
      out.write('"');
      out.write('>');
      out.print(registration.getLast_activityAsDate());
      out.write("</td>\r\n\t\t\t\t\t\t<td><input type=\"checkbox\"\r\n\t\t\t\t\t\t\tname=\"");
      out.print(registration.getUsername());
      out.write("\"\r\n\t\t\t\t\t\t\tvalue=\"");
      out.print(registration.getTransport());
      out.write("\"></td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t");

						}
					
      out.write("\r\n\t\t\t\t</tbody>\r\n\t\t\t</table>\r\n\t\t</div>\r\n\t\t<div align=\"center\"><br>\r\n\t\t\t<input type=\"button\" value=\"check/uncheck all\" onclick='checkedAll();'>\r\n\t\t<br>\r\n\t\t\t<input type=\"submit\" value=\"Unregister\">\r\n\t\t</div>\r\n\t</form>\r\n</body>\r\n</html>\r\n\r\n");
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
