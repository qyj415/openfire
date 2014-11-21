package org.jivesoftware.openfire.plugin.clientControl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.spark.Bookmark;
import org.jivesoftware.openfire.plugin.spark.SparkUtil;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.muc.MUCRoom;
import org.jivesoftware.openfire.muc.MultiUserChatService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.jivesoftware.util.NotFoundException;
import org.jivesoftware.util.LocaleUtils;

public final class create_002dbookmark_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    /**
     * A more elegant string representing all users that this bookmark
     * "belongs" to.
     *
     * @return the string.
     */
    public String getCommaDelimitedList(Collection<String> strings) {
        StringBuilder buf = new StringBuilder();
        for (String string : strings) {
            buf.append(string);
            buf.append(",");
        }

        String returnStr = buf.toString();
        if (returnStr.endsWith(",")) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }


        return returnStr;
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

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

    boolean urlType = false;
    boolean groupchatType = false;

    String type = request.getParameter("type");
    if ("url".equals(type)) {
        urlType = true;
    }
    else {
        groupchatType = true;
    }

    boolean edit = request.getParameter("edit") != null;
    String bookmarkID = request.getParameter("bookmarkID");

    Bookmark editBookmark = null;
    if (edit && bookmarkID != null) {
        try {
            editBookmark = new Bookmark(Long.parseLong(bookmarkID));
        }
        catch (NotFoundException e) {
            Log.error(e);
        }
    }

    Map<String,String> errors = new HashMap<String,String>();
    String groupchatName = request.getParameter("groupchatName");
    String groupchatJID = request.getParameter("groupchatJID");

    boolean autojoin = ParamUtils.getBooleanParameter(request,"autojoin");

    String users = request.getParameter("users");
    String groups = request.getParameter("groups");


    String url = request.getParameter("url");
    String urlName = request.getParameter("urlName");

    boolean isRSS = ParamUtils.getBooleanParameter(request, "rss", false);

    boolean allUsers = ParamUtils.getBooleanParameter(request,"all");

    boolean createGroupchat = request.getParameter("createGroupchatBookmark") != null;
    boolean createURLBookmark = request.getParameter("createURLBookmark") != null;


    boolean submit = false;
    if (createGroupchat || createURLBookmark) {
        submit = true;
    }

    if (submit && createURLBookmark) {
        if (!SparkUtil.hasLength(url)) {
            errors.put("url", LocaleUtils.getLocalizedString("bookmark.url.error", "clientcontrol"));
        }

        if (!SparkUtil.hasLength(urlName)) {
            errors.put("urlName", LocaleUtils.getLocalizedString("bookmark.urlName.error", "clientcontrol"));
        }
    }
    else if (submit && createGroupchat) {
        if (!SparkUtil.hasLength(groupchatName)) {
            errors.put("groupchatName", LocaleUtils.getLocalizedString("bookmark.groupchat.name.error", "clientcontrol"));
        }

        if (!SparkUtil.hasLength(groupchatJID) || !groupchatJID.contains("@")) {
            errors.put("groupchatJID", LocaleUtils.getLocalizedString("bookmark.groupchat.address.error", "clientcontrol"));
        }
    }

    if (!submit && errors.size() == 0) {
        if (editBookmark != null) {
            if (editBookmark.getType() == Bookmark.Type.url) {
                url = editBookmark.getProperty("url");
                urlName = editBookmark.getName();
            }
            else {
                groupchatName = editBookmark.getName();
                autojoin = editBookmark.getProperty("autojoin") != null;
                groupchatJID = editBookmark.getValue();
            }

            users = getCommaDelimitedList(editBookmark.getUsers());
            groups = getCommaDelimitedList(editBookmark.getGroups());
            allUsers = editBookmark.isGlobalBookmark();
            isRSS = editBookmark.getProperty("rss") != null;
        }
        else {
            groupchatName = "";
            groupchatJID = "";
            url = "";
            urlName = "";
            users = "";
            groups = "";
        }
    }
    else {
        if ((createURLBookmark || createGroupchat) && errors.size() == 0) {
            Bookmark bookmark = null;

            if (bookmarkID == null) {
                if (createURLBookmark)
                    bookmark = new Bookmark(Bookmark.Type.url, urlName, url);

                if (createGroupchat) {
                    bookmark = new Bookmark(Bookmark.Type.group_chat, groupchatName, groupchatJID);
                }
            }
            else {
                try {
                    bookmark = new Bookmark(Long.parseLong(bookmarkID));
                }
                catch (NotFoundException e) {
                    Log.error(e);
                }
                if (createURLBookmark) {
                    bookmark.setName(urlName);
                    bookmark.setValue(url);
                }
                else {
                    bookmark.setName(groupchatName);
                    bookmark.setValue(groupchatJID);
                }
            }

            List<String> userCollection = new ArrayList<String>();
            List<String> groupCollection = new ArrayList<String>();
            if (users != null) {
                StringTokenizer tkn = new StringTokenizer(users, ",");
                while (tkn.hasMoreTokens()) {
                    userCollection.add(tkn.nextToken());
                }

                bookmark.setUsers(userCollection);
            }

            if (groups != null) {
                StringTokenizer tkn = new StringTokenizer(groups, ",");
                while (tkn.hasMoreTokens()) {
                    groupCollection.add(tkn.nextToken());
                }

                bookmark.setGroups(groupCollection);
            }

            if (allUsers) {
                bookmark.setGlobalBookmark(true);
            }
            else {
                bookmark.setGlobalBookmark(false);
            }

            if (createURLBookmark) {
                if (url != null) {
                    bookmark.setProperty("url", url);
                }

                if (isRSS) {
                    bookmark.setProperty("rss", "true");
                }
		else {
	            bookmark.deleteProperty("rss");
		}
            }
            else {
                if (autojoin) {
                    bookmark.setProperty("autojoin", "true");
                }
		else {
	            bookmark.deleteProperty("autojoin");
		}
            }
        }
    }

    if (submit && errors.size() == 0) {
        if (createURLBookmark) {
            response.sendRedirect("url-bookmarks.jsp?urlCreated=true");
            return;
        }
        else if (createGroupchat) {
            response.sendRedirect("groupchat-bookmarks.jsp?groupchatCreated=true");
        }
    }

    String description = LocaleUtils.getLocalizedString("bookmark.url.create.description", "clientcontrol");
    if (groupchatType) {
        description = LocaleUtils.getLocalizedString("bookmark.groupchat.create.description", "clientcontrol");
        if(edit){
            description = LocaleUtils.getLocalizedString("bookmark.groupchat.edit.description", "clientcontrol");
        }
    }
    else if(edit){
        description = LocaleUtils.getLocalizedString("bookmark.url.edit.description", "clientcontrol");
    }


      out.write("\n\n\n<html>\n<head>\n    <title>");
      out.print( editBookmark != null ? LocaleUtils.getLocalizedString("bookmark.edit", "clientcontrol") : LocaleUtils.getLocalizedString("bookmark.create", "clientcontrol"));
      out.write("</title>\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\n    <meta name=\"pageID\" content=\"");
      out.print( groupchatType ? "groupchat-bookmarks" : "url-bookmarks");
      out.write("\"/>\n    <script src=\"/js/prototype.js\" type=\"text/javascript\"></script>\n    <script src=\"/js/scriptaculous.js\" type=\"text/javascript\"></script>\n    <script type=\"text/javascript\">\n        function toggleAllElement(ele, users, groups) {\n            users.disabled = ele.checked;\n            groups.disabled = ele.checked;\n        }\n\n        function showPicker() {\n            alert(\"Not implemented!\");\n        }\n\n        function validateForms(form) {\n            form.users.disabled = form.all.checked;\n            form.groups.disabled = form.all.checked;\n        }\n    </script>\n    <style type=\"text/css\">\n        .div-border {\n            border: 1px;\n            border-color: #ccc;\n            border-style: dotted;\n        }\n    </style>\n    <style type=\"text/css\">\n        @import \"style/style.css\";\n    </style>\n</head>\n\n<body>\n\n<!-- Create URL Bookmark -->\n<p>\n    ");
      out.print( description);
      out.write("\n</p>\n\n\n");
 if (submit && errors.size() == 0 && createURLBookmark) { 
      out.write("\n<div class=\"success\">\n   ");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("\n</div>\n");
 } 
      out.write('\n');
      out.write('\n');
      out.write('\n');
 if (urlType) { 
      out.write("\n<form id=\"urlForm\" name=\"urlForm\" action=\"create-bookmark.jsp\" method=\"post\">\n    <table class=\"div-border\" cellpadding=\"3\">\n        <tr valign=\"top\">\n            <td><b>");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write(":</b></td>\n            <td><input type=\"text\" name=\"urlName\" size=\"30\" value=\"");
      out.print(urlName );
      out.write("\"/><br/>\n                ");
 if (errors.get("urlName") != null) { 
      out.write("\n                <span class=\"jive-error-text\">");
      out.print( errors.get("urlName"));
      out.write("<br/></span>\n                ");
 } 
      out.write("\n                <span class=\"jive-description\">");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</span></td>\n\n        </tr>\n        <tr valign=\"top\">\n            <td><b>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write(":</b></td>\n            <td><input type=\"text\" name=\"url\" size=\"30\" value=\"");
      out.print(url );
      out.write("\"/><br/>\n                ");
 if (errors.get("url") != null) { 
      out.write("\n                <span class=\"jive-error-text\">");
      out.print( errors.get("url"));
      out.write("<br/></span>\n                ");
 } 
      out.write("\n                <span class=\"jive-description\">eg. http://www.acme.com</span></td>\n        </tr>\n        <tr valign=\"top\">\n            <td><b>");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write(":</b></td>\n            <td><input type=\"text\" name=\"users\" size=\"30\" value=\"");
      out.print( users);
      out.write("\"/><br/>\n                <span class=\"jive-error-text\"></span></td>\n            <!--\n            <td><img src=\"images/icon_browse_14x13.gif\"/></td><td><a href=\"javascript:showPicker();\">");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</a></td>-->\n            <td><input type=\"checkbox\" name=\"all\" ");
      out.print( allUsers ? "checked" : "" );
      out.write(" onclick=\"toggleAllElement(this, document.urlForm.users, document.urlForm.groups);\"/>All Users</td>\n        </tr>\n\n        <tr valign=\"top\">\n            <td><b>");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write(":</b></td>\n            <td><input type=\"text\" name=\"groups\" size=\"30\" value=\"");
      out.print( groups );
      out.write("\"/><br/><span\n                class=\"jive-error-text\"></span></td><!--\n            <td><img src=\"images/icon_browse_14x13.gif\"/></td><td><a href=\"javascript:showPicker();\">");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</a></td>-->\n        </tr>\n        ");
 if (errors.get("noUsersOrGroups") != null) { 
      out.write("\n        <tr>\n            <td colspan=\"2\" class=\"jive-error-text\">");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</td>\n        </tr>\n        ");
 } 
      out.write("\n        <tr><td><b>");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("</b></td><td><input type=\"checkbox\" name=\"rss\" ");
      out.print( isRSS ? "checked" : "" );
      out.write("/></td></tr>\n\n        <tr><td></td><td><input type=\"submit\" name=\"createURLBookmark\"\n                                value=\"");
      out.print( editBookmark != null ? LocaleUtils.getLocalizedString("bookmark.save.changes", "clientcontrol") : LocaleUtils.getLocalizedString("create", "clientcontrol")  );
      out.write("\"/>\n            &nbsp;<input type=\"button\" value=\"");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("\"\n                         onclick=\"window.location.href='url-bookmarks.jsp'; return false;\">\n        </td>\n        </tr>\n\n    </table>\n    <input type=\"hidden\" name=\"type\" value=\"url\"/>\n    ");
 if (editBookmark != null) { 
      out.write("\n    <input type=\"hidden\" name=\"bookmarkID\" value=\"");
      out.print( editBookmark.getBookmarkID());
      out.write("\"/>\n    <input type=\"hidden\" name=\"edit\" value=\"true\" />\n    ");
 } 
      out.write("\n\n<script type=\"text/javascript\">\n   validateForms(document.urlForm);\n</script>\n</form>\n\n");
 }
else { 
      out.write("\n\n<form name=\"f\" id=\"f\" action=\"create-bookmark.jsp\" method=\"post\">\n\n    <table class=\"div-border\" cellpadding=\"3\">\n        <tr valign=\"top\">\n            <td><b>");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write(":</b></td>\n            <td colspan=\"3\"><input type=\"text\" name=\"groupchatName\" size=\"40\" value=\"");
      out.print( groupchatName );
      out.write("\"/><br/>\n                ");
 if (errors.get("groupchatName") != null) { 
      out.write("\n                <span class=\"jive-error-text\">");
      out.print( errors.get("groupchatName"));
      out.write("<br/></span>\n                ");
 } 
      out.write("\n                <span class=\"jive-description\">eg. Discussion Room</span></td>\n        </tr>\n        <tr valign=\"top\">\n            <td><b>");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write(":</b></td>\n            <td colspan=\"3\"><input type=\"text\" name=\"groupchatJID\" size=\"40\" value=\"");
      out.print( groupchatJID );
      out.write("\"/><br/>\n                ");
 if (errors.get("groupchatJID") != null) { 
      out.write("\n                <span class=\"jive-error-text\">");
      out.print( errors.get("groupchatJID"));
      out.write("<br/></span>\n                ");
 } 
      out.write("\n                <span class=\"jive-description\">eg. myroom@conference.example.com</span></td>\n        </tr>\n\n        <tr valign=\"top\">\n            <td><b>");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write(":</b></td>\n            <td><input type=\"text\" name=\"users\" size=\"30\" value=\"");
      out.print( users);
      out.write("\"/><br/>\n                <span class=\"jive-error-text\"></span></td>\n            <!--\n            <td><img src=\"images/icon_browse_14x13.gif\"/></td><td><a href=\"javascript:showPicker();\">");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("</a></td>-->\n            <td><input type=\"checkbox\" name=\"all\" ");
      out.print( allUsers ? "checked" : "" );
      out.write(" onclick=\"toggleAllElement(this, document.f.users, document.f.groups);\"/>");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</td>\n        </tr>\n\n        <tr valign=\"top\">\n            <td><b>");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write(":</b></td>\n            <td><input type=\"text\" name=\"groups\" size=\"30\" value=\"");
      out.print( groups );
      out.write("\"/><br/><span\n                class=\"jive-error-text\"></span></td>\n            <!--\n            <td><img src=\"images/icon_browse_14x13.gif\"/></td><td><a href=\"javascript:showPicker();\">");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("</a></td>-->\n        </tr>\n        <tr>\n            <td><b>");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write(":</b></td><td><input type=\"checkbox\" name=\"autojoin\" ");
      out.print( autojoin ? "checked" : "" );
      out.write("/></td>\n        </tr>\n        <tr>\n            <td></td>\n            <td><input type=\"submit\" name=\"createGroupchatBookmark\"  value=\"");
      out.print( editBookmark != null ? LocaleUtils.getLocalizedString("bookmark.save.changes", "clientcontrol") : LocaleUtils.getLocalizedString("create", "clientcontrol")  );
      out.write("\"/>&nbsp;\n                <input type=\"button\" value=\"Cancel\" onclick=\"window.location.href='groupchat-bookmarks.jsp'; return false;\">\n            </td>\n        </tr>\n\n    </table>\n    <input type=\"hidden\" name=\"type\" value=\"groupchat\"/>\n    ");
 if (editBookmark != null) { 
      out.write("\n    <input type=\"hidden\" name=\"bookmarkID\" value=\"");
      out.print( editBookmark.getBookmarkID());
      out.write("\"/>\n    <input type=\"hidden\" name=\"edit\" value=\"true\" />\n    ");
 } 
      out.write("\n\n<script type=\"text/javascript\">\n    validateForms(document.f);\n</script>\n</form>\n\n");
 } 
      out.write("\n\n\n\n</body>\n</html>\n\n");
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
    _jspx_th_fmt_message_0.setKey("bookmark.created");
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
    _jspx_th_fmt_message_1.setKey("bookmark.url.name");
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
    _jspx_th_fmt_message_2.setKey("bookmark.url.name.description");
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
    _jspx_th_fmt_message_3.setKey("bookmark.url");
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
    _jspx_th_fmt_message_4.setKey("users");
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
    _jspx_th_fmt_message_5.setKey("bookmark.browse.users");
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
    _jspx_th_fmt_message_6.setKey("groups");
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
    _jspx_th_fmt_message_7.setKey("bookmark.browse.groups");
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
    _jspx_th_fmt_message_8.setKey("bookmark.users.groups.error");
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
    _jspx_th_fmt_message_9.setKey("bookmark.create.rss.feed");
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
    _jspx_th_fmt_message_10.setKey("cancel");
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
    _jspx_th_fmt_message_11.setKey("group.chat.bookmark.name");
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
    _jspx_th_fmt_message_12.setKey("group.chat.bookmark.address");
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
    _jspx_th_fmt_message_13.setKey("users");
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
    _jspx_th_fmt_message_14.setKey("bookmark.browse.users");
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
    _jspx_th_fmt_message_15.setKey("bookmark.create.all.users");
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
    _jspx_th_fmt_message_16.setKey("groups");
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
    _jspx_th_fmt_message_17.setKey("bookmark.browse.groups");
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
    _jspx_th_fmt_message_18.setKey("group.chat.bookmark.autojoin");
    int _jspx_eval_fmt_message_18 = _jspx_th_fmt_message_18.doStartTag();
    if (_jspx_th_fmt_message_18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_18);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_18);
    return false;
  }
}
