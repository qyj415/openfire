package org.jivesoftware.openfire.admin.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.ldap.LdapManager;
import org.jivesoftware.util.JiveGlobals;
import java.util.*;

public final class setup_002dldap_002dserver_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/setup/ldap-server.jspf");
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

    // Redirect if we've already run setup:
    if (!XMPPServer.getInstance().isSetupMode()) {
        response.sendRedirect("setup-completed.jsp");
        return;
    }

      out.write('\n');
      out.write('\n');

    String serverType = null;
    if (request.getParameter("save") != null || request.getParameter("test") != null) {
        int serverTypeInt = ParamUtils.getIntParameter(request, "servertype", 1);
        switch (serverTypeInt) {
            case 1:
                serverType = "other";
                break;
            case 2:
                serverType = "activedirectory";
                break;
            case 3:
                serverType = "openldap";
                break;
            default:
                serverType = "other";
        }
    }

    boolean initialSetup = true;
    String currentPage = "setup-ldap-server.jsp";
    String testPage = "setup-ldap-server_test.jsp?serverType="+ serverType;
    String nextPage = "setup-ldap-user.jsp?serverType=" + serverType;
    Map<String, String> meta = new HashMap<String, String>();
    meta.put("currentStep", "3");

      out.write('\n');
      out.write("\n\n\n\n\n\n\n\n");

    String host;
    int port = 389;
    String baseDN;
    String adminDN;
    String adminPassword;
    boolean connectionPoolEnabled = true;
    boolean sslEnabled = false;
    boolean debugEnabled = false;
    boolean referralsEnabled = false;
    boolean aliasReferralsEnabled = true;
    boolean encloseDNs = true;

    @SuppressWarnings("unchecked")
    Map<String,String> xmppSettings = (Map<String,String>)session.getAttribute("xmppSettings");

    // Get parameters
    boolean save = request.getParameter("save") != null;
    boolean test = request.getParameter("test") != null;

    LdapManager manager = LdapManager.getInstance();
    Map<String, String> errors = new HashMap<String, String>();

    if (save || test) {
        host = ParamUtils.getParameter(request, "host");
        if (host == null) {
            errors.put("host", LocaleUtils.getLocalizedString("setup.ldap.server.host_error"));
        }
        port = ParamUtils.getIntParameter(request, "port", port);
        if (port <= 0) {
            errors.put("port", LocaleUtils.getLocalizedString("setup.ldap.server.port_error"));
        }
        baseDN = ParamUtils.getParameter(request, "basedn");
        if (baseDN == null) {
            errors.put("baseDN", LocaleUtils.getLocalizedString("setup.ldap.server.basedn_error"));
        }
        adminDN = ParamUtils.getParameter(request, "admindn");
        adminPassword = ParamUtils.getParameter(request, "adminpwd");
        connectionPoolEnabled =
                ParamUtils.getBooleanParameter(request, "connectionpool", connectionPoolEnabled);
        sslEnabled = ParamUtils.getBooleanParameter(request, "ssl", sslEnabled);
        debugEnabled = ParamUtils.getBooleanParameter(request, "debug", debugEnabled);
        referralsEnabled = ParamUtils.getBooleanParameter(request, "referrals", referralsEnabled);
        aliasReferralsEnabled = ParamUtils.getBooleanParameter(request, "aliasreferrals", aliasReferralsEnabled);
        encloseDNs = ParamUtils.getBooleanParameter(request, "enclosedns", encloseDNs);
	
        if (errors.isEmpty()) {
            // Store settings in a map and keep it in the session
            Map<String, String> settings = new HashMap<String, String>();
            settings.put("ldap.serverType", serverType);
            settings.put("ldap.host", host);
            settings.put("ldap.port", Integer.toString(port));
            settings.put("ldap.baseDN", baseDN);
            if (adminDN != null) {
                settings.put("ldap.adminDN", adminDN);
            }
            if (adminPassword != null) {
                settings.put("ldap.adminPassword", adminPassword);
            }
            settings.put("ldap.connectionPoolEnabled",
                    Boolean.toString(connectionPoolEnabled));
            settings.put("ldap.sslEnabled", Boolean.toString(sslEnabled));
            settings.put("ldap.debugEnabled", Boolean.toString(debugEnabled));
            settings.put("ldap.autoFollowReferrals", Boolean.toString(referralsEnabled));
            settings.put("ldap.autoFollowAliasReferrals", Boolean.toString(aliasReferralsEnabled));
    		settings.put("ldap.encloseDNs", Boolean.toString(encloseDNs));

            // Always disable connection pooling so that connections aren't left hanging open.
            settings.put("ldap.connectionPoolEnabled", "false");
            session.setAttribute("ldapSettings", settings);

            if (save) {
                // Save settings and redirect
                Collection<String> hosts = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(host, " ,\t\n\r\f");
                while (st.hasMoreTokens()) {
                    hosts.add(st.nextToken());
                }
                manager.setHosts(hosts);
                manager.setPort(port);
                manager.setBaseDN(baseDN);
                manager.setAdminDN(adminDN);
                manager.setAdminPassword(adminPassword);
                manager.setConnectionPoolEnabled(connectionPoolEnabled);
                manager.setSslEnabled(sslEnabled);
                manager.setDebugEnabled(debugEnabled);
                manager.setFollowReferralsEnabled(referralsEnabled);
                manager.setFollowAliasReferralsEnabled(aliasReferralsEnabled);
				manager.setIsEnclosingDNs(encloseDNs);
				
                // Save the settings for later, if we're in setup
                if (xmppSettings != null) {
                    xmppSettings.put("ldap.host", host);
                    xmppSettings.put("ldap.port", Integer.toString(port));
                    xmppSettings.put("ldap.baseDN", baseDN);
                    xmppSettings.put("ldap.adminDN", adminDN);
                    xmppSettings.put("ldap.adminPassword", adminPassword);
                    xmppSettings.put("ldap.connectionPoolEnabled", Boolean.toString(connectionPoolEnabled));
                    xmppSettings.put("ldap.sslEnabled", Boolean.toString(sslEnabled));
                    xmppSettings.put("ldap.debugEnabled", Boolean.toString(debugEnabled));
                    xmppSettings.put("ldap.autoFollowReferrals", Boolean.toString(referralsEnabled));
                    xmppSettings.put("ldap.autoFollowAliasReferrals", Boolean.toString(aliasReferralsEnabled));
                    xmppSettings.put("ldap.encloseDNs", Boolean.toString(encloseDNs));

                    JiveGlobals.setPropertyEncrypted("ldap.adminDN", true);
                    JiveGlobals.setPropertyEncrypted("ldap.adminPassword", true);

                    session.setAttribute("xmppSettings", xmppSettings);
                }

                // Redirect to next step.
                response.sendRedirect(nextPage);
                return;
            }
        }
    } else {
        // See if there are already values for the variables defined.
        StringBuilder sb = new StringBuilder();
        for (String aHost : LdapManager.getInstance().getHosts()) {
            sb.append(aHost).append(", ");
        }
        host = sb.toString();
        if (host.trim().length() > 0) {
            host = host.substring(0, host.length() - 2);
        }
        port = manager.getPort();
        baseDN = manager.getBaseDN();
        adminDN = manager.getAdminDN();
        adminPassword = manager.getAdminPassword();
        connectionPoolEnabled = manager.isConnectionPoolEnabled();
        sslEnabled = manager.isSslEnabled();
        debugEnabled = manager.isDebugEnabled();
        referralsEnabled = manager.isFollowReferralsEnabled();
        aliasReferralsEnabled = manager.isFollowAliasReferralsEnabled();
        encloseDNs = manager.isEnclosingDNs();
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
      out.write("\n</head>\n<body>\n\n    ");
 if (test && errors.isEmpty()) { 
      out.write("\n\n        <a href=\"");
      out.print( testPage);
      out.write("\" id=\"lbmessage\" title=\"");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\" style=\"display:none;\"></a>\n        <script type=\"text/javascript\">\n            function loadMsg() {\n                var lb = new lightbox(document.getElementById('lbmessage'));\n                lb.activate();\n            }\n            setTimeout('loadMsg()', 250);\n        </script>\n\n    ");
 } 
      out.write("\n\n    ");
 if (initialSetup) { 
      out.write("\n    <h1>");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write(": <span>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</span></h1>\n    ");
 } 
      out.write("\n\n    <!-- BEGIN jive-contentBox_stepbar -->\n\t<div id=\"jive-contentBox_stepbar\">\n\t\t<span class=\"jive-stepbar_step\"><strong>1. ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</strong></span>\n\t\t<span class=\"jive-stepbar_step\"><em>2. ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</em></span>\n\t\t<span class=\"jive-stepbar_step\"><em>3. ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</em></span>\n\t</div>\n\t<!-- END jive-contentBox-stepbar -->\n\n    <!-- BEGIN jive-contentBox -->\n    <div class=\"jive-contentBox jive-contentBox_for-stepbar\">\n\n\t<h2>");
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
      out.write("\" method=\"post\">\n\t\t<!-- BEGIN jive-contentBox_bluebox -->\n\t\t<div class=\"jive-contentBox_bluebox\">\n\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\n\t\t\t<tr>\n\t\t\t    <td colspan=\"4\"><strong>");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</strong></td>\n\t\t\t</tr>\n            ");
 if (initialSetup) { 
      out.write("\n            <tr>\n                <td align=\"right\" width=\"1%\" nowrap=\"nowrap\">");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write(":</td>\n                <td colspan=\"3\" nowrap>\n                    <select name=\"servertype\" size=\"1\" id=\"jiveLDAPserverType\" style=\"width:90%;\">\n                        <option value=\"1\" ");
      out.print( serverType == null ? "selected" : "" );
      out.write('>');
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</option>\n                        <option value=\"2\" ");
      out.print( "activedirectory".equals(serverType) ? "selected" : "" );
      out.write(">Active Directory</option>\n                        <option value=\"3\" ");
      out.print( "openldap".equals(serverType) ? "selected" : "" );
      out.write(">OpenLDAP</option>\n                        <option value=\"4\" ");
      out.print( "other".equals(serverType) ? "selected" : "" );
      out.write('>');
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("</option>\n                    </select><span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span>\n                </td>\n\t\t\t</tr>\n            ");
 } 
      out.write("\n            <tr>\n\t\t\t    <td align=\"right\" width=\"1%\" nowrap=\"nowrap\">");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write(":</td>\n                <td width=\"1%\">\n                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n                    <tr>\n                        <td width=\"1%\" nowrap=\"nowrap\">\n                            <input type=\"text\" name=\"host\" id=\"jiveLDAPphost\" size=\"22\" maxlength=\"50\" value=\"");
      out.print( host!=null?host:"" );
      out.write("\">\n                        </td>\n                        <td width=\"99%\">\n                            <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span>\n                        </td>\n                    </tr>\n                    </table>\n                </td>\n                <td align=\"right\" width=\"1%\" nowrap=\"nowrap\">&nbsp;&nbsp; ");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write(":</td>\n                <td><input type=\"text\" name=\"port\" id=\"jiveLDAPport\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( port );
      out.write("\"><span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span></td>\n\t\t\t</tr>\n\t\t\t<tr>\n                <td align=\"right\">");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write(":</td>\n                <td colspan=\"3\">\n                    <input type=\"text\" name=\"basedn\" id=\"jiveLDAPbasedn\" size=\"40\" maxlength=\"150\" value=\"");
      out.print( baseDN!=null?baseDN:"");
      out.write("\" style=\"width:90%;\">\n                    <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 16000);\"></span>\n                </td>\n\t\t\t</tr>\n            <tr><td colspan=\"4\">&nbsp;</td></tr>\n            <tr>\n\t\t\t    <td colspan=\"4\"><strong>");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write(":</strong></td>\n\t\t\t</tr>\n\t\t\t<tr>\n                <td align=\"right\" width=\"1%\" nowrap=\"nowrap\">");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write(":</td>\n                <td colspan=\"3\">\n                    <input type=\"text\" name=\"admindn\" id=\"jiveLDAPadmindn\" size=\"40\" maxlength=\"150\" value=\"");
      out.print( adminDN!=null?adminDN:"");
      out.write("\" style=\"width:90%;\">\n                    <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', -1);\"></span>\n                </td>\n\t\t\t</tr>\n\t\t\t<tr>\n                <td align=\"right\" width=\"1%\" nowrap=\"nowrap\">");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write(":</td>\n                <td colspan=\"3\"><input type=\"password\" name=\"adminpwd\" id=\"jiveLDAPadminpwd\" size=\"22\" maxlength=\"30\" value=\"");
      out.print( adminPassword!=null?adminPassword:"");
      out.write("\"> <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span></td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t</div>\n\t\t<!-- END jive-contentBox_bluebox -->\n\n\n\t\t<!-- BEGIN jiveAdvancedButton -->\n\t\t<div class=\"jiveAdvancedButton\">\n\t\t\t<a href=\"#\" onclick=\"togglePanel(jiveAdvanced); return false;\" id=\"jiveAdvancedLink\">");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("</a>\n\t\t</div>\n\t\t<!-- END jiveAdvancedButton -->\n\n\t\t<!-- BEGIN jiveAdvancedPanelcs (advanced connection settings) -->\n\t\t<div class=\"jiveadvancedPanelcs\" id=\"jiveAdvanced\" style=\"display: none;\">\n\t\t\t<div>\n\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"1\">\n\t\t\t\t<thead>\n\t\t\t\t<tr>\n\t\t\t\t\t<th width=\"10%\"></th>\n\t\t\t\t\t<th></th>\n\t\t\t\t\t<th width=\"50\">");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("</th>\n\t\t\t\t\t<th width=\"50\">");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("</th>\n\t\t\t\t</tr>\n\t\t\t\t</thead>\n\t\t\t\t<tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-advancedLabel\" nowrap>\n\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write(":\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n\t\t\t\t\t    ");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n\t\t\t\t\t\t<input type=\"radio\" name=\"connectionpool\" value=\"true\" ");
 if (connectionPoolEnabled) { 
      out.write("checked ");
 } 
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedBorderBottom\" align=\"center\">\n\t\t\t\t\t\t<input type=\"radio\" name=\"connectionpool\" value=\"false\" ");
 if (!connectionPoolEnabled) { 
      out.write("checked ");
 } 
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-advancedLabel\" nowrap>\n\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write(":\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n\t\t\t\t\t\t<input type=\"radio\" name=\"ssl\" value=\"true\" ");
 if (sslEnabled) { 
      out.write("checked ");
 } 
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedBorderBottom\" align=\"center\">\n\t\t\t\t\t\t<input type=\"radio\" name=\"ssl\" value=\"false\" ");
 if (!sslEnabled) { 
      out.write("checked ");
 } 
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-advancedLabel\" nowrap>\n\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_33(_jspx_page_context))
        return;
      out.write(":\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_34(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n\t\t\t\t\t\t<input type=\"radio\" name=\"debug\" value=\"true\" ");
 if (debugEnabled) { 
      out.write("checked ");
 } 
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedBorderBottom\" align=\"center\">\n\t\t\t\t\t\t<input type=\"radio\" name=\"debug\" value=\"false\" ");
 if (!debugEnabled) { 
      out.write("checked ");
 } 
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-advancedLabel\" nowrap>\n\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_35(_jspx_page_context))
        return;
      out.write(":\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_36(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n\t\t\t\t\t\t<input type=\"radio\" name=\"referrals\" value=\"true\" ");
 if (referralsEnabled) { 
      out.write("checked ");
 } 
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"jive-advancedBorderBottom\" align=\"center\">\n\t\t\t\t\t\t<input type=\"radio\" name=\"referrals\" value=\"false\" ");
 if (!referralsEnabled) { 
      out.write("checked ");
 } 
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n                <tr>\n                    <td class=\"jive-advancedLabel\" nowrap>\n                        ");
      if (_jspx_meth_fmt_message_37(_jspx_page_context))
        return;
      out.write(":\n                    </td>\n                    <td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n                        ");
      if (_jspx_meth_fmt_message_38(_jspx_page_context))
        return;
      out.write("\n                    </td>\n                    <td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n                        <input type=\"radio\" name=\"aliasreferrals\" value=\"true\" ");
 if (aliasReferralsEnabled) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                    <td class=\"jive-advancedBorderBottom\" align=\"center\">\n                        <input type=\"radio\" name=\"aliasreferrals\" value=\"false\" ");
 if (!aliasReferralsEnabled) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                </tr>\n                <tr>\n                    <td class=\"jive-advancedLabel\" nowrap>\n                        ");
      if (_jspx_meth_fmt_message_39(_jspx_page_context))
        return;
      out.write(":\n                    </td>\n                    <td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n                        ");
      if (_jspx_meth_fmt_message_40(_jspx_page_context))
        return;
      out.write("\n                    </td>\n                    <td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n                        <input type=\"radio\" name=\"enclosedns\" value=\"true\" ");
 if (encloseDNs) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                    <td class=\"jive-advancedBorderBottom\" align=\"center\">\n                        <input type=\"radio\" name=\"enclosedns\" value=\"false\" ");
 if (!encloseDNs) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                </tr>                \n                </tbody>\n\t\t\t\t</table>\n\t\t\t</div>\n\t\t</div>\n\t\t<!-- END jiveAdvancedPanelcs (advanced connection settings) -->\n\n\n\t\t<!-- BEGIN jive-buttons -->\n\t\t<div class=\"jive-buttons\">\n\n\t\t\t<!-- BEGIN right-aligned buttons -->\n\t\t\t<div align=\"right\">\n\n                <input type=\"Submit\" name=\"test\" value=\"");
      if (_jspx_meth_fmt_message_41(_jspx_page_context))
        return;
      out.write("\" id=\"jive-setup-test\" border=\"0\">\n\n                <input type=\"Submit\" name=\"save\" value=\"");
      if (_jspx_meth_fmt_message_42(_jspx_page_context))
        return;
      out.write("\" id=\"jive-setup-save\" border=\"0\">\n\t\t\t</div>\n\t\t\t<!-- END right-aligned buttons -->\n\n\t\t</div>\n\t\t<!-- END jive-buttons -->\n\n\t</form>\n\n\t</div>\n\t<!-- END jive-contentBox -->\n\n\n\n</body>\n</html>\n");
      out.write('\n');
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
    _jspx_th_fmt_message_3.setKey("setup.ldap.connection_settings");
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
    _jspx_th_fmt_message_7.setKey("setup.ldap.step_one");
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
    _jspx_th_fmt_message_8.setKey("setup.ldap.connection_settings");
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
    _jspx_th_fmt_message_9.setKey("setup.ldap.server.description");
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
    _jspx_th_fmt_message_10.setKey("setup.ldap.server.ldap_server");
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
    _jspx_th_fmt_message_11.setKey("setup.ldap.server.type");
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
    _jspx_th_fmt_message_12.setKey("setup.ldap.server.type_select");
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
    _jspx_th_fmt_message_13.setKey("setup.ldap.server.type_other");
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
    _jspx_th_fmt_message_14.setKey("setup.ldap.server.type_help");
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
    _jspx_th_fmt_message_15.setKey("setup.ldap.server.host");
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
    _jspx_th_fmt_message_16.setKey("setup.ldap.server.host_help");
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
    _jspx_th_fmt_message_17.setKey("setup.ldap.server.port");
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
    _jspx_th_fmt_message_18.setKey("setup.ldap.server.port_help");
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
    _jspx_th_fmt_message_19.setKey("setup.ldap.server.basedn");
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
    _jspx_th_fmt_message_20.setKey("setup.ldap.server.basedn_help");
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
    _jspx_th_fmt_message_21.setKey("setup.ldap.server.auth");
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
    _jspx_th_fmt_message_22.setKey("setup.ldap.server.admindn");
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
    _jspx_th_fmt_message_23.setKey("setup.ldap.server.admindn_help");
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
    _jspx_th_fmt_message_24.setKey("setup.ldap.server.password");
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
    _jspx_th_fmt_message_25.setKey("setup.ldap.server.password_help");
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
    _jspx_th_fmt_message_26.setKey("setup.ldap.advanced");
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
    _jspx_th_fmt_message_27.setKey("global.yes");
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
    _jspx_th_fmt_message_28.setKey("global.no");
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
    _jspx_th_fmt_message_29.setKey("setup.ldap.server.connection_pool");
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
    _jspx_th_fmt_message_30.setKey("setup.ldap.server.connection_pool_help");
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
    _jspx_th_fmt_message_31.setKey("setup.ldap.server.ssl");
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
    _jspx_th_fmt_message_32.setKey("setup.ldap.server.ssl_help");
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
    _jspx_th_fmt_message_33.setKey("setup.ldap.server.debug");
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
    _jspx_th_fmt_message_34.setKey("setup.ldap.server.debug_help");
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
    _jspx_th_fmt_message_35.setKey("setup.ldap.server.referral");
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
    _jspx_th_fmt_message_36.setKey("setup.ldap.server.referral_help");
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
    _jspx_th_fmt_message_37.setKey("setup.ldap.server.alias_dereference");
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
    _jspx_th_fmt_message_38.setKey("setup.ldap.server.alias_dereference_help");
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
    _jspx_th_fmt_message_39.setKey("setup.ldap.server.enclose_dns");
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
    _jspx_th_fmt_message_40.setKey("setup.ldap.server.enclose_dns_help");
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
    _jspx_th_fmt_message_41.setKey("setup.ldap.test");
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
    _jspx_th_fmt_message_42.setKey("setup.ldap.continue");
    int _jspx_eval_fmt_message_42 = _jspx_th_fmt_message_42.doStartTag();
    if (_jspx_th_fmt_message_42.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_42);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_42);
    return false;
  }
}
