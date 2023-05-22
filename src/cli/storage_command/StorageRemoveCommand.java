package cli.storage_command;

import app.AppConfig;
import cli.basic_command.CLICommand;


public class StorageRemoveCommand implements CLICommand {

    @Override
    public String commandName() { return "remove"; }

    @Override
    public void execute(String args) {

        if (args == null || args.isEmpty()) {
            AppConfig.timestampedStandardPrint("Invalid argument for add command. Should be add path.");
            return;
        }

        String path = args.replace('/' , '\\');

//        AppConfig.chordState.gitRemove(path);

    }

}
