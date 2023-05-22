package cli.storage_command;

import app.AppConfig;
import cli.basic_command.CLICommand;


public class StoragePullCommand implements CLICommand {

    @Override
    public String commandName() {
        return "pull";
    }

    @Override
    public void execute(String args) {

        if (args == null || args.isEmpty()) {
            AppConfig.timestampedStandardPrint("Invalid arguments for pull command. Should be pull file name [file.txt] / dir name [subDir] ");
            return;
        }

        String[] splitArgs = args.split(" ");
        String path = splitArgs[0].replace('/', '\\');


        AppConfig.chordState.pullFile(path);


    }

}
