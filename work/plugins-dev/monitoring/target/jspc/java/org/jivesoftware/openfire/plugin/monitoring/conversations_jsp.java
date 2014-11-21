package org.jivesoftware.openfire.plugin.monitoring;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.MonitoringPlugin;
import org.jivesoftware.openfire.archive.ConversationManager;
import org.jivesoftware.openfire.archive.Conversation;
import org.jivesoftware.util.JiveGlobals;
import org.xmpp.packet.JID;
import org.jivesoftware.openfire.user.UserManager;
import java.net.URLEncoder;
import org.jivesoftware.util.StringUtils;

public final class conversations_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

    // Get handle on the Monitoring plugin
    MonitoringPlugin plugin = (MonitoringPlugin)XMPPServer.getInstance().getPluginManager().getPlugin(
            "monitoring");
    ConversationManager conversationManager = (ConversationManager)plugin.getModule(
            ConversationManager.class);

    XMPPServer server = XMPPServer.getInstance();
    UserManager userManager = UserManager.getInstance();

      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>Conversations</title>\r\n        <meta name=\"pageID\" content=\"active-conversations\"/>\r\n        <script src=\"/js/prototype.js\" type=\"text/javascript\"></script>\r\n        <script src=\"/js/scriptaculous.js\" type=\"text/javascript\"></script>\r\n        <script src=\"/plugins/monitoring/dwr/engine.js\" type=\"text/javascript\" ></script>\r\n        <script src=\"/plugins/monitoring/dwr/util.js\" type=\"text/javascript\" ></script>\r\n        <script src=\"/plugins/monitoring/dwr/interface/conversations.js\" type=\"text/javascript\"></script>\r\n    </head>\r\n    <body>\r\n\r\n<style type=\"text/css\">\r\n\t@import \"style/style.css\";\r\n</style>\r\n<script type=\"text/javascript\">\r\nDWREngine.setErrorHandler(handleError);\r\nwindow.onerror = handleError;\r\nfunction handleError() {\r\n    // swallow errors: probably caused by the server being down\r\n}\r\n\r\nvar peConversations = new PeriodicalExecuter(conversationUpdater, 10);\r\n\r\nfunction conversationUpdater() {\r\n    try {\r\n        conversations.getConversations(updateConversations, true);\r\n");
      out.write("    } catch(err) {\r\n        // swallow errors\r\n    }\r\n}\r\n\r\nfunction updateConversations(data) {\r\n    conversationsTable = $('conversations');\r\n    rows = conversationsTable.getElementsByTagName(\"tr\");\r\n    // loop over existing rows in the table\r\n    var rowsToDelete = new Array();\r\n    for (i = 0; i < rows.length; i++) {\r\n        // is this a conversation row?\r\n        if (rows[i].id == 'noconversations') {\r\n            rowsToDelete.push(i);\r\n        } else if (rows[i].id != '') {\r\n            // does the conversation exist in update we received?\r\n            convID = rows[i].id.replace('conversation-', '');\r\n            if (data[convID] != undefined) {\r\n\r\n                row = rows[i];\r\n                cells = row.getElementsByTagName('td');\r\n                conversation = data[convID];\r\n                if (cells[3].innerHTML != conversation.messageCount) {\r\n                    users = conversation.participant1 + '<br />' + conversation.participant2;\r\n                    cells[0].innerHTML = users;\r\n                    cells[1].innerHTML = conversation.duration;\r\n");
      out.write("                    cells[2].innerHTML = conversation.lastActivity;\r\n                    cells[3].innerHTML = conversation.messageCount;\r\n                    new Effect.Highlight(row, {duration: 3.0});\r\n                }\r\n            // doesn't exist in update, delete from table\r\n            } else {\r\n                rowsToDelete.push(i);\r\n            }\r\n        }\r\n    }\r\n\r\n    for (i=0; i<rowsToDelete.length; i++) {\r\n        conversationsTable.deleteRow(rowsToDelete[i]);\r\n    }\r\n\r\n\r\n    // then add any new conversations from the update\r\n    counter = 0;\r\n    for (var c in data) {\r\n        counter++;\r\n        // does this conversation already exist?\r\n        if ($('conversation-' + c) == undefined) {\r\n            conversation = data[c];\r\n            users = conversation.participant1 + '<br />' + conversation.participant2;\r\n            var newTR = document.createElement(\"tr\");\r\n            newTR.setAttribute('id', 'conversation-' + c)\r\n            conversationsTable.appendChild(newTR);\r\n            var TD = document.createElement(\"TD\");\r\n");
      out.write("            TD.innerHTML = users;\r\n            newTR.appendChild(TD);\r\n\r\n            TD = document.createElement(\"TD\");\r\n            TD.innerHTML = conversation.duration;\r\n            newTR.appendChild(TD);\r\n\r\n            TD = document.createElement(\"TD\");\r\n            TD.innerHTML = conversation.lastActivity;\r\n            newTR.appendChild(TD);\r\n\r\n            TD = document.createElement(\"TD\");\r\n            TD.innerHTML = conversation.messageCount;\r\n            newTR.appendChild(TD);\r\n        }\r\n    }\r\n\r\n    // update activeConversations number\r\n    $('activeConversations').innerHTML = counter;\r\n}\r\n\r\n</script>\r\n\r\n<!-- <a href=\"#\" onclick=\"conversationUpdater(); return false;\">click me</a> -->\r\n<p>\r\n    ");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("\r\n    <span id=\"activeConversations\">");
      out.print( conversationManager.getConversationCount() );
      out.write("</span\r\n</p>\r\n\r\n");

    Collection<Conversation> conversations = conversationManager.getConversations();

      out.write("\r\n\r\n\r\n<div class=\"jive-table\">\r\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" id=\"conversations\">\r\n<thead>\r\n    <tr>\r\n        <th nowrap>");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("</th>\r\n        <th nowrap>");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</th>\r\n        <th nowrap>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</th>\r\n        <th nowrap>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</th>\r\n    </tr>\r\n</thead>\r\n<tbody>\r\n    ");

        if (conversations.isEmpty()) {
    
      out.write("\r\n        <tr id=\"noconversations\">\r\n            <td colspan=\"4\">\r\n                ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\r\n            </td>\r\n        </tr>\r\n\r\n    ");
  } 
      out.write("\r\n    ");

        for (Conversation conversation : conversations) {
            Collection<JID> participants = conversation.getParticipants();
    
      out.write("\r\n    <tr id=\"conversation-");
      out.print( conversation.getConversationID());
      out.write("\">\r\n        <td>\r\n            ");
 if (conversation.getRoom() == null) { 
      out.write("\r\n                ");
 for (JID jid : participants) { 
      out.write("\r\n                    ");
 if (server.isLocal(jid) && userManager.isRegisteredUser(jid.getNode())) { 
      out.write("\r\n                        <a href=\"/user-properties.jsp?username=");
      out.print( jid.getNode() );
      out.write('"');
      out.write('>');
      out.print( jid );
      out.write("</a><br />\r\n                    ");
 } else { 
      out.write("\r\n                        ");
      out.print( jid.toBareJID() );
      out.write("<br/>\r\n                    ");
 } 
      out.write("\r\n                ");
 } 
      out.write("\r\n            ");
 } else { 
      out.write("\r\n                ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_6 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_6.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_6.setParent(null);
      _jspx_th_fmt_message_6.setKey("archive.group_conversation");
      int _jspx_eval_fmt_message_6 = _jspx_th_fmt_message_6.doStartTag();
      if (_jspx_eval_fmt_message_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_6 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_6.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_6.doInitBody();
        }
        do {
          out.write("\r\n                    ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_6);
          _jspx_th_fmt_param_0.setValue( "<a href='../../muc-room-occupants.jsp?roomJID=" + URLEncoder.encode(conversation.getRoom().toBareJID(), "UTF-8") + "'>" );
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          out.write("\r\n                    ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_1 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_1.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_6);
          _jspx_th_fmt_param_1.setValue( "</a>" );
          int _jspx_eval_fmt_param_1 = _jspx_th_fmt_param_1.doStartTag();
          if (_jspx_th_fmt_param_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
          out.write("\r\n                ");
          int evalDoAfterBody = _jspx_th_fmt_message_6.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_6 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_6);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_6);
      out.write("\r\n            ");
 } 
      out.write("\r\n        </td>\r\n        ");

            long duration = conversation.getLastActivity().getTime() -
                    conversation.getStartDate().getTime();
        
      out.write("\r\n        <td>");
      out.print( StringUtils.getTimeFromLong(duration) );
      out.write("</td>\r\n        <td>");
      out.print( JiveGlobals.formatTime(conversation.getLastActivity()) );
      out.write("</td>\r\n        <td>");
      out.print( conversation.getMessageCount() );
      out.write("</td>\r\n    </tr>\r\n    ");
  } 
      out.write("\r\n</tbody>\r\n</table>\r\n</div>\r\n\r\n</body>\r\n</html>");
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
    _jspx_th_fmt_message_0.setKey("archive.conversations");
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
    _jspx_th_fmt_message_1.setKey("archive.conversations.users");
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
    _jspx_th_fmt_message_2.setKey("archive.conversations.duration");
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
    _jspx_th_fmt_message_3.setKey("archive.conversations.lastactivity");
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
    _jspx_th_fmt_message_4.setKey("archive.conversations.messages");
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
    _jspx_th_fmt_message_5.setKey("archive.converations.no_conversations");
    int _jspx_eval_fmt_message_5 = _jspx_th_fmt_message_5.doStartTag();
    if (_jspx_th_fmt_message_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
    return false;
  }
}
