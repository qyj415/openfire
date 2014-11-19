package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Map;
import java.util.HashMap;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.ParamUtils;
import java.util.HashMap;
import java.util.Map;
import org.jivesoftware.openfire.ldap.LdapManager;

public final class ldap_002dgroup_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/setup/ldap-group.jspf");
  }

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

      out.write('\n');
      out.write('\n');

    boolean initialSetup = false;
    String currentPage = "ldap-group.jsp";
    String testPage = "setup/setup-ldap-group_test.jsp";
    String nextPage = "profile-settings.jsp";
    Map<String, String> meta = new HashMap<String, String>();
    meta.put("pageID", "profile-settings");

      out.write("\n\n<style type=\"text/css\" title=\"setupStyle\" media=\"screen\">\n\t@import \"style/lightbox.css\";\n\t@import \"style/ldap.css\";\n</style>\n\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"js/prototype.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"js/scriptaculous.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"js/lightbox.js\"></script>\n<script language=\"javascript\" type=\"text/javascript\" src=\"js/tooltips/domLib.js\"></script>\n<script language=\"javascript\" type=\"text/javascript\" src=\"js/tooltips/domTT.js\"></script>\n<script language=\"javascript\" type=\"text/javascript\" src=\"js/setup.js\"></script>\n\n");
      out.write("\n\n\n\n\n\n\n\n");

    // Get parameters
    @SuppressWarnings("unchecked")
    Map<String,String> xmppSettings = (Map<String,String>)session.getAttribute("xmppSettings");

    String serverType = ParamUtils.getParameter(request, "serverType");
    // Server type should never be null, but if it is, assume "other"
    if (serverType == null) {
        serverType = "other";
    }

    LdapManager manager = LdapManager.getInstance();

    // Determine the right default values based on the the server type.
    String defaultGroupNameField = JiveGlobals.getProperty("ldap.groupNameField");
    String defaultGroupMemberField = JiveGlobals.getProperty("ldap.groupMemberField");
    String defaultGroupDescriptionField = JiveGlobals.getProperty("ldap.groupDescriptionField");
    String posixModeString = JiveGlobals.getProperty("ldap.posixMode");
    boolean defaultPosixMode = Boolean.parseBoolean(posixModeString);
    String defaultGroupSearchFilter = JiveGlobals.getProperty("ldap.groupSearchFilter");

    if (serverType.equals("activedirectory")) {
        if (defaultGroupNameField == null) {
            defaultGroupNameField = "cn";
        }
        if (defaultGroupMemberField == null) {
            defaultGroupMemberField = "member";
        }
        if (defaultGroupDescriptionField == null) {
            defaultGroupDescriptionField = "description";
        }
        if (posixModeString == null) {
            defaultPosixMode = false;
        }
        if (defaultGroupSearchFilter == null) {
            defaultGroupSearchFilter = "(objectClass=group)";
        }
    } else {
        if (defaultGroupNameField == null) {
            defaultGroupNameField = "cn";
        }
        if (defaultGroupMemberField == null) {
            defaultGroupMemberField = "member";
        }
        if (defaultGroupDescriptionField == null) {
            defaultGroupDescriptionField = "description";
        }
        if (posixModeString == null) {
            defaultPosixMode = false;
        }
    }

    String groupNameField = ParamUtils.getParameter(request, "groupNameField");
    if (groupNameField == null) {
        groupNameField = defaultGroupNameField;
    }
    String groupMemberField = ParamUtils.getParameter(request, "groupMemberField");
    if (groupMemberField == null) {
        groupMemberField = defaultGroupMemberField;
    }
    String groupDescriptionField = ParamUtils.getParameter(request, "groupDescriptionField");
    if (groupDescriptionField == null) {
        groupDescriptionField = defaultGroupDescriptionField;
    }
    String posixModeParam = ParamUtils.getParameter(request, "posixMode");
    boolean posixMode;
    if (posixModeParam == null) {
        posixMode = defaultPosixMode;
    } else {
        posixMode = Boolean.parseBoolean(posixModeParam);
    }
    String groupSearchFilter = ParamUtils.getParameter(request, "groupSearchFilter");
    if (groupSearchFilter == null) {
        groupSearchFilter = defaultGroupSearchFilter;
    }

    boolean save = request.getParameter("save") != null;
    boolean doTest = request.getParameter("test") != null;
    if (save || doTest) {
        // Save information in the session so we can use it in testing pages during setup
        Map<String, String> settings = new HashMap<String, String>();
        settings.put("ldap.groupNameField", groupNameField);
        settings.put("ldap.groupMemberField", groupMemberField);
        settings.put("ldap.groupDescriptionField", groupDescriptionField);
        settings.put("ldap.posixMode", Boolean.toString(posixMode));
        settings.put("ldap.groupSearchFilter", groupSearchFilter);
        session.setAttribute("ldapGroupSettings", settings);

        if (save) {
            if (groupNameField != null) {
                manager.setGroupNameField(groupNameField);
            }
            if (groupMemberField != null) {
                manager.setGroupMemberField(groupMemberField);
            }
            if (groupDescriptionField != null) {
                manager.setGroupDescriptionField(groupDescriptionField);
            }
            manager.setPosixMode(posixMode);
            if (groupSearchFilter != null) {
                manager.setGroupSearchFilter(groupSearchFilter);
            }

            // Enable the LDAP auth provider. The LDAP user provider will be enabled on the next step.
            JiveGlobals.setProperty("provider.group.className",
                    "org.jivesoftware.openfire.ldap.LdapGroupProvider");

            // Save the settings for later, if we're in setup
            if (xmppSettings != null) {
                xmppSettings.put("provider.group.className",
                        "org.jivesoftware.openfire.ldap.LdapGroupProvider");
                xmppSettings.put("ldap.groupNameField", groupNameField);
                xmppSettings.put("ldap.groupMemberField", groupMemberField);
                xmppSettings.put("ldap.groupDescriptionField", groupDescriptionField);
                xmppSettings.put("ldap.posixMode", Boolean.toString(posixMode));
                xmppSettings.put("ldap.groupSearchFilter", groupSearchFilter);
                session.setAttribute("xmppSettings", xmppSettings);
            }

            // Redirect
            response.sendRedirect(nextPage);
            return;
        }
    }

      out.write("\n<html>\n<head>\n    <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n    ");
 for (Map.Entry<String, String> entry : meta.entrySet()) { 
      out.write("\n    <meta name=\"");
      out.print( entry.getKey());
      out.write("\" content=\"");
      out.print( entry.getValue());
      out.write("\"/>\n    ");
 } 
      out.write("\n</head>\n\n<body>\n    ");
 if (doTest) {
        StringBuilder sb = new StringBuilder();
        sb.append(testPage);
        sb.append("?serverType=").append(serverType);
    
      out.write("\n        <a href=\"");
      out.print( sb.toString());
      out.write("\" id=\"lbmessage\" title=\"");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\" style=\"display:none;\"></a>\n        <script type=\"text/javascript\">\n            function loadMsg() {\n                var lb = new lightbox(document.getElementById('lbmessage'));\n                lb.activate();\n            }\n            setTimeout('loadMsg()', 250);\n        </script>\n\n    ");
 } 
      out.write("\n\n    ");
 if (initialSetup) { 
      out.write("\n\t<h1>");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write(": <span>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</span></h1>\n    ");
 } 
      out.write("\n\n\t<!-- BEGIN jive-contentBox_stepbar -->\n\t<div id=\"jive-contentBox_stepbar\">\n\t\t<span class=\"jive-stepbar_step\"><em>1. ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</em></span>\n\t\t<span class=\"jive-stepbar_step\"><em>2. ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</em></span>\n\t\t<span class=\"jive-stepbar_step\"><strong>3. ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</strong></span>\n\t</div>\n\t<!-- END jive-contentBox-stepbar -->\n\n\t<!-- BEGIN jive-contentBox -->\n\t<div class=\"jive-contentBox jive-contentBox_for-stepbar\">\n\n\t<h2>");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write(": <span>");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</span></h2>\n\t<p>");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("</p>\n\n\t<form action=\"\" method=\"get\">\n\t\t<!-- BEGIN jive-contentBox_bluebox -->\n\t\t<div class=\"jive-contentBox_bluebox\">\n\n\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\n\t\t\t<tr>\n\t\t\t<td colspan=\"2\"><strong>");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</strong></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td align=\"right\">");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write(":</td>\n\t\t\t<td><input type=\"text\" name=\"groupNameField\" id=\"jiveLDAPgroupname\" size=\"22\" maxlength=\"50\" value=\"");
      out.print( groupNameField!=null?groupNameField:"");
      out.write("\"><span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', -1);\"></span></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td align=\"right\">");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write(":</td>\n\t\t\t<td><input type=\"text\" name=\"groupMemberField\" id=\"jiveLDAPgroupmember\" size=\"22\" maxlength=\"50\" value=\"");
      out.print( groupMemberField!=null?groupMemberField:"");
      out.write("\"><span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', -1);\"></span></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td align=\"right\">");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write(":</td>\n\t\t\t<td><input type=\"text\" name=\"groupDescriptionField\" id=\"jiveLDAPgroupdesc\" size=\"22\" maxlength=\"50\" value=\"");
      out.print( groupDescriptionField!=null?groupDescriptionField:"");
      out.write("\"><span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', -1);\"></span></td>\n\t\t\t</tr>\n\t\t\t</table>\n\n\t\t\t<!-- BEGIN jiveAdvancedButton -->\n\t\t\t<div class=\"jiveAdvancedButton jiveAdvancedButtonTopPad\">\n\t\t\t\t<a href=\"#\" onclick=\"togglePanel(jiveAdvanced); return false;\" id=\"jiveAdvancedLink\">");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("</a>\n\t\t\t</div>\n\t\t\t<!-- END jiveAdvancedButton -->\n\n\t\t\t<!-- BEGIN jiveAdvancedPanelu (advanced user mapping settings) -->\n\t\t\t\t<div class=\"jiveadvancedPanelu\" id=\"jiveAdvanced\" style=\"display: none;\">\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"right\">");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write(":</td>\n\t\t\t\t\t\t<td><span style=\"float: left;\">\n\t\t\t\t\t\t\t<input type=\"radio\" name=\"posixMode\" value=\"true\" style=\"float: none;\" id=\"posix1\" ");
 if(posixMode) {
      out.write("checked");
 } 
      out.write("><label for=\"posix1\"> ");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("  </label>\n\t\t\t\t\t\t\t<input type=\"radio\" name=\"posixMode\" value=\"false\" style=\"float: none;\" id=\"posix2\" ");
 if(!posixMode) {
      out.write("checked");
 } 
      out.write("><label for=\"posix2\"> ");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("  </label>\n\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t<span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', -1);\"></span></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"right\">");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("</td>\n\t\t\t\t\t\t<td><input type=\"text\" name=\"groupSearchFilter\" value=\"");
      out.print( groupSearchFilter!=null?groupSearchFilter:"");
      out.write("\" id=\"jiveLDAPgroupsearchfilter\" size=\"22\" maxlength=\"250\"><span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', -1);\"></span></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t<!-- END jiveAdvancedPanelu (advanced user mapping settings) -->\n\n\t\t</div>\n\t\t<!-- END jive-contentBox_bluebox -->\n\n\n\n\t\t<!-- BEGIN jive-buttons -->\n\t\t<div class=\"jive-buttons\">\n\n\t\t\t<!-- BEGIN right-aligned buttons -->\n\t\t\t<div align=\"right\">\n                <input type=\"Submit\" name=\"test\" value=\"");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("\" id=\"jive-setup-test\" border=\"0\">\n\n\t\t\t\t<input type=\"Submit\" name=\"save\" value=\"");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_25 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_25.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_25.setParent(null);
      _jspx_th_fmt_message_25.setKey( initialSetup ? "setup.ldap.continue" : "global.save_settings");
      int _jspx_eval_fmt_message_25 = _jspx_th_fmt_message_25.doStartTag();
      if (_jspx_th_fmt_message_25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
        return;
      }
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
      out.write("\" id=\"jive-setup-save\" border=\"0\">\n\t\t\t</div>\n\t\t\t<!-- END right-aligned buttons -->\n\n\t\t</div>\n\t\t<!-- END jive-buttons -->\n\n\t</form>\n\n\t</div>\n\t<!-- END jive-contentBox -->\n\n\n\n</body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("setup.ldap.title");
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
    _jspx_th_fmt_message_1.setKey("global.test");
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
    _jspx_th_fmt_message_2.setKey("setup.ldap.profile");
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
    _jspx_th_fmt_message_3.setKey("setup.ldap.group_mapping");
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
    _jspx_th_fmt_message_4.setKey("setup.ldap.connection_settings");
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
    _jspx_th_fmt_message_5.setKey("setup.ldap.user_mapping");
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
    _jspx_th_fmt_message_6.setKey("setup.ldap.group_mapping");
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
    _jspx_th_fmt_message_7.setKey("setup.ldap.step_three");
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
    _jspx_th_fmt_message_8.setKey("setup.ldap.group_mapping");
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
    _jspx_th_fmt_message_9.setKey("setup.ldap.group.description");
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
    _jspx_th_fmt_message_10.setKey("setup.ldap.group_mapping");
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
    _jspx_th_fmt_message_11.setKey("setup.ldap.group.name_field");
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
    _jspx_th_fmt_message_12.setKey("setup.ldap.group.name_field_description");
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
    _jspx_th_fmt_message_13.setKey("setup.ldap.group.member_field");
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
    _jspx_th_fmt_message_14.setKey("setup.ldap.group.member_field_description");
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
    _jspx_th_fmt_message_15.setKey("setup.ldap.group.description_field");
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
    _jspx_th_fmt_message_16.setKey("setup.ldap.group.description_field_description");
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
    _jspx_th_fmt_message_17.setKey("setup.ldap.advanced");
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
    _jspx_th_fmt_message_18.setKey("setup.ldap.group.posix");
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
    _jspx_th_fmt_message_19.setKey("global.yes");
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
    _jspx_th_fmt_message_20.setKey("global.no");
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
    _jspx_th_fmt_message_21.setKey("setup.ldap.group.posix_description");
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
    _jspx_th_fmt_message_22.setKey("setup.ldap.group.filter");
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
    _jspx_th_fmt_message_23.setKey("setup.ldap.group.filter_description");
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
    _jspx_th_fmt_message_24.setKey("setup.ldap.test");
    int _jspx_eval_fmt_message_24 = _jspx_th_fmt_message_24.doStartTag();
    if (_jspx_th_fmt_message_24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_24);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_24);
    return false;
  }
}
