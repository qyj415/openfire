package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.Connection;
import org.jivesoftware.openfire.ConnectionManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.server.ServerDialback;
import org.jivesoftware.openfire.session.LocalClientSession;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.ParamUtils;

public final class ssl_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
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
  try { 
      out.write('\n');
      out.write('\n');
 // Get parameters:
    boolean update = request.getParameter("update") != null;
    boolean success = ParamUtils.getBooleanParameter(request, "success");
    // Client configuration parameters
    String clientSecurityRequired = ParamUtils.getParameter(request, "clientSecurityRequired");
    String ssl = ParamUtils.getParameter(request, "ssl");
    String tls = ParamUtils.getParameter(request, "tls");
    // Server configuration parameters
    String serverSecurityRequired = ParamUtils.getParameter(request, "serverSecurityRequired");
    String dialback = ParamUtils.getParameter(request, "dialback");
    String server_tls = ParamUtils.getParameter(request, "server_tls");
    boolean selfSigned = ParamUtils.getBooleanParameter(request, "selfSigned");

    if (update) {
        if ("req".equals(clientSecurityRequired)) {
            // User selected that security is required

            // Enable 5222 port and make TLS required
            XMPPServer.getInstance().getConnectionManager().enableClientListener(true);
            LocalClientSession.setTLSPolicy(Connection.TLSPolicy.required);
            // Enable 5223 port (old SSL port)
            XMPPServer.getInstance().getConnectionManager().enableClientSSLListener(true);
        } else if ("notreq".equals(clientSecurityRequired)) {
            // User selected that security is NOT required

            // Enable 5222 port and make TLS optional
            XMPPServer.getInstance().getConnectionManager().enableClientListener(true);
            LocalClientSession.setTLSPolicy(Connection.TLSPolicy.optional);
            // Enable 5223 port (old SSL port)
            XMPPServer.getInstance().getConnectionManager().enableClientSSLListener(true);
        } else if ("custom".equals(clientSecurityRequired)) {
            // User selected custom client authentication

            // Enable or disable 5223 port (old SSL port)
            XMPPServer.getInstance().getConnectionManager().enableClientSSLListener("available".equals(ssl));

            // Enable port 5222 and configure TLS policy
            XMPPServer.getInstance().getConnectionManager().enableClientListener(true);
            if ("notavailable".equals(tls)) {
                LocalClientSession.setTLSPolicy(Connection.TLSPolicy.disabled);
            } else if ("optional".equals(tls)) {
                LocalClientSession.setTLSPolicy(Connection.TLSPolicy.optional);
            } else {
                LocalClientSession.setTLSPolicy(Connection.TLSPolicy.required);
            }
        }

        if ("req".equals(serverSecurityRequired)) {
            // User selected that security for s2s is required

            // Enable TLS and disable server dialback
            XMPPServer.getInstance().getConnectionManager().enableServerListener(true);
            JiveGlobals.setProperty("xmpp.server.tls.enabled", "true");
            JiveGlobals.setProperty("xmpp.server.dialback.enabled", "false");
        } else if ("notreq".equals(serverSecurityRequired)) {
            // User selected that security for s2s is NOT required

            // Enable TLS and enable server dialback
            XMPPServer.getInstance().getConnectionManager().enableServerListener(true);
            JiveGlobals.setProperty("xmpp.server.tls.enabled", "true");
            JiveGlobals.setProperty("xmpp.server.dialback.enabled", "true");
        } else if ("custom".equals(serverSecurityRequired)) {
            // User selected custom server authentication

            boolean dialbackEnabled = "available".equals(dialback);
            boolean tlsEnabled = "optional".equals(server_tls);

            if (dialbackEnabled || tlsEnabled) {
                XMPPServer.getInstance().getConnectionManager().enableServerListener(true);

                // Enable or disable server dialback
                JiveGlobals.setProperty("xmpp.server.dialback.enabled", dialbackEnabled ? "true" : "false");

                // Enable or disable TLS for s2s connections
                JiveGlobals.setProperty("xmpp.server.tls.enabled", tlsEnabled ? "true" : "false");
            } else {
                XMPPServer.getInstance().getConnectionManager().enableServerListener(false);
                // Disable server dialback
                JiveGlobals.setProperty("xmpp.server.dialback.enabled", "false");

                // Disable TLS for s2s connections
                JiveGlobals.setProperty("xmpp.server.tls.enabled", "false");
            }
        }
        ServerDialback.setEnabledForSelfSigned(selfSigned);
        success = true;
        // Log the event
        webManager.logEvent("updated SSL configuration", "xmpp.server.dialback.enabled = "+JiveGlobals.getProperty("xmpp.server.dialback.enabled")+"\nxmpp.server.tls.enabled = "+JiveGlobals.getProperty("xmpp.server.tls.enabled"));
    }

    // Set page vars
    ConnectionManager connectionManager = XMPPServer.getInstance().getConnectionManager();
    if (connectionManager.isClientListenerEnabled() && connectionManager.isClientSSLListenerEnabled()) {
        if (Connection.TLSPolicy.required.equals(LocalClientSession.getTLSPolicy())) {
            clientSecurityRequired = "req";
            ssl = "available";
            tls = "required";
        } else if (Connection.TLSPolicy.optional.equals(LocalClientSession.getTLSPolicy())) {
            clientSecurityRequired = "notreq";
            ssl = "available";
            tls = "optional";
        } else {
            clientSecurityRequired = "custom";
            ssl = "available";
            tls = "notavailable";
        }
    } else {
        clientSecurityRequired = "custom";
        ssl = connectionManager.isClientSSLListenerEnabled() ? "available" : "notavailable";
        tls = Connection.TLSPolicy.disabled.equals(LocalClientSession.getTLSPolicy()) ? "notavailable" :
                LocalClientSession.getTLSPolicy().toString();
    }

    boolean tlsEnabled = JiveGlobals.getBooleanProperty("xmpp.server.tls.enabled", true);
    boolean dialbackEnabled = JiveGlobals.getBooleanProperty("xmpp.server.dialback.enabled", true);
    if (tlsEnabled) {
        if (dialbackEnabled) {
            serverSecurityRequired = "notreq";
            dialback = "available";
            server_tls = "optional";
        } else {
            serverSecurityRequired = "req";
            dialback = "notavailable";
            server_tls = "optional";
        }
    } else {
        serverSecurityRequired = "custom";
        dialback = dialbackEnabled ? "available" : "notavailable";
        server_tls = "notavailable";
    }
    selfSigned = ServerDialback.isEnabledForSelfSigned();

      out.write("\n\n<html>\n<head>\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n<meta name=\"pageID\" content=\"server-ssl\"/>\n<meta name=\"helpPage\" content=\"manage_security_certificates.html\"/>\n<script language=\"JavaScript\" type=\"text/javascript\">\n\t<!-- // code for window popups\n\tfunction showOrHide(whichLayer, mode)\n\t{\n\n\t\tif (mode == \"show\") {\n\t\t\tmode = \"\";\n\t\t}\n\t\telse {\n\t\t\tmode = \"none\";\n\t\t}\n\n\t\tif (document.getElementById)\n\t\t{\n\t\t\t// this is the way the standards work\n\t\t\tvar style2 = document.getElementById(whichLayer).style;\n\t\t\tstyle2.display = mode;\n\t\t}\n\t\telse if (document.all)\n\t\t{\n\t\t\t// this is the way old msie versions work\n\t\t\tvar style2 = document.all[whichLayer].style;\n\t\t\tstyle2.display = mode;\n\t\t}\n\t\telse if (document.layers)\n\t\t{\n\t\t\t// this is the way nn4 works\n\t\t\tvar style2 = document.layers[whichLayer].style;\n\t\t\tstyle2.display = mode;\n\t\t}\n\t}\n\t//-->\n</script>\n</head>\n<body>\n\n");
  if (success) { 
      out.write("\n\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n        <td class=\"jive-icon-label\">\n        ");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\n        </td></tr>\n    </tbody>\n    </table>\n    </div><br>\n\n");
  } else if (ParamUtils.getBooleanParameter(request,"deletesuccess")) { 
      out.write("\n\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n        <td class=\"jive-icon-label\">\n        ");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\n        </td></tr>\n    </tbody>\n    </table>\n    </div><br>\n\n");
  } 
      out.write("\n\n<p>\n");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\n</p>\n\n\n<!-- BEGIN 'Client Connection Security' -->\n<form action=\"ssl-settings.jsp\" method=\"post\">\n\t<div class=\"jive-contentBox\" style=\"-moz-border-radius: 3px;\">\n\t<h4>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</h4>\n\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n\t\t<tbody>\n\t\t\t<tr valign=\"middle\">\n\t\t\t\t<tr valign=\"middle\">\n\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t<input type=\"radio\" name=\"clientSecurityRequired\" value=\"notreq\" id=\"rb02\" onclick=\"showOrHide('custom', 'hide')\"\n\t\t\t\t\t\t ");
      out.print( ("notreq".equals(clientSecurityRequired) ? "checked" : "") );
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t<label for=\"rb02\">\n\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</label>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr valign=\"middle\">\n\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t<input type=\"radio\" name=\"clientSecurityRequired\" value=\"req\" id=\"rb01\" onclick=\"showOrHide('custom', 'hide')\"\n\t\t\t\t\t ");
      out.print( ("req".equals(clientSecurityRequired) ? "checked" : "") );
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t<label for=\"rb01\">\n\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</label>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr valign=\"middle\">\n\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t<input type=\"radio\" name=\"clientSecurityRequired\" value=\"custom\" id=\"rb03\" onclick=\"showOrHide('custom', 'show')\"\n\t\t\t\t\t\t ");
      out.print( ("custom".equals(clientSecurityRequired) ? "checked" : "") );
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t<label for=\"rb03\">\n\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</label>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr valign=\"top\" id=\"custom\" ");
 if (!"custom".equals(clientSecurityRequired)) out.write("style=\"display:none\""); 
      out.write(">\n\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n\t\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"ssl\" value=\"notavailable\" id=\"rb04\" ");
      out.print( ("notavailable".equals(ssl) ? "checked" : "") );
      out.write("\n\t\t\t\t\t\t\t\t\t   onclick=\"this.form.clientSecurityRequired[2].checked=true;\">&nbsp;<label for=\"rb04\">");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</label>&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"ssl\" value=\"available\" id=\"rb05\" ");
      out.print( ("available".equals(ssl) ? "checked" : "") );
      out.write("\n\t\t\t\t\t\t\t\t\t   onclick=\"this.form.clientSecurityRequired[2].checked=true;\">&nbsp;<label for=\"rb05\">");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("</label>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"tls\" value=\"notavailable\" id=\"rb06\" ");
      out.print( ("notavailable".equals(tls) ? "checked" : "") );
      out.write("\n\t\t\t\t\t\t\t\t\t   onclick=\"this.form.clientSecurityRequired[2].checked=true;\">&nbsp;<label for=\"rb06\">");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</label>&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"tls\" value=\"optional\" id=\"rb07\" ");
      out.print( ("optional".equals(tls) ? "checked" : "") );
      out.write("\n\t\t\t\t\t\t\t\t\t   onclick=\"this.form.clientSecurityRequired[2].checked=true;\">&nbsp;<label for=\"rb07\">");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("</label>&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"tls\" value=\"required\" id=\"rb08\" ");
      out.print( ("required".equals(tls) ? "checked" : "") );
      out.write("\n\t\t\t\t\t\t\t\t\t   onclick=\"this.form.clientSecurityRequired[2].checked=true;\">&nbsp;<label for=\"rb08\">");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("</label>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t    </tbody>\n\t\t</table>\n\n\n<!-- END 'Client Connection Security' -->\n\n    <br/>\n    <br/>\n\n<!-- BEGIN 'Server Connection Security' -->\n\n    <h4>");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</h4>\n\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n\t\t<tbody>\n\t\t\t<tr valign=\"middle\">\n\t\t\t\t<tr valign=\"middle\">\n\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t<input type=\"radio\" name=\"serverSecurityRequired\" value=\"notreq\" id=\"rb09\" onclick=\"showOrHide('server_custom', 'hide')\"\n\t\t\t\t\t\t ");
      out.print( ("notreq".equals(serverSecurityRequired) ? "checked" : "") );
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t<label for=\"rb09\">\n\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</label>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr valign=\"middle\">\n\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t<input type=\"radio\" name=\"serverSecurityRequired\" value=\"req\" id=\"rb10\" onclick=\"showOrHide('server_custom', 'hide')\"\n\t\t\t\t\t ");
      out.print( ("req".equals(serverSecurityRequired) ? "checked" : "") );
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t<label for=\"rb10\">\n\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</label>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr valign=\"middle\">\n\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t<input type=\"radio\" name=\"serverSecurityRequired\" value=\"custom\" id=\"rb11\" onclick=\"showOrHide('server_custom', 'show')\"\n\t\t\t\t\t\t ");
      out.print( ("custom".equals(serverSecurityRequired) ? "checked" : "") );
      out.write(">\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t<label for=\"rb11\">\n\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</label>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr valign=\"top\" id=\"server_custom\" ");
 if (!"custom".equals(serverSecurityRequired)) out.write("style=\"display:none\""); 
      out.write(">\n\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n\t\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"dialback\" value=\"notavailable\" id=\"rb12\" ");
      out.print( ("notavailable".equals(dialback) ? "checked" : "") );
      out.write("\n\t\t\t\t\t\t\t\t\t   onclick=\"this.form.serverSecurityRequired[2].checked=true;\">&nbsp;<label for=\"rb12\">");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("</label>&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"dialback\" value=\"available\" id=\"rb13\" ");
      out.print( ("available".equals(dialback) ? "checked" : "") );
      out.write("\n\t\t\t\t\t\t\t\t\t   onclick=\"this.form.serverSecurityRequired[2].checked=true;\">&nbsp;<label for=\"rb13\">");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("</label>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"server_tls\" value=\"notavailable\" id=\"rb14\" ");
      out.print( ("notavailable".equals(server_tls) ? "checked" : "") );
      out.write("\n\t\t\t\t\t\t\t\t\t   onclick=\"this.form.serverSecurityRequired[2].checked=true;\">&nbsp;<label for=\"rb14\">");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("</label>&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"server_tls\" value=\"optional\" id=\"rb15\" ");
      out.print( ("optional".equals(server_tls) ? "checked" : "") );
      out.write("\n\t\t\t\t\t\t\t\t\t   onclick=\"this.form.serverSecurityRequired[2].checked=true;\">&nbsp;<label for=\"rb15\">");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("</label>&nbsp;&nbsp;\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n                <tr valign=\"middle\">\n                    <td width=\"1%\" nowrap>\n                        <input type=\"checkbox\" name=\"selfSigned\" id=\"cb02\" ");
      out.print( (selfSigned ? "checked" : "") );
      out.write(">\n                    </td>\n                    <td width=\"99%\">\n                        <label for=\"rb02\">\n                        ");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write("\n                        </label>\n                    </td>\n                </tr>\n\t\t    </tbody>\n\t\t</table>\n\t</div>\n\n    <input type=\"submit\" name=\"update\" value=\"");
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write("\">\n</form>\n<!-- BEGIN 'Server Connection Security' -->\n\n</body>\n</html>\n\n");
  } catch (Throwable t) { t.printStackTrace(); } 
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
    _jspx_th_fmt_message_0.setKey("ssl.settings.title");
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
    _jspx_th_fmt_message_1.setKey("ssl.settings.update");
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
    _jspx_th_fmt_message_2.setKey("ssl.settings.uninstalled");
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
    _jspx_th_fmt_message_3.setKey("ssl.settings.client.info");
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
    _jspx_th_fmt_message_4.setKey("ssl.settings.client.legend");
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
    _jspx_th_fmt_message_5.setKey("ssl.settings.client.label_notrequired");
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
    _jspx_th_fmt_message_6.setKey("ssl.settings.client.label_notrequired_info");
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
    _jspx_th_fmt_message_7.setKey("ssl.settings.client.label_required");
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
    _jspx_th_fmt_message_8.setKey("ssl.settings.client.label_required_info");
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
    _jspx_th_fmt_message_9.setKey("ssl.settings.client.label_custom");
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
    _jspx_th_fmt_message_10.setKey("ssl.settings.client.label_custom_info");
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
    _jspx_th_fmt_message_11.setKey("ssl.settings.client.customSSL");
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
    _jspx_th_fmt_message_12.setKey("ssl.settings.notavailable");
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
    _jspx_th_fmt_message_13.setKey("ssl.settings.available");
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
    _jspx_th_fmt_message_14.setKey("ssl.settings.client.customTLS");
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
    _jspx_th_fmt_message_15.setKey("ssl.settings.notavailable");
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
    _jspx_th_fmt_message_16.setKey("ssl.settings.optional");
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
    _jspx_th_fmt_message_17.setKey("ssl.settings.required");
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
    _jspx_th_fmt_message_18.setKey("ssl.settings.server.legend");
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
    _jspx_th_fmt_message_19.setKey("ssl.settings.server.label_notrequired");
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
    _jspx_th_fmt_message_20.setKey("ssl.settings.server.label_notrequired_info");
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
    _jspx_th_fmt_message_21.setKey("ssl.settings.server.label_required");
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
    _jspx_th_fmt_message_22.setKey("ssl.settings.server.label_required_info");
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
    _jspx_th_fmt_message_23.setKey("ssl.settings.server.label_custom");
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
    _jspx_th_fmt_message_24.setKey("ssl.settings.server.label_custom_info");
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
    _jspx_th_fmt_message_25.setKey("ssl.settings.server.dialback");
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
    _jspx_th_fmt_message_26.setKey("ssl.settings.notavailable");
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
    _jspx_th_fmt_message_27.setKey("ssl.settings.available");
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
    _jspx_th_fmt_message_28.setKey("ssl.settings.server.customTLS");
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
    _jspx_th_fmt_message_29.setKey("ssl.settings.notavailable");
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
    _jspx_th_fmt_message_30.setKey("ssl.settings.optional");
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
    _jspx_th_fmt_message_31.setKey("ssl.settings.client.label_self-signed");
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
    _jspx_th_fmt_message_32.setKey("global.save_settings");
    int _jspx_eval_fmt_message_32 = _jspx_th_fmt_message_32.doStartTag();
    if (_jspx_th_fmt_message_32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
    return false;
  }
}
