package project.bookkeeper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CsvWriterBoundary {	
	
	private CsvWriterBoundary() {
	    throw new IllegalStateException("Utility class");
	  }
	
	private static final int HALFRELEASE = MainControl.halfRelease;
	private static final String PROJECTNAME = MainControl.PROJECTNAME;


	
	public static void write (List<Data> list) {
		
		try (PrintWriter writer = new PrintWriter(new File("outputFinali\\"+PROJECTNAME+"_BuggyDataset_outputD2M1.csv"))) {

		      StringBuilder sb = new StringBuilder();
		      sb.append("Release");
		      sb.append(',');
		      
		      sb.append("Filename");
		      sb.append(',');
		      
		      sb.append("LOC");
		      sb.append(',');
		      
		      sb.append("NR");
		      sb.append(',');
		      
		      sb.append("nAuth");
		      sb.append(',');
		      
		      sb.append("locTouched");
		      sb.append(',');
		      
		      sb.append("locAdded");
		      sb.append(',');
		      
		      sb.append("max_locAdded");
		      sb.append(',');
		      
		      sb.append("avg_locAdded");
		      sb.append(',');
		      
		      sb.append("churn");
		      sb.append(',');
		      
		      sb.append("max_churn");
		      sb.append(',');
		      
		      sb.append("avg_churn");
		      sb.append(',');
		      
		      sb.append("chgSetSize");
		      sb.append(',');
		      
		      sb.append("max_chgSetSize");
		      sb.append(',');
		      
		      sb.append("avg_chgSetSize");
		      sb.append(',');
		      
		      sb.append("Buggy");
		      sb.append('\n');

		      for (int i=0;i<list.size();i++) {	  
		    	  
		    	  if (list.get(i).getRelease().getIndex()>HALFRELEASE){
		    		  break;		    		  
		    	  }
		    	  else {
		    	  		      
				      sb.append(list.get(i).getRelease().getIndex());	//release index
				      sb.append(',');
				      sb.append(list.get(i).getFilename());	//filename
				      sb.append(',');
				      sb.append(list.get(i).getLoc());	
				      sb.append(',');
				      sb.append(list.get(i).getNr());	
				      sb.append(',');
				      sb.append(list.get(i).getnAuth());	
				      sb.append(',');
				      sb.append(list.get(i).getLocTouched());	
				      sb.append(',');
				      sb.append(list.get(i).getLocAdded());	
				      sb.append(',');
				      sb.append(list.get(i).getMaxLocAdded());	
				      sb.append(',');
				      sb.append((int)(list.get(i).getAvgLocAdded()));	
				      sb.append(',');
				      sb.append(list.get(i).getChurn());	
				      sb.append(',');
				      sb.append(list.get(i).getMaxChurn());	
				      sb.append(',');
				      sb.append(list.get(i).getAvgChurn());	
				      sb.append(',');
				      sb.append(list.get(i).getChgSetSize());	
				      sb.append(',');
				      sb.append(list.get(i).getMaxChgSetSize());	
				      sb.append(',');
				      sb.append((int)(list.get(i).getAvgChgSetSize()));	
				      sb.append(',');
				      sb.append(list.get(i).getBuggy());	
				      sb.append('\n');
		    	 
		    	  }
		      }

		      writer.write(sb.toString());
		      Log.infoLog("done!");

		    } catch (FileNotFoundException e) {
		      Log.infoLog(e.getMessage());
		    }
		
	}
	
	
}