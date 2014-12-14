package jfs.smb2.client;

import java.io.IOException;

import jfs.authentication.ntlm.message.NegotiateMessage;
import jfs.smb2.message.RequestPacket;
import jfs.smb2.transport.TcpTransport;

public class ProtocolClient {

	public void connect(String serverName) throws IOException {
		TcpTransport transport = new TcpTransport();
		transport.connect(serverName);
		
		RequestPacket request = MessageBuilder.ComposeNegotiateRequest();
		
		transport.send(request.toBytes());
		transport.receive();
		
		NegotiateMessage NTLMNegotiateMessage = jfs.authentication.ntlm.client.MessageBuilder.ComposeNTLMNegotiateMessage();
		request = MessageBuilder.ComposeSessionSetupRequest(NTLMNegotiateMessage.toBytes());
		transport.send(request.toBytes());
		
		transport.receive();
		
	}
	
}
