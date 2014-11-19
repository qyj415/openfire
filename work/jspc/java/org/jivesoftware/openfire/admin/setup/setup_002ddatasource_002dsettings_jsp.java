package org.jivesoftware.openfire.admin.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.database.EmbeddedConnectionProvider;
import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.database.ConnectionProvider;
import java.util.*;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.ClassUtils;
import org.jivesoftware.openfire.XMPPServer;

public final class setup_002ddatasource_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

 // Global vars

    static final String STANDARD = "standard";
    static final String JNDI = "jndi";
    static final String EMBEDDED = "embedded";

    boolean testConnection(Map<String,String> errors) {
        boolean success = true;
        Connection con = null;
        try {
            con = DbConnectionManager.getConnection();
            if (con == null) {
                success = false;
                errors.put("general","A connection to the database could not be "
                    + "made. View the error message by opening the "
                    + "\"" + File.separator + "logs" + File.separator + "error.log\" log "
                    + "file, then go back to fix the problem.");
            }
            else {
            	// See if the Jive db schema is installed.
            	try {
            		Statement stmt = con.createStatement();
            		// Pick an arbitrary table to see if it's there.
            		stmt.executeQuery("SELECT * FROM ofID");
            		stmt.close();
            	}
            	catch (SQLException sqle) {
                    success = false;
                    errors.put("general","The Openfire database schema does not "
                        + "appear to be installed. Follow the installation guide to "
                        + "fix this error.");
            	}
            }
        }
        catch (Exception ignored) {}
        finally {
            try {
        	    con.close();
            } catch (Exception ignored) {}
        }
        return success;
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n");
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

	// Redirect if we've already run setup:
	if (!XMPPServer.getInstance().isSetupMode()) {
        response.sendRedirect("setup-completed.jsp");
        return;
    }

      out.write("\r\n\r\n");
      out.write("\r\n\r\n");

    boolean embeddedMode = false;
    try {
        ClassUtils.forName("org.jivesoftware.openfire.starter.ServerStarter");
        embeddedMode = true;
    }
    catch (Exception ignored) {}

    // Get parameters
    String mode = ParamUtils.getParameter(request,"mode");
    boolean next = ParamUtils.getBooleanParameter(request,"next");

    // handle a mode redirect
    Map<String,String> errors = new HashMap<String,String>();
    if (next) {
        if (STANDARD.equals(mode)) {
            response.sendRedirect("setup-datasource-standard.jsp");
            return;
        }
        else if (JNDI.equals(mode)) {
            if (!embeddedMode) {
                response.sendRedirect("setup-datasource-jndi.jsp");
                return;
            }
        }
        else if (EMBEDDED.equals(mode)) {
            // Set the classname of the provider in the config file:
            JiveGlobals.setXMLProperty("connectionProvider.className",
                    "org.jivesoftware.database.EmbeddedConnectionProvider");
            ConnectionProvider conProvider = new EmbeddedConnectionProvider();
            DbConnectionManager.setConnectionProvider(conProvider);
            if (testConnection(errors)) {
                // Redirect
                response.sendRedirect("setup-profile-settings.jsp");
                return;
            }
        }
    }

    // Defaults
    if (mode == null) {
        // If the "embedded-database" directory exists, select to the embedded db as the default.
        if (new File(JiveGlobals.getHomeDirectory(), "embedded-db").exists()) {
            mode = EMBEDDED;
        }
        // Otherwise default to standard.
        else {
            mode = STANDARD;
        }
    }

      out.write("\r\n\r\n<html>\r\n<head>\r\n    <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n    <meta name=\"currentStep\" content=\"2\"/>\r\n</head>\r\n<body>\r\n\r\n\t<h1>\r\n\t");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\r\n\t</h1>\r\n\r\n\t<p>\r\n\t");
      //  fmt:message
      org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_2 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
      _jspx_th_fmt_message_2.setPageContext(_jspx_page_context);
      _jspx_th_fmt_message_2.setParent(null);
      _jspx_th_fmt_message_2.setKey("setup.datasource.settings.info");
      int _jspx_eval_fmt_message_2 = _jspx_th_fmt_message_2.doStartTag();
      if (_jspx_eval_fmt_message_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_fmt_message_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_fmt_message_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_fmt_message_2.doInitBody();
        }
        do {
          out.write("\r\n\t    ");
          //  fmt:param
          org.apache.taglibs.standard.tag.rt.fmt.ParamTag _jspx_th_fmt_param_0 = (org.apache.taglibs.standard.tag.rt.fmt.ParamTag) _jspx_tagPool_fmt_param_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.ParamTag.class);
          _jspx_th_fmt_param_0.setPageContext(_jspx_page_context);
          _jspx_th_fmt_param_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_fmt_message_2);
          _jspx_th_fmt_param_0.setValue( LocaleUtils.getLocalizedString("short.title") );
          int _jspx_eval_fmt_param_0 = _jspx_th_fmt_param_0.doStartTag();
          if (_jspx_th_fmt_param_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
            return;
          }
          _jspx_tagPool_fmt_param_value_nobody.reuse(_jspx_th_fmt_param_0);
          out.write('\r');
          out.write('\n');
          out.write('	');
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
      out.write("\r\n\t</p>\r\n\r\n");
  if (errors.size() > 0) { 
      out.write("\r\n\r\n    <p class=\"jive-error-text\">\r\n    ");
      out.print( errors.get("general") );
      out.write("\r\n    </p>\r\n\r\n");
  } 
      out.write("\r\n\r\n\t<!-- BEGIN jive-contentBox -->\r\n\t<div class=\"jive-contentBox\">\r\n\r\n\t\t<form action=\"setup-datasource-settings.jsp\">\r\n\r\n<input type=\"hidden\" name=\"next\" value=\"true\">\r\n\r\n<table cellpadding=\"3\" cellspacing=\"2\" border=\"0\">\r\n<tr>\r\n    <td align=\"center\" valign=\"top\">\r\n        <input type=\"radio\" name=\"mode\" value=\"");
      out.print( STANDARD );
      out.write("\" id=\"rb02\"\r\n         ");
      out.print( ((STANDARD.equals(mode)) ? "checked" : "") );
      out.write(">\r\n    </td>\r\n    <td>\r\n        <label for=\"rb02\"><b>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</b></label>\r\n        <br>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n</tr>\r\n\r\n");
  if (!embeddedMode) { 
      out.write("\r\n\r\n    <tr>\r\n        <td align=\"center\" valign=\"top\">\r\n            <input type=\"radio\" name=\"mode\" value=\"");
      out.print( JNDI );
      out.write("\" id=\"rb03\"\r\n             ");
      out.print( ((JNDI.equals(mode)) ? "checked" : "") );
      out.write(">\r\n        </td>\r\n        <td>\r\n            <label for=\"rb03\"><b>");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</b></label>\r\n            <br>");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\r\n        </td>\r\n    </tr>\r\n\r\n");
  } 
      out.write("\r\n\r\n<tr>\r\n    <td align=\"center\" valign=\"top\">\r\n        <input type=\"radio\" name=\"mode\" value=\"");
      out.print( EMBEDDED );
      out.write("\" id=\"rb01\"\r\n         ");
      out.print( ((EMBEDDED.equals(mode)) ? "checked" : "") );
      out.write(">\r\n    </td>\r\n    <td>\r\n        <label for=\"rb01\"><b>");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</b></label>\r\n        <br>");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n</tr>\r\n</table>\r\n\r\n<br><br>\r\n\r\n\r\n\t\t<div align=\"right\">\r\n\t\t\t<input type=\"Submit\" name=\"continue\" value=\"");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\" id=\"jive-setup-save\" border=\"0\">\r\n\t\t</div>\r\n\t</form>\r\n\r\n\t</div>\r\n\t<!-- END jive-contentBox -->\r\n\r\n\r\n</body>\r\n</html>");
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
    _jspx_th_fmt_message_0.setKey("setup.datasource.settings.title");
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
    _jspx_th_fmt_message_1.setKey("setup.datasource.settings.title");
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
    _jspx_th_fmt_message_3.setKey("setup.datasource.settings.connect");
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
    _jspx_th_fmt_message_4.setKey("setup.datasource.settings.connect_info");
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
    _jspx_th_fmt_message_5.setKey("setup.datasource.settings.jndi");
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
    _jspx_th_fmt_message_6.setKey("setup.datasource.settings.jndi_info");
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
    _jspx_th_fmt_message_7.setKey("setup.datasource.settings.embedded");
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
    _jspx_th_fmt_message_8.setKey("setup.datasource.settings.embedded_info");
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
    _jspx_th_fmt_message_9.setKey("global.continue");
    int _jspx_eval_fmt_message_9 = _jspx_th_fmt_message_9.doStartTag();
    if (_jspx_th_fmt_message_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_9);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_9);
    return false;
  }
}
