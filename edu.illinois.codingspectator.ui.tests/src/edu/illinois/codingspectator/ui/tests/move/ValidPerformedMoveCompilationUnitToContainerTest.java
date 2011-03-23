/**
 * This file is licensed under the University of Illinois/NCSA Open Source License. See LICENSE.TXT for details.
 */
package edu.illinois.codingspectator.ui.tests.move;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jface.dialogs.IDialogConstants;

import edu.illinois.codingspectator.ui.tests.RefactoringLogChecker;
import edu.illinois.codingspectator.ui.tests.RefactoringTest;
import edu.illinois.codingspectator.ui.tests.RefactoringLog.LogType;

/**
 * @author Balaji Ambresh Rajkumar
 */
public class ValidPerformedMoveCompilationUnitToContainerTest extends RefactoringTest {

	@Override
	protected String getTestFileName() {
		return "MoveCusTestFile";
	}

	@Override
	protected String getTestInputLocation() {
		return "move";
	}

	@Override
	protected void doExecuteRefactoring() {
		bot.selectFromPackageExplorer(getProjectName(), "src", "edu.illinois.codingspectator", getTestFileFullName());
		bot.invokeRefactoringFromMenu("Move...");
		bot.activateShellWithName("Move");
		bot.getCurrentTree().pressShortcut(org.eclipse.jface.bindings.keys.KeyStroke.getInstance('.'));
		
		bot.clickButtons(IDialogConstants.OK_LABEL);
	}
	

	@Override
	protected Collection<RefactoringLogChecker> getRefactoringLogCheckers() {
		return Arrays.asList(new RefactoringLogChecker(LogType.PERFORMED, getTestInputLocation(), getClass().getSimpleName(), getProjectName()), new RefactoringLogChecker(LogType.ECLIPSE,
				getTestInputLocation(), getClass().getSimpleName(), getProjectName()));
	}
}
