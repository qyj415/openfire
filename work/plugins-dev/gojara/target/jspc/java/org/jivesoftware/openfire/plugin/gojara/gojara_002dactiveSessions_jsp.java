package org.jivesoftware.openfire.plugin.gojara;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.gojara.sessions.TransportSessionManager;
import org.jivesoftware.openfire.plugin.gojara.sessions.GojaraAdminManager;
import org.jivesoftware.openfire.plugin.gojara.sessions.GatewaySession;
import org.jivesoftware.openfire.plugin.gojara.utils.JspHelper;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import org.jivesoftware.openfire.XMPPServer;

public final class gojara_002dactiveSessions_jsp extends org.apache.jasper.runtime.HttpJspBase
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
	Map<String, String> sortParams = new HashMap<String, String>();
	if (request.getParameter("sortby") != null && request.getParameter("sortorder") != null) {
		sortParams.put("sortby", request.getParameter("sortby"));
		sortParams.put("sortorder", request.getParameter("sortorder"));
	} else {
		sortParams.put("sortby", "transport");
		sortParams.put("sortorder", "ASC");
	}

      out.write("\r\n<html>\r\n<head>\r\n<title>Gateway Sessions</title>\r\n\r\n<meta name=\"pageID\" content=\"gojaraSessions\" />\r\n</head>\r\n<body>\r\n\t <div align=\"center\">\r\n\t");
 if (!gojaraAdminManager.areGatewaysConfigured()) {
      out.write("\r\n\t\t<h2><a href=\"gojara-gatewayStatistics.jsp\">Warning: Not all Gateways are configured for admin usage. This means session details may be inaccurate or not logged at all.<br/>\r\n\t\t Please configure admin_jid = gojaraadmin@");
      out.print( XMPPServer.getInstance().getServerInfo().getXMPPDomain() );
      out.write(" in Spectrum2 transport configuration.</a></h2><br/>\r\n\t ");
 } 
      out.write("\r\n\t<h4>\r\n\t\tCurrent number of active Gateway Sessions: &emsp;\r\n\t\t<b style=\"font-size:150%\">");
      out.print( transportManager.getNumberOfActiveSessions() );
      out.write("</b>\r\n\t</h4>\r\n\t<br>\r\n\t");

		Map<String, Map<String, Long>> sessions = transportManager.getSessions();
		for (String transport : sessions.keySet()) {
	
      out.write('\r');
      out.write('\n');
      out.write('	');
      out.print(transport.substring(0, 10));
      out.write("... :\r\n\t<b style=\"font-size:150%\">");
      out.print(sessions.get(transport).size());
      out.write("</b> &emsp;\r\n\t");

		}
	
      out.write("</div>\r\n\t<br>\r\n\t<br>\r\n\t");

		//pagination logic
		//get all records, we limit these later as we have to sort them first
		ArrayList<GatewaySession> gwSessions = transportManager
				.getSessionsSorted(sortParams.get("sortby"), sortParams.get("sortorder"));
		
		int numOfSessions = gwSessions.size();
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
	
      out.write("\r\n\t<p>\r\n\t\tPages: [\r\n\t\t");

		for (int i = 1; i <= numOfPages; i++) {
		
      out.write("\r\n\t\t");
      out.print("<a href=\"gojara-activeSessions.jsp?page=" + i + "&sortby=" + sortParams.get("sortby") + "&sortorder="
						+ sortParams.get("sortorder") + "\" class=\"" + (current_page == i ? "jive-current" : "") + "\">" + i
						+ "</a>");
      out.write("\r\n\t\t");

			}
		
      out.write("\r\n\t\t]\r\n\t</p>\r\n\t<div class=\"jive-table\">\r\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n\t\t\t<thead>\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<th nowrap>#</th>\r\n\t\t\t\t\t<th nowrap>");
      out.print(JspHelper.sortingHelperSessions("username", sortParams));
      out.write("</th>\r\n\t\t\t\t\t<th nowrap>");
      out.print(JspHelper.sortingHelperSessions("transport", sortParams));
      out.write("</th>\r\n\t\t\t\t\t<th nowrap>");
      out.print(JspHelper.sortingHelperSessions("loginTime", sortParams));
      out.write("</th>\r\n\t\t\t\t</tr>\r\n\t\t\t</thead>\r\n\t\t\t<tbody>\r\n\t\t\t\t");
 if (numOfSessions == 0) { 
      out.write("\r\n\t\t\t\t<tr><td colspan=\"4\">No active Sessions</td></tr>\r\n\t\t\t\t");
 } else {
					int show_number = 1 + current_index;
					for (GatewaySession gwsession : gwSessions.subList(current_index, next_items)) {
				
      out.write("\r\n\t\t\t\t<tr class=\"jive-odd\">\r\n\t\t\t\t\t<td>");
      out.print( show_number);
      out.write("</td>\r\n\t\t\t\t\t<td><a\r\n\t\t\t\t\t\thref=\"gojara-sessionDetails.jsp?username=");
      out.print(gwsession.getUsername());
      out.write("\"\r\n\t\t\t\t\t\ttitle=\"Session Details for ");
      out.print(gwsession.getUsername());
      out.write('"');
      out.write('>');
      out.print(gwsession.getUsername());
      out.write("</a></td>\r\n\t\t\t\t\t<td>");
      out.print(gwsession.getTransport());
      out.write("</td>\r\n\t\t\t\t\t<td\r\n\t\t\t\t\t\ttitle=\"");
      out.print(JspHelper.dateDifferenceHelper(gwsession.getLastActivity()));
      out.write('"');
      out.write('>');
      out.print(gwsession.getLastActivity());
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t");

					show_number++;
					}}
				
      out.write("\r\n\t\t\t</tbody>\r\n\t\t</table>\r\n\t</div>\r\n\t<br>\r\n\t<p>\r\n\t\tPages: [\r\n\t\t");

		for (int i = 1; i <= numOfPages; i++) {
	
      out.write("\r\n\t\t");
      out.print("<a href=\"gojara-activeSessions.jsp?page=" + i + "&sortby=" + sortParams.get("sortby") + "&sortorder="
						+ sortParams.get("sortorder") + "\" class=\"" + (current_page == i ? "jive-current" : "") + "\">" + i
						+ "</a>");
      out.write("\r\n\t\t");

			}
		
      out.write("\r\n\t\t]\r\n\t</p>\r\n</body>\r\n</html>");
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
