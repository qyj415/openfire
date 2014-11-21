package org.jivesoftware.openfire.plugin.kraken;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import java.util.Collection;
import java.util.Iterator;
import org.dom4j.Element;
import org.dom4j.Attribute;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.LocaleUtils;
import org.dom4j.Document;
import net.sf.kraken.KrakenPlugin;
import org.jivesoftware.openfire.XMPPServer;

public final class kraken_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

    final KrakenPlugin plugin =
            (KrakenPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("kraken");

    final ArrayList<String> optionTypes = new ArrayList<String>();

    class GatewaySettings {

        Logger Log = Logger.getLogger(GatewaySettings.class);

        JspWriter out = null;
        Integer jsID = 0; // Javascript incrementable id

        GatewaySettings(JspWriter out) {
            this.out = out;
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

                    String inputId = var.getText();
                    out.println("<tr valign='middle'>");
                    out.println("<td align='right' width='20%'><label for='" + inputId + "'>" +
                            descStr + "</label>:</td>");
                    out.print("<td><input type='text' id='" + inputId + "' name='" + inputId + "'" +
                            (size != null ? " size='" + size.getText() + "'" : "") +
                            (size != null ? " maxlength='" + maxlen.getText() + "'" : "") +
                            " value='" + setting + "'");
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

                    String jsStr = (++jsID).toString();
                    String checkId = var.getText();
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
            Document optConfig = plugin.getOptionsConfig();
            Element mainPanel = optConfig.getRootElement().element("mainpanel");
            if (mainPanel != null && mainPanel.nodeCount() > 0) {
                for (Object nodeObj : mainPanel.elements("item")) {
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
                Document optConfig = plugin.getOptionsConfig();
                Element mainPanel = optConfig.getRootElement().element("mainpanel");

      out.write("\n    <!-- Options Window -->\n        <div>\n            <form id=\"jiveoptionsform\" action=\"\" onSubmit=\"return false\">\n                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n                    <tr>\n                        <td align=\"left\">\n");

                if (mainPanel != null && mainPanel.nodeCount() > 0) {
                    out.println("<table border='0' cellpadding='1' cellspacing='2'>");
                    for (Object nodeObj : mainPanel.elements("item")) {
                        Element node = (Element)nodeObj;
                        printConfigNode(node);
                    }
                    out.println("</table>");
                }
                else {
                    out.println("&nbsp;");
                }

      out.write("\n                        </td>\n                    </tr>\n                </table>\n\n                <span id=\"optionsresults\" class=\"saveResultsMsg\"></span>\n                <input type=\"submit\" name=\"submit\" value=\"");
      out.print( LocaleUtils.getLocalizedString("gateway.web.settings.saveoptions", "kraken") );
      out.write("\" onclick=\"saveOptions(); return false\" class=\"jive-formButton\">\n                <input type=\"reset\" name=\"cancel\" value=\"");
      out.print( LocaleUtils.getLocalizedString("gateway.web.settings.cancelchanges", "kraken") );
      out.write("\" onclick=\"cancelOptions(); return true\" class=\"jive-formButton\">\n            </form>\n        </div>\n\n\n");

            }
            catch (Exception e) {
                // Uhm, yeah, that sucks.
                Log.error("Error printing settings section:", e);
            }
        }
    }

    GatewaySettings settings =  new GatewaySettings(out);

      out.write("\n\n<html>\n\n<head>\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n<meta name=\"pageID\" content=\"kraken-settings\">\n<style type=\"text/css\">\n<!--\t@import url(\"style/kraken.css\");    -->\n</style>\n<script src=\"dwr/engine.js\" type=\"text/javascript\"></script>\n<script src=\"dwr/util.js\" type=\"text/javascript\"></script>\n<script src=\"dwr/interface/ConfigManager.js\" type=\"text/javascript\"></script>\n<script type=\"text/javascript\" >\n    DWREngine.setErrorHandler(handleError);\n    window.onerror = handleError;\n\n    function handleError(error) {\n        // swallow errors\n    }\n\n    var optionTypes = new Array(\n");

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

      out.write("\n    );\n\n    function saveOptions(transportID) {\n        var globalSettings = new Object();\n        for (var x in optionTypes) {\n            var optType = optionTypes[x];\n            var optionId = transportID+optType;\n            var testoption = document.getElementById(optionId);\n            if (testoption != null) {\n                transportSettings[optType] = DWRUtil.getValue(optionId);\n            }\n        }\n        ConfigManager.saveSettings(globalSettings);\n        document.getElementById(\"setStatusMsg\").style.display = \"\";\n        document.getElementById(\"setStatusMsg\").innerHTML = \"<span class='successresults'><img src='images/success-16x16.gif' align='absmiddle' />");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("</span>\";\n        setTimeout(\"to_saveOptions()\", 5000);\n    }\n\n    function cancelOptions(transportID) {\n        document.getElementById(\"setStatusMsg\").style.display = \"\";\n        document.getElementById(\"setStatusMsg\").innerHTML = \"<span class='warningresults'><img src='images/warning-16x16.gif' align='absmiddle' />");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</span>\";\n        setTimeout(\"to_saveOptions()\", 5000);\n    }\n\n    function to_saveOptions() {\n        Effect.Fade(\"setStatusMsg\");\n    }\n\n    function pingSession() {\n        ConnectionTester.pingSession();\n        setTimeout(\"pingSession()\", 60000); // Every minute\n    }\n\n    setTimeout(\"pingSession()\", 60000); // One minute after first load\n</script>\n</head>\n\n<body>\n<p>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</p>\n\n<div id=\"setStatusMsg\" style=\"display: none\"></div>\n\n<form action=\"\" name=\"gatewayForm\">\n\n");
 settings.printSettingsDialog(); 
      out.write("\n\n</form>\n\n<br clear=\"all\" />\n</body>\n\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("gateway.web.settings.title");
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
    _jspx_th_fmt_message_1.setKey("gateway.web.settings.settingssaved");
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
    _jspx_th_fmt_message_2.setKey("gateway.web.settings.cancelledchanges");
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
    _jspx_th_fmt_message_3.setKey("gateway.web.settings.instructions");
    int _jspx_eval_fmt_message_3 = _jspx_th_fmt_message_3.doStartTag();
    if (_jspx_th_fmt_message_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_3);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_3);
    return false;
  }
}
