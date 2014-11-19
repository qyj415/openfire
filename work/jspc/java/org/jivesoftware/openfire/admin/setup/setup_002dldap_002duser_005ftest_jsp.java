package org.jivesoftware.openfire.admin.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.admin.LdapUserProfile;
import org.jivesoftware.admin.LdapUserTester;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.ldap.LdapManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jivesoftware.util.StringUtils;

public final class setup_002dldap_002duser_005ftest_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

    String errorDetail = null;
    Map<String, String> attributes = null;

    Map<String, String> settings = (Map<String, String>) session.getAttribute("ldapSettings");
    Map<String, String> userSettings = (Map<String, String>) session.getAttribute("ldapUserSettings");
    LdapUserProfile vCardSettings = (LdapUserProfile) session.getAttribute("ldapVCardBean");
    int userIndex = ParamUtils.getIntParameter(request, "userIndex", -1);
    if (settings != null && userSettings != null && vCardSettings != null) {
        LdapManager manager = new LdapManager(settings);
        manager.setUsernameField(userSettings.get("ldap.usernameField"));
        manager.setSearchFilter(userSettings.get("ldap.searchFilter"));

        // Build the tester with the recreated LdapManager and vcard mapping information
        LdapUserTester tester = new LdapUserTester(manager, vCardSettings);
        List<String> usernames = new ArrayList<String>();
        try {
            usernames = tester.getSample(40);
        }
        catch (Exception e) {
            // Inform user that an error occurred while trying to get users data
            errorDetail = LocaleUtils.getLocalizedString("setup.ldap.test.error-loading-sample");
            Log.error("Error occurred while trying to get users data from LDAP", e);
        }
        if (usernames.isEmpty()) {
            // Inform user that no users were found
            errorDetail = LocaleUtils.getLocalizedString("setup.ldap.user.test.users-not-found");
        } else {
            // Pick a user from the sample list of users
            userIndex = userIndex + 1;
            if (usernames.size() <= userIndex) {
                userIndex = 0;
            }
            // Get attributes for selected user
            attributes = tester.getAttributes(usernames.get(userIndex));
        }
    }
    else {
        // Information was not found in the HTTP Session. Internal error?
        errorDetail = LocaleUtils.getLocalizedString("setup.ldap.user.test.internal-server-error");
    }

      out.write("\r\n<html>\r\n<head>\r\n<meta name=\"decorator\" content=\"none\"/>\r\n</head>\r\n<body>\r\n<script type=\"text/javascript\" language=\"javascript\" src=\"../js/tooltips/domTT.js\"></script>\r\n<script type=\"text/javascript\" language=\"javascript\" src=\"../js/tooltips/domLib.js\"></script>\r\n<style type=\"text/css\">\r\n#lightbox{\r\n\ttop: 20%;\r\n\tmargin-top: -20px;\r\n\t}\r\n\r\n.jive-testPanel {\r\n\tmargin-top: -100px;\r\n\t}\r\nhtml>body .jive-testPanel {\r\n\tmargin-top: 0px;\r\n\t}\r\n</style>\r\n\r\n<!-- BEGIN connection settings test panel -->\r\n<div class=\"jive-testPanel\">\r\n\t<div class=\"jive-testPanel-content\">\r\n\r\n\t\t<div align=\"right\" class=\"jive-testPanel-close\">\r\n\t\t\t<a href=\"#\" class=\"lbAction\" rel=\"deactivate\">");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</a>\r\n\t\t</div>\r\n\r\n\r\n\t\t<h2>");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write(": <span>");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</span></h2>\r\n\r\n\t\t<!--<h4 class=\"jive-testSuccess\">Success!</h4>-->\r\n\t\t<!-- <h4 class=\"jive-testError\">Error</h4> -->\r\n\r\n\t\t<p>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</p>\r\n\r\n\t\t<div class=\"jive-testpanel-vcard\">\r\n            ");
 if (attributes != null) { 
      out.write("\r\n            <table width=\"331\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"jive-testTable-vcard\" style=\"margin-right: 5px;\">\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td colspan=\"2\" class=\"jive-testpanel-vcard-header\">");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 String value = attributes.get(LdapUserTester.NAME);
                       boolean failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\" width=\"20%\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.EMAIL);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.NICKNAME);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BIRTHDAY);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.PHOTO);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : StringUtils.chopAtWord(value , 17) + "...");
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td colspan=\"2\"></td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td colspan=\"2\" class=\"jive-testpanel-vcard-header\">");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.HOME_STREET);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.HOME_CITY);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.HOME_STATE);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.HOME_ZIP);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.HOME_COUNTRY);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.HOME_PHONE);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.HOME_MOBILE);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.HOME_FAX);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.HOME_PAGER);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t</table>\r\n\r\n\t\t\t<table width=\"331\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"jive-testTable-vcard\">\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td colspan=\"2\" class=\"jive-testpanel-vcard-header\">");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_STREET);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\" width=\"20%\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_CITY);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_STATE);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_ZIP);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_COUNTRY);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_JOB_TITLE);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_DEPARTMENT);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_PHONE);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_MOBILE);
                        failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_FAX);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n                    ");
 value = attributes.get(LdapUserTester.BUSINESS_PAGER);
                       failed = value != null && value.contains("{");
                    
      out.write("\r\n\t\t\t\t\t<td class=\"jive-testpanel-vcard-label\">");
      out.print( value != null ? "<strong>" : "");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write(':');
      out.print( value != null ? "</strong>" : "");
      out.write("</td>\r\n                    <td class=\"jive-testpanel-vcard-value\">");
      out.print( failed || value == null? "" : value);
      out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td colspan=\"2\" class=\"jive-testpanel-vcard-next\">\r\n\t\t\t\t\t\t<a href=\"");
      out.print( ParamUtils.getParameter(request, "currentPage"));
      out.write("?test=true&serverType=");
      out.print( ParamUtils.getParameter(request, "serverType"));
      out.write("&userIndex=");
      out.print(userIndex);
      out.write('"');
      out.write('>');
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write("</a>\r\n\t\t\t\t\t</td>\r\n\t\t\t\t</tr>\r\n\t\t\t</table>\r\n            ");
 } else { 
      out.write("\r\n            <h4 class=\"jive-testError\">");
      if (_jspx_meth_fmt_message_33(_jspx_page_context))
        return;
      out.write("</h4>\r\n            <p>");
      out.print( errorDetail );
      out.write("</p>\r\n            ");
 } 
      out.write("\r\n\t\t</div>\r\n\r\n\t</div>\r\n</div>\r\n\r\n\r\n</body>\r\n</html>\r\n");
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
    _jspx_th_fmt_message_0.setKey("setup.ldap.server.test.close");
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
    _jspx_th_fmt_message_1.setKey("setup.ldap.server.test.title");
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
    _jspx_th_fmt_message_2.setKey("setup.ldap.user_mapping");
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
    _jspx_th_fmt_message_3.setKey("setup.ldap.user.vcard.test.description");
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
    _jspx_th_fmt_message_4.setKey("setup.ldap.user.vcard.personal");
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
    _jspx_th_fmt_message_5.setKey("setup.ldap.user.vcard.name");
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
    _jspx_th_fmt_message_6.setKey("setup.ldap.user.vcard.email");
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
    _jspx_th_fmt_message_7.setKey("setup.ldap.user.vcard.nickname");
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
    _jspx_th_fmt_message_8.setKey("setup.ldap.user.vcard.birthday");
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
    _jspx_th_fmt_message_9.setKey("setup.ldap.user.vcard.photo");
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
    _jspx_th_fmt_message_10.setKey("setup.ldap.user.vcard.home");
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
    _jspx_th_fmt_message_11.setKey("setup.ldap.user.vcard.street");
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
    _jspx_th_fmt_message_12.setKey("setup.ldap.user.vcard.city");
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
    _jspx_th_fmt_message_13.setKey("setup.ldap.user.vcard.state");
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
    _jspx_th_fmt_message_14.setKey("setup.ldap.user.vcard.pcode");
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
    _jspx_th_fmt_message_15.setKey("setup.ldap.user.vcard.country");
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
    _jspx_th_fmt_message_16.setKey("setup.ldap.user.vcard.phone");
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
    _jspx_th_fmt_message_17.setKey("setup.ldap.user.vcard.mobile");
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
    _jspx_th_fmt_message_18.setKey("setup.ldap.user.vcard.fax");
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
    _jspx_th_fmt_message_19.setKey("setup.ldap.user.vcard.pager");
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
    _jspx_th_fmt_message_20.setKey("setup.ldap.user.vcard.business");
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
    _jspx_th_fmt_message_21.setKey("setup.ldap.user.vcard.street");
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
    _jspx_th_fmt_message_22.setKey("setup.ldap.user.vcard.city");
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
    _jspx_th_fmt_message_23.setKey("setup.ldap.user.vcard.state");
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
    _jspx_th_fmt_message_24.setKey("setup.ldap.user.vcard.pcode");
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
    _jspx_th_fmt_message_25.setKey("setup.ldap.user.vcard.country");
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
    _jspx_th_fmt_message_26.setKey("setup.ldap.user.vcard.title");
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
    _jspx_th_fmt_message_27.setKey("setup.ldap.user.vcard.department");
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
    _jspx_th_fmt_message_28.setKey("setup.ldap.user.vcard.phone");
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
    _jspx_th_fmt_message_29.setKey("setup.ldap.user.vcard.mobile");
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
    _jspx_th_fmt_message_30.setKey("setup.ldap.user.vcard.fax");
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
    _jspx_th_fmt_message_31.setKey("setup.ldap.user.vcard.pager");
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
    _jspx_th_fmt_message_32.setKey("setup.ldap.user.vcard.test.random");
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
    _jspx_th_fmt_message_33.setKey("setup.ldap.server.test.status-error");
    int _jspx_eval_fmt_message_33 = _jspx_th_fmt_message_33.doStartTag();
    if (_jspx_th_fmt_message_33.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_33);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_33);
    return false;
  }
}
