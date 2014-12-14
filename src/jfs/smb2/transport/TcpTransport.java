package jfs.smb2.transport;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

import jfs.authentication.ntlm.message.ChallengeMessage;
import jfs.smb2.message.NegotiateResponse;
import jfs.smb2.message.PacketHeader;
import jfs.smb2.message.SessionSetupResponse;
import jfs.smb2.message.basic.CommandValues;

public class TcpTransport {

	private Socket socket;

	public void connect(String server) throws IOException {
		InetAddress addr = InetAddress.getByName(server);
		socket = new Socket(addr, 445);
	}

	public void send(byte[] data) throws IOException {
		socket.getOutputStream().write(data);
	}

	public void receive() throws IOException {
		InputStream stream = socket.getInputStream();
		byte byteSize[] = new byte[4];
		stream.read(byteSize);
		int packetSize = (((int) byteSize[1] & 0xff) << 16)
				+ (((int) byteSize[2] & 0xff) << 8)
				+ ((int) byteSize[3] & 0xff);
		byte packetData[] = new byte[packetSize];
		stream.read(packetData);

		PacketHeader header = new PacketHeader();
		header.fromBytes(packetData, 0, (int) PacketHeader.FIXED_SIZE);

		switch (header.Command) {
		case CommandValues.NEGOTIATE:
			NegotiateResponse negotiateResponse = new NegotiateResponse();
			negotiateResponse.fromBytes(packetData,
					(int) PacketHeader.FIXED_SIZE, packetSize
							- PacketHeader.FIXED_SIZE);
			break;
		case CommandValues.SESSION_SETUP:
			SessionSetupResponse sessionSetupResponse = new SessionSetupResponse();
			sessionSetupResponse.fromBytes(packetData,
					(int) PacketHeader.FIXED_SIZE, packetSize
							- PacketHeader.FIXED_SIZE);
			
			ChallengeMessage ntlmChallengeMessage = new ChallengeMessage();
			ntlmChallengeMessage.fromBytes(sessionSetupResponse.Buffer, 0, sessionSetupResponse.Buffer.length);
			
			break;
		}
	}
}
