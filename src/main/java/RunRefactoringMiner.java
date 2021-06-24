import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

//Omitted Details 
public class RunRefactoringMiner {
	
	public static void main(String[] args) throws Exception {
		
		// Required Services and Instances
		GitService gitService = new GitServiceImpl();
		GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
		final RefactoringJava refactoringJava = new RefactoringJava("greeDAO","https://github.com/greenrobot/greenDAO.git");
		
		//Get the repository by its cloning
		Repository repository = refactoringJava.cloneRepository(gitService);
		
		//Detects all refactorings from the repository's master branch 
		miner.detectAll(repository, "master", new RefactoringHandler() {
			@Override
			public void handle(String commitId, List<Refactoring> refactorings) {
				if (refactorings.size() > 0) {			
					for (Refactoring refactoring : refactorings) {
						refactoringJava.printRefactorings(commitId, refactoring);
						refactoringJava.registerRefactoringInTSVFile(commitId, refactoring.getName());
					}
				}
			}
		});
	}	
}