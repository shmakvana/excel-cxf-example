package server.service;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import server.exception.AppException;

@Path("/accountservice")
public class AccountService {

  @POST
  @Path("/upload/text")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response uploadTextFile(Attachment attachment, @Context HttpServletRequest request) {
    try {

      Locale clientLocale = request.getLocale();
      Calendar calendar = Calendar.getInstance(clientLocale);
      TimeZone clientTimeZone = calendar.getTimeZone();
      System.out.println(calendar);
      System.out.println("=================");
      System.out.println(clientTimeZone);

      AllUtils.validateTiming("07:00:00", "20:00:00", clientTimeZone.getID());
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
    }
    return Response.ok("DONE").build();
  }

  @GET
  @Path("/print/hello1")
  public Response printHello1() {
    throw new AppException(" AppException 1");
  }

  @GET
  @Path("/print/hello")
  public Response printHello() throws Exception {
    throw new Exception(" Exception 3");
  }
}