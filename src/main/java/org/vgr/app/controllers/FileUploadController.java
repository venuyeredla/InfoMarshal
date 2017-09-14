package org.vgr.app.controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.vgr.app.service.ProfileService;
import org.vgr.ioc.annot.Controller;
import org.vgr.ioc.annot.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;	

@Controller(id="fileUploadController")
public class FileUploadController {
	private static final Logger LOG=LoggerFactory.getLogger(FileUploadController.class);
	    private static final int P_WIDTH = 500;
		private static final int P_HEIGHT = 300;
		private static final int M_WIDTH = 202;
		private static final int M_HEIGHT = 168;
		private static final int S_WIDTH = 25;
		private static final int S_HEIGHT = 25;
		
		@Inject(value="/home/venugopal/Documents/JWork/Work/AppData/Static/")
        private String basePath;
        @Inject(ref="profileService")
        private  ProfileService  profileService=null;
        
/*	@Handler(path="/upload.htm")
	public String uploadProfilePhoto(HttpRequest servletRequest,HttpResponse servletResponse){
		      
		        StringBuilder jsonString=new StringBuilder("{\"msg\":\"");
		        HttpSession sess=servletRequest.getSession(false);
     	       
		        Profile profile=(Profile)sess.getAttribute("loginUser");    
	                 try{
		                  List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(servletRequest);
		                  LOG.info("Mulitparts size : "+multiparts.size());
			              for(FileItem item : multiparts){
					               if(!item.isFormField()){
					                  String name = new File(item.getName()).getName();
					                  String path=this.basePath+profile.getUserId()+File.separator + name;
					                  LOG.info("File Path : "+path);
					                  item.write( new File(path));
					                  
					                  BufferedImage originalImage = ImageIO.read(new File(path));
					      		      int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
					                  
					                  BufferedImage pImg = resizeImage(originalImage, type, P_WIDTH, P_HEIGHT);
						    		  ImageIO.write(pImg, "jpg", new File(this.basePath+profile.getUserId()+File.separator+"p_"+name));
						    		  LOG.info(this.basePath+profile.getUserId()+File.separator+"p_"+name);
						    			
						    		  BufferedImage mImage = resizeImage(originalImage, type, M_WIDTH, M_HEIGHT);
						    		  ImageIO.write(mImage, "jpg", new File(this.basePath+profile.getUserId()+File.separator+"m_"+name));
						    			
						    		  BufferedImage sImage = resizeImage(originalImage, type , S_WIDTH, S_HEIGHT);
						    		  ImageIO.write(sImage, "jpg", new File(this.basePath+profile.getUserId()+File.separator+"s_"+name));
					      		     
					      		    BufferedImage resizeImageJpg = resizeImage(originalImage, type);
					    			ImageIO.write(resizeImageJpg, "jpg", new File("/home/venugopal/Pictures/venu1.jpg")); 
					    	 
					    			 
					    	 
					    			BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
					    			ImageIO.write(resizeImageHintJpg, "jpg", new File("/home/venugopal/Pictures/mkyong_hint_jpg.jpg")); 
					    	 
					    			BufferedImage resizeImageHintPng = resizeImageWithHint(originalImage, type);
					    			ImageIO.write(resizeImageHintPng, "png", new File("/home/venugopal/Pictures/mkyong_hint_png.jpg")); 
					    	
					      		    
					      	        String imgurl="static/"+profile.getUserId()+"/p_"+name;
					      		    profileService.updateImg(profile.getUserId(), name);
						    		jsonString.append("Uploaded Successfully\",\"path\" : \""+imgurl+"\"}");
					             }
			                  }
			                  
			              } catch (Exception ex) {
			            	  ex.printStackTrace();
			            	  jsonString.append("Upload failed\"}");
			              }         
			            
          servletRequest.setAttribute("jsonString", jsonString);
		  return "json";
	  }*/
	   
	   private static BufferedImage resizeImage(BufferedImage originalImage, int type,int IMG_WIDTH, int IMG_HEIGHT ){
			BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
			g.dispose();
		 
			return resizedImage;
	  }



	public ProfileService getProfileService() {
		return profileService;
	}
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

  /* private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){
		    	
			BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
			g.dispose();	
			g.setComposite(AlphaComposite.Src);
		 
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
			return resizedImage;
   }*/	
	   
}
