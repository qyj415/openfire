package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.audit.AuditManager;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.StringUtils;
import org.xmpp.packet.JID;
import java.io.File;
import java.util.*;

public final class audit_002dpolicy_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n\n");
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
      out.write("\n\n<html>\n<head>\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n<meta name=\"pageID\" content=\"server-audit-policy\"/>\n<meta name=\"helpPage\" content=\"set_server_traffic_auditing_policy.html\"/>\n</head>\n<body>\n\n\n\n");
   // Get parameters:
    boolean update = request.getParameter("update") != null;
    boolean auditEnabled = ParamUtils.getBooleanParameter(request,"auditEnabled");
    boolean auditMessages = ParamUtils.getBooleanParameter(request,"auditMessages");
    boolean auditPresence = ParamUtils.getBooleanParameter(request,"auditPresence");
    boolean auditIQ = ParamUtils.getBooleanParameter(request,"auditIQ");
    String maxTotalSize = ParamUtils.getParameter(request,"maxTotalSize");
    String maxFileSize = ParamUtils.getParameter(request,"maxFileSize");
    String maxDays = ParamUtils.getParameter(request,"maxDays");
    String logTimeout = ParamUtils.getParameter(request,"logTimeout");
    String logDir = ParamUtils.getParameter(request,"logDir");
    String ignore = ParamUtils.getParameter(request,"ignore");


    // Get an audit manager:
    AuditManager auditManager = XMPPServer.getInstance().getAuditManager();

    Map<String,String> errors = new HashMap<String,String>();
    if (update) {
        auditManager.setEnabled(auditEnabled);
        auditManager.setAuditMessage(auditMessages);
        auditManager.setAuditPresence(auditPresence);
        auditManager.setAuditIQ(auditIQ);
        /*
        auditManager.setAuditXPath(auditXPath);
        if (newXpathQuery != null) {
            auditManager.addXPath(newXpathQuery);
        }
        for (int i=0; i<xpathQuery.length; i++) {
            auditManager.removeXPath(xpathQuery[i]);
        }
        */
        try {
            auditManager.setMaxTotalSize(Integer.parseInt(maxTotalSize));
        } catch (Exception e){
            errors.put("maxTotalSize","maxTotalSize");
        }
        try {
            auditManager.setMaxFileSize(Integer.parseInt(maxFileSize));
        } catch (Exception e){
            errors.put("maxFileSize","maxFileSize");
        }
        try {
            auditManager.setMaxDays(Integer.parseInt(maxDays));
        } catch (Exception e){
            errors.put("maxDays","maxDays");
        }
        try {
            auditManager.setLogTimeout(Integer.parseInt(logTimeout) * 1000);
        } catch (Exception e){
            errors.put("logTimeout","logTimeout");
        }
        if (logDir == null || logDir.trim().length() == 0) {
            errors.put("logDir","logDir");
        }
        else {
            if (new File(logDir).isDirectory()) {
                auditManager.setLogDir(logDir);
            }
            else {
                errors.put("logDir","logDir");
            }
        }
        if (errors.size() == 0){
            if (ignore == null){
                // remove all ignored users
                auditManager.setIgnoreList(new ArrayList<String>());
            }
            else {
                // Set the new ignore list
                Collection<String> newIgnoreList = new HashSet<String>(ignore.length());
                StringTokenizer tokenizer = new StringTokenizer(ignore, ", \t\n\r\f");
                while (tokenizer.hasMoreTokens()) {
                    String tok = tokenizer.nextToken();
                    String username = tok;
                    if (tok.contains("@")) {
                        if (tok.contains("@" + webManager.getServerInfo().getXMPPDomain())) {
                           username = new JID(tok).getNode();
                        }
                        else {
                            // Skip this JID since it belongs to a remote server
                            continue;
                        }
                    }
                    try {
                        webManager.getUserManager().getUser(username);
                        newIgnoreList.add(username);
                    }
                    catch (UserNotFoundException e){
                    }
                }
                auditManager.setIgnoreList(newIgnoreList);
            }
            // Log the event
            // TODO: Should probably log more here
            webManager.logEvent("updated stanza audit policy", null);
        // All done, redirect
        
      out.write("\n\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n        <td class=\"jive-icon-label\">\n        ");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\n        </td></tr>\n    </tbody>\n    </table>\n    </div><br>\n\n        ");

        }
    }

    // Set page vars
    if (errors.size() == 0) {
        auditEnabled = auditManager.isEnabled();
        auditMessages = auditManager.isAuditMessage();
        auditPresence = auditManager.isAuditPresence();
        auditIQ = auditManager.isAuditIQ();
        maxTotalSize = Integer.toString(auditManager.getMaxTotalSize());
        maxFileSize = Integer.toString(auditManager.getMaxFileSize());
        maxDays = Integer.toString(auditManager.getMaxDays());
        logTimeout = Integer.toString(auditManager.getLogTimeout() / 1000);
        logDir = auditManager.getLogDir();
        StringBuilder ignoreList = new StringBuilder();
        for (String username : auditManager.getIgnoreList()) {
            if (ignoreList.length() == 0) {
                ignoreList.append(username);
            }
            else {
                ignoreList.append(", ").append(username);
            }
        }
        ignore = ignoreList.toString();
    }

      out.write("\n\n<p>\n");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write('\n');
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\n</p>\n\n<!-- BEGIN 'Set Message Audit Policy' -->\n<form action=\"audit-policy.jsp\" name=\"f\">\n\t<div class=\"jive-contentBoxHeader\">\n\t\t");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\n\t</div>\n\t<div class=\"jive-contentBox\">\n\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n\t\t<tbody>\n\t\t\t<tr valign=\"middle\">\n\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t<input type=\"radio\" name=\"auditEnabled\" value=\"false\" id=\"rb01\"\n\t\t\t\t\t ");
      out.print( (!auditEnabled ? "checked" : "") );
      out.write(">\n\t\t\t\t</td>\n\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t<label for=\"rb01\">\n\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</b> ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</label>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr valign=\"middle\">\n\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t<input type=\"radio\" name=\"auditEnabled\" value=\"true\" id=\"rb02\"\n\t\t\t\t\t ");
      out.print( (auditEnabled ? "checked" : "") );
      out.write(">\n\t\t\t\t</td>\n\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t<label for=\"rb02\">\n\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</b> ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t</label>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr valign=\"top\">\n\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t&nbsp;\n\t\t\t\t</td>\n\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td width=\"1%\" nowrap class=\"c1\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t<input type=\"text\" size=\"50\" maxlength=\"150\" name=\"logDir\"\n\t\t\t\t\t\t\t value=\"");
      out.print( ((logDir != null) ? StringUtils.escapeForXML(logDir) : "") );
      out.write("\">\n\n\t\t\t\t\t\t");
  if (errors.get("logDir") != null) { 
      out.write("\n\n\t\t\t\t\t\t\t<span class=\"jive-error-text\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t</span>\n\n\t\t\t\t\t\t");
  } 
      out.write("\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td width=\"1%\" nowrap class=\"c1\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t<input type=\"text\" size=\"15\" maxlength=\"50\" name=\"maxTotalSize\"\n\t\t\t\t\t\t\t value=\"");
      out.print( ((maxTotalSize != null) ? maxTotalSize : "") );
      out.write("\">\n\n\t\t\t\t\t\t");
  if (errors.get("maxTotalSize") != null) { 
      out.write("\n\n\t\t\t\t\t\t\t<span class=\"jive-error-text\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t</span>\n\n\t\t\t\t\t\t");
  } 
      out.write("\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td width=\"1%\" nowrap class=\"c1\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t<input type=\"text\" size=\"15\" maxlength=\"50\" name=\"maxFileSize\"\n\t\t\t\t\t\t\t value=\"");
      out.print( ((maxFileSize != null) ? maxFileSize : "") );
      out.write("\">\n\n\t\t\t\t\t\t");
  if (errors.get("maxFileSize") != null) { 
      out.write("\n\n\t\t\t\t\t\t\t<span class=\"jive-error-text\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t</span>\n\n\t\t\t\t\t\t");
  } 
      out.write("\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td width=\"1%\" nowrap class=\"c1\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t<input type=\"text\" size=\"15\" maxlength=\"50\" name=\"maxDays\"\n\t\t\t\t\t\t\t value=\"");
      out.print( ((maxDays != null) ? maxDays : "") );
      out.write("\">\n\n\t\t\t\t\t\t\t");
  if (errors.get("maxDays") != null) { 
      out.write("\n\n\t\t\t\t\t\t\t\t<span class=\"jive-error-text\">\n\t\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t\t</span>\n\n\t\t\t\t\t\t\t");
  } 
      out.write("\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td width=\"1%\" nowrap class=\"c1\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t<input type=\"text\" size=\"15\" maxlength=\"50\" name=\"logTimeout\"\n\t\t\t\t\t\t\t value=\"");
      out.print( ((logTimeout != null) ? logTimeout : "") );
      out.write("\">\n\n\t\t\t\t\t\t");
  if (errors.get("logTimeout") != null) { 
      out.write("\n\n\t\t\t\t\t\t\t<span class=\"jive-error-text\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t</span>\n\n\t\t\t\t\t\t");
  } 
      out.write("\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td width=\"1%\" nowrap class=\"c1\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"99%\">\n\n\t\t\t\t\t\t\t<table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"auditMessages\" id=\"cb01\"\n\t\t\t\t\t\t\t\t\t onclick=\"this.form.auditEnabled[1].checked=true;\"\n\t\t\t\t\t\t\t\t\t ");
      out.print( (auditMessages ? "checked" : "") );
      out.write(">\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t\t\t<label for=\"cb01\">\n\t\t\t\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("</b>\n\t\t\t\t\t\t\t\t\t</label>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"auditPresence\" id=\"cb02\"\n\t\t\t\t\t\t\t\t\t onclick=\"this.form.auditEnabled[1].checked=true;\"\n\t\t\t\t\t\t\t\t\t ");
      out.print( (auditPresence ? "checked" : "") );
      out.write(">\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t\t\t<label for=\"cb02\">\n\t\t\t\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</b>\n\t\t\t\t\t\t\t\t\t</label>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"auditIQ\" id=\"cb03\"\n\t\t\t\t\t\t\t\t\t onclick=\"this.form.auditEnabled[1].checked=true;\"\n\t\t\t\t\t\t\t\t\t ");
      out.print( (auditIQ ? "checked" : "") );
      out.write(">\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t\t\t<label for=\"cb03\">\n\t\t\t\t\t\t\t\t\t<b>");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("</b>\n\t\t\t\t\t\t\t\t\t</label>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td width=\"1%\" nowrap class=\"c1\">\n\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t<textarea name=\"ignore\" cols=\"40\" rows=\"3\" wrap=\"virtual\">");
      out.print( ((ignore != null) ? StringUtils.escapeHTMLTags(ignore) : "") );
      out.write("</textarea>\n\t\t\t\t\t\t\t");
  if (errors.get("ignore") != null) { 
      out.write("\n\n\t\t\t\t\t\t\t\t<span class=\"jive-error-text\">\n\t\t\t\t\t\t\t\t");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t\t\t</span>\n\n\t\t\t\t\t\t\t");
  } 
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</tbody>\n\t\t</table>\n\t\t<table border=\"0\">\n\t\t\t<tr valign=\"top\">\n\t\t\t\t<td width=\"1%\" nowrap class=\"c1\">\n\t\t\t\t\t");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t</td>\n\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t ");
      out.print( auditManager.getAuditor().getQueuedPacketsNumber() );
      out.write("\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t</div>\n    <input type=\"submit\" name=\"update\" value=\"");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("\">\n</form>\n<!-- END 'Set Message Audit Policy' -->\n\n\n</body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("audit.policy.title");
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
    _jspx_th_fmt_message_1.setKey("audit.policy.settings.saved_successfully");
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
    _jspx_th_fmt_message_2.setKey("title");
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
    _jspx_th_fmt_message_3.setKey("audit.policy.title_info");
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
    _jspx_th_fmt_message_4.setKey("audit.policy.policytitle");
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
    _jspx_th_fmt_message_5.setKey("audit.policy.label_disable_auditing");
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
    _jspx_th_fmt_message_6.setKey("audit.policy.label_disable_auditing_info");
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
    _jspx_th_fmt_message_7.setKey("audit.policy.label_enable_auditing");
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
    _jspx_th_fmt_message_8.setKey("audit.policy.label_enable_auditing_info");
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
    _jspx_th_fmt_message_9.setKey("audit.policy.log_directory");
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
    _jspx_th_fmt_message_10.setKey("audit.policy.valid_log_directory");
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
    _jspx_th_fmt_message_11.setKey("audit.policy.maxtotal_size");
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
    _jspx_th_fmt_message_12.setKey("audit.policy.validnumber");
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
    _jspx_th_fmt_message_13.setKey("audit.policy.maxfile_size");
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
    _jspx_th_fmt_message_14.setKey("audit.policy.validnumber");
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
    _jspx_th_fmt_message_15.setKey("audit.policy.maxdays_number");
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
    _jspx_th_fmt_message_16.setKey("audit.policy.validnumber");
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
    _jspx_th_fmt_message_17.setKey("audit.policy.flush_interval");
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
    _jspx_th_fmt_message_18.setKey("audit.policy.validnumber");
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
    _jspx_th_fmt_message_19.setKey("audit.policy.packet_audit");
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
    _jspx_th_fmt_message_20.setKey("audit.policy.label_audit_messenge_packets");
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
    _jspx_th_fmt_message_21.setKey("audit.policy.label_audit_presence_packets");
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
    _jspx_th_fmt_message_22.setKey("audit.policy.label_audit_iq_packets");
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
    _jspx_th_fmt_message_23.setKey("audit.policy.ignore");
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
    _jspx_th_fmt_message_24.setKey("audit.policy.validignore");
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
    _jspx_th_fmt_message_25.setKey("audit.policy.queued_packets");
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
    _jspx_th_fmt_message_26.setKey("global.save_settings");
    int _jspx_eval_fmt_message_26 = _jspx_th_fmt_message_26.doStartTag();
    if (_jspx_th_fmt_message_26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
    return false;
  }
}
