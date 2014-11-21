package org.jivesoftware.openfire.plugin.contentFilter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.*;
import org.jivesoftware.openfire.plugin.ContentFilterPlugin;
import org.jivesoftware.util.*;
import java.util.regex.Pattern;

public final class contentfilter_002dprops_002dedit_002dform_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

    boolean save = request.getParameter("save") != null;
    boolean reset = request.getParameter("reset") !=null;
    boolean success = request.getParameter("success") != null;
    
    //filter options
    boolean patternsEnabled = ParamUtils.getBooleanParameter(request, "patternsenabled");
    String patterns =  ParamUtils.getParameter(request, "patterns");
    String [] filterStatusChecked = ParamUtils.getParameters(request, "filterstatus");
    boolean filterStatusEnabled = filterStatusChecked.length > 0;
 
    //match options
    boolean allowOnMatch = ParamUtils.getBooleanParameter(request, "allowonmatch");
    String [] maskChecked = ParamUtils.getParameters(request, "maskenabled");
	boolean maskEnabled = maskChecked.length > 0;
   	String mask =  ParamUtils.getParameter(request, "mask");

    //rejection options
    boolean rejectionNotificationEnabled = ParamUtils.getBooleanParameter(request, "rejectionnotificationenabled");
    String rejectionMsg = ParamUtils.getParameter(request, "rejectionMsg");
  
    //notification options  
    boolean notificationEnabled = ParamUtils.getBooleanParameter(request, "notificationenabled");
    String contactName = ParamUtils.getParameter(request, "contactname");
    List<String> notificationOptions = Arrays.asList(ParamUtils.getParameters(request, "notificationcb"));
    boolean notificationByIMEnabled = notificationOptions.contains("notificationbyim");
    boolean notificationByEmailEnabled = notificationOptions.contains("notificationbyemail");
    boolean includeOriginalEnabled = notificationOptions.contains("notificationincludeoriginal");
    
    //get handle to plugin
	ContentFilterPlugin plugin = (ContentFilterPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("contentfilter");

    //input validation
    Map<String, String> errors = new HashMap<String, String>();
    if (save) {
    
        if (patterns == null) {
            errors.put("missingPatterns", "missingPatterns");
        } else {
            String[] data = patterns.split(",");
            try {
                for (String aData : data) {
                    Pattern.compile(aData);
                }
            } catch (java.util.regex.PatternSyntaxException e) {
    			    errors.put("patternSyntaxException", e.getMessage());
    			}
    		}
    		    	
	    	if (mask == null) {
	    	    errors.put("missingMask", "missingMask");
	    	}
    	
	    	if (rejectionMsg == null) {
	    	    errors.put("missingRejectionMsg", "missingRejectionMsg");
	    	}
    	
	    	if (contactName == null) {
		    errors.put("missingContactName", "missingContactName");
		} else {
		    contactName = contactName.trim().toLowerCase();
		    try  {
		        User user = UserManager.getInstance().getUser(contactName);
		        if (notificationByEmailEnabled) {
		            //verify that the user has an email address
		            if (user.getEmail() == null) {
		                errors.put("userEmailNotConfigured", "userEmailNotConfigured");
		            }
		            //verify that the email server is configured
		            if (!JiveGlobals.getBooleanProperty("mail.configured", false)) {
		                errors.put("mailServerNotConfigured", "mailServerNotConfigured");
		            }
		        }
			} catch (UserNotFoundException unfe) {
			    errors.put("userNotFound", "userNotFound");
			}
		}
		
		if (!notificationByIMEnabled && !notificationByEmailEnabled) {
		    errors.put("notificationFormatNotConfigured", "notificationFormatNotConfigured");
		}
	    	       	    	    
	    if (errors.size() == 0) {
		    plugin.setPatternsEnabled(patternsEnabled);
		    plugin.setPatterns(patterns);
		    plugin.setFilterStatusEnabled(filterStatusEnabled);
		    plugin.setAllowOnMatch(allowOnMatch);
		    plugin.setMaskEnabled(maskEnabled);
		    plugin.setMask(mask);
	        plugin.setViolationNotificationEnabled(notificationEnabled);
	        plugin.setViolationContact(contactName);
	        plugin.setViolationNotificationByIMEnabled(notificationByIMEnabled);
	        plugin.setViolationNotificationByEmailEnabled(notificationByEmailEnabled);
	        plugin.setViolationIncludeOriginalPacketEnabled(includeOriginalEnabled);           
	        plugin.setRejectionNotificationEnabled(rejectionNotificationEnabled);
	        plugin.setRejectionMessage(rejectionMsg);            
	        response.sendRedirect("contentfilter-props-edit-form.jsp?success=true");
	        return;
	    }
    } else if (reset) {
      plugin.reset();
      response.sendRedirect("contentfilter-props-edit-form.jsp?success=true");
    } else {
        patterns = plugin.getPatterns();
        mask = plugin.getMask();   
        contactName = plugin.getViolationContact();
        rejectionMsg = plugin.getRejectionMessage();
    }

    if (errors.size() == 0) {
        patterns = plugin.getPatterns();
        mask = plugin.getMask();   
        contactName = plugin.getViolationContact();
        rejectionMsg = plugin.getRejectionMessage();
        notificationByIMEnabled = plugin.isViolationNotificationByIMEnabled();
        notificationByEmailEnabled = plugin.isViolationNotificationByEmailEnabled();
        includeOriginalEnabled = plugin.isViolationIncludeOriginalPacketEnabled();
    }
    
    patternsEnabled = plugin.isPatternsEnabled();
    filterStatusEnabled = plugin.isFilterStatusEnabled();
    allowOnMatch = plugin.isAllowOnMatch();
    maskEnabled = plugin.isMaskEnabled();
    notificationEnabled = plugin.isViolationNotificationEnabled();
    rejectionNotificationEnabled = plugin.isRejectionNotificationEnabled();


      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>Content Filter</title>\r\n        <meta name=\"pageID\" content=\"contentfilter-props-edit-form\"/>\r\n    </head>\r\n    <body>\r\n\r\n<p>\r\nUse the form below to edit content filter settings.<br>\r\n</p>\r\n\r\n");
  if (success) { 
      out.write("\r\n\r\n    <div class=\"jive-success\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n\t        <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n\t        <td class=\"jive-icon-label\">Settings updated successfully.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div><br>\r\n\r\n");
  } else if (errors.size() > 0) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        \t<td class=\"jive-icon-label\">Error saving the settings.</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div><br>\r\n\r\n");
  } 
      out.write("\r\n\r\n<form action=\"contentfilter-props-edit-form.jsp\" method=\"post\">\r\n\r\n<fieldset>\r\n    <legend>Filter</legend>\r\n    <div>\r\n    \r\n    <p>\r\n    To enable the content filter you need to set up some regular expressions.\r\n    </p>\r\n    \r\n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <tbody>\r\n    \t<tr>\r\n    \t    <td width=\"1%\">\r\n            <input type=\"radio\" name=\"patternsenabled\" value=\"false\" id=\"not01\"\r\n             ");
      out.print( ((patternsEnabled) ? "" : "checked") );
      out.write(">\r\n        </td>\r\n        <td width=\"99%\">\r\n            <label for=\"not01\"><b>Disabled</b></label> - Packets will not be filtered.\r\n        </td>\r\n    </tr>\r\n    <tr>\r\n        <td width=\"1%\">\r\n            <input type=\"radio\" name=\"patternsenabled\" value=\"true\" id=\"not02\"\r\n             ");
      out.print( ((patternsEnabled) ? "checked" : "") );
      out.write(">\r\n        </td>\r\n        <td width=\"99%\">\r\n            <label for=\"not02\"><b>Enabled</b></label> - Packets will be filtered.\r\n        </td>\r\n    </tr>\r\n    \t<tr>\r\n        \t<td>&nbsp;</td>\r\n        \t<td align=\"left\">Patterns:&nbsp;</td>\r\n    </tr>\r\n    <tr>\r\n        <td>&nbsp;</td>\r\n\t    <td>\r\n\t        <textarea rows=\"10\" cols=\"100\" name=\"patterns\">");
      out.print( (patterns != null ? patterns : "") );
      out.write("</textarea>\r\n\t        \t");
 if (errors.containsKey("missingPatterns")) { 
      out.write("\r\n\t            <span class=\"jive-error-text\">\r\n\t                <br>Please enter comma separated, regular expressions.\r\n\t            </span>\r\n\t            ");
 } else if (errors.containsKey("patternSyntaxException")) { 
      out.write("\r\n\t            <span class=\"jive-error-text\">\r\n\t                <br>Invalid regular expression: ");
      out.print( errors.get("patternSyntaxException") );
      out.write(". Please try again.\r\n\t            </span>\r\n\t            ");
 } 
      out.write("\r\n\t    </td>\r\n\t</tr>\r\n\t<tr>\r\n\t\t<td>&nbsp;</td>\r\n        <td><input type=\"checkbox\" name=\"filterstatus\" value=\"filterstatus\" ");
      out.print( filterStatusEnabled ? "checked" : "" );
      out.write("/>Filter users presence status.</td>\r\n\t</tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n</fieldset>\r\n\r\n<br><br>\r\n\r\n<fieldset>\r\n    <legend>On Content Match</legend>\r\n    <div>\r\n    \r\n    <p>\r\n    Configure this feature to reject or allow (and optionally mask) packet content when there is a match.\r\n    </p>\r\n    \r\n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <tbody>\r\n    \t<tr>\r\n            <td width=\"1%\">\r\n            <input type=\"radio\" name=\"allowonmatch\" value=\"false\" id=\"not03\"\r\n             ");
      out.print( ((allowOnMatch) ? "" : "checked") );
      out.write(">\r\n            </td>\r\n            <td width=\"99%\">\r\n                <label for=\"not01\"><b>Reject</b></label> - Packets will be rejected.\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td width=\"1%\">\r\n            <input type=\"radio\" name=\"allowonmatch\" value=\"true\" id=\"not04\"\r\n             ");
      out.print( ((allowOnMatch) ? "checked" : "") );
      out.write(">\r\n            </td>\r\n            <td width=\"99%\">\r\n                <label for=\"not02\"><b>Allow</b></label> - Packets will be allowed.\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n        \t<td>&nbsp;</td>\r\n\t        <td align=\"left\">Mask:&nbsp;\r\n\t        <input type=\"text\" size=\"100\" maxlength=\"100\" name=\"mask\" \r\n\t        \tvalue=\"");
      out.print( (mask != null ? mask : "") );
      out.write("\">\r\n\t        \t");
 if (errors.containsKey("missingMask")) { 
      out.write("\r\n\t            <span class=\"jive-error-text\">\r\n\t                <br>Please enter a mask.\r\n\t            </span>\r\n\t            ");
 } 
      out.write("\r\n\t        </td>\r\n\t    </tr>\r\n\t    <tr>\r\n\t\t<td>&nbsp;</td>\r\n        <td><input type=\"checkbox\" name=\"maskenabled\" value=\"maskenabled\" ");
      out.print( maskEnabled ? "checked" : "" );
      out.write("/>Enable mask.</td>\r\n\t</tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n</fieldset>\r\n\r\n<br><br>\r\n\r\n<fieldset>\r\n    <legend>Rejection Notification</legend>\r\n    <div>\r\n    \r\n    <p>\r\n    Enable this feature to have the sender notified whenever a packet is rejected.\r\n    NB: This feature is only operational if \"On Content Match\" is set to reject packets.\r\n    </p>\r\n    \r\n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <tbody>\r\n    \t<tr>\r\n            <td width=\"1%\">\r\n            <input type=\"radio\" name=\"rejectionnotificationenabled\" value=\"false\" id=\"not05\"\r\n             ");
      out.print( ((rejectionNotificationEnabled) ? "" : "checked") );
      out.write(">\r\n            </td>\r\n            <td width=\"99%\">\r\n                <label for=\"not01\"><b>Disabled</b></label> - Notifications will not be sent out.\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td width=\"1%\">\r\n            <input type=\"radio\" name=\"rejectionnotificationenabled\" value=\"true\" id=\"not06\"\r\n             ");
      out.print( ((rejectionNotificationEnabled) ? "checked" : "") );
      out.write(">\r\n            </td>\r\n            <td width=\"99%\">\r\n                <label for=\"not02\"><b>Enabled</b></label> - Notifications will be sent out.\r\n            </td>\r\n        </tr>\r\n         <tr>\r\n        \t<td>&nbsp;</td>\r\n\t        <td align=\"left\">Rejection message:&nbsp;\r\n\t        <input type=\"text\" size=\"100\" maxlength=\"100\" name=\"rejectionMsg\" \r\n\t        \tvalue=\"");
      out.print( (rejectionMsg != null ? rejectionMsg : "") );
      out.write("\">\r\n\t        \t");
 if (errors.containsKey("missingRejectionMsg")) { 
      out.write("\r\n\t            <span class=\"jive-error-text\">\r\n\t                <br>Please enter a rejection message.\r\n\t            </span>\r\n\t            ");
 } 
      out.write("\r\n\t        </td>\r\n\t    </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n</fieldset>\r\n\r\n<br><br>\r\n\r\n<fieldset>\r\n    <legend>Content Match Notification</legend>\r\n    <div>\r\n    \r\n    <p>\r\n    Enable this feature to have the contact person notified whenever there is a content match.\r\n    </p>\r\n    \r\n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n    <tbody>\r\n    \t<tr>\r\n            <td width=\"1%\">\r\n            <input type=\"radio\" name=\"notificationenabled\" value=\"false\" id=\"not07\"\r\n             ");
      out.print( ((notificationEnabled) ? "" : "checked") );
      out.write(">\r\n            </td>\r\n            <td width=\"99%\">\r\n                <label for=\"not01\"><b>Disabled</b></label> - Notifications will not be sent out.\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td width=\"1%\">\r\n            <input type=\"radio\" name=\"notificationenabled\" value=\"true\" id=\"not08\"\r\n             ");
      out.print( ((notificationEnabled) ? "checked" : "") );
      out.write(">\r\n            </td>\r\n            <td width=\"99%\">\r\n                <label for=\"not02\"><b>Enabled</b></label> - Notifications will be sent out.\r\n            </td>\r\n        </tr>        \r\n        <tr>\r\n        \t    <td>&nbsp;</td>\r\n\t        <td align=\"left\">Username:&nbsp\r\n                <input type=\"text\" size=\"20\" maxlength=\"100\" name=\"contactname\" value=\"");
      out.print( (contactName != null ? contactName : "") );
      out.write('"');
      out.write('>');
      out.write('@');
      out.print( XMPPServer.getInstance().getServerInfo().getXMPPDomain() );
      out.write("\r\n\t\t        ");
 if (errors.containsKey("missingContactName")) { 
      out.write("\r\n\t\t            <span class=\"jive-error-text\">\r\n\t\t            <br>Please enter a username.\r\n\t\t            </span>\r\n\t\t        ");
 } else if (errors.containsKey("userNotFound")) { 
      out.write("\r\n\t\t            <span class=\"jive-error-text\">\r\n\t\t            <br>Could not find user. Please try again.\r\n\t\t            </span>\r\n\t\t        ");
 } 
      out.write("\r\n\t        </td>\r\n\t    </tr>\r\n\t    <tr>\r\n\t        <td>&nbsp;</td>\r\n\t        <td>\r\n                <input type=\"checkbox\" name=\"notificationcb\" value=\"notificationbyim\" ");
      out.print( notificationByIMEnabled ? "checked" : "" );
      out.write("/>Notify by IM.\r\n                <input type=\"checkbox\" name=\"notificationcb\" value=\"notificationbyemail\" ");
      out.print( notificationByEmailEnabled ? "checked" : "" );
      out.write("/>Notify by Email.\r\n\t            <input type=\"checkbox\" name=\"notificationcb\" value=\"notificationincludeoriginal\" ");
      out.print( includeOriginalEnabled ? "checked" : "" );
      out.write("/>Include original packet.\r\n\t            ");
 if (errors.containsKey("mailServerNotConfigured")) { 
      out.write("\r\n\t\t            <span class=\"jive-error-text\">\r\n\t\t            <br>Error, sending an email will fail because the mail server is not setup. Please go to the <a href=\"/system-email.jsp\">mail settings page</a> and set the mail host.\r\n\t\t            </span>\r\n\t\t        ");
 } else if (errors.containsKey("userEmailNotConfigured")) { 
      out.write("\r\n\t\t            <span class=\"jive-error-text\">\r\n\t\t            <br>Please configure <a href=\"/user-properties.jsp?username=");
      out.print( contactName );
      out.write('"');
      out.write('>');
      out.print( contactName );
      out.write("'s</a> email address.\r\n\t\t            </span>\r\n\t\t        ");
 } else if (errors.containsKey("notificationFormatNotConfigured")) { 
      out.write("\r\n\t\t            <span class=\"jive-error-text\">\r\n\t\t            <br>Users must be notified by IM and/or Email.\r\n\t\t            </span>\r\n\t\t        ");
 } 
      out.write("\r\n\t        </td>\r\n\t    </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n</fieldset>\r\n\r\n<br><br>\r\n\r\n<input type=\"submit\" name=\"save\" value=\"Save settings\">\r\n<input type=\"submit\" name=\"reset\" value=\"Restore factory settings*\">\r\n</form>\r\n\r\n<br><br>\r\n\r\n<em>*Restores the plugin to its factory state, you will lose all changes ever made to this plugin!</em>\r\n</body>\r\n</html>");
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
}
