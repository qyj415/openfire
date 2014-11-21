package org.jivesoftware.openfire.plugin.dbaccess;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.database.DbConnectionManager;
import java.sql.*;

public final class db_002daccess_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
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

      out.write("\n\n\n\n\n");

    // Get parameters
    boolean execute = request.getParameter("execute") != null;
    String sql = request.getParameter("sql");

      out.write("\n\n<html>\n    <head>\n        <title>DB Access Tool</title>\n        <meta name=\"pageID\" content=\"db-access\"/>\n    </head>\n    <body>\n\n<div class=\"information\">\n    Do <b>NOT</b> use this to edit your database unless you know what you are doing.  Openfire will not necessarily\n    handle changes to it's database out from under it while it is running.  Most likely you were asked to try a\n    couple of commands by whoever recommended this plugin, so please try to stick to that (or read-only activities).\n</div>\n\n<div>\n    <h3>SQL Statement:</h3>\n    <form action=\"db-access.jsp\" method=\"post\">\n        <textarea rows=\"10\" cols=\"80\" name=\"sql\">");
      out.print( sql != null ? sql : "" );
      out.write("</textarea>\n        <br />\n        <input type=\"submit\" name=\"execute\" value=\"Execute SQL\"/>\n    </form>\n</div>\n\n<div>\n    <h3>SQL Output:</h3>\n    <div style=\"width: 100%; height: 200px; border: 1.0px solid #000000; overflow: scroll\" id=\"output\">\n");

    // Handle an execution
    if (execute) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DbConnectionManager.getConnection();
            stmt = con.createStatement();

            // SQL
            out.println("<p>Your query: <b>" + sql + "</b></p>");
            stmt.execute(sql);
            rs = stmt.getResultSet();
            if (rs == null) {
                // print updatecount
                out.println("<p>Result: updateCount = <b>" + stmt.getUpdateCount() + "</p>");
            } else {
                // process resultset
                out.println("<br>Your response:");

                ResultSetMetaData md = rs.getMetaData();
                int count = md.getColumnCount();
                out.println("<table border=1>");
                out.print("<tr>");
                for (int i=1; i<=count; i++) {
                    out.print("<th>");
                    out.print(md.getColumnName(i));
                }
                out.println("</tr>");
                while (rs.next()) {
                    out.print("<tr>");
                    for (int i=1; i<=count; i++) {
                        out.print("<td>");
                        out.print(rs.getString(i));
                    }
                    out.println("</tr>");
                }
            }
            out.println("</table>");
            // rs.close();
        } catch (SQLException ex) {
            out.print("<B>" + getClass() + ": SQL Error:</B>\n" + ex);
//            out.print("<pre>");
//            ex.printStackTrace(out);
//            out.print("</pre>");
        }
        finally {
            DbConnectionManager.closeConnection(rs, stmt, con);
        }
    }

      out.write("\n    </div>\n</div>\n\n</body>\n</html>");
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
}
