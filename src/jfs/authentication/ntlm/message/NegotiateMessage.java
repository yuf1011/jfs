package jfs.authentication.ntlm.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import jfs.authentication.ntlm.message.basic.NegotiateFlagValues;
import jfs.authentication.ntlm.message.basic.VersionStructure;
import jfs.common.IEncodable;

public class NegotiateMessage implements IEncodable {

	public static final int FIXED_SIZE = 40;

	public byte Signature[];
	public int MessageType;
	public int NegotiateFlags;
	public short DomainNameLen;
	public short DomainNameMaxLen;
	public int DomainNameBufferOffset;
	public short WorkstationLen;
	public short WorkstationMaxLen;
	public int WorkstationBufferOffset;
	public VersionStructure Version;
	public byte DomainName[];
	public byte WorkstationName[];

	public NegotiateMessage() {
		Signature = new byte[] { 'N', 'T', 'L', 'M', 'S', 'S', 'P', 0 };
		MessageType = 0x00000001;
		NegotiateFlags = NegotiateFlagValues.NTLMSSP_REQUEST_TARGET
				| NegotiateFlagValues.NTLMSSP_NEGOTIATE_NTLM
				| NegotiateFlagValues.NTLMSSP_NEGOTIATE_ALWAYS_SIGN
				| NegotiateFlagValues.NTLMSSP_NEGOTIATE_UNICODE
				| NegotiateFlagValues.NTLMSSP_NEGOTIATE_EXTENDED_SESSIONSECURITY;
		Version = new VersionStructure();
	}

	@Override
	public byte[] toBytes() throws IOException {
		byte data[] = new byte[size()];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(Signature);
		buffer.putInt(MessageType);
		buffer.putInt(NegotiateFlags);
		buffer.putShort(DomainNameLen);
		buffer.putShort(DomainNameMaxLen);
		buffer.putInt(DomainNameBufferOffset);
		buffer.putShort(WorkstationLen);
		buffer.putShort(WorkstationMaxLen);
		byte VersionBytes[] = Version.toBytes();
		buffer.put(VersionBytes);
		if (DomainName != null) {
			buffer.put(DomainName);
		}
		if (WorkstationName != null) {
			buffer.put(WorkstationName);
		}
		return data;
	}

	@Override
	public int size() {
		return FIXED_SIZE + DomainNameLen + WorkstationLen;
	}

}
