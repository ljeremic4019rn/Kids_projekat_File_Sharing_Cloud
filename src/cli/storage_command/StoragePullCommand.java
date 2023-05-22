package cli.storage_command;

import app.AppConfig;
import cli.basic_command.CLICommand;


public class StoragePullCommand implements CLICommand {

    @Override
    public String commandName() { return "pull"; }

    @Override
    public void execute(String args) {

        if (args == null || args.isEmpty()) {
            AppConfig.timestampedStandardPrint("Invalid arguments for pull command. Should be pull name [version]");
            return;
        }

        String[] splitArgs = args.split(" ");
        String path = splitArgs[0].replace('/', '\\');
        int version = -1;
        if (splitArgs.length > 1) {
            try {
                version = Integer.parseInt(splitArgs[1]);
            } catch (NumberFormatException e) {
                AppConfig.timestampedErrorPrint("Version argument must be a number.");
                return;
            }
        }

//        Thread t = new Thread(new PullCollector(path, version));
//        t.start();

    }

}
