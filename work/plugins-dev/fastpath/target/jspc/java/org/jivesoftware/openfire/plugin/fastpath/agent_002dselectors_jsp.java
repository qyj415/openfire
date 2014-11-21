package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.beans.*;
import org.jivesoftware.xmpp.workgroup.dispatcher.AgentSelector;
import org.jivesoftware.util.ParamUtils;
import java.util.List;
import org.jivesoftware.util.StringUtils;
import org.jivesoftware.openfire.fastpath.util.WorkgroupUtils;
import org.jivesoftware.util.ClassUtils;
import org.jivesoftware.util.Log;

public final class agent_002dselectors_jsp extends org.apache.jasper.runtime.HttpJspBase
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
 try { 
      out.write("\r\n\r\n");
 // Get parameters
    boolean addAlgorithm = ParamUtils.getBooleanParameter(request, "addAlgorithm");
    String newClassname = ParamUtils.getParameter(request, "newClassname");

    String error = "";
    String errorMessage = ParamUtils.getParameter(request, "errorMessage");

    // Add a new interceptor to the list of installable algorithms
    if (addAlgorithm) {
        try {
            if (newClassname != null) {
                // Load the specified class, make sure it's an insance of the interceptor class:
                Class c = ClassUtils.forName(newClassname.trim());
                Object obj = c.newInstance();
                if (obj instanceof AgentSelector) {
                    WorkgroupUtils.addAgentSelectorClass(c);
                }
                else {
                    error = newClassname.trim() + " is not an AgentSelector";
                }
            }
            else {
                error = "You must specify an AgentSelector class to load.";
            }
        }
        catch (ClassNotFoundException cnfe) {
            error = newClassname.trim() + " is not a valid classname";
        }

        catch (InstantiationException ie) {
            error = newClassname.trim() + " must have a valid constructor";
        }
        catch (Exception e) {
            Log.error(e);
            error = "Could not load class " + newClassname.trim();
        }
        String redirect = "agent-selectors.jsp?errorMessage=" + error;
        response.sendRedirect(redirect);
        return;
    }


      out.write("\r\n\r\n<html>\r\n    <head>\r\n        <title>Dispatcher Settings</title>\r\n        <meta name=\"pageID\" content=\"member-selectors\"/>\r\n        <!--<meta name=\"helpPage\" content=\"configure_global_dispatcher_settings.html\"/>-->\r\n\r\n        <script language=\"JavaScript\" type=\"text/javascript\">\r\n        var algorithmInfo = new Array(\r\n");
	    int i = 0;
        List<AgentSelector> availableAgentSelectors = WorkgroupUtils.getAvailableAgentSelectors();
        for(AgentSelector agentSelector : availableAgentSelectors){

            try {
                BeanDescriptor descriptor = (Introspector.getBeanInfo(agentSelector.getClass())).getBeanDescriptor();

      out.write("\r\n            new Array(\r\n                \"");
      out.print( descriptor.getBeanClass().getName() );
      out.write("\",\r\n                \"");
      out.print( descriptor.getValue("version") );
      out.write("\",\r\n                \"");
      out.print( descriptor.getValue("author") );
      out.write("\",\r\n                \"");
      out.print( StringUtils.replace(descriptor.getShortDescription(), "\"", "\\\"") );
      out.write("\"\r\n            )\r\n");
          if ((availableAgentSelectors.size() - i) > 1) { 
      out.write("\r\n                ,\r\n");
	        }
                } catch (Exception e) {}
                 i++;
            }

      out.write("\r\n        );\r\n        function properties(theForm) {\r\n            var className = theForm.algorithms.options[theForm.algorithms.selectedIndex].value;\r\n            var selected = 0;\r\n            for (selected=0; selected<algorithmInfo.length; selected++) {\r\n                if (algorithmInfo[selected][0] == className) {\r\n                    var version = algorithmInfo[selected][1];\r\n                    var author = algorithmInfo[selected][2];\r\n                    var description = algorithmInfo[selected][3];\r\n                    theForm.version.value = ((version==\"null\")?\"\":version);\r\n                    theForm.author.value = ((author==\"null\")?\"\":author);\r\n                    theForm.description.value = ((description==\"null\")?\"\":description);\r\n                    break;\r\n                }\r\n            }\r\n        }\r\n        </script>\r\n    </head>\r\n    <body>\r\n\r\n<span>\r\n\r\n<p>Below is a list of available algorithms for choosing the best agent in a queue that may\r\nreceive an offer. Use the form below to install new algorithms.\r\n");
      out.write("</p>\r\n\r\n</span>\r\n\r\n<p>\r\n\r\n");
  // Print out a message if one exists
    String oneTimeMessage = errorMessage;
    if (oneTimeMessage != null) {

      out.write("\r\n    <font size=\"-1\" color=\"#ff0000\">\r\n    <p><i>");
      out.print( oneTimeMessage );
      out.write("</i></p>\r\n\r\n");
  }

      out.write("\r\n\r\n<p>\r\n\r\n<form action=\"agent-selectors.jsp\" method=\"post\">\r\n\r\n<span class=\"jive-install-interceptor\">\r\n\r\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n<tr><td>\r\n    <b>Available Algorithms</b>\r\n    </td>\r\n    <td>\r\n    <a href=\"#\" onclick=\"helpwin('algorithms','install_interceptor');return false;\"\r\n     title=\"Click for help\"\r\n     ><img src=\"images/help-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" hspace=\"8\" alt=\"\" /></a>\r\n    </td>\r\n</tr>\r\n</table><br>\r\n\r\n<ul>\r\n\t<table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"1%\">\r\n    <tr><td>\r\n        <table cellpadding=\"4\" cellspacing=\"1\" border=\"0\" width=\"100%\">\r\n        <tr bgcolor=\"#eeeeee\">\r\n            <td align=\"center\">\r\n                <font size=\"-2\" face=\"verdana\"><b>AVAILABLE ALGORITHMS</b></font>\r\n            </td>\r\n        </tr>\r\n        <tr bgcolor=\"#ffffff\">\r\n            <td>\r\n                <table cellpadding=\"1\" cellspacing=\"0\" border=\"0\">\r\n                <tr>\r\n                    <td width=\"48%\" valign=\"top\">\r\n                        <select size=\"8\" name=\"algorithms\" onchange=\"properties(this.form);\">\r\n");
      out.write("                        ");
  for(AgentSelector agentSelector : WorkgroupUtils.getAvailableAgentSelectors()) {
                            BeanDescriptor descriptor = (Introspector.getBeanInfo(agentSelector.getClass())).getBeanDescriptor();
                        
      out.write("\r\n                            <option value=\"");
      out.print( descriptor.getBeanClass().getName() );
      out.write("\"\r\n                             >");
      out.print( descriptor.getDisplayName() );
      out.write("\r\n\r\n                        ");
  } 
      out.write("\r\n                        </select>\r\n                        <br>\r\n                    </td>\r\n                    <td width=\"2%\"><img src=\"images/blank.gif\" width=\"5\" height=\"1\" border=\"0\" alt=\"\" /></td>\r\n                    <td width=\"48%\" valign=\"top\">\r\n\r\n                        <table cellpadding=\"2\" cellspacing=\"0\" border=\"0\" width=\"100%\">\r\n                        <tr>\r\n                            <td><font size=\"-2\">VERSION</font></td>\r\n                            <td><input type=\"text\" size=\"20\" name=\"version\" style=\"width:100%\"></td>\r\n                        </tr>\r\n                        <tr>\r\n                            <td><font size=\"-2\">AUTHOR</font></td>\r\n                            <td><input type=\"text\" size=\"20\" name=\"author\" style=\"width:100%\"></td>\r\n                        </tr>\r\n                        <tr>\r\n                            <td valign=\"top\"><font size=\"-2\">DESCRIPTION</font></td>\r\n                            <td><textarea name=\"description\" cols=\"20\" rows=\"5\" wrap=\"virtual\"></textarea></td>\r\n");
      out.write("                        </tr>\r\n                        </table>\r\n\r\n                    </td>\r\n                </tr>\r\n                </table>\r\n            </td>\r\n        </tr>\r\n        </table>\r\n    </td></tr>\r\n    </table>\r\n</ul>\r\n\r\n</span>\r\n\r\n</form>\r\n\r\n<form action=\"agent-selectors.jsp\">\r\n<input type=\"hidden\" name=\"addAlgorithm\" value=\"true\">\r\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n<tr><td>\r\n    <b>Add Algorithm Class</b>\r\n    </td>\r\n    <td>\r\n    <a href=\"#\" onclick=\"helpwin('algorithms','add_algorithm_class');return false;\"\r\n     title=\"Click for help\"\r\n     ><img src=\"images/help-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" hspace=\"8\" alt=\"\" /></a>\r\n    </td>\r\n</tr>\r\n</table><br>\r\n<ul>\r\n    <table cellpadding=\"2\" cellspacing=\"0\" border=\"0\">\r\n    <tr>\r\n    \t<td>Class Name:</td>\r\n    \t<td><input type=\"text\" name=\"newClassname\" value=\"\" size=\"30\" maxlength=\"100\"></td>\r\n    \t<td><input type=\"submit\" value=\"Add Algorithm\"></td>\r\n    </tr>\r\n    </table>\r\n</ul>\r\n</form>\r\n\r\n<p>\r\n\r\n\r\n</body>\r\n</html>\r\n");
 } catch(Exception ex){ex.printStackTrace(); } 
      out.write("\r\n\r\n");
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
