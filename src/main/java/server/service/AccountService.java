package server.service;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import server.obj.Data;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accountservice/")
public class AccountService {

  @POST
  @Path("/upload/text")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response uploadTextFile(Attachment attachment, @Context HttpServletRequest request) {
    try {
      Data demo = AllUtils.getData(attachment);
      System.out.println(demo);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
    }
    return Response.ok().build();
  }
}