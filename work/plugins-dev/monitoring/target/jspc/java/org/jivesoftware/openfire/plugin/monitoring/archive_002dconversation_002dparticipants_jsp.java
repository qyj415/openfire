package org.jivesoftware.openfire.plugin.monitoring;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.MonitoringPlugin;
import org.jivesoftware.openfire.archive.Conversation;
import org.jivesoftware.openfire.archive.ConversationManager;
import org.jivesoftware.openfire.archive.ConversationParticipation;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.NotFoundException;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.packet.JID;
import java.util.*;

public final class archive_002dconversation_002dparticipants_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

    long conversationID = ParamUtils.getLongParameter(request, "conversationID", -1);
    int start = ParamUtils.getIntParameter(request, "start", 0);

    // Get handle on the Monitoring plugin
    XMPPServer server = XMPPServer.getInstance();
    UserManager userManager = server.getUserManager();
    MonitoringPlugin plugin = (MonitoringPlugin) server.getPluginManager().getPlugin("monitoring");

    ConversationManager conversationmanager = (ConversationManager) plugin.getModule(ConversationManager.class);
    List<String[]> values = new ArrayList<String[]>();
    JID room = null;
    try {
        Conversation conversation = conversationmanager.getConversation(conversationID);
        List<JID> participants = new ArrayList<JID>(conversation.getParticipants());
        for (JID user : participants) {
            for (ConversationParticipation participation : conversation.getParticipations(user)) {
                values.add(new String[]{participation.getNickname(), user.toString()});
            }
        }
        Collections.sort(values, new Comparator<String[]>() {
            public int compare(String[] o1, String[] o2) {
                return o1[0].compareTo(o2[0]);
            }
        });
        room = conversation.getRoom();
    }
    catch (NotFoundException e) {
        Log.error("Conversation not found: " + conversationID, e);
    }
    // paginator vars
    int range = 16;
    int numPages = (int) Math.ceil((double) (values.size() / 2) / (double) (range / 2));
    int curPage = (start / range) + 1;

      out.write("\r\n<html>\r\n<head>\r\n<meta name=\"decorator\" content=\"none\"/>\r\n</head>\r\n<body>\r\n<script type=\"text/javascript\" language=\"javascript\" src=\"scripts/tooltips/domTT.js\"></script>\r\n<script type=\"text/javascript\" language=\"javascript\" src=\"scripts/tooltips/domLib.js\"></script>\r\n<style type=\"text/css\">\r\n#lightbox{\r\n\ttop: 20%;\r\n\tmargin-top: -20px;\r\n\t}\r\n\r\n.jive-testPanel {\r\n\tdisplay: block;\r\n\tposition: relative;\r\n\tfloat: left;\r\n\tmargin: 0;\r\n\tpadding: 0;\r\n\tborder: 2px solid #666666;\r\n\tbackground-color: #f8f7eb;\r\n\toverflow: hidden;\r\n\tz-index: 9997;\r\n\t-moz-border-radius: 6px;\r\n\t}\r\n.jive-testPanel-content {\r\n\tdisplay: block;\r\n\tfloat: left;\r\n\tpadding: 20px;\r\n\tfont-size: 8pt;\r\n\tz-index: 9999;\r\n\t}\r\n.jive-testPanel-close a,\r\n.jive-testPanel-close a:visited {\r\n\tfloat: right;\r\n\tcolor: #666;\r\n\tpadding: 2px 5px 2px 18px;\r\n\tmargin: 0;\r\n\tfont-size: 8pt;\r\n\tbackground: transparent url(../../../images/setup_btn_closetestx.gif) no-repeat left;\r\n\tbackground-position: 4;\r\n\tborder: 1px solid #ccc;\r\n\tz-index: 9999;\r\n\t}\r\n.jive-testPanel-close a:hover {\r\n");
      out.write("\tbackground-color: #e9e8d9;\r\n\t}\r\n.jive-testPanel-content h2 {\r\n\tfont-size: 14pt;\r\n\tcolor: #396b9c;\r\n\tmargin: 0 0 10px 0;\r\n\tpadding: 0;\r\n\t}\r\n.jive-testPanel-content h2 span {\r\n\tfont-size: 10pt;\r\n\tcolor: #000;\r\n\t}\r\n.jive-testPanel-content h4 {\r\n\tfont-size: 12pt;\r\n\tmargin: 0 0 10px 0;\r\n\tpadding: 0;\r\n\t}\r\n.jive-testPanel-content h4.jive-testSuccess {\r\n\tcolor: #1e7100;\r\n\t}\r\n.jive-testPanel-content h4.jive-testError {\r\n\tcolor: #890000;\r\n\t}\r\n</style>\r\n\r\n<!-- BEGIN connection settings test panel -->\r\n<div class=\"jive-testPanel\">\r\n\t<div class=\"jive-testPanel-content\">\r\n\r\n\t\t<div align=\"right\" class=\"jive-testPanel-close\">\r\n\t\t\t<a href=\"#\" class=\"lbAction\" rel=\"deactivate\">");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</a>\r\n\t\t</div>\r\n\r\n\r\n\t\t<h2>");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("</h2>\r\n\r\n\t\t<p>");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_2 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_2.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_2.setParent(null);
      _jspx_th_fmt_message_2.setKey("archive.group_conversation.participants.description");
      int _jspx_eval_fmt_message_2 = _jspx_th_fmt_message_2.doStartTag();
      if (_jspx_eval_fmt_message_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_2.doInitBody();
        }
        do {
          out.write("\r\n                ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_2);
          _jspx_th_fmt_param_0.setValue( room != null ? "<b>"+room.getNode()+"</b>" : "" );
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          out.write("\r\n            ");
          int evalDoAfterBody = _jspx_th_fmt_message_2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_2);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_2);
      out.write("\r\n        </p>\r\n\r\n        ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write(": <b>");
      out.print( values.size() );
      out.write("</b>\r\n\r\n        ");
  if (numPages > 1) { 
      out.write("\r\n\r\n            -- ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write(' ');
      out.print( (start+1) );
      out.write('-');
      out.print( (start+range) );
      out.write("\r\n\r\n        ");
  } 
      out.write("\r\n\r\n        ");
  if (numPages > 1) { 
      out.write("\r\n\r\n            <p>\r\n            ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write(":\r\n            [\r\n            ");
  for (int i=0; i<numPages; i++) {
                    String sep = ((i+1)<numPages) ? " " : "";
                    boolean isCurrent = (i+1) == curPage;
            
      out.write("\r\n                <a href=\"#\" rel=\"deactivate\" onclick=\"showOccupants('");
      out.print(conversationID);
      out.write('\'');
      out.write(',');
      out.write(' ');
      out.print((i*range));
      out.write(");return false;\" class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write('"');
      out.write('>');
      out.print( (i+1) );
      out.write("</a>");
      out.print( sep );
      out.write("\r\n            ");
  } 
      out.write("\r\n            ]\r\n\r\n        ");
  } 
      out.write("\r\n        <div class=\"jive-table\">\r\n        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n        <thead>\r\n            <tr>\r\n                <th colspan=\"2\">");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</th>\r\n            </tr>\r\n        </thead>\r\n        <tbody>\r\n            ");
 if (!values.isEmpty()) {
                int from = (curPage-1) * range;
                int to = curPage * range;
                // Check ranges
                from = from > values.size() ? values.size() : from;
                to = to > values.size() ? values.size() : to;
                // Get subset of participants to display 
                values = values.subList(from, to);
                for (Iterator<String[]> it = values.iterator(); it.hasNext();) {
                    String[] participation = it.next();
                    String nickname = participation[0];
                    JID participant = new  JID(participation[1]);
            
      out.write("\r\n            <tr>\r\n                \r\n                <td>");
      out.print(nickname);
      out.write(" <i>(");
      out.print( server.isLocal(participant) && userManager.isRegisteredUser(participant) ? "<a href='/user-properties.jsp?username=" + participant.getNode() + "'>" + participant.toBareJID() + "</a>" : participant.toBareJID() );
      out.write(")</i></td>\r\n\r\n                ");
 if (it.hasNext()) {
                    participation = it.next();
                    nickname = participation[0];
                    participant = new  JID(participation[1]);
                
      out.write("\r\n                <td>");
      out.print(nickname);
      out.write(" <i>(");
      out.print( server.isLocal(participant) && userManager.isRegisteredUser(participant) ? "<a href='/user-properties.jsp?username=" + participant.getNode() + "'>" + participant.toBareJID() + "</a>" : participant.toBareJID() );
      out.write(")</i></td>\r\n                ");
 } else { 
      out.write("\r\n                <td>&nbsp;</td>\r\n                ");
 } 
      out.write("\r\n            </tr>\r\n            ");
 } } else { 
      out.write("\r\n            <tr>\r\n                <td colspan=\"3\">");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</td>\r\n            </tr>\r\n            ");
 } 
      out.write("\r\n        </tbody>\r\n        </table>\r\n        </div>\r\n\t</div>\r\n</div>\r\n\r\n</body>\r\n</html>\r\n");
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
    _jspx_th_fmt_message_0.setKey("archive.group_conversation.close");
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
    _jspx_th_fmt_message_1.setKey("archive.group_conversation.participants.title");
    int _jspx_eval_fmt_message_1 = _jspx_th_fmt_message_1.doStartTag();
    if (_jspx_th_fmt_message_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_1);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_1);
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
    _jspx_th_fmt_message_3.setKey("archive.group_conversation.participants");
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
    _jspx_th_fmt_message_4.setKey("global.showing");
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
    _jspx_th_fmt_message_5.setKey("global.pages");
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
    _jspx_th_fmt_message_6.setKey("archive.group_conversation.participants.participant");
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
    _jspx_th_fmt_message_7.setKey("archive.group_conversation.participants.empty");
    int _jspx_eval_fmt_message_7 = _jspx_th_fmt_message_7.doStartTag();
    if (_jspx_th_fmt_message_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_7);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_7);
    return false;
  }
}
