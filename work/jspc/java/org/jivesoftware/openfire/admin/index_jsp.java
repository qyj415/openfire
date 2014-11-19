package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.jivesoftware.admin.AdminConsole;
import org.jivesoftware.openfire.*;
import org.jivesoftware.openfire.container.AdminConsolePlugin;
import org.jivesoftware.openfire.filetransfer.proxy.FileTransferProxy;
import org.jivesoftware.openfire.http.HttpBindManager;
import org.jivesoftware.openfire.mediaproxy.MediaProxyService;
import org.jivesoftware.openfire.net.SSLConfig;
import org.jivesoftware.openfire.session.LocalClientSession;
import org.jivesoftware.openfire.session.LocalConnectionMultiplexerSession;
import org.jivesoftware.openfire.spi.ConnectionManagerImpl;
import org.jivesoftware.openfire.update.Update;
import org.jivesoftware.openfire.update.UpdateManager;
import org.jivesoftware.util.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

 long lastRRSFecth = 0;
    SyndFeed lastBlogFeed = null;
    SyndFeed lastReleaseFeed = null;
    String blogFeedRSS = "http://www.igniterealtime.org/community/blogs/ignite/feeds/posts";
    String releaseFeedRSS = "http://www.igniterealtime.org/community/community/feeds/messages?community=2017";


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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
      out.write('\n');
      org.jivesoftware.admin.AdminPageBean pageinfo = null;
      synchronized (request) {
        pageinfo = (org.jivesoftware.admin.AdminPageBean) _jspx_page_context.getAttribute("pageinfo", PageContext.REQUEST_SCOPE);
        if (pageinfo == null){
          pageinfo = new org.jivesoftware.admin.AdminPageBean();
          _jspx_page_context.setAttribute("pageinfo", pageinfo, PageContext.REQUEST_SCOPE);
        }
      }
      out.write('\n');
      out.write('\n');
  // Simple logout code
    if ("true".equals(request.getParameter("logout"))) {
        session.removeAttribute("jive.admin.authToken");
        response.sendRedirect("index.jsp");
        return;
    }

      out.write('\n');
      out.write('\n');
      out.write('\n');
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
 webManager.init(request, response, session, application, out); 
      out.write('\n');
      out.write('\n');
      out.write('\n');
 // Get parameters //
    boolean serverOn = (webManager.getXMPPServer() != null);

    String interfaceName = JiveGlobals.getXMLProperty("network.interface");

    ConnectionManagerImpl connectionManager = ((ConnectionManagerImpl) XMPPServer.getInstance().getConnectionManager());
    SocketAcceptor socketAcceptor = connectionManager.getSocketAcceptor();
    SocketAcceptor sslSocketAcceptor = connectionManager.getSSLSocketAcceptor();
    SocketAcceptor multiplexerSocketAcceptor = connectionManager.getMultiplexerSocketAcceptor();
    ServerPort serverPort = null;
    ServerPort componentPort = null;
    AdminConsolePlugin adminConsolePlugin =
            (AdminConsolePlugin) XMPPServer.getInstance().getPluginManager().getPlugin("admin");

    FileTransferProxy fileTransferProxy = XMPPServer.getInstance().getFileTransferProxy();
    HttpBindManager httpBindManager = HttpBindManager.getInstance();
    MediaProxyService mediaProxyService = XMPPServer.getInstance().getMediaProxyService();
    FlashCrossDomainHandler flashCrossDomainHandler = XMPPServer.getInstance().getFlashCrossDomainHandler();

    // Search for s2s and external component ports info
    for (ServerPort port : XMPPServer.getInstance().getServerInfo().getServerPorts()) {
        if (port.getType() == ServerPort.Type.server) {
            serverPort = port;
        } else if (port.getType() == ServerPort.Type.component) {
            componentPort = port;
        }
    }

    boolean rssEnabled = JiveGlobals.getBooleanProperty("rss.enabled", true);

      out.write("\n\n<html>\n    <head>\n        <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n        <meta name=\"pageID\" content=\"server-settings\"/>\n        <meta name=\"helpPage\" content=\"about_the_server.html\"/>\n    </head>\n    <body>\n\n");

    UpdateManager updateManager = XMPPServer.getInstance().getUpdateManager();
    Update serverUpdate = updateManager.getServerUpdate();
    if (serverUpdate != null) { 
      out.write("\n    <div class=\"warning\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" >\n    <tbody>\n        <tr>\n            <td class=\"jive-icon-label\">\n            <b>");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("</b><br/><br/>\n            </td>\n        </tr>\n        <td valign=\"top\" align=\"left\" colspan=\"2\">\n        <span>");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_2 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_2.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_2.setParent(null);
      _jspx_th_fmt_message_2.setKey("index.update.info");
      int _jspx_eval_fmt_message_2 = _jspx_th_fmt_message_2.doStartTag();
      if (_jspx_eval_fmt_message_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_2.doInitBody();
        }
        do {
          out.write("\n                ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_2);
          _jspx_th_fmt_param_0.setValue( serverUpdate.getLatestVersion());
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          out.write("\n                ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_1 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_1.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_2);
          _jspx_th_fmt_param_1.setValue( "<a href='"+serverUpdate.getURL()+"'>" );
          int _jspx_eval_fmt_param_1 = _jspx_th_fmt_param_1.doStartTag();
          if (_jspx_th_fmt_param_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
          out.write("\n                ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_2 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_2.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_2);
          _jspx_th_fmt_param_2.setValue( "</a>" );
          int _jspx_eval_fmt_param_2 = _jspx_th_fmt_param_2.doStartTag();
          if (_jspx_th_fmt_param_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_2);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_2);
          out.write("\n                ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_3 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_3.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_2);
          _jspx_th_fmt_param_3.setValue( "<a href='"+serverUpdate.getChangelog()+"'>" );
          int _jspx_eval_fmt_param_3 = _jspx_th_fmt_param_3.doStartTag();
          if (_jspx_th_fmt_param_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_3);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_3);
          out.write("\n                ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_4 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_4.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_2);
          _jspx_th_fmt_param_4.setValue( "</a>" );
          int _jspx_eval_fmt_param_4 = _jspx_th_fmt_param_4.doStartTag();
          if (_jspx_th_fmt_param_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_4);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_4);
          out.write("\n            ");
          int evalDoAfterBody = _jspx_th_fmt_message_2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_2);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_2);
      out.write("</span>\n        </td>\n    </tbody>\n    </table>\n    </div>\n    <br>\n\n");

    }

      out.write("\n<style type=\"text/css\">\n.bar TD {\n    padding : 0;\n}\n#jive-latest-activity .jive-bottom-line {\n\tpadding-top: 10px;\n    border-bottom : 1px #e8a400 solid;\n\t}\n#jive-latest-activity {\n    border: 1px #E8A400 solid;\n    background-color: #FFFBE2;\n\tfont-family: Lucida Grande, Arial, Helvetica, sans-serif;\n\tfont-size: 9pt;\n    padding: 0 10px 10px 10px;\n    margin-bottom: 10px;\n    min-height: 280px;\n    -moz-border-radius: 4px;\n    width: 95%;\n    margin-right: 20px;\n\t}\n#jive-latest-activity h4 {\n\tfont-size: 13pt;\n\tmargin: 15px 0 4px 0;\n\t}\n#jive-latest-activity h5 {\n\tfont-size: 9pt;\n\tfont-weight: normal;\n    margin: 15px 0 5px 5px;\n\tpadding: 0;\n\t}\n#jive-latest-activity .jive-blog-date {\n    font-size: 8pt;\n    white-space: nowrap;\n\t}\n#jive-latest-activity .jive-feed-icon {\n    float: right;\n    padding-top: 10px;\n\t}\n.info-header {\n    background-color: #eee;\n    font-size: 10pt;\n}\n.info-table {\n    margin-right: 12px;\n}\n.info-table .c1 {\n    text-align: right;\n    vertical-align: top;\n    color: #666;\n    font-weight: bold;\n");
      out.write("    font-size: 9pt;\n    white-space: nowrap;\n}\n.info-table .c2 {\n    font-size: 9pt;\n    width: 90%;\n}\n</style>\n\n<p>\n");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\n</p>\n<table border=\"0\" width=\"100%\">\n    <td valign=\"top\">\n\n        <!-- <div class=\"jive-table\"> -->\n        <table border=\"0\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\" class=\"info-table\">\n        <thead>\n            <tr>\n                <th colspan=\"2\" align=\"left\" class=\"info-header\">");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</th>\n            </tr>\n        </thead>\n        <tbody>\n\n            ");
  if (serverOn) { 
      out.write("\n\n                 <tr>\n                    <td class=\"c1\">");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</td>\n                    <td class=\"c2\">\n                        ");

                            long now = System.currentTimeMillis();
                            long lastStarted = webManager.getXMPPServer().getServerInfo().getLastStarted().getTime();
                            long uptime = now - lastStarted;
                            String uptimeDisplay = StringUtils.getElapsedTime(uptime);
                        
      out.write("\n\n                        ");
  if (uptimeDisplay != null) { 
      out.write("\n                            ");
      out.print( uptimeDisplay );
      out.write(" -- started\n                        ");
  } 
      out.write("\n\n                        ");
      out.print( JiveGlobals.formatDateTime(webManager.getXMPPServer().getServerInfo().getLastStarted()) );
      out.write("\n                    </td>\n                </tr>\n\n            ");
  } 
      out.write("\n\n            <tr>\n                <td class=\"c1\">");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</td>\n                <td class=\"c2\">\n                    ");
      out.print( AdminConsole.getAppName() );
      out.write("\n                    ");
      out.print( AdminConsole.getVersionString() );
      out.write("\n                </td>\n            </tr>\n            <tr>\n                <td class=\"c1\">");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</td>\n                <td class=\"c2\">\n                    ");
      out.print( JiveGlobals.getHomeDirectory() );
      out.write("\n                </td>\n            </tr>\n            <tr>\n                <td class=\"c1\">\n                    ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\n                </td>\n                <td class=\"c2\">\n                    ");
 try { 
      out.write("\n                    ");
 if (!CertificateManager.isRSACertificate(SSLConfig.getKeyStore(), XMPPServer.getInstance().getServerInfo().getXMPPDomain())) {
      out.write("\n                    <img src=\"images/warning-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\">&nbsp;\n                    ");
 } 
      out.write("\n                    ");
 } catch (Exception e) { 
      out.write("\n                    <img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("\">&nbsp;\n                    ");
 } 
      out.write("\n                    ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${webManager.serverInfo.XMPPDomain}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\n                </td>\n            </tr>\n            <tr><td>&nbsp;</td></tr>\n        </tbody>\n        <thead>\n            <tr>\n                <th colspan=\"2\" align=\"left\" class=\"info-header\">");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("</th>\n            </tr>\n        </thead>\n        <tbody>\n            <tr>\n                <td class=\"c1\">");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</td>\n                <td class=\"c2\">\n                    ");

                        String vmName = System.getProperty("java.vm.name");
                        if (vmName == null) {
                            vmName = "";
                        }
                        else {
                            vmName = " -- " + vmName;
                        }
                    
      out.write("\n                    ");
      out.print( System.getProperty("java.version") );
      out.write(' ');
      out.print( System.getProperty("java.vendor") );
      out.print( vmName );
      out.write("\n                </td>\n            </tr>\n            <tr>\n                <td class=\"c1\">");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</td>\n                <td class=\"c2\">\n                    ");
      out.print( application.getServerInfo() );
      out.write("\n                </td>\n            </tr>\n            <tr>\n                <td class=\"c1\">\n                    ");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\n                </td>\n                <td class=\"c2\">\n                    ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${webManager.serverInfo.hostname}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\n                </td>\n            </tr>\n            <tr>\n                <td class=\"c1\">");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("</td>\n                <td class=\"c2\">\n                    ");
      out.print( System.getProperty("os.name") );
      out.write(' ');
      out.write('/');
      out.write(' ');
      out.print( System.getProperty("os.arch") );
      out.write("\n                </td>\n            </tr>\n            <tr>\n                <td class=\"c1\">");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</td>\n                <td class=\"c2\">\n                    ");
      out.print( JiveGlobals.getLocale() );
      out.write(' ');
      out.write('/');
      out.write(' ');
      out.print( JiveGlobals.getTimeZone().getDisplayName(JiveGlobals.getLocale()) );
      out.write("\n                    (");
      out.print( (JiveGlobals.getTimeZone().getRawOffset()/1000/60/60) );
      out.write(" GMT)\n                </td>\n            </tr>\n            <tr>\n                <td class=\"c1\">");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("</td>\n                <td>\n                ");
    // The java runtime
                    Runtime runtime = Runtime.getRuntime();

                    double freeMemory = (double)runtime.freeMemory()/(1024*1024);
                    double maxMemory = (double)runtime.maxMemory()/(1024*1024);
                    double totalMemory = (double)runtime.totalMemory()/(1024*1024);
                    double usedMemory = totalMemory - freeMemory;
                    double percentFree = ((maxMemory - usedMemory)/maxMemory)*100.0;
                    double percentUsed = 100 - percentFree;
                    int percent = 100-(int)Math.round(percentFree);

                    DecimalFormat mbFormat = new DecimalFormat("#0.00");
                    DecimalFormat percentFormat = new DecimalFormat("#0.0");
                
      out.write("\n\n                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"300\">\n                <tr valign=\"middle\">\n                    <td width=\"99%\" valign=\"middle\">\n                        <div class=\"bar\">\n                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border:1px #666 solid;\">\n                        <tr>\n                            ");
  if (percent == 0) { 
      out.write("\n\n                                <td width=\"100%\"><img src=\"images/percent-bar-left.gif\" width=\"100%\" height=\"8\" border=\"0\" alt=\"\"></td>\n\n                            ");
  } else { 
      out.write("\n\n                                ");
  if (percent >= 90) { 
      out.write("\n\n                                    <td width=\"");
      out.print( percent );
      out.write("%\" background=\"images/percent-bar-used-high.gif\"\n                                        ><img src=\"images/blank.gif\" width=\"1\" height=\"8\" border=\"0\" alt=\"\"></td>\n\n                                ");
  } else { 
      out.write("\n\n                                    <td width=\"");
      out.print( percent );
      out.write("%\" background=\"images/percent-bar-used-low.gif\"\n                                        ><img src=\"images/blank.gif\" width=\"1\" height=\"8\" border=\"0\" alt=\"\"></td>\n\n                                ");
  } 
      out.write("\n                                <td width=\"");
      out.print( (100-percent) );
      out.write("%\" background=\"images/percent-bar-left.gif\"\n                                    ><img src=\"images/blank.gif\" width=\"1\" height=\"8\" border=\"0\" alt=\"\"></td>\n                            ");
  } 
      out.write("\n                        </tr>\n                        </table>\n                        </div>\n                    </td>\n                    <td width=\"1%\" nowrap>\n                        <div style=\"padding-left:6px;\" class=\"c2\">\n                        ");
      out.print( mbFormat.format(usedMemory) );
      out.write(" MB of ");
      out.print( mbFormat.format(maxMemory) );
      out.write(" MB (");
      out.print( percentFormat.format(percentUsed) );
      out.write("%) used\n                        </div>\n                    </td>\n                </tr>\n                </table>\n                </td>\n            </tr>\n        </tbody>\n        </table>\n        <!-- </div> -->\n    </td>\n    ");
 if (rssEnabled) { 
      out.write("\n    <td valign=\"top\" width=\"40%\"> \n        <div id=\"jive-latest-activity\">\n\n            <a href=\"");
      out.print( blogFeedRSS );
      out.write("\" class=\"jive-feed-icon\"><img src=\"images/feed-icon-16x16.gif\" alt=\"\" style=\"border:0;\" /></a>\n            <h4>");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("</h4>\n            ");
 long nowTime = System.currentTimeMillis();
                if (lastBlogFeed == null || lastReleaseFeed == null || nowTime - lastRRSFecth > 21600000) {

                    FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
                    FeedFetcher feedFetcher = new HttpClientWithTimeoutFeedFetcher(feedInfoCache);

                    try {
                        lastBlogFeed = feedFetcher.retrieveFeed(new URL(blogFeedRSS));
                        lastReleaseFeed = feedFetcher.retrieveFeed(new URL(releaseFeedRSS));

                        lastRRSFecth = nowTime;
                    }
                    catch (Exception ioe) {
                        // ignore
                    }
                }

                
      out.write("<div class=\"jive-bottom-line\"></div>");

                if (lastBlogFeed != null && !lastBlogFeed.getEntries().isEmpty()) {

                    List entries = lastBlogFeed.getEntries();
                    for (int i = 0; i < entries.size() && i < 3; i++) {
                        SyndEntry entry = (SyndEntry) entries.get(i); 
      out.write("\n                        <h5><a href=\"");
      out.print( entry.getLink() );
      out.write("\" target=\"_blank\">");
      out.print( entry.getTitle());
      out.write("</a>,\n                        <span class=\"jive-blog-date\">");
      out.print( JiveGlobals.formatDate(entry.getPublishedDate()));
      out.write("</span></h5>\n                    ");
 }

                } else { 
      out.write("\n                    ");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("\n                 ");
 }

                 
      out.write("<div class=\"jive-bottom-line\"></div>");

                if (lastReleaseFeed != null && !lastReleaseFeed.getEntries().isEmpty()) {

                    List entries = lastReleaseFeed.getEntries();
                    for (int i = 0; i < entries.size() && i < 3; i++) {
                        SyndEntry entry = (SyndEntry) entries.get(i); 
      out.write("\n                        <h5><a href=\"");
      out.print( entry.getLink() );
      out.write("\" target=\"_blank\">");
      out.print( entry.getTitle());
      out.write("</a>,\n                        <span class=\"jive-blog-date\">");
      out.print( JiveGlobals.formatDate(entry.getPublishedDate()));
      out.write("</span></h5>\n                    ");
 }

                } else { 
      out.write("\n                    ");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("\n                 ");
 }
            
      out.write("\n\n        </div>\n    </td>\n    ");
 } 
      out.write("\n</table>\n\n<br>\n\n<div id=\"jive-title\">");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("</div>\n<div class=\"jive-table\">\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n<thead>\n    <tr>\n        <th width=\"80\">");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("</th>\n        <th width=\"1\">");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("</th>\n        <th width=\"1\">&nbsp;</th>\n        <th width=\"130\">");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("</th>\n        <th>");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("</th>\n    </tr>\n</thead>\n<tbody>\n    ");
 if (socketAcceptor != null) {
        for (SocketAddress socketAddress : socketAcceptor.getManagedServiceAddresses()) {
            InetSocketAddress address = (InetSocketAddress) socketAddress;
    
      out.write("\n    <tr>\n        <td>");
      out.print( "0.0.0.0".equals(address.getHostName()) ? LocaleUtils.getLocalizedString("ports.all_ports") : address.getHostName() );
      out.write("</td>\n        <td>");
      out.print( address.getPort() );
      out.write("</td>\n        ");
 try { 
      out.write("\n        ");
 if (!CertificateManager.isRSACertificate(SSLConfig.getKeyStore(), XMPPServer.getInstance().getServerInfo().getXMPPDomain()) || LocalClientSession.getTLSPolicy() == org.jivesoftware.openfire.Connection.TLSPolicy.disabled) { 
      out.write("\n            <td><img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\"/></td>\n        ");
 } else { 
      out.write("\n            <td><img src=\"images/lock.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("\"/></td>\n        ");
 } 
      out.write("\n        ");
 } catch (Exception e) { 
      out.write("\n            <td><img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\"/></td>\n        ");
 } 
      out.write("\n        <td>");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write("\n        </td>\n    </tr>\n    ");
 } } 
      out.write("\n    ");
 if (sslSocketAcceptor != null) {
        for (SocketAddress socketAddress : sslSocketAcceptor.getManagedServiceAddresses()) {
            InetSocketAddress address = (InetSocketAddress) socketAddress;
    
      out.write("\n    <tr>\n        <td>");
      out.print( "0.0.0.0".equals(address.getHostName()) ? LocaleUtils.getLocalizedString("ports.all_ports") : address.getHostName() );
      out.write("</td>\n        <td>");
      out.print( address.getPort() );
      out.write("</td>\n        <td><img src=\"images/lock.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_33(_jspx_page_context))
        return;
      out.write("\"/></td>\n        <td>");
      if (_jspx_meth_fmt_message_34(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_35(_jspx_page_context))
        return;
      out.write("\n        </td>\n    </tr>\n    ");
 } } 
      out.write("\n    ");

        if (serverPort != null) {
    
      out.write("\n    <tr>\n        <td>");
      out.print( interfaceName == null ? LocaleUtils.getLocalizedString("ports.all_ports") : serverPort.getIPAddress() );
      out.write("</td>\n        <td>");
      out.print( serverPort.getPort() );
      out.write("</td>\n        ");
 if (JiveGlobals.getBooleanProperty("xmpp.server.tls.enabled", true)) { 
      out.write("\n            <td><img src=\"images/lock.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_36(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_37(_jspx_page_context))
        return;
      out.write("\"/></td>\n        ");
 } else { 
      out.write("\n            <td><img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\"/></td>\n        ");
 } 
      out.write("\n        <td>");
      if (_jspx_meth_fmt_message_38(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_39(_jspx_page_context))
        return;
      out.write("\n        </td>\n        <td>\n</td>\n    </tr>\n    ");
 } 
      out.write("\n    ");
 if (multiplexerSocketAcceptor != null) {
        for (SocketAddress socketAddress : multiplexerSocketAcceptor.getManagedServiceAddresses()) {
            InetSocketAddress address = (InetSocketAddress) socketAddress;
    
      out.write("\n    <tr>\n        <td>");
      out.print( "0.0.0.0".equals(address.getHostName()) ? LocaleUtils.getLocalizedString("ports.all_ports") : address.getHostName() );
      out.write("</td>\n        <td>");
      out.print( address.getPort() );
      out.write("</td>\n        ");
 if (LocalConnectionMultiplexerSession.getTLSPolicy() == org.jivesoftware.openfire.Connection.TLSPolicy.disabled) { 
      out.write("\n            <td><img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\"></td>\n        ");
 } else { 
      out.write("\n            <td><img src=\"images/lock.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_40(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_41(_jspx_page_context))
        return;
      out.write("\"/></td>\n        ");
 } 
      out.write("\n        <td>");
      if (_jspx_meth_fmt_message_42(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_43(_jspx_page_context))
        return;
      out.write("\n        </td>\n    </tr>\n    ");
 } } 
      out.write("\n    ");

        if (componentPort != null) {
    
      out.write("\n    <tr>\n        <td>");
      out.print( interfaceName == null ? LocaleUtils.getLocalizedString("ports.all_ports") : componentPort.getIPAddress() );
      out.write("</td>\n        <td>");
      out.print( componentPort.getPort() );
      out.write("</td>\n        <td><img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\"></td>\n        <td>");
      if (_jspx_meth_fmt_message_44(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_45(_jspx_page_context))
        return;
      out.write("\n        </td>\n    </tr>\n    ");
 } 
      out.write("\n    <tr>\n        <td>");
      out.print( adminConsolePlugin.getBindInterface() == null ? LocaleUtils.getLocalizedString("ports.all_ports") : adminConsolePlugin.getBindInterface() );
      out.write("</td>\n        <td>");
      out.print( adminConsolePlugin.getAdminUnsecurePort() );
      out.write("</td>\n        <td><img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\"></td>\n        <td>");
      if (_jspx_meth_fmt_message_46(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_47(_jspx_page_context))
        return;
      out.write("</td>\n    </tr>\n    ");

        if (adminConsolePlugin.getAdminSecurePort() > 0) {
    
      out.write("\n    <tr>\n        <td>");
      out.print( adminConsolePlugin.getBindInterface() == null ? LocaleUtils.getLocalizedString("ports.all_ports") : adminConsolePlugin.getBindInterface() );
      out.write("</td>\n        <td>");
      out.print( adminConsolePlugin.getAdminSecurePort() );
      out.write("</td>\n        <td><img src=\"images/lock.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_48(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_49(_jspx_page_context))
        return;
      out.write("\"/></td>\n        <td>");
      if (_jspx_meth_fmt_message_50(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_51(_jspx_page_context))
        return;
      out.write("</td>\n    </tr>\n    ");
 } 
      out.write("\n    ");

        if (fileTransferProxy.isProxyEnabled()) {
    
      out.write("\n    <tr>\n        <td>");
      out.print( interfaceName == null ? LocaleUtils.getLocalizedString("ports.all_ports") : interfaceName );
      out.write("</td>\n        <td>");
      out.print( fileTransferProxy.getProxyPort() );
      out.write("</td>\n        <td><img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\"></td>\n        <td>");
      if (_jspx_meth_fmt_message_52(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_53(_jspx_page_context))
        return;
      out.write("</td>\n    </tr>\n    ");
 } 
      out.write("\n    ");

        if (httpBindManager.isHttpBindEnabled()) {
    
      out.write("\n        ");

            if (httpBindManager.getHttpBindUnsecurePort() > 0) {
        
      out.write("\n        <tr>\n            <td>");
      out.print( interfaceName == null ? LocaleUtils.getLocalizedString("ports.all_ports") : interfaceName );
      out.write("</td>\n            <td>");
      out.print( httpBindManager.getHttpBindUnsecurePort() );
      out.write("</td>\n            <td><img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\"></td>\n            <td>");
      if (_jspx_meth_fmt_message_54(_jspx_page_context))
        return;
      out.write("</td>\n            <td>");
      if (_jspx_meth_fmt_message_55(_jspx_page_context))
        return;
      out.write("</td>\n        </tr>\n        ");
 } 
      out.write("\n        ");

            if (httpBindManager.isHttpsBindActive()) {
        
      out.write("\n        <tr>\n            <td>");
      out.print( interfaceName == null ? LocaleUtils.getLocalizedString("ports.all_ports") : interfaceName );
      out.write("</td>\n            <td>");
      out.print( httpBindManager.getHttpBindSecurePort() );
      out.write("</td>\n            <td><img src=\"images/lock.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_56(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_57(_jspx_page_context))
        return;
      out.write("\"/></td>\n            <td>");
      if (_jspx_meth_fmt_message_58(_jspx_page_context))
        return;
      out.write("</td>\n            <td>");
      if (_jspx_meth_fmt_message_59(_jspx_page_context))
        return;
      out.write("</td>\n        </tr>\n        ");
 } 
      out.write("\n    ");
 } 
      out.write("\n    ");

        if (mediaProxyService.isEnabled()) {
    
      out.write("\n    <tr>\n        <td>");
      out.print( interfaceName == null ? LocaleUtils.getLocalizedString("ports.all_ports") : interfaceName );
      out.write("</td>\n        <td>");
      out.print( mediaProxyService.getMinPort() );
      out.write(' ');
      out.write('-');
      out.write(' ');
      out.print( mediaProxyService.getMaxPort() );
      out.write("</td>\n        <td><img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\"></td>\n        <td>");
      if (_jspx_meth_fmt_message_60(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_61(_jspx_page_context))
        return;
      out.write("</td>\n    </tr>\n    ");
 } 
      out.write("\n    <tr>\n        <td>");
      out.print( interfaceName == null ? LocaleUtils.getLocalizedString("ports.all_ports") : interfaceName );
      out.write("</td>\n        <td>");
      out.print( flashCrossDomainHandler.getPort() );
      out.write("</td>\n        <td><img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\"></td>\n        <td>");
      if (_jspx_meth_fmt_message_62(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_63(_jspx_page_context))
        return;
      out.write("</td>\n    </tr>\n    ");

        if (JMXManager.isEnabled()) {
    
      out.write("\n    <tr>\n        <td>");
      out.print( interfaceName == null ? LocaleUtils.getLocalizedString("ports.all_ports") : interfaceName );
      out.write("</td>\n        <td>");
      out.print( JMXManager.getPort() );
      out.write("</td>\n        <td>");
 if (JMXManager.isSecure()) {
            
      out.write("<img src=\"images/user.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_64(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_65(_jspx_page_context))
        return;
      out.write('"');
      out.write('/');
      out.write('>');

        } else {
            
      out.write("<img src=\"images/blank.gif\" width=\"1\" height=\"1\" alt=\"\">");
 }
        
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_66(_jspx_page_context))
        return;
      out.write("</td>\n        <td>");
      if (_jspx_meth_fmt_message_67(_jspx_page_context))
        return;
      out.write("</td>\n    </tr>\n    ");
 } 
      out.write("\n</tbody>\n</table>\n</div>\n<br>\n\n<form action=\"server-props.jsp\">\n<input type=\"submit\" value=\"");
      if (_jspx_meth_fmt_message_68(_jspx_page_context))
        return;
      out.write("\">\n</form>\n\n    </body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("index.title");
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
    _jspx_th_fmt_message_1.setKey("index.update.alert");
    int _jspx_eval_fmt_message_1 = _jspx_th_fmt_message_1.doStartTag();
    if (_jspx_th_fmt_message_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_1);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_1);
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
    _jspx_th_fmt_message_3.setKey("index.title.info");
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
    _jspx_th_fmt_message_4.setKey("index.properties");
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
    _jspx_th_fmt_message_5.setKey("index.uptime");
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
    _jspx_th_fmt_message_6.setKey("index.version");
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
    _jspx_th_fmt_message_7.setKey("index.home");
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
    _jspx_th_fmt_message_8.setKey("index.server_name");
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
    _jspx_th_fmt_message_9.setKey("index.certificate-warning");
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
    _jspx_th_fmt_message_10.setKey("index.certificate-warning");
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
    _jspx_th_fmt_message_11.setKey("index.certificate-error");
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
    _jspx_th_fmt_message_12.setKey("index.certificate-error");
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
    _jspx_th_fmt_message_13.setKey("index.environment");
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
    _jspx_th_fmt_message_14.setKey("index.jvm");
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
    _jspx_th_fmt_message_15.setKey("index.app");
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
    _jspx_th_fmt_message_16.setKey("index.host_name");
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
    _jspx_th_fmt_message_17.setKey("index.os");
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
    _jspx_th_fmt_message_18.setKey("index.local");
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
    _jspx_th_fmt_message_19.setKey("index.memory");
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
    _jspx_th_fmt_message_20.setKey("index.cs_blog");
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
    _jspx_th_fmt_message_21.setKey("index.cs_blog.unavailable");
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
    _jspx_th_fmt_message_22.setKey("index.cs_blog.unavailable");
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
    _jspx_th_fmt_message_23.setKey("index.server_port");
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
    _jspx_th_fmt_message_24.setKey("ports.interface");
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
    _jspx_th_fmt_message_25.setKey("ports.port");
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
    _jspx_th_fmt_message_26.setKey("ports.type");
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
    _jspx_th_fmt_message_27.setKey("ports.description");
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
    _jspx_th_fmt_message_28.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_29.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_30.setKey("ports.client_to_server");
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
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_31 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_31.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_31.setParent(null);
    _jspx_th_fmt_message_31.setKey("ports.client_to_server.desc");
    int _jspx_eval_fmt_message_31 = _jspx_th_fmt_message_31.doStartTag();
    if (_jspx_eval_fmt_message_31 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fmt_message_31 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fmt_message_31.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fmt_message_31.doInitBody();
      }
      do {
        out.write("\n            ");
        if (_jspx_meth_fmt_param_5(_jspx_th_fmt_message_31, _jspx_page_context))
          return true;
        out.write("\n            ");
        if (_jspx_meth_fmt_param_6(_jspx_th_fmt_message_31, _jspx_page_context))
          return true;
        out.write("\n            ");
        int evalDoAfterBody = _jspx_th_fmt_message_31.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fmt_message_31 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_fmt_message_31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_31);
      return true;
    }
    _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_31);
    return false;
  }

  private boolean _jspx_meth_fmt_param_5(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_31, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_5 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_5.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_31);
    _jspx_th_fmt_param_5.setValue(new String("<a href='ssl-settings.jsp'>"));
    int _jspx_eval_fmt_param_5 = _jspx_th_fmt_param_5.doStartTag();
    if (_jspx_th_fmt_param_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_5);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_5);
    return false;
  }

  private boolean _jspx_meth_fmt_param_6(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_31, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_6 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_6.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_31);
    _jspx_th_fmt_param_6.setValue(new String("</a>"));
    int _jspx_eval_fmt_param_6 = _jspx_th_fmt_param_6.doStartTag();
    if (_jspx_th_fmt_param_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_6);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_6);
    return false;
  }

  private boolean _jspx_meth_fmt_message_32(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_32 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_32.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_32.setParent(null);
    _jspx_th_fmt_message_32.setKey("ports.secure.alt");
    int _jspx_eval_fmt_message_32 = _jspx_th_fmt_message_32.doStartTag();
    if (_jspx_th_fmt_message_32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
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
    _jspx_th_fmt_message_33.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_34.setKey("ports.client_to_server");
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
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_35 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_35.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_35.setParent(null);
    _jspx_th_fmt_message_35.setKey("ports.client_to_server.desc_old_ssl");
    int _jspx_eval_fmt_message_35 = _jspx_th_fmt_message_35.doStartTag();
    if (_jspx_eval_fmt_message_35 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fmt_message_35 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fmt_message_35.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fmt_message_35.doInitBody();
      }
      do {
        out.write("\n            ");
        if (_jspx_meth_fmt_param_7(_jspx_th_fmt_message_35, _jspx_page_context))
          return true;
        out.write("\n            ");
        if (_jspx_meth_fmt_param_8(_jspx_th_fmt_message_35, _jspx_page_context))
          return true;
        out.write("\n            ");
        int evalDoAfterBody = _jspx_th_fmt_message_35.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fmt_message_35 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_fmt_message_35.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_35);
      return true;
    }
    _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_35);
    return false;
  }

  private boolean _jspx_meth_fmt_param_7(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_35, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_7 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_7.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_35);
    _jspx_th_fmt_param_7.setValue(new String("<a href='ssl-settings.jsp'>"));
    int _jspx_eval_fmt_param_7 = _jspx_th_fmt_param_7.doStartTag();
    if (_jspx_th_fmt_param_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_7);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_7);
    return false;
  }

  private boolean _jspx_meth_fmt_param_8(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_35, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_8 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_8.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_35);
    _jspx_th_fmt_param_8.setValue(new String("</a>"));
    int _jspx_eval_fmt_param_8 = _jspx_th_fmt_param_8.doStartTag();
    if (_jspx_th_fmt_param_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_8);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_8);
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
    _jspx_th_fmt_message_36.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_37.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_38.setKey("ports.server_to_server");
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
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_39 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_39.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_39.setParent(null);
    _jspx_th_fmt_message_39.setKey("ports.server_to_server.desc");
    int _jspx_eval_fmt_message_39 = _jspx_th_fmt_message_39.doStartTag();
    if (_jspx_eval_fmt_message_39 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fmt_message_39 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fmt_message_39.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fmt_message_39.doInitBody();
      }
      do {
        out.write("\n            ");
        if (_jspx_meth_fmt_param_9(_jspx_th_fmt_message_39, _jspx_page_context))
          return true;
        out.write("\n            ");
        if (_jspx_meth_fmt_param_10(_jspx_th_fmt_message_39, _jspx_page_context))
          return true;
        out.write("\n            ");
        int evalDoAfterBody = _jspx_th_fmt_message_39.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fmt_message_39 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_fmt_message_39.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_39);
      return true;
    }
    _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_39);
    return false;
  }

  private boolean _jspx_meth_fmt_param_9(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_39, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_9 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_9.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_39);
    _jspx_th_fmt_param_9.setValue(new String("<a href='server2server-settings.jsp'>"));
    int _jspx_eval_fmt_param_9 = _jspx_th_fmt_param_9.doStartTag();
    if (_jspx_th_fmt_param_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_9);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_9);
    return false;
  }

  private boolean _jspx_meth_fmt_param_10(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_39, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_10 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_10.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_39);
    _jspx_th_fmt_param_10.setValue(new String("</a>"));
    int _jspx_eval_fmt_param_10 = _jspx_th_fmt_param_10.doStartTag();
    if (_jspx_th_fmt_param_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_10);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_10);
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
    _jspx_th_fmt_message_40.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_41.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_42.setKey("ports.connection_manager");
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
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_43 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_43.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_43.setParent(null);
    _jspx_th_fmt_message_43.setKey("ports.connection_manager.desc");
    int _jspx_eval_fmt_message_43 = _jspx_th_fmt_message_43.doStartTag();
    if (_jspx_eval_fmt_message_43 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fmt_message_43 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fmt_message_43.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fmt_message_43.doInitBody();
      }
      do {
        out.write("\n            ");
        if (_jspx_meth_fmt_param_11(_jspx_th_fmt_message_43, _jspx_page_context))
          return true;
        out.write("\n            ");
        if (_jspx_meth_fmt_param_12(_jspx_th_fmt_message_43, _jspx_page_context))
          return true;
        out.write("\n            ");
        int evalDoAfterBody = _jspx_th_fmt_message_43.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fmt_message_43 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_fmt_message_43.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_43);
      return true;
    }
    _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_43);
    return false;
  }

  private boolean _jspx_meth_fmt_param_11(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_43, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_11 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_11.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_43);
    _jspx_th_fmt_param_11.setValue(new String("<a href='connection-managers-settings.jsp'>"));
    int _jspx_eval_fmt_param_11 = _jspx_th_fmt_param_11.doStartTag();
    if (_jspx_th_fmt_param_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_11);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_11);
    return false;
  }

  private boolean _jspx_meth_fmt_param_12(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_43, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_12 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_12.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_43);
    _jspx_th_fmt_param_12.setValue(new String("</a>"));
    int _jspx_eval_fmt_param_12 = _jspx_th_fmt_param_12.doStartTag();
    if (_jspx_th_fmt_param_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_12);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_12);
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
    _jspx_th_fmt_message_44.setKey("ports.external_components");
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
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_45 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_45.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_45.setParent(null);
    _jspx_th_fmt_message_45.setKey("ports.external_components.desc");
    int _jspx_eval_fmt_message_45 = _jspx_th_fmt_message_45.doStartTag();
    if (_jspx_eval_fmt_message_45 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fmt_message_45 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fmt_message_45.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fmt_message_45.doInitBody();
      }
      do {
        out.write("\n            ");
        if (_jspx_meth_fmt_param_13(_jspx_th_fmt_message_45, _jspx_page_context))
          return true;
        out.write("\n            ");
        if (_jspx_meth_fmt_param_14(_jspx_th_fmt_message_45, _jspx_page_context))
          return true;
        out.write("\n            ");
        int evalDoAfterBody = _jspx_th_fmt_message_45.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fmt_message_45 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_fmt_message_45.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_45);
      return true;
    }
    _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_45);
    return false;
  }

  private boolean _jspx_meth_fmt_param_13(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_45, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_13 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_13.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_45);
    _jspx_th_fmt_param_13.setValue(new String("<a href='external-components-settings.jsp'>"));
    int _jspx_eval_fmt_param_13 = _jspx_th_fmt_param_13.doStartTag();
    if (_jspx_th_fmt_param_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_13);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_13);
    return false;
  }

  private boolean _jspx_meth_fmt_param_14(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_45, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_14 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_14.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_45);
    _jspx_th_fmt_param_14.setValue(new String("</a>"));
    int _jspx_eval_fmt_param_14 = _jspx_th_fmt_param_14.doStartTag();
    if (_jspx_th_fmt_param_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_14);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_14);
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
    _jspx_th_fmt_message_46.setKey("ports.admin_console");
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
    _jspx_th_fmt_message_47.setKey("ports.admin_console.desc_unsecured");
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
    _jspx_th_fmt_message_48.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_49.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_50.setKey("ports.admin_console");
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
    _jspx_th_fmt_message_51.setKey("ports.admin_console.desc_secured");
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
    _jspx_th_fmt_message_52.setKey("ports.file_proxy");
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
    _jspx_th_fmt_message_53.setKey("ports.file_proxy.desc");
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
    _jspx_th_fmt_message_54.setKey("ports.http_bind");
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
    _jspx_th_fmt_message_55.setKey("ports.http_bind.desc_unsecured");
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
    _jspx_th_fmt_message_56.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_57.setKey("ports.secure.alt");
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
    _jspx_th_fmt_message_58.setKey("ports.http_bind");
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
    _jspx_th_fmt_message_59.setKey("ports.http_bind.desc_secured");
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
    _jspx_th_fmt_message_60.setKey("ports.media_proxy");
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
    _jspx_th_fmt_message_61.setKey("ports.media_proxy.desc");
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
    _jspx_th_fmt_message_62.setKey("ports.flash_cross_domain");
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
    _jspx_th_fmt_message_63.setKey("ports.flash_cross_domain.desc");
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
    _jspx_th_fmt_message_64.setKey("ports.jmx_console.alt");
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
    _jspx_th_fmt_message_65.setKey("ports.jmx_console.alt");
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
    _jspx_th_fmt_message_66.setKey("ports.jmx_console");
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
    _jspx_th_fmt_message_67.setKey("ports.jmx_console.desc");
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
    _jspx_th_fmt_message_68.setKey("global.edit_properties");
    int _jspx_eval_fmt_message_68 = _jspx_th_fmt_message_68.doStartTag();
    if (_jspx_th_fmt_message_68.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_68);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_68);
    return false;
  }
}
