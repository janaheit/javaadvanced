package be.abis.exercise.repository;

import be.abis.exercise.exception.CompanyNotFoundException;
import be.abis.exercise.exception.CourseNotFoundException;
import be.abis.exercise.exception.EmailNotCorrectException;
import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class FileSessionRepository implements SessionRepository {
	private Logger log = LogManager.getLogger("exceptionLogger");

	private static FileSessionRepository fileSessionRepository;

	private ArrayList<Session> sessions = new ArrayList<>();

	private FileSessionRepository(){

		try {
			List<String> sessionStrings = Files.readAllLines(Paths.get("/temp/javacourses/sessions.txt"));
			for(String s:sessionStrings){
				Session session = createSession(s);
				sessions.add(session);
			}
			sessions.trimToSize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

	public Session createSession(String sessionLine) {
		String[] fields = sessionLine.split(";");

		Session session=null;

		if (fields[0].equals("P")) {
			try{
				session = createPublicSession(fields);
			} catch (CourseNotFoundException |PersonNotFoundException|CompanyNotFoundException e) {
				System.out.println(e.getMessage() + " Session was not created");
			}
		}
		else if (fields[0].equals("C")){
			try {
				session = createCompanySession(fields);
			} catch (CourseNotFoundException | CompanyNotFoundException | PersonNotFoundException e) {
				System.out.println(e.getMessage() + " Session was not created");
			}
		}

		return session;
	}

	private Session createPublicSession(String[] fields) throws CourseNotFoundException, CompanyNotFoundException, PersonNotFoundException {

		Course course = Course.getCourseByTitle(fields[1]);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(fields[2], formatter);
		Company location = FileCompanyRepository.getInstance().findCompanyByName(fields[3]);
		Instructor instructor = FilePersonRepository.getInstance().findPersonByName(fields[4]);
		int sessionNr = Integer.parseInt(fields[5]);

		PublicSession publicSession = new PublicSession(course,date,location,instructor);
		publicSession.setSessionNr(sessionNr);
		addParticipantsToSession(publicSession, fields);

		return publicSession;
	}

	private void addParticipantsToSession(PublicSession session, String[] fields) throws PersonNotFoundException {
		for (int x=6; x<fields.length; x++){
			try{
				session.addEnrolment();FilePersonRepository.getInstance().findPersonByName(fields[x]);
			} catch (PersonNotFoundException e) {
				System.out.println(e.getMessage() + " They will not be added to the participant list.");
			}
		}
	}


	private Session createCompanySession(String[] fields) throws CourseNotFoundException, CompanyNotFoundException, PersonNotFoundException {
		Course course = Course.getCourseByTitle(fields[1]);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(fields[2], formatter);
		Company location = FileCompanyRepository.getInstance().findCompanyByName(fields[3]);
		Instructor instructor = FilePersonRepository.getInstance().findPersonByName(fields[4]);

		// Company Session specific fields

		Company organiser = FileCompanyRepository.getInstance().findCompanyByName(fields[5]);

		int participantNr = Integer.parseInt(fields[6]);

		CompanySession companySession = new CompanySession(course,date,location,instructor, organiser, participantNr);


		return companySession;
	}




	@Override
	public void printSessionsSortedToFile(String file){
		//TODO make session comparable
		// Collections.sort(sessions);
		try (PrintWriter pw = new PrintWriter(file)){
			for (Session c: sessions){
				pw.println(c);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void writeAllSessionsToFile(String file, List<Session> sessions){
		try (PrintWriter pw = new PrintWriter(file)){
			for (Session s:sessions){
				StringBuilder sl = writeSessionLine(s);
				pw.println(sl);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	public void writeOneSessionToFile(String file, Session session){
		try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))){
			StringBuilder sl = writeSessionLine(session);
			pw.println(sl);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	public StringBuilder writeSessionLine(Session s){
		StringBuilder sessionLine = new StringBuilder();

		return sessionLine;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(ArrayList<Session> sessions) {
		this.sessions = sessions;
	}
	


	public static FileSessionRepository getInstance() {
		if (fileSessionRepository == null) fileSessionRepository = new FileSessionRepository();
		return fileSessionRepository;
	}

}
