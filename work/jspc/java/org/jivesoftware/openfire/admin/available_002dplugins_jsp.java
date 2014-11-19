package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ByteFormat;
import org.jivesoftware.util.Version;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.util.StringUtils;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.update.AvailablePlugin;
import org.jivesoftware.openfire.update.UpdateManager;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jivesoftware.util.JiveGlobals;
import java.util.Date;

public final class available_002dplugins_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
 webManager.init(request, response, session, application, out ); 
      out.write('\n');
      out.write('\n');

    boolean downloadRequested = request.getParameter("download") != null;
    String url = request.getParameter("url");

    UpdateManager updateManager = XMPPServer.getInstance().getUpdateManager();
    List<AvailablePlugin> plugins = updateManager.getNotInstalledPlugins();

    String time = JiveGlobals.getProperty("update.lastCheck");
    // Sort plugins alphabetically
    Collections.sort(plugins, new Comparator<AvailablePlugin>() {
        public int compare(AvailablePlugin o1, AvailablePlugin o2) {
            return o1.getName().compareTo(o2.getName());
        }
    });

    if (downloadRequested) {
        // Download and install new plugin
        updateManager.downloadPlugin(url);
        // Log the event
        webManager.logEvent("downloaded new plugin from "+url, null);
    }


      out.write("\n\n<html>\n<head>\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n<meta name=\"pageID\" content=\"available-plugins\"/>\n\n<style type=\"text/css\">\n\n.light-gray-border {\n    border-color: #ccc;\n    border-style: solid;\n    border-width: 1px 1px 1px 1px;\n    padding: 5px;\n\t-moz-border-radius: 3px;\n}\n\n\n\n.table-header {\n    text-align: left;\n    font-family: verdana, arial, helvetica, sans-serif;\n    font-size: 8pt;\n    font-weight: bold;\n    border-color: #ccc;\n    border-style: solid;\n    border-width: 1px 0 1px 0;\n    padding: 5px;\n}\n\n.row-header {\n    text-align: left;\n    font-family: verdana, arial, helvetica, sans-serif;\n    font-size: 8pt;\n    font-weight: bold;\n    border-color: #ccc;\n    border-style: solid;\n    border-width: 1px 1px 1px 0;\n    padding: 5px;\n}\n\n.table-header-left {\n    text-align: left;\n    font-family: verdana, arial, helvetica, sans-serif;\n    font-size: 8pt;\n    font-weight: bold;\n    border-color: #ccc;\n    border-style: solid;\n    border-width: 1px 0 1px 1px;\n    padding: 5px;\n\n}\n\n.table-header-right {\n    text-align: left;\n    font-family: verdana, arial, helvetica, sans-serif;\n");
      out.write("    font-size: 8pt;\n    font-weight: bold;\n    border-color: #ccc;\n    border-style: solid;\n    border-width: 1px 1px 1px 0;\n    padding: 5px;\n}\n\n.line-bottom-border {\n    font-family: verdana, arial, helvetica, sans-serif;\n    font-size: 9pt;\n    border-color: #e3e3e3;\n    border-style: solid;\n    border-width: 0 0 1px 0;\n    padding: 5px;\n}\n\n\n</style>\n\n<script src=\"dwr/engine.js\" type=\"text/javascript\"></script>\n<script src=\"dwr/util.js\" type=\"text/javascript\"></script>\n<script src=\"dwr/interface/downloader.js\" type=\"text/javascript\"></script>\n<script type=\"text/javascript\">\n\n    var downloading;\n    function downloadPlugin(url, id) {\n        downloading = true;\n        document.getElementById(id + \"-image\").innerHTML = '<img src=\"images/working-16x16.gif\" border=\"0\"/>';\n        document.getElementById(id).style.background = \"#FFFFCC\";\n        setTimeout(\"startDownload('\" + url + \"','\" + id + \"')\", 5000);\n    }\n\n    function startDownload(url, id) {\n        downloader.installPlugin(downloadComplete, url, id);\n");
      out.write("    }\n\n    function downloadComplete(status) {\n        downloading = false;\n        if (!status.successfull) {\n            document.getElementById(status.hashCode + \"-image\").innerHTML = '<img src=\"images/add-16x16.gif\" border=\"0\"/>';\n            document.getElementById(status.hashCode).style.background = \"#FFFFFF\";\n            document.getElementById(\"errorMessage\").style.display = '';\n            document.getElementById(status.hashCode).style.display = '';\n            document.getElementById(status.hashCode + \"-row\").style.display = 'none';\n            setTimeout(\"closeErrorMessage()\", 5000);\n        }\n        else {\n            document.getElementById(status.hashCode).style.display = 'none';\n            document.getElementById(status.hashCode + \"-row\").style.display = '';\n            setTimeout(\"fadeIt('\" + status.hashCode + \"')\", 3000);\n        }\n    }\n\n    function closeErrorMessage(){\n        Effect.Fade(\"errorMessage\");\n    }\n\n    function fadeIt(id) {\n        Effect.Fade(id + \"-row\");\n    }\n\n\n    DWREngine.setErrorHandler(handleError);\n");
      out.write("\n    function handleError(error) {\n    }\n\n    // Handle leaving of page validation.\n    window.onbeforeunload = function (evt) {\n        if (!downloading) {\n            return;\n        }\n        var message = '");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("';\n        if (typeof evt == 'undefined') {\n            evt = window.event;\n        }\n        if (evt) {\n            evt.returnValue = message;\n        }\n        return message;\n    }\n\n    function updatePluginsList(){\n        document.getElementById(\"reloaderID\").innerHTML = '<img src=\"images/working-16x16.gif\" border=\"0\"/>';\n        downloader.updatePluginsList(pluginsListUpdated);\n    }\n\n    function updatePluginsListNow(){\n        document.getElementById(\"reloader2\").innerHTML = '<img src=\"images/working-16x16.gif\" border=\"0\"/>';\n        downloader.updatePluginsList(pluginsListUpdated);\n    }\n\n    function pluginsListUpdated(){\n        window.location.href = \"available-plugins.jsp\";\n    }\n\n\n</script>\n</head>\n\n<body>\n\n<p>\n    ");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\n</p>\n\n<p>\n\n");
if(time == null){ 
      out.write("\n<div style=\"padding:10px;background:#FFEBB5;border:1px solid #DEB24A;width:75%;\">\n    ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("&nbsp;<span id=\"reloaderID\"><a href=\"javascript:updatePluginsList();\">");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</a></span>\n</div>\n<br/>\n<div style=\"width:75%;\">\n    <p>\n   ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\n</p>\n\n");
 if(!updateManager.isServiceEnabled()){ 
      out.write('\n');
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write(" <b>");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</b>. <a href=\"manage-updates.jsp\">");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</a> ");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write('\n');
 } 
      out.write("\n</div>\n");
 } else {
      out.write("\n\n\n<div id=\"errorMessage\" class=\"error\" style=\"display:none;\">\n    ");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\n</div>\n\n\n<div class=\"light-gray-border\" style=\"padding:10px;\">\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n<thead>\n    <tr style=\"background:#eee;\">\n        <td class=\"table-header-left\">&nbsp;</td>\n        <td nowrap colspan=\"2\" class=\"table-header\">");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("</td>\n        <td nowrap class=\"table-header\">");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</td>\n        <td nowrap class=\"table-header\">");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("</td>\n        <td nowrap class=\"table-header\">");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</td>\n        <td nowrap class=\"table-header\">File Size</td>\n        <td nowrap class=\"table-header-right\">");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</td>\n    </tr>\n</thead>\n<tbody>\n\n");

    // If only the admin plugin is installed, show "none".
    if (plugins.isEmpty()) {

      out.write("\n<tr>\n    <td align=\"center\" colspan=\"8\">");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("</td>\n</tr>\n");

    }

    for (AvailablePlugin plugin : plugins) {
        String pluginName = plugin.getName();
        String pluginDescription = plugin.getDescription();
        String pluginAuthor = plugin.getAuthor();
        String pluginVersion = plugin.getLatestVersion();
        ByteFormat byteFormat = new ByteFormat();
        String fileSize = byteFormat.format(plugin.getFileSize());

        if (plugin.isCommercial()) {
            continue;
        }

      out.write("\n<tr id=\"");
      out.print( plugin.hashCode());
      out.write("\">\n    <td width=\"1%\" class=\"line-bottom-border\">\n        ");
 if (plugin.getIcon() != null) { 
      out.write("\n        <img src=\"");
      out.print( StringUtils.escapeForXML(plugin.getIcon()) );
      out.write("\" width=\"16\" height=\"16\" alt=\"Plugin\">\n        ");
 }
        else { 
      out.write("\n        <img src=\"images/plugin-16x16.gif\" width=\"16\" height=\"16\" alt=\"Plugin\">\n        ");
 } 
      out.write("\n    </td>\n    <td width=\"20%\" nowrap class=\"line-bottom-border\">\n        ");
      out.print( (pluginName != null ? StringUtils.escapeHTMLTags(pluginName) : "") );
      out.write(" &nbsp;\n    </td>\n    <td nowrap valign=\"top\" class=\"line-bottom-border\">\n        ");
 if (plugin.getReadme() != null) { 
      out.write("\n        <a href=\"");
      out.print( StringUtils.escapeForXML(plugin.getReadme()) );
      out.write("\"\n            ><img src=\"images/doc-readme-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"README\"></a>\n        ");
 }
        else { 
      out.write(" &nbsp; ");
 } 
      out.write("\n        ");
 if (plugin.getChangelog() != null) { 
      out.write("\n        <a href=\"");
      out.print( StringUtils.escapeForXML(plugin.getChangelog()) );
      out.write("\"\n            ><img src=\"images/doc-changelog-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"changelog\"></a>\n        ");
 }
        else { 
      out.write(" &nbsp; ");
 } 
      out.write("\n    </td>\n    <td width=\"60%\" class=\"line-bottom-border\">\n        ");
      out.print( pluginDescription != null ? StringUtils.escapeHTMLTags(pluginDescription) : "" );
      out.write("\n    </td>\n    <td width=\"5%\" align=\"center\" valign=\"top\" class=\"line-bottom-border\">\n        ");
      out.print( pluginVersion != null ? StringUtils.escapeHTMLTags(pluginVersion) : "" );
      out.write("\n    </td>\n    <td width=\"15%\" nowrap valign=\"top\" class=\"line-bottom-border\">\n        ");
      out.print( pluginAuthor != null ? StringUtils.escapeHTMLTags(pluginAuthor) : "" );
      out.write("  &nbsp;\n    </td>\n    <td width=\"15%\" nowrap valign=\"top\" class=\"line-bottom-border\" align=\"right\">\n        ");
      out.print( StringUtils.escapeHTMLTags(fileSize) );
      out.write("\n    </td>\n    <td width=\"1%\" align=\"center\" valign=\"top\" class=\"line-bottom-border\">\n        ");

            String updateURL = plugin.getURL();
            if (updateManager.isPluginDownloaded(updateURL)) {
        
      out.write("\n        &nbsp;\n        ");
  }
        else { 
      out.write("\n        ");


        
      out.write("\n        <a href=\"javascript:downloadPlugin('");
      out.print(StringUtils.escapeForXML(updateURL));
      out.write("', '");
      out.print( plugin.hashCode());
      out.write("')\"><span id=\"");
      out.print( plugin.hashCode() );
      out.write("-image\"><img src=\"images/add-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"\n                                                                                                                                        alt=\"");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("\"></span></a>\n\n        ");
 } 
      out.write("\n    </td>\n</tr>\n<tr id=\"");
      out.print( plugin.hashCode());
      out.write("-row\" style=\"display:none;background: #E7FBDE;\">\n    <td width=\"1%\" class=\"line-bottom-border\">\n        <img src=\"");
      out.print( StringUtils.escapeForXML(plugin.getIcon()));
      out.write("\" width=\"16\" height=\"16\" alt=\"\"/>\n    </td>\n    <td colspan=\"6\" nowrap class=\"line-bottom-border\">");
      out.print( StringUtils.escapeHTMLTags(plugin.getName()));
      out.write(' ');
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</td>\n    <td class=\"line-bottom-border\" align=\"center\">\n        <img src=\"images/success-16x16.gif\" height=\"16\" width=\"16\" alt=\"\"/>\n    </td>\n</tr>\n");

    }

      out.write("\n<tr><td><br/></td></tr>\n<tr style=\"background:#eee;\">\n    <td class=\"table-header-left\">&nbsp;</td>\n    <td nowrap colspan=\"7\" class=\"row-header\">");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("</td>\n</tr>\n");

    for (AvailablePlugin plugin : plugins) {
        String pluginName = plugin.getName();
        String pluginDescription = plugin.getDescription();
        String pluginAuthor = plugin.getAuthor();
        String pluginVersion = plugin.getLatestVersion();
        ByteFormat byteFormat = new ByteFormat();
        String fileSize = byteFormat.format(plugin.getFileSize());

        if (!plugin.isCommercial()) {
            continue;
        }

      out.write("\n<tr id=\"");
      out.print( plugin.hashCode());
      out.write("\">\n    <td width=\"1%\" class=\"line-bottom-border\">\n        ");
 if (plugin.getIcon() != null) { 
      out.write("\n        <img src=\"");
      out.print( StringUtils.escapeForXML(plugin.getIcon()) );
      out.write("\" width=\"16\" height=\"16\" alt=\"Plugin\">\n        ");
 }
        else { 
      out.write("\n        <img src=\"images/plugin-16x16.gif\" width=\"16\" height=\"16\" alt=\"Plugin\">\n        ");
 } 
      out.write("\n    </td>\n    <td width=\"20%\" nowrap class=\"line-bottom-border\">\n        ");
      out.print( (pluginName != null ? StringUtils.escapeHTMLTags(pluginName) : "") );
      out.write(" &nbsp;\n    </td>\n    <td nowrap valign=\"top\" class=\"line-bottom-border\">\n        ");
 if (plugin.getReadme() != null) { 
      out.write("\n        <a href=\"");
      out.print( StringUtils.escapeForXML(plugin.getReadme()) );
      out.write("\"\n            ><img src=\"images/doc-readme-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"README\"></a>\n        ");
 }
        else { 
      out.write(" &nbsp; ");
 } 
      out.write("\n        ");
 if (plugin.getChangelog() != null) { 
      out.write("\n        <a href=\"");
      out.print( StringUtils.escapeForXML(plugin.getChangelog()) );
      out.write("\"\n            ><img src=\"images/doc-changelog-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"changelog\"></a>\n        ");
 }
        else { 
      out.write(" &nbsp; ");
 } 
      out.write("\n    </td>\n    <td width=\"60%\" class=\"line-bottom-border\">\n        ");
      out.print( pluginDescription != null ? StringUtils.escapeHTMLTags(pluginDescription) : "" );
      out.write("\n    </td>\n    <td width=\"5%\" align=\"center\" valign=\"top\" class=\"line-bottom-border\">\n        ");
      out.print( pluginVersion != null ? StringUtils.escapeHTMLTags(pluginVersion) : "" );
      out.write("\n    </td>\n    <td width=\"15%\" nowrap valign=\"top\" class=\"line-bottom-border\">\n        ");
      out.print( pluginAuthor != null ? StringUtils.escapeHTMLTags(pluginAuthor) : "" );
      out.write("  &nbsp;\n    </td>\n    <td width=\"15%\" nowrap valign=\"top\" class=\"line-bottom-border\">\n        ");
      out.print( StringUtils.escapeHTMLTags(fileSize)  );
      out.write("\n    </td>\n    <td width=\"1%\" align=\"center\" valign=\"top\" class=\"line-bottom-border\">\n        ");

            String updateURL = plugin.getURL();
            if (updateManager.isPluginDownloaded(updateURL)) {
        
      out.write("\n        &nbsp;\n        ");
  }
        else { 
      out.write("\n\n        <span id=\"");
      out.print( plugin.hashCode() );
      out.write("-image\"><a href=\"javascript:downloadPlugin('");
      out.print(StringUtils.escapeForXML(updateURL) );
      out.write("', '");
      out.print( plugin.hashCode() );
      out.write("')\"><img src=\"images/add-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"\n                                                                                                                                        alt=\"");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("\"></a></span>\n        ");
 } 
      out.write("\n    </td>\n</tr>\n<tr id=\"");
      out.print( plugin.hashCode());
      out.write("-row\" style=\"display:none;background: #E7FBDE;\">\n     <td width=\"1%\" class=\"line-bottom-border\">\n        <img src=\"");
      out.print( StringUtils.escapeForXML(plugin.getIcon()));
      out.write("\" width=\"16\" height=\"16\" alt=\"\"/>\n    </td>\n    <td colspan=\"6\" nowrap class=\"line-bottom-border\">");
      out.print( StringUtils.escapeHTMLTags(plugin.getName()));
      out.write(' ');
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</td>\n    <td class=\"line-bottom-border\" align=\"center\">\n        <img src=\"images/success-16x16.gif\" height=\"16\" width=\"16\" alt=\"\"/>\n    </td>\n</tr>\n");

    }

      out.write("\n\n</tbody>\n</table>\n\n</div>\n\n<br/>\n\n\n");

    final XMPPServer server = XMPPServer.getInstance();
    Version version = server.getServerInfo().getVersion();
    List<Plugin> outdatedPlugins = new ArrayList<Plugin>();
    for (Plugin plugin : server.getPluginManager().getPlugins()) {
        String pluginVersion = server.getPluginManager().getMinServerVersion(plugin);
        if (pluginVersion != null && pluginVersion.compareTo(version.getVersionString()) > 0) {
            outdatedPlugins.add(plugin);
        }
    }

    if (outdatedPlugins.size() > 0) {

      out.write("\n    <div class=\"light-gray-border\" style=\"padding:10px;\">\n    <p>");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("<a href=\"http://www.igniterealtime.org/projects/openfire/\" target=\"_blank\">");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("</a></p>\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n\n\n        ");

            PluginManager pluginManager = server.getPluginManager();
            for (Plugin plugin : outdatedPlugins) {
                String pluginName = pluginManager.getName(plugin);
                String pluginDescription = pluginManager.getDescription(plugin);
                String pluginAuthor = pluginManager.getAuthor(plugin);
                String pluginVersion = pluginManager.getVersion(plugin);
                File pluginDir = pluginManager.getPluginDirectory(plugin);
                File icon = new File(pluginDir, "logo_small.png");
                boolean readmeExists = new File(pluginDir, "readme.html").exists();
                boolean changelogExists = new File(pluginDir, "changelog.html").exists();
                if (!icon.exists()) {
                    icon = new File(pluginDir, "logo_small.gif");
                }
        
      out.write("\n        <tr>\n            <td class=\"line-bottom-border\" width=\"1%\">\n                ");
 if (icon.exists()) { 
      out.write("\n                <img src=\"/geticon?plugin=");
      out.print( URLEncoder.encode(pluginDir.getName(), "utf-8") );
      out.write("&showIcon=true&decorator=none\" width=\"16\" height=\"16\" alt=\"Plugin\">\n                ");
 }
                else { 
      out.write("\n                <img src=\"images/plugin-16x16.gif\" width=\"16\" height=\"16\" alt=\"Plugin\">\n                ");
 } 
      out.write("\n            </td>\n            <td class=\"line-bottom-border\" width=\"1%\" nowrap>\n                ");
      out.print( pluginName);
      out.write("\n            </td>\n            <td nowrap class=\"line-bottom-border\">\n                <p>");
 if (readmeExists) { 
      out.write("\n                    <a href=\"plugin-admin.jsp?plugin=");
      out.print( URLEncoder.encode(pluginDir.getName(), "utf-8") );
      out.write("&showReadme=true&decorator=none\"\n                        ><img src=\"images/doc-readme-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"README\"></a>\n                    ");
 }
                    else { 
      out.write(" &nbsp; ");
 } 
      out.write("\n                    ");
 if (changelogExists) { 
      out.write("\n                    <a href=\"plugin-admin.jsp?plugin=");
      out.print( URLEncoder.encode(pluginDir.getName(), "utf-8") );
      out.write("&showChangelog=true&decorator=none\"\n                        ><img src=\"images/doc-changelog-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"changelog\"></a>\n                    ");
 }
                    else { 
      out.write(" &nbsp; ");
 } 
      out.write("</p>\n            </td>\n            <td class=\"line-bottom-border\">\n                ");
      out.print( StringUtils.escapeHTMLTags(pluginDescription) );
      out.write("\n            </td>\n            <td class=\"line-bottom-border\">\n                ");
      out.print( StringUtils.escapeHTMLTags(pluginVersion) );
      out.write("\n            </td>\n            <td class=\"line-bottom-border\">\n                ");
      out.print( StringUtils.escapeHTMLTags(pluginAuthor) );
      out.write("\n            </td>\n        </tr>\n        ");
 }
      out.write("\n  </table>\n\n        ");
} 
      out.write("\n\n</div>\n<br/>\n ");

        if(time != null){
            Date date = new Date(Long.parseLong(time));
            time = JiveGlobals.formatDate(date);
        }
    
      out.write("\n       <p>\n           ");
 if(time != null) { 
      out.write("\n        ");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write(' ');
      out.print( time);
      out.write(".\n           ");
 } 
      out.write("\n           ");
 if(updateManager.isServiceEnabled()){
      out.write("\n              ");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("\n           ");
 } else { 
      out.write("\n                ");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("\n           ");
 } 
      out.write("\n           &nbsp;<span id=\"reloader2\"><a href=\"javascript:updatePluginsListNow()\">");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("</a></span>\n        </p>\n           ");
 } 
      out.write("\n\n</body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("plugin.available.title");
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
    _jspx_th_fmt_message_1.setKey("plugin.available.cancel.redirect");
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
    _jspx_th_fmt_message_2.setKey("plugin.available.info");
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
    _jspx_th_fmt_message_3.setKey("plugin.available.no.list");
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
    _jspx_th_fmt_message_4.setKey("plugin.available.list");
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
    _jspx_th_fmt_message_5.setKey("plugin.available.no.list.description");
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
    _jspx_th_fmt_message_6.setKey("plugin.available.auto.update.currently");
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
    _jspx_th_fmt_message_7.setKey("plugin.available.auto.update.currently.disabled");
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
    _jspx_th_fmt_message_8.setKey("plugin.available.click.here");
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
    _jspx_th_fmt_message_9.setKey("plugin.available.change");
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
    _jspx_th_fmt_message_10.setKey("plugin.available.error.downloading");
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
    _jspx_th_fmt_message_11.setKey("plugin.available.open_source");
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
    _jspx_th_fmt_message_12.setKey("plugin.available.description");
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
    _jspx_th_fmt_message_13.setKey("plugin.available.version");
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
    _jspx_th_fmt_message_14.setKey("plugin.available.author");
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
    _jspx_th_fmt_message_15.setKey("plugin.available.install");
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
    _jspx_th_fmt_message_16.setKey("plugin.available.no_plugin");
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
    _jspx_th_fmt_message_17.setKey("plugin.available.download");
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
    _jspx_th_fmt_message_18.setKey("plugin.available.installation.success");
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
    _jspx_th_fmt_message_19.setKey("plugin.available.commercial_plugins");
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
    _jspx_th_fmt_message_20.setKey("plugin.available.download");
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
    _jspx_th_fmt_message_21.setKey("plugin.available.installation.success");
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
    _jspx_th_fmt_message_22.setKey("plugin.available.outdated");
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
    _jspx_th_fmt_message_23.setKey("plugin.available.outdated.update");
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
    _jspx_th_fmt_message_24.setKey("plugin.available.autoupdate");
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
    _jspx_th_fmt_message_25.setKey("plugin.available.autoupdate.on");
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
    _jspx_th_fmt_message_26.setKey("plugin.available.autoupdate.off");
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
    _jspx_th_fmt_message_27.setKey("plugin.available.manual.update");
    int _jspx_eval_fmt_message_27 = _jspx_th_fmt_message_27.doStartTag();
    if (_jspx_th_fmt_message_27.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_27);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_27);
    return false;
  }
}
