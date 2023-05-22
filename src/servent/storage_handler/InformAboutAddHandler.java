package servent.storage_handler;

import app.AppConfig;
import app.ChordState;
import app.ServentInfo;
import app.file_util.FileInfo;
import servent.base_handler.MessageHandler;
import servent.base_message.Message;
import servent.base_message.MessageType;
import servent.base_message.util.MessageUtil;
import servent.storage_message.InformAboutAddMessage;

public class InformAboutAddHandler implements MessageHandler {

    private final Message clientMessage;

    public InformAboutAddHandler(Message clientMessage) { this.clientMessage = clientMessage; }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.ADD_INFORM) {
            InformAboutAddMessage additionInfoMsg = (InformAboutAddMessage) clientMessage;

            String requesterNode = additionInfoMsg.getReceiverIpAddress() + ":" + additionInfoMsg.getReceiverPort();
            int key = ChordState.chordHash(requesterNode);

            if (key == AppConfig.myServentInfo.getChordId()) {
                FileInfo fileInfo = additionInfoMsg.getFileInfo();
                AppConfig.chordState.addtoStorage(fileInfo, additionInfoMsg.getSenderIpAddress(), additionInfoMsg.getSenderPort());
            }
            else {
                ServentInfo nextNode = AppConfig.chordState.getNextNodeForKey(key);
                Message nextSuccessMessage = new InformAboutAddMessage(
                        additionInfoMsg.getSenderIpAddress(), additionInfoMsg.getSenderPort(),
                        nextNode.getIpAddress(), nextNode.getListenerPort(),
                        additionInfoMsg.getRequesterIpAddress(), additionInfoMsg.getRequesterPort(),
                        additionInfoMsg.getFileInfo());
                MessageUtil.sendMessage(nextSuccessMessage);
            }
        } else {
            AppConfig.timestampedErrorPrint("Add success handler got message that's not of type ADD_SUCCESS.");
        }

    }

}
