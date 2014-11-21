package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.xmpp.component.ComponentManagerFactory;
import org.xmpp.packet.JID;
import java.util.Iterator;
import java.util.List;
import org.jivesoftware.util.Log;
import org.jivesoftware.util.StringUtils;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.xmpp.workgroup.UnauthorizedException;
import org.jivesoftware.xmpp.workgroup.DbProperties;

public final class uploadSounds_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


     private String encode(byte[] data) {
         try {
             final String encodedFile = StringUtils.encodeBase64(data);
             return encodedFile;
         }
         catch (Exception ex) {
             Log.error(ex);
         }
         return null;
     }

     private void saveSetting(String encodedFile, String setting, String workgroupName) {
         final JID workgroupJID = new JID(workgroupName);
         Workgroup workgroup = null;
         try {
             workgroup = WorkgroupManager.getInstance().getWorkgroup(workgroupJID);
         }
         catch (UserNotFoundException e) {
             Log.error(e);
             return;
         }

         DbProperties props = workgroup.getProperties();
         try {
             props.setProperty(setting, encodedFile);
         }
         catch (UnauthorizedException e) {
             e.printStackTrace();
         }
         workgroup.imagesChanged();
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

      out.write('\n');

    DiskFileUpload upload = new DiskFileUpload();
    List items = null;
    try {
        items = upload.parseRequest(request);
    }
    catch (FileUploadException e) {
        e.printStackTrace();
    }

    String workgroup = request.getParameter("wgID");
    if (!ModelUtil.hasLength(workgroup)) {
        workgroup = (String)request.getSession().getAttribute("workgroup");
    }

    Iterator iter = items.iterator();
    byte[] data = null;
    while (iter.hasNext()) {
        FileItem item = (FileItem)iter.next();

        if (!item.isFormField()) {
            String setting = item.getFieldName();
            String filename = item.getName();
            data = item.get();

            if (filename != null && filename.trim().length() > 0) {
                // Know let's encode the file.
                final String encodedFile = encode(data);
                saveSetting(encodedFile, setting, workgroup);
            }
        }
    }

    // Go back to workgroup-sound-settings.jsp
    try {
        response.sendRedirect("workgroup-sound-settings.jsp?wgID=" + workgroup + "&updated=true");
    }
    catch (Exception ex) {
        Log.error(ex);
    }

      out.write('\n');
      out.write('\n');
      out.write(' ');
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
