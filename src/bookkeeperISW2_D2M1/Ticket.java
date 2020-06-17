package bookkeeperISW2_D2M1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;

public class Ticket {

	
	private String ticketID;
	private List<Integer> AV;
	private LocalDate resolutionDate;	//FV
	private int FV;
	private LocalDate createdDate;	//OV
	private int OV;
	private int IV;
	
	private List<String> relatedCommits;	//lista ID commits
	private List<String> relatedJavaFiles;	//classi toccate dai commit
	
	private int numTicket;
	private int p;
	
	public Ticket(String myID, List<Integer> myAV, LocalDate res, LocalDate cr) {
		setTicketID(myID);
		setAV(myAV);
		setResolutionDate(res);
		setCreatedDate(cr);
		
		this.initRelatedCommits();
		this.initRelatedJavaFiles();
		this.setNumTicket();
	}
	
	public void setNumTicket() {
		
		int beginIndex;
		String temp;
		beginIndex=this.getTicketID().indexOf("-")+1;
		
		
		
		temp=  this.getTicketID().substring(beginIndex);
		this.numTicket= Integer.parseInt(temp);

	}
	
	
	
	public void initRelatedCommits() {
		
		this.relatedCommits= new ArrayList<String>();
	}

	public void initRelatedJavaFiles() {
		
		this.relatedJavaFiles= new ArrayList<String>();
	}

	public String getTicketID() {
		return ticketID;
	}


	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}


	public List<Integer> getAV() {
		return AV;
	}


	public void setAV(List<Integer> aV) {
		AV = aV;
	}


	public LocalDate getResolutionDate() {
		return resolutionDate;
	}


	public void setResolutionDate(LocalDate resolutionDate) {
		this.resolutionDate = resolutionDate;
	}


	public LocalDate getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}


	public List<String> getRelatedCommits() {
		return relatedCommits;
	}


	public void setRelatedCommitss(List<String> relatedCommits) {
		this.relatedCommits = relatedCommits;
	}



	public int getFV() {
		return FV;
	}



	public void setFV(int fV) {
		FV = fV;
	}



	public int getOV() {
		return OV;
	}



	public void setOV(int oV) {
		OV = oV;
	}



	public int getIV() {
		return IV;
	}



	public void setIV2(int iV) {
		IV = iV;
		
		Integer myInteger = new Integer(5000);
		int i = myInteger.intValue();
			
	}


	public void setIV(Integer iV) {

		int i = iV.intValue();
		IV = i;
			
	}



	public List<String> getRelatedJavaFiles() {
		return relatedJavaFiles;
	}



	public void setRelatedJavaFiles(List<String> relatedJavaFiles) {
		this.relatedJavaFiles = relatedJavaFiles;
	}

	public int getNumTicket() {
		return numTicket;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}
	
}