package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.fastpath.history.AgentChatSession;
import org.jivesoftware.openfire.fastpath.history.ChatSession;
import org.jivesoftware.openfire.fastpath.history.ChatTranscriptManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.packet.JID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.user.User;

public final class chat_002dsummary_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n<html>\r\n<head>\r\n    <title>Chat Summary</title>\r\n    <meta name=\"pageID\" content=\"chat-summary\"/>\r\n    <style type=\"text/css\">@import url( /js/jscalendar/calendar-win2k-cold-1.css );</style>\r\n    <script type=\"text/javascript\" src=\"/js/jscalendar/calendar.js\"></script>\r\n    <script type=\"text/javascript\" src=\"/js/jscalendar/i18n.jsp\"></script>\r\n    <script type=\"text/javascript\" src=\"/js/jscalendar/calendar-setup.js\"></script>\r\n\r\n    <style type=\"text/css\">\r\n        .textfield {\r\n            font-size: 11px;\r\n            font-family: verdana;\r\n            padding: 3px 2px;\r\n            background: #efefef;\r\n        }\r\n\r\n        .text {\r\n            font-size: 11px;\r\n            font-family: verdana;\r\n        }\r\n    </style>\r\n    <!--<meta name=\"helpPage\" content=\"view_chat_transcript_reports_for_a_workgroup.html\"/>-->\r\n</head>\r\n\r\n<body>\r\n<style type=\"text/css\">\r\n    @import \"style/style.css\";\r\n</style>\r\n");

    // Get a workgroup manager
    WorkgroupManager wgManager = WorkgroupManager.getInstance();

      out.write('\r');
      out.write('\n');
 // Get parameters //
    boolean cancel = request.getParameter("cancel") != null;
    String queueName = ParamUtils.getParameter(request, "queueName");
    if (queueName == null) {
        queueName = "Default Queue";
    }
    // Handle a cancel
    if (cancel) {
        response.sendRedirect("workgroup-summary.jsp");
        return;
    }

    final String sess = request.getParameter("sessionID");
    final String delete = request.getParameter("delete");
    if (ModelUtil.hasLength(sess) && ModelUtil.hasLength(delete)) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement("delete from fpSession where sessionID=?");
            pstmt.setString(1, sess);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }

        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement("delete from fpSessionProp where sessionID=?");
            pstmt.setString(1, sess);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }

        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement("delete from fpSessionMetadata where sessionID=?");
            pstmt.setString(1, sess);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }

        try {
            con = DbConnectionManager.getConnection();
            pstmt = con.prepareStatement("delete from fpAgentSession where sessionID=?");
            pstmt.setString(1, sess);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DbConnectionManager.closeConnection(pstmt, con);
        }

    }

    boolean submit = request.getParameter("submit") != null;

    boolean errors = false;
    String errorMessage = "";

    String start = request.getParameter("startDate");
    String end = request.getParameter("endDate");

    Date startDate = null;
    Date endDate = null;

    if (submit) {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");

        if (start == null || "".equals(start)) {
            errors = true;
            start = "";
            errorMessage = "Please specify a valid start date.";
        }
        else {
            try {
                startDate = formatter.parse(start);
            }
            catch (Exception e) {
                errors = true;
                start = "";
                errorMessage = "Please specify a valid start date.";
                Log.error(e);
            }
        }

        if (end == null || "".equals(end)) {
            errors = true;
            end = "";
            errorMessage = "Please specify a valid end date.";
        }
        else {
            try {
                endDate = formatter.parse(end);
            }
            catch (Exception e) {
                errors = true;
                end = "";
                errorMessage = "Please specify a valid end date.";
                Log.error(e);
            }
        }
    }



      out.write("\r\n\r\n");
 if(errors){ 
      out.write("\r\n<div class=\"error\">\r\n    ");
      out.print( errorMessage);
      out.write("\r\n</div>\r\n");
 } 
      out.write("\r\n\r\n");
   if (ModelUtil.hasLength(sess) && ModelUtil.hasLength(delete)) { 
      out.write("\r\n<div class=\"success\">\r\n    Conversation has been removed.\r\n</div>\r\n");
 } 
      out.write("\r\n\r\n<p>\r\n   Allows for specific report retrieval of previous conversations during two specified dates.\r\n</p>\r\n\r\n<div  class=\"jive-contentBox\">\r\n<form name=\"workgroupForm\" method=\"post\" action=\"chat-summary.jsp\">\r\n  <h4>Chat Transcripts</h4>\r\n        <table cellpadding=\"3\" cellspacing=\"1\" border=\"0\">\r\n            <tr>\r\n                <td width=\"1%\" nowrap>\r\n                    <span class=\"text\">Select Workgroup:</span>\r\n                </td>\r\n                <td>\r\n                    <select name=\"workgroupBox\" class=\"text\">\r\n                        ");

                            String wgroup = request.getParameter("workgroupBox");
                            for (Workgroup w : wgManager.getWorkgroups()) {
                                String selectionID = "";
                                if (wgroup != null && wgroup.equals(w.getJID().toString())) {
                                    selectionID = "selected";
                                }
                        
      out.write("\r\n                        <option value=\"");
      out.print( w.getJID().toString() );
      out.write('"');
      out.write(' ');
      out.print( selectionID );
      out.write(">\r\n                            ");
      out.print( w.getJID().toString() );
      out.write("</option>\r\n                        ");

                            }
                        
      out.write("\r\n                    </select>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td class=\"text\" width=\"1%\" nowrap>\r\n                    Choose Date:\r\n                </td>\r\n                <td nowrap>\r\n                    <!-- Start of Date -->\r\n                    <TABLE border=\"0\">\r\n                        <tr valign=\"top\">\r\n                            <td width=\"1%\" nowrap class=\"text\">\r\n                                From:\r\n                            </td>\r\n                            <td width=\"1%\" nowrap class=\"text\"><input type=\"text\" name=\"startDate\" id=\"startDate\" size=\"15\" value=\"");
      out.print( start != null ? start : "");
      out.write("\"/><br/>\r\n                            Use mm/dd/yy</td>\r\n                            <td width=\"1%\" nowrap>&nbsp;<img src=\"images/icon_calendarpicker.gif\" vspace=\"3\" id=\"startDateTrigger\"></td>\r\n\r\n\r\n                            <TD width=\"1%\" nowrap class=\"text\">\r\n                                To:\r\n                            </td>\r\n                            <td width=\"1%\" nowrap class=\"text\"><input type=\"text\" name=\"endDate\" id=\"endDate\" size=\"15\" value=\"");
      out.print( end != null ? end : "" );
      out.write("\"/><br/>\r\n                             Use mm/dd/yy</td>\r\n                            <td>&nbsp;<img src=\"images/icon_calendarpicker.gif\" vspace=\"3\" id=\"endDateTrigger\"></td>\r\n                         </TR>\r\n                    </TABLE>\r\n                    <!-- End Of Date -->\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td>\r\n                    <input type=\"submit\" name=\"submit\" value=\"View Chat Transcripts\"/>\r\n                </td>\r\n                <td align=\"left\">\r\n                    &nbsp;\r\n                </td>\r\n            </tr>\r\n        </table>\r\n</form>\r\n</div>\r\n");

    StringBuffer buf = new StringBuffer();
    final String workgroupName = request.getParameter("workgroupBox");

      out.write('\r');
      out.write('\n');
 if (ModelUtil.hasLength(workgroupName) && !errors) { 
      out.write('\r');
      out.write('\n');

    final Workgroup g = wgManager.getWorkgroup(new JID(workgroupName));

      out.write("\r\n<br>\r\n<table class=\"jive-table\"  cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <th nowrap>\r\n        Customer\r\n    </th>\r\n    <th>\r\n        Agent\r\n    </th>\r\n     <th>\r\n        Question\r\n    </th>\r\n    <th>\r\n        Date/Time\r\n    </th>\r\n    <th>\r\n        Options\r\n    </th>\r\n    ");

        Collection list = ChatTranscriptManager.getChatSessionsForWorkgroup(g, startDate, endDate);
        Iterator citer = list.iterator();
        while (citer.hasNext()) {
            ChatSession chatSession = (ChatSession)citer
                    .next();
            if (chatSession.getStartTime() == 0) {
                continue;
            }
            String sessionID = chatSession.getSessionID();
    
      out.write("\r\n    <tr>\r\n         <td nowrap width=\"1%\" class=\"conversation-body\">\r\n               ");

                String email = chatSession.getEmail();
                if (email.indexOf('@') != -1) {
            
      out.write(" <a href=\"mailto:");
      out.print(email);
      out.write("\">  ");
      out.print( chatSession.getCustomerName() );
      out.write(" </a>");

        }
        else {
        
      out.write(' ');
      out.print( chatSession.getCustomerName());
      out.write(' ');

            }
        
      out.write("\r\n\r\n        </td>\r\n        <td nowrap>\r\n            ");

                AgentChatSession initial = chatSession.getFirstSession();
                if (initial == null) {
                    out.println("<font color=red>");
                    if (chatSession.getState() == 0) {
                        out.println("User left the queue.");
                    }
                    else if (chatSession.getState() == 1) {
                        out.println("No agent picked up request.");
                    }
                    else {
                        out.println("Agent never joined");
                    }
                    out.println("</font>");
                }
                else {
                    JID jid = new JID(initial.getAgentJID());
                    User user = UserManager.getInstance().getUser(jid.getNode());

                    out.println("<a href=\"/user-properties.jsp?username="+user.getName()+"\">"+user.getName()+"</a>");
                }
                final SimpleDateFormat dayFormatter = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");

                final String displayDate = dayFormatter
                    .format(new Date(chatSession.getStartTime()));
            
      out.write("\r\n        </td>\r\n        <td>\r\n            ");
      out.print( chatSession.getQuestion() );
      out.write("\r\n        </td>\r\n        <td nowrap>");
      out.print( displayDate  );
      out.write("\r\n        </td>\r\n        <td nowrap>\r\n            <a href=\"chat-conversation.jsp?sessionID=");
      out.print( sessionID );
      out.write("\">View</a>\r\n            <a href=\"chat-summary.jsp?");
      out.print(buf.toString() );
      out.write("&workgroupBox=");
      out.print( workgroupName);
      out.write("&delete=true&sessionID=");
      out.print(sessionID);
      out.write("&startDate=");
      out.print(start);
      out.write("&endDate=");
      out.print(end);
      out.write("&submit=true\">Delete</a>\r\n        </td>\r\n\r\n    </tr>\r\n    ");
 } 
      out.write("</table>\r\n");

    if (list.size() == 0) {


      out.write("\r\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"100%\">\r\n    <tr>\r\n        <td class=\"c1\" colspan=6>\r\n            <tr><td class=\"text\">No Chats have occured in this workgroup.</td></tr>\r\n        </td>\r\n    </tr>\r\n</table>\r\n");
 } 
      out.write('\r');
      out.write('\n');
 } 
      out.write("\r\n\r\n\r\n<script type=\"text/javascript\">\r\n    function catcalc(cal) {\r\n        var endDateField = $('endDate');\r\n        var startDateField = $('startDate');\r\n\r\n        var endTime = new Date(endDateField.value);\r\n        var startTime = new Date(startDateField.value);\r\n        if (endTime.getTime() < startTime.getTime()) {\r\n            alert(\"Dates do not match\");\r\n            startDateField.value = \"\";\r\n            endDateField.value= \"\";\r\n        }\r\n    }\r\n\r\n    Calendar.setup(\r\n    {\r\n        inputField  : \"startDate\",         // ID of the input field\r\n        ifFormat    : \"%m/%d/%y\",    // the date format\r\n        button      : \"startDateTrigger\",       // ID of the button\r\n        onUpdate    :  catcalc\r\n    });\r\n\r\n    Calendar.setup(\r\n    {\r\n        inputField  : \"endDate\",         // ID of the input field\r\n        ifFormat    : \"%m/%d/%y\",    // the date format\r\n        button      : \"endDateTrigger\",       // ID of the button\r\n        onUpdate    :  catcalc\r\n    });\r\n</script>\r\n</body>\r\n</html>\r\n");
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
