package org.jivesoftware.openfire.admin.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.database.DefaultConnectionProvider;
import org.jivesoftware.util.ClassUtils;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.XMPPServer;
import java.io.File;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import java.lang.Throwable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class setup_002ddatasource_002dstandard_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    boolean testConnection(Map<String,String> errors) {
        boolean success = true;
        Connection con = null;
        try {
            con = DbConnectionManager.getConnection();
            if (con == null) {
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
        catch (SQLException ex) {
            success = false;
            errors.put("general","A connection to the database could not be "
                + "made. View the error message by opening the "
                + "\"" + File.separator + "logs" + File.separator + "error.log\" log "
                + "file, then go back to fix the problem.");

        }
        finally {
            try {
        	    con.close();
            } catch (Exception ignored) {}
        }
        return success;
    }

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

      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

	// Redirect if we've already run setup:
	if (!XMPPServer.getInstance().isSetupMode()) {
        response.sendRedirect("setup-completed.jsp");
        return;
    }

      out.write("\r\n\r\n");
      out.write("\r\n\r\n\r\n");
  // Get parameters
    String driver = ParamUtils.getParameter(request,"driver");
    String serverURL = ParamUtils.getParameter(request,"serverURL");
    String username = ParamUtils.getParameter(request,"username",true);
    String password = ParamUtils.getParameter(request,"password",true);
    int minConnections = ParamUtils.getIntParameter(request,"minConnections",-1);
    int maxConnections = ParamUtils.getIntParameter(request,"maxConnections",-1);
    double connectionTimeout = ParamUtils.getDoubleParameter(request,"connectionTimeout",0.0);

    boolean doContinue = request.getParameter("continue") != null;

    // handle a continue request
    Map<String,String> errors = new HashMap<String,String>();
    if (doContinue) {
        // Error check
        if (driver == null || "sun.jdbc.odbc.JdbcOdbcDriver".equals(driver)
                || "com.internetcds.jdbc.tds.Driver".equals(driver))
        {
            errors.put("driver","Please enter a valid JDBC driver class.");
        }
        else {
            try {
                ClassUtils.forName(driver);
            }
            catch (Throwable t) {
                errors.put("driver","Unable to load the specified JDBC driver. Please verify the " +
                        "name of the driver is correct and that the driver is in the classpath " +
                        "of this server (usually the 'lib' directory). If you add a driver to " +
                        "your classpath you will neeed to restart the server.");
            }
        }
        if (serverURL == null) {
            errors.put("serverURL", "Please enter a valid JDBC URL.");
        }
        if (minConnections < 3) {
            errors.put("minConnections","The minimum connection pool size is three connections.");
        }
        if (maxConnections < minConnections) {
            errors.put("maxConnections","The maximum number of connections cannot be less than the minimum.");
        }
        if (connectionTimeout <= 0.0) {
            errors.put("connectionTimeout","Please enter a valid connection timeout value.");
        }

        // if there were no errors, continue
        if (errors.size() == 0) {
            // set properties, test connection, etc

            // Force the standard jive connection provider to be used by deleting the current setting:
            JiveGlobals.setXMLProperty("connectionProvider.className",
                    "org.jivesoftware.database.DefaultConnectionProvider");
            DefaultConnectionProvider conProvider = new DefaultConnectionProvider();
            try {
                conProvider.setDriver(driver);
                conProvider.setConnectionTimeout(connectionTimeout);
                conProvider.setMinConnections(minConnections);
                conProvider.setMaxConnections(maxConnections);
                conProvider.setServerURL(serverURL);
                conProvider.setUsername(username);
                conProvider.setPassword(password);
                conProvider.setTestSQL(DbConnectionManager.getTestSQL(driver));

                JiveGlobals.setXMLProperty("database.defaultProvider.driver", driver);
                JiveGlobals.setXMLProperty("database.defaultProvider.serverURL", serverURL);
                JiveGlobals.setXMLProperty("database.defaultProvider.username", username);
                JiveGlobals.setXMLProperty("database.defaultProvider.password", password);
                JiveGlobals.setXMLProperty("database.defaultProvider.testSQL", DbConnectionManager.getTestSQL(driver));

                JiveGlobals.setXMLProperty("database.defaultProvider.minConnections",
                        Integer.toString(minConnections));
                JiveGlobals.setXMLProperty("database.defaultProvider.maxConnections",
                        Integer.toString(maxConnections));
                JiveGlobals.setXMLProperty("database.defaultProvider.connectionTimeout",
                Double.toString(connectionTimeout));
            }
            catch (Exception e) {
                errors.put("general","Setting connection properties failed - please see the error "
                        + "log located in home/logs for more details.");
                Log.error(e);
            }
            // No errors setting the properties, so test the connection
            DbConnectionManager.setConnectionProvider(conProvider);
            if (testConnection(errors)) {
                // Success, move on
                response.sendRedirect("setup-profile-settings.jsp");
                return;
            }
        }
    }

    if (!doContinue) {
        // reset values of jdbc driver from props file
        driver = JiveGlobals.getXMLProperty("database.defaultProvider.driver");
        serverURL = JiveGlobals.getXMLProperty("database.defaultProvider.serverURL");
        username = JiveGlobals.getXMLProperty("database.defaultProvider.username");
        password = JiveGlobals.getXMLProperty("database.defaultProvider.password");
        try {
            minConnections = Integer.parseInt(
                    JiveGlobals.getXMLProperty("database.defaultProvider.minConnections"));
        }
        catch (Exception e) {
            minConnections = 5;
        }
        try {
            maxConnections = Integer.parseInt(
                    JiveGlobals.getXMLProperty("database.defaultProvider.maxConnections"));
        }
        catch (Exception e) {
            maxConnections = 25;
        }
        try {
            connectionTimeout = Double.parseDouble(
                    JiveGlobals.getXMLProperty("database.defaultProvider.connectionTimeout"));
        }
        catch (Exception e) {
            connectionTimeout = 1.0;
        }
    }

      out.write("\r\n\r\n<html>\r\n<head>\r\n    <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\r\n    <meta name=\"currentStep\" content=\"2\"/>\r\n</head>\r\n<body>\r\n\r\n\t<h1>\r\n\t");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\r\n\t</h1>\r\n\r\n\t<p>\r\n\t");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write(".\r\n\t</p>\r\n\r\n\t<p>\r\n\t<b>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write(" </b>");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write(" <tt>[Openfire_HOME]/resources/database</tt>.\r\n\t</p>\r\n\r\n");
  if (errors.size() > 0) { 
      out.write("\r\n    <div class=\"error\">\r\n    ");
  if (errors.get("general") != null) { 
      out.write("\r\n\r\n        ");
      out.print( errors.get("general") );
      out.write("\r\n\r\n    ");
  } else { 
      out.write("\r\n\r\n        ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\r\n\r\n    ");
  } 
      out.write("\r\n    </div>\r\n");
  } 
      out.write("\r\n\r\n\r\n\r\n\t<!-- BEGIN jive-contentBox -->\r\n\t<div class=\"jive-contentBox\">\r\n\r\n\r\n");
  // DB preset data
    List<String[]> presets = new ArrayList<String []>();
    presets.add(new String[]{"MySQL","com.mysql.jdbc.Driver","jdbc:mysql://[host-name]:3306/[database-name]?rewriteBatchedStatements=true"});
    presets.add(new String[]{"Oracle","oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@[host-name]:1521:[SID]"});
    presets.add(new String[]{"Microsoft SQLServer","net.sourceforge.jtds.jdbc.Driver","jdbc:jtds:sqlserver://[host-name]/[database-name];appName=jive"});
    presets.add(new String[]{"PostgreSQL","org.postgresql.Driver","jdbc:postgresql://[host-name]:5432/[database-name]"});
    presets.add(new String[]{"IBM DB2","com.ibm.db2.jcc.DB2Driver","jdbc:db2://[host]:50000/[database-name]"});

      out.write("\r\n<script language=\"JavaScript\" type=\"text/javascript\">\r\nvar data = new Array();\r\n");
  for (int i=0; i<presets.size(); i++) {
        String[] data = presets.get(i);

      out.write("\r\n    data[");
      out.print( i );
      out.write("] = new Array('");
      out.print( data[0] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print( data[1] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print( data[2] );
      out.write("');\r\n");
  } 
      out.write("\r\nfunction populate(i) {\r\n    document.dbform.driver.value=data[i][1];\r\n    document.dbform.serverURL.value=data[i][2];\r\n}\r\nvar submitted = false;\r\nfunction checkSubmit() {\r\n    if (!submitted) {\r\n        submitted = true;\r\n        return true;\r\n    }\r\n    return false;\r\n}\r\n</script>\r\n\r\n<form action=\"setup-datasource-standard.jsp\" method=\"post\" name=\"dbform\" onsubmit=\"return checkSubmit();\">\r\n\r\n<table cellpadding=\"3\" cellspacing=\"2\" border=\"0\">\r\n<tr>\r\n\t<td nowrap align=\"right\">");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write(":</td>\r\n    <td>\r\n        <select size=\"1\" name=\"presets\" onchange=\"populate(this.options[this.selectedIndex].value)\">\r\n            <option value=\"\">");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\r\n            ");
  for (int i=0; i<presets.size(); i++) {
                    String[] data = presets.get(i);
            
      out.write("\r\n                <option value=\"");
      out.print( i );
      out.write("\"> &#149; ");
      out.print( data[0] );
      out.write("\r\n            ");
  } 
      out.write("\r\n        </select>\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td nowrap align=\"right\">\r\n        ");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n    <td>\r\n        <input type=\"text\" name=\"driver\" size=\"50\" maxlength=\"150\"\r\n         value=\"");
      out.print( ((driver != null) ? driver : "") );
      out.write("\">\r\n        <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span>\r\n        ");
  if (errors.get("driver") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      out.print( errors.get("driver") );
      out.write("\r\n            </span>\r\n        ");
  } 
      out.write("\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td nowrap align=\"right\">\r\n        ");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n    <td>\r\n        <input type=\"text\" name=\"serverURL\" size=\"50\" maxlength=\"250\"\r\n         value=\"");
      out.print( ((serverURL != null) ? serverURL : "") );
      out.write("\">\r\n\t    <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span>\r\n        ");
  if (errors.get("serverURL") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      out.print( errors.get("serverURL") );
      out.write("\r\n            </span>\r\n        ");
  } 
      out.write("\r\n    </td>\r\n</tr>\r\n<tr><td colspan=\"2\">&nbsp;</td></tr>\r\n<tr valign=\"top\">\r\n    <td nowrap align=\"right\">\r\n        ");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n    <td>\r\n        <input type=\"text\" name=\"username\" size=\"20\" maxlength=\"50\"\r\n         value=\"");
      out.print( ((username != null) ? username : "") );
      out.write("\">\r\n        <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span>\r\n        ");
  if (errors.get("username") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      out.print( errors.get("username") );
      out.write("\r\n            </span>\r\n        ");
  } 
      out.write("\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td nowrap align=\"right\">\r\n        ");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n    <td>\r\n        <input type=\"password\" name=\"password\" size=\"20\" maxlength=\"50\"\r\n         value=\"");
      out.print( ((password != null) ? password : "") );
      out.write("\">\r\n        <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span>\r\n        ");
  if (errors.get("password") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      out.print( errors.get("password") );
      out.write("\r\n            </span>\r\n        ");
  } 
      out.write("\r\n    </td>\r\n</tr>\r\n<tr><td colspan=\"2\">&nbsp;</td></tr>\r\n<tr valign=\"top\">\r\n    <td nowrap align=\"right\">\r\n        ");
      out.write("\r\n        Minimum Connections:\r\n    </td>\r\n    <td>\r\n\t    <input type=\"text\" name=\"minConnections\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( ((minConnections != -1) ? ""+minConnections : "") );
      out.write("\">\r\n        <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span>\r\n        ");
  if (errors.get("minConnections") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      out.print( errors.get("minConnections") );
      out.write("\r\n            </span>\r\n        ");
  } 
      out.write("\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td nowrap align=\"right\">\r\n        ");
      out.write("\r\n        Maximum Connections:\r\n    </td>\r\n    <td>\r\n\t    <input type=\"text\" name=\"maxConnections\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( ((maxConnections != -1) ? ""+maxConnections : "") );
      out.write("\">\r\n        <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span>\r\n        ");
  if (errors.get("maxConnections") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      out.print( errors.get("maxConnections") );
      out.write("\r\n            </span>\r\n        ");
  } 
      out.write("\r\n    </td>\r\n</tr>\r\n<tr valign=\"top\">\r\n    <td nowrap align=\"right\">\r\n        ");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("\r\n    </td>\r\n    <td>\r\n        <input type=\"text\" name=\"connectionTimeout\" size=\"5\" maxlength=\"5\"\r\n         value=\"");
      out.print( connectionTimeout );
      out.write("\"> <span style=\"display: block; float: left; padding: 2px 5px 0px 2px;\">Days</span>\r\n        <span class=\"jive-setup-helpicon\" onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("', 'styleClass', 'jiveTooltip', 'trail', true, 'delay', 300, 'lifetime', 8000);\"></span>\r\n        ");
  if (errors.get("connectionTimeout") != null) { 
      out.write("\r\n            <span class=\"jive-error-text\">\r\n            ");
      out.print( errors.get("connectionTimeout") );
      out.write("\r\n            </span>\r\n        ");
  } 
      out.write("\r\n    </td>\r\n</tr>\r\n</table>\r\n\r\n<br>\r\n\r\n\t\t<div align=\"right\"><div class=\"jive-description\" style=\"padding-bottom:10px;\">\r\n\t\t\t");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</div>\r\n\t\t\t<input type=\"Submit\" name=\"continue\" value=\"");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
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
    _jspx_th_fmt_message_0.setKey("setup.datasource.standard.title");
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
    _jspx_th_fmt_message_1.setKey("setup.datasource.standard.title");
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
    _jspx_th_fmt_message_2.setKey("setup.datasource.standard.info");
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
    _jspx_th_fmt_message_3.setKey("title");
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
    _jspx_th_fmt_message_4.setKey("setup.datasource.standard.info2");
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
    _jspx_th_fmt_message_5.setKey("setup.datasource.standard.info3");
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
    _jspx_th_fmt_message_6.setKey("setup.datasource.standard.failed_connect");
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
    _jspx_th_fmt_message_7.setKey("setup.datasource.standard.label");
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
    _jspx_th_fmt_message_8.setKey("setup.datasource.standard.pick_database");
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
    _jspx_th_fmt_message_9.setKey("setup.datasource.standard.jdbc");
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
    _jspx_th_fmt_message_10.setKey("setup.datasource.standard.jdbc_info");
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
    _jspx_th_fmt_message_11.setKey("setup.datasource.standard.url");
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
    _jspx_th_fmt_message_12.setKey("setup.datasource.standard.valid_url");
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
    _jspx_th_fmt_message_13.setKey("setup.datasource.standard.username");
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
    _jspx_th_fmt_message_14.setKey("setup.datasource.standard.username_info");
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
    _jspx_th_fmt_message_15.setKey("setup.datasource.standard.password");
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
    _jspx_th_fmt_message_16.setKey("setup.datasource.standard.password_info");
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
    _jspx_th_fmt_message_17.setKey("setup.datasource.standard.pool");
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
    _jspx_th_fmt_message_18.setKey("setup.datasource.standard.pool");
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
    _jspx_th_fmt_message_19.setKey("setup.datasource.standard.timeout");
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
    _jspx_th_fmt_message_20.setKey("setup.datasource.standard.timeout_info");
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
    _jspx_th_fmt_message_21.setKey("setup.datasource.standard.note");
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
    _jspx_th_fmt_message_22.setKey("global.continue");
    int _jspx_eval_fmt_message_22 = _jspx_th_fmt_message_22.doStartTag();
    if (_jspx_th_fmt_message_22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
    return false;
  }
}
