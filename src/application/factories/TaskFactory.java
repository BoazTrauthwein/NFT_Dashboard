//package application.factories;
//
//import java.util.TimerTask;
//
//import application.controller.MyController;
//
//
//public class TaskFactory {
//	
//	public static TimerTask getTask(String taskType){
//	      if(taskType == null){
//	         return null;
//	      }		
//	      
//	      
//	      if(taskType.equalsIgnoreCase("EmailsTask")){
//	         return  new EmailsTask();
//	         
//	      } else if(taskType.equalsIgnoreCase("RefreshTask")){
//	         return  new RefreshTask();
//	         
//	      } 
//	      
//	      return null;
//	   }
//}
