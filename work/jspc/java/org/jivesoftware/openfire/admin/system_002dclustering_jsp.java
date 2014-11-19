package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.cluster.ClusterManager;
import org.jivesoftware.openfire.cluster.ClusterNodeInfo;
import org.jivesoftware.openfire.cluster.GetBasicStatistics;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.cache.CacheFactory;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.net.URLEncoder;
import org.jivesoftware.util.Base64;

public final class system_002dclustering_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\r');
      out.write('\n');
 webManager.init(request, response, session, application, out ); 
      out.write("\r\n\r\n<html>\r\n<head>\r\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n<meta name=\"pageID\" content=\"system-clustering\"/>\r\n<style type=\"text/css\">\r\n.jive-contentBox .local {\r\n    background-color: #ffc;\r\n    }\r\n</style>\r\n</head>\r\n<body>\r\n\r\n");
 // Get parameters
    boolean update = request.getParameter("update") != null;
    boolean clusteringEnabled = ParamUtils.getBooleanParameter(request, "clusteringEnabled");
    boolean updateSucess = false;

    if (update) {
        if (!clusteringEnabled) {
            ClusterManager.setClusteringEnabled(false);
            // Log the event
            webManager.logEvent("disabled clustering", null);
            updateSucess = true;
        }
        else {
            if (ClusterManager.isClusteringAvailable()) {
                ClusterManager.setClusteringEnabled(true);
                // Log the event
                webManager.logEvent("enabled clustering", null);
                updateSucess = ClusterManager.isClusteringStarted();
            }
            else {
                Log.error("Failed to enable clustering. Clustering is not installed.");
            }
        }
    }

    boolean usingEmbeddedDB = DbConnectionManager.isEmbeddedDB();
    boolean clusteringAvailable = !usingEmbeddedDB && ClusterManager.isClusteringAvailable();
    int maxClusterNodes = ClusterManager.getMaxClusterNodes();
    clusteringEnabled = ClusterManager.isClusteringStarted() || ClusterManager.isClusteringStarting();

    Collection<ClusterNodeInfo> clusterNodesInfo = ClusterManager.getNodesInfo();
    // Get some basic statistics from the cluster nodes
    // TODO Set a timeout so the page can load fast even if a node is taking too long to answer
    Collection<Object> statistics =
            CacheFactory.doSynchronousClusterTask(new GetBasicStatistics(), true);
    // Calculate percentages
    int clients = 0;
    int incoming = 0;
    int outgoing = 0;
    for (Object stat : statistics) {
        Map<String, Object> statsMap = (Map<String, Object>) stat;
        if (statsMap == null) {
            continue;
        }
        clients += (Integer) statsMap.get(GetBasicStatistics.CLIENT);
        incoming += (Integer) statsMap.get(GetBasicStatistics.INCOMING);
        outgoing += (Integer) statsMap.get(GetBasicStatistics.OUTGOING);
    }
    for (Object stat : statistics) {
        Map<String, Object> statsMap = (Map<String, Object>) stat;
        if (statsMap == null) {
            continue;
        }
        int current = (Integer) statsMap.get(GetBasicStatistics.CLIENT);
        int percentage = clients == 0 ? 0 : current * 100 / clients;
        statsMap.put(GetBasicStatistics.CLIENT, current + " (" + Math.round(percentage) + "%)");
        current = (Integer) statsMap.get(GetBasicStatistics.INCOMING);
        percentage = incoming == 0 ? 0 : current * 100 / incoming;
        statsMap.put(GetBasicStatistics.INCOMING, current + " (" + Math.round(percentage) + "%)");
        current = (Integer) statsMap.get(GetBasicStatistics.OUTGOING);
        percentage = outgoing == 0 ? 0 : current * 100 / outgoing;
        statsMap.put(GetBasicStatistics.OUTGOING, current + " (" + Math.round(percentage) + "%)");
    }

      out.write("\r\n\r\n<p>\r\n");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\r\n</p>\r\n\r\n");
  if (update) {
        if (updateSucess) { 
      out.write("\r\n\r\n    <div class=\"jive-success\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n        <td class=\"jive-icon-label\">\r\n        ");
 if (ClusterManager.isClusteringStarted()) { 
      out.write("\r\n            ");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\r\n        ");
 } else { 
      out.write("\r\n            ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\r\n        ");

            }
        
      out.write("\r\n        </td></tr>\r\n    </tbody>\r\n    </table>\r\n    </div><br>\r\n\r\n");
  } else { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n            <td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/></td>\r\n            <td class=\"jive-icon-label\">\r\n                ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\r\n            </td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n    <br>\r\n");
  }
} else if (!clusteringAvailable) {

      out.write("\r\n    <div class=\"warning\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" >\r\n    <tbody>\r\n        <tr>\r\n            <td class=\"jive-icon-label\">\r\n            <b>");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</b><br/><br/>\r\n            </td>\r\n        </tr>\r\n        <td valign=\"top\" align=\"left\" colspan=\"2\">\r\n            ");
 if (usingEmbeddedDB) { 
      out.write("\r\n                <span>");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</span>\r\n            ");
 } else if (maxClusterNodes == 0) { 
      out.write("\r\n                <span>");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</span>\r\n            ");
 } else { 
      out.write("\r\n                <span>");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</span>\r\n            ");
 } 
      out.write("\r\n        </td>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n    <br>\r\n");
 } 
      out.write(" \r\n\r\n<!-- BEGIN 'Clustering Enabled' -->\r\n<form action=\"system-clustering.jsp\" method=\"post\">\r\n\t<div class=\"jive-contentBoxHeader\">\r\n\t\t");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\r\n\t</div>\r\n\t<div class=\"jive-contentBox\">\r\n\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n\t\t<tbody>\r\n\t\t\t<tr>\r\n\t\t\t\t<td width=\"1%\" valign=\"top\" nowrap>\r\n\t\t\t\t\t<input type=\"radio\" name=\"clusteringEnabled\" value=\"false\" id=\"rb01\"\r\n\t\t\t\t\t ");
      out.print( (!clusteringEnabled ? "checked" : "") );
      out.write(' ');
      out.print( clusteringAvailable ? "" : "disabled" );
      out.write(">\r\n\t\t\t\t</td>\r\n\t\t\t\t<td width=\"99%\">\r\n\t\t\t\t\t<label for=\"rb01\">\r\n\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\r\n\t\t\t\t\t</label>\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t\t<tr>\r\n\t\t\t\t<td width=\"1%\" valign=\"top\" nowrap>\r\n\t\t\t\t\t<input type=\"radio\" name=\"clusteringEnabled\" value=\"true\" id=\"rb02\"\r\n\t\t\t\t\t ");
      out.print( (clusteringEnabled ? "checked" : "") );
      out.write(' ');
      out.print( clusteringAvailable ? "" : "disabled" );
      out.write(">\r\n\t\t\t\t</td>\r\n\t\t\t\t<td width=\"99%\">\r\n\t\t\t\t\t<label for=\"rb02\">\r\n\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write(" <b>");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</b> \r\n\t\t\t\t\t</label>\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t</tbody>\r\n\t\t</table>\r\n        <br/>\r\n        ");
 if (clusteringAvailable) { 
      out.write("\r\n        <input type=\"submit\" name=\"update\" value=\"");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("\">\r\n        ");
 } 
      out.write("\r\n    </div>\r\n</form>\r\n<!-- END 'Clustering Enabled' -->\r\n<br>\r\n<div class=\"jive-contentBoxHeader\">\r\n    ");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\r\n</div>\r\n<div class=\"jive-contentBox\">\r\n    <p>\r\n        ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_17 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_17.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_17.setParent(null);
      _jspx_th_fmt_message_17.setKey("system.clustering.overview.info");
      int _jspx_eval_fmt_message_17 = _jspx_th_fmt_message_17.doStartTag();
      if (_jspx_eval_fmt_message_17 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_17 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_17.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_17.doInitBody();
        }
        do {
          out.write("\r\n            ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_17);
          _jspx_th_fmt_param_0.setValue( clusterNodesInfo.size() );
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          out.write("\r\n            ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_1 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_1.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_17);
          _jspx_th_fmt_param_1.setValue( maxClusterNodes );
          int _jspx_eval_fmt_param_1 = _jspx_th_fmt_param_1.doStartTag();
          if (_jspx_th_fmt_param_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
          out.write("\r\n            ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_2 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_2.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_17);
          _jspx_th_fmt_param_2.setValue( "<span style='background-color:#ffc;'>" );
          int _jspx_eval_fmt_param_2 = _jspx_th_fmt_param_2.doStartTag();
          if (_jspx_th_fmt_param_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_2);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_2);
          out.write("\r\n            ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_3 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_3.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_17);
          _jspx_th_fmt_param_3.setValue( "</span>" );
          int _jspx_eval_fmt_param_3 = _jspx_th_fmt_param_3.doStartTag();
          if (_jspx_th_fmt_param_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_3);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_3);
          out.write("\r\n        ");
          int evalDoAfterBody = _jspx_th_fmt_message_17.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_17 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_17);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_17);
      out.write("\r\n    </p>\r\n\r\n      <table cellpadding=\"3\" cellspacing=\"2\" border=\"0\">\r\n          <thead>\r\n              <tr>\r\n                  <th colspan=\"2\">\r\n                      ");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("\r\n                  </th>\r\n                  <th>\r\n                      ");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("\r\n                  </th>\r\n                  <th style=\"text-align:center;\">\r\n                      ");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("\r\n                  </th>\r\n                  <th style=\"text-align:center;\">\r\n                      ");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("\r\n                  </th>\r\n                  <th style=\"text-align:center;\">\r\n                      ");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("\r\n                  </th>\r\n                  <th style=\"text-align:center;\">\r\n                      ");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("\r\n                  </th>\r\n                  <th width=\"90%\" class=\"last\">&nbsp;</th>\r\n              </tr>\r\n          </thead>\r\n          <tbody>\r\n            ");
 if (!clusterNodesInfo.isEmpty()) {
                for (ClusterNodeInfo nodeInfo : clusterNodesInfo) {
                    boolean isLocalMember =
                            XMPPServer.getInstance().getNodeID().equals(nodeInfo.getNodeID());
                    String nodeID = Base64.encodeBytes(nodeInfo.getNodeID().toByteArray(), Base64.URL_SAFE);
                    Map<String, Object> nodeStats = null;
                    for (Object stat : statistics) {
                        Map<String, Object> statsMap = (Map<String, Object>) stat;
                        if (statsMap == null) {
                            continue;
                        }
                        if (Arrays.equals((byte[]) statsMap.get(GetBasicStatistics.NODE),
                                nodeInfo.getNodeID().toByteArray())) {
                            nodeStats = statsMap;
                            break;
                        }
                    }
            
      out.write("\r\n              <tr class=\"");
      out.print( (isLocalMember ? "local" : "") );
      out.write("\" valign=\"middle\">\r\n                  <td align=\"center\" width=\"1%\">\r\n                      <a href=\"plugins/");
      out.print( CacheFactory.getPluginName() );
      out.write("/system-clustering-node.jsp?UID=");
      out.print( URLEncoder.encode(nodeID) );
      out.write("\"\r\n                       title=\"Click for more details\"\r\n                       ><img src=\"images/server-network-24x24.gif\" width=\"24\" height=\"24\" border=\"0\" alt=\"\"></a>\r\n                  </td>\r\n                  <td class=\"jive-description\" nowrap width=\"1%\" valign=\"middle\">\r\n                      <a href=\"plugins/");
      out.print( CacheFactory.getPluginName() );
      out.write("/system-clustering-node.jsp?UID=");
      out.print( URLEncoder.encode(nodeID) );
      out.write("\">\r\n                      ");
  if (isLocalMember) { 
      out.write("\r\n                          <b>");
      out.print( nodeInfo.getHostName() );
      out.write("</b>\r\n                      ");
  } else { 
      out.write("\r\n                          ");
      out.print( nodeInfo.getHostName() );
      out.write("\r\n                      ");
  } 
      out.write("</a>\r\n                      <br />\r\n                      ");
      out.print( nodeInfo.getNodeID() );
      out.write("\r\n                  </td>\r\n                  <td class=\"jive-description\" nowrap width=\"1%\" valign=\"middle\">\r\n                      ");
      out.print( JiveGlobals.formatDateTime(new Date(nodeInfo.getJoinedTime())) );
      out.write("\r\n                  </td>\r\n                  <td class=\"jive-description\" nowrap width=\"1%\" valign=\"middle\">\r\n                      ");
      out.print( nodeStats != null ? nodeStats.get(GetBasicStatistics.CLIENT) : "N/A" );
      out.write("\r\n                  </td>\r\n                  <td class=\"jive-description\" nowrap width=\"1%\" valign=\"middle\">\r\n                      ");
      out.print( nodeStats != null ? nodeStats.get(GetBasicStatistics.INCOMING) : "N/A" );
      out.write("\r\n                  </td>\r\n                  <td class=\"jive-description\" nowrap width=\"1%\" valign=\"middle\">\r\n                      ");
      out.print( nodeStats != null ? nodeStats.get(GetBasicStatistics.OUTGOING) : "N/A" );
      out.write("\r\n                  </td>\r\n                  <td class=\"jive-description\" nowrap width=\"75%\" valign=\"middle\">\r\n                  <table width=\"100%\">\r\n                    <tr>\r\n                      ");

                          int percent = 0;
                          String memory = "N/A";
                          if (nodeStats != null) {
                              double usedMemory = (Double) nodeStats.get(GetBasicStatistics.MEMORY_CURRENT);
                              double maxMemory = (Double) nodeStats.get(GetBasicStatistics.MEMORY_MAX);
                              double percentFree = ((maxMemory - usedMemory) / maxMemory) * 100.0;
                              percent = 100-(int)Math.round(percentFree);
                                DecimalFormat mbFormat = new DecimalFormat("#0.00");
                                memory = mbFormat.format(usedMemory) + " MB of " + mbFormat.format(maxMemory) + " MB used";
                          }
                      
      out.write("\r\n                        <td width=\"30%\">\r\n                          <div class=\"bar\">\r\n                          <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border:1px #666 solid;\">\r\n                          <tr>\r\n                              ");
  if (percent == 0) { 
      out.write("\r\n\r\n                                  <td width=\"100%\"><img src=\"images/percent-bar-left.gif\" width=\"100%\" height=\"4\" border=\"0\" alt=\"\"></td>\r\n\r\n                              ");
  } else { 
      out.write("\r\n\r\n                                  ");
  if (percent >= 90) { 
      out.write("\r\n\r\n                                      <td width=\"");
      out.print( percent );
      out.write("%\" background=\"images/percent-bar-used-high.gif\"\r\n                                          ><img src=\"images/blank.gif\" width=\"1\" height=\"4\" border=\"0\" alt=\"\"></td>\r\n\r\n                                  ");
  } else { 
      out.write("\r\n\r\n                                      <td width=\"");
      out.print( percent );
      out.write("%\" background=\"images/percent-bar-used-low.gif\"\r\n                                          ><img src=\"images/blank.gif\" width=\"1\" height=\"4\" border=\"0\" alt=\"\"></td>\r\n\r\n                                  ");
  } 
      out.write("\r\n                                  <td width=\"");
      out.print( (100-percent) );
      out.write("%\" background=\"images/percent-bar-left.gif\"\r\n                                      ><img src=\"images/blank.gif\" width=\"1\" height=\"4\" border=\"0\" alt=\"\"></td>\r\n                              ");
  } 
      out.write("\r\n                          </tr>\r\n                          </table>\r\n                          </div>\r\n                        </td>\r\n                        <td class=\"jive-description\">\r\n                          ");
      out.print( memory );
      out.write("\r\n                        </td>\r\n                      </tr>\r\n                    </table>\r\n                  </td>\r\n                  <td width=\"20%\">&nbsp;</td>\r\n              </tr>\r\n              ");
 }
              } else if (ClusterManager.isClusteringStarting()) { 
      out.write("\r\n              <tr valign=\"middle\" align=\"middle\" class=\"local\">\r\n                  <td colspan=8>\r\n                      ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_24 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_24.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_24.setParent(null);
      _jspx_th_fmt_message_24.setKey("system.clustering.starting");
      int _jspx_eval_fmt_message_24 = _jspx_th_fmt_message_24.doStartTag();
      if (_jspx_eval_fmt_message_24 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_24 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_24.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_24.doInitBody();
        }
        do {
          out.write("\r\n                          ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_4 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_4.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_24);
          _jspx_th_fmt_param_4.setValue( "<a href='system-clustering.jsp'>" );
          int _jspx_eval_fmt_param_4 = _jspx_th_fmt_param_4.doStartTag();
          if (_jspx_th_fmt_param_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_4);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_4);
          out.write("\r\n                          ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_5 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_5.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_24);
          _jspx_th_fmt_param_5.setValue( "</a>" );
          int _jspx_eval_fmt_param_5 = _jspx_th_fmt_param_5.doStartTag();
          if (_jspx_th_fmt_param_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_5);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_5);
          out.write("\r\n                      ");
          int evalDoAfterBody = _jspx_th_fmt_message_24.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_24 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_24);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_24);
      out.write("\r\n                  </td>\r\n              </tr>\r\n              ");
 } 
      out.write("\r\n        </tbody>\r\n        </table>\r\n</div>\r\n\r\n\r\n</body>\r\n</html>\r\n");
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
    _jspx_th_fmt_message_0.setKey("system.clustering.title");
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
    _jspx_th_fmt_message_1.setKey("system.clustering.info");
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
    _jspx_th_fmt_message_2.setKey("system.clustering.enabled");
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
    _jspx_th_fmt_message_3.setKey("system.clustering.disabled");
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
    _jspx_th_fmt_message_4.setKey("system.clustering.failed-start");
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
    _jspx_th_fmt_message_5.setKey("system.clustering.not-available");
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
    _jspx_th_fmt_message_6.setKey("system.clustering.using-embedded-db");
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
    _jspx_th_fmt_message_7.setKey("system.clustering.not-installed");
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
    _jspx_th_fmt_message_8.setKey("system.clustering.not-valid-license");
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
    _jspx_th_fmt_message_9.setKey("system.clustering.enabled.legend");
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
    _jspx_th_fmt_message_10.setKey("system.clustering.label_disable");
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
    _jspx_th_fmt_message_11.setKey("system.clustering.label_disable_info");
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
    _jspx_th_fmt_message_12.setKey("system.clustering.label_enable");
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
    _jspx_th_fmt_message_13.setKey("system.clustering.label_enable_info");
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
    _jspx_th_fmt_message_14.setKey("system.clustering.label_enable_info2");
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
    _jspx_th_fmt_message_15.setKey("global.save_settings");
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
    _jspx_th_fmt_message_16.setKey("system.clustering.overview.label");
    int _jspx_eval_fmt_message_16 = _jspx_th_fmt_message_16.doStartTag();
    if (_jspx_th_fmt_message_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_16);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_16);
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
    _jspx_th_fmt_message_18.setKey("system.clustering.overview.node");
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
    _jspx_th_fmt_message_19.setKey("system.clustering.overview.joined");
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
    _jspx_th_fmt_message_20.setKey("system.clustering.overview.clients");
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
    _jspx_th_fmt_message_21.setKey("system.clustering.overview.incoming_servers");
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
    _jspx_th_fmt_message_22.setKey("system.clustering.overview.outgoing_servers");
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
    _jspx_th_fmt_message_23.setKey("system.clustering.overview.memory");
    int _jspx_eval_fmt_message_23 = _jspx_th_fmt_message_23.doStartTag();
    if (_jspx_th_fmt_message_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
    return false;
  }
}
