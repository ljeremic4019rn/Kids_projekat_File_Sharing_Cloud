package cli.storage_command;

import app.AppConfig;
import app.file_util.FileInfo;
import app.file_util.FileUtils;
import cli.basic_command.CLICommand;
import mutex.TokenMutex;

import java.io.IOException;

public class StorageAddCommand implements CLICommand {

    @Override
    public String commandName() {
        return "add";
    }

    @Override
    public void execute(String args) {

        if (args == null || args.isEmpty()) {
            AppConfig.timestampedStandardPrint("Invalid argument for add command. Should be add path.");
            return;
        }

        String path = args.replace('/' , '\\');

        if (FileUtils.isPathFile(AppConfig.ROOT_DIR, path)) {

            TokenMutex.lock();//todo LOCK 1a

            FileInfo fileInfo = FileUtils.getFileInfoFromPath(AppConfig.ROOT_DIR, path);
            if (fileInfo != null) {
                AppConfig.chordState.addToStorage(fileInfo, AppConfig.myServentInfo.getIpAddress(), AppConfig.myServentInfo.getListenerPort());
            }
        }
//        else {todo dir add
//            List<FileInfo> fileInfoList = FileUtils.getDirectoryInfoFromPath(AppConfig.ROOT_DIR, path);
//            if (!fileInfoList.isEmpty()) {
//                for (FileInfo fileInfo : fileInfoList) {
//                    AppConfig.chordState.gitAdd(fileInfo, AppConfig.myServentInfo.getIpAddress(), AppConfig.myServentInfo.getListenerPort());
//                }
//            }
//        }

    }

}
