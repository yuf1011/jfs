package jfs.smb2.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import jfs.common.IDecodable;
import jfs.common.IEncodable;

public class PacketHeader implements IEncodable, IDecodable {

	public static final short FIXED_SIZE = 64;

	public byte ProtocolId[];
	public short StructureSize;
	public short CreditCharge;
	public int Status;
	public short Command;
	public short Credit;
	public int Flags;
	public int NextCommand;
	public long MessageId;
	public int Reserved;
	public int TreeId;
	public long SessionId;
	public byte Signature[];

	public PacketHeader() {
		ProtocolId = new byte[] { (byte) 0xFE, 'S', 'M', 'B' };
		StructureSize = FIXED_SIZE;
		Signature = new byte[16];
	}

	public class FlagsValues {
		public static final int SMB2_FLAGS_SERVER_TO_REDIR = 0x00000001;
		public static final int SMB2_FLAGS_ASYNC_COMMAND = 0x00000002;
		public static final int SMB2_FLAGS_RELATED_OPERATIONS = 0x00000004;
		public static final int SMB2_FLAGS_SIGNED = 0x00000008;
		public static final int SMB2_FLAGS_DFS_OPERATIONS = 0x10000000;
		public static final int SMB2_FLAGS_REPLAY_OPERATION = 0x20000000;
	}

	@Override
	public byte[] toBytes() throws IOException {

		byte data[] = new byte[size()];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		buffer.put(ProtocolId);
		buffer.putShort(StructureSize);
		buffer.putShort(CreditCharge);
		buffer.putInt(Status);
		buffer.putShort(Command);
		buffer.putShort(Credit);
		buffer.putInt(Flags);
		buffer.putInt(NextCommand);
		buffer.putLong(MessageId);
		buffer.putInt(Reserved);
		buffer.putInt(TreeId);
		buffer.putLong(SessionId);
		buffer.put(Signature);

		return data;
	}

	public int size() {
		return FIXED_SIZE;
	}

	public int fromBytes(byte[] data, int offset, int length) {
		byte protocolId[] = new byte[4];
		ProtocolId = protocolId;
		byte signature[] = new byte[16];
		Signature = signature;

		ByteBuffer buffer = ByteBuffer.wrap(data, offset, 64);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		buffer.get(ProtocolId, 0, 4);
		StructureSize = buffer.getShort();
		Status = buffer.getInt();
		CreditCharge = buffer.getShort();
		Command = buffer.getShort();
		Credit = buffer.getShort();
		Flags = buffer.getInt();
		NextCommand = buffer.getInt();
		MessageId = buffer.getLong();
		Reserved = buffer.getInt();
		TreeId = buffer.getInt();
		SessionId = buffer.getLong();
		buffer.get(Signature, 0, 16);
		return FIXED_SIZE;
	}
}
