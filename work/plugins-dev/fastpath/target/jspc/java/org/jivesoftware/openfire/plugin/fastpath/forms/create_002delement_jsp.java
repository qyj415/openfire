package org.jivesoftware.openfire.plugin.fastpath.forms;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import java.util.StringTokenizer;
import java.util.List;
import java.util.Iterator;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.openfire.fastpath.dataforms.FormManager;
import org.jivesoftware.openfire.fastpath.dataforms.WorkgroupForm;
import org.jivesoftware.openfire.fastpath.dataforms.FormElement;

public final class create_002delement_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


  private String getOption(WorkgroupForm.FormEnum form, String label, String answerType){
     String selected = form.toString().equals(answerType) ? "selected" : "";
     if(!ModelUtil.hasLength(answerType)){
         if(form == WorkgroupForm.FormEnum.textfield){
             selected = "selected";
         }
     }
     String returnStr = "<option value=\""+form.toString()+"\" "+selected+">"+label+"</option>";
     return returnStr;
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

    boolean createElement = ParamUtils.getParameter(request, "createElement") != null;
    boolean edit = ParamUtils.getBooleanParameter(request, "edit", false);

    String label = ParamUtils.getParameter(request, "label");
    String variable = ParamUtils.getParameter(request, "variable");
    String answerType = ParamUtils.getParameter(request, "answer");
    boolean required = ParamUtils.getBooleanParameter(request, "required");
    String listItems = ParamUtils.getParameter(request, "items");
    String description = ParamUtils.getParameter(request, "description");

    boolean saveEdit = ParamUtils.getBooleanParameter(request, "saveEdit");
    int index = ParamUtils.getIntParameter(request, "index", -1);

    boolean hasCookie = false;

    if (createElement) {
        // Create Element
        FormElement formElement = new FormElement();
        if (saveEdit) {
            int saveIndex = ParamUtils.getIntParameter(request, "saveIndex", -1);
            formElement = workgroupForm.getFormElementAt(saveIndex);
            formElement.getAnswers().removeAll(formElement.getAnswers());
        }
        formElement.setLabel(label);
        formElement.setAnswerType(answerType);
        formElement.setRequired(required);
        formElement.setVisible(true);
        formElement.setVariable(variable);
        formElement.setDescription(description);

        if (listItems != null) {
            StringTokenizer tkn = new StringTokenizer(listItems, "\n");
            while (tkn.hasMoreTokens()) {
                String value = tkn.nextToken();
                value = value.replace('\r', ' ');
                formElement.getAnswers().add(value.trim());
            }
        }

        boolean prepopulate = ParamUtils.getBooleanParameter(request, "prepopulate");
        if (prepopulate) {
            String tag = "setCookie_" + variable;
            boolean containsTag = workgroupForm.containsHiddenTag(tag);
            if (!containsTag) {
                // Add new tag
                FormElement el = new FormElement();
                el.setAnswerType(WorkgroupForm.FormEnum.hidden);
                el.setVariable(tag);
                workgroupForm.addHiddenVar(el);
            }
        }
        else {
            String tag = "setCookie_" + variable;
            workgroupForm.removeHiddenVar(tag);
        }

        if (!saveEdit) {
            workgroupForm.addFormElement(formElement);
        }

        workgroup = workgroupManager.getWorkgroup(new JID(wgID));

        response.sendRedirect("workgroup-dataform.jsp?wgID=" + wgID);
        return;
    }

    String title = "Create Form Element";

    if (edit) {
        if (index != -1) {
            FormElement elem = workgroupForm.getFormElementAt(index);
            label = elem.getLabel();
            variable = elem.getVariable();
            description = elem.getDescription();
            answerType = elem.getAnswerType().toString();
            required = elem.isRequired();

            String tag = "setCookie_" + variable;
            hasCookie = workgroupForm.containsHiddenTag(tag);

            StringBuffer buf = new StringBuffer();
            List answers = elem.getAnswers();
            Iterator iter = answers.iterator();
            while (iter.hasNext()) {
                buf.append((String)iter.next());
                buf.append("\n");
            }
            listItems = buf.toString();
        }
        title = "Edit Form Element";
    }

    if (label == null) {
        label = "";
    }

    if (variable == null) {
        variable = "";
    }

    if (description == null) {
        description = "";
    }

    if (answerType == null) {
        answerType = "";
    }

    if (listItems == null) {
        listItems = "";
    }

      out.write("\r\n<html>\r\n    <head>\r\n        <title>");
      out.print( title );
      out.write("</title>\r\n        <meta name=\"subPageID\" content=\"workgroup-forms\"/>\r\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\r\n        <!--<meta name=\"helpPage\" content=\"create_a_custom_form_field.html\"/>-->\r\n\r\n        <script>\r\n         function Jtrim(st) {\r\n            var len = st.length;\r\n            var begin = 0, end = len - 1;\r\n            while (st.charAt(begin) == \" \" && begin < len) {\r\n                begin++;\r\n            }\r\n            while (st.charAt(end) == \" \" && end > begin) {\r\n                end--;\r\n            }\r\n            return st.substring(begin, end + 1);\r\n         }\r\n\r\n         function validateForm(){\r\n             if(!Jtrim(document.f.label.value)){\r\n               alert(\"Please supply a label for this form element.\");\r\n               document.f.label.focus();\r\n               return false;\r\n             }\r\n\r\n             if(!Jtrim(document.f.variable.value)){\r\n               alert(\"Please supply a variable for this form element.\");\r\n               document.f.variable.focus();\r\n               return false;\r\n             }\r\n\r\n              if(document.f.variable.value.indexOf(\" \") != -1){\r\n               alert(\"Please supply a valid variable name for this form element.\");\r\n");
      out.write("               document.f.variable.focus();\r\n               return false;\r\n             }\r\n\r\n             var v = document.f.answer.value;\r\n             if(v == '");
      out.print( WorkgroupForm.FormEnum.dropdown_box);
      out.write("' || v == '");
      out.print( WorkgroupForm.FormEnum.radio_button);
      out.write("' || v == '");
      out.print( WorkgroupForm.FormEnum.checkbox);
      out.write("'){\r\n                if(!Jtrim(document.f.items.value)){\r\n                  alert(\"Please supply at least one item for a multi choice  element.\");\r\n                  return false;\r\n                }\r\n             }\r\n\r\n             return true;\r\n         }\r\n        </script>\r\n    </head>\r\n    <body>\r\n\r\n    <form name=\"f\" action=\"create-element.jsp\" method=\"post\" onsubmit=\"return validateForm(); return false;\"  >\r\n        <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" width=\"600\">\r\n        <tr>\r\n            <th colspan=\"2\">New Form Element</th>\r\n        </tr>\r\n        <tr valign=\"top\">\r\n            <td>Variable Label:*</td><td><input type=\"text\" size=\"60\" name=\"label\" value=\"");
      out.print( label );
      out.write("\">\r\n            <br/><span class=\"jive-description\">The text to display on the HTML Form. e.g. Product:</span>\r\n            </td>\r\n        </tr>\r\n       <tr valign=\"top\">\r\n            <td>Variable Name:*</td><td><input type=\"text\" size=\"60\" name=\"variable\" value=\"");
      out.print( variable);
      out.write("\">\r\n            <br/><span class=\"jive-description\">The name of the html form element. e.g. product_name</span>\r\n            </td>\r\n        </tr>\r\n       <tr valign=\"top\">\r\n            <td>Description:</td><td><input type=\"text\" size=\"60\" name=\"description\" value=\"");
      out.print( description );
      out.write("\">\r\n             <br/><span class=\"jive-description\">A description of this form element.</span>\r\n            </td>\r\n        </tr>\r\n       <tr valign=\"top\">\r\n        <td>Answer Type:*</td>\r\n        <td>\r\n            <select name=\"answer\">\r\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.dropdown_box, "Dropdown Box", answerType) );
      out.write("\r\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.checkbox, "Checkbox", answerType) );
      out.write("\r\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.radio_button, "Radio Button", answerType) );
      out.write("\r\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.textfield, "TextField", answerType) );
      out.write("\r\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.textarea, "TextArea", answerType) );
      out.write("\r\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.password, "Password", answerType) );
      out.write("\r\n            </select>\r\n        </td>\r\n        </tr>\r\n        <tr>\r\n            <td>&nbsp;</td>\r\n            <td><input type=\"checkbox\" name=\"required\" ");
      out.print( required ? "checked" : "");
      out.write(">&nbsp;<b>Required</b></td>\r\n        </tr>\r\n        <tr>\r\n        <td colspan=\"2\"><input type=\"checkbox\" name=\"prepopulate\" ");
      out.print( hasCookie ? "checked" : "");
      out.write(">Populate with user's previous choice.</td>\r\n        </tr>\r\n        </table>\r\n\r\n        <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" width=\"600\">\r\n        <tr>\r\n            <th colspan=\"2\">Add List Items</th>\r\n        </tr>\r\n        <tr>\r\n            <td colspan=\"2\"><i>Hit return after each list item.</i></td>\r\n        </tr>\r\n        <tr>\r\n        <td colspan=\"2\">\r\n            <textarea name=\"items\" cols=\"40\" rows=\"3\">");
      out.print( listItems );
      out.write("</textarea>\r\n        </td>\r\n        </tr>\r\n        <tr>\r\n           <td><input type=\"submit\" name=\"createElement\" value=\"Update\">&nbsp;\r\n           <input type=\"button\" name=\"cancel\" value=\"Cancel\" onclick=\"javascript:window.location.href='workgroup-dataform.jsp?wgID=");
      out.print(wgID);
      out.write("'\"></td>\r\n        </tr>\r\n        </table>\r\n        <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\">\r\n        ");
 if(edit) { 
      out.write("\r\n        <input type=\"hidden\" name=\"saveEdit\" value=\"true\" />\r\n        <input type=\"hidden\" name=\"saveIndex\" value=\"");
      out.print( index );
      out.write("\" />\r\n        ");
 } 
      out.write("\r\n    </form>\r\n</body>\r\n</html>\r\n\r\n");
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
