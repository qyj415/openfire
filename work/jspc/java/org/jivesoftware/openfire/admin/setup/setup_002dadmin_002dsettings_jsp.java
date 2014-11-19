package org.jivesoftware.openfire.admin.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.AuthFactory;
import org.jivesoftware.openfire.ldap.LdapManager;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.StringUtils;
import org.xmpp.packet.JID;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.*;

public final class setup_002dadmin_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

 // Global vars, methods, etc
    void setSetupFinished(HttpSession session) {
        JiveGlobals.setXMLProperty("setup","true");
    }

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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

	// Redirect if we've already run setup:
	if (!XMPPServer.getInstance().isSetupMode()) {
        response.sendRedirect("setup-completed.jsp");
        return;
    }

      out.write("\r\n\r\n");
      out.write("\r\n\r\n");

    // Get parameters
    String username = ParamUtils.getParameter(request, "username");
    String password = ParamUtils.getParameter(request, "password");
    String email = ParamUtils.getParameter(request, "email");
    String newPassword = ParamUtils.getParameter(request, "newPassword");
    String newPasswordConfirm = ParamUtils.getParameter(request, "newPasswordConfirm");

    boolean doContinue = request.getParameter("continue") != null;
    boolean doSkip = request.getParameter("doSkip") != null;
    boolean doTest = request.getParameter("test") != null;

    boolean ldap = "true".equals(request.getParameter("ldap"));

    boolean addAdmin = request.getParameter("addAdministrator") != null;
    boolean deleteAdmins = request.getParameter("deleteAdmins") != null;
    boolean ldapFinished = request.getParameter("ldapFinished") != null;

    @SuppressWarnings("unchecked")
    Map<String,String> xmppSettings = (Map<String,String>)session.getAttribute("xmppSettings");
    String domain = xmppSettings.get("xmpp.domain");

    // Handle a skip request
    if (doSkip) {
        // assume the admin account is setup, so we're done:
        setSetupFinished(session);
        // redirect
        response.sendRedirect("setup-finished.jsp");
        return;
    }

    // Error checks
    Map<String, String> errors = new HashMap<String, String>();
    if (doContinue) {
        if (password == null) {
            errors.put("password", "password");
        }
        if (email == null) {
            errors.put("email", "email");
        }
        if (newPassword == null) {
            errors.put("newPassword", "newPassword");
        }
        if (newPasswordConfirm == null) {
            errors.put("newPasswordConfirm", "newPasswordConfirm");
        }
        if (newPassword != null && newPasswordConfirm != null
                && !newPassword.equals(newPasswordConfirm)) {
            errors.put("match", "match");
        }
        // if no errors, continue:
        if (errors.size() == 0) {
            try {
                User adminUser = UserManager.getInstance().getUser("admin");
                adminUser.setPassword(newPassword);
                if (email != null) {
                    adminUser.setEmail(email);
                }
                Date now = new Date();
                adminUser.setCreationDate(now);
                adminUser.setModificationDate(now);

                // setup is finished, indicate so:
                setSetupFinished(session);
                // All good so redirect
                response.sendRedirect("setup-finished.jsp");
                return;
            }
            catch (Exception e) {
                //System.err.println("Could not find UserManager");
                e.printStackTrace();
                errors.put("general", "There was an unexpected error encountered when "
                        + "setting the new admin information. Please check your error "
                        + "logs and try to remedy the problem.");
            }
        }
    }

    if (ldapFinished) {
        setSetupFinished(session);
        // All good so redirect
        response.sendRedirect("setup-finished.jsp");
        return;
    }

    if (addAdmin && !doTest) {
        final String admin = request.getParameter("administrator");
        if (admin != null) {
            if (ldap) {
                // Try to verify that the username exists in LDAP
                Map<String, String> settings = (Map<String, String>) session.getAttribute("ldapSettings");
                Map<String, String> userSettings = (Map<String, String>) session.getAttribute("ldapUserSettings");
                if (settings != null) {
                    LdapManager manager = new LdapManager(settings);
                    manager.setUsernameField(userSettings.get("ldap.usernameField"));
                    manager.setSearchFilter(userSettings.get("ldap.searchFilter"));
                    try {
                        manager.findUserDN(JID.unescapeNode(admin));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        errors.put("administrator", "");
                    }
                }
            }
            if (errors.isEmpty()) {
                String currentList = xmppSettings.get("admin.authorizedJIDs");
                final List users = new ArrayList(StringUtils.stringToCollection(currentList));
                users.add(new JID(admin.toLowerCase(), domain, null).toBareJID());

                String userList = StringUtils.collectionToString(users);
                xmppSettings.put("admin.authorizedJIDs", userList);
            }
        } else {
            errors.put("administrator", "");
        }
    }

    if (deleteAdmins) {
        String[] params = request.getParameterValues("remove");
        String currentAdminList = xmppSettings.get("admin.authorizedJIDs");
        Collection<String> adminCollection = StringUtils.stringToCollection(currentAdminList);
        List temporaryUserList = new ArrayList<String>(adminCollection);
        final int no = params != null ? params.length : 0;
        for (int i = 0; i < no; i++) {
            temporaryUserList.remove(params[i]);
        }

        String newUserList = StringUtils.collectionToString(temporaryUserList);
        if (temporaryUserList.size() == 0) {
            xmppSettings.put("admin.authorizedJIDs", "");
        } else {
            xmppSettings.put("admin.authorizedJIDs", newUserList);
        }
    }

    // This handles the case of reverting back to default settings from LDAP/Clearspace. Will
    // add admin to the authorizedJIDs list if the authorizedJIDs list contains
    // entries.
    if (!ldap && !doTest) {
        String currentAdminList = xmppSettings.get("admin.authorizedJIDs");
        List<String> adminCollection = new ArrayList<String>(StringUtils.stringToCollection(currentAdminList));
        if ((!adminCollection.isEmpty() && !adminCollection.contains("admin")) ||
                xmppSettings.get("admin.authorizedJIDs") != null) {
            adminCollection.add(new JID("admin", domain, null).toBareJID());
            xmppSettings.put("admin.authorizedJIDs",
                    StringUtils.collectionToString(adminCollection));
        }
    }

    // Save the updated settings
    session.setAttribute("xmppSettings", xmppSettings);


      out.write("\r\n<html>\r\n<head>\r\n    <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n    <meta name=\"currentStep\" content=\"4\"/>\r\n</head>\r\n<body>\r\n\r\n\r\n\t<h1>\r\n\t");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\r\n\t</h1>\r\n\r\n");
 if(!ldap){ 
      out.write("\r\n    <p>\r\n\t");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\r\n\t</p>\r\n\r\n");
  if (errors.size() > 0) { 
      out.write("\r\n\r\n    <div class=\"error\">\r\n    ");
  if (errors.get("general") != null) { 
      out.write("\r\n\r\n        ");
      out.print( errors.get("general") );
      out.write("\r\n\r\n    ");
  } else if (errors.get("administrator") != null) { 
      out.write("\r\n\r\n        ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\r\n\r\n    ");
  } else { 
      out.write("\r\n\r\n        ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\r\n\r\n    ");
  } 
      out.write("\r\n    </div>\r\n\r\n");
  } 
      out.write("\r\n\r\n\r\n\t<!-- BEGIN jive-contentBox -->\r\n\t<div class=\"jive-contentBox\">\r\n\r\n\r\n<script language=\"JavaScript\" type=\"text/javascript\">\r\nvar clicked = false;\r\nfunction checkClick() {\r\n    if (!clicked) {\r\n        clicked = true;\r\n        return true;\r\n    }\r\n    return false;\r\n}\r\n</script>\r\n\r\n<form action=\"setup-admin-settings.jsp\" name=\"acctform\" method=\"post\" onsubmit=\"return checkClick();\">\r\n\r\n<table cellpadding=\"3\" cellspacing=\"2\" border=\"0\">\r\n\r\n");

    // If the current password is "admin", don't show the text box for them to type
    // the current password. This makes setup simpler for first-time users.
    String currentPass = null;
    try {
        currentPass = AuthFactory.getPassword("admin");
    }
    catch (Exception e) {
        // Ignore.
    }
    if ("admin".equals(currentPass)) {

      out.write("\r\n<input type=\"hidden\" name=\"password\" value=\"admin\">\r\n");

    }
    else {

      out.write("\r\n\r\n<tr valign=\"top\">\r\n    <td class=\"jive-label\">\r\n        ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n    <td>\r\n        <input type=\"password\" name=\"password\" size=\"20\" maxlength=\"50\"\r\n         value=\"");
      out.print( ((password!=null) ? password : "") );
      out.write("\"><br>\r\n\r\n        ");
  if (errors.get("password") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\r\n            </span>\r\n        ");
  } else { 
      out.write("\r\n            <span class=\"jive-description\">\r\n            ");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("\r\n            </span>\r\n        ");
 } 
      out.write("\r\n    </td>\r\n</tr>\r\n\r\n");
  } 
      out.write("\r\n\r\n");

    // Get the current email address, if there is one.
    String currentEmail = "";
    try {
        User adminUser = UserManager.getInstance().getUser("admin");
        if (adminUser.getEmail() != null) {
            currentEmail = adminUser.getEmail();
        }
    }
    catch (Exception e) {
        // Ignore.
    }

      out.write("\r\n\r\n<tr valign=\"top\">\r\n    <td class=\"jive-label\" align=\"right\">\r\n        ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n    <td>\r\n        <input type=\"text\" name=\"email\" size=\"40\" maxlength=\"150\"\r\n         value=\"");
      out.print( ((email!=null) ? email : currentEmail) );
      out.write("\"><br>\r\n\r\n        ");
  if (errors.get("email") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\r\n            </span>\r\n        ");
  } else { 
      out.write("\r\n            <span class=\"jive-description\">\r\n            ");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\r\n            </span>\r\n        ");
 } 
      out.write("\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td class=\"jive-label\" align=\"right\">\r\n        ");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n    <td>\r\n        <input type=\"password\" name=\"newPassword\" size=\"20\" maxlength=\"50\"\r\n         value=\"");
      out.print( ((newPassword!=null) ? newPassword : "") );
      out.write("\"><br>\r\n\r\n        ");
  if (errors.get("newPassword") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("\r\n            </span>\r\n        ");
  } else if (errors.get("match") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\r\n            </span>\r\n        ");
  } 
      out.write("\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td class=\"jive-label\" align=\"right\">\r\n        ");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n    <td>\r\n        <input type=\"password\" name=\"newPasswordConfirm\" size=\"20\" maxlength=\"50\"\r\n         value=\"");
      out.print( ((newPasswordConfirm!=null) ? newPasswordConfirm : "") );
      out.write("\"><br>\r\n        ");
  if (errors.get("newPasswordConfirm") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("\r\n            </span>\r\n        ");
  } 
      out.write("\r\n    </td>\r\n</tr>\r\n</table>\r\n\r\n<br>\r\n\t\t<div align=\"right\">\r\n\t\t\t<input type=\"submit\" name=\"doSkip\" value=\"");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\" id=\"jive-setup-skip\" border=\"0\">\r\n\t\t\t<input type=\"Submit\" name=\"continue\" value=\"");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("\" id=\"jive-setup-save\" border=\"0\">\r\n\t\t</div>\r\n\r\n\t</form>\r\n\t</div>\r\n\t<!-- END jive-contentBox -->\r\n\r\n\r\n<script language=\"JavaScript\" type=\"text/javascript\">\r\n<!--\r\ndocument.acctform.newPassword.focus();\r\n//-->\r\n</script>\r\n\r\n\r\n\r\n");
 } else if (ldap) {
if (errors.size() > 0) { 
      out.write("\r\n\r\n    <div class=\"error\">\r\n    ");
  if (errors.get("general") != null) { 
      out.write("\r\n\r\n        ");
      out.print( errors.get("general") );
      out.write("\r\n\r\n    ");
  } else if (errors.get("administrator") != null) { 
      out.write("\r\n\r\n        ");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("\r\n\r\n    ");
  } else { 
      out.write("\r\n\r\n        ");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("\r\n\r\n    ");
  } 
      out.write("\r\n    </div>\r\n\r\n");
  }
    if (doTest) {
        StringBuffer testLink = new StringBuffer();
        testLink.append("setup-admin-settings_test.jsp?username=");
        testLink.append(URLEncoder.encode(username, "UTF-8"));
        if (password != null) {
            testLink.append("&password=").append(URLEncoder.encode(password, "UTF-8"));
        }
        testLink.append("&ldap=true");

      out.write("\r\n\r\n    <a href=\"");
      out.print( testLink );
      out.write("\" id=\"lbmessage\" title=\"");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("\" style=\"display:none;\"></a>\r\n    <script type=\"text/javascript\">\r\n        function loadMsg() {\r\n            var lb = new lightbox(document.getElementById('lbmessage'));\r\n            lb.activate();\r\n        }\r\n        setTimeout('loadMsg()', 250);\r\n    </script>\r\n\r\n");
 } 
      out.write("\r\n<p>\r\n ");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("\r\n  </p>\r\n<div class=\"jive-contentBox\">\r\n\r\n<form action=\"setup-admin-settings.jsp\" name=\"acctform\" method=\"post\">\r\n\r\n    <!-- Admin Table -->\r\n\r\n<table cellpadding=\"3\" cellspacing=\"2\" border=\"0\">\r\n    <tr valign=\"top\">\r\n        <td class=\"jive-label\">\r\n            ");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write(":\r\n        </td>\r\n         <td>\r\n        <input type=\"text\" name=\"administrator\" size=\"20\" maxlength=\"50\"/>\r\n        </td>\r\n        <td>\r\n            <input type=\"submit\" name=\"addAdministrator\" value=\"");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("\"/>\r\n        </td>\r\n    </tr>\r\n</table>\r\n");

    String authorizedJIDs = xmppSettings.get("admin.authorizedJIDs");
    boolean hasAuthorizedName = authorizedJIDs != null && authorizedJIDs.length() > 0;

      out.write("\r\n    ");
 if(hasAuthorizedName) { 
      out.write("\r\n    <!-- List of admins -->\r\n    <table class=\"jive-vcardTable\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n        <tr>\r\n            <th nowrap>");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("</th>\r\n            <th width=\"1%\" nowrap>");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("</th>\r\n            <th width=\"1%\" nowrap>");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("</th>\r\n        </tr>\r\n");

    for (String authJIDstr : StringUtils.stringToCollection(authorizedJIDs)) {
        JID authJID = new JID(authJIDstr);

      out.write("\r\n    <tr valign=\"top\">\r\n        <td>\r\n            ");
      out.print( authJID.getNode());
      out.write("\r\n        </td>\r\n        <td width=\"1%\" align=\"center\">\r\n            <a href=\"setup-admin-settings.jsp?ldap=true&test=true&username=");
      out.print( URLEncoder.encode(authJID.getNode(), "UTF-8") );
      out.write("\"\r\n             title=\"");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("\"\r\n             ><img src=\"../images/setup_btn_gearplay.gif\" width=\"14\" height=\"14\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("\"></a>\r\n        </td>\r\n        <td>\r\n            <input type=\"checkbox\" name=\"remove\" value=\"");
      out.print(authJID.toBareJID());
      out.write("\"/>\r\n        </td>\r\n    </tr>\r\n\r\n    ");

        }
        if (authorizedJIDs != null) {
    
      out.write("\r\n         <tr valign=\"top\">\r\n        <td>\r\n           &nbsp;\r\n        </td>\r\n        <td>\r\n           &nbsp;\r\n        </td>\r\n        <td>\r\n            <input type=\"submit\" name=\"deleteAdmins\" value=\"Remove\"/>\r\n        </td>\r\n    </tr>\r\n\r\n        ");

            }

        
      out.write("\r\n</table>\r\n    ");
 } 
      out.write("\r\n\r\n\r\n<input type=\"hidden\" name=\"ldap\" value=\"true\"/>\r\n\r\n     <div align=\"right\">\r\n    <br/>\r\n  <input type=\"submit\" name=\"ldapFinished\" value=\"");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("\"  id=\"jive-setup-save\" border=\"0\" style=\"display:none;\">\r\n          </div>\r\n </form>\r\n\r\n</div>\r\n\r\n");

    if(hasAuthorizedName) {
      out.write("\r\n        <script type=\"text/javascript\">\r\n            document.getElementById(\"jive-setup-save\").style.display = \"\";\r\n        </script>\r\n    ");
 } 
      out.write('\r');
      out.write('\n');
 } 
      out.write("\r\n\r\n</body>\r\n</html>\r\n");
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
    _jspx_th_fmt_message_0.setKey("setup.admin.settings.account");
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
    _jspx_th_fmt_message_1.setKey("setup.admin.settings.account");
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
    _jspx_th_fmt_message_2.setKey("setup.admin.settings.info");
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
    _jspx_th_fmt_message_3.setKey("setup.admin.settings.username-error");
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
    _jspx_th_fmt_message_4.setKey("setup.admin.settings.error");
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
    _jspx_th_fmt_message_5.setKey("setup.admin.settings.current_password");
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
    _jspx_th_fmt_message_6.setKey("setup.admin.settings.current_password_error");
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
    _jspx_th_fmt_message_7.setKey("setup.admin.settings.current_password_description");
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
    _jspx_th_fmt_message_8.setKey("setup.admin.settings.email");
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
    _jspx_th_fmt_message_9.setKey("setup.admin.settings.email_error");
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
    _jspx_th_fmt_message_10.setKey("setup.admin.settings.email_description");
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
    _jspx_th_fmt_message_11.setKey("setup.admin.settings.new_password");
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
    _jspx_th_fmt_message_12.setKey("setup.admin.settings.valid_new_password");
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
    _jspx_th_fmt_message_13.setKey("setup.admin.settings.not_new_password");
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
    _jspx_th_fmt_message_14.setKey("setup.admin.settings.confirm_password");
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
    _jspx_th_fmt_message_15.setKey("setup.admin.settings.valid_confirm");
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
    _jspx_th_fmt_message_16.setKey("setup.admin.settings.skip_this_step");
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
    _jspx_th_fmt_message_17.setKey("global.continue");
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
    _jspx_th_fmt_message_18.setKey("setup.admin.settings.username-error");
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
    _jspx_th_fmt_message_19.setKey("setup.admin.settings.error");
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
    _jspx_th_fmt_message_20.setKey("global.test");
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
    _jspx_th_fmt_message_21.setKey("setup.admin.settings.ldap.info");
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
    _jspx_th_fmt_message_22.setKey("setup.admin.settings.add.administrator");
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
    _jspx_th_fmt_message_23.setKey("global.add");
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
    _jspx_th_fmt_message_24.setKey("setup.admin.settings.administrator");
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
    _jspx_th_fmt_message_25.setKey("global.test");
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
    _jspx_th_fmt_message_26.setKey("setup.admin.settings.remove");
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
    _jspx_th_fmt_message_27.setKey("global.click_test");
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
    _jspx_th_fmt_message_28.setKey("global.click_test");
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
    _jspx_th_fmt_message_29.setKey("global.continue");
    int _jspx_eval_fmt_message_29 = _jspx_th_fmt_message_29.doStartTag();
    if (_jspx_th_fmt_message_29.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_29);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_29);
    return false;
  }
}
