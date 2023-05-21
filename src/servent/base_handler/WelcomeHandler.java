package servent.base_handler;

import app.AppConfig;
import servent.base_message.Message;
import servent.base_message.MessageType;
import servent.base_message.UpdateMessage;
import servent.base_message.WelcomeMessage;
import servent.base_message.util.MessageUtil;

public class WelcomeHandler implements MessageHandler {

	private Message clientMessage;
	
	public WelcomeHandler(Message clientMessage) {
		this.clientMessage = clientMessage;
	}
	
	@Override
	public void run() {
		if (clientMessage.getMessageType() == MessageType.WELCOME) {
			WelcomeMessage welcomeMsg = (WelcomeMessage)clientMessage;
			
			AppConfig.chordState.init(welcomeMsg);

			UpdateMessage um = new UpdateMessage(AppConfig.myServentInfo.getIpAddress(), AppConfig.myServentInfo.getListenerPort(),
					AppConfig.chordState.getNextNodeIp(), AppConfig.chordState.getNextNodePort(), "");
			MessageUtil.sendMessage(um);
			
		} else {
			AppConfig.timestampedErrorPrint("Welcome handler got a message that is not WELCOME");
		}

	}

}
