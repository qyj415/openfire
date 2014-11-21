package org.jivesoftware.openfire.plugin.kraken;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.SessionManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.cluster.ClusterManager;
import org.jivesoftware.openfire.cluster.ClusterNodeInfo;
import org.jivesoftware.openfire.cluster.NodeID;
import net.sf.kraken.KrakenPlugin;
import net.sf.kraken.registration.Registration;
import net.sf.kraken.registration.RegistrationManager;
import net.sf.kraken.session.cluster.TransportSessionRouter;
import org.jivesoftware.openfire.session.ClientSession;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.packet.JID;
import org.xmpp.packet.Presence;
import java.util.*;
import net.sf.kraken.TransportInstance;
import net.sf.kraken.session.TransportSession;
import net.sf.kraken.BaseTransport;

public final class kraken_002dregistrations_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_param_value_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_fmt_message_key_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_fmt_message_key = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_fmt_param_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_fmt_message_key_nobody.release();
    _jspx_tagPool_fmt_message_key.release();
    _jspx_tagPool_fmt_param_value_nobody.release();
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
      			"error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write("\r\n\r\n");

    final KrakenPlugin plugin =
            (KrakenPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("kraken");
    HashMap<String, Boolean> trEnabled = new HashMap<String, Boolean>();
    trEnabled.put("aim", plugin.getTransportInstance("aim").isEnabled());
    trEnabled.put("facebook", plugin.getTransportInstance("facebook").isEnabled());
    trEnabled.put("gadugadu", plugin.getTransportInstance("gadugadu").isEnabled());
    trEnabled.put("gtalk", plugin.getTransportInstance("gtalk").isEnabled());
    trEnabled.put("icq", plugin.getTransportInstance("icq").isEnabled());
    trEnabled.put("irc", plugin.getTransportInstance("irc").isEnabled());
    trEnabled.put("livejournal", plugin.getTransportInstance("livejournal").isEnabled());
    trEnabled.put("msn", plugin.getTransportInstance("msn").isEnabled());
    trEnabled.put("myspaceim", plugin.getTransportInstance("myspaceim").isEnabled());
    trEnabled.put("qq", plugin.getTransportInstance("qq").isEnabled());
    trEnabled.put("renren", plugin.getTransportInstance("renren").isEnabled());
    trEnabled.put("sametime", plugin.getTransportInstance("sametime").isEnabled());
    trEnabled.put("simple", plugin.getTransportInstance("simple").isEnabled());
    trEnabled.put("xmpp", plugin.getTransportInstance("xmpp").isEnabled());
    trEnabled.put("yahoo", plugin.getTransportInstance("yahoo").isEnabled());

    webManager.init(request, response, session, application, out);

    // Get the user manager
    SessionManager sessionManager = webManager.getSessionManager();

    // Lets gather what information we are going to display
    class regResult {
        public JID jid = null;
        public long id = -1;
        public String type = null;
        public String username = null;
        public String nickname = null;
        public String status = "unavailable";
        public String linestatus = "offline";
        public String lastLogin = null;
        public String clusterNode = null;
        public boolean sessionActive = false;
    }
    Collection<regResult> regResults = new ArrayList<regResult>();

    HashMap<String, String> filtervars = new HashMap<String, String>();
    if (ParamUtils.getParameter(request, "filterUser") != null) {
        filtervars.put("filterUser", ParamUtils.getParameter(request, "filterUser"));
    }
    if (ParamUtils.getParameter(request, "filterLegacyUsername") != null) {
        filtervars.put("filterLegacyUsername", ParamUtils.getParameter(request, "filterLegacyUsername"));
    }
    ArrayList<String> filteropts = new ArrayList<String>();
    if (ParamUtils.getParameter(request, "filter[]") != null) {
        String[] optlist = ParamUtils.getParameters(request, "filter[]");
        filteropts.addAll(Arrays.asList(optlist));
    } else if (webManager.getPageProperty("kraken-registrations", "filterSET", 0) != 0) {
        if (webManager.getPageProperty("kraken-registrations", "filterAIM", 0) != 0) {
            filteropts.add("aim");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterFACEBOOK", 0) != 0) {
            filteropts.add("facebook");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterGADUGADU", 0) != 0) {
            filteropts.add("gadugadu");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterGTALK", 0) != 0) {
            filteropts.add("gtalk");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterICQ", 0) != 0) {
            filteropts.add("icq");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterIRC", 0) != 0) {
            filteropts.add("irc");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterLIVEJOURNAL", 0) != 0) {
            filteropts.add("livejournal");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterMSN", 0) != 0) {
            filteropts.add("msn");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterMYSPACEIM", 0) != 0) {
            filteropts.add("myspaceim");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterQQ", 0) != 0) {
            filteropts.add("qq");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterRENREN", 0) != 0) {
            filteropts.add("renren");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterSAMETIME", 0) != 0) {
            filteropts.add("sametime");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterSIMPLE", 0) != 0) {
            filteropts.add("simple");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterXMPP", 0) != 0) {
            filteropts.add("xmpp");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterYAHOO", 0) != 0) {
            filteropts.add("yahoo");
        }
        if (webManager.getPageProperty("kraken-registrations", "filterSIGNEDON", 0) != 0) {
            filteropts.add("signedon");
        }
    } else {
        filteropts.add("aim");
        filteropts.add("facebook");
        filteropts.add("gadugadu");
        filteropts.add("gtalk");
        filteropts.add("icq");
        filteropts.add("irc");
        filteropts.add("livejournal");
        filteropts.add("msn");
        filteropts.add("myspaceim");
        filteropts.add("qq");
        filteropts.add("renren");
        filteropts.add("sametime");
        filteropts.add("simple");
        filteropts.add("xmpp");
        filteropts.add("yahoo");
    }

    webManager.setPageProperty("kraken-registrations", "filterSET", 1);
    webManager.setPageProperty("kraken-registrations", "filterAIM", filteropts.contains("aim") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterFACEBOOK", filteropts.contains("facebook") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterGADUGADU", filteropts.contains("gadugadu") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterGTALK", filteropts.contains("gtalk") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterICQ", filteropts.contains("icq") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterIRC", filteropts.contains("irc") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterLIVEJOURNAL", filteropts.contains("livejournal") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterMSN", filteropts.contains("msn") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterMYSPACEIM", filteropts.contains("myspaceim") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterQQ", filteropts.contains("qq") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterRENREN", filteropts.contains("renren") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterSAMETIME", filteropts.contains("sametime") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterSIMPLE", filteropts.contains("simple") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterXMPP", filteropts.contains("xmpp") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterYAHOO", filteropts.contains("yahoo") ? 1 : 0);
    webManager.setPageProperty("kraken-registrations", "filterSIGNEDON", filteropts.contains("signedon") ? 1 : 0);

    TransportSessionRouter sessionRouter = plugin.getSessionRouter();
    byte[] thisNode = XMPPServer.getInstance().getNodeID().toByteArray();
    Map<NodeID,String> nodeIDToName = new HashMap<NodeID,String>();
    if (ClusterManager.isClusteringStarted()) {
        Collection<ClusterNodeInfo> clusterNodesInfo = ClusterManager.getNodesInfo();
        if (clusterNodesInfo != null && !clusterNodesInfo.isEmpty()) {
            for (ClusterNodeInfo nodeInfo : clusterNodesInfo) {
                nodeIDToName.put(nodeInfo.getNodeID(), nodeInfo.getHostName());
            }
        }
    }
    int resCount = 0;
    if (filteropts.contains("signedon")) {
        for (String transportName : plugin.getTransports()) {
            if (filteropts.contains(transportName)) {
                TransportInstance trInstance = plugin.getTransportInstance(transportName);
                if (trInstance != null) {
                    BaseTransport transport = trInstance.getTransport();
                    if (transport != null) {
                        Collection<TransportSession> trSessions =  transport.getSessionManager().getSessions();
                        for (TransportSession trSession : trSessions) {
                            Registration registration = trSession.getRegistration();
                            regResult res = new regResult();
                            res.id = registration.getRegistrationID();
                            res.jid = registration.getJID();
                            if (filtervars.containsKey("filterUser") && !(filtervars.get("filterUser").equalsIgnoreCase(res.jid.toString()) || filtervars.get("filterUser").equalsIgnoreCase(res.jid.getNode()))) {
                                continue;
                            }
                            if (filtervars.containsKey("filterLegacyUsername") && !filtervars.get("filterLegacyUsername").equalsIgnoreCase(res.username)) {
                                continue;
                            }
                            res.nickname = registration.getNickname();
                            res.type = registration.getTransportType().toString();
                            res.sessionActive = trSession.isLoggedIn();
                            res.status = trSession.getPresence().toString();
                            res.linestatus = "online";

                            Date lastLogin = registration.getLastLogin();
                            res.lastLogin = ((lastLogin != null) ? lastLogin.toString() : "<i>" + LocaleUtils.getLocalizedString("gateway.web.registrations.never", "kraken") + "</i>");                            res.username = registration.getUsername();
                            resCount++;
                            regResults.add(res);
                        }
                    }
                }
            }
        }
    } else {

        Collection<Registration> registrations = RegistrationManager.getInstance().getRegistrations();

        for (Registration registration : registrations) {
            regResult res = new regResult();
            res.id = registration.getRegistrationID();
            res.jid = registration.getJID();
            if (filtervars.containsKey("filterUser") && !(filtervars.get("filterUser").equalsIgnoreCase(res.jid.toString()) || filtervars.get("filterUser").equalsIgnoreCase(res.jid.getNode()))) {
                continue;
            }
            res.username = registration.getUsername();
            if (filtervars.containsKey("filterLegacyUsername") && !filtervars.get("filterLegacyUsername").equalsIgnoreCase(res.username)) {
                continue;
            }
            res.nickname = registration.getNickname();
            res.type = registration.getTransportType().toString();
            if (!filteropts.contains(res.type)) {
                continue;
            }
            res.sessionActive = false;
            byte[] nodeID = sessionRouter.getSession(res.type, res.jid.toBareJID());
            if (nodeID != null) {
                if (Arrays.equals(thisNode, nodeID)) {
                    res.clusterNode = LocaleUtils.getLocalizedString("gateway.web.registrations.local", "kraken");
                }
                else {
                    res.clusterNode = nodeIDToName.get(NodeID.getInstance(nodeID));
                }
                res.sessionActive = true;
            }

            if (!res.sessionActive && filteropts.contains("signedon")) {
                continue;
            }

            try {
                ClientSession clientSession = (ClientSession) sessionManager.getSessions(res.jid.getNode()).toArray()[0];
                if (clientSession != null) {
                    Presence presence = clientSession.getPresence();
                    if (presence == null) {
                        // not logged in, leave alone
                    } else if (presence.getShow() == Presence.Show.xa) {
                        res.status = "away";
                        res.linestatus = "online";
                    } else if (presence.getShow() == Presence.Show.away) {
                        res.status = "away";
                        res.linestatus = "online";
                    } else if (presence.getShow() == Presence.Show.chat) {
                        res.status = "free_chat";
                        res.linestatus = "online";
                    } else if (presence.getShow() == Presence.Show.dnd) {
                        res.status = "dnd";
                        res.linestatus = "online";
                    } else if (presence.isAvailable()) {
                        res.status = "available";
                        res.linestatus = "online";
                    }
                }
            }
            catch (Exception e) {
            }

            if (res.linestatus.equals("offline") && filteropts.contains("signedon")) {
                continue;
            }

            Date lastLogin = registration.getLastLogin();
            res.lastLogin = ((lastLogin != null) ? lastLogin.toString() : "<i>" + LocaleUtils.getLocalizedString("gateway.web.registrations.never", "kraken") + "</i>");

            resCount++;
            regResults.add(res);
        }
    }

    final int DEFAULT_RANGE = 15;
    final int[] RANGE_PRESETS = {15, 30, 50, 100};

    int start = ParamUtils.getIntParameter(request, "start", 0);
    int range = ParamUtils.getIntParameter(request, "range", webManager.getRowsPerPage("kraken-registrations", DEFAULT_RANGE));

    if (request.getParameter("range") != null) {
        webManager.setRowsPerPage("kraken-registrations", range);
    }

    // paginator vars
    int numPages = (int) Math.ceil((double) resCount / (double) range);
    int curPage = (start / range) + 1;

    int topRange = ((start + range) < resCount) ? (start + range) : resCount;

      out.write("\r\n\r\n\r\n\r\n\r\n<html>\r\n\r\n<head>\r\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n<meta name=\"pageID\" content=\"kraken-registrations\">\r\n<style type=\"text/css\">\r\n<!--\t@import url(\"style/kraken.css\");    -->\r\n</style>\r\n<script src=\"dwr/engine.js\" type=\"text/javascript\"></script>\r\n<script src=\"dwr/util.js\" type=\"text/javascript\"></script>\r\n<script src=\"dwr/interface/ConfigManager.js\" type=\"text/javascript\"></script>\r\n<script src=\"dwr/interface/ConnectionTester.js\" type=\"text/javascript\"></script>\r\n<script type=\"text/javascript\" >\r\n    DWREngine.setErrorHandler(handleError);\r\n    window.onerror = handleError;\r\n\r\n    function handleError(error) {\r\n        // swallow errors\r\n    }\r\n\r\n    var lastRegistrationID;\r\n\r\n\tfunction logoutSession(registrationID)\r\n\t{\r\n\t\t ConfigManager.logoutSession(registrationID, cb_logoutSession);\r\n\t}\r\n\t\r\n\tfunction cb_logoutSession(registrationID)\r\n\t{\r\n\t\tif(registrationID > 0)\r\n\t\t{\r\n\t\t\tdocument.getElementById('registrationUsername'+registrationID).parentNode.className = document.getElementById('registrationUsername'+registrationID).parentNode.className.replace(/on/g,'off');\r\n");
      out.write("\t\t\tdocument.getElementById('registrationLogoff'+registrationID).innerHTML='';\r\n\t\t}\r\n\t}\r\n\r\n    function deleteRegistration(registrationID) {\r\n        lastRegistrationID = registrationID;\r\n        ConfigManager.deleteRegistration(registrationID, cb_deleteRegistration);\r\n    }\r\n\r\n    function cb_deleteRegistration(statusMsg) {\r\n        Effect.Fade(\"jiveRegistration\"+lastRegistrationID);\r\n        document.getElementById(\"regStatusMsg\").style.display = \"\";\r\n        if (statusMsg == null) {\r\n            document.getElementById(\"regStatusMsg\").innerHTML = \"<div class='jive-success'><img src='images/success-16x16.gif' align='absmiddle' />");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("</div>\";\r\n        }\r\n        else {\r\n            document.getElementById(\"regStatusMsg\").innerHTML = \"<div class='jive-error'><img src='images/error-16x16.gif' align='absmiddle' />\"+statusMsg+\"</div>\";\r\n        }\r\n        setTimeout(\"to_statusMessage()\", 5000);\r\n    }\r\n\r\n    function updateRegistration(registrationID) {\r\n        var usernameEntry = DWRUtil.getValue(\"gatewayUsername\"+registrationID);\r\n        var passwordEntry = DWRUtil.getValue(\"gatewayPassword\"+registrationID);\r\n        if (passwordEntry == \"********\") {\r\n            passwordEntry = null;\r\n        }\r\n        var nicknameEntry = DWRUtil.getValue(\"gatewayNickname\"+registrationID);\r\n        lastRegistrationID = registrationID;\r\n        ConfigManager.updateRegistration(registrationID, usernameEntry, passwordEntry, nicknameEntry, cb_updateRegistration);\r\n    }\r\n\r\n    function cb_updateRegistration(statusMsg) {\r\n        toggleEdit(lastRegistrationID);\r\n        var usernameEntry = DWRUtil.getValue(\"gatewayUsername\"+lastRegistrationID);\r\n        document.getElementById(\"registrationUsername\"+lastRegistrationID).innerHTML = usernameEntry;\r\n");
      out.write("        document.getElementById(\"regStatusMsg\").style.display = \"\";\r\n        if (statusMsg == null) {\r\n            document.getElementById(\"regStatusMsg\").innerHTML = \"<div class='jive-success'><img src='images/success-16x16.gif' align='absmiddle' />");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</div>\";\r\n        }\r\n        else {\r\n            document.getElementById(\"regStatusMsg\").innerHTML = \"<div class='jive-error'><img src='images/error-16x16.gif' align='absmiddle' />\"+statusMsg+\"</div>\";\r\n        }\r\n        setTimeout(\"to_statusMessage()\", 5000);\r\n    }\r\n\r\n    function addRegistration() {\r\n        var userEntry = DWRUtil.getValue(\"newRegistrationUser\");\r\n        var typeEntry = DWRUtil.getValue(\"newRegistrationType\");\r\n        var legacyUsernameEntry = DWRUtil.getValue(\"newRegistrationLegacyUsername\");\r\n        var legacyPasswordEntry = DWRUtil.getValue(\"newRegistrationLegacyPassword\");\r\n        var legacyNicknameEntry = DWRUtil.getValue(\"newRegistrationLegacyNickname\");\r\n        ConfigManager.addRegistration(userEntry, typeEntry, legacyUsernameEntry, legacyPasswordEntry, legacyNicknameEntry, cb_addRegistration);\r\n    }\r\n\r\n    function cb_addRegistration(statusMsg) {\r\n        toggleAdd();\r\n        document.getElementById(\"regStatusMsg\").style.display = \"\";\r\n        if (statusMsg == null) {\r\n            document.getElementById(\"regStatusMsg\").innerHTML = \"<div class='jive-success'><img src='images/success-16x16.gif' align='absmiddle' />");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</div>\";\r\n        }\r\n        else {\r\n            document.getElementById(\"regStatusMsg\").innerHTML = \"<div class='jive-error'><img src='images/error-16x16.gif' align='absmiddle' />\"+statusMsg+\"</div>\";\r\n        }\r\n        setTimeout(\"to_statusMessage()\", 5000);\r\n    }\r\n\r\n    function to_statusMessage() {\r\n        Effect.Fade(\"regStatusMsg\");\r\n    }\r\n\r\n    function pingSession() {\r\n        ConnectionTester.pingSession();\r\n        setTimeout(\"pingSession()\", 60000); // Every minute\r\n    }\r\n\r\n    setTimeout(\"pingSession()\", 60000); // One minute after first load\r\n\r\n    /*\r\n    toggleAdd function\r\n    This is the function that shows / hides the add registration form\r\n    */\r\n    function toggleAdd(theID) {\r\n        var jiveAddRegPanel = document.getElementById(\"jiveAddRegPanel\");\r\n        var jiveAddRegButton = document.getElementById(\"jiveAddRegButton\");\r\n        var jiveAddRegLink = document.getElementById(\"jiveAddRegLink\");\r\n        if ($(jiveAddRegPanel).style.display != 'none') {\r\n            Effect.SlideUp($(jiveAddRegPanel), {duration: .2})\r\n");
      out.write("            $(jiveAddRegButton).className = \"jive-gateway-addregBtn\";\r\n            $(jiveAddRegLink).innerHTML = '");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("';\r\n        } else if ($(jiveAddRegPanel).style.display == 'none') {\r\n            Effect.SlideDown($(jiveAddRegPanel), {duration: .2})\r\n            $(jiveAddRegButton).className = \"jive-gateway-addregBtn jive-gateway-cancelAdd\";\r\n            $(jiveAddRegLink).innerHTML = '");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("';\r\n        }\r\n    }\r\n\r\n\r\n\r\n    /*\r\n    toggleEdit function\r\n    This is the function that shows / hides the edit fields for an existing registration\r\n    */\r\n    function toggleEdit(theNum) {\r\n        var normalRow = \"jiveRegistration\"+theNum;\r\n        var editRow = \"jiveRegistrationEdit\"+theNum;\r\n        if ($(editRow).style.display != 'none') {\r\n            $(editRow).className = \"jive-registrations-edit\";\r\n            $(editRow).style.display = 'none';\r\n            $(normalRow).className = \"jive-registrations-normal\";\r\n        } else if ($(editRow).style.display == 'none') {\r\n            $(normalRow).className = \"jive-registrations-normalHidden\";\r\n            $(editRow).className = \"jive-registrations-editVisible\";\r\n            Effect.Appear($(editRow), {duration: .2});\r\n        }\r\n    }\r\n</script>\r\n</head>\r\n\r\n<body>\r\n<p>");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</p>\r\n\r\n<div id=\"regStatusMsg\" style=\"display: none\"></div>\r\n\r\n<!-- BEGIN add registration -->\r\n<div class=\"jive-gateway-addregBtn\" id=\"jiveAddRegButton\">\r\n\t<a href=\"\" onClick=\"toggleAdd(); return false\" id=\"jiveAddRegLink\">");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</a>\r\n</div>\r\n<div class=\"jive-gateway-addreg\" id=\"jiveAddRegPanel\" style=\"display: none;\">\r\n\t<div class=\"jive-gateway-addregPad\">\r\n\t\t<form action=\"\" name=\"jive-addRegistration\" onSubmit=\"return false\">\r\n        <input type=\"hidden\" name=\"action\" value=\"add\" />\r\n\t\t<div class=\"jive-registrations-addJid\">\r\n\t\t\t<input type=\"text\" name=\"newRegistrationUser\" id=\"newRegistrationUser\" size=\"12\" maxlength=\"50\" value=\"\"><br>\r\n\t\t\t<strong>");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</strong>\r\n\t\t</div>\r\n\t\t<div class=\"jive-registrations-addGateway\">\r\n\t\t\t<select name=\"newRegistrationType\" id=\"newRegistrationType\" size=\"1\">\r\n\t\t\t<option value=\"0\" SELECTED> -- ");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write(" -- </option>\r\n\t\t\t");
 if (trEnabled.get("aim")) { 
      out.write(" <option value=\"aim\">");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n            ");
 if (trEnabled.get("facebook")) { 
      out.write(" <option value=\"facebook\">");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n            ");
 if (trEnabled.get("gadugadu")) { 
      out.write(" <option value=\"gadugadu\">");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n            ");
 if (trEnabled.get("gtalk")) { 
      out.write(" <option value=\"gtalk\">");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n\t\t\t");
 if (trEnabled.get("icq")) { 
      out.write(" <option value=\"icq\">");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n\t\t\t");
 if (trEnabled.get("irc")) { 
      out.write(" <option value=\"irc\">");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n            ");
 if (trEnabled.get("livejournal")) { 
      out.write(" <option value=\"livejournal\">");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n\t\t\t");
 if (trEnabled.get("msn")) { 
      out.write(" <option value=\"msn\">");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n            ");
 if (trEnabled.get("myspaceim")) { 
      out.write(" <option value=\"myspaceim\">");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n            ");
 if (trEnabled.get("qq")) { 
      out.write(" <option value=\"qq\">");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n            ");
 if (trEnabled.get("renren")) { 
      out.write(" <option value=\"renren\">");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n            ");
 if (trEnabled.get("sametime")) { 
      out.write(" <option value=\"sametime\">");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n            ");
 if (trEnabled.get("simple")) { 
      out.write(" <option value=\"simple\">");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n            ");
 if (trEnabled.get("xmpp")) { 
      out.write(" <option value=\"xmpp\">");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n\t\t\t");
 if (trEnabled.get("yahoo")) { 
      out.write(" <option value=\"yahoo\">");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("</option> ");
 } 
      out.write("\r\n\t\t\t</select><br>\r\n\t\t\t<strong>");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("</strong>\r\n\t\t</div>\r\n\t\t<div class=\"jive-registrations-addUsername\">\r\n\t\t\t<input type=\"text\" name=\"newRegistrationLegacyUsername\" id=\"newRegistrationLegacyUsername\" size=\"12\" maxlength=\"50\" value=\"\"><br>\r\n\t\t\t<strong>");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("</strong>\r\n\t\t</div>\r\n\t\t<div class=\"jive-registrations-addPassword\">\r\n\t\t\t<input type=\"password\" name=\"newRegistrationLegacyPassword\" id=\"newRegistrationLegacyPassword\" size=\"12\" maxlength=\"50\" value=\"\"><br>\r\n\t\t\t<strong>");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("</strong>\r\n\t\t</div>\r\n        <div class=\"jive-registrations-addNickname\">\r\n            <input type=\"text\" name=\"newRegistrationLegacyNickname\" id=\"newRegistrationLegacyNickname\" size=\"12\" maxlength=\"50\" value=\"\"><br>\r\n            <strong>");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("</strong>\r\n        </div>\r\n        <div class=\"jive-registrations-addButtons\">\r\n\t\t\t<input type=\"submit\" name=\"Submit\" value=\"");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("\" class=\"savechanges\" onClick=\"addRegistration(); return false\"> &nbsp;\r\n\t\t\t<input type=\"reset\" name=\"reset\" value=\"");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("\" class=\"cancel\" onClick=\"toggleAdd();\">\r\n\t\t</div>\r\n\t\t</form>\r\n\t</div>\r\n</div>\r\n<!-- END add registration -->\r\n\r\n\r\n\r\n<!-- BEGIN registrations table -->\r\n<div class=\"jive-registrations\">\r\n\r\n\r\n\t<!-- BEGIN results -->\r\n\t<div class=\"jive-registrations-results\">\r\n\t\t");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write(':');
      out.write(' ');
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_32 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_32.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_32.setParent(null);
      _jspx_th_fmt_message_32.setKey("gateway.web.registrations.xofy");
      int _jspx_eval_fmt_message_32 = _jspx_th_fmt_message_32.doStartTag();
      if (_jspx_eval_fmt_message_32 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_32 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_32.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_32.doInitBody();
        }
        do {
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_32);
          _jspx_th_fmt_param_0.setValue( "<strong>"+(start+1)+"-"+topRange+"</strong>" );
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_1 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_1.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_32);
          _jspx_th_fmt_param_1.setValue( "<strong>"+resCount+"</strong>" );
          int _jspx_eval_fmt_param_1 = _jspx_th_fmt_param_1.doStartTag();
          if (_jspx_th_fmt_param_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
          int evalDoAfterBody = _jspx_th_fmt_message_32.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_32 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_32);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_32);
      out.write("\r\n\t</div>\r\n\t<!-- END results -->\r\n\r\n\r\n\t<!-- BEGIN results size (num per page) -->\r\n\t<div class=\"jive-registrations-resultsSize\"><form action=\"kraken-registrations.jsp\" method=\"get\">\r\n\t\t<select name=\"range\" id=\"range\" size=\"1\" onchange=\"this.form.submit()\">\r\n                ");
  for (int rangePreset : RANGE_PRESETS) { 
      out.write("\r\n\r\n                    <option value=\"");
      out.print( rangePreset );
      out.write('"');
      out.print( (rangePreset== range ? "selected" : "") );
      out.write('>');
      out.print( rangePreset );
      out.write("</option>\r\n\r\n                ");
  } 
      out.write("\r\n\t\t</select>\r\n\t\t<span>");
      if (_jspx_meth_fmt_message_33(_jspx_page_context))
        return;
      out.write("</span>\r\n\t</form></div>\r\n\t<!-- END results size -->\r\n\r\n\r\n\t<!-- BEGIN pagination -->\r\n\t<div class=\"jive-registrations-pagination\">\r\n\t\t<strong>");
      if (_jspx_meth_fmt_message_34(_jspx_page_context))
        return;
      out.write(":</strong> &nbsp;\r\n            ");

                if (numPages > 1 && ((curPage) > 1)) {
            
      out.write("\r\n                    <a href=\"kraken-registrations.jsp?start=");
      out.print( ((curPage-2)*range) );
      out.write("\">&lt; ");
      if (_jspx_meth_fmt_message_35(_jspx_page_context))
        return;
      out.write("</a>\r\n            ");

                }
                for (int i=0; i<numPages; i++) {
                    boolean isCurrent = (i+1) == curPage;
                    if (isCurrent) {
            
      out.write("\r\n                        <strong>");
      out.print( (i+1) );
      out.write("</strong> \r\n            ");

                    }
                    else {
            
      out.write("\r\n                        <a href=\"kraken-registrations.jsp?start=");
      out.print( (i*range) );
      out.write('"');
      out.write('>');
      out.print( (i+1) );
      out.write("</a> \r\n            ");

                    }
                }
                if (numPages > 1 && ((curPage) < numPages)) {
            
      out.write("\r\n                    <a href=\"kraken-registrations.jsp?start=");
      out.print( (curPage*range) );
      out.write('"');
      out.write('>');
      if (_jspx_meth_fmt_message_36(_jspx_page_context))
        return;
      out.write(" &gt;</a>\r\n            ");

                }
            
      out.write("\r\n\t</div>\r\n\t<!-- END pagination -->\r\n\t\r\n\t\r\n\t<!-- BEGIN gateway filter -->\r\n\t<form action=\"kraken-registrations.jsp\" name=\"jive-filterForm\">\r\n\t<div class=\"jive-gateway-filter\" id=\"jiveGatewayFilters\">\r\n\t\t<div>\r\n            <strong>");
      if (_jspx_meth_fmt_message_37(_jspx_page_context))
        return;
      out.write(":</strong>\r\n            <div>\r\n            <label for=\"filterAIMcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"aim\" ");
      out.print( ((filteropts.contains("aim")) ? "checked" : "") );
      out.write(" id=\"filterAIMcheckbox\">\r\n                <img src=\"images/aim.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_38(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_39(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_40(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterFACEBOOKcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"facebook\" ");
      out.print( ((filteropts.contains("facebook")) ? "checked" : "") );
      out.write(" id=\"filterFACEBOOKcheckbox\">\r\n                <img src=\"images/facebook.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_41(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_42(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_43(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterGADUGADUcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"gadugadu\" ");
      out.print( ((filteropts.contains("gadugadu")) ? "checked" : "") );
      out.write(" id=\"filterGADUGADUcheckbox\">\r\n                <img src=\"images/gadugadu.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_44(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_45(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_46(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterGTALKcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"gtalk\" ");
      out.print( ((filteropts.contains("gtalk")) ? "checked" : "") );
      out.write(" id=\"filterGTALKcheckbox\">\r\n                <img src=\"images/gtalk.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_47(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_48(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_49(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterICQcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"icq\" ");
      out.print( ((filteropts.contains("icq")) ? "checked" : "") );
      out.write(" id=\"filterICQcheckbox\">\r\n                <img src=\"images/icq.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_50(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_51(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_52(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterIRCcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"irc\" ");
      out.print( ((filteropts.contains("irc")) ? "checked" : "") );
      out.write(" id=\"filterIRCcheckbox\">\r\n                <img src=\"images/irc.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_53(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_54(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_55(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterLIVEJOURNALcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"livejournal\" ");
      out.print( ((filteropts.contains("livejournal")) ? "checked" : "") );
      out.write(" id=\"filterLIVEJOURNALcheckbox\">\r\n                <img src=\"images/livejournal.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_56(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_57(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_58(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterMSNcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"msn\" ");
      out.print( ((filteropts.contains("msn")) ? "checked" : "") );
      out.write(" id=\"filterMSNcheckbox\">\r\n                <img src=\"images/msn.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_59(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_60(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_61(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <br />\r\n            <label for=\"filterMYSPACEIMcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"myspaceim\" ");
      out.print( ((filteropts.contains("myspaceim")) ? "checked" : "") );
      out.write(" id=\"filterMYSPACEIMcheckbox\">\r\n                <img src=\"images/myspaceim.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_62(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_63(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_64(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterQQcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"qq\" ");
      out.print( ((filteropts.contains("qq")) ? "checked" : "") );
      out.write(" id=\"filterQQcheckbox\">\r\n                <img src=\"images/qq.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_65(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_66(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_67(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterRENRENcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"renren\" ");
      out.print( ((filteropts.contains("renren")) ? "checked" : "") );
      out.write(" id=\"filterRENRENcheckbox\">\r\n                <img src=\"images/renren.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_68(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_69(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_70(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterSAMETIMEcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"sametime\" ");
      out.print( ((filteropts.contains("sametime")) ? "checked" : "") );
      out.write(" id=\"filterSAMETIMEcheckbox\">\r\n                <img src=\"images/sametime.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_71(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_72(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_73(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterSIMPLEcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"simple\" ");
      out.print( ((filteropts.contains("simple")) ? "checked" : "") );
      out.write(" id=\"filterSIMPLEcheckbox\">\r\n                <img src=\"images/simple.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_74(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_75(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_76(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterXMPPcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"xmpp\" ");
      out.print( ((filteropts.contains("xmpp")) ? "checked" : "") );
      out.write(" id=\"filterXMPPcheckbox\">\r\n                <img src=\"images/xmpp.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_77(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_78(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_79(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            <label for=\"filterYAHOOcheckbox\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"yahoo\" ");
      out.print( ((filteropts.contains("yahoo")) ? "checked" : "") );
      out.write(" id=\"filterYAHOOcheckbox\">\r\n                <img src=\"images/yahoo.png\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_80(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_81(_jspx_page_context))
        return;
      out.write("\"/>\r\n                <!--<span>");
      if (_jspx_meth_fmt_message_82(_jspx_page_context))
        return;
      out.write("</span>-->\r\n            </label>\r\n            </div>\r\n\t\t</div>\r\n        <div class=\"jive-gateway-filter-textentryline\">\r\n            <div class=\"jive-gateway-filter-textentry\">\r\n                <input type=\"text\" name=\"filterUser\" id=\"filterUser\" size=\"12\" maxlength=\"50\" value=\"");
      out.print( (filtervars.containsKey("filterUser") ? filtervars.get("filterUser") : "") );
      out.write("\">\r\n                <span>");
      if (_jspx_meth_fmt_message_83(_jspx_page_context))
        return;
      out.write("</span>\r\n            </div>\r\n            <div class=\"jive-gateway-filter-textentry\">\r\n                <input type=\"text\" name=\"filterLegacyUsername\" id=\"filterLegacyUsername\" size=\"12\" maxlength=\"50\" value=\"");
      out.print( (filtervars.containsKey("filterLegacyUsername") ? filtervars.get("filterLegacyUsername") : "") );
      out.write("\">\r\n                <span>");
      if (_jspx_meth_fmt_message_84(_jspx_page_context))
        return;
      out.write("</span>\r\n            </div>\r\n            <label for=\"filterActiveOnly\">\r\n                <input type=\"checkbox\" name=\"filter[]\" value=\"signedon\" ");
      out.print( ((filteropts.contains("signedon")) ? "checked" : "") );
      out.write(" id=\"filterActiveOnly\">\r\n                <span>");
      if (_jspx_meth_fmt_message_85(_jspx_page_context))
        return;
      out.write("</span>\r\n            </label>\r\n            <input type=\"submit\" name=\"submit\" value=\"");
      if (_jspx_meth_fmt_message_86(_jspx_page_context))
        return;
      out.write("\" class=\"filterBtn\">\r\n        </div>\r\n    </div>\r\n\t</form>\r\n\t<!-- END gateway filter -->\r\n\r\n\r\n    <!-- BEGIN registrations table -->\r\n\t<table cellpadding=\"0\" cellspacing=\"0\">\r\n\t<thead>\r\n\t\t<tr>\r\n\t\t\t<th width=\"20\" class=\"border-left\">&nbsp;</th>\r\n\t\t\t<th width=\"10%\">");
      if (_jspx_meth_fmt_message_87(_jspx_page_context))
        return;
      out.write("</th>\r\n            ");
 if (ClusterManager.isClusteringStarted()) { 
      out.write("\r\n            <th width=\"15%\">");
      if (_jspx_meth_fmt_message_88(_jspx_page_context))
        return;
      out.write("</th>\r\n            ");
 } 
      out.write("\r\n            <th>");
      if (_jspx_meth_fmt_message_89(_jspx_page_context))
        return;
      out.write("</th>\r\n\t\t\t<th>");
      if (_jspx_meth_fmt_message_90(_jspx_page_context))
        return;
      out.write("</th>\r\n\t\t\t<th width=\"1%\"><div align=\"center\">");
      if (_jspx_meth_fmt_message_91(_jspx_page_context))
        return;
      out.write("</div></th>\r\n\t\t\t<th width=\"1%\" class=\"border-right\">");
      if (_jspx_meth_fmt_message_92(_jspx_page_context))
        return;
      out.write("</th>\r\n\t\t</tr>\r\n\t</thead>\r\n\t<tbody>\r\n\t\t\r\n");

    int cnt = 0;
    for (regResult result : regResults) {
        cnt++;
        if (cnt < (start+1)) { continue; }
        if (cnt > (start+range)) { continue; }

      out.write("\r\n\t\t<tr id=\"jiveRegistration");
      out.print( result.id );
      out.write("\">\r\n\t\t\t<td align=\"center\">\r\n\t\t\t<img src=\"images/im_");
      out.print( result.status );
      out.write(".gif\" alt=\"");
      out.print( result.linestatus );
      out.write("\" border=\"0\"></td>\r\n\t\t\t<td>");
      out.print( result.jid.getNode() );
      out.write("</td>\r\n            ");
 if (ClusterManager.isClusteringStarted()) { 
      out.write("\r\n            <td>");
      out.print( result.clusterNode != null ? result.clusterNode : "&nbsp;" );
      out.write("</td>\r\n            ");
 } 
      out.write("\r\n            <td><span class=\"jive-gateway-");
      out.print( result.linestatus );
      out.write(" jive-gateway-");
      out.print( result.type.toUpperCase() );
      out.print( ((result.sessionActive) ? "on" : "off") );
      out.write("\"><span id=\"registrationUsername");
      out.print( result.id );
      out.write('"');
      out.write('>');
      out.print( result.username );
      out.write("</span>");
 if(result.sessionActive){ 
      out.write("<span id=\"registrationLogoff");
      out.print( result.id );
      out.write("\"> [<a href=\"javascript:noop()\" onclick=\"logoutSession(");
      out.print( result.id );
      out.write(")\">logout</a>]</span> ");
 } 
      out.write("</span></td>\r\n\t\t\t<td>");
      out.print( result.lastLogin );
      out.write("</td>\r\n\t\t\t<td align=\"center\"><a href=\"javascript:noop()\" onClick=\"");
 if (!trEnabled.get(result.type)) { 
      out.write("alert('You must enable this transport to modify registrations.'); return false;");
 } else { 
      out.write("toggleEdit(");
      out.print( result.id );
      out.write("); return false");
 } 
      out.write("\"><img src=\"images/edit-16x16.gif\" alt=\"");
      if (_jspx_meth_fmt_message_93(_jspx_page_context))
        return;
      out.write("\" border=\"0\"></a></td>\r\n            <td align=\"center\"><a href=\"javascript:noop()\" onClick=\"");
 if (!trEnabled.get(result.type)) { 
      out.write("alert('You must enable this transport to delete registrations.'); return false;");
 } else { 
      out.write("if (confirm('");
      if (_jspx_meth_fmt_message_94(_jspx_page_context))
        return;
      out.write("')) { deleteRegistration('");
      out.print( result.id );
      out.write("'); return false; } else { return false; }");
 } 
      out.write("\"><img src=\"images/delete-16x16.gif\" alt=\"");
      if (_jspx_meth_fmt_message_95(_jspx_page_context))
        return;
      out.write("\" border=\"0\"></a></td>\r\n\t\t</tr>\r\n\t\t<tr id=\"jiveRegistrationEdit");
      out.print( result.id );
      out.write("\" style=\"display: none\">\r\n\t\t\t<td align=\"center\"><img src=\"images/im_");
      out.print( result.status );
      out.write(".gif\" alt=\"");
      out.print( result.status );
      out.write("\" border=\"0\"></td>\r\n\t\t\t<td>");
      out.print( result.jid );
      out.write("</td>\r\n\t\t\t<td colspan=\"4\"><form method=\"post\" id=\"editRegistration");
      out.print( result.id );
      out.write("\" name=\"editRegistration");
      out.print( result.id );
      out.write("\" action=\"\" onSubmit=\"return false\">\r\n\t\t\t<span class=\"jive-gateway-");
      out.print( result.linestatus );
      out.write(" jive-gateway-");
      out.print( result.type.toUpperCase() );
      out.write("on\">\r\n\t\t\t\t<div class=\"jive-registrations-editUsername\">\r\n\t\t\t\t<input type=\"text\" name=\"gatewayUsername");
      out.print( result.id );
      out.write("\" id=\"gatewayUsername");
      out.print( result.id );
      out.write("\"size=\"12\" maxlength=\"50\" value=\"");
      out.print( result.username );
      out.write("\"><br>\r\n\t\t\t\t<strong>");
      if (_jspx_meth_fmt_message_96(_jspx_page_context))
        return;
      out.write("</strong>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"jive-registrations-editPassword\">\r\n\t\t\t\t<input type=\"password\" name=\"gatewayPassword");
      out.print( result.id );
      out.write("\" id=\"gatewayPassword");
      out.print( result.id );
      out.write("\"size=\"12\" maxlength=\"50\" value=\"********\"><br>\r\n\t\t\t\t<strong>");
      if (_jspx_meth_fmt_message_97(_jspx_page_context))
        return;
      out.write("</strong>\r\n\t\t\t\t</div>\r\n                <div class=\"jive-registrations-editNickname\">\r\n                <input type=\"text\" name=\"gatewayNickname");
      out.print( result.id );
      out.write("%>\" id=\"gatewayNickname");
      out.print( result.id );
      out.write("\" size=\"12\" maxlength=\"50\" value=\"");
      out.print( result.nickname );
      out.write("\"><br>\r\n                <strong>");
      if (_jspx_meth_fmt_message_98(_jspx_page_context))
        return;
      out.write("</strong>\r\n                </div>\r\n                <div class=\"jive-registrations-editButtons\">\r\n\t\t\t\t<input type=\"submit\" name=\"Submit\" value=\"");
      if (_jspx_meth_fmt_message_99(_jspx_page_context))
        return;
      out.write("\" class=\"savechanges\" onClick=\"updateRegistration('");
      out.print( result.id );
      out.write("'); return false\" /> &nbsp;\r\n\t\t\t\t<input type=\"reset\" name=\"reset\" value=\"");
      if (_jspx_meth_fmt_message_100(_jspx_page_context))
        return;
      out.write("\" class=\"cancel\" onClick=\"toggleEdit(");
      out.print( result.id );
      out.write(");\" />\r\n\t\t\t\t</div>\r\n\t\t\t</span>\r\n\t\t\t</form></td>\r\n\t\t</tr>\r\n");

    }

      out.write("\r\n\t</tbody>\r\n\t</table>\r\n\t<!-- BEGIN registrations table -->\r\n\r\n\r\n\t<!-- BEGIN pagination -->\r\n    <div class=\"jive-registrations-pagination\">\r\n        <strong>");
      if (_jspx_meth_fmt_message_101(_jspx_page_context))
        return;
      out.write(":</strong> &nbsp;\r\n            ");

                if (numPages > 1 && ((curPage) > 1)) {
            
      out.write("\r\n                    <a href=\"kraken-registrations.jsp?start=");
      out.print( ((curPage-2)*range) );
      out.write("\">&lt; ");
      if (_jspx_meth_fmt_message_102(_jspx_page_context))
        return;
      out.write("</a>\r\n            ");

                }
                for (int i=0; i<numPages; i++) {
                    boolean isCurrent = (i+1) == curPage;
                    if (isCurrent) {
            
      out.write("\r\n                        <strong>");
      out.print( (i+1) );
      out.write("</strong>\r\n            ");

                    }
                    else {
            
      out.write("\r\n                        <a href=\"kraken-registrations.jsp?start=");
      out.print( (i*range) );
      out.write('"');
      out.write('>');
      out.print( (i+1) );
      out.write("</a>\r\n            ");

                    }
                }
                if (numPages > 1 && ((curPage) < numPages)) {
            
      out.write("\r\n                    <a href=\"kraken-registrations.jsp?start=");
      out.print( (curPage*range) );
      out.write('"');
      out.write('>');
      if (_jspx_meth_fmt_message_103(_jspx_page_context))
        return;
      out.write(" &gt;</a>\r\n            ");

                }
            
      out.write("\r\n    </div>\r\n\t<!-- END pagination -->\r\n\r\n\r\n</div>\r\n<!-- END registrations table -->\r\n\r\n\r\n<br clear=\"all\" />\r\n</body>\r\n\r\n</html>\r\n");
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
    _jspx_th_fmt_message_0.setKey("gateway.web.registrations.title");
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
    _jspx_th_fmt_message_1.setKey("gateway.web.registrations.deletesuccess");
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
    _jspx_th_fmt_message_2.setKey("gateway.web.registrations.updatesuccess");
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
    _jspx_th_fmt_message_3.setKey("gateway.web.registrations.addsuccess");
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
    _jspx_th_fmt_message_4.setKey("gateway.web.registrations.addnewreg");
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
    _jspx_th_fmt_message_5.setKey("gateway.web.registrations.cancelnewreg");
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
    _jspx_th_fmt_message_6.setKey("gateway.web.registrations.instructions");
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
    _jspx_th_fmt_message_7.setKey("gateway.web.registrations.addnewreg");
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
    _jspx_th_fmt_message_8.setKey("gateway.web.registrations.jid");
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
    _jspx_th_fmt_message_9.setKey("gateway.web.registrations.select");
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
    _jspx_th_fmt_message_10.setKey("gateway.aim.shortservice");
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
    _jspx_th_fmt_message_11.setKey("gateway.facebook.shortservice");
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
    _jspx_th_fmt_message_12.setKey("gateway.gadugadu.shortservice");
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
    _jspx_th_fmt_message_13.setKey("gateway.gtalk.shortservice");
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
    _jspx_th_fmt_message_14.setKey("gateway.icq.shortservice");
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
    _jspx_th_fmt_message_15.setKey("gateway.irc.shortservice");
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
    _jspx_th_fmt_message_16.setKey("gateway.livejournal.shortservice");
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
    _jspx_th_fmt_message_17.setKey("gateway.msn.shortservice");
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
    _jspx_th_fmt_message_18.setKey("gateway.myspaceim.shortservice");
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
    _jspx_th_fmt_message_19.setKey("gateway.qq.shortservice");
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
    _jspx_th_fmt_message_20.setKey("gateway.renren.shortservice");
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
    _jspx_th_fmt_message_21.setKey("gateway.sametime.shortservice");
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
    _jspx_th_fmt_message_22.setKey("gateway.simple.shortservice");
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
    _jspx_th_fmt_message_23.setKey("gateway.xmpp.shortservice");
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
    _jspx_th_fmt_message_24.setKey("gateway.yahoo.shortservice");
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
    _jspx_th_fmt_message_25.setKey("gateway.web.registrations.gateway");
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
    _jspx_th_fmt_message_26.setKey("gateway.web.registrations.username");
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
    _jspx_th_fmt_message_27.setKey("gateway.web.registrations.password");
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
    _jspx_th_fmt_message_28.setKey("gateway.web.registrations.nickname");
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
    _jspx_th_fmt_message_29.setKey("global.add");
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
    _jspx_th_fmt_message_30.setKey("global.cancel");
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
    _jspx_th_fmt_message_31.setKey("gateway.web.registrations.registrations");
    int _jspx_eval_fmt_message_31 = _jspx_th_fmt_message_31.doStartTag();
    if (_jspx_th_fmt_message_31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_31);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_31);
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
    _jspx_th_fmt_message_33.setKey("gateway.web.registrations.perpage");
    int _jspx_eval_fmt_message_33 = _jspx_th_fmt_message_33.doStartTag();
    if (_jspx_th_fmt_message_33.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_33);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_33);
    return false;
  }

  private boolean _jspx_meth_fmt_message_34(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_34 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_34.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_34.setParent(null);
    _jspx_th_fmt_message_34.setKey("gateway.web.registrations.page");
    int _jspx_eval_fmt_message_34 = _jspx_th_fmt_message_34.doStartTag();
    if (_jspx_th_fmt_message_34.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_34);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_34);
    return false;
  }

  private boolean _jspx_meth_fmt_message_35(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_35 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_35.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_35.setParent(null);
    _jspx_th_fmt_message_35.setKey("gateway.web.registrations.prev");
    int _jspx_eval_fmt_message_35 = _jspx_th_fmt_message_35.doStartTag();
    if (_jspx_th_fmt_message_35.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_35);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_35);
    return false;
  }

  private boolean _jspx_meth_fmt_message_36(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_36 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_36.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_36.setParent(null);
    _jspx_th_fmt_message_36.setKey("gateway.web.registrations.next");
    int _jspx_eval_fmt_message_36 = _jspx_th_fmt_message_36.doStartTag();
    if (_jspx_th_fmt_message_36.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_36);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_36);
    return false;
  }

  private boolean _jspx_meth_fmt_message_37(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_37 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_37.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_37.setParent(null);
    _jspx_th_fmt_message_37.setKey("gateway.web.registrations.filterby");
    int _jspx_eval_fmt_message_37 = _jspx_th_fmt_message_37.doStartTag();
    if (_jspx_th_fmt_message_37.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_37);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_37);
    return false;
  }

  private boolean _jspx_meth_fmt_message_38(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_38 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_38.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_38.setParent(null);
    _jspx_th_fmt_message_38.setKey("gateway.aim.shortservice");
    int _jspx_eval_fmt_message_38 = _jspx_th_fmt_message_38.doStartTag();
    if (_jspx_th_fmt_message_38.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_38);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_38);
    return false;
  }

  private boolean _jspx_meth_fmt_message_39(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_39 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_39.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_39.setParent(null);
    _jspx_th_fmt_message_39.setKey("gateway.aim.shortservice");
    int _jspx_eval_fmt_message_39 = _jspx_th_fmt_message_39.doStartTag();
    if (_jspx_th_fmt_message_39.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_39);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_39);
    return false;
  }

  private boolean _jspx_meth_fmt_message_40(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_40 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_40.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_40.setParent(null);
    _jspx_th_fmt_message_40.setKey("gateway.aim.shortservice");
    int _jspx_eval_fmt_message_40 = _jspx_th_fmt_message_40.doStartTag();
    if (_jspx_th_fmt_message_40.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_40);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_40);
    return false;
  }

  private boolean _jspx_meth_fmt_message_41(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_41 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_41.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_41.setParent(null);
    _jspx_th_fmt_message_41.setKey("gateway.facebook.shortservice");
    int _jspx_eval_fmt_message_41 = _jspx_th_fmt_message_41.doStartTag();
    if (_jspx_th_fmt_message_41.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_41);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_41);
    return false;
  }

  private boolean _jspx_meth_fmt_message_42(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_42 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_42.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_42.setParent(null);
    _jspx_th_fmt_message_42.setKey("gateway.facebook.shortservice");
    int _jspx_eval_fmt_message_42 = _jspx_th_fmt_message_42.doStartTag();
    if (_jspx_th_fmt_message_42.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_42);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_42);
    return false;
  }

  private boolean _jspx_meth_fmt_message_43(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_43 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_43.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_43.setParent(null);
    _jspx_th_fmt_message_43.setKey("gateway.facebook.shortservice");
    int _jspx_eval_fmt_message_43 = _jspx_th_fmt_message_43.doStartTag();
    if (_jspx_th_fmt_message_43.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_43);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_43);
    return false;
  }

  private boolean _jspx_meth_fmt_message_44(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_44 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_44.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_44.setParent(null);
    _jspx_th_fmt_message_44.setKey("gateway.gadugadu.shortservice");
    int _jspx_eval_fmt_message_44 = _jspx_th_fmt_message_44.doStartTag();
    if (_jspx_th_fmt_message_44.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_44);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_44);
    return false;
  }

  private boolean _jspx_meth_fmt_message_45(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_45 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_45.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_45.setParent(null);
    _jspx_th_fmt_message_45.setKey("gateway.gadugadu.shortservice");
    int _jspx_eval_fmt_message_45 = _jspx_th_fmt_message_45.doStartTag();
    if (_jspx_th_fmt_message_45.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_45);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_45);
    return false;
  }

  private boolean _jspx_meth_fmt_message_46(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_46 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_46.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_46.setParent(null);
    _jspx_th_fmt_message_46.setKey("gateway.gadugadu.shortservice");
    int _jspx_eval_fmt_message_46 = _jspx_th_fmt_message_46.doStartTag();
    if (_jspx_th_fmt_message_46.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_46);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_46);
    return false;
  }

  private boolean _jspx_meth_fmt_message_47(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_47 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_47.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_47.setParent(null);
    _jspx_th_fmt_message_47.setKey("gateway.gtalk.shortservice");
    int _jspx_eval_fmt_message_47 = _jspx_th_fmt_message_47.doStartTag();
    if (_jspx_th_fmt_message_47.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_47);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_47);
    return false;
  }

  private boolean _jspx_meth_fmt_message_48(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_48 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_48.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_48.setParent(null);
    _jspx_th_fmt_message_48.setKey("gateway.gtalk.shortservice");
    int _jspx_eval_fmt_message_48 = _jspx_th_fmt_message_48.doStartTag();
    if (_jspx_th_fmt_message_48.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_48);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_48);
    return false;
  }

  private boolean _jspx_meth_fmt_message_49(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_49 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_49.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_49.setParent(null);
    _jspx_th_fmt_message_49.setKey("gateway.gtalk.shortservice");
    int _jspx_eval_fmt_message_49 = _jspx_th_fmt_message_49.doStartTag();
    if (_jspx_th_fmt_message_49.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_49);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_49);
    return false;
  }

  private boolean _jspx_meth_fmt_message_50(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_50 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_50.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_50.setParent(null);
    _jspx_th_fmt_message_50.setKey("gateway.icq.shortservice");
    int _jspx_eval_fmt_message_50 = _jspx_th_fmt_message_50.doStartTag();
    if (_jspx_th_fmt_message_50.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_50);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_50);
    return false;
  }

  private boolean _jspx_meth_fmt_message_51(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_51 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_51.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_51.setParent(null);
    _jspx_th_fmt_message_51.setKey("gateway.icq.shortservice");
    int _jspx_eval_fmt_message_51 = _jspx_th_fmt_message_51.doStartTag();
    if (_jspx_th_fmt_message_51.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_51);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_51);
    return false;
  }

  private boolean _jspx_meth_fmt_message_52(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_52 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_52.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_52.setParent(null);
    _jspx_th_fmt_message_52.setKey("gateway.icq.shortservice");
    int _jspx_eval_fmt_message_52 = _jspx_th_fmt_message_52.doStartTag();
    if (_jspx_th_fmt_message_52.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_52);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_52);
    return false;
  }

  private boolean _jspx_meth_fmt_message_53(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_53 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_53.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_53.setParent(null);
    _jspx_th_fmt_message_53.setKey("gateway.irc.shortservice");
    int _jspx_eval_fmt_message_53 = _jspx_th_fmt_message_53.doStartTag();
    if (_jspx_th_fmt_message_53.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_53);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_53);
    return false;
  }

  private boolean _jspx_meth_fmt_message_54(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_54 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_54.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_54.setParent(null);
    _jspx_th_fmt_message_54.setKey("gateway.irc.shortservice");
    int _jspx_eval_fmt_message_54 = _jspx_th_fmt_message_54.doStartTag();
    if (_jspx_th_fmt_message_54.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_54);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_54);
    return false;
  }

  private boolean _jspx_meth_fmt_message_55(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_55 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_55.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_55.setParent(null);
    _jspx_th_fmt_message_55.setKey("gateway.irc.shortservice");
    int _jspx_eval_fmt_message_55 = _jspx_th_fmt_message_55.doStartTag();
    if (_jspx_th_fmt_message_55.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_55);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_55);
    return false;
  }

  private boolean _jspx_meth_fmt_message_56(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_56 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_56.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_56.setParent(null);
    _jspx_th_fmt_message_56.setKey("gateway.livejournal.shortservice");
    int _jspx_eval_fmt_message_56 = _jspx_th_fmt_message_56.doStartTag();
    if (_jspx_th_fmt_message_56.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_56);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_56);
    return false;
  }

  private boolean _jspx_meth_fmt_message_57(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_57 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_57.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_57.setParent(null);
    _jspx_th_fmt_message_57.setKey("gateway.livejournal.shortservice");
    int _jspx_eval_fmt_message_57 = _jspx_th_fmt_message_57.doStartTag();
    if (_jspx_th_fmt_message_57.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_57);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_57);
    return false;
  }

  private boolean _jspx_meth_fmt_message_58(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_58 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_58.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_58.setParent(null);
    _jspx_th_fmt_message_58.setKey("gateway.livejournal.shortservice");
    int _jspx_eval_fmt_message_58 = _jspx_th_fmt_message_58.doStartTag();
    if (_jspx_th_fmt_message_58.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_58);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_58);
    return false;
  }

  private boolean _jspx_meth_fmt_message_59(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_59 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_59.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_59.setParent(null);
    _jspx_th_fmt_message_59.setKey("gateway.msn.shortservice");
    int _jspx_eval_fmt_message_59 = _jspx_th_fmt_message_59.doStartTag();
    if (_jspx_th_fmt_message_59.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_59);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_59);
    return false;
  }

  private boolean _jspx_meth_fmt_message_60(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_60 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_60.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_60.setParent(null);
    _jspx_th_fmt_message_60.setKey("gateway.msn.shortservice");
    int _jspx_eval_fmt_message_60 = _jspx_th_fmt_message_60.doStartTag();
    if (_jspx_th_fmt_message_60.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_60);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_60);
    return false;
  }

  private boolean _jspx_meth_fmt_message_61(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_61 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_61.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_61.setParent(null);
    _jspx_th_fmt_message_61.setKey("gateway.msn.shortservice");
    int _jspx_eval_fmt_message_61 = _jspx_th_fmt_message_61.doStartTag();
    if (_jspx_th_fmt_message_61.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_61);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_61);
    return false;
  }

  private boolean _jspx_meth_fmt_message_62(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_62 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_62.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_62.setParent(null);
    _jspx_th_fmt_message_62.setKey("gateway.myspaceim.shortservice");
    int _jspx_eval_fmt_message_62 = _jspx_th_fmt_message_62.doStartTag();
    if (_jspx_th_fmt_message_62.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_62);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_62);
    return false;
  }

  private boolean _jspx_meth_fmt_message_63(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_63 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_63.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_63.setParent(null);
    _jspx_th_fmt_message_63.setKey("gateway.myspaceim.shortservice");
    int _jspx_eval_fmt_message_63 = _jspx_th_fmt_message_63.doStartTag();
    if (_jspx_th_fmt_message_63.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_63);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_63);
    return false;
  }

  private boolean _jspx_meth_fmt_message_64(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_64 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_64.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_64.setParent(null);
    _jspx_th_fmt_message_64.setKey("gateway.myspaceim.shortservice");
    int _jspx_eval_fmt_message_64 = _jspx_th_fmt_message_64.doStartTag();
    if (_jspx_th_fmt_message_64.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_64);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_64);
    return false;
  }

  private boolean _jspx_meth_fmt_message_65(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_65 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_65.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_65.setParent(null);
    _jspx_th_fmt_message_65.setKey("gateway.qq.shortservice");
    int _jspx_eval_fmt_message_65 = _jspx_th_fmt_message_65.doStartTag();
    if (_jspx_th_fmt_message_65.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_65);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_65);
    return false;
  }

  private boolean _jspx_meth_fmt_message_66(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_66 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_66.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_66.setParent(null);
    _jspx_th_fmt_message_66.setKey("gateway.qq.shortservice");
    int _jspx_eval_fmt_message_66 = _jspx_th_fmt_message_66.doStartTag();
    if (_jspx_th_fmt_message_66.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_66);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_66);
    return false;
  }

  private boolean _jspx_meth_fmt_message_67(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_67 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_67.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_67.setParent(null);
    _jspx_th_fmt_message_67.setKey("gateway.qq.shortservice");
    int _jspx_eval_fmt_message_67 = _jspx_th_fmt_message_67.doStartTag();
    if (_jspx_th_fmt_message_67.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_67);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_67);
    return false;
  }

  private boolean _jspx_meth_fmt_message_68(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_68 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_68.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_68.setParent(null);
    _jspx_th_fmt_message_68.setKey("gateway.renren.shortservice");
    int _jspx_eval_fmt_message_68 = _jspx_th_fmt_message_68.doStartTag();
    if (_jspx_th_fmt_message_68.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_68);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_68);
    return false;
  }

  private boolean _jspx_meth_fmt_message_69(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_69 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_69.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_69.setParent(null);
    _jspx_th_fmt_message_69.setKey("gateway.renren.shortservice");
    int _jspx_eval_fmt_message_69 = _jspx_th_fmt_message_69.doStartTag();
    if (_jspx_th_fmt_message_69.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_69);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_69);
    return false;
  }

  private boolean _jspx_meth_fmt_message_70(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_70 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_70.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_70.setParent(null);
    _jspx_th_fmt_message_70.setKey("gateway.renren.shortservice");
    int _jspx_eval_fmt_message_70 = _jspx_th_fmt_message_70.doStartTag();
    if (_jspx_th_fmt_message_70.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_70);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_70);
    return false;
  }

  private boolean _jspx_meth_fmt_message_71(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_71 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_71.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_71.setParent(null);
    _jspx_th_fmt_message_71.setKey("gateway.sametime.shortservice");
    int _jspx_eval_fmt_message_71 = _jspx_th_fmt_message_71.doStartTag();
    if (_jspx_th_fmt_message_71.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_71);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_71);
    return false;
  }

  private boolean _jspx_meth_fmt_message_72(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_72 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_72.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_72.setParent(null);
    _jspx_th_fmt_message_72.setKey("gateway.sametime.shortservice");
    int _jspx_eval_fmt_message_72 = _jspx_th_fmt_message_72.doStartTag();
    if (_jspx_th_fmt_message_72.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_72);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_72);
    return false;
  }

  private boolean _jspx_meth_fmt_message_73(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_73 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_73.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_73.setParent(null);
    _jspx_th_fmt_message_73.setKey("gateway.sametime.shortservice");
    int _jspx_eval_fmt_message_73 = _jspx_th_fmt_message_73.doStartTag();
    if (_jspx_th_fmt_message_73.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_73);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_73);
    return false;
  }

  private boolean _jspx_meth_fmt_message_74(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_74 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_74.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_74.setParent(null);
    _jspx_th_fmt_message_74.setKey("gateway.simple.shortservice");
    int _jspx_eval_fmt_message_74 = _jspx_th_fmt_message_74.doStartTag();
    if (_jspx_th_fmt_message_74.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_74);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_74);
    return false;
  }

  private boolean _jspx_meth_fmt_message_75(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_75 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_75.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_75.setParent(null);
    _jspx_th_fmt_message_75.setKey("gateway.simple.shortservice");
    int _jspx_eval_fmt_message_75 = _jspx_th_fmt_message_75.doStartTag();
    if (_jspx_th_fmt_message_75.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_75);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_75);
    return false;
  }

  private boolean _jspx_meth_fmt_message_76(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_76 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_76.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_76.setParent(null);
    _jspx_th_fmt_message_76.setKey("gateway.simple.shortservice");
    int _jspx_eval_fmt_message_76 = _jspx_th_fmt_message_76.doStartTag();
    if (_jspx_th_fmt_message_76.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_76);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_76);
    return false;
  }

  private boolean _jspx_meth_fmt_message_77(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_77 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_77.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_77.setParent(null);
    _jspx_th_fmt_message_77.setKey("gateway.xmpp.shortservice");
    int _jspx_eval_fmt_message_77 = _jspx_th_fmt_message_77.doStartTag();
    if (_jspx_th_fmt_message_77.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_77);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_77);
    return false;
  }

  private boolean _jspx_meth_fmt_message_78(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_78 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_78.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_78.setParent(null);
    _jspx_th_fmt_message_78.setKey("gateway.xmpp.shortservice");
    int _jspx_eval_fmt_message_78 = _jspx_th_fmt_message_78.doStartTag();
    if (_jspx_th_fmt_message_78.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_78);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_78);
    return false;
  }

  private boolean _jspx_meth_fmt_message_79(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_79 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_79.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_79.setParent(null);
    _jspx_th_fmt_message_79.setKey("gateway.xmpp.shortservice");
    int _jspx_eval_fmt_message_79 = _jspx_th_fmt_message_79.doStartTag();
    if (_jspx_th_fmt_message_79.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_79);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_79);
    return false;
  }

  private boolean _jspx_meth_fmt_message_80(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_80 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_80.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_80.setParent(null);
    _jspx_th_fmt_message_80.setKey("gateway.yahoo.shortservice");
    int _jspx_eval_fmt_message_80 = _jspx_th_fmt_message_80.doStartTag();
    if (_jspx_th_fmt_message_80.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_80);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_80);
    return false;
  }

  private boolean _jspx_meth_fmt_message_81(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_81 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_81.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_81.setParent(null);
    _jspx_th_fmt_message_81.setKey("gateway.yahoo.shortservice");
    int _jspx_eval_fmt_message_81 = _jspx_th_fmt_message_81.doStartTag();
    if (_jspx_th_fmt_message_81.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_81);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_81);
    return false;
  }

  private boolean _jspx_meth_fmt_message_82(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_82 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_82.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_82.setParent(null);
    _jspx_th_fmt_message_82.setKey("gateway.yahoo.shortservice");
    int _jspx_eval_fmt_message_82 = _jspx_th_fmt_message_82.doStartTag();
    if (_jspx_th_fmt_message_82.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_82);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_82);
    return false;
  }

  private boolean _jspx_meth_fmt_message_83(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_83 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_83.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_83.setParent(null);
    _jspx_th_fmt_message_83.setKey("gateway.web.registrations.jid");
    int _jspx_eval_fmt_message_83 = _jspx_th_fmt_message_83.doStartTag();
    if (_jspx_th_fmt_message_83.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_83);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_83);
    return false;
  }

  private boolean _jspx_meth_fmt_message_84(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_84 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_84.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_84.setParent(null);
    _jspx_th_fmt_message_84.setKey("gateway.web.registrations.username");
    int _jspx_eval_fmt_message_84 = _jspx_th_fmt_message_84.doStartTag();
    if (_jspx_th_fmt_message_84.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_84);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_84);
    return false;
  }

  private boolean _jspx_meth_fmt_message_85(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_85 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_85.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_85.setParent(null);
    _jspx_th_fmt_message_85.setKey("gateway.web.registrations.signedon");
    int _jspx_eval_fmt_message_85 = _jspx_th_fmt_message_85.doStartTag();
    if (_jspx_th_fmt_message_85.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_85);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_85);
    return false;
  }

  private boolean _jspx_meth_fmt_message_86(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_86 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_86.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_86.setParent(null);
    _jspx_th_fmt_message_86.setKey("gateway.web.registrations.update");
    int _jspx_eval_fmt_message_86 = _jspx_th_fmt_message_86.doStartTag();
    if (_jspx_th_fmt_message_86.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_86);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_86);
    return false;
  }

  private boolean _jspx_meth_fmt_message_87(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_87 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_87.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_87.setParent(null);
    _jspx_th_fmt_message_87.setKey("gateway.web.registrations.user");
    int _jspx_eval_fmt_message_87 = _jspx_th_fmt_message_87.doStartTag();
    if (_jspx_th_fmt_message_87.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_87);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_87);
    return false;
  }

  private boolean _jspx_meth_fmt_message_88(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_88 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_88.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_88.setParent(null);
    _jspx_th_fmt_message_88.setKey("gateway.web.registrations.node");
    int _jspx_eval_fmt_message_88 = _jspx_th_fmt_message_88.doStartTag();
    if (_jspx_th_fmt_message_88.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_88);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_88);
    return false;
  }

  private boolean _jspx_meth_fmt_message_89(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_89 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_89.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_89.setParent(null);
    _jspx_th_fmt_message_89.setKey("gateway.web.registrations.serviceusername");
    int _jspx_eval_fmt_message_89 = _jspx_th_fmt_message_89.doStartTag();
    if (_jspx_th_fmt_message_89.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_89);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_89);
    return false;
  }

  private boolean _jspx_meth_fmt_message_90(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_90 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_90.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_90.setParent(null);
    _jspx_th_fmt_message_90.setKey("gateway.web.registrations.lastlogin");
    int _jspx_eval_fmt_message_90 = _jspx_th_fmt_message_90.doStartTag();
    if (_jspx_th_fmt_message_90.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_90);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_90);
    return false;
  }

  private boolean _jspx_meth_fmt_message_91(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_91 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_91.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_91.setParent(null);
    _jspx_th_fmt_message_91.setKey("gateway.web.registrations.edit");
    int _jspx_eval_fmt_message_91 = _jspx_th_fmt_message_91.doStartTag();
    if (_jspx_th_fmt_message_91.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_91);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_91);
    return false;
  }

  private boolean _jspx_meth_fmt_message_92(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_92 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_92.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_92.setParent(null);
    _jspx_th_fmt_message_92.setKey("gateway.web.registrations.remove");
    int _jspx_eval_fmt_message_92 = _jspx_th_fmt_message_92.doStartTag();
    if (_jspx_th_fmt_message_92.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_92);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_92);
    return false;
  }

  private boolean _jspx_meth_fmt_message_93(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_93 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_93.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_93.setParent(null);
    _jspx_th_fmt_message_93.setKey("global.edit");
    int _jspx_eval_fmt_message_93 = _jspx_th_fmt_message_93.doStartTag();
    if (_jspx_th_fmt_message_93.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_93);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_93);
    return false;
  }

  private boolean _jspx_meth_fmt_message_94(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_94 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_94.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_94.setParent(null);
    _jspx_th_fmt_message_94.setKey("gateway.web.registrations.confirmdelete");
    int _jspx_eval_fmt_message_94 = _jspx_th_fmt_message_94.doStartTag();
    if (_jspx_th_fmt_message_94.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_94);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_94);
    return false;
  }

  private boolean _jspx_meth_fmt_message_95(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_95 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_95.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_95.setParent(null);
    _jspx_th_fmt_message_95.setKey("global.delete");
    int _jspx_eval_fmt_message_95 = _jspx_th_fmt_message_95.doStartTag();
    if (_jspx_th_fmt_message_95.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_95);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_95);
    return false;
  }

  private boolean _jspx_meth_fmt_message_96(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_96 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_96.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_96.setParent(null);
    _jspx_th_fmt_message_96.setKey("gateway.web.registrations.username");
    int _jspx_eval_fmt_message_96 = _jspx_th_fmt_message_96.doStartTag();
    if (_jspx_th_fmt_message_96.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_96);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_96);
    return false;
  }

  private boolean _jspx_meth_fmt_message_97(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_97 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_97.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_97.setParent(null);
    _jspx_th_fmt_message_97.setKey("gateway.web.registrations.password");
    int _jspx_eval_fmt_message_97 = _jspx_th_fmt_message_97.doStartTag();
    if (_jspx_th_fmt_message_97.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_97);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_97);
    return false;
  }

  private boolean _jspx_meth_fmt_message_98(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_98 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_98.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_98.setParent(null);
    _jspx_th_fmt_message_98.setKey("gateway.web.registrations.nickname");
    int _jspx_eval_fmt_message_98 = _jspx_th_fmt_message_98.doStartTag();
    if (_jspx_th_fmt_message_98.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_98);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_98);
    return false;
  }

  private boolean _jspx_meth_fmt_message_99(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_99 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_99.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_99.setParent(null);
    _jspx_th_fmt_message_99.setKey("global.save_changes");
    int _jspx_eval_fmt_message_99 = _jspx_th_fmt_message_99.doStartTag();
    if (_jspx_th_fmt_message_99.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_99);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_99);
    return false;
  }

  private boolean _jspx_meth_fmt_message_100(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_100 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_100.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_100.setParent(null);
    _jspx_th_fmt_message_100.setKey("global.cancel");
    int _jspx_eval_fmt_message_100 = _jspx_th_fmt_message_100.doStartTag();
    if (_jspx_th_fmt_message_100.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_100);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_100);
    return false;
  }

  private boolean _jspx_meth_fmt_message_101(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_101 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_101.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_101.setParent(null);
    _jspx_th_fmt_message_101.setKey("gateway.web.registrations.page");
    int _jspx_eval_fmt_message_101 = _jspx_th_fmt_message_101.doStartTag();
    if (_jspx_th_fmt_message_101.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_101);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_101);
    return false;
  }

  private boolean _jspx_meth_fmt_message_102(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_102 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_102.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_102.setParent(null);
    _jspx_th_fmt_message_102.setKey("gateway.web.registrations.prev");
    int _jspx_eval_fmt_message_102 = _jspx_th_fmt_message_102.doStartTag();
    if (_jspx_th_fmt_message_102.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_102);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_102);
    return false;
  }

  private boolean _jspx_meth_fmt_message_103(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_103 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_103.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_103.setParent(null);
    _jspx_th_fmt_message_103.setKey("gateway.web.registrations.next");
    int _jspx_eval_fmt_message_103 = _jspx_th_fmt_message_103.doStartTag();
    if (_jspx_th_fmt_message_103.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_103);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_103);
    return false;
  }
}
