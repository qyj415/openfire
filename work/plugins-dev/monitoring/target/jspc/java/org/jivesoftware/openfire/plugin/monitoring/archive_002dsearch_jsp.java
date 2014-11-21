package org.jivesoftware.openfire.plugin.monitoring;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.MonitoringPlugin;
import org.jivesoftware.openfire.archive.ArchiveSearch;
import org.jivesoftware.openfire.archive.ArchiveSearcher;
import org.jivesoftware.openfire.archive.Conversation;
import org.jivesoftware.openfire.archive.ConversationManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.user.UserNameManager;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.*;
import org.xmpp.packet.JID;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public final class archive_002dsearch_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    public TreeMap<String, JID> getParticipants(Conversation conv) {
        final TreeMap<String, JID> participants = new TreeMap<String, JID>();
        for (JID jid : conv.getParticipants()) {
            try {
                if (jid == null) {
                    continue;
                }
                String identifier = jid.toBareJID();
                try {
                    identifier = UserNameManager.getUserName(jid, jid.toBareJID());
                } catch (UserNotFoundException e) {
                    // Ignore
                }
                participants.put(identifier, jid);
            }
            catch (Exception e) {
                Log.error(e);
            }

        }

        return participants;
    }

    public String getFormattedDate(Conversation conv) {
        return JiveGlobals.formatDate(conv.getStartDate());
    }

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_param_value_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_fmt_message_key_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_fmt_message_key = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_fmt_param_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_fmt_message_key_nobody.release();
    _jspx_tagPool_fmt_message_key.release();
    _jspx_tagPool_fmt_param_value_nobody.release();
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
      			"/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

    // Get handle on the Monitoring plugin
    MonitoringPlugin plugin = (MonitoringPlugin) XMPPServer.getInstance().getPluginManager().getPlugin(
            "monitoring");
    ArchiveSearcher archiveSearcher = (ArchiveSearcher) plugin.getModule(
            ArchiveSearcher.class);

    ConversationManager conversationManager = (ConversationManager) plugin.getModule(
            ConversationManager.class);


    boolean submit = request.getParameter("submitForm") != null;
    if (!submit) {
        submit = request.getParameter("parseRange") != null;
    }
    String query = request.getParameter("keywords");

    Collection<Conversation> conversations = null;


    String participant1 = request.getParameter("participant1");
    String participant2 = request.getParameter("participant2");

    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");

    String anyText = LocaleUtils.getLocalizedString("archive.settings.any", "monitoring");

    int start = 0;
    int range = 15;
    int numPages = 1;
    int curPage = (start / range) + 1;

    if (anyText.equals(participant1)) {
        participant1 = null;
    }

    if (anyText.equals(participant2)) {
        participant2 = null;
    }

    if (anyText.equals(startDate)) {
        startDate = null;
    }

    if (anyText.equals(endDate)) {
        endDate = null;
    }

    if (submit) {
        UserManager userManager = UserManager.getInstance();
        ArchiveSearch search = new ArchiveSearch();
        JID participant1JID = null;
        JID participant2JID = null;

        String serverName = XMPPServer.getInstance().getServerInfo().getXMPPDomain();
        if (participant1 != null && participant1.length() > 0) {
            int position = participant1.lastIndexOf("@");
            if (position > -1) {
                String node = participant1.substring(0, position);
                participant1JID = new JID(JID.escapeNode(node) + participant1.substring(position));
            } else {
                participant1JID = new JID(JID.escapeNode(participant1), serverName, null);
            }
        }

        if (participant2 != null && participant2.length() > 0) {
            int position = participant2.lastIndexOf("@");
            if (position > -1) {
                String node = participant2.substring(0, position);
                participant2JID = new JID(JID.escapeNode(node) + participant2.substring(position));
            } else {
                participant2JID = new JID(JID.escapeNode(participant2), serverName, null);
            }
        }

        if (startDate != null && startDate.length() > 0) {
            DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            try {
                Date date = formatter.parse(startDate);
                search.setDateRangeMin(date);
            }
            catch (Exception e) {
                // TODO: mark as an error in the JSP instead of logging..
                Log.error(e);
            }
        }

        if (endDate != null && endDate.length() > 0) {
            DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            try {
                Date date = formatter.parse(endDate);
                // The user has chosen an end date and expects that any conversation
                // that falls on that day will be included in the search results. For
                // example, say the user choose 6/17/2006 as an end date. If a conversation
                // occurs at 5:33 PM that day, it should be included in the results. In
                // order to make this possible, we need to make the end date one millisecond
                // before the next day starts.
                date = new Date(date.getTime() + JiveConstants.DAY - 1);
                search.setDateRangeMax(date);
            }
            catch (Exception e) {
                // TODO: mark as an error in the JSP instead of logging..
                Log.error(e);
            }
        }

        if (query != null && query.length() > 0) {
            search.setQueryString(query);
        }

        if (participant1JID != null && participant2JID != null) {
            search.setParticipants(participant1JID, participant2JID);
        } else if (participant1JID != null) {
            search.setParticipants(participant1JID);
        } else if (participant2JID != null) {
            search.setParticipants(participant2JID);
        }

        start = ParamUtils.getIntParameter(request, "start", 0);
        range = 15;


        conversations = archiveSearcher.search(search);

        numPages = (int) Math.ceil((double) conversations.size() / (double) range);
        curPage = (start / range) + 1;
    }

    boolean isArchiveEnabled = conversationManager.isArchivingEnabled();

      out.write("\r\n\r\n<html>\r\n<head>\r\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n<meta name=\"pageID\" content=\"archive-search\"/>\r\n<script src=\"/js/prototype.js\" type=\"text/javascript\"></script>\r\n<script src=\"/js/scriptaculous.js\" type=\"text/javascript\"></script>\r\n<script src=\"dwr/engine.js\" type=\"text/javascript\"></script>\r\n<script src=\"dwr/util.js\" type=\"text/javascript\"></script>\r\n<script src=\"dwr/interface/conversations.js\" type=\"text/javascript\"></script>\r\n<script type=\"text/javascript\" language=\"javascript\" src=\"scripts/tooltips/domLib.js\"></script>\r\n<script type=\"text/javascript\" language=\"javascript\" src=\"scripts/tooltips/domTT.js\"></script>\r\n\r\n<style type=\"text/css\">@import url( /js/jscalendar/calendar-win2k-cold-1.css );</style>\r\n<script type=\"text/javascript\" src=\"/js/jscalendar/calendar.js\"></script>\r\n<script type=\"text/javascript\" src=\"/js/jscalendar/i18n.jsp\"></script>\r\n<script type=\"text/javascript\" src=\"/js/jscalendar/calendar-setup.js\"></script>\r\n\r\n<script type=\"text/javascript\">\r\n    function hover(oRow) {\r\n        oRow.style.background = \"#A6CAF0\";\r\n        oRow.style.cursor = \"pointer\";\r\n");
      out.write("    }\r\n\r\n    function noHover(oRow) {\r\n        oRow.style.background = \"white\";\r\n    }\r\n\r\n    function viewConversation(conversationID) {\r\n        window.frames['view'].location.href = \"conversation-viewer.jsp?conversationID=\" + conversationID;\r\n    }\r\n\r\n    function submitFormAgain(start, range){\r\n        document.f.start.value = start;\r\n        document.f.range.value = range;\r\n        document.f.parseRange.value = \"true\";\r\n        document.f.submit();\r\n    }\r\n</script>\r\n<style type=\"text/css\">\r\n    .small-label {\r\n        font-size: 11px;\r\n        font-weight: bold;\r\n        font-family: Verdana, Arial, sans-serif;\r\n    }\r\n\r\n    .small-label-no-bold {\r\n        font-size: 11px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n    }\r\n\r\n\r\n    .small-label-with-padding {\r\n        font-size: 12px;\r\n        font-weight: bold;\r\n        font-family: Verdana, Arial, sans-serif;\r\n    }\r\n\r\n\r\n    .small-text {\r\n        font-size: 11px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n        line-height: 11px;\r\n    }\r\n");
      out.write("\r\n    .very-small-label {\r\n        font-size: 10px;\r\n        font-weight: bold;\r\n        font-family: Verdana, Arial, sans-serif;\r\n        padding-right:5px;\r\n    }\r\n\r\n\r\n    .stat {\r\n        margin: 0px 0px 8px 0px;\r\n        border: 1px solid #cccccc;\r\n        -moz-border-radius: 3px;\r\n    }\r\n\r\n    .stat td table {\r\n        margin: 5px 10px 5px 10px;\r\n    }\r\n    .stat div.verticalrule {\r\n        display: block;\r\n        width: 1px;\r\n        height: 110px;\r\n        background-color: #cccccc;\r\n        overflow: hidden;\r\n        margin-left: 3px;\r\n        margin-right: 3px;\r\n    }\r\n\r\n    .conversation-body {\r\n        color: black;\r\n        font-size: 11px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n    }\r\n\r\n    .conversation-label1 {\r\n        color: blue;\r\n        font-size: 10px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n    }\r\n\r\n    .conversation-label2 {\r\n        color: red;\r\n        font-size: 10px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n    }\r\n\r\n    .conversation-label3 {\r\n        color: orchid;\r\n");
      out.write("        font-size: 10px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n    }\r\n\r\n    .conversation-label4 {\r\n        color: black;\r\n        font-size: 10px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n    }\r\n\r\n    .conversation-table {\r\n        font-family: Verdana, Arial, sans-serif;\r\n        font-size: 11px;\r\n    }\r\n    .conversation-table td {\r\n        font-size: 11px;\r\n        padding: 5px 5px 5px 5px;\r\n    }\r\n\r\n    .light-gray-border {\r\n        border-color: #bbb;\r\n        border-style: solid;\r\n        border-width: 1px 1px 1px 1px;\r\n    }\r\n\r\n    .light-gray-border-bottom {\r\n        border-color: #bbb;\r\n        border-style: solid;\r\n        border-width: 0px 0px 1px 0px;\r\n    }\r\n\r\n    .small-description {\r\n        font-size: 11px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n        color: #666;\r\n    }\r\n\r\n   .description {\r\n        font-size: 12px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n        color: #666;\r\n    }\r\n\r\n\r\n      .pagination {\r\n        border-color: #bbb;\r\n        border-style: solid;\r\n");
      out.write("        border-width: 0px 0px 1px 0px;\r\n        font-size: 10px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n\r\n    }\r\n\r\n    .content {\r\n        border-color: #bbb;\r\n        border-style: solid;\r\n        border-width: 0px 0px 1px 0px;\r\n    }\r\n\r\n    /* Default DOM Tooltip Style */\r\n    div.domTT {\r\n        border: 1px solid #bbb;\r\n        background-color: #FFFBE2;\r\n        font-family: Arial, Helvetica sans-serif;\r\n        font-size: 9px;\r\n        padding: 5px;\r\n    }\r\n\r\n    div.domTT .caption {\r\n        font-family: serif;\r\n        font-size: 12px;\r\n        font-weight: bold;\r\n        padding: 1px 2px;\r\n        color: #FFFFFF;\r\n    }\r\n\r\n    div.domTT .contents {\r\n        font-size: 12px;\r\n        font-family: sans-serif;\r\n        padding: 3px 2px;\r\n    }\r\n\r\n    .textfield {\r\n        font-size: 11px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n        height: 20px;\r\n        background: #efefef;\r\n    }\r\n\r\n    .keyword-field {\r\n        font-size: 11px;\r\n        font-family: Verdana, Arial, sans-serif;\r\n");
      out.write("        height: 20px;\r\n    }\r\n\r\n    #searchResults {\r\n        margin: 10px 0px 10px 0px;\r\n    }\r\n\r\n    #searchResults h3 {\r\n        font-size: 14px;\r\n        padding: 0px;\r\n        margin: 0px 0px 2px 0px;\r\n        color: #555555;\r\n    }\r\n\r\n    #searchResults p.resultDescription {\r\n        margin: 0px 0px 12px 0px;\r\n    }\r\n</style>\r\n\r\n<style type=\"text/css\" title=\"setupStyle\" media=\"screen\">\r\n\t@import \"../../style/lightbox.css\";\r\n</style>\r\n\r\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"../../js/lightbox.js\"></script>\r\n\r\n<script type=\"text/javascript\">\r\n    var selectedConversation;\r\n\r\n    function showConversation(conv) {\r\n        selectedConversation = conv;\r\n        conversations.getConversationInfo(showConv, conv, true);\r\n    }\r\n\r\n    function showConv(results) {\r\n        $('chat-viewer-empty').style.display = 'none';\r\n        $('chat-viewer').style.display = '';\r\n        if (results.allParticipants != null) {\r\n            $('con-participant1').innerHTML = results.allParticipants.length;\r\n            $('con-participant2').innerHTML = '(<a href=\"#\" onclick=\"showOccupants(' + results.conversationID + ', 0);return false;\">view</a>)';\r\n");
      out.write("        }\r\n        else {\r\n            $('con-participant1').innerHTML = results.participant1 + ',';\r\n            $('con-participant2').innerHTML = results.participant2;\r\n        }\r\n        $('con-chatTime').innerHTML = results.date;\r\n        $('conversation-body').innerHTML = results.body;\r\n        $('con-noMessages').innerHTML = results.messageCount;\r\n        $('con-duration').innerHTML = results.duration;\r\n        ");
 if (conversationManager.isArchivingEnabled()) { 
      out.write("\r\n            $('con-chat-link').innerHTML = '<a href=\"conversation?conversationID='+selectedConversation+'\" class=\"very-small-label\"  style=\"text-decoration:none\" target=_blank>View PDF</a>';\r\n        ");
 } else { 
      out.write("\r\n            Element.hide('pdf-image');\r\n        ");
 } 
      out.write("\r\n    }\r\n\r\n    function showOccupants(conversationID, start) {\r\n        var aref = document.getElementById('lbmessage');\r\n        aref.href = 'archive-conversation-participants.jsp?conversationID=' + conversationID + '&start=' + start;\r\n        var lbCont = document.getElementById('lbContent');\r\n        if (lbCont != null) {\r\n            document.getElementById('lightbox').removeChild(lbCont);\r\n        }\r\n        lb = new lightbox(aref);\r\n        lb.activate();\r\n    }\r\n\r\n    function grayOut(ele) {\r\n        if (ele.value == 'Any') {\r\n            ele.style.backgroundColor = \"#FFFBE2\";\r\n        }\r\n        else {\r\n            ele.style.backgroundColor = \"#ffffff\";\r\n        }\r\n    }\r\n</script>\r\n<script type=\"text/javascript\" src=\"/js/behaviour.js\"></script>\r\n<script type=\"text/javascript\">\r\n    // Add a nice little rollover effect to any row in a jive-table object. This will help\r\n    // visually link left and right columns.\r\n\r\n    var selectedElement;\r\n\r\n    var myrules = {\r\n        '.conversation-table TR' : function(el) {\r\n");
      out.write("            var backgroundColor;\r\n            var selected = false;\r\n            el.onmouseover = function() {\r\n\r\n                if (selectedElement != null && selectedElement == this) {\r\n                    return;\r\n                }\r\n                backgroundColor = this.style.backgroundColor;\r\n                this.style.backgroundColor = '#dedede';\r\n                this.style.cursor = 'pointer';\r\n            }\r\n\r\n            el.onmouseout = function() {\r\n                if (selectedElement != this) {\r\n                    this.style.backgroundColor = backgroundColor;\r\n                }\r\n            }\r\n\r\n            el.onmousedown = function() {\r\n                this.style.backgroundColor = '#fffBc2';\r\n                if (selectedElement != null) {\r\n                    selectedElement.style.backgroundColor = backgroundColor;\r\n                }\r\n                selectedElement = this;\r\n            }\r\n        }\r\n    };\r\n\r\n    var textfieldRules = {\r\n        '.textfield' : function(el) {\r\n            el.onblur = function() {\r\n");
      out.write("                var va = el.value;\r\n                if (va.length == 0 || va == 'Any') {\r\n                    this.style.backgroundColor = '#efefef';\r\n                    el.value = \"");
      out.print( anyText);
      out.write("\";\r\n                }\r\n                else {\r\n                    this.style.backgroundColor = '#ffffff';\r\n                }\r\n            }\r\n\r\n            el.onfocus = function() {\r\n                var va = el.value;\r\n                if (va == 'Any') {\r\n                    this.style.backgroundColor = '#ffffff';\r\n                    el.value = \"\";\r\n                }\r\n            }\r\n        }\r\n    };\r\n\r\n    Behaviour.register(textfieldRules);\r\n    Behaviour.register(myrules);\r\n</script>\r\n<style type=\"text/css\">\r\n\t@import \"style/style.css\";\r\n</style>\r\n</head>\r\n<body>\r\n\r\n<a href=\"archive-conversation-participants.jsp?conversationID=\" id=\"lbmessage\" title=\"");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\" style=\"display:none;\"></a>\r\n\r\n<form action=\"archive-search.jsp\" name=\"f\">\r\n<!-- Search Table -->\r\n<div>\r\n<table class=\"stat\">\r\n<tr valign=\"top\">\r\n<td>\r\n    <table>\r\n        <tr>\r\n            <td colspan=\"3\">\r\n                <img src=\"images/icon_participants.gif\" align=\"absmiddle\" alt=\"\" style=\"margin-right: 4px;\"/>\r\n                <b>");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</b>\r\n                <a onmouseover=\"domTT_activate(this, event, 'content',\r\n                    '");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("',\r\n                    'trail', true, 'direction', 'northeast', 'width', '220');\"><img src=\"images/icon_help_14x14.gif\" alt=\"\" vspace=\"2\" align=\"texttop\"/></a>\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td>\r\n                <input type=\"text\" size=\"22\" name=\"participant1\" value=\"");
      out.print( participant1 != null ? participant1 :
                LocaleUtils.getLocalizedString("archive.search.participants.any", "monitoring") );
      out.write("\" class=\"textfield\"/>\r\n            </td>\r\n\r\n        </tr>\r\n        <tr>\r\n            <td>\r\n                <input type=\"text\" size=\"22\" name=\"participant2\" value=\"");
      out.print( participant2 != null ? participant2 : anyText );
      out.write("\" class=\"textfield\"/>\r\n            </td>\r\n\r\n        </tr>\r\n    </table>\r\n</td>\r\n<td width=\"0\" height=\"100%\" valign=\"middle\">\r\n    <div class=\"verticalrule\"></div>\r\n</td>\r\n<td>\r\n\r\n    <table>\r\n        <tr>\r\n            <td colspan=\"3\">\r\n                <img src=\"images/icon_daterange.gif\" align=\"absmiddle\" alt=\"\" style=\"margin: 0px 4px 0px 2px;\"/>\r\n                <b>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</b>\r\n                <a onmouseover=\"domTT_activate(this, event, 'content',\r\n                    '");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("',\r\n                    'trail', true, 'direction', 'northeast', 'width', '220');\"><img src=\"images/icon_help_14x14.gif\" vspace=\"2\" align=\"texttop\"/></a>\r\n            </td>\r\n        </tr>\r\n        <tr valign=\"top\">\r\n            <td>");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</td>\r\n            <td>\r\n                <input type=\"text\" id=\"startDate\" name=\"startDate\" size=\"13\"\r\n                       value=\"");
      out.print( startDate != null ? startDate :
                       LocaleUtils.getLocalizedString("archive.search.daterange.any", "monitoring"));
      out.write("\" class=\"textfield\"/><br/>\r\n                <span class=\"jive-description\">");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</span>\r\n            </td>\r\n            <td>\r\n                <img src=\"images/icon_calendarpicker.gif\" vspace=\"3\" id=\"startDateTrigger\">\r\n            </td>\r\n        </tr>\r\n        <tr valign=\"top\">\r\n            <td>");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</td>\r\n            <td>\r\n                <input type=\"text\" id=\"endDate\" name=\"endDate\" size=\"13\"\r\n                       value=\"");
      out.print( endDate != null ? endDate :
                       LocaleUtils.getLocalizedString("archive.search.daterange.any", "monitoring") );
      out.write("\" class=\"textfield\"/><br/>\r\n                <span class=\"jive-description\">");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("</span>\r\n            </td>\r\n            <td>\r\n                <img src=\"images/icon_calendarpicker.gif\" vspace=\"3\" id=\"endDateTrigger\">\r\n            </td>\r\n        </tr>\r\n    </table>\r\n\r\n\r\n</td>\r\n<td>\r\n    <td width=\"0\" height=\"100%\" valign=\"middle\">\r\n        <div class=\"verticalrule\"></div>\r\n    </td>\r\n</td>\r\n<td>\r\n    <table>\r\n        <tr valign=\"top\">\r\n            <td>\r\n                <img src=\"images/icon_keywords.gif\" align=\"absmiddle\" alt=\"\" style=\"margin-right: 4px;\"/>\r\n                <b>");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</b> ");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td>\r\n                ");
 if(isArchiveEnabled){
      out.write("\r\n                <input type=\"text\" name=\"keywords\" size=\"35\" class=\"keyword-field\" value=\"");
      out.print( query != null ? query : "");
      out.write("\"/>\r\n                ");
 } else { 
      out.write("\r\n                    ");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("\r\n                ");
 } 
      out.write("\r\n            </td>\r\n        </tr>\r\n    </table>\r\n</td>\r\n</tr>\r\n</table>\r\n</div>\r\n<input type=\"submit\" name=\"submitForm\" value=\"");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\" class=\"small-text\"/>\r\n\r\n\r\n<input type=\"hidden\" name=\"start\"  />\r\n<input type=\"hidden\" name=\"range\"  />\r\n<input type=\"hidden\" name=\"parseRange\" />\r\n</form>\r\n\r\n");

    // Code for the searches.


      out.write("\r\n\r\n");
 if (conversations != null && conversations.size() > 0) { 
      out.write("\r\n<table id=\"searchResults\" width=\"100%\" style=\"");
      out.print( conversations == null ? "display:none;" : "" );
      out.write("\">\r\n    <tr>\r\n        <td colspan=\"2\">\r\n            <h3>");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write(' ');
      out.print( conversations.size() );
      out.write("</h3>\r\n            <p class=\"resultDescription\">\r\n                ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_15 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_15.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_15.setParent(null);
      _jspx_th_fmt_message_15.setKey("archive.search.results.description");
      int _jspx_eval_fmt_message_15 = _jspx_th_fmt_message_15.doStartTag();
      if (_jspx_eval_fmt_message_15 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_15 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_15.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_15.doInitBody();
        }
        do {
          out.write("\r\n                    ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_2 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_2.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_15);
          _jspx_th_fmt_param_2.setValue( conversations.size());
          int _jspx_eval_fmt_param_2 = _jspx_th_fmt_param_2.doStartTag();
          if (_jspx_th_fmt_param_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_2);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_2);
          out.write("\r\n                ");
          int evalDoAfterBody = _jspx_th_fmt_message_15.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_15 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_15);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_15);
      out.write("\r\n            </p>\r\n        </td>\r\n    </tr>\r\n    <tr valign=\"top\">\r\n        <td width=\"300\">\r\n            <!-- Search Result Table -->\r\n            <table cellspacing=\"0\" class=\"light-gray-border\">\r\n                <tr class=\"light-gray-border-bottom\">\r\n                    <td class=\"light-gray-border-bottom\">\r\n                        ");

                            int endPoint = (start + range) > conversations.size() ? conversations.size() : (start + range);
                        
      out.write("\r\n                        <span class=\"small-label-with-padding\">\r\n                            ");
      out.print( start + 1);
      out.write(' ');
      out.write('-');
      out.write(' ');
      out.print( endPoint );
      out.write(' ');
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\r\n                            ");
      out.print( conversations.size());
      out.write("</span>\r\n                    </td>\r\n                    <td align=\"right\" nowrap class=\"light-gray-border-bottom\" style=\"padding-right:3px;\">\r\n                          ");
  if (numPages > 1) { 
      out.write("\r\n\r\n                        <p>\r\n                            ");
  int num = 5 + curPage;
                                int s = curPage - 1;
                                if (s > 5) {
                                    s -= 5;
                                }
                                if (s < 5) {
                                    s = 0;
                                }
                                if (s > 2) {
                            
      out.write("\r\n                            <a href=\"javascript:submitFormAgain('0', '");
      out.print( range);
      out.write("');\">1</a> ...\r\n\r\n                            ");

                                }
                                int i = 0;
                                for (i = s; i < numPages && i < num; i++) {
                                    String sep = ((i + 1) < numPages) ? " " : "";
                                    boolean isCurrent = (i + 1) == curPage;
                            
      out.write("\r\n                            <a href=\"javascript:submitFormAgain('");
      out.print( (i*range) );
      out.write("', '");
      out.print( range );
      out.write("');\"\r\n                               class=\"");
      out.print( ((isCurrent) ? "small-label" : "small-label-no-bold") );
      out.write("\"\r\n                                >");
      out.print( (i + 1) );
      out.write("</a>");
      out.print( sep );
      out.write("\r\n\r\n                            ");
  } 
      out.write("\r\n\r\n                            ");
  if (i < numPages) { 
      out.write("\r\n\r\n                            ... <a href=\"javascript:submitFormAgain('");
      out.print( ((numPages-1)*range) );
      out.write("', '");
      out.print( range );
      out.write("');\">");
      out.print( numPages );
      out.write("</a>\r\n\r\n                            ");
  } 
      out.write("\r\n                        </p>\r\n\r\n                        ");
  } else { 
      out.write("\r\n                        &nbsp;\r\n                        ");
  } 
      out.write("\r\n\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td colspan=\"2\" align=\"left\">\r\n                        <div style=\"HEIGHT:300px;width:285px;OVERFLOW:auto\">\r\n                            <table cellpadding=\"3\" cellspacing=\"0\" width=\"100%\" class=\"conversation-table\">\r\n\r\n                                ");

                                    int i = 1;
                                    int end = start + range + 1;
                                    for (Conversation conversation : conversations) {
                                        if(i == end){
                                            break;
                                        }
                                        else if(i < start){
                                            i++;
                                            continue;
                                        }
                                        Map<String, JID> participants = getParticipants(conversation);
                                        String color = "#FFFFFF";
                                        if (i % 2 == 0) {
                                            color = "#F0F0F0";
                                        }

                                
      out.write("\r\n                                <tr id=\"");
      out.print( conversation.getConversationID());
      out.write("\" valign=\"top\" bgcolor=\"");
      out.print( color);
      out.write("\" onclick=\"showConversation('");
      out.print( conversation.getConversationID() );
      out.write("'); return false;\">\r\n                                    <td><b>");
      out.print( i );
      out.write(".</b></td>\r\n                                    <td width=\"98%\">\r\n                                        ");
 if (conversation.getRoom() == null) { 
      out.write("\r\n                                            ");

                                                Iterator iter = participants.keySet().iterator();
                                                while (iter.hasNext()) {
                                                    String name = (String)iter.next();
                                            
      out.write("\r\n                                            ");
      out.print( name);
      out.write("<br/>\r\n                                            ");
 } 
      out.write("\r\n                                        ");
 } else { 
      out.write("\r\n                                            <i>");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_17 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_17.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_17.setParent(null);
      _jspx_th_fmt_message_17.setKey("archive.search.group_conversation");
      int _jspx_eval_fmt_message_17 = _jspx_th_fmt_message_17.doStartTag();
      if (_jspx_eval_fmt_message_17 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_17 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_17.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_17.doInitBody();
        }
        do {
          out.write("\r\n                                                ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_3 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_3.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_17);
          _jspx_th_fmt_param_3.setValue( conversation.getRoom().getNode() );
          int _jspx_eval_fmt_param_3 = _jspx_th_fmt_param_3.doStartTag();
          if (_jspx_th_fmt_param_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_3);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_3);
          out.write("\r\n                                            ");
          int evalDoAfterBody = _jspx_th_fmt_message_17.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_17 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_17);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_17);
      out.write("</i><br>\r\n                                            ");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write(' ');
      out.print( conversation.getParticipants().size() );
      out.write("\r\n                                        ");
 } 
      out.write("\r\n                                    </td>\r\n                                    <td align=\"right\" nowrap>\r\n                                        ");
      out.print( getFormattedDate(conversation));
      out.write("\r\n                                    </td>\r\n                                </tr>\r\n                                ");
 i++;
                                } 
      out.write("\r\n                            </table>\r\n                        </div>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n        </td>\r\n        <td>\r\n\r\n\r\n             <!-- Conversation Viewer (empty) -->\r\n            <div id=\"chat-viewer-empty\">\r\n                <table class=\"light-gray-border\" width=\"100%\" style=\"height: 323px;\">\r\n                    <tr>\r\n                        <td align=\"center\" valign=\"top\" bgcolor=\"#fafafa\">\r\n                            <br>\r\n                            <p>Select a conversation to the left to view details.</p></td>\r\n                    </tr>\r\n                </table>\r\n            </div>\r\n\r\n            <!-- Conversation Viewer -->\r\n            <div id=\"chat-viewer\" style=\"display:none;\">\r\n                <table class=\"light-gray-border\" cellspacing=\"0\">\r\n                    <tr valign=\"top\">\r\n                        <td width=\"99%\" bgcolor=\"#f0f0f0\" class=\"light-gray-border-bottom\" style=\"padding: 3px 2px 4px 5px;\">\r\n                            <span class=\"small-label\">");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("</span>&nbsp;\r\n                            <span class=\"small-text\" id=\"con-participant1\"></span>&nbsp;\r\n                            <span class=\"small-text\" id=\"con-participant2\"></span><br/>\r\n                            <span class=\"small-label\">");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("</span>&nbsp;\r\n                            <span class=\"small-text\" id=\"con-noMessages\"></span><br/>\r\n                            <span class=\"small-label\">");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</span>&nbsp;\r\n                            <span class=\"small-text\" id=\"con-chatTime\"></span><br/>\r\n                            <span class=\"small-label\">");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("</span>&nbsp;\r\n                            <span class=\"small-text\" id=\"con-duration\"></span>\r\n                        </td>\r\n                        <td id=\"pdf-image\" width=\"1%\" bgcolor=\"#f0f0f0\" nowrap align=\"right\" class=\"light-gray-border-bottom\" style=\"padding: 4px 3px 3px 0px;\">\r\n                            <img src=\"images/icon_pdf.gif\" alt=\"\" align=\"texttop\" border=\"0\" /> <span id=\"con-chat-link\"></span>\r\n                        </td>\r\n\r\n                    </tr>\r\n                    <tr>\r\n                        <td colspan=\"2\">\r\n                            <div class=\"conversation\" id=\"conversation-body\" style=\"HEIGHT:241px;width:100%;OVERFLOW:auto\">\r\n                            </div>\r\n                        </td>\r\n                    </tr>\r\n                </table>\r\n            </div>\r\n\r\n\r\n        </td>\r\n    </tr>\r\n</table>\r\n\r\n");
 } else if(submit) { 
      out.write("\r\n<span class=\"description\">\r\n");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("\r\n</span>\r\n");
 } 
      out.write("\r\n\r\n\r\n<script type=\"text/javascript\">\r\n    grayOut(f.participant1);\r\n    grayOut(f.participant2);\r\n    grayOut(f.startDate);\r\n    grayOut(f.endDate);\r\n\r\n     function catcalc(cal) {\r\n        var endDateField = $('endDate');\r\n        var startDateField = $('startDate');\r\n\r\n        var endTime = new Date(endDateField.value);\r\n        var startTime = new Date(startDateField.value);\r\n        if(endTime.getTime() < startTime.getTime()){\r\n            alert(\"");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("\");\r\n            startDateField.value = \"");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("\";\r\n        }\r\n    }\r\n\r\n    Calendar.setup(\r\n    {\r\n        inputField  : \"startDate\",         // ID of the input field\r\n        ifFormat    : \"%m/%d/%y\",    // the date format\r\n        button      : \"startDateTrigger\",       // ID of the button\r\n        onUpdate    :  catcalc\r\n    });\r\n\r\n    Calendar.setup(\r\n    {\r\n        inputField  : \"endDate\",         // ID of the input field\r\n        ifFormat    : \"%m/%d/%y\",    // the date format\r\n        button      : \"endDateTrigger\",       // ID of the button\r\n        onUpdate    :  catcalc\r\n    });\r\n</script>\r\n</body>\r\n</html>\r\n\r\n");
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
    _jspx_th_fmt_message_0.setKey("archive.search.title");
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
    _jspx_th_fmt_message_1.setKey("archive.group_conversation.participants");
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
    _jspx_th_fmt_message_2.setKey("archive.search.participants");
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
    _jspx_th_fmt_message_3.setKey("archive.search.participants.tooltip");
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
    _jspx_th_fmt_message_4.setKey("archive.search.daterange");
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
    _jspx_th_fmt_message_5.setKey("archive.search.daterange.tooltip");
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
    _jspx_th_fmt_message_6.setKey("archive.search.daterange.start");
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
    _jspx_th_fmt_message_7.setKey("archive.search.daterange.format");
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
    _jspx_th_fmt_message_8.setKey("archive.search.daterange.end");
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
    _jspx_th_fmt_message_9.setKey("archive.search.daterange.format");
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
    _jspx_th_fmt_message_10.setKey("archive.search.keywords");
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
    _jspx_th_fmt_message_11.setKey("archive.search.keywords.optional");
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
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_12 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_12.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_12.setParent(null);
    _jspx_th_fmt_message_12.setKey("archive.search.keywords.disabled");
    int _jspx_eval_fmt_message_12 = _jspx_th_fmt_message_12.doStartTag();
    if (_jspx_eval_fmt_message_12 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fmt_message_12 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fmt_message_12.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fmt_message_12.doInitBody();
      }
      do {
        out.write("\r\n                        ");
        if (_jspx_meth_fmt_param_0(_jspx_th_fmt_message_12, _jspx_page_context))
          return true;
        out.write("\r\n                        ");
        if (_jspx_meth_fmt_param_1(_jspx_th_fmt_message_12, _jspx_page_context))
          return true;
        out.write("\r\n                    ");
        int evalDoAfterBody = _jspx_th_fmt_message_12.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fmt_message_12 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_fmt_message_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_12);
      return true;
    }
    _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_12);
    return false;
  }

  private boolean _jspx_meth_fmt_param_0(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_12, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_12);
    _jspx_th_fmt_param_0.setValue(new String("<a href='archiving-settings.jsp'>"));
    int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
    if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
    return false;
  }

  private boolean _jspx_meth_fmt_param_1(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_12, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_1 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_1.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_12);
    _jspx_th_fmt_param_1.setValue(new String("</a>"));
    int _jspx_eval_fmt_param_1 = _jspx_th_fmt_param_1.doStartTag();
    if (_jspx_th_fmt_param_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
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
    _jspx_th_fmt_message_13.setKey("archive.search.submit");
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
    _jspx_th_fmt_message_14.setKey("archive.search.results");
    int _jspx_eval_fmt_message_14 = _jspx_th_fmt_message_14.doStartTag();
    if (_jspx_th_fmt_message_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_14);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_14);
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
    _jspx_th_fmt_message_16.setKey("archive.search.results.xofy");
    int _jspx_eval_fmt_message_16 = _jspx_th_fmt_message_16.doStartTag();
    if (_jspx_th_fmt_message_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_16);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_16);
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
    _jspx_th_fmt_message_18.setKey("archive.search.results.participants");
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
    _jspx_th_fmt_message_19.setKey("archive.search.results.participants");
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
    _jspx_th_fmt_message_20.setKey("archive.search.results.messagecount");
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
    _jspx_th_fmt_message_21.setKey("archive.search.results.date");
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
    _jspx_th_fmt_message_22.setKey("archive.search.results.duration");
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
    _jspx_th_fmt_message_23.setKey("archive.search.results.none");
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
    _jspx_th_fmt_message_24.setKey("archive.search.daterange.error");
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
    _jspx_th_fmt_message_25.setKey("archive.search.daterange.any");
    int _jspx_eval_fmt_message_25 = _jspx_th_fmt_message_25.doStartTag();
    if (_jspx_th_fmt_message_25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
    return false;
  }
}
