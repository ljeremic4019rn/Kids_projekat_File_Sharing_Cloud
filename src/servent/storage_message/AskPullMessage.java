package servent.storage_message;



import app.file_util.FileInfo;
import servent.base_message.BasicMessage;
import servent.base_message.MessageType;

import java.io.Serial;

public class AskPullMessage extends BasicMessage {

	@Serial
	private static final long serialVersionUID = -8558031124520315033L;
	private final FileInfo fileInfo;
	private final int requesterId;


	public AskPullMessage(String senderIpAddress, int senderPort,String receiverIpAddress, int receiverPort, int requesterId, FileInfo fileInfo) {
		super(MessageType.ASK_PULL, senderIpAddress, senderPort, receiverIpAddress, receiverPort);
		this.fileInfo = fileInfo;
		this.requesterId = requesterId;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public int getRequesterId() {
		return requesterId;
	}


}
