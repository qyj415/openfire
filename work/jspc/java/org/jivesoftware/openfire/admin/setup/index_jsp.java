package org.jivesoftware.openfire.admin.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.XMPPServer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.lang.reflect.Method;
import java.io.File;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.ClassUtils;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

 // Global vars, methods, etc

    static final String JIVE_HOME = "jive_home";
    static final String JIVE_LICENSE = "jive_license_file";
    static final String JIVE_LICENSE_TEXT = "jive_license_text";
    static final String JIVE_DEPENDENCY = "jive_dependency";
    static final String JIVE_CONFIG_FILE = "jive_config_file";

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/setup/setup-env-check.jspf");
  }

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

      out.write("\r\n\r\n\r\n");
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write("\r\n\r\n");

	// Redirect if we've already run setup:
	if (!XMPPServer.getInstance().isSetupMode()) {
        response.sendRedirect("setup-completed.jsp");
        return;
    }

      out.write("\r\n\r\n");
      out.write("\r\n\r\n\r\n\r\n\r\n");
  // Check the user's environment for minimum requirements.

    boolean jdk13Installed = false;
    boolean servlet22Installed = false;
    boolean jsp11Installed = false;
    boolean jiveJarsInstalled = false;
    boolean openfireHomeExists = false;
    File openfireHome = null;

    // Check for JDK 1.5
    try {
        ClassUtils.forName("java.util.concurrent.ConcurrentHashMap");
        jdk13Installed = true;
    }
    catch (ClassNotFoundException cnfe) {}
    // Check for Servlet 2.3:
    try {
        Class c = ClassUtils.forName("javax.servlet.http.HttpSession");
        Method m = c.getMethod("getAttribute",new Class[]{String.class});
        servlet22Installed = true;
    }
    catch (ClassNotFoundException cnfe) {}
    // Check for JSP 1.1:
    try {
        ClassUtils.forName("javax.servlet.jsp.tagext.Tag");
        jsp11Installed = true;
    }
    catch (ClassNotFoundException cnfe) {}
    // Check that the Openfire jar are installed:
    try {
        ClassUtils.forName("org.jivesoftware.openfire.XMPPServer");
        jiveJarsInstalled = true;
    }
    catch (ClassNotFoundException cnfe) {}

    // Try to determine what the jiveHome directory is:
    try {
        Class jiveGlobalsClass = ClassUtils.forName("org.jivesoftware.util.JiveGlobals");
        Method getOpenfireHomeMethod = jiveGlobalsClass.getMethod("getHomeDirectory", (Class[])null);
        String openfireHomeProp = (String)getOpenfireHomeMethod.invoke(jiveGlobalsClass, (Object[])null);
        if (openfireHomeProp != null) {
            openfireHome = new File(openfireHomeProp);
            if (openfireHome.exists()) {
                openfireHomeExists = true;
            }
        }
    }
    catch (Exception e) {
        e.printStackTrace();
    }

    // If there were no errors, redirect to the main setup page
    if (!jdk13Installed || !servlet22Installed || !jsp11Installed || !jiveJarsInstalled
            || !openfireHomeExists)
    {

      out.write("\r\n        <html>\r\n        <head>\r\n            <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("</title>\r\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\r\n        </head>\r\n        <body>\r\n\r\n        <p class=\"jive-setup-page-header\">\r\n        ");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\r\n        </p>\r\n\r\n        <p class=\"jive-setup-error-text\">\r\n        ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write(".\r\n        </p>\r\n\r\n        <p>\r\n        ");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_7 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_7.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_7.setParent(null);
      _jspx_th_fmt_message_7.setKey("setup.env.check.error_info");
      int _jspx_eval_fmt_message_7 = _jspx_th_fmt_message_7.doStartTag();
      if (_jspx_eval_fmt_message_7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_7 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_7.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_7.doInitBody();
        }
        do {
          out.write("\r\n            ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_7);
          _jspx_th_fmt_param_0.setValue( LocaleUtils.getLocalizedString("title") );
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          out.write("\r\n        ");
          int evalDoAfterBody = _jspx_th_fmt_message_7.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_7 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_7);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_7);
      out.write("\r\n        </p>\r\n\r\n        <ul>\r\n        <table cellpadding=\"3\" cellspacing=\"2\" border=\"0\">\r\n        ");
  if (jdk13Installed) { 
      out.write("\r\n\r\n            <tr>\r\n                <td><img src=\"../images/check.gif\" width=\"13\" height=\"13\" border=\"0\"></td>\r\n                <td>\r\n                    ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\r\n                </td>\r\n            </tr>\r\n\r\n        ");
  } else { 
      out.write("\r\n\r\n            <tr>\r\n                <td><img src=\"../images/x.gif\" width=\"13\" height=\"13\" border=\"0\"></td>\r\n                <td>\r\n                    <span class=\"jive-setup-error-text\">\r\n                    ");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\r\n                    </span>\r\n                </td>\r\n            </tr>\r\n\r\n        ");
  }
            if (servlet22Installed) {
        
      out.write("\r\n            <tr>\r\n                <td><img src=\"../images/check.gif\" width=\"13\" height=\"13\" border=\"0\"></td>\r\n                <td>\r\n                    ");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\r\n                </td>\r\n            </tr>\r\n\r\n        ");
  } else { 
      out.write("\r\n\r\n            <tr>\r\n                <td><img src=\"../images/x.gif\" width=\"13\" height=\"13\" border=\"0\"></td>\r\n                <td>\r\n                    <span class=\"jive-setup-error-text\">\r\n                    ");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\r\n                    </span>\r\n                </td>\r\n            </tr>\r\n\r\n        ");
  }
            if (jsp11Installed) {
        
      out.write("\r\n            <tr>\r\n                <td><img src=\"../images/check.gif\" width=\"13\" height=\"13\" border=\"0\"></td>\r\n                <td>\r\n                    ");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("\r\n                </td>\r\n            </tr>\r\n\r\n        ");
  } else { 
      out.write("\r\n\r\n            <tr>\r\n                <td><img src=\"../images/x.gif\" width=\"13\" height=\"13\" border=\"0\"></td>\r\n                <td>\r\n                    <span class=\"jive-setup-error-text\">\r\n                    ");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\r\n                    </span>\r\n                </td>\r\n            </tr>\r\n\r\n        ");
  }
            if (jiveJarsInstalled) {
        
      out.write("\r\n            <tr>\r\n                <td><img src=\"../images/check.gif\" width=\"13\" height=\"13\" border=\"0\"></td>\r\n                <td>\r\n                    ");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("\r\n                </td>\r\n            </tr>\r\n\r\n        ");
  } else { 
      out.write("\r\n\r\n            <tr>\r\n                <td><img src=\"../images/x.gif\" width=\"13\" height=\"13\" border=\"0\"></td>\r\n                <td>\r\n                    <span class=\"jive-setup-error-text\">\r\n                    ");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("\r\n                    </span>\r\n                </td>\r\n            </tr>\r\n\r\n        ");
  }
            if (openfireHomeExists) {
        
      out.write("\r\n            <tr>\r\n                <td><img src=\"../images/x.gif\" width=\"13\" height=\"13\" border=\"0\"></td>\r\n                <td>\r\n                    <span class=\"jive-setup-error-text\">\r\n                    ");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write(' ');
      out.write('(');
      out.print( openfireHome.toString() );
      out.write(")\r\n                    </span>\r\n                </td>\r\n            </tr>\r\n\r\n        ");
  } else { 
      out.write("\r\n\r\n            <tr>\r\n                <td><img src=\"../images/x.gif\" width=\"13\" height=\"13\" border=\"0\"></td>\r\n                <td>\r\n                    <span class=\"jive-setup-error-text\">\r\n                    ");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("\r\n                    </span>\r\n                </td>\r\n            </tr>\r\n\r\n        ");
  } 
      out.write("\r\n        </table>\r\n        </ul>\r\n\r\n        <p>\r\n        ");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("\r\n        </p>\r\n\r\n        </body>\r\n        </html>\r\n\r\n");
      // return so we stop showing the page:
        return;
    }

      out.write("\r\n\r\n");
  // Get parameters
    String localeCode = ParamUtils.getParameter(request,"localeCode");
    boolean save = request.getParameter("save") != null;

    Map errors = new HashMap();

    if (save) {
        Locale newLocale = null;
        if (localeCode != null) {
            newLocale = LocaleUtils.localeCodeToLocale(localeCode.trim());
            if (newLocale == null) {
                errors.put("localeCode","");
            }
            else {
                JiveGlobals.setLocale(newLocale);
                // redirect
                response.sendRedirect("setup-host-settings.jsp");
                return;
            }
        }
    }

    Locale locale = JiveGlobals.getLocale();

      out.write("\r\n\r\n<html>\r\n<head>\r\n<title>");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</title>\r\n<meta name=\"currentStep\" content=\"0\"/>\r\n</head>\r\n<body>\r\n\r\n\r\n\t<h1>\r\n\t");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("\r\n\t</h1>\r\n\r\n\t<p>\r\n\t");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_23 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_23.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_23.setParent(null);
      _jspx_th_fmt_message_23.setKey("setup.index.info");
      int _jspx_eval_fmt_message_23 = _jspx_th_fmt_message_23.doStartTag();
      if (_jspx_eval_fmt_message_23 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_23 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_23.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_23.doInitBody();
        }
        do {
          out.write("\r\n\t\t");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_1 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_1.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_23);
          _jspx_th_fmt_param_1.setValue( LocaleUtils.getLocalizedString("title") );
          int _jspx_eval_fmt_param_1 = _jspx_th_fmt_param_1.doStartTag();
          if (_jspx_th_fmt_param_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_1);
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_fmt_message_23.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_fmt_message_23 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE)
          out = _jspx_page_context.popBody();
      }
      if (_jspx_th_fmt_message_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_23);
        return;
      }
      _jspx_tagPool_fmt_message_key.reuse(_jspx_th_fmt_message_23);
      out.write("\r\n\t</p>\r\n\r\n\r\n\t<!-- BEGIN jive-contentBox -->\r\n\t<div class=\"jive-contentBox\">\r\n\r\n\t<h2>");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("</h2>\r\n\r\n\t<form action=\"index.jsp\" name=\"sform\">\r\n");
  boolean usingPreset = false;
    Locale[] locales = Locale.getAvailableLocales();
    for (int i=0; i<locales.length; i++) {
        usingPreset = locales[i].equals(locale);
        if (usingPreset) { break; }
    }

      out.write("\r\n\t\t<div id=\"jive-setup-language\">\r\n\t\t\t<p>\r\n\t\t\t<label for=\"loc01\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"cs_CZ\" ");
      out.print( ("cs_CZ".equals(locale.toString()) ? "checked" : "") );
      out.write(" id=\"loc01\" />\r\n\t\t\t<b>Czech</b> (cs_CZ)\r\n\t\t\t</label><br>\r\n\r\n\t\t\t<label for=\"loc02\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"de\" ");
      out.print( ("de".equals(locale.toString()) ? "checked" : "") );
      out.write(" id=\"loc02\" />\r\n\t\t\t<b>Deutsch</b> (de)\r\n\t\t\t</label><br>\r\n\r\n\t\t\t<label for=\"loc03\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"en\" ");
      out.print( ("en".equals(locale.toString()) ? "checked" : "") );
      out.write(" id=\"loc03\" />\r\n\t\t\t<b>English</b> (en)\r\n\t\t\t</label><br>\r\n\r\n\t\t\t<label for=\"loc04\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"es\" ");
      out.print( ("es".equals(locale.toString()) ? "checked" : "") );
      out.write(" id=\"loc04\" />\r\n\t\t\t<b>Espa&ntilde;ol</b> (es)\r\n\t\t\t</label><br>\r\n\r\n\t\t\t<label for=\"loc05\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"fr\" ");
      out.print( ("fr".equals(locale.toString()) ? "checked" : "") );
      out.write(" id=\"loc05\" />\r\n\t\t\t<b>Fran&ccedil;ais</b> (fr)\r\n\t\t\t</label><br>\r\n\r\n\t\t\t<label for=\"loc06\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"nl\" ");
      out.print( ("nl".equals(locale.toString()) ? "checked" : "") );
      out.write(" id=\"loc06\" />\r\n\t\t\t<b>Nederlands</b> (nl)\r\n\t\t\t</label><br>\r\n\r\n\t\t\t<label for=\"loc07\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"pl_PL\" ");
      out.print( ("pl_PL".equals(locale.toString()) ? "checked" : "") );
      out.write(" id=\"loc07\" />\r\n\t\t\t<b>Polski</b> (pl_PL)\r\n\t\t\t</label><br>\r\n\r\n\t\t\t<label for=\"loc08\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"pt_BR\" ");
      out.print( ("pt_BR".equals(locale.toString()) ? "checked" : "") );
      out.write(" id=\"loc08\" />\r\n\t\t\t<b>Portugu&ecirc;s Brasileiro</b> (pt_BR)\r\n\t\t\t</label><br>\r\n\r\n\t\t\t<label for=\"loc09\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"ru_RU\" ");
      out.print( ("ru_RU".equals(locale.toString()) ? "checked" : "") );
      out.write("  id=\"loc09\" />\r\n            <b>&#x420;&#x443;&#x441;&#x441;&#x43A;&#x438;&#x439;</b> (ru_RU)\r\n\t\t\t</label><br>\r\n\r\n\t\t\t<label for=\"loc10\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"sk\" ");
      out.print( ("sk".equals(locale.toString()) ? "checked" : "") );
      out.write(" id=\"loc10\" />\r\n\t\t\t<b>Sloven&#269;ina</b> (sk)\r\n\t\t\t</label><br>\r\n\r\n\t\t\t<label for=\"loc11\">\r\n\t\t\t<input type=\"radio\" name=\"localeCode\" value=\"zh_CN\" ");
      out.print( ("zh_CN".equals(locale.toString()) ? "checked" : "") );
      out.write(" id=\"loc11\" />\r\n            <img src=\"../images/setup_language_zh_CN.gif\" border=\"0\" align=\"top\" />\r\n            <b>Simplified Chinese</b> (zh_CN)\r\n\t\t\t</label><br>\r\n\t\t\t</p>\r\n\t\t</div>\r\n\r\n\t\t<div align=\"right\">\r\n\t\t\t<input type=\"Submit\" name=\"save\" value=\"");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("\" id=\"jive-setup-save\" border=\"0\">\r\n\t\t</div>\r\n\t</form>\r\n\r\n\t</div>\r\n\t<!-- END jive-contentBox -->\r\n\r\n\r\n</body>\r\n</html>\r\n");
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
    _jspx_th_fmt_message_0.setKey("title");
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
    _jspx_th_fmt_message_1.setKey("setup.title");
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
    _jspx_th_fmt_message_3.setKey("setup.title");
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
    _jspx_th_fmt_message_4.setKey("setup.env.check.error");
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
    _jspx_th_fmt_message_5.setKey("title");
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
    _jspx_th_fmt_message_6.setKey("setup.title");
    int _jspx_eval_fmt_message_6 = _jspx_th_fmt_message_6.doStartTag();
    if (_jspx_th_fmt_message_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
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
    _jspx_th_fmt_message_8.setKey("setup.env.check.jdk");
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
    _jspx_th_fmt_message_9.setKey("setup.env.check.jdk");
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
    _jspx_th_fmt_message_10.setKey("setup.env.check.servlet");
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
    _jspx_th_fmt_message_11.setKey("setup.env.check.servlet");
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
    _jspx_th_fmt_message_12.setKey("setup.env.check.jsp");
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
    _jspx_th_fmt_message_13.setKey("setup.env.check.jsp");
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
    _jspx_th_fmt_message_14.setKey("title");
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
    _jspx_th_fmt_message_15.setKey("setup.env.check.class");
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
    _jspx_th_fmt_message_16.setKey("title");
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
    _jspx_th_fmt_message_17.setKey("setup.env.check.class");
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
    _jspx_th_fmt_message_18.setKey("setup.env.check.jive");
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
    _jspx_th_fmt_message_19.setKey("setup.env.check.not_home");
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
    _jspx_th_fmt_message_20.setKey("setup.env.check.doc");
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
    _jspx_th_fmt_message_21.setKey("setup.index.title");
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
    _jspx_th_fmt_message_22.setKey("setup.index.title");
    int _jspx_eval_fmt_message_22 = _jspx_th_fmt_message_22.doStartTag();
    if (_jspx_th_fmt_message_22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
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
    _jspx_th_fmt_message_24.setKey("setup.index.choose_lang");
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
    _jspx_th_fmt_message_25.setKey("global.continue");
    int _jspx_eval_fmt_message_25 = _jspx_th_fmt_message_25.doStartTag();
    if (_jspx_th_fmt_message_25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
    return false;
  }
}
