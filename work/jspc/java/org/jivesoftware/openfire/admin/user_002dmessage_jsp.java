package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.StringUtils;
import org.jivesoftware.openfire.SessionManager;
import org.jivesoftware.openfire.session.ClientSession;
import org.jivesoftware.openfire.user.User;
import org.xmpp.packet.JID;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class user_002dmessage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

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

      out.write('\n');
      out.write("\n\n\n\n\n\n\n");
  // Get parameters
    String username = ParamUtils.getParameter(request,"username");
    boolean send = ParamUtils.getBooleanParameter(request,"send");
    boolean success = ParamUtils.getBooleanParameter(request,"success");
    boolean sendToAll = ParamUtils.getBooleanParameter(request,"sendToAll");
    boolean tabs = ParamUtils.getBooleanParameter(request,"tabs",true);
    String jid = ParamUtils.getParameter(request,"jid");
    String[] jids = ParamUtils.getParameters(request,"jid");
    String message = ParamUtils.getParameter(request,"message");

      out.write('\n');
      out.write('\n');
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
 webManager.init(pageContext); 
      out.write('\n');
      out.write('\n');

    // Handle a cancel
    if (request.getParameter("cancel") != null) {
        if (username == null) {
            response.sendRedirect("session-summary.jsp");
            return;
        }
        else {
            response.sendRedirect("user-properties.jsp?username=" + URLEncoder.encode(username, "UTF-8"));
            return;
        }
    }

    // Get the user - a user might not be passed in if this is a system-wide message
    User user = null;
    if (username != null) {
        user = webManager.getUserManager().getUser(username);
    }

    // Get the session manager
    SessionManager sessionManager = webManager.getSessionManager();

    // Handle the request to send a message:
    Map<String,String> errors = new HashMap<String,String>();
    if (send) {
        // Validate the message and jid
        if (jid == null && !sendToAll && user != null) {
            errors.put("jid","jid");
        }
        if (message == null) {
            errors.put("message","message");
        }
        if (errors.size() == 0) {
            // no errors, so continue
            if (user == null) {
                // system-wide message:
                sessionManager.sendServerMessage(null,message);
            }
            else {
                if (sendToAll) {
                    // loop through all sessions based on the user assoc with the JID, send
                    // message to all
                    for (String jid1 : jids) {
                        JID address = new JID(jid1);
                        // TODO: Do we really need this?
                        sessionManager.getSession(address);
                        sessionManager.sendServerMessage(address, null, message);
                        // Log the event
                        webManager.logEvent("send server message", "jid = all active\nmessage = "+message);
                    }
                }
                else {
                    sessionManager.sendServerMessage(new JID(jid),null,message);
                    // Log the event
                    webManager.logEvent("send server message", "jid = "+jid+"\nmessage = "+message);
                }
            }
            if (username != null){
                response.sendRedirect("user-message.jsp?success=true&username=" +
                        URLEncoder.encode(username, "UTF-8") + "&tabs=" + tabs);
            }
            else {
                response.sendRedirect("user-message.jsp?success=true");
            }
            return;
        }
    }

    // Get all sessions associated with this user:
    int numSessions = -1;
    ClientSession sess = null;
    Collection<ClientSession> sessions = null;
    if (user != null) {
        numSessions = sessionManager.getSessionCount(user.getUsername());
        sessions = sessionManager.getSessions(user.getUsername());
        if (numSessions == 1) {
            sess = sessions.iterator().next();
        }
    }

      out.write("\n\n\n<html>\n<head>\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n<meta name=\"pageID\" content=\"user-message\"/>\n<meta name=\"helpPage\" content=\"send_an_administrative_message_to_users.html\"/>\n</head>\n<body>\n\n");
  if (success) { 
      out.write("\n\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n        <td class=\"jive-icon-label\">\n        ");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\n        </td></tr>\n    </tbody>\n    </table>\n    </div><br>\n\n");
  } 
      out.write("\n\n<script language=\"JavaScript\" type=\"text/javascript\">\nfunction updateSelect(el) {\n    if (el.checked) {\n        for (var e=0; e<el.form.jid.length; e++) {\n            el.form.jid[e].selected = true;\n        }\n    }\n    else {\n        for (var e=0; e<el.form.jid.length; e++) {\n            el.form.jid[e].selected = false;\n        }\n    }\n    el.form.message.focus();\n}\n</script>\n\n<form action=\"user-message.jsp\" method=\"post\" name=\"f\">\n");
 if(username != null){ 
      out.write("\n<input type=\"hidden\" name=\"username\" value=\"");
      out.print( StringUtils.escapeForXML(username) );
      out.write('"');
      out.write('>');
      out.write('\n');
 } 
      out.write("\n<input type=\"hidden\" name=\"tabs\" value=\"");
      out.print( tabs );
      out.write("\">\n<input type=\"hidden\" name=\"send\" value=\"true\">\n");
  if (sess != null) { 
      out.write("\n\n    <input type=\"hidden\" name=\"sessionID\" value=\"");
      out.print( sess.getAddress().toString() );
      out.write("\">\n\n");
  } 
      out.write("\n\n\t<!-- BEGIN send message block -->\n\t<!--<div class=\"jive-contentBoxHeader\">\n\t\t");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\n\t</div>-->\n\t<div class=\"jive-contentBox\" style=\"-moz-border-radius: 3px;\">\n\t\t<table cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"600\">\n\n\t\t<tr><td colspan=3 class=\"text\" style=\"padding-bottom: 10px;\">\n\t\t");
   if (user == null) { 
      out.write("\n\n\t\t\t<p>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</p>\n\n\t\t");
  } else { 
      out.write("\n\n\t\t\t<p>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</p>\n\n\t\t");
  } 
      out.write("\n\t\t</td></tr>\n\t\t<tr>\n\t\t\t<td class=\"jive-label\">\n\t\t\t\t");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write(":\n\t\t\t</td>\n\t\t\t<td>\n\t\t\t\t");
  if (user == null) { 
      out.write("\n\n\t\t\t\t\t");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\n\n\t\t\t\t");
  } else { 
      out.write("\n\n\t\t\t\t\t");
  if (sess != null && numSessions == 1) { 
      out.write("\n\n\t\t\t\t\t\t");
      out.print( sess.getAddress().toString() );
      out.write("\n\t\t\t\t\t\t<input type=\"hidden\" name=\"jid\" value=\"");
      out.print( sess.getAddress().toString() );
      out.write("\">\n\n\t\t\t\t\t");
  } else { 
      out.write("\n\n\t\t\t\t\t\t<select size=\"2\" name=\"jid\" multiple>\n\n\t\t\t\t\t\t");

                            for (ClientSession clisess : sessions) {
                        
      out.write("\n                            <option value=\"");
      out.print( clisess.getAddress().toString() );
      out.write('"');
      out.write('>');
      out.print( clisess.getAddress().toString() );
      out.write("\n                            </option>\n\n                            ");
 } 
      out.write("\n\n\t\t\t\t\t\t</select>\n\n\t\t\t\t\t\t<input type=\"checkbox\" name=\"sendToAll\" value=\"true\" id=\"cb01\"\n\t\t\t\t\t\t onfocus=\"updateSelect(this);\" onclick=\"updateSelect(this);\">\n\t\t\t\t\t\t<label for=\"cb01\">");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</label>\n\n\t\t\t\t\t");
  } 
      out.write("\n\n\t\t\t\t\t");
  if (errors.get("jid") != null) { 
      out.write("\n\n\t\t\t\t\t\t<br>\n\t\t\t\t\t\t<span class=\"jive-error-text\">\n\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</span>\n\n\t\t\t\t\t");
  } 
      out.write("\n\n\t\t\t\t");
  } 
      out.write("\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr valign=\"top\">\n\t\t\t<td class=\"jive-label\">\n\t\t\t\t");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write(":\n\t\t\t</td>\n\t\t\t<td>\n\t\t\t\t");
  if (errors.get("message") != null) { 
      out.write("\n\n\t\t\t\t\t<span class=\"jive-error-text\">\n\t\t\t\t\t");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</span>\n\t\t\t\t\t<br>\n\n\t\t\t\t");
  } 
      out.write("\n\t\t\t\t<textarea name=\"message\" cols=\"55\" rows=\"5\" wrap=\"virtual\"></textarea>\n\t\t\t</td>\n\t\t</tr>\n\t\t</table>\n\t</div>\n\t<!-- END send message block -->\n\n<input type=\"submit\" value=\"");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\">\n<input type=\"submit\" name=\"cancel\" value=\"");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("\">\n\n</form>\n\n<script language=\"JavaScript\" type=\"text/javascript\">\ndocument.f.message.focus();\n</script>\n\n\n</body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("user.message.title");
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
    _jspx_th_fmt_message_1.setKey("user.message.send");
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
    _jspx_th_fmt_message_2.setKey("user.message.send_admin_msg");
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
    _jspx_th_fmt_message_3.setKey("user.message.info");
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
    _jspx_th_fmt_message_4.setKey("user.message.specified_user_info");
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
    _jspx_th_fmt_message_5.setKey("user.message.to");
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
    _jspx_th_fmt_message_6.setKey("user.message.all_online_user");
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
    _jspx_th_fmt_message_7.setKey("user.message.send_session");
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
    _jspx_th_fmt_message_8.setKey("user.message.valid_address");
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
    _jspx_th_fmt_message_9.setKey("user.message.message");
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
    _jspx_th_fmt_message_10.setKey("user.message.valid_message");
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
    _jspx_th_fmt_message_11.setKey("user.message.send_message");
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
    _jspx_th_fmt_message_12.setKey("global.cancel");
    int _jspx_eval_fmt_message_12 = _jspx_th_fmt_message_12.doStartTag();
    if (_jspx_th_fmt_message_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_12);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_12);
    return false;
  }
}
