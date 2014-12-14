package jfs.smb2.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

import jfs.common.IDecodable;

public class NegotiateResponse implements IDecodable {
	public short StructureSize;
	public short SecurityMode;
	public short DialectRevision;
	public short Reserved;
	public UUID ServerGuid;
	public int Capabilities;
	public int MaxTransactSize;
	public int MaxReadSize;
	public int MaxWriteSize;
	public long SystemTime;
	public long ServerStartTime;
	public short SecurityBufferOffset;
	public short SecurityBufferLength;
	public int NegotiateContextOffset;
	public byte Buffer[];
	public byte NegotiateContextList[];

	public int fromBytes(byte data[], int offset, int length) {

		ByteBuffer buffer = ByteBuffer.wrap(data, offset, data.length - offset);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		StructureSize = buffer.getShort();
		SecurityMode = buffer.getShort();
		DialectRevision = buffer.getShort();
		Reserved = buffer.getShort();
		byte serverGuidBytes[] = new byte[16];
		buffer.get(serverGuidBytes);
		ServerGuid = UUID.nameUUIDFromBytes(serverGuidBytes);
		Capabilities = buffer.getInt();
		MaxTransactSize = buffer.getInt();
		MaxReadSize = buffer.getInt();
		MaxWriteSize = buffer.getInt();
		SystemTime = buffer.getLong();
		ServerStartTime = buffer.getLong();
		SecurityBufferOffset = buffer.getShort();
		SecurityBufferLength = buffer.getShort();
		NegotiateContextOffset = buffer.getInt();
		Buffer = new byte[SecurityBufferLength & 0xffff];
		buffer.get(Buffer);
		return size();
	}

	@Override
	public int size() {
		return SecurityBufferOffset + SecurityBufferLength;
	}
}
