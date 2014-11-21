package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.Agent;
import org.jivesoftware.xmpp.workgroup.AgentSession;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.packet.JID;
import org.xmpp.packet.Presence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class workgroup_002dagents_002dstatus_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			"/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n");

    String wgID = ParamUtils.getParameter(request, "wgID");
    int start = ParamUtils.getIntParameter(request, "start", 0);
    int range = ParamUtils.getIntParameter(request, "range", 15);
    String filter = ParamUtils.getParameter(request, "filter");
    if (filter == null) {
        filter = "all";
    }

    // Load the workgroup
    final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));

    Collection<Agent> agents = new ArrayList<Agent>(workgroup.getAgents());
    if ("connected".equals(filter)) {
        for (Agent agent : agents) {
            agent.getAgentSession();
        }
    }
    else if ("available".equals(filter)) {
        for (Iterator<Agent> it=agents.iterator(); it.hasNext();) {
            AgentSession agentSession = it.next().getAgentSession();
            if (!agentSession.isAvailableToChat()) {
                it.remove();
            }
        }
    }

    int numPages = (int)Math.ceil((double)agents.size()/(double)range);
    int curPage = (start/range) + 1;

      out.write("\r\n<html>\r\n    <head>\r\n        <title>Status of Agents</title>\r\n        <meta name=\"pageID\" content=\"workgroup-summary\"/>\r\n    </head>\r\n    <body>\r\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n  <tr>\r\n    <td colspan=\"8\">\r\nBelow is a list of agents related to the workgroup <a href=\"workgroup-properties.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\">\r\n");
      out.print(  workgroup.getJID().getNode() );
      out.write("</a>. The list includes the status of the agent and the number of chats that the agent is having at the moment.</td>\r\n  </tr>\r\n</table>\r\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n  <tr>\r\n    <td colspan=\"8\" class=\"text\">\r\n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n    <tr>\r\n        <td>Total Agents: <a href=\"workgroup-agents-status.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\">\r\n            ");
 if ("all".equals(filter)) { 
      out.write("\r\n                <b>");
      out.print( workgroup.getAgents().size());
      out.write("</b>\r\n            ");
 } else { 
      out.write("\r\n               ");
      out.print( workgroup.getAgents().size());
      out.write("\r\n            ");
 } 
      out.write("</a>\r\n        </td>\r\n        <td>Connected: <a href=\"workgroup-agents-status.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("&filter=connected\">\r\n            ");
 if ("connected".equals(filter)) { 
      out.write("\r\n                <b>");
      out.print( workgroup.getAgentSessions().size());
      out.write("</b>\r\n            ");
 } else { 
      out.write("\r\n                ");
      out.print( workgroup.getAgentSessions().size());
      out.write("\r\n            ");
 } 
      out.write("</a>\r\n        </td>\r\n        <td>Available: <a href=\"workgroup-agents-status.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("&filter=available\">\r\n            ");
 if ("available".equals(filter)) { 
      out.write("\r\n                <b>");
      out.print( workgroup.getAgentAvailableSessions().size());
      out.write("</b>\r\n            ");
 } else { 
      out.write("\r\n                ");
      out.print( workgroup.getAgentAvailableSessions().size());
      out.write("\r\n            ");
 } 
      out.write("</a>\r\n        </td>\r\n    </tr>\r\n    ");
  if (numPages > 1) { 
      out.write("\r\n    <tr>\r\n        Showing:\r\n        [\r\n        ");
  for (int i=0; i<numPages; i++) {
                String sep = ((i+1)<numPages) ? " " : "";
                boolean isCurrent = (i+1) == curPage;
        
      out.write("\r\n            <a href=\"workgroup-agents-status.jsp?start=");
      out.print( (i*range) );
      out.write("&wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\"\r\n             class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\"\r\n             >");
      out.print( (i+1) );
      out.write("</a>");
      out.print( sep );
      out.write("\r\n\r\n        ");
  } 
      out.write("\r\n        ]\r\n    </tr>\r\n    ");
  } 
      out.write("\r\n    </table>\r\n    </td>\r\n  </tr>\r\n</table>\r\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n  <thead>\r\n    <tr>\r\n      <th nowrap align=\"left\" colspan=\"2\">Agent</th>\r\n      <th nowrap align=\"left\">Nickname</th>\r\n      <th nowrap colspan=\"2\">Status</th>\r\n      <th nowrap>Current chats</th>\r\n      <th nowrap>Max chats</th>\r\n    </tr>\r\n  </thead>\r\n    ");
   // Print the list of agents
    if (agents.size() == 0) {


      out.write("\r\n    <tr>\r\n      <td align=\"center\" colspan=\"8\">\r\n        <br/>No agents found\r\n      </td>\r\n    </tr>\r\n    ");


    }

    int i = start;

    int stop = i + range;

    int counter = 0;
    for (Agent agent : agents) {
        AgentSession agentSession = agent.getAgentSession();

        counter++;
        if(counter < i){
            continue;
        }

        if(counter == stop){
            break;
        }
        i++;


      out.write("\r\n    <tr class=\"c1\">\r\n      <td width=\"1%\">\r\n        ");
      out.print(  i );
      out.write(".\r\n      </td>\r\n      <td>\r\n        ");
      out.print(  agent.getAgentJID() );
      out.write("\r\n      </td>\r\n      <td>\r\n       ");
   if (agent.getNickname() != null) { 
      out.write("\r\n          ");
      out.print(  agent.getNickname() );
      out.write("\r\n        ");
   } 
      out.write("\r\n      </td>\r\n        ");
 if (agentSession == null) { 
      out.write("\r\n        <td width=\"1%\"\r\n            ><img src=\"images/bullet-clear-16x16.png\" border=\"0\" title=\"Not Available\" alt=\"\"\r\n            ></td>\r\n        <td width=\"46%\">\r\n            Not Available\r\n        </td>\r\n        ");

        } else {
            Presence.Show _show = agentSession.getPresence().getShow();
            String _stat = agentSession.getPresence().getStatus();
            if (_show == Presence.Show.away) {
        
      out.write("\r\n        <td width=\"1%\"\r\n            ><img src=\"images/bullet-yellow-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Away\" alt=\"\"\r\n            ></td>\r\n        <td width=\"46%\">\r\n            ");
  if (_stat != null) { 
      out.write("\r\n\r\n                ");
      out.print( _stat );
      out.write("\r\n\r\n            ");
  } else { 
      out.write("\r\n\r\n                Away\r\n\r\n            ");
  } 
      out.write("\r\n        </td>\r\n\r\n    ");
  } else if (_show == Presence.Show.chat) { 
      out.write("\r\n\r\n        <td width=\"1%\"\r\n            ><img src=\"images/bullet-green-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Available to Chat\" alt=\"\"\r\n            ></td>\r\n        <td width=\"46%\">\r\n            Available to Chat\r\n        </td>\r\n\r\n    ");
  } else if (_show == Presence.Show.dnd) { 
      out.write("\r\n\r\n        <td width=\"1%\"\r\n            ><img src=\"images/bullet-red-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Do Not Disturb\" alt=\"\"\r\n            ></td>\r\n        <td width=\"46%\">\r\n            ");
  if (_stat != null) { 
      out.write("\r\n\r\n                ");
      out.print( _stat );
      out.write("\r\n\r\n            ");
  } else { 
      out.write("\r\n\r\n                Do Not Disturb\r\n\r\n            ");
  } 
      out.write("\r\n        </td>\r\n\r\n    ");
  } else if (_show == null) { 
      out.write("\r\n\r\n        <td width=\"1%\"\r\n            ><img src=\"images/bullet-green-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Online\" alt=\"\"\r\n            ></td>\r\n        <td width=\"46%\">\r\n            Online\r\n        </td>\r\n\r\n    ");
  } else if (_show == Presence.Show.xa) { 
      out.write("\r\n\r\n        <td width=\"1%\"\r\n            ><img src=\"images/bullet-yellow-14x14.gif\" width=\"14\" height=\"14\" border=\"0\" title=\"Extended Away\" alt=\"\"\r\n            ></td>\r\n        <td width=\"46%\">\r\n            ");
  if (_stat != null) { 
      out.write("\r\n\r\n                ");
      out.print( _stat );
      out.write("\r\n\r\n            ");
  } else { 
      out.write("\r\n\r\n                Extended Away\r\n\r\n            ");
  } 
      out.write("\r\n        </td>\r\n\r\n    ");
  } else { 
      out.write("\r\n\r\n        <td colspan=\"2\" width=\"46%\">\r\n            Unknown/Not Recognized\r\n        </td>\r\n\r\n    ");
  } } 
      out.write("\r\n      <td width=\"10%\" align=\"center\">\r\n        ");
 if (agentSession == null) { 
      out.write("\r\n            0\r\n        ");
 } else { 
      out.write("\r\n            <a href=\"workgroup-agent-chats.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("&agentJID=");
      out.print( agent.getAgentJID() );
      out.write('"');
      out.write('>');
      out.print(  agentSession.getCurrentChats(workgroup) );
      out.write("</a>\r\n        ");
 } 
      out.write("\r\n      </td>\r\n      <td width=\"10%\" align=\"center\">\r\n        ");
 if (agentSession != null) { 
      out.write("\r\n            ");
      out.print(  agentSession.getMaxChats(workgroup) );
      out.write("\r\n        ");
 } else { 
      out.write("\r\n            ");
      out.print(  workgroup.getMaxChats() );
      out.write("\r\n        ");
 } 
      out.write("\r\n      </td>\r\n    </tr>\r\n    ");
 } 
      out.write("\r\n</table>\r\n");
  if (numPages > 1) { 
      out.write("\r\n\r\n    <p>\r\n    Pages:\r\n    [\r\n    ");
  for (i=0; i<numPages; i++) {
            String sep = ((i+1)<numPages) ? " " : "";
            boolean isCurrent = (i+1) == curPage;
    
      out.write("\r\n        <a href=\"workgroup-agents-status.jsp?start=");
      out.print( (i*range) );
      out.write("&wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("\"\r\n         class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\"\r\n         >");
      out.print( (i+1) );
      out.write("</a>");
      out.print( sep );
      out.write("\r\n\r\n    ");
  } 
      out.write("\r\n    ]\r\n    </p>\r\n\r\n");
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
}
