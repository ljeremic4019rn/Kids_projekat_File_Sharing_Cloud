package servent.storage_handler;

import app.AppConfig;
import app.ChordState;
import app.ServentInfo;
import app.file_util.FileInfo;
import servent.base_handler.MessageHandler;
import servent.base_message.Message;
import servent.base_message.MessageType;
import servent.base_message.util.MessageUtil;
import servent.storage_message.AddInformMessage;

public class AddInformHandler implements MessageHandler {

    private final Message clientMessage;

    public AddInformHandler(Message clientMessage) { this.clientMessage = clientMessage; }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.ADD_INFORM) {
            AddInformMessage additionInfoMsg = (AddInformMessage) clientMessage;

            String requesterNode = additionInfoMsg.getReceiverIpAddress() + ":" + additionInfoMsg.getReceiverPort();
            int key = ChordState.chordHash(requesterNode);

            if (key == AppConfig.myServentInfo.getChordId()) {
                FileInfo fileInfo = additionInfoMsg.getFileInfo();
                AppConfig.chordState.addToStorage(fileInfo, additionInfoMsg.getSenderIpAddress(), additionInfoMsg.getSenderPort());
            }
            else {
                ServentInfo nextNode = AppConfig.chordState.getNextNodeForKey(key);
                Message nextSuccessMessage = new AddInformMessage(
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
