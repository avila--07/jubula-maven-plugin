package org.mule.tooling.jubula.cliexecutor.internal;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.mule.tooling.jubula.cliexecutor.Callback;

public class DefaultCliExecutor implements CliExecutor {
	
	@Override
	public int run(File executable, String params) {
		try {
			DefaultExecutor executor = new DefaultExecutor();
			CommandLine command = CommandLine.parse(executable.getAbsolutePath() + " " + params);
			return executor.execute(command);
		} catch (ExecuteException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void runAsync(File executable, final Callback callback, String params) {
		try {
			DefaultExecutor executor = new DefaultExecutor();
			CommandLine command = CommandLine.parse(executable.getAbsolutePath() + " " + params);
			executor.execute(command, new ExecuteResultHandler() {
				
				@Override
				public void onProcessFailed(ExecuteException returnCode) {
					callback.failure(returnCode.getExitValue());
				}
				
				@Override
				public void onProcessComplete(int returnCode) {
					callback.success(returnCode);
				}
			});
		} catch (ExecuteException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
