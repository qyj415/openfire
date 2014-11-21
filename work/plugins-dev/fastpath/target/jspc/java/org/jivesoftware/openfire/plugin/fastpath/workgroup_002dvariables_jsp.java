package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import java.util.List;
import org.jivesoftware.openfire.fastpath.dataforms.FormManager;
import org.jivesoftware.openfire.fastpath.dataforms.WorkgroupForm;
import org.jivesoftware.openfire.fastpath.dataforms.FormElement;

public final class workgroup_002dvariables_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			"workgroup-error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');

    String wgID = ParamUtils.getParameter(request, "wgID");
    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));


    FormManager formManager = FormManager.getInstance();

    WorkgroupForm workgroupForm = formManager.getWebForm(workgroup);
    if (workgroupForm == null) {
        workgroupForm = new WorkgroupForm();
        formManager.addWorkgroupForm(workgroup, workgroupForm);
    }

    boolean delete = ParamUtils.getBooleanParameter(request, "delete");
    boolean addVariable = ParamUtils.getParameter(request, "addVariable") != null;

    if (addVariable) {
        String variableType = ParamUtils.getParameter(request, "variableType");
        String variableName = ParamUtils.getParameter(request, "variableName");
        String variableValue = ParamUtils.getParameter(request, "variableValue");
        String variableDescription = ParamUtils.getParameter(request, "variableDescription");

        FormElement formElement = new FormElement();
        formElement.setAnswerType(WorkgroupForm.FormEnum.hidden);
        formElement.getAnswers().add(variableType + "_" + variableValue);
        formElement.setVariable(variableName);
        if (variableDescription != null) {
            formElement.setDescription(variableDescription);
        }

        workgroupForm.addHiddenVar(formElement);
    }

    if (delete) {
        int index = ParamUtils.getIntParameter(request, "index", -1);

        if (index >= 0) {
            // Re-add it based on the "direction" we're doing. First, remove it:
            workgroupForm.removeHiddenVarAt(index);

            // done, so redirect
            response.sendRedirect("workgroup-variables.jsp?wgID=" + wgID);
            return;
        }
    }

    boolean save = ParamUtils.getParameter(request, "save") != null;
    String message = "";
    if (save) {
        String title = ParamUtils.getParameter(request, "title");
        String description = ParamUtils.getParameter(request, "description");
        if (ModelUtil.hasLength(title)) {
            workgroupForm.setTitle(title);
        }

        if (ModelUtil.hasLength(description)) {
            workgroupForm.setDescription(description);
        }

        formManager.saveWorkgroupForm(workgroup);
        message = "Web Variables has been saved.";
    }

    String formTitle = workgroupForm.getTitle();
    String description = workgroupForm.getDescription();
    if (!ModelUtil.hasLength(formTitle)) {
        formTitle = "";
    }

    if (!ModelUtil.hasLength(description)) {
        description = "";
    }

      out.write("\r\n\r\n\r\n<html>\r\n    <head>\r\n        <title>");
      out.print( "Workgroup Variables for "+wgID);
      out.write("</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-variables\"/>\r\n        <meta name=\"extraParams\" content=\"wgID=");
      out.print( wgID );
      out.write("\"/>\r\n        <!--<meta name=\"helpPage\" content=\"create_a_form_variable.html\"/>-->\r\n\r\n        <script type=\"text/javascript\">\r\n          function Jtrim(st) {\r\n            var len = st.length;\r\n            var begin = 0, end = len - 1;\r\n            while (st.charAt(begin) == \" \" && begin < len) {\r\n                begin++;\r\n            }\r\n            while (st.charAt(end) == \" \" && end > begin) {\r\n                end--;\r\n            }\r\n            return st.substring(begin, end + 1);\r\n          }\r\n\r\n          function validateForm(){\r\n            if(!Jtrim(document.f.variableValue.value)){\r\n                alert(\"Please specify the name of the variable.\");\r\n                document.f.variableValue.focus();\r\n                return false;\r\n            }\r\n\r\n            if(!Jtrim(document.f.variableName.value)){\r\n               alert(\"Please specify the the new name of the variable.\");\r\n                document.f.variableName.focus();\r\n                return false;\r\n            }\r\n            return true;\r\n          }\r\n");
      out.write("        </script>\r\n    </head>\r\n    <body>\r\n\r\n<p>\r\n Add all variables you wish to have collected during a Chat Request.\r\n</p>\r\n\r\n<p>\r\n<b>Important:</b> Save the form to have your changes take affect.\r\n</p>\r\n");
 if(save){ 
      out.write("\r\n <div class=\"jive-success\">\r\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n    <tbody>\r\n        <tr>\r\n        \t<td class=\"jive-icon\"><img alt=\"success image\" src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\r\n        \t<td class=\"jive-icon-label\">");
      out.print( message );
      out.write("</td>\r\n        </tr>\r\n    </tbody>\r\n    </table>\r\n    </div>\r\n    <br/>\r\n");
 } 
      out.write("\r\n\r\n  <form name=\"f\" action=\"workgroup-variables.jsp\" method=\"post\" onsubmit=\"return validateForm(); return false;\">\r\n  <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\">\r\n  <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" >\r\n    <tr>\r\n        <th colspan=\"7\">Create New Variable From Chat Request</th>\r\n    </tr>\r\n    <tr valign=\"top\">\r\n    <td width=\"25%\">HTTP Type:</td>\r\n    <td colspan=\"7\">\r\n        <select name=\"variableType\">\r\n        <option value=\"getRequest\">Request Parameter</option>\r\n        <option value=\"getCookie\">Cookie</option>\r\n        <option value=\"getHeader\">HTTP Header</option>\r\n        <option value=\"getSession\">Session Attribute</option>\r\n        </select>\r\n        <br/><span class=\"jive-description\">The type of variable to be retrieved.</span>\r\n        </td>\r\n    </tr>\r\n    <tr valign=\"top\">\r\n    <td width=\"25%\">Name:*</td><td colspan=\"6\"><input type=\"text\" name=\"variableValue\" size=\"40\"><br/><span class=\"jive-description\">The name of the variable to retrieve.</span></td>\r\n    </tr>\r\n\r\n    <tr valign=\"top\">\r\n    <td width=\"25%\">Assign New Name:*</td><td colspan=\"6\"><input type=\"text\" name=\"variableName\" size=\"40\"><br/><span class=\"jive-description\">Specify the name you wish to assign to the variable value upon retrieving the information.</span></td>\r\n");
      out.write("    </tr>\r\n\r\n    <tr valign=\"top\">\r\n    <td width=\"25%\">Description:</td><td colspan=\"6\"><input type=\"text\" name=\"variableDescription\" size=\"40\"><br/><span class=\"jive-description\">Specify a general description of the variable being retrieved.</span></td>\r\n    </tr>\r\n    <tr>\r\n    <td colspan=\"7\"><input type=\"submit\" name=\"addVariable\" value=\"Add Variable\"></td>\r\n    </tr>\r\n   </form>\r\n     <form action=\"workgroup-variables.jsp\" method=\"post\">\r\n\r\n    <tr>\r\n        <th>Type</th><th>Name</th><th>Returned Name</th><th>Description</th><th>Delete</th>\r\n    </tr>\r\n    <!-- Build table -->\r\n    ");

        int counter = 0;
        for(FormElement element : workgroupForm.getHiddenVars()){
            if(element.getAnswerType() != WorkgroupForm.FormEnum.hidden){
                counter++;
                continue;
            }

            String variableValue = "";
            List answers = element.getAnswers();
            if(answers.size() > 0){
              variableValue = (String)element.getAnswers().get(0);
            }

            String type = null;
            if(variableValue.startsWith("getRequest_")){
                type = "Request Parameter";
            }
            if(variableValue.startsWith("getCookie_")){
                type = "Cookie";
            }
            else if(variableValue.startsWith("getHeader_")){
                type = "HTTP Header";
            }
            else if(variableValue.startsWith("getSession_")){
                type = "Session Attribute";
            }

            int indexOf = variableValue.indexOf("_");
            String varValue = variableValue.substring(indexOf + 1);
            if(type != null){
      out.write("\r\n        <tr valign=\"top\">\r\n            <td><b>");
      out.print( type );
      out.write("</b></td>\r\n            <td>");
      out.print( element.getVariable() );
      out.write("</td>\r\n            <td>");
      out.print( varValue );
      out.write("</td>\r\n            <td><span class=\"jive-description\">");
      out.print( element.getDescription() != null ? element.getDescription() : "&nbsp;" );
      out.write("</span></td>\r\n            <td> <a href=\"workgroup-variables.jsp?wgID=");
      out.print(wgID);
      out.write("&delete=true&index=");
      out.print(counter);
      out.write("\"><img src=\"images/delete-16x16.gif\" border=\"0\"></a></td>\r\n        </tr>\r\n        ");
 } 
      out.write("\r\n\r\n\r\n        ");
 counter++; }
      out.write("\r\n\r\n    <tr>\r\n\r\n    <td colspan=\"7\"><input type=\"submit\" name=\"save\" value=\"Save Form\"></td>\r\n    <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\">\r\n    </tr>\r\n   </table>\r\n   </form>\r\n\r\n</body>\r\n</html>");
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
