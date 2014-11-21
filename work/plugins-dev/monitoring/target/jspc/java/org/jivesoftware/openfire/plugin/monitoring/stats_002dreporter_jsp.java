package org.jivesoftware.openfire.plugin.monitoring;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.reporting.stats.StatsViewer;
import org.jivesoftware.util.CookieUtils;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.stats.Statistic;
import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jivesoftware.openfire.plugin.MonitoringPlugin;
import org.jivesoftware.openfire.XMPPServer;

public final class stats_002dreporter_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    private StatsViewer statsViewer;

    public static final String STAT_DEFAULT = "sessions";
    public static final String COOKIE_TIMEPERIOD = "openfire-reporting-timeperiod";
    public StatsViewer getStatsViewer() {
        if (statsViewer == null) {
            MonitoringPlugin plugin = (MonitoringPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("monitoring");
            statsViewer = (StatsViewer) plugin.getModule(StatsViewer.class);
        }
        return statsViewer;
    }
    /**
     * Sorts Statistics by Name.
     */
    final Comparator<String> statisticComporator = new Comparator<String>() {
        public int compare(String stat1, String stat2) {
            String statName1 = getStatsViewer().getStatistic(stat1)[0].getName();
            String statName2 = getStatsViewer().getStatistic(stat2)[0].getName();

            return statName1.toLowerCase().compareTo(statName2.toLowerCase());
        }
    };

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_setLocale_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_fmt_message_key_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_fmt_setLocale_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_fmt_message_key_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_fmt_setLocale_value_nobody.release();
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

    List<String> statList = Arrays.asList(getStatsViewer().getAllHighLevelStatKeys());
    Collections.sort(statList, statisticComporator);

    String dateRangeType = ParamUtils.getParameter(request,"dateRangeType");
    String dateRangePreset = ParamUtils.getParameter(request,"dateRangePreset");
    String fromDateParam = ParamUtils.getParameter(request,"fromDate");
    String toDateParam = ParamUtils.getParameter(request,"toDate");

    String timePeriod = "last60minutes";
    Cookie timePeriodCookie = CookieUtils.getCookie(request, COOKIE_TIMEPERIOD);
    if (timePeriodCookie != null) {
        String cookieValue = timePeriodCookie.getValue();
        if (cookieValue.startsWith("this") || cookieValue.startsWith("last")) {
            dateRangeType = "preset";
            dateRangePreset = cookieValue;
        }
        else {
            String[] dates = cookieValue.split("to");
            dateRangeType = "specific";
            if (dates.length == 2) {
                fromDateParam = dates[0];
                toDateParam = dates[1];
            }
        }
        timePeriod = cookieValue;
    }

    // Set parameter defaults
    if (dateRangeType == null) {
        dateRangeType = "preset";
    }


      out.write('\n');
      out.write('\n');
      //  fmt:setLocale
      org.apache.taglibs.standard.tag.rt.fmt.SetLocaleTag _jspx_th_fmt_setLocale_0 = (org.apache.taglibs.standard.tag.rt.fmt.SetLocaleTag) _jspx_tagPool_fmt_setLocale_value_nobody.get(org.apache.taglibs.standard.tag.rt.fmt.SetLocaleTag.class);
      _jspx_th_fmt_setLocale_0.setPageContext(_jspx_page_context);
      _jspx_th_fmt_setLocale_0.setParent(null);
      _jspx_th_fmt_setLocale_0.setValue( JiveGlobals.getLocale().getLanguage() );
      int _jspx_eval_fmt_setLocale_0 = _jspx_th_fmt_setLocale_0.doStartTag();
      if (_jspx_th_fmt_setLocale_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_fmt_setLocale_value_nobody.reuse(_jspx_th_fmt_setLocale_0);
        return;
      }
      _jspx_tagPool_fmt_setLocale_value_nobody.reuse(_jspx_th_fmt_setLocale_0);
      out.write("\n\n<html>\n<head>\n    <title>");
      if (_jspx_meth_fmt_message_0(_jspx_page_context))
        return;
      out.write("</title>\n    <meta name=\"pageID\" content=\"stats-reporter\"/>\n    <script src=\"/js/prototype.js\" type=\"text/javascript\"></script>\n    <script src=\"/js/effects.js\" type=\"text/javascript\"></script>\n    <script src=\"/js/scriptaculous.js\" type=\"text/javascript\"></script>\n\n    <style type=\"text/css\">\n        .dateerror { font-weight: bold; color:red;}\n        .dateerrorinput {background-color:red};\n        .datenormal { font-weight: normal; color:black;}\n    </style>\n\n    <style type=\"text/css\">@import url( /js/jscalendar/calendar-win2k-cold-1.css );</style>\n    <script type=\"text/javascript\" src=\"/js/jscalendar/calendar.js\"></script>\n    <script type=\"text/javascript\" src=\"/js/jscalendar/i18n.jsp\"></script>\n    <script type=\"text/javascript\" src=\"/js/jscalendar/calendar-setup.js\"></script>\n\n    <script type=\"text/javascript\">\n\n        var datesAreValid = true;\n\n        var stats = {};\n        ");

        for (String statKey : statList) { 
 Statistic stat = getStatsViewer().getStatistic(statKey)[0]; 
      out.write("\n            stats[\"");
      out.print( statKey );
      out.write("\"] = {\"name\":\"");
      out.print( stat.getName() );
      out.write("\",\"description\":\"");
      out.print( stat.getDescription() );
      out.write("\"};\n        ");
 } 
      out.write("\n\n        var currentStat = '");
      out.print( STAT_DEFAULT );
      out.write("';\n        var currentTimePeriod = '");
      out.print( timePeriod );
      out.write("';\n        function viewStat(stat) {\n            timePeriod = '';\n            timePeriodName = '';\n            if ($('drt01').checked == true) {\n                // get a preset value\n                drselect = $('dateRangePreset');\n                timePeriod = drselect[drselect.selectedIndex].value;\n                timePeriodName = drselect[drselect.selectedIndex].text;\n                datesAreValid = true;\n            } else {\n                // get a date range\n                validateStartAndEndDate();\n                if (datesAreValid) {\n                    timePeriod = $('fromDate').value + 'to' + $('toDate').value;\n                    timePeriodName = $('fromDate').value + ' to ' + $('toDate').value;\n                } else {\n                    return;\n                }\n            }\n\n            if (datesAreValid && (stat != currentStat || timePeriod != currentTimePeriod)) {\n                var viewElement = $('viewer');\n                var pdfViewElement = $('pdfviewer');\n                var pdfViewAllElement = $('pdfviewerall');\n");
      out.write("                viewElement.style.display = \"none\";\n                var i = new Image();\n                i.onload = function() {\n                    viewElement.src = i.src;\n                    pdfViewElement.href = i.src + \"&pdf=true\";\n                    pdfViewAllElement.href = i.src + \"&pdf=all\";\n                    $('graph-header').innerHTML = stats[stat].name + ': ' + timePeriodName;\n                    $('graph-description').innerHTML = '<b>' + stats[stat].name + '</b><br /><br />' +\n                            stats[stat].description;\n                    Effect.Appear('viewer');\n                    currentStat = stat;\n                    currentTimePeriod = timePeriod;\n                    createCookie(\"");
      out.print( COOKIE_TIMEPERIOD );
      out.write("\",currentTimePeriod,1000);\n                }\n                var d = new Date();\n                var t = d.getTime()\n                i.src = \"graph?stat=\" + stat + \"&date=\" + t + '&timeperiod=' + timePeriod + '&width=500&height=250';\n            }\n        }\n\n\n        function createCookie(name,value,days) {\n            if (days) {\n\t\t        var date = new Date();\n                date.setTime(date.getTime()+(days*24*60*60*1000));\n                var expires = \"; expires=\"+date.toGMTString();\n            }\n            else {\n                var expires = \"\";\n            }\n            document.cookie = name+\"=\"+value+expires+\"; path=/\";\n        }\n\n        function writeTimePeriod() {\n            if ($('drt01').checked == true) {\n                drselect = $('dateRangePreset');\n                document.write(drselect[drselect.selectedIndex].text);\n            }\n            else {\n                // get a date range\n                if ($('fromDate').value != '' && $('toDate').value != '') {\n                    document.write($('fromDate').value + ' to ' + $('toDate').value);\n");
      out.write("                }\n            }\n        }\n\n        function checkPreset() {\n            document.statsForm.dateRangeType[0].checked=true;\n            document.statsForm.dateRangePreset.disabled = false;\n            document.statsForm.fromDate.disabled = true;\n            document.statsForm.toDate.disabled = true;\n            viewStat(currentStat);\n        }\n\n        function checkSpecific() {\n            document.statsForm.dateRangeType[1].checked=true\n            document.statsForm.fromDate.disabled = false;\n            document.statsForm.toDate.disabled = false;\n            document.statsForm.dateRangePreset.disabled = true;\n            viewStat(currentStat);\n        }\n\n        function validateStartAndEndDate() {\n            if ($('fromDate').value != '' && $('toDate').value != '') {\n                fromDate = $('fromDate').value;\n                toDate = $('toDate').value;\n\n                if (!isValidDate(fromDate)) {\n                    $('fromDateTitle').className = 'dateerror';\n                    $('fromDate').className = 'dateerrorinput';\n");
      out.write("                    datesAreValid = false;\n                    return;\n                }\n\n                if (!isValidDate(toDate)) {\n                    $('toDateTitle').className = 'dateerror';\n                    $('toDate').className = 'dateerrorinput';\n                    datesAreValid = false;\n                    return;\n                }\n\n                if (!isValidCombination(fromDate, toDate)) {\n                    $('toDateTitle').className = 'dateerror';\n                    $('fromDateTitle').className = 'dateerror';\n                    $('toDate').className = 'dateerrorinput';\n                    $('fromDate').className = 'dateerrorinput';\n                    datesAreValid = false;\n                    return;\n                }\n\n                datesAreValid = true;\n                $('toDate').className = '';\n                $('fromDate').className = '';\n                $('toDateTitle').className = 'datenormal';\n                $('fromDateTitle').className = 'datenormal';\n                return;\n            }\n");
      out.write("            else {\n                datesAreValid = false;\n                return;\n            }\n        }\n\n        function isValidCombination(startdate, enddate) {\n            if (!getDate(startdate) || !getDate(enddate)) {\n                return false;\n            }\n            else {\n                return getDate(startdate) < getDate(enddate);\n            }\n        }\n\n        function getDate(datestring) {\n            dateSplit = datestring.split('/');\n            if (dateSplit.length < 3) {\n                return false;\n            }\n\n            var monthLength = new Array(31,28,31,30,31,30,31,31,30,31,30,31);\n            var day = parseInt(dateSplit[1]);\n\t        var month = parseInt(dateSplit[0]);\n\t        var year = parseInt(dateSplit[2]);\n\n            if (!day || !month || !year)\n                return false;\n\n            year = year + 2000;\n\n            if (year/4 == parseInt(year/4))\n                monthLength[1] = 29;\n\n            if (day > monthLength[month-1])\n                return false;\n\n            monthLength[1] = 28;\n");
      out.write("\n            var now = new Date();\n            now = now.getTime(); //NN3\n\n            var dateToCheck = new Date();\n            dateToCheck.setYear(year);\n            dateToCheck.setMonth(month-1);\n            dateToCheck.setDate(day);\n            var checkDate = dateToCheck.getTime();\n            if (now < checkDate) {\n                return false;\n            }\n            else {\n                return checkDate;\n            }\n        }\n\n        function isValidDate(datestring) {\n            d = getDate(datestring);\n            if (!d) {\n                return false;\n            }\n            else {\n                var now = new Date();\n                now = now.getTime(); //NN3\n                if (now < d) {\n                    return false;\n                } else {\n                    return true;\n                }\n            }\n        }\n    </script>\n    <style type=\"text/css\">\n\t    @import \"style/style.css\";\n    </style>\n</head>\n\n<body>\n\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"753\">\n<tr>\n");
      out.write("    <td width=\"180\" valign=\"top\">\n        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"180\" class=\"jive-table\">\n        <thead>\n        <tr>\n            <th>\n                ");
      if (_jspx_meth_fmt_message_1(_jspx_page_context))
        return;
      out.write("\n            </th>\n        </tr>\n        </thead>\n        <tr style=\"border-bottom: none\"><form action=\"\" id=\"statsForm\" name=\"statsForm\">\n            <td colspan=\"2\" valign=\"top\" width=\"180\">\n                <table>\n                    <tr>\n                        <td width=\"1%\" valign=\"top\">\n                            <input type=\"radio\" name=\"dateRangeType\" value=\"preset\" id=\"drt01\"\n                             onclick=\"checkPreset();\"\n                             ");
      out.print( ("preset".equals(dateRangeType) ? "checked" : "") );
      out.write(">\n                        </td>\n                        <td width=\"99%\" valign=\"top\">\n                            <label for=\"drt01\">");
      if (_jspx_meth_fmt_message_2(_jspx_page_context))
        return;
      out.write("</label></td>\n                    </tr>\n                    <tr>\n                        <td colspan=\"2\" align=\"right\">\n                            <select size=\"1\" name=\"dateRangePreset\" id=\"dateRangePreset\" onchange=\"checkPreset();\">\n                                <option value=\"last60minutes\"\n                                        ");
      out.print( ("last60minutes".equals(dateRangePreset) ? "selected" : "") );
      out.write("\n                                    >");
      if (_jspx_meth_fmt_message_3(_jspx_page_context))
        return;
      out.write(" </option>\n                                <option value=\"last24hours\"\n                                        ");
      out.print( ("last24hours".equals(dateRangePreset) ? "selected" : "") );
      out.write("\n                                    >");
      if (_jspx_meth_fmt_message_4(_jspx_page_context))
        return;
      out.write(" </option>\n                                <option value=\"thisweek\"\n                                        ");
      out.print( ("thisweek".equals(dateRangePreset) ? "selected" : "") );
      out.write("\n                                    >");
      if (_jspx_meth_fmt_message_5(_jspx_page_context))
        return;
      out.write(" </option>\n                                <option value=\"last7days\"\n                                        ");
      out.print( ("last7days".equals(dateRangePreset) ? "selected" : "") );
      out.write("\n                                    >");
      if (_jspx_meth_fmt_message_6(_jspx_page_context))
        return;
      out.write(" </option>\n                                <option value=\"lastweek\"\n                                        ");
      out.print( ("lastweek".equals(dateRangePreset) ? "selected" : "") );
      out.write("\n                                    >");
      if (_jspx_meth_fmt_message_7(_jspx_page_context))
        return;
      out.write(" </option>\n                                <option value=\"thismonth\"\n                                        ");
      out.print( ("thismonth".equals(dateRangePreset) ? "selected" : "") );
      out.write("\n                                    >");
      if (_jspx_meth_fmt_message_8(_jspx_page_context))
        return;
      out.write(" </option>\n                                <option value=\"lastmonth\"\n                                        ");
      out.print( ("lastmonth".equals(dateRangePreset) ? "selected" : "") );
      out.write("\n                                    >");
      if (_jspx_meth_fmt_message_9(_jspx_page_context))
        return;
      out.write(" </option>\n                                <option value=\"last3months\"\n                                        ");
      out.print( ("last3months".equals(dateRangePreset) ? "selected" : "") );
      out.write("\n                                    >");
      if (_jspx_meth_fmt_message_10(_jspx_page_context))
        return;
      out.write(" </option>\n                            </select>\n                        </td>\n                    </tr>\n                    <tr valign=\"top\">\n                        <td width=\"1%\" valign=\"top\">\n                            <input type=\"radio\" name=\"dateRangeType\" value=\"specific\" id=\"drt02\"\n                             onclick=\"checkSpecific();\"\n                             ");
      out.print( ("specific".equals(dateRangeType) ? "checked" : "") );
      out.write(">\n                        </td>\n                        <td width=\"99%\"><label for=\"drt02\">");
      if (_jspx_meth_fmt_message_11(_jspx_page_context))
        return;
      out.write("</label></td>\n                    </tr>\n                    <tr valign=\"top\">\n                        <td colspan=\"2\" align=\"right\">\n                            <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                            <tr>\n                                <td align=\"right\" id=\"fromDateTitle\" class=\"datenormal\">\n                                    ");
      if (_jspx_meth_fmt_message_12(_jspx_page_context))
        return;
      out.write("\n                                </td>\n                                <td>\n                                    <input type=\"text\" size=\"10\" name=\"fromDate\" id=\"fromDate\" maxlength=\"10\"\n                                     onclick=\"checkSpecific();\"\n                                     onchange=\"viewStat(currentStat);\"\n                                     ");
      out.print( ("specific".equals(dateRangeType) ? "" : "disabled") );
      out.write("\n                                     value=\"");
      out.print( (fromDateParam != null ? fromDateParam : "") );
      out.write("\"\n                                     >\n                                </td>\n                                <td>\n                                    &nbsp;<img src=\"images/icon_calendarpicker.gif\" id=\"fromDateCal\" />\n                                </td>\n                            </tr>\n                            <tr>\n                                <td align=\"right\" id=\"toDateTitle\" class=\"datenormal\">\n                                    ");
      if (_jspx_meth_fmt_message_13(_jspx_page_context))
        return;
      out.write("\n                                </td>\n                                <td>\n                                    <input type=\"text\" size=\"10\" name=\"toDate\" id=\"toDate\" maxlength=\"10\"\n                                     onclick=\"checkSpecific();\"\n                                     onchange=\"viewStat(currentStat);\"\n                                     ");
      out.print( ("specific".equals(dateRangeType) ? "" : "disabled") );
      out.write("\n                                     value=\"");
      out.print( (toDateParam != null ? toDateParam : "") );
      out.write("\">\n                                </td>\n                                <td>\n                                    &nbsp;<img src=\"images/icon_calendarpicker.gif\" id=\"toDateCal\" />\n                                </td>\n                            </tr>\n                            </table>\n                        </td>\n                    </tr>\n                </table>\n            </td>\n        </form></tr>\n        </table>\n        <br />\n        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"180\" class=\"jive-table\"\n               style=\"border-bottom: 1px solid #bbb;\">\n        <thead>\n        <tr>\n            <th>\n                ");
      if (_jspx_meth_fmt_message_14(_jspx_page_context))
        return;
      out.write("\n            </th>\n        </tr>\n        </thead>\n        <tbody>\n        ");
 for (String stat : statList) { 
      out.write("\n        <tr\n            id=\"statDetail");
      out.print( stat );
      out.write("\"\n            ");
 if (stat.equalsIgnoreCase(STAT_DEFAULT)) { 
      out.write("\n                class=\"allreports_report_selected\"\n            ");
 }
               else { 
      out.write("\n                class=\"allreports_report_default\"\n            ");
 } 
      out.write("\n            >\n            <td valign=\"top\"\n                onclick=\"viewStat('");
      out.print( stat );
      out.write("');toggleSelected('");
      out.print( stat );
      out.write("'); return false;\"\n                onmouseover=\"toggleMouseOver('");
      out.print( stat );
      out.write("');\"\n                onmouseout=\"toggleMouseOver('");
      out.print( stat );
      out.write("');\">\n                ");
      out.print( getStatsViewer().getStatistic(stat)[0].getName());
      out.write("\n            </td>\n        </tr>\n        ");
 } 
      out.write("\n\n        <script type=\"text/javascript\">\n            var selectedStat = '");
      out.print( STAT_DEFAULT );
      out.write("';\n            function toggleSelected(statname) {\n                $('statDetail' + selectedStat).className = 'allreports_report_default';\n                $('statDetail' + statname).className = 'allreports_report_selected';\n                selectedStat = statname;\n            }\n            function toggleMouseOver(statname) {\n                if (statname != selectedStat) {\n                    if ($('statDetail' + statname).className == 'allreports_report_hover') {\n                        $('statDetail' + statname).className = 'allreports_report_default';\n                    }\n                    else {\n                        $('statDetail' + statname).className = 'allreports_report_hover';\n                    }\n                }\n            }\n\n        </script>\n\n        </tbody>\n        </table>\n\n        <br />\n        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"180\" class=\"jive-table\">\n        <thead>\n        <tr>\n            <th style=\"border-bottom:none\">\n                <table cellspacing='0' cellpadding='0' border='0' align=\"center\" style=\"padding:0px;border-bottom:none\">\n");
      out.write("                <tr>\n                    <td colspan=\"2\" style=\"padding:0px;border-bottom:none; font-size: 12px;\">\n                        <b>");
      if (_jspx_meth_fmt_message_15(_jspx_page_context))
        return;
      out.write("</b>\n                    </td>\n                    <td style=\"padding:0px;border-bottom:none\"><img\n                            src=\"images/blank.gif\" alt=\"\" border=\"0\" height=\"1\" width=\"1\" /></td>\n                </tr>\n                </table>\n            </th>\n        </tr>\n        <tr>\n            <th style=\"border-bottom : 1px #ccc solid;\">\n                <table cellspacing='0' cellpadding='0' border='0' align=\"center\" style=\"padding:0px;border-bottom:none\">\n                <tr>\n                    <td style=\"padding:0px;border-bottom:none; font-size: 12px;\"><img\n                            src=\"images/icon_pdf.gif\"\n                            alt=\"");
      if (_jspx_meth_fmt_message_16(_jspx_page_context))
        return;
      out.write("\" border=\"0\" /></td>\n                    <td style=\"padding:0px;border-bottom:none; font-size: 12px;\">&nbsp;\n                        <a target=\"_blank\" href=\"graph?stat=");
      out.print( STAT_DEFAULT );
      out.write("&timeperiod=");
      out.print( timePeriod );
      out.write("&pdf=all\"\n                           id=\"pdfviewerall\">");
      if (_jspx_meth_fmt_message_17(_jspx_page_context))
        return;
      out.write("</a></td>\n                </tr>\n                </table>\n            </th>\n        </thead>\n        </table>\n\n\n    </td>\n    <td width=\"20\">&nbsp;</td>\n    <td valign=\"top\" width=\"553\">\n        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"553\" class=\"jive-table\">\n        <thead>\n        <tr>\n            <th width=\"70%\" id=\"graph-header\">\n                ");
      out.print( getStatsViewer().getStatistic(STAT_DEFAULT)[0].getName() );
      out.write(":\n                <script type=\"text/javascript\">writeTimePeriod();</script>\n            </th>\n            <th style=\"text-align:right; border-bottom : 1px #ccc solid; padding:0px;\" nowrap>\n                <table cellspacing='0' cellpadding='0' border='0' align=\"right\" style=\"padding:0px;border-bottom:none;\">\n                <tr>\n                    <td style=\"padding:0px;border-bottom:none;font-size: 11px;\" nowrap>\n                        ");
      if (_jspx_meth_fmt_message_18(_jspx_page_context))
        return;
      out.write("&nbsp;&nbsp;</td>\n                    <td style=\"padding:0px;border-bottom:none;\"><img\n                            src=\"images/icon_pdf.gif\" alt=\"PDF Format\" border=\"0\" /></td>\n                    <td style=\"padding:0px;border-bottom:none;font-size: 11px;\">&nbsp;\n                        <a target=\"_blank\" href=\"graph?stat=");
      out.print( STAT_DEFAULT );
      out.write("&timeperiod=");
      out.print( timePeriod );
      out.write("&pdf=true\"\n                           id=\"pdfviewer\">");
      if (_jspx_meth_fmt_message_19(_jspx_page_context))
        return;
      out.write("</a></td>\n                    <td style=\"padding:0px;border-bottom:none;\"><img\n                            src=\"images/blank.gif\" alt=\"\" border=\"0\" height=\"1\" width=\"8\" /></td>\n                </tr>\n                </table>\n            </th>\n        </tr>\n        </thead>\n        <tr>\n            <td colspan=\"2\" style=\"padding:0px; border-bottom:0px\"><img src=\"/images/blank.gif\" alt=\"\" border=\"0\" height=\"1\" width=\"500\" /></td>\n        </tr>\n        <tr>\n            <td colspan=\"2\">\n                <table class=\"noclass\">\n                <tr>\n                    <td><img src=\"/images/blank.gif\" alt=\"\" border=\"0\" height=\"280\" width=\"1\" /></td>\n                    <td><img id=\"viewer\" src=\"graph?stat=");
      out.print( STAT_DEFAULT );
      out.write("&timeperiod=");
      out.print( timePeriod );
      out.write("&width=500&height=250\" border=\"0\" /></td>\n                </tr>\n\n                <!-- <tr>\n                    <td colspan=\"2\">\n                        <div id=\"graph-description_NEW\" style=\"padding: 10px 10px 20px 15px;\">\n                            ");
      out.print( getStatsViewer().getStatistic(STAT_DEFAULT)[0].getDescription() );
      out.write("\n                        </div>\n                    </td>\n                </tr> -->\n\n                </table>\n            </td>\n        </tr>\n        </table>\n        <br />\n\n        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"553\" class=\"jive-table\">\n        <thead>\n        <tr>\n            <th style=\"border-bottom : 1px #ccc solid\">\n                ");
      if (_jspx_meth_fmt_message_20(_jspx_page_context))
        return;
      out.write("\n            </th>\n        </tr>\n        </thead>\n        <tr>\n            <td align=\"left\" id=\"graph-description\">\n                <b>");
      out.print( getStatsViewer().getStatistic(STAT_DEFAULT)[0].getName() );
      out.write("</b><br /><br />\n\n                ");
      out.print( getStatsViewer().getStatistic(STAT_DEFAULT)[0].getDescription() );
      out.write("\n            </td>\n        </tr>\n        </table>\n    </td>\n</tr>\n</table>\n<br>\n\n<script type=\"text/javascript\" >\n    Calendar.setup(\n    {\n        inputField  : \"fromDate\",       // ID of the input field\n        ifFormat    : \"%m/%d/%y\",       // the date format\n        button      : \"fromDateCal\",    // ID of the button\n        onUpdate    :  viewStat\n    });\n\n    Calendar.setup(\n    {\n        inputField  : \"toDate\",         // ID of the input field\n        ifFormat    : \"%m/%d/%y\",       // the date format\n        button      : \"toDateCal\",      // ID of the button\n        onUpdate    :  viewStat\n    });\n\n</script>\n\n\n</body>\n</html>\n\n");
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
    _jspx_th_fmt_message_0.setKey("allreports.title");
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
    _jspx_th_fmt_message_1.setKey("allreports.daterange");
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
    _jspx_th_fmt_message_2.setKey("allreports.daterange.preset");
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
    _jspx_th_fmt_message_3.setKey("allreports.daterange.preset.last60minutes");
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
    _jspx_th_fmt_message_4.setKey("allreports.daterange.preset.last24hours");
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
    _jspx_th_fmt_message_5.setKey("allreports.daterange.preset.thisweek");
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
    _jspx_th_fmt_message_6.setKey("allreports.daterange.preset.last7days");
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
    _jspx_th_fmt_message_7.setKey("allreports.daterange.preset.lastweek");
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
    _jspx_th_fmt_message_8.setKey("allreports.daterange.preset.thismonth");
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
    _jspx_th_fmt_message_9.setKey("allreports.daterange.preset.lastmonth");
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
    _jspx_th_fmt_message_10.setKey("allreports.daterange.preset.last3months");
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
    _jspx_th_fmt_message_11.setKey("allreports.daterange.specific");
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
    _jspx_th_fmt_message_12.setKey("allreports.daterange.specific.startdate");
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
    _jspx_th_fmt_message_13.setKey("allreports.daterange.specific.enddate");
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
    _jspx_th_fmt_message_14.setKey("allreports.selectreport");
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
    _jspx_th_fmt_message_15.setKey("allreports.download.allreports");
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
    _jspx_th_fmt_message_16.setKey("allreports.download.allreports.pdf.format");
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
    _jspx_th_fmt_message_17.setKey("allreports.download.allreports.pdf");
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
    _jspx_th_fmt_message_18.setKey("allreports.download.singlereport");
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
    _jspx_th_fmt_message_19.setKey("allreports.download.singlereport.pdf");
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
    _jspx_th_fmt_message_20.setKey("allreports.reportinformation");
    int _jspx_eval_fmt_message_20 = _jspx_th_fmt_message_20.doStartTag();
    if (_jspx_th_fmt_message_20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_20);
      return true;
    }
    _jspx_tagPool_fmt_message_key_nobody.reuse(_jspx_th_fmt_message_20);
    return false;
  }
}
