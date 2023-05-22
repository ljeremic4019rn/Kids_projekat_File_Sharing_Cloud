package servent.base_message;

import app.file_util.FileInfo;

import java.util.Map;

public class WelcomeMessage extends BasicMessage {

	private static final long serialVersionUID = -8981406250652693908L;

	private final Map<String, FileInfo> storageMap;
	
	public WelcomeMessage(String senderIpAddress, int senderPort, String receiverIpAddress, int receiverPort, Map<String, FileInfo> storageMap) {
		super(MessageType.WELCOME, senderIpAddress, senderPort, receiverIpAddress, receiverPort);
		this.storageMap = storageMap;
	}

	public Map<String, FileInfo> getStorageMap() {
		return storageMap;
	}

}
