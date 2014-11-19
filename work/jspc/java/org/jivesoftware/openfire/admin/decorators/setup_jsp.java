package org.jivesoftware.openfire.admin.decorators;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.LocaleUtils;
import java.beans.PropertyDescriptor;
import java.io.File;
import org.jivesoftware.database.DbConnectionManager;
import java.sql.Connection;
import java.util.Map;
import java.sql.Statement;
import java.sql.SQLException;
import org.jivesoftware.admin.AdminConsole;

public final class setup_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    final PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] pd, String name) {
        for (PropertyDescriptor aPd : pd) {
            if (name.equals(aPd.getName())) {
                return aPd;
            }
        }
        return null;
    }

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
                    sqle.printStackTrace();
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

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_usePage_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_title_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_head_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_getProperty_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_body_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_decorator_usePage_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_fmt_message_key_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_title_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_head_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_getProperty_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_body_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_decorator_usePage_id_nobody.release();
    _jspx_tagPool_fmt_message_key_nobody.release();
    _jspx_tagPool_decorator_title_nobody.release();
    _jspx_tagPool_decorator_head_nobody.release();
    _jspx_tagPool_decorator_getProperty_property_nobody.release();
    _jspx_tagPool_decorator_body_nobody.release();
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

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
      //  decorator:usePage
      com.opensymphony.module.sitemesh.taglib.decorator.UsePageTag _jspx_th_decorator_usePage_0 = (com.opensymphony.module.sitemesh.taglib.decorator.UsePageTag) _jspx_tagPool_decorator_usePage_id_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.UsePageTag.class);
      _jspx_th_decorator_usePage_0.setPageContext(_jspx_page_context);
      _jspx_th_decorator_usePage_0.setParent(null);
      _jspx_th_decorator_usePage_0.setId("decoratedPage");
      int _jspx_eval_decorator_usePage_0 = _jspx_th_decorator_usePage_0.doStartTag();
      if (_jspx_th_decorator_usePage_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_decorator_usePage_id_nobody.reuse(_jspx_th_decorator_usePage_0);
        return;
      }
      _jspx_tagPool_decorator_usePage_id_nobody.reuse(_jspx_th_decorator_usePage_0);
      com.opensymphony.module.sitemesh.Page decoratedPage = null;
      decoratedPage = (com.opensymphony.module.sitemesh.Page) _jspx_page_context.findAttribute("decoratedPage");
      out.write('\n');

    // Check to see if the sidebar should be shown; default to true unless the page specifies
    // that it shouldn't be.
    String sidebar = decoratedPage.getProperty("meta.showSidebar");
    if (sidebar == null) {
        sidebar = "true";
    }
    boolean showSidebar = Boolean.parseBoolean(sidebar);
    int currentStep = decoratedPage.getIntProperty("meta.currentStep");

      out.write('\n');
      out.write('\n');

    String preloginSidebar = (String) session.getAttribute("prelogin.setup.sidebar");
    if (preloginSidebar == null) {
        preloginSidebar = "false";
    }
    boolean showPreloginSidebar = Boolean.parseBoolean(preloginSidebar);

      out.write('\n');
      out.write('\n');
      out.write("\n\n<html>\n<head>\n    <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write(':');
      out.write(' ');
      if (_jspx_meth_decorator_title_0(_jspx_page_context))
        return;
      out.write("</title>\n\n    <style type=\"text/css\" title=\"setupStyle\" media=\"screen\">\n        @import \"../style/global.css\";\n        @import \"../style/setup.css\";\n        @import \"../style/lightbox.css\";\n    </style>\n\n    <script language=\"JavaScript\" type=\"text/javascript\" src=\"../js/prototype.js\"></script>\n    <script language=\"JavaScript\" type=\"text/javascript\" src=\"../js/scriptaculous.js\"></script>\n    <script language=\"JavaScript\" type=\"text/javascript\" src=\"../js/lightbox.js\"></script>\n    <script language=\"javascript\" type=\"text/javascript\" src=\"../js/tooltips/domLib.js\"></script>\n    <script language=\"javascript\" type=\"text/javascript\" src=\"../js/tooltips/domTT.js\"></script>\n    <script language=\"javascript\" type=\"text/javascript\" src=\"../js/setup.js\"></script>\n    ");
      if (_jspx_meth_decorator_head_0(_jspx_page_context))
        return;
      out.write("\n</head>\n\n<body onload=\"");
      if (_jspx_meth_decorator_getProperty_0(_jspx_page_context))
        return;
      out.write("\">\n\n<!-- BEGIN jive-main -->\n<div id=\"main\">\n\n    <!-- BEGIN jive-header -->\n    <div id=\"jive-header\">\n        <div id=\"jive-logo\">\n            <a href=\"/index.jsp\"><img src=\"/images/login_logo.gif\" alt=\"Openfire\" width=\"179\" height=\"53\" /></a>\n        </div>\n        <div id=\"jive-userstatus\">\n            ");
      out.print( AdminConsole.getAppName() );
      out.write(' ');
      out.print( AdminConsole.getVersionString() );
      out.write("<br/>\n        </div>\n        <div id=\"jive-nav\">\n            <div id=\"jive-nav-left\"></div>\n            <ul>\n                <li><a>");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</a></li>\n            </ul>\n            <div id=\"jive-nav-right\"></div>\n        </div>\n        <div id=\"jive-subnav\">\n            &nbsp;\n        </div>\n    </div>\n    <!-- END jive-header -->\n\n\n    <div id=\"jive-main\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n    <tbody>\n        <tr valign=\"top\">\n            <td width=\"1%\">\n                <div id=\"jive-sidebar-container\">\n                    <div id=\"jive-sidebar-box\">\n\n\n<!-- BEGIN jive-sidebar -->\n                        <div id=\"jive-sidebar\">\n                            ");
  if (showSidebar) {
                                    String[] names;
                                    String[] links;
                                    if (showPreloginSidebar) {
                                        names = new String[] {
                                                LocaleUtils.getLocalizedString((String) session.getAttribute("prelogin.setup.sidebar.title"))
                                        };
                                        links = new String[] {
                                                (String) session.getAttribute("prelogin.setup.sidebar.link")
                                        };
                                    } else {
                                        names = new String[] {
                                             LocaleUtils.getLocalizedString("setup.sidebar.language"),
                                             LocaleUtils.getLocalizedString("setup.sidebar.settings"),
                                             LocaleUtils.getLocalizedString("setup.sidebar.datasource"),
                                             LocaleUtils.getLocalizedString("setup.sidebar.profile"),
                                             LocaleUtils.getLocalizedString("setup.sidebar.admin")
                                         };
                                         links = new String[] {
                                             "index.jsp",
                                             "setup-host-settings.jsp",
                                             "setup-datasource-settings.jsp",
                                             "setup-profile-settings.jsp",
                                             "setup-admin-settings.jsp"
                                         };
                                    }
                                    
      out.write("\n                                <ul id=\"jive-sidebar-progress\">\n                                    ");
  if (!showPreloginSidebar) { 
      out.write("\n                                    <li class=\"category\">");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</li>\n                                    <li><img src=\"../images/setup_sidebar_progress");
      out.print( currentStep );
      out.write(".gif\" alt=\"\" width=\"142\" height=\"13\" border=\"0\"></li>\n                                    ");
  } 
      out.write("\n                                    ");
  for (int i=0; i<names.length; i++) { 
      out.write("\n                                        ");
  if (currentStep < i) { 
      out.write("\n                                        <li><a href=\"");
      out.print( links[i] );
      out.write('"');
      out.write('>');
      out.print( names[i] );
      out.write("</a></li>\n                                        ");
  } else if (currentStep == i) { 
      out.write("\n                                        <li class=\"currentlink\"><a href=\"");
      out.print( links[i] );
      out.write('"');
      out.write('>');
      out.print( names[i] );
      out.write("</a></li>\n                                        ");
  } else { 
      out.write("\n                                        <li class=\"completelink\"><a href=\"");
      out.print( links[i] );
      out.write('"');
      out.write('>');
      out.print( names[i] );
      out.write("</a></li>\n                                        ");
  } 
      out.write("\n                                    ");
  } 
      out.write("\n                                </ul>\n\n                            ");
  } 
      out.write("\n\n\n                        </div>\n<!-- END jive-sidebar -->\n\n                    </div>\n                </div>\n            </td>\n            <td width=\"99%\" id=\"jive-content\">\n\n<!-- BEGIN jive-body -->\n\n                <div id=\"jive-main-content\">\n                    ");
      if (_jspx_meth_decorator_body_0(_jspx_page_context))
        return;
      out.write("\n                </div>\n\n<!-- END jive-body -->\n            </td>\n        </tr>\n    </tbody>\n    </table>\n    </div>\n\n</div>\n<!-- END jive-main -->\n\n<!-- BEGIN jive-footer -->\n    <div id=\"jive-footer\">\n        <div class=\"jive-footer-copyright\">\n            Built by <a href=\"http://www.jivesoftware.com\">Jive Software</a> and the <a href=\"http://www.igniterealtime.org\">IgniteRealtime.org</a> community\n        </div>\n    </div>\n<!-- END jive-footer -->\n\n</body>\n</html>");
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

  private boolean _jspx_meth_decorator_title_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:title
    com.opensymphony.module.sitemesh.taglib.decorator.TitleTag _jspx_th_decorator_title_0 = (com.opensymphony.module.sitemesh.taglib.decorator.TitleTag) _jspx_tagPool_decorator_title_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.TitleTag.class);
    _jspx_th_decorator_title_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_title_0.setParent(null);
    int _jspx_eval_decorator_title_0 = _jspx_th_decorator_title_0.doStartTag();
    if (_jspx_th_decorator_title_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_title_nobody.reuse(_jspx_th_decorator_title_0);
      return true;
    }
    _jspx_tagPool_decorator_title_nobody.reuse(_jspx_th_decorator_title_0);
    return false;
  }

  private boolean _jspx_meth_decorator_head_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:head
    com.opensymphony.module.sitemesh.taglib.decorator.HeadTag _jspx_th_decorator_head_0 = (com.opensymphony.module.sitemesh.taglib.decorator.HeadTag) _jspx_tagPool_decorator_head_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.HeadTag.class);
    _jspx_th_decorator_head_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_head_0.setParent(null);
    int _jspx_eval_decorator_head_0 = _jspx_th_decorator_head_0.doStartTag();
    if (_jspx_th_decorator_head_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_head_nobody.reuse(_jspx_th_decorator_head_0);
      return true;
    }
    _jspx_tagPool_decorator_head_nobody.reuse(_jspx_th_decorator_head_0);
    return false;
  }

  private boolean _jspx_meth_decorator_getProperty_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:getProperty
    com.opensymphony.module.sitemesh.taglib.decorator.PropertyTag _jspx_th_decorator_getProperty_0 = (com.opensymphony.module.sitemesh.taglib.decorator.PropertyTag) _jspx_tagPool_decorator_getProperty_property_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.PropertyTag.class);
    _jspx_th_decorator_getProperty_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_getProperty_0.setParent(null);
    _jspx_th_decorator_getProperty_0.setProperty("body.onload");
    int _jspx_eval_decorator_getProperty_0 = _jspx_th_decorator_getProperty_0.doStartTag();
    if (_jspx_th_decorator_getProperty_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_getProperty_property_nobody.reuse(_jspx_th_decorator_getProperty_0);
      return true;
    }
    _jspx_tagPool_decorator_getProperty_property_nobody.reuse(_jspx_th_decorator_getProperty_0);
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
    _jspx_th_fmt_message_2.setKey("setup.title");
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
    _jspx_th_fmt_message_3.setKey("setup.sidebar.title");
    int _jspx_eval_fmt_message_3 = _jspx_th_fmt_message_3.doStartTag();
    if (_jspx_th_fmt_message_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_3);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_3);
    return false;
  }

  private boolean _jspx_meth_decorator_body_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:body
    com.opensymphony.module.sitemesh.taglib.decorator.BodyTag _jspx_th_decorator_body_0 = (com.opensymphony.module.sitemesh.taglib.decorator.BodyTag) _jspx_tagPool_decorator_body_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.BodyTag.class);
    _jspx_th_decorator_body_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_body_0.setParent(null);
    int _jspx_eval_decorator_body_0 = _jspx_th_decorator_body_0.doStartTag();
    if (_jspx_th_decorator_body_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_body_nobody.reuse(_jspx_th_decorator_body_0);
      return true;
    }
    _jspx_tagPool_decorator_body_nobody.reuse(_jspx_th_decorator_body_0);
    return false;
  }
}
