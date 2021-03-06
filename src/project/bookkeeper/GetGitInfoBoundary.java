package project.bookkeeper;




import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.TreeWalk;


public class GetGitInfoBoundary {
	
	protected static final List <Release> releases= MainControl.releases;
	protected static final List <String> classesList= MainControl.classesList;

	
	 
	private GetGitInfoBoundary() {
		    throw new IllegalStateException("Utility class");
	 }
	
	
	public static void getFilesPerRelease(Git git, List<Data> dbEntries, Repository repository) throws IOException, GitAPIException {    	


		
    	Iterable<RevCommit> log = git.log().all().call();
    	List<RevCommit> logCommitList = new  ArrayList<>();
    	
    	for (RevCommit commit : log) {
                logCommitList.add(commit);
        }
    	
    	//=== CONFRONTO DATE COMMIT E DATE RELEASE PER ASSOCIARE A UNA RELEASE TUTTI I SUOI COMMIT
    	
    	for (RevCommit commit : logCommitList) {
    		
    		//prendo un commit e gli associo la release (con confronto date)
    		//poi aggiungo quel commit alla lista di quella release
    		
    		associateCommit(commit);
    		
    	}
    	
    
    	
    	//=== ADESSO PRENDO L'ULTIMO COMMIT PER OGNI RELEASE

       	populateLastCommitRelease();
    	
    	    	
    	// === PRENDO TUTTI I FILE JAVA A PARTIRE DALL'ULTIMO COMMIT DI UNA RELEASE

    	retrieveJavaFiles(dbEntries, repository);

	}	
		
		
		
		
	
	public static void associateCommit(RevCommit commit) {
		
		int i;
		LocalDateTime commitDate;
		LocalDateTime releaseDate;
		
		commitDate= Instant.ofEpochSecond(commit.getCommitTime()).atZone(ZoneId.of("UTC")).toLocalDateTime();
		
		for (i=0;i<releases.size();i++) {
			
			releaseDate=releases.get(i).getDate();
			
			if (commitDate.compareTo(releaseDate)<0) {
				
				//il commit viene prima della data della release res, quindi sta dopo la release che ha superato e me lo ritrovo in quella successiva
				
				releases.get(i).getCommitsOfRelease().add(commit);

				break;
			}
			
		}
		
	}
	
	public static void populateLastCommitRelease() {
		
		List<RevCommit> commitList;
		RevCommit lastCommit;
		
		//ordina lista commit per data e prendi l'ultima
		
		for (int i=0;i<releases.size();i++) {
			
			commitList=releases.get(i).getCommitsOfRelease();	//lista commit della release
			
			//prendi la lista di commit di quella release

			if (commitList.isEmpty() && i!=0){
				
				//se questa release non ha commit, gli devo mettere come ultimo commit quello della release prima
				releases.get(i).setLastCommit(releases.get(i-1).getLastCommit());
			}
			else {

				Collections.sort(commitList, (c1, c2) -> Instant.ofEpochSecond(c1.getCommitTime()).atZone(ZoneId.of("UTC")).toLocalDateTime().compareTo(Instant.ofEpochSecond(c2.getCommitTime()).atZone(ZoneId.of("UTC")).toLocalDateTime()));
				
				for (int j=0;j<commitList.size();j++){

					//ora prendo l'ultima data
			        lastCommit=commitList.get(commitList.size()-1);
					releases.get(i).setLastCommit(lastCommit);
								        
				
				}
			}
	
		}
		
	}

	//prendo TUTTI i file in una release
	public static void retrieveJavaFiles(List<Data> dbEntries, Repository repository) throws IOException {
		
		int count;
		int fileLoc=0;
       

        for (int i=0;i<releases.size();i++) {
        	
        	RevCommit lastCommit= releases.get(i).getLastCommit();
        
            count=0;

            RevTree tree = lastCommit.getTree();
            TreeWalk treeWalk = new TreeWalk(repository);
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);
            while (treeWalk.next()) {
            	
            	if (treeWalk.getPathString().contains(".java")) {
            		            		
            		classesList.add(treeWalk.getPathString());
            		releases.get(i).getFilesOfRelease().add(treeWalk.getPathString());
            		
            		count++;
            		
            		Data dbEntry=new Data(releases.get(i), treeWalk.getPathString());
            		fileLoc= MetricsControl.loc(treeWalk, repository);
            		dbEntry.setLoc(fileLoc);
            		dbEntries.add(dbEntry);
         
            	}
            	

            }
            
           
 
         Log.infoLog("================================================================");
         Log.infoLog("count release "+releases.get(i).getIndex()+": "+count);

        }
	}
	
	
    
    public static List<RevCommit> getCommitsID(Git git, List<Ticket> ticketlist) throws GitAPIException {
    	
    	List<RevCommit> myCommits= new ArrayList <>();
    	int i;

    	

    	//get Commits
    	Iterable<RevCommit> log = git.log().call();
    	List<RevCommit> logCommitList = new  ArrayList<>();
    	
    	for (RevCommit commit : log) {
    		
                logCommitList.add(commit);

        }
    	
    	//inserisco in Ticket gli id dei commit che si riferiscono ad esso
    	for (i=0;i<ticketlist.size();i++) {
    		
    		for (RevCommit commit : logCommitList) {
    			
    			if (commit.getFullMessage().contains(ticketlist.get(i).getTicketID()+":")) {
    				
    				ticketlist.get(i).getRelatedCommits().add(commit.getId().toString());

    				myCommits.add(commit);
 
    			  
    				
    			}
    				
    		}
    		
    		
    	}
    	

    	return myCommits;
    	
    }
    

 


}