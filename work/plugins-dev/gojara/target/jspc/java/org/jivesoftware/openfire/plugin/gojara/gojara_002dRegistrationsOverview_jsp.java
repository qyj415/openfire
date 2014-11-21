package org.jivesoftware.openfire.plugin.gojara;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.gojara.sessions.TransportSessionManager;
import org.jivesoftware.openfire.plugin.gojara.sessions.GojaraAdminManager;
import org.jivesoftware.openfire.plugin.gojara.database.SessionEntry;
import org.jivesoftware.openfire.plugin.gojara.utils.JspHelper;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import org.jivesoftware.openfire.XMPPServer;

public final class gojara_002dRegistrationsOverview_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

	TransportSessionManager transportManager = TransportSessionManager.getInstance();
	GojaraAdminManager gojaraAdminManager = GojaraAdminManager.getInstance();

	//Helper object for generation of sorting links, column restriction is done in DatabaseManager
	// we need this object so we cann pass the parameters around to our functions
	Map<String, String> sortParams = new HashMap<String, String>();
	if (request.getParameter("sortby") != null && request.getParameter("sortorder") != null) {
		sortParams.put("sortby", request.getParameter("sortby"));
		sortParams.put("sortorder", request.getParameter("sortorder"));
	} else {
		sortParams.put("sortby", "username");
		sortParams.put("sortorder", "ASC");
	}

      out.write("\r\n\r\n<html>\r\n<head>\r\n<title>Overview of existing Registrations</title>\r\n<meta name=\"pageID\" content=\"gojaraRegistrationAdministration\" />\r\n</head>\r\n<body>\r\n\t<div align=\"center\">\r\n\t<ul style=\"list-style: none;padding:0;margin:0;\">\r\n\t");

		//do unregisters if supplied, we do them here because we generate output that should be displayed
		if (request.getParameterMap() != null) {
			String uninteresting_params = "sortorder sortby page";
			for (Object key : request.getParameterMap().keySet()) {
				if (uninteresting_params.contains(key.toString())) {
					continue;
				}
				String[] uservalues = request.getParameterValues(key.toString());
				for (String transport : uservalues) {
	
      out.write("\r\n\t<li>");
      out.print(gojaraAdminManager.unregisterUserFrom(transport, key.toString()));
      out.write("</li>\r\n\t");

		}
			}
		}
	
      out.write("\r\n\t</ul>\r\n\t</div>\r\n\r\n\r\n\t<div align=\"center\">\r\n\t");
 if (!gojaraAdminManager.areGatewaysConfigured()) {
      out.write("\r\n\t\t<h2><a href=\"gojara-gatewayStatistics.jsp\">Warning: Not all Gateways are configured for admin usage. This means unregistrations will not be properly executed.<br/>\r\n\t\t Please configure admin_jid = gojaraadmin@");
      out.print( XMPPServer.getInstance().getServerInfo().getXMPPDomain() );
      out.write("  in Spectrum2 transport configuration.</a></h2>\r\n\t ");
 } 
      out.write("\r\n\t\t<h5>Logintime 1970 means User did register but never logged in,\r\n\t\t\tpropably because of invalid credentials.</h5>\r\n\t\t\t<br>\r\n\t\t\t<br>\r\n\t\tRegistrations total: <b style=\"font-size:150%\">");
      out.print(transportManager.getNumberOfRegistrations());
      out.write("</b><br>\r\n\t</div>\r\n\t<br>\r\n\t");

		//pagination logic
		//get all records, we limit these later. Not all databes support limiting queries so we need to do it the bad way
		ArrayList<SessionEntry> registrations = transportManager.getAllRegistrations(sortParams.get("sortby"),
				sortParams.get("sortorder"));
		
		int numOfSessions = registrations.size();
		// 100 entries is exactly 1 page, 101 entries is 2 pages
		int numOfPages = numOfSessions % 100 == 0 ? (numOfSessions / 100) : (1 + (numOfSessions / 100));
		//lets check for validity if page parameter is supplied, set it to 1 if not in valid range 
		int current_page = 1;
		if (request.getParameter("page") != null) {
			try {
				current_page = Integer.parseInt(request.getParameter("page"));
				if (current_page < 1 || current_page > numOfPages)
					current_page = 1;
			} catch (Exception e) {
			}
		}
		// we now know current_page is in valid range from supplied parameter or standard.
		// this will be our sublist starting index, 0, 100, 200 ... 
		int current_index = (current_page -1)* 100;
		//ending index, 100, 200 etc, when next items > numOfSessions we have reached last page, set proper index so we have no out of bounds
		//ending index is excluded, so 0-100 is 0-99, e.g. item 1-100
		int next_items = current_index + 100;
		if (next_items > numOfSessions)
			next_items = numOfSessions;
	
      out.write("\r\n\t<p>\r\n\t\t<br> Pages: [\r\n\t\t");

			for (int i = 1; i <= numOfPages; i++) {
		
      out.write("\r\n\t\t");
      out.print("<a href=\"gojara-RegistrationsOverview.jsp?page=" + i + "&sortby=" + sortParams.get("sortby") + "&sortorder="
						+ sortParams.get("sortorder") + "\" class=\"" + (current_page  == i ? "jive-current" : "") + "\">" + i
						+ "</a>" );
      out.write("\r\n\t\t");

			}
		
      out.write("\r\n\t\t]\r\n\t</p>\r\n\t<form name=\"unregister-form\" id=\"gojara-RegOverviewUnregister\"\r\n\t\tmethod=\"POST\">\r\n\t\t<div class=\"jive-table\">\r\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n\t\t\t\t<thead>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<th nowrap>#</th>\r\n\t\t\t\t\t\t<th nowrap>");
      out.print(JspHelper.sortingHelperRegistrations("username", sortParams));
      out.write("</th>\r\n\t\t\t\t\t\t<th nowrap>");
      out.print(JspHelper.sortingHelperRegistrations("transport", sortParams));
      out.write("</th>\r\n\t\t\t\t\t\t<th nowrap>Active?</th>\r\n\t\t\t\t\t\t<th nowrap>Admin Configured?</th>\r\n\t\t\t\t\t\t<th nowrap>");
      out.print(JspHelper.sortingHelperRegistrations("lastActivity", sortParams));
      out.write("</th>\r\n\t\t\t\t\t\t<th nowrap>Unregister?</th>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</thead>\r\n\t\t\t\t<tbody>\r\n\t\t\t\t\t");
	
						int show_number = 1 + current_index;
						for (SessionEntry registration : registrations.subList(current_index, next_items)) {
					
      out.write("\r\n\t\t\t\t\t<tr class=\"jive-odd\">\r\n\t\t\t\t\t\t<td>");
      out.print( show_number);
      out.write("</td>\r\n\t\t\t\t\t\t<td><a\r\n\t\t\t\t\t\t\thref=\"gojara-sessionDetails.jsp?username=");
      out.print(registration.getUsername());
      out.write("\"\r\n\t\t\t\t\t\t\ttitle=\"Session Details for ");
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

						show_number++;
						}
					
      out.write("\r\n\t\t\t\t</tbody>\r\n\t\t\t</table>\r\n\t\t</div>\r\n\t\t<p>\r\n\t\t\tPages: [\r\n\t\t\t");

			for (int i = 1; i <= numOfPages; i++) {
		
      out.write("\r\n\t\t\t");
      out.print("<a href=\"gojara-RegistrationsOverview.jsp?page=" + i + "&sortby=" + sortParams.get("sortby") + "&sortorder="
						+ sortParams.get("sortorder") + "\" class=\"" + (current_page == i ? "jive-current" : "") + "\">" + i
						+ "</a>");
      out.write("\r\n\t\t\t");

				}
			
      out.write("\r\n\t\t\t]\r\n\t\t</p>\r\n\t\t<br>\r\n\t\t<div align=\"center\">\r\n\t\t\t<input type=\"submit\" value=\"Unregister\">\r\n\t\t</div>\r\n\t</form>\r\n</body>\r\n</html>");
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
