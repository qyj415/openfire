package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.RequestQueue;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.routing.RoutingManager;
import org.jivesoftware.xmpp.workgroup.routing.RoutingRule;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.forms.DataForm;
import org.xmpp.forms.FormField;
import org.xmpp.packet.JID;
import java.util.Collection;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.NotFoundException;
import org.jivesoftware.openfire.fastpath.dataforms.FormManager;
import org.jivesoftware.openfire.user.UserNotFoundException;

public final class workgroup_002drouting_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n<html>\n  <head><title>Workgroup Routing</title></head>\n  <body>\n\n  ");

      String wgID = request.getParameter("wgID");

      final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
      Workgroup workgroup = null;
      try {
          workgroup = workgroupManager.getWorkgroup(new JID(wgID));
      }
      catch (UserNotFoundException e) {
          Log.error(e);
      }
      FormManager formManager = FormManager.getInstance();
      DataForm dataForm = formManager.getDataForm(workgroup);

      RoutingManager routingManager = RoutingManager.getInstance();

      Collection<RoutingRule> rules = routingManager.getRoutingRules(workgroup);

      boolean edit = request.getParameter("edit") != null;
      int pos = ParamUtils.getIntParameter(request, "pos", -1);

      boolean submit = request.getParameter("submit") != null;
      boolean errors = false;
      if (submit) {
          String variable = request.getParameter("variable");
          String value = request.getParameter("value");


          if (!ModelUtil.hasLength(value)) {
              errors = true;
          }

          if (!errors) {
              // Add Rule
              long queueID = ParamUtils.getLongParameter(request, "queueID", -1);
              routingManager.addRoutingRule(workgroup, queueID, rules.size() + 1, "");
          }
      }


      boolean changePos = request.getParameter("changePos") != null;
      boolean delete = request.getParameter("remove") != null;


      if (changePos) {
          boolean up = request.getParameter("up") != null;
          boolean down = request.getParameter("down") != null;
          String index = request.getParameter("pos");
          int routerIndex = Integer.parseInt(index);

          RoutingRule moveUpRule = null;
          RoutingRule moveDownRule = null;
          if (up) {
              // Change selected router index to pos - 1 and
              // change pos - 1 to pos + 1 and save.
              for (RoutingRule rule : rules) {
                  if (rule.getPosition() == routerIndex) {
                      moveUpRule = rule;
                  }

                  if (rule.getPosition() == routerIndex - 1) {
                      moveDownRule = rule;
                  }
              }

              // Delete both rules and reapply
              routingManager.removeRoutingRule(workgroup, routerIndex);
              routingManager.removeRoutingRule(workgroup, routerIndex - 1);

              // Add new rules
              moveUpRule.setPosition(routerIndex - 1);
              moveDownRule.setPosition(routerIndex);

          }
          else if (down) {
              for (RoutingRule rule : rules) {
                  if (rule.getPosition() == routerIndex) {
                      moveUpRule = rule;
                  }

                  if (rule.getPosition() == routerIndex - 1) {
                      moveDownRule = rule;
                  }
              }

              // Delete both rules and reapply
              routingManager.removeRoutingRule(workgroup, routerIndex);
              routingManager.removeRoutingRule(workgroup, routerIndex + 1);

              // Add new rules
              moveUpRule.setPosition(routerIndex + 1);
              moveDownRule.setPosition(routerIndex);
          }

          routingManager
                  .addRoutingRule(workgroup, moveUpRule.getQueueID(), moveUpRule.getPosition(), "");
          routingManager.addRoutingRule(workgroup, moveDownRule.getQueueID(),
                  moveDownRule.getPosition(), "");
      }

      if (delete) {
          String index = request.getParameter("pos");
          int routerIndex = Integer.parseInt(index);
          routingManager.removeRoutingRule(workgroup, routerIndex);
          for (RoutingRule rule : rules) {
              if (rule.getPosition() > routerIndex) {
                  routingManager
                          .updateRoutingRule(workgroup, rule.getPosition(), rule.getPosition() - 1);
              }
          }

      }


      rules = routingManager.getRoutingRules(workgroup);

  
      out.write("\n\n  ");
 if (errors) { 
      out.write("\n        <div class=\"errors\">\n            Please specify a value to match against the metadata variable.\n        </div>\n  ");
 } 
      out.write("\n\n   <div class=\"div-border\" style=\"padding: 12px; width: 95%;\">\n        <table class=\"jive-table\" cellspacing=\"0\" width=\"100%\">\n            <th>Order</th><th>Metadata</th><th>Value</th><th>Routes to Queue</th><th>Move</th><th>Edit</th><th>Delete</th>\n\n            <tr style=\"border-left: none;\">\n\n            </tr>\n\n\n            ");
 for (RoutingRule rule : rules) {
                RequestQueue rq = null;
                try {
                    rq = workgroup.getRequestQueue(rule.getQueueID());
                }
                catch (NotFoundException e) {
                    Log.error(e);
                }

                int rulePosition = rule.getPosition();
            
      out.write("\n               <tr>\n                   <td>");
      out.print( rule.getPosition());
      out.write("</td>\n                   <td>\n                       test\n                   </td>\n                   \n                   <td>\n                       ");
      out.print( rq.getName() );
      out.write("\n                   </td>\n                  <td nowrap>\n            ");
  if ((rule.getPosition()) < rules.size()) { 
      out.write("\n                <a href=\"workgroup-routing.jsp?wgID=");
      out.print( wgID );
      out.write("&changePos=true&down=true&pos=");
      out.print( rule.getPosition() );
      out.write("\"\n                        ><img src=\"images/arrow_down.gif\" width=\"16\" height=\"16\" alt=\"Move this router down.\" border=\"0\"></a>\n            ");
  } else { 
      out.write("\n                <img src=\"images/blank.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/>\n            ");
  } 
      out.write("\n\n            ");
  if (rule.getPosition() != 1) { 
      out.write("\n                <a href=\"workgroup-routing.jsp?wgID=");
      out.print( wgID );
      out.write("&changePos=true&up=true&pos=");
      out.print( rule.getPosition() );
      out.write("\"\n                        ><img src=\"images/arrow_up.gif\" width=\"16\" height=\"16\" alt=\"Move this router up.\" border=\"0\"></a>\n            ");
  } else { 
      out.write("\n                <img src=\"images/blank.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/>\n            ");
  } 
      out.write("\n                  <td align=\"center\">\n            <a href=\"workgroup-routing.jsp?edit=true&wgID=");
      out.print( wgID );
      out.write("&pos=");
      out.print( rule.getPosition() );
      out.write("\"\n                    ><img src=\"images/edit-16x16.gif\" width=\"16\" height=\"16\" alt=\"Edit the properties of this Router\" border=\"0\"\n                    ></a>\n        </td>\n        <td align=\"center\">\n            <a href=\"workgroup-routing.jsp?remove=true&wgID=");
      out.print( wgID );
      out.write("&pos=");
      out.print( rule.getPosition());
      out.write("\"\n                    ><img src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\" alt=\"Delete this Router\" border=\"0\"\n                    ></a>\n        </td>\n\n               </tr>\n            ");
 } 
      out.write("\n\n\n\n            ");
 if (rules.size() == 0) { 
      out.write("\n            <tr>\n                <td colspan=\"4\" align=\"center\">There are no routing rules defined for this workgroup.</td>\n            </tr>\n            ");
} 
      out.write("\n\n            </table>\n       <br/>\n\n            <fieldset>\n              <legend>Add Routing Rule</legend>\n                <table cellspacing=\"0\" cellpadding=\"3\">\n                      <form action=\"workgroup-routing.jsp\" method=\"post\">\n                  <tr>\n                      <td colspan=\"3\">\n                          Create a new routing rule. Routing rules do searches against given values of the form.<br/><br/>\n                      </td>\n                  </tr>\n                <tr>\n                <td>\n                    Form Variable:\n                </td>\n                    <td>\n                    <select name=\"variable\">\n                     ");
 for (FormField field : dataForm.getFields()) { 
      out.write("\n                          <option value=\"");
      out.print( field.getVariable());
      out.write('"');
      out.write('>');
      out.print( field.getVariable());
      out.write("</option>\n                        ");
 } 
      out.write("\n\n                    </select>\n                </td>\n                    </tr><tr>\n                 <td>Form Value:</td>\n                <td>\n                    <input type=\"text\" name=\"value\" size=\"30\"/>\n                </td>\n                  </tr><tr>\n\n                    <td>Route To Queue:</td>\n                <td>\n                    <select name=\"queueID\">\n                     ");
 for (RequestQueue queue : workgroup.getRequestQueues()) { 
      out.write("\n                            <option value=\"");
      out.print( queue.getID());
      out.write('"');
      out.write('>');
      out.print( queue.getName());
      out.write("</option>\n                        ");
 } 
      out.write("\n                    </select>\n                </td>\n                <td>\n\n                   <input type=\"submit\" name=\"submit\" value=\"Add Routing Rule\"/>\n                </td>\n            </tr>\n\n                <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\"/>\n                           </form>\n                     </table>\n   </fieldset>\n    </div>\n\n  </body>\n</html>");
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
