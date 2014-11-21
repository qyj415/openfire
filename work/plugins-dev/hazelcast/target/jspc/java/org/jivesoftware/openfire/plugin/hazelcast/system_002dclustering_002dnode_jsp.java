package org.jivesoftware.openfire.plugin.hazelcast;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.jivesoftware.util.cluster.NodeRuntimeStats;
import org.jivesoftware.util.cache.CacheFactory;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.Cluster;
import com.hazelcast.core.Member;
import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import org.jivesoftware.openfire.cluster.ClusterManager;
import org.jivesoftware.openfire.cluster.NodeID;
import org.jivesoftware.openfire.cluster.ClusterNodeInfo;
import org.jivesoftware.util.*;
import org.jivesoftware.util.cache.Cache;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.LinkedList;

public final class system_002dclustering_002dnode_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
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
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n<html>\r\n<head>\r\n    <title>Cluster Node Information</title>\r\n    <meta name=\"pageID\" content=\"system-clustering\"/>\r\n    <meta http-equiv=\"refresh\" content=\"10\" >\r\n    <style type=\"text/css\">\r\n    .warning {\r\n        color : #f00;\r\n        font-weight : bold;\r\n    }\r\n    .jive-stats .jive-table THEAD TH, .jive-stats .jive-table TBODY TD {\r\n        border-right : 1px #ccc solid;\r\n        text-align : center;\r\n    }\r\n    .jive-stats .jive-table .c6c7c8, .jive-stats .jive-table .c8, .jive-stats .jive-table TBODY .c8 {\r\n        border-right : 0px;\r\n    }\r\n    .jive-stats .jive-table TBODY TD TABLE TD {\r\n        border : 0px;\r\n    }\r\n\r\n    .jive-info .c1 {\r\n        width : 30%;\r\n    }\r\n    .jive-info .c2 {\r\n        width : 25%;\r\n    }\r\n    .jive-info .c3 {\r\n        width : 15%;\r\n        text-align : center;\r\n    }\r\n    .jive-info .c4 {\r\n        width : 30%;\r\n    }\r\n    </style>\r\n</head>\r\n\r\n<body>\r\n\r\n");
 // Is clustering enabled? If not, redirect back to the cache page
    if (!ClusterManager.isClusteringStarted()) {
        response.sendRedirect("../../system-clustering.jsp");
        return;
    }

    // get parameters
    boolean clear = request.getParameter("clear") != null;
    String uid = ParamUtils.getParameter(request, "UID");

    // Clear the cache stats if requested
    if (clear) {
        NodeRuntimeStats.clearCacheStats();
        response.sendRedirect("system-clustering-node.jsp?UID=" + uid);
        return;
    }

	List<ClusterNodeInfo> members = (List<ClusterNodeInfo>) CacheFactory.getClusterNodesInfo();
    Map<NodeID, NodeRuntimeStats.NodeInfo> nodeInfoMap = NodeRuntimeStats.getNodeInfo();

    // Sort it according to name
    Collections.sort(members, new Comparator<ClusterNodeInfo>() {
        public int compare(ClusterNodeInfo member1, ClusterNodeInfo member2) {
            String name1 = member1.getHostName() + " (" + member1.getNodeID() + ")";
            String name2 = member2.getHostName() + " (" + member2.getNodeID() + ")";
            return name1.toLowerCase().compareTo(name2.toLowerCase().toLowerCase());
        }
    });

    // If no UID was used, use the UID from the first member in the member list
    byte[] byteArray;
    if (uid == null) {
        byteArray = members.get(0).getNodeID().toByteArray();
    } else {
        byteArray = Base64.decode(uid, Base64.URL_SAFE);
    }

    // Get the specific member requested
    ClusterNodeInfo member = null;
    for (int i = 0; i < members.size(); i++) {
        ClusterNodeInfo m = members.get(i);
        if (Arrays.equals(byteArray, m.getNodeID().toByteArray())) {
            member = m;
            break;
        }
    }

    if (member == null) {
        Log.warn("Node not found: " + uid);
        for (int i = 0; i < members.size(); i++) {
            ClusterNodeInfo m = members.get(i);
            Log.warn("Available members: " + m.getNodeID().toString());
        }

        response.sendRedirect("../../system-clustering.jsp");
        return;
    }

    // Get the cache stats object:
    Map cacheStats = Hazelcast.getHazelcastInstanceByName("openfire").getMap("opt-$cacheStats");

    // Decimal formatter for numbers
    DecimalFormat decFormat = new DecimalFormat("#,##0.0");
    NumberFormat numFormat = NumberFormat.getInstance();
    DecimalFormat mbFormat = new DecimalFormat("#0.00");
    DecimalFormat percentFormat = new DecimalFormat("#0.0");

    // Get the list of existing caches
    Cache[] caches = webManager.getCaches();
    String[] cacheNames = new String[caches.length];
    for (int i = 0; i < caches.length; i++) {
        cacheNames[i] = caches[i].getName();
    }

      out.write("\r\n\r\n<p>\r\nBelow you will find statistic information for the selected node. This page will be automatically\r\nrefreshed every 10 seconds.\r\n</p>\r\n\r\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n<tr>\r\n    <td width=\"99%\">\r\n        &nbsp;\r\n    </td>\r\n    <td width=\"1%\" nowrap=\"nowrap\">\r\n        <a href=\"../../system-clustering.jsp\">&laquo; Back to cluster summary</a>\r\n    </td>\r\n</tr>\r\n</table>\r\n\r\n<br />\r\n\r\n<div class=\"jive-stats\">\r\n<div class=\"jive-table\">\r\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n<thead>\r\n    <tr>\r\n        <th rowspan=\"2\" class=\"c1\">Node</th>\r\n        <th rowspan=\"2\" class=\"c2\">Memory Usage</th>\r\n        <th colspan=\"3\" class=\"c3c4c5\">Incoming Packets</th>\r\n        <th colspan=\"3\" class=\"c6c7c8\">Outgoing Packets</th>\r\n    </tr>\r\n    <tr>\r\n        <th class=\"c3\" colspan=\"2\">Packets Received</th>\r\n        <th class=\"c5\">Success</th>\r\n        <th class=\"c6\">CPU</th>\r\n        <th class=\"c7\">Throughput</th>\r\n        <th class=\"c8\">Success</th>\r\n    </tr>\r\n</thead>\r\n");
      out.write("\r\n<tbody>\r\n\r\n");
  for (int i=0; i<members.size(); i++) {
        ClusterNodeInfo m = members.get(i);
        if (member != m) {
            continue;
        }
        NodeRuntimeStats.NodeInfo nodeInfo = nodeInfoMap.get(m.getNodeID());

      out.write("\r\n    <tr bgcolor=\"#ffffcc\">\r\n\r\n        <td nowrap class=\"c1\">\r\n            ");
      out.print( m.getHostName() );
      out.write("<br/>");
      out.print( m.getNodeID() );
      out.write("\r\n        </td>\r\n\r\n        <td class=\"c2\" valign=\"middle\">\r\n            ");
  double freeMem = (double)nodeInfo.getFreeMem()/(1024.0*1024.0);
                double maxMem = (double)nodeInfo.getMaxMem()/(1024.0*1024.0);
                double totalMem = (double)nodeInfo.getTotalMem()/(1024.0*1024.0);
                double usedMem = totalMem - freeMem;
                double percentFree = ((maxMem - usedMem)/maxMem)*100.0;
                double percentUsed = 100.0 - percentFree;
                int percent = 100-(int)Math.round(percentFree);
            
      out.write("\r\n\r\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"250\">\r\n            <tr>\r\n                <td width=\"99%\">\r\n                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border:1px #666 solid;\">\r\n                    <tr>\r\n                        ");
  if (percent == 0) { 
      out.write("\r\n\r\n                            <td width=\"100%\" style=\"padding:0px;\"><img src=\"../../images/percent-bar-left.gif\" width=\"100%\" height=\"4\" border=\"0\" alt=\"\"></td>\r\n\r\n                        ");
  } else { 
      out.write("\r\n\r\n                            ");
  if (percent >= 90) { 
      out.write("\r\n\r\n                                <td width=\"");
      out.print( percent );
      out.write("%\" background=\"../../images/percent-bar-used-high.gif\" style=\"padding:0px;\"\r\n                                    ><img src=\"images/blank.gif\" width=\"1\" height=\"4\" border=\"0\" alt=\"\"></td>\r\n\r\n                            ");
  } else { 
      out.write("\r\n\r\n                                <td width=\"");
      out.print( percent );
      out.write("%\" background=\"../../images/percent-bar-used-low.gif\" style=\"padding:0px;\"\r\n                                    ><img src=\"images/blank.gif\" width=\"1\" height=\"4\" border=\"0\" alt=\"\"></td>\r\n\r\n                            ");
  } 
      out.write("\r\n\r\n                            <td width=\"");
      out.print( (100-percent) );
      out.write("%\" background=\"../../images/percent-bar-left.gif\" style=\"padding:0px;\"\r\n                                ><img src=\"images/blank.gif\" width=\"1\" height=\"4\" border=\"0\" alt=\"\"></td>\r\n\r\n                        ");
  } 
      out.write("\r\n                    </tr>\r\n                    </table>\r\n                </td>\r\n                <td width=\"1%\" nowrap=\"nowrap\">\r\n                    ");
      out.print( mbFormat.format(totalMem) );
      out.write(" MB, ");
      out.print( decFormat.format(percentUsed) );
      out.write("% used\r\n                </td>\r\n           </tr>\r\n           </table>\r\n\r\n        </td>\r\n        <td class=\"c3\" colspan=\"2\">\r\n            \r\n        </td>\r\n\r\n        <td class=\"c5\">\r\n            \r\n        </td>\r\n        <td class=\"c6\">\r\n           \r\n        </td>\r\n        <td class=\"c7\">\r\n           \r\n        </td>\r\n        <td class=\"c8\">\r\n            \r\n        </td>\r\n    </tr>\r\n\r\n");
  } 
      out.write("\r\n\r\n</tbody>\r\n\r\n</table>\r\n</div>\r\n</div>\r\n\r\n\r\n<br/>\r\n\r\n[<a href=\"system-clustering-node.jsp?clear=true&UID=");
      out.print(uid);
      out.write("\">Clear Cache Stats</a>]\r\n\r\n<br /><br />\r\n\r\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n<tr>\r\n    <td width=\"1%\"><img src=\"images/server-network-48x48.gif\" width=\"48\" height=\"48\" border=\"0\" alt=\"\" hspace=\"10\"></td>\r\n    <td width=\"99%\">\r\n        <span style=\"font-size:1.1em;\"><b>Node Details: ");
      out.print( member.getHostName() );
      out.write(' ');
      out.write('(');
      out.print( member.getNodeID() );
      out.write(")</b></span>\r\n        <br />\r\n        <span style=\"font-size:0.9em;\">\r\n        Joined: ");
      out.print( JiveGlobals.formatDateTime(new Date(member.getJoinedTime())) );
      out.write("\r\n        </span>\r\n    </td>\r\n</tr>\r\n</table>\r\n\r\n<p>\r\nCache statistics for this cluster node appear below.\r\n</p>\r\n\r\n<div class=\"jive-info\">\r\n<div class=\"jive-table\">\r\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n<thead>\r\n    <tr>\r\n        <th class=\"c1\">Cache Type</th>\r\n        <th class=\"c2\">Size</th>\r\n        <th class=\"c3\">Objects</th>\r\n        <th class=\"c4\">Effectiveness</th>\r\n    </tr>\r\n</thead>\r\n\r\n<tbody>\r\n\r\n");
 Map cNames = (Map) cacheStats.get(member.getNodeID().toString());
    if (cNames == null) {

      out.write("\r\n    <tr>\r\n    <td align=\"center\" colspan=\"4\"><i>No stats available</i></td>\r\n    </tr>\r\n\r\n");
 } else {
    // Iterate through the cache names,
    for (String cacheName : cacheNames) {
        long[] theStats = (long[]) cNames.get(cacheName);
        // Skip caches that are in this JVM but not in other nodes
        if (theStats == null) {
            continue;
        }
        long size = theStats[0];
        long maxSize = theStats[1];
        long numObjects = theStats[2];

        double memUsed = (double) size / (1024 * 1024);
        double totalMem = (double) maxSize / (1024 * 1024);
        double freeMem = 100 - 100 * memUsed / Math.max(1, totalMem);
        double usedMem = 100 * memUsed / Math.max(1, totalMem);
        long hits = theStats[3];
        long misses = theStats[4];
        double hitPercent = 0.0;
        if (hits + misses == 0) {
            hitPercent = 0.0;
        } else {
            hitPercent = 100 * (double) hits / (hits + misses);
        }
        boolean lowEffec = (hits > 500 && hitPercent < 85.0 && freeMem < 20.0);

      out.write("\r\n    <tr>\r\n        <td class=\"c1\">\r\n            ");
      out.print( cacheName );
      out.write("\r\n        </td>\r\n        <td class=\"c2\">\r\n\r\n            ");
 if (maxSize != -1 && maxSize != Integer.MAX_VALUE) { 
      out.write("\r\n            ");
      out.print( mbFormat.format(totalMem) );
      out.write(" MB,\r\n            ");
      out.print( percentFormat.format(usedMem));
      out.write("% used\r\n            ");
 } else { 
      out.write("\r\n            Unlimited\r\n            ");
 } 
      out.write("\r\n\r\n        </td>\r\n        <td class=\"c3\">\r\n\r\n            ");
      out.print( LocaleUtils.getLocalizedNumber(numObjects) );
      out.write("\r\n\r\n        </td>\r\n        <td class=\"c4\">\r\n\r\n            ");
 if (lowEffec) { 
      out.write("\r\n            <font color=\"#ff0000\"><b>");
      out.print( percentFormat.format(hitPercent));
      out.write("%</b>\r\n                ");
  } else { 
      out.write("\r\n                <b>");
      out.print( percentFormat.format(hitPercent));
      out.write("%</b>\r\n                ");
  } 
      out.write("\r\n                (");
      out.print( LocaleUtils.getLocalizedNumber(hits) );
      out.write("\r\n                hits, ");
      out.print( LocaleUtils.getLocalizedNumber(misses) );
      out.write(" misses)\r\n\r\n        </td>\r\n    </tr>\r\n    ");

        }
    }
    
      out.write("\r\n</tbody>\r\n\r\n</table>\r\n</div>\r\n</div>\r\n\r\n<br /><br />\r\n\r\n<div class=\"jive-table\">\r\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n<thead>\r\n    <tr>\r\n        <th colspan=\"2\">\r\n            Openfire Cluster Details\r\n        </th>\r\n    </tr>\r\n</thead>\r\n<tbody>\r\n    <tr>\r\n        <td width=\"100%\">\r\n            Hazelcast Version ");
      out.print( NodeRuntimeStats.getProviderConfig("hazelcast.version") );
      out.write(" \r\n            Build ");
      out.print( NodeRuntimeStats.getProviderConfig("hazelcast.build") );
      out.write("\r\n        </td>\r\n    </tr>\r\n</tbody>\r\n</table>\r\n</div>\r\n\r\n<br/>\r\n\r\n</body>\r\n</html>");
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
