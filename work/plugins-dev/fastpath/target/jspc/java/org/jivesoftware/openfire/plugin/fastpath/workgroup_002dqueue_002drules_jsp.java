package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.*;
import java.util.*;
import org.jivesoftware.xmpp.workgroup.*;
import org.xmpp.packet.JID;

public final class workgroup_002dqueue_002drules_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n");

    final boolean addsuccess = ParamUtils.getParameter(request, "addsuccess") != null;
    final boolean deletesuccess = ParamUtils.getParameter(request, "deletesuccess") != null;

      out.write("\r\n\r\n");
 // Get parameters //
    String wgID = ParamUtils.getParameter(request, "wgID");
    long   queueID = ParamUtils.getLongParameter(request, "qID", -1L);
    boolean add = request.getParameter("add") != null;
    boolean delete = request.getParameter("delete") != null;
    String name = ParamUtils.getParameter(request, "name");
    String value = ParamUtils.getParameter(request, "value");

    // Load the workgroup
    final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));
    AgentManager aManager = workgroupManager.getAgentManager();
    // Load the queue:
    RequestQueue queue = workgroup.getRequestQueue(queueID);
    Map    errors = new HashMap();
    if (add) {
        if (name == null) {
            errors.put("name", "");
        }
        if (value == null) {
            errors.put("value", "");
        }
        if (errors.size() == 0) {
            queue.getProperties().setProperty(name, value);
            response.sendRedirect("workgroup-queue-rules.jsp?wgID=" + wgID + "&qID=" + queueID + "&addsuccess=true");
            return;
        }
    }
    if (delete) {
        if (name != null) {
            queue.getProperties().deleteProperty(name);
            response.sendRedirect("workgroup-queue-rules.jsp?wgID=" + wgID + "&qID=" + queueID + "&deletesuccess=true");
            return;
        }
    }
    DbProperties props = queue.getProperties();

      out.write("\r\n<html>\r\n    <head>\r\n        <title>Add Queue Rules</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-queues\"/>\r\n        <meta name=\"extraParams\" content=\"wgID=");
      out.print( wgID );
      out.write("\"/>\r\n    </head>\r\n    <body>\r\n        <p>Below is a list of routing rules for this queue. Use the form below to add new rules. Routing rules are\r\n        keyed off of incoming call request meta data. So, if you have a rule name and value of &quot;foo&quot; and\r\n        &quot;bar&quot; then this rule would match if there is an incoming meta data name of &quot;foo&quot; with the\r\n        value &quot;bar&quot;.</p>\r\n       ");
 if (addsuccess) { 
      out.write("\r\n            <div class=\"jive-success\">\r\n                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n                    <tbody>\r\n                        <tr>\r\n                            <td class=\"jive-icon\">\r\n                                <img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"/>\r\n                            </td>\r\n                            <td class=\"jive-icon-label\">Rule has been added.</td>\r\n                        </tr>\r\n                    </tbody>\r\n                </table>\r\n            </div>\r\n        ");
 } 
      out.write("\r\n        ");
 if(deletesuccess) { 
      out.write("\r\n            <div class=\"jive-success\">\r\n                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n                    <tbody>\r\n                        <tr>\r\n                            <td class=\"jive-icon\">\r\n                                <img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"/>\r\n                            </td>\r\n                            <td class=\"jive-icon-label\">Rule has been deleted.</td>\r\n                        </tr>\r\n                    </tbody>\r\n                </table>\r\n            </div>\r\n            ");
 } 
      out.write("\r\n    <br/>\r\n    <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n        <tr>\r\n            <th>&nbsp;</th> <th>Name</th> <th>Value</th> <th>Delete</th>\r\n        </tr>\r\n");

        Collection<String> propertyKeys = props.getPropertyNames();
        if (propertyKeys.isEmpty()) {

      out.write("\r\n            <tr>\r\n                <td colspan=\"4\" align=\"center\">\r\n                    <br/>\r\n                    <i>No rules set</i>\r\n                    <br/>\r\n                    <br/>\r\n                </td>\r\n            </tr>\r\n");

        }

      out.write('\r');
      out.write('\n');

        int counter = 0;
        for (String key : propertyKeys) {
            counter++;
            String val = props.getProperty(key);

      out.write("\r\n            <tr class=\"jive-");
      out.print( (((counter % 2) == 0) ? "even" : "odd") );
      out.write("\">\r\n                <td width=\"1%\" nowrap>\r\n                    ");
      out.print( (counter) );
      out.write(".\r\n                </td>\r\n                <td width=\"49%\">\r\n                    ");
      out.print( StringUtils.escapeHTMLTags(key) );
      out.write("\r\n                </td>\r\n                <td width=\"49%\">\r\n                    ");
      out.print( StringUtils.escapeHTMLTags(val) );
      out.write("\r\n                </td>\r\n                <td width=\"1%\" nowrap align=\"center\">\r\n                    <a href=\"workgroup-queue-rules.jsp?wgID=");
      out.print( wgID );
      out.write("&qID=");
      out.print( queueID );
      out.write("&delete=true&name=");
      out.print( key );
      out.write("\">\r\n                    <img src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"/> </a>\r\n                </td>\r\n            </tr>\r\n");

        }

      out.write("\r\n    </table>\r\n          <p>\r\n              <b>Add New Rule</b>\r\n          </p>\r\n    <form action=\"workgroup-queue-rules.jsp\" method=\"post\">\r\n        <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\"/>\r\n        <input type=\"hidden\" name=\"qID\" value=\"");
      out.print( queueID );
      out.write("\"/>\r\n        <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n            <tr>\r\n                <td class=\"c1\">Name: *\r\n");

                    if (errors.get("name") != null) {

      out.write("\r\n                        <span class=\"jive-error-text\">\r\n                        <br/>Please enter a valid rule name. </span>\r\n");

                    }

      out.write("\r\n                </td>\r\n                <td class=\"c2\">\r\n                    <input type=\"text\" name=\"name\" size=\"30\" value=\"");
      out.print( ((name != null) ? name : "") );
      out.write("\"/>\r\n                </td>\r\n            </tr>\r\n            <tr>\r\n                <td class=\"c1\">Value: *\r\n");

                    if (errors.get("value") != null) {

      out.write("\r\n                        <span class=\"jive-error-text\">\r\n                        <br/>Please enter a valid value for this rule. </span>\r\n");

                    }

      out.write("\r\n                </td>\r\n                <td class=\"c2\">\r\n                    <input type=\"text\" name=\"value\" size=\"30\" value=\"");
      out.print( ((value != null) ? value : "") );
      out.write("\"/>\r\n                </td>\r\n            </tr>\r\n        </table>\r\n            <p>* Required fields</p>\r\n        <input type=\"submit\" name=\"add\" value=\"Add Rule\"/>\r\n    </form>\r\n    </body>\r\n</html>\r\n");
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
