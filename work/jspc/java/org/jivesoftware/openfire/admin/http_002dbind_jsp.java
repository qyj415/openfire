package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.StringUtils;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.StringUtils;
import org.jivesoftware.openfire.http.FlashCrossDomainServlet;
import org.jivesoftware.openfire.http.HttpBindManager;
import org.jivesoftware.openfire.http.FlashCrossDomainServlet;

public final class http_002dbind_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    HttpBindManager serverManager = HttpBindManager.getInstance();

    Map<String, String> handleUpdate(HttpServletRequest request) {
        Map<String, String> errorMap = new HashMap<String, String>();
        boolean isEnabled = ParamUtils.getBooleanParameter(request, "httpBindEnabled",
                serverManager.isHttpBindEnabled());
        // CORS        
        boolean isCORSEnabled = ParamUtils.getBooleanParameter(request, "CORSEnabled",
                serverManager.isCORSEnabled());
        // XFF        
        boolean isXFFEnabled = ParamUtils.getBooleanParameter(request, "XFFEnabled",
                serverManager.isXFFEnabled());
        if (isEnabled) {
            int requestedPort = ParamUtils.getIntParameter(request, "port",
                    serverManager.getHttpBindUnsecurePort());
            int requestedSecurePort = ParamUtils.getIntParameter(request, "securePort",
                    serverManager.getHttpBindSecurePort());
            // CORS
            String CORSDomains = ParamUtils.getParameter(request, "CORSDomains", true);     
            try {
                serverManager.setHttpBindPorts(requestedPort, requestedSecurePort);
                // CORS
                serverManager.setCORSEnabled(isCORSEnabled);
                serverManager.setCORSAllowOrigin(CORSDomains);
                // XFF
                serverManager.setXFFEnabled(isXFFEnabled);
                String param = ParamUtils.getParameter(request, "XFFHeader");
                serverManager.setXFFHeader(param);
                param = ParamUtils.getParameter(request, "XFFServerHeader");
                serverManager.setXFFServerHeader(param);
                param = ParamUtils.getParameter(request, "XFFHostHeader");
                serverManager.setXFFHostHeader(param);
                param = ParamUtils.getParameter(request, "XFFHostName");
                serverManager.setXFFHostName(param);
            }
            catch (Exception e) {
                Log.error("An error has occured configuring the HTTP binding ports", e);
                errorMap.put("port", e.getMessage());
            }
            boolean isScriptSyntaxEnabled = ParamUtils.getBooleanParameter(request,
                    "scriptSyntaxEnabled", serverManager.isScriptSyntaxEnabled());
            serverManager.setScriptSyntaxEnabled(isScriptSyntaxEnabled);
        }
        if (errorMap.size() <= 0) {
            serverManager.setHttpBindEnabled(isEnabled);
        }
        return errorMap;
    }

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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
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
      out.write('\n');
      out.write('\n');

    Map<String, String> errorMap = new HashMap<String, String>();
    if (request.getParameter("update") != null) {
        errorMap = handleUpdate(request);
        // Log the event
        webManager.logEvent("updated HTTP bind settings", null);
    }

    boolean isHttpBindEnabled = serverManager.isHttpBindEnabled();
    int port = serverManager.getHttpBindUnsecurePort();
    int securePort = serverManager.getHttpBindSecurePort();
    boolean isScriptSyntaxEnabled = serverManager.isScriptSyntaxEnabled();
    // CORS
    boolean isCORSEnabled = serverManager.isCORSEnabled();
    // XFF
    boolean isXFFEnabled = serverManager.isXFFEnabled();
    String xffHeader = serverManager.getXFFHeader();
    String xffServerHeader = serverManager.getXFFServerHeader();
    String xffHostHeader = serverManager.getXFFHostHeader();
    String xffHostName = serverManager.getXFFHostName();

      out.write("\n\n<html>\n<head>\n    <title>\n        ");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("\n    </title>\n    <meta name=\"pageID\" content=\"http-bind\"/>\n    <script type=\"text/javascript\">\n        var enabled = ");
      out.print(isHttpBindEnabled);
      out.write(";\n        var setEnabled = function() {\n            $(\"port\").disabled = !enabled;\n            $(\"securePort\").disabled = !enabled;\n            $(\"rb03\").disabled = !enabled;\n            $(\"rb04\").disabled = !enabled;\n            $(\"rb05\").disabled = !enabled;\n            $(\"rb06\").disabled = !enabled;\n            $(\"rb07\").disabled = !enabled;\n            $(\"rb08\").disabled = !enabled;\n            $(\"CORSDomains\").disabled = !enabled;\n            $(\"XFFHeader\").disabled = !enabled;\n            $(\"XFFServerHeader\").disabled = !enabled;\n            $(\"XFFHostHeader\").disabled = !enabled;\n            $(\"XFFHostName\").disabled = !enabled;\n            $(\"crossdomain\").disabled = !enabled;\n        }\n        window.onload = setTimeout(\"setEnabled()\", 500);\n    </script>\n</head>\n<body>\n<p>\n    ");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\n</p>\n");
 if (errorMap.size() > 0) {
    for (String key : errorMap.keySet()) { 
      out.write("\n<div class=\"error\" style=\"width: 400px\">\n    ");
 if (key.equals("port")) { 
      out.write("\n    ");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\n    ");
 }
    else { 
      out.write("\n    ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\n    ");
 } 
      out.write("\n</div>\n");
 }
} 
      out.write("\n\n<form action=\"http-bind.jsp\" method=\"post\">\n    \n    <div class=\"jive-contentBox\" style=\"-moz-border-radius: 3px;\">\n        <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n            <tbody>\n                <tr valign=\"top\">\n                    <td width=\"1%\" nowrap>\n                        <input type=\"radio\" name=\"httpBindEnabled\" value=\"true\" id=\"rb02\"\n                                onclick=\"enabled = true; setEnabled();\"\n                        ");
      out.print( (isHttpBindEnabled ? "checked" : "") );
      out.write(">\n                    </td>\n                    <td width=\"99%\" colspan=\"2\">\n                        <label for=\"rb02\">\n                            <b>\n                                ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\n                            </b> -\n                            ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\n                        </label>\n\n                        <table border=\"0\">\n                             <tr>\n                                <td>\n                                    <label for=\"port\">\n                                    ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\n                                    </label>\n                                </td><td>\n                                    <input id=\"port\" type=\"text\" size=\"5\" maxlength=\"10\" name=\"port\"\n                                           value=\"");
      out.print(port);
      out.write("\" />\n                                </td>\n                            </tr>\n                            <tr>\n                                <td>\n                                    <label for=\"securePort\">\n                                    ");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("\n                                    </label>\n                                </td><td>\n                                    <input id=\"securePort\" type=\"text\" size=\"5\" maxlength=\"10\" name=\"securePort\"\n                                           value=\"");
      out.print(securePort);
      out.write("\" />\n                                </td>\n                            </tr>\n                        </table>\n                    </td>\n                </tr>\n                <tr valign=\"top\">\n                    <td width=\"1%\" nowrap>\n                        <input type=\"radio\" name=\"httpBindEnabled\" value=\"false\" id=\"rb01\"\n                               onclick=\"enabled = false; setEnabled();\"\n                        ");
      out.print( (!isHttpBindEnabled ? "checked" : "") );
      out.write(">\n                    </td>\n                    <td width=\"99%\" colspan=\"2\">\n                        <label for=\"rb01\">\n                            <b>\n                                ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\n                            </b> -\n                            ");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\n                        </label>\n                    </td>\n                </tr>\n            </tbody>\n        </table>\n    </div>\n    <div class=\"jive-contentBoxHeader\">Script Syntax</div>\n    <div class=\"jive-contentbox\">\n\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n\t\t<tbody>\n\t\t\t<tr valign=\"middle\">\n\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t<input type=\"radio\" name=\"scriptSyntaxEnabled\" value=\"true\" id=\"rb03\"\n\t\t\t\t\t ");
      out.print( (isScriptSyntaxEnabled ? "checked" : "") );
      out.write(">\n\t\t\t\t</td>\n\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t<label for=\"rb03\">\n\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</label>\n\t\t\t\t</td>\n\t\t\t</tr>\n            <tr valign=\"middle\">\n\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t<input type=\"radio\" name=\"scriptSyntaxEnabled\" value=\"false\" id=\"rb04\"\n\t\t\t\t\t ");
      out.print( (!isScriptSyntaxEnabled ? "checked" : "") );
      out.write(">\n\t\t\t\t</td>\n\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t<label for=\"rb04\">\n\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</label>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</tbody>\n\t\t</table>\n    </div>\n    \n    <!-- CORS -->\n\t<div class=\"jive-contentBoxHeader\">");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</div>\n    <div class=\"jive-contentbox\">\n    \t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n\t\t<tbody>\n\t\t\t<tr valign=\"top\">\n\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t<input type=\"radio\" name=\"CORSEnabled\" value=\"true\" id=\"rb05\"\n\t\t\t\t\t ");
      out.print( (isCORSEnabled ? "checked" : "") );
      out.write(">\n\t\t\t\t</td>\n\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t<label for=\"rb05\">\n\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</label>\n\t\t\t\t\t<table border=\"0\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t<label for=\"CORSDomains\">\n\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t\t</label>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n                        <tr>\n                            <td>\n                                <input id=\"CORSDomains\" type=\"text\" size=\"80\" name=\"CORSDomains\" value=\"");
      out.print( StringUtils.escapeForXML(serverManager.getCORSAllowOrigin()) );
      out.write("\">\n                            </td>\n                        </tr>\n                    </table>\n\t\t\t\t</td>\n\t\t\t</tr>\n            <tr valign=\"top\">\n\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t<input type=\"radio\" name=\"CORSEnabled\" value=\"false\" id=\"rb06\"\n\t\t\t\t\t ");
      out.print( (!isCORSEnabled ? "checked" : "") );
      out.write(">\n\t\t\t\t</td>\n\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t<label for=\"rb06\">\n\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</label>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</tbody>\n\t\t</table>\n    </div>\n    <!-- CORS -->\n    \n    <!-- XFF -->\n\t<div class=\"jive-contentBoxHeader\">");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("</div>\n    <div class=\"jive-contentbox\">\n    \t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n\t\t<tbody>\n\t\t\t<tr valign=\"top\">\n\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t<input type=\"radio\" name=\"XFFEnabled\" value=\"true\" id=\"rb07\"\n\t\t\t\t\t ");
      out.print( (isXFFEnabled ? "checked" : "") );
      out.write(">\n\t\t\t\t</td>\n\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t<label for=\"rb07\">\n\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</label>\n\t\t\t\t\t<table border=\"0\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t<label for=\"XFFHeader\">");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("</label>\n\t\t\t\t\t\t\t</td>\n                            <td>\n                                <input id=\"XFFHeader\" type=\"text\" size=\"40\" name=\"XFFHeader\"  value=\"");
      out.print( xffHeader == null ? "" : StringUtils.escapeForXML(xffHeader) );
      out.write("\">\n                            </td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t<label for=\"XFFServerHeader\">");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("</label>\n\t\t\t\t\t\t\t</td>\n                            <td>\n                                <input id=\"XFFServerHeader\" type=\"text\" size=\"40\" name=\"XFFServerHeader\" value=\"");
      out.print( xffServerHeader == null ? "" : StringUtils.escapeForXML(xffServerHeader) );
      out.write("\">\n                            </td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t<label for=\"XFFHostHeader\">");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("</label>\n\t\t\t\t\t\t\t</td>\n                            <td>\n                                <input id=\"XFFHostHeader\" type=\"text\" size=\"40\" name=\"XFFHostHeader\" value=\"");
      out.print( xffHostHeader == null ? "" : StringUtils.escapeForXML(xffHostHeader) );
      out.write("\">\n                            </td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t<label for=\"XFFHostName\">");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("</label>\n\t\t\t\t\t\t\t</td>\n                            <td>\n                                <input id=\"XFFHostName\" type=\"text\" size=\"40\" name=\"XFFHostName\" value=\"");
      out.print( xffHostName == null ? "" : StringUtils.escapeForXML(xffHostName) );
      out.write("\">\n                            </td>\n\t\t\t\t\t\t</tr>\n                    </table>\n\t\t\t\t</td>\n\t\t\t</tr>\n            <tr valign=\"top\">\n\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t<input type=\"radio\" name=\"XFFEnabled\" value=\"false\" id=\"rb08\"\n\t\t\t\t\t ");
      out.print( (!isXFFEnabled ? "checked" : "") );
      out.write(">\n\t\t\t\t</td>\n\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t<label for=\"rb08\">\n\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("</b> - ");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</label>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</tbody>\n\t\t</table>\n    </div>\n    <!-- XFF -->\n    \n    <div class=\"jive-contentBoxHeader\">Cross-domain policy</div>\n    <div class=\"jive-contentbox\">\n    \t<p>");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("</p>\n    \t<p>");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("</p>\n    \t<p>");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write("</p>\n    \t<textarea id=\"crossdomain\" cols=\"120\" rows=\"10\" wrap=\"virtual\" readonly=\"readonly\">");
      out.print( (isHttpBindEnabled ? StringUtils.escapeForXML(FlashCrossDomainServlet.getCrossDomainContent()) : "") );
      out.write("</textarea>\n    </div>\n    \n    <input type=\"submit\" id=\"settingsUpdate\" name=\"update\"\n               value=\"");
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write("\">\n</form>\n</body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("httpbind.settings.title");
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
    _jspx_th_fmt_message_1.setKey("httpbind.settings.info");
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
    _jspx_th_fmt_message_2.setKey("httpbind.settings.error.port");
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
    _jspx_th_fmt_message_3.setKey("httpbind.settings.error.general");
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
    _jspx_th_fmt_message_4.setKey("httpbind.settings.label_enable");
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
    _jspx_th_fmt_message_5.setKey("httpbind.settings.label_enable_info");
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
    _jspx_th_fmt_message_6.setKey("httpbind.settings.vanilla_port");
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
    _jspx_th_fmt_message_7.setKey("httpbind.settings.secure_port");
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
    _jspx_th_fmt_message_8.setKey("httpbind.settings.label_disable");
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
    _jspx_th_fmt_message_9.setKey("httpbind.settings.label_disable_info");
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
    _jspx_th_fmt_message_10.setKey("httpbind.settings.script.label_enable");
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
    _jspx_th_fmt_message_11.setKey("httpbind.settings.script.label_enable_info");
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
    _jspx_th_fmt_message_12.setKey("httpbind.settings.script.label_disable");
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
    _jspx_th_fmt_message_13.setKey("httpbind.settings.script.label_disable_info");
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
    _jspx_th_fmt_message_14.setKey("httpbind.settings.cors.group");
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
    _jspx_th_fmt_message_15.setKey("httpbind.settings.cors.label_enable");
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
    _jspx_th_fmt_message_16.setKey("httpbind.settings.cors.label_enable_info");
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
    _jspx_th_fmt_message_17.setKey("httpbind.settings.cors.domain_list");
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
    _jspx_th_fmt_message_18.setKey("httpbind.settings.cors.label_disable");
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
    _jspx_th_fmt_message_19.setKey("httpbind.settings.cors.label_disable_info");
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
    _jspx_th_fmt_message_20.setKey("httpbind.settings.xff.group");
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
    _jspx_th_fmt_message_21.setKey("httpbind.settings.xff.label_enable");
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
    _jspx_th_fmt_message_22.setKey("httpbind.settings.xff.label_enable_info");
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
    _jspx_th_fmt_message_23.setKey("httpbind.settings.xff.forwarded_for");
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
    _jspx_th_fmt_message_24.setKey("httpbind.settings.xff.forwarded_server");
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
    _jspx_th_fmt_message_25.setKey("httpbind.settings.xff.forwarded_host");
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
    _jspx_th_fmt_message_26.setKey("httpbind.settings.xff.host_name");
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
    _jspx_th_fmt_message_27.setKey("httpbind.settings.xff.label_disable");
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
    _jspx_th_fmt_message_28.setKey("httpbind.settings.xff.label_disable_info");
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
    _jspx_th_fmt_message_29.setKey("httpbind.settings.crossdomain.info.general");
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
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_30 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_30.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_30.setParent(null);
    _jspx_th_fmt_message_30.setKey("httpbind.settings.crossdomain.info.override");
    int _jspx_eval_fmt_message_30 = _jspx_th_fmt_message_30.doStartTag();
    if (_jspx_eval_fmt_message_30 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_fmt_message_30 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_fmt_message_30.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_fmt_message_30.doInitBody();
      }
      do {
        out.write("\n            ");
        if (_jspx_meth_fmt_param_0(_jspx_th_fmt_message_30, _jspx_page_context))
          return true;
        out.write("\n        ");
        int evalDoAfterBody = _jspx_th_fmt_message_30.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_fmt_message_30 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
        out = _jspx_page_context.popBody();
    }
    if (_jspx_th_fmt_message_30.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_30);
      return true;
    }
    _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_30);
    return false;
  }

  private boolean _jspx_meth_fmt_param_0(javax.servlet.jsp.tagext.JspTag _jspx_th_fmt_message_30, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:param
    org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
    _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
    _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_30);
    _jspx_th_fmt_param_0.setValue(new String("<tt>&lt;openfireHome&gt;/conf/crossdomain.xml</tt>"));
    int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
    if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
      return true;
    }
    _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
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
    _jspx_th_fmt_message_31.setKey("httpbind.settings.crossdomain.info.policy");
    int _jspx_eval_fmt_message_31 = _jspx_th_fmt_message_31.doStartTag();
    if (_jspx_th_fmt_message_31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_31);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_31);
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
    _jspx_th_fmt_message_32.setKey("global.save_settings");
    int _jspx_eval_fmt_message_32 = _jspx_th_fmt_message_32.doStartTag();
    if (_jspx_th_fmt_message_32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
    return false;
  }
}
