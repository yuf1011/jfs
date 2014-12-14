package jfs.authentication.ntlm.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;

import jfs.authentication.ntlm.message.basic.AvPair;
import jfs.authentication.ntlm.message.basic.VersionStructure;
import jfs.common.IDecodable;

public class ChallengeMessage implements IDecodable {

	public static final short FIXED_SIZE = 56;

	public long Signature;
	public int MessageType;
	public short TargetNameLen;
	public short TargetNameMaxLen;
	public int TargetNameBufferOffset;
	public int NegotiateFlags;
	public long ServerChallenge;
	public long Reserved;
	public short TargetInfoLen;
	public short TargetInfoMaxLen;
	public int TargetInfoBufferOffset;
public VersionStructure Version;
	public byte TargetName[];
	public AvPair TargetInfo[];

	@Override
	public int size() {
		return FIXED_SIZE + TargetNameLen + TargetInfoLen;
	}

	@Override
	public int fromBytes(byte[] data, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.wrap(data, offset, length);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		Signature = buffer.getLong();
		MessageType = buffer.getInt();
		assert MessageType == NtlmMessageTypeValues.NtLmChallenge;
		TargetNameLen = buffer.getShort();
		TargetNameMaxLen = buffer.getShort();
		TargetNameBufferOffset = buffer.getInt();
		NegotiateFlags = buffer.getInt();
		ServerChallenge = buffer.getLong();
		Reserved = buffer.getLong();
		TargetInfoLen = buffer.getShort();
		TargetInfoMaxLen = buffer.getShort();
		TargetInfoBufferOffset = buffer.getInt();
		Version = new VersionStructure();
		Version.fromBytes(data, offset + buffer.position(), VersionStructure.FIXED_SIZE);
		buffer.position(buffer.position() + VersionStructure.FIXED_SIZE);
		if (TargetNameLen != 0) {
			TargetName = new byte[TargetNameLen];
			buffer.position(TargetNameBufferOffset);
			buffer.get(TargetName);
		}

		int offsetInside = 0;
		if (TargetInfoLen != 0) {
			List<AvPair> l = new LinkedList<AvPair>();
			while (true) {
				assert offsetInside < TargetInfoLen;
				AvPair pair = new AvPair();
				int size = pair.fromBytes(data, offset + TargetInfoBufferOffset
						+ offsetInside, TargetInfoLen - offsetInside);
				offsetInside += size;
				l.add(pair);
				if (TargetInfoLen == offsetInside) {
					break;
				}
			}
			TargetInfo = new AvPair[0];
			TargetInfo = l.toArray(TargetInfo);
		}
		return size();
	}

}
