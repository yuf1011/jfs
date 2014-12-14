package jfs.authentication.ntlm.message.basic;

import java.io.IOException;

import jfs.common.IEncodable;

public class Ntlmv2ClientChanllenge implements IEncodable {
	public static final short FIXED_SIZE = 28;

	public byte RespType;
	public byte HiRespType;
	public short Reserved1;
	public int Reserved2;
	public long TimeStamp;
	public byte ChallengeFromClient[];
	public int Reserved3;
	public AvPair AvPairs[];

	private int avPairSize = -1;

	@Override
	public byte[] toBytes() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		if (avPairSize == -1) {
			for (AvPair pair : AvPairs) {
				avPairSize += 4 + pair.AvLen;
			}
		}
		return FIXED_SIZE + avPairSize;
	}

}
