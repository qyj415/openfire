package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.util.*;
import java.net.URLEncoder;
import java.net.URLDecoder;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.openfire.fastpath.macros.MacroGroup;
import org.jivesoftware.openfire.fastpath.macros.WorkgroupMacros;
import org.jivesoftware.openfire.fastpath.macros.Macro;

public final class workgroup_002dadd_002dresponse_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n\r\n");
 // Get parameters
    String wgID = ParamUtils.getParameter(request, "wgID");
    boolean add = request.getParameter("add") != null;
    boolean delete = request.getParameter("delete") != null;
    boolean edit = request.getParameter("edit") != null;

    String success = request.getParameter("success");
    String failure = request.getParameter("failure");

    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));
    WorkgroupMacros workgroupMacros = WorkgroupMacros.getInstance();

    String groupTitle = URLDecoder
            .decode(ParamUtils.getParameter(request, "macroGroupTitle"), "UTF-8");


    MacroGroup rootGroup = workgroupMacros.getMacroGroup(workgroup, groupTitle);


    String responseTitle = ParamUtils.getParameter(request, "responseTitle");
    String responseBody = ParamUtils.getParameter(request, "responseBody");
    int responseType = ParamUtils.getIntParameter(request, "responseType", -1);

    int entry = ParamUtils.getIntParameter(request, "entry", -1);

    if (delete) {
        if (entry != -1) {
            Macro macro = rootGroup.getMacro(entry);
            if (macro != null) {
                rootGroup.removeMacro(macro);
                success = "Response has been deleted.";
            }
        }
    }


    boolean commitEdit = request.getParameter("editAdd") != null;
    if (commitEdit) {
        Macro macro = rootGroup.getMacro(entry);
        macro.setTitle(responseTitle);
        macro.setResponse(responseBody);
        macro.setType(responseType);
        workgroupMacros.saveMacros(workgroup);
        response.sendRedirect("workgroup-add-response.jsp?macroGroupTitle="
                + URLEncoder.encode(groupTitle, "UTF-8") + "&wgID=" + wgID
                + "&success=Response has been edited successfully.");
        return;
    }


    if (add && !edit) {
        if (ModelUtil.hasLength(responseTitle) && ModelUtil.hasLength(responseBody)) {
            // Create new MacroGroup and add
            Macro macro = new Macro();
            macro.setTitle(responseTitle);
            macro.setResponse(responseBody);
            macro.setType(responseType);
            rootGroup.addMacro(macro);
            workgroupMacros.saveMacros(workgroup);
            response.sendRedirect("workgroup-add-response.jsp?macroGroupTitle="
                    + URLEncoder.encode(groupTitle, "UTF-8") + "&wgID=" + wgID
                    + "&success=New response has been added.");
            return;
        }
    }


    boolean inEditMode = false;

    String title = "Add New Canned Response To Category";


    String macroTitle = "";
    String macroBody = "";
    int type = 0;
    if (edit && !ModelUtil.hasLength(responseTitle)) {
        if (entry != -1) {
            Macro macro = rootGroup.getMacro(entry);
            macroTitle = macro.getTitle();
            macroBody = macro.getResponse();
            inEditMode = true;
            title = "Edit Canned Response";
        }
    }



      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>");
      out.print( title );
      out.write("</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-macros\"/>\r\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\r\n    </head>\r\n    <body>\r\n<script language=\"javascript\">\r\n        function Jtrim(st) {\r\n            var len = st.length;\r\n            var begin = 0, end = len - 1;\r\n            while (st.charAt(begin) == \" \" && begin < len) {\r\n                begin++;\r\n            }\r\n            while (st.charAt(end) == \" \" && end > begin) {\r\n                end--;\r\n            }\r\n            return st.substring(begin, end + 1);\r\n        }\r\n\r\n        function validateForm(){\r\n           if(!Jtrim(document.f.responseTitle.value)){\r\n              alert(\"You must specify a valid title for this response.\");\r\n              document.f.responseTitle.focus();\r\n              return false;\r\n           }\r\n\r\n           if(!Jtrim(document.f.responseBody.value)){\r\n                alert(\"You must specify a valid response body for this response.\");\r\n                document.f.responseBody.focus();\r\n                return false;\r\n            }\r\n           return true;\r\n        }\r\n\r\n\r\n</script>\r\n\r\n");
 if(!edit){ 
      out.write("\r\n<p>\r\nCreate a new response to add to the \"<b>");
      out.print( groupTitle);
      out.write("</b>\" using the form below.\r\n</p>\r\n");
 } else { 
      out.write("\r\n<p>\r\nEdit the response using the form below.\r\n</p>\r\n");
 } 
      out.write("\r\n\r\n");
  if (ModelUtil.hasLength(failure)) { 
      out.write("\r\n\r\n    <p class=\"jive-error-text\">\r\n    ");
      out.print( failure);
      out.write("\r\n    </p>\r\n\r\n");
  } else if (ModelUtil.hasLength(success)) { 
      out.write("\r\n\r\n    <div class=\"jive-success\">\r\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n\r\n            <tbody>\r\n                <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\r\n                <td class=\"jive-icon-label\">\r\n                ");
      out.print( success);
      out.write("\r\n                </td></tr>\r\n            </tbody>\r\n            </table>\r\n            </div><br>\r\n");
 } 
      out.write("\r\n\r\n<form name=\"f\" action=\"workgroup-add-response.jsp\" method=\"post\" onsubmit=\"return validateForm(); return false;\">\r\n<input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\" />\r\n<input type=\"hidden\" name=\"macroGroupTitle\" value=\"");
      out.print( URLEncoder.encode(groupTitle, "UTF-8") );
      out.write("\" />\r\n<input type=\"hidden\" name=\"add\" value=\"true\" />\r\n");
if(inEditMode){ 
      out.write("\r\n<input type=\"hidden\" name=\"editAdd\" value=\"true\" />\r\n<input type=\"hidden\" name=\"entry\" value=\"");
      out.print(entry);
      out.write("\" />\r\n");
 } 
      out.write("\r\n<table class=\"jive-table\" cellspacing=\"0\" cellpadding=\"0\"  width=\"100%\">\r\n<th colspan=\"2\">");
      out.print( inEditMode ? "Edit Response" : "Add New Response");
      out.write("</th>\r\n  <tr valign=\"top\">\r\n  <td>Response Title<br/><span class=\"jive-description\">Please enter the title for this response.</span></td><td><input type=\"text\" name=\"responseTitle\" value=\"");
      out.print( macroTitle );
      out.write("\" size=\"40\" maxlength=\"40\"><br><span class=\"jive-description\">Adding new response to <b>");
      out.print( rootGroup.getTitle() );
      out.write("</b></span></td>\r\n  </tr>\r\n  <tr valign=\"top\">\r\n  <td>Response Type<br/><span class=\"jive-description\">Please select the response type.</span></td><td>\r\n  <select name=\"responseType\">\r\n  <option value=\"0\">Text</option>\r\n  <option value=\"1\">URL</option>\r\n  <option value=\"2\">Image</option>\r\n  </select>\r\n  </td>\r\n  </tr>\r\n  <tr valign=\"top\">\r\n  <td width=\"40%\">Response Body<br/><span class=\"jive-description\">Enter your response body. If you selected a url or image, just enter the URL to either.</span></td><td><textarea name=\"responseBody\" cols=\"40\" rows=\"4\">");
      out.print( macroBody);
      out.write("</textarea></td>\r\n  </tr>\r\n  <tr>\r\n  <td colspan=\"2\">\r\n  <input type=\"submit\" name=\"Add\" value=\"");
      out.print( inEditMode ? "Save Changes" : "Add Response" );
      out.write("\">\r\n  </td>\r\n  </tr>\r\n</table>\r\n</form>\r\n\r\n<br><br>\r\n<p>\r\nViewing all responses in <b>\"");
      out.print( groupTitle );
      out.write("\"</b>\r\n</p>\r\n\r\n<form action=\"workgroup-add-response.jsp\" method=\"post\">\r\n<input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\" />\r\n<input type=\"hidden\" name=\"macroGroupTitle\" value=\"");
      out.print( URLEncoder.encode(groupTitle, "UTF-8") );
      out.write("\" />\r\n<input type=\"hidden\" name=\"add\" value=\"true\" />\r\n<table class=\"jive-table\" cellspacing=\"0\" cellpadding=\"0\"  width=\"100%\">\r\n<th colspan=\"1\">Title</th><th>Type</th><th>Options</th>\r\n");

    int count = 0;
    for(Macro macro : rootGroup.getMacros()){
      out.write("\r\n        <tr>\r\n        <td>");
      out.print( macro.getTitle());
      out.write("</td>\r\n        <td>");
      out.print( "Text");
      out.write("</td>\r\n        <td align=\"center\"><a href=\"workgroup-add-response.jsp?edit=true&wgID=");
      out.print(wgID);
      out.write("&macroGroupTitle=");
      out.print( URLEncoder.encode(groupTitle, "UTF-8"));
      out.write("&entry=");
      out.print(count);
      out.write("\"><img src=\"images/edit-16x16.gif\" border=\"0\"></a>&nbsp;<a href=\"workgroup-add-response.jsp?delete=true&wgID=");
      out.print(wgID);
      out.write("&macroGroupTitle=");
      out.print( URLEncoder.encode(groupTitle, "UTF-8"));
      out.write("&entry=");
      out.print(count);
      out.write("\"><img src=\"images/delete-16x16.gif\" border=\"0\"></a></td>\r\n        </tr>\r\n\r\n");

        count++;
    }

      out.write("\r\n</table>\r\n</form>\r\n</body>\r\n</html>\r\n\r\n");
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
