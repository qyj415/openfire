package org.jivesoftware.openfire.plugin.xmldebugger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.plugin.DebuggerPlugin;
import org.jivesoftware.openfire.XMPPServer;

public final class debugger_002dconf_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n<html>\n    <head>\n        <title>XML Debugger Properties</title>\n        <meta name=\"pageID\" content=\"debugger-conf\"/>\n    </head>\n    <body>\n\n");

    boolean update = request.getParameter("update") != null;
    boolean c2s = ParamUtils.getBooleanParameter(request,"c2s");
    boolean ssl = ParamUtils.getBooleanParameter(request,"ssl");
    boolean extcomp = ParamUtils.getBooleanParameter(request,"extcomp");
    boolean cm = ParamUtils.getBooleanParameter(request,"cm");

    DebuggerPlugin plugin = (DebuggerPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("xmldebugger");
    if (update) {
        // Save new settings
        plugin.getDefaultPortFilter().setEnabled(c2s);
        plugin.getOldPortFilter().setEnabled(ssl);
        plugin.getComponentPortFilter().setEnabled(extcomp);
        plugin.getMultiplexerPortFilter().setEnabled(cm);
    }
    else {
        // Set current values
        c2s = plugin.getDefaultPortFilter().isEnabled();
        ssl = plugin.getOldPortFilter().isEnabled();
        extcomp = plugin.getComponentPortFilter().isEnabled();
        cm = plugin.getMultiplexerPortFilter().isEnabled();
    }

      out.write("\n\n\n<form name=\"f\" action=\"debugger-conf.jsp\">\n\t<div class=\"jive-contentBoxHeader\">\n\t\tDebug connections\n\t</div>\n\t<div class=\"jive-contentBox\">\n\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n\t\t<tbody>\n\t\t<tr valign=\"middle\">\n\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t<input id=\"rb01\" type=\"checkbox\" name=\"c2s\" ");
      out.print( (c2s ? "checked" : "") );
      out.write("/>\n\t\t\t</td>\n\t\t\t<td width=\"99%\">\n\t\t\t\t<label for=\"rb01\">\n\t\t\t\t\tClient (default port)\n\t\t\t\t</label>\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr valign=\"middle\">\n\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t<input id=\"rb02\" type=\"checkbox\" name=\"ssl\" ");
      out.print( (ssl ? "checked" : "") );
      out.write("/>\n\t\t\t</td>\n\t\t\t<td width=\"99%\">\n\t\t\t\t<label for=\"rb02\">\n\t\t\t\t\tClient (old SSL port)\n\t\t\t\t</label>\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr valign=\"middle\">\n\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t<input id=\"rb03\" type=\"checkbox\" name=\"extcomp\" ");
      out.print( (extcomp ? "checked" : "") );
      out.write("/>\n\t\t\t</td>\n\t\t\t<td width=\"99%\">\n\t\t\t\t<label for=\"rb03\">\n\t\t\t\t\tExternal Component\n\t\t\t\t</label>\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr valign=\"middle\">\n\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t<input id=\"rb04\" type=\"checkbox\" name=\"cm\" ");
      out.print( (cm ? "checked" : "") );
      out.write("/>\n\t\t\t</td>\n\t\t\t<td width=\"99%\">\n\t\t\t\t<label for=\"rb04\">\n\t\t\t\t\tConnection Manager\n\t\t\t\t</label>\n\t\t\t</td>\n\t\t</tr>\n\t\t</tbody>\n\t\t</table>\n\t</div>\n    <input type=\"submit\" name=\"update\" value=\"");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("\">\n</form>\n\n</body>\n</html>");
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
    _jspx_th_fmt_message_0.setKey("global.save_settings");
    int _jspx_eval_fmt_message_0 = _jspx_th_fmt_message_0.doStartTag();
    if (_jspx_th_fmt_message_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
    return false;
  }
}
