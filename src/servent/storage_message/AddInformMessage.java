package servent.storage_message;


import app.file_util.FileInfo;
import servent.base_message.BasicMessage;
import servent.base_message.MessageType;

import java.io.Serial;

public class AddInformMessage extends BasicMessage {

    @Serial
    private static final long serialVersionUID = 7277911492888707017L;
    private final String requesterIpAddress;
    private final int requesterPort;
    private final FileInfo fileInfo;

    public AddInformMessage(String senderIpAddress, int senderPort, String receiverIpAddress, int receiverPort,
                            String requesterIpAddress, int requesterPort, FileInfo fileInfo) {
        super(MessageType.ADD_INFORM, senderIpAddress, senderPort, receiverIpAddress, receiverPort);

        this.requesterIpAddress = requesterIpAddress;
        this.requesterPort = requesterPort;
        this.fileInfo = fileInfo;
    }

    public String getRequesterIpAddress() { return requesterIpAddress; }

    public int getRequesterPort() { return requesterPort; }

    public FileInfo getFileInfo() { return fileInfo; }

}
