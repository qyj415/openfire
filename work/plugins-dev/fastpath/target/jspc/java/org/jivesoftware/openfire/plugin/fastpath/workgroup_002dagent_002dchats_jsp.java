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
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import java.text.DateFormat;
import java.util.*;
import org.jivesoftware.util.StringUtils;

public final class workgroup_002dagent_002dchats_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n<!-- Define Administration Bean -->\r\n");

    String wgID = ParamUtils.getParameter(request, "wgID");
    String agentJID = ParamUtils.getParameter(request, "agentJID");
    String roomID = ParamUtils.getParameter(request, "roomID");

    // Load the workgroup
    final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));
    Agent agent = workgroupManager.getAgentManager().getAgent(new JID(agentJID));
    AgentSession agentSession = agent.getAgentSession();
    List<AgentSession.ChatInfo> chatsInfo = new ArrayList<AgentSession.ChatInfo>(agentSession.getChatsInfo(workgroup));
    Collections.sort(chatsInfo);

    Map<Packet, java.util.Date> transcript = null;
    if (roomID != null) {
        transcript = new HashMap<Packet, java.util.Date>(workgroup.getTranscript(roomID));
    }

    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

      out.write("\r\n<html>\r\n    <head>\r\n        <title>Current chats of Agent</title>\r\n        <meta name=\"pageID\" content=\"workgroup-summary\"/>\r\n    </head>\r\n    <body>\r\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n  <tr>\r\n    <td colspan=\"8\">\r\nBelow is a list of current chats the agent <b>");
      out.print( agentJID );
      out.write("</b> is having.</td>\r\n  </tr>\r\n</table>\r\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n  <tr>\r\n    <td colspan=\"8\" class=\"text\">\r\n    Total Chats: ");
      out.print( chatsInfo.size());
      out.write("\r\n    </td>\r\n  </tr>\r\n</table>\r\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n  <thead>\r\n    <tr>\r\n      <th nowrap align=\"left\">Date</th>\r\n      <th nowrap>User ID</th>\r\n      <th nowrap>User JID</th>\r\n      <th nowrap>Room ID</th>\r\n      <th nowrap>Messages</th>\r\n    </tr>\r\n  </thead>\r\n    ");
   // Print the list of chats
    if (chatsInfo.size() == 0) {

      out.write("\r\n    <tr>\r\n      <td align=\"center\" colspan=\"8\">\r\n        <br/>Agent is not having chats at the moment\r\n      </td>\r\n    </tr>\r\n    ");

    }

    for (AgentSession.ChatInfo chatInfo : chatsInfo) {

      out.write("\r\n    <tr class=\"c1\">\r\n      <td width=\"30%\">\r\n        ");
      out.print( formatter.format(chatInfo.getDate()) );
      out.write("\r\n      </td>\r\n      <td width=\"20%\" align=\"center\">\r\n        ");
      out.print( chatInfo.getUserID());
      out.write("</td>\r\n      <td width=\"20%\" align=\"center\">\r\n        ");
      out.print( chatInfo.getUserJID().toString() );
      out.write("</td>\r\n      <td width=\"20%\" align=\"center\">\r\n        ");
      out.print( chatInfo.getSessionID() );
      out.write("\r\n      </td>\r\n      <td width=\"10%\" align=\"center\">\r\n        ");
 int count = 0;
        for (Packet packet : chatInfo.getPackets().keySet()) {
            if (packet instanceof Message) {
                count++;
            }
        }
      out.write("\r\n        <a href=\"workgroup-agent-chats.jsp?wgID=");
      out.print( workgroup.getJID().toString() );
      out.write("&agentJID=");
      out.print( agent.getAgentJID() );
      out.write("&roomID=");
      out.print( chatInfo.getSessionID());
      out.write('"');
      out.write('>');
      out.print( count );
      out.write("</a>\r\n      </td>\r\n    </tr>\r\n    ");
 } 
      out.write("\r\n  </thead>\r\n</table>\r\n\r\n\r\n");
 if (transcript != null) { 
      out.write("\r\n<br>\r\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n  <tr>\r\n    <td colspan=\"8\">Below is the chat transcript of the room <b>");
      out.print( roomID );
      out.write("</b>.</td>\r\n  </tr>\r\n</table>\r\n<table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n  <thead>\r\n    <tr>\r\n      <th nowrap align=\"left\">Date</th>\r\n      <th nowrap>Sender</th>\r\n      <th nowrap>Message</th>\r\n    </tr>\r\n  </thead>\r\n    ");
   // Print the list of chats
    if (transcript.size() == 0) {

      out.write("\r\n    <tr>\r\n      <td align=\"center\" colspan=\"8\">\r\n        <br/>No messages in the room where found\r\n      </td>\r\n    </tr>\r\n    ");
 }

    SortedMap<Date, Packet> sortedTranscript = new TreeMap<Date, Packet>();
    for (Packet packet : transcript.keySet()) {
        sortedTranscript.put(transcript.get(packet), packet);
    }

    for (Date date : sortedTranscript.keySet()) {
        Packet packet = sortedTranscript.get(date);
        if (!(packet instanceof Message)) {
            continue;
        }

      out.write("\r\n    <tr class=\"c1\">\r\n      <td width=\"20%\">\r\n        ");
      out.print( formatter.format(date) );
      out.write("\r\n      </td>\r\n      <td width=\"10%\" align=\"center\">\r\n        ");
      out.print( StringUtils.escapeForXML(packet.getFrom().getResource()) );
      out.write("</td>\r\n      <td width=\"70%\" align=\"center\">\r\n        ");
      out.print( StringUtils.escapeForXML(((Message)packet).getBody()) );
      out.write("</td>\r\n    </tr>\r\n    ");
 } 
      out.write("\r\n  </thead>\r\n</table>\r\n\r\n");
 } 
      out.write("\r\n</body>\r\n</html>\r\n");
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
