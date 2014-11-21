package org.jivesoftware.openfire.plugin.packetFilter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.PacketFilterConstants;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.component.InternalComponentManager;
import org.jivesoftware.openfire.group.Group;
import org.jivesoftware.openfire.plugin.component.ComponentList;
import org.jivesoftware.openfire.plugin.rules.*;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.util.ParamUtils;
import org.xmpp.component.Component;
import org.xmpp.packet.JID;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class rule_002dedit_002dform_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

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

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');

    webManager.init(request, response, session, application, out);
    Collection<Group> groups = webManager.getGroupManager().getGroups();

     ComponentList cList = ComponentList.getInstance();

    Collection<String> components = cList.getComponentDomains();

    RuleManager rm = new RuleManagerProxy();
    Rule rule = null;
    //Get Action
    boolean editSave = request.getParameter("editSave") != null;
    boolean edit = request.getParameter("edit") != null;
    boolean cancel = request.getParameter("cancel") != null;


    boolean isDestOther = false;
    boolean isDestGroup = false;
    boolean isDestUser = false;
    boolean isDestAny = false;
    boolean isDestComponent = false;

    boolean isSourceOther = false;
    boolean isSourceGroup = false;
    boolean isSourceUser = false;
    boolean isSourceAny = false;
    boolean isSourceComponent = false;

    //Get data
    String packetAction = ParamUtils.getParameter(request, "packetAction");
    String disable = ParamUtils.getParameter(request, "disable");
    String packetType = ParamUtils.getParameter(request, "packetType");
    String source = ParamUtils.getParameter(request, "source");
    String destination = ParamUtils.getParameter(request, "destination");
    String log = ParamUtils.getParameter(request, "log");
    String description = ParamUtils.getParameter(request, "description");
    String order = ParamUtils.getParameter(request, "order");


    Collection<String> userList = UserManager.getInstance().getUsernames();
    String serverName = XMPPServer.getInstance().getServerInfo().getXMPPDomain();

    Map<String, String> errors = new HashMap<String, String>();

    String sourceJID = "";
    String destJID = "";


    if (cancel) {
        response.sendRedirect("pf-main.jsp");
        return;
    }
    if (edit) {
        rule = rm.getRuleById(new Integer(request.getParameter("edit")));
        disable = rule.isDisabled().toString();
        packetType = rule.getPackeType().toString();
        source = rule.getSource();
        destination = rule.getDestination();
        log = rule.doLog().toString();
        description = rule.getDescription();

        Rule.SourceDestType destType = rule.getDestType();
        Rule.SourceDestType sourceType = rule.getSourceType();

        destJID = destination;
        sourceJID = source;

        if (destType == Rule.SourceDestType.Any) {
            isDestAny = true;
        } else if (destType == Rule.SourceDestType.Group) {
            isDestGroup = true;
        } else if (destType == Rule.SourceDestType.Component) {
            isDestComponent = true;
        } else if (destType == Rule.SourceDestType.User) {
            isDestUser = true;
        } else if (destType == Rule.SourceDestType.Other) {
            isDestOther = true;
        }

        if (sourceType == Rule.SourceDestType.Any) {
            isSourceAny = true;
        } else if (sourceType == Rule.SourceDestType.Group) {
            isSourceGroup = true;
        } else if (sourceType == Rule.SourceDestType.Component) {
            isSourceComponent = true;
        } else if (sourceType == Rule.SourceDestType.User) {
            isSourceUser = true;
        } else if (sourceType == Rule.SourceDestType.Other) {
            isSourceOther = true;
        }

    }
    if (editSave) {

        //Destination simple case any
        if (destination.equals(Rule.SourceDestType.Any.toString())) isDestAny = true;
        else if (destination.equals(Rule.SourceDestType.Group.toString())) isDestGroup = true;
        else if (destination.equals(Rule.SourceDestType.User.toString())) isDestUser = true;
        else if (destination.equals(Rule.SourceDestType.Other.toString())) isDestOther = true;
        else if (destination.equals(Rule.SourceDestType.Component.toString())) isDestComponent = true;

        //Do the same thing as above for source. I'm repeating myself a little but
        //it will make things much easier to read.
        if (source.equals(Rule.SourceDestType.Any.toString())) isSourceAny = true;
        else if (source.equals(Rule.SourceDestType.Group.toString())) isSourceGroup = true;
        else if (source.equals(Rule.SourceDestType.User.toString())) isSourceUser = true;
        else if (source.equals(Rule.SourceDestType.Other.toString())) isSourceOther = true;
        else if (source.equals(Rule.SourceDestType.Component.toString())) isSourceComponent = true;

        if (packetAction.equals("Pass")) {
            rule = new Pass();
        } else if (packetAction.equals("Reject")) {
            rule = new Reject();
        } else if (packetAction.equals("Drop")) {
            rule = new Drop();
        } 

        if (rule != null) {
            rule.setDescription(description);
            rule.setPacketType(Rule.PacketType.valueOf(packetType));
            if (source.equals(Rule.SourceDestType.Any.toString())) {
                rule.setSource(source);
                rule.setSourceType(Rule.SourceDestType.Any);
            } else if (source.equals(Rule.SourceDestType.Other.toString())) {
                sourceJID = ParamUtils.getParameter(request, "sourceOtherJID");
                if (sourceJID == null || !(sourceJID.length() > 0)) {
                    sourceJID = "";
                    errors.put("sourceOther", "");
                }
                rule.setSource(sourceJID);
                rule.setSourceType(Rule.SourceDestType.Other);
            } else if (source.equals(Rule.SourceDestType.User.toString())) {
                sourceJID = ParamUtils.getParameter(request, "sourceUserJID");
                rule.setSource(sourceJID);
                rule.setSourceType(Rule.SourceDestType.User);
            } else if (source.equals(Rule.SourceDestType.Group.toString())) {
                sourceJID = ParamUtils.getParameter(request, "sourceGroupJID");
                rule.setSource(sourceJID);
                rule.setSourceType(Rule.SourceDestType.Group);
            } else if (source.equals(Rule.SourceDestType.Component.toString())) {
                sourceJID = ParamUtils.getParameter(request, "sourceComponentJID");
                rule.setSource(sourceJID);
                rule.setSourceType(Rule.SourceDestType.Component);
            }


            if (destination.equals(Rule.SourceDestType.Any.toString())) {
                rule.setDestination(destination);
                rule.setDestType(Rule.SourceDestType.Any);
            } else if (destination.equals(Rule.SourceDestType.Other.toString())) {
                destJID = ParamUtils.getParameter(request, "destOtherJID");
                if (destJID == null || !(destJID.length() > 0)) {
                    destJID = "";
                    errors.put("destOther", "");
                }
                rule.setDestination(destJID);
                rule.setDestType(Rule.SourceDestType.Other);
            } else if (destination.equals(Rule.SourceDestType.User.toString())) {
                destJID = ParamUtils.getParameter(request, "destUserJID");
                rule.setDestination(destJID);
                rule.setDestType(Rule.SourceDestType.User);
            } else if (destination.equals(Rule.SourceDestType.Group.toString())) {
                destJID = ParamUtils.getParameter(request, "destGroupJID");
                rule.setDestination(destJID);
                rule.setDestType(Rule.SourceDestType.Group);
            } else if (destination.equals(Rule.SourceDestType.Component.toString())) {
                destJID = ParamUtils.getParameter(request, "destComponentJID");
                rule.setDestination(destJID);
                rule.setDestType(Rule.SourceDestType.Component);
            }


            rule.doLog(new Boolean(log).booleanValue());
            rule.isDisabled(new Boolean(disable).booleanValue());

            rule.setRuleId(request.getParameter("ruleId"));
            rule.setOrder(new Integer(order));
            if (errors.isEmpty()) {
               if (rule.getSourceType() == Rule.SourceDestType.User ||
                       rule.getSourceType() == Rule.SourceDestType.Other) {
                    rule.setSource(rule.getSource().toLowerCase());
               }
               else {
                  rule.setSource(rule.getSource());
               }

               if (rule.getDestType() == Rule.SourceDestType.User ||
                       rule.getDestType() == Rule.SourceDestType.Other) {
                rule.setDestination(rule.getDestination().toLowerCase());
               }
               else {
                  rule.setDestination(rule.getDestination());
               }
                rm.updateRule(rule);
                response.sendRedirect("pf-main.jsp");
            }

        }


    }

      out.write("\n<html>\n<head>\n    <title>\n        ");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("\n    </title>\n    <meta name=\"pageID\" content=\"packetFilter\"/>\n    <script language=\"JavaScript\" type=\"text/javascript\" src=\"scripts/packetfilter.js\"></script>\n</head>\n<body>\n\n\n");
 if (!errors.isEmpty()) { 
      out.write("\n\n<div class=\"jive-error\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n        <tbody>\n            <tr>\n                <td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"/></td>\n                <td class=\"jive-icon-label\">\n\n                    ");
 if (errors.get("sourceOther") != null) { 
      out.write("\n                    ");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\n                    ");
 } else if (errors.get("destOther") != null) { 
      out.write("\n                    ");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\n                    ");
 } 
      out.write("\n                </td>\n            </tr>\n        </tbody>\n    </table>\n</div>\n<br>\n");
 } 
      out.write("\n<form action=\"rule-edit-form.jsp?editSave\" method=\"get\">\n<input type=\"hidden\" name=\"ruleId\" value=\"");
      out.print(rule.getRuleId());
      out.write("\">\n<input type=\"hidden\" name=\"order\" value=\"");
      out.print(rule.getOrder());
      out.write("\">\n\n<div class=\"jive-table\">\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n<tbody>\n<tr class=\"jive-even\">\n    <td>Action</td>\n    <td>\n        <select label=\"packetAction\" name=\"packetAction\">\n\n            ");
 Rule.Action[] actions = Rule.Action.values();
                            for (int i = 0; i < actions.length; i++) {
                                String action = actions[i].toString();
                        
      out.write("\n                        <option value=\"");
      out.print(action);
      out.write('"');
      out.write(' ');
if ((packetAction != null && packetAction.equals(action))||rule.getDisplayName().equals(action) ) {
      out.write("\n                                selected");
}
      out.write("\n                                >\n                            ");
      out.print(action);
      out.write("\n                        </option>\n                        ");
}
      out.write("\n\n        </select>\n    </td>\n</tr>\n<tr class=\"jive-odd\">\n    <td>Disable</td>\n    <td><input type=\"checkbox\" name=\"disable\" value=\"true\"\n    ");
if (rule.isDisabled()) {
      out.write("\n               checked\n    ");
}
      out.write("\n            ></td>\n\n</tr>\n<tr class=\"jive-even\">\n    <td>Packet Type</td>\n    <td>\n        <select label=\"packetType\" name=\"packetType\">\n            ");

                Rule.PacketType[] packetTypes = Rule.PacketType.values();
                for (int i = 0; i < packetTypes.length; i++) {

            
      out.write("\n\n            <option value=\"");
      out.print(packetTypes[i].toString());
      out.write("\"\n                    ");
if (packetType != null && packetType.equals(packetTypes[i].toString())) {
      out.write("\n                    selected ");
}
      out.write("\n                    >\n                ");
      out.print(packetTypes[i].getDisplayName());
      out.write("\n            </option>\n            ");
 } 
      out.write("\n\n        </select>\n    </td>\n\n</tr>\n<tr class=\"jive-odd\">\n    <td>From</td>\n    <td>                                                                                                              \n        <select id=\"source\" name=\"source\" onChange=\"ShowSourceField('source')\">\n            <option value=\"Any\" ");
if (isSourceAny) {
      out.write(" selected ");
}
      out.write(">Any</option>\n            <option value=\"User\" ");
if (isSourceUser) {
      out.write(" selected ");
}
      out.write(">User</option>\n            <option value=\"Group\" ");
if (isSourceGroup) {
      out.write(" selected ");
}
      out.write(">Group</option>\n            <option value=\"Other\" ");
if (isSourceOther) {
      out.write(" selected ");
}
      out.write(">Other</option>\n            <option value=\"Component\" ");
if (isSourceComponent) {
      out.write(" selected ");
}
      out.write(">Component</option>\n        </select>\n    </td>\n</tr>\n<tr class=\"jive-odd\" name=\"SourceOther\" id=\"SourceOther\" ");
if (!isSourceOther) {
      out.write(" style=\"display:none;\"");
}
      out.write(">\n    <td>\n        Other JID\n    </td>\n    <td>\n        <input type=\"text\" name=\"sourceOtherJID\" id=\"sourceOtherJID\" ");
if (isSourceOther) {
      out.write("\n               value=\"");
      out.print(sourceJID);
      out.write('"');
}
      out.write("></input>\n    </td>\n</tr>\n\n<tr class=\"jive-odd\" name=\"SourceGroup\" id=\"SourceGroup\" ");
 if (!isSourceGroup) {
      out.write(" style=\"display:none;\"");
}
      out.write(">\n    <td>\n        Source Group\n    </td>\n    <td>\n        <select id=\"sourceGroupJID\" name=\"sourceGroupJID\">\n            ");
 for (Group group : groups) {
      out.write("\n\n            <option value=\"");
      out.print(group.getName());
      out.write("\"\n                    ");
if (isSourceGroup && source.equals(group.getName())) {
      out.write(" selected");
}
      out.write("\n                    >");
      out.print(group.getName());
      out.write("\n            </option>\n            ");
}
      out.write("\n            <option value=\"");
      out.print(PacketFilterConstants.ANY_GROUP);
      out.write('"');
      out.write(' ');
if (isSourceGroup && source.equals(PacketFilterConstants.ANY_GROUP)) {
      out.write("\n                    selected");
}
      out.write("\n                    >");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\n            </option>\n        </select>\n    </td>\n</tr>\n\n<tr class=\"jive-odd\" name=\"SourceUser\" id=\"SourceUser\" ");
 if (!isSourceUser) {
      out.write(" style=\"display:none;\"");
}
      out.write(">\n    <td>\n        Source User\n    </td>\n    <td>\n        <select id=\"sourceUserJID\" name=\"sourceUserJID\">\n            ");
 for (String userName : userList) {
      out.write("\n            <option value=\"");
      out.print(userName+"@"+serverName);
      out.write("\"\n                    ");
 if (isSourceUser && source.equals(userName + "@" + serverName)) {
      out.write("\n                    selected\n                    ");
}
      out.write("\n                    >");
      out.print(userName + "@" + serverName);
      out.write("\n            </option>\n            ");
}
      out.write("\n        </select>\n    </td>\n</tr>\n\n<tr class=\"jive-odd\" name=\"SourceComponent\" id=\"SourceComponent\" ");
if (!isSourceComponent) {
      out.write("\n    style=\"display:none;\"");
}
      out.write("\n        >\n    <td>\n        Component\n    </td>\n    <td>\n        <select id=\"sourceComponentJID\" name=\"sourceComponentJID\">\n            ");
 for (String component : components) {
            if (component != null && cList.getComponentName(component) != null) {
      out.write("\n            <option value=\"");
      out.print(component);
      out.write("\"\n                    ");
if (sourceJID != null && sourceJID.equals(component)) {
      out.write("\n                    selected");
}
      out.write(">\n                ");
      out.print(cList.getComponentName(component));
      out.write("\n            </option>\n            ");
 }
            }
      out.write("\n        </select>\n    </td>\n</tr>\n\n<tr class=\"jive-even\">\n    <td>To</td>\n    <td>\n        <select name=\"destination\" id=\"destination\" onChange=\"ShowDestinationField('destination')\">\n            <option value=\"Any\" ");
if (isDestAny) {
      out.write(" selected ");
}
      out.write(">Any</option>\n            <option value=\"User\" ");
if (isDestUser) {
      out.write(" selected ");
}
      out.write(">User</option>\n            <option value=\"Group\" ");
if (isDestGroup) {
      out.write(" selected ");
}
      out.write(">Group</option>\n            <option value=\"Other\" ");
if (isDestOther) {
      out.write(" selected ");
}
      out.write(">Other</option>\n            <option value=\"Component\" ");
 if (isDestComponent) { 
      out.write(" selected ");
}
      out.write(">Component</option>\n        </select>\n    </td>\n\n</tr>\n\n<tr class=\"jive-even\" name=\"DestOther\" id=\"DestOther\" ");
if (!isDestOther) {
      out.write(" style=\"display:none;\"");
}
      out.write(">\n    <td>\n        Other JID\n    </td>\n    <td>\n        <input type=\"text\" name=\"destOtherJID\" id=\"destOtherJID\"\n                ");
 if (isDestOther) {
      out.write(" value=\"");
      out.print(destJID);
      out.write('"');
}
      out.write("\n                ></input>\n    </td>\n\n</tr>\n\n<tr class=\"jive-odd\" name=\"DestGroup\" id=\"DestGroup\" ");
if (!isDestGroup) {
      out.write(" style=\"display:none;\"");
}
      out.write(">\n    <td>\n        Destination Group\n    </td>\n    <td>\n        <select id=\"destGroupJID\" name=\"destGroupJID\">\n            ");
 for (Group group : groups) {
      out.write("\n\n            <option value=\"");
      out.print(group.getName());
      out.write("\"\n                    ");
if (isDestGroup && destination.equals(group.getName())) {
      out.write(" selected");
}
      out.write("\n                    >");
      out.print(group.getName());
      out.write("\n            </option>\n            ");
}
      out.write("\n             <option value=\"");
      out.print(PacketFilterConstants.ANY_GROUP);
      out.write('"');
      out.write(' ');
if (isDestGroup && destination.equals(PacketFilterConstants.ANY_GROUP)) {
      out.write("\n                    selected");
}
      out.write("\n                    >");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\n            </option>\n        </select>\n    </td>\n</tr>\n\n<tr class=\"jive-odd\" name=\"DestUser\" id=\"DestUser\" ");
if (!isDestUser) {
      out.write(" style=\"display:none;\"");
}
      out.write(">\n    <td>\n        Destination User\n    </td>\n    <td>\n        <select id=\"destUserJID\" name=\"destUserJID\">\n            ");
 for (String userName : userList) {
      out.write("\n            <option value=\"");
      out.print(userName+"@"+serverName);
      out.write("\"\n                    ");
 if (isDestUser && destination.equals(userName + "@" + serverName)) {
      out.write("\n                    selected\n                    ");
}
      out.write("\n                    >");
      out.print(userName + "@" + serverName);
      out.write("\n            </option>\n            ");
}
      out.write("\n        </select>\n    </td>\n</tr>\n\n<tr class=\"jive-even\" name=\"DestComponent\" id=\"DestComponent\" ");
if (!isDestComponent) {
      out.write("\n    style=\"display:none;\"");
}
      out.write("\n        >\n    <td>\n        Component\n    </td>\n    <td>\n        <select id=\"destComponentJID\" name=\"destComponentJID\">\n            ");
 for (String component : components) {
            if (component != null && cList.getComponentName(component) != null) {
      out.write("\n            <option value=\"");
      out.print(component);
      out.write("\"\n                    ");
if (destJID != null && destJID.equals(component)) {
      out.write("\n                    selected");
}
      out.write(">\n                ");
      out.print(cList.getComponentName(component));
      out.write("\n            </option>\n            ");
 }
            }
      out.write("\n        </select>\n    </td>\n</tr>\n\n\n<tr class=\"jive-odd\">\n    <td>Log</td>\n    <td><input type=\"checkbox\" name=\"log\" value=\"true\"\n    ");
if(rule.doLog()) {
      out.write(" checked");
}
      out.write("></td>\n</tr>\n<tr class=\"jive-even\">\n    <td>Description</td>\n    <td><input type=\"text\" size=\"40\" name=\"description\"\n            ");
if (rule.getDescription() != null) {
      out.write("\n               value=\"");
      out.print(rule.getDescription());
      out.write("\"\n            ");
}
      out.write("\n            ></input></td>\n</tr>\n<tr>\n    <td>\n        <input type=\"submit\" name=\"editSave\" value=\"");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\">\n        <input type=\"submit\" name=\"cancel\" value=\"");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\">\n    </td>\n    <td>&nbsp;</td>\n</tr>\n</tbody>\n</table>\n\n</div>\n</form>\n\n</body>\n</html>\n\n");
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
    _jspx_th_fmt_message_0.setKey("pf.save.edit");
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
    _jspx_th_fmt_message_1.setKey("pf.error.sourceOther");
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
    _jspx_th_fmt_message_2.setKey("pf.error.destOther");
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
    _jspx_th_fmt_message_3.setKey("pf.anygroup");
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
    _jspx_th_fmt_message_4.setKey("pf.anygroup");
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
    _jspx_th_fmt_message_5.setKey("pf.save.edit");
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
    _jspx_th_fmt_message_6.setKey("pf.global.cancel");
    int _jspx_eval_fmt_message_6 = _jspx_th_fmt_message_6.doStartTag();
    if (_jspx_th_fmt_message_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_6);
    return false;
  }
}
