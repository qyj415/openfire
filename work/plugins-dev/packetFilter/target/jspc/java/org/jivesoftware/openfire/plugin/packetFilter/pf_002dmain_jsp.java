package org.jivesoftware.openfire.plugin.packetFilter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.PacketFilterUtil;
import org.jivesoftware.openfire.component.InternalComponentManager;
import org.jivesoftware.openfire.plugin.component.ComponentList;
import org.jivesoftware.openfire.plugin.rules.Rule;
import org.jivesoftware.openfire.plugin.rules.RuleManager;
import org.jivesoftware.openfire.plugin.rules.RuleManagerProxy;
import org.xmpp.packet.JID;
import java.util.List;

public final class pf_002dmain_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n\n\n\n");
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
    RuleManager rm = new RuleManagerProxy();
    ComponentList componentManager = ComponentList.getInstance();
    int i = 0;

    boolean moveOne = request.getParameter("moveOne") != null;

    if (moveOne) {
        int destId = new Integer(request.getParameter("moveOne")).intValue();
        int srcId = new Integer(request.getParameter("ruleId")).intValue();
        rm.moveOne(srcId, destId);
        response.sendRedirect("pf-main.jsp");
    }

    List<Rule> rules = rm.getRules();
    int lastOrder = rm.getLastOrder();

      out.write("\n\n<html>\n<head>\n    <style type=\"text/css\">\n        <!--\n        @import url( \"style/packetfilter.css\" );\n        -->\n    </style>\n    <title>\n        ");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("\n    </title>\n    <meta name=\"pageID\" content=\"packetFilter\"/>\n    <meta name=\"helpPage\" content=\"\"/>\n    <script language=\"JavaScript\" type=\"text/javascript\" src=\"scripts/packetfilter.js\"></script>\n    \n\n</head>\n<body>\n\n\n<div class=\"jive-table\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n        <thead>\n            <tr>\n                <th nowrap>Rule Type</th>\n                <th nowrap>From</th>\n                <th nowrap>To</th>\n                <th nowrap>Packet Type</th>\n                <th nowrap>Log</th>\n                <th>&nbsp</th>\n            </tr>\n        </thead>\n\n        <tbody>\n            ");

                for (Rule rule : rules) {


            
      out.write("\n\n            <tr class=\"jive-");
      out.print( (((i%2)==0) ? "even" : "odd") );
      out.write("\">\n\n                ");
 if (rule.isDisabled()) { 
      out.write("\n                       <td><strike>");
      out.print(rule.getDisplayName());
      out.write("</strike></td>\n                    ");
} else {
      out.write("\n                        <td>");
      out.print(rule.getDisplayName());
      out.write("</td>\n                    ");
}
      out.write("\n                ");
 if (rule.isDisabled()) { 
      out.write("\n                      <td><strike>");
      out.print(rule.getSource());
      out.write("</strike></td>\n                    ");
} else if (rule.getSourceType().equals(Rule.SourceDestType.Component.toString())
                            && componentManager.getComponentName(new JID(rule.getSource()))!= null) { 
      out.write("\n                        <td>");
      out.print(componentManager.getComponentName(new JID(rule.getSource())));
      out.write("</td>\n                    ");
 } else {
      out.write("\n                       <td>");
      out.print(PacketFilterUtil.formatRuleSourceDest(rule.getSource()));
      out.write("</td>\n                    ");
}
      out.write("\n                ");
 if (rule.isDisabled()) { 
      out.write("\n                         <td><strike>");
      out.print(rule.getDestination());
      out.write("</strike></td>\n                    ");
} else if (rule.getDestType().equals(Rule.SourceDestType.Component.toString())
                            && componentManager.getComponentName(new JID(rule.getDestination()))!= null) {
      out.write("\n                          <td>");
      out.print(componentManager.getComponentName(new JID(rule.getDestination())));
      out.write("</td>\n                   ");
 }else {
      out.write("\n                          <td>");
      out.print(PacketFilterUtil.formatRuleSourceDest(rule.getDestination()));
      out.write("</td>\n                    ");
}
      out.write("\n                ");
 if (rule.isDisabled()) { 
      out.write("\n                         <td><strike>");
      out.print(rule.getPackeType());
      out.write("</strike></td>\n                    ");
} else {
      out.write("\n                        <td>");
      out.print(rule.getPackeType().getDisplayName());
      out.write("</td>\n                    ");
}
      out.write("\n                ");
 if (rule.isDisabled()) { 
      out.write("\n                       <td><strike>");
      out.print(rule.doLog());
      out.write("</strike></td>\n                    ");
} else {
      out.write("\n                      <td>");
      out.print(rule.doLog());
      out.write("</td>\n                    ");
}
      out.write("\n\n                <td><a href=\"delete-rule.jsp?ruleId=");
      out.print(rule.getRuleId());
      out.write("\"><img src=\"/images/delete-16x16.gif\"\n                                                                                width=\"16\" height=\"16\"\n                                                                                border=\"0\"\n                                                                                alt=\"");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\"></a>\n                    <!--<a href=\"rule-form.jsp?beforeId=");
      out.print(rule.getRuleId());
      out.write("\">Before</a>\n                    <a href=\"rule-form.jsp?afterId=");
      out.print(rule.getRuleId());
      out.write("\">After</a>-->\n                    ");
if (rule.getOrder() > 1 && i>0) {
      out.write("\n                    <a href=\"pf-main.jsp?moveOne=");
      out.print(rules.get(i-1).getRuleId());
      out.write("&ruleId=");
      out.print(rule.getRuleId());
      out.write("\"><img src=\"arrow_up.png\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\"></a>\n                    ");
}
      out.write("\n                     ");
if (rule.getOrder() != lastOrder) {
      out.write("\n                    <a href=\"pf-main.jsp?moveOne=");
      out.print(rules.get(i+1).getRuleId());
      out.write("&ruleId=");
      out.print(rule.getRuleId());
      out.write("\"><img src=\"arrow_down.png\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\"></a>\n                    ");
}
      out.write("\n                    <a href=\"rule-edit-form.jsp?edit=");
      out.print(rule.getRuleId());
      out.write("\"><img src=\"/images/edit-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\"></a>\n                </td>\n            </tr>\n            ");

                i++;
                } 
      out.write("\n        </tbody>\n    </table>\n    </div>\n    <input type=\"button\" ONCLICK=\"window.location.href='rule-form.jsp'\" name=\"create\" value=\"");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\">\n\n</body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("pf.summary.title");
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
    _jspx_th_fmt_message_1.setKey("global.click_delete");
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
    _jspx_th_fmt_message_2.setKey("pf.click.up");
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
    _jspx_th_fmt_message_3.setKey("pf.click.down");
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
    _jspx_th_fmt_message_4.setKey("pf.click.edit");
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
    _jspx_th_fmt_message_5.setKey("pf.create.new.rule");
    int _jspx_eval_fmt_message_5 = _jspx_th_fmt_message_5.doStartTag();
    if (_jspx_th_fmt_message_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_5);
    return false;
  }
}
