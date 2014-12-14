package jfs.smb2.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import jfs.common.IEncodable;

public class SessionSetupRequest implements IEncodable{
	public static final short FIXED_SIZE = 24;
	
	public short StructureSize;
	public byte Flags;
	public byte SecurityMode;
	public int Capabilities;
	public int Channel;
	public short SecurityBufferOffset;
	public short SecurityBufferLength;
	public long PreviousSessionId;
	public byte Buffer[];
	
	public SessionSetupRequest() {
		StructureSize = (short) (FIXED_SIZE + 1);
		Channel = 0;
		SecurityBufferOffset = (short) (PacketHeader.FIXED_SIZE + FIXED_SIZE);	
	}
	
	public static class FlagValues {
		public static byte SMB2_SESSION_FLAG_BINDING = 0x01;
		public static byte SMB2_SESSION_FLAG_ENCRYPT_DATA = 0x04;
	}
	
	public static class SecurityModeValues {
		public static byte SMB2_NEGOTIATE_SIGNING_ENABLED = 0x01;
		public static byte SMB2_NEGOTIATE_SIGNING_REQUIRED = 0x02;
	}
	
	public static class CapabilityValues {
		public static int SMB2_GLOBAL_CAP_DFS = 0x00000001;
		public static int SMB2_GLOBAL_CAP_UNUSED1 = 0x00000002;
		public static int SMB2_GLOBAL_CAP_UNUSED2 = 0x00000004;
		public static int SMB2_GLOBAL_CAP_UNUSED3 = 0x00000008;
	}

	@Override
	public byte[] toBytes() throws IOException {
		
		byte data[] = new byte[size()];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort(StructureSize);
		buffer.put(Flags);
		buffer.put(SecurityMode);
		buffer.putInt(Capabilities);
		buffer.putInt(Channel);
		buffer.putShort(SecurityBufferOffset);
		buffer.putShort(SecurityBufferLength);
		buffer.putLong(PreviousSessionId);
		if(Buffer != null) {
			buffer.put(Buffer);
		}
		return data;
	}

	@Override
	public int size() {
		return FIXED_SIZE +  SecurityBufferLength;
	}
	
}
