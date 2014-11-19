package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.CertificateManager;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.StringUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.net.SSLConfig;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.security.KeyStore;

public final class import_002dcertificate_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n");
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
      out.write("\r\n\r\n");
      out.write('\r');
      out.write('\n');
 // Get parameters:
    boolean save = ParamUtils.getParameter(request, "save") != null;
    String privateKey = ParamUtils.getParameter(request, "private-key");
    String passPhrase = ParamUtils.getParameter(request, "passPhrase");
    String certificate = ParamUtils.getParameter(request, "certificate");

    Map<String, Object> errors = new HashMap<String, Object>();
    if (save) {
        if (privateKey == null || "".equals(privateKey)) {
            errors.put("privateKey", "privateKey");
        }
        if (certificate == null || "".equals(certificate)) {
            errors.put("certificate", "certificate");
        }
        if (errors.isEmpty()) {
            try {
                // Create an alias for the signed certificate
                String domain = XMPPServer.getInstance().getServerInfo().getXMPPDomain();
                int index = 1;
                String alias = domain + "_" + index;
                while (SSLConfig.getKeyStore().containsAlias(alias)) {
                    index = index + 1;
                    alias = domain + "_" + index;
                }
                KeyStore keystore;
                try {
                    keystore = SSLConfig.getKeyStore();
                }
                catch (Exception e) {
                    keystore = SSLConfig.initializeKeyStore();
                }
                // Import certificate
                CertificateManager.installCert(keystore, SSLConfig.gets2sTrustStore(),
                        SSLConfig.getKeyPassword(), alias, new ByteArrayInputStream(privateKey.getBytes()), passPhrase,
                        new ByteArrayInputStream(certificate.getBytes()), true, true);
                // Save keystore
                SSLConfig.saveStores();
                // Log the event
                webManager.logEvent("imported SSL certificate", "alias = "+alias);
                response.sendRedirect("ssl-certificates.jsp?importsuccess=true");
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
                errors.put("import", e);
            }
        }
    }

      out.write("\r\n\r\n<html>\r\n  <head>\r\n      <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n      <meta name=\"pageID\" content=\"ssl-certificates\"/>\r\n  </head>\r\n  <body>\r\n\r\n  ");
  if (errors.containsKey("privateKey")) { 
      out.write("\r\n\r\n      <div class=\"jive-error\">\r\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n      <tbody>\r\n          <tr><td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n          <td class=\"jive-icon-label\">\r\n          ");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\r\n          </td></tr>\r\n      </tbody>\r\n      </table>\r\n      </div><br>\r\n\r\n  ");
  } else if (errors.containsKey("certificate")) { 
      out.write("\r\n\r\n      <div class=\"jive-error\">\r\n  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n  <tbody>\r\n      <tr><td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n      <td class=\"jive-icon-label\">\r\n      ");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\r\n      </td></tr>\r\n  </tbody>\r\n  </table>\r\n  </div><br>\r\n\r\n  ");
  } else if (errors.containsKey("import")) {
          Exception e = (Exception)errors.get("import");
  
      out.write("\r\n\r\n      <div class=\"jive-error\">\r\n      <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n      <tbody>\r\n          <tr><td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\r\n          <td class=\"jive-icon-label\">\r\n          ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\r\n          ");
  if (e != null && e.getMessage() != null) { 
      out.write("\r\n              ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write(':');
      out.write(' ');
      out.print( StringUtils.escapeHTMLTags(e.getMessage()) );
      out.write("\r\n          ");
  } 
      out.write("\r\n          </td></tr>\r\n      </tbody>\r\n      </table>\r\n      </div><br>\r\n  ");
 } 
      out.write("\r\n\r\n  <p>\r\n  ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_5 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_5.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_5.setParent(null);
      _jspx_th_fmt_message_5.setKey("ssl.import.certificate.info");
      int _jspx_eval_fmt_message_5 = _jspx_th_fmt_message_5.doStartTag();
      if (_jspx_eval_fmt_message_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_5 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_5.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_5.doInitBody();
        }
        do {
          out.write("\r\n      ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_5);
          _jspx_th_fmt_param_0.setValue( "<a href='http://java.sun.com/javase/downloads/index.jsp'>" );
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          out.write("\r\n      ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_1 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_1.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_5);
          _jspx_th_fmt_param_1.setValue( "</a>" );
          int _jspx_eval_fmt_param_1 = _jspx_th_fmt_param_1.doStartTag();
          if (_jspx_th_fmt_param_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
          out.write("\r\n  ");
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
      out.write("\r\n  </p>\r\n\r\n  <!-- BEGIN 'Import Private Key and Certificate' -->\r\n  <form action=\"import-certificate.jsp\" method=\"post\" name=\"f\">\r\n      <div class=\"jive-contentBoxHeader\">\r\n          ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\r\n      </div>\r\n      <div class=\"jive-contentBox\">\r\n          <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\r\n          <tbody>\r\n              <tr valign=\"top\">\r\n                  <td width=\"1%\" nowrap class=\"c1\">\r\n                      ");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("\r\n                  </td>\r\n                  <td width=\"99%\">\r\n                      <input type=\"text\" size=\"30\" maxlength=\"100\" name=\"passPhrase\">\r\n                  </td>\r\n              </tr>\r\n              <tr valign=\"top\">\r\n                  <td width=\"1%\" nowrap class=\"c1\">\r\n                      ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\r\n                  </td>\r\n                  <td width=\"99%\">\r\n                      <textarea name=\"private-key\" cols=\"60\" rows=\"5\" wrap=\"virtual\"></textarea>\r\n                  </td>\r\n              </tr>\r\n              <tr valign=\"top\">\r\n                  <td width=\"1%\" nowrap class=\"c1\">\r\n                      ");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\r\n                  </td>\r\n                  <td width=\"99%\">\r\n                      <textarea name=\"certificate\" cols=\"60\" rows=\"5\" wrap=\"virtual\"></textarea>\r\n                  </td>\r\n              </tr>\r\n          </tbody>\r\n          </table>\r\n      </div>\r\n      <input type=\"submit\" name=\"save\" value=\"");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\">\r\n  </form>\r\n  <!-- END 'Import Private Key and Certificate' -->\r\n\r\n  </body>\r\n</html>\r\n");
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
    _jspx_th_fmt_message_0.setKey("ssl.import.certificate.title");
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
    _jspx_th_fmt_message_1.setKey("ssl.import.certificate.error.private-key");
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
    _jspx_th_fmt_message_2.setKey("ssl.import.certificate.error.certificate");
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
    _jspx_th_fmt_message_3.setKey("ssl.import.certificate.error.import");
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
    _jspx_th_fmt_message_4.setKey("ssl.certificates.error_messenge");
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
    _jspx_th_fmt_message_6.setKey("ssl.import.certificate.boxtitle");
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
    _jspx_th_fmt_message_7.setKey("ssl.import.certificate.pass-phrase");
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
    _jspx_th_fmt_message_8.setKey("ssl.import.certificate.private-key");
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
    _jspx_th_fmt_message_9.setKey("ssl.import.certificate.certificate");
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
    _jspx_th_fmt_message_10.setKey("global.save");
    int _jspx_eval_fmt_message_10 = _jspx_th_fmt_message_10.doStartTag();
    if (_jspx_th_fmt_message_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_10);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_10);
    return false;
  }
}
