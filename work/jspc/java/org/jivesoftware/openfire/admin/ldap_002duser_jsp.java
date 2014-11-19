package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Map;
import java.util.HashMap;
import org.jivesoftware.admin.LdapUserProfile;
import org.jivesoftware.util.BeanUtils;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.ParamUtils;
import java.util.HashMap;
import java.util.Map;
import org.jivesoftware.openfire.ldap.LdapManager;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.ldap.LdapUserProvider;

public final class ldap_002duser_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/setup/ldap-user.jspf");
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
    String currentPage = "ldap-user.jsp";
    String testPage = "setup/setup-ldap-user_test.jsp";
    String nextPage = "ldap-group.jsp";
    Map<String, String> meta = new HashMap<String, String>();
    meta.put("pageID", "profile-settings");

      out.write("\n\n<style type=\"text/css\" title=\"setupStyle\" media=\"screen\">\n\t@import \"style/lightbox.css\";\n\t@import \"style/ldap.css\";\n</style>\n\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"js/prototype.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"js/scriptaculous.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"js/lightbox.js\"></script>\n<script language=\"javascript\" type=\"text/javascript\" src=\"js/tooltips/domLib.js\"></script>\n<script language=\"javascript\" type=\"text/javascript\" src=\"js/tooltips/domTT.js\"></script>\n<script language=\"javascript\" type=\"text/javascript\" src=\"js/setup.js\"></script>\n\n");
      out.write("\n\n\n\n\n\n\n\n\n\n");
      org.jivesoftware.admin.LdapUserProfile vcardBean = null;
      synchronized (session) {
        vcardBean = (org.jivesoftware.admin.LdapUserProfile) _jspx_page_context.getAttribute("vcardBean", PageContext.SESSION_SCOPE);
        if (vcardBean == null){
          vcardBean = new org.jivesoftware.admin.LdapUserProfile();
          _jspx_page_context.setAttribute("vcardBean", vcardBean, PageContext.SESSION_SCOPE);
        }
      }
      out.write('\n');
      out.write('\n');

    // Get parameters
    String serverType = ParamUtils.getParameter(request, "serverType");
    // Server type should never be null, but if it is, assume "other"
    if (serverType == null) {
        serverType = "other";
    }

    LdapManager manager = LdapManager.getInstance();

    @SuppressWarnings("unchecked")
    Map<String,String> xmppSettings = (Map<String,String>)session.getAttribute("xmppSettings");

    // Determine the right default values based on the the server type.
    String defaultUsernameField;
    String defaultSearchFields;
    String defaultSearchFilter;
    // First check if the http session holds data from a previous post of this page
    if (session.getAttribute("ldapUserSettings") != null && session.getAttribute("ldapVCardBean") != null) {
        @SuppressWarnings("unchecked")
        Map<String, String> userSettings = (Map<String, String>) session.getAttribute("ldapUserSettings");
        defaultUsernameField = userSettings.get("ldap.usernameField");
        defaultSearchFields = userSettings.get("ldap.searchFields");
        defaultSearchFilter = userSettings.get("ldap.searchFilter");
        vcardBean = (LdapUserProfile) session.getAttribute("ldapVCardBean");
    }
    else {
        // No info in the session so try stored XML values or default ones
        defaultUsernameField = JiveGlobals.getProperty("ldap.usernameField");
        defaultSearchFields = JiveGlobals.getProperty("ldap.searchFields");
        defaultSearchFilter = JiveGlobals.getProperty("ldap.searchFilter");
        vcardBean = new LdapUserProfile();
        if (vcardBean.loadFromProperties()) {
            // Loaded from stored settings, no need to do anything else.  
        }
        else if (serverType.equals("activedirectory")) {
            if (!vcardBean.loadFromProperties()) {
                // Initialize vCard mappings
                vcardBean.initForActiveDirectory();
            }
            if (defaultUsernameField == null) {
                defaultUsernameField = "sAMAccountName";
                // Initialize vCard mappings
            }
            if (defaultSearchFilter == null) {
                defaultSearchFilter = "(objectClass=organizationalPerson)";
            }
        }
        else {
            if (!vcardBean.loadFromProperties()) {
                // Initialize vCard mappings
                vcardBean.initForOpenLDAP();
            }
            if (defaultUsernameField == null) {
                defaultUsernameField = "uid";
            }
        }
    }

    String usernameField = defaultUsernameField;
    String searchFields = defaultSearchFields;
    String searchFilter = defaultSearchFilter;

    Map<String, String> errors = new HashMap<String, String>();

    boolean save = request.getParameter("save") != null;
    boolean doTest = request.getParameter("test") != null;
    boolean isTesting = request.getParameter("userIndex") != null;
    if ((save || doTest) && !isTesting) {
        usernameField = ParamUtils.getParameter(request, "usernameField");
        if (usernameField == null) {
            errors.put("username",
                    LocaleUtils.getLocalizedString("setup.ldap.user.username_field_error"));
        }
        searchFields = ParamUtils.getParameter(request, "searchFields");
        searchFilter = ParamUtils.getParameter(request, "searchFilter");
        // Set the properties to the vCard bean with the user input
        BeanUtils.setProperties(vcardBean, request);
        if (request.getParameter("storeAvatarInDB") != null) {
            vcardBean.setAvatarStoredInDB(true);
        }
        else {
            vcardBean.setAvatarStoredInDB(false);
        }
        // Store the vcard db setting for later saving.
        if (xmppSettings != null) {
            xmppSettings.put("ldap.override.avatar", vcardBean.getAvatarStoredInDB().toString());
        }

        // Save settings and redirect.
        if (errors.isEmpty()) {
            // Save information in the session so we can use it in testing pages during setup
            Map<String, String> settings = new HashMap<String, String>();
            settings.put("ldap.usernameField", usernameField);
            settings.put("ldap.searchFields", searchFields);
            settings.put("ldap.searchFilter", searchFilter);
            session.setAttribute("ldapUserSettings", settings);
            session.setAttribute("ldapVCardBean", vcardBean);

            if (save) {
                manager.setUsernameField(usernameField);
                if (searchFields != null) {
                    if ("org.jivesoftware.openfire.ldap.LdapUserProvider"
                            .equals(JiveGlobals.getProperty("provider.user.className"))) {
                        // Update current instance being used
                        ((LdapUserProvider) UserManager.getUserProvider()).setSearchFields(searchFields);
                    } else {
                        // Just update the property. It will be later used by LdapUserProvider 
                        JiveGlobals.setProperty("ldap.searchFields", searchFields);

                        // Store in xmppSettings for later saving if we're in setup
                        if (xmppSettings != null) {
                            xmppSettings.put("ldap.searchFields", searchFields);
                        }
                    }
                }
                if (searchFilter != null) {
                    manager.setSearchFilter(searchFilter);
                }
                // Save vCard mappings
                vcardBean.saveProperties();

                // Enable the LDAP auth and user providers. The group provider will be enabled on the next step.
                JiveGlobals.setProperty("provider.user.className",
                        "org.jivesoftware.openfire.ldap.LdapUserProvider");
                JiveGlobals.setProperty("provider.auth.className",
                        "org.jivesoftware.openfire.ldap.LdapAuthProvider");

                // Store in xmppSettings for later saving if we're in setup
                if (xmppSettings != null) {
                    xmppSettings.put("provider.user.className",
                            "org.jivesoftware.openfire.ldap.LdapUserProvider");
                    xmppSettings.put("provider.auth.className",
                            "org.jivesoftware.openfire.ldap.LdapAuthProvider");
                }

                // Redirect
                response.sendRedirect(nextPage + "?serverType=" + serverType);
                return;
            }
        }

        // Save the settings for later, if we're in setup
        if (xmppSettings != null) {
            session.setAttribute("xmppSettings", xmppSettings);
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
      out.write("\n</head>\n\n<body>\n\n    ");
 if (doTest && errors.isEmpty()) {
        StringBuilder sb = new StringBuilder();
        sb.append(testPage);
        sb.append("?serverType=").append(serverType);
        sb.append("&currentPage=").append(currentPage);
        if (isTesting) {
            sb.append("&userIndex=").append(request.getParameter("userIndex"));
        }
    
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
      out.write("</em></span>\n\t\t<span class=\"jive-stepbar_step\"><strong>2. ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</strong></span>\n\t\t<span class=\"jive-stepbar_step\"><em>3. ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</em></span>\n\t</div>\n\t<!-- END jive-contentBox-stepbar -->\n\n\t<!-- BEGIN jive-contentBox -->\n\t<div class=\"jive-contentBox jive-contentBox_for-stepbar\">\n\n\t<h2>");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write(": <span>");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</span></h2>\n\t<p>");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("</p>\n\n    ");
  if (errors.size() > 0) { 
      out.write("\n\n    <div class=\"error\">\n        ");
 for (String error:errors.values()) { 
      out.write("\n            ");
      out.print( error);
      out.write("<br/>\n        ");
 } 
      out.write("\n    </div>\n\n    ");
  } 
      out.write("\n\n    <form action=\"");
      out.print( currentPage);
      out.write("\" method=\"post\">\n\t\t<input type=\"hidden\" name=\"serverType\" value=\"");
      out.print(serverType);
      out.write("\"/>\n        <!-- BEGIN jive-contentBox_bluebox -->\n\t\t<div class=\"jive-contentBox_bluebox\">\n\n\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\n\t\t\t<tr>\n\t\t\t<td colspan=\"2\"><strong>");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</strong></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td align=\"right\">");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write(":</td>\n\t\t\t<td><input type=\"text\" name=\"usernameField\" id=\"jiveLDAPusername\" size=\"22\" maxlength=\"50\" value=\"");
      out.print( usernameField!=null?usernameField:"");
      out.write("\"><span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', -1);\"/></td>\n\t\t\t</tr>\n\t\t\t</table>\n\n\t\t\t<!-- BEGIN jiveAdvancedButton -->\n\t\t\t<div class=\"jiveAdvancedButton jiveAdvancedButtonTopPad\">\n\t\t\t\t<a href=\"#\" onclick=\"togglePanel(jiveAdvanced); return false;\" id=\"jiveAdvancedLink\">");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("</a>\n\t\t\t</div>\n\t\t\t<!-- END jiveAdvancedButton -->\n\n\t\t\t<!-- BEGIN jiveAdvancedPanelu (advanced user mapping settings) -->\n\t\t\t\t<div class=\"jiveadvancedPanelu\" id=\"jiveAdvanced\" style=\"display: none;\">\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"right\">");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write(":</td>\n\t\t\t\t\t\t<td><input type=\"text\" name=\"searchFields\" value=\"");
      out.print( searchFields!=null?searchFields:"");
      out.write("\" id=\"jiveLDAPsearchfields\" size=\"40\" maxlength=\"250\"><span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', -1);\"/></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"right\">");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write(":</td>\n\t\t\t\t\t\t<td><input type=\"text\" name=\"searchFilter\" value=\"");
      out.print( searchFilter!=null?searchFilter:"");
      out.write("\" id=\"jiveLDAPsearchfilter\" size=\"40\" maxlength=\"250\"><span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', -1);\"/></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t<!-- END jiveAdvancedPanelu (advanced user mapping settings) -->\n\n\t\t</div>\n\t\t<!-- END jive-contentBox_bluebox -->\n\n\n\t\t<script type=\"text/javascript\" language=\"JavaScript\">\n\t\t\tfunction jiveRowHighlight(theInput) {\n\n\t\t\t\tvar e = $(jivevCardTable).getElementsByTagName('tr');\n\t\t\t\t\tfor (var i = 0; i < e.length; i++) {\n\t\t\t\t\t\t\te[i].style.backgroundColor = \"#fff\";\n\t\t\t\t\t}\n\n\t\t\t\ttheInput.parentNode.parentNode.style.backgroundColor = \"#eaeff4\";\n\t\t\t}\n\n\t\t</script>\n\t\t<!-- BEGIN jive-contentBox_greybox -->\n\t\t<div class=\"jive-contentBox_greybox\">\n\t\t\t<strong>");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</strong>\n\t\t\t<p>");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("<br/>\n            <input type=\"checkbox\" value=\"enabled\" name=\"storeAvatarInDB\"");
      out.print( vcardBean.getAvatarStoredInDB() ? " checked" : "");
      out.write('/');
      out.write('>');
      out.write(' ');
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("</p>\n\n\t\t\t<!-- BEGIN vcard table -->\n\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"jive-vcardTable\" id=\"jivevCardTable\">\n\t\t\t\t<thead>\n\t\t\t\t<tr>\n\t\t\t\t\t<th width=\"40%\">");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</th>\n\t\t\t\t\t<th width=\"60%\">");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("</th>\n\t\t\t\t</tr>\n\t\t\t\t</thead>\n\t\t\t\t<tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t<strong>");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("</strong>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"name\" value=\"");
      out.print( vcardBean.getName() );
      out.write("\" id=\"name\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t<strong>");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("</strong>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"email\" value=\"");
      out.print( vcardBean.getEmail() );
      out.write("\" id=\"email\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t<strong>");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("</strong>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"fullName\" value=\"");
      out.print( vcardBean.getFullName() );
      out.write("\" id=\"fullName\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t<strong>");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("</strong>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"nickname\" value=\"");
      out.print( vcardBean.getNickname() );
      out.write("\" id=\"nickname\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t<strong>");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("</strong>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"birthday\" value=\"");
      out.print( vcardBean.getBirthday() );
      out.write("\" id=\"birthday\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t<strong>");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("</strong>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"photo\" value=\"");
      out.print( vcardBean.getPhoto() );
      out.write("\" id=\"photo\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t<strong>");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("</strong>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"homeStreet\" value=\"");
      out.print( vcardBean.getHomeStreet() );
      out.write("\" id=\"homeStreet\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"homeCity\" value=\"");
      out.print( vcardBean.getHomeCity() );
      out.write("\" id=\"homeCity\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"homeState\" value=\"");
      out.print( vcardBean.getHomeState() );
      out.write("\" id=\"homeState\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_33(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"homeZip\" value=\"");
      out.print( vcardBean.getHomeZip() );
      out.write("\" id=\"homeZip\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_34(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"homeCountry\" value=\"");
      out.print( vcardBean.getHomeCountry() );
      out.write("\" id=\"homeCountry\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_35(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"homePhone\" value=\"");
      out.print( vcardBean.getHomePhone() );
      out.write("\" id=\"homePhone\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_36(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"homeMobile\" value=\"");
      out.print( vcardBean.getHomeMobile() );
      out.write("\" id=\"homeMobile\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_37(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"homeFax\" value=\"");
      out.print( vcardBean.getHomeFax() );
      out.write("\" id=\"homeFax\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_38(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"homePager\" value=\"");
      out.print( vcardBean.getHomePager() );
      out.write("\" id=\"homePager\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t<strong>");
      if (_jspx_meth_fmt_message_39(_jspx_page_context))
        return;
      out.write("</strong>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_40(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessStreet\" value=\"");
      out.print( vcardBean.getBusinessStreet() );
      out.write("\" id=\"businessStreet\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_41(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessCity\" value=\"");
      out.print( vcardBean.getBusinessCity() );
      out.write("\" id=\"businessCity\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_42(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessState\" value=\"");
      out.print( vcardBean.getBusinessState() );
      out.write("\" id=\"businessState\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_43(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessZip\" value=\"");
      out.print( vcardBean.getBusinessZip() );
      out.write("\" id=\"businessZip\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_44(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessCountry\" value=\"");
      out.print( vcardBean.getBusinessCountry() );
      out.write("\" id=\"businessCountry\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_45(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessJobTitle\" value=\"");
      out.print( vcardBean.getBusinessJobTitle() );
      out.write("\" id=\"businessJobTitle\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_46(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessDepartment\" value=\"");
      out.print( vcardBean.getBusinessDepartment() );
      out.write("\" id=\"businessDepartment\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_47(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessPhone\" value=\"");
      out.print( vcardBean.getBusinessPhone() );
      out.write("\" id=\"businessPhone\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_48(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessMobile\" value=\"");
      out.print( vcardBean.getBusinessMobile() );
      out.write("\" id=\"businessMobile\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_49(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessFax\" value=\"");
      out.print( vcardBean.getBusinessFax() );
      out.write("\" id=\"businessFax\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-vcardTable-label jive-vardBorderBottom jive-vardBorderRight\" nowrap>\n\t\t\t\t\t\t- ");
      if (_jspx_meth_fmt_message_50(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-vcardTable-value jive-vardBorderBottom\">\n\t\t\t\t\t\t<input type=\"text\" name=\"businessPager\" value=\"");
      out.print( vcardBean.getBusinessPager() );
      out.write("\" id=\"businessPager\" size=\"22\" maxlength=\"50\" onFocus=\"jiveRowHighlight(this);\">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<!-- END vcard table -->\n\n\t\t</div>\n\t\t<!-- END jive-contentBox_greybox -->\n\n\t\t<!-- BEGIN jive-buttons -->\n\t\t<div class=\"jive-buttons\">\n\n\t\t\t<!-- BEGIN right-aligned buttons -->\n\t\t\t<div align=\"right\">\n                <input type=\"Submit\" name=\"test\" value=\"");
      if (_jspx_meth_fmt_message_51(_jspx_page_context))
        return;
      out.write("\" id=\"jive-setup-test\" border=\"0\">\n\n\t\t\t\t<input type=\"Submit\" name=\"save\" value=\"");
      if (_jspx_meth_fmt_message_52(_jspx_page_context))
        return;
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
    _jspx_th_fmt_message_3.setKey("setup.ldap.user_mapping");
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
    _jspx_th_fmt_message_7.setKey("setup.ldap.step_two");
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
    _jspx_th_fmt_message_8.setKey("setup.ldap.user_mapping");
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
    _jspx_th_fmt_message_9.setKey("setup.ldap.user.description");
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
    _jspx_th_fmt_message_10.setKey("setup.ldap.user_mapping");
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
    _jspx_th_fmt_message_11.setKey("setup.ldap.user.username_field");
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
    _jspx_th_fmt_message_12.setKey("setup.ldap.user.username_field_description");
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
    _jspx_th_fmt_message_13.setKey("setup.ldap.advanced");
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
    _jspx_th_fmt_message_14.setKey("setup.ldap.user.search_fields");
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
    _jspx_th_fmt_message_15.setKey("setup.ldap.user.search_fields_description");
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
    _jspx_th_fmt_message_16.setKey("setup.ldap.user.user_filter");
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
    _jspx_th_fmt_message_17.setKey("setup.ldap.user.user_filter_description");
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
    _jspx_th_fmt_message_18.setKey("setup.ldap.user.vcard.mapping");
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
    _jspx_th_fmt_message_19.setKey("setup.ldap.user.vcard.description");
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
    _jspx_th_fmt_message_20.setKey("setup.ldap.user.vcard.avatardb");
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
    _jspx_th_fmt_message_21.setKey("setup.ldap.user.vcard.label1");
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
    _jspx_th_fmt_message_22.setKey("setup.ldap.user.vcard.label2");
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
    _jspx_th_fmt_message_23.setKey("setup.ldap.user.vcard.name");
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
    _jspx_th_fmt_message_24.setKey("setup.ldap.user.vcard.email");
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
    _jspx_th_fmt_message_25.setKey("setup.ldap.user.vcard.fullname");
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
    _jspx_th_fmt_message_26.setKey("setup.ldap.user.vcard.nickname");
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
    _jspx_th_fmt_message_27.setKey("setup.ldap.user.vcard.birthday");
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
    _jspx_th_fmt_message_28.setKey("setup.ldap.user.vcard.photo");
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
    _jspx_th_fmt_message_29.setKey("setup.ldap.user.vcard.home");
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
    _jspx_th_fmt_message_30.setKey("setup.ldap.user.vcard.street");
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
    _jspx_th_fmt_message_31.setKey("setup.ldap.user.vcard.city");
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
    _jspx_th_fmt_message_32.setKey("setup.ldap.user.vcard.state");
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
    _jspx_th_fmt_message_33.setKey("setup.ldap.user.vcard.pcode");
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
    _jspx_th_fmt_message_34.setKey("setup.ldap.user.vcard.country");
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
    _jspx_th_fmt_message_35.setKey("setup.ldap.user.vcard.phone");
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
    _jspx_th_fmt_message_36.setKey("setup.ldap.user.vcard.mobile");
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
    _jspx_th_fmt_message_37.setKey("setup.ldap.user.vcard.fax");
    int _jspx_eval_fmt_message_37 = _jspx_th_fmt_message_37.doStartTag();
    if (_jspx_th_fmt_message_37.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_37);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_37);
    return false;
  }

  private boolean _jspx_meth_fmt_message_38(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_38 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_38.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_38.setParent(null);
    _jspx_th_fmt_message_38.setKey("setup.ldap.user.vcard.pager");
    int _jspx_eval_fmt_message_38 = _jspx_th_fmt_message_38.doStartTag();
    if (_jspx_th_fmt_message_38.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_38);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_38);
    return false;
  }

  private boolean _jspx_meth_fmt_message_39(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_39 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_39.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_39.setParent(null);
    _jspx_th_fmt_message_39.setKey("setup.ldap.user.vcard.business");
    int _jspx_eval_fmt_message_39 = _jspx_th_fmt_message_39.doStartTag();
    if (_jspx_th_fmt_message_39.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_39);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_39);
    return false;
  }

  private boolean _jspx_meth_fmt_message_40(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_40 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_40.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_40.setParent(null);
    _jspx_th_fmt_message_40.setKey("setup.ldap.user.vcard.street");
    int _jspx_eval_fmt_message_40 = _jspx_th_fmt_message_40.doStartTag();
    if (_jspx_th_fmt_message_40.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_40);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_40);
    return false;
  }

  private boolean _jspx_meth_fmt_message_41(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_41 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_41.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_41.setParent(null);
    _jspx_th_fmt_message_41.setKey("setup.ldap.user.vcard.city");
    int _jspx_eval_fmt_message_41 = _jspx_th_fmt_message_41.doStartTag();
    if (_jspx_th_fmt_message_41.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_41);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_41);
    return false;
  }

  private boolean _jspx_meth_fmt_message_42(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_42 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_42.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_42.setParent(null);
    _jspx_th_fmt_message_42.setKey("setup.ldap.user.vcard.state");
    int _jspx_eval_fmt_message_42 = _jspx_th_fmt_message_42.doStartTag();
    if (_jspx_th_fmt_message_42.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_42);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_42);
    return false;
  }

  private boolean _jspx_meth_fmt_message_43(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_43 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_43.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_43.setParent(null);
    _jspx_th_fmt_message_43.setKey("setup.ldap.user.vcard.pcode");
    int _jspx_eval_fmt_message_43 = _jspx_th_fmt_message_43.doStartTag();
    if (_jspx_th_fmt_message_43.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_43);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_43);
    return false;
  }

  private boolean _jspx_meth_fmt_message_44(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_44 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_44.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_44.setParent(null);
    _jspx_th_fmt_message_44.setKey("setup.ldap.user.vcard.country");
    int _jspx_eval_fmt_message_44 = _jspx_th_fmt_message_44.doStartTag();
    if (_jspx_th_fmt_message_44.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_44);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_44);
    return false;
  }

  private boolean _jspx_meth_fmt_message_45(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_45 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_45.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_45.setParent(null);
    _jspx_th_fmt_message_45.setKey("setup.ldap.user.vcard.title");
    int _jspx_eval_fmt_message_45 = _jspx_th_fmt_message_45.doStartTag();
    if (_jspx_th_fmt_message_45.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_45);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_45);
    return false;
  }

  private boolean _jspx_meth_fmt_message_46(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_46 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_46.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_46.setParent(null);
    _jspx_th_fmt_message_46.setKey("setup.ldap.user.vcard.department");
    int _jspx_eval_fmt_message_46 = _jspx_th_fmt_message_46.doStartTag();
    if (_jspx_th_fmt_message_46.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_46);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_46);
    return false;
  }

  private boolean _jspx_meth_fmt_message_47(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_47 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_47.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_47.setParent(null);
    _jspx_th_fmt_message_47.setKey("setup.ldap.user.vcard.phone");
    int _jspx_eval_fmt_message_47 = _jspx_th_fmt_message_47.doStartTag();
    if (_jspx_th_fmt_message_47.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_47);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_47);
    return false;
  }

  private boolean _jspx_meth_fmt_message_48(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_48 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_48.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_48.setParent(null);
    _jspx_th_fmt_message_48.setKey("setup.ldap.user.vcard.mobile");
    int _jspx_eval_fmt_message_48 = _jspx_th_fmt_message_48.doStartTag();
    if (_jspx_th_fmt_message_48.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_48);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_48);
    return false;
  }

  private boolean _jspx_meth_fmt_message_49(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_49 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_49.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_49.setParent(null);
    _jspx_th_fmt_message_49.setKey("setup.ldap.user.vcard.fax");
    int _jspx_eval_fmt_message_49 = _jspx_th_fmt_message_49.doStartTag();
    if (_jspx_th_fmt_message_49.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_49);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_49);
    return false;
  }

  private boolean _jspx_meth_fmt_message_50(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_50 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_50.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_50.setParent(null);
    _jspx_th_fmt_message_50.setKey("setup.ldap.user.vcard.pager");
    int _jspx_eval_fmt_message_50 = _jspx_th_fmt_message_50.doStartTag();
    if (_jspx_th_fmt_message_50.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_50);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_50);
    return false;
  }

  private boolean _jspx_meth_fmt_message_51(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_51 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_51.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_51.setParent(null);
    _jspx_th_fmt_message_51.setKey("setup.ldap.test");
    int _jspx_eval_fmt_message_51 = _jspx_th_fmt_message_51.doStartTag();
    if (_jspx_th_fmt_message_51.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_51);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_51);
    return false;
  }

  private boolean _jspx_meth_fmt_message_52(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_52 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_52.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_52.setParent(null);
    _jspx_th_fmt_message_52.setKey("setup.ldap.continue");
    int _jspx_eval_fmt_message_52 = _jspx_th_fmt_message_52.doStartTag();
    if (_jspx_th_fmt_message_52.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_52);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_52);
    return false;
  }
}
