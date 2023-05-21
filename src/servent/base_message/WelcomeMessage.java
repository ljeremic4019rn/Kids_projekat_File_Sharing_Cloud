package servent.base_message;

import app.file_util.FileInfo;

import java.util.Map;

public class WelcomeMessage extends BasicMessage {

	private static final long serialVersionUID = -8981406250652693908L;

	private final Map<Integer, FileInfo> storageMap;
	
	public WelcomeMessage(String senderIpAddress, int senderPort, String receiverIpAddress, int receiverPort, Map<Integer, FileInfo> storageMap) {
		super(MessageType.WELCOME, senderIpAddress, senderPort, receiverIpAddress, receiverPort);
		this.storageMap = storageMap;
	}

	public Map<Integer, FileInfo> getStorageMap() {
		return storageMap;
	}

}
