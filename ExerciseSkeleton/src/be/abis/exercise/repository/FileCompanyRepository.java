package be.abis.exercise.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.abis.exercise.model.Company;

public class FileCompanyRepository implements CompanyRepository {

	private static FileCompanyRepository fileCompanyRepository;
	
	private ArrayList<Company> companies = new ArrayList<Company>();

	private FileCompanyRepository(){
		try {
			List<String> compStrings = Files.readAllLines(Paths.get("c:\\temp\\javacourses\\companies.txt"));
			for(String s:compStrings){
				companies.add(new Company(s.trim()));
			}
			companies.trimToSize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(ArrayList<Company> companies) {
		this.companies = companies;
	}
	
	@Override
	public void printCompaniesSortedToFile(String file){
		Collections.sort(companies);
		try (PrintWriter pw = new PrintWriter(file)){
		    for (Company c: companies){
		    	pw.println(c);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static FileCompanyRepository getInstance() {
		if (fileCompanyRepository == null) fileCompanyRepository= new FileCompanyRepository();
		return fileCompanyRepository;
	}

}
