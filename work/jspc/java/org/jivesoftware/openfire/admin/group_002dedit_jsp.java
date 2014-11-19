package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.group.Group;
import org.jivesoftware.openfire.group.GroupManager;
import org.jivesoftware.openfire.security.SecurityAuditManager;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.user.UserNotFoundException;
import gnu.inet.encoding.Stringprep;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.StringUtils;
import org.xmpp.packet.JID;
import org.xmpp.packet.Presence;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public final class group_002dedit_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    private static String toList(String[] array, String enc) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        String sep = "";
        for (String anArray : array) {
            String item;
            try {
                item = URLDecoder.decode(anArray, enc);
            }
            catch (UnsupportedEncodingException e) {
                item = anArray;
            }
            buf.append(sep).append(item);
            sep = ",";
        }
        return buf.toString();
    }

    private static boolean contains(String[] array, String item) {
        if (array == null || array.length == 0 || item == null) {
            return false;
        }
        for (String anArray : array) {
            if (item.equals(anArray)) {
                return true;
            }
        }
        return false;
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

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<!-- Define Administration Bean -->\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
  webManager.init(pageContext); 
      out.write('\n');
      out.write('\n');
  // Get parameters
    boolean add = request.getParameter("add") != null;
    boolean delete = request.getParameter("remove") != null;
    boolean updateMember = request.getParameter("updateMember") != null;
    boolean update = request.getParameter("save") != null;
    boolean cancel = request.getParameter("cancel") != null;
    String username = ParamUtils.getParameter(request, "username");
    String [] adminIDs = ParamUtils.getParameters(request, "admin");
    String [] deleteMembers = ParamUtils.getParameters(request, "delete");
    String groupName = ParamUtils.getParameter(request, "group");
    GroupManager groupManager = webManager.getGroupManager();
    boolean groupInfoChanged = ParamUtils.getBooleanParameter(request, "groupChanged", false);

    Map<String,String> errors = new HashMap<String,String>();

    // Get the presence manager
    PresenceManager presenceManager = webManager.getPresenceManager();
    UserManager userManager = webManager.getUserManager();

    boolean enableRosterGroups = ParamUtils.getBooleanParameter(request,"enableRosterGroups");
    boolean shareAdditional = ParamUtils.getParameter(request, "shareContactList") != null;
    String groupDisplayName = ParamUtils.getParameter(request,"groupDisplayName");
    String showGroup = ParamUtils.getParameter(request,"showGroup");
    String[] groupNames = ParamUtils.getParameters(request, "groupNames");

    Group group = groupManager.getGroup(groupName);
    boolean success;
    StringBuffer errorBuf = new StringBuffer();

    if (cancel) {
        response.sendRedirect("group-summary.jsp");
        return;
    }

    if (update) {
        if (enableRosterGroups && (groupDisplayName == null || groupDisplayName.trim().length() == 0)) {
            errors.put("groupDisplayName", "");
        }
        if (errors.isEmpty()) {
            if (enableRosterGroups) {
                if (showGroup == null || !shareAdditional) {
                    showGroup = "onlyGroup";
                }
                if ("spefgroups".equals(showGroup)) {
                    showGroup = "onlyGroup";
                }
                else {
                    groupNames = new String[] {};
                }
                group.getProperties().put("sharedRoster.showInRoster", showGroup);
                if (groupDisplayName != null) {
                    group.getProperties().put("sharedRoster.displayName", groupDisplayName);
                }
                group.getProperties().put("sharedRoster.groupList", toList(groupNames, "UTF-8"));

                if (!SecurityAuditManager.getSecurityAuditProvider().blockGroupEvents()) {
                    // Log the event
                    webManager.logEvent("enabled roster groups for "+groupName, "showinroster = "+showGroup+"\ndisplayname = "+groupDisplayName+"\ngrouplist = "+toList(groupNames, "UTF-8"));
                }
            }
            else {
                group.getProperties().put("sharedRoster.showInRoster", "nobody");
                group.getProperties().put("sharedRoster.displayName", "");
                group.getProperties().put("sharedRoster.groupList", "");

                if (!SecurityAuditManager.getSecurityAuditProvider().blockGroupEvents()) {
                    // Log the event
                    webManager.logEvent("disabled roster groups for "+groupName, null);
                }
            }

            // Get admin list and compare it the admin posted list.
            response.sendRedirect("group-edit.jsp?group=" + URLEncoder.encode(groupName, "UTF-8") + "&groupChanged=true");
            return;
        }
        else {
            // Continue editing since there are some errors
            updateMember = false;
        }
    }


    if (updateMember) {
        Set<JID> adminIDSet = new HashSet<JID>();
        for (String adminID : adminIDs) {
            JID newAdmin = new JID(adminID);
            adminIDSet.add(newAdmin);
            boolean isAlreadyAdmin = group.getAdmins().contains(newAdmin);
            if (!isAlreadyAdmin) {
                // Add new admin
                group.getAdmins().add(newAdmin);
            }
        }
        Collection<JID> admins = Collections.unmodifiableCollection(group.getAdmins());
        Set<JID> removeList = new HashSet<JID>();
        for (JID admin : admins) {
            if (!adminIDSet.contains(admin)) {
                removeList.add(admin);
            }
        }
        for (JID member : removeList) {
            group.getMembers().add(member);
        }
        if (!SecurityAuditManager.getSecurityAuditProvider().blockGroupEvents()) {
            // Log the event
            // TODO: Should log more here later
            webManager.logEvent("updated group membership for "+groupName, null);
        }
        // Get admin list and compare it the admin posted list.
        response.sendRedirect("group-edit.jsp?group=" + URLEncoder.encode(groupName, "UTF-8") + "&updatesuccess=true");
        return;
    }
    else if (add && username != null) {
        int count = 0;
        username = username.trim();
        username = username.toLowerCase();

        if (username.indexOf('@') != -1) {
            try {
                UserManager.getInstance().getUser(JID.escapeNode(username));
                // That means that this user has an email address as their node.
                username = JID.escapeNode(username);
            }
            catch (UserNotFoundException e) {

            }
        }

        // Add to group as member by default.
        try {
            boolean added;
            if (username.indexOf('@') == -1) {
                // No @ was found so assume this is a JID of a local user
                username = JID.escapeNode(username);
                username = Stringprep.nodeprep(username);
                UserManager.getInstance().getUser(username);
                added = group.getMembers().add(webManager.getXMPPServer().createJID(username, null));
            }
            else {
                // Admin entered a JID. Add the JID directly to the list of group members
                added = group.getMembers().add(new JID(username));
                if (!SecurityAuditManager.getSecurityAuditProvider().blockGroupEvents()) {
                    // Log the event
                    webManager.logEvent("added group member to "+groupName, "username = "+username);
                }
            }

            if (added) {
                count++;
            }
            else {
                errorBuf.append("<br>").append(
                        LocaleUtils.getLocalizedString("group.edit.already_user", Arrays.asList(username)));
            }

        }
        catch (Exception e) {
            Log.warn("Problem adding new user to existing group", e);
            errorBuf.append("<br>").append(
                    LocaleUtils.getLocalizedString("group.edit.inexistent_user", Arrays.asList(username)));
        }
        if (count > 0) {
            response.sendRedirect("group-edit.jsp?group=" +
                    URLEncoder.encode(groupName, "UTF-8") + "&success=true");
            return;
        }
        else {
            success = false;
            add = true;
        }

    }
    else if(add && username == null){
        add = false;
    }
    else if (delete) {
        for (String deleteMember : deleteMembers) {
            JID member = new JID(deleteMember);
            group.getMembers().remove(member);
            group.getAdmins().remove(member);
        }
        response.sendRedirect("group-edit.jsp?group=" + URLEncoder.encode(groupName, "UTF-8") + "&deletesuccess=true");
        return;
    }
    success = groupInfoChanged || "true".equals(request.getParameter("success")) ||
            "true".equals(request.getParameter("deletesuccess")) ||
            "true".equals(request.getParameter("updatesuccess")) ||
            "true".equals(request.getParameter("creategroupsuccess"));

    if (errors.size() == 0) {
        showGroup = group.getProperties().get("sharedRoster.showInRoster");
        enableRosterGroups = !"nobody".equals(showGroup);
        shareAdditional = "everybody".equals(showGroup);
        if ("onlyGroup".equals(showGroup)) {
            String glist = group.getProperties().get("sharedRoster.groupList");
            List<String> l = new ArrayList<String>();
            if (glist != null) {
                StringTokenizer tokenizer = new StringTokenizer(glist,",\t\n\r\f");
                while (tokenizer.hasMoreTokens()) {
                    String tok = tokenizer.nextToken().trim();
                    l.add(tok.trim());
                }
                if (!l.isEmpty()) {
                    shareAdditional = true;
                }
            }
            groupNames = l.toArray(new String[]{});
        }
        groupDisplayName = group.getProperties().get("sharedRoster.displayName"); 
    }

      out.write("\n\n<html>\n<head>\n<title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n<meta name=\"subPageID\" content=\"group-edit\"/>\n<meta name=\"extraParams\" content=\"");
      out.print( "group="+URLEncoder.encode(groupName, "UTF-8") );
      out.write("\"/>\n<meta name=\"helpPage\" content=\"edit_group_properties.html\"/>\n</head>\n<body>\n\n");
 if (webManager.getGroupManager().isReadOnly() && webManager.getGroupManager().isPropertyReadOnly()) { 
      out.write("\n<div class=\"error\">\n    ");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\n</div>\n");
 } 
      out.write("\n\n<p>\n\t");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("\n</p>\n\n<p>\n\t<a href=\"group-summary.jsp\" class=\"jive-link-back\"><span>&laquo;</span> Back to all groups</a>\n</p>\n\n");

    if (success) {

      out.write("\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n        <td class=\"jive-icon-label\">\n        ");
 if (groupInfoChanged) { 
      out.write("\n        ");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("\n        ");
 } else if ("true".equals(request.getParameter("success"))) { 
      out.write("\n            ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\n        ");
 } else if ("true".equals(request.getParameter("deletesuccess"))) { 
      out.write("\n            ");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("\n        ");
 } else if ("true".equals(request.getParameter("updatesuccess"))) { 
      out.write("\n            ");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("\n         ");
 } else if ("true".equals(request.getParameter("creategroupsuccess"))) { 
      out.write("\n            ");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("\n        ");

            }
        
      out.write("\n        </td></tr>\n    </tbody>\n    </table>\n    </div><br>\n");

    }
    else if(!success && add){

      out.write("\n\t<div class=\"jive-error\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr><td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n        <td class=\"jive-icon-label\">\n        ");
 if(add) { 
      out.write("\n        ");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("\n        ");
      out.print( StringUtils.escapeHTMLTags(errorBuf.toString()) );
      out.write("\n        ");
 } 
      out.write("\n        </td></tr>\n    </tbody>\n    </table>\n    </div><br>\n");
 } 
      out.write("\n\n\t<div class=\"jive-horizontalRule\"></div>\n\n<form name=\"ff\" action=\"group-edit.jsp\">\n<input type=\"hidden\" name=\"group\" value=\"");
      out.print( StringUtils.escapeForXML(groupName) );
      out.write("\"/>\n\n\n\t<!-- BEGIN group name and description -->\n\t<div class=\"jive-contentBox-plain\">\n        ");
  // Only show edit and delete options if the groups aren't read-only.
            if (!webManager.getGroupManager().isReadOnly()) { 
      out.write("\n        <div class=\"jive-contentBox-toolbox\">\n\t\t\t<a href=\"group-create.jsp?group=");
      out.print( URLEncoder.encode(group.getName(), "UTF-8"));
      out.write("&name=");
      out.print( URLEncoder.encode(group.getName(), "UTF-8"));
      out.write("&description=");
      out.print( group.getDescription() != null? URLEncoder.encode(group.getDescription(), "UTF-8") : "" );
      out.write("\" class=\"jive-link-edit\">");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("</a>\n\t\t\t<a href=\"group-delete.jsp?group=");
      out.print( URLEncoder.encode(group.getName(), "UTF-8"));
      out.write("\" class=\"jive-link-delete\">");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</a>\n\t\t</div>\n        ");
 } 
      out.write("\n\n        <h3>\n\t\t\t");
      out.print( StringUtils.escapeHTMLTags(group.getName()) );
      out.write("\n\t\t</h3>\n\t\t<p>\n\t\t\t");
      out.print( group.getDescription() != null ? StringUtils.escapeHTMLTags(group.getDescription()) : "" );
      out.write("\n\t\t</p>\n    </div>\n\t<!-- END group name and description -->\n\n\n\t<!-- BEGIN contact list settings -->\n\t<div class=\"jive-contentBoxHeader\">\n\t\t");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\n\n\t</div>\n\t<div class=\"jive-contentBox\">\n            ");
 if (webManager.getGroupManager().isPropertyReadOnly()) { 
      out.write("\n        <p>\n                ");
 if (enableRosterGroups) { 
      out.write("\n            ");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("\n                ");
 } else { 
      out.write("\n            ");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\n                ");
 } 
      out.write("\n        </p>\n\n            ");
 } else { 
      out.write("\n        <p>\n            ");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("\n        </p>\n\n\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n\t\t<tbody>\n\t\t<tr>\n            <td width=\"1%\">\n                <input type=\"radio\" name=\"enableRosterGroups\" value=\"false\" id=\"rb201\" ");
      out.print( !enableRosterGroups ? "checked" : "" );
      out.write(" onClick=\"document.getElementById('jive-roster').style.display = 'none';\">\n            </td>\n            <td width=\"99%\">\n                <label for=\"rb201\">");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</label>\n            </td>\n        </tr>\n        <tr>\n            <td width=\"1%\" valign=\"top\">\n                <input type=\"radio\" name=\"enableRosterGroups\" value=\"true\" id=\"rb202\" ");
      out.print( enableRosterGroups ? "checked" : "" );
      out.write(" onClick=\"document.getElementById('jive-roster').style.display = 'block';\">\n            </td>\n            <td width=\"99%\">\n                <label for=\"rb202\">");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("</label>\n\n                <div id=\"jive-roster\" style=\"display: ");
      out.print( !enableRosterGroups ? "none" : "block"  );
      out.write(";\">\n\t               <b>");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("</b>\n\t               <input type=\"text\" name=\"groupDisplayName\" size=\"30\" maxlength=\"100\" value=\"");
      out.print( (groupDisplayName != null ? StringUtils.escapeForXML(groupDisplayName) : "") );
      out.write("\"><br>\n                       ");
  if (errors.get("groupDisplayName") != null) { 
      out.write("\n                           <span class=\"jive-error-text\">");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("</span><br/>\n                       ");
  } 
      out.write("\n\t                   <script type=\"text/javascript\" language=\"JavaScript\">\n\t\t                   function toggleRosterShare() {\n\t\t\t                   if (document.getElementById('cb101').checked == false) {\n\t\t\t                       document.getElementById('jive-rosterShare').style.display = 'none';\n                                } else {\n\t\t\t\t                   document.getElementById('jive-rosterShare').style.display = 'block';\n                                   document.getElementById('rb002').checked = true;\n\t\t\t                   }\n\t\t                   }\n\t                   </script>\n\n\t               <input type=\"checkbox\" id=\"cb101\" name=\"shareContactList\" onClick=\"toggleRosterShare();\" style=\"vertical-align: middle;\"\n\t\t\t\t\t\t\t\t\t\t ");
      out.print( (shareAdditional ? "checked" : "") );
      out.write(">\n\t               <label for=\"cb101\">");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("</label>\n\t                    <div id=\"jive-rosterShare\" style=\"display: ");
      out.print( (enableRosterGroups && shareAdditional) ? "block" : "none"  );
      out.write(";\">\n\t\t                    <table cellpadding=\"2\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"showGroup\" value=\"everybody\" id=\"rb002\"\n\t\t\t\t\t\t\t\t\t\t ");
      out.print( ("everybody".equals(showGroup) ? "checked" : "") );
      out.write(">\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t\t\t\t<label for=\"rb002\">");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("</label>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"showGroup\" value=\"spefgroups\" id=\"rb003\"\n\t\t\t\t\t\t\t\t\t\t ");
      out.print( (groupNames != null && groupNames.length > 0) ? "checked" : "" );
      out.write(">\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t\t\t\t<label for=\"rb003\">");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</label>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td width=\"1%\" nowrap>\n\t\t\t\t\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t\t\t\t<select name=\"groupNames\" size=\"6\" onclick=\"this.form.showGroup[1].checked=true;\"\n\t\t\t\t\t\t\t\t\t\t multiple style=\"width:340px;font-family:verdana,arial,helvetica,sans-serif;font-size:8pt;\">\n\n\t\t\t\t\t\t\t\t\t\t");
  for (Group g : webManager.getGroupManager().getGroups()) {
											// Do not offer the edited group in the list of groups
											// Members of the editing group can always see each other
											if (g.equals(group)) {
												continue;
											}
										
      out.write("\n\n\t\t\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print( URLEncoder.encode(g.getName(), "UTF-8") );
      out.write("\"\n\t\t\t\t\t\t\t\t\t\t\t ");
      out.print( (contains(groupNames, g.getName()) ? "selected" : "") );
      out.write("\n\t\t\t\t\t\t\t\t\t\t\t >");
      out.print( StringUtils.escapeHTMLTags(g.getName()) );
      out.write("</option>\n\n\t\t\t\t\t\t\t\t\t\t");
  } 
      out.write("\n\n\t\t\t\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t</table>\n\t\t                </div>\n                </div>\n            </td>\n        </tr>\n        <tr>\n            <td width=\"1%\">\n                &nbsp;\n            </td>\n            <td width=\"99%\">\n\n                <input type=\"submit\" name=\"save\" value=\"");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("\">\n\n            </td>\n        </tr>\n    </tbody>\n    </table>\n            ");
 } 
      out.write("\n\t</div>\n\t<!-- END contact list settings -->\n\n\n</form>\n\n\n\t<!-- BEGIN group membership management -->\n\t<div class=\"jive-contentBoxHeader\">\n\t\t");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write("\n\t</div>\n\t<div class=\"jive-contentBox\">\n\t\t");
  // Only show if the group isn't read-only.
            if (!webManager.getGroupManager().isReadOnly()) { 
      out.write("\n        <p>\n\t\t\t");
      if (_jspx_meth_fmt_message_24(_jspx_page_context))
        return;
      out.write("\n\t\t</p>\n\n        <form action=\"group-edit.jsp\" method=\"post\" name=\"f\">\n        <input type=\"hidden\" name=\"group\" value=\"");
      out.print( StringUtils.escapeForXML(groupName) );
      out.write("\">\n        <input type=\"hidden\" name=\"add\" value=\"Add\"/>\n        <table cellpadding=\"3\" cellspacing=\"1\" border=\"0\" style=\"margin: 0 0 8px 0;\">\n            <tr>\n                <td nowrap width=\"1%\">\n                    ");
      if (_jspx_meth_fmt_message_25(_jspx_page_context))
        return;
      out.write("\n                </td>\n                <td nowrap class=\"c1\" align=\"left\">\n                    <input type=\"text\" size=\"45\" name=\"username\"/>\n                    &nbsp;<input type=\"submit\" name=\"addbutton\" value=\"");
      if (_jspx_meth_fmt_message_26(_jspx_page_context))
        return;
      out.write("\">\n                </td>\n            </tr>\n        </table>\n        </form>\n\n        ");
 } 
      out.write("\n\n        <form action=\"group-edit.jsp\" method=\"post\" name=\"main\">\n        <input type=\"hidden\" name=\"group\" value=\"");
      out.print( StringUtils.escapeForXML(groupName) );
      out.write("\">\n        <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"435\">\n            <tr>\n\t            <th>&nbsp;</th>\n                <th nowrap>");
      if (_jspx_meth_fmt_message_27(_jspx_page_context))
        return;
      out.write("</th>\n                ");
  // Only show if the group isn't read-only.
                if (!webManager.getGroupManager().isReadOnly()) { 
      out.write("\n                <th width=\"1%\" nowrap class=\"jive-table-th-center\">");
      if (_jspx_meth_fmt_message_28(_jspx_page_context))
        return;
      out.write("</th>\n                <th width=\"1%\" nowrap class=\"jive-table-th-center\">");
      if (_jspx_meth_fmt_message_29(_jspx_page_context))
        return;
      out.write("</th>\n                ");
 } 
      out.write("\n            </tr>\n            <!-- Add admins first -->\n");

            int memberCount = group.getMembers().size() + group.getAdmins().size();
            boolean showUpdateButtons = memberCount > 0;
            boolean showRemoteJIDsWarning = false;
            if (memberCount == 0) {

      out.write("\n                <tr>\n                    <td align=\"center\" colspan=\"4\">\n                        <br>\n                        ");
      if (_jspx_meth_fmt_message_30(_jspx_page_context))
        return;
      out.write("\n                        <br>\n                        <br>\n                    </td>\n                </tr>\n");

            }
            else {
                // Sort the list of members.
                ArrayList<JID> allMembers = new ArrayList<JID>(memberCount);
                allMembers.addAll(group.getMembers());
                Collection<JID> admins = group.getAdmins();
                allMembers.addAll(admins);
                Collections.sort(allMembers);
                for (JID jid:allMembers) {
                    boolean isLocal = webManager.getXMPPServer().isLocal(jid);
                    User user = null;
                    if (isLocal) {
                        try {
                            user = userManager.getUser(jid.getNode());
                        }
                        catch (UserNotFoundException unfe) {
                            // Ignore.
                        }
                    }

      out.write("\n                <tr>\n                    <td width=\"1%\">\n                    ");
  if (user != null && presenceManager.isAvailable(user)) {
                            Presence presence = presenceManager.getPresence(user);
                    
      out.write("\n                    ");
 if (presence.getShow() == null) { 
      out.write("\n                    <img src=\"images/im_available.gif\" width=\"16\" height=\"16\" border=\"0\" title=\"");
      if (_jspx_meth_fmt_message_31(_jspx_page_context))
        return;
      out.write("\" alt=\"");
      if (_jspx_meth_fmt_message_32(_jspx_page_context))
        return;
      out.write("\">\n                    ");
 } 
      out.write("\n                    ");
 if (presence.getShow() == Presence.Show.chat) { 
      out.write("\n                    <img src=\"images/im_free_chat.gif\" width=\"16\" height=\"16\" border=\"0\" title=\"");
      if (_jspx_meth_fmt_message_33(_jspx_page_context))
        return;
      out.write("\" alt=\"");
      if (_jspx_meth_fmt_message_34(_jspx_page_context))
        return;
      out.write("\">\n                    ");
 } 
      out.write("\n                    ");
 if (presence.getShow() == Presence.Show.away) { 
      out.write("\n                    <img src=\"images/im_away.gif\" width=\"16\" height=\"16\" border=\"0\" title=\"");
      if (_jspx_meth_fmt_message_35(_jspx_page_context))
        return;
      out.write("\" alt=\"");
      if (_jspx_meth_fmt_message_36(_jspx_page_context))
        return;
      out.write("\">\n                    ");
 } 
      out.write("\n                    ");
 if (presence.getShow() == Presence.Show.xa) { 
      out.write("\n                    <img src=\"images/im_away.gif\" width=\"16\" height=\"16\" border=\"0\" title=\"");
      if (_jspx_meth_fmt_message_37(_jspx_page_context))
        return;
      out.write("\" alt=\"");
      if (_jspx_meth_fmt_message_38(_jspx_page_context))
        return;
      out.write("\">\n                    ");
 } 
      out.write("\n                    ");
 if (presence.getShow() == Presence.Show.dnd) { 
      out.write("\n                    <img src=\"images/im_dnd.gif\" width=\"16\" height=\"16\" border=\"0\" title=\"");
      if (_jspx_meth_fmt_message_39(_jspx_page_context))
        return;
      out.write("\" alt=\"");
      if (_jspx_meth_fmt_message_40(_jspx_page_context))
        return;
      out.write("\">\n                    ");
 } 
      out.write("\n\n                    ");
  } else { 
      out.write("\n                    <img src=\"images/im_unavailable.gif\" width=\"16\" height=\"16\" border=\"0\" title=\"");
      if (_jspx_meth_fmt_message_41(_jspx_page_context))
        return;
      out.write("\" alt=\"");
      if (_jspx_meth_fmt_message_42(_jspx_page_context))
        return;
      out.write("\">\n                    ");
  } 
      out.write("\n\n                    </td>\n                    ");
 if (user != null) { 
      out.write("\n                    <td><a href=\"user-properties.jsp?username=");
      out.print( URLEncoder.encode(user.getUsername(), "UTF-8") );
      out.write('"');
      out.write('>');
      out.print( StringUtils.escapeHTMLTags(JID.unescapeNode(user.getUsername())) );
      out.write("</a>");
 if (!isLocal) { showRemoteJIDsWarning = true; 
      out.write(" <font color=\"red\"><b>*</b></font>");
}
      out.write("</td>\n                    ");
 } else { 
      out.write("\n                    <td>");
      out.print( jid );
 showRemoteJIDsWarning = true; 
      out.write(" <font color=\"red\"><b>*</b></font></td>\n                    ");
 } 
      out.write("\n                    ");
  // Only show if the group isn't read-only.
                    if (!webManager.getGroupManager().isReadOnly()) { 
      out.write("\n                    <td align=\"center\">\n                        <input type=\"checkbox\" name=\"admin\" value=\"");
      out.print( jid );
      out.write('"');
      out.write(' ');
 if (admins.contains(jid)) { 
      out.write("checked");
 } 
      out.write(">\n                    </td>\n                    <td align=\"center\">\n                        <input type=\"checkbox\" name=\"delete\" value=\"");
      out.print( jid );
      out.write("\">\n                    </td>\n                    ");
 } 
      out.write("\n                </tr>\n");

                }
            }
            if (showUpdateButtons && !webManager.getGroupManager().isReadOnly()) {

      out.write("\n                <tr>\n                    <td colspan=\"2\">\n                        &nbsp;\n                    </td>\n                    <td align=\"center\">\n                        <input type=\"submit\" name=\"updateMember\" value=\"Update\">\n                    </td>\n                    <td align=\"center\">\n                        <input type=\"submit\" name=\"remove\" value=\"Remove\">\n                    </td>\n                </tr>\n");

            }

            if (showRemoteJIDsWarning) {

      out.write("\n            <tr>\n                <td colspan=\"4\">\n                    <font color=\"red\">* ");
      if (_jspx_meth_fmt_message_43(_jspx_page_context))
        return;
      out.write("</font>\n                </td>\n            </tr>\n");

            }

      out.write("\n        </table>\n        </form>\n\n    <script type=\"text/javascript\">\n        document.f.username.focus();\n    </script>\n\n\t</div>\n\t<!-- END group membership management -->\n\n\n\n</body>\n</html>\n\n\n");
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

  private boolean _jspx_meth_fmt_message_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_0 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_0.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_0.setParent(null);
    _jspx_th_fmt_message_0.setKey("group.edit.title");
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
    _jspx_th_fmt_message_1.setKey("group.read_only");
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
    _jspx_th_fmt_message_2.setKey("group.edit.form_info");
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
    _jspx_th_fmt_message_3.setKey("group.edit.update");
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
    _jspx_th_fmt_message_4.setKey("group.edit.update_add_user");
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
    _jspx_th_fmt_message_5.setKey("group.edit.update_del_user");
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
    _jspx_th_fmt_message_6.setKey("group.edit.update_user");
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
    _jspx_th_fmt_message_7.setKey("group.edit.update_success");
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
    _jspx_th_fmt_message_8.setKey("group.edit.not_update");
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
    _jspx_th_fmt_message_9.setKey("group.edit.edit_details");
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
    _jspx_th_fmt_message_10.setKey("group.edit.delete");
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
    _jspx_th_fmt_message_11.setKey("group.edit.share_title");
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
    _jspx_th_fmt_message_12.setKey("group.edit.share_status_enabled");
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
    _jspx_th_fmt_message_13.setKey("group.edit.share_status_disabled");
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
    _jspx_th_fmt_message_14.setKey("group.edit.share_content");
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
    _jspx_th_fmt_message_15.setKey("group.edit.share_not_in_rosters");
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
    _jspx_th_fmt_message_16.setKey("group.edit.share_in_rosters");
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
    _jspx_th_fmt_message_17.setKey("group.edit.share_display_name");
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
    _jspx_th_fmt_message_18.setKey("group.edit.share_display_name");
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
    _jspx_th_fmt_message_19.setKey("group.edit.share_additional");
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
    _jspx_th_fmt_message_20.setKey("group.edit.share_all_users");
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
    _jspx_th_fmt_message_21.setKey("group.edit.share_roster_groups");
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
    _jspx_th_fmt_message_22.setKey("group.edit.share_save");
    int _jspx_eval_fmt_message_22 = _jspx_th_fmt_message_22.doStartTag();
    if (_jspx_th_fmt_message_22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_22);
    return false;
  }

  private boolean _jspx_meth_fmt_message_23(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_23 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_23.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_23.setParent(null);
    _jspx_th_fmt_message_23.setKey("group.edit.members");
    int _jspx_eval_fmt_message_23 = _jspx_th_fmt_message_23.doStartTag();
    if (_jspx_th_fmt_message_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
    return false;
  }

  private boolean _jspx_meth_fmt_message_24(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_24 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_24.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_24.setParent(null);
    _jspx_th_fmt_message_24.setKey("group.edit.members_description");
    int _jspx_eval_fmt_message_24 = _jspx_th_fmt_message_24.doStartTag();
    if (_jspx_th_fmt_message_24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_24);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_24);
    return false;
  }

  private boolean _jspx_meth_fmt_message_25(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_25 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_25.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_25.setParent(null);
    _jspx_th_fmt_message_25.setKey("group.edit.add_user");
    int _jspx_eval_fmt_message_25 = _jspx_th_fmt_message_25.doStartTag();
    if (_jspx_th_fmt_message_25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_25);
    return false;
  }

  private boolean _jspx_meth_fmt_message_26(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_26 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_26.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_26.setParent(null);
    _jspx_th_fmt_message_26.setKey("global.add");
    int _jspx_eval_fmt_message_26 = _jspx_th_fmt_message_26.doStartTag();
    if (_jspx_th_fmt_message_26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_26);
    return false;
  }

  private boolean _jspx_meth_fmt_message_27(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_27 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_27.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_27.setParent(null);
    _jspx_th_fmt_message_27.setKey("group.edit.username");
    int _jspx_eval_fmt_message_27 = _jspx_th_fmt_message_27.doStartTag();
    if (_jspx_th_fmt_message_27.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_27);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_27);
    return false;
  }

  private boolean _jspx_meth_fmt_message_28(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_28 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_28.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_28.setParent(null);
    _jspx_th_fmt_message_28.setKey("group.edit.admin");
    int _jspx_eval_fmt_message_28 = _jspx_th_fmt_message_28.doStartTag();
    if (_jspx_th_fmt_message_28.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_28);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_28);
    return false;
  }

  private boolean _jspx_meth_fmt_message_29(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_29 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_29.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_29.setParent(null);
    _jspx_th_fmt_message_29.setKey("group.edit.remove");
    int _jspx_eval_fmt_message_29 = _jspx_th_fmt_message_29.doStartTag();
    if (_jspx_th_fmt_message_29.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_29);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_29);
    return false;
  }

  private boolean _jspx_meth_fmt_message_30(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_30 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_30.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_30.setParent(null);
    _jspx_th_fmt_message_30.setKey("group.edit.user_hint");
    int _jspx_eval_fmt_message_30 = _jspx_th_fmt_message_30.doStartTag();
    if (_jspx_th_fmt_message_30.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_30);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_30);
    return false;
  }

  private boolean _jspx_meth_fmt_message_31(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_31 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_31.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_31.setParent(null);
    _jspx_th_fmt_message_31.setKey("user.properties.available");
    int _jspx_eval_fmt_message_31 = _jspx_th_fmt_message_31.doStartTag();
    if (_jspx_th_fmt_message_31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_31);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_31);
    return false;
  }

  private boolean _jspx_meth_fmt_message_32(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_32 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_32.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_32.setParent(null);
    _jspx_th_fmt_message_32.setKey("user.properties.available");
    int _jspx_eval_fmt_message_32 = _jspx_th_fmt_message_32.doStartTag();
    if (_jspx_th_fmt_message_32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_32);
    return false;
  }

  private boolean _jspx_meth_fmt_message_33(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_33 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_33.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_33.setParent(null);
    _jspx_th_fmt_message_33.setKey("session.details.chat_available");
    int _jspx_eval_fmt_message_33 = _jspx_th_fmt_message_33.doStartTag();
    if (_jspx_th_fmt_message_33.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_33);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_33);
    return false;
  }

  private boolean _jspx_meth_fmt_message_34(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_34 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_34.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_34.setParent(null);
    _jspx_th_fmt_message_34.setKey("session.details.chat_available");
    int _jspx_eval_fmt_message_34 = _jspx_th_fmt_message_34.doStartTag();
    if (_jspx_th_fmt_message_34.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_34);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_34);
    return false;
  }

  private boolean _jspx_meth_fmt_message_35(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_35 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_35.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_35.setParent(null);
    _jspx_th_fmt_message_35.setKey("session.details.away");
    int _jspx_eval_fmt_message_35 = _jspx_th_fmt_message_35.doStartTag();
    if (_jspx_th_fmt_message_35.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_35);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_35);
    return false;
  }

  private boolean _jspx_meth_fmt_message_36(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_36 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_36.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_36.setParent(null);
    _jspx_th_fmt_message_36.setKey("session.details.away");
    int _jspx_eval_fmt_message_36 = _jspx_th_fmt_message_36.doStartTag();
    if (_jspx_th_fmt_message_36.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_36);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_36);
    return false;
  }

  private boolean _jspx_meth_fmt_message_37(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_37 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_37.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_37.setParent(null);
    _jspx_th_fmt_message_37.setKey("session.details.extended");
    int _jspx_eval_fmt_message_37 = _jspx_th_fmt_message_37.doStartTag();
    if (_jspx_th_fmt_message_37.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_37);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_37);
    return false;
  }

  private boolean _jspx_meth_fmt_message_38(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_38 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_38.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_38.setParent(null);
    _jspx_th_fmt_message_38.setKey("session.details.extended");
    int _jspx_eval_fmt_message_38 = _jspx_th_fmt_message_38.doStartTag();
    if (_jspx_th_fmt_message_38.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_38);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_38);
    return false;
  }

  private boolean _jspx_meth_fmt_message_39(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_39 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_39.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_39.setParent(null);
    _jspx_th_fmt_message_39.setKey("session.details.not_disturb");
    int _jspx_eval_fmt_message_39 = _jspx_th_fmt_message_39.doStartTag();
    if (_jspx_th_fmt_message_39.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_39);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_39);
    return false;
  }

  private boolean _jspx_meth_fmt_message_40(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_40 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_40.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_40.setParent(null);
    _jspx_th_fmt_message_40.setKey("session.details.not_disturb");
    int _jspx_eval_fmt_message_40 = _jspx_th_fmt_message_40.doStartTag();
    if (_jspx_th_fmt_message_40.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_40);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_40);
    return false;
  }

  private boolean _jspx_meth_fmt_message_41(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_41 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_41.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_41.setParent(null);
    _jspx_th_fmt_message_41.setKey("user.properties.offline");
    int _jspx_eval_fmt_message_41 = _jspx_th_fmt_message_41.doStartTag();
    if (_jspx_th_fmt_message_41.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_41);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_41);
    return false;
  }

  private boolean _jspx_meth_fmt_message_42(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_42 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_42.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_42.setParent(null);
    _jspx_th_fmt_message_42.setKey("user.properties.offline");
    int _jspx_eval_fmt_message_42 = _jspx_th_fmt_message_42.doStartTag();
    if (_jspx_th_fmt_message_42.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_42);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_42);
    return false;
  }

  private boolean _jspx_meth_fmt_message_43(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_message_43 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _jspx_tagPool_fmt_message_key_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_message_43.setPageContext(_jspx_page_context);
    _jspx_th_fmt_message_43.setParent(null);
    _jspx_th_fmt_message_43.setKey("group.edit.note");
    int _jspx_eval_fmt_message_43 = _jspx_th_fmt_message_43.doStartTag();
    if (_jspx_th_fmt_message_43.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_43);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_43);
    return false;
  }
}
