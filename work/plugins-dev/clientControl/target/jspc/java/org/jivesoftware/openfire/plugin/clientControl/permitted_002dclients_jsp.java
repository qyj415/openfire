package org.jivesoftware.openfire.plugin.clientControl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.JiveGlobals;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.ClientControlPlugin;

public final class permitted_002dclients_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    /**
     * Enumeration of possible clients.
     */
    enum Clients {
        Spark("Spark", "spark", "http://www.igniterealtime.org/projects/spark/index.jsp", "images/client-icon_spark.gif"),
        Adium("Adium", "libgaim", "http://www.adiumx.com/", "images/client-icon_adium.gif"),
        Exodus("Exodus", "exodus", "http://exodus.jabberstudio.org/", "images/client-icon_exodus.gif"),
        Pidgin("Pidgin", "pidgin", "http://www.pidgin.im/", "images/client-icon_pidgin.gif"),
        IChat("IChat", "ichat", "http://www.mac.com/1/ichat.html", "images/client-icon_ichat.gif"),
        JBother("JBother", "jbother", "http://www.jbother.org/", "images/client-icon_jbother.gif"),
        Pandion("Pandion", "pandion", "http://www.pandion.be/", "images/client-icon_pandion.gif"),
        PSI("PSI", "psi", "http://psi-im.org", "images/client-icon_psi.gif"),
        Trillian("Trillian", "trillian", "http://www.ceruleanstudios.com/", "images/client-icon_trillian.gif");

        private String name;
        private String version;
        private String url;
        private String image;

        Clients(String name, String version, String url, String image) {
            this.name = name;
            this.version = version;
            this.url = url;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public String getURL() {
            return url;
        }

        public String getImage(){
            return image;
        }

    }


    void persistOtherClients(List otherClients) {
        // Build other string.
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < otherClients.size(); i++) {
            builder.append(otherClients.get(i));
            if (i < otherClients.size()) {
                builder.append(",");
            }
        }

        JiveGlobals.setProperty("other.clients.allowed", builder.toString());
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
      			"/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n\n");
      out.write('\n');

    String clientsAllowed = JiveGlobals.getProperty("clients.allowed", "all");
    String otherClientsAllowed = JiveGlobals.getProperty("other.clients.allowed", "");

    final List<String> clients = new ArrayList<String>();
    final List<String> otherClients = new ArrayList<String>();


    StringTokenizer otherTokens = new StringTokenizer(otherClientsAllowed, ",");
    while (otherTokens.hasMoreTokens()) {
        otherClients.add(otherTokens.nextToken());
    }


    String other = null;


    boolean submit = request.getParameter("submit") != null;
    boolean addOther = request.getParameter("addOther") != null;
    boolean remove = request.getParameter("removeClient") != null;

    if (submit) {
        String[] cls = request.getParameterValues("client");

        int length = cls != null ? cls.length : 0;
        for (int i = 0; i < length; i++) {
            clients.add(cls[i]);
        }

        boolean all = Boolean.valueOf(request.getParameter("all"));


        final StringBuilder builder = new StringBuilder();
        // Set clients allowed
        for (String client : clients) {
            builder.append(client);
            builder.append(",");
        }

        // If all clients are allowed, delete the property to enforce the default of
        // all clients being allowed.
        if (all) {
            JiveGlobals.deleteProperty("clients.allowed");
            clients.add("all");
        }
        // Otherw, set the specific set of clients.
        else {
            JiveGlobals.setProperty("clients.allowed", builder.toString());
        }

    }
    else if (addOther) {
        other = request.getParameter("other");
        otherTokens = new StringTokenizer(other, ",");
        while (otherTokens.hasMoreTokens()) {
            otherClients.add(otherTokens.nextToken());
        }

        persistOtherClients(otherClients);
    }
    else if (remove) {
        String clientToRemove = request.getParameter("removeClient");
        otherClients.remove(clientToRemove);

        persistOtherClients(otherClients);
    }
    else {
        StringTokenizer tkn = new StringTokenizer(clientsAllowed, ",");
        while (tkn.hasMoreTokens()) {
            String token = tkn.nextToken();
            if (!"all".equals(token)) {
                other = token;
            }
            clients.add(token);
        }
    }

      out.write("\n\n<html>\n<head>\n    <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n    <meta name=\"pageID\" content=\"client-version\"/>\n    <script type=\"text/javascript\" language=\"javascript\" src=\"scripts/tooltips/domLib.js\"></script>\n    <script type=\"text/javascript\" language=\"javascript\" src=\"scripts/tooltips/domTT.js\"></script>\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"style/style.css\">\n\n    <script type=\"text/javascript\">\n        function disableAll() {\n            var all_value;\n            for (i = 0; i < document.f.all.length; i++) {\n                if (document.f.all[i].checked) {\n                    all_value = document.f.all[i].value;\n                }\n            }\n\n            for (i = 0; i < document.f.client.length; i++) {\n                if (all_value == \"true\") {\n                    document.f.client[i].disabled = true;\n                    document.f.other.disabled = true;\n                        var boxall = document.getElementById('boxall');\n                        var boxspecify = document.getElementById('boxspecify');\n                        boxall.style.background = \"#fffbe2\"\n");
      out.write("\t\t\t\t\t    boxspecify.style.background = \"#F4F4F4\"\n                }\n                else {\n                    document.f.client[i].disabled = false;\n                    document.f.other.disabled = false;\n                        var boxall = document.getElementById('boxall');\n                        var boxspecify = document.getElementById('boxspecify');\n                        boxall.style.background = \"#F4F4F4\"\n\t\t\t\t\t    boxspecify.style.background = \"#fffbe2\"\n                }\n            }\n        }\n\n        function selectCheckbox(boxName) {\n            for (i = 0; i < document.f.client.length; i++) {\n                var box = document.f.client[i];\n                if (box.value == boxName) {\n                    box.checked = !box.checked;\n                }\n            }\n        }\n    </script>\n\n    <style type=\"text/css\">\n    .content {\n        border-color: #bbb;\n        border-style: solid;\n        border-width: 0px 0px 1px 0px;\n    }\n\n    /* Default DOM Tooltip Style */\n    div.domTT {\n        border: 1px solid #bbb;\n");
      out.write("        background-color: #F9F5D5;\n        font-family: arial;\n        font-size: 9px;\n        padding: 5px;\n    }\n\n    div.domTT .caption {\n        font-family: serif;\n        font-size: 12px;\n        font-weight: bold;\n        padding: 1px 2px;\n        color: #FFFFFF;\n    }\n\n    div.domTT .contents {\n        font-size: 12px;\n        font-family: sans-serif;\n        padding: 3px 2px;\n    }\n\n    .textfield {\n        font-size: 11px;\n        font-family: verdana;\n        padding: 3px 2px;\n        background: #efefef;\n    }\n\n    .keyword-field {\n        font-size: 11px;\n        font-family: verdana;\n        padding: 3px 2px;\n    }\n    </style>\n    <style type=\"text/css\">\n        @import \"style/style.css\";\n    </style>\n</head>\n\n<body>\n\n");
 if (submit || remove) { 
      out.write("\n\n<div class=\"success\">\n  ");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\n</div>\n<br>\n");
 }
      out.write("\n\n\n\n<form name=\"f\" action=\"permitted-clients.jsp\" method=\"post\">\n\n    <fieldset style=\"display: block;width:620px;\">\n    <legend>");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</legend>\n\n    <div class=\"clientscontent\">\n\n\t\t<div class=\"permitclientbox permitclientActive\" id=\"boxall\">\n\t\t<input type=\"radio\" name=\"all\" value=\"true\" onclick=\"disableAll();\" ");
      out.print( clients.contains("all") ? "checked" : "");
      out.write(" /><strong>");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</strong> - ");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("\n\t\t</div>\n\n\t\t<div class=\"permitclientbox\" id=\"boxspecify\">\n\t\t<input type=\"radio\" name=\"all\" value=\"false\" onclick=\"disableAll();\" ");
      out.print( !clients.contains("all") ? "checked" : "");
      out.write(" /><strong>");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</strong><br>\n\t\t\t<div class=\"specifyclients\">\n                <table border=\"0\">\n                    <tr>\n\t\t\t\t        <td valign=\"top\" nowrap>\n                            <div style=\"display: block; width: 260px;\">\n                               ");

                                int count = 0;
                                for (Clients client : Clients.values()) {
                                count++;
                                    if (count == 6) {
                                
      out.write("\n                            </div>\n                        </td>\n                        <td valign=\"top\" nowrap>\n                            <div style=\"display: block; width: 205px;\">\n                                    ");
 } 
      out.write("\n                            <label for=\"");
      out.print( client.getName() );
      out.write("\"><input type=\"checkbox\" name=\"client\" value=\"");
      out.print( client.getName() );
      out.write("\" id=\"");
      out.print( client.getName() );
      out.write('"');
      out.write(' ');
      out.print( clients.contains(client.getName()) ? "checked" : "");
      out.write(" /> <img src=\"");
      out.print( client.getImage() );
      out.write("\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"> <strong>");
      out.print( client.getName() );
      out.write("</strong></label><span>(<a href=\"");
      out.print( client.getURL() );
      out.write("\" target=\"_blank\">");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</a>)</span><br>\n                                ");
 } 
      out.write("\n                            </div>\n                        </td>\n\t\t\t\t    </tr>\n\t\t\t\t</table>\n\n            <span class=\"horizontalrule\" style=\"height:1px;\"></span>\n\n            <strong>");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write(":</strong>\n            <a onmouseover=\"domTT_activate(this, event, 'content', '");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("', 'trail', true, 'direction', 'northeast', 'width', '220');\"><img src=\"images/icon_help_14x14.gif\" align=\"texttop\" /></a><br>\n\t\t\t<input type=\"text\" name=\"other\" style=\"width: 160px;\">&nbsp;<input type=\"submit\" name=\"addOther\" value=\"");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("\"/><br>\n            ");
 for (String otherClient : otherClients) { 
      out.write("\n                ");
      out.print( otherClient);
      out.write("&nbsp(<a href=\"permitted-clients.jsp?removeClient=");
      out.print(otherClient);
      out.write("\" name=\"removeClient\" id=\"");
      out.print( otherClient );
      out.write('"');
      out.write('>');
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</a>)<br>\n            ");
 } 
      out.write("\n\n            </div>\n\n        </div>\n\n\t</div>\n\n    </fieldset>\n\n\n\n    <input type=\"submit\" name=\"submit\" value=\"");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("\" style=\"clear: both; margin-top: 15px;\"/>\n\n</form>\n\n<script type=\"text/javascript\">\n    disableAll();\n</script>\n\n</body>\n</html>\n\n");
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
    _jspx_th_fmt_message_0.setKey("permitted.client.title");
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
    _jspx_th_fmt_message_1.setKey("permitted.client.success");
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
    _jspx_th_fmt_message_2.setKey("permitted.client.legend");
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
    _jspx_th_fmt_message_3.setKey("permitted.client.all.clients");
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
    _jspx_th_fmt_message_4.setKey("permitted.client.all.clients.description");
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
    _jspx_th_fmt_message_5.setKey("permitted.client.specific.clients");
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
    _jspx_th_fmt_message_6.setKey("permitted.client.website");
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
    _jspx_th_fmt_message_7.setKey("permitted.client.add.other.client");
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
    _jspx_th_fmt_message_8.setKey("permitted.client.tooltip");
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
    _jspx_th_fmt_message_9.setKey("permitted.client.add");
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
    _jspx_th_fmt_message_10.setKey("permitted.client.remove");
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
    _jspx_th_fmt_message_11.setKey("permitted.client.save.settings");
    int _jspx_eval_fmt_message_11 = _jspx_th_fmt_message_11.doStartTag();
    if (_jspx_th_fmt_message_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_11);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_11);
    return false;
  }
}
