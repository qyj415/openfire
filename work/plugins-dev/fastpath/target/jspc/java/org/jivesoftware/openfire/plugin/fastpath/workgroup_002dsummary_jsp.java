package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.*;
import java.util.Iterator;
import org.jivesoftware.xmpp.workgroup.WorkgroupResultFilter;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupAdminManager;
import org.jivesoftware.openfire.muc.MultiUserChatService;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.cluster.ClusterManager;
import org.jivesoftware.util.cache.CacheFactory;
import org.jivesoftware.openfire.container.GetAdminConsoleInfoTask;
import org.jivesoftware.util.StringUtils;
import org.jivesoftware.util.Base64;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.AuthToken;

public final class workgroup_002dsummary_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/check-cluster.jspf");
  }

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

      out.write("\r\n\r\n\r\n\r\n<!-- Define Administration Bean -->\r\n");

    WorkgroupAdminManager webManager = new WorkgroupAdminManager();
    webManager.init(pageContext);

      out.write("\r\n\r\n");

    // Get muc server
    MultiUserChatService mucService = null;
    for (MultiUserChatService service : webManager.getMultiUserChatManager().getMultiUserChatServices()) {
        if (!service.isRoomCreationRestricted()) {
            mucService = service;
        }
    }


      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

    if (!ClusterManager.isSeniorClusterMember()) {

        GetAdminConsoleInfoTask info = (GetAdminConsoleInfoTask) CacheFactory.doSynchronousClusterTask(
                new GetAdminConsoleInfoTask(), ClusterManager.getSeniorClusterMember().toByteArray());

        AuthToken authToken = (AuthToken) session.getAttribute("jive.admin.authToken");

        if (info != null && authToken != null && info.getBindInterface() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(request.isSecure() ? "https" : "http");
            sb.append("://");
            sb.append(info.getBindInterface());
            sb.append(":");
            sb.append(request.isSecure() ? info.getAdminSecurePort() : info.getAdminPort());
            sb.append("/login.jsp?login=true&username=").append(authToken.getUsername());
            sb.append("&secret=").append(StringUtils.hash(info.getAdminSecret()));
            sb.append("&nodeID=")
                    .append(Base64.encodeBytes(XMPPServer.getInstance().getNodeID().toByteArray(), Base64.URL_SAFE));
            sb.append("&url=/plugins/fastpath/workgroup-summary.jsp");

            response.sendRedirect(sb.toString());
            return;
        }
    }

      out.write("\r\n<html>\r\n    <head>\r\n        <title>Workgroup Summary</title>\r\n        <meta name=\"pageID\" content=\"workgroup-summary\"/>\r\n        <!--<meta name=\"helpPage\" content=\"get_around_in_the_admin_console.html\"/>-->\r\n    </head>\r\n    <body>\r\n    <style type=\"text/css\">\r\n        @import \"style/style.css\";\r\n    </style>\r\n");
 if(mucService == null){ 
      out.write("\r\n    <div class=\"warning\">\r\n        Fastpath needs a Group Conference service set up so rooms can be created on the server without restriction. Please set up a Group Conference service with permissions <a href=\"/muc-service-summary.jsp\">here</a>.\r\n    </div>\r\n\r\n");
 } 
      out.write("\r\n\r\n\r\n");

    boolean deleted = ParamUtils.getParameter(request, "deleted") != null;

      out.write('\r');
      out.write('\n');
 if(deleted){
      out.write("\r\n    <div class=\"success\">\r\n       Workgroup has been deleted!\r\n     </div><br>\r\n");
 } 
      out.write('\r');
      out.write('\n');

    int start = ParamUtils.getIntParameter(request, "start", 0);
    int range = ParamUtils.getIntParameter(request, "range", 15);
    webManager.setStart(start);
    webManager.setRange(range);

    int numPages = (int)Math.ceil((double)webManager.getWorkgroupManager().getWorkgroupCount()/(double)range);
    int curPage = (start/range) + 1;

      out.write("\r\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n  <tr>\r\n    <td colspan=\"8\">\r\nBelow is the list of workgroups in the system. A workgroup is an alias for contacting a group of members and is made up of one or more queues.</td>\r\n  </tr>\r\n</table>\r\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n  <tr>\r\n    <td colspan=\"8\" class=\"text\">Total Workgroups: ");
      out.print( webManager.getWorkgroupManager().getWorkgroupCount());
      out.write(".\r\n     ");
 if(webManager.getNumPages() > 1) { 
      out.write("\r\n        Showing ");
      out.print( webManager.getStart() + 1);
      out.write(' ');
      out.write('-');
      out.write(' ');
      out.print( webManager.getStart() + webManager.getRange());
      out.write("\r\n      ");
 } 
      out.write("\r\n      <br/><br/>\r\n      ");
 if(webManager.getNumPages() > 1){ 
      out.write("\r\n        <table border=\"0\" cellpadding=\"3\" cellspacing=\"0\">\r\n        <tr>\r\n          <td colspan=\"8\" class=\"text\">Pages: [\r\n            ");
   for (int pageIndex=0; pageIndex<numPages; pageIndex++) {

            String sep = ((pageIndex+1)<numPages) ? " " : "";

            boolean isCurrent = (pageIndex+1) == curPage;

    
      out.write("\r\n            <a href=\"workgroup-summary.jsp?start=");
      out.print( (pageIndex*range) );
      out.write("\" class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\">\r\n              ");
      out.print(  (pageIndex+1) );
      out.write("\r\n            </a>\r\n            ");
      out.print(  sep );
      out.write("\r\n            ");
   } 
      out.write("]\r\n          </td>\r\n        </tr>\r\n        </table>\r\n      ");
 } 
      out.write("\r\n    </td>\r\n  </tr>\r\n</table>\r\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n  <thead>\r\n    <tr>\r\n      <th nowrap align=\"left\" colspan=\"2\">Name</th>\r\n      <th nowrap>Status</th>\r\n      <th nowrap>Members (Active/Total) </th>\r\n      <th nowrap>Queues</th>\r\n      <th nowrap>Users in Queues</th>\r\n      <th nowrap>Edit</th>\r\n      <th nowrap>Delete</th>\r\n    </tr>\r\n  </thead>\r\n    ");
   // Print the list of workgroups

    WorkgroupResultFilter filter = new WorkgroupResultFilter();

    filter.setStartIndex(start);

    filter.setNumResults(range);

    Iterator workgroups = webManager.getWorkgroupManager().getWorkgroups(filter);

    if (!workgroups.hasNext()) {


      out.write("\r\n    <tr>\r\n      <td align=\"center\" colspan=\"8\">\r\n        <br/>No workgroups --\r\n        <a href=\"workgroup-create.jsp\">create workgroup<a>.\r\n            <br/>\r\n            <br/>\r\n          </a>\r\n        </a>\r\n      </td>\r\n    </tr>\r\n    ");


    }

    int i = start;

    while (workgroups.hasNext()) {

        Workgroup workgroup = (Workgroup)workgroups.next();

        i++;


      out.write("\r\n    <tr class=\"c1\">\r\n      <td width=\"39%\" colspan=\"2\">\r\n        <a href=\"workgroup-queues.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\">\r\n            <b>");
      out.print(  workgroup.getJID().getNode() );
      out.write("</b>\r\n          </a>\r\n        ");
   if (workgroup.getDescription() != null) { 
      out.write("\r\n        <span class=\"jive-description\">\r\n          <br/>\r\n          ");
      out.print(  workgroup.getDescription() );
      out.write("\r\n        </span>\r\n        ");
   } 
      out.write("\r\n      </td>\r\n      <td width=\"10%\" align=\"center\" nowrap>\r\n      <table>\r\n         <tr>\r\n             <td width=\"14\">\r\n                 ");
   if (workgroup.getStatus() == Workgroup.Status.OPEN) { 
      out.write("\r\n                 <img src=\"images/bullet-green-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Workgroup is currently open, active and accepting requests.\" alt=\"\"/>\r\n                 </td><td nowrap>Open\r\n                     ");
   } else if (workgroup.getStatus() == Workgroup.Status.READY) { 
      out.write("\r\n                 <img src=\"images/bullet-yellow-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Workgroup is currently ready to open when a member is available.\" alt=\"\"/>\r\n                 </td><td nowrap>Waiting for member\r\n                         ");
   } else { 
      out.write("\r\n                 <img src=\"images/bullet-red-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Workgroup is currently closed.\" alt=\"\"/>\r\n                 </td><td nowrap>Closed\r\n                 ");
   } 
      out.write("\r\n         </td>\r\n        </tr></table>\r\n      </td>\r\n      <td width=\"10%\" align=\"center\">\r\n        <a href=\"workgroup-agents-status.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\">\r\n          ");
      out.print(  webManager.getActiveAgentMemberCount(workgroup) );
      out.write('/');
      out.print(  webManager.getAgentsInWorkgroup(workgroup).size() );
      out.write("\r\n        </a>\r\n      </td>\r\n      <td width=\"10%\" align=\"center\">\r\n        ");
      out.print(  workgroup.getRequestQueueCount() );
      out.write("\r\n      </td>\r\n      <td width=\"10%\" align=\"center\">\r\n        ");
      out.print(  webManager.getWaitingCustomerCount(workgroup) );
      out.write("\r\n      </td>\r\n      <td width=\"10%\" align=\"center\">\r\n        <a href=\"workgroup-queues.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\" title=\"Click to edit...\">\r\n          <img src=\"images/edit-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/>\r\n        </a>\r\n      </td>\r\n      <td width=\"10%\" align=\"center\">\r\n        <a href=\"workgroup-delete.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\" title=\"Click to delete...\">\r\n          <img src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/>\r\n        </a>\r\n      </td>\r\n    </tr>\r\n    ");


    }


      out.write("\r\n  </thead>\r\n</table>\r\n");
 if(numPages > 1){ 
      out.write("\r\n  <p>Pages: [\r\n    ");
   for (int pageIndex=0; pageIndex<numPages; pageIndex++) {

            String sep = ((pageIndex+1)<numPages) ? " " : "";

            boolean isCurrent = (pageIndex+1) == curPage;

    
      out.write("\r\n    <a href=\"workgroup-summary.jsp?start=");
      out.print( (pageIndex*range) );
      out.write("\" class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\">\r\n      ");
      out.print(  (pageIndex+1) );
      out.write("\r\n    </a>\r\n    ");
      out.print(  sep );
      out.write("\r\n    ");
   } 
      out.write("]\r\n  </p>\r\n");
 } 
      out.write("\r\n\r\n    </body>\r\n</html>");
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
