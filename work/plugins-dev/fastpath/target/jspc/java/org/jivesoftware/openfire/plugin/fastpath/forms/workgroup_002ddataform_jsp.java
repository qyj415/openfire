package org.jivesoftware.openfire.plugin.fastpath.forms;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.openfire.fastpath.dataforms.FormManager;
import org.jivesoftware.openfire.fastpath.dataforms.WorkgroupForm;
import org.jivesoftware.openfire.fastpath.dataforms.FormElement;
import org.jivesoftware.openfire.fastpath.dataforms.FormUtils;

public final class workgroup_002ddataform_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    String wgID = ParamUtils.getParameter(request, "wgID");
    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));


    FormManager formManager = FormManager.getInstance();

    WorkgroupForm workgroupForm = formManager.getWebForm(workgroup);
    if (workgroupForm == null) {
        workgroupForm = new WorkgroupForm();
        formManager.addWorkgroupForm(workgroup, workgroupForm);
    }

    boolean changePosition = ParamUtils.getBooleanParameter(request, "changePos");
    boolean up = ParamUtils.getBooleanParameter(request, "up");
    boolean down = ParamUtils.getBooleanParameter(request, "down");

    boolean delete = ParamUtils.getBooleanParameter(request, "delete");

    // Change the position of a interceptor
    if (changePosition) {
        int index = ParamUtils.getIntParameter(request, "index", -1);

        if (index >= 0) {
            // Get the Form element at index
            FormElement element = workgroupForm.getFormElementAt(index);

            // Re-add it based on the "direction" we're doing. First, remove it:
            workgroupForm.removeFormElement(index);
            if (up) {
                workgroupForm.addFormElement(element, index - 1);
            }
            if (down) {
                workgroupForm.addFormElement(element, index + 1);
            }

            // done, so redirect
            response.sendRedirect("workgroup-dataform.jsp?wgID=" + wgID);
            return;
        }
    }

    String changeRequired = ParamUtils.getParameter(request, "notRequired");
    if (ModelUtil.hasLength(changeRequired)) {
        boolean notRequired = ParamUtils.getBooleanParameter(request, "notRequired");
        int index = ParamUtils.getIntParameter(request, "index", -1);
        FormElement elem = workgroupForm.getFormElementAt(index);
        elem.setRequired(notRequired);
    }


    if (delete) {
        int index = ParamUtils.getIntParameter(request, "index", -1);

        if (index >= 0) {
            // Re-add it based on the "direction" we're doing. First, remove it:
            workgroupForm.removeFormElement(index);

            // done, so redirect
            response.sendRedirect("workgroup-dataform.jsp?wgID=" + wgID);
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
        message = "Web Form has been saved.";
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
      out.print( "Workgroup Web Form for "+wgID);
      out.write("</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-forms\"/>\r\n        <meta name=\"extraParams\" content=\"wgID=");
      out.print( wgID );
      out.write("\"/>\r\n        <!--<meta name=\"helpPage\" content=\"create_a_form.html\"/>-->\r\n    </head>\r\n    <body>\r\n\r\n<p>\r\n Create your own customized HTML Form to collect information from the user.\r\n</p>\r\n\r\n<p>\r\n<b>Important:</b>&nbsp;Saving the form makes the form visible to customers.\r\n</p>\r\n");
 if(save){ 
      out.write("\r\n <div class=\"success\">\r\n        \t");
      out.print( message );
      out.write("\r\n</div>\r\n");
 } 
      out.write("\r\n\r\n  <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" >\r\n     <form action=\"workgroup-dataform.jsp\" method=\"post\">\r\n    <tr>\r\n        <th>Order</th><th>Label</th><th>Name</th><th>Type</th><th>Required</th><th>Edit</th><th>Delete</th>\r\n    </tr>\r\n    <!-- Build table -->\r\n    ");

        int counter = 0;
        int size = workgroupForm.getFormElements().size();
        for(FormElement element : workgroupForm.getFormElements()){
            boolean isHidden = (element.getAnswerType() == WorkgroupForm.FormEnum.hidden);
    
      out.write("\r\n\r\n\r\n        <tr valign=\"top\" ");
      out.print( isHidden ? "bgcolor=\"#fffff\"" : "");
      out.write(">\r\n        <td>\r\n        ");
 if(counter == 0 && size > 1) { 
      out.write("\r\n          <a href=\"workgroup-dataform.jsp?wgID=");
      out.print(wgID);
      out.write("&changePos=true&down=true&index=");
      out.print(counter);
      out.write("\">\r\n          <img src=\"images/arrow_down.gif\" width=\"16\" height=\"16\" border=\"0\"></a>\r\n\r\n          <img src=\"images/blank.gif\" width=\"16\" height=\"16\">\r\n        ");
 } 
      out.write("\r\n\r\n\r\n        ");
 if(counter > 0 && counter < size - 1){ 
      out.write("\r\n         <a href=\"workgroup-dataform.jsp?wgID=");
      out.print(wgID);
      out.write("&changePos=true&down=true&index=");
      out.print(counter);
      out.write("\"><img src=\"images/arrow_down.gif\" width=\"16\" height=\"16\" border=\"0\"></a>\r\n         <a href=\"workgroup-dataform.jsp?wgID=");
      out.print(wgID);
      out.write("&changePos=true&up=true&index=");
      out.print(counter);
      out.write("\">\r\n          <img src=\"images/arrow_up.gif\" width=\"16\" height=\"16\" border=\"0\"></a>\r\n        ");
}
      out.write("\r\n\r\n        ");
 if(counter > 0 && counter ==  size - 1){ 
      out.write("\r\n         <img src=\"images/blank.gif\" width=\"16\" height=\"16\">\r\n          <a href=\"workgroup-dataform.jsp?wgID=");
      out.print(wgID);
      out.write("&changePos=true&up=true&index=");
      out.print(counter);
      out.write("\">\r\n            <img src=\"images/arrow_up.gif\" width=\"16\" height=\"16\" border=\"0\"></a>\r\n        ");
}
      out.write("\r\n\r\n        ");
 if(size == 1){
      out.write("\r\n        &nbsp;\r\n        ");
}
      out.write("\r\n\r\n\r\n        </td>\r\n           ");
 if(!isHidden){ 
      out.write("\r\n            <td><b>");
      out.print( element.getLabel());
      out.write("</b></td>\r\n            <td>");
      out.print( element.getVariable());
      out.write("</td>\r\n            <td>");
      out.print( FormUtils.createAnswers(element) );
      out.write("<br><span class=\"jive-description\">");
      out.print( element.getDescription() != null ? element.getDescription() : "" );
      out.write("</span></td>\r\n\r\n            ");

                if(element.isRequired()){
            
      out.write("\r\n            <td><a href=\"workgroup-dataform.jsp?wgID=");
      out.print(wgID );
      out.write("&notRequired=false&index=");
      out.print(counter );
      out.write("\">Required</a></td>\r\n            ");
 } else { 
      out.write("\r\n            <td><a href=\"workgroup-dataform.jsp?wgID=");
      out.print(wgID );
      out.write("&notRequired=true&index=");
      out.print(counter );
      out.write("\">Not Required</a></td>\r\n            ");
 }
      out.write("\r\n\r\n            <td>\r\n                <a href=\"create-element.jsp?wgID=");
      out.print( wgID);
      out.write("&edit=true&index=");
      out.print( counter);
      out.write("\"><img src=\"images/edit-16x16.gif\" border=\"0\" /></a></td>\r\n           ");
 } else {
            if(element.getAnswerType() == WorkgroupForm.FormEnum.hidden){
            String variableName = element.getVariable();
            String type = "";
            if(variableName.startsWith("cookie_")){
                type = "Cookie";
            }
            else if(variableName.startsWith("header_")){
                type = "HTTP Header";
            }
            else if(variableName.startsWith("session_")){
                type = "Session Attribute";
            }

            int indexOf = variableName.indexOf("_");
            String varName = variableName.substring(indexOf + 1);
            
      out.write("\r\n              <td colspan=\"5\" align=\"center\">{Hidden variable - Type: ");
      out.print(type);
      out.write(". Checks for variable:");
      out.print( varName);
      out.write("}</td>\r\n\r\n           ");
 }}  
      out.write("\r\n\r\n            <td> <a href=\"workgroup-dataform.jsp?wgID=");
      out.print(wgID);
      out.write("&delete=true&index=");
      out.print(counter);
      out.write("\"><img src=\"images/delete-16x16.gif\" border=\"0\"></a></td>\r\n        </tr>\r\n\r\n        ");
 counter++; }
      out.write("\r\n\r\n    <tr>\r\n\r\n    <td colspan=\"1\"><input type=\"button\" name=\"create\" value=\"Add Field\" onclick=\"window.location.href='create-element.jsp?wgID=");
      out.print( wgID);
      out.write("'\"></td>\r\n        <td colspan=\"6\"><input type=\"submit\" name=\"save\" value=\"Save Changes\"></td>\r\n\r\n        <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\">\r\n    </tr>\r\n          </form>\r\n   </table>\r\n    <br/><br/>\r\n    <div style=\"width:600px\">\r\n    <p>Spark Fastpath has assigned certain functionality to certain form element names. Please review before building your list.\r\n    For a more customized approach via code, you may want to implement your own MetadataProvider. Please see the Web Chat Client api\r\n    for more information.\r\n    </p>\r\n</div>\r\n    <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" width=\"600\">\r\n    <tr>\r\n    <th nowrap>Form Name</th><th>Description</th>\r\n    </tr>\r\n    <tr>\r\n    <td>username</td><td>Use this variable to allow a user to specify their own user name to use for this chat session.</td>\r\n    </tr>\r\n    <tr>\r\n    <td>password</td><td>Use this variable in conjunction with 'username' to allow a user to login to their account.</td>\r\n    </tr>\r\n    <tr>\r\n    <td>email</td><td>Use this form element name to allow a user to specify their email address.</td>\r\n    </tr>\r\n     <tr>\r\n    <td>question</td><td>Use this form element name to set the question a user asks before entering a queue.</td>\r\n");
      out.write("    </tr>\r\n     <tr>\r\n    <td>agent</td><td>Use this form element name to specify a particular agent to initially route to in the workgroup. If the agent is not available, the failover is to route to others in the workgroup.</td>\r\n    </tr>\r\n    </table>\r\n\r\n</body>\r\n</html>");
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
