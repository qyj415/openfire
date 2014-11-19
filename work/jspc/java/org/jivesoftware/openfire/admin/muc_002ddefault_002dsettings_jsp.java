package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.*;
import java.util.*;
import org.jivesoftware.openfire.muc.spi.MUCPersistenceManager;
import java.net.URLEncoder;

public final class muc_002ddefault_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
 webManager.init(request, response, session, application, out ); 
      out.write('\n');
      out.write('\n');
  // Get parameters
    boolean save = request.getParameter("save") != null;
    boolean success = request.getParameter("success") != null;
    String mucname = ParamUtils.getParameter(request,"mucname");

    String publicRoom = ParamUtils.getParameter(request, "roomconfig_publicroom");
    String persistentRoom = ParamUtils.getParameter(request, "roomconfig_persistentroom");
    String moderatedRoom = ParamUtils.getParameter(request, "roomconfig_moderatedroom");
    String membersOnly = ParamUtils.getParameter(request, "roomconfig_membersonly");
    String nonanonymous = ParamUtils.getParameter(request, "roomconfig_nonanonymous");
    String allowInvites = ParamUtils.getParameter(request, "roomconfig_allowinvites");
    String changeSubject = ParamUtils.getParameter(request, "roomconfig_changesubject");
    String reservedNick = ParamUtils.getParameter(request, "roomconfig_reservednick");
    String canChangeNick = ParamUtils.getParameter(request, "roomconfig_canchangenick");
    String registrationEnabled = ParamUtils.getParameter(request, "roomconfig_registration");
    String enableLog = ParamUtils.getParameter(request, "roomconfig_enablelogging");
    String maxUsers = ParamUtils.getParameter(request, "roomconfig_maxusers");

    if (!webManager.getMultiUserChatManager().isServiceRegistered(mucname)) {
        // The requested service name does not exist so return to the list of the existing rooms
        response.sendRedirect("muc-service-summary.jsp");
        return;
    }

    // Handle a save
    Map<String,String> errors = new HashMap<String,String>();
    if (save) {
        try {
            int max = Integer.parseInt(maxUsers);
            MUCPersistenceManager.setProperty(mucname, "room.maxUsers", maxUsers);
        }
        catch (Exception e) {
            errors.put("max_users", "max_users");
        }
        if (errors.size() == 0) {
            if (publicRoom != null && publicRoom.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.publicRoom", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.publicRoom", "false");
            }
            if (persistentRoom != null && persistentRoom.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.persistent", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.persistent", "false");
            }
            if (moderatedRoom != null && moderatedRoom.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.moderated", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.moderated", "false");
            }
            if (membersOnly != null && membersOnly.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.membersOnly", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.membersOnly", "false");
            }
            if (nonanonymous != null && nonanonymous.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.canAnyoneDiscoverJID", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.canAnyoneDiscoverJID", "false");
            }
            if (allowInvites != null && allowInvites.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.canOccupantsInvite", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.canOccupantsInvite", "false");
            }
            if (changeSubject != null && changeSubject.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.canOccupantsChangeSubject", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.canOccupantsChangeSubject", "false");
            }
            if (reservedNick != null && reservedNick.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.loginRestrictedToNickname", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.loginRestrictedToNickname", "false");
            }
            if (canChangeNick != null && canChangeNick.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.canChangeNickname", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.canChangeNickname", "false");
            }
            if (registrationEnabled != null && registrationEnabled.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.registrationEnabled", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.registrationEnabled", "false");
            }
            if (enableLog != null && enableLog.trim().length() > 0) {
                MUCPersistenceManager.setProperty(mucname, "room.logEnabled", "true");
            }
            else {
                MUCPersistenceManager.setProperty(mucname, "room.logEnabled", "false");
            }
        }

        response.sendRedirect("muc-default-settings.jsp?success=true&mucname="+URLEncoder.encode(mucname, "UTF-8"));
        return;
    }

      out.write("\n\n<html>\n<head>\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n<meta name=\"subPageID\" content=\"muc-defaultsettings\"/>\n<meta name=\"extraParams\" content=\"");
      out.print( "mucname="+URLEncoder.encode(mucname, "UTF-8") );
      out.write("\"/>\n<meta name=\"helpPage\" content=\"set_group_chat_room_creation_permissions.html\"/>\n</head>\n<body>\n\n<p>\n");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write('\n');
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write(" <b><a href=\"muc-service-edit-form.jsp?mucname=");
      out.print( URLEncoder.encode(mucname, "UTF-8") );
      out.write('"');
      out.write('>');
      out.print( StringUtils.escapeHTMLTags(mucname) );
      out.write("</a></b>\n</p>\n\n");
  if (errors.size() > 0) { 
      out.write("\n\n    <div class=\"jive-error\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr><td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n        <td class=\"jive-icon-label\">\n        ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\n        </td></tr>\n    </tbody>\n    </table>\n    </div><br>\n\n");
  } else if (success) { 
      out.write("\n\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n        <td class=\"jive-icon-label\">\n            ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\n        </td></tr>\n    </tbody>\n    </table>\n    </div><br>\n\n");
  } 
      out.write("\n\n<!-- BEGIN 'Default Room Settings' -->\n<form action=\"muc-default-settings.jsp?save\" method=\"post\">\n    <input type=\"hidden\" name=\"mucname\" value=\"");
      out.print( StringUtils.escapeForXML(mucname) );
      out.write("\" />\n    <div class=\"jive-contentBoxHeader\">\n        ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\n    </div>\n    <div class=\"jive-contentBox\">\n        <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n        <tbody>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_publicroom\" value=\"true\" id=\"publicRoom\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.publicRoom", true)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"publicRoom\">");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_persistentroom\" value=\"true\" id=\"persistentRoom\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.persistent", false)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"persistentRoom\">");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_moderatedroom\" value=\"true\" id=\"moderated\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.moderated", false)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"moderated\">");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_membersonly\" value=\"true\" id=\"membersOnly\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.membersOnly", false)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"membersOnly\">");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_nonanonymous\" value=\"true\" id=\"nonanonymous\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.canAnyoneDiscoverJID", true)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"nonanonymous\">");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_allowinvites\" value=\"true\" id=\"allowInvites\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.canOccupantsInvite", false)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"allowInvites\">");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_changesubject\" value=\"true\" id=\"changeSubject\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.canOccupantsChangeSubject", false)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"changeSubject\">");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_reservednick\" value=\"true\" id=\"reservedNick\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.loginRestrictedToNickname", false)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"reservedNick\">");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_canchangenick\" value=\"true\" id=\"canChangeNick\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.canChangeNickname", true)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"canChangeNick\">");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_registration\" value=\"true\" id=\"registration\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.registrationEnabled", true)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"registration\">");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    <input name=\"roomconfig_enablelogging\" value=\"true\" id=\"enableLogging\" type=\"checkbox\"\n                    ");
      out.print( ((MUCPersistenceManager.getBooleanProperty(mucname, "room.logEnabled", true)) ? "checked" : "") );
      out.write(">\n                </td>\n                <td width=\"99%\">\n                    <label for=\"enableLogging\">");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td width=\"1%\">\n                    &nbsp;\n                </td>\n                <td width=\"99%\">\n                    <label for=\"roomconfig_maxusers\">");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write(":</label>\n                    &nbsp;\n                    <select name=\"roomconfig_maxusers\">\n                        ");
 for(int i=10; i<=50; i=i+10) { 
      out.write("\n                            <option value=\"");
      out.print( i );
      out.write("\"\n                            ");
      out.print( ((MUCPersistenceManager.getIntProperty(mucname, "room.maxUsers", 30)) == i ? "selected=\"selected\"" : "") );
      out.write("\n                            >");
      out.print( i );
      out.write("</option>\n                        ");
 } 
      out.write("\n                        <option value=\"");
      out.print( 0 );
      out.write("\"\n                        ");
      out.print( ((MUCPersistenceManager.getIntProperty(mucname, "room.maxUsers", 30)) == 0 ? "selected=\"selected\"" : "") );
      out.write("\n                        >");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</option>\n                    </select>\n                </td>\n            </tr>\n        </tbody>\n        </table>\n    </div>\n    <input type=\"submit\" value=\"");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("\">\n</form>\n<!-- END 'Default Room Settings' -->\n\n</body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("muc.default.settings.title");
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
    _jspx_th_fmt_message_1.setKey("muc.default.settings.info");
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
    _jspx_th_fmt_message_2.setKey("groupchat.service.settings_affect");
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
    _jspx_th_fmt_message_3.setKey("muc.default.settings.error");
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
    _jspx_th_fmt_message_4.setKey("muc.default.settings.update");
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
    _jspx_th_fmt_message_5.setKey("muc.default.settings.title");
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
    _jspx_th_fmt_message_6.setKey("muc.default.settings.public_room");
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
    _jspx_th_fmt_message_7.setKey("muc.default.settings.persistent_room");
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
    _jspx_th_fmt_message_8.setKey("muc.default.settings.moderated");
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
    _jspx_th_fmt_message_9.setKey("muc.default.settings.members_only");
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
    _jspx_th_fmt_message_10.setKey("muc.default.settings.can_anyone_discover_jid");
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
    _jspx_th_fmt_message_11.setKey("muc.default.settings.allow_invites");
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
    _jspx_th_fmt_message_12.setKey("muc.default.settings.change_subject");
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
    _jspx_th_fmt_message_13.setKey("muc.default.settings.reserved_nick");
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
    _jspx_th_fmt_message_14.setKey("muc.default.settings.can_change_nick");
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
    _jspx_th_fmt_message_15.setKey("muc.default.settings.registration");
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
    _jspx_th_fmt_message_16.setKey("muc.default.settings.enable_logging");
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
    _jspx_th_fmt_message_17.setKey("muc.default.settings.max_users");
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
    _jspx_th_fmt_message_18.setKey("global.unlimited");
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
    _jspx_th_fmt_message_19.setKey("global.save_settings");
    int _jspx_eval_fmt_message_19 = _jspx_th_fmt_message_19.doStartTag();
    if (_jspx_th_fmt_message_19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_19);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_19);
    return false;
  }
}
