package org.jivesoftware.openfire.plugin.kraken;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.JspWriter;
import org.jivesoftware.openfire.XMPPServer;
import net.sf.kraken.KrakenPlugin;
import net.sf.kraken.type.TransportType;
import org.jivesoftware.util.LocaleUtils;
import org.dom4j.Element;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.jivesoftware.util.JiveGlobals;
import java.util.ArrayList;
import net.sf.kraken.permissions.PermissionManager;
import java.util.Collection;
import java.util.Iterator;
import org.apache.log4j.Logger;

public final class kraken_002dtransports_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

    final KrakenPlugin plugin =
            (KrakenPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("kraken");

    final ArrayList<String> optionTypes = new ArrayList<String>();

    class GatewaySettings {

        Logger Log = Logger.getLogger(GatewaySettings.class);

        String description = null;
        String fulljid = null;
        TransportType gatewayType = null;
        boolean gwEnabled = false;
        JspWriter out = null;
        Integer jsID = 0; // Javascript incrementable id
        String connectHost = null;
        String connectPort = null;
        String userPermText = "[" + LocaleUtils.getLocalizedString("gateway.web.transports.noneselected", "kraken") + "]";
        String groupPermText = "[" + LocaleUtils.getLocalizedString("gateway.web.transports.noneselected", "kraken") + "]";
        String userPermEntry = "";
        String groupPermEntry = "";
        Integer globalPermSetting = 1;
        Boolean globalPermStrict = false;

        GatewaySettings(JspWriter out, KrakenPlugin plugin, TransportType gatewayType,
                        String desc) {
            // Allow XMPP setting to be overridden.
            // TODO: Make this more generic.
            if (gatewayType.equals(TransportType.xmpp) && JiveGlobals.getProperty("plugin.gateway.xmpp.overrideservice") != null) {
                desc = JiveGlobals.getProperty("plugin.gateway.xmpp.overrideservice");
            }
            this.description = desc;
            this.gatewayType = gatewayType;
            this.fulljid = JiveGlobals.getProperty("plugin.gateway." + gatewayType.toString() + ".subdomain", gatewayType.toString()) + "." + XMPPServer.getInstance().getServerInfo().getXMPPDomain();
            this.gwEnabled = plugin.serviceEnabled(gatewayType.toString());
            this.out = out;
            getConnectHostAndPort();
            getPermissionsList();
            pollConfigOptions();
        }

        public String join(Collection s, String delimiter) {
            // Borrowed from http://www.bigbold.com/snippets/posts/show/91
            StringBuffer buffer = new StringBuffer();
            Iterator iter = s.iterator();
            while (iter.hasNext()) {
                buffer.append(iter.next());
                if (iter.hasNext()) {
                    buffer.append(delimiter);
                }
            }
            return buffer.toString();
        }

        void getPermissionsList() {
            PermissionManager permissionManager = new PermissionManager(this.gatewayType);
            ArrayList<String> userList = permissionManager.getAllUsers();
            if (userList.size() > 0) {
                String joinedString = join(userList, " ");
                userPermText = joinedString;
                userPermEntry = joinedString;
            }
            ArrayList<String> groupList = permissionManager.getAllGroups();
            if (groupList.size() > 0) {
                String joinedString = join(groupList, " ");
                groupPermText = joinedString;
                groupPermEntry = joinedString;
            }
            globalPermSetting = JiveGlobals.getIntProperty("plugin.gateway." + this.gatewayType.toString() + ".registration", 1);
            globalPermStrict = JiveGlobals.getBooleanProperty("plugin.gateway." + this.gatewayType.toString() + ".registrationstrict", false);
        }

        void getConnectHostAndPort() {
            // This assumes that you've chosen to keep the connect host and port in a standard
            // location ... as a "root level" option in the left or right panel.
            Document optConfig = plugin.getOptionsConfig(gatewayType);
            if (optConfig == null) {
                Log.debug("No options config present for transport.");
                return;
            }
            Element leftPanel = optConfig.getRootElement().element("leftpanel");
            Element rightPanel = optConfig.getRootElement().element("rightpanel");
            Element bottomPanel = optConfig.getRootElement().element("bottompanel");
            if (leftPanel != null && leftPanel.nodeCount() > 0) {
                for (Object nodeObj : leftPanel.elements("item")) {
                    Element node = (Element) nodeObj;
                    Attribute type = node.attribute("type");
                    Attribute var = node.attribute("var");
                    Attribute sysprop = node.attribute("sysprop");
                    if (type == null || var == null || sysprop == null) {
                        Log.error("Missing variable from options config.");
                        continue;
                    }
                    Attribute def = node.attribute("default");
                    String defStr = "";
                    if (def != null) {
                        defStr = def.getText();
                    }
                    if (type.getText().equals("text") && var.getText().equals("host")) {
                        this.connectHost = JiveGlobals.getProperty(sysprop.getText(), defStr);
                    }
                    if (type.getText().equals("text") && var.getText().equals("port")) {
                        this.connectPort = JiveGlobals.getProperty(sysprop.getText(), defStr);
                    }
                }
            }
            if (rightPanel != null && rightPanel.nodeCount() > 0) {
                for (Object nodeObj : rightPanel.elements("item")) {
                    Element node = (Element) nodeObj;
                    Attribute type = node.attribute("type");
                    Attribute var = node.attribute("var");
                    Attribute sysprop = node.attribute("sysprop");
                    if (type == null || var == null || sysprop == null) {
                        Log.error("Missing variable from options config.");
                        continue;
                    }
                    Attribute def = node.attribute("default");
                    String defStr = "";
                    if (def != null) {
                        defStr = def.getText();
                    }
                    if (type.getText().equals("text") && var.getText().equals("host")) {
                        this.connectHost = JiveGlobals.getProperty(sysprop.getText(), defStr);
                    }
                    if (type.getText().equals("text") && var.getText().equals("port")) {
                        this.connectPort = JiveGlobals.getProperty(sysprop.getText(), defStr);
                    }
                }
            }
            if (bottomPanel != null && bottomPanel.nodeCount() > 0) {
                for (Object nodeObj : bottomPanel.elements("item")) {
                    Element node = (Element) nodeObj;
                    Attribute type = node.attribute("type");
                    Attribute var = node.attribute("var");
                    Attribute sysprop = node.attribute("sysprop");
                    if (type == null || var == null || sysprop == null) {
                        Log.error("Missing variable from options config.");
                        continue;
                    }
                    Attribute def = node.attribute("default");
                    String defStr = "";
                    if (def != null) {
                        defStr = def.getText();
                    }
                    if (type.getText().equals("text") && var.getText().equals("host")) {
                        this.connectHost = JiveGlobals.getProperty(sysprop.getText(), defStr);
                    }
                    if (type.getText().equals("text") && var.getText().equals("port")) {
                        this.connectPort = JiveGlobals.getProperty(sysprop.getText(), defStr);
                    }
                }
            }
        }

        void printConfigNode(Element node) {
            try {
                Attribute type = node.attribute("type");
                if (type.getText().equals("text")) {
                    // Required fields
                    Attribute desckey = node.attribute("desckey");
                    Attribute var = node.attribute("var");
                    Attribute sysprop = node.attribute("sysprop");

                    // Optional fields
                    Attribute def = node.attribute("default");
                    Attribute size = node.attribute("size");
                    Attribute maxlen = node.attribute("maxlength");

                    if (desckey == null || var == null || sysprop == null) {
                        Log.error("Missing variable from options config.");
                        return;
                    }

                    String defStr = "";
                    if (def != null) {
                        defStr = def.getText();
                    }

                    String descStr = LocaleUtils.getLocalizedString(desckey.getText(), "kraken");
                    String setting = JiveGlobals.getProperty(sysprop.getText(), defStr);

                    String inputId = gatewayType.toString() + var.getText();
                    out.println("<tr valign='middle'>");
                    out.println("<td align='right' width='20%'><label for='" + inputId + "'>" +
                            descStr + "</label>:</td>");
                    out.print("<td><input type='text' id='" + inputId + "' name='" + inputId + "'" +
                            (size != null ? " size='" + size.getText() + "'" : "") +
                            (size != null ? " maxlength='" + maxlen.getText() + "'" : "") +
                            " value='" + setting + "'");
                    if (var.getText().equals("host")) {
                        out.print(" onChange='document.getElementById(\"" + gatewayType.toString() +
                                "testhost\").innerHTML = this.value'");
                    }
                    if (var.getText().equals("port")) {
                        out.print(" onChange='document.getElementById(\"" + gatewayType.toString() +
                                "testport\").innerHTML = this.value'");
                    }
                    out.println(" /></td>");
                    out.println("</tr>");
                } else if (type.getText().equals("toggle")) {
                    // Required fields
                    Attribute desckey = node.attribute("desckey");
                    Attribute var = node.attribute("var");
                    Attribute sysprop = node.attribute("sysprop");

                    // Optional fields
                    Attribute def = node.attribute("default");
                    Attribute alert = node.attribute("alert");

                    if (desckey == null || var == null || sysprop == null) {
                        Log.error("Missing variable from options config.");
                        return;
                    }

                    boolean defBool = false;
                    if (def != null && (def.getText().equals("1") || def.getText().equals("true") ||
                            def.getText().equals("enabled") || def.getText().equals("yes"))) {
                        defBool = true;
                    }

                    String descStr = LocaleUtils.getLocalizedString(desckey.getText(), "kraken");
                    String alertStr = null;
                    if (alert != null && alert.getText() != null && alert.getText().length() > 0) {
                        alertStr = LocaleUtils.getLocalizedString(alert.getText(), "kraken");
                    }
                    boolean setting = JiveGlobals.getBooleanProperty(sysprop.getText(), defBool);

                    String jsStr = gatewayType.toString() + (++jsID);
                    String checkId = gatewayType.toString() + var.getText();
                    boolean hasChildren = node.elements("item").size() > 0;
                    out.println("<tr valign='top'>");
                    out.print("<td align='right' width='20%'><input type='checkbox' id='" +
                            checkId + "' name='" + checkId + "' value='true' " +
                            (setting ? " checked='checked'" : ""));
                    if (hasChildren) {
                        out.print(" onClick='elem = document.getElementById(\"" + jsStr +
                                "\"); if (elem) { if (this.checked) { elem.style.display=\"table\"} else { elem.style.display=\"none\"} }'");
                    }
                    if (alertStr != null) {
                        out.print(" onClick='elem = document.getElementById(\"" + checkId +
                                "\"); if (elem) { if (this.checked) { return confirm(\""+alertStr+"\") } else { return true; } } else { return true; }'");
                    }
                    out.println("/></td>");
                    out.print("<td><label for='" + checkId + "'>" + descStr + "</label>");
                    if (hasChildren) {
                        out.println("<table id='" + jsStr + "' width='100%' style='display: " +
                                (setting ? "table" : "none") + "'>");
                        for (Object itemObj : node.elements("item")) {
                            Element item = (Element) itemObj;
                            printConfigNode(item);
                        }
                        out.println("</table>");
                    }
                    out.println("</td>");
                    out.println("</tr>");
                }
            }
            catch (Exception e) {
                // Uhm, yeah, that sucks.
                Log.error("Error printing config node:", e);
            }
        }

        void pollConfigOptions() {
            Document optConfig = plugin.getOptionsConfig(gatewayType);
            Element leftPanel = optConfig.getRootElement().element("leftpanel");
            Element rightPanel = optConfig.getRootElement().element("rightpanel");
            Element bottomPanel = optConfig.getRootElement().element("bottompanel");
            if (leftPanel != null && leftPanel.nodeCount() > 0) {
                for (Object nodeObj : leftPanel.elements("item")) {
                    Element node = (Element) nodeObj;
                    getConfigOptions(node);
                }
            }

            if (rightPanel != null && rightPanel.nodeCount() > 0) {
                for (Object nodeObj : rightPanel.elements("item")) {
                    Element node = (Element) nodeObj;
                    getConfigOptions(node);
                }
            }

            if (bottomPanel != null && bottomPanel.nodeCount() > 0) {
                for (Object nodeObj : bottomPanel.elements("item")) {
                    Element node = (Element) nodeObj;
                    getConfigOptions(node);
                }
            }
        }

        void getConfigOptions(Element node) {
            try {
                Attribute type = node.attribute("type");
                if (type.getText().equals("text")) {
                    // Required fields
                    Attribute desckey = node.attribute("desckey");
                    Attribute var = node.attribute("var");
                    Attribute sysprop = node.attribute("sysprop");

                    if (desckey == null || var == null || sysprop == null) {
                        Log.error("Missing variable from options config.");
                        return;
                    }

                    // Store a copy of the node variable for later use.
                    if (!optionTypes.contains(var.getText())) {
                        optionTypes.add(var.getText());
                    }
                } else if (type.getText().equals("toggle")) {
                    // Required fields
                    Attribute desckey = node.attribute("desckey");
                    Attribute var = node.attribute("var");
                    Attribute sysprop = node.attribute("sysprop");

                    if (desckey == null || var == null || sysprop == null) {
                        Log.error("Missing variable from options config.");
                        return;
                    }

                    // Store a copy of the node variable for later use.
                    if (!optionTypes.contains(var.getText())) {
                        optionTypes.add(var.getText());
                    }
                    for (Object itemObj : node.elements("item")) {
                        Element item = (Element) itemObj;
                        getConfigOptions(item);
                    }
                }
            }
            catch (Exception e) {
                // Uhm, yeah, that sucks.
                Log.error("Error reading config node:", e);
            }
        }

        void printSettingsDialog() {
            try {
                Document optConfig = plugin.getOptionsConfig(gatewayType);
                Element leftPanel = optConfig.getRootElement().element("leftpanel");
                Element rightPanel = optConfig.getRootElement().element("rightpanel");
                Element bottomPanel = optConfig.getRootElement().element("bottompanel");

      out.write("\n\n\t<!-- BEGIN gateway - ");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write(" -->\n    <div ");
      out.print( ((!this.gwEnabled) ? " class='jive-gateway jive-gatewayDisabled'" : "class='jive-gateway'") );
      out.write(" id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("\">\n\t\t<label for=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("checkbox\">\n\t\t\t<input type=\"checkbox\" name=\"kraken\" value=\"");
      out.print( this.gatewayType.toString().toLowerCase() );
      out.write("\" id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("checkbox\" ");
      out.print( ((this.gwEnabled) ? "checked" : "") );
      out.write(" onClick=\"ConfigManager.toggleTransport('");
      out.print( this.gatewayType.toString().toLowerCase() );
      out.write("'); checkToggle(jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("); return true\">\n\t\t\t<img src=\"images/");
      out.print( this.gatewayType.toString().toLowerCase() );
      out.write(".png\" alt=\"\" border=\"0\">\n\t\t\t<strong>");
      out.print( this.description );
      out.write("</strong>\n\t\t</label>\n\t\t<div class=\"jive-gatewayButtons\">\n            <a href=\"#\" onclick=\"togglePanel(jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("tests); return false\" id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("testsLink\" ");
      out.print( ((!this.gwEnabled) ? "style='display:none'" : "") );
      out.write('>');
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.tests", "kraken") );
      out.write("</a>\n            <a href=\"#\" onclick=\"togglePanel(jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("options); return false\" id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("optionsLink\" ");
      out.print( ((!this.gwEnabled) ? "style='display:none'" : "") );
      out.write('>');
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.options", "kraken") );
      out.write("</a>\n\t\t\t<a href=\"#\" onclick=\"togglePanel(jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("perms); return false\" id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("permsLink\" ");
      out.print( ((!this.gwEnabled) ? "style='display:none'" : "") );
      out.write('>');
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.permissions", "kraken") );
      out.write("</a>\n\t\t</div>\n\t</div>\n    <!-- JID display -->\n    <div class=\"jive-gatewaySub\" id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("sub\" ");
      out.print( ((this.gwEnabled) ? " style='display: block'" : "style='display: none'") );
      out.write(">\n        <div>\n            ");
      out.print( this.fulljid );
      out.write("\n        </div>\n    </div>\n    <!-- Tests Window -->\n    <div class=\"jive-gatewayPanel\" id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("tests\" style=\"display: none;\">\n        <div>\n\t\t\t");
 if (this.gatewayType.toString().equals("qq")) { 
      out.write("\n\t\t\t<b>Tests not supported for QQ (yet).</b>\n\t\t\t");
 } else { 
      out.write("\n            <form id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("testsform\" action=\"\" onSubmit=\"return false\">\n                <span style=\"font-weight: bold\">");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.connecttohost", "kraken") );
      out.write(":</span> <span id=\"");
      out.print( this.gatewayType.toString() );
      out.write("testhost\">");
      out.print( connectHost );
      out.write("</span><br />\n                <span style=\"font-weight: bold\">");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.connecttoport", "kraken") );
      out.write(":</span> <span id=\"");
      out.print( this.gatewayType.toString() );
      out.write("testport\">");
      out.print( connectPort );
      out.write("</span><br />\n\n                <span id=\"");
      out.print( this.gatewayType.toString() );
      out.write("testsresults\" class=\"saveResultsMsg\"></span>\n                <input type=\"submit\" name=\"submit\" value=\"");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.testconnection", "kraken") );
      out.write("\" onclick=\"testConnect('");
      out.print( this.gatewayType.toString() );
      out.write("'); return false\" class=\"jive-formButton\">\n            </form>\n\t\t\t");
 } 
      out.write("\n        </div>\n    </div>\n    <!-- Options Window -->\n    <div class=\"jive-gatewayPanel\" id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("options\" style=\"display: none;\">\n\t\t<div>\n            <form id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("optionsform\" action=\"\" onSubmit=\"return false\">\n                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                    <tr valign=\"top\">\n                        <td align=\"left\" width=\"50%\">\n");

                if (leftPanel != null && leftPanel.nodeCount() > 0) {
                    out.println("<table border='0' cellpadding='1' cellspacing='2'>");
                    for (Object nodeObj : leftPanel.elements("item")) {
                        Element node = (Element)nodeObj;
                        printConfigNode(node);
                    }
                    out.println("</table>");
                }
                else {
                    out.println("&nbsp;");
                }

      out.write("\n                        </td>\n                        <td align=\"left\" width=\"50%\">\n");

                if (rightPanel != null && rightPanel.nodeCount() > 0) {
                    out.println("<table border='0' cellpadding='1' cellspacing='2'>");
                    for (Object nodeObj : rightPanel.elements("item")) {
                        Element node = (Element)nodeObj;
                        printConfigNode(node);
                    }
                    out.println("</table>");
                }
                else {
                    out.println("&nbsp;");
                }

      out.write("\n                        </td>\n                    </tr>\n                    <tr>\n                        <td align=\"left\" colspan=\"2\">\n");

                if (bottomPanel != null && bottomPanel.nodeCount() > 0) {
                    out.println("<table border='0' cellpadding='1' cellspacing='2' width='100%'>");
                    for (Object nodeObj : bottomPanel.elements("item")) {
                        Element node = (Element)nodeObj;
                        printConfigNode(node);
                    }
                    out.println("</table>");
                }
                else {
                    out.println("&nbsp;");
                }

      out.write("\n                        </td>\n                    </tr>\n                </table>\n\n                <span id=\"");
      out.print( this.gatewayType.toString() );
      out.write("optionsresults\" class=\"saveResultsMsg\"></span>\n                <input type=\"submit\" name=\"submit\" value=\"");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.saveoptions", "kraken") );
      out.write("\" onclick=\"saveOptions('");
      out.print( this.gatewayType.toString() );
      out.write("'); return false\" class=\"jive-formButton\">\n                <input type=\"reset\" name=\"cancel\" value=\"");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.cancelchanges", "kraken") );
      out.write("\" onclick=\"cancelOptions('");
      out.print( this.gatewayType.toString() );
      out.write("'); return true\" class=\"jive-formButton\">\n            </form>\n\t\t</div>\n\t</div>\n    <!-- Permissions Window -->\n    <div class=\"jive-gatewayPanel\" id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("perms\" style=\"display: none;\">\n\t\t<div>\n            <form id=\"jive");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write("permsform\" action=\"\"  onSubmit=\"return false\">\n                <input type=\"radio\" name=\"");
      out.print( this.gatewayType.toString() );
      out.write("userreg\" value=\"all\" onClick=\"hideSpecificChoices('");
      out.print( this.gatewayType.toString() );
      out.write("')\" ");
      out.print( (this.globalPermSetting == 1 ? "checked='checked'" : "") );
      out.write(" /> ");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.registerall", "kraken") );
      out.write("<br>\n                <input type=\"radio\" name=\"");
      out.print( this.gatewayType.toString() );
      out.write("userreg\" value=\"specific\" onClick=\"showSpecificChoices('");
      out.print( this.gatewayType.toString() );
      out.write("')\"  ");
      out.print( (this.globalPermSetting == 2 ? "checked='checked'" : "") );
      out.write(" /> ");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.registersome", "kraken") );
      out.write("<br>\n                <div id=\"");
      out.print( this.gatewayType.toString() );
      out.write("userreg_specific\" style=\"");
      out.print( (this.globalPermSetting == 2 ? "" : "display: none; ") );
      out.write("margin: 0; padding: 0; font-size: 80%\">\n                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-left: 30.0px\" width='100%'>\n                        <tr valign=\"top\">\n                            <td align=\"left\" style=\"padding-right: 15.0px\" width='50%'>\n                                <span style=\"font-weight: bold\">");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.users", "kraken") );
      out.write("</span> <a href=\"javascript:noop()\" onClick=\"activateModifyUsers('");
      out.print( this.gatewayType.toString() );
      out.write("'); return false\">(");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.modifyusers", "kraken") );
      out.write(")</a><br />\n                                <div id=\"");
      out.print( this.gatewayType.toString() );
      out.write("userpermtextdiv\" style=\"margin: 0; padding: 0\" class='permissionListDiv'><span id=\"");
      out.print( this.gatewayType.toString() );
      out.write("userpermtext\">");
      out.print( this.userPermText );
      out.write("</span></div>\n                                <div id=\"");
      out.print( this.gatewayType.toString() );
      out.write("userpermentrydiv\" style=\"margin: 0; padding: 0; display:none\" class='permissionListDiv'><textarea style=\"margin: 0\" class='permissionListTextArea' rows=\"5\" cols=\"20\" id=\"");
      out.print( this.gatewayType.toString() );
      out.write("userpermentry\" name=\"");
      out.print( this.gatewayType.toString() );
      out.write("userpermentry\">");
      out.print( this.userPermEntry );
      out.write("</textarea></div>\n                            </td>\n                            <td align=\"left\" style=\"margin-left: 15.0px\" width='50%'>\n                                <span style=\"font-weight: bold\">");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.groups", "kraken") );
      out.write("</span> <a href=\"javascript:noop()\" onClick=\"activateModifyGroups('");
      out.print( this.gatewayType.toString() );
      out.write("'); return false\">(");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.modifygroups", "kraken") );
      out.write(")</a><br />\n                                <div id=\"");
      out.print( this.gatewayType.toString() );
      out.write("grouppermtextdiv\" style=\"margin: 0; padding: 0\" class='permissionListDiv'><span id=\"");
      out.print( this.gatewayType.toString() );
      out.write("grouppermtext\">");
      out.print( this.groupPermText );
      out.write("</span></div>\n                                <div id=\"");
      out.print( this.gatewayType.toString() );
      out.write("grouppermentrydiv\" style=\"margin: 0; padding: 0; display:none\" class='permissionListDiv'><textarea style=\"margin: 0\" class='permissionListTextArea' rows=\"5\" cols=\"20\" id=\"");
      out.print( this.gatewayType.toString() );
      out.write("grouppermentry\" name=\"");
      out.print( this.gatewayType.toString() );
      out.write("grouppermentry\">");
      out.print( this.groupPermEntry );
      out.write("</textarea></div>\n                            </td>\n                        </tr>\n                    </table>\n                </div>\n                <input type=\"radio\" name=\"");
      out.print( this.gatewayType.toString() );
      out.write("userreg\" value=\"manual\" onClick=\"hideSpecificChoices('");
      out.print( this.gatewayType.toString() );
      out.write("')\" ");
      out.print( (this.globalPermSetting == 3 ? "checked='checked'" : "") );
      out.write(" /> ");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.registernone", "kraken") );
      out.write("<br>\n                <br>\n                <input type=\"checkbox\" name=\"");
      out.print( this.gatewayType.toString() );
      out.write("strictperms\" value=\"true\" ");
      out.print( (this.globalPermStrict ? "checked='checked'" : "") );
      out.write(" /> ");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.permsstrict", "kraken") );
      out.write("<br>\n\n                <span id=\"");
      out.print( this.gatewayType.toString() );
      out.write("permsresults\" class=\"saveResultsMsg\"></span>\n                <input type=\"submit\" name=\"submit\" value=\"");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.savepermissions", "kraken") );
      out.write("\" onclick=\"savePermissions('");
      out.print( this.gatewayType.toString() );
      out.write("'); return false\" class=\"jive-formButton\">\n                <input type=\"reset\" name=\"cancel\" value=\"");
      out.print( LocaleUtils.getLocalizedString("gateway.web.transports.cancelchanges", "kraken") );
      out.write("\" onclick=\"cancelPermissions('");
      out.print( this.gatewayType.toString() );
      out.write("'); return true\" class=\"jive-formButton\">\n            </form>\n\t\t</div>\n\t</div>\n\t<!-- END gateway - ");
      out.print( this.gatewayType.toString().toUpperCase() );
      out.write(" -->\n\n");

            }
            catch (Exception e) {
                // Uhm, yeah, that sucks.
                Log.error("Error printing settings section:", e);                
            }
        }
    }

    GatewaySettings aimSettings = new GatewaySettings(out, plugin, TransportType.aim, LocaleUtils.getLocalizedString("gateway.aim.service", "kraken"));
    GatewaySettings facebookSettings = new GatewaySettings(out, plugin, TransportType.facebook, LocaleUtils.getLocalizedString("gateway.facebook.service", "kraken"));
    GatewaySettings gadugaduSettings = new GatewaySettings(out, plugin, TransportType.gadugadu, LocaleUtils.getLocalizedString("gateway.gadugadu.service", "kraken"));
    GatewaySettings gtalkSettings = new GatewaySettings(out, plugin, TransportType.gtalk, LocaleUtils.getLocalizedString("gateway.gtalk.service", "kraken"));
    GatewaySettings icqSettings = new GatewaySettings(out, plugin, TransportType.icq, LocaleUtils.getLocalizedString("gateway.icq.service", "kraken"));
    GatewaySettings ircSettings = new GatewaySettings(out, plugin, TransportType.irc, LocaleUtils.getLocalizedString("gateway.irc.service", "kraken"));
    GatewaySettings livejournalSettings = new GatewaySettings(out, plugin, TransportType.livejournal, LocaleUtils.getLocalizedString("gateway.livejournal.service", "kraken"));
    GatewaySettings msnSettings = new GatewaySettings(out, plugin, TransportType.msn, LocaleUtils.getLocalizedString("gateway.msn.service", "kraken"));
    GatewaySettings myspaceimSettings = new GatewaySettings(out, plugin, TransportType.myspaceim, LocaleUtils.getLocalizedString("gateway.myspaceim.service", "kraken"));
    GatewaySettings qqSettings = new GatewaySettings(out, plugin, TransportType.qq, LocaleUtils.getLocalizedString("gateway.qq.service", "kraken"));
    GatewaySettings renrenSettings = new GatewaySettings(out, plugin, TransportType.renren, LocaleUtils.getLocalizedString("gateway.renren.service", "kraken"));
    GatewaySettings sametimeSettings = new GatewaySettings(out, plugin, TransportType.sametime, LocaleUtils.getLocalizedString("gateway.sametime.service", "kraken"));
    GatewaySettings simpleSettings = new GatewaySettings(out, plugin, TransportType.simple, LocaleUtils.getLocalizedString("gateway.simple.service", "kraken"));
    GatewaySettings xmppSettings = new GatewaySettings(out, plugin, TransportType.xmpp, LocaleUtils.getLocalizedString("gateway.xmpp.service", "kraken"));
    GatewaySettings yahooSettings = new GatewaySettings(out, plugin, TransportType.yahoo, LocaleUtils.getLocalizedString("gateway.yahoo.service", "kraken"));

      out.write("\n\n\n\n\n<html>\n\n<head>\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n<meta name=\"pageID\" content=\"kraken-transports\">\n<style type=\"text/css\">\n<!-- @import url(\"style/kraken.css\"); -->\n</style>\n<script src=\"dwr/engine.js\" type=\"text/javascript\"></script>\n<script src=\"dwr/util.js\" type=\"text/javascript\"></script>\n<script src=\"dwr/interface/ConfigManager.js\" type=\"text/javascript\"></script>\n<script src=\"dwr/interface/ConnectionTester.js\" type=\"text/javascript\"></script>\n<script type=\"text/javascript\" >\n    DWREngine.setErrorHandler(handleError);\n    window.onerror = handleError;\n\n    function handleError(error) {\n        // swallow errors\n    }\n\n    var lastID = \"\";\n\n    function togglePanel(thisID) {\n\n        var activeLink = thisID.id+\"Link\";\n        if (lastID != \"\" && lastID != thisID) {\n            var oldLink = lastID.id+\"Link\";\n            if ($(thisID).style.display != 'none' && $(lastID).style.display == 'none') {\n                Effect.toggle($(thisID),'slide', {duration: .4});\n                $(activeLink).className = \"\";\n            } else if ($(thisID).style.display == 'none' && $(lastID).style.display != 'none') {\n");
      out.write("                Effect.toggle($(lastID),'slide', {duration: .4});\n                $(oldLink).className = \"\";\n                Effect.toggle($(thisID),'slide', {duration: .4, delay: .5});\n                $(activeLink).className = \"jive-gatewayButtonOn\";\n            } else {\n                Effect.toggle($(thisID),'slide', {duration: .4});\n                $(activeLink).className = \"jive-gatewayButtonOn\";\n            }\n        }\n        else {\n            if ($(thisID).style.display != 'none') {\n                Effect.toggle($(thisID),'slide', {duration: .4});\n                $(activeLink).className = \"\";\n            } else {\n                Effect.toggle($(thisID),'slide', {duration: .4});\n                $(activeLink).className = \"jive-gatewayButtonOn\";\n            }\n        }\n\n        lastID = thisID;\n    }\n\n\n\n    /*\n    checkToggle function\n    this toggles the appearance and options for the gateways. When a user\n    unchecks a gateway the box goes grey panels aren't accessible.\n    */\n    function checkToggle(theID) {\n");
      out.write("\n        var theCheckbox = theID.id+\"checkbox\";\n        var testLink = theID.id+\"testsLink\";\n        var optsLink = theID.id+\"optionsLink\";\n        var permLink = theID.id+\"permsLink\";\n        var testPanel = theID.id+\"tests\";\n        var optsPanel = theID.id+\"options\";\n        var permPanel = theID.id+\"perms\";\n        var subPanel = theID.id+\"sub\";\n\n        if ($(theCheckbox).checked) {\n            $(theID).className = \"jive-gateway\";\n            $(testLink).style.display = 'block';\n            $(optsLink).style.display = 'block';\n            $(permLink).style.display = 'block';\n            Effect.toggle($(subPanel), 'slide', {duration: .2});\n        } else {\n            $(theID).className = \"jive-gateway jive-gatewayDisabled\";\n            $(testLink).style.display = 'none';\n            $(optsLink).style.display = 'none';\n            $(permLink).style.display = 'none';\n            /* fix the panels so they roll up and the buttons go back to default states */\n            $(testLink).className = \"\";\n            $(optsLink).className = \"\";\n");
      out.write("            $(permLink).className = \"\";\n            if ($(optsPanel).style.display != 'none') {\n                Effect.toggle($(optsPanel), 'slide', {duration: .4});\n            } else if ($(permPanel).style.display != 'none') {\n                Effect.toggle($(permPanel), 'slide', {duration: .4});\n            } else if ($(testPanel).style.display != 'none') {\n                Effect.toggle($(testPanel), 'slide', {duration: .4});\n            }\n            Effect.toggle($(subPanel), 'slide', {duration: .2});\n        }\n\n    }\n\n    var settings;\n    var optionTypes = new Array(\n");

        Boolean first = true;
        for (String var : optionTypes) {
            if (!first) {
                out.println(",");
            }
            out.print("      \""+var+"\"");
            if (first) {
                first = false;
            }
        }

      out.write("\n    );\n    var testTransportID = null;\n\n    function saveOptions(transportID) {\n        var transportSettings = new Object();\n        for (var x in optionTypes) {\n            var optType = optionTypes[x];\n            var optionId = transportID+optType;\n            var testoption = document.getElementById(optionId);\n            if (testoption != null) {\n                transportSettings[optType] = DWRUtil.getValue(optionId);\n            }\n        }\n        ConfigManager.saveSettings(transportID, transportSettings);\n        document.getElementById(transportID+\"optionsresults\").style.display = \"\";\n        document.getElementById(transportID+\"optionsresults\").innerHTML = \"<span class='successresults'><img src='images/success-16x16.gif' align='absmiddle' />");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("</span>\";\n        setTimeout(\"to_saveOptions('\"+transportID+\"')\", 5000);\n    }\n\n    function cancelOptions(transportID) {\n        document.getElementById(transportID+\"optionsresults\").style.display = \"\";\n        document.getElementById(transportID+\"optionsresults\").innerHTML = \"<span class='warningresults'><img src='images/warning-16x16.gif' align='absmiddle' />");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</span>\";\n        setTimeout(\"to_saveOptions('\"+transportID+\"')\", 5000);\n    }\n\n    function to_saveOptions(transportID) {\n        Effect.Fade(transportID+\"optionsresults\");\n    }\n\n    function testConnect(transportID) {\n        testTransportID = transportID;\n        ConnectionTester.testConnection(DWRUtil.getValue(transportID+\"testhost\"),\n                DWRUtil.getValue(transportID+\"testport\"), cb_testConnect);\n    }\n\n    function cb_testConnect(result) {\n        document.getElementById(testTransportID+\"testsresults\").style.display = \"\";\n        if (result) {\n            document.getElementById(testTransportID+\"testsresults\").innerHTML = \"<span class='successresults'><img src='images/success-16x16.gif' alt='' align='absmiddle' />");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</span>\";\n        }\n        else {\n            document.getElementById(testTransportID+\"testsresults\").innerHTML = \"<span class='failureresults'><img src='images/failure-16x16.gif' alt='' align='absmiddle' />");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</span>\";\n        }\n        setTimeout(\"to_testConnect('\"+testTransportID+\"')\", 5000);\n        testTransportID = null;\n    }\n\n    function to_testConnect(transportID) {\n        Effect.Fade(transportID+\"testsresults\");\n    }\n\n    var lastUserList;\n    var lastGroupList;\n    var lastTransportID;\n\n    function savePermissions(transportID) {\n        var userEntry = DWRUtil.getValue(transportID+\"userpermentry\");\n        var groupEntry = DWRUtil.getValue(transportID+\"grouppermentry\");\n        var globalSettingStr = DWRUtil.getValue(transportID+\"userreg\");\n        var globalSetting = 1; // Allow all as default\n        if (globalSettingStr == \"all\") {\n            globalSetting = 1;\n        }\n        else if (globalSettingStr == \"specific\") {\n            globalSetting = 2;\n        }\n        else if (globalSettingStr == \"manual\") {\n            globalSetting = 3;\n        }\n        var globalPermStr = DWRUtil.getValue(transportID+\"strictperms\");\n        var globalPermSetting = false; // Not strict by default\n        if (globalPermStr) {\n");
      out.write("            globalPermSetting = true;\n        }\n        var userList = userEntry.split(/\\s+/);\n        var groupList = groupEntry.split(/\\s+/);\n        lastUserList = userList;\n        lastGroupList = groupList;\n        lastTransportID = transportID;\n        ConfigManager.savePermissions(transportID, globalSetting, userList, groupList, globalPermSetting, cb_savePermissions);\n    }\n\n    function cb_savePermissions(errorList) {\n        var userList = lastUserList;\n        var groupList = lastGroupList;\n        var transportID = lastTransportID;\n        if (errorList != null && errorList.length > 0) {\n            var errUsers = new Array();\n            var errGroups = new Array();\n            for (var i = 0; i < errorList.length; i++) {\n                if (errorList[i].charAt(0) == \"@\") {\n                    var grpName = errorList[i].substr(1, (errorList[i].length-1));\n                    errGroups.push(grpName);\n                    for (var j = 0; j < groupList.length; j++) {\n                        if (groupList[j] == grpName) {\n");
      out.write("                            groupList.splice(j, 1);\n                            break;\n                        }\n                    }\n                }\n                else {\n                    var userName = errorList[i];\n                    errUsers.push(userName);\n                    for (var j = 0; j < userList.length; j++) {\n                        if (userList[j] == userName) {\n                            userList.splice(j, 1);\n                            break;\n                        }\n                    }\n                }\n            }\n            var errMsg = \"\";\n            if (errUsers.length > 0) {\n                errMsg = errMsg + \"\\nThe following users were not valid and were ignored:\\n\" + errUsers.join(\"\\n\") + \"\\n\";\n            }\n            if (errGroups.length > 0) {\n                errMsg = errMsg + \"\\nThe following groups were not valid and were ignored:\\n\" + errGroups.join(\"\\n\") + \"\\n\";\n            }\n            alert(errMsg);\n        }\n        for (var i = 0; i < userList.length; i++) {\n");
      out.write("            var charPos = userList[i].indexOf(\"@\");\n            if (charPos >= 0) {\n                userList[i] = userList[i].substr(0, charPos);\n            }\n        }\n        document.getElementById(transportID+\"userpermtext\").innerHTML = (userList.length > 0 ? userList.join(\" \") : \"[");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("]\");\n        document.getElementById(transportID+\"userpermentry\").value = userList.join(\" \");\n        document.getElementById(transportID+\"grouppermtext\").innerHTML = (groupList.length > 0 ? groupList.join(\" \") : \"[");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("]\");\n        document.getElementById(transportID+\"grouppermentry\").value = groupList.join(\" \");\n        deactivateModifyUsers(transportID);\n        deactivateModifyGroups(transportID);\n        document.getElementById(transportID+\"permsresults\").style.display = \"\";\n        document.getElementById(transportID+\"permsresults\").innerHTML = \"<span class='successresults'><img src='images/success-16x16.gif' align='absmiddle' />");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</span>\";\n        setTimeout(\"to_savePermissions('\"+transportID+\"')\", 5000);\n    }\n\n    function cancelPermissions(transportID) {\n        deactivateModifyUsers(transportID);\n        deactivateModifyGroups(transportID);\n        document.getElementById(transportID+\"permsresults\").style.display = \"\";\n        document.getElementById(transportID+\"permsresults\").innerHTML = \"<span class='warningresults'><img src='images/warning-16x16.gif' align='absmiddle' />");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</span>\";\n        setTimeout(\"to_savePermissions('\"+transportID+\"')\", 5000);\n    }\n\n    function to_savePermissions(transportID) {\n        Effect.Fade(transportID+\"permsresults\");\n    }\n\n    function activateModifyUsers(transportID) {\n        var textElem = document.getElementById(transportID+\"userpermtextdiv\");\n        var entryElem = document.getElementById(transportID+\"userpermentrydiv\");\n        if (textElem.style.display != \"none\") {\n            Effect.SlideUp(textElem,{duration: .4});\n        }\n        if (entryElem.style.display == \"none\") {\n            Effect.SlideDown(entryElem, {duration: .4, delay: .5});\n        }\n    }\n\n    function deactivateModifyUsers(transportID) {\n        var textElem = document.getElementById(transportID+\"userpermtextdiv\");\n        var entryElem = document.getElementById(transportID+\"userpermentrydiv\");\n        if (entryElem.style.display != \"none\") {\n            Effect.SlideUp(entryElem,{duration: .4});\n        }\n        if (textElem.style.display == \"none\") {\n            Effect.SlideDown(textElem, {duration: .4, delay: .5});\n");
      out.write("        }\n    }\n\n    function activateModifyGroups(transportID) {\n        var textElem = document.getElementById(transportID+\"grouppermtextdiv\");\n        var entryElem = document.getElementById(transportID+\"grouppermentrydiv\");\n        if (textElem.style.display != \"none\") {\n            Effect.SlideUp(textElem,{duration: .4});\n        }\n        if (entryElem.style.display == \"none\") {\n            Effect.SlideDown(entryElem, {duration: .4, delay: .5});\n        }\n    }\n\n    function deactivateModifyGroups(transportID) {\n        var textElem = document.getElementById(transportID+\"grouppermtextdiv\");\n        var entryElem = document.getElementById(transportID+\"grouppermentrydiv\");\n        if (entryElem.style.display != \"none\") {\n            Effect.SlideUp(entryElem,{duration: .4});\n        }\n        if (textElem.style.display == \"none\") {\n            Effect.SlideDown(textElem, {duration: .4, delay: .5});\n        }\n    }\n\n    function hideSpecificChoices(transportID) {\n        var targElement = document.getElementById(transportID+\"userreg_specific\");\n");
      out.write("        if (targElement.style.display != \"none\") {\n            Effect.toggle(targElement,'slide', {duration: .4});\n        }\n    }\n\n    function showSpecificChoices(transportID) {\n        var targElement = document.getElementById(transportID+\"userreg_specific\");\n        if (targElement.style.display == \"none\") {\n            Effect.toggle(targElement,'slide', {duration: .4});\n        }\n    }\n\n    function pingSession() {\n        ConnectionTester.pingSession();\n        setTimeout(\"pingSession()\", 60000); // Every minute\n    }\n\n    setTimeout(\"pingSession()\", 60000); // One minute after first load\n</script>\n</head>\n\n<body>\n<p>");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("</p>\n\n<form action=\"\" name=\"gatewayForm\">\n\n");
 aimSettings.printSettingsDialog(); 
      out.write('\n');
 facebookSettings.printSettingsDialog(); 
      out.write('\n');
 gadugaduSettings.printSettingsDialog(); 
      out.write('\n');
 gtalkSettings.printSettingsDialog(); 
      out.write('\n');
 icqSettings.printSettingsDialog(); 
      out.write('\n');
 ircSettings.printSettingsDialog(); 
      out.write('\n');
 livejournalSettings.printSettingsDialog(); 
      out.write('\n');
 msnSettings.printSettingsDialog(); 
      out.write('\n');
 xmppSettings.printSettingsDialog(); 
      out.write('\n');
 yahooSettings.printSettingsDialog(); 
      out.write("\n\n<br><br>\n\n<div id=\"jive-title\">");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</div>\n\n<p>");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("</p>\n\n");
 qqSettings.printSettingsDialog(); 
      out.write('\n');
 myspaceimSettings.printSettingsDialog(); 
      out.write('\n');
 renrenSettings.printSettingsDialog(); 
      out.write('\n');
 sametimeSettings.printSettingsDialog(); 
      out.write('\n');
 simpleSettings.printSettingsDialog(); 
      out.write("\n\n</form>\n<br clear=\"all\">\n</body>\n\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("gateway.web.transports.title");
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
    _jspx_th_fmt_message_1.setKey("gateway.web.transports.settingssaved");
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
    _jspx_th_fmt_message_2.setKey("gateway.web.transports.cancelledchanges");
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
    _jspx_th_fmt_message_3.setKey("gateway.web.transports.success");
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
    _jspx_th_fmt_message_4.setKey("gateway.web.transports.failed");
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
    _jspx_th_fmt_message_5.setKey("gateway.web.transports.noneselected");
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
    _jspx_th_fmt_message_6.setKey("gateway.web.transports.noneselected");
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
    _jspx_th_fmt_message_7.setKey("gateway.web.transports.permissionssaved");
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
    _jspx_th_fmt_message_8.setKey("gateway.web.transports.cancelledchanges");
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
    _jspx_th_fmt_message_9.setKey("gateway.web.transports.instructions");
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
    _jspx_th_fmt_message_10.setKey("gateway.web.transports.unstable.title");
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
    _jspx_th_fmt_message_11.setKey("gateway.web.transports.unstable.notice");
    int _jspx_eval_fmt_message_11 = _jspx_th_fmt_message_11.doStartTag();
    if (_jspx_th_fmt_message_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_11);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_11);
    return false;
  }
}
