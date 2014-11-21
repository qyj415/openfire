package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import java.util.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.muc.MultiUserChatService;

public final class workgroup_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n<html>\r\n    <head>\r\n        <title>Workgroup Settings</title>\r\n        <meta name=\"pageID\" content=\"workgroup-settings\"/>\r\n        <!--<meta name=\"helpPage\" content=\"edit_global_workgroup_settings.html\"/>-->\r\n    </head>\r\n    <body>\r\n");


    // Get a workgroup manager
    WorkgroupManager wgManager = WorkgroupManager.getInstance();

    // If the workgroup manager is null, service is down so redirect:
    if (wgManager == null) {
        response.sendRedirect("error-serverdown.jsp");
        return;
    }

      out.write('\r');
      out.write('\n');
  // Get parameters
    int maxChats = ParamUtils.getIntParameter(request,"maxChats",0);
    int minChats = ParamUtils.getIntParameter(request,"minChats",0);
    long requestTimeout = ParamUtils.getLongParameter(request,"requestTimeout",0);
    long offerTimeout = ParamUtils.getLongParameter(request,"offerTimeout",0);
    int rejectionTimeout = ParamUtils.getIntParameter(request,"rejectionTimeout",0);
    int maxOverflows = ParamUtils.getIntParameter(request,"maxOverflows",3);
    boolean canChangeName = ParamUtils.getBooleanParameter(request, "canChangeName");
    boolean save = request.getParameter("save") != null;

    Map errors = new HashMap();
    if (save) {
        if (maxChats <= 0) {
            errors.put("maxChats","");
        }
        if (minChats <= 0) {
            errors.put("minChats","");
        }
        if (minChats > maxChats) {
            errors.put("minChatsGreater","");
        }
        if (requestTimeout <= 0) {
            errors.put("requestTimeout","");
        }
        if (offerTimeout <= 0) {
            errors.put("offerTimeout","");
        }
        if (offerTimeout > requestTimeout) {
            errors.put("offerGreater","");
        }
        if (rejectionTimeout <= 0) {
            errors.put("rejectionTimeout","");
        }
        if (rejectionTimeout > requestTimeout) {
            errors.put("rejectionGreater","");
        }
        if (maxOverflows < 0) {
            errors.put("maxOverflows","");
        }

        if (errors.size() == 0) {
            wgManager.setDefaultMaxChats(maxChats);
            wgManager.setDefaultMinChats(minChats);
            wgManager.setDefaultRequestTimeout(requestTimeout * 1000);
            wgManager.setDefaultOfferTimeout(offerTimeout * 1000);
            JiveGlobals.setProperty("xmpp.live.rejection.timeout", Integer.toString(rejectionTimeout * 1000));
            JiveGlobals.setProperty("xmpp.live.request.overflow", Integer.toString(maxOverflows));
            JiveGlobals.setProperty("xmpp.live.agent.change-properties", canChangeName ? "true" : "false");
            // done, so redirect
            response.sendRedirect("workgroup-settings.jsp?success=true");
            return;
        }
    }

    if (errors.size() == 0) {
        maxChats = wgManager.getDefaultMaxChats();
        minChats = wgManager.getDefaultMinChats();
        requestTimeout = wgManager.getDefaultRequestTimeout() / 1000;
        offerTimeout = wgManager.getDefaultOfferTimeout() / 1000;
        rejectionTimeout = JiveGlobals.getIntProperty("xmpp.live.rejection.timeout", 20000) / 1000;
        maxOverflows = JiveGlobals.getIntProperty("xmpp.live.request.overflow", 3);
        canChangeName = JiveGlobals.getBooleanProperty("xmpp.live.agent.change-properties", true);
    }

      out.write("\r\n<style type=\"text/css\">\r\n    @import \"style/style.css\";\r\n</style>\r\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" >\r\n<tr><td colspan=\"2\">\r\nUse the form below to set properties that are global to all workgroups. The current set of\r\nproperties below only affect the default settings of newly created workgroups.\r\n</td></tr></table>\r\n<br>\r\n\r\n");
  if (errors.get("general") != null) { 
      out.write("\r\n\r\n    <p class=\"jive-error-text\">\r\n    Error saving settings.\r\n    </p>\r\n\r\n");
  } 
      out.write("\r\n\r\n");
  if ("true".equals(request.getParameter("success"))) { 
      out.write("\r\n\r\n    <p class=\"jive-success-text\">\r\n    Settings updated successfully.\r\n    </p>\r\n\r\n");
  } 
      out.write("\r\n\r\n<form name=\"f\" action=\"workgroup-settings.jsp\" method=\"post\">\r\n\r\n<table width=\"100%\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"600\">\r\n<tr>\r\n    <th colspan=\"3\">Global Settings</th>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td class=\"c1\" nowrap>\r\n        <b>Default maximum chat sessions per agent: *</b>\r\n\r\n        ");
  if (errors.get("maxChats") != null) { 
      out.write("\r\n\r\n            <span class=\"jive-error-text\">\r\n            <br>Invalid number.\r\n            </span>\r\n\r\n        ");
  } 
      out.write("\r\n    </td>\r\n    <td class=\"c2\">\r\n        <input type=\"text\" name=\"maxChats\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( maxChats );
      out.write("\">\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td class=\"c1\" nowrap>\r\n        <b>Default minimum chat sessions per agent: *</b>\r\n\r\n        ");
  if (errors.get("minChats") != null) { 
      out.write("\r\n\r\n            <span class=\"jive-error-text\">\r\n            <br>Invalid number.\r\n            </span>\r\n\r\n        ");
  } else if (errors.get("minChatsGreater") != null) { 
      out.write("\r\n\r\n            <span class=\"jive-error-text\">\r\n            <br>Min chats must be less than max chats.\r\n            </span>\r\n\r\n        ");
  } 
      out.write("\r\n    </td>\r\n    <td class=\"c2\">\r\n        <input type=\"text\" name=\"minChats\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( minChats );
      out.write("\">\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td class=\"c1\">\r\n        <b>Request timeout: *</b>\r\n\r\n        ");
  if (errors.get("requestTimeout") != null) { 
      out.write("\r\n\r\n            <span class=\"jive-error-text\">\r\n            <br>Invalid number.\r\n            </span>\r\n\r\n        ");
  } 
      out.write("\r\n        <br>\r\n        <span class=\"jive-description\">\r\n        The total time before an individual request will timeout if no agent accepts it.\r\n        </span>\r\n    </td>\r\n    <td class=\"c2\">\r\n        <input type=\"text\" name=\"requestTimeout\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( requestTimeout );
      out.write("\"> seconds\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td class=\"c1\" nowrap>\r\n        <b>Agent timeout to accept an offer: *</b>\r\n\r\n        ");
  if (errors.get("offerTimeout") != null) { 
      out.write("\r\n\r\n            <span class=\"jive-error-text\">\r\n            <br>Invalid number.\r\n            </span>\r\n\r\n        ");
  } else if (errors.get("offerGreater") != null) { 
      out.write("\r\n\r\n            <span class=\"jive-error-text\">\r\n            <br>Offer timeout must be less than request timeout.\r\n            </span>\r\n\r\n        ");
  } 
      out.write("\r\n        <br>\r\n        <span class=\"jive-description\">\r\n        The time each agent will be given to accept a chat request.\r\n        </span>\r\n    </td>\r\n    <td class=\"c2\">\r\n        <input type=\"text\" name=\"offerTimeout\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( offerTimeout );
      out.write("\"> seconds\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td class=\"c1\">\r\n        <b>Expire agent rejection: *</b>\r\n\r\n        ");
  if (errors.get("rejectionTimeout") != null) { 
      out.write("\r\n\r\n            <span class=\"jive-error-text\">\r\n            <br>Invalid number.\r\n            </span>\r\n\r\n        ");
  } else if (errors.get("rejectionGreater") != null) { 
      out.write("\r\n\r\n            <span class=\"jive-error-text\">\r\n            <br>Rejection timeout must be less than request timeout.\r\n            </span>\r\n\r\n        ");
  } 
      out.write("\r\n        <br>\r\n        <span class=\"jive-description\">\r\n        The time each rejection will last. Once expired new offers for the rejected request may be sent again.\r\n        </span>\r\n    </td>\r\n    <td class=\"c2\">\r\n        <input type=\"text\" name=\"rejectionTimeout\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( rejectionTimeout );
      out.write("\"> seconds\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td class=\"c1\">\r\n        <b>Times to overflow before canceling request: *</b>\r\n\r\n        ");
  if (errors.get("maxOverflows") != null) { 
      out.write("\r\n\r\n            <span class=\"jive-error-text\">\r\n            <br>Invalid number.\r\n            </span>\r\n\r\n        ");
  } 
      out.write("\r\n        <br/>\r\n        <span class=\"jive-description\">\r\n        Number of times a request may be moved to other queues before giving up and canceling the request.\r\n        </span>\r\n    </td>\r\n    <td class=\"c2\">\r\n        <input type=\"text\" name=\"maxOverflows\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( maxOverflows );
      out.write("\">\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td class=\"c1\" nowrap>\r\n        <b>Agents are allowed to change their names: *</b>\r\n    </td>\r\n    <td class=\"c2\">\r\n        <input type=\"checkbox\" name=\"canChangeName\" ");
      out.print( (canChangeName ? "checked" : "") );
      out.write(">\r\n    </td>\r\n</tr>\r\n</table>\r\n<br>\r\n\r\n* Required field.\r\n\r\n<br><br>\r\n\r\n<input type=\"submit\" name=\"save\" value=\"Save Settings\">\r\n\r\n</form>\r\n\r\n</body>\r\n</html>");
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
