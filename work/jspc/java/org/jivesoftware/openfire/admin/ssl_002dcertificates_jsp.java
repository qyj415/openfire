package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.CertificateManager;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.StringUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.net.SSLConfig;
import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.net.URLEncoder;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.container.AdminConsolePlugin;
import java.io.IOException;

public final class ssl_002dcertificates_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n");
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
 // Get parameters:
    boolean generate = ParamUtils.getBooleanParameter(request, "generate");
    boolean delete = ParamUtils.getBooleanParameter(request, "delete");
    boolean importReply = ParamUtils.getBooleanParameter(request, "importReply");
    String type = ParamUtils.getParameter(request, "type");
    String alias = ParamUtils.getParameter(request, "alias");
    Map<String, Object> errors = new HashMap<String, Object>();
    KeyStore keyStore = null;

    try {
        keyStore = SSLConfig.getKeyStore();
    }
    catch (IOException e) {
        e.printStackTrace();
        errors.put("ioerror", e);
    }

    if (generate) {
        String domain = XMPPServer.getInstance().getServerInfo().getXMPPDomain();
        try {
            if (errors.containsKey("ioerror") && keyStore == null) {
                keyStore = SSLConfig.initializeKeyStore();
            }
            if (errors.containsKey("ioerror") || !CertificateManager.isDSACertificate(keyStore, domain)) {
                CertificateManager
                        .createDSACert(keyStore, SSLConfig.getKeyPassword(), domain + "_dsa", "cn=" + domain, "cn=" + domain, "*." + domain);
            }
            if (errors.containsKey("ioerror") || !CertificateManager.isRSACertificate(keyStore, domain)) {
                CertificateManager
                        .createRSACert(keyStore, SSLConfig.getKeyPassword(), domain + "_rsa", "cn=" + domain, "cn=" + domain, "*." + domain);
            }
            // Save new certificates into the key store
            SSLConfig.saveStores();
            // Log the event
            webManager.logEvent("generated SSL self-signed certs", null);
            response.sendRedirect("ssl-certificates.jsp?generatesuccess=true");
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            errors.put("generate", e);
        }
    }
    if (delete) {
        if (type != null && alias != null) {
            try {
                CertificateManager.deleteCertificate(keyStore, alias);
                SSLConfig.saveStores();
                // Log the event
                webManager.logEvent("deleted SSL cert with alias "+alias, null);
                response.sendRedirect("ssl-certificates.jsp?deletesuccess=true");
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
                errors.put("delete", e);
            }
        }
    }
    if (importReply) {
        String reply = ParamUtils.getParameter(request, "reply");
        if (alias != null && reply != null && reply.trim().length() > 0) {
            try {
                CertificateManager.installReply(SSLConfig.getKeyStore(), SSLConfig.gets2sTrustStore(),
                        SSLConfig.getKeyPassword(), alias, new ByteArrayInputStream(reply.getBytes()), true, true);
                SSLConfig.saveStores();
                // Log the event
                webManager.logEvent("imported SSL certificate with alias "+alias, null);
                response.sendRedirect("ssl-certificates.jsp?importsuccess=true");
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
                errors.put("importReply", e);
            }
        }
    }

    PluginManager pluginManager = XMPPServer.getInstance().getPluginManager();

      out.write("\n\n<html>\n  <head>\n      <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n      <meta name=\"pageID\" content=\"ssl-certificates\"/>\n  </head>\n  <body>\n\n  ");
 if (((AdminConsolePlugin) pluginManager.getPlugin("admin")).isRestartNeeded()) { 
      out.write("\n      <div class=\"warning\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr>\n          <td class=\"jive-icon-label\">\n              ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_1 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_1.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_1.setParent(null);
      _jspx_th_fmt_message_1.setKey("ssl.certificates.restart_server");
      int _jspx_eval_fmt_message_1 = _jspx_th_fmt_message_1.doStartTag();
      if (_jspx_eval_fmt_message_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_1.doInitBody();
        }
        do {
          out.write("\n                  ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_1);
          _jspx_th_fmt_param_0.setValue( "<a href='server-restart.jsp?page=ssl-certificates.jsp'>" );
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          out.write("\n                  ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_1 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_1.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_1);
          _jspx_th_fmt_param_1.setValue( "</a>" );
          int _jspx_eval_fmt_param_1 = _jspx_th_fmt_param_1.doStartTag();
          if (_jspx_th_fmt_param_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
          out.write("\n              ");
          int evalDoAfterBody = _jspx_th_fmt_message_1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_1);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_1);
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n  ");
  } else if (errors.containsKey("ioerror")) {
          Exception e = (Exception)errors.get("ioerror");
  
      out.write("\n      <div class=\"jive-error\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr><td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n          <td class=\"jive-icon-label\">\n          ");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("<br />\n          ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_3 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_3.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_3.setParent(null);
      _jspx_th_fmt_message_3.setKey("ssl.certificates.no_installed");
      int _jspx_eval_fmt_message_3 = _jspx_th_fmt_message_3.doStartTag();
      if (_jspx_eval_fmt_message_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_3.doInitBody();
        }
        do {
          out.write("\n              ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_2 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_2.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_3);
          _jspx_th_fmt_param_2.setValue( "<a href='ssl-certificates.jsp?generate=true'>" );
          int _jspx_eval_fmt_param_2 = _jspx_th_fmt_param_2.doStartTag();
          if (_jspx_th_fmt_param_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_2);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_2);
          out.write("\n              ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_3 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_3.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_3);
          _jspx_th_fmt_param_3.setValue( "</a>" );
          int _jspx_eval_fmt_param_3 = _jspx_th_fmt_param_3.doStartTag();
          if (_jspx_th_fmt_param_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_3);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_3);
          out.write("\n              ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_4 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_4.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_3);
          _jspx_th_fmt_param_4.setValue( "<a href='import-certificate.jsp'>" );
          int _jspx_eval_fmt_param_4 = _jspx_th_fmt_param_4.doStartTag();
          if (_jspx_th_fmt_param_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_4);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_4);
          out.write("\n              ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_5 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_5.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_3);
          _jspx_th_fmt_param_5.setValue( "</a>" );
          int _jspx_eval_fmt_param_5 = _jspx_th_fmt_param_5.doStartTag();
          if (_jspx_th_fmt_param_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_5);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_5);
          out.write("\n          ");
          int evalDoAfterBody = _jspx_th_fmt_message_3.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_3);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_3);
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n  ");
 } else if (keyStore != null && keyStore.size() > 1 && !CertificateManager.isRSACertificate(SSLConfig.getKeyStore(), XMPPServer.getInstance().getServerInfo().getXMPPDomain())) { 
      out.write("\n      <div class=\"warning\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr>\n          <td class=\"jive-icon-label\">\n              ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n  ");
 } else if (keyStore != null && keyStore.size() < 2 ) { 
      out.write("\n      <div class=\"warning\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr>\n          <td class=\"jive-icon-label\">\n              ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_5 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_5.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_5.setParent(null);
      _jspx_th_fmt_message_5.setKey("ssl.certificates.no_installed");
      int _jspx_eval_fmt_message_5 = _jspx_th_fmt_message_5.doStartTag();
      if (_jspx_eval_fmt_message_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_5 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_5.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_5.doInitBody();
        }
        do {
          out.write("\n                  ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_6 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_6.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_5);
          _jspx_th_fmt_param_6.setValue( "<a href='ssl-certificates.jsp?generate=true'>" );
          int _jspx_eval_fmt_param_6 = _jspx_th_fmt_param_6.doStartTag();
          if (_jspx_th_fmt_param_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_6);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_6);
          out.write("\n                  ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_7 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_7.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_5);
          _jspx_th_fmt_param_7.setValue( "</a>" );
          int _jspx_eval_fmt_param_7 = _jspx_th_fmt_param_7.doStartTag();
          if (_jspx_th_fmt_param_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_7);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_7);
          out.write("\n                  ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_8 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_8.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_5);
          _jspx_th_fmt_param_8.setValue( "<a href='import-certificate.jsp'>" );
          int _jspx_eval_fmt_param_8 = _jspx_th_fmt_param_8.doStartTag();
          if (_jspx_th_fmt_param_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_8);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_8);
          out.write("\n                  ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_9 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_9.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_5);
          _jspx_th_fmt_param_9.setValue( "</a>" );
          int _jspx_eval_fmt_param_9 = _jspx_th_fmt_param_9.doStartTag();
          if (_jspx_th_fmt_param_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_9);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_9);
          out.write("\n              ");
          int evalDoAfterBody = _jspx_th_fmt_message_5.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_5 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_5);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_5);
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n  ");
  } else if (ParamUtils.getBooleanParameter(request,"addupdatesuccess")) { 
      out.write("\n\n      <div class=\"jive-success\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n          <td class=\"jive-icon-label\">\n          ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n\n  ");
  } else if (ParamUtils.getBooleanParameter(request,"generatesuccess")) { 
      out.write("\n\n      <div class=\"jive-success\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n          <td class=\"jive-icon-label\">\n          ");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n\n  ");
  } else if (ParamUtils.getBooleanParameter(request,"deletesuccess")) { 
      out.write("\n\n      <div class=\"jive-success\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n          <td class=\"jive-icon-label\">\n          ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n\n  ");
  } else if (errors.containsKey("delete")) {
          Exception e = (Exception)errors.get("delete");
  
      out.write("\n\n      <div class=\"jive-error\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr><td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n          <td class=\"jive-icon-label\">\n          ");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\n          ");
  if (e != null && e.getMessage() != null) { 
      out.write("\n              ");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write(':');
      out.write(' ');
      out.print( e.getMessage() );
      out.write("\n          ");
  } 
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n\n  ");
  } else if (ParamUtils.getBooleanParameter(request,"issuerUpdated")) { 
      out.write("\n\n      <div class=\"jive-success\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n          <td class=\"jive-icon-label\">\n          ");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n\n  ");
  } else if (ParamUtils.getBooleanParameter(request,"importsuccess")) { 
      out.write("\n\n      <div class=\"jive-success\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n          <td class=\"jive-icon-label\">\n          ");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n\n  ");
  } else if (errors.containsKey("importReply")) {  
      out.write("\n\n      <div class=\"jive-error\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr><td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n          <td class=\"jive-icon-label\">\n          ");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n\n  ");
  } else if (errors.containsKey("generate")) {
          Exception e = (Exception)errors.get("generate");
  
      out.write("\n\n      <div class=\"jive-error\">\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n      <tbody>\n          <tr><td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n          <td class=\"jive-icon-label\">\n          ");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("\n          ");
  if (e != null && e.getMessage() != null) { 
      out.write("\n              ");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write(':');
      out.write(' ');
      out.print( e.getMessage() );
      out.write("\n          ");
  } 
      out.write("\n          </td></tr>\n      </tbody>\n      </table>\n      </div><br>\n  ");
 } 
      out.write("\n\n  <!-- BEGIN 'Installed Certificates' -->\n  <p>\n  ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_16 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_16.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_16.setParent(null);
      _jspx_th_fmt_message_16.setKey("ssl.certificates.info");
      int _jspx_eval_fmt_message_16 = _jspx_th_fmt_message_16.doStartTag();
      if (_jspx_eval_fmt_message_16 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_16 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_16.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_16.doInitBody();
        }
        do {
          out.write("\n      ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_10 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_10.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_16);
          _jspx_th_fmt_param_10.setValue( "<a href='import-certificate.jsp'>" );
          int _jspx_eval_fmt_param_10 = _jspx_th_fmt_param_10.doStartTag();
          if (_jspx_th_fmt_param_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_10);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_10);
          out.write("\n      ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_11 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_11.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_16);
          _jspx_th_fmt_param_11.setValue( "</a>" );
          int _jspx_eval_fmt_param_11 = _jspx_th_fmt_param_11.doStartTag();
          if (_jspx_th_fmt_param_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_11);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_11);
          out.write('\n');
          out.write(' ');
          out.write(' ');
          int evalDoAfterBody = _jspx_th_fmt_message_16.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_16 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_16);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_16);
      out.write("\n  </p>\n\n  <table class=\"jive-table\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n  <thead>\n      <tr>\n          <th width=\"1%\">&nbsp;</th>\n          <th>\n              ");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("\n          </th>\n          <th width=\"20%\">\n              ");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("\n          </th>\n          <th colspan=\"2\">\n              ");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("\n          </th>\n          <th>\n              ");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("\n          </th>\n          <th width=\"1%\">\n              ");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("\n          </th>\n      </tr>\n  </thead>\n  <tbody>\n\n  ");
  int i = 0;
      boolean offerUpdateIssuer = false;
      Map<String, String> signingRequests = new LinkedHashMap<String, String>();
      if (keyStore != null) {
          for (Enumeration aliases = keyStore.aliases(); aliases.hasMoreElements();) {
              i++;
              String a = (String) aliases.nextElement();
              X509Certificate c = (X509Certificate) keyStore.getCertificate(a);
              StringBuffer identities = new StringBuffer();
              for (String identity : CertificateManager.getPeerIdentities(c)) {
                  identities.append(identity).append(", ");
              }
              if (identities.length() > 0) {
                  identities.setLength(identities.length() - 2);
              }
              // Self-signed certs are certs generated by Openfire whose IssueDN equals SubjectDN
              boolean isSelfSigned = CertificateManager.isSelfSignedCertificate(keyStore, a);
              // Signing Request pending = not self signed certs whose chain has only 1 cert (the same cert)
              boolean isSigningPending = CertificateManager.isSigningRequestPending(keyStore, a);

              offerUpdateIssuer = offerUpdateIssuer || isSelfSigned || isSigningPending;
              if (isSigningPending) {
                  // Generate new signing request for certificate
                  PrivateKey privKey = (PrivateKey) keyStore.getKey(a, SSLConfig.getKeyPassword().toCharArray());
                  if (privKey != null) {
                      signingRequests.put(a, CertificateManager.createSigningRequest(c, privKey));
                  }
              }
  
      out.write("\n      <tr valign=\"top\">\n          <td id=\"rs");
      out.print(i);
      out.write("\" width=\"1\" rowspan=\"1\">");
      out.print( (i) );
      out.write(".</td>\n          <td>\n              ");
      out.print( StringUtils.escapeHTMLTags(identities.toString()) );
      out.write(' ');
      out.write('(');
      out.print( StringUtils.escapeHTMLTags(a) );
      out.write(")\n          </td>\n          <td>\n              ");
 boolean expired = c.getNotAfter().before(new Date());
                  if (expired) { 
      out.write("\n                  <font color=\"red\">\n              ");
 } 
      out.write("\n              ");
      out.print( JiveGlobals.formatDate(c.getNotAfter()) );
      out.write("\n              ");
 if (expired) { 
      out.write("\n                  </font>\n              ");
 } 
      out.write("\n          </td>\n          ");
 if (isSelfSigned && !isSigningPending) { 
      out.write("\n          <td width=\"1%\"><img src=\"images/certificate_warning-16x16.png\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("\"></td>\n          <td width=\"1%\" nowrap>\n            ");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("\n          </td>\n          ");
 } else if (isSigningPending) { 
      out.write("\n          <td width=\"1%\"><img src=\"images/certificate_warning-16x16.png\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("\"></td>\n          <td width=\"1%\" nowrap>\n            ");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("\n          </td>\n          ");
 } else { 
      out.write("\n          <td width=\"1%\"><img src=\"images/certificate_ok-16x16.png\" width=\"16\" height=\"16\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("\" title=\"");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("\"></td>\n          <td width=\"1%\" nowrap>\n            ");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("\n          </td>\n          ");
 } 
      out.write("\n          <td width=\"2%\">\n              ");
      out.print( c.getPublicKey().getAlgorithm() );
      out.write("\n          </td>\n          <td width=\"1\" align=\"center\">\n              <a href=\"ssl-certificates.jsp?alias=");
      out.print( URLEncoder.encode(a) );
      out.write("&type=server&delete=true\"\n               title=\"");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write("\"\n               onclick=\"return confirm('");
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write("');\"\n               ><img src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></a>\n          </td>\n      </tr>\n      ");
 if (isSigningPending) { 
      out.write("\n      <form action=\"ssl-certificates.jsp\" method=\"post\">\n      <input name=\"importReply\" type=\"hidden\" value=\"true\">\n      <input name=\"alias\" type=\"hidden\" value=\"");
      out.print( StringUtils.escapeForXML(a));
      out.write("\">\n      <tr id=\"pk");
      out.print(i);
      out.write("\">\n          <td colspan=\"6\">\n              <span class=\"jive-description\">\n              ");
      if (_jspx_meth_fmt_message_33(_jspx_page_context))
        return;
      out.write("\n              </span>\n              <textarea name=\"reply\" cols=\"40\" rows=\"3\" style=\"width:100%;font-size:8pt;\" wrap=\"virtual\"></textarea>\n          </td>\n          <td valign=\"bottom\">\n              <input type=\"submit\" name=\"install\" value=\"");
      if (_jspx_meth_fmt_message_34(_jspx_page_context))
        return;
      out.write("\">   \n          </td>\n      </tr>\n      </form>\n      ");
 } 
      out.write("\n     ");
 } 
      out.write('\n');
      out.write(' ');
      out.write(' ');
  } 
      out.write("\n\n  </tbody>\n  </table>\n  <!-- END 'Installed Certificates' -->\n  <!-- BEGIN 'Signing request' -->\n  ");
 if (offerUpdateIssuer || !signingRequests.isEmpty()) { 
      out.write("\n  <br>\n  <div class=\"jive-contentBoxHeader\">\n      ");
      if (_jspx_meth_fmt_message_35(_jspx_page_context))
        return;
      out.write("\n  </div>\n  <div class=\"jive-contentBox\">\n      ");
 if (offerUpdateIssuer) { 
      out.write("\n      <p>\n          ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_36 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_36.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_36.setParent(null);
      _jspx_th_fmt_message_36.setKey("ssl.signing-request.offer-issuer-information");
      int _jspx_eval_fmt_message_36 = _jspx_th_fmt_message_36.doStartTag();
      if (_jspx_eval_fmt_message_36 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_36 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_36.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_36.doInitBody();
        }
        do {
          out.write("\n              ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_12 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_12.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_36);
          _jspx_th_fmt_param_12.setValue( "<a href='ssl-signing-request.jsp'>" );
          int _jspx_eval_fmt_param_12 = _jspx_th_fmt_param_12.doStartTag();
          if (_jspx_th_fmt_param_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_12);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_12);
          out.write("\n              ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_13 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_13.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_36);
          _jspx_th_fmt_param_13.setValue( "</a>" );
          int _jspx_eval_fmt_param_13 = _jspx_th_fmt_param_13.doStartTag();
          if (_jspx_th_fmt_param_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_13);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_13);
          out.write("\n          ");
          int evalDoAfterBody = _jspx_th_fmt_message_36.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_36 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_36.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_36);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_36);
      out.write("\n      </p>\n      ");
 } 
      out.write("\n      ");
 if (!signingRequests.isEmpty()) { 
      out.write("\n        <p>\n            ");
      if (_jspx_meth_fmt_message_37(_jspx_page_context))
        return;
      out.write("\n        </p>\n        <table cellpadding=\"3\" cellspacing=\"2\" border=\"0\">\n            <thead>\n                <tr>\n                    <th>\n                        ");
      if (_jspx_meth_fmt_message_38(_jspx_page_context))
        return;
      out.write("\n                    </th>\n                    <th>\n                        ");
      if (_jspx_meth_fmt_message_39(_jspx_page_context))
        return;
      out.write("\n                    </th>\n                </tr>\n            </thead>\n            <tbody>\n                ");
 for (Map.Entry<String, String> entry : signingRequests.entrySet()) { 
      out.write("\n                <tr>\n                    <td width=\"1%\" valign=\"top\">\n                        ");
      out.print( entry.getKey() );
      out.write("\n                    </td>\n                    <td width=\"99%\" >\n                        ");
      out.print( StringUtils.escapeHTMLTags(entry.getValue()) );
      out.write("\n                    </td>\n                </tr>\n                ");
 } 
      out.write("\n          </tbody>\n          </table>\n      ");
 } 
      out.write("\n  </div>\n  ");
 } 
      out.write("\n  <!-- END 'Signing request' -->\n  </body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("ssl.certificates.title");
    int _jspx_eval_fmt_message_0 = _jspx_th_fmt_message_0.doStartTag();
    if (_jspx_th_fmt_message_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_0);
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
    _jspx_th_fmt_message_2.setKey("ssl.certificates.io_error");
    int _jspx_eval_fmt_message_2 = _jspx_th_fmt_message_2.doStartTag();
    if (_jspx_th_fmt_message_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_2);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_2);
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
    _jspx_th_fmt_message_4.setKey("index.certificate-warning");
    int _jspx_eval_fmt_message_4 = _jspx_th_fmt_message_4.doStartTag();
    if (_jspx_th_fmt_message_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_4);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_4);
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
    _jspx_th_fmt_message_6.setKey("ssl.certificates.added_updated");
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
    _jspx_th_fmt_message_7.setKey("ssl.certificates.generated");
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
    _jspx_th_fmt_message_8.setKey("ssl.certificates.deleted");
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
    _jspx_th_fmt_message_9.setKey("ssl.certificates.error");
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
    _jspx_th_fmt_message_10.setKey("ssl.certificates.error_messenge");
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
    _jspx_th_fmt_message_11.setKey("ssl.certificates.issuer-updated");
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
    _jspx_th_fmt_message_12.setKey("ssl.certificates.imported");
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
    _jspx_th_fmt_message_13.setKey("ssl.certificates.error_importing-reply");
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
    _jspx_th_fmt_message_14.setKey("ssl.certificates.error");
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
    _jspx_th_fmt_message_15.setKey("ssl.certificates.error_messenge");
    int _jspx_eval_fmt_message_15 = _jspx_th_fmt_message_15.doStartTag();
    if (_jspx_th_fmt_message_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_15);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_15);
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
    _jspx_th_fmt_message_17.setKey("ssl.certificates.alias");
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
    _jspx_th_fmt_message_18.setKey("ssl.certificates.expiration");
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
    _jspx_th_fmt_message_19.setKey("ssl.certificates.status");
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
    _jspx_th_fmt_message_20.setKey("ssl.certificates.algorithm");
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
    _jspx_th_fmt_message_21.setKey("global.delete");
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
    _jspx_th_fmt_message_22.setKey("ssl.certificates.self-signed.info");
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
    _jspx_th_fmt_message_23.setKey("ssl.certificates.self-signed.info");
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
    _jspx_th_fmt_message_24.setKey("ssl.certificates.self-signed");
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
    _jspx_th_fmt_message_25.setKey("ssl.certificates.signing-pending.info");
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
    _jspx_th_fmt_message_26.setKey("ssl.certificates.signing-pending.info");
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
    _jspx_th_fmt_message_27.setKey("ssl.certificates.signing-pending");
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
    _jspx_th_fmt_message_28.setKey("ssl.certificates.ca-signed.info");
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
    _jspx_th_fmt_message_29.setKey("ssl.certificates.ca-signed.info");
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
    _jspx_th_fmt_message_30.setKey("ssl.certificates.ca-signed");
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
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_31 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_31.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_31.setParent(null);
    _jspx_th_fmt_message_31.setKey("global.click_delete");
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
    _jspx_th_fmt_message_32.setKey("ssl.certificates.confirm_delete");
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
    _jspx_th_fmt_message_33.setKey("ssl.certificates.ca-reply");
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
    _jspx_th_fmt_message_34.setKey("global.save");
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
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_35 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_35.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_35.setParent(null);
    _jspx_th_fmt_message_35.setKey("ssl.signing-request.title");
    int _jspx_eval_fmt_message_35 = _jspx_th_fmt_message_35.doStartTag();
    if (_jspx_th_fmt_message_35.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_35);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_35);
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
    _jspx_th_fmt_message_37.setKey("ssl.signing-request.requests_info");
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
    _jspx_th_fmt_message_38.setKey("ssl.signing-request.alias");
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
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_39 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_39.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_39.setParent(null);
    _jspx_th_fmt_message_39.setKey("ssl.signing-request.signing-request");
    int _jspx_eval_fmt_message_39 = _jspx_th_fmt_message_39.doStartTag();
    if (_jspx_th_fmt_message_39.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_39);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_39);
    return false;
  }
}
