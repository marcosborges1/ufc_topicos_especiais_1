import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;

public class RefactoringJava {

	private String projectName, urlRepository, tempProjectFolder, tempTSVFolder;

	public RefactoringJava(String projectName, String urlRepository) {
		this.projectName = projectName;
		this.urlRepository = urlRepository;
		this.tempProjectFolder = "temp/" + this.getProjectName();
		this.tempTSVFolder = "tempTSV/";
	}

	public String getProjectName() {
		return projectName;
	}

	public String getUrlRepository() {
		return urlRepository;
	}

	public String getTempTSVFolder() {
		return tempTSVFolder;
	}

	public String getTempProjectFolder() {
		return tempProjectFolder;
	}

	public Repository cloneRepository(GitService gitService) throws Exception {
		return gitService.cloneIfNotExists(this.getTempProjectFolder(), this.getUrlRepository());
	}

	public void printRefactorings(String commitId, Refactoring refactoring) {
		System.out.println("Refactorings at " + commitId + "\t" + refactoring);
	}

	public void registerRefactoringInTSVFile(String commitId, String content) {

		this.createDirIfNotExists(this.getTempTSVFolder());

		// The file name generated is based on pattern "temp"+"projectName"+".tsv"
		String filePath = this.getTempTSVFolder() + this.getProjectName() + ".tsv";

		File tempTSVFile = new File(filePath);
		try {
			FileOutputStream fos = new FileOutputStream(tempTSVFile, true);
			String newContent = commitId + "\t" + content + "\t1\n";
			fos.write(newContent.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createDirIfNotExists(String path) {

		File theDir = new File(path);
		if (!theDir.exists()) {
			theDir.mkdirs();
		}
	}

}
