package jfs.authentication.ntlm.message.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import jfs.common.IDecodable;
import jfs.common.IEncodable;
import jfs.common.TransferableData;

public class AvPair implements IEncodable, IDecodable {
	public short AvId;
	public short AvLen;
	public TransferableData Value;

	public static class AvIdValues {
		public static final short MsvAvEOL = 0x0000;
		public static final short MsvAvNbComputerName = 0x0001;
		public static final short MsvAvNbDomainName = 0x0002;
		public static final short MsvAvDnsComputerName = 0x0003;
		public static final short MsvAvDnsDomainName = 0x0004;
		public static final short MsvAvDnsTreeName = 0x0005;
		public static final short MsvAvFlags = 0x0006;
		public static final short MsvAvTimestamp = 0x0007;
		public static final short MsvAvSingleHost = 0x0008;
		public static final short MsvAvTargetName = 0x0009;
		public static final short MsvChannelBindings = 0x000A;
	}

	@Override
	public byte[] toBytes() throws IOException {
		byte data[] = new byte[size()];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort(AvId);
		buffer.putShort(AvLen);
		byte valueBytes[] = Value.toBytes();
		if (AvLen != 0) {
			assert AvLen == valueBytes.length;
			buffer.put(valueBytes);
		}
		return data;
	}

	@Override
	public int size() {
		return AvLen + 4;
	}

	@Override
	public int fromBytes(byte[] data, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		AvId = buffer.getShort();
		AvLen = buffer.getShort();
		offset += 4;

		switch (AvId) {
		case AvPair.AvIdValues.MsvAvEOL:
			Value = null;
			break;
		case AvPair.AvIdValues.MsvAvNbComputerName:
			Value = new AvNbComputerName();
			break;
		case AvPair.AvIdValues.MsvAvNbDomainName:
			Value = new AvNbDomainName();
			break;
		case AvPair.AvIdValues.MsvAvDnsComputerName:
			Value = new AvDnsComputerName();
			break;
		case AvPair.AvIdValues.MsvAvDnsDomainName:
			Value = new AvDnsDomainName();
			break;
		case AvPair.AvIdValues.MsvAvDnsTreeName:
			Value = new AvDnsTreeName();
			break;
		case AvPair.AvIdValues.MsvAvFlags:
			Value = new AvFlags();
			break;
		case AvPair.AvIdValues.MsvAvSingleHost:
			Value = new AvSingleHost();
			break;
		case AvPair.AvIdValues.MsvAvTargetName:
			Value = new AvTargetName();
			break;
		case AvPair.AvIdValues.MsvAvTimestamp:
			Value = new AvTimestamp();
			break;
		case AvPair.AvIdValues.MsvChannelBindings:
			Value = new ChannelBindings();
			break;
		default:
			assert false;
		}

		if (Value != null) {
			Value.fromBytes(data, offset, AvLen);
		}
		
		return size();
	}
}
