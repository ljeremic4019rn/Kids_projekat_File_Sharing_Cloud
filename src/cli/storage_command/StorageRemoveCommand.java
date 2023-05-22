package cli.storage_command;

import app.AppConfig;
import cli.basic_command.CLICommand;
import mutex.TokenMutex;


public class StorageRemoveCommand implements CLICommand {

    @Override
    public String commandName() {
        return "remove";
    }

    @Override
    public void execute(String args) {

        if (args == null || args.isEmpty()) {
            AppConfig.timestampedStandardPrint("Invalid argument for add command. Should be add path.");
            return;
        }


        String path = args.replace('/' , '\\');

        //proverimo da li postoji path, i proverimo da li smo mi node koji je originalno dodao file
        if (AppConfig.chordState.getStorageMap().containsKey(path)){
            if (AppConfig.chordState.getStorageMap().get(path).getOgNode() == AppConfig.myServentInfo.getChordId()){
                TokenMutex.lock();//LOCK 1r
                AppConfig.chordState.removeFileFromStorage(path);
                TokenMutex.unlock();//UNLOCK 1r
            } else AppConfig.timestampedErrorPrint("This node is not the main holder of the file, can not delete selected file");
        } else AppConfig.timestampedErrorPrint("Nonexistent path " + path);

    }

}
