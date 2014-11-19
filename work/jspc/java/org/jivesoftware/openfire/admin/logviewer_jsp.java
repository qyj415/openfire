package org.jivesoftware.openfire.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.*;
import org.jivesoftware.util.*;
import java.text.*;
import java.net.URLEncoder;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.openfire.user.*;
import java.util.*;

public final class logviewer_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    static final String NONE = LocaleUtils.getLocalizedString("global.none");

    static final String ERROR = "error";
    static final String INFO = "info";
    static final String WARN = "warn";
    static final String DEBUG = "debug";
    static final String DEFAULT = ERROR;

    static final String ASCENDING = "asc";
    static final String DESCENDING = "desc";

    static final String[] LINES = {"50","100","250","500"};

    static final String[] REFRESHES = {NONE,"10","30","60","90"};

    private static HashMap parseCookie(Cookie cookie) {
        if (cookie == null || cookie.getValue() == null) {
            return new HashMap();
        }
        StringTokenizer tokenizer = new StringTokenizer(cookie.getValue(),"&");
        HashMap<String, String> valueMap = new HashMap<String, String>();
        while (tokenizer.hasMoreTokens()) {
            String tok = tokenizer.nextToken();
            int pos = tok.indexOf("=");
            if (pos > 0) {
                String name = tok.substring(0,pos);
                String value = tok.substring(pos+1,tok.length());
                valueMap.put(name,value);
            }
        }
        return valueMap;
    }

    private static void saveCookie(HttpServletResponse response, HashMap cookie) {
        StringBuffer buf = new StringBuffer();
        for (Iterator iter=cookie.keySet().iterator(); iter.hasNext();) {
            String name = (String)iter.next();
            String value = (String)cookie.get(name);
            buf.append(name).append("=").append(value);
            if (iter.hasNext()) {
                buf.append("&");
            }
        }
        Cookie newCookie = new Cookie("jiveforums.admin.logviewer",buf.toString());
        newCookie.setPath("/");
        newCookie.setMaxAge(60*60*24*30); // one month
        response.addCookie(newCookie);
    }

    private static HashMap getLogUpdate(HttpServletRequest request, HttpServletResponse response,
            File logDir)
    {
        // Get the cookie associated with the log files
        HashMap cookie = parseCookie(CookieUtils.getCookie(request,"jiveforums.admin.logviewer"));
        String[] logs = {"error", "info", "warn", "debug"};
        HashMap<String,String> newCookie = new HashMap<String,String>();
        HashMap<String,String> updates = new HashMap<String,String>();
        for (String log : logs) {
            // Check for the value in the cookie:
            String key = log + ".size";
            long savedSize = 0L;
            if (cookie.containsKey(key)) {
                try {
                    savedSize = Long.parseLong((String) cookie.get(key));
                }
                catch (NumberFormatException nfe) {
                }
            }
            // Update the size in the Map:
            File logFile = new File(logDir, log + ".log");
            long currentSize = logFile.length();
            newCookie.put(key, "" + currentSize);
            if (currentSize != savedSize) {
                updates.put(log, "true");
            }
        }
        saveCookie(response, newCookie);
        return updates;
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

      out.write("\n\n\n\n\n\n");
      org.jivesoftware.admin.AdminPageBean pageinfo = null;
      synchronized (request) {
        pageinfo = (org.jivesoftware.admin.AdminPageBean) _jspx_page_context.getAttribute("pageinfo", PageContext.REQUEST_SCOPE);
        if (pageinfo == null){
          pageinfo = new org.jivesoftware.admin.AdminPageBean();
          _jspx_page_context.setAttribute("pageinfo", pageinfo, PageContext.REQUEST_SCOPE);
        }
      }
      out.write('\n');
      out.write('\n');
      org.jivesoftware.util.WebManager admin = null;
      synchronized (_jspx_page_context) {
        admin = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("admin", PageContext.PAGE_SCOPE);
        if (admin == null){
          admin = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("admin", admin, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
 admin.init(request, response, session, application, out ); 
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');

    // Get parameters
    String log = ParamUtils.getParameter(request, "log");
    String numLinesParam = ParamUtils.getParameter(request,"lines");
    int numLines = ParamUtils.getIntParameter(request,"lines",50);
    int refresh = ParamUtils.getIntParameter(request,"refresh",10);
    String refreshParam = ParamUtils.getParameter(request,"refresh");
    String mode = ParamUtils.getParameter(request,"mode");
    boolean clearLog = ParamUtils.getBooleanParameter(request,"clearLog");
    boolean markLog = ParamUtils.getBooleanParameter(request,"markLog");
    boolean saveLog = ParamUtils.getBooleanParameter(request,"saveLog");
    boolean emailLog = ParamUtils.getBooleanParameter(request,"emailLog");
    boolean debugEnabled = ParamUtils.getBooleanParameter(request,"debugEnabled");
    boolean wasDebugEnabled = ParamUtils.getBooleanParameter(request,"wasDebugEnabled");

    // Enable/disable debugging
    if (request.getParameter("wasDebugEnabled") != null && wasDebugEnabled != debugEnabled) {
        Log.setDebugEnabled(debugEnabled);
        // Log the event
        admin.logEvent((debugEnabled ? "enabled" : "disabled")+" debug logging", null);
        response.sendRedirect("logviewer.jsp?log=debug");
        return;
    }

    // Santize variables to prevent vulnerabilities
    if (log != null) {
        log = StringUtils.escapeHTMLTags(log);
    }
    debugEnabled = Log.isDebugEnabled();
    User pageUser = admin.getUser();

    if (clearLog && log != null) {
        if ("error".equals(log)) {
            Log.rotateErrorLogFile();
        }
        else if ("warn".equals(log)) {
            Log.rotateWarnLogFile();
        }
        else if ("info".equals(log)) {
            Log.rotateInfoLogFile();
        }
        else if ("debug".equals(log)) {
            Log.rotateDebugLogFile();
        }
        response.sendRedirect("logviewer.jsp?log=" + log);
        return;
    }
    else if (markLog && log != null) {
        if ("error".equals(log)) {
            Log.markErrorLogFile(pageUser.getUsername());
        }
        else if ("warn".equals(log)) {
            Log.markWarnLogFile(pageUser.getUsername());
        }
        else if ("info".equals(log)) {
            Log.markInfoLogFile(pageUser.getUsername());
        }
        else if ("debug".equals(log)) {
            Log.markDebugLogFile(pageUser.getUsername());
        }
        response.sendRedirect("logviewer.jsp?log=" + log);
        return;
    }
    else if (saveLog && log != null) {
        saveLog = false;
        response.sendRedirect(request.getContextPath() + "/servlet/JiveServlet/?log=" + log);
        return;
    }
    else if (emailLog && log != null) {
        response.sendRedirect("emaillog.jsp?log=" + log);
        return;
    }

    // Set defaults
    if (log == null) {
        log = DEFAULT;
    }
    if (mode == null) {
        mode = ASCENDING;
    }
    if (numLinesParam == null) {
        numLinesParam = "50";
    }

    // Other vars
    File logDir = new File(Log.getLogDirectory());
    String filename = log + ".log";
    File logFile = new File(logDir, filename);

    // Determine if any of the log files contents have been updated:
    HashMap newlogs = getLogUpdate(request, response, logDir);

      out.write("\n\n<html>\n    <head>\n        <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n        <meta name=\"pageID\" content=\"server-logs\"/>\n        <meta name=\"helpPage\" content=\"use_the_server_logs.html\"/>\n    </head>\n    <body>\n\n");
  if (refreshParam != null && !NONE.equals(refreshParam)) { 
      out.write("\n    <meta http-equiv=\"refresh\" content=\"");
      out.print( refresh );
      out.write('"');
      out.write('>');
      out.write('\n');
  } 
      out.write("\n\n<div id=\"logviewer\">\n\n<style type=\"text/css\">\nSELECT, INPUT {\n    font-family : verdana, arial, sans-serif;\n    font-size : 8pt;\n}\n.date {\n    color : #00f;\n    border-width : 0 0 1px 0;\n    border-style : dotted;\n    border-color : #00f;\n}\n.buttons TD {\n    padding : 3px;\n}\n.buttons .icon-label {\n    padding-right : 1em;\n}\n.log-info {\n    border-width : 0 1px 1px 1px;\n    border-color : #ccc;\n    border-style : solid;\n}\nIFRAME {\n    border : 1px #666 solid;\n}\n</style>\n\n<form action=\"logviewer.jsp\" name=\"logViewer\" method=\"get\">\n<input type=\"hidden\" name=\"log\" value=\"");
      out.print( StringUtils.escapeForXML(log) );
      out.write("\">\n\n<div class=\"logviewer\">\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n<tbody>\n    <tr>\n        <td class=\"jive-spacer\" width=\"1%\">&nbsp;</td>\n        <td class=\"jive-tab");
      out.print( (("error".equals(log))?"-active":"") );
      out.write("\" width=\"1%\">\n            <a href=\"logviewer.jsp?log=error\"\n            >");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("</a>\n            <span class=\"new\">\n            ");
      out.print( ((newlogs.containsKey("error"))?"*":"") );
      out.write("\n            </span>\n        </td>\n        <td class=\"jive-spacer\" width=\"1%\">&nbsp;</td>\n        <td class=\"jive-tab");
      out.print( (("warn".equals(log))?"-active":"") );
      out.write("\" width=\"1%\">\n            <a href=\"logviewer.jsp?log=warn\"\n            >");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</a>\n            <span class=\"new\">\n            ");
      out.print( ((newlogs.containsKey("warn"))?"*":"") );
      out.write("\n            </span>\n        </td>\n        <td class=\"jive-spacer\" width=\"1%\">&nbsp;</td>\n        <td class=\"jive-tab");
      out.print( (("info".equals(log))?"-active":"") );
      out.write("\" width=\"1%\">\n            <a href=\"logviewer.jsp?log=info\"\n            >");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write("</a>\n            <span class=\"new\">\n            ");
      out.print( ((newlogs.containsKey("info"))?"*":"") );
      out.write("\n            </span>\n        </td>\n        <td class=\"jive-spacer\" width=\"1%\">&nbsp;</td>\n        <td class=\"jive-tab");
      out.print( (("debug".equals(log))?"-active":"") );
      out.write("\" width=\"1%\">\n            <a href=\"logviewer.jsp?log=debug\"\n            >");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write("</a>\n            <span class=\"new\">\n            ");
      out.print( ((newlogs.containsKey("debug"))?"*":"") );
      out.write("\n            </span>\n        </td>\n        <td class=\"jive-stretch\" width=\"92%\" align=\"right\" nowrap>\n            &nbsp;\n        </td>\n    </tr>\n</tbody>\n</table>\n</div>\n\n");
  ByteFormat byteFormatter = new ByteFormat();
    Date lastMod = new Date(logFile.lastModified());
    DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

      out.write("\n\n<div class=\"log-info\">\n<table cellpadding=\"6\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n<tbody>\n    <tr>\n        <td>\n            <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n            <tr>\n                <td nowrap>");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write("</td>\n                <td nowrap><b>");
      out.print( StringUtils.escapeHTMLTags(logFile.getName()) );
      out.write("</b> (");
      out.print( byteFormatter.format(logFile.length()) );
      out.write(")</td>\n                <td width=\"96%\" rowspan=\"3\">&nbsp;</td>\n                <td nowrap>");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write("</td>\n                <td nowrap>\n                    <input type=\"radio\" name=\"mode\" value=\"asc\"");
      out.print( ("asc".equals(mode)?" checked":"") );
      out.write("\n                     onclick=\"this.form.submit();\" id=\"rb01\"\n                     ><label for=\"rb01\">");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write("</label>\n                    <input type=\"radio\" name=\"mode\" value=\"desc\"");
      out.print( ("desc".equals(mode)?" checked":"") );
      out.write("\n                     onclick=\"this.form.submit();\" id=\"rb02\"\n                     ><label for=\"rb02\">");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write("</label>\n                </td>\n            </tr>\n            <tr>\n                <td nowrap>");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write("</td>\n                <td nowrap>\n                    <span>");
      out.print( dateFormatter.format(lastMod) );
      out.write("</span>\n                </td>\n                <td nowrap>");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write("</td>\n                <td nowrap>\n                    <select name=\"lines\" size=\"1\"\n                     onchange=\"this.form.submit();\">\n                        ");
 for (String aLINES : LINES) {
                            String selected = (aLINES.equals(numLinesParam)) ? " selected" : "";
                        
      out.write("\n                        <option value=\"");
      out.print( aLINES );
      out.write('"');
      out.print( selected );
      out.write('>');
      out.print( aLINES );
      out.write("</option>\n\n                        ");
  } 
      out.write("\n                            <option value=\"All\"");
      out.print( (("All".equals(numLinesParam))?" selected":"") );
      out.write("\n                             >");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("</option>\n                    </select>\n                </td>\n            </tr>\n            <tr>\n                <td colspan=\"2\">\n                    <script language=\"JavaScript\" type=\"text/javascript\">\n                        <!--\n                        function setLog(log) {\n                            document.logViewer.clearLog.value = 'false';\n                            document.logViewer.markLog.value = 'false';\n                            document.logViewer.saveLog.value = 'false';\n                            document.logViewer.emailLog.value = 'false';\n\n                            var t = eval(\"document.logViewer.\" + log);\n                            t.value = 'true';\n                        }\n                        // -->\n                    </script>\n                    <input type=\"hidden\" name=\"clearLog\" value=\"false\">\n                    <input type=\"hidden\" name=\"markLog\" value=\"false\">\n                    <input type=\"hidden\" name=\"saveLog\" value=\"false\">\n                    <input type=\"hidden\" name=\"emailLog\" value=\"false\">\n");
      out.write("                    <div class=\"buttons\">\n                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                    <tbody>\n                        <tr>\n                            <td class=\"icon\">\n                                <a href=\"#\" onclick=\"if (confirm('");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("')) {setLog('clearLog'); document.logViewer.submit(); return true;} else { return false; }\"><img src=\"images/delete-16x16.gif\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\"></a>\n                            </td>\n                            <td class=\"icon-label\">\n                                <a href=\"#\" onclick=\"if (confirm('");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("')) {setLog('clearLog'); document.logViewer.submit(); return true;} else { return false; }\"\n                                 >");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</a>\n                            </td>\n                            <td class=\"icon\">\n                                <a href=\"#\" onclick=\"setLog('markLog'); document.logViewer.submit(); return true;\"><img src=\"images/mark-16x16.gif\" border=\"0\" alt=\"");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\"></a>\n                            </td>\n                            <td class=\"icon-label\">\n                                <a href=\"#\" onclick=\"setLog('markLog'); document.logViewer.submit(); return true;\"\n                                 >");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("</a>\n                            </td>\n                        </tr>\n                    </tbody>\n                    </table>\n                    </div>\n                </td>\n                <td nowrap>");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write(":</td>\n                <td nowrap>\n                    <select size=\"1\" name=\"refresh\" onchange=\"this.form.submit();\">\n                    ");
 for (String aREFRESHES : REFRESHES) {
                        String selected = aREFRESHES.equals(refreshParam) ? " selected" : "";
                    
      out.write("\n                        <option value=\"");
      out.print( aREFRESHES );
      out.write('"');
      out.print( selected );
      out.write('>');
      out.print( aREFRESHES );
      out.write("\n\n                    ");
  } 
      out.write("\n                    </select>\n                    (");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write(")\n                </td>\n            </tr>\n\n            ");
  if ("debug".equals(log)) { 
      out.write("\n\n                <tr>\n                    <td colspan=\"5\">\n\n                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n                        <tr>\n                            <td width=\"1%\" nowrap>\n                                ");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write(": &nbsp;\n                            </td>\n                            <td width=\"1%\">\n                                <input type=\"radio\" name=\"debugEnabled\" value=\"true\"");
      out.print( ((debugEnabled) ? " checked" : "") );
      out.write(" id=\"de01\">\n                            </td>\n                            <td width=\"1%\" nowrap>\n                                <label for=\"de01\">");
      if (_jspx_meth_fmt_message_21(_jspx_page_context))
        return;
      out.write("</label> &nbsp;\n                            </td>\n                            <td width=\"1%\">\n                                <input type=\"radio\" name=\"debugEnabled\" value=\"false\"");
      out.print( ((!debugEnabled) ? " checked" : "") );
      out.write(" id=\"de02\">\n                            </td>\n                            <td width=\"1%\" nowrap>\n                                <label for=\"de02\">Disabled</label> &nbsp;\n                            </td>\n                            <td width=\"1%\">\n                                <input type=\"hidden\" name=\"wasDebugEnabled\" value=\"");
      out.print( debugEnabled );
      out.write("\">\n                                <input type=\"submit\" name=\"\" value=\"");
      if (_jspx_meth_fmt_message_22(_jspx_page_context))
        return;
      out.write("\">\n                            </td>\n                            <td width=\"94%\">&nbsp;</td>\n                        </tr>\n                        </table>\n                    </td>\n                </tr>\n\n            ");
  } 
      out.write("\n\n            </table>\n        </td>\n    </tr>\n</tbody>\n</table>\n</div>\n\n<br>\n\n<span class=\"jive-description\" style=\"color:#666;\">\n");
      if (_jspx_meth_fmt_message_23(_jspx_page_context))
        return;
      out.write(':');
      out.write(' ');
      out.print( JiveGlobals.getHomeDirectory() );
      out.print( File.separator );
      out.write("logs\n</span>\n\n<br><br>\n\n<iframe src=\"log.jsp?log=");
      out.print( URLEncoder.encode(log) );
      out.write("&mode=");
      out.print( URLEncoder.encode(mode) );
      out.write("&lines=");
      out.print( ("All".equals(numLinesParam) ? "All" : String.valueOf(numLines)) );
      out.write("\"\n    frameborder=\"0\" height=\"400\" width=\"100%\" marginheight=\"0\" marginwidth=\"0\" scrolling=\"auto\"></iframe>\n\n</form>\n\n</div>\n\n    </body>\n</html>\n");
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
    _jspx_th_fmt_message_0.setKey("logviewer.title");
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
    _jspx_th_fmt_message_1.setKey("logviewer.error");
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
    _jspx_th_fmt_message_2.setKey("logviewer.warn");
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
    _jspx_th_fmt_message_3.setKey("logviewer.info");
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
    _jspx_th_fmt_message_4.setKey("logviewer.debug");
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
    _jspx_th_fmt_message_5.setKey("logviewer.log");
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
    _jspx_th_fmt_message_6.setKey("logviewer.order");
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
    _jspx_th_fmt_message_7.setKey("logviewer.normal");
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
    _jspx_th_fmt_message_8.setKey("logviewer.reverse");
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
    _jspx_th_fmt_message_9.setKey("logviewer.modified");
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
    _jspx_th_fmt_message_10.setKey("logviewer.line");
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
    _jspx_th_fmt_message_11.setKey("logviewer.all");
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
    _jspx_th_fmt_message_12.setKey("logviewer.confirm");
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
    _jspx_th_fmt_message_13.setKey("logviewer.alt_clear");
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
    _jspx_th_fmt_message_14.setKey("logviewer.confirm");
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
    _jspx_th_fmt_message_15.setKey("logviewer.clear");
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
    _jspx_th_fmt_message_16.setKey("logviewer.alt_mark");
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
    _jspx_th_fmt_message_17.setKey("logviewer.mark");
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
    _jspx_th_fmt_message_18.setKey("global.refresh");
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
    _jspx_th_fmt_message_19.setKey("global.seconds");
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
    _jspx_th_fmt_message_20.setKey("logviewer.debug_log");
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
    _jspx_th_fmt_message_21.setKey("logviewer.enabled");
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
    _jspx_th_fmt_message_22.setKey("global.save_changes");
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
    _jspx_th_fmt_message_23.setKey("logviewer.log_dir");
    int _jspx_eval_fmt_message_23 = _jspx_th_fmt_message_23.doStartTag();
    if (_jspx_th_fmt_message_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_23);
    return false;
  }
}
