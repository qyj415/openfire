package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.packet.JID;
import java.util.HashMap;
import java.util.Map;
import org.jivesoftware.openfire.fastpath.util.WorkgroupUtils;
import org.jivesoftware.openfire.fastpath.dataforms.FormManager;
import org.jivesoftware.openfire.fastpath.dataforms.WorkgroupForm;
import org.jivesoftware.openfire.fastpath.dataforms.FormElement;

public final class workgroup_002dproperties_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			"workgroup-error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

    // Get parameters //
    String wgID = ParamUtils.getParameter(request, "wgID");
    boolean created = ParamUtils.getParameter(request, "created") != null;

      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>Workgroup Settings For ");
      out.print(wgID);
      out.write("</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-properties\"/>\r\n        <meta name=\"extraParams\" content=\"wgID=");
      out.print( wgID );
      out.write("\"/>\r\n        <!--<meta name=\"helpPage\" content=\"edit_workgroup_properties.html\"/>-->\r\n    </head>\r\n    <body>\r\n\r\n    ");
 if(created) { 
      out.write("\r\n        <div class=\"jive-success\">\r\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n                <tbody>\r\n                    <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\"\r\n                    border=\"0\"></td>\r\n                        <td class=\"jive-icon-label\">\r\n                            Workgroup has been created. To add members to the workgroup, click on the Queues link in the sidebar.\r\n                        </td></tr>\r\n                </tbody>\r\n            </table>\r\n        </div><br>\r\n    ");
 } 
      out.write('\r');
      out.write('\n');

    // Get a workgroup manager
    WorkgroupManager wgManager = WorkgroupManager.getInstance();
    // If the workgroup manager is null, service is down so redirect:
    if (wgManager == null) {
        response.sendRedirect("error-serverdown.jsp");
        return;
    }

      out.write('\r');
      out.write('\n');

    // Load the workgroup object
    JID workgroupJID = new JID(wgID);
    Workgroup workgroup = wgManager.getWorkgroup(workgroupJID);
    int maxChats = workgroup.getMaxChats();
    int minChats = workgroup.getMinChats();
    long requestTimeout = workgroup.getRequestTimeout() / 1000;
    long offerTimeout = workgroup.getOfferTimeout() / 1000;
    String description = workgroup.getDescription();
    String displayName = workgroup.getDisplayName();
    boolean authRequired = Boolean.valueOf(workgroup.getProperties().getProperty("authRequired"));
    boolean doEnable = ParamUtils.getBooleanParameter(request, "doEnable");
    boolean enableWorkgroup = ParamUtils.getBooleanParameter(request, "enableWorkgroup");

    boolean update = ModelUtil.hasLength(request.getParameter("update"));

    if (doEnable && ModelUtil.hasLength(request.getParameter("enableWorkgroup"))) {
        if (enableWorkgroup) {
            workgroup.setStatus(Workgroup.Status.READY);
        }
        else {
            workgroup.setStatus(Workgroup.Status.CLOSED);
        }
    }

    String statusMessage = "";
    Map errors = new HashMap();
    if (update) {
        displayName = request.getParameter("displayName");
        if (displayName == null && displayName.length() == 0) {
            errors.put("displayName", "");
        }

        maxChats = ParamUtils.getIntParameter(request, "maxChats", wgManager.getDefaultMaxChats());


        minChats = ParamUtils.getIntParameter(request, "minChats", wgManager.getDefaultMinChats());


        requestTimeout = ParamUtils.getLongParameter(request, "requestTimeout",
                wgManager.getDefaultRequestTimeout() / 1000) * 1000;

        offerTimeout = ParamUtils.getLongParameter(request, "offerTimeout",
                wgManager.getDefaultOfferTimeout() / 1000) * 1000;

        authRequired = ParamUtils.getBooleanParameter(request, "authRequired", false);


        if (minChats <= 0) {
            errors.put("minChats", "");
        }
        if (minChats > maxChats) {
            errors.put("minChatsGreater", "");
        }
        if (requestTimeout <= 0) {
            errors.put("requestTimeout", "");
        }
        if (offerTimeout <= 0) {
            errors.put("offerTimeout", "");
        }
        if (offerTimeout > requestTimeout) {
            errors.put("offerGreater", "");
        }
        if (errors.size() == 0) {
            description = request.getParameter("description");
            statusMessage = WorkgroupUtils.updateWorkgroup(wgID, displayName, description, maxChats,
                    minChats, requestTimeout, offerTimeout);
            requestTimeout = workgroup.getRequestTimeout() / 1000;
            offerTimeout = workgroup.getOfferTimeout() / 1000;
            workgroup.getProperties().setProperty("authRequired", String.valueOf(authRequired));

            FormManager formManager = FormManager.getInstance();

            WorkgroupForm workgroupForm = formManager.getWebForm(workgroup);
            if (workgroupForm == null) {
                workgroupForm = new WorkgroupForm();
                formManager.addWorkgroupForm(workgroup, workgroupForm);
            }

            // check if password field exists and get its index if it does exist.
            int index = -1;
            int counter = 0;
            for (FormElement element : workgroupForm.getFormElements()) {
                if ("password".equals(element.getVariable())) {
                    index = counter;
                    break;
                }
                counter++;
            }

            if (authRequired && index == -1) {
                // Create Element
                FormElement formElement = new FormElement();
                formElement.setLabel("Password");
                formElement.setAnswerType(WorkgroupForm.FormEnum.password);
                formElement.setRequired(true);
                formElement.setVisible(true);
                formElement.setVariable("password");
                formElement.setDescription("Authentication Required");
                workgroupForm.addFormElement(formElement);
                formManager.saveWorkgroupForm(workgroup);
            }
            else if (!authRequired && index != -1) {
                // Remove Element
                workgroupForm.removeFormElement(index);
                formManager.saveWorkgroupForm(workgroup);
            }
        }
    }


      out.write("\r\n    <p>Below are the general settings for the <b>");
      out.print( workgroupJID.getNode() );
      out.write("</b> workgroup.</p>\r\n    <script langauge=\"JavaScript\" type=\"text/javascript\">\r\n        function wgEnable(enable) {\r\n            if (enable) {\r\n                document.overview.enableWorkgroup.value = 'true';\r\n            }\r\n            else{\r\n                document.overview.enableWorkgroup.value = 'false';\r\n            }\r\n            document.overview.submit();\r\n        }\r\n    </script>\r\n\r\n");
  if (!errors.isEmpty()) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n            <td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"/></td>\r\n            <td class=\"jive-icon-label\">\r\n\r\n            ");
 if (errors.get("displayName") != null) { 
      out.write("\r\n                Please enter a valid display name.\r\n            ");
 } else if (errors.get("maxChats") != null) { 
      out.write("\r\n                Please enter a valid max number of chats value.\r\n            ");
 } else if (errors.get("minChats") != null) { 
      out.write("\r\n                Please enter a valid min number of chats value.\r\n            ");
 } else if (errors.get("minChatsGreater") != null) { 
      out.write("\r\n                Min chats must be less than max chats.\r\n            ");
 } else if (errors.get("requestTimeout") != null) { 
      out.write("\r\n                Please enter a valid request timeout value.\r\n            ");
 } else if (errors.get("offerTimeout") != null) { 
      out.write("\r\n                Please enter a valid offer timeout value.\r\n            ");
 } else if (errors.get("offerGreater") != null) { 
      out.write("\r\n                Offer timeout must be less than request timeout.\r\n            ");
 } 
      out.write("\r\n            </td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n    <br>\r\n\r\n");
  } else { 
      out.write("\r\n\r\n      ");
      out.print( statusMessage );
      out.write("\r\n\r\n");
  } 
      out.write("\r\n\r\n    <form action=\"workgroup-properties.jsp\" name=\"overview\">\r\n    <table width=\"100%\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n        <tr>\r\n            <th colspan=\"2\">Workgroup Details</th>\r\n        </tr>\r\n\r\n        <tr>\r\n            <td class=\"c1\"><b>Current Status</b></td>\r\n            <td>\r\n                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-width : 0px !important;\">\r\n                    <tr>\r\n");

                        if (workgroup.getStatus() == Workgroup.Status.OPEN) {

      out.write("\r\n                            <td class=\"c2\">\r\n                                <img src=\"images/bullet-green-14x14.gif\" width=\"14\" height=\"14\" border=\"0\"/>\r\n                            </td>\r\n                            <td class=\"c2\">Workgroup is currently active and accepting requests.</td>\r\n                            <td>&nbsp;\r\n                                <input type=\"button\" value=\"Close\" onclick=\"wgEnable(false);return false;\"/>\r\n                            </td>\r\n");

                        }
                        else if (workgroup.getStatus() == Workgroup.Status.READY) {

      out.write("\r\n                            <td class=\"c2\">\r\n                                <img src=\"images/bullet-yellow-14x14.gif\" width=\"14\" height=\"14\" border=\"0\"/>\r\n                            </td>\r\n                            <td class=\"c2\">Waiting for member.</td>\r\n                            <td>&nbsp;\r\n                                <input type=\"button\" value=\"Close\" onclick=\"wgEnable(false);return false;\"/>\r\n                            </td>\r\n");

                        }
                        else{

      out.write("\r\n                            <td class=\"c2\">\r\n                                <img src=\"images/bullet-red-14x14.gif\" width=\"14\" height=\"14\" border=\"0\"/>\r\n                            </td>\r\n                            <td class=\"c2\">&nbsp; Workgroup is currently closed.</td>\r\n                            <td>&nbsp;\r\n                                <input type=\"button\" value=\"Enable\" onclick=\"wgEnable(true);return false;\"/>\r\n                            </td>\r\n");

                        }

      out.write("\r\n                    </tr>\r\n                </table>\r\n            </td>\r\n        </tr>\r\n       \r\n         <tr>\r\n            <td class=\"c1\">\r\n                <b>Display Name</b>\r\n            </td>\r\n            <td class=\"c2\">\r\n                <input type=\"text\" name=\"displayName\" size=\"30\" maxlength=\"50\" value=\"");
      out.print( ((displayName != null) ? displayName : "") );
      out.write("\">\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n           <td class=\"c1\">\r\n               <b>Description</b>\r\n           </td>\r\n           <td class=\"c2\">\r\n               <textarea id=\"description\" name=\"description\" cols=\"30\" rows=\"3\">");
      out.print( ((description != null) ? description : "") );
      out.write("</textarea>\r\n           </td>\r\n       </tr>\r\n        </table>\r\n    <br/>\r\n     <table width=\"100%\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n        <tr>\r\n            <th colspan=\"2\">Chat Request Settings</th>\r\n        </tr>\r\n         <tr>\r\n            <td class=\"c1\">\r\n                <b>Max Sessions</b><br/><span class=\"jive-description\">Specify the maximum number of chats for a workgroup member.</span>\r\n            </td>\r\n            <td class=\"c2\">\r\n                            <input type=\"text\" name=\"maxChats\" value=\"");
      out.print( maxChats );
      out.write("\"\r\n                             size=\"5\" maxlength=\"5\"\r\n                            >\r\n                        </td>\r\n                    </tr>\r\n        <tr>\r\n            <td class=\"c1\">\r\n              <b>Min Sessions</b><br/><span class=\"jive-description\">Specify the minimum number of chats for a workgroup member.</span>\r\n            </td>\r\n                  <td class=\"c2\">\r\n                            <input type=\"text\" name=\"minChats\" value=\"");
      out.print( minChats );
      out.write("\"\r\n                             size=\"5\" maxlength=\"5\">\r\n                        </td>\r\n                    </tr>\r\n\r\n        <tr>\r\n            <td class=\"c1\">\r\n                <b>Request timeout</b><br/><span class=\"jive-description\">Total time a user will be in a queue before timing out.</span>\r\n            </td>\r\n  <td class=\"c2\">\r\n                            <input type=\"text\" name=\"requestTimeout\" value=\"");
      out.print(requestTimeout);
      out.write("\"\r\n                             size=\"5\" maxlength=\"10\"> seconds\r\n      </td>\r\n\r\n        </tr>\r\n        <tr>\r\n            <td class=\"c1\">\r\n                <b>Offer Timeout</b><br/><span class=\"jive-description\">Amount of time each member has to answer an incoming request.</span>\r\n            </td>\r\n            <td class=\"c2\">\r\n\r\n                            <input type=\"text\" name=\"offerTimeout\" value=\"");
      out.print( offerTimeout );
      out.write("\"\r\n                             size=\"5\" maxlength=\"10\"> seconds\r\n                        </td>\r\n                    </tr>\r\n\r\n\r\n        <tr>\r\n            <td class=\"c1\">\r\n                <b>Web authentication</b><br/><span class=\"jive-description\">If checked, requires user to have a valid Openfire account.</span>\r\n            </td>\r\n            <td class=\"c2\">\r\n                <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n                <tbody>\r\n                    <input type=\"checkbox\" name=\"authRequired\" ");
      out.print( (authRequired ? "checked" : "") );
      out.write(">\r\n                </tbody>\r\n                </table>\r\n            </td>\r\n        </tr>\r\n    </table>\r\n    <br/>\r\n     <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\"/>\r\n        <input type=\"hidden\" name=\"enableWorkgroup\" value=\"\"/>\r\n        <input type=\"hidden\" name=\"doEnable\" value=\"true\"/>\r\n        <input type=\"submit\" name=\"update\" value=\"Update Workgroup\" />\r\n     </form>\r\n\r\n\r\n    </body>\r\n</html>\r\n\r\n\r\n\r\n\r\n");
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
