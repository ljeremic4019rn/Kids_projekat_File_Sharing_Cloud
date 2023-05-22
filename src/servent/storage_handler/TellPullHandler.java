package servent.storage_handler;

import app.AppConfig;
import servent.base_handler.MessageHandler;
import servent.base_message.Message;
import servent.base_message.MessageType;
import servent.base_message.util.MessageUtil;
import servent.storage_message.TellPullMessage;

public class TellPullHandler implements MessageHandler {

	private final Message clientMessage;
	
	public TellPullHandler(Message clientMessage) {
		this.clientMessage = clientMessage;
	}
	
	@Override
	public void run() {
		if (clientMessage.getMessageType() == MessageType.TELL_PULL) {
			TellPullMessage tellPullMessage = (TellPullMessage) clientMessage;

			if (tellPullMessage.getRequesterId() == AppConfig.myServentInfo.getChordId()) {//mi smo originalni pull request node
				AppConfig.chordState.addPulledFile(tellPullMessage.getFileInfo());
			}
			else {//prosledi dalje
				Message tellMessage = new TellPullMessage(tellPullMessage.getSenderIpAddress(), tellPullMessage.getSenderPort(),
						AppConfig.chordState.getNextNodeIp(), AppConfig.chordState.getNextNodePort(),
						tellPullMessage.getRequesterIpAddress(), tellPullMessage.getRequesterId(), tellPullMessage.getFileInfo());
				MessageUtil.sendMessage(tellMessage);
			}
		} else {
			AppConfig.timestampedErrorPrint("Tell get handler got a message that is not TELL_GET");
		}
	}

}
