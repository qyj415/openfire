package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.text.*;
import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.database.ProfiledConnection;
import org.jivesoftware.database.ProfiledConnectionEntry;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.LocaleUtils;

public final class server_002ddb_002dstats_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

 // Global methods, vars

    // Default refresh values
    static final int[] REFRESHES = {10,30,60,90};

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_fmt_message_key_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_fmt_message_key_nobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\r');
      out.write('\n');
 webManager.init(request, response, session, application, out ); 
      out.write("\r\n\r\n");
      out.write("\r\n\r\n");

    // Get parameters
    boolean doClear = request.getParameter("doClear") != null;
    String enableStats = ParamUtils.getParameter(request,"enableStats");
    int refresh = ParamUtils.getIntParameter(request,"refresh", -1);
    boolean doSortByTime = ParamUtils.getBooleanParameter(request,"doSortByTime");

    // Var for the alternating colors
    int rowColor = 0;

    // Clear the statistics
    if (doClear) {
        ProfiledConnection.resetStatistics();
        // Reload the page without params.
        response.sendRedirect("server-db-stats.jsp");
    }

    // Enable/disable stats
    if ("true".equals(enableStats) && ! DbConnectionManager.isProfilingEnabled()) {
        DbConnectionManager.setProfilingEnabled(true);
        // Log the event
        webManager.logEvent("enabled db profiling", null);
    }
    else if ("false".equals(enableStats) && DbConnectionManager.isProfilingEnabled()) {
        DbConnectionManager.setProfilingEnabled(false);
        // Log the event
        webManager.logEvent("disabled db profiling", null);
    }

    boolean showQueryStats = DbConnectionManager.isProfilingEnabled();

    // Number intFormat for pretty printing of large number values and decimals:
    NumberFormat intFormat = NumberFormat.getInstance(JiveGlobals.getLocale());
    DecimalFormat decFormat = new DecimalFormat("#,##0.00");

      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n        <meta name=\"pageID\" content=\"server-db\"/>\r\n    ");
  // Enable refreshing if specified
        if (refresh >= 10) {
    
      out.write("\r\n        <meta http-equiv=\"refresh\" content=\"");
      out.print( refresh );
      out.write(";URL=server-db-stats.jsp?refresh=");
      out.print( refresh );
      out.write("\">\r\n\r\n    ");
  } 
      out.write("\r\n</head>\r\n<body>\r\n\r\n<p>\r\n");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\r\n</p>\r\n\r\n\r\n\r\n\r\n<div class=\"jive-contentBox jive-contentBoxGrey\" style=\"width: 732px;\">\r\n<h3>");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</h3>\r\n\r\n<form action=\"server-db-stats.jsp\">\r\n    <table cellpadding=\"3\" cellspacing=\"1\" border=\"0\">\r\n    <tr>\r\n        <td>\r\n            <input type=\"radio\" name=\"enableStats\" value=\"true\" id=\"rb01\" ");
      out.print( ((showQueryStats) ? "checked":"") );
      out.write(">\r\n            <label for=\"rb01\">");
      out.print( ((showQueryStats) ? "<b>" +
                    LocaleUtils.getLocalizedString("server.db_stats.enabled") + "</b>": LocaleUtils.getLocalizedString("server.db_stats.enabled")) );
      out.write("</label>\r\n        </td>\r\n        <td>\r\n            <input type=\"radio\" name=\"enableStats\" value=\"false\" id=\"rb02\" ");
      out.print( ((!showQueryStats) ? "checked":"") );
      out.write(">\r\n            <label for=\"rb02\">");
      out.print( ((!showQueryStats) ? "<b>" +
                     LocaleUtils.getLocalizedString("server.db_stats.disabled") + "</b>":  LocaleUtils.getLocalizedString("server.db_stats.disabled")) );
      out.write("</label>\r\n        </td>\r\n        <td>\r\n            <input type=\"submit\" name=\"\" value=\"");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\">\r\n        </td>\r\n    </tr>\r\n    </table>\r\n</form>\r\n\r\n");
  if (showQueryStats) { 
      out.write("\r\n\t<br>\r\n\t<h3>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</h3>\r\n\r\n    <form action=\"server-db-stats.jsp\">\r\n        <table cellpadding=\"3\" cellspacing=\"5\" border=\"0\">\r\n        <tr>\r\n            <td>\r\n                ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write(":\r\n                <select size=\"1\" name=\"refresh\" onchange=\"this.form.submit();\">\r\n                <option value=\"none\">");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\r\n\r\n                ");
  for(int aREFRESHES: REFRESHES){
                        String selected = ((aREFRESHES == refresh) ? " selected" : "");
                
      out.write("\r\n                    <option value=\"");
      out.print( aREFRESHES );
      out.write('"');
      out.print( selected );
      out.write("\r\n                     >");
      out.print( aREFRESHES
                            );
      out.write(' ');
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("\r\n\r\n                ");
  } 
      out.write("\r\n                </select>\r\n            </td>\r\n            <td>\r\n                <input type=\"submit\" name=\"\" value=\"");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\">\r\n            </td>\r\n            <td>|</td>\r\n            <td>\r\n                <input type=\"submit\" name=\"\" value=\"");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\">\r\n            </td>\r\n            <td>|</td>\r\n            <td>\r\n                <input type=\"submit\" name=\"doClear\" value=\"");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\">\r\n            </td>\r\n        </tr>\r\n        </table>\r\n    </form>\r\n\r\n</div>\r\n\r\n\r\n    <b>");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("</b>\r\n\r\n    <ul>\r\n\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"600\">\r\n    <tr><td>\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"100%\">\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( intFormat.format(ProfiledConnection.getQueryCount(ProfiledConnection.Type.select)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( intFormat.format(ProfiledConnection.getTotalQueryTime(ProfiledConnection.Type.select)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( decFormat.format(ProfiledConnection.getAverageQueryTime(ProfiledConnection.Type.select)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( decFormat.format(ProfiledConnection.getQueriesPerSecond(ProfiledConnection.Type.select)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td bgcolor=\"#ffffff\">");

                    ProfiledConnectionEntry[] list = ProfiledConnection.getSortedQueries(ProfiledConnection.Type.select, doSortByTime);

                    if (list == null || list.length < 1) {
                        out.println(LocaleUtils.getLocalizedString("server.db_stats.no_queries"));
                    }
                    else { 
      out.write("\r\n                &nbsp;\r\n         </td>\r\n    </tr>\r\n    </table>\r\n    </td></tr>\r\n    </table>\r\n\r\n    <br />\r\n\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"600\">\r\n    <tr><td>\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <tr bgcolor=\"#ffffff\"><td>\r\n    ");
      out.println("<table width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#aaaaaa\"><tr><td bgcolor=\"#ffffff\" align=\"left\"><b>" + LocaleUtils.getLocalizedString("server.db_stats.query") + "</b></td>");
            out.println("<td bgcolor=\"#ffffff\"><b><a href=\"javascript:location.href='server-db-stats.jsp?doSortByTime=false&refresh=" + refresh + ";'\">" + LocaleUtils.getLocalizedString("server.db_stats.count") + "</a></b></td>");
            out.println("<td nowrap bgcolor=\"#ffffff\"><b>" + LocaleUtils.getLocalizedString("server.db_stats.time") + "</b></td>");
            out.println("<td nowrap bgcolor=\"#ffffff\"><b><a href=\"javascript:location.href='server-db-stats.jsp?doSortByTime=true&refresh=" + refresh + ";'\">" + LocaleUtils.getLocalizedString("server.db_stats.average_time") + "</a></b></td></tr>");

            for (int i = 0; i < ((list.length > 20) ? 20 : list.length); i++) {
                ProfiledConnectionEntry pce = list[i];
                out.println("<tr><td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + pce.sql + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.count) + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.totalTime) + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor++%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.totalTime/pce.count) + "</td></tr>");
            }
            out.println("</table>");
        }
     
      out.write("</td>\r\n    </tr>\r\n    </table>\r\n    </td></tr>\r\n    </table>\r\n\r\n    </ul>\r\n\r\n    <b>");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("</b>\r\n\r\n    <ul>\r\n\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"600\">\r\n    <tr><td>\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"100%\">\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( intFormat.format(ProfiledConnection.getQueryCount(ProfiledConnection.Type.insert)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( intFormat.format(ProfiledConnection.getTotalQueryTime(ProfiledConnection.Type.insert)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( decFormat.format(ProfiledConnection.getAverageQueryTime(ProfiledConnection.Type.insert)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( decFormat.format(ProfiledConnection.getQueriesPerSecond(ProfiledConnection.Type.insert)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td bgcolor=\"#ffffff\">");

                    list = ProfiledConnection.getSortedQueries(ProfiledConnection.Type.insert, doSortByTime);

                    if (list == null || list.length < 1) {
                        out.println(LocaleUtils.getLocalizedString("server.db_stats.no_queries"));
                    }
                    else { 
      out.write("\r\n                &nbsp;\r\n         </td>\r\n    </tr>\r\n    </table>\r\n    </td></tr>\r\n    </table>\r\n\r\n    <br />\r\n\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"600\">\r\n    <tr><td>\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <tr bgcolor=\"#ffffff\"><td>\r\n    ");
      out.println("<table width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#aaaaaa\"><tr><td bgcolor=\"#ffffff\" align=\"middle\"><b>" + LocaleUtils.getLocalizedString("server.db_stats.query") + "</b></td>");
            out.println("<td bgcolor=\"#ffffff\"><b><a href=\"javascript:location.href='server-db-stats.jsp?doSortByTime=false&refresh=" + refresh + ";'\">" + LocaleUtils.getLocalizedString("server.db_stats.count") + "</a></b></td>");
            out.println("<td nowrap bgcolor=\"#ffffff\"><b>" + LocaleUtils.getLocalizedString("server.db_stats.time") + "</b></td>");
            out.println("<td nowrap bgcolor=\"#ffffff\"><b><a href=\"javascript:location.href='server-db-stats.jsp?doSortByTime=true&refresh=" + refresh + ";'\">" + LocaleUtils.getLocalizedString("server.db_stats.average_time") + "</a></b></td></tr>");

            for (int i = 0; i < ((list.length > 10) ? 10 : list.length); i++) {
                ProfiledConnectionEntry pce = list[i];
                out.println("<tr><td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + pce.sql + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.count) + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.totalTime) + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor++%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.totalTime/pce.count) + "</td></tr>");
            }
            out.println("</table>");
        }
     
      out.write("</td>\r\n    </tr>\r\n    </table>\r\n    </td></tr>\r\n    </table>\r\n\r\n    </ul>\r\n\r\n    <b>");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("</b>\r\n\r\n    <ul>\r\n\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"600\">\r\n    <tr><td>\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"100%\">\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( intFormat.format(ProfiledConnection.getQueryCount(ProfiledConnection.Type.update)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( intFormat.format(ProfiledConnection.getTotalQueryTime(ProfiledConnection.Type.update)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( decFormat.format(ProfiledConnection.getAverageQueryTime(ProfiledConnection.Type.update)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( decFormat.format(ProfiledConnection.getQueriesPerSecond(ProfiledConnection.Type.update)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td bgcolor=\"#ffffff\">");

                    list = ProfiledConnection.getSortedQueries(ProfiledConnection.Type.update, doSortByTime);

                    if (list == null || list.length < 1) {
                        out.println(LocaleUtils.getLocalizedString("server.db_stats.no_queries"));
                    }
                    else { 
      out.write("\r\n                &nbsp;\r\n         </td>\r\n    </tr>\r\n    </table>\r\n    </td></tr>\r\n    </table>\r\n\r\n    <br />\r\n\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"600\">\r\n    <tr><td>\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <tr bgcolor=\"#ffffff\"><td>\r\n    ");
      out.println("<table width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#aaaaaa\"><tr><td bgcolor=\"#ffffff\" align=\"middle\"><b>" + LocaleUtils.getLocalizedString("server.db_stats.query") + "</b></td>");
            out.println("<td bgcolor=\"#ffffff\"><b><a href=\"javascript:location.href='server-db-stats.jsp?doSortByTime=false&refresh=" + refresh + ";'\">" + LocaleUtils.getLocalizedString("server.db_stats.count") + "</a></b></td>");
            out.println("<td nowrap bgcolor=\"#ffffff\"><b>" + LocaleUtils.getLocalizedString("server.db_stats.time") + "</b></td>");
            out.println("<td nowrap bgcolor=\"#ffffff\"><b><a href=\"javascript:location.href='server-db-stats.jsp?doSortByTime=true&refresh=" + refresh + ";'\">" + LocaleUtils.getLocalizedString("server.db_stats.average_time") + "</a></b></td></tr>");

            for (int i = 0; i < ((list.length > 10) ? 10 : list.length); i++) {
                ProfiledConnectionEntry pce = list[i];
                out.println("<tr><td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + pce.sql + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.count) + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.totalTime) + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor++%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.totalTime/pce.count) + "</td></tr>");
            }
            out.println("</table>");
        }
     
      out.write("</td>\r\n    </tr>\r\n    </table>\r\n    </td></tr>\r\n    </table>\r\n\r\n    </ul>\r\n\r\n    <b>");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("</b>\r\n\r\n    <ul>\r\n\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"600\">\r\n    <tr><td>\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"100%\">\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( intFormat.format(ProfiledConnection.getQueryCount(ProfiledConnection.Type.delete)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( intFormat.format(ProfiledConnection.getTotalQueryTime(ProfiledConnection.Type.delete)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( decFormat.format(ProfiledConnection.getAverageQueryTime(ProfiledConnection.Type.delete)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_33(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td>");
      out.print( decFormat.format(ProfiledConnection.getQueriesPerSecond(ProfiledConnection.Type.delete)) );
      out.write("</td>\r\n    </tr>\r\n    <tr bgcolor=\"#ffffff\">\r\n        <td>");
      if (_jspx_meth_fmt_message_34(_jspx_page_context))
        return;
      out.write("</td>\r\n        <td bgcolor=\"#ffffff\">");

                    list = ProfiledConnection.getSortedQueries(ProfiledConnection.Type.delete, doSortByTime);

                    if (list == null || list.length < 1) {
                        out.println(LocaleUtils.getLocalizedString("server.db_stats.no_queries"));
                    }
                    else { 
      out.write("\r\n                &nbsp;\r\n         </td>\r\n    </tr>\r\n    </table>\r\n    </td></tr>\r\n    </table>\r\n\r\n    <br />\r\n\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"600\">\r\n    <tr><td>\r\n    <table bgcolor=\"#aaaaaa\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <tr bgcolor=\"#ffffff\"><td>\r\n    ");
      out.println("<table width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#aaaaaa\"><tr><td bgcolor=\"#ffffff\" align=\"middle\"><b>" + LocaleUtils.getLocalizedString("server.db_stats.query") + "</b></td>");
            out.println("<td bgcolor=\"#ffffff\"><b><a href=\"javascript:location.href='server-db-stats.jsp?doSortByTime=false&refresh=" + refresh + ";'\">" + LocaleUtils.getLocalizedString("server.db_stats.count") + "</a></b></td>");
            out.println("<td nowrap bgcolor=\"#ffffff\"><b>" + LocaleUtils.getLocalizedString("server.db_stats.time") + "</b></td>");
            out.println("<td nowrap bgcolor=\"#ffffff\"><b><a href=\"javascript:location.href='server-db-stats.jsp?doSortByTime=true&refresh=" + refresh + ";'\">" + LocaleUtils.getLocalizedString("server.db_stats.average_time") + "</a></b></td></tr>");

            for (int i = 0; i < ((list.length > 10) ? 10 : list.length); i++) {
                ProfiledConnectionEntry pce = list[i];
                out.println("<tr><td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + pce.sql + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.count) + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.totalTime) + "</td>");
                out.println("<td bgcolor=\"" + ((rowColor++%2 == 0) ? "#efefef" : "#ffffff") + "\">" + intFormat.format(pce.totalTime/pce.count) + "</td></tr>");
            }
            out.println("</table>");
        }
     
      out.write("</td>\r\n    </tr>\r\n    </table>\r\n    </td></tr>\r\n    </table>\r\n\r\n    </ul>\r\n\r\n");
 } 
      out.write("\r\n\r\n\r\n</body></html>\r\n");
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

  private boolean _jspx_meth_fmt_message_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_0 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_0.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_0.setParent(null);
    _jspx_th_fmt_message_0.setKey("server.db_stats.title");
    int _jspx_eval_fmt_message_0 = _jspx_th_fmt_message_0.doStartTag();
    if (_jspx_th_fmt_message_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
    return false;
  }

  private boolean _jspx_meth_fmt_message_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_1 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_1.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_1.setParent(null);
    _jspx_th_fmt_message_1.setKey("server.db_stats.description");
    int _jspx_eval_fmt_message_1 = _jspx_th_fmt_message_1.doStartTag();
    if (_jspx_th_fmt_message_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_1);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_1);
    return false;
  }

  private boolean _jspx_meth_fmt_message_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_2 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_2.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_2.setParent(null);
    _jspx_th_fmt_message_2.setKey("server.db_stats.status");
    int _jspx_eval_fmt_message_2 = _jspx_th_fmt_message_2.doStartTag();
    if (_jspx_th_fmt_message_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_2);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_2);
    return false;
  }

  private boolean _jspx_meth_fmt_message_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_3 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_3.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_3.setParent(null);
    _jspx_th_fmt_message_3.setKey("server.db_stats.update");
    int _jspx_eval_fmt_message_3 = _jspx_th_fmt_message_3.doStartTag();
    if (_jspx_th_fmt_message_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_3);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_3);
    return false;
  }

  private boolean _jspx_meth_fmt_message_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_4 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_4.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_4.setParent(null);
    _jspx_th_fmt_message_4.setKey("server.db_stats.settings");
    int _jspx_eval_fmt_message_4 = _jspx_th_fmt_message_4.doStartTag();
    if (_jspx_th_fmt_message_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_4);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_4);
    return false;
  }

  private boolean _jspx_meth_fmt_message_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_5 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_5.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_5.setParent(null);
    _jspx_th_fmt_message_5.setKey("server.db_stats.refresh");
    int _jspx_eval_fmt_message_5 = _jspx_th_fmt_message_5.doStartTag();
    if (_jspx_th_fmt_message_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
    return false;
  }

  private boolean _jspx_meth_fmt_message_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_6 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_6.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_6.setParent(null);
    _jspx_th_fmt_message_6.setKey("server.db_stats.none");
    int _jspx_eval_fmt_message_6 = _jspx_th_fmt_message_6.doStartTag();
    if (_jspx_th_fmt_message_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
    return false;
  }

  private boolean _jspx_meth_fmt_message_7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_7 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_7.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_7.setParent(null);
    _jspx_th_fmt_message_7.setKey("server.db_stats.seconds");
    int _jspx_eval_fmt_message_7 = _jspx_th_fmt_message_7.doStartTag();
    if (_jspx_th_fmt_message_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_7);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_7);
    return false;
  }

  private boolean _jspx_meth_fmt_message_8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_8 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_8.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_8.setParent(null);
    _jspx_th_fmt_message_8.setKey("server.db_stats.set");
    int _jspx_eval_fmt_message_8 = _jspx_th_fmt_message_8.doStartTag();
    if (_jspx_th_fmt_message_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_8);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_8);
    return false;
  }

  private boolean _jspx_meth_fmt_message_9(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_9 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_9.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_9.setParent(null);
    _jspx_th_fmt_message_9.setKey("server.db_stats.update");
    int _jspx_eval_fmt_message_9 = _jspx_th_fmt_message_9.doStartTag();
    if (_jspx_th_fmt_message_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_9);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_9);
    return false;
  }

  private boolean _jspx_meth_fmt_message_10(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_10 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_10.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_10.setParent(null);
    _jspx_th_fmt_message_10.setKey("server.db_stats.clear_stats");
    int _jspx_eval_fmt_message_10 = _jspx_th_fmt_message_10.doStartTag();
    if (_jspx_th_fmt_message_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_10);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_10);
    return false;
  }

  private boolean _jspx_meth_fmt_message_11(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_11 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_11.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_11.setParent(null);
    _jspx_th_fmt_message_11.setKey("server.db_stats.select_stats");
    int _jspx_eval_fmt_message_11 = _jspx_th_fmt_message_11.doStartTag();
    if (_jspx_th_fmt_message_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_11);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_11);
    return false;
  }

  private boolean _jspx_meth_fmt_message_12(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_12 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_12.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_12.setParent(null);
    _jspx_th_fmt_message_12.setKey("server.db_stats.operations");
    int _jspx_eval_fmt_message_12 = _jspx_th_fmt_message_12.doStartTag();
    if (_jspx_th_fmt_message_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_12);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_12);
    return false;
  }

  private boolean _jspx_meth_fmt_message_13(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_13 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_13.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_13.setParent(null);
    _jspx_th_fmt_message_13.setKey("server.db_stats.total_time");
    int _jspx_eval_fmt_message_13 = _jspx_th_fmt_message_13.doStartTag();
    if (_jspx_th_fmt_message_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_13);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_13);
    return false;
  }

  private boolean _jspx_meth_fmt_message_14(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_14 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_14.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_14.setParent(null);
    _jspx_th_fmt_message_14.setKey("server.db_stats.avg_rate");
    int _jspx_eval_fmt_message_14 = _jspx_th_fmt_message_14.doStartTag();
    if (_jspx_th_fmt_message_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_14);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_14);
    return false;
  }

  private boolean _jspx_meth_fmt_message_15(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_15 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_15.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_15.setParent(null);
    _jspx_th_fmt_message_15.setKey("server.db_stats.total_rate");
    int _jspx_eval_fmt_message_15 = _jspx_th_fmt_message_15.doStartTag();
    if (_jspx_th_fmt_message_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_15);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_15);
    return false;
  }

  private boolean _jspx_meth_fmt_message_16(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_16 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_16.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_16.setParent(null);
    _jspx_th_fmt_message_16.setKey("server.db_stats.queries");
    int _jspx_eval_fmt_message_16 = _jspx_th_fmt_message_16.doStartTag();
    if (_jspx_th_fmt_message_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_16);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_16);
    return false;
  }

  private boolean _jspx_meth_fmt_message_17(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_17 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_17.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_17.setParent(null);
    _jspx_th_fmt_message_17.setKey("server.db_stats.insert_stats");
    int _jspx_eval_fmt_message_17 = _jspx_th_fmt_message_17.doStartTag();
    if (_jspx_th_fmt_message_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_17);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_17);
    return false;
  }

  private boolean _jspx_meth_fmt_message_18(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_18 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_18.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_18.setParent(null);
    _jspx_th_fmt_message_18.setKey("server.db_stats.operations");
    int _jspx_eval_fmt_message_18 = _jspx_th_fmt_message_18.doStartTag();
    if (_jspx_th_fmt_message_18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_18);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_18);
    return false;
  }

  private boolean _jspx_meth_fmt_message_19(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_19 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_19.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_19.setParent(null);
    _jspx_th_fmt_message_19.setKey("server.db_stats.total_time");
    int _jspx_eval_fmt_message_19 = _jspx_th_fmt_message_19.doStartTag();
    if (_jspx_th_fmt_message_19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_19);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_19);
    return false;
  }

  private boolean _jspx_meth_fmt_message_20(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_20 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_20.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_20.setParent(null);
    _jspx_th_fmt_message_20.setKey("server.db_stats.avg_rate");
    int _jspx_eval_fmt_message_20 = _jspx_th_fmt_message_20.doStartTag();
    if (_jspx_th_fmt_message_20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_20);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_20);
    return false;
  }

  private boolean _jspx_meth_fmt_message_21(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_21 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_21.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_21.setParent(null);
    _jspx_th_fmt_message_21.setKey("server.db_stats.total_rate");
    int _jspx_eval_fmt_message_21 = _jspx_th_fmt_message_21.doStartTag();
    if (_jspx_th_fmt_message_21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_21);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_21);
    return false;
  }

  private boolean _jspx_meth_fmt_message_22(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_22 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_22.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_22.setParent(null);
    _jspx_th_fmt_message_22.setKey("server.db_stats.queries");
    int _jspx_eval_fmt_message_22 = _jspx_th_fmt_message_22.doStartTag();
    if (_jspx_th_fmt_message_22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
    return false;
  }

  private boolean _jspx_meth_fmt_message_23(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_23 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_23.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_23.setParent(null);
    _jspx_th_fmt_message_23.setKey("server.db_stats.update_stats");
    int _jspx_eval_fmt_message_23 = _jspx_th_fmt_message_23.doStartTag();
    if (_jspx_th_fmt_message_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
    return false;
  }

  private boolean _jspx_meth_fmt_message_24(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_24 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_24.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_24.setParent(null);
    _jspx_th_fmt_message_24.setKey("server.db_stats.operations");
    int _jspx_eval_fmt_message_24 = _jspx_th_fmt_message_24.doStartTag();
    if (_jspx_th_fmt_message_24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_24);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_24);
    return false;
  }

  private boolean _jspx_meth_fmt_message_25(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_25 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_25.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_25.setParent(null);
    _jspx_th_fmt_message_25.setKey("server.db_stats.total_time");
    int _jspx_eval_fmt_message_25 = _jspx_th_fmt_message_25.doStartTag();
    if (_jspx_th_fmt_message_25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
    return false;
  }

  private boolean _jspx_meth_fmt_message_26(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_26 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_26.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_26.setParent(null);
    _jspx_th_fmt_message_26.setKey("server.db_stats.avg_rate");
    int _jspx_eval_fmt_message_26 = _jspx_th_fmt_message_26.doStartTag();
    if (_jspx_th_fmt_message_26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
    return false;
  }

  private boolean _jspx_meth_fmt_message_27(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_27 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_27.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_27.setParent(null);
    _jspx_th_fmt_message_27.setKey("server.db_stats.total_rate");
    int _jspx_eval_fmt_message_27 = _jspx_th_fmt_message_27.doStartTag();
    if (_jspx_th_fmt_message_27.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_27);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_27);
    return false;
  }

  private boolean _jspx_meth_fmt_message_28(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_28 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_28.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_28.setParent(null);
    _jspx_th_fmt_message_28.setKey("server.db_stats.queries");
    int _jspx_eval_fmt_message_28 = _jspx_th_fmt_message_28.doStartTag();
    if (_jspx_th_fmt_message_28.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_28);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_28);
    return false;
  }

  private boolean _jspx_meth_fmt_message_29(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_29 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_29.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_29.setParent(null);
    _jspx_th_fmt_message_29.setKey("server.db_stats.delete_stats");
    int _jspx_eval_fmt_message_29 = _jspx_th_fmt_message_29.doStartTag();
    if (_jspx_th_fmt_message_29.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_29);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_29);
    return false;
  }

  private boolean _jspx_meth_fmt_message_30(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_30 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_30.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_30.setParent(null);
    _jspx_th_fmt_message_30.setKey("server.db_stats.operations");
    int _jspx_eval_fmt_message_30 = _jspx_th_fmt_message_30.doStartTag();
    if (_jspx_th_fmt_message_30.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_30);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_30);
    return false;
  }

  private boolean _jspx_meth_fmt_message_31(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_31 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_31.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_31.setParent(null);
    _jspx_th_fmt_message_31.setKey("server.db_stats.total_time");
    int _jspx_eval_fmt_message_31 = _jspx_th_fmt_message_31.doStartTag();
    if (_jspx_th_fmt_message_31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_31);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_31);
    return false;
  }

  private boolean _jspx_meth_fmt_message_32(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_32 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_32.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_32.setParent(null);
    _jspx_th_fmt_message_32.setKey("server.db_stats.avg_rate");
    int _jspx_eval_fmt_message_32 = _jspx_th_fmt_message_32.doStartTag();
    if (_jspx_th_fmt_message_32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
    return false;
  }

  private boolean _jspx_meth_fmt_message_33(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_33 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_33.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_33.setParent(null);
    _jspx_th_fmt_message_33.setKey("server.db_stats.total_rate");
    int _jspx_eval_fmt_message_33 = _jspx_th_fmt_message_33.doStartTag();
    if (_jspx_th_fmt_message_33.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_33);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_33);
    return false;
  }

  private boolean _jspx_meth_fmt_message_34(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_34 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_34.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_34.setParent(null);
    _jspx_th_fmt_message_34.setKey("server.db_stats.queries");
    int _jspx_eval_fmt_message_34 = _jspx_th_fmt_message_34.doStartTag();
    if (_jspx_th_fmt_message_34.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_34);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_34);
    return false;
  }
}
