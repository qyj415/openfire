package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.spi.ChatHistoryUtils;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.packet.JID;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class usage_002dsummary_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n");

    WorkgroupManager wgManager = WorkgroupManager.getInstance();

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


      out.write("\r\n\r\n<html>\r\n<head>\r\n    <title>Usage Summary</title>\r\n    <meta name=\"pageID\" content=\"usage-summary\"/>\r\n    <style type=\"text/css\">@import url( /js/jscalendar/calendar-win2k-cold-1.css );</style>\r\n    <script type=\"text/javascript\" src=\"/js/jscalendar/calendar.js\"></script>\r\n    <script type=\"text/javascript\" src=\"/js/jscalendar/i18n.jsp\"></script>\r\n    <script type=\"text/javascript\" src=\"/js/jscalendar/calendar-setup.js\"></script>\r\n    <!--<meta name=\"helpPage\" content=\"view_workgroup_usage_reports.html\"/>-->\r\n\r\n    <style type=\"text/css\">\r\n        .textfield {\r\n            font-size: 11px;\r\n            font-family: verdana;\r\n            padding: 3px 2px;\r\n            background: #efefef;\r\n        }\r\n\r\n        .text {\r\n            font-size: 11px;\r\n            font-family: verdana;\r\n        }\r\n    </style>\r\n</head>\r\n\r\n<body>\r\n<style type=\"text/css\">\r\n    @import \"style/style.css\";\r\n</style>\r\n");
 if(errors){ 
      out.write("\r\n<div class=\"error\">\r\n    ");
      out.print( errorMessage);
      out.write("\r\n</div>\r\n");
 } 
      out.write("\r\n\r\n<p>\r\n    <span class=\"jive-description\">This reports shows historical information on overall usage for all Workgroups.\r\n    </span>\r\n</p>\r\n\r\n<div  class=\"jive-contentBox\">\r\n      <h4>Overall Usage Summary</h4>\r\n    <table class=\"box\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\">\r\n\r\n\r\n        <tr>\r\n            <td width=\"1%\" class=\"text\" nowrap>\r\n                Total number of users entering chat queues:\r\n            </td>\r\n            <td class=\"text\">\r\n                ");
      out.print( ChatHistoryUtils.getTotalRequestCountForSystem() );
      out.write("\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td width=\"1%\" class=\"text\" nowrap>\r\n                Number of users served by agents:\r\n            </td>\r\n            <td class=\"text\">\r\n                ");
      out.print( ChatHistoryUtils.getTotalChatsInSystem() );
      out.write("\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td width=\"1%\" class=\"text\" nowrap>\r\n                Percentage of users served by an agent:\r\n            </td>\r\n            <td class=\"text\">\r\n                ");

                    int totalRequests = ChatHistoryUtils.getTotalRequestCountForSystem();
                    int totalChats = ChatHistoryUtils.getTotalChatsInSystem();
                    DecimalFormat format = new DecimalFormat(".00");
                    double per = (double)totalChats / totalRequests * 100;
                    if (totalChats == 0 || totalRequests == 0) {
                        out.println("Not Available");
                    }
                    else {
                        String percentage = format.format((double)totalChats / totalRequests * 100);
                        out.println(percentage + "%");
                    }
                
      out.write("\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td width=\"1%\" class=\"text\" nowrap>\r\n                Average user wait time prior to being served:\r\n            </td>\r\n            <td class=\"text\">\r\n                ");
      out.print( ChatHistoryUtils.getAverageWaitTimeForServer() );
      out.write("\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td width=\"1%\" class=\"text\" nowrap>\r\n                Average length of a user chat session:\r\n            </td>\r\n            <td class=\"text\">\r\n                ");
      out.print( ChatHistoryUtils.getDateFromLong(ChatHistoryUtils.getAverageChatLengthForServer()) );
      out.write("\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td width=\"1%\" class=\"text\" nowrap>\r\n                Total length of all user chat sessions:\r\n            </td>\r\n            <td class=\"text\">\r\n                ");
      out.print( ChatHistoryUtils.getDateFromLong(ChatHistoryUtils.getTotalTimeForAllChatsInServer()) );
      out.write("\r\n            </td>\r\n        </tr>\r\n\r\n    </table>\r\n</div>\r\n<br/>\r\n\r\n<form name=\"workgroupForm\" method=\"post\" action=\"usage-summary.jsp\">\r\n<div  class=\"jive-contentBox\">\r\n      <h4>Workgroup Summaries</h4>\r\n<table class=\"box\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\">\r\n<tr>\r\n    <td width=\"1%\" nowrap class=\"text\">\r\n        Select Workgroup:\r\n    </td>\r\n    <td class=\"text\" width=\"1%\" nowrap>\r\n        <select name=\"workgroupBox\">\r\n            ");

                String wgroup = request.getParameter("workgroupBox");
                for (Workgroup w : wgManager.getWorkgroups()) {
                    String selectionID = "";
                    if (wgroup != null && wgroup.equals(w.getJID().toString())) {
                        selectionID = "selected";
                    }
            
      out.write("\r\n            <option value=\"");
      out.print( w.getJID().toString() );
      out.write('"');
      out.write(' ');
      out.print( selectionID );
      out.write(">\r\n                ");
      out.print( w.getJID().toString() );
      out.write("\r\n            </option>\r\n            ");

                }
            
      out.write("\r\n        </select>\r\n\r\n\r\n    </td>\r\n</tr>\r\n<tr>\r\n<td width=\"1%\" nowrap class=\"text\">\r\n    Choose Date\r\n</td>\r\n    <td width=\"1%\" class=\"text\" nowrap>\r\n        <!-- Start of Date -->\r\n        <TABLE border=\"0\">\r\n            <tr valign=\"top\">\r\n                <td width=\"1%\" nowrap class=\"text\">\r\n                    From:\r\n                </td>\r\n                <td width=\"1%\" nowrap class=\"text\"><input type=\"text\" name=\"startDate\" id=\"startDate\" size=\"15\" value=\"");
      out.print( start != null ? start : "");
      out.write("\"/><br/>\r\n                    Use mm/dd/yy</td>\r\n                <td width=\"1%\" nowrap>&nbsp;<img src=\"images/icon_calendarpicker.gif\" vspace=\"3\" id=\"startDateTrigger\"></td>\r\n\r\n\r\n                <TD width=\"1%\" nowrap class=\"text\">\r\n                    To:\r\n                </td>\r\n                <td width=\"1%\" nowrap class=\"text\"><input type=\"text\" name=\"endDate\" id=\"endDate\" size=\"15\" value=\"");
      out.print( end != null ? end : "" );
      out.write("\"/><br/>\r\n                    Use mm/dd/yy</td>\r\n                <td>&nbsp;<img src=\"images/icon_calendarpicker.gif\" vspace=\"3\" id=\"endDateTrigger\"></td>\r\n            </TR>\r\n        </TABLE>\r\n        <!-- End Of Date -->\r\n    </td>\r\n</tr>\r\n</table>\r\n<!-- End Of Date -->\r\n<table class=\"box\"  width=\"500\">\r\n<tr>\r\n    <td width=\"1%\" colspan=\"2\">\r\n    <input type=\"submit\" name=\"submit\" value=\"View Statistics\"/>\r\n</td>\r\n</tr>\r\n");

    String workgroupName = ParamUtils.getParameter(request, "workgroupBox");

      out.write("\r\n\r\n\r\n\r\n");
 if (ModelUtil.hasLength(workgroupName) && !errors) { 
      out.write("\r\n\r\n\r\n");

    if (workgroupName != null) {
        final Workgroup g = wgManager.getWorkgroup(new JID(workgroupName));
        String name = g.getJID().toString();

      out.write("\r\n<tr>\r\n     <td width=\"1%\" class=\"text\" nowrap colspan=\"2\">Usage Summary for <b>");
      out.print( name );
      out.write("</b> between ");
      out.print( start);
      out.write(" and ");
      out.print( end);
      out.write("<br/><br/></td>\r\n</tr>\r\n<tr>\r\n\r\n      <td width=\"1%\" class=\"text\" nowrap> Total number of users entering chat queues:</td>\r\n     <td width=\"1%\" class=\"text\" nowrap>\r\n        ");
      out.print( ChatHistoryUtils.getNumberOfRequestsForWorkgroup(name, startDate, endDate) );
      out.write("\r\n    </td>\r\n</tr>\r\n<tr>\r\n      <td width=\"1%\" class=\"text\" nowrap>Number of chat users served by agents:</td>\r\n     <td width=\"1%\" class=\"text\" nowrap>\r\n        ");
      out.print( ChatHistoryUtils.getNumberOfChatsAccepted(name, startDate, endDate) );
      out.write("\r\n    </td>\r\n</tr>\r\n<tr>\r\n      <td width=\"1%\" class=\"text\" nowrap>Number of users cancelling request</td>\r\n      <td width=\"1%\" class=\"text\" nowrap>\r\n        ");
      out.print( ChatHistoryUtils.getNumberOfRequestsCancelledByUser(name, startDate, endDate) );
      out.write("\r\n    </td>\r\n</tr><tr>\r\n      <td width=\"1%\" class=\"text\" nowrap>Number of users never picked up by an agent:</td>\r\n      <td width=\"1%\" class=\"text\" nowrap>\r\n        ");
      out.print( ChatHistoryUtils.getNumberOfRequestsNeverPickedUp(name, startDate, endDate) );
      out.write("\r\n    </td>\r\n</tr>\r\n\r\n<tr>\r\n     <td width=\"1%\" class=\"text\" nowrap>Average user wait time prior to being served</td>\r\n     <td width=\"1%\" class=\"text\" nowrap>\r\n        ");
      out.print( ChatHistoryUtils.getDateFromLong(ChatHistoryUtils.getAverageWaitTimeForWorkgroup(name, startDate, endDate)) );
      out.write("\r\n    </td>\r\n</tr><tr>\r\n     <td width=\"1%\" class=\"text\" nowrap>\r\n        Total length of all customer chat sessions:</td>\r\n     <td width=\"1%\" class=\"text\" nowrap>\r\n        ");
      out.print( ChatHistoryUtils.getDateFromLong(ChatHistoryUtils.getTotalChatTimeForWorkgroup(name)));
      out.write("\r\n    </td>\r\n</tr>\r\n");
 } 
      out.write('\r');
      out.write('\n');
 } 
      out.write("\r\n</table>\r\n</div>\r\n</form>\r\n\r\n<script type=\"text/javascript\">\r\n    function catcalc(cal) {\r\n        var endDateField = $('endDate');\r\n        var startDateField = $('startDate');\r\n\r\n        var endTime = new Date(endDateField.value);\r\n        var startTime = new Date(startDateField.value);\r\n        if (endTime.getTime() < startTime.getTime()) {\r\n            alert(\"Dates do not match\");\r\n            startDateField.value = \"\";\r\n            endDateField.value= \"\";\r\n        }\r\n    }\r\n\r\n    Calendar.setup(\r\n    {\r\n        inputField  : \"startDate\",         // ID of the input field\r\n        ifFormat    : \"%m/%d/%y\",    // the date format\r\n        button      : \"startDateTrigger\",       // ID of the button\r\n        onUpdate    :  catcalc\r\n    });\r\n\r\n    Calendar.setup(\r\n    {\r\n        inputField  : \"endDate\",         // ID of the input field\r\n        ifFormat    : \"%m/%d/%y\",    // the date format\r\n        button      : \"endDateTrigger\",       // ID of the button\r\n        onUpdate    :  catcalc\r\n    });\r\n</script>\r\n");
      out.write("</body>\r\n</html>\r\n\r\n");
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
