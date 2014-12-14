package jfs.smb2.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

import jfs.common.IEncodable;

public class NegotiateRequest implements IEncodable{
	public static final short FIXED_SIZE = 36;
	
	public short StructureSize;
	public short DialectCount;
	public short SecurityMode;
	public short Reserved;
	public int Capabilities;
	public UUID ClientGuid;
	public long ClientStartTime;
	public short Dialects[];
	public byte Padding[];
	public byte NegotiateContextList[];
	
	public class SecurityModeValues {
		public static final short SMB2_NEGOTIATE_SIGNING_ENABLED = 0x0001;
		public static final short SMB2_NEGOTIATE_SIGNING_REQUIRED = 0x0002;	
	}
	
	public NegotiateRequest() {
		StructureSize = FIXED_SIZE;
	}
	
	public byte[] toBytes() throws IOException{
		
		byte data[] = new byte[size()];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort(StructureSize);
		buffer.putShort(DialectCount);
		buffer.putShort(SecurityMode);
		buffer.putShort(Reserved);
		buffer.putInt(Capabilities);
		buffer.putLong(ClientGuid.getLeastSignificantBits());
		buffer.putLong(ClientGuid.getMostSignificantBits());
		buffer.putLong(ClientStartTime);
		for(int i = 0; i < DialectCount; ++i){
			buffer.putShort(Dialects[i]);
		}
		if(Padding != null) {
			buffer.put(Padding);
		}
		if(NegotiateContextList != null) {
			buffer.put(NegotiateContextList);
		}
		return data;
	}


	@Override
	public int size() {
		return FIXED_SIZE + 16 * DialectCount;
	}
}
