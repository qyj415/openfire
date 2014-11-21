package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.Agent;
import org.jivesoftware.xmpp.workgroup.AgentManager;
import org.jivesoftware.xmpp.workgroup.RequestQueue;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupAdminManager;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.dispatcher.DispatcherInfo;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.group.Group;
import org.jivesoftware.openfire.group.GroupManager;
import org.xmpp.component.ComponentManagerFactory;
import org.xmpp.packet.JID;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.jivesoftware.xmpp.workgroup.AgentNotFoundException;
import org.jivesoftware.util.Log;
import org.jivesoftware.openfire.group.GroupNotFoundException;
import org.jivesoftware.openfire.user.UserManager;

public final class workgroup_002dqueue_002dagents_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\n');
      out.write("\n\n\n\n\n\n");

    // Get parameters //
    String wgID = ParamUtils.getParameter(request, "wgID");
    long queueID = ParamUtils.getLongParameter(request, "qID", -1L);
    String agentGroups = ParamUtils.getParameter(request, "agentGroups");
    String agents = ParamUtils.getParameter(request, "agents");
    String success = ParamUtils.getParameter(request, "success");


    boolean addAgents = request.getParameter("addAgents") != null;
    boolean addGroups = request.getParameter("addGroups") != null;
    boolean removeAgents = request.getParameter("removeUsers") != null;
    boolean removeGroups = request.getParameter("removeGroup") != null;

    WorkgroupManager wgManager = WorkgroupManager.getInstance();
    WorkgroupAdminManager adminManager = new WorkgroupAdminManager();
    GroupManager groupManager = GroupManager.getInstance();

    // Load the workgroup
    Workgroup workgroup = wgManager.getWorkgroup(new JID(wgID));
    AgentManager agentManager = wgManager.getAgentManager();
    // Load the request queue:
    RequestQueue queue = workgroup.getRequestQueue(queueID);
    //AgentSelector newSelector = null;

    DispatcherInfo infos = queue.getDispatcher().getDispatcherInfo();
    long requestTimeout = infos.getRequestTimeout() / 1000;
    long offerTimeout = infos.getOfferTimeout() / 1000;

    String successMessage = null;

      out.write("\n\n\n\n\n");



    Map errors = new HashMap();

    if(addGroups && agentGroups == null){
        errors.put("groups", "Please specify a valid group name.");
    }



    if(addGroups && errors.size() == 0){
        StringTokenizer tokenizer = new StringTokenizer(agentGroups, ",\t\n\r\f");
        while (tokenizer.hasMoreTokens()) {
            String tok = tokenizer.nextToken().trim();
            try {
                Group group = groupManager.getGroup(tok);
                queue.addGroup(group);
            }
            catch (Exception e) {
                errors.put("groups", "Group not found.");
                Log.error(e);
            }
        }

        successMessage = "Group(s) have been added to queue.";
    }
    else if (addAgents && errors.size() == 0) {
        if (agents != null) {
            // loop thru all params
            StringTokenizer tokenizer = new StringTokenizer(agents, ", \t\n\r\f");
            while (tokenizer.hasMoreTokens()) {
                String usernameToken = tokenizer.nextToken();
                if (usernameToken.indexOf('@') != -1) {
                    usernameToken = JID.escapeNode(usernameToken);
                }
                try {
                    // See if they are a user in the system.
                    UserManager.getInstance().getUser(usernameToken);
                    usernameToken += ("@" + ComponentManagerFactory.getComponentManager().getServerName());
                    JID address = new JID(usernameToken.trim());
                    Agent agent = null;

                    if (agentManager.hasAgent(address)) {
                        agent = agentManager.getAgent(address);
                    }
                    else {
                        agent = agentManager.createAgent(address);
                    }
                    queue.addMember(agent);
                }
                catch (Exception e) {
                    if (!errors.containsKey("agents")) {
                        errors.put("agents", new LinkedList());
                    }
                    List list = (List)errors.get("agents");
                    list.add(usernameToken);
                }

                successMessage = "Agent(s) have been added to the queue.";
            }

        }

        if(errors.size() == 0){
        response.sendRedirect("workgroup-queue-agents.jsp?success=true&wgID=" + wgID + "&qID=" + queue.getID());
        return;
        }
    }

    if(removeAgents){
        String[] agentID = request.getParameterValues("remove");
        final int no = agentID != null ? agentID.length : 0;
        for(int i=0; i<no; i++){
            try {
                long id = Long.parseLong(agentID[i]);
                Agent agent = agentManager.getAgent(id);
                queue.removeMember(agent);
            }
            catch (AgentNotFoundException e1) {
                Log.error(e1);
            }
        }

       successMessage = "Agent(s) have been removed from the queue.";
    }

    if(removeGroups){
        String[] groups = request.getParameterValues("groupRemove");
        final int no = groups != null ? groups.length : 0;
        for (int i = 0; i < no; i++) {
            try {
                String groupName = groups[i];
                Group group = groupManager.getGroup(groupName);
                queue.removeGroup(group);
            }
            catch (GroupNotFoundException e1) {
                Log.error(e1);
            }
        }

        successMessage = "Group(s) have been removed from the queue.";
    }

    RequestQueue backupQueue = null;

    if (errors.size() == 0) {
        DispatcherInfo dispatcherInfo = queue.getDispatcher().getDispatcherInfo();
        agentGroups = "";


        for (Iterator iter = queue.getGroups().iterator(); iter.hasNext();) {
            Group ag = (Group)iter.next();
            String sep = (iter.hasNext() ? ", " : "");
            agentGroups += (ag.getName() + sep);
        }
        agents = "";

        for (Iterator iter = queue.getMembers().iterator(); iter.hasNext();) {
            Agent a = (Agent)iter.next();
            String sep = (iter.hasNext() ? ", " : "");
            agents += (a.getAgentJID().toString() + sep);
        }
    }

    String overFlowDescription = "";
    RequestQueue.OverflowType overflowType = queue.getOverflowType();
    if (overflowType == RequestQueue.OverflowType.OVERFLOW_BACKUP) {
        overFlowDescription = "Overflow to " + queue.getBackupQueue().getName();
    }
    else if (overflowType == RequestQueue.OverflowType.OVERFLOW_NONE) {
        overFlowDescription = "No Overflow.";
    }
    else {
        overFlowDescription = "Overflow to random queue.";
    }

      out.write("\n\n\n<html>\n    <head>\n        <title>Manage Queue - ");
      out.print( queue.getName());
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-queues\"/>\n        <meta name=\"extraParams\" content=\"wgID=");
      out.print( wgID );
      out.write("\"/>\n        <!--<meta name=\"helpPage\" content=\"edit_queue_properties.html\"/>-->\n\n        <script language=\"JavaScript\" type=\"text/javascript\">\n        function openWin(el) {\n            var win = window.open('user-browser.jsp?formName=f&elName=agents', 'newWin', 'width=500,height=550,menubar=yes,location=no,personalbar=no,scrollbars=yes,resize=yes');\n        }\n\n        function openAgentGroupWindow(el) {\n            var agentwin = window.open('agent-group-browser.jsp?formName=f&elName=agentGroups', 'newWin', 'width=500,height=550,menubar=yes,location=no,personalbar=no,scrollbars=yes,resize=yes');\n\n        }\n        </script>\n    </head>\n    <body>\n\n    <p>\n        <b>Description:&nbsp;</b>");
      out.print( queue.getDescription() != null ? queue.getDescription() : "");
      out.write("\n    </p>\n<p>\n    Use the form below to add agents and/or groups to this queue. All users specified will be marked as agents that are\n    able to take incoming chat requests to this queue.\n</p>\n<p>\n<a href=\"workgroup-queues.jsp?wgID=");
      out.print( wgID );
      out.write("\"\n        >&laquo; Back to list of queues in workgroup.</a>\n</p>\n\n\n");
 if (successMessage != null && errors.size() == 0) { 
      out.write("\n<div class=\"success\">\n    ");
      out.print( successMessage);
      out.write("\n</div>\n<br/>\n");
 } 
      out.write('\n');
  if (errors.size() > 0) { 
      out.write("\n\n    <div class=\"error\">\n    Please fix the errors below.\n    </div>\n\n");
  } 
      out.write("\n\n <table width=\"100%\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n    <th colspan=\"2\">Queue Settings</th>\n     <tr>\n         <td width=\"1%\" nowrap>Offer Timeout:</td>\n         <td>");
      out.print( offerTimeout);
      out.write(" seconds</td>\n     </tr>\n     <tr>\n         <td width=\"1%\" nowrap>Request Timeout:</td>\n         <td>");
      out.print( requestTimeout);
      out.write(" seconds.</td>\n     </tr>\n     <tr>\n         <td width=\"1%\" nowrap>Overflow Policy:</td>\n         <td>");
      out.print( overFlowDescription);
      out.write("</td>\n     </tr>\n     <tr>\n         <td colspan=\"2\"><input type=\"button\" name=\"edit\" value=\"Edit Settings\" onclick=\"window.location.href='workgroup-queue-manage.jsp?wgID=");
      out.print(wgID);
      out.write("&qID=");
      out.print( queueID);
      out.write("'\"/></td>\n     </tr>\n</table>\n\n<br/>\n\n\n<form action=\"workgroup-queue-agents.jsp\" method=\"post\" name=\"f\">\n<input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\">\n<input type=\"hidden\" name=\"qID\" value=\"");
      out.print( queueID );
      out.write("\">\n\n\n\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n<tr valign=\"top\">\n    <td class=\"c1\">\n        <b>Add Agent(s):</b>\n        ");
  if (errors.get("agents") != null) { 
      out.write("\n            &nbsp;\n            <span class=\"jive-error-text\">\n            Agent not found.\n            </span>\n        ");
  } 
      out.write("\n        <br/>\n    </td>\n    <td>\n        <input type=\"text\" name=\"agents\" size=\"40\"/>&nbsp;<input type=\"submit\" name=\"addAgents\" value=\"Add\"/>\n\n    </td>\n\n    <td>\n\n                <a href=\"#\" onclick=\"openWin(document.f.agents);return false;\"\n                   title=\"Click to browse available agents...\"\n                        >Browse...</a>\n\n    </td>\n\n</table>\n\n<!-- List Agents -->\n    <table width=\"100%\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n       <tr>\n            <th>Username</th><th>Remove</th>\n       </tr>\n        ");
 for(Agent agent : queue.getMembers()){ 
      out.write("\n            <tr>\n                <td>");
      out.print( JID.unescapeNode(agent.getAgentJID().getNode()));
      out.write("</td>\n                <td><input type=\"checkbox\" name=\"remove\" value=\"");
      out.print( agent.getID());
      out.write("\"></td>\n            </tr>\n        ");
 } 
      out.write("\n        <tr><td>&nbsp;</td><td><input type=\"submit\" name=\"removeUsers\" value=\"Remove\" /></td></tr>\n\n     </table>\n    <br/>\n     <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n        <tr valign=\"top\">\n             <td class=\"c1\">\n        <b>Add Groups(s):</b>\n            ");
  if (errors.get("groups") != null) { 
      out.write("\n\n            <br/>\n            <span class=\"jive-error-text\">\n            ");
      out.print( errors.get("groups") );
      out.write("\n            </span>\n\n        ");
  } 
      out.write("\n            </td>\n            <td>\n                <input type=\"text\" name=\"agentGroups\" size=\"40\">&nbsp;\n                <input type=\"submit\" name=\"addGroups\" value=\"Add\"/>\n            </td>\n             <td nowrap valign=\"top\">\n                <a href=\"#\" onclick=\"openAgentGroupWindow(document.f.agentGroups);return false;\"\n                   title=\"Click to browse available agent groups...\"\n                        >Browse...</a>\n            </td>\n        </tr>\n        </table>\n<!-- List  Groups-->\n    <table width=\"100%\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n       <tr>\n            <th>Group</th><th>Remove</th>\n       </tr>\n        ");

            boolean hasGroup = false;
            for(Group group : queue.getGroups()){
            hasGroup = true;
        
      out.write("\n            <tr>\n                <td>");
      out.print( group.getName() );
      out.write("</td>\n                <td><input type=\"checkbox\" name=\"groupRemove\" value=\"");
      out.print( group.getName());
      out.write("\"></td>\n            </tr>\n        ");
 } 
      out.write("\n        ");
 if(hasGroup){ 
      out.write("\n        <tr><td>&nbsp;</td><td><input type=\"submit\" name=\"removeGroup\" value=\"Remove\" /></td></tr>\n        ");
 } 
      out.write("\n     </table>\n\n</form>\n\n<script type=\"text/javascript\">\n    document.f.agents.focus();\n</script>\n</body>\n</html>");
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
