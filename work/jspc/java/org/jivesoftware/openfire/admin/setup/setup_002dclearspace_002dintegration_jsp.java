package org.jivesoftware.openfire.admin.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.ParamUtils;
import java.util.*;
import org.jivesoftware.openfire.clearspace.ClearspaceManager;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.StringUtils;

public final class setup_002dclearspace_002dintegration_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/setup/clearspace-integration.jspf");
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

    boolean initialSetup = true;
    boolean forceTest = false;
    String currentPage = "setup-clearspace-integration.jsp";
    String testPage = "setup-clearspace-integration_test.jsp";
    String nextPage = "setup-finished.jsp";
    Map<String, String> meta = new HashMap<String, String>();
    meta.put("currentStep", "3");

      out.write('\n');
      out.write("\n\n\n\n\n\n\n\n\n");

    String uri;
    String sharedSecret;
    String existingHashSharedSecret = "";
    String plainSharedSecret = null;

    boolean verifyChain = JiveGlobals.getBooleanProperty("clearspace.certificate.verify.chain", true);
    boolean verifyRoot = JiveGlobals.getBooleanProperty("clearspace.certificate.verify.root", true);
    boolean selfSigned = JiveGlobals.getBooleanProperty("clearspace.certificate.accept-selfsigned", false);
    boolean verifyIdentity = JiveGlobals.getBooleanProperty("clearspace.certificate.verify.identity", false);
    boolean verifyValidity = JiveGlobals.getBooleanProperty("clearspace.certificate.verify.validity", true);


    // Get parameters
    boolean save = request.getParameter("save") != null;
    boolean test = request.getParameter("test") != null;
    @SuppressWarnings("unchecked")
    Map<String,String> xmppSettings = (Map<String,String>)session.getAttribute("xmppSettings");

    ClearspaceManager manager;
    if (ClearspaceManager.getInstance() != null) {
        // Use the existing manager. This will be the case after setup was completed
        manager = ClearspaceManager.getInstance();
    }
    else {
        manager = new ClearspaceManager();
    }
    Map<String, String> errors = new HashMap<String, String>();

    // If we came from a prelogin error, show it and remove it from the session
    String preloginError = (String) session.getAttribute("prelogin.setup.error");
    if (preloginError != null) {
        errors.put("prelogin", LocaleUtils.getLocalizedString(preloginError));
        session.removeAttribute("prelogin.setup.error");
    }

    if (save || test) {
        uri = ParamUtils.getParameter(request, "uri");
        if (uri == null) {
            errors.put("uri", LocaleUtils.getLocalizedString("setup.clearspace.service.uri_error"));
        }
        // this could be the new entered plain shared secret or the current hashed shared secret
        sharedSecret = ParamUtils.getParameter(request, "sharedSecret");

        // this will store new entered plain shared secret or the current plain shared secret
        //String plainSharedSecret = null;
        if (sharedSecret == null || sharedSecret.length() == 0) {
            errors.put("secret", LocaleUtils.getLocalizedString("setup.clearspace.service.secret_error"));
        } else {
            // set to the current plain shared secret
            plainSharedSecret = manager.getSharedSecret();

            // Hash the current shared secret
            if (plainSharedSecret != null) {
                existingHashSharedSecret = StringUtils.hash(plainSharedSecret);
            }
            // Check if the new shared secret was changed. If it wasn't changed, then it is the original hashed shared secret
            // NOTE: if the new PLAIN password equals the previous HASHED password this fails, but is unlikely.
            if (!existingHashSharedSecret.equals(sharedSecret)) {
                // Hash the new shared secret since it was changed
                String newHashSharedSecret = "";
                if (sharedSecret != null) {
                        newHashSharedSecret = StringUtils.hash(sharedSecret);
                }

                // Change password if their hash values are different
                if (!existingHashSharedSecret.equals(newHashSharedSecret)) {
                    plainSharedSecret = sharedSecret;
                }
            }
        }

        verifyChain = ParamUtils.getBooleanParameter(request, "verifyChain", verifyChain);
        verifyRoot = ParamUtils.getBooleanParameter(request, "verifyRoot", verifyRoot);
        selfSigned = ParamUtils.getBooleanParameter(request, "selfSigned", selfSigned);
        verifyIdentity = ParamUtils.getBooleanParameter(request, "verifyIdentity", verifyIdentity);
        verifyValidity = ParamUtils.getBooleanParameter(request, "verifyValidity", verifyValidity);

        Map<String, String> settings = new HashMap<String, String>();
        // If there are no errors check if there is a need to run a force test
        if (errors.isEmpty()) {
            // Store settings in a map and keep it in the session
            settings.put("clearspace.uri", uri);

            // Sets the current shared secret, it will be changed if the new shared secret is different
            settings.put("clearspace.sharedSecret", plainSharedSecret);

            settings.put("clearspace.certificate.verify.chain", Boolean.toString(verifyChain));
            settings.put("clearspace.certificate.verify.root", Boolean.toString(verifyRoot));
            settings.put("clearspace.certificate.accept-selfsigned", Boolean.toString(selfSigned));
            settings.put("clearspace.certificate.verify.identity", Boolean.toString(verifyIdentity));
            settings.put("clearspace.certificate.verify.validity", Boolean.toString(verifyValidity));

            if (save && forceTest) {
                ClearspaceManager managerTest = new ClearspaceManager(settings);
                if (managerTest.testConnection() != null) {
                    // if there is any problems don't save it
                    // add an error (this is the reason of why this check is in another "if errors.isEmpty()"
                    errors.put("connection", LocaleUtils.getLocalizedString("setup.clearspace.service.connection_error"));
                }
            }
        }

        if (errors.isEmpty()) {
            session.setAttribute("clearspaceSettings", settings);

            if (save) {

                // Save settings and redirect
                manager.setConnectionURI(uri);
                manager.setSharedSecret(plainSharedSecret);

                // Enable the Clearspace auth, user, group, vcard, lockout, security audit, and admin providers.
                JiveGlobals.setProperty("provider.auth.className",
                        "org.jivesoftware.openfire.clearspace.ClearspaceAuthProvider");
                JiveGlobals.setProperty("provider.user.className",
                        "org.jivesoftware.openfire.clearspace.ClearspaceUserProvider");
                JiveGlobals.setProperty("provider.group.className",
                        "org.jivesoftware.openfire.clearspace.ClearspaceGroupProvider");
                JiveGlobals.setProperty("provider.vcard.className",
                        "org.jivesoftware.openfire.clearspace.ClearspaceVCardProvider");
                JiveGlobals.setProperty("provider.lockout.className",
                        "org.jivesoftware.openfire.clearspace.ClearspaceLockOutProvider");
                JiveGlobals.setProperty("provider.securityAudit.className",
                        "org.jivesoftware.openfire.clearspace.ClearspaceSecurityAuditProvider");
                JiveGlobals.setProperty("provider.admin.className",
                        "org.jivesoftware.openfire.clearspace.ClearspaceAdminProvider");

                // Set configuration for certificate validation
                JiveGlobals.setProperty("clearspace.certificate.verify.chain", Boolean.toString(verifyChain));
                JiveGlobals.setProperty("clearspace.certificate.verify.root", Boolean.toString(verifyRoot));
                JiveGlobals.setProperty("clearspace.certificate.accept-selfsigned", Boolean.toString(selfSigned));
                JiveGlobals.setProperty("clearspace.certificate.verify.identity", Boolean.toString(verifyIdentity));
                JiveGlobals.setProperty("clearspace.certificate.verify.validity", Boolean.toString(verifyValidity));

                // Save the settings for later, if we're in setup
                if (xmppSettings != null) {
                    xmppSettings.put("provider.auth.className",
                            "org.jivesoftware.openfire.clearspace.ClearspaceAuthProvider");
                    xmppSettings.put("provider.user.className",
                            "org.jivesoftware.openfire.clearspace.ClearspaceUserProvider");
                    xmppSettings.put("provider.group.className",
                            "org.jivesoftware.openfire.clearspace.ClearspaceGroupProvider");
                    xmppSettings.put("provider.vcard.className",
                            "org.jivesoftware.openfire.clearspace.ClearspaceVCardProvider");
                    xmppSettings.put("provider.lockout.className",
                            "org.jivesoftware.openfire.clearspace.ClearspaceLockOutProvider");
                    xmppSettings.put("provider.securityAudit.className",
                            "org.jivesoftware.openfire.clearspace.ClearspaceSecurityAuditProvider");
                    xmppSettings.put("provider.admin.className",
                            "org.jivesoftware.openfire.clearspace.ClearspaceAdminProvider");
                    xmppSettings.put("clearspace.uri", uri);
                    xmppSettings.put("clearspace.sharedSecret", plainSharedSecret);
                    xmppSettings.put("clearspace.certificate.verify.chain", Boolean.toString(verifyChain));
                    xmppSettings.put("clearspace.certificate.verify.root", Boolean.toString(verifyRoot));
                    xmppSettings.put("clearspace.certificate.accept-selfsigned", Boolean.toString(selfSigned));
                    xmppSettings.put("clearspace.certificate.verify.identity", Boolean.toString(verifyIdentity));
                    xmppSettings.put("clearspace.certificate.verify.validity", Boolean.toString(verifyValidity));

                    JiveGlobals.setPropertyEncrypted("clearspace.uri", true);
                    JiveGlobals.setPropertyEncrypted("clearspace.sharedSecret", true);

                    session.setAttribute("xmppSettings", xmppSettings);
                }
                if (initialSetup) {
                    // Set that the setup has been completed
                    JiveGlobals.setXMLProperty("setup","true");
                }
                // Redirect to next step.
                response.sendRedirect(nextPage);
                return;
            }
        }
    } else {
        // See if there are already values for the variables defined.
        uri = manager.getConnectionURI();
        sharedSecret = manager.getSharedSecret() == null || manager.getSharedSecret().trim().length() == 0 ? "" : StringUtils.hash(manager.getSharedSecret());
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
      out.write("\n</head>\n<body>\n    ");
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
      out.write("\n\n    <div id=\"jive-contentBox_stepbar\">\n        <span class=\"jive-stepbar_step\"><strong>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</strong></span>\n    </div>\n\n    <div class=\"jive-contentBox jive-contentBox_for-stepbar\">\n    <h2><span>");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</span></h2>\n\n    <p>");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
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
      out.write("\" method=\"post\">\n\t\t<!-- BEGIN jive-contentBox_bluebox -->\n\t\t<div class=\"jive-contentBox_bluebox\">\n\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" width=\"100%\">\n\t\t\t<tr>\n\t\t\t    <td colspan=\"4\"><strong>");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</strong></td>\n\t\t\t</tr>\n            <tr>\n\t\t\t    <td align=\"right\" width=\"1%\" nowrap=\"nowrap\">");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write(":</td>\n                <td width=\"1%\">\n                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n                    <tr>\n                        <td width=\"1%\" nowrap=\"nowrap\">\n                            <input type=\"text\" name=\"uri\" id=\"jiveCLEARSPACEuri\" size=\"40\" maxlength=\"255\" value=\"");
      out.print( uri!=null?uri:"" );
      out.write("\">\n                        </td>\n                        <td width=\"99%\">\n                            <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span>\n                        </td>\n                    </tr>\n                    </table>\n                </td>\n\t\t\t</tr>\n\t\t\t<tr>\n                <td align=\"right\" width=\"1%\" nowrap=\"nowrap\">");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write(' ');
      out.print(plainSharedSecret);
      out.write(":</td>\n                <td colspan=\"3\">\n                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n                    <tr>\n                        <td width=\"1%\" nowrap=\"nowrap\">\n                            <input type=\"password\" name=\"sharedSecret\" id=\"jiveCLEARSPACEsecret\" size=\"22\" maxlength=\"35\" value=\"");
      out.print( (sharedSecret != null) ? sharedSecret : "" );
      out.write("\">\n                        </td>\n                        <td width=\"99%\">\n                            <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', -1);\"></span>\n                        </td>\n                    </tr>\n                    </table>\n                </td>\n\t\t\t</tr>\n\t\t\t</table>\n        </div>\n\t\t<!-- END jive-contentBox_bluebox -->\n\n        <!-- BEGIN jiveAdvancedButton -->\n        <div class=\"jiveAdvancedButton\">\n            <a href=\"#\" onclick=\"togglePanel(jiveAdvanced); return false;\" id=\"jiveAdvancedLink\">");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</a>\n        </div>\n        <!-- END jiveAdvancedButton -->\n\n        <!-- BEGIN jiveAdvancedPanelcs (advanced connection settings) -->\n        <div class=\"jiveadvancedPanelcs\" id=\"jiveAdvanced\" style=\"display: none;\">\n            <div>\n                <table border=\"0\" cellpadding=\"0\" cellspacing=\"1\">\n                <thead>\n                <tr>\n                    <th width=\"10%\"></th>\n                    <th></th>\n                    <th width=\"50\">");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("</th>\n                    <th width=\"50\">");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</th>\n                </tr>\n                </thead>\n                <tbody>\n                <tr>\n                    <td class=\"jive-advancedLabel\" nowrap>\n                        ");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write(":\n                    </td>\n                    <td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n                        ");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\n                    </td>\n                    <td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n                        <input type=\"radio\" name=\"verifyChain\" value=\"true\" ");
 if (verifyChain) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                    <td class=\"jive-advancedBorderBottom\" align=\"center\">\n                        <input type=\"radio\" name=\"verifyChain\" value=\"false\" ");
 if (!verifyChain) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                </tr>\n                <tr>\n                    <td class=\"jive-advancedLabel\" nowrap>\n                        ");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write(":\n                    </td>\n                    <td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n                        ");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("\n                    </td>\n                    <td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n                        <input type=\"radio\" name=\"verifyRoot\" value=\"true\" ");
 if (verifyRoot) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                    <td class=\"jive-advancedBorderBottom\" align=\"center\">\n                        <input type=\"radio\" name=\"verifyRoot\" value=\"false\" ");
 if (!verifyRoot) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                </tr>\n                <tr>\n                    <td class=\"jive-advancedLabel\" nowrap>\n                        ");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write(":\n                    </td>\n                    <td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n                        ");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("\n                    </td>\n                    <td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n                        <input type=\"radio\" name=\"selfSigned\" value=\"true\" ");
 if (selfSigned) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                    <td class=\"jive-advancedBorderBottom\" align=\"center\">\n                        <input type=\"radio\" name=\"selfSigned\" value=\"false\" ");
 if (!selfSigned) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                </tr>\n                <tr>\n                    <td class=\"jive-advancedLabel\" nowrap>\n                        ");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write(":\n                    </td>\n                    <td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n                        ");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("\n                    </td>\n                    <td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n                        <input type=\"radio\" name=\"verifyIdentity\" value=\"true\" ");
 if (verifyIdentity) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                    <td class=\"jive-advancedBorderBottom\" align=\"center\">\n                        <input type=\"radio\" name=\"verifyIdentity\" value=\"false\" ");
 if (!verifyIdentity) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                </tr>\n                <tr>\n                    <td class=\"jive-advancedLabel\" nowrap>\n                        ");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write(":\n                    </td>\n                    <td class=\"jive-advancedDesc jive-advancedBorderBottom jive-advancedBorderRight\">\n                        ");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("\n                    </td>\n                    <td class=\"jive-advancedBorderBottom jive-advancedBorderRight\" align=\"center\">\n                        <input type=\"radio\" name=\"verifyValidity\" value=\"true\" ");
 if (verifyValidity) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                    <td class=\"jive-advancedBorderBottom\" align=\"center\">\n                        <input type=\"radio\" name=\"verifyValidity\" value=\"false\" ");
 if (!verifyValidity) { 
      out.write("checked ");
 } 
      out.write(">\n                    </td>\n                </tr>\n                </tbody>\n                </table>\n            </div>\n        </div>\n        <!-- END jiveAdvancedPanelcs (advanced connection settings) -->\n\n        <!-- BEGIN jive-buttons -->\n\t\t<div class=\"jive-buttons\">\n\n\t\t\t<!-- BEGIN right-aligned buttons -->\n\t\t\t<div align=\"right\">\n\n                <input type=\"Submit\" name=\"test\" value=\"");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("\" id=\"jive-setup-test\" border=\"0\">\n\n                <input type=\"Submit\" name=\"save\" value=\"");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("\" id=\"jive-setup-save\" border=\"0\">\n\t\t\t</div>\n\t\t\t<!-- END right-aligned buttons -->\n\n\t\t</div>\n\t\t<!-- END jive-buttons -->\n\n\t</form>\n    </div>\n    <!-- END jive-contentBox -->\n\n</body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("setup.clearspace.title");
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
    _jspx_th_fmt_message_2.setKey("setup.clearspace.profile");
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
    _jspx_th_fmt_message_3.setKey("setup.clearspace.service.integration");
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
    _jspx_th_fmt_message_4.setKey("setup.clearspace.service.connection_settings");
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
    _jspx_th_fmt_message_5.setKey("setup.clearspace.service.connection_settings");
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
    _jspx_th_fmt_message_6.setKey("setup.clearspace.service.description");
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
    _jspx_th_fmt_message_7.setKey("setup.clearspace.service");
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
    _jspx_th_fmt_message_8.setKey("setup.clearspace.service.uri");
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
    _jspx_th_fmt_message_9.setKey("setup.clearspace.service.uri_help");
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
    _jspx_th_fmt_message_10.setKey("setup.clearspace.service.secret");
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
    _jspx_th_fmt_message_11.setKey("setup.clearspace.service.secret_help");
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
    _jspx_th_fmt_message_12.setKey("setup.clearspace.service.advanced");
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
    _jspx_th_fmt_message_13.setKey("global.yes");
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
    _jspx_th_fmt_message_14.setKey("global.no");
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
    _jspx_th_fmt_message_15.setKey("setup.clearspace.service.certificate.verify.chain");
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
    _jspx_th_fmt_message_16.setKey("setup.clearspace.service.certificate.verify.chain_help");
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
    _jspx_th_fmt_message_17.setKey("setup.clearspace.service.certificate.verify.root");
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
    _jspx_th_fmt_message_18.setKey("setup.clearspace.service.certificate.verify.root_help");
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
    _jspx_th_fmt_message_19.setKey("setup.clearspace.service.certificate.accept-selfsigned");
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
    _jspx_th_fmt_message_20.setKey("setup.clearspace.service.certificate.accept-selfsigned_help");
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
    _jspx_th_fmt_message_21.setKey("setup.clearspace.service.certificate.verify.identity");
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
    _jspx_th_fmt_message_22.setKey("setup.clearspace.service.certificate.verify.identity_help");
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
    _jspx_th_fmt_message_23.setKey("setup.clearspace.service.certificate.verify.validity");
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
    _jspx_th_fmt_message_24.setKey("setup.clearspace.service.certificate.verify.validity_help");
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
    _jspx_th_fmt_message_25.setKey("setup.clearspace.test");
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
    _jspx_th_fmt_message_26.setKey("setup.clearspace.continue");
    int _jspx_eval_fmt_message_26 = _jspx_th_fmt_message_26.doStartTag();
    if (_jspx_th_fmt_message_26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
    return false;
  }
}
