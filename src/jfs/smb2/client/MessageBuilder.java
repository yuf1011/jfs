package jfs.smb2.client;

import java.util.UUID;

import jfs.smb2.message.NegotiateRequest;
import jfs.smb2.message.PacketHeader;
import jfs.smb2.message.RequestPacket;
import jfs.smb2.message.SessionSetupRequest;
import jfs.smb2.message.basic.CommandValues;
import jfs.smb2.message.basic.Dialect;

public class MessageBuilder {

	public static RequestPacket ComposeNegotiateRequest() {

		PacketHeader header = new PacketHeader();
		header.Command = CommandValues.NEGOTIATE;

		NegotiateRequest payload = new NegotiateRequest();
		payload.DialectCount = 1;
		payload.SecurityMode = NegotiateRequest.SecurityModeValues.SMB2_NEGOTIATE_SIGNING_ENABLED;
		payload.ClientGuid = UUID.randomUUID();
		payload.Dialects = new short[1];
		payload.Dialects[0] = Dialect.SMB2002;

		RequestPacket request = new RequestPacket();
		request.Header = header;
		request.Payload = payload;

		return request;
	}

	public static RequestPacket ComposeSessionSetupRequest(
			byte securityBuffer[]) {

		PacketHeader header = new PacketHeader();
		header.Command = CommandValues.SESSION_SETUP;
		header.MessageId = 1;

		SessionSetupRequest payload = new SessionSetupRequest();
		payload.Capabilities = SessionSetupRequest.CapabilityValues.SMB2_GLOBAL_CAP_DFS;
		payload.SecurityMode = SessionSetupRequest.SecurityModeValues.SMB2_NEGOTIATE_SIGNING_ENABLED;
		payload.SecurityBufferLength = (short) securityBuffer.length;
		payload.Buffer = securityBuffer;

		RequestPacket request = new RequestPacket();
		request.Header = header;
		request.Payload = payload;

		return request;
	}

}
