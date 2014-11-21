package org.jivesoftware.openfire.plugin.userImportExport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.MalformedURLException;
import java.util.*;
import org.dom4j.DocumentException;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.jivesoftware.openfire.plugin.ImportExportPlugin;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.util.ParamUtils;

public final class import_002duser_002ddata_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

 
public boolean isEmpty(String s) {
    if (s == null) {
        return true;
    }
    
    if (s.trim().length() == 0) {
        return true;
    }
    
    return false;
}

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

      out.write("\r\n\r\n");

    boolean importUsers = request.getParameter("importUsers") != null;
   
    ImportExportPlugin plugin = (ImportExportPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("userimportexport");
    List<String> duplicateUsers = new ArrayList<String>();
   
    Map<String, String> errors = new HashMap<String, String>();
    if (importUsers) {
        DiskFileUpload dfu = new DiskFileUpload();
      
        List fileItems = dfu.parseRequest(request);
        Iterator i = fileItems.iterator();
        FileItem fi = (FileItem) i.next();
        FileItem pd = (FileItem) i.next();
        String previousDomain = pd.getString();
        
        if (plugin.validateImportFile(fi)) {
            try {
                if (isEmpty(previousDomain)) {
                    duplicateUsers.addAll(plugin.importUserData(fi, null));
                }
                else if (!isEmpty(previousDomain)) {
                    duplicateUsers.addAll(plugin.importUserData(fi, previousDomain));
                }
                else {
                    errors.put("missingDomain", "missingDomain");
                }
              
                if (duplicateUsers.size() == 0) {
                    response.sendRedirect("import-user-data.jsp?success=true");
                    return;
                }
                
                errors.put("invalidUser", "invalidUser");
            }
            catch (MalformedURLException e) {
                errors.put("IOException", "IOException");
            }
            catch (DocumentException e) {
                errors.put("DocumentException", "DocumentException");
            }
        }
        else {
            errors.put("invalidUserFile", "invalidUserFile");
        }
    }

      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>Import User Data</title>\r\n        <meta name=\"pageID\" content=\"import-export-selection\"/>\r\n    </head>\r\n    <body>\r\n\r\n");
 if (errors.size() > 0) { 
      out.write("\r\n\r\n    <div class=\"jive-error\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n        <tbody>\r\n        <tr>\r\n            <td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\r\n            <td class=\"jive-icon-label\">\r\n            ");
 if (errors.containsKey("missingDomain")) { 
      out.write("\r\n                You must supply both a existing and new domain name.\r\n            ");
 } else if (errors.containsKey("IOException")) { 
      out.write("\r\n                Missing or bad file name.\r\n            ");
 } else if (errors.containsKey("DocumentException")) { 
      out.write("\r\n                Import failed.\r\n            ");
 } else if (errors.containsKey("invalidUserFile")) { 
      out.write("\r\n                The import file does not match the user schema.\r\n            ");
 } else if (errors.containsKey("invalidUser")) { 
      out.write("\r\n                \r\n                ");
 if (plugin.isUserProviderReadOnly()) { 
      out.write("\r\n                   The following users did not exist in the system or have invalid username so their roster was not loaded:<br>\r\n                ");
 } else { 
      out.write("\r\n                   The following users already exist in the system or have invalid username and were not loaded:<br>\r\n               ");
 } 
      out.write("\r\n            ");

                Iterator iter = duplicateUsers.iterator();
                while (iter.hasNext()) {
                    String username = (String) iter.next();
                    
      out.print( username );

                    if (iter.hasNext()) {
                        
      out.write(",&nbsp;");

                    } else {
                        
      out.write('.');

                    }
                }
            } 
      out.write("\r\n            </td>\r\n        </tr>\r\n        </tbody>\r\n    </table>\r\n    </div>\r\n    <br>\r\n\r\n");
 } else if (ParamUtils.getBooleanParameter(request, "success")) { 
      out.write("\r\n\r\n    <div class=\"jive-success\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n        <tbody>\r\n        <tr>\r\n            <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\r\n            ");
 if (plugin.isUserProviderReadOnly()) { 
      out.write("\r\n               <td class=\"jive-icon-label\">User roster data added successfully.</td>\r\n            ");
 } else { 
      out.write("\r\n               <td class=\"jive-icon-label\">All users added successfully.</td>\r\n            ");
 } 
      out.write("\r\n        </tr>\r\n        </tbody>\r\n    </table>\r\n    </div>\r\n    <br>\r\n\r\n");
 } 
      out.write("\r\n\r\nUse the form below to import a user data XML file.\r\n\r\n\r\n<form action=\"import-user-data.jsp?importUsers\" method=\"post\" enctype=\"multipart/form-data\">\r\n\r\n<div class=\"jive-contentBoxHeader\">Import</div>\r\n<div class=\"jive-contentBox\">\r\n    <p>\r\n    Choose a file to import:</p>\r\n    <input type=\"file\" name=\"thefile\">\r\n\r\n    <br><br><br>\r\n   \r\n    <p>\r\n    <b>Optional</b> - Use the field below to replace the domain name of user roster entries with the current hostname.\r\n    See the migration section of the <a href=\"../../plugin-admin.jsp?plugin=userimportexport&showReadme=true&decorator=none\">readme</a> for details.\r\n    </p>\r\n    Replace Domain: <input type=\"text\" size=\"20\" maxlength=\"150\" name=\"previousDomain\" value=\"\"/>\r\n</div>\r\n<input type=\"submit\" value=\"Import\">\r\n\r\n</form>\r\n\r\n</body>\r\n</html>\r\n\r\n");
      out.write('\r');
      out.write('\n');
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
