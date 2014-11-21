package org.jivesoftware.openfire.plugin.monitoring;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.archive.Conversation;
import org.jivesoftware.openfire.archive.ConversationManager;
import org.jivesoftware.openfire.reporting.graph.GraphEngine;
import org.jivesoftware.openfire.reporting.stats.StatisticsModule;
import org.jivesoftware.openfire.reporting.stats.StatsAction;
import org.jivesoftware.openfire.reporting.stats.StatsViewer;
import org.jivesoftware.openfire.user.UserNameManager;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.CookieUtils;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.StringUtils;
import org.xmpp.packet.JID;
import javax.servlet.http.Cookie;
import java.net.URLEncoder;
import java.util.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.MonitoringPlugin;

public final class stats_002ddashboard_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    public static final String COOKIE_TIMEPERIOD = "openfire-dashboard-timeperiod";

    /**
     * Sorts conversations by last modified time
     */
    final Comparator<Conversation> conversationComparator = new Comparator<Conversation>() {
        public int compare(Conversation conv1, Conversation conv2) {
           return conv2.getLastActivity().compareTo(conv1.getLastActivity());
        }
    };



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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

    String sessionKey = StatisticsModule.SESSIONS_KEY;
    MonitoringPlugin plugin = (MonitoringPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("monitoring");
    ConversationManager conversationManager = (ConversationManager)plugin.getModule(ConversationManager.class);
    StatsViewer viewer = (StatsViewer)plugin.getModule(StatsViewer.class);

    String timePeriod = "last60minutes";
    Cookie timePeriodCookie = CookieUtils.getCookie(request, COOKIE_TIMEPERIOD);
    if (timePeriodCookie != null) {
        String cookieValue = timePeriodCookie.getValue();
        timePeriod = cookieValue;
    }


      out.write("\n<html>\n<head>\n    <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n    <meta name=\"pageID\" content=\"statistics\"/>\n    <script src=\"/js/prototype.js\" type=\"text/javascript\"></script>\n    <script src=\"/js/scriptaculous.js\" type=\"text/javascript\"></script>\n    <script src=\"dwr/engine.js\" type=\"text/javascript\" ></script>\n\t<script src=\"dwr/util.js\" type=\"text/javascript\" ></script>\n    <script src=\"dwr/interface/Stats.js\" type=\"text/javascript\"></script>\n\n    <style type=\"text/css\">\n    .stats-description {\n        color : black;\n        font-size : 18px;\n        font-weight : bold;\n    }\n    .stats-current {\n        color : #555555;\n        font-size : 20px;\n        font-weight : bold;\n    }\n    .stat {\n        border : 1px;\n        border-color : #ccc;\n        border-style : solid;\n        background-color : #fffBe2;\n        -moz-border-radius: 5px;\n    }\n    .stat_selected {\n        border : 1px;\n        border-color : #f6ab4d;\n        border-style : solid;\n        background-color : #fffBc2;\n        -moz-border-radius: 5px;\n    }\n\n    .stat_enlarge_link {\n        display: block;\n");
      out.write("        position: relative;\n        margin: 4px 0px 2px 6px;\n        padding-left: 18px;\n        background: url(images/reports_dash-expand-small.gif) no-repeat;\n        font-size: 11px;\n    }\n    .stat_shrink_link {\n        position: relative;\n        margin: 4px 0px 2px 6px;\n        padding-left: 18px;\n        background: url(images/reports_dash-contract-small.gif) no-repeat;\n        font-size: 11px;\n    }\n    .timeControl {\n        border : 1px;\n        border-color : #ccc;\n        border-style : solid;\n        background-color : white;\n    }\n\n    .wrapper {\n        border : 1px;\n        border-color : #ccc;\n        border-style : solid;\n        -moz-border-radius: 5px;\n    }\n\n    .quickstats {\n        border: 1px solid #cccccc;\n        border-bottom: none;\n    }\n    .quickstats thead th {\n        background-color: #eeeeee;\n        text-align: left;\n        padding: 3px;\n        border-bottom: 1px solid #cccccc;\n    }\n    .quickstats tbody td {\n        padding: 6px;\n        border-bottom: 1px solid #cccccc;\n");
      out.write("        font-size: 11px;\n    }\n\n    .conversation {\n        border-bottom : 1px;\n        border-top : 0px;\n        border-right : 0px;\n        border-left : 0px;\n        border-color : #ccc;\n        border-style : solid;\n    }\n\n    .conversation table td {\n        font-size: 11px;\n    }\n\n    conv-users, conv-messages {\n\t    float: left;\n        display: block;\n\t    text-decoration: none;\n    }\n</style>\n\n<style type=\"text/css\">\n\t@import \"style/style.css\";\n</style>\n</head>\n\n<body>\n\n<script type=\"text/javascript\">\nPeriodicalExecuter.prototype.registerCallback = function() {\n    this.intervalID = setInterval(this.onTimerEvent.bind(this), this.frequency * 1000);\n}\n\nPeriodicalExecuter.prototype.stop = function() {\n    clearInterval(this.intervalID);\n}\nDWREngine.setErrorHandler(handleError);\nwindow.onerror = handleError;\nfunction handleError() {\n    // swallow errors: probably caused by the server being down\n}\n\nvar peStats = new PeriodicalExecuter(statsUpdater, 30);\n\nvar currentTimePeriod = '");
      out.print( timePeriod );
      out.write("';\n\nfunction statsUpdater() {\n    try {\n        Stats.getUpdatedStats(currentTimePeriod, updateStats);\n    } catch(err) {\n        // swallow errors\n    }\n}\n\n\nfunction changeTimePeriod(period) {\n    if (currentTimePeriod != period) {\n        $(currentTimePeriod).className = '';\n        $(period).className = 'timeControl';\n        currentTimePeriod = period;\n        createCookie(\"");
      out.print( COOKIE_TIMEPERIOD );
      out.write("\",currentTimePeriod,1000);\n        Stats.getUpdatedStats(currentTimePeriod, updateStats);\n    }\n}\n\nfunction updateStats(stats) {\n\n    for (var stat in stats) {\n        updateTable(stat, stats[stat]);\n\n        if (stat == 'conversations' || stat == 'packet_count' || stat == 'sessions') {\n            updateGraph('sparklines-' + stat, 'stat=' + stat + '&sparkline=true');\n        } else {\n            updateGraph('sparklines-' + stat, 'stat=' + stat + '&sparkline=true&color=dark');\n        }\n    }\n}\n\nfunction updateTable(id, data) {\n    $(id + '.low').innerHTML = data.low;\n    $(id + '.high').innerHTML = data.high;\n    if ($(id + '.count') != undefined) {\n        $(id + '.count').innerHTML = data.count;\n    }\n}\n\nfunction updateGraph(graphid, graphkey) {\n   var d = new Date();\n   var t = d.getTime()\n   $(graphid).src = 'graph?' + graphkey + '&t=' + t + \"&timeperiod=\" + currentTimePeriod + \"&format=png\";\n\n    statParam = graphkey.split('&');\n    statName = statParam[0].split('=');\n    if (isSnapshotDetailVisible && currentSnapshot == statName[1]) {\n");
      out.write("        viewElement = $('snapshot-detail-image');\n        viewElement.src = 'graph?stat=' + statName[1] + '&t=' + t + '&timeperiod=' + currentTimePeriod + '&width=700&height=250&format=png'\n    }\n}\n\nvar lastConversationID = 0;\nvar getConversationsDelay = 10000;\nvar insertConversationsDelay = 2000;\nvar peGetConversations;\nvar peInsertConversations;\nvar conversations = new Array();\n\nfunction startupConversations() {\n    conversationUpdater();\n    peGetConversations = new PeriodicalExecuter(conversationUpdater, getConversationsDelay/1000);\n}\n\nfunction conversationUpdater() {\n    Stats.getNLatestConversations(6, lastConversationID, updateConversations);\n}\n\nfunction updateConversations(data) {\n    // list of map objects with users, lastactivity, messages keys\n    if (data.length > 0) {\n        for (var i=0; i<data.length; i++) {\n            conversations[conversations.length] = data[i];\n        }\n        lastConversationID = conversations[conversations.length -1].conversationid;\n    }\n\n    // adjust insert frequency based on how many are in the queue\n");
      out.write("    if (data.length > 0 && Math.round(getConversationsDelay/(data.length)) > 2000) {\n        insertConversationsDelay = Math.round(10000/(data.length));\n    } else {\n        insertConversationsDelay = 2000;\n    }\n\n    if (peInsertConversations) {\n        peInsertConversations.stop();\n    }\n    peInsertConversations = new PeriodicalExecuter(insertConversation, insertConversationsDelay/1000);\n\n}\n\nfunction insertConversation() {\n    if (conversations.length > 0) {\n\n        if ($('conversations-scroller-none') != undefined) {\n            Element.hide('conversations-scroller-none');\n            Element.show('conversations-scroller');\n        }\n\n        var conversation = conversations.shift();\n        convTableID = 'conversations-scroller';\n        var tbody = $(convTableID);\n        var rows = tbody.getElementsByTagName(\"div\");\n        for (var i = rows.length-1; i > 0; i--) {\n            rows[i].innerHTML = rows[i-1].innerHTML;\n        }\n        newRow = document.createElement(\"div\");\n        newRow.setAttribute(\"class\", \"conversation\");\n");
      out.write("        newRow.setAttribute('conversationid', conversation.conversationid);\n\n        users = conversation.users;\n        userString = '';\n        for (i=0; i<users.length; i++) {\n            userString += users[i] + \"<br />\";\n        }\n\n        newRowHTML =\n        '<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">' +\n            '<tr>' +\n                '<td style=\"width:8px;\"><img src=\"images/blank.gif\" height=\"40\" width=\"8\" alt=\"\" border=\"0\" /></td>' +\n                '<td style=\"width:147px;\">' +\n                userString +\n                '</td>' +\n                '<td align=\"center\" style=\"width:85px;\">' +\n                conversation.lastactivity +\n                '</td>' +\n                '<td><img src=\"images/blank.gif\" width=\"6\" alt=\"\" border=\"0\" /></td>' +\n                '<td align=\"center\" style=\"width:77px;\">' + conversation.messages + '</td>' +\n            '</tr>' +\n        '</table>';\n\n        newRow.innerHTML = newRowHTML;\n\n        if (!isIE()) {\n            rows[0].style.display = 'none';\n            rows[0].innerHTML = newRow.innerHTML;\n");
      out.write("            new Effect.Appear(rows[0]);\n        } else {\n            rows[0].innerHTML = newRow.innerHTML;\n        }\n    }\n}\n\nfunction isIE() {\n    return navigator.appName.indexOf('Microsoft') != -1;\n}\n\nvar isSnapshotDetailVisible = false;\nvar currentSnapshot = '';\n\n\nfunction displaySnapshotDetail(snapshot) {\n    if (!isSnapshotDetailVisible) {\n        $('snapshot-detail-image').src = 'graph?stat=' + snapshot + '&t=' + t + '&timeperiod=' + currentTimePeriod + '&width=700&height=250&format=png';\n        Effect.SlideDown('snapshot-detail');\n        isSnapshotDetailVisible = true;\n        toggleSnapshotSelected(snapshot);\n        currentSnapshot = snapshot;\n    } else {\n        if ($('snapshot-detail-image').src.indexOf(snapshot) == -1) {\n            viewElement = $('snapshot-detail-image');\n            viewElement.style.display = \"none\";\n            viewElement.src = '/images/blank.gif';\n            var i = new Image();\n            i.onload = function() {\n                viewElement.src = i.src;\n                Effect.Appear('snapshot-detail-image');\n");
      out.write("            }\n            var d = new Date();\n            var t = d.getTime()\n            i.src = 'graph?stat=' + snapshot + '&t=' + t + '&timeperiod=' + currentTimePeriod + '&width=700&height=250&format=png';\n            toggleSnapshotSelected(snapshot);\n            currentSnapshot = snapshot;\n        } else {\n            hideSnapshotDetail();\n            currentSnapshot = '';\n            $('table-sessions').className = \"stat\";\n            $('table-conversations').className = \"stat\";\n            $('table-packet_count').className = \"stat\";\n        }\n    }\n}\n\nfunction toggleSnapshotSelected(selected) {\n    $('table-' + selected).className = \"stat_selected\";\n    $(selected + '-enlarge').className = 'stat_shrink_link';\n    $(selected + '-enlarge').innerHTML = '");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("';\n    if (currentSnapshot != '') {\n        $('table-' + currentSnapshot).className = \"stat\";\n        $(currentSnapshot + '-enlarge').className = 'stat_enlarge_link';\n        $(currentSnapshot + '-enlarge').innerHTML = '");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("';\n    }\n\n}\n\nfunction hideSnapshotDetail() {\n    if (isSnapshotDetailVisible) {\n        $(currentSnapshot + '-enlarge').className = 'stat_enlarge_link';\n        $(currentSnapshot + '-enlarge').innerHTML = '");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("';\n        Effect.SlideUp('snapshot-detail');\n        currentSnapshot = '';\n        $('table-sessions').className = \"stat\";\n        $('table-conversations').className = \"stat\";\n        $('table-packet_count').className = \"stat\";\n        isSnapshotDetailVisible = false;\n    }\n}\n\nfunction createCookie(name,value,days) {\n    if (days) {\n        var date = new Date();\n        date.setTime(date.getTime()+(days*24*60*60*1000));\n        var expires = \"; expires=\"+date.toGMTString();\n    } else {\n        var expires = \"\";\n    }\n    document.cookie = name+\"=\"+value+expires+\"; path=/\";\n}\n\n\n</script>\n\n<div id=\"instructions\">\n    <table width=\"756\" border=\"0\">\n    <tr>\n        <td width=\"426\">\n        <p>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("<br />");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</p>\n        </td>\n        <td width=\"330\" align=\"right\">\n            <table class=\"stat\" width=\"315\" cellspacing=\"0\" cellpadding=\"0\">\n            <tr>\n                <td colspan=\"6\"><img src=\"images/blank.gif\" height=\"9\" width=\"1\" alt=\"\" /></td>\n            </tr>\n            <tr>\n                <td><img src=\"images/blank.gif\" height=\"1\" width=\"9\" alt=\"\" /></td>\n                <td><b>");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</b></td>\n                <td>\n                    <table\n                        ");
 if (timePeriod.equalsIgnoreCase("last60minutes")) { 
      out.write("\n                            class=\"timeControl\"\n                        ");
 }
      out.write("\n                        id=\"last60minutes\" style=\"cursor: pointer;\">\n                    <tr onClick=\"changeTimePeriod('last60minutes'); return false;\">\n                        <td><img src=\"images/icon_clock-1hour.gif\"\n                                 alt=\"");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("\" border=\"0\" /></td>\n                        <td> ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</td>\n                    </tr>\n                    </table>\n                </td>\n                <td>\n                    <table\n                        ");
 if (timePeriod.equalsIgnoreCase("last24hours")) { 
      out.write("\n                            class=\"timeControl\"\n                        ");
 }
      out.write("\n                        id=\"last24hours\" style=\"cursor: pointer;\">\n                    <tr onClick=\"changeTimePeriod('last24hours'); return false;\">\n                        <td><img src=\"images/icon_clock-24hour.gif\"\n                                 alt=\"");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\" border=\"0\" /></td>\n                        <td> ");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</td>\n                    </tr>\n                    </table>\n                </td>\n                <td>\n                    <table\n                        ");
 if (timePeriod.equalsIgnoreCase("last7days")) { 
      out.write("\n                            class=\"timeControl\"\n                        ");
 }
      out.write("\n                        id=\"last7days\" style=\"cursor: pointer;\">\n                    <tr onClick=\"changeTimePeriod('last7days'); return false;\">\n                        <td><img src=\"images/icon_calendar-week.gif\"\n                                 alt=\"");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\" border=\"0\" /></td>\n                        <td> ");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</td>\n                    </tr>\n                    </table>\n                </td>\n                <td><img src=\"images/blank.gif\" height=\"1\" width=\"9\" alt=\"\" /></td>\n            </tr>\n            <tr>\n                <td colspan=\"6\"><img src=\"images/blank.gif\" height=\"9\" width=\"1\" alt=\"\" /></td>\n            </tr>\n            </table>\n        </td>\n    </tr>\n    </table>\n    <br />\n</div>\n\n\n\n\n\n<table class=\"wrapper\">\n<tr>\n    <td colspan=\"3\">\n\n\n        <div id=\"snapshot-detail\" style=\"display:none;\">\n            <div>\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n            <tr>\n                <td colspan=\"2\"><img border=\"0\" width=\"700\" height=\"25\" src=\"images/blank.gif\" alt=\"\"/></td>\n            </tr>\n            <tr>\n                <td colspan=\"2\">\n                    <div style=\"display: block; width: 692px; text-align: right;\">\n                        <div class=\"stat_shrink_link\" style=\"background: none;\">\n\n                            <a href=\"#\" onclick=\"hideSnapshotDetail(); return false;\">\n");
      out.write("                                <img src=\"images/reports_dash-contract-small.gif\" alt=\"\" border=\"0\" hspace=\"2\" align=\"texttop\">");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\n                            </a>\n                        </div>\n                    </div>\n                </td>\n            </tr>\n            <tr>\n                <td><img border=\"0\" width=\"1\" height=\"250\" src=\"images/blank.gif\" alt=\"\"/></td>\n                <td>\n                        <a href=\"#\" onclick=\"hideSnapshotDetail(); return false;\">\n                        <img border=\"0\" width=\"700\" height=\"250\" src=\"images/blank.gif\" alt=\"\" id=\"snapshot-detail-image\"/></a></td>\n            </tr>\n            </table>\n            </div>\n        </div>\n\n        <img src=\"images/blank.gif\" height=\"14\" width=\"1\" alt=\"\" /></td>\n</tr>\n<tr>\n    <td><img src=\"images/blank.gif\" height=\"1\" width=\"16\" alt=\"\" /></td>\n    <td>\n        <div id=\"snapshot\">\n        <table width=\"705\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n            <tr>\n                ");

                   long[] startAndEnd = GraphEngine.parseTimePeriod(timePeriod);
                   String[] sessionsHighLow = StatsAction.getLowAndHigh("sessions", startAndEnd);
                   String[] conversationsHighLow = StatsAction.getLowAndHigh("conversations", startAndEnd);
                   String[] messageHighLow = StatsAction.getLowAndHigh("packet_count", startAndEnd);
                   String[] serversHighLow = StatsAction.getLowAndHigh("server_sessions", startAndEnd);
                   String[] mucHighLow = StatsAction.getLowAndHigh("muc_rooms", startAndEnd);
                   String[] fileTransferHighLow = StatsAction.getLowAndHigh("proxyTransferRate", startAndEnd);
                   String[] serverBytesHighLow = StatsAction.getLowAndHigh("server_bytes", startAndEnd);
                
      out.write("\n                <td align=\"left\">\n                    <table class=\"stat\" width=\"220\" id=\"table-sessions\">\n                        <tr>\n                            <td colspan=\"5\"><img src=\"images/blank.gif\" width=\"1\" height=\"2\" border=\"0\" /></td>\n                        </tr>\n                        <tr>\n                            <td colspan=\"5\" align=\"center\">\n                                <span class=\"stats-description\">\n                                    ");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("\n                                </span>\n                            </td>\n                        </tr>\n                        <tr>\n                            <td width=\"13\"><img src=\"images/blank.gif\" width=\"13\" height=\"1\" border=\"0\" /></td>\n                            <td align=\"left\" valign=\"middle\" nowrap width=\"27%\">\n                                ");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("\n                                <span id=\"sessions.low\">");
      out.print( sessionsHighLow[0]);
      out.write("</span>\n                            </td>\n                            <td align=\"center\" width=\"27%\">\n                                <span class=\"stats-current\" id=\"sessions.count\">\n                                    ");
      out.print( (int)viewer.getCurrentValue(StatisticsModule.SESSIONS_KEY)[0] );
      out.write("\n                                </span>\n                            </td>\n                            <td align=\"right\" valign=\"middle\" nowrap  width=\"27%\">\n                                ");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\n                                <span id=\"sessions.high\">");
      out.print( sessionsHighLow[1]);
      out.write("</span>\n                            </td>\n                            <td width=\"13\"><img src=\"images/blank.gif\" width=\"13\" height=\"1\" border=\"0\" /></td>\n                        </tr>\n                        <tr>\n                            <td colspan=\"5\" align=\"center\">\n                                <a href=\"#\" onclick=\"displaySnapshotDetail('sessions'); return false;\">\n                                    <img width=\"200\" height=\"50\" style=\"border: 1px solid #b4b4b4;\"\n                                         src=\"graph?stat=");
      out.print(sessionKey);
      out.write("&sparkline=true&format=png\"\n                                         alt=\"");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("\"\n                                         id=\"sparklines-sessions\"/><br>\n                                    <div align=\"left\" id=\"sessions-enlarge\" class=\"stat_enlarge_link\">");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</div></a></td>\n                        </tr>\n\n                    </table>\n                </td>\n                <td align=\"center\">\n                    <table class=\"stat\" width=\"220\" id=\"table-conversations\">\n                        <tr>\n                            <td colspan=\"5\"><img src=\"images/blank.gif\" width=\"1\" height=\"2\" border=\"0\" /></td>\n                        </tr>\n                        <tr>\n                            <td colspan=\"5\" align=\"center\">\n                                <span class=\"stats-description\">\n                                    ");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("\n                                </span>\n                            </td>\n                        </tr>\n                        <tr>\n                            <td align=\"center\" width=\"13\"><img src=\"images/blank.gif\" width=\"13\" height=\"1\" border=\"0\" /></td>\n                            <td align=\"left\" valign=\"middle\" nowrap width=\"27%\">\n                                ");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("\n                                <span id=\"conversations.low\">");
      out.print( conversationsHighLow[0]);
      out.write("</span>\n\n                            </td>\n                            <td align=\"center\" width=\"27%\">\n                                <span class=\"stats-current\" id=\"conversations.count\">\n                                    ");
      out.print( (int)viewer.getCurrentValue(ConversationManager.CONVERSATIONS_KEY)[0] );
      out.write("\n                                </span>\n                            </td>\n                            <td align=\"right\" valign=\"middle\" nowrap  width=\"27%\">\n                                ");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("\n                                <span id=\"conversations.high\">");
      out.print( conversationsHighLow[1]);
      out.write("</span>\n                            </td>\n                            <td align=\"center\" width=\"13\"><img src=\"images/blank.gif\" width=\"13\" height=\"1\" border=\"0\" /></td>\n                        </tr>\n                        <tr>\n                            <td colspan=\"5\" align=\"center\"><a href=\"#\"\n                                onclick=\"displaySnapshotDetail('conversations'); return false;\"><img\n                                    width=\"200\" height=\"50\" style=\"border: 1px solid #b4b4b4;\"\n                                    src=\"graph?stat=conversations&sparkline=true&format=png\"\n                                    alt=\"");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("\"\n                                    id=\"sparklines-conversations\"/><br>\n                                    <div align=\"left\" id=\"conversations-enlarge\" class=\"stat_enlarge_link\">");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("</div></a></td>\n                        </tr>\n                    </table>\n                </td>\n                <td align=\"right\">\n                    <table class=\"stat\" width=\"220\" id=\"table-packet_count\">\n                        <tr>\n                            <td colspan=\"5\"><img src=\"images/blank.gif\" width=\"1\" height=\"2\" border=\"0\" /></td>\n                        </tr>\n                        <tr>\n                            <td colspan=\"5\" align=\"center\">\n                                <span class=\"stats-description\">\n                                    ");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("\n                                </span>\n                            </td>\n                        </tr>\n                        <tr>\n                            <td align=\"center\" width=\"13\"><img src=\"images/blank.gif\" width=\"13\" height=\"1\" border=\"0\" /></td>\n                            <td align=\"left\" valign=\"middle\" nowrap width=\"27%\">\n                                ");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("\n                                <span id=\"packet_count.low\">");
      out.print( messageHighLow[0]);
      out.write("</span>\n\n                            </td>\n                            <td align=\"center\" width=\"27%\">\n                                <span class=\"stats-current\" id=\"packet_count.count\">\n                                    ");
      out.print( (int)viewer.getCurrentValue(StatisticsModule.TRAFFIC_KEY)[0] );
      out.write("\n                                </span>\n                            </td>\n                            <td align=\"right\" valign=\"middle\" nowrap  width=\"27%\">\n                                ");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("\n                                <span id=\"packet_count.high\">");
      out.print( messageHighLow[1]);
      out.write("</span>\n                            </td>\n                            <td align=\"center\" width=\"13\"><img src=\"images/blank.gif\" width=\"13\" height=\"1\" border=\"0\" /></td>\n                        </tr>\n                        <tr>\n                            <td colspan=\"5\" align=\"center\"><a href=\"#\"\n                                onclick=\"displaySnapshotDetail('packet_count'); return false;\"><img\n                                    width=\"200\" height=\"50\" style=\"border: 1px solid #b4b4b4;\"\n                                    src=\"graph?stat=packet_count&sparkline=true&format=png\"\n                                    alt=\"");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("\"\n                                    id=\"sparklines-packet_count\"/><br>\n                                    <div align=\"left\" id=\"packet_count-enlarge\" class=\"stat_enlarge_link\">");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("</div></a></td>\n                        </tr>\n                    </table>\n                </td>\n            </tr>\n        </table>\n        </div>\n\n        <br/>\n\n\n        <!-- Handle SparkLines Stats -->\n        <table width=\"705\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n            <tr valign=\"top\">\n                <td width=\"371\">\n                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"371\" class=\"quickstats\">\n                        <thead>\n                            <tr>\n                                <th colspan=\"2\">\n                                    ");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("\n                                </th>\n                                <th style=\"font-weight:normal; font-size: 11px;\">\n                                    ");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("\n                                </th>\n                                <th>\n                                </th>\n                                <th style=\"font-weight:normal; font-size: 11px; padding-right: 8px;\">\n                                    ");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write("\n                                </th>\n                            </tr>\n                        </thead>\n                        <tbody>\n                            <tr>\n                                <td><b>");
      out.print( viewer.getStatistic("server_sessions")[0].getName() );
      out.write("</b></td>\n                                <td width=\"1%\"><img id=\"sparklines-server_sessions\"\n                                         src=\"graph?stat=server_sessions&sparkline=true&color=dark&format=png\"\n                                         style=\"border: 1px solid #b4b4b4;\" width=\"180\" height=\"50\" /></td>\n                                <td id=\"server_sessions.low\" align=\"center\">");
      out.print( serversHighLow[0] );
      out.write("</td>\n                                <td><img src=\"images/blank.gif\" border=\"0\" width=\"7\" height=\"1\" alt=\"\" /></td>\n                                <td id=\"server_sessions.high\" align=\"center\">");
      out.print( serversHighLow[1] );
      out.write("</td>\n                            </tr>\n                            <tr>\n                                <td><b>");
      out.print( viewer.getStatistic("muc_rooms")[0].getName() );
      out.write("</b></td>\n                                <td><img id=\"sparklines-muc_rooms\"\n                                         src=\"graph?stat=muc_rooms&sparkline=true&color=dark&format=png\"\n                                         style=\"border: 1px solid #b4b4b4;\" width=\"180\" height=\"50\" /></td>\n                                <td id=\"muc_rooms.low\" align=\"center\">");
      out.print( mucHighLow[0] );
      out.write("</td>\n                                <td><img src=\"images/blank.gif\" border=\"0\" width=\"7\" height=\"1\" alt=\"\" /></td>\n                                <td id=\"muc_rooms.high\" align=\"center\">");
      out.print( mucHighLow[1] );
      out.write("</td>\n                            </tr>\n                            <tr>\n                                <td><b>");
      out.print( viewer.getStatistic("proxyTransferRate")[0].getName() );
      out.write("</b></td>\n                                <td width=\"1%\"><img id=\"sparklines-proxyTransferRate\"\n                                         src=\"graph?stat=proxyTransferRate&sparkline=true&color=dark&format=png\"\n                                         style=\"border: 1px solid #b4b4b4;\" width=\"180\" height=\"50\" /></td>\n                                <td id=\"proxyTransferRate.low\" align=\"center\">");
      out.print( fileTransferHighLow[0] );
      out.write("</td>\n                                <td><img src=\"images/blank.gif\" border=\"0\" width=\"7\" height=\"1\" alt=\"\" /></td>\n                                <td id=\"proxyTransferRate.high\" align=\"center\">");
      out.print( fileTransferHighLow[1] );
      out.write("</td>\n                            </tr>\n                            <tr>\n                                <td><b>");
      out.print( viewer.getStatistic("server_bytes")[0].getName() );
      out.write("</b><br />\n                                </td>\n                                <td width=\"1%\"><img id=\"sparklines-server_bytes\"\n                                         src=\"graph?stat=server_bytes&sparkline=true&color=dark&format=png\"\n                                         style=\"border: 1px solid #b4b4b4;\" width=\"180\" height=\"50\" /></td>\n                                <td id=\"server_bytes.low\" align=\"center\">");
      out.print( serverBytesHighLow[0] );
      out.write("</td>\n                                <td><img src=\"images/blank.gif\" border=\"0\" width=\"7\" height=\"1\" alt=\"\" /></td>\n                                <td id=\"server_bytes.high\" align=\"center\">");
      out.print( serverBytesHighLow[1] );
      out.write("</td>\n                            </tr>\n                        </tbody>\n                    </table>\n                <br>\n                </td>\n                <td width=\"17\"><img src=\"images/blank.gif\" width=\"17\" height=\"1\" border=\"0\" alt=\"\" /></td>\n                <td width=\"317\">\n                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" class=\"jive-table\" style=\"border: 1px solid #cccccc; border-bottom: none;\">\n                        <thead>\n                        <tr>\n                            <th>\n                                ");
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write("\n                                (<a href=\"conversations.jsp\">");
      if (_jspx_meth_fmt_message_33(_jspx_page_context))
        return;
      out.write("</a>)\n                            </th>\n                        </tr>\n                        </thead>\n                        <tr>\n                            <td style=\"padding:0px 0px 0px 8px;background-color:#bbbbbb\">\n                                <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n                                <tr>\n                                    <td style=\"width:147px;color:white;font-size:8pt;\">\n                                        <b>");
      if (_jspx_meth_fmt_message_34(_jspx_page_context))
        return;
      out.write("</b>\n                                    </td>\n                                    <td align=\"center\" style=\"width:85px;color:white;font-size:8pt;\">\n                                        <b>");
      if (_jspx_meth_fmt_message_35(_jspx_page_context))
        return;
      out.write("</b>\n                                    </td>\n                                    <td></td>\n                                    <td align=\"center\" style=\"width:77px;color:white;font-size:8pt;\">\n                                        <b>");
      if (_jspx_meth_fmt_message_36(_jspx_page_context))
        return;
      out.write("</b>\n                                    </td>\n                                </tr>\n                                </table>\n                            </td>\n                        </tr>\n                        <tr>\n                            <td style=\"padding:0px\">\n                                ");

                                // Get handle on the Monitoring plugin
                                Collection<Conversation> conversations = conversationManager.getConversations();
                                String displayStyle = "''";
                                if (conversations.isEmpty()) {
                                    displayStyle = "none";
                                
      out.write("\n                                    <div id=\"conversations-scroller-none\" style=\"padding: 10px;\">\n                                        ");
      if (_jspx_meth_fmt_message_37(_jspx_page_context))
        return;
      out.write("\n                                    </div>\n                                ");
 } 
      out.write("\n                                <div id=\"conversations-scroller\" style=\"display:");
      out.print( displayStyle );
      out.write("\">\n                                   ");

                                       List<Conversation> lConversations = Arrays.asList(
                                               conversations.toArray(new Conversation[conversations.size()]));
                                       Collections.sort(lConversations, conversationComparator);
                                       for (int i = 0; i < 6; i++) {
                                           String participantNames = "";
                                           String activityTime = "";
                                           String messageCount = "";
                                           if (lConversations.size() > i) {
                                               Conversation conversation = lConversations.get(i);
                                               if (conversation.getRoom() == null) {
                                                   Collection<JID> participants = conversation.getParticipants();
                                                   for (JID jid : participants) {
                                                       String identifier = jid.toBareJID();
                                                       try {
                                                           identifier = UserNameManager.getUserName(jid, jid.toBareJID());
                                                       } catch (UserNotFoundException e) {
                                                           // Ignore
                                                       }
                                                       participantNames +=
                                                               StringUtils.abbreviate(identifier, 20) +
                                                                       "<br />";
                                                   }
                                               } else {
                                                   // Display "group conversation" with a link to the room occupants
                                                   /*participantNames = LocaleUtils.getLocalizedString(
                                                           "archive.group_conversation", "monitoring", Arrays.asList(
                                                           "<a href='../../muc-room-occupants.jsp?roomJID=" +
                                                                   URLEncoder.encode(conversation.getRoom().toBareJID(),
                                                                           "UTF-8") + "'>", "</a>"));*/

                                                   participantNames = LocaleUtils.getLocalizedString("dashboard.group_conversation", "monitoring");
                                                   participantNames += "<br/>";
                                                   participantNames += "(<i>" + LocaleUtils.getLocalizedString("muc.room.summary.room") + ": <a href='../../muc-room-occupants.jsp?roomJID=" + URLEncoder.encode(conversation.getRoom().toBareJID(),"UTF-8") + "'>" + conversation.getRoom().getNode() + "</a></i>)";
                                               }
                                               activityTime =
                                                       StatsAction.formatTimeLong(conversation.getLastActivity());
                                               messageCount = Integer.toString(conversation.getMessageCount());
                                           }
                                   
      out.write("\n                                        <div class=\"conversation\"\n                                            ");
 if (i == 3) {
      out.write("style=\"opacity: 0.7;filter:alpha(opacity=10);\" ");
}
      out.write("\n                                            ");
 if (i == 4) {
      out.write("style=\"opacity: 0.4;filter:alpha(opacity=10);\" ");
}
      out.write("\n                                            ");
 if (i == 5) {
      out.write("style=\"opacity: 0.2;filter:alpha(opacity=10);border-bottom:0px;\" ");
}
      out.write("\n                                            >\n                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n                                            <tr>\n                                                <td style=\"width:8px;\"><img src=\"images/blank.gif\" height=\"38\" width=\"8\" alt=\"\" border=\"0\" /></td>\n                                                <td style=\"width:147px;\">\n                                                     ");
      out.print( participantNames );
      out.write("\n                                                </td>\n                                                <td align=\"center\" style=\"width:85px;\">");
      out.print( activityTime );
      out.write("</td>\n                                                <td><img src=\"images/blank.gif\" width=\"6\" alt=\"\" border=\"0\" /></td>\n                                                <td align=\"center\" style=\"width:77px;\">");
      out.print( messageCount );
      out.write("</td>\n                                            </tr>\n                                            </table>\n                                        </div>\n                                    ");
  } 
      out.write("\n                                </div>\n                            </td>\n                        </tr>\n                    </table>\n                <br>\n                </td>\n            </tr>\n            <tr>\n                <td></td>\n                <td></td>\n            </tr>\n        </table>\n    </td>\n    <td><img src=\"images/blank.gif\" height=\"1\" width=\"16\" alt=\"\" /></td>\n</tr>\n</table>\n\n<br>\n\n<script type=\"text/javascript\">\n    window.onload = startupConversations;\n</script>\n\n</body>\n</html>\n\n");
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
    _jspx_th_fmt_message_0.setKey("admin.sidebar.statistics.name");
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
    _jspx_th_fmt_message_1.setKey("dashboard.snapshot.enlarge");
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
    _jspx_th_fmt_message_2.setKey("dashboard.snapshot.shrink");
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
    _jspx_th_fmt_message_3.setKey("dashboard.snapshot.enlarge");
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
    _jspx_th_fmt_message_4.setKey("dashboard.description");
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
    _jspx_th_fmt_message_5.setKey("dashboard.directions");
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
    _jspx_th_fmt_message_6.setKey("dashboard.timespan");
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
    _jspx_th_fmt_message_7.setKey("dashboard.timespan.lasthour");
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
    _jspx_th_fmt_message_8.setKey("dashboard.timespan.lasthour");
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
    _jspx_th_fmt_message_9.setKey("dashboard.timespan.last24hours");
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
    _jspx_th_fmt_message_10.setKey("dashboard.timespan.last24hours");
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
    _jspx_th_fmt_message_11.setKey("dashboard.timespan.last7days");
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
    _jspx_th_fmt_message_12.setKey("dashboard.timespan.last7days");
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
    _jspx_th_fmt_message_13.setKey("dashboard.snapshot.shrink");
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
    _jspx_th_fmt_message_14.setKey("dashboard.spotlights.currentusers");
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
    _jspx_th_fmt_message_15.setKey("dashboard.spotlights.low");
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
    _jspx_th_fmt_message_16.setKey("dashboard.spotlights.high");
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
    _jspx_th_fmt_message_17.setKey("dashboard.spotlights.currentusers");
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
    _jspx_th_fmt_message_18.setKey("dashboard.snapshot.enlarge");
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
    _jspx_th_fmt_message_19.setKey("dashboard.spotlights.activeconversations");
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
    _jspx_th_fmt_message_20.setKey("dashboard.spotlights.low");
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
    _jspx_th_fmt_message_21.setKey("dashboard.spotlights.high");
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
    _jspx_th_fmt_message_22.setKey("dashboard.spotlights.activeconversations");
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
    _jspx_th_fmt_message_23.setKey("dashboard.snapshot.enlarge");
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
    _jspx_th_fmt_message_24.setKey("dashboard.spotlights.packetactivity");
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
    _jspx_th_fmt_message_25.setKey("dashboard.spotlights.low");
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
    _jspx_th_fmt_message_26.setKey("dashboard.spotlights.high");
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
    _jspx_th_fmt_message_27.setKey("dashboard.spotlights.packetactivity");
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
    _jspx_th_fmt_message_28.setKey("dashboard.snapshot.enlarge");
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
    _jspx_th_fmt_message_29.setKey("dashboard.quickstats");
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
    _jspx_th_fmt_message_30.setKey("dashboard.quickstats.low");
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
    _jspx_th_fmt_message_31.setKey("dashboard.quickstats.high");
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
    _jspx_th_fmt_message_32.setKey("dashboard.currentconversations");
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
    _jspx_th_fmt_message_33.setKey("dashboard.currentconversations.details");
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
    _jspx_th_fmt_message_34.setKey("dashboard.currentconversations.users");
    int _jspx_eval_fmt_message_34 = _jspx_th_fmt_message_34.doStartTag();
    if (_jspx_th_fmt_message_34.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_34);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_34);
    return false;
  }

  private boolean _jspx_meth_fmt_message_35(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_35 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_35.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_35.setParent(null);
    _jspx_th_fmt_message_35.setKey("dashboard.currentconversations.lastactivity");
    int _jspx_eval_fmt_message_35 = _jspx_th_fmt_message_35.doStartTag();
    if (_jspx_th_fmt_message_35.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_35);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_35);
    return false;
  }

  private boolean _jspx_meth_fmt_message_36(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_36 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_36.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_36.setParent(null);
    _jspx_th_fmt_message_36.setKey("dashboard.currentconversations.messagecount");
    int _jspx_eval_fmt_message_36 = _jspx_th_fmt_message_36.doStartTag();
    if (_jspx_th_fmt_message_36.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_36);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_36);
    return false;
  }

  private boolean _jspx_meth_fmt_message_37(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_37 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_37.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_37.setParent(null);
    _jspx_th_fmt_message_37.setKey("dashboard.currentconversations.none");
    int _jspx_eval_fmt_message_37 = _jspx_th_fmt_message_37.doStartTag();
    if (_jspx_th_fmt_message_37.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_37);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_37);
    return false;
  }
}
